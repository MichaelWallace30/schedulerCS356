/*
 * The MIT License
 *
 * Copyright 2016 Michael Wallace.
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
package schedulercs356.entity;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.ListIterator;
import schedulercs356.controllers.DataBaseController;



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
    private String meetingListTableName;
    public static String queryRoomMeetingTable = "ROOM_MEETING_TABLE_LIST_ROOM_NUMBER_";
    
    
    public Room(int maxOccupancy, String description, Integer roomNumber, LinkedList<Meeting> meetingList){
        this.setMaxOccupancy(maxOccupancy);
        this.setDescription(description);
        this.setRoomNumber(roomNumber);
        this.setMeetingList(meetingList);
        this.setMeetingListTableName(this.queryRoomMeetingTable + getRoomNumber().toString());
        
        //create table if dne
        DataBaseController db = new DataBaseController();
        if(!db.doesTableExsist(getMeetingListTableName())){
            String sql = "CREATE TABLE " + getMeetingListTableName() +"(MEETING_ID VARCHAR(40) primary key)";
            db.createTable(sql);            
        }
        else{//populate table
            
        }
        
        
            
    }
    
    public void addMeeting(Meeting meeting){
        meetingList.add(meeting);        
    }
    
    public void removeMeeting(Meeting meeting){
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
    
    private Boolean addMeetingTable(Connection con, String meetingID){
        try
        {
            PreparedStatement ps = con.prepareStatement(
            "INSERT INTO " + this.meetingListTableName 
                +"(MEETING_ID) VALUES"
                + "(?)");  

             ps.setString(1,meetingID);

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
     Room room = (Room)obj;
        PreparedStatement ps = con.prepareStatement(
        "INSERT INTO ROOMS" 
            +"(ROOM_NUMBER, DESCRIPTION, MAX_OCCUPANCY) VALUES"
            + "(?,?,?)");       
        
        ListIterator<Meeting> it;
        it = meetingList.listIterator();
        while(it.hasNext()){
            addMeetingTable(con, it.next().getMeetingID());
        }
        
        // set the preparedstatement parameters
        ps.setInt(1, room.getRoomNumber());
        ps.setString(2,room.getDescription());
        ps.setInt(3,room.getMaxOccupancy());
        
        
        // call executeUpdate to execute our sql update statement
        ps.executeUpdate();
        ps.close();     
        
    }
    
    @Override
    public void removeObject(DataBaseInterface obj, Connection con)throws SQLException{
        Statement stmt = con.createStatement();
        Room room = (Room)obj;
        stmt.executeUpdate("DELETE FROM ROOMS " + " WHERE ROOM_NUMBER = " +  room.getRoomNumber());

        String sq2 = "DROP TABLE " + this.meetingListTableName;
        stmt.executeUpdate(sq2);
        
        
    }
    
    @Override
    public Boolean updateObject(DataBaseInterface obj, Connection con)throws SQLException{
        Room room = (Room)obj;
        PreparedStatement ps = con.prepareStatement(
        "UPDATE ROOMS SET DESCRIPTION = ?, MAX_OCCUPANCY = ? WHERE ROOM_NUMBER = ?");

        ListIterator<Meeting> it;
        it = meetingList.listIterator();
        while(it.hasNext()){
            addMeetingTable(con, it.next().getMeetingID());
        }
        // set the preparedstatement parameters
        ps.setString(1,room.getDescription());
        ps.setInt(2,room.getMaxOccupancy());        
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

     String getMeetingListTableName() {
        return meetingListTableName;
    }

    public void setMeetingListTableName(String meetingListTableName) {
        this.meetingListTableName = meetingListTableName;
    }
    
}
