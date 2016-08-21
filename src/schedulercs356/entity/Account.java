/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulercs356.entity;

import schedulercs356.entity.Meeting;
import schedulercs356.controllers.DataBaseController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 *
 * @author Micahel Wallace
 * 
 */
public class Account implements DataBaseInterface {
    private Boolean admin;
    private Boolean employee;
    private String firstName;  
    private String lastName;
    private String userName;
    private int password;        
    private String address;
    private int id;
    
    private LinkedList<Meeting> invitedMeetingList;
    private String invitedMeetingListTableName;
    public static String queryAccountInvitedMeetingTable = "ACCOUNT_INVITED_MEETING_TABLE_LIST_ACCOUNT_ID_";
    
    private LinkedList<Meeting> meetingList;
    private String meetingListTableName;
    public static String queryAccountMeetingTable = "ACCOUNT_MEETING_TABLE_LIST_ACCOUNT_ID_";

    
    //non hashed password as string
    public Account(String firstName, String lastName, String address,
            Integer id, String userName, String password,            
            Boolean employee, Boolean admin,
            LinkedList<Meeting> meetingList, LinkedList<Meeting> invitedMeetingList){
        
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setAddress(address);
        
        this.setId(id);
        this.setUserName(userName);
        this.hashPassword(password);

        this.setEmployee(employee);
        this.setAdmin(admin);
        
        this.setMeetingList(meetingList);
        this.setInvitedMeetingList(invitedMeetingList);
        
        this.setAccountInvitedMeetingListTableName(queryAccountInvitedMeetingTable + getId());
        this.setMeetingListTableName(queryAccountMeetingTable  + getId());
        
        //create table if dne for meeting list
        DataBaseController db = new DataBaseController();
        if(!db.doesTableExsist(getMeetingListTableName())){
            String sql = "CREATE TABLE " + getMeetingListTableName() +"(MEETING_ID VARCHAR(40) primary key)";
            db.createTable(sql);            
        }
        else{
            //ignore exception table already exsist
        }        
        //create table if dne for invited list
        if(!db.doesTableExsist(getInvitedMeetingListTableName())){
            String sql = "CREATE TABLE " + getInvitedMeetingListTableName() +"(MEETING_ID VARCHAR(40) primary key)";
            db.createTable(sql);            
        }
        else{
            //ignore exception table already exsist
        }
    }

    //hashed password as int
    public Account(String firstName, String lastName, String address,
            int id, String userName, int password,            
            Boolean employee, Boolean admin,
            LinkedList<Meeting> meetingList, LinkedList<Meeting> invitedMeetingList){
        
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setAddress(address);
        
        this.setId(id);
        this.setUserName(userName);
        this.setPassword(password);

        this.setEmployee(employee);
        this.setAdmin(admin);
        
        this.setMeetingList(meetingList);
        this.setInvitedMeetingList(invitedMeetingList);
        
        this.setAccountInvitedMeetingListTableName(queryAccountInvitedMeetingTable  + getId());
        this.setMeetingListTableName(queryAccountMeetingTable  + getId());

        //create table if dne for meeting list
        DataBaseController db = new DataBaseController();
        if(!db.doesTableExsist(this.getMeetingListTableName())){
            String sql = "CREATE TABLE " + getMeetingListTableName() +"(MEETING_ID VARCHAR(40) primary key)";
            db.createTable(sql);            
        }
        else{
            //ignore exception table already exsist
        }        
        //create table if dne for invited list
        if(!db.doesTableExsist(this.getInvitedMeetingListTableName())){
            String sql = "CREATE TABLE " + getInvitedMeetingListTableName() +"(MEETING_ID VARCHAR(40) primary key)";
            db.createTable(sql);            
        }
        else{
            //ignore exception table already exsist
        }
        
    }
    public Boolean isAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getPassword() {
        return password;
    }

