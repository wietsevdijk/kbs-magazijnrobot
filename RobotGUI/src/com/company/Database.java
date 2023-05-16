package com.company;
import java.io.PrintWriter;
import java.io.Serial;
import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


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

    public Map<Integer, Object> getAllProductLocations(){
        //Deze return een map met de locatie van het product en het productID van het product.
        Map<Integer, Object> map;
        map = new HashMap<Integer, Object>();
        try {
            Connection con = DriverManager.getConnection(url, uname, password);
            Statement statement = con.createStatement();
            String query = "SELECT locatie,productID FROM magazijn";
            ResultSet result = statement.executeQuery(query);
            //De lengte van de for loop moet eigenlijk misschien nog met een globale variable gevuld worden.
            for (int i = 0; i < 25; i++) {
                result.next();
                map.put(Integer.valueOf(result.getString(1)),result.getString(2));
            }
            con.close();
            statement.close();
            result.close();
        } catch (
                SQLException ex) {
            ex.printStackTrace();
        }
        return map;
    }

    public Map<Integer, Object> getCurrentOrderProductLocations(){
        //Deze return een map met de locatie van het product en het productID van het product.
        Map<Integer, Object> map;
        map = new HashMap<Integer, Object>();
        try {
            Connection con = DriverManager.getConnection(url, uname, password);
            Statement statement = con.createStatement();
            String query = "SELECT locatie,productID FROM magazijn WHERE productID IN (SELECT productID FROM orderregels WHERE orderID ="+ getOrderID()+" )";
            ResultSet result = statement.executeQuery(query);
            //De lengte van de for loop moet eigenlijk misschien nog met een globale variable gevuld worden.
            for (int i = 0; i < 25; i++) {
                result.next();
                map.put(Integer.valueOf(result.getString(1)), result.getString(2));
                // hier gaat het fout omdat je maar 3 resultaten terug krijgt en 25 keer iets in de map probeert te zetten
            }
            con.close();
            statement.close();
            result.close();
        } catch (
                SQLException ex) {
            ex.printStackTrace();
        }
        return map;
    }

    public String[] getAllOrders(){
        String[] orderIDs = new String[3];

        try {
            Connection con = DriverManager.getConnection(url, uname, password);
            Statement statement = con.createStatement();
            String query = "SELECT orderID FROM orders WHERE pickingCompleet = 0";
            ResultSet result = statement.executeQuery(query);
                for (int i = 0; i < 3; i++) {
                    result.next();
                    orderIDs[i] = result.getString(1);
                }


            con.close();
            statement.close();
            result.close();
        } catch (
                SQLException ex) {
            ex.printStackTrace();
        }
        return orderIDs;
    }

    public String[] getProductName(String OrderID){
        String[]productNaam = {"","",""};
        try {
            Connection con = DriverManager.getConnection(url, uname, password);
            Statement statement = con.createStatement();
            String query = "SELECT productNaam FROM producten WHERE productID IN(SELECT productID FROM orderregels WHERE orderID =" + OrderID+ ")";
            ResultSet result = statement.executeQuery(query);

                for(int i = 0; i < 3; i++) {
                    result.next();
                    productNaam[i] = result.getString(1);

                }

            con.close();
            statement.close();
            result.close();
        } catch (
                SQLException ex) {
            ex.printStackTrace();
        }
        return productNaam;

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
            con.close();
            statement.close();
            result2.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return productIDs;
    }

    public String[] getProductLocatie(String orderID){
        String[] productLocatie = new String[3];
        try {
                Connection con = DriverManager.getConnection(url, uname, password);
                Statement statement = con.createStatement();
                String query3 = "SELECT locatie FROM magazijn WHERE productID IN(SELECT productID FROM orderregels WHERE orderID = " + orderID + ")";
                ResultSet result3 = statement.executeQuery(query3);
                for (int i = 0; i < 3; i++) {
                    result3.next();
                    productLocatie[i] = result3.getString(1);
                }
                con.close();
                statement.close();
                result3.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return productLocatie;
    }

    public String getCustomerID(int orderID){
            String customerID = null;
        try {
            Connection con = DriverManager.getConnection(url, uname, password);
            Statement statement = con.createStatement();
            String query = "SELECT klantID FROM orders WHERE orderID =" + orderID;
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                customerID = result.getString(1);
            }
            con.close();
            statement.close();
            result.close();
        } catch (
                SQLException ex) {
            ex.printStackTrace();
        }
        return customerID;
    }

    public String[] getCustomerDetails(int customerID){
        String[] customerDetails = new String[6];
        try {
                Connection con = DriverManager.getConnection(url, uname, password);
                Statement statement = con.createStatement();
                String query3 = "SELECT * FROM klanten WHERE klantID = " + customerID;
                ResultSet result3 = statement.executeQuery(query3);
                while(result3.next()) {
                    for (int i = 0; i < 6; i++) {
                        customerDetails[i] = result3.getString((i + 1));
                    }
                }
                con.close();
                statement.close();
                result3.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
            return customerDetails;
    }

    public void shipOrder(String orderID){
        try {
            Connection con = DriverManager.getConnection(url, uname, password);
            Statement statement = con.createStatement();
            String query = "UPDATE orders SET pickingCompleet = 1 WHERE pickingCompleet = 0  AND orderID = " + orderID;
            statement.executeUpdate(query);
            con.close();
            statement.close();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}


