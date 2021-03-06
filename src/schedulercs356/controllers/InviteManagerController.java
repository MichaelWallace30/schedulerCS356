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
package schedulercs356.controllers;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import schedulercs356.cells.MeetingTableCell;
import schedulercs356.entity.Account;
import schedulercs356.entity.Meeting;
import schedulercs356.entity.TimeParser;

/**
 * FXML Controller class
 *
 * @author MAGarcia
 */
public class InviteManagerController implements Initializable {
  private ObservableList<MeetingTableCell> meetingInvites;
  private DataBaseController db;
  private Account account;
  private LocalDateTime timeJustNow;
  
  @FXML
  private Button acceptButton;
  @FXML
  private Button rejectButton;
  @FXML
  private Button postponeButton;
  @FXML
  private TableView<MeetingTableCell> invitedMeetingsTable;
  @FXML
  private TableColumn<MeetingTableCell, String> invitedMeetingIdColumn;
  @FXML
  private TableColumn<MeetingTableCell, Number> roomNumberColumn;
  @FXML
  private TableColumn<MeetingTableCell, LocalDateTime> startDateColumn;
  @FXML
  private TableColumn<MeetingTableCell, LocalDateTime> endDateColumn;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    meetingInvites = FXCollections.observableArrayList();
    
    timeJustNow = LocalDateTime.now();
    invitedMeetingIdColumn.setCellValueFactory(cellData -> cellData.getValue().meetingID);
    roomNumberColumn.setCellValueFactory(cellData -> cellData.getValue().roomNumber);
    startDateColumn.setCellValueFactory(cellData -> cellData.getValue().startDate);
    startDateColumn.setCellFactory(
            new Callback<TableColumn<MeetingTableCell, LocalDateTime>
                    , TableCell<MeetingTableCell, LocalDateTime>>() {

      @Override
      public TableCell<MeetingTableCell, LocalDateTime> call(TableColumn<MeetingTableCell, LocalDateTime> param) {
        return new TableCell<MeetingTableCell, LocalDateTime>() {
          @Override
          protected void updateItem(LocalDateTime time, boolean bool) {
            super.updateItem(time, bool);
            
            if (time != null) {
              this.setText(TimeParser.parseDateToDisplay(time));
            } else {
              this.setText(null);
            }
          }
        };
      }
    });
    
    endDateColumn.setCellValueFactory(cellData -> cellData.getValue().endDate);
    endDateColumn.setCellFactory(
            new Callback<TableColumn<MeetingTableCell, LocalDateTime>
                    , TableCell<MeetingTableCell, LocalDateTime>>() {

      @Override
      public TableCell<MeetingTableCell, LocalDateTime> call(TableColumn<MeetingTableCell, LocalDateTime> param) {
        return new TableCell<MeetingTableCell, LocalDateTime>() {
          @Override
          protected void updateItem(LocalDateTime time, boolean bool) {
            super.updateItem(time, bool);
            
            if (time != null) {
              if (time.isBefore(timeJustNow)) {
                this.setTextFill(Color.RED);
              } else if (time.isAfter(timeJustNow)) {
                this.setTextFill(Color.GREEN);               
              } else {
                this.setTextFill(Color.BLACK);
              }
              
              this.setText(TimeParser.parseDateToDisplay(time));
            } else {
              this.setText(null);
            }
          }
        };
      }                                    
    });
    
    invitedMeetingsTable.setItems(meetingInvites);
  }  

  @FXML
  private void onButtonAccept(ActionEvent event) {
    MeetingTableCell cell = invitedMeetingsTable.getSelectionModel().getSelectedItem();
    
    if (cell != null) {
      Meeting meeting = db.getMeeting(cell.meetingID.get());
      
      if (meeting != null) {
        db.updateAccountMeetingAttendenceStatus(account, meeting, Boolean.TRUE);
        meetingInvites.remove(cell);
      }
    }
  }

  @FXML
  private void onRejectButton(ActionEvent event) {
    MeetingTableCell cell = invitedMeetingsTable.getSelectionModel().getSelectedItem();
    
    if (cell != null) {
      Meeting meeting = db.getMeeting(cell.meetingID.get());
      
      if (meeting != null) {
        db.updateAccountMeetingAttendenceStatus(account, meeting, Boolean.FALSE);
        meetingInvites.remove(cell);
      }
    }
  }
  
  @FXML
  private void onButtonPostpone(ActionEvent event) {
  }
  
  public void setupTable(Account account) {
    this.account = account;
    
    List<Meeting> meetings = account.getInvitedMeetingList();
    if (meetings != null) {
      for (Meeting meeting : meetings) {
        if (meeting != null) {
          meeting = db.getMeeting(meeting.getMeetingID());
          if (meeting != null) {
            meetingInvites.add(new MeetingTableCell(meeting, account));
          }
        }
      }
    }
  }
  
  public void setDataBaseController(DataBaseController controller) {
    db = controller;
  }
}
