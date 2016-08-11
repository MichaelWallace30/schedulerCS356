/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulercs356.cells;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import schedulercs356.entity.Room;

/**
 *
 * @author MAGarcia
 */
public class RoomTableCell {
  public IntegerProperty roomNumber;
  public IntegerProperty maxOccupancy;
  public StringProperty description;
  
  public RoomTableCell(Room room) {
    roomNumber = new SimpleIntegerProperty(room.getRoomNumber());
    maxOccupancy = new SimpleIntegerProperty(room.getMaxOccupancy());
    description = new SimpleStringProperty(room.getDescription());
  }

  public RoomTableCell() {
  }
}
