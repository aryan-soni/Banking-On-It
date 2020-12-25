/** BankingSimulator
*  Models a banking system; contains a list of customers and their relevant information
*  @author Aryan Soni
*/

import java.util.Collections;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class BankingSimulator {
  
  // Outline Attributes 
  private ArrayList <Customer> customers = new ArrayList <Customer>();
  private BankingGUI view;
  File file;
  Scanner in;
  PrintWriter out;
  
  /* Constructs a BankingSimulator object */
  public BankingSimulator() {
    
    try {
      this.file = new File("CustomerData.txt");
      this.in = new Scanner(this.file); 
    }
    catch (FileNotFoundException ex) {
      System.out.println(ex.getMessage() + "in" + System.getProperty("user.dir"));
      System.exit(1);
    }
    
    // Sync customers from text file to ArrayList
    while(this.in.hasNextLine()) {
      String name = Cryptographer.decode(this.in.nextLine());
      String phoneNum = Cryptographer.decode(this.in.nextLine());
      String address = Cryptographer.decode(this.in.nextLine());
      String key = Cryptographer.decode(this.in.nextLine());
      double balance = Double.parseDouble(Cryptographer.decode(this.in.nextLine()));
      this.customers.add(new Customer(name, phoneNum, address, key, balance));
    }
    
    this.in.close();
    
    // Alphabetically sort customers by first name
    this.quickSort(this.customers, 0, this.customers.size() - 1); 
    
  }
  
  /** Alphabetically sorts names using the quicksort algorithm
    * @param arrList The ArrayList of customers
    * @param start The start index to examine
    * @param end The end index to examine
    */
  private void quickSort(ArrayList<Customer> arrList, int start, int end) {
    
    if(start < end) {
      int partitionIndex = partition(arrList, start, end);
      
      quickSort(arrList, start, partitionIndex - 1);
      quickSort(arrList, partitionIndex + 1, end);
    }
    
  }
  
  /** Partitions the ArrayList of customers
    * @param arrList The ArrayList of customers
    * @param start The start index to examine
    * @param end The end index to examine
    * @return The final sorted position
    */
  private int partition(ArrayList<Customer> arrList, int start, int end) {
    
    String pivot = arrList.get(end).getName();
    int i = start - 1;
    
    for(int j = 0; j < end; j++) {
      // if the name for Customer j is lower in the alphabet versus the name of the pivot
      if(arrList.get(j).getName().compareToIgnoreCase(pivot) <= 0) {
        i++;
        
        Collections.swap(arrList, i, j);
      }
    }
    
    Collections.swap(arrList, i + 1, end);
    
    return i + 1;
    
  }

  /** Leverages binary search to enable access to a customer based on the customer's name and account key
    * @param name The customer's name
    * @param key The customer's key
    * @return The index corresponding to the Customer (-1 if the customer doesn't exist)
    */
  public int getCustomer(String name, String key) {
    
    int low = 0, high = this.customers.size() - 1;
    
    while (low <= high) {
      
      int mid = (low + high) / 2;
      Customer selectedCustomer = this.customers.get(mid);
      
      if (selectedCustomer.getName().equalsIgnoreCase(name)) {
        
        if(selectedCustomer.getKey().equalsIgnoreCase(key)) {
          return mid;
        }
        else {
          return linearSearch(name, key);
        }
        
      }
      
      else if (selectedCustomer.getName().compareToIgnoreCase(name) < 0) {
        high = mid - 1;
      }
      
      else {
        low = mid + 1;
      }
    }
    
    return -1;
    
  }

  /** Deploys linear search to enable access to a customer based on the customer's name and account key
    * @param name The customer's name
    * @param key The customer's key
    * @return The index corresponding to the Customer (-1 if the customer doesn't exist)
    */
  private int linearSearch(String name, String key) {
    
    for(Customer customer: this.customers) {
      if(customer.getName().equals(name) && customer.getKey().equals(key)) {
        return this.customers.indexOf(customer);
      }
    }
    
    return -1; 
    
  }
  
  /** Adds a customer to the database
    * @param name The customer's full name
    * @param phoneNum The customer's phone number
    * @param address The customer's address
    * @param key The customer's account key
    * @param balance The customer's account balance
    */
  private void addCustomer(String name, String phoneNum, String address, String key, double balance) {
    
    // Add customer to ArrayList
    Customer newCustomer = new Customer(name, phoneNum, address, key, balance);
    this.customers.add(newCustomer);
    this.quickSort(this.customers, 0, this.customers.size() - 1);
    
    // Add customer's encrypted details to text file
    try {
      this.out = new PrintWriter(this.file); 
    }
    catch (FileNotFoundException ex) {
      System.out.println(ex.getMessage() + "in" + System.getProperty("user.dir"));
      System.exit(1);
    }
    
    out.println("\n" + Cryptographer.encode(name));
    out.println(Cryptographer.encode(phoneNum));
    out.println(Cryptographer.encode(address));
    out.println(Cryptographer.encode(key));
    out.print(Cryptographer.encode(Double.toString(balance)));
    
  }
  
  /** Updates the database */
  public void updateDB() {
    
    this.quickSort(this.customers, 0, this.customers.size() - 1);
    
    try {
      this.out = new PrintWriter(this.file); 
    }
    catch (FileNotFoundException ex) {
      System.out.println(ex.getMessage() + "in" + System.getProperty("user.dir"));
      System.exit(1);
    }
    
    for(Customer customer: this.customers) {
          out.println("\n" + Cryptographer.encode(customer.getName()));
          out.println(Cryptographer.encode(customer.getPhoneNum()));
          out.println(Cryptographer.encode(customer.getAddress()));
          out.println(Cryptographer.encode(customer.getKey()));
          out.print(Cryptographer.encode(Double.toString(customer.getBalance())));
    }
    
  }
  
  /** Return's the simulator's ArrayList of customers
    * @return The simulator's ArrayList of customers
    */
  public ArrayList <Customer> getCustomers() {
    return this.customers;
  }
  
  /** Sets the simulator's GUI
    * @param currentGUI GUI to set as the view
    */
  public void setGUI(BankingGUI currentGUI) {
    this.view = currentGUI;
  }

}
