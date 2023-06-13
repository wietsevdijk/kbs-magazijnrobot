package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TSPtestscherm extends JFrame implements ActionListener {

    private static JTextField aantalCoordinatenField;
    private static int aantalCoordinaten;
    private static JTextField breedteMagazijnField;
    private static int breedteMagazijn;
    private static JTextField hoogteMagazijnField;
    private static int hoogteMagazijn;
    private static JButton goKnop;
    public TSPtestscherm() {
        setTitle("TSP Testscherm");
        setSize(1200, 700);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        add(new JLabel("Aantal Coördinaten: "));
        aantalCoordinatenField = new JTextField(6);
        add(aantalCoordinatenField);
        aantalCoordinatenField.addActionListener(this);

        add(new JLabel("Breedte Magazijn: "));
        breedteMagazijnField = new JTextField(6);
        add(breedteMagazijnField);
        breedteMagazijnField.addActionListener(this);

        add(new JLabel("Hoogte Magazijn: "));
        hoogteMagazijnField = new JTextField(6);
        add(hoogteMagazijnField);
        hoogteMagazijnField.addActionListener(this);

        goKnop = new JButton("GO");
        add(goKnop);
        goKnop.addActionListener(this);

        goKnop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                aantalCoordinaten = Integer.parseInt(aantalCoordinatenField.getText());
                breedteMagazijn = Integer.parseInt(breedteMagazijnField.getText());
                hoogteMagazijn = Integer.parseInt(hoogteMagazijnField.getText());
                TSPAlgoritme.TSPOplossing(aantalCoordinaten, breedteMagazijn, hoogteMagazijn);
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
    }
}
