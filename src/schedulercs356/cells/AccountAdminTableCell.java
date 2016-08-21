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

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import schedulercs356.entity.Account;

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
