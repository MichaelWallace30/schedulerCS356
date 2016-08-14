/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulercs356.entity;

import schedulercs356.controllers.DataBaseController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.UUID;


/**
 *
 * @author Michael Wallace
 * 
 * Meeting ID = 0 + owner id + dateMonth + dateDay + dateTime is special case for non meeting schedule
 * New meetings will auto generate id
 * 
 * When creating new meeting use default constructor init uuid
 * then add the rest of the data can easily create another constructor but its already confusing
 * 
 */
public class Meeting implements DataBaseInterface{    
    private String meetingID;
    
    //Object variables
    private Schedule schedule;
    private Room room;
    
    private LinkedList<Account> invitedList;
    private String invitedMeetingListTableName;
    public static String queryMeetingInvitedTable ="MEETING_INVITED_ACCOUNT_TABLE_LIST_MEETING_ID_";    
    
    private LinkedList<Account> acceptedList;
    private String acceptedMeetingListTableName;
    public static String queryMeetingAcceptedAccountTable ="MEETING_ACCEPTED_ACCOUNT_TABLE_LIST_MEETING_ID_";    
    
    private LinkedList<Account> rejectedList;
    private String rejectedMeetingListTableName;
    public static String queryMeetingRejectedAccountTable ="MEETING_REJECTED_ACCOUNT_TABLE_LIST_MEETING_ID_";    
    
    private Integer ownerID;
    
    
    private static String CREATE_TABLE_SQL = "(ACCOUNT_ID INTEGER primary key)";
    
    public Meeting() {
        
        String meetingID = UUID.randomUUID().toString();
        //table name can't have '-' in it
        meetingID.replace("-", "");
        setMeetingID(meetingID);
                
        setAcceptedMeetingListTableName(queryMeetingAcceptedAccountTable + getMeetingID());
        setInvitedMeetingListTableName(queryMeetingInvitedTable + getMeetingID());
        setRejectedMeetingListTableName(queryMeetingRejectedAccountTable + getMeetingID());
        
        //create table if dne for meeting list
        DataBaseController db = new DataBaseController();
        if(!db.doesTableExsist(getAcceptedMeetingListTableName())){
            String sql = "CREATE TABLE " + getAcceptedMeetingListTableName() +CREATE_TABLE_SQL;
            db.createTable(sql);            
        }
        else{
            //ignore exception table already exsist
        }        
        //create table if dne for invited list
        if(!db.doesTableExsist(getInvitedMeetingListTableName())){
            String sql = "CREATE TABLE " + getInvitedMeetingListTableName() +CREATE_TABLE_SQL;
            db.createTable(sql);            
        }
        else{
            //ignore exception table already exsist
        }
        
        //create table if dne for invited list
        if(!db.doesTableExsist(getRejectedMeetingListTableName())){
            String sql = "CREATE TABLE " + getRejectedMeetingListTableName() +CREATE_TABLE_SQL;
            db.createTable(sql);            
        }
        else{
            //ignore exception table already exsist
        }
        
        
    }
    
    public Meeting(String meetingID, Schedule schedule, Room room, 
            LinkedList<Account> invitedList, LinkedList<Account> acceptedList,
            LinkedList<Account> rejectedList, Integer ownerID){
        this.setMeetingID(meetingID);
        this.setSchedule(schedule);
        this.setRoom(room);
        this.setInvitedList(invitedList);
        this.setAcceptedList(acceptedList);
        this.setRejectedList(rejectedList);
        this.setOwnerID(ownerID);
                        
        setAcceptedMeetingListTableName(queryMeetingAcceptedAccountTable + getMeetingID());
        setInvitedMeetingListTableName(queryMeetingInvitedTable + getMeetingID());
        setRejectedMeetingListTableName(queryMeetingRejectedAccountTable + getMeetingID());
        
         //create table if dne for meeting list
        DataBaseController db = new DataBaseController();
        if(!db.doesTableExsist(getAcceptedMeetingListTableName())){          
            String sql = "CREATE TABLE " + getAcceptedMeetingListTableName() + CREATE_TABLE_SQL;
            db.createTable(sql);            
        }
        //create table if dne for invited list
        if(!db.doesTableExsist(getInvitedMeetingListTableName())){
            String sql = "CREATE TABLE " + getInvitedMeetingListTableName() +CREATE_TABLE_SQL;
            db.createTable(sql);            
        }
        else{
            //ignore exception table already exsist
        }
        
        //create table if dne for invited list
        if(!db.doesTableExsist(getRejectedMeetingListTableName())){
            String sql = "CREATE TABLE " + getRejectedMeetingListTableName() +CREATE_TABLE_SQL;
            db.createTable(sql);            
        }
        else{
            //ignore exception table already exsist
        }
        
        
        
    }

    public String getMeetingID() {
        return meetingID;
    }

