/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulercs356;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

/**
 *
 * @author Lenovo
 */
public class LoginControllerFXML implements Initializable {
    
    DataBaseController dbController;
    
    @FXML
    private TextField inputNameTextField;
    @FXML
    private PasswordField inputPasswordField;
    @FXML
    private Button loginButton;
    @FXML
    private Label invalidLabel;
    
    private void clearTextFields(){
        inputNameTextField.setText("");
        inputPasswordField.setText("");
    }
    
    private void login(){
        
        if(dbController.login(inputNameTextField.getText(), inputPasswordField.getText()))
        {
            //if login succeded
            invalidLabel.setVisible(false);
            
        }
        else
        {
            //if login failed
            invalidLabel.setVisible(true);
            inputNameTextField.requestFocus();
        }
        clearTextFields();
    }
    @FXML
    private void handleButtonAction(ActionEvent event) {
        Button whichButton = (Button)event.getSource();
        login();
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //set focus on inputName TextField
        dbController = new DataBaseController();
        invalidLabel.setVisible(false);
        Platform.runLater(new Runnable() 
        {
            @Override
            public void run()            {
                inputNameTextField.requestFocus();;
            }
        });
        
        //add on key enter to login
        inputPasswordField.setOnKeyReleased(event->{
            if(event.getCode() == KeyCode.ENTER){
                login();               
            }
        });
                //add on key enter to login
        inputNameTextField.setOnKeyReleased(event->{
            if(event.getCode() == KeyCode.ENTER){
                login();               
            }
        });
                //add on key enter to login
        loginButton.setOnKeyReleased(event->{
            if(event.getCode() == KeyCode.ENTER){
                login();               
            }
        });
    }   
}
