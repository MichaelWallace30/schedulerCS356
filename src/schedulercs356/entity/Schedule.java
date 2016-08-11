/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulercs356.entity;
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
 * All schedules belong to a meeting and should have the same UUID while all other entities own a meeting id list which is also a schedule id list
 */
public class Schedule implements DataBaseInterface  {
    
    public final static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String meetingID;// only Meetings own schedules a schedule id is the same as a meeting id

    
    public Schedule(String meetingID, LocalDateTime startTime, LocalDateTime endTime){
        this.meetingID = meetingID;
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

    public String getMeetingID() {
        return meetingID;
    }

    public void setMeetingID(String meetingID) {
        this.meetingID = meetingID;
    }
    @Override
    public void addObject(DataBaseInterface obj,  Connection con)throws SQLException{
                
        Schedule schedule = (Schedule)obj;
        PreparedStatement ps = con.prepareStatement(
        "INSERT INTO SCHEDULE" 
            +"(OWNER_ID, START_TIME,END_TIME) VALUES"
            + "(?,?,?)");
        
        Timestamp startStamp = Timestamp.valueOf(schedule.getStartDateTime());
        Timestamp endStamp = Timestamp.valueOf(schedule.getEndDateTime());

        // set the preparedstatement parameters
        ps.setString(1,schedule.getMeetingID());        
        ps.setTimestamp(2,startStamp);
        ps.setTimestamp(3, endStamp);      

        // call executeUpdate to execute our sql update statement
        ps.executeUpdate();
        ps.close();  
    }
                
    @Override
    public void removeObject(DataBaseInterface obj,  Statement stmt)throws SQLException{
        Schedule schedule = (Schedule)obj;
        stmt.executeUpdate("DELETE FROM SCHEDULE " + " WHERE OWNER_ID = " +  schedule.getMeetingID());
        
    }  
    @Override
    public Boolean updateObject(DataBaseInterface obj,  Connection con)throws SQLException{
        Schedule schedule = (Schedule)obj;
        PreparedStatement ps = con.prepareStatement(
        "UPDATE SCHEDULE SET START_TIME = ?, END_TIME = ? WHERE OWNER_ID= ?");
        
        Timestamp startStamp = Timestamp.valueOf(schedule.getStartDateTime());
        Timestamp endStamp = Timestamp.valueOf(schedule.getEndDateTime());

        // set the preparedstatement parameters
        ps.setTimestamp(1,startStamp);
        ps.setTimestamp(2, endStamp);      
        ps.setString(3,schedule.getMeetingID()); 
        
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