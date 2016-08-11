/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulercs356.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
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
      //checkboxAdministrator.setDisable(true);
    }
    
    System.out.println("Set up!");
  }  
  
}
