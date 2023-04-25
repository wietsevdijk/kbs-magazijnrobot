package com.company;
import java.sql.*;
import java.util.Arrays;

public class Database{
    public String url = "jdbc:mysql://localhost:3306/nerdyrobot";
    public String uname = "root";
    public String password = null;

    public Database() throws SQLException {

        //get JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public String getOrderID(){
        String orderID = null;

        try {
            Connection con = DriverManager.getConnection(url, uname, password);
            Statement statement = con.createStatement();
            String query = "SELECT orderID FROM orders WHERE pickingCompleet = 0 LIMIT 1";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                orderID = result.getString(1);
                System.out.println("OrderID: " + orderID);
            }
            con.close();
            statement.close();
            result.close();
        } catch (
                SQLException ex) {
            ex.printStackTrace();
        }
        return orderID;
    }

    public String[] getProductID(String orderID){
        String[] productIDs = {"","",""};
        try {
            Connection con = DriverManager.getConnection(url, uname, password);
            Statement statement = con.createStatement();
            String query2 = "SELECT productID FROM orderRegels WHERE orderID = " + orderID;
            ResultSet result2 = statement.executeQuery(query2);
            for (int i = 0; i < 3; i++) {
                result2.next();
                productIDs[i] = result2.getString(1);
            }
            System.out.println("productID: " + Arrays.toString(productIDs));
            con.close();
            statement.close();
            result2.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return productIDs;
    }

    public String[] getProductLocatie(String[] productIDs){
        String[] productLocatie = {"","",""};
        try {
            for (int i = 0; i < 3; i++) {
                Connection con = DriverManager.getConnection(url, uname, password);
                Statement statement = con.createStatement();
                String query3 = "SELECT productLocatie FROM producten WHERE productID = " + productIDs[i];
                ResultSet result3 = statement.executeQuery(query3);
                while (result3.next()) {
                    productLocatie[i] = result3.getString(1);
                }
                con.close();
                statement.close();
                result3.close();
            }
            System.out.println("product locatie: " + Arrays.toString(productLocatie));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return productLocatie;
    }
}


