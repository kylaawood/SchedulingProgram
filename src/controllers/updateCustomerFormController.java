/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import databaseClasses.Countries;
import databaseClasses.Customers;
import databaseClasses.FirstLevelDivisions;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import utilities.Checker;
import utilities.DBCustomers;

/**
 * FXML Controller class
 *
 * @author Kwood
 */
public class updateCustomerFormController implements Initializable {
    
    @FXML
    private TextField customerIdTxtBox;

    @FXML
    private TextField customerNameTxtBox;

    @FXML
    private TextField customerAddressTxtBox;

    @FXML
    private TextField postalCodeTxtBox;

    @FXML
    private TextField customerPhoneTxtBox;

    @FXML
    private ComboBox<FirstLevelDivisions> divisionIdComboBox;

    @FXML
    private ComboBox<Countries> coutryIdComboBox;

    Stage stage;
    Parent scene;
    ResourceBundle rb = ResourceBundle.getBundle("i18n/Nat", Locale.getDefault());
    
    
    /**
     * Cancels updating a customer and goes back to the main screen.
     * @param event 
     * @throws IOException 
     */
    @FXML
    void onActionCancelNewCustomer(ActionEvent event) throws IOException {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, rb.getString("Any unsaved changes will be lost, continue?"));
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK){
        
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/views/mainScreen.fxml"), ResourceBundle.getBundle("i18n/Nat", Locale.getDefault()));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }

    /**
     * Saves the updates to the customer
     * Lambda implemented here to provide a boolean value quickly.
     * @param event
     * @throws IOException 
     */
    @FXML
    void onActionSaveNewCustomer(ActionEvent event) throws IOException, InterruptedException {
        
        try{
            int customerId = Integer.valueOf(customerIdTxtBox.getText());
            String customerName = customerNameTxtBox.getText();
            String customerAddress = customerAddressTxtBox.getText();
            String customerPostalCode = postalCodeTxtBox.getText();
            String customerPhoneNumber = customerPhoneTxtBox.getText();
            int divisionId = divisionIdComboBox.getValue().getDivisionId();


            Checker check = ()->{
                for(FirstLevelDivisions division : DBCustomers.getAllDivisions(coutryIdComboBox.getValue().getCountryId())){
                    if(division.getDivisionId() == divisionIdComboBox.getValue().getDivisionId())
                        return true;
                }
                return false;
            };


            if(!check.matchCountry()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText(rb.getString("Please update Division or Country information."));
                alert.showAndWait();

                scene.wait();
            }


            DBCustomers.updateCustomer(customerId, customerName, customerAddress, customerPostalCode, customerPhoneNumber, divisionId);


            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/views/mainScreen.fxml"), ResourceBundle.getBundle("i18n/Nat", Locale.getDefault()));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please put valid input in Text Boxes.");
            alert.showAndWait();
        }
        

    }
    
    /**
     * Interacts with the main screen to get the customer selection
     * @param customer Customer you wish to update.
     */
    public void sendCustomer(Customers customer){
        customerIdTxtBox.setText(String.valueOf(customer.getCustomerId()));
        customerNameTxtBox.setText(customer.getCustomerName());
        customerAddressTxtBox.setText(customer.getCustomerAddress());
        postalCodeTxtBox.setText(customer.getCustomerPostalCode());
        customerPhoneTxtBox.setText(customer.getCustomerPhoneNumber());
        
        for(Countries country : DBCustomers.getAllCountries()){
            if(customer.getCountryId() == country.getCountryId())
                coutryIdComboBox.setValue(country);
        }
        
        for(FirstLevelDivisions division : DBCustomers.getAllDivisions(customer.getCountryId())){
            if(customer.getDivisionId() == division.getDivisionId())
                divisionIdComboBox.setValue(division);
        }
        
            
    }
    
    

    /**
     * Action event on the country combo box to refresh the division combo box.
     * @param event 
     */
    @FXML
    void onActionCountryComboBox(ActionEvent event) {
        
        divisionIdComboBox.setItems(DBCustomers.getAllDivisions(coutryIdComboBox.getValue().getCountryId()));

    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        coutryIdComboBox.setItems(DBCustomers.getAllCountries());
        
    }    
    
}
