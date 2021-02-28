/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import databaseClasses.Appointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
import databaseClasses.Appointments;
import javafx.collections.ObservableList;
*/
/**
 * Class for holding SQL executors that don't necessarily fall under appointments, customers, or connection.
 * @author Kwood
 */
public class DBGeneral {
    
    
   /**
    * SQL Executor for the report on amount of appointments by Type and Month.
    * @return List of Report results
    */ 
   public static ObservableList<String> reportByTypeMonth(){
       ObservableList<String> rlist = FXCollections.observableArrayList();
       try{
           String report = "SELECT COUNT(Appointment_ID), Type, MONTH(Start) FROM appointments GROUP BY Type, MONTH(Start)";
           
           PreparedStatement ps = DBConnection.startConnection().prepareStatement(report);
           ResultSet rs = ps.executeQuery();
           
           while(rs.next()){
               int count = rs.getInt("COUNT(Appointment_ID)");
               String type = rs.getString("Type");
               int month = rs.getInt("MONTH(Start)");
               String monthstr = Month.of(month).name();
               
               rlist.add(type + " " + monthstr + " - " + Integer.toString(count));
               
            }
          
       }
       catch(SQLException e){
          e.printStackTrace();
       }
       
    return rlist; 
   }
   
   /**
    * SQL Executor for the report on customers by country.
    * @return List of report Results
    */
   public static ObservableList<String> reportByCustomerCountry(){
       ObservableList<String> rlist = FXCollections.observableArrayList();
       try{
           String report = "SELECT COUNT(Customer_ID), countries.Country, first_level_divisions.Country_ID FROM customers, first_level_divisions, countries" + 
                           " WHERE customers.Division_ID = first_level_divisions.Division_ID AND first_level_divisions.Country_ID = countries.Country_ID GROUP BY Country_ID;";
           
           PreparedStatement ps = DBConnection.startConnection().prepareStatement(report);
           ResultSet rs = ps.executeQuery();
           
           while(rs.next()){
               int customerCount = rs.getInt("COUNT(Customer_ID)");
               String country = rs.getString("Country");
               
               rlist.add(customerCount + " - " + country);
               
            }
          
       }
       catch(SQLException e){
          e.printStackTrace();
       }
       
    return rlist; 
   }

   /**
    * SQL Executor for the report on appointments by contact.
    * @return List of report results.
    */
   public static ObservableList<String> reportByContactAppointment(){
       ObservableList<String> rlist = FXCollections.observableArrayList();
       try{
           String report = "SELECT contacts.Contact_ID, Contact_Name, Appointment_ID, Title, Type, Description, Start, End, Customer_ID FROM appointments, contacts WHERE contacts.Contact_ID = appointments.Contact_ID GROUP BY Contact_ID, Appointment_ID";
           
           PreparedStatement ps = DBConnection.startConnection().prepareStatement(report);
           ResultSet rs = ps.executeQuery();
           
           while(rs.next()){
               String contactName = rs.getString("Contact_Name");
               int appointmentId = rs.getInt("Appointment_ID");
               String appointmentTitle = rs.getString("Title");
               String appointmentType = rs.getString("Type");
               String appointmentDescription = rs.getString("Description");
               LocalDateTime appointmentStart = rs.getTimestamp("Start").toLocalDateTime();
               LocalDateTime appointmentEnd = rs.getTimestamp("End").toLocalDateTime();
               int customerId = rs.getInt("Customer_ID");
               
               rlist.add(contactName + " | " + Integer.toString(appointmentId) + " | " + appointmentTitle + " | " + appointmentType + " | " + appointmentDescription + "\n" + appointmentStart.toString() + " | " + appointmentEnd.toString() + " | " + Integer.toString(customerId));
           }
       }
       catch(SQLException e){
           e.printStackTrace();
       }
    
       return rlist;
   }
   
}
