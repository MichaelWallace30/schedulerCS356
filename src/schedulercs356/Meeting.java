/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulercs356;

import java.util.UUID;

/**
 *
 * @author Michael Wallace
 */
public class Meeting {
    private String meetingID;
    
    
    public Meeting(){
        meetingID = UUID.randomUUID().toString();
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
    
}
