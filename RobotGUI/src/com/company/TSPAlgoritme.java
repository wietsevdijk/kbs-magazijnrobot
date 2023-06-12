package com.company;

import java.util.ArrayList;

public class TSPAlgoritme {

    public static void main(String[] args) {
        TSPAlgoritme algoritme = new TSPAlgoritme();

        for (int i = 0; i < 4; i++) {
            Coordinaat coordinaat = new Coordinaat((int)Math.random() * 50 + 1, (int)Math.random() * 50 + 1);
            TSPAlgoritme.setBegincoordinaat(coordinaat);
        }
        System.out.println(TSPAlgoritme.getBegincoordinaten());
    }


    private static ArrayList<Coordinaat> begincoordinaten;

    private static ArrayList<Coordinaat> volgorde;

    public TSPAlgoritme() {
        if(begincoordinaten == null) {
            begincoordinaten = new ArrayList<>();
        } else {
            begincoordinaten.clear();
        }
    }

    public static void setBegincoordinaat(Coordinaat coordinaat) {
        begincoordinaten.add(coordinaat);
    }

    public static ArrayList<Coordinaat> getBegincoordinaten() {
        return begincoordinaten;
    }
}
