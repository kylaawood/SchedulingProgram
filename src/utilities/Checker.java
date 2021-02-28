/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;


import databaseClasses.FirstLevelDivisions;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javafx.collections.ObservableList;

/**
 *
 * @author Kwood
 */
public interface Checker {
    
    public abstract boolean matchCountry();
    
}
