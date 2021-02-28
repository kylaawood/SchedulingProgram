/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseClasses;





/**
 * Customer class to make customers objects.
 * @author Kyla Wood
 */
public class Customers {
    private int customerId;
    private String customerName;
    private String customerPostalCode;
    private String customerAddress;
    private String customerPhoneNumber;
    private int countryId;
    private int divisionId;
   
    
    
    /**
     * Constructor of customer class.
     * @param customerId ID of customer, auto generated.
     * @param customerName Name of customer, string value.
     * @param customerPostalCode Postal code of the customer, string value.
     * @param customerAddress Address of customer, string value.
     * @param customerPhoneNumber Phone number of customer, string value.
     * @param countryId ID of country the customer is in, int value.
     * @param divisionId ID of division the customer is in, int value.
     */
    public Customers(int customerId, String customerName, String customerPostalCode, String customerAddress, String customerPhoneNumber, int countryId, int divisionId){
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerPostalCode = customerPostalCode;
        this.customerAddress = customerAddress;
        this.customerPhoneNumber = customerPhoneNumber;
        this.countryId = countryId;
        this.divisionId= divisionId;
    }
   
    
    
    /**
     *
     * @return Customer ID to get.
     */
    public int getCustomerId(){
        return customerId;
    }
    
    /**
     * 
     * @param customerId Customer ID to set.
     */
    public void setCustomerId(int customerId){
        
    }
    
    /**
     *
     * @return Customer name to get.
     */
    public String getCustomerName(){
        return customerName;
    }
   
    /**
     *
     * @param customerName Customer name to set.
     */
    public void setCustomerName(String customerName){
       
    }
    
    /**
     * 
     * @return Customer postal code to get.
     */
    public String getCustomerPostalCode(){
        return customerPostalCode;
    }
    
    /**
     * 
     * @param customerPostalCode Customer postal code to set.
     */
    public void setCustomerPostalCode(String customerPostalCode){
        
    }
   
    /**
     *
     * @return Customer address to get.
     */
    public String getCustomerAddress(){
        return customerAddress;
    }
   
    /**
     *
     * @param customerAddress Customer address to set.
     */
    public void setCustomerAddress(String customerAddress){
       
    }
   
    /**
     *
     * @return Customer phone number to get.
     */
    public String getCustomerPhoneNumber(){
        return customerPhoneNumber;
    }
   
    /**
     *
     * @param customerPhoneNumber Customer phone number to set.
     */
    public void setCustomerPhoneNumber(String customerPhoneNumber){
       
    }
   
    /**
     *
     * @return Country ID to get.
     */
    public int getCountryId(){
        return countryId;
    }
   
    /**
     *
     * @param countryId Country ID to set.
     */
    public void setCountryId(int countryId){
       
    }
   
    /**
     *
     * @return Division Id to get.
     */
    public int getDivisionId(){
        return divisionId;
    }
   
    /**
     *
     * @param divisionId Division ID to set.
     */
    public void setDivisionId(int divisionId){
       
    }
    
    /**
     * Override for To String method  so Combo Boxes display properly
     * @return String of country object
     */
   @Override
    public String toString(){
        return (Integer.toString(customerId) + " " + customerName);
    }
   
}