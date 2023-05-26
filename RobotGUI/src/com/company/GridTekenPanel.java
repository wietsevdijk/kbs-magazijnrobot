package com.company;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GridTekenPanel extends JPanel {

    private boolean isTSPLine;
    private int maxY;
    private int maxX;
    public int vak1;
    public int vak2;
    public int vak3;



    private ArrayList<Magazijnplek> plekkies;

    public GridTekenPanel(int aantalX, int aantalY) {
        maxX = aantalX;
        maxY = aantalY;

        plekkies = new ArrayList<>(aantalX * aantalY);

        setBackground(Color.black);
        setPreferredSize(new Dimension(450, 450));
    }

    public void setTSPLine(boolean TSPLine) {
        isTSPLine = TSPLine;
    }

    public boolean isTSPLine() {
        return isTSPLine;
    }

    public int berekenXpixel(int arrayIndex, int xsize) {
        return plekkies.get(arrayIndex).getPositie().x + (xsize / 2);
    }

    public int berekenYpixel(int arrayIndex, int ysize) {
        return plekkies.get(arrayIndex).getPositie().y + (ysize / 2);
    }

    @Override
    protected void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        int ysize = (height / maxY);
        int xsize = (width / maxX);
        int ystart;
        int xstart;

        super.paintComponent(g);

        for (int y = 1; y <= maxY; y++) {

            ystart = (y * ysize) - ysize;

            for (int x = 1; x <= maxX; x++) {

                xstart = (x * xsize) - xsize;

                if (x % 2 != 0 && y % 2 != 0) {
                    g.setColor(Color.gray);
                } else if (x % 2 == 0 && y % 2 == 0) {
                    g.setColor(Color.gray);
                } else {
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

                int YPixel1 = berekenYpixel(vak1, ysize);
                int XPixel1 = berekenXpixel(vak1, xsize);

                int YPixel2 = berekenYpixel(vak2, ysize);
                int XPixel2 = berekenXpixel(vak2, xsize);

                int YPixel3 = berekenYpixel(vak3, ysize);
                int XPixel3 = berekenXpixel(vak3, xsize);




            g.setColor(Color.RED);
            g.setFont(new Font("Courier New", Font.BOLD, 50));


                g.drawString("1" , XPixel1 - 10, YPixel1 - 10);
                g.drawLine(XPixel1, YPixel1, XPixel2, YPixel2);
                g.drawString("2", XPixel2 -10, YPixel2 - 10);

                g.drawString("3", XPixel3 - 10, YPixel3 - 10);
                g.drawLine(XPixel2, YPixel2, XPixel3, YPixel3);






        repaint();
            revalidate();
        }
    }

    public int getVak1() {
        return vak1;
    }

    public void setVak1(int vak1) {
        this.vak1 = vak1;
    }

    public int getVak2() {
        return vak2;
    }

    public void setVak2(int vak2) {
        this.vak2 = vak2;
    }


    public int getVak3() {
        return vak3;
    }

    public void setVak3(int vak3) {
        this.vak3 = vak3;
    }

}