/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulercs356;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.util.Duration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author MAGarcia
 */
public class UserGUIController implements Initializable {
  private static final String EMPLOYEE = "Employee";
  private static final String ADMINISTRATOR = "Administrator";
  
  // Our databaseController.
  private DataBaseController dbController;
  
  private Account account;
  
  private List<Meeting> accountMeetings;
  private List<Account> accountReferences;
  private List<Schedule> accountSchedules;
  
  private ObservableList<MeetingTableCell> meetingData;
  private ObservableList<AccountTableCell> accounts;
  private ObservableList<Schedule> schedules;
  
  @FXML
  private Text profileName;
  @FXML
  private Button searchButton;
  @FXML
  private Button meetingDetailsButton;
  @FXML
  private Text sidebarName;
  @FXML
  private Button attendMeetingButton;
  @FXML
  private MenuItem menuAboutButton;
  @FXML
  private Tab tabSearch;
  @FXML
  private Text sidebarNews;
  @FXML
  private TextFlow sidebarUpcomingMeetingsDisplay;
  @FXML
  private Tab tablMeetings;
  @FXML
  private Tab tabRoomDetails;
  @FXML
  private Tab tabEditMeeting;
  @FXML
  private MenuBar menuBar;
  @FXML
  private Text profileEmployeeStatus;
  @FXML
  private TableColumn<AccountTableCell, String> nameColumn;
  @FXML
  private Tab tabRooms;
  @FXML
  private TextFlow sidebarNewsDisplay;
  @FXML
  private Text sidebarDate;
  @FXML
  private TableColumn<AccountTableCell, String> inviteStatusColumn;
  @FXML
  private Button editMeetingButton;
  @FXML
  private MenuItem menuCloseButton;
  @FXML
  private Button cancelMeetingButton;
  @FXML
  private Menu fileMenu;
  @FXML
  private TableColumn<AccountTableCell, String> contactNumberColumn;
  @FXML
  private TableView<MeetingTableCell> meetingTable;
  @FXML
  private TableView<Room> roomsTable;
  @FXML
  private Button sidebarCreateMeeting;
  @FXML
  private TableColumn<MeetingTableCell, String> meetingIdColumn;
  @FXML
  private Text profileAddress;
  @FXML
  private Text profileUserName;
  @FXML
  private Menu helpMenu;
  @FXML
  private TableColumn<MeetingTableCell, Boolean> hostingColumn;
  @FXML
  private TextField searchbarText;
  @FXML
  private TableColumn<MeetingTableCell, Integer> numberAttendingColumn;
  @FXML
  private TableColumn<AccountTableCell, String> usernameColumn;
  @FXML
  private Tab tabMeetingDetails;
  @FXML
  private Menu editMenu;
  @FXML
  private Text sidebarEmployeeStatus;
  @FXML
  private Text sidebarUpcomingMeetings;
  @FXML
  private Tab tabAdmin;
  @FXML
  private Tab tabEditRooms;
  @FXML
  private Tab tabProfile;
  @FXML
  private TableColumn<MeetingTableCell, String> dateColumn;
  @FXML
  private TableView<AccountTableCell> usersInMeetingTable;
  @FXML
  private TabPane tabPane;
  @FXML
  private Text editMeetingText;
  @FXML
  private DatePicker editMeetingDatePicker;
  @FXML
  private TableView<?> editMeetingUsersTable;
  @FXML
  private TableColumn<?, ?> editMeetingUsernameColumn;
  @FXML
  private TableColumn<?, ?> editMeetingNameColumn;

