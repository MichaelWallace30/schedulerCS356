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
import static schedulercs356.DataBaseController.listToString;


/**
 *
 * @author Michael Wallace
 *  
 */
public class Room implements DataBaseInterface {
    private int maxOccupancy;
    private String description;    
    private Integer roomNumber;
    private LinkedList<Meeting> meetingList;
    
    
    
    public Room(int maxOccupancy, String description, Integer roomNumber, LinkedList<Meeting> meetingList){
        this.setMaxOccupancy(maxOccupancy);
        this.setDescription(description);
        this.setRoomNumber(roomNumber);
        this.setMeetingList(meetingList);

    }
    
    public void addMeeting(Meeting meeting){
        meetingList.add(meeting);
    }
    
    public void removeMeeting(String meeting){
        meetingList.remove(meeting);
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

    public LinkedList<Meeting> getMeetingList() {
        return meetingList;
    }

    public void setMeetingList(LinkedList<Meeting> meetingList) {
         
        if(meetingList == null){
            this.meetingList = new LinkedList<>();
        }
        else{            
            this.meetingList = meetingList;
         }
    }
      
  
    @Override
    public void addObject(DataBaseInterface obj,  Connection con)throws SQLException{   
     Room room = (Room)obj;
        PreparedStatement ps = con.prepareStatement(
        "INSERT INTO ROOMS" 
            +"(ROOM_NUMBER, DESCRIPTION, MAX_OCCUPANCY, MEETING_ID_LIST) VALUES"
            + "(?,?,?,?)");       
        
        
        
        
            
        LinkedList<String> meetingIDList = new LinkedList<>();
        
        ListIterator<Meeting> it;
        it = meetingList.listIterator();
        while(it.hasNext()){
            meetingIDList.add(it.next().getMeetingID());
        }
        
        // set the preparedstatement parameters
        ps.setInt(1, room.getRoomNumber());
        ps.setString(2,room.getDescription());
        ps.setInt(3,room.getMaxOccupancy());
        ps.setString(4, listToString(meetingIDList));
        

        // call executeUpdate to execute our sql update statement
        ps.executeUpdate();
        ps.close();          
    }
    
    @Override
    public void removeObject(DataBaseInterface obj,  Statement stmt)throws SQLException{
        Room room = (Room)obj;
        stmt.executeUpdate("DELETE FROM ROOMS " + " WHERE ROOM_NUMBER = " +  room.getRoomNumber());

    }
    
    @Override
    public Boolean updateObject(DataBaseInterface obj, Connection con)throws SQLException{
        Room room = (Room)obj;
        PreparedStatement ps = con.prepareStatement(
        "UPDATE ROOMS SET DESCRIPTION = ?, MAX_OCCUPANCY = ?, MEETING_ID_LIST = ? WHERE ROOM_NUMBER = ?");

        ListIterator<Meeting> it;
            
        LinkedList<String> meetingIDList = new LinkedList<>();
        it = meetingList.listIterator();
        while(it.hasNext()){
            meetingIDList.add(it.next().getMeetingID());
        }
        // set the preparedstatement parameters
        ps.setString(1,room.getDescription());
        ps.setInt(2,room.getMaxOccupancy());
        ps.setString(3, listToString(meetingIDList));
        ps.setInt(4,room.getRoomNumber());

        // call executeUpdate to execute our sql update statement
        // call executeUpdate to execute our sql update statement
        int i = ps.executeUpdate();
        ps.close();   
        
        if(i <= 0){
        return false;
        }
        return true;
    }
    
}
