#include <Wire.h>

#define VRX_PIN A2  // Arduino pin connected to VRX pin
#define VRY_PIN A3  // Arduino pin connected to VRY pin

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

  //NOODSTOP
  if (noodstopCheck() && !noodstopTriggered) {
    noodstopTriggered = true;
    digitalWrite(2, LOW);
    digitalWrite(4, LOW);
    digitalWrite(5, HIGH);
    delay(300);
  } else if (noodstopCheck() && noodstopTriggered) {
    noodstopTriggered = false;
    digitalWrite(2, LOW);
    digitalWrite(4, LOW);
    digitalWrite(5, LOW);
    delay(300);
  } 

  //TOGGLE Z-AXIS CONTROL
  jSwitchLast = jSwitchCurrent;
  jSwitchCurrent = joystickSwitch();
  if(jSwitchLast == 1 & jSwitchCurrent == 0){
    Serial.println("Toggled-Z");

    zAxisMode = !zAxisMode;

    Serial.println(zAxisMode);
  }


    // if(joystickSwitch()){
    //   Serial.println("SWITCH");
    //   sendCommand("AAN");
    // } else {
    //   sendCommand("UIT");
    // }

    // UITLEZEN JOYSTICK
    xValue = analogRead(VRY_PIN);
    yValue = analogRead(VRX_PIN);

    if(zAxisMode == 1){
      if(yValue < 50){
        Serial.println("vooruit");
        sendCommand("VOOR");
      } else if (yValue > 950){
        Serial.println("achteruit");
        sendCommand("ACHTER");
      } else {
        sendCommand("");
      }

    } else {

      if (xValue < 50) {
        digitalWrite(12, HIGH);  //Establishes forward direction of Channel A
        digitalWrite(9, LOW);    //Disengage the Brake for Channel A
        analogWrite(3, 200);     //Spins the motor on Channel A at full speed
        Serial.println("naar rechts");
      } else if (xValue > 950) {
        digitalWrite(12, LOW);  //Establishes backward direction of Channel A
        digitalWrite(9, LOW);   //Disengage the Brake for Channel A
        analogWrite(3, 200);    //Spins the motor on Channel A at full speed
        Serial.println("naar links");
      } else {
        digitalWrite(9, HIGH);  //Disengage the Brake for Channel A
      }

      if (yValue < 50) {
        digitalWrite(13, LOW);  //Establishes up direction of Channel B
        digitalWrite(8, LOW);   //Disengage the Brake for Channel B
        analogWrite(11, 255);   //Spins the motor on Channel B at full speed
        Serial.println("omhoog");
      } else if (yValue > 950) {
        digitalWrite(13, HIGH);  //Establishes down direction of Channel B
        digitalWrite(8, LOW);    //Disengage the Brake for Channel B
        analogWrite(11, 200);    //Spins the motor on Channel B at full speed
      } else {
        digitalWrite(8, HIGH);  //Disengage the Brake for Channel A
      }
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

    Serial.println("NOODSTOP!!!");
    delay(1000);

  } else {  //Normale code voor besturen van motoren

    Serial.print("x = ");
    Serial.print(xValue);
    Serial.print(", y = ");
    Serial.println(yValue);

    delay(200);

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

//NIET AANRAKEN
//FUNCTIE: Stuurt een command naar de andere Arduino
void sendCommand(String cmd) {
  char buffer[cmd.length() + 10];
  cmd.toCharArray(buffer, sizeof(buffer));

  Wire.beginTransmission(9);
  Wire.write(buffer);
  Wire.endTransmission();
}
