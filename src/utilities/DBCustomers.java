/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import databaseClasses.Appointments;
import databaseClasses.Countries;
import databaseClasses.Customers;
import databaseClasses.FirstLevelDivisions;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class for holding SQL statements as they pertain to Customers and add/update/delete Customer.
 * @author Kwood
 */
public class DBCustomers {
    
    
    
    /**
     * SQL statement execution for turning MySQL table into Observable Array List
     * @return an Observable array list of all customers.
     */
    public static ObservableList<Customers> getAllCustomers(){
        ObservableList <Customers> customerList = FXCollections.observableArrayList();
        try{
            
            String cust = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, customers.Division_ID, first_level_divisions.COUNTRY_ID" +
                    " from customers, first_level_divisions, countries where first_level_divisions.COUNTRY_ID = countries.COUNTRY_ID and first_level_divisions.Division_ID = customers.Division_ID";
            PreparedStatement ps = DBConnection.startConnection().prepareStatement(cust);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerPostalCode = rs.getString("Postal_Code");
                String customerPhoneNumber = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");
                int countryId = rs.getInt("COUNTRY_ID");
                
                customerList.add(new Customers(customerId, customerName, customerPostalCode, customerAddress, customerPhoneNumber, countryId, divisionId));
                
                
                
            }
            
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return customerList;
    }
   
    /**
     * SQL Statement executor for adding a Customer.
     * @param customerName Name of customer to add.
     * @param customerAddress Address of customer to add.
     * @param customerPostalCode Postal Code of customer to add.
     * @param customerPhoneNumber Phone number of customer to add.
     * @param divisionId Division customer resides in.
     * @param countryId Country customer resides in.
     */
    public static void addCustomer(String customerName, String customerAddress, String customerPostalCode, String customerPhoneNumber, int divisionId, int countryId){
        try{
            String cust = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Division_ID)" +
                    "VALUES(?, ?, ?, ?, ?)";
            
            PreparedStatement ps = DBConnection.startConnection().prepareStatement(cust);
            
            ps.setString(1, customerName);
            ps.setString(2, customerAddress);
            ps.setString(3, customerPostalCode);
            ps.setString(4, customerPhoneNumber);
            ps.setInt(5, divisionId);
            //ps.setInt(6, countryId);
            
            ps.execute();
            
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    /**
     * SQL Statement executor for updating a customer
     * @param customerId ID of customer being updated (non editable)
     * @param customerName Name of customer being updated.
     * @param customerAddress Address of customer being updated.
     * @param customerPostalCode Postal Code of customer being updated.
     * @param customerPhoneNumber Phone number of customer being updated.
     * @param divisionId Division of customer being updated.
     */
    public static void updateCustomer(int customerId, String customerName, String customerAddress, String customerPostalCode, String customerPhoneNumber, int divisionId){
        
        try{
            
            String c = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
                    
            PreparedStatement ps = DBConnection.startConnection().prepareStatement(c);
            
            ps.setInt(6, customerId);
            ps.setString(1, customerName);
            ps.setString(2, customerAddress);
            ps.setString(3, customerPostalCode);
            ps.setString(4, customerPhoneNumber);
            ps.setInt(5, divisionId);
            
            ps.execute();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    /**
     * SQL Statement executor to make an Observable Array list of all relevant countries.
     * Please note more countries can be added as needed.
     * @return A list of all countries we do business with.
     */
    public static ObservableList<Countries> getAllCountries() {
        ObservableList<Countries> countryList = FXCollections.observableArrayList();
        try{
            String c = "SELECT * FROM countries";
                    
            PreparedStatement ps = DBConnection.startConnection().prepareStatement(c);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int countId = rs.getInt("Country_ID");
                String countName = rs.getString("Country");
                
                countryList.add(new Countries(countId, countName));
            }
            
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return countryList;
    }
    
    /**
     * SQL Statement executor to get all divisions within a chosen country.
     * @param countId Indicates which country the divisions will be in.
     * @return A list of all the divisions within one country.
     */
    public static ObservableList<FirstLevelDivisions> getAllDivisions(int countId) {
        ObservableList<FirstLevelDivisions> divisionList = FXCollections.observableArrayList();
        try{
            
            String c = "SELECT Division_ID, Division FROM first_level_divisions WHERE COUNTRY_ID = ?";
            
            
            PreparedStatement ps = DBConnection.startConnection().prepareStatement(c);
            ps.setInt(1, countId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                
                divisionList.add(new FirstLevelDivisions(divisionId, divisionName));
            }
            
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return divisionList;
    }
    
    /**
     * SQL Statement executor to delete a customer using the customer ID.
     * @param customerId Customer ID of the customer you wish to Delete.
     * @return Boolean of true if deleted and false if not.
     */
    public static boolean deleteCustomer(int customerId){
        try{
            String a = "DELETE FROM appointments WHERE customer_ID = ?";
            String c = "DELETE FROM customers WHERE customer_ID = ?";
            
            PreparedStatement psa = DBConnection.startConnection().prepareStatement(a);
            PreparedStatement ps = DBConnection.startConnection().prepareStatement(c);
            
            psa.setInt(1, customerId);
            psa.execute();
            
            ps.setInt(1, customerId);
            ps.execute();
            return true;
        }
        catch(SQLException e){
            
        }
        return false;
    }
    
    public static ObservableList<Appointments> getAllCustomerAppointments(int custId){
        ObservableList <Appointments> appointmentList = FXCollections.observableArrayList();
        try{
            
            String appt = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, appointments.Customer_ID, appointments.User_ID, appointments.Contact_ID" + 
                    " FROM appointments, customers, users, contacts WHERE customers.Customer_ID = appointments.Customer_ID AND users.User_ID = appointments.User_ID AND contacts.Contact_ID = appointments.Contact_ID AND appointments.Customer_ID = ?";
            PreparedStatement ps = DBConnection.startConnection().prepareStatement(appt);
            
            ps.setInt(1, custId);
            
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
    
    public static boolean availabilityChecker(LocalDateTime desiredStart, int custId){
        for(Appointments appt : getAllCustomerAppointments(custId)){
            if((LocalDateTime.of(appt.getAppointmentDate(), appt.getAppointmentStart()).isEqual(desiredStart) || LocalDateTime.of(appt.getAppointmentDate(), appt.getAppointmentStart()).isBefore(desiredStart)) &&
                (LocalDateTime.of(appt.getAppointmentDate(), appt.getAppointmentEnd()).isAfter(desiredStart) || LocalDateTime.of(appt.getAppointmentDate(), appt.getAppointmentEnd()).isEqual(desiredStart))){
                    return true;
            }
        }
        return false;
    }
}
