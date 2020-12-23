/** BankingGUI
*  Models the GUI of a banking system
*  @author Aryan Soni
*/

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;

public class BankingGUI {
  
  // Declare attributes
  
  private BankingSimulator simulator;
  
  private UIManager UI = new UIManager();
  
  private ButtonsController controller;
  
  private ImageIcon iconKey;
  private ImageIcon iconError;
  private ImageIcon iconUnlock;
  
  private JLabel lblEmpty;
  private JLabel lblIncorrectKey;
  private JLabel lblAction;
  
  private Font font;
  
  private NumberFormat money;

  /*Constructs a BankingGUI object 
    * @param simulator The banking simulator to link the GUI to
    */
  public BankingGUI (BankingSimulator simulator) {
    
    this.simulator = simulator;
    this.simulator.setGUI(this);
    this.controller = new ButtonsController(this, this.simulator);
    
    // Configurate settings for UIManager
    this.UI.put("OptionPane.background", Color.black);
    this.UI.put("Panel.background", Color.black);
    UIManager.put("OptionPane.minimumSize", new Dimension(600, 175));
    this.UI.put("OptionPane.cancelButtonText", "Return");
    
    this.font = new Font("Monospaced", Font.BOLD, 16);
    
    this.iconKey = new ImageIcon("./imgs/key.png");
    this.iconError = new ImageIcon("./imgs/error.png");
    this.iconUnlock = new ImageIcon("./imgs/unlock.png");
    
    this.lblEmpty = new JLabel("We received an empty input. Please try again.");
    this.lblEmpty.setFont(this.font);
    this.lblEmpty.setForeground(Color.white);
    this.lblAction = new JLabel("Welcome! Please select an action.");
    this.lblAction.setFont(this.font);
    this.lblAction.setForeground(Color.white);
    
    money = NumberFormat.getCurrencyInstance();
    
    this.layoutViewMain();
    
  }

  /*Lays out the GUI for the main menu */
  private void layoutViewMain() {
    
    
    String options[] = {"Exit", "Admin", "Customer"};
    JLabel lblMain = new JLabel("<html>Welcome to Banking On It - the Ultimate Virtual ATM!<br><br>" +
       "Please select whether you are a customer or administrator.<br><br>Click 'Exit' to exit.</html>"
     );
    
    lblMain.setForeground(Color.white);
    lblMain.setFont(this.font);
    ImageIcon iconBank = new ImageIcon("./imgs/bank.png");
    
    do {
      
       // prompt user to choose option - store response into var
       int response = JOptionPane.showOptionDialog (
          null, lblMain, "Banking On It - Home Page", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, 
          iconBank, options, options[0]
       );
       
       // call method that processes the user input; break out of loop if user wishes to exit
       if(!this.controller.actionPerformedMain(response)) {
         break;
       }
    
    } while (true);
    
  }
  
