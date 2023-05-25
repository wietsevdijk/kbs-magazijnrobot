package com.testfiles.versie2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonInterface extends JFrame implements ActionListener {

    public ButtonInterface(JPanel panel) {

        JPanel linksdivision = new JPanel();
        linksdivision.setLayout(new BoxLayout(linksdivision, BoxLayout.PAGE_AXIS));
        addButtons(linksdivision);

        panel.add(linksdivision);

    }

    public void addButtons(JPanel panel) {

        JButton TSPknop1 = new JButton("Genereer coordinaten");
        JComboBox<String> TSPbox1 = new JComboBox<String>(new String[]{"None chosen", "Nearest Neighbor", "Brute Force"});

        TSPknop1.setMaximumSize(new Dimension(200, 50));
        TSPbox1.setMaximumSize(new Dimension(200, 50));

        ActionListener TSPaction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(e.getSource() == TSPknop1) {

                    JLabel coords;

                    if (TSPknop1.getModel().isPressed()) {

                        int vak1 = getRandomNumber(1, 25);
                        int vak2 = getRandomNumber(1, 25);
                        int vak3 = getRandomNumber(1, 25);

                        coords = new JLabel("" + vak1 + ", " + vak2 + ", " + vak3);

                    }
                    else {

                        coords = new JLabel("Geen coords");

                    }

                    panel.add(coords);
                }
            }
        };

        TSPknop1.addActionListener(TSPaction);

        panel.add(TSPknop1);
        panel.add(TSPbox1);

    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min); //Nodig voor actionperformed
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
