/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulercs356;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author MAGarcia
 */
public class AccountAdminTableCell {
  public IntegerProperty accountId;
  public StringProperty username;
  public StringProperty fullname;
  public IntegerProperty password;
  public StringProperty address;
  public BooleanProperty employee;
  public BooleanProperty admin;
  
  public AccountAdminTableCell(Account account) {
    accountId = new SimpleIntegerProperty(account.getId());
    fullname = new SimpleStringProperty(account.getFirstName() + " " + account.getLastName());
    username = new SimpleStringProperty(account.getUserName());
    password = new SimpleIntegerProperty(account.getPassword());
    address = new SimpleStringProperty(account.getAddress());
    employee = new SimpleBooleanProperty(account.isEmployee());
    admin = new SimpleBooleanProperty(account.isAdmin());
  }
}
