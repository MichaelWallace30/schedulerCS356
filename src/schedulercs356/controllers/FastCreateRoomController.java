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
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import schedulercs356.entity.Room;

/**
 * FXML Controller class
 *
 * @author MAGarcia
 */
public class FastCreateRoomController implements Initializable {
  private ReadOnlyObjectWrapper<Room> room = new ReadOnlyObjectWrapper<>();
  @FXML
  private Text errorText;
  
  public ReadOnlyObjectProperty<Room> getRoomProperty() {
    return room.getReadOnlyProperty();
  }
  
  public Room getRoom() {
    return room.get();
  }
  
  @FXML
  private TextArea descriptionText;
  @FXML
  private TextField roomNumberText;
  @FXML
  private TextField maxOccupancyText;
  @FXML
  private Button createButton;
  @FXML
  private Button cancelButton;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
    errorText.setDisable(true);
    errorText.setVisible(false);
  }  

  
  /**
   * Trigger the call to Create room.
   * @param event 
   */
  @FXML
  private void onCreateButton(ActionEvent event) {
    DropShadow errorGlow = new DropShadow();
    errorGlow.setOffsetX(0f);
    errorGlow.setOffsetY(0f);
    errorGlow.setColor(Color.RED);
    errorGlow.setWidth(20);
    errorGlow.setHeight(20);    
    
    errorText.setVisible(false);
    errorText.setDisable(true);
    
    roomNumberText.setEffect(null);
    maxOccupancyText.setEffect(null);
    
    Integer roomNumber = 0;
    Integer maxOccupancy = 0;
    boolean triggered = false;
    
    try {
      roomNumber = Integer.parseInt(roomNumberText.getText());
    } catch (NumberFormatException e) {
      errorText.setVisible(true);
      errorText.setDisable(false);
      errorText.setText("Input is not a number!");
      roomNumberText.setEffect(errorGlow);
      triggered = true;
    }
    
    try {
      maxOccupancy = Integer.parseInt(maxOccupancyText.getText());
    } catch (NumberFormatException e) {
      errorText.setVisible(true);
      errorText.setDisable(false);
      errorText.setText("Input is not a number!");
      maxOccupancyText.setEffect(errorGlow);
      triggered = true;
    }
    
    if (!triggered) {
      room.set(new Room(maxOccupancy, descriptionText.getText(), roomNumber, new LinkedList<>()));
      onCancelButton(null);
    }
  }

  @FXML
  private void onCancelButton(ActionEvent event) {
    Stage stage = (Stage) cancelButton.getScene().getWindow();
    stage.close();
  }
  
}
