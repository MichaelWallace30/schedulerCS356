/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulercs356;

import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author MAGarcia
 */
public class MeetingTableCell {
  public StringProperty date;
  public StringProperty meetingID;
  public IntegerProperty numberOfAttendees;
  public BooleanProperty isHosting;
  
  MeetingTableCell(Meeting meeting, Account account) {
    date = new SimpleStringProperty(meeting.getSchedule().getStartDateTime().toString()
      + " - " + meeting.getSchedule().getEndDateTime().toString());
    meetingID = new SimpleStringProperty(meeting.getMeetingID());   
    numberOfAttendees = new SimpleIntegerProperty(meeting.getAcceptedList().size());
    isHosting = new SimpleBooleanProperty((meeting.getOwnerID() == account.getId()));
  }
}
