#include <Wire.h>      // Master/slave library
#include <ezButton.h>  // Button library
#include <SharpIR.h>   // measuring distance library

#define Encoder_output_x 2   // encoder output X-axis
#define Encoder_output_y 3   // encoder output Y-axis

#define Encoder_output_z A3  // encoder output Z-axis

// z-axis pins
#define pwmZ 11
#define directionZ 13
#define brakeZ 8

//X and Y direction measure pins
#define directionX 5
#define directionY 7

// Distance measuring unit
#define ir A0       //signal pin for distance measuring unit
#define model 1080  // used 1080 because model GP2Y0A21YK0F is used
//SharpIR IR_prox(ir, model);

//Value used for debug prints
bool debug = true;
unsigned long currentDebugTime;
unsigned long previousDebugTime;


//byte for communication between arduino's
byte x;

//String to store received event command
String command = "";

//Int to store pulses from encoder
volatile int Count_pulses_x = 0;
volatile int Count_pulses_y = 0;

int dirX;
int dirY;

//To send a change only once
bool sendXLim = true;
bool sendYLim = true;

bool sendXStart = false;
bool sendYStart = false;

//If calibrating or not
bool calibrating = false;

//Modus
bool manual = true;

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
  pinMode(Encoder_output_x, INPUT_PULLUP);  // sets the Encoder_output_x pin as the input
  pinMode(Encoder_output_y, INPUT_PULLUP);  // sets the Encoder_output_y pin as the input
  pinMode(A3, INPUT);  // sets the Encoder_output_z pin as the input

  //Interrupt function to read out encoders
  attachInterrupt(digitalPinToInterrupt(Encoder_output_x), readEncoderX, RISING);
  attachInterrupt(digitalPinToInterrupt(Encoder_output_y), readEncoderY, RISING);

  //Setup motor Channel B (Z-axis)
  TCCR2B = TCCR2B & B11111000 | B00000111;  // for PWM frequency for motors of 30.64 Hz

  pinMode(pwmZ, OUTPUT);    //Initiates Motor Channel A pin
  pinMode(brakeZ, OUTPUT);  //Initiates Brake Channel A pin
  pinMode(directionZ, OUTPUT);

  //These pins are bridged Direction pins from the Master Arduino
  //Use these to measure the direction the motors are going when reading encoder
  //X: LOW = Left, HIGH = Right
  //Y: LOW = UP, HIGH = Down
  pinMode(directionX, INPUT);
  pinMode(directionY, INPUT);

  //Set debounce time for limit switches on x-axis and y-axis
  limitSwitchX.setDebounceTime(50);  // set debounce time of limitswitch to 50 milliseconds
  limitSwitchY.setDebounceTime(50);  // set debounce time of limitswitch to 50 milliseconds

  //Debug print
  if(debug){
    Serial.println("----- DEBUG MODE ENABLED -----");
  }
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

void readEncoderX() {
  dirX = digitalRead(directionX);
  if(dirX == HIGH){
    Count_pulses_x++;
  } else {
    Count_pulses_x--;
  }

}

void readEncoderY() {
  dirY = digitalRead(directionY);
  if(dirY == LOW){
    Count_pulses_y++;
  } else {
    Count_pulses_y--;
  }
  
}


//DEPRECATED
//count encoder pulses to measure distance
void DC_Motor_Encoder_x() {
  int b = digitalRead(Encoder_output_x);
  int i = b - (b % 100);
  if (command.equals("RIGHT") && b > 0) {
    Count_pulses_x++;
    // Serial.println(Count_pulses_x);
  }

  if (command.equals("LEFT") && b > 0) {
    Count_pulses_x--;
    // Serial.println(Count_pulses_x);
  }
}

//DEPRECATED
void DC_Motor_Encoder_y() {
  int b = digitalRead(Encoder_output_y);
  int i = b - (b % 100);
  if (command.equals("UP") && b > 0) {
    Count_pulses_y++;
    // Serial.println(Count_pulses_y);
  }

  if (command.equals("DOWN") && b > 0) {
    Count_pulses_y--;
    // Serial.println(Count_pulses_y);
  }
}

float Read_z_encoder() {
  float z_value = analogRead(Encoder_output_z) * (5.0 / 1023.0);
  float z_axis = (13 * (1 / z_value)) * 0.975;
  return z_axis;
}

void sendStartingPoint() {
  if (Count_pulses_x > 6 && !sendXStart) {
    sendXStart = true;
    message = "StrtX";
    requestEvent();
  } else if (Count_pulses_x <= 6 && sendXStart) {
    sendXStart = false;
    message = "StrtN";
    requestEvent();
  }
  if (Count_pulses_y > 300 && !sendYStart) {
    sendYStart = true;
    message = "StrtY";
    requestEvent();
  } else if (Count_pulses_y <= 300 && sendYStart) {
    sendYStart = false;
    message = "StrtN";
    requestEvent();
  }
}

