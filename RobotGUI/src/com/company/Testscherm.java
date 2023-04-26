package com.company;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Testscherm extends JFrame {
    Database db = new Database();

    private int[] squareArray = new int[25];

    private Color DarkGreen = new Color(0, 205, 0);

    private JPanel links;
    private JPanel midden;
    private JPanel rechts;


    public Testscherm() throws SQLException {

        addStartScherm("HMI Startscherm", 1000, 550);

    }

    public void addStartScherm(String titel, int breedte, int hoogte) {

        setTitle(titel);
        setSize(breedte, hoogte);

        setLayout(new GridLayout(1, 3)); //Zet de layout klaar voor 2 panels

        addPanels();
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
        addGridPanels();
        add(rechts);

    }

    public void addOrderList(){
        String[] orders = db.getAllOrders();
        for (int x = 0; x < orders.length; x++){
            JLabel orderNaam = new JLabel("Order: " + orders[x]);
            links.add(orderNaam);
        }
        System.out.println(db.getAllProductLocations());
    }

    public void addDropdowns() {

    }

    public void addGridPanels() {
        for (int i = 0; i < squareArray.length; i++) {

            squareArray[i] = i;
            Panel panel = new Panel();
            JLabel naam = new JLabel("" + (i + 1));


            if (i % 2 == 0) {
                panel.setBackground(Color.green);
            } else {
                panel.setBackground(DarkGreen);
            }

            rechts.add(panel);
            panel.add(naam);

        }

    }
}