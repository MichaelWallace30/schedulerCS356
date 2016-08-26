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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MAGarcia
 */
public class ConfirmationPopupController implements Initializable {
  /**
   * 
   */
  private boolean confirmed;
  
  @FXML
  private Text confirmationText;
  @FXML
  private Text descriptionText;
  @FXML
  private Button yesButton;
  @FXML
  private Button noButton;
  @FXML
  private Text areYouSureText;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    confirmed = false;
  }  

  
  @FXML
  private void onYesButton(ActionEvent event) {
    confirmed = true;
    onNobutton(null);
  }

  
  @FXML
  private void onNobutton(ActionEvent event) {
    Stage stage = (Stage) noButton.getScene().getWindow();
    stage.close();
  }
  
  
  /**
   * 
   * @param text 
   */
  public void setYesButtonText(String text) {
    yesButton.setText(text);
  }
  
  
  /**
   * 
   * @param text 
   */
  public void setNoButtonText(String text) {
    noButton.setText(text);
  }
  
  
  /**
   * 
   * @param header 
   */
  public void setConfirmationHeader(String header) {
    confirmationText.setText(header);
  }
  
  
  /**
   * 
   * @param message 
   */
  public void setDescriptionText(String message) {
    descriptionText.setText(message);
  }
  
  
  public void setAreYouSureText(String message) {
    areYouSureText.setText(message);
  }
  
  
  /**
   * Get the confirmation.
   * @return true if the confirmation is true, false otherwise.
   */
  public boolean isConfirmed() {
    return confirmed;
  }
}
