package com.company;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.io.IOException;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws IOException, InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        // write your code here
        //Hoofdscherm hoofdscherm = new Hoofdscherm();
        Testscherm ts = new Testscherm();


                ts.addKeyListener(new KeyListener(){
            //Controleert of de arrow keys ingedrukt zijn
            @Override
            public void keyPressed(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.VK_UP) {
                    if (ts.sp.openPort()) { // opent de port
                        try {
                            ts.moveRobotUp(ts.sp); // stuurt commando over port
                            Scanner data = new Scanner(ts.sp.getInputStream()); // Krijgt iets terug van de serial
                            while (data.hasNextLine()) {
                                //print vanuit de arduino serial MAAR loopt één commando achter EN werkt heel langzaam
                                System.out.println(data.nextLine());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (event.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (ts.sp.openPort()) { // opent de port
                        try {
                            ts.moveRobotDown(ts.sp); // stuurt commando over port
                            Scanner data = new Scanner(ts.sp.getInputStream());
                            while (data.hasNextLine()) {
                                System.out.println(data.nextLine());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (event.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (ts.sp.openPort()) { // opent de port
                        try {
                            ts.moveRobotLeft(ts.sp); // stuurt commando over port
                            Scanner data = new Scanner(ts.sp.getInputStream());
                            while (data.hasNextLine()) {
                                System.out.println(data.nextLine());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (ts.sp.openPort()) { // opent de port
                        try {
                            ts.moveRobotRight(ts.sp); // stuurt commando over port
                            Scanner data = new Scanner(ts.sp.getInputStream());
                            while (data.hasNextLine()) {
                                System.out.println(data.nextLine());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            }
            }

            //NIET VERWIJDEREN!!! deze moet erin anders krijg je een error
            public void keyReleased(KeyEvent e) {

            }

            //NIET VERWIJDEREN!!! deze moet erin anders krijg je een error
            public void keyTyped(KeyEvent e) {

            }
        });
    }
}



