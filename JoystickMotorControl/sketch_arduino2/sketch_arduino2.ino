#include <Wire.h>
#define Encoder_output_x 5
#define Encoder_output_y 6
#define Encoder_output_z 7

byte x;
String command = "";
int Count_pulses = 0;

void setup() {
  TCCR2B = TCCR2B & B11111000 | B00000111; // for PWM frequency of 30.64 Hz

  // put your setup code here, to run once:
  Serial.begin(9600);

  //I2C
  Wire.begin(9);
  Wire.onReceive(receiveEvent);

  //Set encoders as input
  pinMode(Encoder_output_x,INPUT); // sets the Encoder_output_x pin as the input
  pinMode(Encoder_output_y,INPUT); // sets the Encoder_output_y pin as the input
  pinMode(Encoder_output_z,INPUT); // sets the Encoder_output_z pin as the input

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
  Serial.println("RECEIVED: " + command);
}

  void DC_Motor_Encoder(){
    int b = digitalRead(Encoder_output_x);
    if(b > 0){
    Count_pulses++;
  }
  else{
    Count_pulses--;
  }
}

void loop() {
  // put your main code here, to run repeatedly:
  

    if(command.equals("VOOR")){ //STUUR NAAR VOREN
    digitalWrite(12, LOW);
    digitalWrite(9, LOW);
    analogWrite(3, 200);
    } else if(command.equals("ACHTER")){ //STUUR NAAR ACHTER
    digitalWrite(12, HIGH);
    digitalWrite(9, LOW);
    analogWrite(3, 200);
   } else{
    digitalWrite(9, HIGH); //ENGAGE BRAKES
    analogWrite(3, 0);
  }
  
  Serial.println(Count_pulses);
    DC_Motor_Encoder();
}

  
