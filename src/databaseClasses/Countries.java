/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseClasses;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.DBConnection;



/**
 * Country class for use in combo boxes. No setters necessary
 * @author Kyla Wood
 */
public class Countries {
   
    private int countryId;
    private String countryName;
    
    
   
    /**
     * Constructor for country object
     * @param countryId ID of specific country, provided.
     * @param countryName Name of specific country, provided.
     */
    public Countries(int countryId, String countryName){
        this.countryId = countryId;
        this.countryName = countryName;
    }
   
    
    
    /**
     *
     * @return ID of country to get
     */
    public int getCountryId(){
        return countryId;
    }
   
    /**
     *
     * @return Name of country to get
     */
    public String getCountryName(){
        return countryName;
    }
    
    /**
     * Override for To String method  so Combo Boxes display properly
     * @return String of country object
     */
   @Override
    public String toString(){
        return (Integer.toString(countryId) + " " + countryName);
    }
    
}