    public void hashPassword(String password) {
        this.setPassword(password.hashCode());
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean isEmployee() {
        return employee;
    }

    public void setEmployee(Boolean employee) {
        this.employee = employee;
    }    
    public LinkedList<Meeting> getMeetingList() {
        return meetingList;
    }

    public void setMeetingList(LinkedList<Meeting> meetingList) {         
        if(meetingList == null){
            this.meetingList = new LinkedList<>();
        }
        else{            
            this.meetingList = meetingList;
         }
    }
    
    public void setPassword(int password) {
        this.password = password;
    }

    public LinkedList<Meeting> getInvitedMeetingList() {
        return invitedMeetingList;
    }

    public void setInvitedMeetingList(LinkedList<Meeting> invitedMeetingList) {
        this.invitedMeetingList = invitedMeetingList;
    }
        public String getAccountInvitedMeetingListTableName() {
        return getInvitedMeetingListTableName();
    }

    public void setAccountInvitedMeetingListTableName(String accountInvitedMeetingListTableName) {
        this.setInvitedMeetingListTableName(accountInvitedMeetingListTableName);
    }

    public String getMeetingListTableName() {
        return meetingListTableName;
    }

    public void setMeetingListTableName(String accountMeetingListTableName) {
        this.meetingListTableName = accountMeetingListTableName;
    }

    public String getInvitedMeetingListTableName() {
        return invitedMeetingListTableName;
    }

    public void setInvitedMeetingListTableName(String invitedMeetingListTableName) {
        this.invitedMeetingListTableName = invitedMeetingListTableName;
    }

    public void removeMeeting(Meeting meeting, Connection con){
        
        try{
            PreparedStatement ps = con.prepareStatement(
            "DELETE FROM " + getMeetingListTableName() + "WHERE ID = ?");
             ps.setString(1,meeting.getMeetingID());   
             ps.executeUpdate();       
        
        }catch(SQLException ex){
            //igorne if not found
        }
        
        try{
            PreparedStatement ps = con.prepareStatement(
            "DELETE FROM " + getInvitedMeetingListTableName() + "WHERE ID = ?");
             ps.setString(1,meeting.getMeetingID());   
             ps.executeUpdate();       
        
        }catch(SQLException ex){
            //igorne if not found
        }
        
        
    }
    
    private Boolean addMeetingTable(Connection con, String meetingID, String listName){
        try
        {
            PreparedStatement ps = con.prepareStatement(
            "INSERT INTO " + listName 
                +"(MEETING_ID) VALUES"
                + "(?)");  

            ps.setString(1,meetingID);

            int i = ps.executeUpdate();
            ps.close();   

            if(i <= 0){
            return false;
            }
            return true;         
        }
        catch(SQLException err){
            //System.out.println(err.getMessage());
            //ignore duplicate excetions
        }
        return false;
    }

    @Override
    public void addObject(DataBaseInterface obj,  Connection con)throws SQLException{
        Account account = (Account)obj; 
        
        //get last id of insertion to db
        Statement stmt = con.createStatement();
        String SQL = "SELECT * FROM EMPLOYEES";
        ResultSet rs = stmt.executeQuery(SQL);

        int lastID = 0;
        while(rs.next()){
            lastID = rs.getInt(1);
        }
        lastID++;
        
        account.setId(lastID);
        
        
        PreparedStatement ps = con.prepareStatement(
        "INSERT INTO EMPLOYEES" 
            +"(ID, USER_NAME, PASSWORD, FIRST_NAME, LAST_NAME, ADMIN, EMPLOYEE, ADDRESS) VALUES"
            + "(?,?,?,?,?,?,?,?)");
 
        ListIterator<Meeting> it;
        it = meetingList.listIterator();
        while(it.hasNext()){
            addMeetingTable(con, it.next().getMeetingID(), meetingListTableName);
        }
        
        it = invitedMeetingList.listIterator();
        while(it.hasNext()){
            addMeetingTable(con, it.next().getMeetingID(), invitedMeetingListTableName);
        }

        // set the preparedstatement parameters
        ps.setInt(1,account.getId());
        ps.setString(2, account.getUserName());
        ps.setInt(3, account.getPassword());
        ps.setString(4,account.getFirstName());
        ps.setString(5,account.getLastName());
        ps.setBoolean(6, account.isAdmin());        
        ps.setBoolean(7, account.isEmployee());
        ps.setString(8, account.getAddress());
        

        // call executeUpdate to execute our sql update statement
        ps.executeUpdate();
        ps.close();   
    }  

                
    @Override
    public void removeObject(DataBaseInterface obj, Connection con)throws SQLException{
        Statement stmt = con.createStatement();
        Account account = (Account)obj; 
        stmt.executeUpdate("DELETE FROM EMPLOYEES " + "WHERE ID = " +  account.getId());
          
        String sql = "DROP TABLE " + this.meetingListTableName;
        stmt.executeUpdate(sql);
        
        String sq2 = "DROP TABLE " + this.invitedMeetingListTableName;
        stmt.executeUpdate(sq2);
    }  
    
    @Override
    public Boolean updateObject(DataBaseInterface obj,  Connection con)throws SQLException{
        Account account = (Account)obj; 
        PreparedStatement ps = con.prepareStatement(
        "UPDATE EMPLOYEES SET  USER_NAME = ?, PASSWORD = ?, FIRST_NAME = ?, "
                + "LAST_NAME = ?, ADMIN = ?, EMPLOYEE = ?, ADDRESS = ? WHERE ID = ?");
        
        Iterator<Meeting> it;
        it = meetingList.iterator();
        while(it.hasNext()) {
          Meeting meeting = it.next();
          if (meeting != null) {
            addMeetingTable(con, meeting.getMeetingID(), meetingListTableName);
          }
        }
        
        it = invitedMeetingList.listIterator();
        while(it.hasNext()) {
          Meeting meeting = it.next();
          if (meeting != null) {
            addMeetingTable(con, meeting.getMeetingID(), invitedMeetingListTableName);
          }
        }

        // set the preparedstatement parameters
        ps.setString(1, account.getUserName());
        ps.setInt(2, account.getPassword());
        ps.setString(3,account.getFirstName());
        ps.setString(4,account.getLastName());
        ps.setBoolean(5, account.isAdmin());
        ps.setBoolean(6, account.isEmployee());
        ps.setString(7, account.getAddress());
        ps.setInt(8, account.getId());

        // call executeUpdate to execute our sql update statement
        int i = ps.executeUpdate();
        ps.close();   
        
        if(i <= 0){
        return false;
        }
        return true;
    }  
}
