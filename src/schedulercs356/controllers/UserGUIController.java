/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulercs356.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
  
  // Our databaseController.
  private DataBaseController dbController;
  
  private Account account;
  
  private List<Meeting> accountMeetings;
  private List<Account> accountReferences;
  private List<Schedule> accountSchedules;
  
  private ObservableList<MeetingTableCell> meetingData;
  private ObservableList<AccountTableCell> accounts;
  private ObservableList<Schedule> schedules;
  private ObservableList<AccountAdminTableCell> adminEnabledAccounts;
  private ObservableList<RoomTableCell> rooms;
  private ObservableList<AccountTableCell> notInvitedUsers;
  private ObservableList<AccountTableCell> invitedUsers;
  
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
    SplitPane.setResizableWithParent(sidebarSplitPane, Boolean.FALSE);
    SplitPane.setResizableWithParent(otherSplitPane, Boolean.FALSE);
 
    account = (Account)rb.getObject("data"); 
   
    
    if (account != null) {
      initializeMeetingTable();
      initializeUsersInMeetingTable();
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
      sidebarDate.setText(new Date().toString());
      runTimeOnThread();
      addMeetingsToTables();      
      addRoomsInTables();
      initializeChoiceBoxes();
    } else {
      throw new RuntimeException("Null account value was passed!");
    }
  }
  
  
  /**
   * Run the time on a thread.
   */
  private void runTimeOnThread() {
    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
      sidebarDate.setText(new Date().toString());
    }));
    
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();
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
    List<Meeting> meetings = dbController.getAllMeetings();
    
    for (Meeting meeting : meetings) {
      
      if (meeting != null) {
        // Prolly don't need it yet.
        //List<Account> rejectedList = meeting.getRejectedList();
        if (meeting.getOwnerID() == account.getId()) {
          accountMeetings.add(meeting);
          meetingData.add(new MeetingTableCell(meeting, account));       
        } else {
          
          List<Account> acceptedList = meeting.getAcceptedList();
          List<Account> invitedList = meeting.getInvitedList();
        
          for (Account acc : acceptedList) {
            if (acc.getId() == account.getId()) {
              accountMeetings.add(meeting);
              meetingData.add(new MeetingTableCell(meeting, account));
            }
          }
        
          for (Account acc : invitedList) {
            if (acc.getId() == account.getId()) {
              accountMeetings.add(meeting);
              meetingData.add(new MeetingTableCell(meeting, account));
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
  }
  
  
  /**
   * Initialize the Meeting tables with Users.
   */
  private void initializeUsersInMeetingTable() {
    invitedUsers = FXCollections.observableArrayList();
    notInvitedUsers = FXCollections.observableArrayList();
    
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
    editMeetingUsersNoInviteTable.setItems(notInvitedUsers);
    editMeetingUsersInvitedTable.setItems(invitedUsers);
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
   * @param event 
   */
  @FXML
  void onCreateMeeting(ActionEvent event) {
    Meeting meeting = new Meeting();
    meeting.setOwnerID(account.getId());
    meeting.setInvitedList(new LinkedList<>());
    meeting.setAcceptedList(new LinkedList<>());
    meeting.setRejectedList(new LinkedList<>());
    dbController.addObject(meeting);
    
    
    if (tabEditMeeting.isDisabled()) {
      tabEditMeeting.setDisable(false);
    }
    
    tabEditMeeting.getTabPane().getSelectionModel().select(tabEditMeeting);
    
    List<Account> users = dbController.getAllAccounts();
    
    for (Account user : users) {
      if (user.isEmployee()) {
        AccountTableCell cell = new AccountTableCell(user, meeting);
        notInvitedUsers.add(cell);
      }
    }
    
    meeting.getAcceptedList().add(account);
    editMeetingIdText.setText(meeting.getMeetingID());
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
      
      
      System.out.println("I did it!");
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
    
    
  }

  
  /**
   * 
   * @param event 
   */
  @FXML
  private void onRemoveRoom(ActionEvent event) {
    
  }

  
  @FXML
  private void onEditMeetingUpdate(ActionEvent event) {
  }

  
  @FXML
  private void onAddToInvitesButton(ActionEvent event) {
  }

  
  @FXML
  private void onRemoveInvitesButton(ActionEvent event) {
  }

  
  @FXML
  private void onEditMeeting(ActionEvent event) {
  }

  
  @FXML
  private void onRemoveMeeting(ActionEvent event) {
    MeetingTableCell cell = meetingTable.getSelectionModel().getSelectedItem();
    int index = meetingTable.getSelectionModel().getFocusedIndex();
    
    if (cell != null) {
      Meeting meeting = dbController.getMeeting(cell.meetingID.get());
      if (meeting != null) {
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
