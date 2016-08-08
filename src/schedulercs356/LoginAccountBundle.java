/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulercs356;

import java.util.Collections;
import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 *
 * @author MAGarcia
 */
public class LoginAccountBundle extends ResourceBundle {

  private Account account;
  @Override
  protected Object handleGetObject(String key) {
    if (key.equals("data")) {
      return account;
    }
    
    return null;
  }

  // Im noob so i dunno what this does...
  @Override
  public Enumeration<String> getKeys() {
    return null;
  }
  
  // Get the account reference.
  public void setAccount(Account account) {
    this.account = account;
  }
  
}
