package com.company;

public class Coordinaat {
    private int x_as;
    private int y_as;

    public Coordinaat(int x_as, int y_as) {
        this.x_as = x_as;
        this.y_as = y_as;
    }

    public int getX_as() {
        return x_as;
    }

    public int getY_as() {
        return y_as;
    }

    public String toString() {
        return "(" + x_as + ", " + y_as + ")";
    }
}