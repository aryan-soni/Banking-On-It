/** CustomerGUI
*  Models the GUI of the section of a banking system handling a customer's information
*  @author Aryan Soni
*/

import javax.swing.*;
import java.awt.*;

public class CustomerGUI extends BankingGUI {
  
  // Outline attributes
  
  private CustomerController controller;
  
  private ImageIcon iconName, iconPhone, iconAddress, iconBalance;
  
  private JLabel lblName, lblPhone, lblAddress, lblKey, lblBalance, lblAlpha, lblAlphaNum;
  
  /*Constructs an CustomerGUI object 
    * @param simulator The banking simulator to link the GUI to
   */
  public CustomerGUI(BankingSimulator simulator) {
    
    super(simulator);
    this.controller = new CustomerController(this, simulator);
    
    this.iconName = new ImageIcon("views/imgs/name.png");
    this.iconAddress = new ImageIcon("views/imgs/address.png");
    this.iconPhone = new ImageIcon("views/imgs/phone.png");
    this.iconBalance = new ImageIcon("views/imgs/balance.png");
    
    this.lblName = new JLabel("Full Name: ");
    this.lblName.setFont(this.font);
    this.lblName.setForeground(Color.white);
    
    this.lblPhone = new JLabel("10-Digit Phone Number (Format: ##########): ");
    this.lblPhone.setFont(this.font);
    this.lblPhone.setForeground(Color.white);
    
    this.lblAddress = new JLabel("Address: ");
    this.lblAddress.setFont(this.font);
    this.lblAddress.setForeground(Color.white);
    
    this.lblKey = new JLabel("Alphanumeric account key: ");
    this.lblKey.setFont(this.font);
    this.lblKey.setForeground(Color.white);
    
    this.lblBalance = new JLabel("Account Balance: ");
    this.lblBalance.setFont(this.font);
    this.lblBalance.setForeground(Color.white);
    
    this.lblAlpha = new JLabel("Error! Enter an alphabetic value!");
    this.lblAlpha.setFont(this.font);
    this.lblAlpha.setForeground(Color.white);
    
    this.lblAlphaNum = new JLabel("Error! Enter an alphanumeric value!");
    this.lblAlphaNum.setFont(this.font);
    this.lblAlphaNum.setForeground(Color.white);
    
  }
  
  /* Lays out the GUI for the Customer Portal */
  public void layoutViewCustomerPortal() {
    
    JLabel lblCustomerNotFound = new JLabel("Customer Not Found!");
    lblCustomerNotFound.setFont(super.font);
    lblCustomerNotFound.setForeground(Color.white);
    
    int customerIndex = -1;
    
    do {
      
      String name = this.layoutViewCollectName();
      if(name == null) return;
    
      String key = this.layoutViewCollectKey();
      if(key == null) return;
      
      customerIndex = super.simulator.getCustomer(name, key);
      
      if(customerIndex == -1) {
        JOptionPane.showMessageDialog(null, lblCustomerNotFound, "Banking On It: Customer Log-In - ERROR!", 
           JOptionPane.ERROR_MESSAGE, super.iconError);
      }
    
    } while (customerIndex < 0);
    
    Customer customer = super.simulator.getCustomers().get(customerIndex);
    
    String customerOptions[] = {"Log-Out", "Change Key", "Change Address", "Change Phone Number",
    "View Account Details", "Withdraw Money", "Deposit Money"};
    
    int response = 0;
    
    // controller will ensure that the portal continues to be displayed until the user wishes to exit
    // controller will also handle the user input
    do {
      
       // prompt user to choose option - store response into var
       response = JOptionPane.showOptionDialog (
          null, super.lblAction, "Banking On It - Admin Portal", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, 
          super.iconUnlock, customerOptions, customerOptions[0]
       );
      
    } while (this.controller.actionPerformedPortal(response, customer));
    
  }
  
