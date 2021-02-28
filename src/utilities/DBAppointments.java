/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import databaseClasses.Appointments;
import databaseClasses.Contacts;
import databaseClasses.DisplayAppointments;
import databaseClasses.Users;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;


/**
 * Class to hold SQL statements and executors as they pertain to appointments.
 * @author Kwood
 */
public class DBAppointments {
    
    /**
     * SQL statement execution for turning MySQL table into Observable Array List
     * @return an Observable array list of all customers.
     */
    public static ObservableList<Appointments> getAllAppointments(){
        ObservableList <Appointments> appointmentList = FXCollections.observableArrayList();
        try{
            
            String appt = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, appointments.Customer_ID, appointments.User_ID, appointments.Contact_ID" + 
                    " FROM appointments, customers, users, contacts WHERE customers.Customer_ID = appointments.Customer_ID AND users.User_ID = appointments.User_ID AND contacts.Contact_ID = appointments.Contact_ID";
            PreparedStatement ps = DBConnection.startConnection().prepareStatement(appt);
            ResultSet rs = ps.executeQuery();
            
            
            while(rs.next()){
                
                int appointmentId = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String appointmentDescription = rs.getString("Description");
                String appointmentLocation = rs.getString("Location");
                String appointmentType = rs.getString("Type");
                LocalDate appointmentDate = rs.getDate("Start").toLocalDate();
                LocalTime appointmentStart = rs.getTimestamp("Start").toLocalDateTime().toLocalTime();
                LocalTime appointmentEnd = rs.getTimestamp("End").toLocalDateTime().toLocalTime();
                int customerId = rs.getInt("Customer_ID");       
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                
                        
                        
                appointmentList.add(new Appointments(appointmentId, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, appointmentDate, appointmentStart, appointmentEnd, customerId, userId, contactId));
                
                
                
            }
            
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return appointmentList;
    }
    
  
    
    /**
     * SQL Executor to retrieve all users.
     * @return An ObservableList of all users.
     */
    public static ObservableList<Users> getAllUsers(){
        ObservableList<Users> userList = FXCollections.observableArrayList();
        try{
            String user = "SELECT User_ID, User_Name, Password FROM users";
            
            PreparedStatement ps = DBConnection.startConnection().prepareStatement(user);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                
                userList.add(new Users(userId, userName, password));
            }
            
        }
        
        catch(SQLException e){
            e.printStackTrace();
        }
        return userList;
    }
    
    /**
     * SQL Executor to retrieve all contacts.
     * @return An ObservableList of all contacts.
     */
    public static ObservableList<Contacts> getAllContacts(){
        ObservableList<Contacts> contactList = FXCollections.observableArrayList();
        try{
            String contact = "SELECT Contact_ID, Contact_Name FROM contacts";
            
            PreparedStatement ps = DBConnection.startConnection().prepareStatement(contact);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                
                contactList.add(new Contacts(contactId, contactName));
            }
            
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return contactList;
    }
    
    
    
