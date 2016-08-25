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
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import schedulercs356.entity.Account;

/**
 * FXML Controller class
 *
 * @author MAGarcia
 */
public class EditProfileGUIController implements Initializable {
  private boolean passwordDisabled;
  private DataBaseController dbController;
  private UserGUIController observer;
  private Account account;
  
  @FXML
  private Button updateProfileButton;
  @FXML
  private Button cancelButton;
  @FXML
  private PasswordField oldPassword;
  @FXML
  private PasswordField newPassword;
  @FXML
  private PasswordField retypePassword;
  @FXML
  private Button changePassword;
  @FXML
  private TextField usernameField;
  @FXML
  private TextField firstnameField;
  @FXML
  private TextField lastnameField;
  @FXML
  private TextField addressField;

  
  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    setEnableForPassword(false);
  } 
  
  
  public void setAccountProfileInformation(Account account) {
    this.account = account;
    usernameField.setText(account.getUserName());
    firstnameField.setText(account.getFirstName());
    lastnameField.setText(account.getLastName());
    addressField.setText(account.getAddress());
  }
  
  
  private void setEnableForPassword(boolean enable) {
    passwordDisabled = !enable;
    oldPassword.setDisable(passwordDisabled);
    newPassword.setDisable(passwordDisabled);
    retypePassword.setDisable(passwordDisabled);
  }
  
  
  public boolean passwordFieldDisabled() {
    return passwordDisabled;
  }

  
  @FXML
  private void onUpdateProfileButton(ActionEvent event) {
    account.setAddress(addressField.getText());
    account.setFirstName(firstnameField.getText());
    account.setLastName(lastnameField.getText());
    account.setUserName(usernameField.getText());
    
    boolean success = dbController.updateObject(account);
    
    if (success) {
      System.out.println("Account edited!");
      if (observer != null) {
        observer.notifyPopup("Profile Successfully\nChanged!");
      }
      
      onCancelButton(null);
    } else {
      System.err.println("Account was not updated!");
    }
  }

  
  @FXML
  private void onCancelButton(ActionEvent event) {
    Stage stage = (Stage) cancelButton.getScene().getWindow();
    stage.close();
  }

  
  @FXML
  private void onChangePassword(ActionEvent event) {
    if (passwordDisabled) {
      setEnableForPassword(true);
    } else {
      setEnableForPassword(false);
    }
  }
  
  
  public void attachDBController(DataBaseController db) {
    dbController = db;
  }
  
  public void attachUIObserver(UserGUIController observer) {
    this.observer = observer;
  }
}
