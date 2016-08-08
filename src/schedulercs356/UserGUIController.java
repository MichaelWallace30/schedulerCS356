/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulercs356;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * FXML Controller class
 *
 * @author MAGarcia
 */
public class UserGUIController implements Initializable {
  private Account account;
  @FXML
  private Text sidebarName;
  @FXML
  private Text sidebarEmployeeStatus;
  @FXML
  private Text sidebarDate;
  @FXML
  private Button sidebarCreateMeeting;
  @FXML
  private Text sidebarUpcomingMeetings;
  @FXML
  private TextFlow sidebarUpcomingMeetingsDisplay;
  @FXML
  private Text sidebarNews;
  @FXML
  private TextFlow sidebarNewsDisplay;
  @FXML
  private Tab tabProfile;
  @FXML
  private Text profileName;
  @FXML
  private Text profileEmployeeStatus;
  @FXML
  private Text profileAddress;
  @FXML
  private Text profileUserName;
  @FXML
  private Tab tablMeetings;
  @FXML
  private Tab tabEditMeeting;
  @FXML
  private Tab tabEditRooms;
  @FXML
  private Tab tabRooms;
  @FXML
  private Tab tabSearch;
  @FXML
  private Tab tabAdmin;
  @FXML
  private Tab tabRoomDetails;
  @FXML
  private Tab tabMeetingDetails;
  @FXML
  private TextArea searchbarText;
  @FXML
  private Button searchButton;
  @FXML
  private MenuBar menuBar;
  @FXML
  private Menu fileMenu;
  @FXML
  private MenuItem menuCloseButton;
  @FXML
  private Menu editMenu;
  @FXML
  private Menu helpMenu;
  @FXML
  private MenuItem menuAboutButton;

  /**
   * Initializes the controller class.
   * @param url
   * @param rb
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
    account = (Account)rb.getObject("data"); 
    
    if (account != null) {
      sidebarName.setText(account.getFirstName() + account.getLastName());
      profileName.setText(account.getFirstName() + account.getLastName());
    }
  }  
  
}
