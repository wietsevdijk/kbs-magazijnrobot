package com.company;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import com.fazecast.jSerialComm.*;

public class Hoofdscherm extends JFrame {
    Database db = new Database();

    //Opstart HMI: Haal eerst alle Klanten, Producten en Orders op
    ArrayList<Klant> klanten = db.retrieveAllCustomers();
    ArrayList<Product> producten = db.retrieveAllProducts();
    ArrayList<Order> orders = db.retrieveAllOrders(producten);
    boolean coordModus;


    Artikelscherm as = new Artikelscherm();
    RobotCommands rc = new RobotCommands();
    DBLocationToHMILocation DBtoHMI = new DBLocationToHMILocation();

    SerialPort sp = rc.openSP();
    GridTekenPanel grid;

    private Color DarkGreen = new Color(0, 205, 0);
    private JPanel links;
    private JPanel midden;
    private JPanel rechts;
    private JScrollPane jScrollPaneMidden;
    private JPanel currentorderpanel;
    private JPanel buttonpanel;
    private boolean automatisch = true;
    private boolean noodstopknop = false;
    JLabel artikellocatie1 = new JLabel();
    JLabel artikellocatie2 = new JLabel();
    JLabel artikellocatie3 = new JLabel();




    public Hoofdscherm() throws SQLException {
        addStartScherm("HMI Startscherm", 1250, 450);

    }

