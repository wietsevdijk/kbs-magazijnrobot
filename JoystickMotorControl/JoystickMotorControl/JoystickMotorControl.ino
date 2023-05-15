// Includes and defines
#include <Wire.h>
#include <LiquidCrystal_I2C.h> // LiquidCrystal_I2C library
LiquidCrystal_I2C lcd(0x27, 20, 4); // 0x27 is the i2c address of the LCM1602 IIC v1 module (might differ)

#define VRX_PIN A2  // Arduino pin connected to VRX pin
#define VRY_PIN A3  // Arduino pin connected to VRY pin

#define modeSwitchKnop 10
#define noodstop 7
#define jSwitch 6

#define GroenLED 2
#define GeelLED 4
#define RoodLED 5

int currentMillis;

//initialize variables
int xValue = 0;  // To store value of the X axis from the joystick
int yValue = 0;  // To store value of the Y axis from the joystick

int zAxisMode = 0;

int jSwitchLast;
int jSwitchCurrent;

bool noodstopTriggered = false; //boolean for emergency stop button
bool manual = true; // boolean for manual/automatic mode button

String message; //The message received from the slave

void setup() {
  TCCR2B = TCCR2B & B11111000 | B00000111;  // for PWM frequency of 30.64 Hz

  //Start I2C Bus as Master
  Wire.begin();

  // put your setup code here, to run once:
  Serial.begin(9600);
  Serial.setTimeout(10);

  while (!Serial) {
    ; // wait for serial port to connect.
  }

  lcd.init();                      // initialize the lcd
  lcd.backlight();
  lcd.clear();
  lcd.setCursor(0, 0);

  //NOODSTOP
  pinMode(noodstop, INPUT_PULLUP);

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
  String HMIcommand = "";
  String response = "";
  // put your main code here, to run repeatedly:
   if (Serial.available()) {
    // read serial data
    HMIcommand = String(Serial.readString());
    lcd.print(HMIcommand);
  }

  if (HMIcommand == "UP") {
      response = "omhoog";
        goUp();
      //delay(1000);
    }

    if (HMIcommand == "DOWN") {
      response = "omlaag";
      goDown();
      //delay(1000);
    }

    if (HMIcommand == "LEFT") {
      response = "links";
      goLeft();
      //delay(1000);
    }

    if (HMIcommand == "RIGHT") {
      goRight();
      response = "rechts";
      //delay(1000);
    }

    if (HMIcommand == "FORWARDS") {
      //z-axis
      response = "naar voren";
    }

    if (HMIcommand == "BACKWARDS") {
      //z-axis
      response = "naar achteren";
    }

  response = String(response);
  Serial.print(response);

  
  //MODE CHECK
  if (!noodstopTriggered) {
    digitalWrite(manual ? 4 : 2, HIGH);
  }

  //NOODSTOP
  if (noodstopCheck() && !noodstopTriggered) {
    noodstopTriggered = true;
    brakeOn();
  } else if (noodstopCheck() && noodstopTriggered) {
    noodstopTriggered = false;
    brakeOff();
  }

  //MODE SWITCH
  if (modeSwitch() && !noodstopTriggered && !manual) {
    manual = true;
    digitalWrite(2, LOW);
    digitalWrite(4, HIGH);
    delay(300);
  } else if (modeSwitch() && !noodstopTriggered && manual) {
    manual = false;
    digitalWrite(2, HIGH);
    digitalWrite(4, LOW);
    delay(300);
  }

  //TOGGLE Z-AXIS CONTROL
  jSwitchLast = jSwitchCurrent;
  jSwitchCurrent = joystickSwitch();
  if (jSwitchLast == 1 & jSwitchCurrent == 0) {
    zAxisMode = !zAxisMode; //Toggled Z-axis
    Serial.println(zAxisMode);
  }

  // UITLEZEN JOYSTICK
  if (manual && !noodstopTriggered) {
    xValue = analogRead(VRY_PIN);
    yValue = analogRead(VRX_PIN);

    if (zAxisMode == 1) {
      digitalWrite(9, HIGH);
      digitalWrite(8, HIGH);
      if (yValue < 50) {
        sendCommand("VOOR"); //Send command to slave to make the z-axis go forwards
      } else if (yValue > 950) {
        sendCommand("ACHTER"); //Send command to slave to make the z-axis go backwards
      } else {
        sendCommand("");
      }

    } else {
      sendCommand("");
      if (xValue < 50) {
        goRight(); //execute function to make robot go right
      } else if (xValue > 950) {
        goLeft(); // execute function to make robot go left
      } else {
        digitalWrite(9, HIGH);  //Disengage the Brake for Channel A
      }

      if (yValue < 50) {
        goUp(); // execute function to make robot go up
      } else if (yValue > 950) {
        goDown(); // execute function to make robot go down
      } else {
        digitalWrite(8, HIGH);  //Disengage the Brake for Channel A
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
    delay(200);
  }
}

//Functions from here

//checks emergency button
bool noodstopCheck() {
  bool ingedrukt = digitalRead(noodstop);
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
  digitalWrite(13, LOW);  //Establishes up direction of Channel B
  digitalWrite(8, LOW);   //Disengage the Brake for Channel B
  analogWrite(11, 255);   //Spins the motor on Channel B at full speed

}

//make robot go down
void goDown() {
  digitalWrite(13, HIGH);  //Establishes down direction of Channel B
  digitalWrite(8, LOW);    //Disengage the Brake for Channel B
  analogWrite(11, 200);    //Spins the motor on Channel B at full speed
}

//make robot go left
void goLeft() {
  digitalWrite(12, LOW);  //Establishes backward direction of Channel A
  digitalWrite(9, LOW);   //Disengage the Brake for Channel A
  analogWrite(3, 200);    //Spins the motor on Channel A at full speed
}

//make robot go right
void goRight() {
  digitalWrite(12, HIGH);  //Establishes forward direction of Channel A
  digitalWrite(9, LOW);    //Disengage the Brake for Channel A
  analogWrite(3, 200);     //Spins the motor on Channel A at full speed
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

//send robot a bit up when it touches the x-switch
void robotTouchedLimitY() {
  if (message.endsWith("naarBoven")) {
    goUp();
    delay(150);
    message = "";
  }
}

//send robot a bit to the right when it touches the y-switch
void robotTouchedLimitX() {
  if (message.endsWith("naarRechts")) {
    goRight();
    delay(150);
    message = "";
  }
}

//-----------------------------------------------------------------------DO NOT TOUCH!!!---------------------------------------------------------------//
// sends command to slave
void sendCommand(String cmd) {
  char buffer[cmd.length() + 10];
  cmd.toCharArray(buffer, sizeof(buffer));

  Wire.beginTransmission(9);
  Wire.write(buffer);
  Wire.endTransmission();
}

// receives/requests command from slave
void receivedFromSlave() {
  Wire.requestFrom(9, 9); //("address of slave", "amount of bytes to request", true or false to not cut or cut communication)

  //This should be called on a master device after a call to requestFrom() or on a slave inside the onReceive() handler.
  while (Wire.available()) {
    //Get message from slave and convert to string
    message = String(message + (char)Wire.read());
  }

  //make robot go right a bit
  robotTouchedLimitX();

  //make robot go up a bit
  robotTouchedLimitY();
}