void recieveCalibrating() {
  if (command.equals("CALIBRATING")) {
    calibrating = true;
  } else if (sendXStart && sendYStart) {
    calibrating = false;
  }
}

void recieveMode() {
  if (command.equals("MANUAL")) {
    manual = true;
  } else if (command.equals("AUTO")) {
    manual = false;
    Serial.println(manual);
  }
}

void slideOut() {
  if (Read_z_encoder() < 18) {
    digitalWrite(directionZ, LOW);
    digitalWrite(brakeZ, LOW);
    analogWrite(pwmZ, 200);
  } else {
    digitalWrite(brakeZ, HIGH);
    analogWrite(pwmZ, 0);
  }
}

void loop() {
  //DEBUG
  if(debug){
    //print elke 500ms debug print
    currentDebugTime = millis();
    if(currentDebugTime - previousDebugTime > 200){
      previousDebugTime = currentDebugTime;

      Serial.print("X: ");
      Serial.println(Count_pulses_x);
      Serial.print("Y: ");
      Serial.println(Count_pulses_y);

      //X: LOW = Left, HIGH = Right
      //Y: LOW = UP, HIGH = Down
      // dirX = digitalRead(directionX);
      // dirY = digitalRead(directionY);

      // if(dirX == HIGH){
      //   Serial.println("RIGHT");
      // } else{
      //   Serial.println("LEFT");
      // }

      // if(dirY == HIGH){
      //   Serial.println("DOWN");
      // } else{
      //   Serial.println("UP");
      // }

    }
  }

  // takes the time before the loop on the library begins
  unsigned long startTime = millis();

  // this returns the distance to the object you're measuring
  //  int dis = IR_prox.getDistance();  // read distance in cm

  // returns x-axis distance to the serial monitor
  // Serial.println("Mean distance: " + dis);

  // the following gives you the time taken to get the measurement
  unsigned long endTime = millis() - startTime;
  // Serial.println("Time taken ms): " + endTime);

  //receive event and turns motor on z-axis on or off
  if (manual) {
    if (command.equals("VOOR") && Read_z_encoder() < 18) {  //STUUR NAAR VOREN
      digitalWrite(directionZ, LOW);
      digitalWrite(brakeZ, LOW);
      analogWrite(pwmZ, 200);
    } else if (command.equals("ACHTER") && Read_z_encoder() > 7) {  //STUUR NAAR ACHTER
      digitalWrite(directionZ, HIGH);
      digitalWrite(brakeZ, LOW);
      analogWrite(pwmZ, 200);
    } else {
      digitalWrite(brakeZ, HIGH);  //ENGAGE BRAKES
      analogWrite(pwmZ, 0);
    }    
  }


  //count pulses read by the encoder - DEPRECATED
  //DC_Motor_Encoder_x();
  //DC_Motor_Encoder_y();
  // Serial.println(Count_pulses_x);
  // Serial.println(Count_pulses_y);


  //check limitswitchX
  limitSwitchX.loop();  // MUST call the loop() function first

  //recieve calibrating
  recieveCalibrating();

  //recieve mode
  recieveMode();

  //slide out
  if (command.equals("UITSCHUIVEN")) {
    slideOut();
  }

  // //Get state of limit switch on X-axis and do something
  int stateX = limitSwitchX.getState();
  if (stateX == HIGH) {
    if (sendXLim) {
      // Serial.println("The limit switch on X-Axis is: TOUCHED");
      sendXLim = false;
      Count_pulses_x = 0;
      message = "xLimY";
      requestEvent();
    }
  } else {
    if (!sendXLim) {
      // Serial.println("The limit switch on X-Axis is: UNTOUCHED");
      sendXLim = true;
      message = "xLimN";
      requestEvent();
    }
  }

  //Read Z-axis
  Read_z_encoder();

  //Send starting point
  if (calibrating) {
    sendStartingPoint();
  }

  //check limitswitchY
  limitSwitchY.loop();  // MUST call the loop() function first

  //Get state of limit switch on Y-axis and do something
  int stateY = limitSwitchY.getState();
  if (stateY == HIGH) {
    if (sendYLim) {
      // Serial.println("The limit switch on Y-Axis is: TOUCHED");
      sendYLim = false;
      Count_pulses_y = 0;
      message = "yLimY";
      requestEvent();
    }
  } else {
    if (!sendYLim) {
      // Serial.println("The limit switch on Y-Axis is: UNTOUCHED");
      sendYLim = true;
      message = "yLimN";
      requestEvent();
    }
  }
}