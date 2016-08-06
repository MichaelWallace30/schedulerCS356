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
import java.sql.Timestamp;

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
    
    
     /*********************************************************
     * 
     * Database conversion of list to strings
     * 
     **********************************************************/
    static public String listToString(LinkedList<String> list){
        String newString = new String();
        
        while(!list.isEmpty())
        {
            newString += list.remove();
            if(!list.isEmpty())newString += ":;:";
        }        
        return newString;        
    }
    
    static public LinkedList<String> stringToList(String stringArray){
        String newString = "";
        
        String strArr[] = stringArray.split(":;:");
        LinkedList<String> newList = new LinkedList<>();        
        
        for(int x = 0; x < Array.getLength(strArr); x++)
        {
            newList.add(strArr[x]);
        }
        
        return newList;
    }

     /*********************************************************
     * 
     * Object calls for Room, Account, Schedule, Meeting
     * 
     **********************************************************/
    public void addToDataBase(DataBaseInterface obj){
        try
        {
            Statement stmt = con.createStatement();
            obj.addObject(obj, stmt);
        }
        catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }
    }
    
    public void removeObject(DataBaseInterface obj){
        try
        {
            Statement stmt = con.createStatement();
            obj.removeObject(obj, stmt);
        }
        catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }
    }
    
    public void updateObject(DataBaseInterface obj){
        try
        {            
            obj.updateObject(obj, con);
        }
        catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }
    }
        
    /*********************************************************
     * 
     * none object calls for Room, Account, Schedule, Meeting
     * 
     **********************************************************/
        
    /**********
     * ROOMS
     *********/
    public Room parseRoom(ResultSet rs){        
        try
        {
            Integer roomid = rs.getInt("ROOM_NUMBER");
            String description = rs.getString("DESCRIPTION");
            Integer max = rs.getInt("MAX_OCCUPANCY");
            String meetingList = rs.getString("MEETING_ID_LIST");

            LinkedList<String> lls = new LinkedList<>();
            lls = stringToList(meetingList);
            Room newRoom = new Room(max,description,roomid,lls);
            return newRoom; 

        }
        catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }
        return null;
    }
    
    public LinkedList<Room> getAllRooms( Statement stmt){
        try
        {
            LinkedList<Room> rooms = new LinkedList<>();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ROOMS");
            while(rs.next())
            {
               rooms.add(parseRoom(rs));
            }
            return rooms;
        }
        catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }
        return null;
    }
    
    
    public Room getRoom(Integer objID){         
        try
        {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ROOMS WHERE ROOM_NUMBER =" + objID);
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
    
    /***************
     * SCHEDULES
     ***************/
    public Schedule parseSchedule(ResultSet rs){        
        try
        {
            String onwerID = rs.getString("OWNER_ID");
            Timestamp startTime = rs.getTimestamp("START_TIME");
            Timestamp endTime = rs.getTimestamp("END_TIME");            
            
            
            Schedule schedule = new Schedule(onwerID, startTime.toLocalDateTime(), endTime.toLocalDateTime());
            return schedule; 

        }
        catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }
        return null;
    }
    
    public LinkedList<Schedule> getAllSchedules( Statement stmt){
        try
        {
            LinkedList<Schedule> schedules = new LinkedList<>();
            ResultSet rs = stmt.executeQuery("SELECT * FROM SCHEDULE");
            while(rs.next())
            {
                schedules.add(parseSchedule(rs));
            }
            return schedules;
        }
        catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }
        return null;
    }
    
    
    public Schedule getSchedule(Integer objID){         
        try
        {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM SCHEDULE WHERE OWNER_ID =" + objID);
                if(rs.next())
                {
                   return parseSchedule(rs);
                } 
        }        
        catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }
        return null;
    }
}



