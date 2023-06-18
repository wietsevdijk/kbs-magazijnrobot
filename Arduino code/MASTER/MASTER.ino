#include <Wire.h>
#include <string.h>

#define VRX_PIN A2  // Arduino pin connected to VRX pin
#define VRY_PIN A3  // Arduino pin connected to VRY pin

#define modeSwitchKnop 10
#define noodstop 7
#define noodstopRelease 1
#define jSwitch 6

#define GroenLED 2
#define GeelLED 4
#define RoodLED 5

//Als debug aanstaat, werkt communicatie met de HMI niet
//De HMI leest de Serial output van de robot, wat niet werkt
//als er constant debug prints in de Serial monitor worden gegooid
// Dus alleen debug aanzetten als het echt nodig is
bool debug = false;
unsigned long currentDebugTime;
unsigned long previousDebugTime;

int currentMillis;

//initialize variables
int xValue = 0;  // To store value of the X axis from the joystick
int yValue = 0;  // To store value of the Y axis from the joystick

int zAxisMode = 0;

int jSwitchLast;
int jSwitchCurrent;

enum CommandMode { //Commandmodussen voor switch-case
  coordinaten,
  eindPunt,
  manualControl
};

CommandMode currentCommandMode;

String HMIcommand;
String response;


String message;                  //The message received from the slave
bool noodstopTriggered = false;  //boolean for emergency stop button
bool manual = true;              // boolean for manual/automatic mode button
bool yLimit = false;
bool xLimit = false;

bool goingHome = false;
bool calibrating = false;
bool isAtStart_x = false;
bool isAtStart_y = false;
bool homingComplete = false;

bool foundCoord = false;


long x_axis = 0;
long y_axis = 0;

void setup() {
  TCCR2B = TCCR2B & B11111000 | B00000111;  // for PWM frequency of 30.64 Hz

  //Start I2C Bus as Master
  Wire.begin();

  // put your setup code here, to run once:
  Serial.begin(9600);

  Serial.setTimeout(10);

  while (!Serial) {
    ;  // wait for serial port to connect.
  }

  //NOODSTOP
  pinMode(noodstop, INPUT_PULLUP);
  pinMode(noodstopRelease, INPUT_PULLUP);

  //Joystick button
  pinMode(jSwitch, INPUT_PULLUP);

  //modeSwitchKnop
  pinMode(modeSwitchKnop, INPUT_PULLUP);

  //Setup Channel A
  pinMode(12, OUTPUT);  //Initiates Motor Channel A pin
  pinMode(9, OUTPUT);   //Initiates Brake Channel A pin

  //Setup Channel B
  pinMode(13, OUTPUT);  //Initiates Motor Channel B pin
  pinMode(8, OUTPUT);   //Initiates Brake Channel B pin

  //Setup LEDs
  pinMode(2, OUTPUT);
  pinMode(4, OUTPUT);
  pinMode(5, OUTPUT);
}


