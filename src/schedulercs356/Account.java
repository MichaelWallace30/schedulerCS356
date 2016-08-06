/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulercs356;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
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
    private String firstName;
    private String lastName;
    private String userName;
    private int password;        
    private String address;
    private int id;
    private LinkedList<String> meetingIDList;

    public Account(String firstName, String lastName, String address,
            int id, String userName, String password,            
            Boolean admin,
            LinkedList<String> meetingIDList){
        
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setAddress(address);
        
        this.setId(id);
        this.setUserName(userName);
        this.hashPassword(password);

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
        this.password = password.hashCode();
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
    public void addObject(DataBaseInterface obj,  Statement stmt)throws SQLException{
        Account account = (Account)obj;        
        
        ResultSet rs = stmt.executeQuery("select last_insert_id() as last_id from EMPLOYEES");
        String lastid = rs.getString("last_id");
        
        account.setId(Integer.parseInt(lastid));
        
        
        String formatedString = "" + account.getId() + ", '" + account.getUserName() + "', " +
                account.getPassword() +", '" + account.getFirstName() + "', '" + account.getLastName() + "', " +
                account.isAdmin() + ",'"+ DataBaseController.listToString(account.getMeetingIDList()) + "' ";
        stmt.executeUpdate("INSERT INTO SCHEDULE " + "VALUES (" + formatedString + ")");
            
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
        "UPDATE SCHEDULE SET  USER_NAME = ?, PASSWORD = ?, FIRST_NAME = ?, "
                + "LAST_NAME = ?, ADMIN = ?, MEETING_ID_LIST = ? WHERE ID = ?");
 

        // set the preparedstatement parameters
        ps.setString(1, account.getUserName());
        ps.setInt(2, account.getPassword());
        ps.setString(3,account.getFirstName());
        ps.setString(4,account.getLastName());
        ps.setBoolean(5, account.isAdmin());
        ps.setString(6,DataBaseController.listToString(account.getMeetingIDList()));

        // call executeUpdate to execute our sql update statement
        ps.executeUpdate();
        ps.close();   
    }  
}
