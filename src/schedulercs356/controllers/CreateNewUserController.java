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
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import schedulercs356.entity.Account;

/**
 * FXML Controller class
 *
 * @author MAGarcia
 */
public class CreateNewUserController implements Initializable {

  
  private ReadOnlyObjectWrapper<Account> newAccount = new ReadOnlyObjectWrapper<>();
  
  public ReadOnlyObjectProperty<Account> accountProperty() {
    return newAccount.getReadOnlyProperty();
  }
  
  public Account getNewAccount() {
    return newAccount.get();
  }
  
  @FXML
  private TextField enterFirstNameText;
  @FXML
  private CheckBox checkboxAdministrator;
  @FXML
  private Button cancelButton;
  @FXML
  private Button createUserButton;
  @FXML
  private TextField enterAddressText;
  @FXML
  private CheckBox checkboxEmployee;
  @FXML
  private TextField enterUsernameText;
  @FXML
  private TextField enterLastNameText;
  @FXML
  private PasswordField enterPasswordText;
  @FXML
  private PasswordField retypePasswordText;
  @FXML
  private Text errorText;

  /**
   * Initializes the controller class.
   * @param url
   * @param rb
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
    Account account = (Account)rb.getObject("data");
    System.out.println("Runnin");
    if (account.isEmployee()) {
      System.out.println("am employeed");
      checkboxAdministrator.setVisible(false);
      System.out.println("Visible");
      checkboxAdministrator.setDisable(true);
    }
    
    System.out.println("Set up!");
    errorText.setVisible(false);
    
    enterFirstNameText.setOnKeyReleased(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        onCreateUser(null);
      }
    });
    
    enterLastNameText.setOnKeyReleased(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        onCreateUser(null);
      }
    });
    
    enterUsernameText.setOnKeyReleased(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        onCreateUser(null);
      }
    });
    
    enterAddressText.setOnKeyReleased(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        onCreateUser(null);
      }
    });
    
    enterPasswordText.setOnKeyReleased(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        onCreateUser(null);
      }
    });
    
    retypePasswordText.setOnKeyReleased(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        onCreateUser(null);
      }
    });
    
    checkboxEmployee.setSelected(false);
    checkboxAdministrator.setSelected(false);
  }  

  @FXML
  private void onCreateUser(ActionEvent event) {
    DropShadow errorGlow = new DropShadow();
    errorGlow.setOffsetX(0f);
    errorGlow.setOffsetY(0f);
    errorGlow.setColor(Color.RED);
    errorGlow.setWidth(20);
    errorGlow.setHeight(20);
    
    String errorStr = "";
    boolean triggered = false;
    
    enterUsernameText.setEffect(null);
    enterFirstNameText.setEffect(null);
    enterLastNameText.setEffect(null);
    enterPasswordText.setEffect(null);
    retypePasswordText.setEffect(null); 
    checkboxEmployee.setEffect(null);
    checkboxAdministrator.setEffect(null);
              
    if (enterUsernameText.getText().length() <= 0) {
      errorStr = "You require a Username!";
      triggered = true;
      enterUsernameText.setEffect(errorGlow);
    } else if (enterFirstNameText.getText().length() <= 0) {
      errorStr = "You require a First Name!";
      triggered = true;
      enterFirstNameText.setEffect(errorGlow);
    } else if (enterLastNameText.getText().length() <= 0) {
      errorStr = "You require a Last Name!";
      triggered = true;
      enterLastNameText.setEffect(errorGlow);
      // Address doesn't matter.
    } else if (enterPasswordText.getText().length() <= 0) {
      errorStr = "Password is required!";
      enterPasswordText.setEffect(errorGlow);
      triggered = true;
    } else if (!enterPasswordText.getText().equals(retypePasswordText.getText())) {
      errorStr = "Passord and Retype do not match!";
      triggered = true;
      retypePasswordText.setEffect(errorGlow);
    } else if (!checkboxEmployee.isSelected()) {
      if (checkboxAdministrator.isDisabled()) {
        checkboxEmployee.setEffect(errorGlow);
        errorStr = "New User must at least be an Employee!!";
        triggered = true;
      } else {
        if (!checkboxEmployee.isSelected() && !checkboxAdministrator.isSelected()) {
          errorStr = "User must be Employee and/or Admin!";
          triggered = true;
          checkboxEmployee.setEffect(errorGlow);
          checkboxAdministrator.setEffect(errorGlow);
        }
      }
    }
    
    
    if (triggered) {
      errorText.setVisible(triggered);
      errorText.setText(errorStr);
    } else {
      errorText.setVisible(triggered);
      
      // Create our Account.
      newAccount.set(new Account(enterFirstNameText.getText(),
                                 enterLastNameText.getText(),
                                 enterAddressText.getText(),
                                 0,
                                 enterUsernameText.getText(),
                                 enterPasswordText.getText(),
                                 (Boolean)checkboxEmployee.isSelected(),
                                 (Boolean)checkboxAdministrator.isSelected(),
                                 new LinkedList<>(),
                                 new LinkedList<>()));
      // Close After.
      OnCancelButton(null);
    }
  }

  @FXML
  private void OnCancelButton(ActionEvent event) {
    Stage stage = (Stage) cancelButton.getScene().getWindow();
    stage.close();
  }
  
}