void loop() {

  HMIcommand = "";
  response = "";
   //Read commands coming from HMI
  if (Serial.available()) {
    // read serial data
    HMIcommand = String(Serial.readString());
    // lcd.print(HMIcommand);
  }

  if(HMIcommand.length() > 0){ //Check of er een commando binnen is gekomen

    if (HMIcommand.equals("COORDS")) {
      sendToHMI("ModeCoords");
      currentCommandMode = coordinaten;
    } else if(HMIcommand.equals("END")){
      sendToHMI("ModeEnd");
      currentCommandMode = eindPunt;
    } else if(HMIcommand.equals("MANUAL")){
      sendToHMI("ModeManual");
      currentCommandMode = manualControl;
    } else {
    //Als het commando niet 1 van de commandomodussen is, dan:
    //Handel ingekomen commando af op basis van huidige commandomodus
    switch (currentCommandMode)
      {
      case coordinaten:
        //sendToHMI("Going to coord " + HMIcommand);
        sendToCoord(HMIcommand);
        sendToHMI("ARRIVED");

        break;

      case eindPunt:
        //sendToHMI("Going to end");
        moveToEnd();
        sendToHMI("ARRIVED");
        
        break;

      case manualControl:

        break;
      
      default:
        break;
      }

    }
  }

  // if (HMIcommand == "UP") {
  //   response = "omhoog";
  //   goUp();
  //   //delay(1000);
  // }

  // if (HMIcommand == "DOWN") {
  //   response = "omlaag";
  //   goDown();
  //   //delay(1000);
  // }

  // if (HMIcommand == "LEFT") {
  //   response = "links";
  //   goLeft();
  //   //delay(1000);
  // }

  // if (HMIcommand == "RIGHT") {
  //   goRight();
  //   response = "rechts";
  //   //delay(1000);
  // }

  // if (HMIcommand == "FORWARDS") {
  //   //z-axis
  //   response = "naar voren";
  // }

  // if (HMIcommand == "BACKWARDS") {
  //   //z-axis
  //   response = "naar achteren";
  // }



  // !!! NIET AANRAKEN !!! Gebruikt voor terugcommuniceren naar Java
  Serial.print(response);

  //Send calibrating
  sendCalibrating();

  //MODE CHECK
  if (!noodstopTriggered) {
    digitalWrite(manual ? 4 : 2, HIGH);
  }

  //NOODSTOP
  if (noodstopCheck() && !noodstopTriggered) {
    noodstopTriggered = true;
    digitalWrite(2, LOW);
    digitalWrite(4, LOW);
    digitalWrite(5, HIGH);
    delay(300);
  } else if (noodstopReleaseCheck() && noodstopTriggered) {
    noodstopTriggered = false;
    digitalWrite(5, LOW);
    delay(300);
  }

  //MODE SWITCH
  if (modeSwitch() && !noodstopTriggered && !manual) {
    manual = true;
    digitalWrite(2, LOW);
    digitalWrite(4, HIGH);
    sendCommand("MANUAL");
    delay(300);
  } else if (modeSwitch() && !noodstopTriggered && manual) {
    manual = false;
    goingHome = true;
    digitalWrite(2, HIGH);
    digitalWrite(4, LOW);
    sendCommand("AUTO");
    delay(300);
  }

  //TOGGLE Z-AXIS CONTROL
  jSwitchLast = jSwitchCurrent;
  jSwitchCurrent = joystickSwitch();
  if (jSwitchLast == 1 & jSwitchCurrent == 0) {

    if(debug){Serial.println("Toggled-Z");}

    zAxisMode = !zAxisMode;

    if(debug){Serial.println(zAxisMode);}
  }

  // UITLEZEN JOYSTICK
  if (manual && !noodstopTriggered) {
    xValue = analogRead(VRY_PIN);
    yValue = analogRead(VRX_PIN);

    if (zAxisMode == 1) { //Joystick stuurt Z-as aan
      digitalWrite(9, HIGH);
      digitalWrite(8, HIGH);
      if (yValue < 100) {
        if(debug){Serial.println("vooruit");}
        sendCommand("VOOR");
      } else if (yValue > 800) {
        if(debug){Serial.println("achteruit");}
        sendCommand("ACHTER");
      } else {
        sendCommand("");
      }

    } else { //Joystick stuurt X- en Y-as aan
      sendCommand("");
      if (xValue < 100) {
        goRight();  //execute function to make robot go right
      } else if (xValue > 800 && !xLimit) {
        goLeft();  // execute function to make robot go left
      } else {
        digitalWrite(9, HIGH);  //Engage the Brake for Channel A
      }

      if (yValue < 100) {

        goUp();  // execute function to make robot go up
      } else if (yValue > 800 && !yLimit) {
        goDown();  // execute function to make robot go down
      } else {
        digitalWrite(8, HIGH);  //Engage the Brake for Channel A
      }
    }
    receivedFromSlave();
  }
  if (noodstopTriggered) {  //Check of de noodstop is ingedrukt
    //Zet snelheid van beide motoren op 0
    //Zet daarna de brake aan op beide motoren
    brakeBoth();
    delay(1000);
  } else {  //Normale code voor besturen van motoren
  }

  //Startprocedure
  if (!manual && !noodstopTriggered && !homingComplete) {
    if (goingHome) {
      goToStartingPoint();
    }
  }
}

// ----- Functions from here -----

//Send message back to HMI
void sendToHMI(String input) {
  //Add start and end character to input
  input = ("-" + input + ";");
  response = input;
}

