/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
