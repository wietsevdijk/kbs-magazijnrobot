package com.company;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.mysql.cj.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class RobotCommands implements SerialPortDataListener {
    SerialPort sp;
    private String message = "";

    public RobotCommands(){
        sp = SerialPort.getCommPort("COM5"); // selecteer je gebruikte COM port
        sp.setComPortParameters(9600, 8, 1, 0); //Set Serial baudrate
        sp.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 0, 0); //timeouts uitzetten

        /*
        sp.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
                    return;
                byte[] newData = new byte[sp.bytesAvailable()];
                int numRead = sp.readBytes(newData, newData.length);
                System.out.println("Read " + numRead + " bytes.");
            }
        });
         */

        /*
        sp.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_WRITTEN;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                if (event.getEventType() == SerialPort.LISTENING_EVENT_DATA_WRITTEN)
                    System.out.println("All bytes were successfully transmitted!");
            }
        });
         */

        sp.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                message += new String(event.getReceivedData());

                if(message.endsWith(";")){
                    System.out.println(message);
                    message = "";
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




    public void handleResponse(String response) {
        System.out.println(response);
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
            System.out.println("Send location: " + location);

    }

    public void sendCommandMode(SerialPort sp) throws IOException {
        getSp().openPort();
            byte[] bytes;
            bytes = "COORDS".getBytes(StandardCharsets.UTF_8); //Zet string om naar bytes en stuurt naar arduino
            sp.getOutputStream().write(bytes);
    }

    public void moveRobotUp(SerialPort sp) throws IOException {
        //Send data to arduino
        for (int i = 0; i < 1; ++i) {
            byte[] bytes;
            bytes = "UP".getBytes(StandardCharsets.UTF_8); //Zet string om naar bytes en stuurt naar arduino
            sp.getOutputStream().write(bytes);
        }
    }

    public void moveRobotDown(SerialPort sp) throws IOException {

        for (int i = 0; i < 1; ++i) {
            byte[] bytes;
            bytes = "DOWN".getBytes(StandardCharsets.UTF_8); //Zet string om naar bytes en stuurt naar arduino
            sp.getOutputStream().write(bytes); // stuur hier je bytes
        }
    }

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
