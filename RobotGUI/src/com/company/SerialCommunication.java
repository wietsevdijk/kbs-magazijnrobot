package com.company;
import com.fazecast.jSerialComm.*;

import java.io.PrintWriter;
import java.util.Scanner;

public class SerialCommunication {
    // used for serial communication:
    String readline;
    SerialPort comPort;
    String commPort = "COM5";
    int baudrate = 115200;

    void initializeSerialPort() {

        comPort = SerialPort.getCommPort(commPort);
        comPort.openPort();
        comPort.setBaudRate(baudrate);
    }

    public void receive(String line) {
        if (line!=null) System.out.println("Received: "+line);
    }


    public void send(String s) {
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        try{Thread.sleep(5);} catch(Exception e){}
        PrintWriter pout = new PrintWriter(comPort.getOutputStream());
        pout.print(s);
        pout.flush();
    }
}


