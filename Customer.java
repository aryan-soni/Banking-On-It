/** Customer
*  Models a customer
*  Last Modified: December 20, 2020
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
  public void Customer(String name, String phoneNum, String address, String key, double balance) {
    
    this.name = name;
    this.phoneNum = phoneNum;
    this.address = address;
    this.key = key;
    this.balance = balance;
    
  }
  
}