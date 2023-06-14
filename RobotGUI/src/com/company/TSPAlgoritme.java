package com.company;

import java.util.ArrayList;

public class TSPAlgoritme {

    private static ArrayList<Coordinaat> begincoordinaten = new ArrayList<>();

    private static ArrayList<Coordinaat> volgorde = new ArrayList<>();

    public TSPAlgoritme() {
        if(begincoordinaten == null) {
        } else {
            begincoordinaten.clear();
        }
        if(volgorde == null) {
        } else {
            volgorde.clear();
        }
    }

    public static void TSPOplossing(int aantalCoordinaten, int breedte, int hoogte) {
        double starttijd = System.nanoTime();
        new TSPAlgoritme();

        for (int i = 0; i < aantalCoordinaten; i++) {
            Coordinaat coordinaat = new Coordinaat((int)(Math.random() * breedte) + 1, (int)(Math.random() * hoogte) + 1);
            TSPAlgoritme.setBegincoordinaat(coordinaat);
        }
        double oplossing = 0;
        int huidige_x = 1;
        int huidige_y = 1;
        System.out.println("Input: " + begincoordinaten);
        for(int i = 0; i < aantalCoordinaten; i++) {
            int index = 0;
            int dichtstbijzijndeIndex = 0;
            double kleinsteAfstand = -1;
            Coordinaat dichtstbijzijndeCoordinaat = new Coordinaat(1, 1);
            for(Coordinaat coordinaat: begincoordinaten) {
                int x_as = coordinaat.getX_as();
                int y_as = coordinaat.getY_as();
                double afstand = Math.sqrt(Math.pow((huidige_x - x_as), 2) + Math.pow((huidige_y - y_as), 2));
//                System.out.println(afstand);
                if(kleinsteAfstand == -1 || afstand < kleinsteAfstand) {
                    kleinsteAfstand = afstand;
                    dichtstbijzijndeCoordinaat = coordinaat;
                    dichtstbijzijndeIndex = index;
                }
                index++;
            }
            oplossing+=kleinsteAfstand;
            volgorde.add(dichtstbijzijndeCoordinaat);
            begincoordinaten.remove(dichtstbijzijndeIndex);
            huidige_x = dichtstbijzijndeCoordinaat.getX_as();
            huidige_y = dichtstbijzijndeCoordinaat.getY_as();
        }
        double eindtijd = System.nanoTime();
        System.out.println("Output: " + volgorde);
        System.out.println("De totale afstand is: " + oplossing + " vakjes");
        System.out.println("Verstreken tijd: " + ((eindtijd - starttijd) / 1000000) + " ms");
    }

    public static void setBegincoordinaat(Coordinaat coordinaat) {
        begincoordinaten.add(coordinaat);
    }

    public static ArrayList<Coordinaat> getBegincoordinaten() {
        return begincoordinaten;
    }

    public static ArrayList<Coordinaat> getVolgorde() {
        return volgorde;
    }
}