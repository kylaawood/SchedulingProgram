/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import databaseClasses.Users;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import utilities.DBConnection;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;
import javafx.scene.control.Alert;
import utilities.DBAppointments;
import utilities.ScheduleInterface;

/**
 * FXML Controller class
 *
 * @author Kwood
 */
public class loginScreenController implements Initializable {
    
    @FXML
    private TextField usernameTxtBox;

    @FXML
    private TextField passwordTxtBox;
    
    @FXML
    private Label localTimeZoneLbl;

    /**
     * Exits the application.
     * @param event Action event to end the application.
     */
    @FXML
    void onActionExit(ActionEvent event) {
        DBConnection.closeConnection();
        
        System.exit(0);

    }
    
    ResourceBundle rb = ResourceBundle.getBundle("i18n/Nat", Locale.getDefault());
    Stage stage;
    Parent scene;
    //ResourceBundle rb = ResourceBundle.getBundle("utilities/Nar_fr.properties", Locale.getDefault());
    
    public int loginSuccessful(){
        for(Users user : DBAppointments.getAllUsers()){
            if(user.getUserName().equals(usernameTxtBox.getText()) && user.getPassword().equals(passwordTxtBox.getText())){
                
             return user.getUserId();   
            }
        }
        return 0;
    }
    
    /**
     * Button to log fully into the application.
     * @param event Action event to enter the main screen.
     * @throws IOException 
     */
    @FXML
    void onActionLogin(ActionEvent event) throws IOException {
        
        LocalDateTime compTime = LocalDateTime.now();
        String successfulAttempt = null;
        
        if(loginSuccessful() != 0){
            successfulAttempt = "Successful!";
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/views/mainScreen.fxml"), ResourceBundle.getBundle("i18n/Nat", Locale.getDefault()));
            stage.setScene(new Scene(scene));
            stage.show();
            
            if(DBAppointments.appointmentSender(compTime, loginSuccessful()) != null){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Alert Dialog");
                alert.setContentText(rb.getString("Appointment coming up soon:") + "\n" + DBAppointments.appointmentSender(compTime, loginSuccessful()));
                alert.showAndWait();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Alert Dialog");
                alert.setContentText(rb.getString("No appointments coming up soon!"));
                alert.showAndWait();
            }
        }
        
     
        
        
        else{
            successfulAttempt = "Failed";
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(rb.getString("Warning Dialog"));
            alert.setContentText(rb.getString("Username or Password incorrect") + "\n" + rb.getString("Please enter valid login credentials."));
            alert.showAndWait();
        }
        
        String fileName = "login_activity.txt";
        
        
        FileWriter fwriter = new FileWriter(fileName, true);

        PrintWriter outputFile = new PrintWriter(fwriter);
        
        
        outputFile.println("User: " + usernameTxtBox.getText() + " used password: " + passwordTxtBox.getText() + " attempted " + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + " " + successfulAttempt);
        
        outputFile.close();
    }

    
/*
    @FXML
    void onEnterActionLogin(ActionEvent event) {

    }
*/
    /**
     * Initializes the controller class.
     * A Lambda implemented here.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
        ScheduleInterface zoning = () -> {
        var zone = ZoneId.systemDefault().getId();
        localTimeZoneLbl.setText(rb.getString("Your time zone is:") + " " + zone);
    };
        
        zoning.timeZoneDisplay();
    }    
    
}
