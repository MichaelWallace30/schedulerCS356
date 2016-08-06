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
import static schedulercs356.DataBaseController.listToString;


/**
 *
 * @author Michael Wallace
 * 
 * Needs:
 *      Schedule
 */
public class Room implements DataBaseInterface {
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
         
        if(meetingIDList == null){
            this.meetingIDList = new LinkedList<>();
        }
        else{            
            this.meetingIDList = meetingIDList;
         }
    }
      
  
    @Override
    public void addObject(DataBaseInterface obj,  Statement stmt)throws SQLException{   
        
        Room room = (Room)obj;
    
        String stringArray = DataBaseController.listToString(room.getMeetingIDList());
        String formatedString = "" + room.getRoomNumber() + ", '" + room.getDescription() + "', " + room.getMaxOccupancy() + ", '" + stringArray +"'";
        stmt.executeUpdate("INSERT INTO ROOMS " + "VALUES (" + formatedString + ")");
     
    }
    
    @Override
    public void removeObject(DataBaseInterface obj,  Statement stmt)throws SQLException{
        Room room = (Room)obj;
        stmt.executeUpdate("DELETE FROM ROOMS " + " WHERE ROOM_NUMBER = " +  room.getRoomNumber());

    }
    
    @Override
    public void updateObject(DataBaseInterface obj, Connection con)throws SQLException{
        Room room = (Room)obj;
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
    
}
