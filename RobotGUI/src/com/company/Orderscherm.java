package com.company;

import javax.swing.*;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;


public class Orderscherm extends JFrame {
    Database db = new Database();

    public Orderscherm(String order) throws SQLException {
        //Setup frame settings
        JFrame frame = new JFrame("Order: " + order);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300,300);
        frame.setLayout(new GridLayout(1, 3));

        //maak panels aan
        JPanel panelklantgegevens = new JPanel();
        JPanel panelordergegevens = new JPanel();
        JPanel panelartikelgegevens = new JPanel();
        panelklantgegevens.setLayout(new GridLayout(6,1));
        panelklantgegevens.setBorder(new EmptyBorder(10,10,10,10));
        panelordergegevens.setLayout(new GridLayout(6,1));
        panelordergegevens.setBorder(new EmptyBorder(10,10,10,10));
        panelartikelgegevens.setLayout(new GridLayout(6,1));
        panelartikelgegevens.setBorder(new EmptyBorder(10,10,10,10));

        //voeg panels toe
        frame.add(panelklantgegevens);
        frame.add(panelordergegevens);
        frame.add(panelartikelgegevens);

        //maak labels
        JLabel ordernummer = new JLabel("Ordergegevens:");
        panelordergegevens.add(ordernummer);

        String[] product = db.getProductName(String.valueOf(order));
        for(int i = 0; i < product.length; i++) {
            JLabel productnaam = new JLabel("Artikel " + (i+1) + ": " + product[i]);
            panelordergegevens.add(productnaam);
        }

        JLabel klantgegevens = new JLabel("Klantgegevens: ");
        panelklantgegevens.add(klantgegevens);

        int klantID = Integer.parseInt(db.getCustomerID(Integer.parseInt(order)));
        String[] klantData = db.getCustomerDetails(klantID);

        JLabel klantnaam = new JLabel("Naam: " + klantData[1] + " " + klantData[2]);
        JLabel klantadres = new JLabel("Adres: " + klantData[3]);
        JLabel klantpostcode = new JLabel("Postcode: " + klantData[4]);
        JLabel klantwoonplaats = new JLabel("Woonplaats: " + klantData[5]);

        JLabel artikellocaties = new JLabel("Artikel locaties: ");
        panelartikelgegevens.add(artikellocaties);

        String[] locatie = db.getProductLocatie(order);
        for(int i = 0; i < locatie.length; i++) {
            JLabel artikellocatie = new JLabel("Artikel " + (i+1) + " -> Locatie:  " + locatie[i]);
            panelartikelgegevens.add(artikellocatie);
        }

        //voeg labels toe
        panelklantgegevens.add(klantnaam);
        panelklantgegevens.add(klantadres);
        panelklantgegevens.add(klantpostcode);
        panelklantgegevens.add(klantwoonplaats);

        //laat alles zien
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(true);
    }
}
