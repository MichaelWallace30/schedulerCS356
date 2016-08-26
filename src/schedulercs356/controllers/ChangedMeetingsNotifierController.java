/*
 * The MIT License
 *
 * Copyright 2016 MAGarcia.
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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import schedulercs356.cells.MeetingTableCell;
import schedulercs356.entity.Meeting;

/**
 * FXML Controller class
 *
 * @author MAGarcia
 */
public class ChangedMeetingsNotifierController implements Initializable {
  private ObservableList<MeetingTableCell> meetings;
  
  @FXML
  private TableView<MeetingTableCell> changedMeetingTable;
  @FXML
  private TableColumn<MeetingTableCell, String> meetingIdColumn;
  @FXML
  private TableColumn<MeetingTableCell, LocalDateTime> startTimeColumn;
  @FXML
  private TableColumn<MeetingTableCell, LocalDateTime> endTimeColumn;
  @FXML
  private TableColumn<MeetingTableCell, Number> roomNumberColumn;
  @FXML
  private Button dropMeetingButton;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    meetingIdColumn.setCellValueFactory(cell -> cell.getValue().meetingID);
    startTimeColumn.setCellValueFactory(cell -> cell.getValue().startDate);
    startTimeColumn.setCellFactory(
            new Callback<TableColumn<MeetingTableCell, LocalDateTime>, TableCell<MeetingTableCell, LocalDateTime>>() {

      @Override
      public TableCell<MeetingTableCell, LocalDateTime> call(TableColumn<MeetingTableCell, LocalDateTime> param) {
        return new TableCell<MeetingTableCell, LocalDateTime>() {
          @Override
          protected void updateItem(LocalDateTime time, boolean c) {
            super.updateItem(time, c);
            
            
          }
        };
      }
      
    });
  }  

  @FXML
  private void onDropMeetingButton(ActionEvent event) {
  }
  
  
  public void setupTable(List<Meeting> changedMeetings) {
    
  }
}
