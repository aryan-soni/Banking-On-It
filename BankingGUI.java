/** BankingGUI
*  Models the GUI of a banking system
*  @author Aryan Soni
*/

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;

public class BankingGUI {
  
  // Declare attribute(s)
  private BankingSimulator simulator;

 /** Constructs a BankingGUI object 
    * @param simulator The banking simulator to link the GUI to
    */
  public BankingGUI (BankingSimulator simulator) {
    this.simulator = simulator;
    this.simulator.setGUI(this);
  }
  


}