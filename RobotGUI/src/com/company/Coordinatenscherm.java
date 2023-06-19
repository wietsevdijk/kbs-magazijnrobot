package com.company;

import com.fazecast.jSerialComm.SerialPort;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class Coordinatenscherm {
    public Coordinatenscherm(SerialPort serialPort, RobotCommands robotCommands, GridTekenPanel grid){
        RobotCommands rc = robotCommands;

        SerialPort sp = serialPort;

        JFrame frame = new JFrame("Stuur naar coordinaten");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setPreferredSize(new Dimension(400,225));
        frame.setLayout(new GridLayout(8, 2));

        JLabel coordlabel1X = new JLabel("Coordinaat 1: X");
        JTextField coordfield1X = new JTextField(30);
        JLabel coordlabel1Y = new JLabel("Coordinaat 1: Y");
        JTextField coordfield1Y = new JTextField(30);

        JLabel coordlabel2X = new JLabel("Coordinaat 2: X");
        JTextField coordfield2X = new JTextField(30);
        JLabel coordlabel2Y = new JLabel("Coordinaat 2: Y");
        JTextField coordfield2Y = new JTextField(30);

        JLabel coordlabel3X = new JLabel("Coordinaat 3: X");
        JTextField coordfield3X = new JTextField(30);
        JLabel coordlabel3Y = new JLabel("Coordinaat 3: Y");
        JTextField coordfield3Y = new JTextField(30);



        JButton cancel = new JButton("Annuleren");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                //sluit coordinatenscherm
            }
        });

        JButton submitCoords = new JButton("Stuur coordinaten");

        JButton tekenPad = new JButton("Teken pad (tijdelijk)");

        frame.add(coordlabel1X);
        frame.add(coordfield1X);
        frame.add(coordlabel1Y);
        frame.add(coordfield1Y);

        frame.add(coordlabel2X);
        frame.add(coordfield2X);
        frame.add(coordlabel2Y);
        frame.add(coordfield2Y);

        frame.add(coordlabel3X);
        frame.add(coordfield3X);
        frame.add(coordlabel3Y);
        frame.add(coordfield3Y);

        frame.add(submitCoords);
        frame.add(cancel);

        submitCoords.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean arrived = false;
                ArrayList<Coordinaat> lijst = new ArrayList<>();

                lijst.add(new Coordinaat(Integer.parseInt(coordfield1X.getText()), Integer.parseInt(coordfield1Y.getText())));
                lijst.add(new Coordinaat(Integer.parseInt(coordfield2X.getText()), Integer.parseInt(coordfield2Y.getText())));
                lijst.add(new Coordinaat(Integer.parseInt(coordfield3X.getText()), Integer.parseInt(coordfield3Y.getText())));
                TSPAlgoritme algoritme1 = new TSPAlgoritme(5, 5, lijst);
                lijst = algoritme1.getVolgorde();

                grid.setCoordinaten(lijst);
                grid.setTSPtest(true);

                grid.repaint();
                grid.revalidate();


                try {
                    rc.sendCommandMode(sp, "COORDS");
                    if(rc.getMessage().equals("ModeCoords")){

                        System.out.println("modus is goed");

                        for (Coordinaat product: lijst) {
                            arrived = false;
                            rc.sendLocation(sp, product.buildStringVolgorde());

                            while (!arrived){
                                //niet de meest elegante oplossing, test dit meer
                                Thread.sleep(1000);
                                if (rc.getMessage().equals("ARRIVED")){
                                    rc.setMessage("");
                                    arrived = true;
                                }
                            }
                        }
                    }


                    if(rc.getMessage().equals("")){
                        System.out.println("Geen reactie van Arduino, ga naar eindpunt");

                        rc.sendCommandMode(sp, "END");
                        rc.sendLocation(sp, "GO");
                    }

                    //rc.sendCommandMode(sp, "END");
                    //rc.sendLocation(sp, "GO");

                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

                System.out.println("KLAAR MET PAKKETJES");
            }
        });

        //laat alles zien
        frame.requestFocus();
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(true);
    }

    public int translatePixelToGrid(int pixelX, int pixelY){
        int locatie = -1;

        if(pixelX == 1 && pixelY == 1){
            locatie = 0;
        }

        if(pixelX == 2 && pixelY == 1){
            locatie = 1;
        }

        if(pixelX == 3 && pixelY == 1){
            locatie = 2;
        }

        if(pixelX == 4 && pixelY == 1){
            locatie = 3;
        }

        if(pixelX == 5 && pixelY == 1){
            locatie = 4;
        }

        if(pixelX == 1 && pixelY == 2){
            locatie = 5;
        }

        if(pixelX == 2 && pixelY == 2){
            locatie = 6;
        }

        if(pixelX == 3 && pixelY == 2){
            locatie = 7;
        }

        if(pixelX == 4 && pixelY == 2){
            locatie = 8;
        }

        if(pixelX == 5 && pixelY == 2){
            locatie = 9;
        }

        if(pixelX == 1 && pixelY == 3){
            locatie = 10;
        }

        if(pixelX == 2 && pixelY == 3){
            locatie = 11;
        }

        if(pixelX == 3 && pixelY == 3){
            locatie = 12;
        }

        if(pixelX == 4 && pixelY == 3){
            locatie = 13;
        }

        if(pixelX == 5 && pixelY == 3){
            locatie = 14;
        }

        if(pixelX == 1 && pixelY == 4){
            locatie = 15;
        }

        if(pixelX == 2 && pixelY == 4){
            locatie = 16;
        }

        if(pixelX == 3 && pixelY == 4){
            locatie = 17;
        }

        if(pixelX == 4 && pixelY == 4){
            locatie = 18;
        }

        if(pixelX == 5 && pixelY == 4){
            locatie = 19;
        }

        if(pixelX == 1 && pixelY == 5){
            locatie = 20;
        }

        if(pixelX == 2 && pixelY == 5){
            locatie = 21;
        }

        if(pixelX == 3 && pixelY == 5){
            locatie = 22;
        }

        if(pixelX == 4 && pixelY == 5){
            locatie = 23;
        }

        if(pixelX == 5 && pixelY == 5){
            locatie = 24;
        }

        return locatie;

    }
}
