package com.testfiles.versie3;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

        //uncomment om magazijn in te laden maar de werkt robot besturing niet meer.
    }

    public void addStartScherm(String titel, int breedte, int hoogte) {

        setTitle(titel);
        setSize(breedte, hoogte);

        setLayout(new GridLayout(1, 3)); //Zet de layout klaar voor 2 panels

        addPanels();
        addButtons();
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
                g.drawOval(xCoords("A"),yCoords("4"),35,35);

                g.setColor(Color.blue);

               //A1->E5               //X en Y van beginpunt lijn         //X en Y van eindpunt lijn
               g.drawLine(xCoords("A"), yCoords("1"),xCoords("E"), yCoords("5"));

            }
        }

        GridsCanvas xyz = new GridsCanvas(325, 325);
        rechts.add(xyz);

    }

    public void drawRectangle(Graphics g){
        g.setColor(Color.green);
        g.drawRect(10,10,63,63);
    }

    public int xCoords (String locatieX){
        return switch (locatieX) {
            case "A" -> 14;
            case "B" -> 79;
            case "C" -> 143;
            case "D" -> 208;
            case "E" -> 273;
            default -> 0;
        };
    }

    public int yCoords (String locatieY){
        return switch (locatieY) {
            case "1" -> 14;
            case "2" -> 79;
            case "3" -> 143;
            case "4" -> 208;
            case "5" -> 273;
            default -> 0;
        };
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

    public void addButtons(){
        JButton zendingLaden = new JButton("Zending laden");
        JButton zendingVersturen = new JButton("Zending versturen");

        midden.add(zendingLaden);
        midden.add(zendingVersturen);

        zendingVersturen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String orderID = db.getOrderID(); // krijgt orderID
                String[] productLocatie = db.getProductLocatie(orderID); // krijgt product locaties

                //Als de locatie overeenkomt met de case zet dan de background kleur op groen.
                for (int i = 0; i < productLocatie.length; i++) {
                    switch (productLocatie[i]) {
                        case "A1":
                            // do something
                            rechts.getGraphics().drawRect(xCoords("A"),yCoords("1"),65,65);
                            break;

                        case "A2":
                            // do something
                            break;

                        case "A3":
                            // do something
                            break;

                        case "A4":
                            // do something
                            break;

                        case "A5":
                            // do something
                            rechts.getGraphics().setColor(Color.green);
                            rechts.getGraphics().drawRect(xCoords("A"),yCoords("5"),2000,2000);
                            break;

                        case "B1":
                            // do something
                            break;

                        case "B2":
                            // do something
                            break;

                        case "B3":
                            // do something
                            break;

                        case "B4":
                            // do something
                            break;

                        case "B5":
                            // do something
                            break;

                        case "C1":
                            // do something
                            break;

                        case "C2":
                            // do something
                            break;

                        case "C3":
                            // do something
                            break;

                        case "C4":
                            // do something
                            break;

                        case "C5":
                            // do something
                            break;

                        case "D1":
                            // do something
                            break;

                        case "D2":
                            // do something
                            break;

                        case "D3":
                            // do something
                            break;

                        case "D4":
                            // do something
                            break;
                        case "D5":
                            // do something
                            break;

                        case "E1":
                            // do something
                            break;

                        case "E2":
                            // do something
                            break;

                        case "E3":
                            // do something
                            break;

                        case "E4":
                            // do something
                            break;

                        case "E5":
                            // do something
                            break;

                        default:
                            //maak hier nog een popup melding met ongeldige waarde
                            System.out.println("Er zijn geen zendingen beschikbaar");
                    }
                }
            }
        });

        zendingLaden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public int sendXLocation(int xCoords){
        int xCoord = xCoords;
        return xCoord;
    }

    public int sendYLocation(int yCoords){
        int yCoord = yCoords;
        return yCoord;
    }

    public void addDropdowns() {
    }

    public void loadSelectedOrders() {
    }

    public void addGridPanels() {

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


