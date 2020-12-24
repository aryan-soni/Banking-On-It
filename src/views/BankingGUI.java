/** BankingGUI
*  Models the basic attributes tied to the GUI of a banking system
*  @author Aryan Soni
*/

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;

public class BankingGUI {
  
  // Declare attributes
  
  protected BankingSimulator simulator;
  
  protected UIManager UI = new UIManager();
  
  protected MainController controller;
  
  protected ImageIcon iconKey;
  protected ImageIcon iconError;
  protected ImageIcon iconUnlock;
  
  protected JLabel lblEmpty;
  protected JLabel lblIncorrectKey;
  protected JLabel lblAction;
  
  protected Font font;
  
  protected NumberFormat money;

  /*Constructs a BankingGUI object 
    * @param simulator The banking simulator to link the GUI to
    */
  public BankingGUI (BankingSimulator simulator) {
    
    this.simulator = simulator;
    this.simulator.setGUI(this);
    this.controller = new MainController(this, this.simulator);
    
    // Configurate settings for UIManager
    this.UI.put("OptionPane.background", Color.black);
    this.UI.put("Panel.background", Color.black);
    UIManager.put("OptionPane.minimumSize", new Dimension(600, 175));
    this.UI.put("OptionPane.cancelButtonText", "Return");
    
    this.font = new Font("Monospaced", Font.BOLD, 16);
    
    this.iconKey = new ImageIcon("views/imgs/key.png");
    this.iconError = new ImageIcon("views/imgs/error.png");
    this.iconUnlock = new ImageIcon("views/imgs/unlock.png");
    
    this.lblEmpty = new JLabel("We received an empty input. Please try again.");
    this.lblEmpty.setFont(this.font);
    this.lblEmpty.setForeground(Color.white);
    this.lblAction = new JLabel("Welcome! Please select an action.");
    this.lblAction.setFont(this.font);
    this.lblAction.setForeground(Color.white);
    
    money = NumberFormat.getCurrencyInstance();
    
  }
    
}
