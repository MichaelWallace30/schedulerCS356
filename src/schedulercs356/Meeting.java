/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulercs356;

import java.util.LinkedList;
import java.util.UUID;

/**
 *
 * @author Michael Wallace
 * 
 * Meeting ID = 0 + onwer id + dateMonth + dateDay + dateTime is special case for non meeting schedule
 * New meetings will auto generate id
 * 
 */
public class Meeting {    
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
    
    public Meeting(String meetingID){
        this.setMeetingID(meetingID);
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
    
}
