package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class TSPtestscherm extends JFrame implements ActionListener {

    private JPanel panel;
    private JPanel gridPanel;
    private static JTextField aantalCoordinatenField;
    private static int aantalCoordinaten = 0;
    private static JTextField breedteMagazijnField;
    private static int breedteMagazijn = 0;
    private static JTextField hoogteMagazijnField;
    private static int hoogteMagazijn = 0;
    private static JButton goKnop;
    private static JLabel inputText;
    private static JLabel outputText;
    private static JLabel afstandText;
    private static JLabel tijdText;
    private SpringLayout springLayout;
    public TSPtestscherm() {
        setTitle("TSP Testscherm");
        setSize(650, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        panel = new JPanel();
        panel.setPreferredSize(new Dimension(650, 100));
        panel.setLayout(new SpringLayout());

        gridPanel = new JPanel();
        add(panel);
        panel.add(gridPanel);

        springLayout = new SpringLayout();
        panel.setLayout(springLayout);

        JLabel aantalCoordinatenJL = new JLabel("Aantal Coördinaten: ");
        panel.add(aantalCoordinatenJL);
        springLayout.putConstraint(SpringLayout.NORTH, aantalCoordinatenJL, 10, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, aantalCoordinatenJL, 10, SpringLayout.WEST, this);

        aantalCoordinatenField = new JTextField(6);
        panel.add(aantalCoordinatenField);
        aantalCoordinatenField.addActionListener(this);
        springLayout.putConstraint(SpringLayout.NORTH, aantalCoordinatenField, 10, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, aantalCoordinatenField, 125, SpringLayout.WEST, this);

        JLabel breedteMagazijnJL = new JLabel("Breedte Magazijn: ");
        panel.add(breedteMagazijnJL);
        springLayout.putConstraint(SpringLayout.NORTH, breedteMagazijnJL, 10, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, breedteMagazijnJL, 200, SpringLayout.WEST, this);

        breedteMagazijnField = new JTextField(6);
        panel.add(breedteMagazijnField);
        breedteMagazijnField.addActionListener(this);
        springLayout.putConstraint(SpringLayout.NORTH, breedteMagazijnField, 10, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, breedteMagazijnField, 310, SpringLayout.WEST, this);

        JLabel hoogteMagazijnJL = new JLabel("Hoogte Magazijn: ");
        panel.add(hoogteMagazijnJL);
        springLayout.putConstraint(SpringLayout.NORTH, hoogteMagazijnJL, 10, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, hoogteMagazijnJL, 390, SpringLayout.WEST, this);

        hoogteMagazijnField = new JTextField(6);
        panel.add(hoogteMagazijnField);
        hoogteMagazijnField.addActionListener(this);
        springLayout.putConstraint(SpringLayout.NORTH, hoogteMagazijnField, 10, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, hoogteMagazijnField, 495, SpringLayout.WEST, this);

        goKnop = new JButton("GO");
        panel.add(goKnop);
        goKnop.addActionListener(this);
        springLayout.putConstraint(SpringLayout.NORTH, goKnop, 5, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, goKnop, 570, SpringLayout.WEST, this);

        inputText = new JLabel();
        panel.add(inputText);
        springLayout.putConstraint(SpringLayout.NORTH, inputText, 45, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, inputText, 10, SpringLayout.WEST, this);

        outputText = new JLabel();
        panel.add(outputText);
        springLayout.putConstraint(SpringLayout.NORTH, outputText, 75, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, outputText, 10, SpringLayout.WEST, this);

        afstandText = new JLabel();
        panel.add(afstandText);
        springLayout.putConstraint(SpringLayout.NORTH, afstandText, 105, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, afstandText, 10, SpringLayout.WEST, this);

        tijdText = new JLabel();
        panel.add(tijdText);
        springLayout.putConstraint(SpringLayout.NORTH, tijdText, 135, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, tijdText, 10, SpringLayout.WEST, this);





        goKnop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.remove(gridPanel);
                if(!Objects.equals(aantalCoordinatenField.getText(), "") && !Objects.equals(breedteMagazijnField.getText(), "") && !Objects.equals(hoogteMagazijnField.getText(), "")) {
                    aantalCoordinaten = Integer.parseInt(aantalCoordinatenField.getText());
                    breedteMagazijn = Integer.parseInt(breedteMagazijnField.getText());
                    hoogteMagazijn = Integer.parseInt(hoogteMagazijnField.getText());
                }
                if(aantalCoordinaten != 0 && breedteMagazijn != 0 && hoogteMagazijn != 0) {
                    TSPAlgoritme algoritme = new TSPAlgoritme(breedteMagazijn, hoogteMagazijn, aantalCoordinaten);
                    gridPanel = new GridTekenPanel(breedteMagazijn, hoogteMagazijn, 600, 600);
                    panel.add(gridPanel);
                    springLayout.putConstraint(SpringLayout.NORTH, gridPanel, 165, SpringLayout.NORTH, panel);
                    springLayout.putConstraint(SpringLayout.WEST, gridPanel, 15, SpringLayout.WEST, panel);

                    String input = algoritme.toStringBegincoordinaten();
                    inputText.setText(input);
                    String output = algoritme.toStringVolgorde();
                    outputText.setText(output);
                    String afstand = algoritme.toStringAfstand();
                    afstandText.setText(afstand);
                    String tijd = algoritme.toStringTijd();
                    tijdText.setText(tijd);
                    panel.revalidate();
                    panel.repaint();
                }
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
    }
}