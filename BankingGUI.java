/** BankingGUI
*  Models the GUI of a banking system
*  @author Aryan Soni
*/

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;

public class BankingGUI {
  
  // Declare attributes
  private BankingSimulator simulator;
  private UIManager UI = new UIManager();

  /*Constructs a BankingGUI object 
    * @param simulator The banking simulator to link the GUI to
    */
  public BankingGUI (BankingSimulator simulator) {
    
    this.simulator = simulator;
    this.simulator.setGUI(this);
    this.layoutMainView();
    
    // Configurate settings for UIManager
    this.UI.put("OptionPane.background", Color.black);
    this.UI.put("Panel.background", Color.black);
    UIManager.put("OptionPane.minimumSize", new Dimension(600, 175));
    this.UI.put("OptionPane.cancelButtonText", "Return");
    
  }

  /*Lays out the GUI for the main menu */
  private void layoutMainView() {
    
    String options[] = {"Exit", "Help", "Admin", "Customer"};
    JLabel label = new JLabel("<html>Welcome to Banking On It - the Ultimate Virtual ATM!<br><br>" +
       "Please select whether you are a customer or administrator.<br><br>Click 'Help' for help, and 'Exit' to exit.</html>"
     );
    
    label.setForeground(Color.white);
    ImageIcon icon = new ImageIcon("./imgs/bank.png");
    Font defaultFont = new Font("Monospaced", Font.BOLD, 16);
    label.setFont(defaultFont);
    
    do {
      
       // prompt user to choose option - store response into var
       int response = JOptionPane.showOptionDialog (
          null, label, "Banking On It - Home Page", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, 
          icon, options, options[0]
       );
    
    } while (true);
    
  }

  /*Sets up controllers for each time user inputs their choice */
  private void registerControllers () {
    
    ButtonsController controller = new ButtonsController(this.simulator);
    
  }

}