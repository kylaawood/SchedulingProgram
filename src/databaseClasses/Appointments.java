/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseClasses;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import utilities.DBCustomers;


/**
 * Appointment class to hold appointment objects.
 * @author Kyla Wood
 */
public class Appointments {
   
    private int appointmentId;
    private String appointmentTitle;
    private String appointmentDescription;
    private String appointmentLocation;
    private String appointmentType;
    private LocalTime appointmentStart;
    private LocalTime appointmentEnd;
    private int customerId;
    private int userId;
    private int contactId;
    private LocalDate appointmentDate;
   
    /**
     * Constructor for the appointments.
     * @param appointmentId ID of appointment, auto gen.
     * @param appointmentTitle Title of appointment, string value.
     * @param appointmentDescription Description of appointment, string value.
     * @param appointmentLocation Location of appointment, string value.
     * @param appointmentType Type of appointment, string value but will be put in a combo box for limited selection.
     * @param appointmentDate Date of appointment, local date value.
     * @param appointmentStart Time of appointment start, local time value.
     * @param appointmentEnd Time of appointment end, local time value.
     * @param customerId ID of attending customer, MUST ATTEND int value.
     * @param userId ID of user, not required to attend int value.
     * @param contactId ID of contact, not required to attend int value.
     */
    public Appointments(int appointmentId, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, LocalDate appointmentDate, LocalTime appointmentStart, LocalTime appointmentEnd, int customerId, int userId, int contactId){
        this.appointmentId = appointmentId;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.appointmentType = appointmentType;
        this.appointmentDate = appointmentDate;
        this.appointmentStart = appointmentStart;
        this.appointmentEnd = appointmentEnd;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }
   
    /**
     *
     * @return Appointment ID to return
     */
    public int getAppointmentId(){
        return appointmentId;
    }
    /**
     *
     * @return Appointment Title to get.
     */
    public String getAppointmentTitle(){
        return appointmentTitle;
    }
   
    /**
     *
     * @param appointmentTitle Appointment title to set.
     */
    public void setAppointmentTitle(String appointmentTitle){
       
    }
   
    /**
     *
     * @return Appointment description to get.
     */
    public String getAppointmentDescription(){
        return appointmentDescription;
    }
   
    /**
     *
     * @param appointmentDescription Appointment Description to set.
     */
    public void setAppointmentDescription(String appointmentDescription){
       
    }
    /**
     *
     * @return Appointment location to get.
     */
    public String getAppointmentLocation(){
        return appointmentLocation;
    }
   
    /**
     *
     * @param appointmentLocation Appointment location to set.
     */
    public void setAppointmentLocation(String appointmentLocation){
       
    }
   
    /**
     *
     * @return Appointment type to get.
     */
    public String getAppointmentType(){
        return appointmentType;
    }
   
    /**
     *
     * @param appointmentType Appointment type to set.
     */
    public void setAppointmentType(String appointmentType){
       
    }
    
    public LocalDate getAppointmentDate(){
        return appointmentDate;
    }
    
    public void setAppointmentDate(LocalDate appointmentDate){
        
    }
   
    /**
     *
     * @return Appointment start date time to get.
     */
    public LocalTime getAppointmentStart(){
        return appointmentStart;
    }
   
    /**
     *
     * @param appointmentStart Appointment start time to set.
     */
    public void setAppointmentStart(LocalDateTime appointmentStart){
       
    }
   
    /**
     *
     * @return Appointment end time to get.
     */
    public LocalTime getAppointmentEnd(){
        return appointmentEnd;
    }
   
    /**
     *
     * @param appointmentEnd Appointment end time to set.
     */
    public void setAppointmentEnd(LocalTime appointmentEnd){
       
    }
   
    /**
     *
     * @return Attending customer ID to get.
     */
    public int getCustomerId(){
        return customerId;
    }
   
    /**
     *
     * @param customerId Attending customer ID to set.
     */
    public void setCustomerId(int customerId){
       
    }
   
    /**
     *
     * @return Connected user ID to get.
     */
    public int getUserId(){
        return userId;
    }
   
    /**
     *
     * @param userId Connected user ID to set.
     */
    public void setUserId(int userId){
       
    }
   
    /**
     *
     * @return Connected contact ID to get.
     */
    public int getContactId(){
        return contactId;
    }
   
    /**
     *
     * @param contactId Connected contact ID to set.
     */
    public void setContactId(int contactId){
       
    }
    
    /**
     * Override to string method for combo box setting.
     * @return String of desired appointment.
     */
    @Override
    public String toString(){
        String customerName = null;
        for(Customers customer : DBCustomers.getAllCustomers()){
            if(customer.getCustomerId() == customerId){
                customerName = customer.getCustomerName();
            }
        }
        return (Integer.toString(appointmentId) + " " + appointmentTitle + " " + appointmentStart.format(DateTimeFormatter.ISO_LOCAL_TIME) + " with " + Integer.toString(customerId) + " " + customerName);
    }
}