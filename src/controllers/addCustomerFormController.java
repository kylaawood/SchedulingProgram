/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import databaseClasses.Countries;
import databaseClasses.FirstLevelDivisions;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
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
import utilities.DBCustomers;

/**
 * FXML Controller class
 *
 * @author Kwood
 */
public class addCustomerFormController implements Initializable {
    
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
     * Button that cancels adding a new customer.
     * @param event
     * @throws IOException 
     */
    @FXML
    void onActionCancelNewCustomer(ActionEvent event) throws IOException {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, rb.getString("This will clear all fields, would you like to continue?"));
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK){
        
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/views/mainScreen.fxml"), rb);
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
    
    /**
     * Adds the new customer to the database
     * @param event
     * @throws IOException 
     */
    @FXML
    void onActionSaveNewCustomer(ActionEvent event) throws IOException {
        
        try{
            String customerName = customerNameTxtBox.getText();
            String customerAddress = customerAddressTxtBox.getText();
            String customerPostalCode = postalCodeTxtBox.getText();
            String customerPhoneNumber = customerPhoneTxtBox.getText();
            int countryId = coutryIdComboBox.getValue().getCountryId();
            int divisionId = divisionIdComboBox.getValue().getDivisionId();
            
            
            DBCustomers.addCustomer(customerName, customerAddress, customerPostalCode, customerPhoneNumber, divisionId, countryId);
            
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/views/mainScreen.fxml"), ResourceBundle.getBundle("i18n/Nat", Locale.getDefault()));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter valid input for each text field.");
            alert.showAndWait();
        }
        
        

    }
    
    /**
     * On Action for the country combo box to refresh the Division combo box.
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
        // TODO
        
        coutryIdComboBox.setItems(DBCustomers.getAllCountries());
        //coutryIdComboBox.getSelectionModel().selectFirst();
        
        
        
    }    
    
}
