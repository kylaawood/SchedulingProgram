/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;


import databaseClasses.Contacts;
import databaseClasses.Customers;
import databaseClasses.Users;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import utilities.DBAppointments;
import utilities.DBCustomers;
import java.lang.String;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static java.time.format.DateTimeFormatter.ofLocalizedTime;
import java.util.Locale;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import utilities.ScheduleInterface;

/**
 * FXML Controller class
 *
 * @author Kwood
 */
public class addAppointmentFormController implements Initializable {
    
    @FXML
    private DatePicker startDatePicker;

    @FXML
    private TextField appointmentIdTxtBox;

    @FXML
    private TextField titleTxtBox;

    @FXML
    private TextField descriptionTxtBox;

    @FXML
    private TextField locationTxtBox;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private ComboBox<Customers> customerIdComboBox;

    @FXML
    private ComboBox<Contacts> contactComboBox;

    @FXML
    private ComboBox<LocalTime> startTimeComboBox;

    @FXML
    private ComboBox<LocalTime> endTimeComboBox;

    @FXML
    private ComboBox<Users> userComboBox;
    
    Stage stage;
    Parent scene;
    ResourceBundle rb = ResourceBundle.getBundle("i18n/Nat", Locale.getDefault());
    
    
    /**
     * Action event to keep combo boxes up to date.
     * 
     * @param event action event, typically a mouse click
     */
    @FXML
    void onActionRefreshComboBoxes(ActionEvent event) {
        
        
        
        LocalDate date = startDatePicker.getValue();
        final LocalTime open = LocalTime.of(8, 0);
        final LocalTime close = LocalTime.of(22, 0);
        
        LocalDateTime LDTOpen = LocalDateTime.of(date, open);
        ZonedDateTime zopen = ZonedDateTime.of(LDTOpen, ZoneId.of("UTC-05:00"));
        ZonedDateTime openZone = zopen.withZoneSameInstant(ZoneId.systemDefault());
        LocalTime openTime = openZone.toLocalTime();
        
        

        LocalDateTime LDTClose = LocalDateTime.of(date, close);
        ZonedDateTime zclose = ZonedDateTime.of(LDTClose, ZoneId.of("UTC-05:00"));
        ZonedDateTime closeZone = zclose.withZoneSameInstant(ZoneId.systemDefault());
        final LocalTime closeTime = closeZone.toLocalTime();
        

        while(openTime.isBefore(closeTime)){
            endTimeComboBox.getItems().add(openTime);
            startTimeComboBox.getItems().add(openTime);
            openTime = openTime.plusMinutes(30);
        }
        while(openTime.isBefore(closeTime.plusSeconds(1))){
            
            endTimeComboBox.getItems().add(openTime);
            openTime = openTime.plusMinutes(30);
        }
    }
    

    /**
     * Cancel button to return user to the main screen.
     * @param event action event, typically a mouse click.
     * @throws IOException 
     */
    @FXML
    void onActionCancelNewApointment(ActionEvent event) throws IOException {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, rb.getString("Any unsaved changes will be lost, continue?"));
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK){
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/views/mainScreen.fxml"), rb);
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Makes it so save button actually passes information into the DB
     * @param event action event, typically a mouse click.
     * @throws IOException 
     * @throws InterruptedException 
     */
    @FXML
    void onActionSaveNewAppointment(ActionEvent event) throws IOException, InterruptedException {
        String appointmentTitle = titleTxtBox.getText();
        String appointmentDescription = descriptionTxtBox.getText();
        String appointmentLocation = locationTxtBox.getText();
        String appointmentType = typeComboBox.getValue();
        LocalDate appointmentDate = startDatePicker.getValue();
        LocalTime appointmentStart = startTimeComboBox.getValue();
        LocalTime appointmentEnd = endTimeComboBox.getValue();
        int customerId = customerIdComboBox.getValue().getCustomerId();
        int userId = userComboBox.getValue().getUserId();
        int contactId = contactComboBox.getValue().getContactId();
        
        if(DBCustomers.availabilityChecker(LocalDateTime.of(appointmentDate, appointmentStart), customerId)){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText(rb.getString("Customer is busy during that time, please reschedule."));
            alert.showAndWait();
            
            scene.wait();
        }
        if(appointmentEnd.isBefore(appointmentStart)){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText(rb.getString("Start time must be before end time."));
            alert.showAndWait();
            
            scene.wait();
        }
        
        
        DBAppointments.addAppointment(appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, appointmentDate, appointmentStart, appointmentEnd, customerId, userId, contactId);
        
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/views/mainScreen.fxml"), rb);
        stage.setScene(new Scene(scene));
        stage.show();

    }
    
    /**
     * Makes an ObservableList of types of appointments.
     * @return Observable list of strings.
     */
    private ObservableList<String> getAllTypes(){
        ObservableList<String> typeString = FXCollections.observableArrayList(rb.getString("Presentation"), rb.getString("Follow Up"), rb.getString("Closing"), rb.getString("Other"));
        return typeString;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        /*
        typeString.add("Presentation");
        typeString.add("Follow Up");
        typeString.add("Closing");
        typeString.add("Other");
*/
        typeComboBox.setItems(getAllTypes());

  
        
        userComboBox.setItems(DBAppointments.getAllUsers());
        contactComboBox.setItems(DBAppointments.getAllContacts());
        customerIdComboBox.setItems(DBCustomers.getAllCustomers());
        
        
        
    }    
    
}
