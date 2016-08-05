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
import java.sql.PreparedStatement;
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
    
    
    /***********************************
     * 
     * Room Stuff
     **********************************/
    
    private Room parseRoom(ResultSet rs) throws SQLException{
        Integer roomid = rs.getInt("ROOM_NUMBER");
        String description = rs.getString("DESCRIPTION");
        Integer max = rs.getInt("MAX_OCCUPANCY");
        String meetingList = rs.getString("MEETING_ID_LIST");

        LinkedList<String> lls = new LinkedList<>();
        lls = stringToLinkedList(meetingList);

        Room newRoom = new Room(max,description,roomid,lls);
        return newRoom; 
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
         try
        {
            Statement stmt = con.createStatement();        
            stmt.executeUpdate("DELETE FROM ROOMS " + " WHERE ROOM_NUMBER = " +  room.getRoomNumber());
        }
        catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }
    }
    
    public Room getRoom(Integer roomID)
    {         
        try
        {
        Statement stmt = con.createStatement();        
        ResultSet rs = stmt.executeQuery("SELECT * FROM ROOMS WHERE ROOM_NUMBER =" + roomID);
                if(rs.next())
                {
                   return parseRoom(rs);
                }
        }                        
        catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }
        
        return null;
    }
        
    public LinkedList<Room> getRooms(){
        LinkedList<Room> rooms = new LinkedList<>();
        try
        {
            Statement stmt = con.createStatement();        
            ResultSet rs = stmt.executeQuery("SELECT * FROM ROOMS");
            while(rs.next())
            {
                rooms.add(parseRoom(rs));
            }
        }
        catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }
        return null;
    }
    
    public void updateRoom(Room room){
        try
        {
            PreparedStatement ps = con.prepareStatement(
            "UPDATE ROOMS SET DESCRIPTION = ?, MAX_OCCUPANCY = ?, MEETING_ID_LIST = ? WHERE ROOM_NUMBER = ?");

            // set the preparedstatement parameters
            ps.setString(1,room.getDescription());
            ps.setInt(2,room.getMaxOccupancy());
            ps.setString(3, listToString(room.getMeetingIDList()));
            ps.setInt(4,room.getRoomNumber());

            // call executeUpdate to execute our sql update statement
            ps.executeUpdate();
            ps.close();
        }
        catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }
    }
}



