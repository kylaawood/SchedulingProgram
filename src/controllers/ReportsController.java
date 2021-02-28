/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import utilities.DBGeneral;

/**
 * FXML Controller class
 *
 * @author Kwood
 */
public class ReportsController implements Initializable {
    
    @FXML
    private ListView<String> reportListView;

    @FXML
    private RadioButton typeMonthRB;

    @FXML
    private RadioButton contactAppointments;

    @FXML
    private RadioButton customerCountryRB;

    Stage stage;
    Parent scene;
    
    /**
     * Button for going back to the main screen.
     * @param event action event, typically a mouse click
     * @throws IOException 
     */
    @FXML
    void onActionBack(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/views/mainScreen.fxml"), ResourceBundle.getBundle("i18n/Nat", Locale.getDefault()));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Makes the report show appointments by contact in the list view.
     * @param event action event typically a mouse event
     */
    @FXML
    void onActionReportContactAppointments(ActionEvent event) {
        
        reportListView.setItems(DBGeneral.reportByContactAppointment());
        reportListView.refresh();

    }

    /**
     * Makes the report show amount of customers per country in the list view.
     * @param event action event typically mouse action
     */
    @FXML
    void onActionReportCustomerCountry(ActionEvent event) {
        
        reportListView.setItems(DBGeneral.reportByCustomerCountry());
        reportListView.refresh();

    }

    /**
     * Makes the report show by type and month in the list view.
     * @param event action event typically mouse action
     */
    @FXML
    void onActionReportTypeMonth(ActionEvent event) {
        
        reportListView.setItems(DBGeneral.reportByTypeMonth());
        reportListView.refresh();

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        typeMonthRB.setSelected(true);
        reportListView.setItems(DBGeneral.reportByTypeMonth());
        
    }    
    
}
