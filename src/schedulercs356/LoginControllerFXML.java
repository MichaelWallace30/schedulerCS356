/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulercs356;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Lenovo
 */
public class LoginControllerFXML implements Initializable {
    
    DataBaseController dbController;
    private Stage primaryStage;
    
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
    
    private void login() {
        
        int accountID = dbController.login(inputNameTextField.getText(), inputPasswordField.getText());
                
        if(accountID != -1)
        {
            //if login succeded
            invalidLabel.setVisible(false);   
            LoginAccountBundle parser = new LoginAccountBundle();
            Account account = dbController.getAccount(accountID);
            parser.setAccount(account);
            
            try {
              //call account gui with account1
              FXMLLoader loader = new FXMLLoader(getClass().getResource("UserGUI.fxml"), parser);
              AnchorPane pane = (AnchorPane)loader.load();
              Scene scene = new Scene(pane);
            
              Stage stage = (Stage)loginButton.getScene().getWindow();
              stage.close();
              stage.setScene(scene);
              stage.setTitle("SchedulerCS356");
              stage.show();
            } catch (IOException e) {
              System.out.println("Didn't work");
              e.printStackTrace();
              System.out.println(e.getMessage());
            }
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
