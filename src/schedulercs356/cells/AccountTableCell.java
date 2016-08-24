/*
 * The MIT License
 *
 * Copyright 2016 Mario Garcia, Michael Wallace.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package schedulercs356.cells;

import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import schedulercs356.entity.Account;
import schedulercs356.entity.Meeting;
import schedulercs356.entity.MeetingStatus;

/**
 *
 * @author MAGarcia
 */
public class AccountTableCell {
  public StringProperty username;
  public StringProperty fullname;
  public StringProperty inviteStatus;
  public StringProperty contact;
  public IntegerProperty id;
  
  
  /**
   * Constructor.
   * @param account
   * @param meeting 
   */
  public AccountTableCell(Account account, Meeting meeting) {
    String status = MeetingStatus.UNKNOWN;
    List<Account> list = meeting.getAcceptedList();
    
    username = new SimpleStringProperty(account.getUserName());
    fullname = new SimpleStringProperty(account.getFirstName() + " " + account.getLastName());
    contact = new SimpleStringProperty(account.getAddress());
    id = new SimpleIntegerProperty(account.getId());
    
    if (foundInMeetingLists(list, account)) {
      status = MeetingStatus.ATTENDING;
    } else {
      list = meeting.getInvitedList();
      if (foundInMeetingLists(list, account)) {
        status = MeetingStatus.PENDING;
      } else {
        list = meeting.getRejectedList();
        if (foundInMeetingLists(list, account)) {
          status = MeetingStatus.REJECTED;
        }
      }
    }
    
    inviteStatus = new SimpleStringProperty(status);
  }
  
  
  /**
   * Find the account in the list. Return true if found.
   * @param list
   * @param account
   * @return 
   */
  private boolean foundInMeetingLists(List<Account> list, Account account) {
    boolean success = false;
    
    if (list == null) {
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
