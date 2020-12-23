/* ButtonsController
*  Controls user input received through option dialogs
*  @author Aryan Soni
*/

import javax.swing.*;
import java.awt.*;

public class ButtonsController {
  
  // Outline attribute(s)
  private BankingGUI view;
  private BankingSimulator simulator;
  
  /* Constructs a ButtonsController object 
   * @param simulator The banking simulator to update
   */
  public ButtonsController (BankingGUI view, BankingSimulator simulator) {
    this.view = view;
    this.simulator = simulator;
  }
  
  /* Handles user input for the main menu
   * @param response The user's response to the message dialog
   * @return Whether the user wishes to continue
   * */
  public boolean actionPerformedMain (int response) {
    
     // if the user selects "admin"
     if (response == 1) {
       this.view.layoutViewAdmin();
     }
     // else if the user selects admin
     else if (response == 2) {

     }
     // else if user selects help
     else if (response == 3) {

     }
     // else - user selects exit or x button is clicked
     else {

     }
     
     return true;
  
  }
  
  /* Handles user input for the admin main menu
   * @param password The password inputted 
   * @return The status of the input
   * */
  public String actionPerformedAdminMain (String password) {
    
    if (password == null) {
      return "return home";
    }
    else if (password.trim().length() == 0) {
      return "";
    }
    else if (!password.equals("Test123")) {
      return "wrong";
    }
    else {
      return "Test123";
    }
  
  }
  
  
}