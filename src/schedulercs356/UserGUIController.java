/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulercs356;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MAGarcia
 */
public class UserGUIController implements Initializable {
  private static final String EMPLOYEE = "Employee";
  private static final String ADMINISTRATOR = "Administrator";
  
  // References admin tab.
  private Tab linkedOutAdminTab;
  // References EditRoom tab.
  private Tab linkedOutEditRoomTab;
  
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
  @FXML
  private TabPane tabPane;
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
      sidebarName.setText(account.getFirstName() + " " + account.getLastName());
      profileName.setText(account.getFirstName() + " " + account.getLastName());
      
      if (account.isAdmin()) {
        String status = ADMINISTRATOR;
        
        if (account.isEmployee()) {
          status = EMPLOYEE + " " + status;
        }
        
        sidebarEmployeeStatus.setText(status);
        profileEmployeeStatus.setText(status);
        
      } else {
        sidebarEmployeeStatus.setText(EMPLOYEE);
        profileEmployeeStatus.setText(EMPLOYEE);
        
        // Remove our admin and edit room tabs.
        linkedOutAdminTab = tabPane.getTabs().remove(5);
        linkedOutEditRoomTab = tabPane.getTabs().remove(2);
        
      } 
      
      //sidebarDate.setText(new Date().toString());
      profileUserName.setText(account.getUserName());
      profileAddress.setText("Address: " + account.getAddress());
      sidebarDate.setText(new Date().toString());
      runOnThread();
      
    } else {
      throw new RuntimeException("Null account value was passed!");
    }
  
  }
  
  // Run this little guy on the thread...
  // Houses the timer for the date.
  private void runOnThread() {
    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
      sidebarDate.setText(new Date().toString());
    }));
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();
    
//      Timer timer = new Timer();
//      
//      timer.scheduleAtFixedRate(new TimerTask() {
//        
//        @Override
//        public void run() {
//          Platform.runLater(() -> {
//            sidebarDate.setText(new Date().toString());
//          });
//      }
//    }, 0, 1000);
  }
  
}
