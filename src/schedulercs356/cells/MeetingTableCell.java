/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulercs356.cells;

import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import schedulercs356.entity.Account;
import schedulercs356.entity.Meeting;

/**
 *
 * @author MAGarcia
 */
public class MeetingTableCell {
  public StringProperty date;
  public StringProperty meetingID;
  public IntegerProperty numberOfAttendees;
  public BooleanProperty isHosting;
  public IntegerProperty roomNumber;
  
  public MeetingTableCell(Meeting meeting, Account account) {
    date = new SimpleStringProperty(meeting.getSchedule().getStartDateTime().toString()
      + " - " + meeting.getSchedule().getEndDateTime().toString());
    meetingID = new SimpleStringProperty(meeting.getMeetingID());   
    numberOfAttendees = new SimpleIntegerProperty(meeting.getAcceptedList().size());
    Integer test = meeting.getOwnerID();
    Integer test2 = account.getId();
    isHosting = new SimpleBooleanProperty((meeting.getOwnerID() == account.getId()));
    
    if (meeting.getRoom() != null) {
    roomNumber = new SimpleIntegerProperty(meeting.getRoom().getRoomNumber());
    } else {
      roomNumber = new SimpleIntegerProperty(-1);
    }
    
  }
}
