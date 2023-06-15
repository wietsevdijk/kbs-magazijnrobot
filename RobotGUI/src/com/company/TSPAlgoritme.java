package com.company;

import java.util.ArrayList;

public class TSPAlgoritme {

    private int aantalCoordinaten;
    private int breedte;
    private int hoogte;
    private double afstand;
    private double verstrekenTijd;
    private ArrayList<Coordinaat> begincoordinaten;
    private ArrayList<Coordinaat> volgorde;

    public TSPAlgoritme(int aantalCoordinaten, int breedte, int hoogte) {
        begincoordinaten = new ArrayList<>();
        volgorde = new ArrayList<>();
        this.aantalCoordinaten = aantalCoordinaten;
        this.breedte = breedte;
        this.hoogte = hoogte;
        afstand = 0;
        verstrekenTijd = 0;
        TSPOplossing();
    }

    public void TSPOplossing() {
        double starttijd = System.nanoTime();
        voegRandomCoordinatenToe();
        System.out.println("Input: " + begincoordinaten);
        zetInJuisteVolgorde();
        double eindtijd = System.nanoTime();
        verstrekenTijd = (eindtijd - starttijd) / 1000000;
        System.out.println("Output: " + volgorde);
        System.out.println("De totale afstand is: " + afstand + " vakjes");
        System.out.println("Verstreken tijd: " + verstrekenTijd + " ms");
    }

    public void voegRandomCoordinatenToe() {
        for (int i = 0; i < aantalCoordinaten; i++) {
            Coordinaat coordinaat = new Coordinaat((int)(Math.random() * breedte) + 1, (int)(Math.random() * hoogte) + 1);
            begincoordinaten.add(coordinaat);
        }
    }

    public void zetInJuisteVolgorde() {
        int huidige_x = 1;
        int huidige_y = 1;
        for(int i = 0; i < aantalCoordinaten; i++) {
            int index = 0;
            int dichtstbijzijndeIndex = 0;
            double kleinsteAfstand = -1;
            Coordinaat dichtstbijzijndeCoordinaat = new Coordinaat(1, 1);
            for(Coordinaat coordinaat: begincoordinaten) {
                int x_as = coordinaat.getX_as();
                int y_as = coordinaat.getY_as();
                double afstand = Math.sqrt(Math.pow((huidige_x - x_as), 2) + Math.pow((huidige_y - y_as), 2));
                if(kleinsteAfstand == -1 || afstand < kleinsteAfstand) {
                    kleinsteAfstand = afstand;
                    dichtstbijzijndeCoordinaat = coordinaat;
                    dichtstbijzijndeIndex = index;
                }
                index++;
            }
            afstand+=kleinsteAfstand;
            volgorde.add(dichtstbijzijndeCoordinaat);
            begincoordinaten.remove(dichtstbijzijndeIndex);
            huidige_x = dichtstbijzijndeCoordinaat.getX_as();
            huidige_y = dichtstbijzijndeCoordinaat.getY_as();
        }
    }

    public ArrayList<Coordinaat> getBegincoordinaten() {
        return begincoordinaten;
    }

    public ArrayList<Coordinaat> getVolgorde() {
        return volgorde;
    }

    public double getAfstand() {
        return afstand;
    }

    public double getVerstrekenTijd() {
        return verstrekenTijd;
    }
}