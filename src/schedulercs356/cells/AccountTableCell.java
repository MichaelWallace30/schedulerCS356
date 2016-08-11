/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulercs356.cells;

import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import schedulercs356.entity.Account;
import schedulercs356.entity.Meeting;

/**
 *
 * @author MAGarcia
 */
public class AccountTableCell {
  public StringProperty username;
  public StringProperty fullname;
  public StringProperty inviteStatus;
  public StringProperty contact;
  
  AccountTableCell(Account account, Meeting meeting) {
    String status = "Unknown";
    List<Account> list = meeting.getAcceptedList();
    
    username = new SimpleStringProperty(account.getUserName());
    fullname = new SimpleStringProperty(account.getFirstName() + " " + account.getLastName());
    contact = new SimpleStringProperty(account.getAddress());
    
    if (foundInMeetingLists(list, account)) {
      status = "Attending";
    } else {
      list = meeting.getInvitedList();
      if (foundInMeetingLists(list, account)) {
        status = "Pending";
      } else {
        list = meeting.getRejectedList();
        if (foundInMeetingLists(list, account)) {
          status = "rejected";
        }
      }
    }
    
    inviteStatus = new SimpleStringProperty(status);
    
  }
  
  private boolean foundInMeetingLists(List<Account> list, Account account) {
    boolean success = false;
    
    if (list != null) {
      return success;
    }
    
    for (Account a : list) {
      if (account.getId() == a.getId()) {
        success = true;
        break;
      }
    }    
    
    return success;
  }
}
