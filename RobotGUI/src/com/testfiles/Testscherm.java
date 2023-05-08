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

        //Bereidt het linkerpanel voor
        links = new JPanel();
        links.setLayout(new FlowLayout(FlowLayout.LEFT));

        //Maakt de lijst en gooit het linkerpanel erin
        addList();
        add(links);

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

    public void addList() {
        DefaultListModel listModel = new DefaultListModel();

        for(int i = 0; i < 20; i++) {
            listModel.addElement("Order" + (i + 1));
        }

        JList<String> orderList = new JList<String>(listModel);
        JScrollPane listScroller = new JScrollPane(orderList);
        listScroller.setPreferredSize(new Dimension(250, 80));

        links.add(orderList);
    }

    public void addDropdown() {

    }

    public void addGridPanels() {
        int c = 1;
        int r = 1;

        for(int i = 0; i < this.squareArray.length; ++i) {
            JLabel naam = new JLabel();
            this.squareArray[i] = i;
            int ColumnCount = 5;
            int RowCount = 5;
            Panel panel = new Panel();
            panel.setBackground(Color.green);
            if (c <= ColumnCount && c > 0) {
                naam = new JLabel("" + c + "." + r);
                if (r < RowCount && r > 0) {
                    ++r;
                } else {
                    ++c;
                    r = 1;
                }
            }

            this.rechts.add(panel);
            panel.add(naam);
        }

    }
}
