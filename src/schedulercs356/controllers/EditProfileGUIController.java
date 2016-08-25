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
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import schedulercs356.entity.Account;

/**
 * FXML Controller class
 *
 * @author MAGarcia
 */
public class EditProfileGUIController implements Initializable {
  private DropShadow errorGlow = new DropShadow();
  private boolean passwordDisabled;
  private DataBaseController dbController;
  private UserGUIController observer;
  private Account account;
  private boolean success;
  
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
  @FXML
  private Text errorText;

  
  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    errorText.setVisible(false);
    setEnableForPassword(false);
    success = false;
    
    errorGlow.setOffsetX(0f);
    errorGlow.setOffsetY(0f);
    errorGlow.setColor(Color.RED);
    errorGlow.setWidth(20);
    errorGlow.setHeight(20);
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

  
  /**
   * Update the account.
   * @param event 
   */
  @FXML
  private void onUpdateProfileButton(ActionEvent event) {
    errorText.setVisible(false);
    oldPassword.setEffect(null);
    newPassword.setEffect(null);
    retypePassword.setEffect(null);
    
    account.setAddress(addressField.getText());
    account.setFirstName(firstnameField.getText());
    account.setLastName(lastnameField.getText());
    account.setUserName(usernameField.getText());
  
    // Update the password.
    if (!passwordDisabled) {
      int oldPassHash = oldPassword.getText().hashCode();
      if (oldPassHash == account.getPassword()) {
        if (!newPassword.getText().isEmpty()) {
          if (retypePassword.getText().equals(newPassword.getText())) {
            account.hashPassword(newPassword.getText());
          } else {
            errorText.setVisible(true);
            errorText.setText("retype password does not match new password!");
            retypePassword.setEffect(errorGlow);
            return;
          }
        } else {
          errorText.setVisible(true);
          errorText.setText("A new password is required!");
          newPassword.setEffect(errorGlow);
          return;
        }
      } else {
        errorText.setVisible(true);
        errorText.setText("Old Password does not match original password!");
        oldPassword.setEffect(errorGlow);
        return;
      }
    }
    
    boolean success = dbController.updateObject(account);
    
    if (success) {
      this.success = success;
      System.out.println("Account edited!");
      if (observer != null) {
        observer.notifyPopup("Profile Successfully\nChanged!");
      }
      
      onCancelButton(null);
    } else {
      this.success = false;
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
  
  
  /**
   * Attach the database controller to this controller.
   * @param db 
   */
  public void attachDBController(DataBaseController db) {
    dbController = db;
  }
  
  
  /**
   * Attach the UserGUI that is using this controller.
   * @param observer 
   */
  public void attachUIObserver(UserGUIController observer) {
    this.observer = observer;
  }
  
  
  /**
   * Check if the account was successfully updated.
   */
  public boolean getSuccess() {
    return success;
  }
}
