#define VRX_PIN  A2 // Arduino pin connected to VRX pin
#define VRY_PIN  A3 // Arduino pin connected to VRY pin
int xValue = 0; // To store value of the X axis
int yValue = 0; // To store value of the Y axis

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);

  //Setup Channel A
  pinMode(12, OUTPUT); //Initiates Motor Channel A pin
  pinMode(9, OUTPUT); //Initiates Brake Channel A pin

  //Setup Channel B
  pinMode(13, OUTPUT); //Initiates Motor Channel B pin
  pinMode(8, OUTPUT);  //Initiates Brake Channel B pin
}

void loop() {
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

  Serial.print("x = ");
  Serial.print(xValue);
  Serial.print(", y = ");
  Serial.println(yValue);
  delay(200);

  switch(directie){
    case 'r':
      digitalWrite(12, HIGH); //Establishes forward direction of Channel A
      digitalWrite(9, LOW);   //Disengage the Brake for Channel A
      analogWrite(3, 255);   //Spins the motor on Channel A at full speed
      Serial.println("naar rechts");
    break;

    case 'l':
      digitalWrite(12, LOW); //Establishes backward direction of Channel A
      digitalWrite(9, LOW);   //Disengage the Brake for Channel A
      analogWrite(3, 255);   //Spins the motor on Channel A at full speed
      Serial.println("naar links");
    break;

    case 'u':
      digitalWrite(13, HIGH);  //Establishes forward direction of Channel B
      digitalWrite(8, LOW);   //Disengage the Brake for Channel B
      analogWrite(11, 123);    //Spins the motor on Channel B at half speed
      Serial.println("omhoog");
    break;

    case 'd':
      digitalWrite(13, LOW);  //Establishes backward direction of Channel B
      digitalWrite(8, LOW);   //Disengage the Brake for Channel B
      analogWrite(11, 123);    //Spins the motor on Channel B at half speed
      Serial.println("omlaag");
    break;

    case 's':
      digitalWrite(9, HIGH);  //Engage the Brake for Channel A
      digitalWrite(9, HIGH);  //Engage the Brake for Channel B
      Serial.println("niks");
    break;
    }
}
