package schedulercs356;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Michael Wallace
 */


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public interface DataBaseInterface {        
    
    public void addObject(DataBaseInterface obj,  Connection con)throws SQLException;
    
    public void removeObject(DataBaseInterface obj,  Statement stmt)throws SQLException;       
    
    public void updateObject(DataBaseInterface obj,  Connection con)throws SQLException;      
    
}
