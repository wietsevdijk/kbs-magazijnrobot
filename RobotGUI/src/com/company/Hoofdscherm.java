package com.company;
import org.w3c.dom.ls.LSOutput;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Arrays;
import java.util.Properties;

public class Hoofdscherm extends JFrame implements ActionListener {
    Database db = new Database();

    private JPanel leftPanel;
    private JPanel rightPanel;
    private JLabel gridLabel;
    private JLabel labelOrderID = new JLabel();
    private JButton versturen = new JButton("Zending Laden");
    private JButton compleet = new JButton("Zending Afmelden");
    private Panel p1 = new Panel();
    private Panel p2 = new Panel();
    private Panel p3 = new Panel();
    private Panel p4 = new Panel();
    private Panel p5 = new Panel();
    private Panel p6 = new Panel();
    private Panel p7 = new Panel();
    private Panel p8 = new Panel();
    private Panel p9 = new Panel();
    private Panel p10 = new Panel();
    private Panel p11= new Panel();
    private Panel p12 = new Panel();
    private Panel p13 = new Panel();
    private Panel p14 = new Panel();
    private Panel p15 = new Panel();
    private Panel p16 = new Panel();
    private Panel p17 = new Panel();
    private Panel p18 = new Panel();
    private Panel p19 = new Panel();
    private Panel p20 = new Panel();
    private Panel p21 = new Panel();
    private Panel p22 = new Panel();
    private Panel p23 = new Panel();
    private Panel p24 = new Panel();
    private Panel p25 = new Panel();

    public int circleX;
    public int circleY;

    public String url = "jdbc:mysql://localhost:3306/nerdyrobot";
    public String uname = "root";
    public String password = null;
    public String orderID = "";
    public String productID = "";
    public String[] productIDs = {"","",""};
    public String[] productLocatie = {"","",""};


    public Hoofdscherm() throws SQLException {
        addMainPanels();
        addFields();
        addStorageGrid();
        addScreen("Magazijn Robot", 1000, 550);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == versturen) {
            String orderID = db.getOrderID();
            String productID[] = db.getProductID(orderID);
            String productLocatie[] = db.getProductLocatie(productID);


            //NOG TOEVOEGEN:
            // heeft robot locatie bereikt maak background dan weer groen.
            // POPUP melding voor ongeldige waarde
            for (int i = 0; i < 3; i++) {
                switch (productLocatie[i]) {
                    case "11":
                        p1.setBackground(Color.blue);
                        circleX = p1.getX();
                        circleY = p1.getY();
                        repaint();
                        break;

                    case "21":
                        p2.setBackground(Color.blue);
                        int p2Xlocatie = p2.getX();
                        int p2Ylocatie = p2.getY();
                        repaint();
                        break;

                    case "31":
                        p3.setBackground(Color.blue);
                        int p3Xlocatie = p3.getX();
                        int p3Ylocatie = p3.getY();
                        repaint();
                        break;

                    case "41":
                        p4.setBackground(Color.blue);
                        int p4Xlocatie = p4.getX();
                        int p4Ylocatie = p4.getY();
                        repaint();
                        break;

                    case "51":
                        p5.setBackground(Color.blue);
                        int p5Xlocatie = p5.getX();
                        int p5Ylocatie = p5.getY();
                        repaint();
                        break;

                    case "12":
                        p6.setBackground(Color.blue);
                        int p6Xlocatie = p6.getX();
                        int p6Ylocatie = p6.getY();
                        repaint();
                        break;

                    case "22":
                        p7.setBackground(Color.blue);
                        int p7Xlocatie = p7.getX();
                        int p7Ylocatie = p7.getY();
                        repaint();
                        break;

                    case "32":
                        p8.setBackground(Color.blue);
                        int p8Xlocatie = p8.getX();
                        int p8Ylocatie = p8.getY();
                        repaint();
                        break;

                    case "42":
                        p9.setBackground(Color.blue);
                        int p9Xlocatie = p9.getX();
                        int p9Ylocatie = p9.getY();
                        repaint();
                        break;

                    case "52":
                        p10.setBackground(Color.blue);
                        int p10Xlocatie = p10.getX();
                        int p10Ylocatie = p10.getY();
                        repaint();
                        break;

                    case "13":
                        p11.setBackground(Color.blue);
                        int p11Xlocatie = p11.getX();
                        int p11Ylocatie = p11.getY();
                        repaint();
                        break;

                    case "23":
                        p12.setBackground(Color.blue);
                        int p12Xlocatie = p12.getX();
                        int p12Ylocatie = p12.getY();
                        repaint();
                        break;

                    case "33":
                        p13.setBackground(Color.blue);
                        int p13Xlocatie = p13.getX();
                        int p13Ylocatie = p13.getY();
                        repaint();
                        break;

                    case "43":
                        p14.setBackground(Color.blue);
                        int p14Xlocatie = p14.getX();
                        int p14Ylocatie = p14.getY();
                        repaint();
                        break;

                    case "53":
                        p15.setBackground(Color.blue);
                        int p15Xlocatie = p15.getX();
                        int p15Ylocatie = p15.getY();
                        repaint();
                        break;

                    case "14":
                        p16.setBackground(Color.blue);
                        int p16Xlocatie = p16.getX();
                        int p16Ylocatie = p16.getY();
                        repaint();
                        break;

                    case "24":
                        p17.setBackground(Color.blue);
                        int p17Xlocatie = p17.getX();
                        int p17Ylocatie = p17.getY();
                        repaint();
                        break;

                    case "34":
                        p18.setBackground(Color.blue);
                        int p18Xlocatie = p18.getX();
                        int p18Ylocatie = p18.getY();
                        repaint();
                        break;

                    case "44":
                        p19.setBackground(Color.blue);
                        int p19Xlocatie = p19.getX();
                        int p19Ylocatie = p19.getY();
                        repaint();
                        break;
                    case "54":
                        p20.setBackground(Color.blue);
                        int p20Xlocatie = p20.getX();
                        int p20Ylocatie = p20.getY();
                        repaint();
                        break;

                    case "15":
                        p21.setBackground(Color.blue);
                        int p21Xlocatie = p21.getX();
                        int p21Ylocatie = p21.getY();
                        repaint();
                        break;

                    case "25":
                        p22.setBackground(Color.blue);
                        int p22Xlocatie = p22.getX();
                        int p22Ylocatie = p22.getY();
                        repaint();
                        break;

                    case "35":
                        p23.setBackground(Color.blue);
                        int p23Xlocatie = p23.getX();
                        int p23Ylocatie = p23.getY();
                        repaint();
                        break;

                    case "45":
                        p24.setBackground(Color.blue);
                        int p24Xlocatie = p24.getX();
                        int p24Ylocatie = p24.getY();
                        repaint();
                        break;

                    case "55":
                        p25.setBackground(Color.blue);
                        int p25Xlocatie = p25.getX();
                        int p25Ylocatie = p25.getY();
                        repaint();
                        break;

                    default:
                        //maak hier nog een popup melding met ongeldige waarde
                        System.out.println("Er zijn geen zendingen beschikbaar");
                }
            }

        }

