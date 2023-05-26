package com.company;

public class Magazijn {
    private String magazijnLocatie;
    private Product product;

    public Magazijn(String magazijnLocatie, Product product) {
        this.magazijnLocatie = magazijnLocatie;
        this.product = product;
    }

    public String getMagazijnLocatie() {
        return magazijnLocatie;
    }

    public void setMagazijnLocatie(String magazijnLocatie) {
        this.magazijnLocatie = magazijnLocatie;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
