/* ExistCustomerController
*  Controls user input when handling the input for customer information
*  @author Aryan Soni
*/

public class CustomerController {
  
  // Outline attributes
  protected CustomerGUI view;
  protected BankingSimulator simulator;
  
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
    else if (!name.replaceAll("\\s+", "").matches("^[a-zA-Z]*$")) {
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
    else if (!phone.replaceAll("\\s+", "").matches("[+-]?[0-9]+") || phone.length() != 10) {
      return "wrong";
    }
    else {
      return "valid";
    }
  
  }
  

  /* Handles user input when an address must be entered
   * @param address The address inputted 
   * @return The status of the input
   * */
  public String actionPerformedAddress (String value) {
    
    if (value == null) {
      return "return home";
    }
    else if (value.trim().length() == 0) {
      return "";
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
    else if (!value.replaceAll("\\s+", "").matches("^(?=.*[a-zA-Z])(?=.*[0-9])[A-Za-z0-9]+")) {
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
      return "valid";
    }
    
    return "wrong";
  
  }
 
  /* Handles user input for the customer portal
   * @param response The user's response
   * @param customer The customer whose information to modify
   * @return Whether the user wishes to continue 
  */
  public boolean actionPerformedPortal (int response, Customer customer) {
    
    String userInput = " "; // default user input to string
    
    // Change key
    if (response == 1) {
      userInput = this.view.layoutViewCollectKey();
      if (userInput != null) customer.setKey(userInput);
    }
    // Change address
    else if (response == 2) {
      userInput = this.view.layoutViewCollectAddress();
      if (userInput != null) customer.setAddress(userInput);
    }
    // Change phone number
    else if (response == 3) {
      userInput = this.view.layoutViewCollectPhone();
      if (userInput != null) customer.setPhoneNum(userInput);
    }
    // View account details
    else if (response == 4) {
      this.view.layoutViewInfo(customer);
      return true;
    }
    // Withdrawal
    else if (response == 5) {
      userInput = this.view.layoutViewWithdrawal(customer);
      if (userInput != null) customer.withdraw(Double.parseDouble(userInput));
    }
    // Deposit
    else if (response == 6) {
      userInput = this.view.layoutViewDeposit(customer);
      if (userInput != null) customer.deposit(Double.parseDouble(userInput));
    }
    // Exit
    else {
      return false;
    }
    
    if(userInput == null) {
      return false;
    }
    else {
      this.simulator.updateDB();
      this.view.layoutViewSuccess(customer.getName()); // inform user that action was successful
      return true;
    }
  
  }
  
}