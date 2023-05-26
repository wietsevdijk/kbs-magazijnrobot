package com.company;

import java.util.ArrayList;

public class Order {
    private int orderID;
    private int klantID;
    private int pickingCompleet;
    private ArrayList<Orderregel> orderLines;

    public Order(int orderID, int klantID, int pickingCompleet, ArrayList<Orderregel> orderLines) {
        this.orderID = orderID;
        this.klantID = klantID;
        this.pickingCompleet = pickingCompleet;
        this.orderLines = orderLines;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getKlantID() {
        return klantID;
    }

    public void setKlantID(int klantID) {
        this.klantID = klantID;
    }

    public int getPickingCompleet() {
        return pickingCompleet;
    }

    public void setPickingCompleet(int pickingCompleet) {
        this.pickingCompleet = pickingCompleet;
    }

    public ArrayList<Orderregel> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(ArrayList<Orderregel> orderLines) {
        this.orderLines = orderLines;
    }
}
