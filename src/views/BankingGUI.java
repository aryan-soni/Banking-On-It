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

    protected ImageIcon iconKey, iconError, iconUnlock;

    protected JLabel lblEmpty, lblAction;

    protected Font font;

    protected NumberFormat money;

    /* Constructs a BankingGUI object 
     * @param simulator The banking simulator to link the GUI to
     */
    public BankingGUI(BankingSimulator simulator) {

        this.simulator = simulator;
        this.simulator.setGUI(this);

        // Configurate settings for UIManager
        this.UI.put("OptionPane.background", new Color(66, 103, 178));
        this.UI.put("Panel.background",new Color(66, 103, 178));
        UIManager.put("OptionPane.minimumSize", new Dimension(600, 175));
        this.UI.put("OptionPane.cancelButtonText", "Return");
        UIManager.put("OptionPane.messageFont", new Font("Futura", Font.PLAIN, 14));
        UIManager.put("OptionPane.buttonFont", new Font("Futura", Font.PLAIN, 14));

        this.font = new Font("Futura", Font.PLAIN, 16);

        this.iconKey = new ImageIcon("views/imgs/key.png");
        this.iconError = new ImageIcon("views/imgs/error.png");
        this.iconUnlock = new ImageIcon("views/imgs/unlock.png");

        this.lblEmpty = new JLabel("We received an empty input. Please try again.");
        this.lblEmpty.setFont(this.font);
        this.lblEmpty.setForeground(Color.white);

        this.lblAction = new JLabel("Welcome! Please select an action.");
        this.lblAction.setFont(this.font);
        this.lblAction.setForeground(Color.white);

        this.money = NumberFormat.getCurrencyInstance();

    }

    /* Formats any given phone number
     * @prompt phoneNum The number to format
     * @return The formatted number
     */
    protected String formatPhone(String phoneNum) {
        return "(" + phoneNum.substring(0, 3) + ") " + phoneNum.substring(3, 6) + "-" + phoneNum.substring(6);
    }

}