/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import databaseClasses.Appointments;
import databaseClasses.Contacts;
import databaseClasses.Customers;
import databaseClasses.DisplayAppointments;
import databaseClasses.FirstLevelDivisions;
import databaseClasses.Users;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utilities.DBAppointments;
import utilities.DBConnection;
import utilities.DBCustomers;

/**
 * FXML Controller class for the main screen
 *
 * @author Kwood
 */
public class mainScreenController implements Initializable {
    
    @FXML
    private TableView<DisplayAppointments> appointmentTableView;

    @FXML
    private TableColumn<DisplayAppointments, Integer> appointmentIdCol;

    @FXML
    private TableColumn<DisplayAppointments, String> titleCol;

    @FXML
    private TableColumn<DisplayAppointments, String> descriptionCol;

    @FXML
    private TableColumn<DisplayAppointments, String> locationCol;

    @FXML
    private TableColumn<DisplayAppointments, String> contactCol;

    @FXML
    private TableColumn<DisplayAppointments, String> appointmentTypeCol;

    @FXML
    private TableColumn<DisplayAppointments, LocalDate> dateCol;

    @FXML
    private TableColumn<DisplayAppointments, LocalTime> startTimeCol;

    @FXML
    private TableColumn<DisplayAppointments, LocalTime> endTimeCol;

    @FXML
    private TableColumn<DisplayAppointments, String> apptCustomerIdCol;
    
    @FXML
    private TableColumn<Appointments, String> customerIdCol;

    @FXML
    private TableView<Customers> customerTableView;

    @FXML
    private TableColumn<Customers, String> customerNameCol;

    @FXML
    private TableColumn<Customers, String> cusstomerAddressCol;

    @FXML
    private TableColumn<Customers, String> postalCodeCol;

    @FXML
    private TableColumn<Customers, String> customerPhoneCol;
/*
  
    @FXML
    private TableColumn<Customers, String> createdByCol;
*/
    @FXML
    private TableColumn<Customers, Integer> divisionIdCol;
    
    @FXML
    private TableColumn<Customers, Integer> custCountryCol;

    @FXML
    private RadioButton weekRB;
    
    @FXML
    private RadioButton allRB;

    /**
     * Radio button controls for which appointments you want to see.
     */
    @FXML
    private ToggleGroup appointmentShowToggle;

    @FXML
    private RadioButton monthRB;
    
    Stage stage;
    Parent scene;
    ResourceBundle rb = ResourceBundle.getBundle("i18n/Nat", Locale.getDefault());
    
