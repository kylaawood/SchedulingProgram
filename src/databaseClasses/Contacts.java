/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseClasses;



/**
 * Contact class for use in combo boxes. No setters necessary.
 * @author Kyla Wood
 */
public class Contacts {
    private int contactId;
    private String contactName;
   
    /**
     * Constructor for contact class.
     * @param contactId ID of contact, int value.
     * @param contactName Name of contact, string value.
     */
    public Contacts(int contactId, String contactName){
        this.contactId = contactId;
        this.contactName = contactName;
    }
   
    /**
     *
     * @return Contact ID to get.
     */
    public int getContactId(){
        return contactId;
    }
   
    /**
     *
     * @return Contact name to get.
     */
    public String getContactName(){
        return contactName;
    }
    
    /**
     * Override for To String method  so Combo Boxes display properly
     * @return String of country object
     */
   @Override
    public String toString(){
        return (Integer.toString(contactId) + " " + contactName);
    }
}