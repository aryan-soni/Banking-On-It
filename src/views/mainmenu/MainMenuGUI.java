/** MainMenuGUI
 *  Models the GUI of the main menu of a banking system
 *  @author Aryan Soni
 */

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainMenuGUI extends BankingGUI {

    // Outline attributes

    private MainMenuController controller;

    /*Constructs a MainMenuGUI object 
     * @param simulator The banking simulator to link the GUI to
     */
    public MainMenuGUI(BankingSimulator simulator) {

        super(simulator);
        this.controller = new MainMenuController(this, simulator);

    }

    /*Lays out the GUI for the main menu */
    public void layoutView() {

        String options[] = {
            "Exit",
            "Admin",
            "Existing Customer",
            "New Customer"
        };
        JLabel lblMain = new JLabel("<html>Welcome to Banking On It — a Comprehensive Banking System!<br><br>" +
            "Please select whether you are a new/existing customer or administrator.<br><br>Click 'Exit' to exit.</html>"
        );

        lblMain.setForeground(Color.white);
        lblMain.setFont(super.font);
        ImageIcon iconBank = new ImageIcon("views/imgs/bank.png");

        do {

            // prompt user to choose option - store response into var
            int response = JOptionPane.showOptionDialog(
                null, lblMain, "Banking On It - Home Page", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                iconBank, options, null
            );

            // call method that processes the user input; break out of loop if user wishes to exit
            if (!this.controller.actionPerformed(response)) {
                break;
            }

        } while (true);

    }

}