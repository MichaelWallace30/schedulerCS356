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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author MAGarcia
 */
public class InviteManagerController implements Initializable {
  @FXML
  private Button acceptButton;
  @FXML
  private Button rejectButton;
  @FXML
  private Button postponeButton;
  @FXML
  private TableView<?> invitedMeetingsTable;
  @FXML
  private TableColumn<?, ?> invitedMeetingIdColumn;
  @FXML
  private TableColumn<?, ?> roomNumberColumn;
  @FXML
  private TableColumn<?, ?> dateColumn;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }  

  @FXML
  private void onButtonAccept(ActionEvent event) {
  }

  @FXML
  private void onRejectButton(ActionEvent event) {
  }

  @FXML
  private void onButtonPostpone(ActionEvent event) {
  }
  
}
