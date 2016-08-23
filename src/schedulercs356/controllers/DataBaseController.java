/*
 * The MIT License
 *
 * Copyright 2016 Michael Wallace, Mario Garcia.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package schedulercs356.controllers;

import java.lang.reflect.Array;
import java.util.LinkedList;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ListIterator;
import schedulercs356.entity.Account;
import schedulercs356.entity.DataBaseInterface;
import schedulercs356.entity.Meeting;
import schedulercs356.entity.Room;
import schedulercs356.entity.Schedule;

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
            obj.removeObject(obj, con);
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
    
    public Boolean doesTableExsist(String name){
        
        try{
            name = name.toUpperCase();
            DatabaseMetaData dbmd = con.getMetaData();
            ResultSet rs = dbmd.getTables(null, "ROOT", name, null);
            if (rs.next()) {
                return true;
            }
        }
        catch(SQLException err){
           // System.out.println(err.getMessage());
        }
        return false;
    }
        
    //called in entiy class
    public void createTable(String sql){
        try{
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
        }
        catch(SQLException err){
            System.out.println(err.getMessage());
        }
    }
    
    public void parseMeetingList(String sqlTable, LinkedList<Meeting> meetingList){
         try{
                Statement stmt = con.createStatement();
                String sql = "SELECT * FROM " + sqlTable;
                ResultSet rsMeetingList = stmt.executeQuery(sql);

                while(rsMeetingList.next()){
                    String temp = rsMeetingList.getString("MEETING_ID");
                    
                    Schedule schedule = getSchedule(temp);
                    String meetingID = "";
                    Integer roomID = -1;
                    Integer ownerID = -1;
                    String sq2 = "SELECT * FROM MEETING WHERE ID = ?";
                    PreparedStatement ps = con.prepareStatement(sq2);
                    ps.setString(1, temp);
                    ResultSet rs = ps.executeQuery();
                    if(rs.next())
                    {
                      meetingID = rs.getString("ID");
                      roomID = rs.getInt("ROOM_ID");            
                      ownerID = rs.getInt("OWNER_ID");
                    } 
                    
                    Room room = getRoom(roomID);
                    
                    Meeting meeting = new Meeting(meetingID,schedule,room,null,null,null,ownerID);
                    meetingList.add(meeting);
                }
            }
            catch(SQLException err){
                //throws exception if table has never been created can ignore
                //just use empty meeting list
                System.out.println(err.getMessage());
            }
    }
    
    public void parseAccountList(String sqlTable, LinkedList<Account> accountList){
         try{
                Statement stmt = con.createStatement();
                String sql = "SELECT * FROM " + sqlTable;
                ResultSet rsAccountList = stmt.executeQuery(sql);

                while(rsAccountList.next()){
                    Integer temp = rsAccountList.getInt("ACCOUNT_ID");
                    Account account = getAccount(temp);
                    accountList.add(account);
                }
            }
            catch(SQLException err){
                //throws exception if table has never been created can ignore
                //just use empty meeting list
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
            
            LinkedList<Meeting> meetingList = new LinkedList<>();
                        
            parseMeetingList(Room.queryRoomMeetingTable + roomid.toString(),meetingList);                

            Room newRoom = new Room(max,description,roomid,meetingList);
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
            
            String sql = "SELECT * FROM SCHEDULE WHERE OWNER_ID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, objID);
            ResultSet rs = ps.executeQuery();
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
            Integer id = rs.getInt("ID");
            
            LinkedList<Meeting> meetingList = new LinkedList<>();
            LinkedList<Meeting> invitedMeetingList = new LinkedList<>();
            
            parseMeetingList(Account.queryAccountInvitedMeetingTable + id.toString(), invitedMeetingList);
            parseMeetingList(Account.queryAccountMeetingTable + id.toString(), meetingList);
            
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
    
    public Account getAccount(String firstName){
        return getAccount(firstName, null);
    }
    
    
    public Account getAccount(String firstName, String lastName){
        try
        {
            String sql = "";
            ResultSet rs;
            //first name only
            if(lastName == null){   
                sql = "SELECT * FROM EMPLOYEES WHERE FIRST_NAME = ?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, firstName);
                rs = ps.executeQuery();
            }else{//first and last name
                sql = "SELECT * FROM EMPLOYEES WHERE FIRST_NAME = ? AND LAST_NAME = ?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, firstName);
                ps.setString(2, lastName);
                rs = ps.executeQuery();
            }
            
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
    
    
    public void updateAccountMeetingAttendenceStatus(Account account, Meeting meeting, Boolean attedningMeetingBoolean){
        
        LinkedList<Meeting> invitedMeetingListAccount = account.getInvitedMeetingList();
        LinkedList<Account> invitedMeetingListMeeting = meeting.getInvitedList();    
        
        for (int i = 0; i < invitedMeetingListAccount.size(); ++i) {
          Meeting temp = invitedMeetingListAccount.get(i);
          if (temp.getMeetingID().equals(meeting.getMeetingID())) {
            invitedMeetingListAccount.remove(i);
            break;
          }
        }
        
        for (int i = 0; i < invitedMeetingListMeeting.size(); ++i) {
          Account temp = invitedMeetingListMeeting.get(i);
          if (temp.getId() == account.getId()) {
            invitedMeetingListMeeting.remove(i);
            break;
          }
        }
        
        if(attedningMeetingBoolean){//meeting accepted
            LinkedList<Meeting> acceptedMeetingListAccount = account.getMeetingList();
            LinkedList<Account> acceptedMeetingListMeeting = meeting.getAcceptedList();
            
            acceptedMeetingListAccount.add(meeting);
            acceptedMeetingListMeeting.add(account);
            
            //account.setInvitedMeetingList(acceptedMeetingListAccount);
            //meeting.setAcceptedList(acceptedMeetingListMeeting);
            
        }
        else{//meeting rejected            
            LinkedList<Account> rejectedMeetingListMeeting = meeting.getRejectedList();
            rejectedMeetingListMeeting.add(account);
            //meeting.setRejectedList(rejectedMeetingListMeeting);
            
        }
        
        //account.setInvitedMeetingList(invitedMeetingListAccount);
        //meeting.setInvitedList(invitedMeetingListMeeting);
        
        this.updateObject(account);
        this.updateObject(meeting);       
            
    }
    
    /************
     * Meetings
     ***********/
public Meeting parseMeeting(ResultSet rs){        
        try
        {
            String meetingID = rs.getString("ID");
            Integer roomID = rs.getInt("ROOM_ID");            
            Integer ownerID = rs.getInt("OWNER_ID");
                        
            LinkedList<Account> invitedMeetingList = new LinkedList<>();
            LinkedList<Account> rejectedMeetingList = new LinkedList<>();
            LinkedList<Account> acceptedMeetingList = new LinkedList<>();
            
            parseAccountList(Meeting.queryMeetingAcceptedAccountTable + meetingID, acceptedMeetingList);
            parseAccountList(Meeting.queryMeetingInvitedTable + meetingID, invitedMeetingList);
            parseAccountList(Meeting.queryMeetingRejectedAccountTable + meetingID, rejectedMeetingList);
            
            
            Room room = getRoom(roomID);
            
            Schedule schedule = getSchedule(meetingID);
            
            Meeting meeting = new Meeting(meetingID, schedule, room, invitedMeetingList, acceptedMeetingList, rejectedMeetingList, ownerID);
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
            String sql = "SELECT * FROM MEETING WHERE ID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, objID);
            ResultSet rs = ps.executeQuery();
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



