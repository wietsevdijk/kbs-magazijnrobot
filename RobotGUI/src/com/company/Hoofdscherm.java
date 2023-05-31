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
    DBLocationToHMILocation DBtoHMI = new DBLocationToHMILocation();
    SerialPort sp = rc.getSp();
    GridTekenPanel grid;

    private Color DarkGreen = new Color(0, 205, 0);
    private JPanel links;
    private JPanel midden;
    private JPanel rechts;
    private boolean automatisch = true;
    private boolean noodstopknop = false;



    public Hoofdscherm() throws SQLException {
        addStartScherm("HMI Startscherm", 1440, 720);
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
        //draw grid van 5x5
        grid = new GridTekenPanel(5, 5);
        rechts.add(grid);
    }


    public void addPanels() {
        //Bereidt het linker paneel voor
        links = new JPanel();
        links.setBorder(new LineBorder(Color.GRAY,2,false));
        //zet tekstlabels in linker vak onder elkaar
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

    //print laatst toegevoegde open order in midden scherm
    public void addHuidigeOrder(){
        //haal orderID op
        int order = Integer.parseInt(db.getOrderID());

        //haal producten op naar basis van eerder opgehaalde orderID
        String[] product = db.getProductName(String.valueOf(order));

        //labels aanmaken
        JLabel huidigeorder = new JLabel("Huidige order: " + order);
        huidigeorder.setHorizontalAlignment(JLabel.CENTER);

        //labels toevoegen
        midden.add(huidigeorder);


        //Voegt productnamen toe
        for(int i = 0; i < product.length; i++) {
            JLabel productnaam = new JLabel("Artikel " + (i+1) + ": " + product[i]);
            productnaam.setHorizontalAlignment(JLabel.CENTER);
            midden.add(productnaam);
        }

        //Voegt product locaties toe
        String[] locatie = db.getProductLocatie(String.valueOf(order));
        for(int i = 0; i < locatie.length; i++) {
            JLabel artikellocatie = new JLabel("Artikel " + (i+1) + " -> Locatie:  " + locatie[i]);
            artikellocatie.setHorizontalAlignment(JLabel.CENTER);
            midden.add(artikellocatie);
        }

        //OUD: genereerd random coordinaten
        //NIEUW: tekent coordinaten uit de laatste open order op de grid
        JButton genereerCoords = new JButton("Genereer coordinaten");
        JLabel coords = new JLabel();
        genereerCoords.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //vertaal Database positie naar HMI positie
                int []positie = DBtoHMI.positie();

                //Zorgt dat we kunnen tekenen op de grid
                grid.setTSPLine(true);

                //Geef posities door aan grid
                grid.setVak1(positie[0]);
                grid.setVak2(positie[1]);
                grid.setVak3(positie[2]);

                System.out.println(positie[0]);


                midden.add(coords);
                rechts.repaint();
                rechts.revalidate();
            }
        });

        midden.add(genereerCoords);

        //Doet nog niks. moet later een nieuwe order gaan inladen
        JButton nieuweOrder = new JButton("Nieuwe order inladen");
        midden.add(nieuweOrder);

        //doet nog niks. moet later een command sturen naar robot op producten op te halen
        JButton orderOphalen = new JButton("Producten ophalen");
        midden.add(orderOphalen);

        //Switch tussen automatisch en handmatig besuren. Werkt maar alleen in het HMI. Moet nog command doorsturen naar robot
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

        //Gooit de noodstop erop. Werkt maar alleen in HMI. Moet nog commando versturen naar robot
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
            d.setModal(false);
            d.setSize(250,100);
            d.add(automatischlabel);
            d.pack();
            d.setLocationRelativeTo(getContentPane());
            d.setVisible(true);
        }
    }


    //haalt alle open orders op
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






