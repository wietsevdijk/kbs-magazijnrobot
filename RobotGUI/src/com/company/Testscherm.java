package com.company;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
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
    public SerialPort sp;


    public Testscherm() throws SQLException {
        addStartScherm("HMI Startscherm", 1000, 550);

        //Serialcomm instellen
        sp = SerialPort.getCommPort("COM5"); // selecteer je gebruikte COM port
        sp.setComPortParameters(9600, 8, 1, 0); //Set Serial baudrate
        sp.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 0, 0); //timeouts uitzetten
    }

    public void addStartScherm(String titel, int breedte, int hoogte) {

        setTitle(titel);
        setSize(breedte, hoogte);

        setLayout(new GridLayout(1, 3)); //Zet de layout klaar voor 2 panels

        addPanels();
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
        midden.setLayout(new FlowLayout());
        midden.setBorder(new LineBorder(Color.GRAY,2,false));

        //Maakt de dropdowns en gooit het midden erin
        add(midden);

        //Initieert de layout voor het rechterpanel
        GridLayout grLay = new GridLayout(5, 5);


        //Bereidt het rechterpanel voor
        rechts = new JPanel();

        //Zet de layout en tussenlijntjes (Voorgeinitieerde layout voor nodig!)
        rechts.setLayout(null);
        rechts.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //See where the mouse clicks on the grid and send location to robot
                if(e.getX() < 65 && e.getY() < 65){
                    System.out.println("A1");
                    try {
                        moveRobotToLocation(sp, "A1");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                if(e.getX() > 65 && e.getX() < 130 && e.getY() < 65){
                    System.out.println("A2");
                    try {
                        moveRobotToLocation(sp, "A2");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                if(e.getX() > 130 && e.getX() < 195 && e.getY() < 65){
                    System.out.println("A3");
                    try {
                        moveRobotToLocation(sp, "A3");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                if(e.getX() > 195 && e.getX() < 260 && e.getY() < 65){
                    System.out.println("A4");
                    try {
                        moveRobotToLocation(sp, "A4");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                if(e.getX() > 260 && e.getX() < 325 && e.getY() < 65){
                    System.out.println("A5");
                    try {
                        moveRobotToLocation(sp, "A5");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                if(e.getX() < 65 && e.getY() > 65 && e.getY() < 130){
                    System.out.println("B1");
                    try {
                        moveRobotToLocation(sp, "B1");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                if(e.getX() > 65 && e.getX() < 130 && e.getY() > 65 && e.getY() < 130){
                    System.out.println("B2");
                    try {
                        moveRobotToLocation(sp, "B2");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                if(e.getX() > 130 && e.getX() < 195 && e.getY() > 65 && e.getY() < 130){
                    System.out.println("B3");
                    try {
                        moveRobotToLocation(sp, "B3");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                if(e.getX() > 195 && e.getX() < 260 && e.getY() > 65 && e.getY() < 130){
                    System.out.println("B4");
                    try {
                        moveRobotToLocation(sp, "B4");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                if(e.getX() > 260 && e.getX() < 325 && e.getY() > 65 && e.getY() < 130){
                    System.out.println("B5");
                    try {
                        moveRobotToLocation(sp, "B5");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                if(e.getX() < 65 && e.getY() > 130 && e.getY() < 195){
                    System.out.println("C1");
                    try {
                        moveRobotToLocation(sp, "C1");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                if(e.getX() > 65 && e.getX() < 130 && e.getY() > 130 && e.getY() < 195){
                    System.out.println("C2");
                    try {
                        moveRobotToLocation(sp, "C2");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                if(e.getX() > 130 && e.getX() < 195 && e.getY() > 130 && e.getY() < 195){
                    System.out.println("C3");
                    try {
                        moveRobotToLocation(sp, "C3");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                if(e.getX() > 195 && e.getX() < 260 && e.getY() > 130 && e.getY() < 195){
                    System.out.println("C4");
                    try {
                        moveRobotToLocation(sp, "C4");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                if(e.getX() > 260 && e.getX() < 325 && e.getY() > 130 && e.getY() < 195){
                    System.out.println("C5");
                    try {
                        moveRobotToLocation(sp, "C5");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                if(e.getX() < 65 && e.getY() > 195 && e.getY() < 260){
                    System.out.println("D1");
                    try {
                        moveRobotToLocation(sp, "D1");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                if(e.getX() > 65 && e.getX() < 130 && e.getY() > 195 && e.getY() < 260){
                    System.out.println("D2");
                    try {
                        moveRobotToLocation(sp, "D2");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                if(e.getX() > 130 && e.getX() < 195 && e.getY() > 195 && e.getY() < 260) {
                    System.out.println("D3");
                    try {
                        moveRobotToLocation(sp, "D3");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                if(e.getX() > 195 && e.getX() < 260 && e.getY() > 195 && e.getY() < 260){
                    System.out.println("D4");
                    try {
                        moveRobotToLocation(sp, "D4");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                if(e.getX() > 260 && e.getX() < 325 && e.getY() > 195 && e.getY() < 260){
                    System.out.println("D5");
                    try {
                        moveRobotToLocation(sp, "D5");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                if(e.getX() < 65 && e.getY() > 260 && e.getY() < 325){
                    System.out.println("E1");
                    try {
                        moveRobotToLocation(sp, "E1");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                if(e.getX() > 65 && e.getX() < 130 && e.getY() > 260 && e.getY() < 325){
                    System.out.println("E2");
                    try {
                        moveRobotToLocation(sp, "E2");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                if(e.getX() > 130 && e.getX() < 195 && e.getY() > 260 && e.getY() < 325){
                    System.out.println("E3");
                    try {
                        moveRobotToLocation(sp, "E3");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                if(e.getX() > 195 && e.getX() < 260 && e.getY() > 260 && e.getY() < 325){
                    System.out.println("E4");
                    try {
                        moveRobotToLocation(sp, "E4");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                if(e.getX() > 260 && e.getX() < 325 && e.getY() > 260 && e.getY() < 325){
                    System.out.println("E5");
                    try {
                        moveRobotToLocation(sp, "E5");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }



            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        //addGridPanels(false);
        add(rechts);

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
                        g.setColor(Color.red);
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
        rechts.add(xyz);

    }

    public void drawRectangle(Graphics g){
        g.setColor(Color.green);
        g.drawRect(10,10,63,63);
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


    public void addDropdowns() {
    }

    public void loadSelectedOrders() {
    }

    public void addGridPanels() {

    }

    public void moveRobotToLocation(SerialPort sp, String location) throws IOException {
        byte[] bytes;
        bytes = location.getBytes(StandardCharsets.UTF_8); //Zet string om naar bytes en stuurt naar arduino
        sp.getOutputStream().write(bytes);
        sp.getOutputStream().flush();
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
        SerialPort sp = SerialPort.getCommPort("COM7");
        if(sp.closePort()) { // sluit de connectie
            System.out.println("Failed to open port :(");
        }
        return sp;
    }

}


