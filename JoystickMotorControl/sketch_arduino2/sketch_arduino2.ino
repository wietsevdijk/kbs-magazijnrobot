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
String isCalibrating;

//Encoder locations for all coordinates
int x_position_old [6] = {0, 30, 730, 1431, 2145, 2841}; //oude posities voordat brake code was gefixt, niet gebruiken

int x_position [6] = {0, 80, 770, 1470, 2170, 2870};
int y_position [6] = {0, 2251, 1737, 1233, 715, 185};

//Coordinates to find when homing
int findX = 1;
int findY = 5;


//byte for communication between arduino's
byte x;

//String to store received event command
String command = "";

//Int to store pulses from encoder
volatile int Count_pulses_x = 0;
volatile int Count_pulses_y = 0;
volatile int distanceZ;

int dirX;
int dirY;

//To send a change only once
bool sendXLim = true;
bool sendYLim = true;

bool sendXStart = false;
bool sendYStart = false;

bool foundXPos = false;
bool foundYPos = false;

//If calibrating or not
bool calibrating = false;
bool calibratingComplete = false;

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


float Read_z_encoder() {
  float z_value = analogRead(Encoder_output_z) * (5.0 / 1023.0);
  float z_axis = (13 * (1 / z_value)) * 0.975;
  return z_axis;
}

void sendStartingPoint() {
    //Serial.println("FINDING START POINT");
    if (Count_pulses_x > x_position[findX] && !sendXStart) {
      sendXStart = true;
      message = "StrtX";
      requestEvent();
    } else if (Count_pulses_x <= x_position[findX] && sendXStart) {
      sendXStart = false;
      message = "StrtN";
      requestEvent();
    }
    if (Count_pulses_y > y_position[findY] && !sendYStart) {
      sendYStart = true;
      message = "StrtY";
      requestEvent();
    } else if (Count_pulses_y <= y_position[findY] && sendYStart) {
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
    //KALIBRATIE COMPLEET
    calibratingComplete = true;
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

//functions to send X and Y movement commands to master
void moveLeft() {
  message = "xMoveL";
  requestEvent();
}

void moveRight() {
  message = "xMoveR";
  requestEvent();
}

void moveUp(){
  message = "yMoveU";
  requestEvent();
}

void moveDown(){
  message = "yMoveD";
  requestEvent();
}

void stopMoving(){
  message = "dontMv";
  requestEvent();
}

//Signal to the Master that the robot has arrived at coordinates
void sendArrived() {
  message = "CoordF";
  requestEvent();
}

//Clear the request message & request a new event
void clearRequestMessage() {
  message = "";
  requestEvent();
}


void loop() {
  //DEBUG
  if(debug){
    //print elke 500ms debug print
    currentDebugTime = millis();
    if(currentDebugTime - previousDebugTime > 500){
      previousDebugTime = currentDebugTime;

      Serial.print("X: ");
      Serial.println(Count_pulses_x);
      Serial.print("Y: ");
      Serial.println(Count_pulses_y);
      Serial.print("Z: ");
      Serial.println(distanceZ);

      isCalibrating = calibrating ? "IS CALIBRATING" : "NOT CALIBRATING";
      Serial.println(isCalibrating);

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

  //Read Z-axis
  distanceZ = Read_z_encoder();

  //receive event and turns motor on z-axis on or off
  if (manual) {
    if (command.equals("VOOR") && distanceZ < 18) {  //STUUR NAAR VOREN
      digitalWrite(directionZ, LOW);
      digitalWrite(brakeZ, LOW);
      analogWrite(pwmZ, 200);
    } else if (command.equals("ACHTER") && distanceZ > 6) {  //STUUR NAAR ACHTER
      digitalWrite(directionZ, HIGH);
      digitalWrite(brakeZ, LOW);
      analogWrite(pwmZ, 200);
    } else {
      digitalWrite(brakeZ, HIGH);  //ENGAGE BRAKES
      analogWrite(pwmZ, 0);
    }    
  }

  //check limitswitchX
  limitSwitchX.loop();  // MUST call the loop() function first

  //recieve calibrating
  if (!calibratingComplete){
    recieveCalibrating();
  }

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

  //Coordinaat ontvangen vanaf Master
  if(command.startsWith("GOTO")){
    //Reset values
    foundXPos = false;
    foundYPos = false;
    message = "";

    Serial.println("-----");
    Serial.println(command);

    //Remove "GOTO" from String, left with coordinate
    command.remove(0, 4);
    Serial.println(command);

    //voorbeeld: input is 4.3 (X4, Y3)
    //Haal coordinaten op uit String
    int X = command.substring(0, 1).toInt();
    int Y = command.substring(2, 3).toInt();

    Serial.println("-----");

    Serial.print("X target: ");
    Serial.print(X);
    Serial.print(" - ");
    Serial.println(x_position[X]);

    Serial.print("Y target: ");
    Serial.print(Y);
    Serial.print(" - ");
    Serial.println(y_position[Y]);

    Serial.println("-----");

    goToX(X);
    goToY(Y);

    if(foundXPos && foundYPos){
      sendArrived();
    }

  }

}

//Send robot to X coordinate
void goToX(int X){

  //first check if robot is already at correct point on axis to prevent unnecessary movement
    if ((x_position[X] -30) < Count_pulses_x  && Count_pulses_x <= (x_position[X] + 30)){
      stopMoving();
      foundXPos = true;
      Serial.println("----- ALREADY AT X -----");
    }

    //if robot is not already at correct point, start moving until it is found
    while(!foundXPos){

      if (Count_pulses_x < (x_position[X])) {
        moveRight();
      } else if (Count_pulses_x >= (x_position[X])) {
        moveLeft();
      } 

      if ((x_position[X] -1) < Count_pulses_x  && Count_pulses_x <= (x_position[X] + 1)) {
        stopMoving();
        foundXPos = true;
        Serial.println("----- FOUND X -----");
        Serial.print("TARGET:");
        Serial.println(x_position[X]);
        Serial.print("CURRENT POS:");
        Serial.println(Count_pulses_x);
        Serial.println("-----");
        delay(10); //ik weet het, delays zijn slecht, sorry ~Wietse
      }
    }

}

//Send robot to Y coordinate
void goToY(int Y){
  
  //first check if robot is already at correct point on axis to prevent unnecessary movement
  if ((y_position[Y] -30) < Count_pulses_y  && Count_pulses_y <= (y_position[Y] + 30)){
    stopMoving();
    foundYPos = true;
    Serial.println("----- ALREADY AT Y -----");
  }


    //if robot is not already at correct point, start moving until it is found
    while(!foundYPos){

      if (Count_pulses_y < (y_position[Y])) {
        moveUp();
      } else if (Count_pulses_y >= (y_position[Y])) {
        moveDown();
      } 

      if ((y_position[Y] -1) < Count_pulses_y  && Count_pulses_y <= (y_position[Y] + 1)) {
        stopMoving();
        foundYPos = true;
        Serial.println("----- FOUND Y -----");
        Serial.print("TARGET:");
        Serial.println(y_position[Y]);
        Serial.print("CURRENT POS:");
        Serial.println(Count_pulses_y);
        Serial.println("-----");
        delay(10); //ik weet het, delays zijn slecht, sorry ~Wietse
      }
    }

}