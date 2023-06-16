#include <Wire.h>
#include <LiquidCrystal_I2C.h>       // LiquidCrystal_I2C library
LiquidCrystal_I2C lcd(0x27, 20, 4);  // 0x27 is the i2c address of the LCM1602 IIC v1 module (might differ)

#define LED 6
byte receivedChar;
bool coordMode = false;

void setup() {
  // put your setup code here, to run once:
  lcd.init();  // initialize the lcd
  lcd.backlight();
  lcd.clear();
  pinMode(LED, OUTPUT);
  Serial.begin(9600);
  Serial.setTimeout(100);

  while (!Serial) {
    ;  // wait for serial port to connect.
  }

  lcd.setCursor(0, 0);
  lcd.print("Init done");
  delay(1000);
  lcd.clear();
}

void loop() {
  // put your main code here, to run repeatedly:
  String HMIcommand = "";  //convert decimal bytes to String
  String response = "";
  if (Serial.available()) {
    // wait a bit for the entire message to arrive
    HMIcommand = String(Serial.readString());
  
  }

  if (HMIcommand == "COORDS") {

      if(coordMode == false){
      response = "modustrue;";
      coordMode = true;
      }

      if(coordMode == true){
        response = "modusfalse;";
        coordMode = false;
      }

    }

  if (HMIcommand == "DOWN") {
    response = "omlaag";
  }

  if (HMIcommand == "LEFT") {
    response = "links";
  }

  if (HMIcommand == "RIGHT") {
    response = "rechts";
  }

  lcd.print(HMIcommand);
  Serial.print(response);
}