//Send robot to coordinate
//TODO: Change void to proper response for HMI
void sendToCoord(String coordinate){
  if(debug){Serial.println("STARTING COORD COMMAND");}
  String response;
  //Reset boolean
  foundCoord = false;

  //Send coordinate to slave
  sendCommand("GOTO" + coordinate);

  //lees commando maar doe er niks mee, cleart buffer
  receiveMotorCommandFromSlave();

  while(foundCoord == false){
    //Start listening to slave Arduino for commands
    response = receiveMotorCommandFromSlave();
    

    //X AXIS CONTROL
    if(response.endsWith("xMoveL")){
      goLeft();
    } else if(response.endsWith("xMoveR")){
      goRight();
    } else if(response.endsWith("dontMv")) {
      brakeX();
    }

    //X AXIS CONTROL
    if(response.endsWith("yMoveU")){
      goUp();
    } else if(response.endsWith("yMoveD")){
      goDown();
    } else if(response.endsWith("dontMv")){
      brakeY();
    }

    if(response.endsWith("CoordF")){
      if(debug){Serial.println(" !!!!!! ROBOT HAS ARRIVED AT " + coordinate);}
      foundCoord = true;
    }
  }
}

void moveToEnd (){
  if(debug){Serial.println("STARTING END COMMAND");}
  String response;
  //Reset boolean
  foundCoord = false;

  //Send end to slave
  sendCommand("END");

  //lees commando maar doe er niks mee, cleart buffer
  receiveMotorCommandFromSlave();

  while(foundCoord = false){
    //Start listening to slave Arduino for commands
    response = receiveMotorCommandFromSlave();

    //X AXIS CONTROL
    if(response.endsWith("xMoveL")){
      goLeft();
    } else if(response.endsWith("xMoveR")){
      goRight();
    } else if(response.endsWith("dontMv")) {
      brakeX();
    }

    //X AXIS CONTROL
    if(response.endsWith("yMoveU")){
      goUp();
    } else if(response.endsWith("yMoveD")){
      goDown();
    } else if(response.endsWith("dontMv")){
      brakeY();
    }

    if(response.endsWith("CoordF")){
      if(debug){Serial.println(" !!!!!! ROBOT HAS ARRIVED AT END");}
      foundCoord = true;
    }
    
  }
}

//checks emergency button

bool noodstopCheck() {
  bool ingedrukt = digitalRead(noodstop);
  return !ingedrukt;
}

bool noodstopReleaseCheck() {
  bool ingedrukt = digitalRead(noodstopRelease);
  return !ingedrukt;
}

//checks if joystick is pressed

bool joystickSwitch() {
  bool ingedrukt = digitalRead(jSwitch);
  return !ingedrukt;
}

//switches between modes

bool modeSwitch() {
  bool manual = digitalRead(modeSwitchKnop);
  return !manual;
}

//make robot go up
void goUp() {
  sendCommand("UP");
  digitalWrite(13, LOW);  //Establishes up direction of Channel B
  digitalWrite(8, LOW);   //Disengage the Brake for Channel B
  analogWrite(11, 255);   //Spins the motor on Channel B at full speed
}

//make robot go down
void goDown() {
  sendCommand("DOWN");
  digitalWrite(13, HIGH);  //Establishes down direction of Channel B
  digitalWrite(8, LOW);    //Disengage the Brake for Channel B
  analogWrite(11, 200);    //Spins the motor on Channel B at full speed
}

//stop Y axis movement
void brakeY() {
  digitalWrite(8, HIGH);
}

//make robot go left
void goLeft() {
  sendCommand("LEFT");
  digitalWrite(12, LOW);  //Establishes backward direction of Channel A
  digitalWrite(9, LOW);   //Disengage the Brake for Channel A
  analogWrite(3, 200);    //Spins the motor on Channel A at full speed
}

//make robot go right
void goRight() {
  sendCommand("RIGHT");
  digitalWrite(12, HIGH);  //Establishes forward direction of Channel A
  digitalWrite(9, LOW);    //Disengage the Brake for Channel A
  analogWrite(3, 200);     //Spins the motor on Channel A at full speed
}

//stop X axis movement
void brakeX() {
  digitalWrite(9, HIGH);
}

void sendCalibrating() {
  if(calibrating) {
    sendCommand("CALIBRATING");
  }
}

