#include <Wire.h>

#define VRX_PIN A2  // Arduino pin connected to VRX pin
#define VRY_PIN A3  // Arduino pin connected to VRY pin

#define modeSwitchKnop 10
#define noodstop 7
#define jSwitch 6

#define GroenLED 2
#define GeelLED 4
#define RoodLED 5

int xValue = 0;  // To store value of the X axis
int yValue = 0;  // To store value of the Y axis

int zAxisMode = 0;

int jSwitchLast;
int jSwitchCurrent;

bool noodstopTriggered = false;
bool manual = true;
bool yLimit = false;
bool xLimit = false;

long x_axis = 0;
long y_axis = 0;

void setup() {
  TCCR2B = TCCR2B & B11111000 | B00000111;  // for PWM frequency of 30.64 Hz

  //Start I2C Bus as Master
  Wire.begin();

  // put your setup code here, to run once:
  Serial.begin(9600);

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

  //COARDS PRINTEN
  // Serial.print("X-Axis: ");
  // Serial.println(map(x_axis, 0, 450, 1, 500));
  // Serial.print("Y-Axis: ");
  // Serial.println(map(y_axis, 0, 500, 1, 500));

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
  } else if (noodstopCheck() && noodstopTriggered) {
    noodstopTriggered = false;
    digitalWrite(5, LOW);
    delay(300);
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
    // Serial.println("Toggled-Z");

    zAxisMode = !zAxisMode;

    // Serial.println(zAxisMode);
  }


  // if(joystickSwitch()){
  //   Serial.println("SWITCH");
  //   sendCommand("AAN");
  // } else {
  //   sendCommand("UIT");
  // }

  // UITLEZEN JOYSTICK
  if (manual && !noodstopTriggered) {
    xValue = analogRead(VRY_PIN);
    yValue = analogRead(VRX_PIN);

    // Serial.println(yValue);

    if (zAxisMode == 1) {
      digitalWrite(9, HIGH);
      digitalWrite(8, HIGH);
      if (yValue < 100) {
        // Serial.println("vooruit");
        sendCommand("VOOR");
      } else if (yValue > 800) {
        // Serial.println("achteruit");
        sendCommand("ACHTER");
      } else {
        sendCommand("");
      }

    } else {
      sendCommand("");
      if (xValue < 100) {
        digitalWrite(12, HIGH);  //Establishes forward direction of Channel A
        digitalWrite(9, LOW);    //Disengage the Brake for Channel A
        analogWrite(3, 200);     //Spins the motor on Channel A at full speed
        // Serial.println("naar rechts");
        x_axis++;
      } else if (xValue > 800 && !xLimit) {
        digitalWrite(12, LOW);  //Establishes backward direction of Channel A
        digitalWrite(9, LOW);   //Disengage the Brake for Channel A
        analogWrite(3, 200);    //Spins the motor on Channel A at full speed
        // Serial.println("naar links");
        x_axis--;
      } else {
        digitalWrite(9, HIGH);  //Disengage the Brake for Channel A
      }

      if (yValue < 50) {
        digitalWrite(13, LOW);  //Establishes up direction of Channel B
        digitalWrite(8, LOW);   //Disengage the Brake for Channel B
        analogWrite(11, 255);   //Spins the motor on Channel B at full speed
        // Serial.println("omhoog");
        y_axis++;
      } else if (yValue > 950 && !yLimit) {
        digitalWrite(13, HIGH);  //Establishes down direction of Channel B
        digitalWrite(8, LOW);    //Disengage the Brake for Channel B
        analogWrite(11, 200);    //Spins the motor on Channel B at full speed
        // Serial.println("omlaag");
        y_axis--;
      } else {
        digitalWrite(8, HIGH);  //Disengage the Brake for Channel A
      }
    }
    receivedFromSlave();
  }


  // put your main code here, to run repeatedly:




  if (noodstopTriggered) {  //Check of de noodstop is ingedrukt
    //Zet snelheid van beide motoren op 0
    //Zet daarna de brake aan op beide motoren

    //A
    analogWrite(3, 0);
    digitalWrite(9, HIGH);

    //B
    analogWrite(1, 0);
    digitalWrite(8, HIGH);

    // Serial.println("NOODSTOP!!!");
    delay(1000);

  } else {  //Normale code voor besturen van motoren

    // Serial.print("x = ");
    // Serial.println(xValue);
    // Serial.println(", y = ");
    // Serial.println(yValue);
  }
}

bool noodstopCheck() {
  bool ingedrukt = digitalRead(noodstop);
  return !ingedrukt;
}

bool joystickSwitch() {
  bool ingedrukt = digitalRead(jSwitch);
  return !ingedrukt;
}

bool modeSwitch() {
  bool manual = digitalRead(modeSwitchKnop);
  return !manual;
}

//NIET AANRAKEN
//FUNCTIE: Stuurt een command naar de andere Arduino
void sendCommand(String cmd) {
  char buffer[cmd.length() + 10];
  cmd.toCharArray(buffer, sizeof(buffer));

  Wire.beginTransmission(9);
  Wire.write(buffer);
  Wire.endTransmission();
}

void omhoog() {
  digitalWrite(13, LOW);  //Establishes up direction of Channel B
  digitalWrite(8, LOW);   //Disengage the Brake for Channel B
  analogWrite(11, 0);     //Spins the motor on Channel B at full speed
  // Serial.println("omhoog");
}

void omlaag() {
  digitalWrite(13, HIGH);  //Establishes down direction of Channel B
  digitalWrite(8, LOW);    //Disengage the Brake for Channel B
  analogWrite(11, 200);    //Spins the motor on Channel B at full speed
  // Serial.println("omlaag");
}

void receivedFromSlave() {
  //This is the part where the master request a data from the slave
  //Wire.requestFrom("address of slave", "amount of bytes to request", true or false to not cut or cut communication)
  Wire.requestFrom(9, 5);
  String message;

  //Returns the number of bytes available for retrieval with read().
  //This should be called on a master device after a call to requestFrom() or on a slave inside the onReceive() handler.
  while (Wire.available()) {
    message = String(message + (char)Wire.read());
    // Serial.print(message);
  }
  // Serial.print(message);

  //   Writes the ("stuff here") on the serial monitor
  if (message.endsWith("yLimY")) {
    yLimit = true;
    y_axis = 0;
    if ((yValue > 950)) {
      analogWrite(11, 0);      //Spins the motor on Channel B at full speedbool yBeneden = true;
    }
  } else if (message.endsWith("yLimN")) {
    yLimit = false;
    if ((yValue > 950)) {
      analogWrite(11, 200);      //Spins the motor on Channel B at full speedbool yBeneden = true;
    }
  }

  if (message.endsWith("xLimY")) {
    xLimit = true;
    x_axis = 0;
    if ((xValue > 950)) {
      analogWrite(3, 0);      //Spins the motor on Channel B at full speedbool yBeneden = true;
    }
  } else if (message.endsWith("xLimN")) {
    xLimit = false;
    if ((xValue > 950)) {
      analogWrite(3, 200);      //Spins the motor on Channel B at full speedbool yBeneden = true;
    }
}
}