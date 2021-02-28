/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseClasses;



/**
 * User class for use in combo boxes. No setters necessary.
 * @author Kyla Wood
 */
public class Users {
   
    private int userId;
    private String userName;
    private String password;
   
    /**
     * Constructor of User class.
     * @param userId ID of user, int value provided.
     * @param userName Name of user, string value provided.
     */
    public Users(int userId, String userName, String password){
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }
   
    /**
     *
     * @return User ID to get.
     */
    public int getUserId(){
        return userId;
    }
   
    /**
     *
     * @return User name to get.
     */
    public String getUserName(){
        return userName;
    }
    
    public String getPassword(){
        return password;
    }
    
    /**
     * Override for To String method  so Combo Boxes display properly
     * @return String of user object
     */
   @Override
    public String toString(){
        return (Integer.toString(userId) + " " + userName);
    }
}