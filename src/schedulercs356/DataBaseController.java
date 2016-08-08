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
import java.util.ListIterator;

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

    public Integer login(String userName, String password)
    {
        try
        {
            Statement stmt = con.createStatement();
            String SQL = "SELECT * FROM EMPLOYEES WHERE USER_NAME='"+userName+"'";
            ResultSet rs = stmt.executeQuery(SQL);
            
            if(rs.next())
            {
                int recievedPass = rs.getInt("PASSWORD");
                int accountID = rs.getInt("ID");
                if(recievedPass == password.hashCode())
                {
                    return accountID;
                }
            }
        }
        catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }
        return -1;
    }    
    
    
     /*********************************************************
     * 
     * Database conversion of list to strings
     * 
     **********************************************************/
    static public String listToString(LinkedList<String> list){
        String newString = new String();
        
        
        while(list != null && !list.isEmpty())
        {
            newString += list.remove();
            if(!list.isEmpty())newString += ":;:";
        }        
        return newString;        
    }
    
    static public LinkedList<String> stringToList(String stringArray){
        String newString = "";
        
        LinkedList<String> newList = new LinkedList<>();  
        
        if(stringArray != null){
            String strArr[] = stringArray.split(":;:");

            for(int x = 0; x < Array.getLength(strArr); x++)
            {
                newList.add(strArr[x]);
            }
        }
        
        return newList;
    }

     /*********************************************************
     * 
     * Object calls for Room, Account, Schedule, Meeting
     * 
     **********************************************************/
    public Boolean addObject(DataBaseInterface obj){
        try
        {            
            obj.addObject(obj, con);
            
        }
        catch(SQLException err)
        {
            System.out.println(err.getMessage());
            return false;
        }
        return true;
    }
    
    public Boolean removeObject(DataBaseInterface obj){
        try
        {
            Statement stmt = con.createStatement();
            obj.removeObject(obj, stmt);
        }
        catch(SQLException err)
        {
            System.out.println(err.getMessage());
            return false;
        }
        return true;
    }
    
    public Boolean updateObject(DataBaseInterface obj){
        try
        {            
            Boolean addSuccess = obj.updateObject(obj, con);
            if(!addSuccess)return false;//no object to update
        }
        catch(SQLException err)
        {
            System.out.println(err.getMessage());
            return false;
        }
        return true;
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
    
    public LinkedList<Room> getAllRooms(){
        try
        {
            Statement stmt = con.createStatement();
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
    
    public LinkedList<Schedule> getAllSchedules(){
        try
        {
            Statement stmt = con.createStatement();
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
    
    
    public Schedule getSchedule(String objID){         
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
    
    
    /***************
     * ACCOUNTS
     ***************/
    public Account parseAccount(ResultSet rs){        
        try
        {
            Boolean admin = rs.getBoolean("ADMIN");
            Boolean employee = rs.getBoolean("EMPLOYEE");
            String firstName = rs.getString("FIRST_NAME");
            String lastName = rs.getString("LAST_NAME");
            String userName = rs.getString("USER_NAME");
            int password = rs.getInt("PASSWORD");
            String address = rs.getString("ADDRESS");
            int id = rs.getInt("ID");
            LinkedList<String> meetingIDList = stringToList(rs.getString("MEETING_ID_LIST"));
            LinkedList<String> invitedMeetingIDList = stringToList(rs.getString("INVITED_MEETING_ID_LIST"));
            
            
            ListIterator<String> it;
            
            LinkedList<Meeting> meetingList = new LinkedList<>();
            it = meetingIDList.listIterator();
            while(it.hasNext()){
                meetingList.add(getMeeting(it.next()));
            }
        
            LinkedList<Meeting> invitedMeetingList = new LinkedList<>();
            it = invitedMeetingIDList.listIterator();
            while(it.hasNext()){
                invitedMeetingList.add(getMeeting(it.next()));
            }
            
            Account account = new Account(firstName, lastName, address, id, userName, password, employee, admin, meetingList, invitedMeetingList);
            return account; 

        }
        catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }
        return null;
    }
    
    public LinkedList<Account> getAllAccounts(){
        try
        {
            Statement stmt = con.createStatement();
            LinkedList<Account> accounts = new LinkedList<>();
            ResultSet rs = stmt.executeQuery("SELECT * FROM EMPLOYEES");
            while(rs.next())
            {
                accounts.add(parseAccount(rs));
            }
            return accounts;
        }
        catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }
        return null;
    }
    
    
    public Account getAccount(Integer objID){         
        try
        {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM EMPLOYEES WHERE ID =" + objID);
                if(rs.next())
                {
                   return parseAccount(rs);
                } 
        }        
        catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }
        return null;
    }
    
    /************
     * Meetings
     ***********/
public Meeting parseMeeting(ResultSet rs){        
        try
        {
            String meetingID = rs.getString("ID");
            Integer roomID = rs.getInt("ROOM_ID");
            String invitedString = rs.getString("INVITED_LIST");
            String acceptedString = rs.getString("ACCEPTED_LIST");
            String rejectedString = rs.getString("REJECTED_LIST");
            Integer ownerID = rs.getInt("OWNER_ID");
                        
            LinkedList<String> invitedStringList = stringToList(invitedString);
            LinkedList<String> acceptedStringList = stringToList(acceptedString);
            LinkedList<String> rejectedStringList = stringToList(rejectedString);
            
            LinkedList<Account> invitedList = new LinkedList<>();
            LinkedList<Account> acceptedList = new LinkedList<>();
            LinkedList<Account> rejectedList = new LinkedList<>();
                               
            ListIterator<String> it;
            
            it = invitedStringList.listIterator();
            while(it.hasNext()){
                invitedList.add(getAccount(Integer.parseInt(it.next())));
            }

            it = acceptedStringList.listIterator();
            while(it.hasNext()){
                acceptedList.add(getAccount(Integer.parseInt(it.next())));
            }
            
            it = rejectedStringList.listIterator();
            while(it.hasNext()){
                rejectedList.add(getAccount(Integer.parseInt(it.next())));
            }
            
            Room room = getRoom(roomID);
            
            Schedule schedule = getSchedule(meetingID);
            
            Meeting meeting = new Meeting(meetingID, schedule, room, invitedList, acceptedList, rejectedList);
            return meeting; 

        }
        catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }
        return null;
    }
    
    public LinkedList<Meeting> getAllMeetings(){
        try
        {
            Statement stmt = con.createStatement();
            LinkedList<Meeting> meetings = new LinkedList<>();
            ResultSet rs = stmt.executeQuery("SELECT * FROM MEETING");
            while(rs.next())
            {
                meetings.add(parseMeeting(rs));
            }
            return meetings;
        }
        catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }
        return null;
    }
    
    
    public Meeting getMeeting(String objID){         
        try
        {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM MEETING WHERE ID =" + objID);
                if(rs.next())
                {
                   return parseMeeting(rs);
                } 
        }        
        catch(SQLException err)
        {
            System.out.println(err.getMessage());
        }
        return null;
    }
}