    public void addStartScherm(String titel, int breedte, int hoogte) {

        setTitle(titel);
        setSize(breedte, hoogte);
        setResizable(false);
        requestFocus();

        setLayout(new GridLayout(1, 3)); //Zet de layout klaar voor 3 panels

        addPanels();
        addOrderList();
        addHuidigeOrder();
        addGrid();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    public void addGrid(){
        //draw grid van 5x5
        grid = new GridTekenPanel(5, 5);
        rechts.add(grid);
    }


    public void addPanels() {
        //Bereidt het linker paneel voor
        links = new JPanel();
        //zet tekstlabels in linker vak onder elkaar
        links.setLayout(new GridLayout(1,1));
        links.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        //gooit linker paneel erin
        add(links);


        //Bereidt het midden paneel voor
        midden = new JPanel();
        midden.setLayout(new GridLayout(2, 1));
        midden.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        //maakt scroll panel aan met huidige order locaties
        currentorderpanel = new JPanel();
        currentorderpanel.setLayout(new GridLayout(10,0));
        jScrollPaneMidden = new JScrollPane(currentorderpanel);
        jScrollPaneMidden.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        midden.add(jScrollPaneMidden);

        //maakt buttonpanel aan
        buttonpanel = new JPanel();
        buttonpanel.setLayout(new GridLayout(6,1));

        midden.add(buttonpanel);



        //Maakt de dropdowns en gooit het midden erin
        add(midden);

        //Bereidt het rechterpanel voor
        rechts = new JPanel();

        //addGridPanels(false);
        add(rechts);
    }

    //print laatst toegevoegde open order in midden scherm
    public void addHuidigeOrder(){
        //OUD: genereerd random coordinaten
        //NIEUW: tekent coordinaten uit de laatste open order op de grid
        JButton genereerCoords = new JButton("Genereer coordinaten");
        genereerCoords.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //vertaal Database positie naar HMI positie
                int []positie = DBtoHMI.positie();
                //Zorgt dat we kunnen tekenen op de grid
                grid.setTSPLine(true);

                //Geef posities door aan grid
                grid.setVak1(getRandomNumber(0,24));
                grid.setVak2(getRandomNumber(0,24));
                grid.setVak3(getRandomNumber(0,24));

                showLatestorder();
                rechts.repaint();
                rechts.revalidate();
            }
        });

        buttonpanel.add(genereerCoords);

        //Doet nog niks. moet later een nieuwe order gaan inladen
        JButton nieuweOrder = new JButton("Nieuwe order inladen");
        buttonpanel.add(nieuweOrder);

        nieuweOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLatestorder();
                //vertaal Database positie naar HMI positie
                int []positie = DBtoHMI.positie( );

                //Zorgt dat we kunnen tekenen op de grid
                grid.setTSPLine(true);

                //Geef posities door aan grid
                grid.setVak1(positie[0]);
                grid.setVak2(positie[1]);
                grid.setVak3(positie[2]);

                midden.repaint();
                midden.revalidate();
                rechts.repaint();
                rechts.revalidate();
            }
        });

        JButton coordMode = new JButton("Coord Mode");
        buttonpanel.add(coordMode);

        coordMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    rc.sendCommandMode(sp);
                    if(rc.getMessage().endsWith("modustrue")){
                        System.out.println("modus is aan");
                        rc.setMessage("");
                    }

                    if(rc.getMessage().endsWith("modusfalse")){
                        System.out.println("modus is uit");
                        rc.setMessage("");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });


        //doet nog niks. moet later een command sturen naar robot op producten op te halen
        JButton orderOphalen = new JButton("Producten ophalen");
        buttonpanel.add(orderOphalen);

        orderOphalen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> productLocaties = db.getProductLocatie(db.getOrderID());
                try {
                    rc.sendLocation(sp, "test");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                //db.shipOrder(db.getOrderID());
            }
        });

        //Switch tussen automatisch en handmatig besuren. Werkt maar alleen in het HMI. Moet nog command doorsturen naar robot
        JButton toggleModus = new JButton("Automatisch/Handmatig");
        toggleModus.setBackground(Color.ORANGE);
        toggleModus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(automatisch && !noodstopknop){
                    addDialogBox();
                    toggleModus.setBackground(Color.GREEN);
                    automatisch = false;
                } else if (!automatisch && !noodstopknop){
                    toggleModus.setBackground(Color.ORANGE);
                    addDialogBox();
                    automatisch = true;
                }
            }
        });
        buttonpanel.add(toggleModus);

        //Gooit de noodstop erop. Werkt maar alleen in HMI. Moet nog commando versturen naar robot
        JButton noodstop = new JButton("NOODSTOP!");
        noodstop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(noodstopknop){
                    noodstop.setBackground(null);
                    toggleModus.setBackground(null);
                    noodstopknop = false;
                    addDialogBox();
                } else if (!noodstopknop){
                    toggleModus.setBackground(Color.RED);
                    noodstop.setBackground(Color.RED);
                    noodstopknop = true;
                    addDialogBox();
                }
            }
        });
        buttonpanel.add(noodstop);
    }

    //Laat een dialog box zien op basis van je gekozen waarde (Automatisch, Handmatig, Noodstop)
    //in het handmatig dialogbox kan je de robot besturen met pijltjes toetsen
    public void addDialogBox() {
        if(noodstopknop) {
            JDialog d = new JDialog(Hoofdscherm.this, "NOODSTOP INGEDRUKT");
            JLabel noodstop = new JLabel("Je hebt de noodstop ingedrukt, alle proccessen worden stil gelegd!");
            d.setModal(false); //Zorgt ervoor dat we toesteming hebben om Focus te vragen
            d.setSize(450,100);
            d.add(noodstop);
            d.pack();
            d.setLocationRelativeTo(getContentPane()); //opent de dialog box in het midden van het hoofdscherm
            d.setVisible(true);
        } else if (!automatisch && !noodstopknop){
            JDialog d = new JDialog(Hoofdscherm.this, "Handmatig");
            JLabel handmatiglabel = new JLabel("Robot staat nu op handmatige modus! Je kan nu de robot besturen met de pijltjes toetsen");
            d.setModal(false); //Zorgt ervoor dat we toesteming hebben om Focus te vragen
            d.setSize(250,100);
            d.add(handmatiglabel);
            d.pack();
            d.setLocationRelativeTo(getContentPane());
            d.setVisible(true);
            d.requestFocus(); //Vraag focus in dit scherm zodat we de robot kunnen besturen

            d.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent event) {
                    if (event.getKeyCode() == KeyEvent.VK_UP) {
                        if (sp.openPort()) { // opent de port
                            try {
                                rc.moveRobotUp(rc.getSp()); // stuurt commando over port
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    if (event.getKeyCode() == KeyEvent.VK_DOWN) {
                        if (sp.openPort()) { // opent de port
                            try {
                                rc.moveRobotDown(rc.getSp()); // stuurt commando over port
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    if (event.getKeyCode() == KeyEvent.VK_LEFT) {
                        if (sp.openPort()) { // opent de port
                            try {
                                rc.moveRobotLeft(rc.getSp()); // stuurt commando over port
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
                        if (sp.openPort()) { // opent de port
                            try {
                                rc.moveRobotRight(rc.getSp()); // stuurt commando over port
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
            d.setModal(false);
            d.setSize(250,100);
            d.add(automatischlabel);
            d.pack();
            d.setLocationRelativeTo(getContentPane());
            d.setVisible(true);
        }
    }

    public void showLatestorder(){
        //haal orderID op
        int order = 0;
        try {
            order = Integer.parseInt(db.getOrderID());
        }catch (Exception sqlException){
            System.out.println("Geen open orders");
        }
        //haal producten op naar basis van eerder opgehaalde orderID
        String[] product = db.getProductName(String.valueOf(order));

        //labels aanmaken
        JLabel huidigeorder = new JLabel("Huidige order: " + order);
        huidigeorder.setHorizontalAlignment(JLabel.CENTER);
        huidigeorder.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        jScrollPaneMidden.setColumnHeaderView(huidigeorder);

        //Voegt product locaties toe
        String[] locatie = db.getProductLocatie(String.valueOf(order)).toArray(new String[0]);
        for(int i = 0; i < locatie.length; i++) {
            if(i == 0) {
                artikellocatie1.setText("Product " + (i + 1) + ": " + locatie[i]);
            }

            if(i == 1) {
                artikellocatie2.setText("Product " + (i + 1) + ": " + locatie[i]);
            }

            if(i == 2) {
                artikellocatie3.setText("Product " + (i + 1) + ": " + locatie[i]);
            }
        }
        currentorderpanel.add(artikellocatie1);
        currentorderpanel.add(artikellocatie2);
        currentorderpanel.add(artikellocatie3);
        currentorderpanel.repaint();
        currentorderpanel.revalidate();
    }


    //haalt alle open orders op
    public void addOrderList() {
        ArrayList<String> orderIDs = db.getAllOrders();
        String order= null;

        GridLayout layout = new GridLayout(20,1);
        JPanel orderpanel = new JPanel();
        orderpanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        orderpanel.setLayout(layout);

        JScrollPane jScrollPane1 = new JScrollPane(orderpanel);
        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JLabel openorders = new JLabel("Open orders: ");
        openorders.setHorizontalAlignment(0);
        openorders.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        jScrollPane1.setColumnHeaderView(openorders);
        links.add(jScrollPane1);

        for (String orderID : orderIDs) {
            JLabel orderNaam = new JLabel("Ordernummer: " + orderID); //print alle open orders
            orderNaam.setForeground(Color.blue);

            order = orderID; //krijg order nummer
            orderNaam.setCursor(new Cursor(Cursor.HAND_CURSOR)); // maak order nummer label clickable
            orderpanel.add(orderNaam);
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
            repaint();
            revalidate();
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






