package com.company;

import java.util.ArrayList;
import java.util.List;

public class Locations {
    public int[] positie;
    public int yPixel;
    public int xPixel;

    List<Locations> locations = new ArrayList<Locations>();


    public Locations(int positie, int yPixel, int xPixel) {
        for(int i = 0; i < 10; ++i) {
            //int posities = positie[i];
           //locations.add(new Locations(posities, yPixel, xPixel));
        }
    }
}

