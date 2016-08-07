/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulercs356;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

/**
 *
 * @author Micahel Wallace
 * Needs:
 *  @TODO
 *  Missing address in employees table

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
    private LinkedList<String> meetingIDList;

    
    //non hashed password as string
    public Account(String firstName, String lastName, String address,
            int id, String userName, String password,            
            Boolean employee, Boolean admin,
            LinkedList<String> meetingIDList){
        
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setAddress(address);
        
        this.setId(id);
        this.setUserName(userName);
        this.hashPassword(password);

        this.setEmployee(employee);
        this.setAdmin(admin);
        
        this.setMeetingIDList(meetingIDList);
        
    }

    //hashed password as int
    public Account(String firstName, String lastName, String address,
            int id, String userName, int password,            
            Boolean employee, Boolean admin,
            LinkedList<String> meetingIDList){
        
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setAddress(address);
        
        this.setId(id);
        this.setUserName(userName);
        this.setPassword(password);

        this.setEmployee(employee);
        this.setAdmin(admin);
        
        this.setMeetingIDList(meetingIDList);
        
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
    public LinkedList<String> getMeetingIDList() {
        return meetingIDList;
    }

    public void setMeetingIDList(LinkedList<String> meetingIDList) {         
        if(meetingIDList == null){
            this.meetingIDList = new LinkedList<>();
        }
        else{            
            this.meetingIDList = meetingIDList;
         }
    }
    @Override
    public void addObject(DataBaseInterface obj,  Connection con)throws SQLException{
Account account = (Account)obj; 
        PreparedStatement ps = con.prepareStatement(
        "INSERT INTO EMPLOYEES" 
            +"(ID, USER_NAME, PASSWORD, FIRST_NAME, LAST_NAME, ADMIN, MEETING_ID_LIST, EMPLOYEE, ADDRESS) VALUES"
            + "(?,?,?,?,?,?,?,?, ?)");
 
        // set the preparedstatement parameters
        ps.setInt(1,account.getId());
        ps.setString(2, account.getUserName());
        ps.setInt(3, account.getPassword());
        ps.setString(4,account.getFirstName());
        ps.setString(5,account.getLastName());
        ps.setBoolean(6, account.isAdmin());
        ps.setString(7,DataBaseController.listToString(account.getMeetingIDList()));
        ps.setBoolean(8, account.isEmployee());
        ps.setString(9, account.getAddress());

        // call executeUpdate to execute our sql update statement
        ps.executeUpdate();
        ps.close();   
    }  

                
    @Override
    public void removeObject(DataBaseInterface obj,  Statement stmt)throws SQLException{
        Account account = (Account)obj; 
        stmt.executeUpdate("DELETE FROM EMPLOYEES " + " ID = " +  account.getId());
        
    }  
    @Override
    public void updateObject(DataBaseInterface obj,  Connection con)throws SQLException{
        Account account = (Account)obj; 
        PreparedStatement ps = con.prepareStatement(
        "UPDATE EMPLOYEES SET  USER_NAME = ?, PASSWORD = ?, FIRST_NAME = ?, "
                + "LAST_NAME = ?, ADMIN = ?, MEETING_ID_LIST = ?, EMPLOYEE = ?, ADDRESS = ? WHERE ID = ?");
 

        // set the preparedstatement parameters
        ps.setString(1, account.getUserName());
        ps.setInt(2, account.getPassword());
        ps.setString(3,account.getFirstName());
        ps.setString(4,account.getLastName());
        ps.setBoolean(5, account.isAdmin());
        ps.setString(6,DataBaseController.listToString(account.getMeetingIDList()));
        ps.setBoolean(7, account.isEmployee());
        ps.setString(8, account.getAddress());
        ps.setInt(9, account.getId());

        // call executeUpdate to execute our sql update statement
        ps.executeUpdate();
        ps.close();   
    }  

    public void setPassword(int password) {
        this.password = password;
    }

}
