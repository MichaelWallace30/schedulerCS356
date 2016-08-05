/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulercs356;

import java.util.LinkedList;

/**
 *
 * @author Michael Wallace
 * 
 * Needs:
 *      Schedule
 */
public class Room {
    private int maxOccupancy;
    private String description;    
    private Integer roomNumber;
    private LinkedList<String> meetingIDList;
    
    
    public Room(int maxOccupancy, String description, Integer roomNumber, LinkedList<String> meetingIDList){
        this.setMaxOccupancy(maxOccupancy);
        this.setDescription(description);
        this.setRoomNumber(roomNumber);
        this.setMeetingIDList(meetingIDList);
    }
    
    public void addMeetingID(String meetingID){
        meetingIDList.add(meetingID);
    }
    
    public void removeMeetinID(String meetingID){
        meetingIDList.remove(meetingID);
    }

    public int getMaxOccupancy() {
        return maxOccupancy;
    }

    public void setMaxOccupancy(int maxOccupancy) {
        this.maxOccupancy = maxOccupancy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public LinkedList<String> getMeetingIDList() {
        return meetingIDList;
    }

    public void setMeetingIDList(LinkedList<String> meetingIDList) {
        this.meetingIDList = meetingIDList;
    }
    
}
