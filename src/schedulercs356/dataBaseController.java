/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulercs356;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 *
 * @author Michael Wallace
 */
public class dataBaseController {
    String host = "jdbc:derby://localhost:1527/companyDataBase";
    String uName = "root";
    String uPass = "toor";
    Connection con;
    
    
    Boolean login(String userName, String password)
    {
        try
        {
            Statement stmt = con.createStatement();
            String SQL = "SELECT * FROM EMPLOYEES WHERE USER_NAME='"+userName+"'";
            ResultSet rs = stmt.executeQuery(SQL);
            
            if(rs.next())
            {
                int recievedPass = rs.getInt("PASSWORD");
                if(recievedPass == password.hashCode())
                {
                    return true;
                }
            }
        }
        catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }
        return false;
    }
    
    dataBaseController(){
        try
        {
        con = DriverManager.getConnection(host,uName, uPass); 
        }
        catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }
                
    }
}


