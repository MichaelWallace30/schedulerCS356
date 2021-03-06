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
    public void removeObject(DataBaseInterface obj, Connection con)throws SQLException{
        Statement stmt = con.createStatement();
        Schedule schedule = (Schedule)obj;
        stmt.executeUpdate("DELETE FROM SCHEDULE " + " WHERE OWNER_ID = \'" +  schedule.getMeetingID() + "\'");
        
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