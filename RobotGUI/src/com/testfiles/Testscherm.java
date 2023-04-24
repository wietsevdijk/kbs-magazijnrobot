package com.testfiles;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Testscherm extends JFrame{

    private int[] squareArray = new int[25];

    private JPanel links;
    private JPanel rechts;

    public Testscherm() {

        addStartScherm("HMI Startscherm", 1000, 550);

    }

    public void addStartScherm(String titel, int breedte, int hoogte) {

        setTitle(titel);
        setSize(breedte, hoogte);

        setLayout(new GridLayout(1, 2)); //Zet de layout klaar voor 2 panels

        addPanels();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    public void addPanels() {

        //Gooit het linkerpanel erin
        links = new JPanel();
        links.setLayout(new FlowLayout());
        add(links);

        //Gooit het rechterpanel erin
        rechts = new JPanel();
        rechts.setLayout(new GridLayout(5, 5));
        addGridPanels();
        add(rechts);

    }

    public void addGridPanels() {

        for(int i = 0; i < squareArray.length; i++) {

            squareArray[i] = i;

            rechts.add(new JButton("" + (squareArray[i] + 1)));

        }

    }
}
