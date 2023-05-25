package com.testfiles.versie3;
import com.testfiles.versie1.Testscherm;
import com.testfiles.versie2.Testscherm2;
import com.testfiles.versie3.Testscherm3;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws SQLException {

        // write your code here
        Testscherm schermpje = new Testscherm();
        Testscherm2 testscherm = new Testscherm2();
        Testscherm3 ts = new Testscherm3();

        //Communicatie met arduino
        SerialPort sp = SerialPort.getCommPort("COM7"); // selecteer je gebruikte COM port
        sp.setComPortParameters(9600, 8, 1, 0); //Set Serial baudrate
        sp.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 0, 0); //timeouts uitzetten
        boolean hasOpened = sp.openPort();

        ts.addKeyListener(new KeyListener(){
            //Controleert of de arrow keys ingedrukt zijn
            @Override
            public void keyPressed(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.VK_UP) {
                    if (hasOpened) { // opent de port
                        try {
                            ts.moveRobotUp(sp); // stuurt commando over port
                            Scanner data = new Scanner(sp.getInputStream()); // Krijgt iets terug van de serial
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
                    if (sp.openPort()) { // opent de port
                        try {
                            ts.moveRobotDown(sp); // stuurt commando over port
                            Scanner data = new Scanner(sp.getInputStream());
                            while (data.hasNextLine()) {
                                System.out.println(data.nextLine());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (event.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (sp.openPort()) { // opent de port
                        try {
                            ts.moveRobotLeft(sp); // stuurt commando over port
                            Scanner data = new Scanner(sp.getInputStream());
                            while (data.hasNextLine()) {
                                System.out.println(data.nextLine());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (sp.openPort()) { // opent de port
                        try {
                            ts.moveRobotRight(sp); // stuurt commando over port
                            Scanner data = new Scanner(sp.getInputStream());
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