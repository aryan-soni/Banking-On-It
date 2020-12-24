/* AdminController
*  Controls user input when handling the admin's input
*  @author Aryan Soni
*/

public class AdminController {
  
  // Outline attributes
  AdminGUI view;
  BankingSimulator simulator;
  
  /* Constructs a AdminController object 
   * @param simulator The banking simulator to update
   */
  public AdminController (AdminGUI view, BankingSimulator simulator) {
    this.view = view;
    this.simulator = simulator;
  }
  
  /* Handles user input for the admin main menu
   * @param password The password inputted 
   * @return The status of the input
   * */
  public String actionPerformedMain (String password) {
    
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

  /* Handles user input for the admin portal
   * @param response The user's response
   * @return Whether the user wishes to continue
   */
  public boolean actionPerformedPortal (int response) {
    
    if (response == 0) {
      return false;
    }
    else if (response == 1) {
    }
    else {
      this.view.layoutViewCustomers();
    }
    
    return true;
  
  }
  
}