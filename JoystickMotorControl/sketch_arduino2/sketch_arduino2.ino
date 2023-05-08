#include <Wire.h>

byte x;
String command = "";

void setup() {
  TCCR2B = TCCR2B & B11111000 | B00000111; // for PWM frequency of 30.64 Hz

  // put your setup code here, to run once:
  Serial.begin(9600);

  //I2C
  Wire.begin(9);
  Wire.onReceive(receiveEvent);

    //Setup Channel A
  pinMode(12, OUTPUT); //Initiates Motor Channel A pin
  pinMode(9, OUTPUT); //Initiates Brake Channel A pin
}

void receiveEvent(int bytes){
  command = "";
  for(int i = 0; i < bytes; i++) {
    command = command + (char)Wire.read();
  }

  //x = Wire.read();
  Serial.println("RECEIVED");
  Serial.println(command);
}

void loop() {
  // put your main code here, to run repeatedly:
  //Serial.println(x);

  if(command.equals("AAN")){
    digitalWrite(12, LOW);
    digitalWrite(9, LOW);
    analogWrite(3, 200);
  } else {
    digitalWrite(12, HIGH);
    digitalWrite(9, LOW);
    analogWrite(3, 200);
  }
}
