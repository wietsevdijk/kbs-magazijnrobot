package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TSPtestscherm extends JFrame implements ActionListener {

    private JPanel panel;
    private JPanel gridPanel;
    private static JTextField aantalCoordinatenField;
    private static int aantalCoordinaten;
    private static JTextField breedteMagazijnField;
    private static int breedteMagazijn;
    private static JTextField hoogteMagazijnField;
    private static int hoogteMagazijn;
    private static JButton goKnop;
    private static JLabel coordinaatText;
    private static ArrayList<Coordinaat> volgorde = new ArrayList<>();
    private SpringLayout springLayout;
    public TSPtestscherm() {
        setTitle("TSP Testscherm");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        panel = new JPanel();
        panel.setPreferredSize(new Dimension(700, 100));
        panel.setLayout(new SpringLayout());

        gridPanel = new JPanel();
        add(panel);
        panel.add(gridPanel);

        springLayout = new SpringLayout();
        panel.setLayout(springLayout);

        JLabel aantalCoordinatenJL = new JLabel("Aantal Coördinaten: ");
        panel.add(aantalCoordinatenJL);
        springLayout.putConstraint(SpringLayout.NORTH, aantalCoordinatenJL, 5, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, aantalCoordinatenJL, 0, SpringLayout.WEST, this);

        aantalCoordinatenField = new JTextField(6);
        panel.add(aantalCoordinatenField);
        aantalCoordinatenField.addActionListener(this);
        springLayout.putConstraint(SpringLayout.NORTH, aantalCoordinatenField, 5, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, aantalCoordinatenField, 115, SpringLayout.WEST, this);

        JLabel breedteMagazijnJL = new JLabel("Breedte Magazijn: ");
        panel.add(breedteMagazijnJL);
        springLayout.putConstraint(SpringLayout.NORTH, breedteMagazijnJL, 5, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, breedteMagazijnJL, 190, SpringLayout.WEST, this);

        breedteMagazijnField = new JTextField(6);
        panel.add(breedteMagazijnField);
        breedteMagazijnField.addActionListener(this);
        springLayout.putConstraint(SpringLayout.NORTH, breedteMagazijnField, 5, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, breedteMagazijnField, 300, SpringLayout.WEST, this);

        JLabel hoogteMagazijnJL = new JLabel("Hoogte Magazijn: ");
        panel.add(hoogteMagazijnJL);
        springLayout.putConstraint(SpringLayout.NORTH, hoogteMagazijnJL, 5, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, hoogteMagazijnJL, 380, SpringLayout.WEST, this);

        hoogteMagazijnField = new JTextField(6);
        panel.add(hoogteMagazijnField);
        hoogteMagazijnField.addActionListener(this);
        springLayout.putConstraint(SpringLayout.NORTH, hoogteMagazijnField, 5, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, hoogteMagazijnField, 485, SpringLayout.WEST, this);

        goKnop = new JButton("GO");
        panel.add(goKnop);
        goKnop.addActionListener(this);
        springLayout.putConstraint(SpringLayout.NORTH, goKnop, 0, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, goKnop, 550, SpringLayout.WEST, this);

        coordinaatText = new JLabel();
        panel.add(coordinaatText);
        springLayout.putConstraint(SpringLayout.NORTH, coordinaatText, 30, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, coordinaatText, 0, SpringLayout.WEST, this);





        goKnop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.remove(gridPanel);
                aantalCoordinaten = Integer.parseInt(aantalCoordinatenField.getText());
                breedteMagazijn = Integer.parseInt(breedteMagazijnField.getText());
                hoogteMagazijn = Integer.parseInt(hoogteMagazijnField.getText());
                TSPAlgoritme algoritme = new TSPAlgoritme(aantalCoordinaten, breedteMagazijn, hoogteMagazijn);
                gridPanel = new GridTekenPanel(breedteMagazijn, hoogteMagazijn, 600, 600);
                panel.add(gridPanel);
                springLayout.putConstraint(SpringLayout.NORTH, gridPanel, 50, SpringLayout.NORTH, panel);
                springLayout.putConstraint(SpringLayout.WEST, gridPanel, 5, SpringLayout.WEST, panel);



                volgorde = algoritme.getVolgorde();
                String coordinaatTexten = "";
                for(Coordinaat coordinaat: volgorde) {
                    coordinaatTexten+=coordinaat.toString();
                }
                coordinaatText.setText(coordinaatTexten);
                panel.revalidate();
                panel.repaint();
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
    }
}
