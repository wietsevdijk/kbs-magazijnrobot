package com.company;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.fazecast.jSerialComm.*;

public class Hoofdscherm extends JFrame {
    Database db = new Database();

    //Opstart HMI: Haal eerst alle Klanten, Producten en Orders op
    ArrayList<Klant> klanten = db.retrieveAllCustomers();
    ArrayList<Product> producten = db.retrieveAllProducts();
    ArrayList<Order> orders = db.retrieveAllOrders(producten);



    Artikelscherm as = new Artikelscherm();
    RobotCommands rc = new RobotCommands();
    SerialPort sp = rc.getSp();
    GridTekenPanel gridje;

    private Color DarkGreen = new Color(0, 205, 0);
    private JPanel links;
    private JPanel midden;
    private JPanel rechts;
    private boolean automatisch = true;
    private boolean noodstopknop = false;



    public Hoofdscherm() throws SQLException {
        addStartScherm("HMI Startscherm", 1440, 720);

        //Serialcomm instellen
    }

    public void addStartScherm(String titel, int breedte, int hoogte) {

        setTitle(titel);
        setSize(breedte, hoogte);
        setResizable(false);

        setLayout(new GridLayout(1, 3)); //Zet de layout klaar voor 3 panels

        addPanels();
        addGrid();
        addHuidigeOrder();
        addOrderList();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    public void addGrid(){
        //draw grid
        gridje = new GridTekenPanel(5, 5);
        rechts.add(gridje);
    }


    public void addPanels() {

        //Bereidt het linker paneel voor
        links = new JPanel();
        links.setBorder(new LineBorder(Color.GRAY,2,false));
        //zet tekstlabels onder elkaar
        links.setLayout(new BoxLayout(links, BoxLayout.Y_AXIS));

        //gooit linker paneel erin
        add(links);


        //Bereidt het midden paneel voor
        midden = new JPanel();
        GridLayout layout = new GridLayout(14,1);
        midden.setLayout(layout);
        midden.setBorder(new LineBorder(Color.GRAY,2,false));


        //Maakt de dropdowns en gooit het midden erin
        add(midden);

        //Bereidt het rechterpanel voor
        rechts = new JPanel();

        //addGridPanels(false);
        add(rechts);
    }

    public void addHuidigeOrder(){
        int order = Integer.parseInt(db.getOrderID());
        String[] product = db.getProductName(String.valueOf(order));

        //labels aanmaken
        JLabel huidigeorder = new JLabel("Huidige order: " + order);
        huidigeorder.setHorizontalAlignment(JLabel.CENTER);
        //labels toevoegen
        midden.add(huidigeorder);


        for(int i = 0; i < 3; i++) {
            JLabel productnaam = new JLabel("Artikel " + (i+1) + ": " + product[i]);
            productnaam.setHorizontalAlignment(JLabel.CENTER);
            midden.add(productnaam);
        }

        String[] locatie = db.getProductLocatie(String.valueOf(order));
        for(int i = 0; i < 3; i++) {
            JLabel artikellocatie = new JLabel("Artikel " + (i+1) + " -> Locatie:  " + locatie[i]);
            artikellocatie.setHorizontalAlignment(JLabel.CENTER);
            midden.add(artikellocatie);
        }
        JButton genereerCoords = new JButton("Genereer coordinaten");
        JLabel coords = new JLabel();
        genereerCoords.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
       
                gridje.setTSPLine(true);
                gridje.setVak1(getRandomNumber(0,24) );
                gridje.setVak2(getRandomNumber(0,24));
                gridje.setVak3(getRandomNumber(0,24));

                int vak1 = gridje.getVak1() + 1;
                int vak2 = gridje.getVak2() + 1;
                int vak3 = gridje.getVak3() + 1;

                coords.setText("" + vak1 + ", " + vak2 + ", " + vak3);

                midden.add(coords);
                rechts.repaint();
                rechts.revalidate();
            }
        });

        midden.add(genereerCoords);

        JButton nieuweOrder = new JButton("Nieuwe order inladen");
        midden.add(nieuweOrder);

        JButton orderOphalen = new JButton("Producten ophalen");
        midden.add(orderOphalen);

        JButton toggleModus = new JButton("Automatisch/Handmatig");
        toggleModus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(automatisch){
                    addDialogBox();
                    automatisch = false;
                } else if (!automatisch){
                    addDialogBox();
                    automatisch = true;
                }
            }
        });
        midden.add(toggleModus);

        JButton noodstop = new JButton("NOODSTOP!");
        noodstop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(noodstopknop){
                    noodstopknop = false;
                    addDialogBox();
                } else if (!noodstopknop){
                    noodstopknop = true;
                    addDialogBox();
                }
            }
        });
        midden.add(noodstop);

    }

    public void addDialogBox() {
        if(noodstopknop) {
            JDialog d = new JDialog(Hoofdscherm.this, "NOODSTOP INGEDRUKT");
            JLabel noodstop = new JLabel("Je hebt de noodstop ingedrukt, alle proccessen worden stil gelegd!");
            d.setModal(true);
            d.setSize(450,100);
            d.add(noodstop);
            d.pack();
            d.setLocationRelativeTo(getContentPane());
            d.setVisible(true);
        } else if (!automatisch && !noodstopknop){
            JDialog d = new JDialog(Hoofdscherm.this, "Handmatig");
            JLabel handmatiglabel = new JLabel("Robot staat nu op handmatige modus! Je kan nu de robot besturen met de pijltjes toetsen");
            d.setModal(true);
            d.setSize(250,100);
            d.add(handmatiglabel);
            d.pack();
            d.setLocationRelativeTo(getContentPane());
            d.setVisible(true);
            d.requestFocus();

            d.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent event) {
                    if (event.getKeyCode() == KeyEvent.VK_UP) {
                        System.out.println("UP");
                        if (sp.openPort()) { // opent de port
                            try {
                                rc.moveRobotUp(rc.getSp()); // stuurt commando over port
                                Scanner data = new Scanner(rc.getSp().getInputStream()); // Krijgt iets terug van de serial
                                while (data.hasNextLine()) {
                                    //print vanuit de arduino serial MAAR loopt één commando achter EN werkt heel langzaam
                                    System.out.println(data.nextLine());
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    if (event.getKeyCode() == KeyEvent.VK_DOWN) {
                        System.out.println("DOWN");
                        if (sp.openPort()) { // opent de port
                            try {
                                rc.moveRobotDown(rc.getSp()); // stuurt commando over port
                                Scanner data = new Scanner(rc.getSp().getInputStream());
                                while (data.hasNextLine()) {
                                    System.out.println(data.nextLine());
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    if (event.getKeyCode() == KeyEvent.VK_LEFT) {
                        System.out.println("LEFT");
                        if (sp.openPort()) { // opent de port
                            try {
                                rc.moveRobotLeft(rc.getSp()); // stuurt commando over port
                                Scanner data = new Scanner(rc.getSp().getInputStream());
                                while (data.hasNextLine()) {
                                    System.out.println(data.nextLine());
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
                        System.out.println("RIGHT");
                        if (sp.openPort()) { // opent de port
                            try {
                                rc.moveRobotRight(rc.getSp()); // stuurt commando over port
                                Scanner data = new Scanner(rc.getSp().getInputStream());
                                while (data.hasNextLine()) {
                                    System.out.println(data.nextLine());
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });
        } else if (automatisch && !noodstopknop){
            JDialog d = new JDialog(Hoofdscherm.this, "Automatisch");
            JLabel automatischlabel = new JLabel("Robot staat nu op automatische modus!");
            d.setModal(true);
            d.setSize(250,100);
            d.add(automatischlabel);
            d.pack();
            d.setLocationRelativeTo(getContentPane());
            d.setVisible(true);
        }
    }



    public void addOrderList() {
        JLabel openorders = new JLabel("Open orders: ");
        openorders.setLocation((this.getWidth()-openorders.getWidth())/2,50);
        links.add(openorders);
        String[] orders = db.getAllOrders();
        String order= null;
        for (int x = 0; x < orders.length; x++) {
            JLabel orderNaam = new JLabel("Ordernummer: " + orders[x]); //print alle open orders
            orderNaam.setForeground(Color.blue);
            order = orders[x]; //krijg order nummer
            orderNaam.setCursor(new Cursor(Cursor.HAND_CURSOR)); // maak order nummer label clickable
            String finalOrder = order;

            orderNaam.addMouseListener(new MouseAdapter() { // voeg mouseclick listener toe aan ordernummer label
                public void mouseClicked(MouseEvent me) {
                    // your code
                    try {
                        Orderscherm os = new Orderscherm(finalOrder); // open order scherm uit Orderscherm klasse
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            });
            links.add(orderNaam);
        }
    }

    public boolean isNoodstopknop() {
        return noodstopknop;
    }

    public boolean isAutomatisch() {
        return automatisch;
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min); //Nodig voor actionperformed
    }

}






