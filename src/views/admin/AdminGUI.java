/** AdminGUI
 *  Models the GUI of the admin section of a banking system
 *  @author Aryan Soni
 */

import javax.swing.*;
import java.awt.*;

public class AdminGUI extends BankingGUI {

    // Outline attributes

    private AdminController controller;

    /* Constructs an AdminGUI object 
     * @param simulator The banking simulator to link the GUI to
     */
    public AdminGUI(BankingSimulator simulator) {

        super(simulator);
        this.controller = new AdminController(this, simulator);

        // if the user is authenticated
        if (this.layoutViewAuthentication()) {
            this.layoutViewPortal();
        };

    }

    /* Lays out the GUI to authenticate the admin 
     * @return Whether the user should be directed back to the home page
     */
    public boolean layoutViewAuthentication() {

        JLabel lblAdmin = new JLabel("<html> Enter the case-sensitive admin password: <br> (Pass. is Test123) </html>");
        lblAdmin.setFont(super.font);
        lblAdmin.setForeground(Color.white);

        JLabel lblIncorrectPass = new JLabel("The password entered was incorrect. Please try again.");
        lblIncorrectPass.setFont(super.font);
        lblIncorrectPass.setForeground(Color.white);

        String password = (String) JOptionPane.showInputDialog(
            null, lblAdmin, "Banking On It: Admin Log-In", JOptionPane.QUESTION_MESSAGE, super.iconKey, null, ""
        );

        // method called will return a string indicating the validity of the input
        // method will return the correct password if the password is correct
        String passwordInputStatus = this.controller.actionPerformedMain(password);

        // controller will continue to validate input until the correct input is inputted
        while (!passwordInputStatus.equals("Test123")) {

            // if the user wishers to return to the home page
            if (passwordInputStatus.equals("return home")) {
                return false;
            }
            // else if there's empty input
            else if (passwordInputStatus.equals("")) {

                JOptionPane.showMessageDialog(
                    null, super.lblEmpty, "Banking On It: Admin Log-In - ERROR!", JOptionPane.ERROR_MESSAGE, super.iconError
                );

                password = (String) JOptionPane.showInputDialog(
                    null, lblAdmin, "Banking On It: Admin Log-In", JOptionPane.QUESTION_MESSAGE, super.iconKey, null, ""
                );
            }
            // else if the password is incorrect
            else if (!passwordInputStatus.equals("Test123")) {

                JOptionPane.showMessageDialog(
                    null, lblIncorrectPass, "Banking On It: Admin Log-In - ERROR!", JOptionPane.ERROR_MESSAGE, super.iconError
                );

                password = (String) JOptionPane.showInputDialog(
                    null, lblAdmin, "Banking On It: Admin Log-In", JOptionPane.QUESTION_MESSAGE, super.iconKey, null, ""
                );
            }

            passwordInputStatus = this.controller.actionPerformedMain(password);

        }

        return true;

    }

    /* Lays out the GUI for the admin portal */
    public void layoutViewPortal() {

        String adminOptions[] = {
            "Log-Out",
            "Modify a Customer's Information",
            "View All Customers"
        };

        int response = 0;

        // controller will ensure that the portal continues to be displayed until the user wishes to exit
        // controller will also handle the user input
        do {

            // prompt user to choose option - store response into var
            response = JOptionPane.showOptionDialog(
                null, super.lblAction, "Banking On It - Admin Portal", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                super.iconUnlock, adminOptions, adminOptions[0]
            );

        } while (this.controller.actionPerformedPortal(response));

    }

    /* Lays out the GUI such that the admin can view all customers */
    public void layoutViewCustomers() {

        String allCustomers = "FORMAT: Name, Phone Number, Address, Account Key, Balance\n";

        // populate String to output with customer details
        for (Customer customer: this.simulator.getCustomers()) {
            allCustomers += customer.getName() + ", " + super.formatPhone(customer.getPhoneNum()) + ", " +
                customer.getAddress() + ", " + customer.getKey() + ", " + money.format(customer.getBalance()) + "\n";
        }

        JTextArea txtArea = new JTextArea(allCustomers);
        txtArea.setForeground(Color.black);
        txtArea.setFont(super.font);

        JScrollPane scrollPane = new JScrollPane(txtArea);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JOptionPane.showMessageDialog(
            null, scrollPane, "Banking On It: View All Customers", JOptionPane.INFORMATION_MESSAGE, super.iconUnlock
        );

    }

}