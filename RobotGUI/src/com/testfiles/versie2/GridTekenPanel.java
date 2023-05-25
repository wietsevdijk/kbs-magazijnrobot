package com.testfiles.versie2;

import com.testfiles.versie2.onderdelen.Magazijnplek;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GridTekenPanel extends JPanel {

    private boolean isTSPLine;
    private int maxY;
    private int maxX;

    private ArrayList<Magazijnplek> plekkies;

    public GridTekenPanel(int aantalX, int aantalY){
        maxX = aantalX;
        maxY = aantalY;

        plekkies = new ArrayList<>(aantalX * aantalY);

        setBackground(Color.black);
        setPreferredSize(new Dimension(1000, 1000));
    }

    public void setTSPLine(boolean TSPLine) {
        isTSPLine = TSPLine;
    }

    public boolean isTSPLine() {
        return isTSPLine;
    }

    public int berekenXpixel(int arrayIndex, int xsize) {
        return plekkies.get(arrayIndex).getPositie().x + (xsize / 2);}
    public int berekenYpixel(int arrayIndex, int ysize) {
        return plekkies.get(arrayIndex).getPositie().y + (ysize / 2);}

    @Override
    protected void paintComponent(Graphics g)
    {
        int width = getWidth();
        int height = getHeight();

        int ysize = (height / maxY);
        int xsize = (width / maxX);
        int ystart;
        int xstart;

        super.paintComponent(g);

        for(int y = 1; y <= maxY; y++) {

            ystart = (y * ysize) - ysize;

            for (int x = 1; x <= maxX; x++) {

                xstart = (x * xsize) - xsize;

                if(x % 2 != 0 && y % 2 != 0) {
                    g.setColor(Color.gray);
                }
                else if(x % 2 == 0 && y % 2 == 0) {
                    g.setColor(Color.gray);
                }
                else {
                    g.setColor(Color.darkGray);
                }

                Magazijnplek plekkie = new Magazijnplek(x, y, new Point(xstart, ystart));
//                System.out.println(plekkie.getX() +
//                                    ", " + plekkie.getY() +
//                                    ", " + plekkie.getPositie());
                plekkies.add(plekkie);

                g.fillRect(xstart, ystart, xsize, ysize);
            }
        }

        if (isTSPLine()) {

            int YPixelOld = 900;
            int XPixelOld = 100;

            int YPixel = berekenYpixel(getRandomNumber(0,24), ysize);
            int XPixel = berekenXpixel(getRandomNumber(0,24), xsize);

            g.setColor(Color.RED);
            g.setFont(new Font("Courier New", Font.BOLD, 50));

            for(int i = 1; i <= 5; i++) {

                g.drawString("" + (i-1), XPixelOld - 10, YPixelOld - 10);
                g.drawLine(XPixelOld, YPixelOld, XPixel, YPixel);

                YPixelOld = YPixel;
                XPixelOld = XPixel;

                YPixel = berekenYpixel(getRandomNumber(0,24), ysize);
                XPixel = berekenXpixel(getRandomNumber(0,24), xsize);

            }

        }
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min); //Nodig voor actionperformed
    }
}
