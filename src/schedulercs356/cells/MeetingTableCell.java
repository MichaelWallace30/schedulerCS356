/*
 * The MIT License
 *
 * Copyright 2016 Mario Garcia, Michael Wallace.
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
    if (meeting.getSchedule() != null) {
      date = new SimpleStringProperty(meeting.getSchedule().getStartDateTime().toString()
        + " - " + meeting.getSchedule().getEndDateTime().toString());
    } else {
      date = new SimpleStringProperty("Unknown time!");
    }
    
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
  
  public void setDate(String start, String end) {
    date = new SimpleStringProperty(start + " - " + end);
  }
  
  public void setMeetingID(String id) {
    meetingID = new SimpleStringProperty(id);
  }
  
  public void setNumberOfAttendees(Integer num) {
    numberOfAttendees = new SimpleIntegerProperty(num);
  }
  
  public void setIsHosting(Boolean yes) {
    isHosting = new SimpleBooleanProperty(yes);
  }
  
  public void setRoomNumbeR(Integer room) {
    roomNumber = new SimpleIntegerProperty(room);
  }
}