    /**
     * Button to add an appointment.
     * @param event Action Event to trigger the add appointment scene.
     * @throws IOException 
     */
    @FXML
    void onActionAddAppointment(ActionEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/views/addAppointmentForm.fxml"), ResourceBundle.getBundle("i18n/Nat", Locale.getDefault()));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Button to add a customer.
     * @param event Action Event to trigger the add customer scene.
     * @throws IOException 
     */
    @FXML
    void onActionAddCustomer(ActionEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/views/addCustomerForm.fxml"), ResourceBundle.getBundle("i18n/Nat", Locale.getDefault()));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Button to delete an appointment.
     * @param event Action Event to delete an appointment.
     * @throws IOException 
     */
    @FXML
    void onActionDeleteAppointment(ActionEvent event) throws IOException {
        try{
            int appointmentId = 0;
            String appointmentTitle = matchAppointment(appointmentTableView.getSelectionModel().getSelectedItem()).getAppointmentTitle();
            appointmentId = matchAppointment(appointmentTableView.getSelectionModel().getSelectedItem()).getAppointmentId();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, rb.getString("This will delete selected appointment, do you want to continue?"));

            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK){
                DBAppointments.deleteAppointment(appointmentId);

                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle("Information Dialog");
                info.setContentText(appointmentTitle + " Has been deleted.");
                info.showAndWait();

            }
            appointmentTableView.setItems(DisplayAppointments.displayAppointments()); 
        }
        catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please make a selection.");
            alert.showAndWait();
        } 

    }

    /**
     * Button to delete a customer
     * @param event Action Event to delete an appointment.
     */
    @FXML
    void onActionDeleteCustomer(ActionEvent event) {
        try{
            int customerId = 0;
            String customerName = customerTableView.getSelectionModel().getSelectedItem().getCustomerName();
            customerId = customerTableView.getSelectionModel().getSelectedItem().getCustomerId();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, rb.getString("This will delete selected customer and all associated appointments, do you want to continue?"));

            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK){
                DBCustomers.deleteCustomer(customerId);

                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle("Information Dialog");
                info.setContentText(customerName + " Has been deleted.");
                info.showAndWait();
            }
            customerTableView.setItems(DBCustomers.getAllCustomers());
            appointmentTableView.setItems(DisplayAppointments.displayAppointments());
        }
        catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please make a selection.");
            alert.showAndWait();
        }
       
    }

    /**
     * Button to edit an appointment
     * @param event Action Event to trigger the update appointment scene.
     * @throws IOException 
     */
    @FXML
    void onActionEditAppointment(ActionEvent event) throws IOException {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/updateAppointmentForm.fxml"));
            loader.setResources(rb);
            loader.load();

            updateAppointmentFormController UAFController = loader.getController();
            UAFController.sendAppointment(matchAppointment(appointmentTableView.getSelectionModel().getSelectedItem()));


            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        }
        catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please make a selection.");
            alert.showAndWait();
        }
        
    }

    /**
     * Exit button.
     * @param event 
     */
    @FXML
    void onActionExit(ActionEvent event) {
        DBConnection.closeConnection();
        
        System.exit(0);

    }
    
    /**
     * Pulls up the reports screen.
     * @param event Action event to trigger the report scene.
     * @throws IOException 
     */
    @FXML
    void onActionShowReports(ActionEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/views/Reports.fxml"), ResourceBundle.getBundle("i18n/Nat", Locale.getDefault()));
        stage.setScene(new Scene(scene));
        stage.show();

    }
    
    /**
     * Function used here to retrieve to appointment you intend to update/delete
     * Lambda implemented here for clean and short appointment retrieval
     * @param display DisplayAppointments selection from the table
     * @return Appointment that matches the one you select.
     */
    Appointments matchAppointment(DisplayAppointments display){
        Appointments result = DBAppointments.getAllAppointments().stream()
            .filter(a -> a.getAppointmentId() == display.getAppointmentId())
            .findFirst().get();
        return result;
    }

    /**
     * Button to update customer.
     * @param event Action Event to trigger the update customer scene.
     * @throws IOException 
     */
    @FXML
    void onActionUpdateCustomer(ActionEvent event) throws IOException {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/updateCustomerForm.fxml"));
            loader.setResources(rb);
            loader.load();

            updateCustomerFormController UCFController = loader.getController();
            UCFController.sendCustomer(customerTableView.getSelectionModel().getSelectedItem());


            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please make a selection.");
            alert.showAndWait();
        }
        

    }
    
    /**
     * Radio button to display all appointments
     * @param event Action event to change the table view to all appointments.
     */
    @FXML
    void allAppointmentRB(ActionEvent event) {
        if(allRB.isSelected()){
            
            appointmentTableView.setItems(DisplayAppointments.displayAppointments());
            appointmentTableView.refresh();
            
        }
    }

    /**
     * Radio button event to display all appointments in the upcoming month.
     * @param event Action event to change the table view to the upcoming appointments within the month.
     */
    @FXML
    void monthAppointmentRB(ActionEvent event) {
        LocalDateTime compTime = LocalDateTime.now();
        if(monthRB.isSelected()){
            appointmentTableView.setItems(DBAppointments.getAppointmentsForMonth(compTime));
            appointmentTableView.refresh();
            
        }
    }
    
    /**
     * Radio Button event to display all appointments in the upcoming week.
     * @param event Action event to change the table view to the upcoming appointments within the week.
     */
    @FXML
    void weekAppointmentRB(ActionEvent event) {
        LocalDateTime compTime = LocalDateTime.now();
        if(weekRB.isSelected()){
            appointmentTableView.setItems(DBAppointments.getAppointmentsForWeek(compTime));
            appointmentTableView.refresh();
            
        }
    }
    
    

    /**
     * Initializes the controller class.
     * Had issues getting the tableview to display correctly, this is the stackoverflow page that helped https://stackoverflow.com/questions/54083513/javafx-populating-tableview-with-models-from-different-classes
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
        appointmentShowToggle.selectToggle(allRB);
        
        customerTableView.setItems(DBCustomers.getAllCustomers());
        
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        cusstomerAddressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));
        divisionIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
        custCountryCol.setCellValueFactory(new PropertyValueFactory<>("countryId"));
        
        appointmentTableView.setItems(DisplayAppointments.displayAppointments());
        
        
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
        apptCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));  
        
    }    
    
}