    /**
     * SQL Executor for adding an appointment. No ID argument as that is auto generated.
     * @param appointmentTitle Title of new appointment.
     * @param appointmentDescription Description of new appointment.
     * @param appointmentLocation Location of new appointment.
     * @param appointmentType Type of new appointment.
     * @param appointmentDate LocalDate of new appointment.
     * @param appointmentStart LocalTime start of new appointment. 
     * @param appointmentEnd LocalTime end of new appointment.
     * @param customerId ID of customer involved.
     * @param userId ID of user involved.
     * @param contactId ID of contact involved.
     */
    public static void addAppointment(String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, 
                                      LocalDate appointmentDate, LocalTime appointmentStart, LocalTime appointmentEnd, int customerId, int userId, int contactId){
        try{
            String appt = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement ps = DBConnection.startConnection().prepareStatement(appt);
            
            ps.setString(1, appointmentTitle);
            ps.setString(2, appointmentDescription);
            ps.setString(3, appointmentLocation);
            ps.setString(4, appointmentType);
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.of(appointmentDate, appointmentStart)));
            ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.of(appointmentDate, appointmentEnd)));
            ps.setInt(7, customerId);
            ps.setInt(8, userId);
            ps.setInt(9, contactId);
            
            ps.execute();
            
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    /**
     * SQL Executor for updating an appointment.
     * @param appointmentId ID of appointment you wish to update, uneditable.
     * @param appointmentTitle Title of appointment you wish to update.
     * @param appointmentDescription Description of appointment you wish to update.
     * @param appointmentLocation Location of appointment you wish to update.
     * @param appointmentType Type of appointment you wish to update.
     * @param appointmentDate LocalDate of appointment you wish to update.
     * @param appointmentStart LocalDateTime start of appointment you wish to update.
     * @param appointmentEnd LocalDateTime end of appointment you wish to update.
     * @param customerId ID of the customer involved with appointment.
     * @param userId ID of the user involved with appointment.
     * @param contactId ID of the contact involved with the appointment
     */
    public static void updateAppointment(int appointmentId, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, 
                                      LocalDate appointmentDate, LocalTime appointmentStart, LocalTime appointmentEnd, int customerId, int userId, int contactId){
    try{
        String appt = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        
        PreparedStatement ps = DBConnection.startConnection().prepareStatement(appt);
        
        ps.setInt(10, appointmentId);
        ps.setString(1, appointmentTitle);
        ps.setString(2, appointmentDescription);
        ps.setString(3, appointmentLocation);
        ps.setString(4, appointmentType);
        ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.of(appointmentDate, appointmentStart)));
        ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.of(appointmentDate, appointmentEnd)));
        ps.setInt(7, customerId);
        ps.setInt(8, userId);
        ps.setInt(9, contactId);
        
        ps.execute();
        
    }
    catch(SQLException e){
        e.printStackTrace();
    }
}
    
    /**
     * SQL Executor for deleting appointments.
     * @param appointmentId ID of appointment you wish to delete.
     * @return Boolean of true if deleted and false if not.
     */
    public static boolean deleteAppointment(int appointmentId){
        try{
            String c = "DELETE FROM appointments WHERE appointment_ID = ?";
            
            PreparedStatement ps = DBConnection.startConnection().prepareStatement(c);
            
            ps.setInt(1, appointmentId);
            ps.execute();
            return true;
            
        }
        catch(SQLException e){
            
        }
        return false;
    }
    
    
    /**
     * SQL Executor for getting the upcoming week's appointments
     * @param compTime LocalDateTime of the users current time.
     * @return Observable list of the appointments coming up that week.
     */
    public static ObservableList<DisplayAppointments> getAppointmentsForWeek(LocalDateTime compTime){
        ObservableList <DisplayAppointments> appointmentList = FXCollections.observableArrayList();
        try{
            String appt = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_Name, appointments.User_ID, Contact_Name" + 
                    " FROM appointments, customers, users, contacts WHERE customers.Customer_ID = appointments.Customer_ID AND users.User_ID = appointments.User_ID AND contacts.Contact_ID = appointments.Contact_ID AND Start BETWEEN ? AND ?";
            PreparedStatement ps = DBConnection.startConnection().prepareStatement(appt);
            
            ps.setTimestamp(1, Timestamp.valueOf(compTime));
            ps.setTimestamp(2, Timestamp.valueOf(compTime.plusDays(7)));
            
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
                
                appointmentList.add(new DisplayAppointments(appointmentId, appointmentTitle, appointmentDescription, appointmentLocation, contactName, appointmentType, appointmentDate, appointmentStart, appointmentEnd, customerName));
                
                
                
            }
            
        }
    
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return appointmentList;
    
        
    }
    
    /**
     * SQL Executor for getting the appointments in a month
     * @param compTime LocalDateTime of the users current time.
     * @return Observable list of appointments coming up in the month.
     */
    public static ObservableList<DisplayAppointments> getAppointmentsForMonth(LocalDateTime compTime){
        ObservableList <DisplayAppointments> appointmentList = FXCollections.observableArrayList();
        try{
            String appt = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_Name, appointments.User_ID, Contact_Name" + 
                    " FROM appointments, customers, users, contacts WHERE customers.Customer_ID = appointments.Customer_ID AND users.User_ID = appointments.User_ID AND contacts.Contact_ID = appointments.Contact_ID AND Start BETWEEN ? AND ?";
            PreparedStatement ps = DBConnection.startConnection().prepareStatement(appt);
            ps.setTimestamp(1, Timestamp.valueOf(compTime));
            ps.setTimestamp(2, Timestamp.valueOf(compTime.plusMonths(1)));
            
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
                
                appointmentList.add(new DisplayAppointments(appointmentId, appointmentTitle, appointmentDescription, appointmentLocation, contactName, appointmentType, appointmentDate, appointmentStart, appointmentEnd, customerName));
                
                
                
            }
            
        }
    
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return appointmentList;
    
        
    }
    
    
    /**
     * Checks for any appointments and interacts with the main screen alert for showing them.
     * @param compTime Current LocalDateTime of the user's computer.
     * @return Appointment within 15 min or null if there is none.
     */
    public static Appointments appointmentSender(LocalDateTime compTime, int userId){
        for(Appointments appointment : getAllAppointments()){
            if(LocalDateTime.of(appointment.getAppointmentDate(), appointment.getAppointmentStart()).isBefore(compTime.plusMinutes(15))
                    && LocalDateTime.of(appointment.getAppointmentDate(), appointment.getAppointmentStart()).isAfter(compTime) && appointment.getUserId() == userId){
                
                return appointment;
            }
        }
       return null; 
    }
    
}
