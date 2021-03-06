/* MainController
 *  Controls user input received through option dialogs for the main menu
 *  @author Aryan Soni
 */

import javax.swing.*;
import java.awt.*;

public class MainMenuController {

    // Outline attribute(s)
    protected MainMenuGUI view;
    protected BankingSimulator simulator;

    /* Constructs a MainMenuController object 
     * @param simulator The banking simulator to update
     */
    public MainMenuController(MainMenuGUI view, BankingSimulator simulator) {
        this.view = view;
        this.simulator = simulator;
    }

    /* Handles user input for the main menu
     * @param response The user's response to the message dialog
     * @return Whether the user wishes to continue
     * */
    public boolean actionPerformed(int response) {

        // if the user selects "admin"
        if (response == 1) {
            AdminGUI admin = new AdminGUI(this.simulator);
        }
        // else if the user selects existing customer
        else if (response == 2) {
            CustomerGUI customerView = new CustomerGUI(this.simulator);
            CustomerController customerController = new CustomerController(customerView, this.simulator);
            customerView.layoutViewExistingCustomerPortal();
        }
        // else if user selects new customer
        else if (response == 3) {
            CustomerGUI customerView = new CustomerGUI(this.simulator);
            CustomerController customerController = new CustomerController(customerView, this.simulator);
            customerView.layoutViewNewCustomerPortal();
        }
        // else - user selects exit or x button is clicked
        else {
            return false;
        }

        return true;

    }

}