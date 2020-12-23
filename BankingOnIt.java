/* BankingOnIt
*  Simulates a banking system; leveraging Object-Oriented-Programming
*  @author Aryan Soni
*/

import javax.swing.*;
import java.awt.*;

public class BankingOnIt
{
  /** Instantiates the relevant objects
    * @param args
    */
  public static void main (String[] args)
  {
    
    BankingSimulator simulator = new BankingSimulator();
    BankingGUI view = new BankingGUI(simulator);
    
    // set up the JFrame 
    JFrame frame = new JFrame("Banking On It: Banking Simulator");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    
  } // end of main method
  
} // end of class