        if(e.getSource() == compleet){
            for (int i = 0; i < 3; i++) {
                switch (productLocatie[i]) {
                    case "11":
                        p1.setBackground(Color.green);
                        repaint();
                        break;

                    case "21":
                        p2.setBackground(Color.green);
                        repaint();
                        break;

                    case "31":
                        p3.setBackground(Color.green);
                        repaint();
                        break;

                    case "41":
                        p4.setBackground(Color.green);
                        repaint();
                        break;

                    case "51":
                        p5.setBackground(Color.green);
                        repaint();
                        break;

                    case "12":
                        p6.setBackground(Color.green);
                        repaint();
                        break;

                    case "22":
                        p7.setBackground(Color.green);
                        repaint();
                        break;

                    case "32":
                        p8.setBackground(Color.green);
                        repaint();
                        break;

                    case "42":
                        p9.setBackground(Color.green);
                        repaint();
                        break;

                    case "52":
                        p10.setBackground(Color.green);
                        repaint();
                        break;

                    case "13":
                        p11.setBackground(Color.green);
                        repaint();
                        break;

                    case "23":
                        p12.setBackground(Color.green);
                        repaint();
                        break;

                    case "33":
                        p13.setBackground(Color.green);
                        repaint();
                        break;

                    case "43":
                        p14.setBackground(Color.green);
                        repaint();
                        break;

                    case "53":
                        p15.setBackground(Color.green);
                        repaint();
                        break;

                    case "14":
                        p16.setBackground(Color.green);
                        repaint();
                        break;

                    case "24":
                        p17.setBackground(Color.green);
                        repaint();
                        break;

                    case "34":
                        p18.setBackground(Color.green);
                        repaint();
                        break;

                    case "44":
                        p19.setBackground(Color.green);
                        repaint();
                        break;
                    case "54":
                        p20.setBackground(Color.green);
                        repaint();
                        break;

                    case "15":
                        p21.setBackground(Color.green);
                        repaint();
                        break;

                    case "25":
                        p22.setBackground(Color.green);
                        repaint();
                        break;

                    case "35":
                        p23.setBackground(Color.green);
                        repaint();
                        break;

                    case "45":
                        p24.setBackground(Color.green);
                        repaint();
                        break;

                    case "55":
                        p25.setBackground(Color.green);
                        repaint();
                        break;

                    default:
                        //maak hier nog een popup melding met ongeldige waarde
                        System.out.println("Er zijn geen zendingen beschikbaar");
                }
            }
            //tetetete
            db.shipOrder(orderID);
        }


    }


    public int getCircleX() {
        return circleX;
    }

    public int getCircleY() {
        return circleY;
    }

    public void addStorageGrid(){
        p1.setBackground(Color.green);
        JLabel gridLabel1 = new JLabel();
        gridLabel1.setText("1.1");
        p1.add(gridLabel1);

        p2.setBackground(Color.green);
        JLabel gridLabel2 = new JLabel();
        gridLabel2.setText("2.1");
        p2.add(gridLabel2);

        p3.setBackground(Color.green);
        JLabel gridLabel3 = new JLabel();
        gridLabel3.setText("3.1");
        p3.add(gridLabel3);

        p4.setBackground(Color.green);
        JLabel gridLabel4 = new JLabel();
        gridLabel4.setText("4.1");
        p4.add(gridLabel4);

        p5.setBackground(Color.green);
        JLabel gridLabel5 = new JLabel();
        gridLabel5.setText("5.1");
        p5.add(gridLabel5);

        p6.setBackground(Color.green);
        JLabel gridLabel6 = new JLabel();
        gridLabel6.setText("1.2");
        p6.add(gridLabel6);

        p7.setBackground(Color.green);
        p8.setBackground(Color.green);
        p9.setBackground(Color.green);
        p10.setBackground(Color.green);

        p11.setBackground(Color.green);
        JLabel gridLabel11 = new JLabel();
        gridLabel11.setText("1.3");
        p11.add(gridLabel11);

        p12.setBackground(Color.green);
        p13.setBackground(Color.green);
        p14.setBackground(Color.green);
        p15.setBackground(Color.green);

        p16.setBackground(Color.green);
        JLabel gridLabel16 = new JLabel();
        gridLabel16.setText("1.4");
        p16.add(gridLabel16);

        p17.setBackground(Color.green);
        p18.setBackground(Color.green);
        p19.setBackground(Color.green);
        p20.setBackground(Color.green);

        p21.setBackground(Color.green);
        JLabel gridLabel21 = new JLabel();
        gridLabel21.setText("1.5");
        p21.add(gridLabel21);

        p22.setBackground(Color.green);
        p23.setBackground(Color.green);
        p24.setBackground(Color.green);
        p25.setBackground(Color.green);

        rightPanel.add(p1);
        rightPanel.add(p2);
        rightPanel.add(p3);
        rightPanel.add(p4);
        rightPanel.add(p5);
        rightPanel.add(p6);
        rightPanel.add(p7);
        rightPanel.add(p8);
        rightPanel.add(p9);
        rightPanel.add(p10);
        rightPanel.add(p11);
        rightPanel.add(p12);
        rightPanel.add(p13);
        rightPanel.add(p14);
        rightPanel.add(p15);
        rightPanel.add(p16);
        rightPanel.add(p17);
        rightPanel.add(p18);
        rightPanel.add(p19);
        rightPanel.add(p20);
        rightPanel.add(p21);
        rightPanel.add(p22);
        rightPanel.add(p23);
        rightPanel.add(p24);
        rightPanel.add(p25);
    }

    public void addMainPanels(){
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(230,500));
        leftPanel.setLayout(new FlowLayout());
        rightPanel.setPreferredSize(new Dimension(730,500));
        rightPanel.setBackground(Color.GRAY);
        GridLayout layout = new GridLayout(5,5);
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.gray, 10));
        layout.setHgap(10);
        layout.setVgap(10);
        rightPanel.setLayout(layout);
        add(leftPanel);
        add(rightPanel);
    }

    public void addFields(){
        leftPanel.add(labelOrderID);
        versturen.addActionListener(this);
        compleet.addActionListener(this);
        leftPanel.add(versturen);
        leftPanel.add(compleet);
    }

    //maakt je basis voor je scherm, stel zelf je layout hier in.
    public void addScreen(String title, int width, int height){
        this.setTitle(title);
        this.setSize(width,height);
        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
