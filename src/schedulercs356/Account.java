/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulercs356;

import java.util.LinkedList;

/**
 *
 * @author Micahel Wallace
 * Needs:
 *  getAvailability
 *
 * Not sure if java calendar is too much info to store on data base
 * 
 */
public class Account {
    private Boolean admin;
    private Boolean employee;
    private String firstName;
    private String lastName;
    private String userName;
    private int password;        
    private String address;
    private int id;
    private LinkedList<String> meetingIDList;

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
         
    public Boolean isAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Boolean isEmployee() {
        return employee;
    }

    public void setEmployee(Boolean employee) {
        this.employee = employee;
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
}
