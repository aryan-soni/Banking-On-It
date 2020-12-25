/* ExisstCustomerController
*  Controls user input when handling the input for customer information
*  @author Aryan Soni
*/

public class CustomerController {
  
  // Outline attributes
  CustomerGUI view;
  BankingSimulator simulator;
  
  /* Constructs a CustomerController object 
   * @param simulator The banking simulator to update
   */
  public CustomerController (CustomerGUI view, BankingSimulator simulator) {
    this.view = view;
    this.simulator = simulator;
  }
  
  /* Handles user input when a customer enters their name
   * @param name The name inputted 
   * @return The status of the input
   * */
  public String actionPerformedName (String name) {
    
    if (name == null) {
      return "return home";
    }
    else if (name.trim().length() == 0) {
      return "";
    }
    else if (!name.matches("^[a-zA-Z]*$")) {
      return "wrong";
    }
    else {
      return "valid";
    }
  
  }

 /* Handles user input when a customer's phone number is entered
   * @param phone The phone number inputted 
   * @return The status of the input
   * */
  public String actionPerformedPhone (String phone) {
    
    if (phone == null) {
      return "return home";
    }
    else if (phone.trim().length() == 0) {
      return "";
    }
    else if (!phone.matches("-?\\d+(\\.\\d+)?") || phone.length() != 10) {
      return "wrong";
    }
    else {
      return "valid";
    }
  
  }

  
  /* Handles user input when an alpha-numeric value must be entered
   * @param value The String inputted 
   * @return The status of the input
   * */
  public String actionPerformedAlphaNum (String value) {
    
    if (value == null) {
      return "return home";
    }
    else if (value.trim().length() == 0) {
      return "";
    }
    else if (!value.matches("^[a-zA-Z0-9]*$")) {
      return "wrong";
    }
    else {
      return "valid";
    }
  
  }
  
  /* Handles user input when a customer's balance is entered
   * @param balance The balance inputted 
   * @return The status of the input
   * */
  public String actionPerformedBalance (String balance) {
    
    if (balance == null) {
      return "return home";
    }
    else if (balance.trim().length() == 0) {
      return "";
    }
    else {
      
      try
      {
        double parsedBalance = Double.parseDouble(balance.trim());
        if(parsedBalance >= 0) return "valid";
      }
      catch (NumberFormatException e)
      {
        return "wrong";
      }
    }
    
    return "wrong";
  
  }

  /* Handles user input when a customer's deposit is entered
   * @param deposit The deposit inputted 
   * @customer The customer who will be depositing money
   * @return The status of the input
   * */
  public String actionPerformedDeposit (String deposit, Customer customer) {
    
    if (deposit == null) {
      return "return home";
    }
    else if (deposit.trim().length() == 0) {
      return "";
    }
    else {
      
      try
      {
        double parsedDeposit = Double.parseDouble(deposit.trim());
        if(parsedDeposit >= 0) {
          customer.deposit(parsedDeposit);
          return "valid";      
        }
      }
      catch (NumberFormatException e)
      {
        return "wrong";
      }
    }
    
    return "wrong";
  
  }
  
  /* Handles user input when a customer's withdrawal is entered
   * @param balance The balance inputted 
   * @customer The customer who will be withdrawing money
   * @return The status of the input
   * */
  public String actionPerformedWithdrawal (String withdrawal, Customer customer) {
    
    double parsedWithdrawal = -1;
    
    if (withdrawal == null) {
      return "return home";
    }
    else if (withdrawal.trim().length() == 0) {
      return "";
    }
    else {
      
      try
      {
        parsedWithdrawal = Double.parseDouble(withdrawal.trim());
      }
      catch (NumberFormatException e)
      {
        return "wrong";
      }
    }
    
    if(customer.isWithdrawalValid(parsedWithdrawal)) {
      customer.withdraw(parsedWithdrawal);
      return "valid";
    }
    
    return "wrong";
  
  }
 
  /* Handles user input for the admin portal
   * @param response The user's response
   * @return Whether the user wishes to continue

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
   */
  
}