  /* Lays out the GUI to inform a customer that the action they performed was successful 
   * @param customer The customer whose action was successful
   */
  public void layoutViewSuccess(Customer customer) {
    
    JLabel lblInfo = new JLabel("Information Updated for: " + customer.getName() + "!");
    lblInfo.setFont(super.font);
    lblInfo.setForeground(Color.white);
    
    ImageIcon iconSuccess = new ImageIcon("views/imgs/success.png");
    
    JOptionPane.showMessageDialog(null, lblInfo, "Banking On It: Customer Information - SUCCESS!", 
       JOptionPane.INFORMATION_MESSAGE, iconSuccess);
  
  }

  /* Lays out the GUI to collect a customer's name
   * @return The customer's name
   */
  public String layoutViewCollectName() {
    
    String name = (String) JOptionPane.showInputDialog (
       null, this.lblName, "Banking On It: Customer Information", JOptionPane.QUESTION_MESSAGE, this.iconName, null, ""
    );
    
    // method called will return a string indicating the validity of the input
    String nameInputStatus = this.controller.actionPerformedName(name);
    
    // controller will continue to validate input until the correct input is inputted
    while (!nameInputStatus.equals("valid")) {
      
      // if the user wishers to return to the home page
      if(nameInputStatus.equals("return home")) {
        return null;
      }
      // else if there's empty input
      else if(nameInputStatus.equals("")) {
        
        JOptionPane.showMessageDialog (
           null, super.lblEmpty, "Banking On It: Customer Information - ERROR!", JOptionPane.ERROR_MESSAGE, super.iconError
        );
        
        name = (String) JOptionPane.showInputDialog (
           null, this.lblName, "Banking On It: Banking On It: Customer Information", JOptionPane.QUESTION_MESSAGE, 
           this.iconName, null, ""
        );
      }
      // else if the name does not contain letters part of the alphabet
      else if(!nameInputStatus.equals("valid")) {
        
        JOptionPane.showMessageDialog (
           null, this.lblAlpha, "Banking On It: Admin Log-In - ERROR!", JOptionPane.ERROR_MESSAGE, super.iconError
        );
        
        name = (String) JOptionPane.showInputDialog (
           null, this.lblName, "Banking On It: Admin Log-In", JOptionPane.QUESTION_MESSAGE, this.iconName, null, ""
        );
      }
      
      nameInputStatus = this.controller.actionPerformedName(name);
      
    }
    
    return name;
    
  }
  
   /* Lays out the GUI to collect a customer's phone number
   * @return The customer's phone number
   */
  public String layoutViewCollectPhone() {
    
    String phone = (String) JOptionPane.showInputDialog (
       null, this.lblPhone, "Banking On It: Customer Information", JOptionPane.QUESTION_MESSAGE, this.iconPhone, null, ""
    );
    
    // method called will return a string indicating the validity of the input
    String phoneInputStatus = this.controller.actionPerformedPhone(phone);
    
    JLabel lblIncorrectPhone = new JLabel("Error! Enter a 10-digit phone num. in the following format: ##########");
    lblIncorrectPhone.setFont(super.font);
    lblIncorrectPhone.setForeground(Color.white);
    
    // controller will continue to validate input until the correct input is inputted
    while (!phoneInputStatus.equals("valid")) {
      
      // if the user wishers to return to the home page
      if(phoneInputStatus.equals("return home")) {
        return null;
      }
      // else if there's empty input
      else if(phoneInputStatus.equals("")) {
        
        JOptionPane.showMessageDialog (
           null, super.lblEmpty, "Banking On It: Customer Information - ERROR!", JOptionPane.ERROR_MESSAGE, super.iconError
        );
        
        phone = (String) JOptionPane.showInputDialog (
           null, this.lblPhone, "Banking On It: Banking On It: Customer Information", JOptionPane.QUESTION_MESSAGE, 
           this.iconPhone, null, ""
        );
      }
      // else if the name does not contain letters part of the alphabet
      else if(!phoneInputStatus.equals("valid")) {
        
        JOptionPane.showMessageDialog (
           null, lblIncorrectPhone, "Banking On It: Admin Log-In - ERROR!", JOptionPane.ERROR_MESSAGE, super.iconError
        );
        
        phone = (String) JOptionPane.showInputDialog (
           null, this.lblPhone, "Banking On It: Admin Log-In", JOptionPane.QUESTION_MESSAGE, this.iconPhone, null, ""
        );
      }
      
      phoneInputStatus = this.controller.actionPerformedPhone(phone);
      
    }
    
    return phone;
    
  }
  
