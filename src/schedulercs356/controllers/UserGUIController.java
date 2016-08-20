/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulercs356.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import schedulercs356.bundles.LoginAccountBundle;
import schedulercs356.entity.Account;
import schedulercs356.cells.AccountAdminTableCell;
import schedulercs356.cells.AccountTableCell;
import schedulercs356.entity.Meeting;
import schedulercs356.cells.MeetingTableCell;
import schedulercs356.cells.RoomTableCell;
import schedulercs356.entity.Room;
import schedulercs356.entity.Schedule;

/**
 * FXML Controller class
 *
 * @author MAGarcia
 */
public class UserGUIController implements Initializable {
  private static final String EMPLOYEE = "Employee";
  private static final String ADMINISTRATOR = "Administrator";
  private static final Integer MAX_SIDE_BAR_DISPLAY = 5;
  private Integer meetingsDisplayed = 0;
  
  // Our databaseController.
  private DataBaseController dbController;
  
  private Account account;
  
  private int meetingSelectedIndex;
  
  private List<Meeting> accountMeetings;
  private List<Account> accountReferences;
  private List<Schedule> accountSchedules;
  
  private ObservableList<AccountTableCell> tempRejected;
  private ObservableList<MeetingTableCell> meetingData;
  private ObservableList<AccountTableCell> accounts;
  private ObservableList<Schedule> schedules;
  private ObservableList<AccountAdminTableCell> adminEnabledAccounts;
  private ObservableList<RoomTableCell> rooms;
  private ObservableList<AccountTableCell> editMeetingNotInvitedUsers;
  private ObservableList<AccountTableCell> editMeetingInvitedUsers;
  
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
  private TableView<RoomTableCell> roomsTable;
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
  private TableColumn<MeetingTableCell, Number> numberAttendingColumn;
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
  private MenuItem logoutMenuItem;
  @FXML
  private TableView<AccountAdminTableCell> adminAccountsTable;
  @FXML
  private TableColumn<AccountAdminTableCell, Number> adminAccountIdColumn;
  @FXML
  private TableColumn<AccountAdminTableCell, String> adminUsernameColumn;
  @FXML
  private TableColumn<AccountAdminTableCell, String> adminFullnameColumn;
  @FXML
  private TableColumn<AccountAdminTableCell, Number> adminPasswordColumn;
  @FXML
  private TableColumn<AccountAdminTableCell, String> adminAddressColumn;
  @FXML
  private TableColumn<AccountAdminTableCell, Boolean> adminEmployeeColumn;
  @FXML
  private TableView<RoomTableCell> adminRoomsTable;
  @FXML
  private TableColumn<RoomTableCell, Number> adminRoomNumberColumn;
  @FXML
  private TableColumn<RoomTableCell, Number> adminMaxOccupancyColumn;
  @FXML
  private TableColumn<RoomTableCell, String> adminDescriptionColumn;
  @FXML
  private Button adminCreateRoomButton;
  @FXML
  private Button adminEditRoomButton;
  @FXML
  private Button adminRemoveRoomButton;
  @FXML
  private Button editMeetingUpdateButton;
  @FXML
  private Text editMeetingIdText;
  @FXML
  private ChoiceBox<Number> editMeetingStartTimeChooserHour;
  @FXML
  private ChoiceBox<Number> editMeetingStartTimeMinuteChooser;
  @FXML
  private ChoiceBox<Number> editMeetingEndTimeChooserHour1;
  @FXML
  private ChoiceBox<Number> editMeetingEndTimeMinuteChooser1;
  @FXML
  private TextArea editMeetingDescription;
  @FXML
  private Button adminCreateUserButton;
  @FXML
  private Button adminResetEmployeePasswordButton;
  @FXML
  private Button adminRemoveEmployeePassword;
  @FXML
  private TableColumn<RoomTableCell, Number> roomsRoomNumberColumn;
  @FXML
  private SplitPane parentSplitPane;
  @FXML
  private AnchorPane sidebarSplitPane;
  @FXML
  private AnchorPane otherSplitPane;
  @FXML
  private Text sidebarInvites;
  @FXML
  private Button editRoomsUpdateButton;
  @FXML
  private Button editRoomsCancelChangesButton;
  @FXML
  private Button editRoomsRemoveRoomButton;
  @FXML
  private TextArea editRoomsDescription;
  @FXML
  private TextField editRoomsRoomNumber;
  @FXML
  private TextField editRoomsMaxIOccupancy;
  @FXML
  private TableColumn<RoomTableCell, Number> roomsMaxOccupancyColumn;
  @FXML
  private TableColumn<RoomTableCell, String> roomsDescriptionColumn;
  @FXML
  private TableColumn<MeetingTableCell, Number> meetingsRoomColumn;
  @FXML
  private TableView<AccountTableCell> editMeetingUsersNoInviteTable;
  @FXML
  private TableColumn<AccountTableCell, String> editMeetingNotInviteUsernameColumn;
  @FXML
  private TableColumn<AccountTableCell, String> editMeetingNotInviteNameColumn;
  @FXML
  private TableView<AccountTableCell> editMeetingUsersInvitedTable;
  @FXML
  private TableColumn<AccountTableCell, String> editMeetingInvitedUsernameColumn;
  @FXML
  private TableColumn<AccountTableCell, String> editMeetingInvitedNameColumn;
  @FXML
  private Button editMeetingAddInviteButton;
  @FXML
  private Button editMeetingRemoveInviteButton;
  @FXML
  private TableView<RoomTableCell> editMeetingRoomSelectColumn;
  @FXML
  private TableColumn<RoomTableCell, Number> editMeetingRoomNumberColumn;
  @FXML
  private TableColumn<RoomTableCell, Number> editMeetingRoomMaxOccupancyColumn;
  @FXML
  private TableColumn<RoomTableCell, String> editMeetingRoomDescriptionColumn;
  @FXML
  private ChoiceBox<String> editMeetingTimeDay;
  @FXML
  private ChoiceBox<String> editMeetingEndTimeDay;

  
  private static final int TIME_HOUR = 12;
  private static final int TIME_MINUTE = 60;
  private static final int TIME_ONE_DIGIT = 10;
  
  
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
    
