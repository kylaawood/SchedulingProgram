
import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import utilities.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kyla Wood
 */

public class mainThread extends Application {
    
    /**
     *
     * @param stage 
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        
        
        Parent root = FXMLLoader.load(getClass().getResource("/views/loginScreen.fxml"), ResourceBundle.getBundle("i18n/Nat", Locale.getDefault()));
        
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
        
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, IOException {
        
        
        
        
        
        DBConnection.startConnection();
        
        launch(args);
        
        DBConnection.closeConnection();
    }
    
}
