package com.testfiles.versie1;

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
        links.setLayout(new GridLayout(1, 2)); //Zet de layout klaar voor 2 panels
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

        for(int i = 0; i < 50; i++) {
            listModel.addElement("Order " + (i + 1));
        }

        JList<String> orderList = new JList<String>(listModel);
        JScrollPane listScroller = new JScrollPane(orderList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JScrollBar bigBar = listScroller.getVerticalScrollBar();
        bigBar.setPreferredSize(new Dimension(20, 1000));

//        constraints.ipady = 1000;
//        constraints.ipadx = 75;
//        constraints.gridx = 0;
//        constraints.gridy = 0;
//        constraints.gridwidth = 1;
//        constraints.gridheight = 3;
        links.add(listScroller);
    }

    public void addDetails() {

    }

    public void addGridPanels() {
        int c = 1; //Startkolom
        int r = 1; //Startpunt van de rij

        for(int i = 0; i < this.squareArray.length; ++i) {
            JLabel naam = new JLabel();
            this.squareArray[i] = i;
            int ColumnCount = 5;
            int RowCount = 5;
            Panel panel = new Panel();
            panel.setBackground(Color.green);

            //Als het kolomnummer tussen 1 en 5 zit
            if (c <= ColumnCount && c > 0) {
                naam = new JLabel("" + r + "." + c); //Zet de label van dit panel

                //Als het rijnummer tussen 1 en 5 zit
                if (r < RowCount && r > 0) {
                    ++r; //Stap 1 naar rechts in de rij
                }
                else {
                    ++c; //Stap 1 kolom omlaag
                    r = 1;
                }
            }

            this.rechts.add(panel);
            panel.add(naam);
        }

    }
}