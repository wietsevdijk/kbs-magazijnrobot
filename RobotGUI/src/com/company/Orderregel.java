package com.company;

public class Orderregel {
    private int orderRegelID;
    private int orderID;
    private Product product;
    private int productAantal;

    public Orderregel(int orderRegelID, int orderID, Product product, int productAantal) {
        this.orderRegelID = orderRegelID;
        this.orderID = orderID;
        this.product = product;
        this.productAantal = productAantal;
    }

    public int getOrderRegelID() {
        return orderRegelID;
    }

    public void setOrderRegelID(int orderRegelID) {
        this.orderRegelID = orderRegelID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getProductAantal() {
        return productAantal;
    }

    public void setProductAantal(int productAantal) {
        this.productAantal = productAantal;
    }
}
