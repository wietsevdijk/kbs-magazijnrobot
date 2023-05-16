package com.testfiles.versie2;

import com.testfiles.versie2.GridTekenPanel;

import javax.swing.*;
import java.awt.*;

public class Testscherm2 extends JFrame{

    private int[] squareArray = new int[25];

    private JPanel links;
    private JPanel rechts;

    public Testscherm2() {

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

        //Bereidt het linkerpanel voor
        links = new JPanel();
        links.setLayout(new GridLayout(1, 2)); //Zet de layout klaar voor 2 panels
        //Gooit het linkerpanel erin
        add(links);

        //Initieert de layout voor het rechterpanel

        //Bereidt het rechterpanel voor
        rechts = new JPanel();

        //Maakt de grid en gooit het rechterpanel erin
        addGridPanels();
        add(rechts);

    }

    public void addGridPanels() {

        GridTekenPanel gridje = new GridTekenPanel();
        rechts.add(gridje);

    }
}