    /* Lays out the GUI to collect a customer's address
   * @return The customer's address
   */
  public String layoutViewCollectAddress() {
    
    String address = (String) JOptionPane.showInputDialog (
       null, this.lblAddress, "Banking On It: Customer Information", JOptionPane.QUESTION_MESSAGE, this.iconAddress, null, ""
    );
    
    // method called will return a string indicating the validity of the input
    String addressInputStatus = this.controller.actionPerformedAlphaNum(address);
    
    // controller will continue to validate input until the correct input is inputted
    while (!addressInputStatus.equals("valid")) {
      
      // if the user wishers to return to the home page
      if(addressInputStatus.equals("return home")) {
        return null;
      }
      // else if there's empty input
      else if(addressInputStatus.equals("")) {
        
        JOptionPane.showMessageDialog (
           null, super.lblEmpty, "Banking On It: Customer Information - ERROR!", JOptionPane.ERROR_MESSAGE, super.iconError
        );
        
        address = (String) JOptionPane.showInputDialog (
           null, this.lblAddress, "Banking On It: Banking On It: Customer Information", JOptionPane.QUESTION_MESSAGE, 
           this.iconAddress, null, ""
        );
      }
      // else if the name does not contain letters part of the alphabet
      else if(!addressInputStatus.equals("valid")) {
        
        JOptionPane.showMessageDialog (
           null, this.lblAlphaNum, "Banking On It: Customer Information - ERROR!", JOptionPane.ERROR_MESSAGE, super.iconError
        );
        
        address = (String) JOptionPane.showInputDialog (
           null, this.lblAddress, "Banking On It: Customer Information", JOptionPane.QUESTION_MESSAGE, this.iconAddress, null, ""
        );
      }
      
      addressInputStatus = this.controller.actionPerformedAlphaNum(address);
      
    }
    
    return address;
    
  }
  
  /* Lays out the GUI to collect a customer's key
   * @return The customer's account key
   */
  public String layoutViewCollectKey() {
    
    String key = (String) JOptionPane.showInputDialog (
       null, this.lblKey, "Banking On It: Customer Information", JOptionPane.QUESTION_MESSAGE, super.iconKey, null, ""
    );
    
    // method called will return a string indicating the validity of the input
    String keyInputStatus = this.controller.actionPerformedAlphaNum(key);
    
    // controller will continue to validate input until the correct input is inputted
    while (!keyInputStatus.equals("valid")) {
      
      // if the user wishers to return to the home page
      if(keyInputStatus.equals("return home")) {
        return null;
      }
      // else if there's empty input
      else if(keyInputStatus.equals("")) {
        
        JOptionPane.showMessageDialog (
           null, super.lblEmpty, "Banking On It: Customer Information - ERROR!", JOptionPane.ERROR_MESSAGE, super.iconError
        );
        
        key = (String) JOptionPane.showInputDialog (
           null, this.lblKey, "Banking On It: Banking On It: Customer Information", JOptionPane.QUESTION_MESSAGE, 
           super.iconKey, null, ""
        );
      }
      // else if the name does not contain letters part of the alphabet
      else if(!keyInputStatus.equals("valid")) {
        
        JOptionPane.showMessageDialog (
           null, this.lblAlphaNum, "Banking On It: Customer Information - ERROR!", JOptionPane.ERROR_MESSAGE, super.iconError
        );
        
        key = (String) JOptionPane.showInputDialog (
           null, this.lblKey, "Banking On It: Customer Information", JOptionPane.QUESTION_MESSAGE, super.iconKey, null, ""
        );
      }
      
      keyInputStatus = this.controller.actionPerformedAlphaNum(key);
      
    }
    
    return key;
    
  }
  
