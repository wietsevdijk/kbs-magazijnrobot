package com.testfiles.versie2;

import javax.swing.*;
import java.awt.*;

public class GridTekenPanel extends JPanel {
    public GridTekenPanel(){
        setBackground(new Color(200,200,200));
        setPreferredSize(new Dimension(1000, 1000));
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        int width = getWidth();
        int height = getHeight();
        super.paintComponent(g);

        int xstart;

        for(int i = 1; i <= 5; i++)
        {
            xstart = i*(width/5);
            g.drawLine(xstart, 0, xstart, height);
        }

        int ystart;

        for(int i = 1; i <= 5; i++)
        {
            ystart = i*(height/5);
            g.drawLine(0, ystart, width, ystart );
        }
    }
}
