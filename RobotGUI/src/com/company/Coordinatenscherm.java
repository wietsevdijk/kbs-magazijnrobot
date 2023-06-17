package com.company;

import com.company.RobotCommands;
import com.fazecast.jSerialComm.SerialPort;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Coordinatenscherm {
    public Coordinatenscherm(){
        RobotCommands rc = new RobotCommands();

        ArrayList<Coordinaat> lijst = new ArrayList<>();
        //TSPAlgoritme algoritme2 = new TSPAlgoritme(5, 5, 3);
        //System.out.println(algoritme2);


        SerialPort sp = rc.getSp();

        JFrame frame = new JFrame("Stuur naar coordinaten");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setPreferredSize(new Dimension(400,225));
        frame.setLayout(new GridLayout(7, 2));

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



        JButton cancel = new JButton("cancel");
        JButton submitCoords = new JButton("Stuur coordinaten");

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
                int nummer = 1;
                boolean arrived = false;
                lijst.add(new Coordinaat(Integer.parseInt(coordfield1X.getText()), Integer.parseInt(coordfield1Y.getText())));
                lijst.add(new Coordinaat(Integer.parseInt(coordfield2X.getText()), Integer.parseInt(coordfield2Y.getText())));
                lijst.add(new Coordinaat(Integer.parseInt(coordfield3X.getText()), Integer.parseInt(coordfield3Y.getText())));
                TSPAlgoritme algoritme1 = new TSPAlgoritme(5, 5, lijst);

                try {
                    rc.sendCommandMode(sp, "COORDS");
                    if(rc.getMessage().equals("Command mode enabled")){

                        System.out.println("modus is goed");

                        for (Coordinaat product: lijst) {
                            arrived = false;
                            rc.sendLocation(sp, product.buildStringVolgorde());
                            while (!arrived){
                                if (Objects.equals(rc.getMessage(), "ARRIVED")){
                                    arrived = true;
                                }
                            }
                        }
                        //rc.setMessage("");
                    }

                    if(rc.getMessage().equals("in Case Coordinaten")){;
                        System.out.println("toppie");
                        //rc.setMessage("");
                    }

                    if(rc.getMessage().equals("")){
                        System.out.println("Geen reactie van Arduino");
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        //laat alles zien
        frame.requestFocus();
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(true);
    }
}