  /* Lays out the GUI to collect a customer's account balance
   * @return The customer's balance
   */
  public String layoutViewCollectBalance() {
    
    String balance = (String) JOptionPane.showInputDialog (
       null, this.lblBalance, "Banking On It: Customer Information", JOptionPane.QUESTION_MESSAGE, this.iconBalance, null, ""
    );
    
    // method called will return a string indicating the validity of the input
    String balanceInputStatus = this.controller.actionPerformedBalance(balance);
    
    JLabel lblIncorrectBalance = new JLabel("Error! Enter a balance that is a number and is >= 0!");
    lblIncorrectBalance.setFont(super.font);
    lblIncorrectBalance.setForeground(Color.white);
    
    // controller will continue to validate input until the correct input is inputted
    while (!balanceInputStatus.equals("valid")) {
      
      // if the user wishers to return to the home page
      if(balanceInputStatus.equals("return home")) {
        return null;
      }
      // else if there's empty input
      else if(balanceInputStatus.equals("")) {
        
        JOptionPane.showMessageDialog (
           null, super.lblEmpty, "Banking On It: Customer Information - ERROR!", JOptionPane.ERROR_MESSAGE, super.iconError
        );
        
        balance = (String) JOptionPane.showInputDialog (
           null, this.lblBalance, "Banking On It: Banking On It: Customer Information", JOptionPane.QUESTION_MESSAGE, 
           this.iconBalance, null, ""
        );
      }
      // else if the name does not contain letters part of the alphabet
      else if(!balanceInputStatus.equals("valid")) {
        
        JOptionPane.showMessageDialog (
           null, lblIncorrectBalance, "Banking On It: Customer Information - ERROR!", JOptionPane.ERROR_MESSAGE, super.iconError
        );
        
        balance = (String) JOptionPane.showInputDialog (
           null, this.lblBalance, "Banking On It: Customer Information", JOptionPane.QUESTION_MESSAGE, this.iconBalance, null, ""
        );
      }
      
      balanceInputStatus = this.controller.actionPerformedBalance(balance);
      
    }
    
    return balance;
    
  }
  
   /* Lays out the GUI to deposit an amount into the customer's balance
   * @param customer The customer to deposit the amount to
   * @return The withdrawal amount
   */
  public String layoutViewDeposit(Customer customer) {
    
    JLabel lblDeposit = new JLabel("Deposit: ");
    lblDeposit.setFont(super.font);
    lblDeposit.setForeground(Color.white);
    
    String deposit = (String) JOptionPane.showInputDialog (
       null, lblDeposit, "Banking On It: Customer Information", JOptionPane.QUESTION_MESSAGE, this.iconBalance, null, ""
    );
    
    // method called will return a string indicating the validity of the input
    String depositInputStatus = this.controller.actionPerformedDeposit(deposit, customer);
    
    JLabel lblIncorrectDeposit = new JLabel("Error! Enter a deposit that is a number and is >= 0!");
    lblIncorrectDeposit.setFont(super.font);
    lblIncorrectDeposit.setForeground(Color.white);
    
    // controller will continue to validate input until the correct input is inputted
    while (!depositInputStatus.equals("valid")) {
      
      // if the user wishers to return to the home page
      if(depositInputStatus.equals("return home")) {
        return null;
      }
      // else if there's empty input
      else if(depositInputStatus.equals("")) {
        
        JOptionPane.showMessageDialog (
           null, super.lblEmpty, "Banking On It: Customer Information - ERROR!", JOptionPane.ERROR_MESSAGE, super.iconError
        );
        
        deposit = (String) JOptionPane.showInputDialog (
           null, lblDeposit, "Banking On It: Banking On It: Customer Information", JOptionPane.QUESTION_MESSAGE, 
           this.iconBalance, null, ""
        );
      }
      // else if the name does not contain letters part of the alphabet
      else if(!depositInputStatus.equals("valid")) {
        
        JOptionPane.showMessageDialog (
           null, lblIncorrectDeposit, "Banking On It: Customer Information - ERROR!", JOptionPane.ERROR_MESSAGE, super.iconError
        );
        
        deposit = (String) JOptionPane.showInputDialog (
           null, lblDeposit, "Banking On It: Customer Information", JOptionPane.QUESTION_MESSAGE, this.iconBalance, null, ""
        );
      }
      
      depositInputStatus = this.controller.actionPerformedDeposit(deposit, customer);
      
    }
    
    return deposit;
    
  }
  
