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


import schedulercs356.entity.Account;
import schedulercs356.controllers.DataBaseController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import schedulercs356.bundles.LoginAccountBundle;

/**
 *
 * @author Lenovo
 */
public class LoginControllerFXML implements Initializable {
    private static final int MAX_NUMBER_OF_TRIES = 5;
    DataBaseController dbController;
    private int incorrectTries;
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
              FXMLLoader loader = new FXMLLoader(getClass().getResource("/schedulercs356/gui/UserGUI.fxml"), parser);
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
            incorrectTries += 1;
            
            if (incorrectTries == MAX_NUMBER_OF_TRIES) {
              try {
              FXMLLoader loader = 
                      new FXMLLoader(
                              getClass()
                                      .getResource("/schedulercs356/gui/wrongPassWordGUI.fxml"));
              
              AnchorPane pane = (AnchorPane) loader.load();
              Scene scene = new Scene(pane);
              Stage parentStage = (Stage)loginButton.getScene().getWindow();
              Stage stage = new Stage();
              
              stage.setScene(scene);
              stage.initOwner(parentStage);
              stage.initModality(Modality.WINDOW_MODAL);
              stage.setTitle("DIDN'T SAY THE MAGIC WORD!!");
              
              stage.setOnCloseRequest((WindowEvent event) -> {
                System.out.println("Window has closed.");
                wrongPassWordController controller = loader.getController();
                controller.stop();
              });
              
              stage.showAndWait();
              incorrectTries = 0;
              } catch (IOException e) {
                e.printStackTrace();
              }
            }
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
        incorrectTries = 0;
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