    accounts = FXCollections.observableArrayList();
    schedules = FXCollections.observableArrayList();
    meetingData = FXCollections.observableArrayList();
    rooms = FXCollections.observableArrayList();
    tempRejected = FXCollections.observableArrayList();
    
    SplitPane.setResizableWithParent(sidebarSplitPane, Boolean.FALSE);
    SplitPane.setResizableWithParent(otherSplitPane, Boolean.FALSE);
 
    account = (Account)rb.getObject("data"); 
   
    
    if (account != null) {
      initializeUsersInMeetingTable();      
      initializeMeetingTable();
      initializeRoomsInTables();
      
      sidebarName.setText(account.getFirstName() + " " + account.getLastName());
      profileName.setText(account.getFirstName() + " " + account.getLastName());
      
      if (account.isAdmin()) {
        adminEnabledAccounts = FXCollections.observableArrayList();
        
        String status = ADMINISTRATOR;
        
        if (account.isEmployee()) {
          status = EMPLOYEE + " " + status;
        } else {
          tabPane.getTabs().remove(tabEditMeeting);
          tabPane.getTabs().remove(tabMeetingDetails);
          tabPane.getTabs().remove(tablMeetings);
          sidebarCreateMeeting.setVisible(false);
          sidebarUpcomingMeetings.setText("No meetings for Administrator!");
        }
        
        sidebarEmployeeStatus.setText(status);
        profileEmployeeStatus.setText(status);
        initializeAdminTables();
        
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
      parseDateToDisplay(LocalDateTime.now());
      runTimeOnThread();
      addMeetingsToTables();      
      addRoomsInTables();
      initializeChoiceBoxes();
    } else {
      throw new RuntimeException("Null account value was passed!");
    }
  }
  