  void Attending(ActionEvent event) {

  }
  
  
  /**
   * Initializes the controller class.
   * @param url
   * @param rb
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    accountMeetings = new LinkedList<>();
    accountReferences = new LinkedList<>();
    accountSchedules = new LinkedList<>();
    dbController = new DataBaseController();
 
    account = (Account)rb.getObject("data"); 
    
    if (account != null) {
      initializeMeetingTable();
      initializeUsersInMeetingTable();
      
      sidebarName.setText(account.getFirstName() + " " + account.getLastName());
      profileName.setText(account.getFirstName() + " " + account.getLastName());
      
      if (account.isAdmin()) {
        String status = ADMINISTRATOR;
        
        if (account.isEmployee()) {
          status = EMPLOYEE + " " + status;
        } else {
          tabPane.getTabs().remove(tabEditMeeting);
          tabPane.getTabs().remove(tabMeetingDetails);
          tabPane.getTabs().remove(tablMeetings);
        }
        
        sidebarEmployeeStatus.setText(status);
        profileEmployeeStatus.setText(status);
        
      } else {
        sidebarEmployeeStatus.setText(EMPLOYEE);
        profileEmployeeStatus.setText(EMPLOYEE);
        
        // Remove our admin and edit room tabs.
        tabPane.getTabs().remove(tabAdmin);
        tabPane.getTabs().remove(tabEditRooms);
        
      } 
      
      //sidebarDate.setText(new Date().toString());
      profileUserName.setText(account.getUserName());
      profileAddress.setText("Address: " + account.getAddress());
      sidebarDate.setText(new Date().toString());
      runOnThread();
      addMeetingsToTables();      
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
  }
  
  
  /**
   * 
   */
  private void addMeetingsToTables() {
      
    /*
      @TODO
      I changed list id to objects this code is probably now not needed
      I put this in to no break any thing
      */
    ListIterator<Meeting> it;  
    LinkedList<String> meetings = new LinkedList<>();
    it = account.getMeetingList().listIterator();
    while(it.hasNext()){
        meetings.add(it.next().getMeetingID());
    }
    //List<String> meetings = account.getMeetingList();
    
    for (String str : meetings) {
      Meeting meeting = dbController.getMeeting(str);
      
      if (meeting != null) {
        accountMeetings.add(meeting);
        meetingData.add(new MeetingTableCell(meeting, account));
        
        for (Account ref : meeting.getAcceptedList()) {
          accountReferences.add(ref);
        }
        
        for (Account ref : meeting.getInvitedList()) {
          accountReferences.add(ref);
        }
        
        for (Account ref : meeting.getRejectedList()) {
          accountReferences.add(ref);
        }
      }
    }
  }
  
  
  /**
   * 
   */
  private void initializeMeetingTable() {
    meetingData = FXCollections.observableArrayList();
    meetingTable = new TableView<>();
    meetingIdColumn = new TableColumn<>("Meeting ID");
    dateColumn = new TableColumn<>("Date");
    numberAttendingColumn = new TableColumn<>("# Attending");
    hostingColumn = new TableColumn<>("Hosting");
    
    meetingIdColumn.setCellValueFactory(new PropertyValueFactory("meetingID"));
    dateColumn.setCellFactory(new PropertyValueFactory("date"));
    numberAttendingColumn.setCellFactory(new PropertyValueFactory("numberOfAttendees"));
    hostingColumn.setCellFactory(new PropertyValueFactory("isHosting"));
    
    meetingTable.setItems(meetingData);
    meetingTable.getColumns().addAll(meetingIdColumn, dateColumn, numberAttendingColumn, hostingColumn);
  }
  
  
  /**
   * 
   */
  private void initializeUsersInMeetingTable() {
    accounts = FXCollections.observableArrayList();
    schedules = FXCollections.observableArrayList();
    
    usersInMeetingTable = new TableView<>();
    usernameColumn = new TableColumn<>("Username");
    nameColumn = new TableColumn<>("Name");
    inviteStatusColumn = new TableColumn<>("Invite Status");
    contactNumberColumn = new TableColumn<>("Contact");
    
    usernameColumn.setCellFactory(new PropertyValueFactory("username"));
    nameColumn.setCellFactory(new PropertyValueFactory("fullname"));
    inviteStatusColumn.setCellFactory(new PropertyValueFactory("inviteStatus"));
    contactNumberColumn.setCellFactory(new PropertyValueFactory("contact"));
    
    usersInMeetingTable.setItems(accounts);
    usersInMeetingTable.getColumns().addAll(usernameColumn, nameColumn, inviteStatusColumn, contactNumberColumn);
  }
  
  
  /**
   * Create Meeting Button Action.
   * @param event 
   */
  @FXML
  void onCreateMeeting(ActionEvent event) {
    if (tabEditMeeting.isDisabled()) {
      tabEditMeeting.setDisable(false);
    }
    
    tabEditMeeting.getTabPane().getSelectionModel().select(tabEditMeeting);
    
    
  }
  
  @FXML
  void onCloseMenuItem(ActionEvent event) {
    if (Platform.isImplicitExit()) {
      Platform.exit();
    }
  }
  
  
  @FXML
  void onLogoutMenuItem(ActionEvent event) {
    account = null;
    
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginFXML.fxml"));
      AnchorPane pane = (AnchorPane)loader.load();
      Scene scene = new Scene(pane);
      Stage stage = (Stage)tabPane.getScene().getWindow();
    
      stage.close();
      stage.setScene(scene);
      stage.setTitle("Login");
      stage.show();
    } catch (IOException e) {
      
    }
  }
}
