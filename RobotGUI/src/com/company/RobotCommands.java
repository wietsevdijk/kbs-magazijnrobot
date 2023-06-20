package com.company;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.mysql.cj.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class RobotCommands implements SerialPortDataListener {
    SerialPort sp;
    private String message = "";
    private String localMessage = "";
    private boolean buildUpMessage;
    private String receivedData;
    private int arduinoDelay = 100;

    public RobotCommands(){
        sp = SerialPort.getCommPort("COM4"); // selecteer je gebruikte COM port
        sp.setComPortParameters(9600, 8, 1, 0); //Set Serial baudrate
        sp.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 0, 0); //timeouts uitzetten

        sp.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                receivedData = new String(event.getReceivedData());

                if(receivedData.startsWith("-")){ //Check of bericht starting prefix heeft
                    message = ""; // Clear eerst globaal message attribuut
                    buildUpMessage = true; //Zo ja, sta bericht opbouwen toe
                    //System.out.println("MESSAGE MODE ENABLED");
                }

                if(buildUpMessage) {
                    //System.out.println("RECEIVED DATA");
                    localMessage += receivedData;
                }

                if(receivedData.endsWith(";")){
                    //System.out.println(message);
                    //op dit punt moet er iets gebeuren met de message
                    setMessage(parseReceivedMessage(localMessage)); //stel message in voor object
                    System.out.print("ARDUINO RESPONSE: \"" + message + "\" at ");
                    System.out.println(java.time.LocalTime.now());
                    localMessage = "";
                    //System.out.println("MESSAGE MODE DISABLED");
                    buildUpMessage = false;
                }
            }
        });

    }


    @Override
    public int getListeningEvents() {
        return 0;
    }

    @Override
    public void serialEvent(SerialPortEvent event) {

    }



    public SerialPort openSP() {
        sp.openPort();
        return sp;
    }

    public SerialPort getSp() {
        return sp;
    }

    public void sendLocation(SerialPort sp, String location) throws IOException {
            getSp().openPort();
            byte[] bytes;
            bytes = location.getBytes(StandardCharsets.UTF_8); //Zet string om naar bytes en stuurt naar arduino
            sp.getOutputStream().write(bytes);
            System.out.println("Sent location: " + location);

        //wachten voor reactie
        sleepWithDelay();

    }

    public void sendCommandMode(SerialPort sp, String mode) throws IOException {
        getSp().openPort();
            byte[] bytes;
            bytes = mode.getBytes(StandardCharsets.UTF_8); //Zet string om naar bytes en stuurt naar arduino
            sp.getOutputStream().write(bytes);
            System.out.println("Sent commandmode: " + mode);

        //wachten voor reactie
        sleepWithDelay();
    }

    //OUD, NIET MEER GEBRUIKT
    public void moveRobotUp(SerialPort sp) throws IOException {
        //Send data to arduino
        for (int i = 0; i < 1; ++i) {
            byte[] bytes;
            bytes = "UP".getBytes(StandardCharsets.UTF_8); //Zet string om naar bytes en stuurt naar arduino
            sp.getOutputStream().write(bytes);
        }
    }

    //OUD, NIET MEER GEBRUIKT
    public void moveRobotDown(SerialPort sp) throws IOException {

        for (int i = 0; i < 1; ++i) {
            byte[] bytes;
            bytes = "DOWN".getBytes(StandardCharsets.UTF_8); //Zet string om naar bytes en stuurt naar arduino
            sp.getOutputStream().write(bytes); // stuur hier je bytes
        }
    }

    //OUD, NIET MEER GEBRUIKT
    public void moveRobotLeft(SerialPort sp) throws IOException {
        for (int i = 0; i < 1; ++i) {
            byte[] bytes;
            bytes = "LEFT".getBytes(StandardCharsets.UTF_8); //Zet string om naar bytes en stuurt naar arduino
            sp.getOutputStream().write(bytes);
        }


    }

    public void moveRobotRight(SerialPort sp) throws IOException {

        for (int i = 0; i < 1; ++i) {
            byte[] bytes;
            bytes = "RIGHT".getBytes(StandardCharsets.UTF_8); //Zet string om naar bytes en stuurt naar arduino
            sp.getOutputStream().write(bytes);
        }
    }

    public SerialPort closeArduinoConnection(){
        SerialPort sp = SerialPort.getCommPort("COM5");
        if(sp.closePort()) { // sluit de connectie
            System.out.println("Failed to open port :(");
        }
        return sp;
    }

    public String parseReceivedMessage(String receivedMessage){
        receivedMessage = receivedMessage.replace("-", "");
        receivedMessage = receivedMessage.replace(";", "");
        return receivedMessage;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    //Functie om te wachten voor een bepaalde tijd,
    //nodig omdat de Arduino een delay heeft voordat hij een reactie teruggeeft
    //Het wachten zorgt ervoor dat het "message" attribuut altijd up-to-date is
    private void sleepWithDelay() {
        try {
            Thread.sleep(arduinoDelay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } //Afhandelen van de Exception in de methode zelf zorgt ervoor dat hij makkelijker overal aan te roepen is
    }

    //Wachten met handmatig meegegeven delay
    private void sleepWithDelay(int delayInMs) {
        try {
            Thread.sleep(delayInMs);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } //Afhandelen van de Exception in de methode zelf zorgt ervoor dat hij makkelijker overal aan te roepen is
    }


}
