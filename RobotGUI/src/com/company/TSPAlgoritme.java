package com.company;

import java.util.ArrayList;

public class TSPAlgoritme {

    public static void main(String[] args) {
        TSPAlgoritme algoritme = new TSPAlgoritme();

        for (int i = 0; i < 30; i++) {
            Coordinaat coordinaat = new Coordinaat((int)(Math.random() * 50) + 1, (int)(Math.random() * 50) + 1);
            TSPAlgoritme.setBegincoordinaat(coordinaat);
        }
        algoritme.geoptimaliseerdPad();
    }


    private static ArrayList<Coordinaat> begincoordinaten;

    private static ArrayList<Coordinaat> volgorde;

    public TSPAlgoritme() {
        if(begincoordinaten == null) {
            begincoordinaten = new ArrayList<>();
        } else {
            begincoordinaten.clear();
        }
        if(volgorde == null) {
            volgorde = new ArrayList<>();
        } else {
            volgorde.clear();
        }
    }

    public void geoptimaliseerdPad() {
        long starttijd = System.nanoTime();
        double solution = 0;
        int huidige_x = 1;
        int huidige_y = 1;
        int aantalCoordinaten = begincoordinaten.size();
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
            solution+=kleinsteAfstand;
            volgorde.add(dichtstbijzijndeCoordinaat);
            begincoordinaten.remove(dichtstbijzijndeIndex);
            huidige_x = dichtstbijzijndeCoordinaat.getX_as();
            huidige_y = dichtstbijzijndeCoordinaat.getY_as();
        }
        long eindtijd = System.nanoTime();
        System.out.println("Output: " + volgorde);
        System.out.println("De totale afstand is: " + solution + " vakjes");
        System.out.println("Verstreken tijd: " + ((eindtijd - starttijd) / 1000000) + " ms");
    }

    public static void setBegincoordinaat(Coordinaat coordinaat) {
        begincoordinaten.add(coordinaat);
    }

    public static ArrayList<Coordinaat> getBegincoordinaten() {
        return begincoordinaten;
    }
}