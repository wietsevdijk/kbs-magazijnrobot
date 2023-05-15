package com.company;
import com.fazecast.jSerialComm.SerialPort;
import java.io.IOException;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws IOException, InterruptedException, SQLException {

        // write your code here
        //Hoofdscherm hoofdscherm = new Hoofdscherm();
        Testscherm ts = new Testscherm();

        //Communicatie met arduino
        SerialPort sp = SerialPort.getCommPort("COM5"); // selecteer je gebruikte COM port
        sp.setComPortParameters(9600, 8, 1, 0); //Set Serial baudrate
        sp.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 0, 0); //timeouts uitzetten

        ts.addKeyListener(new KeyListener(){
            //Controleert of de arrow keys ingedrukt zijn
            @Override
            public void keyPressed(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.VK_UP) {
                    if (sp.openPort()) { // opent de port
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



