#include <Wire.h>      // Master/slave library
#include <ezButton.h>  // Button library
#include <SharpIR.h>   // measuring distance library

#define Encoder_output_x 2  // encoder output X-axis
#define Encoder_output_y 5

// z-axis pins
#define pwmZ 11
#define directionZ 13
#define brakeZ 8

// Distance measuring unit
#define ir A0       //signal pin for distance measuring unit
#define model 1080  // used 1080 because model GP2Y0A21YK0F is used
SharpIR IR_prox(ir, model);


//byte for communication between arduino's
byte x;

//String to store received event command
String command = "";
char cmd = "";

//Int to store pulses from encoder
int Count_pulses = 0;

//To store the measurment data from z-axis
String Data;

String message = "";

ezButton limitSwitchX(4);  // create ezButton object that attaches to pin 4
ezButton limitSwitchY(6);  // create ezButton object that attaches to pin 5

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);

  //Start Communication between arduino's
  Wire.begin(9);
  Wire.onReceive(receiveEvent);
  Wire.onRequest(requestEvent);

  //Set encoders as input
  pinMode(Encoder_output_x, INPUT);  // sets the Encoder_output_x pin as the input
  //pinMode(Encoder_output_y, INPUT);  // sets the Encoder_output_y pin as the input
  //pinMode(Encoder_output_z, INPUT);  // sets the Encoder_output_z pin as the input

  //Interrupt function to read out encoders
  attachInterrupt(digitalPinToInterrupt(Encoder_output_x), DC_Motor_Encoder, RISING);

  //Setup motor Channel B (Z-axis)
  TCCR2B = TCCR2B & B11111000 | B00000111;  // for PWM frequency for motors of 30.64 Hz

  pinMode(pwmZ, OUTPUT);    //Initiates Motor Channel A pin
  pinMode(brakeZ, OUTPUT);  //Initiates Brake Channel A pin
  pinMode(directionZ, OUTPUT);

  //Set debounce time for limit switches on x-axis and y-axis
  limitSwitchX.setDebounceTime(50);  // set debounce time of limitswitch to 50 milliseconds
  limitSwitchY.setDebounceTime(50);  // set debounce time of limitswitch to 50 milliseconds
}

//receive command for master arduino
void receiveEvent(int bytes) {
  command = "";
  for (int i = 0; i < bytes; i++) {
    command = command + (char)Wire.read();
  }
  //x = Wire.read();
  //Serial.println("RECEIVED: " + command);
}

// send command

//this method is used to send data on the slave to the master
//Basically, when master asks for data from slave
void requestEvent() {
  //Inside the wire.write is the data to be sent to the master upon request
  Wire.write(message.c_str());
}

//count encoder pulses to measure distance
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

  // takes the time before the loop on the library begins
  unsigned long startTime = millis();

  // this returns the distance to the object you're measuring
  int dis = IR_prox.getDistance();  // read distance in cm

  // returns x-axis distance to the serial monitor
  // Serial.println("Mean distance: " + dis);

  // the following gives you the time taken to get the measurement
  unsigned long endTime = millis() - startTime;
  // Serial.println("Time taken ms): " + endTime);

  //receive event and turns motor on z-axis on or off
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

  //count pulses read by the encoder
  Serial.println("Pulses: " + Count_pulses);


  //check limitswitchX
  limitSwitchX.loop();  // MUST call the loop() function first

  // //Get state of limit switch on X-axis and do something
  int stateX = limitSwitchX.getState();
  if (stateX == HIGH) {
    Serial.println("The limit switch on X-Axis is: TOUCHED");
  } else {
    Serial.println("The limit switch on X-Axis is: UNTOUCHED");
  }


  //check limitswitchY
  limitSwitchY.loop();  // MUST call the loop() function first

  //Get state of limit switch on Y-axis and do something
  int stateY = limitSwitchY.getState();
  if (stateY == HIGH) {
    Serial.println("The limit switch on Y-Axis is: TOUCHED");
    message = "jemoeder";
    requestEvent();
  } else {
    Serial.println("The limit switch on Y-Axis is: UNTOUCHED");
  }
}
