/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseClasses;

import java.time.LocalDate;
import java.time.LocalTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *Wrapper class to populate Appointment tableview, see main screen controller.initialize javadoc for more info.
 * @author Kwood
 */
public class DisplayAppointments {
    
    private int appointmentId;
    private String appointmentTitle;
    private String appointmentDescription;
    private String appointmentLocation;
    private String contactName;
    private String appointmentType;
    private LocalDate appointmentDate;
    private LocalTime appointmentStart;
    private LocalTime appointmentEnd;
    private String customerName;
    
    
    /**
     * Constructor for the appointments to be displayed
     * @param appointmentId Integer value of the appointment ID
     * @param appointmentTitle String value of the title
     * @param appointmentDescription String value of the description of appointment
     * @param appointmentLocation String value of the location of the meeting
     * @param contactName String value of the contact associated
     * @param appointmentType String value of the type of meeting
     * @param appointmentDate LocalDate value of the day of the appointment
     * @param appointmentStart LocalTime value of the start of the appointment
     * @param appointmentEnd LocalTime value of the end of the appointment
     * @param customerName String value of the customer's name who is attending
     */
    public DisplayAppointments(int appointmentId, String appointmentTitle, String appointmentDescription, String appointmentLocation, String contactName, String appointmentType, LocalDate appointmentDate, LocalTime appointmentStart, LocalTime appointmentEnd, String customerName){
        this.appointmentId = appointmentId;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.contactName = contactName;
        this.appointmentType = appointmentType;
        this.appointmentDate = appointmentDate;
        this.appointmentStart = appointmentStart;
        this.appointmentEnd = appointmentEnd;
        this.customerName  = customerName;
    }
    
    public int getAppointmentId(){
        return appointmentId;
    }
    
    public String getAppointmentTitle(){
        return appointmentTitle;
    }
    
    public String getAppointmentDescription(){
        return appointmentDescription;
    }
    
    public String getAppointmentLocation(){
        return appointmentLocation;
    }
    
    public String getContactName(){
        return contactName;
    }
    
    public String getAppointmentType(){
        return appointmentType;
    }
    
    public LocalDate getAppointmentDate(){
        return appointmentDate;
    }
    
    public LocalTime getAppointmentStart(){
        return appointmentStart;
    }
    
    public LocalTime getAppointmentEnd(){
        return appointmentEnd;
    }
    
    public String getCustomerName(){
        return customerName;
    }
    
    public static ObservableList<DisplayAppointments> displayAppointments(){
        ObservableList<DisplayAppointments> display = FXCollections.observableArrayList();
        
        try{
            String dis = "SELECT Appointment_ID, Title, Description, Location, contacts.Contact_Name, Type, Start, End, customers.Customer_Name " +
                "FROM appointments, customers, contacts WHERE appointments.Contact_ID = contacts.Contact_ID AND appointments.Customer_ID = customers.Customer_ID;";
            
            PreparedStatement ps = DBConnection.startConnection().prepareStatement(dis);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                int appointmentId = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String appointmentDescription = rs.getString("Description");
                String appointmentLocation = rs.getString("Location");
                String contactName = rs.getString("Contact_Name");
                String appointmentType = rs.getString("Type");
                LocalDate appointmentDate = rs.getDate("Start").toLocalDate();
                LocalTime appointmentStart = rs.getTimestamp("Start").toLocalDateTime().toLocalTime();
                LocalTime appointmentEnd = rs.getTimestamp("End").toLocalDateTime().toLocalTime();
                String customerName = rs.getString("Customer_Name");
                
                display.add(new DisplayAppointments(appointmentId, appointmentTitle, appointmentDescription, appointmentLocation, contactName, appointmentType, appointmentDate, appointmentStart, appointmentEnd, customerName));
                
            }
            
            
        }
        catch(SQLException e){
            e.printStackTrace();
        }
       return display; 
    }
    
}
