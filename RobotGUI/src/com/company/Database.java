package com.company;
import java.io.PrintWriter;
import java.io.Serial;
import java.sql.*;
import java.util.*;


public class Database{
    private String url = "jdbc:mysql://localhost:3306/nerdyrobot";
    private String uname = "root";
    private String password = null;
    private Connection con = DriverManager.getConnection(url, uname, password);

    public Database() throws SQLException {

        //get JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();;
        }

    }

    //Functie om een nette SQL exception te printen
    public static String printSqlException(SQLException sqlex) {
        StringBuffer sb = new StringBuffer();

        sb.append("SQLException:\n");

        while (sqlex != null) {

            sb.append(sqlex.getMessage() + "\n");
            sb.append("SQL State: " + sqlex.getSQLState() + "\n");
            sb.append("Vendor Error Code: " + sqlex.getErrorCode() + "\n");

            sqlex = sqlex.getNextException();
        }

        return sb.toString();
    }

    /*
    Haalt lijst op van alle klanten in de database
    Gebruikt bij opstart van HMI
     */
    public ArrayList<Klant> retrieveAllCustomers() {
        String retrieveQuery = //Haalt alle klantinformatie op
                "SELECT klantID, klantVoornaam, klantAchternaam, klantAdres, klantPostcode, klantWoonplaats " +
                        "FROM klanten";

        //Maakt nieuwe arraylist aan die later wordt teruggegeven
        ArrayList<Klant> klanten = new ArrayList<>();

        //Voert query uit als prepared statement
        try (PreparedStatement preparedStatement = con.prepareStatement(retrieveQuery)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) { //Loop door alle resultaten

                //Haal alle kolommen op en sla ze op
                int klantID = rs.getInt(1);
                String voornaam = rs.getString(2);
                String achternaam = rs.getString(3);
                String adres = rs.getString(4);
                String postcode = rs.getString(5);
                String woonplaats = rs.getString(6);

                //Voeg toe aan klanten-array
                klanten.add(new Klant(klantID, voornaam, achternaam, adres, postcode, woonplaats));
            }

        } catch (SQLException ex) {
            System.out.println(printSqlException(ex));
            if (con != null) { //Rollback als de transactie faalt.
                try {
                    System.err.print("Transaction is being rolled back");
                    con.rollback();
                } catch (SQLException excep) {
                    System.out.println(printSqlException(excep));
                }
            }
        }
        return klanten;
    }

    public String getOrderID(){

        String orderID = null;
        try {
            
            Statement statement = con.createStatement();
            String query = "SELECT orderID FROM orders WHERE pickingCompleet = 0 LIMIT 1";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                orderID = result.getString(1);
            }
            
            statement.close();
            result.close();
        } catch (SQLException ex) {
            System.out.println(printSqlException(ex));
        }
        return orderID;
    }


    public Map<Integer, Object> getAllProductLocations(){
        //Deze return een map met de locatie van het product en het productID van het product.
        Map<Integer, Object> map;
        map = new HashMap<Integer, Object>();
        try {
            
            Statement statement = con.createStatement();
            String query = "SELECT locatie,productID FROM magazijn";
            ResultSet result = statement.executeQuery(query);
            //De lengte van de for loop moet eigenlijk misschien nog met een globale variable gevuld worden.
            for (int i = 0; i < 25; i++) {
                result.next();
                map.put(Integer.valueOf(result.getString(1)),result.getString(2));
            }
            
            statement.close();
            result.close();
        } catch (
                SQLException ex) {
            System.out.println(printSqlException(ex));
        }
        return map;
    }

    public Map<Integer, Object> getCurrentOrderProductLocations(){
        //Deze return een map met de locatie van het product en het productID van het product.
        Map<Integer, Object> map;
        map = new HashMap<Integer, Object>();
        try {
            
            Statement statement = con.createStatement();
            String query = "SELECT locatie,productID FROM magazijn WHERE productID IN (SELECT productID FROM orderregels WHERE orderID ="+ getOrderID()+" )";
            ResultSet result = statement.executeQuery(query);
            //De lengte van de for loop moet eigenlijk misschien nog met een globale variable gevuld worden.
            for (int i = 0; i < 25; i++) {
                result.next();
                map.put(Integer.valueOf(result.getString(1)), result.getString(2));
                // hier gaat het fout omdat je maar 3 resultaten terug krijgt en 25 keer iets in de map probeert te zetten
            }
            
            statement.close();
            result.close();
        } catch (
                SQLException ex) {
            System.out.println(printSqlException(ex));
        }
        return map;
    }

    public String[] getAllOrders(){
        String[] orderIDs = new String[3];

        try {
            Statement statement = con.createStatement();
            String query = "SELECT orderID FROM orders WHERE pickingCompleet = 0";
            ResultSet result = statement.executeQuery(query);
                for (int i = 0; i < 3; i++) {
                    result.next();
                    orderIDs[i] = result.getString(1);
                }

            statement.close();
            result.close();
        } catch (SQLException ex) {
            System.err.println(printSqlException(ex));
        }
        return orderIDs;
    }

    public String[] getProductData (String productLocatie){
        String[]productData = new String[3];

        try {
            
            Statement statement = con.createStatement();
            String query = "SELECT productNaam, productPrijs, productAantal FROM producten WHERE productID IN (SELECT productID FROM magazijn WHERE locatie = " + "\"" + productLocatie + "\"" + ")";
            ResultSet result = statement.executeQuery(query);

            while(result.next()){
                productData[0] = result.getString(1);
                productData[1] = result.getString(2);
                productData[2] = result.getString(3);
            }

            
            statement.close();
            result.close();
        } catch (
                SQLException ex) {
            System.out.println(printSqlException(ex));
        }
        return productData;

    }

    public String[] getProductName(String OrderID){
        String[]productNaam = {"","",""};
        try {
            
            Statement statement = con.createStatement();
            String query = "SELECT productNaam FROM producten WHERE productID IN(SELECT productID FROM orderregels WHERE orderID =" + OrderID+ ")";
            ResultSet result = statement.executeQuery(query);

                for(int i = 0; i < 3; i++) {
                    result.next();
                    productNaam[i] = result.getString(1);

                }

            
            statement.close();
            result.close();
        } catch (
                SQLException ex) {
            System.out.println(printSqlException(ex));
        }
        return productNaam;

    }

    public String[] getProductID(String orderID){
        String[] productIDs = {"","",""};
        try {
            
            Statement statement = con.createStatement();
            String query2 = "SELECT productID FROM orderRegels WHERE orderID = " + orderID;
            ResultSet result2 = statement.executeQuery(query2);
            for (int i = 0; i < 3; i++) {
                result2.next();
                productIDs[i] = result2.getString(1);
            }
            
            statement.close();
            result2.close();
        } catch (SQLException ex) {
            System.out.println(printSqlException(ex));
        }
        return productIDs;
    }

    public String[] getProductLocatie(String orderID){
        String[] productLocatie = new String[3];
        try {
                
                Statement statement = con.createStatement();
                String query3 = "SELECT locatie FROM magazijn WHERE productID IN(SELECT productID FROM orderregels WHERE orderID = " + orderID + ")";
                ResultSet result3 = statement.executeQuery(query3);
                for (int i = 0; i < 3; i++) {
                    result3.next();
                    productLocatie[i] = result3.getString(1);
                }
                
                statement.close();
                result3.close();

        } catch (SQLException ex) {
            System.out.println(printSqlException(ex));
        }
        return productLocatie;
    }

    public String getCustomerID(int orderID){
            String customerID = null;
        try {
            
            Statement statement = con.createStatement();
            String query = "SELECT klantID FROM orders WHERE orderID =" + orderID;
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                customerID = result.getString(1);
            }
            
            statement.close();
            result.close();
        } catch (
                SQLException ex) {
            System.out.println(printSqlException(ex));
        }
        return customerID;
    }

    public String[] getCustomerDetails(int customerID){
        String[] customerDetails = new String[6];
        try {
                
                Statement statement = con.createStatement();
                String query3 = "SELECT * FROM klanten WHERE klantID = " + customerID;
                ResultSet result3 = statement.executeQuery(query3);
                while(result3.next()) {
                    for (int i = 0; i < 6; i++) {
                        customerDetails[i] = result3.getString((i + 1));
                    }
                }
                
                statement.close();
                result3.close();
        } catch (SQLException ex) {
            System.out.println(printSqlException(ex));
        }
            return customerDetails;
    }

    public void shipOrder(String orderID){
        try {
            
            Statement statement = con.createStatement();
            String query = "UPDATE orders SET pickingCompleet = 1 WHERE pickingCompleet = 0  AND orderID = " + orderID;
            statement.executeUpdate(query);
            
            statement.close();
        }catch (SQLException ex){
            System.out.println(printSqlException(ex));
        }
    }
}


