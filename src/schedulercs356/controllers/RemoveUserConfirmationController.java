/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulercs356.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import schedulercs356.entity.Account;

/**
 * FXML Controller class
 *
 * @author MAGarcia
 */
public class RemoveUserConfirmationController implements Initializable {
  private ReadOnlyObjectWrapper<Boolean> remove = new ReadOnlyObjectWrapper<>();
  
  public ReadOnlyObjectProperty<Boolean> removeProperty() {
    return remove.getReadOnlyProperty();
  }
  
  public Boolean getRemove() {
    return remove.get();
  }
  
  @FXML
  private Text username;
  @FXML
  private Button removeButton;
  @FXML
  private Button cancelButton;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
    Account account = (Account)rb.getObject("data");
    
    if (account != null) {
      username.setText(account.getUserName());
    }
  }  

  @FXML
  private void onRemoveButton(ActionEvent event) {
    remove.set(true);
    
    onCancelButton(null);
  }

  @FXML
  private void onCancelButton(ActionEvent event) {
    Stage stage = (Stage)cancelButton.getScene().getWindow();
    
    stage.close();
  }
  
}
