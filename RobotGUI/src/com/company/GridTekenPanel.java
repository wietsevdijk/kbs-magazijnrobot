package com.company;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GridTekenPanel extends JPanel {

    private boolean isTSPLine;
    private boolean TSPtest;
    private ArrayList<Coordinaat> coordinaten;
    private int maxY;
    private int maxX;
    public int vak1;
    public int vak2;
    public int vak3;



    private ArrayList<Magazijnplek> plekken;

    //Teken een door jouw gedefineerde grid
    public GridTekenPanel(int aantalX, int aantalY, int breedte, int hoogte) {
        maxX = aantalX;
        maxY = aantalY;

        plekken = new ArrayList<>(aantalX * aantalY);

        setPreferredSize(new Dimension(breedte, hoogte));
    }

    public GridTekenPanel(int aantalX, int aantalY, int breedte, int hoogte, ArrayList<Coordinaat> coordinaten) {
        this.coordinaten = new ArrayList<>();
        this.coordinaten.addAll(coordinaten);
        maxX = aantalX;
        maxY = aantalY;

        plekken = new ArrayList<>(aantalX * aantalY);

        setPreferredSize(new Dimension(breedte, hoogte));
    }

    //Berekent afstand tussen 2 locaties in de grid
    public double distanceTo(int currentX, int currentY, int xLocation, int yLocation){
        int xDistance = Math.abs(currentX) - xLocation;
        int yDistance = Math.abs(currentY) - yLocation;

        double distance = Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));

        return distance;
    }

    public void setTSPLine(boolean TSPLine) {
        isTSPLine = TSPLine;
    }

    public void setTSPtest(boolean TSPtest) {
        this.TSPtest = TSPtest;
    }

    public boolean isTSPLine() {
        return isTSPLine;
    }

    //Bereken pixel locatie van array index naar pixel waarde
    public int berekenXpixel(int arrayIndex, int xsize) {
        return plekken.get(arrayIndex).getPositie().x + (xsize / 2);
    }

    //Bereken pixel locatie van array index naar pixel waarde
    public int berekenYpixel(int arrayIndex, int ysize) {
        return plekken.get(arrayIndex).getPositie().y + (ysize / 2);
    }

    @Override
    protected void paintComponent(Graphics g) {
        int ystart;
        int xstart;
        int width = getWidth();
        int height = getHeight();
        int ysize = height / maxY;
        int xsize = width / maxX;

        super.paintComponent(g);

        //Voegt grid toe
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

                Magazijnplek plek = new Magazijnplek(x, y, new Point(xstart, ystart));
                plekken.add(plek);

                g.fillRect(xstart, ystart, xsize, ysize);
            }
        }

        if(TSPtest) {
            g.setColor(Color.ORANGE);
            g.setFont(new Font("Courier New", Font.BOLD, 30));
            int maxXpixel = (xsize * maxX) - 10;
            int maxYpixel = (ysize * maxY) + 1;
            int vorigeX = xsize / 2;
            int vorigeY = maxYpixel - (ysize / 2);
            for(Coordinaat coordinaat: coordinaten) {
                int x_positie = ((maxXpixel / maxX) * coordinaat.getX_as()) - (xsize / 2);
                int y_positie = (((maxYpixel / maxY) * coordinaat.getY_as()) - (ysize / 2));
                if(x_positie < xsize * 9) {
                    int correctie = xsize * 9 - x_positie;
                    x_positie = x_positie - (int)(correctie * (maxX * 0.002));
                } else {
                    int correctie = x_positie - xsize * 9;
                    x_positie = x_positie + (int)(correctie * (maxX * 0.002));
                }
                g.drawString(".", x_positie, y_positie);
                g.drawLine(vorigeX, vorigeY, x_positie + 9, y_positie - 2);
                vorigeX = x_positie + 9;
                vorigeY = y_positie - 2;
            }
            g.setColor(Color.GREEN);
            g.setFont(new Font("Courier New", Font.BOLD, 50));
            g.drawString(".", ((maxXpixel / maxX) - (xsize / 2)) - 15, maxYpixel - (((maxYpixel / maxY)- (ysize / 2))) + 1);
        }

        if (isTSPLine()) {
            //Aantal locatie om te bezoeken (3 locaties + home)
            int locations = 4;

                //Bereken locatie van product 1
                int YPixel1 = berekenYpixel(vak1, ysize);
                int XPixel1 = berekenXpixel(vak1, xsize);

                //Bereken locatie van product 2
                int YPixel2 = berekenYpixel(vak2, ysize);
                int XPixel2 = berekenXpixel(vak2, xsize);

                //Bereken locatie van product 3
                int YPixel3 = berekenYpixel(vak3, ysize);
                int XPixel3 = berekenXpixel(vak3, xsize);




            g.setColor(Color.RED);
            g.setFont(new Font("Courier New", Font.BOLD, 50));

            //algoritme (niet scaleable)

            int currentPosX = startPositieX(20); //Start bij Home update later naar locatie van laatste product
            int currentPosY = startPositieY(20); //Start bij Home update later naar locatie van laatste product

            boolean visitedP1 = false; //Om te checken of product 1 al bezocht is
            boolean visitedP2 = false; //Om te checken of product 2 al bezocht is
            boolean visitedP3 = false; //Om te checken of product 3 al bezocht is

            //Waardes om de afstand tussen producten uit te rekenen
            double distance1 = 0;
            double distance2 = 0;
            double distance3 = 0;

            //Waardes om de kortste route te selecteren
            double shortest1 = 0;
            double shortest2 = 0;
            double shortest3 = 0;


            //algoritme om pad te tekenen (Nog compacter maken / scalable)
                for(int i = 1; i < locations; i++){
                    //distance from start to pixel1
                    distance1 = distanceTo(currentPosX, currentPosY, XPixel1, YPixel1);

                    //distance from start to pixel2
                    distance2 = distanceTo(currentPosX, currentPosY, XPixel2, YPixel2);

                    //distance from start to pixel3
                    distance3 = distanceTo(currentPosX, currentPosY, XPixel3, YPixel3);

                        if(i == 1) {
                            //pick shortest distance
                            shortest1 = Math.min(Math.min(distance1, distance2), distance3);

                            if (shortest1 == distance1 && !visitedP1) {
                                //draw line
                                g.drawString("1", XPixel1 - 10, YPixel1 - 10);
                                g.drawLine(startPositieX(20), startPositieY(20), XPixel1, YPixel1);
                                currentPosX = XPixel1;
                                currentPosY = YPixel1;
                                visitedP1 = true;
                            }

                            if (shortest1 == distance2 && !visitedP2) {
                                //draw line
                                g.drawString("1", XPixel2 - 10, YPixel2 - 10);
                                g.drawLine(startPositieX(20), startPositieY(20), XPixel2, YPixel2);
                                currentPosX = XPixel2;
                                currentPosY = YPixel2;
                                visitedP2 = true;
                            }

                            if (shortest1 == distance3 && !visitedP3) {
                                //draw line
                                g.drawString("1", XPixel3 - 10, YPixel3 - 10);
                                g.drawLine(startPositieX(20), startPositieY(20), XPixel3, YPixel3);
                                currentPosX = XPixel3;
                                currentPosY = YPixel3;
                                visitedP3 = true;
                            }
                            i++;
                        }

                        if (i == 2){
                            //distance from last position to pixel1
                            if(!visitedP1) {
                                 distance1 = distanceTo(currentPosX, currentPosY, XPixel1, YPixel1);
                            }

                            //distance from last position to pixel2
                            if(!visitedP2) {
                                 distance2 = distanceTo(currentPosX, currentPosY, XPixel2, YPixel2);
                            }

                            //distance from last position to pixel3
                            if(!visitedP3) {
                                 distance3 = distanceTo(currentPosX, currentPosY, XPixel3, YPixel3);
                            }

                            //bereken kortste route bij onbezochte producten
                            if(!visitedP1 && !visitedP2){
                                shortest2 = Math.min(distance1, distance2);
                            }

                            //bereken kortste route bij onbezochte producten
                            if(!visitedP2 && !visitedP3){
                                shortest2 = Math.min(distance2, distance3);
                            }

                            //bereken kortste route bij onbezochte producten
                            if(!visitedP1 && !visitedP3){
                                shortest2 = Math.min(distance1, distance3);
                            }

                            //Als dit de kortste route is en deze nog niet bezocht is teken dan een lij
                            if(shortest2 == distance1 && !visitedP1){
                                g.drawString("2", XPixel1 - 10, YPixel1 - 10);
                                g.drawLine(currentPosX, currentPosY, XPixel1, YPixel1);
                                currentPosX = XPixel1;
                                currentPosY = YPixel1;
                                visitedP1 = true;
                            }

                            //Als dit de kortste route is en deze nog niet bezocht is teken dan een lij
                            if(shortest2 == distance2 && !visitedP2){
                                g.drawString("2", XPixel2 - 10, YPixel2 - 10);
                                g.drawLine(currentPosX, currentPosY, XPixel2, YPixel2);
                                currentPosX = XPixel2;
                                currentPosY = YPixel2;
                                visitedP2 = true;
                            }

                            //Als dit de kortste route is en deze nog niet bezocht is teken dan een lij
                            if(shortest2 == distance3 && !visitedP3){
                                g.drawString("2", XPixel3 - 10, YPixel3 - 10);
                                g.drawLine(currentPosX, currentPosY, XPixel3, YPixel3);
                                currentPosX = XPixel3;
                                currentPosY = YPixel3;
                                visitedP3 = true;
                            }
                            i++;
                        }

                        if(i == 3){
                            //dit is de laatste waarde dus de afstand hoeft niet echt berekent te worden want er is maar een route

                            //Kijk of product al bezocht is, zo niet teken een lijn
                            if(!visitedP1){
                                g.drawString("3", XPixel1 - 10, YPixel1 - 10);
                                g.drawLine(currentPosX, currentPosY, XPixel1, YPixel1);
                                currentPosX = XPixel1;
                                currentPosY = YPixel1;
                                visitedP1 = true;
                            }

                            //Kijk of product al bezocht is, zo niet teken een lijn
                            if(!visitedP2){
                                g.drawString("3", XPixel2 - 10, YPixel2 - 10);
                                g.drawLine(currentPosX, currentPosY, XPixel2, YPixel2);
                                currentPosX = XPixel2;
                                currentPosY = YPixel2;
                                visitedP2 = true;
                            }

                            //Kijk of product al bezocht is, zo niet teken een lijn
                            if(!visitedP3){
                                g.drawString("3", XPixel3 - 10, YPixel3 - 10);
                                g.drawLine(currentPosX, currentPosY, XPixel3, YPixel3);
                                currentPosX = XPixel3;
                                currentPosY = YPixel3;
                                visitedP3 = true;
                            }
                            i++;
                        }


                        if(i == 4){
                            //Zodra alle positie zijn bezocht teken een lijn naar de startlocatie (Home)
                            if(visitedP1 && visitedP2 && visitedP3) {
                                g.drawLine(currentPosX, currentPosY, startPositieX(20), startPositieY(20));
                            }
                        }

                    }
                }
        repaint();
            revalidate();
        }



    //berekent door jouw opgegeven start positie (0,24)
    public int startPositieX(int positie){
        int xsize = (getWidth() / maxX);

        int startpositie = positie;
        int startpositieX = berekenXpixel(20,xsize);

        return startpositieX;
    }

    //berekent door jouw opgegeven start positie (0,24)
    public int  startPositieY(int positie){
        int ysize = (getHeight() / maxY);
        int startpositie = positie;
        int startpositieY = berekenYpixel(20, ysize);

        return startpositieY;
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