void goToStartingPoint() {
  //bool calibrating is ONLY true when robot has found home, 
  //but is aligning with starting coordinate (X1 Y5)
  //Receive command from slave
  receivedFromSlave();

  //Find X Home
  if(!xLimit && !calibrating) {
    goLeft();
    isAtStart_x = false;
  } else if(xLimit && !calibrating) {
    isAtStart_x = false;
    analogWrite(3, 0);    
  }

  //Find Y Home
  //NOTE: Check for X limit switch is needed for communication bug
  if(!yLimit && !calibrating && xLimit) {
    goDown();
    isAtStart_y = false;
  } else if(yLimit && !calibrating) {
    isAtStart_y = false;
    analogWrite(3, 0);    
  }

  //Tell the robot to start finding the start point (X1 Y5)
  if(xLimit && yLimit) {
    calibrating = true;
  }

  //Find starting point
  if(calibrating) {
    if(!isAtStart_x) {
      goRight();
    } else {
      analogWrite(3, 0);
    }
    if(!isAtStart_y) {
      goUp();
    } else {
      analogWrite(11, 0);
    }
    if(isAtStart_x && isAtStart_y) {
      calibrating = false;
      goingHome = false;
      homingComplete = true;

      //TEST COORDS - Dit is voor nu hardcoded, 
      // dit moet volledig worden weggehaald wanneer coordinaat ontvangen
      // vanaf Java compleet is
      // if(debug){Serial.println("SENDING TEST COORDS");}
      // sendToCoord("5.5");
      // sendToCoord("3.3");
      // sendToCoord("1.1");

      // ---    

    }
  }
}

void goToBox(int x, int y) {
    
}

//turn on brakes for both X and Y
void brakeBoth() {
  //A
  analogWrite(3, 0);
  digitalWrite(9, HIGH);

  //B
  analogWrite(1, 0);
  digitalWrite(8, HIGH);
}

//turn brake on
void brakeOn() {
  digitalWrite(2, LOW);
  digitalWrite(4, LOW);
  digitalWrite(5, HIGH);
  delay(300);
}

//turn brake off
void brakeOff() {
  digitalWrite(5, LOW);
  delay(300);
}

//-----------------------------------------------------------------------DO NOT TOUCH!!!---------------------------------------------------------------//
// sends command to slave

//NIET AANRAKEN
//FUNCTIE: Stuurt een command naar de andere Arduino

void sendCommand(String cmd) {
  char buffer[cmd.length() + 10];
  cmd.toCharArray(buffer, sizeof(buffer));

  Wire.beginTransmission(9);
  Wire.write(buffer);
  Wire.endTransmission();
}

String receivedFromSlave() {
  //This is the part where the master request a data from the slave
  //Wire.requestFrom("address of slave", "amount of bytes to request", true or false to not cut or cut communication)
  Wire.requestFrom(9, 5);
  String message;

  //Returns the number of bytes available for retrieval with read().
  //This should be called on a master device after a call to requestFrom() or on a slave inside the onReceive() handler.
  while (Wire.available()) {
    message = String(message + (char)Wire.read());
  }
  if(debug){Serial.println(message);}

  //   Writes the ("stuff here") on the serial monitor
  if (message.endsWith("yLimY")) {
    yLimit = true;
    y_axis = 0;
    if ((yValue > 800)) {
      analogWrite(11, 0);  //Spins the motor on Channel B at full speedbool yBeneden = true;
    }
  } else if (message.endsWith("yLimN")) {
    yLimit = false;
    if ((yValue > 800)) {
      analogWrite(11, 200);  //Spins the motor on Channel B at full speedbool yBeneden = true;
    }
  }

  if (message.endsWith("xLimY")) {
    xLimit = true;
    x_axis = 0;
    if ((xValue > 800)) {
      analogWrite(3, 0);  //Spins the motor on Channel B at full speedbool yBeneden = true;
    }
  } else if (message.endsWith("xLimN")) {
    xLimit = false;
    if ((xValue > 800)) {
      analogWrite(3, 200);  //Spins the motor on Channel B at full speedbool yBeneden = true;
    }
  }

  //Tijdelijke oplossing voor limitswitch probleem
  if (message.endsWith("StrtX")) {
    isAtStart_x = true;
  }
  
  if (message.endsWith("StrtY")) {
    isAtStart_y = true;
  }

  return message;
}

String receiveMotorCommandFromSlave() {
    //This is the part where the master request a data from the slave
  //Wire.requestFrom("address of slave", "amount of bytes to request", true or false to not cut or cut communication)
  Wire.requestFrom(9, 6);
  String message;

  //Returns the number of bytes available for retrieval with read().
  //This should be called on a master device after a call to requestFrom() or on a slave inside the onReceive() handler.
  while (Wire.available()) {
    message = String(message + (char)Wire.read());
  }
  if(debug){Serial.println(message);}

  return message;
}