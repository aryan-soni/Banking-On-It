/** Customer
*  Models a customer
*  @author Aryan Soni
*/

public class Customer {
  
  // Outline attributes
  private String name;
  private String phoneNum;
  private String address;
  private String key;
  private double balance; 
  
  /** Constructs a Customer object 
    * @param name The customer's full name
    * @param phoneNum The customer's phone number
    * @param address The customer's address
    * @param key The customer's account key
    * @param balance The customer's account balance
    */
  public Customer(String name, String phoneNum, String address, String key, double balance) {
    
    this.name = name;
    this.phoneNum = phoneNum;
    this.address = address;
    this.key = key;
    this.balance = balance;
    
  }
  
  /** Enables access to customer's name 
    * @return The customer's full name
  */
  public String getName() {
    return this.name;
  }
  
  /** Enables access to customer's phone number 
    * @return The customer's phone number
  */
  public String getPhoneNum() {
    return this.phoneNum;
  }
  
  /** Enables access to customer's address
    * @return The customer's address
  */
  public String getAddress() {
    return this.address;
  }
  
  /** Enables access to customer's account key 
    * @return The customer's account key
  */
  public String getKey() {
    return this.key;
  }
  
  /** Enables access to customer's balance 
    * @return The customer's balance
  */
  public double getBalance() {
    return this.balance;
  }
  
  /** Enables the customer's phone number to be changed
    * @param newPhoneNum The customer's new phone number
  */
  public void setPhoneNum(String newPhoneNum) {
    this.phoneNum = newPhoneNum;
  }
  
  /** Enables the customer's account key to be changed
    * @param newKey The customer's new account key
  */
  public void setKey(String newKey) {
    this.key = newKey;
  }
  
  /** Enables an amount to be deposited into the customer's balance
    * @param amoountToDeposit The amount to be deposited into the customer's balance
  */
  public void deposit(double amoountToDeposit) {
    this.balance += amoountToDeposit;
  }
  
  /** Enables an amount to be withdrawn from the customer's balance
    * @param amoountToWithdraw The amount to be withdrawn from the customer's balance
  */
  public void withdraw(double amoountToWithdraw) {
    this.balance -= amoountToWithdraw;
  }

  /** Checks if the amount to withdraw is valid
    * @param amoountToWithdraw The amount to be withdrawn from the customer's balance
    * @return Whether the amount to withdraw is less than or equal to the balance
  */
  public boolean isWithdrawalValid(double amountToWithdraw) {
    return amountToWithdraw <= this.balance;
  }
  
}