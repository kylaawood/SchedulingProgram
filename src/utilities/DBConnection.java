/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;



//import com.sun.jdi.connect.spi.Connection;
import java.sql.Connection;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * Class for holding the connection pieces.
 * @author Kyla Wood
 */
public class DBConnection {
    
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String address = "//wgudb.ucertify.com:3306/";
    private static final String dbName = "WJ082m5";
    
    private static final String jdbcLink = protocol + vendor + address + dbName;
    
    private static final String mySQLJDBCDriver = "com.mysql.cj.jdbc.Driver";
    private static Connection conn = null;
    
    private static final String username = "U082m5";
    private static final String password = "53689198789";
    
    public static Connection startConnection() throws SQLException{
        try{
           Class.forName(mySQLJDBCDriver);
           conn = (Connection)DriverManager.getConnection(jdbcLink, username, password);
        }
        catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return conn;
        
    }
    
    public static void closeConnection(){
        
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        
        
    }
    
}