    @FXML
    private void menuAboutButtonAction(ActionEvent event) {
        //about button was pressed
        try{
        FXMLLoader loader = 
                      new FXMLLoader(
                              getClass()
                                      .getResource("/schedulercs356/gui/aboutGUI.fxml"));
              
              AnchorPane pane = (AnchorPane) loader.load();
              Scene scene = new Scene(pane);
              Stage parentStage = (Stage)((Node)menuBar).getScene().getWindow();
              Stage stage = new Stage();
              
              stage.setTitle("About");
              stage.setScene(scene);
              stage.initOwner(parentStage);
              stage.initModality(Modality.WINDOW_MODAL);
              
              
              stage.showAndWait();
        }
        catch (IOException e) {
            e.printStackTrace();
         }
        
    }
  
  
  /**
   * Run the time on a thread.
   */
  private void runTimeOnThread() {
    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
      LocalDateTime date = LocalDateTime.now();
      parseDateToDisplay(date);
    }));
    
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();
  }
  
  
  private void parseDateToDisplay(LocalDateTime date) {    
      int hour = date.getHour();
      String minuteString = "00";
      String secondString = "00";
      String timeDay = "AM";
      
      if (hour > TIME_HOUR) {
        hour = hour - TIME_HOUR;
        timeDay = "PM";
      } else if (hour <= 0) {
        hour = TIME_HOUR;
      }
      
      int minute = date.getMinute();
      if (minute < TIME_ONE_DIGIT) {
        minuteString = "0" + minute;
      } else {
        minuteString = String.valueOf(minute);
      }
      
      int second = date.getSecond();
      
      if (second < TIME_ONE_DIGIT) {
        secondString = "0" + second;
      } else {
        secondString = String.valueOf(second);
      }
      
      sidebarDate.setText(date.getDayOfWeek() + " "
              + date.getMonth() + " " + date.getDayOfMonth() + ", "
              + date.getYear() + "\n " + hour + ":" + minuteString 
              + ":" + secondString + " " + timeDay);
  }
  
  
  /**
   * Adds meeting values into tables for Employees and 
   * Employee Administrators.
   */
  private void addMeetingsToTables() {
    /*
      @TODO
      I changed list id to objects this code is probably now not needed
      I put this in to no break any thing
      if not meeting list this breaks the code
      */
    List<Meeting> meetings = account.getInvitedMeetingList();
    addMeetingsToTable(meetings);
    meetings = account.getMeetingList();
    addMeetingsToTable(meetings);
  }
  
  
  private void addMeetingsToTable(List<Meeting> meetings) {
    for (Meeting meeting : meetings) {
      
      if (meeting != null) {
        // Prolly don't need it yet.
        //List<Account> rejectedList = meeting.getRejectedList();
        meeting = dbController.getMeeting(meeting.getMeetingID());
        
        if (meeting != null) {
          accountMeetings.add(meeting);
          meetingData.add(new MeetingTableCell(meeting, account));
        
          if (meetingsDisplayed < MAX_SIDE_BAR_DISPLAY) {
            Schedule s = meeting.getSchedule();
            if (s != null) {
            
              int hour = s.getStartDateTime().getHour();
              int minute = s.getStartDateTime().getMinute();
              String minuteString = "00";
              String dayTime = "AM";
            
              if (hour > TIME_HOUR) {
                hour = hour - TIME_HOUR;
                dayTime = "PM";
              } else if (hour <= 0) {
                hour = TIME_HOUR;
              }
              
              if (minute < TIME_ONE_DIGIT) {
                minuteString = "0" + minute;
              } else {
                minuteString = String.valueOf(minute);
              }
            
              Text newText = new Text("Meeting on " + s.getStartDateTime().getDayOfWeek()
                + " " + s.getStartDateTime().getDayOfMonth()
                + " of " + s.getStartDateTime().getMonth()
                + ", " + s.getStartDateTime().getYear()
                + " at " + hour
                + " : " + minuteString
                + " " + dayTime + "\n");
          
              sidebarUpcomingMeetingsDisplay.getChildren().add(newText);
              meetingsDisplayed++;
            }
          }
        }
      }
    }
  }
  
  
  private void initializeChoiceBoxes() {
    ObservableList<Number> minutes = FXCollections.observableArrayList();
    
    for (int i = 0; i < 60; ++i) {
      minutes.add(i);
    }
    
    
    editMeetingStartTimeChooserHour.setItems(FXCollections.observableArrayList(1, 2,
            3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
    editMeetingStartTimeMinuteChooser.setItems(minutes);
    editMeetingEndTimeChooserHour1.setItems(FXCollections.observableArrayList(1, 2,
            3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
    editMeetingEndTimeMinuteChooser1.setItems(minutes);
    editMeetingTimeDay.setItems(FXCollections.observableArrayList("AM", "PM"));
    editMeetingEndTimeDay.setItems(FXCollections.observableArrayList("AM", "PM"));
  }
  
  
  /**
   * Initialized the Meeting tables.
   */
  private void initializeMeetingTable() {
    meetingIdColumn.setCellValueFactory(cellData -> cellData.getValue().meetingID);
    dateColumn.setCellValueFactory(cellData -> cellData.getValue().date);
    numberAttendingColumn.setCellValueFactory(cellData -> cellData.getValue().numberOfAttendees);
    hostingColumn.setCellValueFactory(cellData -> cellData.getValue().isHosting);
    
    meetingTable.setItems(meetingData);
    
    // Set up a listener to the observer.
    meetingTable.getSelectionModel().selectedItemProperty().addListener((edc, old, n) -> {
      if (n != null) {
        System.out.println("Initializing users in meeting " + n.meetingID.get());
        accounts.clear();
        MeetingTableCell cell = meetingTable.getSelectionModel().getSelectedItem();
        Meeting meeting = dbController.getMeeting(cell.meetingID.get());
      
        if (meeting != null) {
          List<Account> cs = meeting.getInvitedList();
          cs.addAll(meeting.getAcceptedList());
          
          for (Account acc : cs) {
            accounts.add(new AccountTableCell(acc, meeting));
          }
        } else {
          System.err.println("Meeting " + n.meetingID.get() + " does not exist!");
        }
      } else {
        System.err.println("Selected cell is null! Must have been deleted...");
      }
    });
  }
  
  
  /**
   * Initialize the Meeting tables with Users.
   */
  private void initializeUsersInMeetingTable() {
    editMeetingInvitedUsers = FXCollections.observableArrayList();
    editMeetingNotInvitedUsers = FXCollections.observableArrayList();
    
    usernameColumn.setCellValueFactory(cellData -> cellData.getValue().username);
    nameColumn.setCellValueFactory(cellData -> cellData.getValue().fullname);
    inviteStatusColumn.setCellValueFactory(cellData -> cellData.getValue().inviteStatus);
    contactNumberColumn.setCellValueFactory(cellData -> cellData.getValue().contact);
    meetingsRoomColumn.setCellValueFactory(cellData -> cellData.getValue().roomNumber);
    
    editMeetingNotInviteUsernameColumn.setCellValueFactory(cellData -> cellData.getValue().username);
    editMeetingNotInviteNameColumn.setCellValueFactory(cellData -> cellData.getValue().fullname);
    
    editMeetingInvitedUsernameColumn.setCellValueFactory(cellData -> cellData.getValue().username);
    editMeetingInvitedNameColumn.setCellValueFactory(cellData -> cellData.getValue().fullname);
    
    usersInMeetingTable.setItems(accounts);
    editMeetingUsersNoInviteTable.setItems(editMeetingNotInvitedUsers);
    editMeetingUsersInvitedTable.setItems(editMeetingInvitedUsers);   
  }
  
  
  /**
   * Initialize Administrator tables.
   */
  private void initializeAdminTables() {
    adminAccountIdColumn.setCellValueFactory(cellData -> cellData.getValue().accountId);
    adminUsernameColumn.setCellValueFactory(cellData -> cellData.getValue().username);
    adminFullnameColumn.setCellValueFactory(cellData -> cellData.getValue().fullname);
    adminPasswordColumn.setCellValueFactory(cellData -> cellData.getValue().password);
    adminAddressColumn.setCellValueFactory(cellData -> cellData.getValue().address);
    adminEmployeeColumn.setCellValueFactory(cellData -> cellData.getValue().employee);
    
    adminAccountsTable.setItems(adminEnabledAccounts);
    
    addAccountsInAdminTable();
  }
  
  
  /**
   * Initialize Rooms in tables for both Administrator and Employee.
   */
  private void initializeRoomsInTables() {
    adminRoomNumberColumn.setCellValueFactory(cellData -> cellData.getValue().roomNumber);
    adminMaxOccupancyColumn.setCellValueFactory(cellData -> cellData.getValue().maxOccupancy);
    adminDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().description);
    
    roomsRoomNumberColumn.setCellValueFactory(cellData -> cellData.getValue().roomNumber);
    roomsMaxOccupancyColumn.setCellValueFactory(cellData -> cellData.getValue().maxOccupancy);
    roomsDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().description);
    
    editMeetingRoomNumberColumn.setCellValueFactory(cellData -> cellData.getValue().roomNumber);
    editMeetingRoomMaxOccupancyColumn.setCellValueFactory(cellData -> cellData.getValue().maxOccupancy);
    editMeetingRoomDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().description);
    
    adminRoomsTable.setItems(rooms);
    roomsTable.setItems(rooms);
    editMeetingRoomSelectColumn.setItems(rooms);
  }
  
  
  /**
   * Add rooms into the tables.
   */
  private void addRoomsInTables() {
    List<Room> roomTempo = dbController.getAllRooms();
    
    for (Room room : roomTempo) {
      RoomTableCell cell = new RoomTableCell(room);
      rooms.add(cell);
    }
  }
  
  
  /**
   * Add accounts into the administrator table.
   */
  private void addAccountsInAdminTable() {
    List<Account> accountTempo = dbController.getAllAccounts();
    
    for (Account acc : accountTempo) {
      AccountAdminTableCell cell = new AccountAdminTableCell(acc);
      
      if (!account.isEmployee()) {
          if (acc.isAdmin()) {
            adminEnabledAccounts.add(cell);
          }
      } else {
        if (!acc.isAdmin()) {
          adminEnabledAccounts.add(cell);
        }
      }
    }
  }
  
  
  /**
   * Create Meeting Button Action.
   * 
   * @param event 
   */
  @FXML
  void onCreateMeeting(ActionEvent event) {
    Meeting meeting = new Meeting();
    meeting.setOwnerID(account.getId());
    meeting.setInvitedList(new LinkedList<>());
    meeting.setAcceptedList(new LinkedList<>());
    meeting.setRejectedList(new LinkedList<>());
    meeting.setSchedule(new Schedule(meeting.getMeetingID(), 
            LocalDateTime.now(), 
            LocalDateTime.now()));
    
    account.getMeetingList().add(meeting);
    meeting.getAcceptedList().add(account);
    
    dbController.addObject(meeting);
    dbController.updateObject(account);
   
    if (tabEditMeeting.isDisabled()) {
      tabEditMeeting.setDisable(false);
    }
    
    tabEditMeeting.getTabPane().getSelectionModel().select(tabEditMeeting);
    meetingData.add(new MeetingTableCell(meeting, account));
    int index = meetingData.size() - 1;
     
    setupEditMeeting(meeting, index);
  }
  
  
  /**
   * Close the Window using the Menu Item Close.
   * @param event 
   */
  @FXML
  void onCloseMenuItem(ActionEvent event) {
    if (Platform.isImplicitExit()) {
      Platform.exit();
    }
  }
  
  
  /**
   * Log out of the account, in order to sign in as another user.
   * @param event 
   */
  @FXML
  void onLogoutMenuItem(ActionEvent event) {
    account = null;
    
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/schedulercs356/gui/LoginFXML.fxml"));
      AnchorPane pane = (AnchorPane)loader.load();
      Scene scene = new Scene(pane);
      Stage stage = (Stage)tabPane.getScene().getWindow();
    
      stage.close();
      stage.setScene(scene);
      stage.setTitle("Login");
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  
  /**
   * Allows the user to add an employee into the database. Administrative 
   * privileges only!.
   * @param event 
   */
  @FXML
  private void onAdminCreateEmployeeButton(ActionEvent event) {
    try {
      LoginAccountBundle bundle = new LoginAccountBundle();
      bundle.setAccount(account);
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/schedulercs356/gui/CreateNewUser.fxml"),
                                         bundle);
      
      AnchorPane pane = (AnchorPane) loader.load();
      Scene scene = new Scene(pane);
      Stage parentStage = (Stage) tabPane.getScene().getWindow();
      Stage stage = new Stage();
      
      CreateNewUserController child = loader.getController();
      child.accountProperty().addListener((obj, old, n) -> {
        if (n != null) {
          dbController.addObject(n);
          addAccountToAdminList(n);
        }
      });
      
      
      stage.initModality(Modality.WINDOW_MODAL);
      stage.initOwner(parentStage);
      stage.setScene(scene);
      
      stage.centerOnScreen();
      stage.showAndWait();
      
      
      System.out.println("Admin Tables have been initialized.");
    } catch (IOException e) {
      e.printStackTrace();
    }
    
  }

  
  /**
   * Allows user to reset an employee password! Administrative privileges only!
   * @param event 
   */
  @FXML
  private void onAdminResetEmployeePassword(ActionEvent event) {
    // Still need this mofo.
    AccountAdminTableCell cell = adminAccountsTable.getSelectionModel().getSelectedItem();
    
    if (cell == null) {
      return;
    }
    
    int index = adminAccountsTable.getSelectionModel().getSelectedIndex();
    Account victimAccount = dbController.getAccount(cell.accountId.get());
    
    if (victimAccount != null) {
      try {
        LoginAccountBundle bundle = new LoginAccountBundle();
        bundle.setAccount(victimAccount);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/schedulercs356/gui/ResetUserPassword.fxml"),
                                           bundle);
      
        AnchorPane pane = (AnchorPane) loader.load();
        Scene scene = new Scene(pane);
        Stage parentStage = (Stage) tabPane.getScene().getWindow();
        Stage stage = new Stage();
      
        ResetUserPasswordController child = loader.getController();
        child.passwordProperty().addListener((obj, old, n) -> {
          adminEnabledAccounts.remove(cell);
          victimAccount.hashPassword(n);
          dbController.updateObject(victimAccount);
          AccountAdminTableCell newCell = new AccountAdminTableCell(victimAccount);
          adminEnabledAccounts.add(index, newCell);
        });
      
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(parentStage);
        stage.setScene(scene);
        
        stage.centerOnScreen();
        stage.showAndWait();
        
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    
  }

  
  /**
   * Allows user to remove an employee from the data base. Administrative 
   * privileges only!
   * @param event 
   */
  @FXML
  private void onAdminRemoveEmployee(ActionEvent event) {
    AccountAdminTableCell cell = adminAccountsTable.getSelectionModel().getSelectedItem();
    
    if (cell == null) {
      return;
    }
    
    int index = adminAccountsTable.getSelectionModel().getSelectedIndex();
    Account victimAccount = dbController.getAccount(cell.accountId.get());
    
    if (victimAccount != null) {
      try {
        LoginAccountBundle bundle = new LoginAccountBundle();
        bundle.setAccount(victimAccount);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/schedulercs356/gui/RemoveUserConfirmation.fxml"),
                                           bundle);
      
        AnchorPane pane = (AnchorPane) loader.load();
        Scene scene = new Scene(pane);
        Stage parentStage = (Stage) tabPane.getScene().getWindow();
        Stage stage = new Stage();
        
        RemoveUserConfirmationController child = loader.getController();
        child.removeProperty().addListener((obj, old, n) -> {
          if (n) {
            boolean success = dbController.removeObject(victimAccount);
            if (success) {
              adminEnabledAccounts.remove(index);
            } else {
              System.err.println("Account" + victimAccount.getId() + " was not properly removed!");
            }
          }
        });
        
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(parentStage);
        stage.setScene(scene);
        
        stage.centerOnScreen();
        stage.showAndWait();
        
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    
  }
  
  
  /**
   * Add an account in the list of adminEnabledAccounts.
   * @param newAccount 
   */
  private void addAccountToAdminList(Account newAccount) {
    AccountAdminTableCell cell = new AccountAdminTableCell(newAccount);
    adminEnabledAccounts.add(cell);
  }

  
  /**
   * Create a room in a quick fashion. Administrative privileges only!
   * @param event 
   */
  @FXML
  private void onCreateRoom(ActionEvent event) {
    
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/schedulercs356/gui/FastCreateRoom.fxml"));
      AnchorPane pane = (AnchorPane)loader.load();
      Scene scene = new Scene(pane);
      Stage parentStage = (Stage) tabPane.getScene().getWindow();
      Stage stage = new Stage();
    
      FastCreateRoomController child = loader.getController();
      child.getRoomProperty().addListener((obj, old, n) -> {
        if (n != null) {
          RoomTableCell cell = new RoomTableCell(n);
          dbController.addObject(n);
          rooms.add(cell);
        }
      });
      
      stage.initModality(Modality.WINDOW_MODAL);
      stage.initOwner(parentStage);
      stage.setScene(scene);
      
      stage.centerOnScreen();
      stage.showAndWait();
    
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  
  /**
   * 
   * @param event 
   */
  @FXML
  private void onEditRoom(ActionEvent event) {
    tabEditRooms.getTabPane().getSelectionModel().select(tabEditRooms);
    
    tabEditRooms.setDisable(false);
    
    editMeetingIdText.setText("");
    
  }

  
  /**
   * 
   * @param event 
   */
  @FXML
  private void onRemoveRoom(ActionEvent event) {
    
  }

  
  /**
   * Update the meeting. BIG OL' ALGORITHM.
   * @param event 
   */
  @FXML
  private void onEditMeetingUpdate(ActionEvent event) {
    String id = editMeetingIdText.getText();
    Meeting meeting = dbController.getMeeting(id);
    
    if (meeting != null) {
      // TODO (Garcia): Must check if an invitation has already been 
      // sent. This won't require too much time.
    
      // Send invitations to invited users.
      for (int i = 0; i < editMeetingInvitedUsers.size(); ++i) {
        AccountTableCell cell = editMeetingInvitedUsers.get(i);
        Account acc = dbController.getAccount(cell.id.get());
        // store account ref into invited list.
        if (acc != null) {
          // Add the account into the meeting invite list.
          meeting.getInvitedList().add(acc);
          
          // Add the meeting into the account invited list.
          acc.getInvitedMeetingList().add(meeting);
          dbController.updateObject(acc);
        }
      }
      
      // remove users from invite list
      for (int i = 0; i < tempRejected.size(); ++i) {
        Account acc = dbController.getAccount(tempRejected.get(i).id.get());
        meeting.getRejectedList().add(acc);
        // Not sure if this works. May need to check.
        List<Account> invited = meeting.getInvitedList();
        
        for (int j = 0; j < invited.size(); ++j) {
          Account culprit = invited.get(j);
          if (culprit.getId() == acc.getId()) {
            invited.remove(j);
            break;
          }
        }
      }
      
      // This is where I call upon the power of zues...
      // This is hell... Wallace has a bounty on me...
      Schedule sch = meeting.getSchedule();
      int hour = editMeetingStartTimeChooserHour.getValue().intValue();
      
      if (editMeetingTimeDay.getValue().equals("PM")) {
        if (hour == TIME_HOUR) {
          hour = TIME_HOUR;
        } else {
          hour += TIME_HOUR;
          if (hour > 23) {
            hour = TIME_HOUR;
          }
        }
      } else {
        if (hour == TIME_HOUR) {
          hour = 0;
        }
      }
      
      LocalDateTime newDate = editMeetingDatePicker
              .getValue()
              .atStartOfDay()
              .withHour(hour)
              .withMinute(editMeetingStartTimeMinuteChooser.getValue().intValue());
     
      // Now lets do endTime.
      hour = editMeetingEndTimeChooserHour1.getValue().intValue();
      
      if (editMeetingEndTimeDay.getValue().equals("PM")) {
        if (hour == TIME_HOUR) {
          hour = TIME_HOUR;
        } else {
          hour += TIME_HOUR;
          if (hour > 23) {
            hour = TIME_HOUR;
          }
        }
      } else {
        if (hour == TIME_HOUR) {
          hour = 0;
        }
      }
      
      LocalDateTime end = editMeetingDatePicker
              .getValue()
              .atStartOfDay()
              .withHour(hour)
              .withMinute(editMeetingEndTimeMinuteChooser1.getValue().intValue());
      // update the schedule.
      sch.setStartDateTime(newDate);
      sch.setEndDateTime(end);
      
      // now update the room.
      RoomTableCell roomCell = editMeetingRoomSelectColumn.getSelectionModel().getSelectedItem();
      if (roomCell != null) {
        Room room = dbController.getRoom(roomCell.roomNumber.get());
        // room will keep reference to meeting.
        //room.addMeeting(meeting);
        meeting.setRoom(room);
        // update room.
        //dbController.updateObject(room);
      }
      
      // Update the meeting in db.
      dbController.updateObject(meeting);
    } else {
      System.err.println("Meeting " + id + " does not exist in the database!");
    }
  }

  
  /**
   * Add to invites.
   * @param event 
   */
  @FXML
  private void onAddToInvitesButton(ActionEvent event) {
    AccountTableCell cell = editMeetingUsersNoInviteTable.getSelectionModel().getSelectedItem();
    
    if (cell != null) {
      editMeetingNotInvitedUsers.remove(cell);
      editMeetingInvitedUsers.add(cell);
    }
  }

  
  @FXML
  private void onRemoveInvitesButton(ActionEvent event) {
    AccountTableCell cell = editMeetingUsersInvitedTable.getSelectionModel().getSelectedItem();
    
    if (cell != null) {
      editMeetingInvitedUsers.remove(cell);
      editMeetingNotInvitedUsers.add(cell);
      tempRejected.add(cell);
    }
  }

  
  @FXML
  private void onEditMeeting(ActionEvent event) {
    MeetingTableCell cell = meetingTable.getSelectionModel().getSelectedItem();
    int index = meetingTable.getSelectionModel().getSelectedIndex();
    
    if (cell != null && (cell.isHosting.get())) {
      Meeting meeting = dbController.getMeeting(cell.meetingID.get());
      if (meeting != null) {
        if (tabEditMeeting.isDisabled()) {
          tabEditMeeting.setDisable(false);
        }
        
        tabEditMeeting.getTabPane().getSelectionModel().select(tabEditMeeting);
        setupEditMeeting(meeting, index);
      }
    } 
  }
  
  
  /**
   * Set up the meeting for editMeetings.
   * @param meeting
   * @param meetingIndex 
   */
  private void setupEditMeeting(Meeting meeting, int meetingIndex) {
    tempRejected.clear();
    editMeetingNotInvitedUsers.clear();
    editMeetingInvitedUsers.clear();
    
    meetingSelectedIndex = meetingIndex;
    editMeetingIdText.setText(meeting.getMeetingID());
    int index;
        
    Schedule sch = meeting.getSchedule();
    index = sch.getStartDateTime().getHour() - 1;
    
    if (index < 0) {
      index = TIME_HOUR - 1;
      editMeetingTimeDay.getSelectionModel().select(0);
    } else {  
      if (index >= TIME_HOUR) {
        index = index - TIME_HOUR;
        editMeetingTimeDay.getSelectionModel().select(1);
      } else if (index < 0) {
        index = 0;
      } else {
        if (index == (TIME_HOUR - 1)) {
          editMeetingTimeDay.getSelectionModel().select(1);
        } else {
          editMeetingTimeDay.getSelectionModel().select(0);
        }
      }   
    }
    
    editMeetingStartTimeChooserHour.getSelectionModel().select(index);
    index = sch.getStartDateTime().getMinute();
    editMeetingStartTimeMinuteChooser.getSelectionModel().select(index);
    index = sch.getEndDateTime().getMinute();
    editMeetingEndTimeMinuteChooser1.getSelectionModel().select(index);
    index = sch.getEndDateTime().getHour() - 1;
    
    if (index < 0) {
      index = TIME_HOUR - 1;
      editMeetingEndTimeDay.getSelectionModel().select(0);
    } else {  
      if (index >= TIME_HOUR) {
        index = index - TIME_HOUR;
        editMeetingEndTimeDay.getSelectionModel().select(1);
      } else if (index < 0) {
        index = 0;
      } else {
        if (index == (TIME_HOUR - 1)) {
          editMeetingEndTimeDay.getSelectionModel().select(1);
        } else {
          editMeetingEndTimeDay.getSelectionModel().select(0);
        }
      }
    }
    editMeetingEndTimeChooserHour1.getSelectionModel().select(index);
    editMeetingDatePicker.setValue(sch.getStartDateTime().toLocalDate());
    
    Room roo = meeting.getRoom();
    if (roo != null) {
      RoomTableCell roomCell = new RoomTableCell(roo);
      editMeetingRoomSelectColumn.getSelectionModel().select(roomCell);
    }
    
    List<Account> allAccounts = dbController.getAllAccounts();
    for (Account acc : allAccounts) {
      if (acc.isEmployee()) {
        editMeetingNotInvitedUsers.add(new AccountTableCell(acc, meeting));
      }
    }
    
    List<Account> theList = meeting.getAcceptedList();
    for (Account acc : theList) {
      if (acc.getId() != account.getId()) {
        editMeetingInvitedUsers.add(new AccountTableCell(acc, meeting));
      }
    }
    
    theList = meeting.getInvitedList();
    
    for (Account acc : theList) {
      editMeetingInvitedUsers.add(new AccountTableCell(acc, meeting));
    }
    
    // Remove the users that are already invited, out of the noninvited list!
    if ( !editMeetingInvitedUsers.isEmpty() ) {
      for (AccountTableCell cell : editMeetingInvitedUsers) {
        for (int i = 0; i < editMeetingNotInvitedUsers.size(); ++i) {
          AccountTableCell notInvitedAccount = editMeetingNotInvitedUsers.get(i);
          if ((notInvitedAccount.id.get() == cell.id.get())) {
            editMeetingNotInvitedUsers.remove(i--);
          } else if (notInvitedAccount.id.get() == account.getId()) {
            // Remove the host from the non invited list!
            editMeetingNotInvitedUsers.remove(i--);
          }
        }
      }
    } else {
      // Just find the Host and remove him from noninvited users.
      for (int i = 0; i < editMeetingNotInvitedUsers.size(); ++i) {
        AccountTableCell notInvitedAccount = editMeetingNotInvitedUsers.get(i);
        if (notInvitedAccount.id.get() == account.getId()) {
          // Remove the host from the non invited list!
          editMeetingNotInvitedUsers.remove(i);
          break;
        }
      }
    }
  }

  
  /**
   * Remove the meeting from user.
   * @param event 
   */
  @FXML
  private void onRemoveMeeting(ActionEvent event) {
    MeetingTableCell cell = meetingTable.getSelectionModel().getSelectedItem();
    int index = meetingTable.getSelectionModel().getFocusedIndex();
    
    if (cell != null && (cell.isHosting.get())) {
      Meeting meeting = dbController.getMeeting(cell.meetingID.get());
      
      List<Meeting> meetings = account.getMeetingList();
      boolean success = false;
      
      for (int i = 0; i < meetings.size(); ++i) {
        Meeting me = meetings.get(i);
        if (me != null) {
          if (me.getMeetingID().equals(meeting.getMeetingID())) {
            meetings.remove(i);
            success = true;
            break;
          }
        }
      }
      
      if (!success) {
        meetings = account.getInvitedMeetingList();
        
        for (int i = 0; i < meetings.size(); ++i) {
          Meeting me = meetings.get(i);
          if (me != null) {
            if (me.getMeetingID().equals(meeting.getMeetingID())) {
              meetings.remove(i);
              success = true;
              break;
            }
          }
        }      
      }
      
      if (meeting != null && success) {
        dbController.updateObject(account);
        boolean removed = dbController.removeObject(meeting);
        
        
        if (removed) {
          System.out.println("Removed!");
          meetingData.remove(index);
        }
      }
    }
  }

  
  @FXML
  private void onAttendMeeting(ActionEvent event) {
    
  }

  
  @FXML
  private void onMeetingDetails(ActionEvent event) {
    
  }
}