    public void setMeetingID(String meetingID) {
        this.meetingID = meetingID;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LinkedList<Account> getInvitedList() {
        return invitedList;
    }

    public void setInvitedList(LinkedList<Account> invitedList) {
        this.invitedList = invitedList;
    }

    public LinkedList<Account> getAcceptedList() {
        return acceptedList;
    }

    public void setAcceptedList(LinkedList<Account> acceptedList) {
        this.acceptedList = acceptedList;
    }

    public LinkedList<Account> getRejectedList() {
        return rejectedList;
    }

    public void setRejectedList(LinkedList<Account> rejectedList) {
        this.rejectedList = rejectedList;
    }

    public Integer getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(Integer ownerID) {
        this.ownerID = ownerID;
    }
    
        public String getInvitedMeetingListTableName() {
        return invitedMeetingListTableName;
    }

    public void setInvitedMeetingListTableName(String invitedMeetingListTableName) {
        this.invitedMeetingListTableName = invitedMeetingListTableName;
    }

    public String getAcceptedMeetingListTableName() {
        return acceptedMeetingListTableName;
    }

    public void setAcceptedMeetingListTableName(String acceptedMeetingListTableName) {
        this.acceptedMeetingListTableName = acceptedMeetingListTableName;
    }

    public String getRejectedMeetingListTableName() {
        return rejectedMeetingListTableName;
    }

    public void setRejectedMeetingListTableName(String rejectedMeetingListTableName) {
        this.rejectedMeetingListTableName = rejectedMeetingListTableName;
    }
    
        
    private Boolean addAccountTable(Connection con, Integer accountID, String listName){
        try
        {
            PreparedStatement ps = con.prepareStatement(
            "INSERT INTO " + listName 
                +"(MEETING_ID) VALUES"
                + "(?)");  

            ps.setInt(1,accountID);

            int i = ps.executeUpdate();
            ps.close();   

            if(i <= 0){
            return false;
            }
            return true;         
        }
        catch(SQLException err){
            //System.out.println(err.getMessage());
            //ignore duplicate excetions
        }
        return false;
    }
    
    
     @Override
    public void addObject(DataBaseInterface obj,  Connection con)throws SQLException{   
        Meeting meeting = (Meeting)obj;
        
        
        //update invited list for each account
        ListIterator<Account> itAccount;
        
        itAccount = invitedList.listIterator();
        while(itAccount.hasNext()){
            //ad meeting to invited list
            Account tempAccount = itAccount.next();
            
            LinkedList<Meeting> meetingInviteList  = tempAccount.getInvitedMeetingList();
            meetingInviteList.add(this);    
            
            tempAccount.setInvitedMeetingList(meetingInviteList);
            
            //update account
            DataBaseController dbController = new DataBaseController();
            dbController.updateObject(tempAccount);            
        }

        ListIterator<Account> it;
        it = acceptedList.listIterator();
        while(it.hasNext()){
            addAccountTable(con, it.next().getId(), acceptedMeetingListTableName);
        }
        
        it = invitedList.listIterator();
        while(it.hasNext()){
            addAccountTable(con, it.next().getId(), invitedMeetingListTableName);
        }
        
        it = rejectedList.listIterator();
        while(it.hasNext()){
            addAccountTable(con, it.next().getId(), rejectedMeetingListTableName);
        }
        

         
        PreparedStatement ps = con.prepareStatement(
        "INSERT INTO MEETING" 
            +"(ID, ROOM_ID, OWNER_ID) VALUES"
            + "(?,?,?,?,?,?)");        
        
        // set the preparedstatement parameters
        ps.setString(1, meeting.getMeetingID());
        
        if (meeting.getRoom() != null) {
          ps.setInt(2,meeting.getRoom().getRoomNumber());
        } else {
          ps.setInt(2, 0);
        }

        ps.setInt(3,meeting.getOwnerID());

        // call executeUpdate to execute our sql update statement
        ps.executeUpdate();
        ps.close();          
    }
    
    @Override
    public void removeObject(DataBaseInterface obj,  Statement stmt)throws SQLException{
        Meeting meeting = (Meeting)obj;
        stmt.executeUpdate("DELETE FROM MEETING " + " WHERE ID = " +  meeting.getMeetingID());
        
        String sql = "DROP TABLE " + this.acceptedMeetingListTableName;
        stmt.executeUpdate(sql);
        
        String sq2 = "DROP TABLE " + this.invitedMeetingListTableName;
        stmt.executeUpdate(sq2);
        
        String sq3 = "DROP TABLE " + this.rejectedMeetingListTableName;
        stmt.executeUpdate(sq3);
    }
    
    @Override
    public Boolean updateObject(DataBaseInterface obj, Connection con)throws SQLException{
        Meeting meeting = (Meeting)obj;
        
        
        //update invited list for each account
        ListIterator<Account> itAccount;
        
        itAccount = invitedList.listIterator();//check if people need new invited
        while(itAccount.hasNext()){
            //ad meeting to invited list
            Account tempAccount = itAccount.next();

                LinkedList<Meeting> meetingInviteList  = tempAccount.getInvitedMeetingList();
                meetingInviteList.add(this);    

                tempAccount.setInvitedMeetingList(meetingInviteList);

                //update account
                DataBaseController dbController = new DataBaseController();
                dbController.updateObject(tempAccount);            
        }
        
        ListIterator<Account> it;
        it = acceptedList.listIterator();
        while(it.hasNext()){
            addAccountTable(con, it.next().getId(), acceptedMeetingListTableName);
        }
        
        it = invitedList.listIterator();
        while(it.hasNext()){
            addAccountTable(con, it.next().getId(), invitedMeetingListTableName);
        }
        
        it = rejectedList.listIterator();
        while(it.hasNext()){
            addAccountTable(con, it.next().getId(), rejectedMeetingListTableName);
        }
        
        
        PreparedStatement ps = con.prepareStatement(
        "UPDATE MEETING SET ROOM_ID = ? ,OWNER_ID = ? WHERE ID = ?");
          
        // set the preparedstatement parameters        
        ps.setInt(1,meeting.getRoom().getRoomNumber());
        ps.setInt(2,meeting.getOwnerID());
        ps.setString(3, meeting.getMeetingID());
        
        // call executeUpdate to execute our sql update statement
        int i = ps.executeUpdate();
        ps.close();   
        
        if(i <= 0){
        return false;
        }
        return true;
    }


    
}
