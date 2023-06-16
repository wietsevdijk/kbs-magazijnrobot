package com.company;

import com.fazecast.jSerialComm.SerialPort;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

//Werkt niet meer met nieuwe grid. nog verwerken naar nieuwe grid dus.

public class Artikelscherm {
    RobotCommands rc = new RobotCommands();
    Database db = new Database();
    SerialPort sp = rc.getSp();
    //See where the mouse clicks on the grid and send location to robot

    public Artikelscherm() throws SQLException {
    }

    public void openArtikelScherm(String productlocatie){
        String[] artikelData = db.getProductData(productlocatie);

        JFrame frame = new JFrame("Locatie: " + productlocatie);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300,300);
        frame.setLayout(new GridLayout(5,2));

        //maak labels
        JLabel productLocatie = new JLabel("Productlocatie: " + productlocatie);
        JLabel productNaam = new JLabel("Product: " + artikelData[0]);
        JLabel productPrijs = new JLabel("Prijs: " + artikelData[1] + ",-");
        JLabel productAantal = new JLabel("Voorraad: " + artikelData[2]);

        //add labels
        frame.add(productLocatie);
        frame.add(productNaam);
        frame.add(productPrijs);
        frame.add(productAantal);

        //maak buttons
        JButton goToLocation = new JButton("Stuur robot naar " + productlocatie);
        goToLocation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                try {
                   rc.moveRobotToLocation(sp,productlocatie);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                */
            }
        });

        //add buttons
        frame.add(goToLocation);

        //laat alles zien
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(true);
    }

    public void openArtikel(int xLocatie, int yLocatie){
        if(xLocatie < 65 && yLocatie < 65){
                System.out.println("A1");
                openArtikelScherm("A1");
        }

        if(xLocatie > 65 && xLocatie < 130 && yLocatie < 65){
            System.out.println("A2");
            openArtikelScherm("A2");
        }

        if(xLocatie > 130 && xLocatie < 195 && yLocatie < 65){
            System.out.println("A3");
            openArtikelScherm("A3");
        }

        if(xLocatie > 195 && xLocatie < 260 && yLocatie < 65){
            System.out.println("A4");
            openArtikelScherm("A4");
        }

        if(xLocatie > 260 && xLocatie < 325 && yLocatie < 65){
            System.out.println("A5");
            openArtikelScherm("A5");
        }

        if(xLocatie < 65 && yLocatie > 65 && yLocatie < 130){
            System.out.println("B1");
            openArtikelScherm("B1");
        }

        if(xLocatie > 65 && xLocatie < 130 && yLocatie > 65 && yLocatie < 130){
            System.out.println("B2");
            openArtikelScherm("B2");
        }

        if(xLocatie > 130 && xLocatie < 195 && yLocatie > 65 && yLocatie < 130){
            System.out.println("B3");
            openArtikelScherm("B3");
        }

        if(xLocatie > 195 && xLocatie < 260 && yLocatie > 65 && yLocatie < 130){
            System.out.println("B4");
            openArtikelScherm("B4");
        }

        if(xLocatie > 260 && xLocatie < 325 && yLocatie > 65 && yLocatie < 130){
            System.out.println("B5");
            openArtikelScherm("B5");
        }

        if(xLocatie < 65 && yLocatie > 130 && yLocatie < 195){
            System.out.println("C1");
            openArtikelScherm("C1");
        }

        if(xLocatie > 65 && xLocatie < 130 && yLocatie > 130 && yLocatie < 195){
            System.out.println("C2");
            openArtikelScherm("C2");
        }

        if(xLocatie > 130 && xLocatie < 195 && yLocatie > 130 && yLocatie < 195){
            System.out.println("C3");
            openArtikelScherm("C3");
        }

        if(xLocatie > 195 && xLocatie < 260 && yLocatie > 130 && yLocatie < 195){
            System.out.println("C4");
            openArtikelScherm("C4");
        }

        if(xLocatie > 260 && xLocatie < 325 && yLocatie > 130 && yLocatie < 195){
            System.out.println("C5");
            openArtikelScherm("C5");
        }

        if(xLocatie < 65 && yLocatie > 195 && yLocatie < 260){
            System.out.println("D1");
            openArtikelScherm("D1");
        }

        if(xLocatie > 65 && xLocatie < 130 && yLocatie > 195 && yLocatie < 260){
            System.out.println("D2");
            openArtikelScherm("D2");
        }

        if(xLocatie > 130 && xLocatie < 195 && yLocatie > 195 && yLocatie < 260) {
            System.out.println("D3");
            openArtikelScherm("D3");
        }

        if(xLocatie > 195 && xLocatie < 260 && yLocatie > 195 && yLocatie < 260){
            System.out.println("D4");
            openArtikelScherm("D4");
        }

        if(xLocatie > 260 && xLocatie < 325 && yLocatie > 195 && yLocatie < 260) {
            System.out.println("D5");
            openArtikelScherm("D5");
        }

        if(xLocatie < 65 && yLocatie > 260 && yLocatie < 325){
            System.out.println("E1");
            openArtikelScherm("E1");
        }

        if(xLocatie > 65 && xLocatie < 130 && yLocatie > 260 && yLocatie < 325){
            System.out.println("E2");
            openArtikelScherm("E2");
        }

        if(xLocatie > 130 &&xLocatie < 195 && yLocatie > 260 && yLocatie < 325){
            System.out.println("E3");
            openArtikelScherm("E3");
        }

        if(xLocatie > 195 && xLocatie < 260 && yLocatie > 260 && yLocatie < 325){
            System.out.println("E4");
            openArtikelScherm("E4");
        }

        if(xLocatie > 260 && xLocatie < 325 && yLocatie > 260 && yLocatie < 325){
            System.out.println("E5");
            openArtikelScherm("E5");
        }

    }
}
