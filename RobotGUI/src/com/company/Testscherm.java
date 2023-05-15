package com.company;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import com.fazecast.jSerialComm.*;

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
        addGridPanels();
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
        //addGridPanels(false);
        add(rechts);

    }

    public void addOrderList() {
        String[] orders = db.getAllOrders();
        for (int x = 0; x < orders.length; x++) {
            JLabel orderNaam = new JLabel("Order: " + orders[x]);
            links.add(orderNaam);
        }
    }

    public void addDropdowns() {
    }

    public void loadSelectedOrders() {
    }

    public void addGridPanels() {
        for (int i = 0; i < squareArray.length; i++) {

            squareArray[i] = i;
            Panel panel = new Panel();
            JLabel naam = new JLabel("" + (i + 1));
            Integer[] magazijnLocatie = db.getAllProductLocations().keySet().toArray(new Integer[0]);
            String[] productID = db.getAllProductLocations().values().toArray(new String[0]);
            int labelInt = i;

            if (i % 2 == 0) {
                panel.setBackground(Color.green);
            } else {
                panel.setBackground(DarkGreen);
            }

            rechts.add(panel);
            panel.add(naam);
        }
        repaint();
    }

    public void moveRobotUp(SerialPort sp) throws IOException {
        //Send data to arduino
        for (int i = 0; i < 1; ++i) {
            byte[] bytes;
            bytes = "UP".getBytes(StandardCharsets.UTF_8); //Zet string om naar bytes en stuurt naar arduino
            sp.getOutputStream().write(bytes);
            sp.getOutputStream().flush();
        }
    }

    public void moveRobotDown(SerialPort sp) throws IOException {

        for (int i = 0; i < 1; ++i) {
            byte[] bytes;
            bytes = "DOWN".getBytes(StandardCharsets.UTF_8); //Zet string om naar bytes en stuurt naar arduino
            sp.getOutputStream().write(bytes); // stuur hier je bytes
            sp.getOutputStream().flush();
        }
    }

    public void moveRobotLeft(SerialPort sp) throws IOException {
        for (int i = 0; i < 1; ++i) {
            byte[] bytes;
            bytes = "LEFT".getBytes(StandardCharsets.UTF_8); //Zet string om naar bytes en stuurt naar arduino
            sp.getOutputStream().write(bytes);
            sp.getOutputStream().flush();
        }


    }

    public void moveRobotRight(SerialPort sp) throws IOException {

        for (int i = 0; i < 1; ++i) {
            byte[] bytes;
            bytes = "RIGHT".getBytes(StandardCharsets.UTF_8); //Zet string om naar bytes en stuurt naar arduino
            sp.getOutputStream().write(bytes);
            sp.getOutputStream().flush();
        }
    }

    public SerialPort closeArduinoConnection(){
        SerialPort sp = SerialPort.getCommPort("COM5");
        if(sp.closePort()) { // sluit de connectie
            System.out.println("Failed to open port :(");
        }
        return sp;
    }

}


