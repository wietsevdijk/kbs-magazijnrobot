package com.company;

import java.sql.SQLException;
import java.util.Objects;

public class DBLocationToHMILocation {
    Database db = new Database();

    public DBLocationToHMILocation() throws SQLException {


    }


    //vertaalt database locatie naar HMI locatie. Nog verwerken met nieuwe database calls
    public int[] positie(){
        String[] locatie = db.getProductLocatie(db.getOrderID());
        int[] positie = new int[locatie.length];
        for(int i = 1; i < locatie.length; i++){
            if(Objects.equals(locatie[i], "A1")){
                positie[i] = 0;
            }
            if(Objects.equals(locatie[i], "A2")){
                positie[i] = 1;
            }

            if(Objects.equals(locatie[i], "A3")){
                positie[i] = 2;
            }

            if(Objects.equals(locatie[i], "A4")){
                positie[i] = 3;
            }

            if(Objects.equals(locatie[i], "A5")){
                positie[i] = 4;
            }

            if(Objects.equals(locatie[i], "B1")){
                positie[i] = 5;
            }

            if(Objects.equals(locatie[i], "B2")){
                positie[i] = 6;
            }

            if(Objects.equals(locatie[i], "B3")){
                positie[i] = 7;
            }

            if(Objects.equals(locatie[i], "B4")){
                positie[i] = 8;
            }

            if(Objects.equals(locatie[i], "B5")){
                positie[i] = 9;
            }
            if(Objects.equals(locatie[i], "C1")){
                positie[i] = 10;
            }

            if(Objects.equals(locatie[i], "C2")){
                positie[i] = 11;
            }

            if(Objects.equals(locatie[i], "C3")){
                positie[i] = 12;
            }

            if(Objects.equals(locatie[i], "C4")){
                positie[i] = 13;
            }

            if(Objects.equals(locatie[i], "C5")){
                positie[i] = 14;
            }

            if(Objects.equals(locatie[i], "D1")){
                positie[i] = 15;
            }

            if(Objects.equals(locatie[i], "D2")){
                positie[i] = 16;
            }

            if(Objects.equals(locatie[i], "D3")){
                positie[i] = 17;
            }

            if(Objects.equals(locatie[i], "D4")){
                positie[i] = 18;
            }

            if(Objects.equals(locatie[i], "D5")){
                positie[i] = 19;
            }

            if(Objects.equals(locatie[i], "E1")){
                positie[i] = 20;
            }

            if(Objects.equals(locatie[i], "E2")){
                positie[i] = 21;
            }

            if(Objects.equals(locatie[i], "E3")){
                positie[i] = 22;
            }

            if(Objects.equals(locatie[i], "E4")){
                positie[i] = 23;
            }

            if(Objects.equals(locatie[i], "E5")){
                positie[i] = 24;
            }
        }

        return positie;
    }
}
