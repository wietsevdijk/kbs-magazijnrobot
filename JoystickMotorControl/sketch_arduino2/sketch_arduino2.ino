#include <Wire.h>
#include <ezButton.h>
#define Encoder_output_x 2
//#define Encoder_output_y 6
//#define Encoder_output_z 7

#define pwmZ 11
#define directionZ 13
#define brakeZ 8

byte x;
String command = "";

int Count_pulses = 0;

ezButton limitSwitchX(4);  // create ezButton object that attach to pin 4
ezButton limitSwitchY(5);  // create ezButton object that attach to pin 5

void setup() {
  TCCR2B = TCCR2B & B11111000 | B00000111;  // for PWM frequency of 30.64 Hz

  // put your setup code here, to run once:
  Serial.begin(9600);

  //I2C
  Wire.begin(9);
  Wire.onReceive(receiveEvent);

  //Set encoders as input
  pinMode(Encoder_output_x, INPUT);  // sets the Encoder_output_x pin as the input
  //pinMode(Encoder_output_y, INPUT);  // sets the Encoder_output_y pin as the input
  //pinMode(Encoder_output_z, INPUT);  // sets the Encoder_output_z pin as the input
  attachInterrupt(digitalPinToInterrupt(Encoder_output_x), DC_Motor_Encoder, RISING);

  //Setup Channel B
  pinMode(pwmZ, OUTPUT);  //Initiates Motor Channel A pin
  pinMode(brakeZ, OUTPUT);   //Initiates Brake Channel A pin
  pinMode(directionZ, OUTPUT);

  limitSwitchX.setDebounceTime(50); // set debounce time of limitswitch to 50 milliseconds
  limitSwitchY.setDebounceTime(50); // set debounce time of limitswitch to 50 milliseconds
}

void receiveEvent(int bytes) {
  command = "";
  for (int i = 0; i < bytes; i++) {
    command = command + (char)Wire.read();
  }

  //x = Wire.read();
  //Serial.println("RECEIVED: " + command);
}

void DC_Motor_Encoder() {
  int b = digitalRead(Encoder_output_x);
  if (b > 0) {
    Count_pulses++;
  } else {
    Count_pulses--;
  }
}

void loop() {
  // put your main code here, to run repeatedly:


  if (command.equals("VOOR")) {  //STUUR NAAR VOREN
    digitalWrite(directionZ, LOW);
    digitalWrite(brakeZ, LOW);
    analogWrite(pwmZ, 200);
  } else if (command.equals("ACHTER")) {  //STUUR NAAR ACHTER
    digitalWrite(directionZ, HIGH);
    digitalWrite(brakeZ, LOW);
    analogWrite(pwmZ, 200);
  } else {
    digitalWrite(brakeZ, HIGH);  //ENGAGE BRAKES
    analogWrite(pwmZ, 0);
  }

  Serial.println(Count_pulses);


  //check limitswitchX
  limitSwitchX.loop(); // MUST call the loop() function first

  if (limitSwitchX.isPressed())
    Serial.println("The limit switch: UNTOUCHED -> TOUCHED");

  if (limitSwitchX.isReleased())
    Serial.println("The limit switch: TOUCHED -> UNTOUCHED");

  int stateX = limitSwitchX.getState();
  if (stateX == LOW)
    Serial.println("The limit switch on X-Axis is: UNTOUCHED");
  else
    Serial.println("The limit switch on X-Axis is: TOUCHED");


  //check limitswitchY
  limitSwitchY.loop(); // MUST call the loop() function first

  if (limitSwitchY.isPressed())
    Serial.println("The limit switch: UNTOUCHED -> TOUCHED");

  if (limitSwitchY.isReleased())
    Serial.println("The limit switch: TOUCHED -> UNTOUCHED");

  int stateY = limitSwitchY.getState();
  if (stateY == LOW)
    Serial.println("The limit switch on Y-Axis is: UNTOUCHED");
  else
    Serial.println("The limit switch on Y-Axis is: TOUCHED");
}
