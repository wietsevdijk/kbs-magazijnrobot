package com.company;

import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.awt.event.*;
import java.sql.*;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws IOException, InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        // write your code here
        //Hoofdscherm hoofdscherm = new Hoofdscherm();
        Hoofdscherm hs = new Hoofdscherm();
        RobotCommands rc = new RobotCommands();
        SerialPort sp = rc.getSp();
        hs.setFocusable(true);
        hs.requestFocus();


        hs.addKeyListener(new KeyListener(){
            //Controleert of de arrow keys ingedrukt zijn
            @Override
            public void keyPressed(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.VK_UP) {
                    System.out.println("UP");
                    if (sp.openPort()) { // opent de port
                        try {
                            rc.moveRobotUp(rc.sp); // stuurt commando over port
                            Scanner data = new Scanner(rc.sp.getInputStream()); // Krijgt iets terug van de serial
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
                    System.out.println("DOWN");
                    if (sp.openPort()) { // opent de port
                        try {
                            rc.moveRobotDown(rc.sp); // stuurt commando over port
                            Scanner data = new Scanner(rc.sp.getInputStream());
                            while (data.hasNextLine()) {
                                System.out.println(data.nextLine());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (event.getKeyCode() == KeyEvent.VK_LEFT) {
                    System.out.println("LEFT");
                    if (sp.openPort()) { // opent de port
                        try {
                            rc.moveRobotLeft(rc.sp); // stuurt commando over port
                            Scanner data = new Scanner(rc.sp.getInputStream());
                            while (data.hasNextLine()) {
                                System.out.println(data.nextLine());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
                    System.out.println("RIGHT");
                    if (sp.openPort()) { // opent de port
                        try {
                            rc.moveRobotRight(rc.sp); // stuurt commando over port
                            Scanner data = new Scanner(rc.sp.getInputStream());
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
            public void keyReleased(KeyEvent e) {}

            //NIET VERWIJDEREN!!! deze moet erin anders krijg je een error
            public void keyTyped(KeyEvent e) {}
        });
    }
}



