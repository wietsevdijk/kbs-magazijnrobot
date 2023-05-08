package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import com.fazecast.jSerialComm.*;

import java.util.*;

public class Testscherm extends JFrame {
    Database db = new Database();
    SerialCommunication sc = new SerialCommunication();


    private int[] squareArray = new int[25];

    private Color DarkGreen = new Color(0, 205, 0);

    private JPanel links;
    private JPanel midden;
    private JPanel rechts;
    private JButton zendingLaden;


    public Testscherm() throws SQLException {

        addStartScherm("HMI Startscherm", 1000, 550);

    }

    public void addStartScherm(String titel, int breedte, int hoogte) {

        setTitle(titel);
        setSize(breedte, hoogte);

        setLayout(new GridLayout(1, 3)); //Zet de layout klaar voor 2 panels

        addPanels();
        addButton(zendingLaden, "Nieuwe zending laden");
        addOrderList();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    public void addPanels() {

        //Bereidt het linker paneel voor
        links = new JPanel();
        links.setBackground(Color.GREEN);
        //zet tekstlabels onder elkaar
        links.setLayout(new BoxLayout(links, BoxLayout.Y_AXIS));

        //gooit linker paneel erin
        add(links);


        //Bereidt het midden paneel voor
        midden = new JPanel();
        midden.setLayout(new FlowLayout());

        //Maakt de dropdowns en gooit het midden erin

        add(midden);

        //Initieert de layout voor het rechterpanel
        GridLayout grLay = new GridLayout(5, 5);


        //Bereidt het rechterpanel voor
        rechts = new JPanel();
        rechts.setBackground(Color.GRAY);

        //Zet de layout en tussenlijntjes (Voorgeinitieerde layout voor nodig!)
        rechts.setLayout(grLay);
        grLay.setHgap(5);
        grLay.setVgap(5);

        //Maakt de grid en gooit het rechterpanel erin
        rechts.setBorder(BorderFactory.createLineBorder(Color.gray, 5));
        addGridPanels(false);
        add(rechts);

    }

    public void addOrderList() {
        String[] orders = db.getAllOrders();
        for (int x = 0; x < orders.length; x++) {
            JLabel orderNaam = new JLabel("Order: " + orders[x]);
            links.add(orderNaam);
        }
        sc.initializeSerialPort();
        sc.send("5");
    }

    public void addButton(JButton naam, String text) {
        naam = new JButton(text);
        naam.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addGridPanels(true);
            }
        });
        midden.add(naam);
    }

    public void addDropdowns() {

    }

    public void loadSelectedOrders() {


    }


    public void addGridPanels(boolean buttonPressed) {
        for (int i = 0; i < squareArray.length; i++) {

            squareArray[i] = i;
            Panel panel = new Panel();
            JLabel naam = new JLabel("" + (i + 1));

            if (!buttonPressed) {
                Integer[] magazijnLocatie = db.getAllProductLocations().keySet().toArray(new Integer[0]);
                String[] productID = db.getAllProductLocations().values().toArray(new String[0]);
                int labelInt = i;

                if (i % 2 == 0) {
                    panel.setBackground(Color.green);
                } else {
                    panel.setBackground(DarkGreen);
                }

                if (labelInt == 0 && magazijnLocatie[0] == 11 && productID[0] != null) {
                    naam.setText(db.getProductName(productID[0]));
                }

                if (labelInt == 1 && magazijnLocatie[1] == 12 && productID[1] != null) {
                    naam.setText(db.getProductName(productID[1]));
                }

                if (labelInt == 2 && magazijnLocatie[2] == 13 && productID[2] != null) {
                    naam.setText(db.getProductName(productID[2]));
                }

                if (labelInt == 3 && magazijnLocatie[3] == 14 && productID[3] != null) {
                    naam.setText(db.getProductName(productID[3]));
                }

                if (labelInt == 5 && magazijnLocatie[4] == 15 && productID[4] != null) {
                    naam.setText(db.getProductName(productID[4]));
                }

                if (labelInt == 6 && magazijnLocatie[5] == 21 && productID[5] != null) {
                    naam.setText(db.getProductName(productID[5]));
                }

                if (labelInt == 7 && magazijnLocatie[6] == 22 && productID[6] != null) {
                    naam.setText(db.getProductName(productID[6]));
                }

                if (labelInt == 8 && magazijnLocatie[7] == 23 && productID[7] != null) {
                    naam.setText(db.getProductName(productID[7]));
                }

                if (labelInt == 9 && magazijnLocatie[8] == 24 && productID[8] != null) {
                    naam.setText(db.getProductName(productID[8]));
                }

                if (labelInt == 10 && magazijnLocatie[9] == 25 && productID[9] != null) {
                    naam.setText(db.getProductName(productID[9]));
                }

                if (labelInt == 11 && magazijnLocatie[10] == 31 && productID[10] != null) {
                    naam.setText(db.getProductName(productID[10]));
                }

                if (labelInt == 12 && magazijnLocatie[11] == 32 && productID[11] != null) {
                    naam.setText(db.getProductName(productID[11]));
                }

                if (labelInt == 13 && magazijnLocatie[12] == 33 && productID[12] != null) {
                    naam.setText(db.getProductName(productID[12]));
                }

                if (labelInt == 14 && magazijnLocatie[13] == 34 && productID[13] != null) {
                    naam.setText(db.getProductName(productID[13]));
                }

                if (labelInt == 15 && magazijnLocatie[14] == 35 && productID[14] != null) {
                    naam.setText(db.getProductName(productID[14]));
                }

                if (labelInt == 16 && magazijnLocatie[15] == 41 && productID[15] != null) {
                    naam.setText(db.getProductName(productID[15]));
                }

                if (labelInt == 17 && magazijnLocatie[16] == 42 && productID[16] != null) {
                    naam.setText(db.getProductName(productID[16]));
                }

                if (labelInt == 18 && magazijnLocatie[17] == 43 && productID[17] != null) {
                    naam.setText(db.getProductName(productID[17]));
                }

                if (labelInt == 19 && magazijnLocatie[18] == 44 && productID[18] != null) {
                    naam.setText(db.getProductName(productID[18]));
                }

                if (labelInt == 20 && magazijnLocatie[19] == 45 && productID[19] != null) {
                    naam.setText(db.getProductName(productID[19]));
                }
                if (labelInt == 21 && magazijnLocatie[20] == 51 && productID[20] != null) {
                    naam.setText(db.getProductName(productID[20]));
                }

                if (labelInt == 22 && magazijnLocatie[21] == 52 && productID[21] != null) {
                    naam.setText(db.getProductName(productID[21]));
                }

                if (labelInt == 23 && magazijnLocatie[22] == 53 && productID[22] != null) {
                    naam.setText(db.getProductName(productID[22]));
                }

                if (labelInt == 24 && magazijnLocatie[23] == 54 && productID[23] != null) {
                    naam.setText(db.getProductName(productID[23]));
                }
                if (labelInt == 25 && magazijnLocatie[24] == 55 && productID[24] != null) {
                    naam.setText(db.getProductName(productID[24]));
                }


                rechts.add(panel);
                panel.add(naam);
            } else {
                Integer[] magazijnLocatie = db.getCurrentOrderProductLocations().keySet().toArray(new Integer[0]);

                if (i == 0 && magazijnLocatie[0] == 11 || magazijnLocatie[1] == 11 || magazijnLocatie[2] == 11) {
                    panel.setBackground(Color.BLUE);
                    System.out.println("repaint");
                    repaint();
                }

                if (i == 1 && magazijnLocatie[1] == 12 || magazijnLocatie[1] == 12 || magazijnLocatie[2] == 12) {
                    panel.setBackground(Color.BLUE);
                    repaint();
                    System.out.println("repaint");
                }

                if (i == 2 && magazijnLocatie[0] == 13 || magazijnLocatie[1] == 13 || magazijnLocatie[2] == 13) {
                    panel.setBackground(Color.BLUE);
                    repaint();
                    System.out.println("repaint");
                }

                if (i == 3 && magazijnLocatie[0] == 14 || magazijnLocatie[1] == 14 || magazijnLocatie[2] == 14) {
                    panel.setBackground(Color.BLUE);
                }

                if (i == 4 && magazijnLocatie[0] == 15 || magazijnLocatie[1] == 15 || magazijnLocatie[2] == 15) {
                    panel.setBackground(Color.BLUE);
                }

                if (i == 5 && magazijnLocatie[0] == 21 || magazijnLocatie[1] == 21 || magazijnLocatie[2] == 21) {
                    panel.setBackground(Color.BLUE);
                }

                if (i == 6 && magazijnLocatie[0] == 22 || magazijnLocatie[1] == 22 || magazijnLocatie[2] == 22) {
                    panel.setBackground(Color.BLUE);
                }

                if (i == 7 && magazijnLocatie[0] == 23 || magazijnLocatie[1] == 23 || magazijnLocatie[2] == 23) {
                    panel.setBackground(Color.BLUE);
                }

                if (i == 8 && magazijnLocatie[0] == 24 || magazijnLocatie[1] == 24 || magazijnLocatie[2] == 24) {
                    panel.setBackground(Color.BLUE);
                }
                if (i == 9 && magazijnLocatie[0] == 25 || magazijnLocatie[1] == 25 || magazijnLocatie[2] == 25) {
                    panel.setBackground(Color.BLUE);
                }

                if (i == 10 && magazijnLocatie[0] == 31 || magazijnLocatie[1] == 31 || magazijnLocatie[2] == 31) {
                    panel.setBackground(Color.BLUE);
                }

                if (i == 11 && magazijnLocatie[0] == 32 || magazijnLocatie[1] == 32 || magazijnLocatie[2] == 32) {
                    panel.setBackground(Color.BLUE);
                }

                if (i == 12 && magazijnLocatie[0] == 33 || magazijnLocatie[1] == 33 || magazijnLocatie[2] == 33) {
                    panel.setBackground(Color.BLUE);
                }

                if (i == 13 && magazijnLocatie[0] == 34 || magazijnLocatie[1] == 34 || magazijnLocatie[2] == 34) {
                    panel.setBackground(Color.BLUE);
                }

                if (i == 14 && magazijnLocatie[0] == 35 || magazijnLocatie[1] == 35 || magazijnLocatie[2] == 35) {
                    panel.setBackground(Color.BLUE);
                }

                if (i == 15 && magazijnLocatie[0] == 41 || magazijnLocatie[1] == 41 || magazijnLocatie[2] == 41) {
                    panel.setBackground(Color.BLUE);
                }

                if (i == 16 && magazijnLocatie[0] == 42 || magazijnLocatie[1] == 42 || magazijnLocatie[2] == 42) {
                    panel.setBackground(Color.BLUE);
                }

                if (i == 17 && magazijnLocatie[0] == 43 || magazijnLocatie[1] == 43 || magazijnLocatie[2] == 43) {
                    panel.setBackground(Color.BLUE);
                }

                if (i == 18 && magazijnLocatie[0] == 44 || magazijnLocatie[1] == 44 || magazijnLocatie[2] == 44) {
                    panel.setBackground(Color.BLUE);
                }

                if (i == 19 && magazijnLocatie[0] == 45 || magazijnLocatie[1] == 45 || magazijnLocatie[2] == 45) {
                    panel.setBackground(Color.BLUE);
                }

                if (i == 20 && magazijnLocatie[0] == 51 || magazijnLocatie[1] == 51 || magazijnLocatie[2] == 51) {
                    panel.setBackground(Color.BLUE);
                }

                if (i == 21 && magazijnLocatie[0] == 52 || magazijnLocatie[1] == 52 || magazijnLocatie[2] == 52) {
                    panel.setBackground(Color.BLUE);
                }

                if (i == 22 && magazijnLocatie[0] == 53 || magazijnLocatie[1] == 53 || magazijnLocatie[2] == 53) {
                    panel.setBackground(Color.BLUE);
                }

                if (i == 23 && magazijnLocatie[0] == 54 || magazijnLocatie[1] == 54 || magazijnLocatie[2] == 54) {
                    panel.setBackground(Color.BLUE);
                }

                if (i == 24 && magazijnLocatie[0] == 55 || magazijnLocatie[1] == 55 || magazijnLocatie[2] == 55) {
                    panel.setBackground(Color.BLUE);
                }
            }
            repaint();
        }
    }

}
