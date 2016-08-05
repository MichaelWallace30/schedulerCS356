/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulercs356;

import java.lang.reflect.Array;
import java.util.LinkedList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 *
 * @author Michael Wallace
 */
public class DataBaseController {
    private String host = "jdbc:derby://localhost:1527/companyDataBase";
    private String uName = "root";
    private String uPass = "toor";
    private Connection con;
      
    
    public DataBaseController(){
        try
        {
        con = DriverManager.getConnection(host,uName, uPass); 
        }
        catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }                
    }



    public Boolean login(String userName, String password)
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
    
    public String listToString(LinkedList<String> list){
        String newString = new String();
        
        while(!list.isEmpty())
        {
            newString += list.remove();
            if(!list.isEmpty())newString += ":;:";
        }        
        return newString;        
    }
    
    public LinkedList<String> stringToLinkedList(String stringArray){
        String newString = "";
        
        String strArr[] = stringArray.split(":;:");
        LinkedList<String> newList = new LinkedList<>();        
        
        for(int x = 0; x < Array.getLength(strArr); x++)
        {
            newList.add(strArr[x]);
        }
        
        return newList;
    }
    
    public void addRoom(Room room){        
        room.getMaxOccupancy();
        room.getDescription(); 
        room.getRoomNumber();
        room.getMeetingIDList();
        
        String stringArray = listToString(room.getMeetingIDList());
        
        try
        {
            Statement stmt = con.createStatement();        
            String formatedString = "" + room.getRoomNumber() + ", '" + room.getDescription() + "', " + room.getMaxOccupancy() + ", '" + stringArray +"'";
                
            stmt.executeUpdate("INSERT INTO ROOMS " + "VALUES (" + formatedString + ")");
        }
        catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }
                
    }
    
    public void removeRoom(Room room){
        
    }
        
    public LinkedList<Room> getRooms(){
        return null;
    }
    
    public void updateRoom(Room room){
        
    }
}



