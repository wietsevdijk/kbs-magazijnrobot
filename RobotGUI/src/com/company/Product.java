package com.company;

public class Product {
    private int productID;
    private String productNaam;
    private float productPrijs;
    private int productGewicht;

    public Product(int productID, String productNaam, float productPrijs, int productGewicht) {
        this.productID = productID;
        this.productNaam = productNaam;
        this.productPrijs = productPrijs;
        this.productGewicht = productGewicht;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductNaam() {
        return productNaam;
    }

    public void setProductNaam(String productNaam) {
        this.productNaam = productNaam;
    }

    public float getProductPrijs() {
        return productPrijs;
    }

    public void setProductPrijs(float productPrijs) {
        this.productPrijs = productPrijs;
    }

    public int getProductGewicht() {
        return productGewicht;
    }

    public void setProductGewicht(int productGewicht) {
        this.productGewicht = productGewicht;
    }
}