  /* Lays out the GUI for the main admin menu */
  public void layoutViewAdminMain() {
    
    JLabel lblAdmin = new JLabel("<html> Enter the case-sensitive admin password: <br> (Pass. is Test123) </html>");
    lblAdmin.setFont(this.font);
    lblAdmin.setForeground(Color.white);
    
    JLabel lblIncorrectPass = new JLabel("The password entered was incorrect. Please try again.");
    lblIncorrectPass.setFont(this.font);
    lblIncorrectPass.setForeground(Color.white);
    
    String password = (String) JOptionPane.showInputDialog (
       null, lblAdmin, "Banking On It: Admin Log-In", JOptionPane.QUESTION_MESSAGE, this.iconKey, null, ""
    );
    
    // method called will return a string indicating the validity of the input
    // method will return the correct password if the password is correct
    String passwordInputStatus = this.controller.actionPerformedAdminMain(password);
    
    // controller will continue to validate input until the correct input is inputted
    while (!passwordInputStatus.equals("Test123")) {
      
      // if the user wishers to return to the home page
      if(passwordInputStatus.equals("return home")) {
        break;
      }
      // else if there's empty input
      else if(passwordInputStatus.equals("")) {
        
        JOptionPane.showMessageDialog (
           null, this.lblEmpty, "Banking On It: Admin Log-In - ERROR!", JOptionPane.ERROR_MESSAGE, this.iconError
        );
        
        passwordInputStatus = (String) JOptionPane.showInputDialog (
           null, lblAdmin, "Banking On It: Admin Log-In", JOptionPane.QUESTION_MESSAGE, this.iconKey, null, ""
        );
      }
      // else if the password is incorrect
      else if(!passwordInputStatus.equals("Test123")) {
        
        JOptionPane.showMessageDialog (
           null, lblIncorrectPass, "Banking On It: Admin Log-In - ERROR!", JOptionPane.ERROR_MESSAGE, this.iconError
        );
        
        passwordInputStatus = (String) JOptionPane.showInputDialog (
           null, lblAdmin, "Banking On It: Admin Log-In", JOptionPane.QUESTION_MESSAGE, this.iconKey, null, ""
        );
      }
      
      passwordInputStatus = this.controller.actionPerformedAdminMain(passwordInputStatus);
      
    }
    
    String adminOptions[] = {"Log-Out", "Modify a Customer's Information", "View All Customers"};
    
    int response = 0;
    
    // controller will continue to validate input until the correct input is inputted
    do {
      
       // prompt user to choose option - store response into var
       response = JOptionPane.showOptionDialog (
          null, this.lblAction, "Banking On It - Admin Portal", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, 
          this.iconUnlock, adminOptions, adminOptions[0]
       );
      
    } while (this.controller.actionPerformedAdminPortal(response));
    
  }
  
  /* Lays out the GUI such that the admin can view all customers */
  public void layoutViewAdminViewCustomers() {
    
   String allCustomers = "FORMAT: Name, Phone Number, Address, Account Key, Balance\n";
   
   // populate String to output with customer details
   for(Customer customer: this.simulator.getCustomers()) {
      allCustomers += customer.getName() + ", " + this.formatPhone(customer.getPhoneNum()) + ", " +
        customer.getAddress() + ", " + customer.getKey() + ", " + money.format(customer.getBalance()) + "\n";
    }
   
   JTextArea txtArea = new JTextArea(allCustomers);
   txtArea.setForeground(Color.black);
   txtArea.setFont(this.font);
   
   JScrollPane scrollPane = new JScrollPane(txtArea);
   scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
   scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
   
   JOptionPane.showMessageDialog(
      null, scrollPane, "Banking On It: View All Customers", JOptionPane.INFORMATION_MESSAGE, this.iconUnlock
   );
   
  }
  
  /* Formats any given phone number
   * @prompt phoneNum The number to format
   * @return The formatted number
  */
  private String formatPhone(String phoneNum) {
    return "(" + phoneNum.substring(0, 3) + ") " + phoneNum.substring(3, 6) + "-" + phoneNum.substring(6);
  }
  
  /* Lays out the GUI such that the a customer's information can be edited */
  public void layoutViewModifyCustomer() {
    
   String allCustomers = "FORMAT: Name, Phone Number, Address, Account Key, Balance\n";
   
   // populate String to output with customer details
   for(Customer customer: this.simulator.getCustomers()) {
      allCustomers += customer.getName() + ", " + this.formatPhone(customer.getPhoneNum()) + ", " +
        customer.getAddress() + ", " + customer.getKey() + ", " + money.format(customer.getBalance()) + "\n";
    }
   
   JTextArea txtArea = new JTextArea(allCustomers);
   txtArea.setForeground(Color.black);
   txtArea.setFont(this.font);
   
   JScrollPane scrollPane = new JScrollPane(txtArea);
   scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
   scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
   
   JOptionPane.showMessageDialog(
      null, scrollPane, "Banking On It: View All Customers", JOptionPane.INFORMATION_MESSAGE, this.iconUnlock
   );
   
  }
    
}
