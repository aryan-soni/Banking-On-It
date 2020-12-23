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
  
  private JLabel lblEmpty;
  private JLabel lblIncorrectKey;
  
  private Font font;

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
    
    this.lblEmpty = new JLabel("We received an empty input. Please try again.");
    this.lblEmpty.setFont(this.font);
    this.lblEmpty.setForeground(Color.white);
    this.lblIncorrectKey = new JLabel("The key entered was incorrect. Please try again.");
    this.lblIncorrectKey.setFont(this.font);
    this.lblIncorrectKey.setForeground(Color.white);
    
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
  
  /* Lays out the GUI for the admin menu */
  public void layoutViewAdmin() {
    
    JLabel lblAdmin = new JLabel("<html> Enter the case-sensitive admin key: <br> (Key is Test123) </html>");
    lblAdmin.setFont(this.font);
    lblAdmin.setForeground(Color.white);
    
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
           null, this.lblIncorrectKey, "Banking On It: Admin Log-In - ERROR!", JOptionPane.ERROR_MESSAGE, this.iconError
        );
        
        passwordInputStatus = (String) JOptionPane.showInputDialog (
           null, lblAdmin, "Banking On It: Admin Log-In", JOptionPane.QUESTION_MESSAGE, this.iconKey, null, ""
        );
      }
      
      passwordInputStatus = this.controller.actionPerformedAdminMain(passwordInputStatus);
      
    }
    
  }
    
}
