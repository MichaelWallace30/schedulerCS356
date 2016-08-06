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
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/**
 *
 * @author Michael Wallace
 * 
 * To convert Java8's java.time.LocalDate to java.sql.Timestamp, just do
 */
public class Schedule implements DataBaseInterface  {
    
    public final static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String ownerID;// only Meetings own schedules

    
    public Schedule(String ownerID, LocalDateTime startTime, LocalDateTime endTime){
        this.ownerID = ownerID;
        this.startDateTime = startTime;
        this.endDateTime = endTime;
    }
    
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }
    @Override
    public void addObject(DataBaseInterface obj,  Statement stmt)throws SQLException{
        Schedule schedule = (Schedule)obj;        
        Timestamp startStamp = Timestamp.valueOf(schedule.getStartDateTime());
        Timestamp endStamp = Timestamp.valueOf(schedule.getEndDateTime());
        
        String formatedString = "" + schedule.getOwnerID() + ", " + startStamp + ", " + endStamp +"";
        stmt.executeUpdate("INSERT INTO SCHEDULE " + "VALUES (" + formatedString + ")");
            
    }
                
    @Override
    public void removeObject(DataBaseInterface obj,  Statement stmt)throws SQLException{
        Schedule schedule = (Schedule)obj;
        stmt.executeUpdate("DELETE FROM SCHEDULE " + " WHERE OWNER_ID = " +  schedule.getOwnerID());
        
    }  
    @Override
    public void updateObject(DataBaseInterface obj,  Connection con)throws SQLException{
        Schedule schedule = (Schedule)obj;
        PreparedStatement ps = con.prepareStatement(
        "UPDATE SCHEDULE SET OWNER_ID = ?, START_TIME = ?, END_TIME = ? WHERE ROOM_NUMBER = ?");
        
        Timestamp startStamp = Timestamp.valueOf(schedule.getStartDateTime());
        Timestamp endStamp = Timestamp.valueOf(schedule.getEndDateTime());

        // set the preparedstatement parameters
        ps.setString(1,schedule.getOwnerID());        
        ps.setTimestamp(2,startStamp);
        ps.setTimestamp(3, endStamp);      

        // call executeUpdate to execute our sql update statement
        ps.executeUpdate();
        ps.close();   
    }
}