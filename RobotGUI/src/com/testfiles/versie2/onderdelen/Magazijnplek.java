package com.testfiles.versie2.onderdelen;

import java.awt.*;

public class Magazijnplek {

    private int X;
    private int Y;

    private Point positie;


    public Magazijnplek(int X, int Y, Point startpos) {
        this.X = X;
        this.Y = Y;

        this.positie = startpos;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public Point getPositie() {
        return positie;
    }
}
