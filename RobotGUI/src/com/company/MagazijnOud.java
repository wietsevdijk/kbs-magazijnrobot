package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class MagazijnOud extends JFrame implements ActionListener {
    Database db = new Database();

    //panels aanmaken
    JLabel gridLabel;
    JButton versturen = new JButton("Zending Laden");
    JButton compleet = new JButton("Zending Afmelden");
    Panel p1 = new Panel();
    Panel p2 = new Panel();
    Panel p3 = new Panel();
    Panel p4 = new Panel();
    Panel p5 = new Panel();
    Panel p6 = new Panel();
    Panel p7 = new Panel();
    Panel p8 = new Panel();
    Panel p9 = new Panel();
    Panel p10 = new Panel();
    Panel p11 = new Panel();
    Panel p12 = new Panel();
    Panel p13 = new Panel();
    Panel p14 = new Panel();
    Panel p15 = new Panel();
    Panel p16 = new Panel();
    Panel p17 = new Panel();
    Panel p18 = new Panel();
    Panel p19 = new Panel();
    Panel p20 = new Panel();
    Panel p21 = new Panel();
    Panel p22 = new Panel();
    Panel p23 = new Panel();
    Panel p24 = new Panel();
    Panel p25 = new Panel();


    public MagazijnOud(JPanel links, JPanel rechts, JPanel midden) throws SQLException {
        //setup panels
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

        //panels toevoegen
        rechts.add(p1);
        rechts.add(p2);
        rechts.add(p3);
        rechts.add(p4);
        rechts.add(p5);
        rechts.add(p6);
        rechts.add(p7);
        rechts.add(p8);
        rechts.add(p9);
        rechts.add(p10);
        rechts.add(p11);
        rechts.add(p12);
        rechts.add(p13);
        rechts.add(p14);
        rechts.add(p15);
        rechts.add(p16);
        rechts.add(p17);
        rechts.add(p18);
        rechts.add(p19);
        rechts.add(p20);
        rechts.add(p21);
        rechts.add(p22);
        rechts.add(p23);
        rechts.add(p24);
        rechts.add(p25);

        midden.add(versturen);
        midden.add(compleet);

        //Luister wanneer er op de zending afmelden knop wordt gedruikt
        compleet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //als de locaties overeenkomen met de case dan wordt de grid weer terug op groen gezet
                String orderID = db.getOrderID(); // krijgt orderID
                String[] productLocatie = db.getProductLocatie(orderID); // krijgt product locaties
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

                //Zet de order in de database op compleet zodat hij uit de lijst wordt gehaald
                db.shipOrder(orderID);
                repaint();
            }
        });

        //Luister wanneer er op de nieuwe zending laden knop wordt gedrukt
        versturen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String orderID = db.getOrderID(); // krijgt orderID
                String[] productLocatie = db.getProductLocatie(orderID); // krijgt product locaties

                //Als de locatie overeenkomt met de case zet dan de background kleur op groen.
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
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