   /* Lays out the GUI to withdraw an amount from the customer's balance
   * @param customer The customer to withdraw the amount from
   * @return The withdrawal amount
   */
  public String layoutViewWithdrawal(Customer customer) {
    
    JLabel lblWithdrawal = new JLabel("Withdrawal: ");
    lblWithdrawal.setFont(super.font);
    lblWithdrawal.setForeground(Color.white);
    
    String withdrawal = (String) JOptionPane.showInputDialog (
       null, lblWithdrawal, "Banking On It: Customer Information", JOptionPane.QUESTION_MESSAGE, this.iconBalance, null, ""
    );
    
    // method called will return a string indicating the validity of the input
    String withdrawalInputStatus = this.controller.actionPerformedWithdrawal(withdrawal, customer);
    
    JLabel lblIncorrectWithdrawal= new JLabel("Error! Enter a withdrawal that is a number is >= " 
       + super.money.format(customer.getBalance()) + "!");
    
    lblIncorrectWithdrawal.setFont(super.font);
    lblIncorrectWithdrawal.setForeground(Color.white);
    
    // controller will continue to validate input until the correct input is inputted
    while (!withdrawalInputStatus.equals("valid")) {
      
      // if the user wishers to return to the home page
      if(withdrawalInputStatus.equals("return home")) {
        return null;
      }
      // else if there's empty input
      else if(withdrawalInputStatus.equals("")) {
        
        JOptionPane.showMessageDialog (
           null, super.lblEmpty, "Banking On It: Customer Information - ERROR!", JOptionPane.ERROR_MESSAGE, super.iconError
        );
        
        withdrawal = (String) JOptionPane.showInputDialog (
           null, lblWithdrawal, "Banking On It: Banking On It: Customer Information", JOptionPane.QUESTION_MESSAGE, 
           this.iconBalance, null, ""
        );
      }
      // else if the name does not contain letters part of the alphabet
      else if(!withdrawalInputStatus.equals("valid")) {
        
        JOptionPane.showMessageDialog (
           null, lblIncorrectWithdrawal, "Banking On It: Customer Information - ERROR!", JOptionPane.ERROR_MESSAGE, super.iconError
        );
        
        withdrawal = (String) JOptionPane.showInputDialog (
           null, lblWithdrawal, "Banking On It: Customer Information", JOptionPane.QUESTION_MESSAGE, this.iconBalance, null, ""
        );
      }
      
      withdrawalInputStatus = this.controller.actionPerformedWithdrawal(withdrawal, customer);
      
    }
    
    return withdrawal;
    
  }
  
   /* Lays out the GUI to display a customer's information
   * @param customer The customer whose information to display
   */
  public void layoutViewInfo(Customer customer) {
    
    String display = "<html> Name: " + customer.getName() + "<br>Phone Number: " + 
      super.formatPhone(customer.getPhoneNum()) + "<br>Address: "
     + customer.getAddress() + "<br>Account Key: " + customer.getKey() + "<br>Account Balance: " + 
      customer.getBalance() + "</html>";
    
    JLabel lblDisplay = new JLabel(display);
    lblDisplay.setFont(super.font);
    lblDisplay.setForeground(Color.white);
    
    JOptionPane.showMessageDialog (
       null, lblDisplay, "Banking On It: Customer Information", JOptionPane.INFORMATION_MESSAGE, this.iconName
     );
    
  }
  
  /* Lays out the GUI for the admin portal
  public void layoutViewPortal() {
    
    String adminOptions[] = {"Log-Out", "Modify a Customer's Information", "View All Customers"};
    
    int response = 0;
    
    // controller will ensure that the portal continues to be displayed until the user wishes to exit
    // controller will also handle the user input
    do {
      
       // prompt user to choose option - store response into var
       response = JOptionPane.showOptionDialog (
          null, super.lblAction, "Banking On It - Admin Portal", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, 
          super.iconUnlock, adminOptions, adminOptions[0]
       );
      
    } while (this.controller.actionPerformedPortal(response));
    
  }
  */

}