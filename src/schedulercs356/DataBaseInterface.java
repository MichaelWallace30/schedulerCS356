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

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.LinkedList;


public interface DataBaseInterface {        
    
    public void addObject(DataBaseInterface obj,  Statement stmt)throws SQLException;
    
    public void removeObject(DataBaseInterface obj,  Statement stmt)throws SQLException;       
    
    public void updateObject(DataBaseInterface obj,  Connection con)throws SQLException;      
    
}
