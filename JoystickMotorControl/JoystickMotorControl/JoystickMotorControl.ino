#include <Wire.h>

#define VRX_PIN  A2 // Arduino pin connected to VRX pin
#define VRY_PIN  A3 // Arduino pin connected to VRY pin

#define noodstop 7
#define jSwitch 6

int xValue = 0; // To store value of the X axis
int yValue = 0; // To store value of the Y axis

bool noodstopTriggered = false;

void setup() {
  TCCR2B = TCCR2B & B11111000 | B00000111; // for PWM frequency of 30.64 Hz

  //Start I2C Bus as Master
  Wire.begin();

  // put your setup code here, to run once:
  Serial.begin(9600);

  //NOODSTOP
  pinMode(noodstop, INPUT_PULLUP);

  //Joystick button
  pinMode(jSwitch, INPUT_PULLUP);

  //Setup Channel A
  pinMode(12, OUTPUT); //Initiates Motor Channel A pin
  pinMode(9, OUTPUT); //Initiates Brake Channel A pin

  //Setup Channel B
  pinMode(13, OUTPUT); //Initiates Motor Channel B pin
  pinMode(8, OUTPUT); //Initiates Brake Channel B pin
  
}


void loop() {
  //NOODSTOP
  if(noodstopCheck()){
    noodstopTriggered = true;
  }

  //I2C

  if(joystickSwitch()){
    Serial.println("SWITCH");
    sendCommand("AAN");
  } else {
    sendCommand("UIT");
  }



  // put your main code here, to run repeatedly:
  xValue = analogRead(VRX_PIN);
  yValue = analogRead(VRY_PIN);
  char directie;

  if(xValue > 500 && yValue < 50){
      directie = 'r';
    } else if (xValue > 500 && yValue > 950){
        directie = 'l';
      } else if (xValue < 50 && yValue > 500){
          directie = 'u';
        } else if (xValue > 1000 && yValue > 500){
            directie = 'd';
          } else {
              directie = 's';
          }



  if(noodstopTriggered){ //Check of de noodstop is ingedrukt
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

  } else { //Normale code voor besturen van motoren

  Serial.print("x = ");
  Serial.print(xValue);
  Serial.print(", y = ");
  Serial.println(yValue);
  delay(200);

    switch(directie){ //Controle X-as en Y-as
      case 'r':
        //forward
        digitalWrite(12, HIGH); //Establishes forward direction of Channel A
        digitalWrite(9, LOW);   //Disengage the Brake for Channel A
        analogWrite(3, 200);   //Spins the motor on Channel A at full speed
        Serial.println("naar rechts");
      break;

      case 'l':
          //backward
        digitalWrite(12, LOW); //Establishes backward direction of Channel A
        digitalWrite(9, LOW);   //Disengage the Brake for Channel A
        analogWrite(3, 200);   //Spins the motor on Channel A at full speed
        Serial.println("naar links");
      break;

      case 'u':

        digitalWrite(13, LOW); //Establishes up direction of Channel B
        digitalWrite(8, LOW);   //Disengage the Brake for Channel B
        analogWrite(11, 255);   //Spins the motor on Channel B at full speed

        Serial.println("omhoog");
      break;

      case 'd':

        digitalWrite(13, HIGH); //Establishes down direction of Channel B
        digitalWrite(8, LOW);   //Disengage the Brake for Channel B
        analogWrite(11, 200);   //Spins the motor on Channel B at full speed

        Serial.println("omlaag");
      break;

      case 's':
        Serial.println("niks");
        digitalWrite(9, HIGH);   //Engage the Brake for Channel A
        digitalWrite(8, HIGH);   //Engage the Brake for Channel B
        
      break;
      }
    }

  

  
}

bool noodstopCheck(){
  bool ingedrukt = digitalRead(noodstop);
  return !ingedrukt;
}

bool joystickSwitch(){
  bool ingedrukt = digitalRead(jSwitch);
  return !ingedrukt;
}

//FUNCTIE: Stuurt een command naar de andere Arduino
void sendCommand(String cmd){
  char buffer[cmd.length() + 10];
  cmd.toCharArray(buffer, sizeof(buffer));

  Wire.beginTransmission(9);
  Wire.write(buffer);
  Wire.endTransmission();
}
