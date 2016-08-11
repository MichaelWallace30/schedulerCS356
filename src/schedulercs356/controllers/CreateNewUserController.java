/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulercs356.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventType;
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
      }
    }
    
    
    if (triggered) {
      errorText.setVisible(triggered);
      errorText.setText(errorStr);
    } else {
      errorText.setVisible(triggered);
    }
  }

  @FXML
  private void OnCancelButton(ActionEvent event) {
    Stage stage = (Stage) cancelButton.getScene().getWindow();
    stage.close();
  }
  
}
