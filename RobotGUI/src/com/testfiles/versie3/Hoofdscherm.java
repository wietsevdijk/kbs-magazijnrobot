package com.testfiles.versie3;
import org.w3c.dom.ls.LSOutput;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Arrays;
import java.util.Properties;

public class Hoofdscherm extends JFrame implements ActionListener {

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

            //get JDBC driver
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }

            //get connection and perform sql statement
            try {
                Connection con = DriverManager.getConnection(url, uname, password);
                Statement statement = con.createStatement();
                String query = "SELECT orderID FROM orders WHERE pickingCompleet = 0 LIMIT 1";
                ResultSet result = statement.executeQuery(query);
                while (result.next()) {
                    orderID = result.getString(1);
                    labelOrderID.setText("OrderID: " + orderID);
                    System.out.println("OrderID: " + orderID);
                }
                repaint();
                con.close();
                statement.close();
                result.close();
            } catch (
                    SQLException ex) {
                ex.printStackTrace();
            }


            try {
                Connection con = DriverManager.getConnection(url, uname, password);
                Statement statement = con.createStatement();
                String query2 = "SELECT productID FROM orderRegels WHERE orderID = " + orderID;
                ResultSet result2 = statement.executeQuery(query2);
                for (int i = 0; i < 3; i++) {
                    result2.next();
                    productIDs[i] = result2.getString(1);
                }
                System.out.println("productID: " + Arrays.toString(productIDs));
                con.close();
                statement.close();
                result2.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            //NOG TOEVOEGEN:
            // heeft robot locatie bereikt maak background dan weer groen.
            // POPUP melding voor ongeldige waarde
            for (int i = 0; i < 3; i++) {
                switch (productLocatie[i]) {
                    case "11":
                        p1.setBackground(Color.blue);
                        repaint();
                        break;

                    case "21":
                        p2.setBackground(Color.blue);
                        repaint();
                        break;

                    case "31":
                        p3.setBackground(Color.blue);
                        repaint();
                        break;

                    case "41":
                        p4.setBackground(Color.blue);
                        repaint();
                        break;

                    case "51":
                        p5.setBackground(Color.blue);
                        repaint();
                        break;

                    case "12":
                        p6.setBackground(Color.blue);
                        repaint();
                        break;

                    case "22":
                        p7.setBackground(Color.blue);
                        repaint();
                        break;

                    case "32":
                        p8.setBackground(Color.blue);
                        repaint();
                        break;

                    case "42":
                        p9.setBackground(Color.blue);
                        repaint();
                        break;

                    case "52":
                        p10.setBackground(Color.blue);
                        repaint();
                        break;

                    case "13":
                        p11.setBackground(Color.blue);
                        repaint();
                        break;

                    case "23":
                        p12.setBackground(Color.blue);
                        repaint();
                        break;

                    case "33":
                        p13.setBackground(Color.blue);
                        repaint();
                        break;

                    case "43":
                        p14.setBackground(Color.blue);
                        repaint();
                        break;

                    case "53":
                        p15.setBackground(Color.blue);
                        repaint();
                        break;

                    case "14":
                        p16.setBackground(Color.blue);
                        repaint();
                        break;

                    case "24":
                        p17.setBackground(Color.blue);
                        repaint();
                        break;

                    case "34":
                        p18.setBackground(Color.blue);
                        repaint();
                        break;

                    case "44":
                        p19.setBackground(Color.blue);
                        repaint();
                        break;
                    case "54":
                        p20.setBackground(Color.blue);
                        repaint();
                        break;

                    case "15":
                        p21.setBackground(Color.blue);
                        repaint();
                        break;

                    case "25":
                        p22.setBackground(Color.blue);
                        repaint();
                        break;

                    case "35":
                        p23.setBackground(Color.blue);
                        repaint();
                        break;

                    case "45":
                        p24.setBackground(Color.blue);
                        repaint();
                        break;

                    case "55":
                        p25.setBackground(Color.blue);
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
            try {
                Connection con = DriverManager.getConnection(url, uname, password);
                Statement statement = con.createStatement();
                String query = "UPDATE orders " + "SET pickingCompleet = 1 WHERE orderID = " + orderID + " AND (pickingCompleet=0)";
                statement.executeUpdate(query);
                con.close();
                statement.close();
            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }


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
