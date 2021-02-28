/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseClasses;



/**
 * Division class for use in combo boxes. No setters necessary.
 * @author Kyla Wood
 */
public class FirstLevelDivisions {
   
    private int divisionId;
    private String divisionName;
   
    /**
     * Constructor of Division Class.
     * @param divisionId ID of division, int value provided.
     * @param divisionName Name of division, string value provided.
     */
    public FirstLevelDivisions(int divisionId, String divisionName){
        this.divisionId = divisionId;
        this.divisionName = divisionName;
    }
   
    /**
     *
     * @return Division ID to get.
     */
    public int getDivisionId(){
        return divisionId;
    }
   
    /**
     *
     * @return Division Name to get.
     */
    public String getDivisionName(){
        return divisionName;
    }
    
    @Override
    public String toString(){
        return (Integer.toString(divisionId) + " " + divisionName);
    }
}
