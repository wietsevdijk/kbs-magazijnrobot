package com.company;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import com.fazecast.jSerialComm.*;

public class Hoofdscherm extends JFrame {
    Database db = new Database();
    Artikelscherm as = new Artikelscherm();
    RobotCommands rc = new RobotCommands();
    SerialPort sp = rc.getSp();

    private Color DarkGreen = new Color(0, 205, 0);
    private JPanel links;
    private JPanel midden;
    private JPanel rechts;
    private boolean automatisch = true;
    private boolean noodstopknop = false;


    public Hoofdscherm() throws SQLException {
        addStartScherm("HMI Startscherm", 1000, 550);

        //Serialcomm instellen
    }

    public void addStartScherm(String titel, int breedte, int hoogte) {

        setTitle(titel);
        setSize(breedte, hoogte);

        setLayout(new GridLayout(1, 3)); //Zet de layout klaar voor 2 panels

        addPanels();
        addHuidigeOrder();
        addGrid();
        addOrderList();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

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
        GridLayout layout = new GridLayout(11,1);
        midden.setLayout(layout);
        midden.setBorder(new LineBorder(Color.GRAY,2,false));


        //Maakt de dropdowns en gooit het midden erin
        add(midden);

        //Initieert de layout voor het rechterpanel
        GridLayout grLay = new GridLayout(5, 5);


        //Bereidt het rechterpanel voor
        rechts = new JPanel();

        //Zet de layout en tussenlijntjes (Voorgeinitieerde layout voor nodig!)
        rechts.setLayout(null);

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

    public void addGrid(){
        //draw grid
        class GridsCanvas extends JPanel {
            int width, height;

            int rows;

            int cols;


            GridsCanvas(int w, int h) {
                setSize(width = w, height = h);
            }

            public void paint(Graphics g) {
                // draw grid
                int width=63;
                int height=63;
                int yGap = 65;
                int xGap = 65;
                for(int x=0;x<5;x++)
                {
                    for(int y=0;y<5;y++)
                    {
                        g.setColor(DarkGreen);
                        g.fillRect(x*xGap,y*yGap,width,height);
                        System.out.println();
                    }
                }

                g.setColor(Color.green);
                //X-axis and Y-axis move in increments of 65


                g.setColor(Color.blue);

                //A1->E5               //X en Y van beginpunt lijn         //X en Y van eindpunt lijn
                //g.drawLine(xCoords("A"), yCoords("1"),xCoords("E"), yCoords("5"));

            }
        }

        GridsCanvas xyz = new GridsCanvas(325, 325);
        xyz.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int xLocatie = e.getX();
                int yLocatie = e.getY();

                as.openArtikel(xLocatie, yLocatie);
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                xyz.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {}
        });

        rechts.add(xyz);
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
}


