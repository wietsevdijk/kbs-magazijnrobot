package com.company;

import java.util.ArrayList;

public class TSPAlgoritme {

    private int aantalCoordinaten;
    private int breedte;
    private int hoogte;
    private double afstand;
    private double verstrekenTijd;
    private ArrayList<Coordinaat> begincoordinaten;
    private ArrayList<Coordinaat> overigecoordinaten;
    private ArrayList<Coordinaat> volgorde;
    boolean random;

    public TSPAlgoritme(int breedte, int hoogte, int aantalCoordinaten) {
        begincoordinaten = new ArrayList<>();
        overigecoordinaten = new ArrayList<>();
        volgorde = new ArrayList<>();
        this.aantalCoordinaten = aantalCoordinaten;
        this.breedte = breedte;
        this.hoogte = hoogte;
        afstand = 0;
        verstrekenTijd = 0;
        random = true;
        volgorde = TSPOplossing();
    }

    public TSPAlgoritme(int breedte, int hoogte, ArrayList<Coordinaat> coordinaten) {
        begincoordinaten = new ArrayList<>();
        begincoordinaten.addAll(coordinaten);
        overigecoordinaten = new ArrayList<>();
        overigecoordinaten.addAll(coordinaten);
        volgorde = new ArrayList<>();
        aantalCoordinaten = coordinaten.size();
        this.breedte = breedte;
        this.hoogte = hoogte;
        afstand = 0;
        verstrekenTijd = 0;
        random = false;
        volgorde = TSPOplossing();
    }

    public ArrayList<Coordinaat> TSPOplossing() {
        double starttijd = System.nanoTime();
        ArrayList<Coordinaat> juisteVolgorde;
        if(random) {
            voegRandomCoordinatenToe();
        }
        juisteVolgorde = zetInJuisteVolgorde();
        double eindtijd = System.nanoTime();
        verstrekenTijd = (eindtijd - starttijd) / 1000000;
        return juisteVolgorde;
    }

    public void voegRandomCoordinatenToe() {
        for (int i = 0; i < aantalCoordinaten; i++) {
            Coordinaat coordinaat = new Coordinaat((int)(Math.random() * breedte) + 1, (int)(Math.random() * hoogte) + 1);
            begincoordinaten.add(coordinaat);
            overigecoordinaten.add(coordinaat);
        }
    }

    public ArrayList<Coordinaat> zetInJuisteVolgorde() {
        ArrayList<Coordinaat> juisteVolgorde = new ArrayList<>();
        int huidige_x = 1;
        int huidige_y = 1;
        for(int i = 0; i < aantalCoordinaten; i++) {
            int index = 0;
            int dichtstbijzijndeIndex = 0;
            double kleinsteAfstand = -1;
            Coordinaat dichtstbijzijndeCoordinaat = new Coordinaat(1, 1);
            for(Coordinaat coordinaat: overigecoordinaten) {
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
            juisteVolgorde.add(dichtstbijzijndeCoordinaat);
            overigecoordinaten.remove(dichtstbijzijndeIndex);
            huidige_x = dichtstbijzijndeCoordinaat.getX_as();
            huidige_y = dichtstbijzijndeCoordinaat.getY_as();
        }
        return juisteVolgorde;
    }

    public ArrayList<Coordinaat> getVolgorde() {
        return volgorde;
    }

    public String toString() {
        return  "Input: " + begincoordinaten + "\n" +
                "Output: " + volgorde + "\n" +
                "De totale afstand is: " + afstand + " vakjes" + "\n" +
                "Verstreken tijd: " + verstrekenTijd + " ms";
    }
}