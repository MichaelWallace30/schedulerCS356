/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulercs356;

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
    private LinkedList<Account> acceptedList;
    private LinkedList<Account> rejectedList;
    private Integer ownerID;
    
    public Meeting(){
        setMeetingID(UUID.randomUUID().toString());
    }
    
    public Meeting(String meetingID, Schedule schedule, Room room, 
            LinkedList<Account> invitedList, LinkedList<Account> acceptedList,
            LinkedList<Account> rejectedList){
        this.setMeetingID(meetingID);
        this.setSchedule(schedule);
        this.setRoom(room);
        this.setInvitedList(invitedList);
        this.setAcceptedList(acceptedList);
        this.setRejectedList(rejectedList);
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

        LinkedList<String> invitedStringList = new LinkedList<>();
        LinkedList<String> acceptedStringList = new LinkedList<>();
        LinkedList<String> rejectedStringList = new LinkedList<>();
        
        ListIterator<Account> it;

        it = invitedList.listIterator();
        while(it.hasNext()){
            invitedStringList.add(Integer.toString(it.next().getId()));
        }

        it = acceptedList.listIterator();
        while(it.hasNext()){
            acceptedStringList.add(Integer.toString(it.next().getId()));
        }
         
        it = rejectedList.listIterator();
        while(it.hasNext()){
            rejectedStringList.add(Integer.toString(it.next().getId()));
        }
         
        PreparedStatement ps = con.prepareStatement(
        "INSERT INTO MEETING" 
            +"(ID, ROOM_ID,INVITED_LIST, ACCEPTED_LIST,REJECTED_LIST,OWNER_ID) VALUES"
            + "(?,?,?,?,?,?)");        
        
        // set the preparedstatement parameters
        ps.setString(1, meeting.getMeetingID());
        ps.setInt(2,meeting.getRoom().getRoomNumber());
        ps.setString(3,DataBaseController.listToString(invitedStringList));
        ps.setString(4,DataBaseController.listToString(acceptedStringList));
        ps.setString(5,DataBaseController.listToString(rejectedStringList));
        ps.setInt(6,meeting.getOwnerID());

        // call executeUpdate to execute our sql update statement
        ps.executeUpdate();
        ps.close();          
    }
    
    @Override
    public void removeObject(DataBaseInterface obj,  Statement stmt)throws SQLException{
        Meeting meeting = (Meeting)obj;
        stmt.executeUpdate("DELETE FROM MEETING " + " WHERE ID = " +  meeting.getMeetingID());
    }
    
    @Override
    public Boolean updateObject(DataBaseInterface obj, Connection con)throws SQLException{
        Meeting meeting = (Meeting)obj;
        
        LinkedList<String> invitedStringList = new LinkedList<>();
        LinkedList<String> acceptedStringList = new LinkedList<>();
        LinkedList<String> rejectedStringList = new LinkedList<>();
        
        ListIterator<Account> it;

        it = invitedList.listIterator();
        while(it.hasNext()){
            invitedStringList.add(Integer.toString(it.next().getId()));
        }

        it = acceptedList.listIterator();
        while(it.hasNext()){
            acceptedStringList.add(Integer.toString(it.next().getId()));
        }
         
        it = rejectedList.listIterator();
        while(it.hasNext()){
            rejectedStringList.add(Integer.toString(it.next().getId()));
        }
        PreparedStatement ps = con.prepareStatement(
        "UPDATE MEETING SET ROOM_ID = ? ,INVITED_LIST = ?, ACCEPTED_LIST = ?,REJECTED_LIST = ?,OWNER_ID = ? WHERE ID = ?");
          
        // set the preparedstatement parameters        
        ps.setInt(1,meeting.getRoom().getRoomNumber());
        ps.setString(2,DataBaseController.listToString(invitedStringList));
        ps.setString(3,DataBaseController.listToString(acceptedStringList));
        ps.setString(4,DataBaseController.listToString(rejectedStringList));
        ps.setInt(5,meeting.getOwnerID());
        ps.setString(6, meeting.getMeetingID());
        
        // call executeUpdate to execute our sql update statement
        int i = ps.executeUpdate();
        ps.close();   
        
        if(i <= 0){
        return false;
        }
        return true;
    }
}
