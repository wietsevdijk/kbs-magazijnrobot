package com.company;

import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class RobotCommands {
    public SerialPort sp;

    public RobotCommands(){
        sp = SerialPort.getCommPort("COM5"); // selecteer je gebruikte COM port
        sp.setComPortParameters(9600, 8, 1, 0); //Set Serial baudrate
        sp.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 0, 0); //timeouts uitzetten
    }

    public SerialPort getSp() {
        return sp;
    }

    public void moveRobotToLocation(SerialPort sp, String location) throws IOException {
        byte[] bytes;
        bytes = location.getBytes(StandardCharsets.UTF_8); //Zet string om naar bytes en stuurt naar arduino
        sp.getOutputStream().write(bytes);
        sp.getOutputStream().flush();
    }

    public void moveRobotUp(SerialPort sp) throws IOException {
        //Send data to arduino
        for (int i = 0; i < 1; ++i) {
            byte[] bytes;
            bytes = "UP".getBytes(StandardCharsets.UTF_8); //Zet string om naar bytes en stuurt naar arduino
            sp.getOutputStream().write(bytes);
            sp.getOutputStream().flush();
        }
    }

    public void moveRobotDown(SerialPort sp) throws IOException {

        for (int i = 0; i < 1; ++i) {
            byte[] bytes;
            bytes = "DOWN".getBytes(StandardCharsets.UTF_8); //Zet string om naar bytes en stuurt naar arduino
            sp.getOutputStream().write(bytes); // stuur hier je bytes
            sp.getOutputStream().flush();
        }
    }

    public void moveRobotLeft(SerialPort sp) throws IOException {
        for (int i = 0; i < 1; ++i) {
            byte[] bytes;
            bytes = "LEFT".getBytes(StandardCharsets.UTF_8); //Zet string om naar bytes en stuurt naar arduino
            sp.getOutputStream().write(bytes);
            sp.getOutputStream().flush();
        }


    }

    public void moveRobotRight(SerialPort sp) throws IOException {

        for (int i = 0; i < 1; ++i) {
            byte[] bytes;
            bytes = "RIGHT".getBytes(StandardCharsets.UTF_8); //Zet string om naar bytes en stuurt naar arduino
            sp.getOutputStream().write(bytes);
            sp.getOutputStream().flush();
        }
    }

    public SerialPort closeArduinoConnection(){
        SerialPort sp = SerialPort.getCommPort("COM7");
        if(sp.closePort()) { // sluit de connectie
            System.out.println("Failed to open port :(");
        }
        return sp;
    }
}
