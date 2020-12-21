/*

    NOTE: The sole purpose of this class is to supply methods.
    This program SHOULD NOT be run on its own.

*/

import java.text.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

// The "Admin" class.
public class Admin {
    public static void main(String[] args) {
        // Leave empty - main method will not explicitly be called
    } // main method


    // method will use input dialog to collect admin password
    public static String collectPass(Font defaultFont) {
        // label for input dialog - use html to format
        JLabel labelPass = new JLabel("<html> Enter the case-sensitive admin password: <br> (Password is Test123) </html>");

        // label if there is an empty password
        JLabel labelError = new JLabel("We received an empty input. Please try again.");

        // label if there is an incorrect password
        JLabel labelIncorrect = new JLabel("The password entered was incorrect. Please try again.");

        ImageIcon iconKey = new ImageIcon("./imgs/key.png"); // image of key
        ImageIcon iconError = new ImageIcon("./imgs/error.png"); // image of error clipart

        labelPass.setForeground(Color.white); // make text white
        labelPass.setFont(defaultFont); // set font to default font
        labelError.setForeground(Color.white); // make text white
        labelError.setFont(defaultFont); // set font to default font
        labelIncorrect.setForeground(Color.white); // make text white
        labelIncorrect.setFont(defaultFont); // set font to default font

        // Note: Due to multiple parameters, InputDialog will return answer as object - need to convert to string to store it in variable
        // ask for password
        String password = (String) JOptionPane.showInputDialog(
            null, labelPass, "Banking On It: Admin Log-In", JOptionPane.QUESTION_MESSAGE, iconKey, null, ""
        );

        boolean validInput = false; // input is invalid by default

        do {
            // if user wishes to return to Home Page
            if (password == null) {
                // return string that indicates that user wishes to go back to Home Page
                return "Back123321";
            }
            // else if there's empty input
            else if (password.trim().length() == 0) {
                // message dialog informs user that input is invalid
                JOptionPane.showMessageDialog(
                    null, labelError, "Banking On It: Admin Log-In - ERROR!", JOptionPane.ERROR_MESSAGE, iconError
                );

                // ask for input again
                password = (String) JOptionPane.showInputDialog(
                    null, labelPass, "Banking On It: Admin Log-In", JOptionPane.QUESTION_MESSAGE, iconKey, null, ""
                );
            }
            // else if the password is incorrect
            else if (!password.equals("Test123")) {
                // message dialog informs user that password is incorrect
                JOptionPane.showMessageDialog(
                    null, labelIncorrect, "Banking On It: Admin Log-In - INCORRECT PASSWORD!", JOptionPane.ERROR_MESSAGE, iconError
                );

                // ask for input again
                password = (String) JOptionPane.showInputDialog(
                    null, labelPass, "Banking On It: Admin Log-In", JOptionPane.QUESTION_MESSAGE, iconKey, null, ""
                );

            }
            // else
            else {
                validInput = true; // input is valid
            }

        }
        while (!validInput); // while input isn't valid

        return password; // return the admin password

    }


    // method will display first set of admin controls
    public static boolean adminControls(String names[], String phoneNums[], String addresses[], String accountKeys[], double balances[], Font defaultFont) throws IOException {
        // store options in array for user to choose
        String options[] = {
            "View All Customers",
            "Modify a Customer's Information",
            "Log-Out"
        };

        // label to add to input dialog
        JLabel label = new JLabel("Welcome! Please select an action.");

        // icon of a lock unlocked
        ImageIcon icon = new ImageIcon("./imgs/unlock.png");

        label.setForeground(Color.white); // make text white
        label.setFont(defaultFont); // set font to default font

        boolean toContinue = true; // assume user wishes to continue

        // while user wishes to continue
        while (toContinue) {
            // prompt user to choose option - store response into var
            int response = JOptionPane.showOptionDialog(
                null, label,
                "Banking On It - Admin Portal", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, icon, options, options[0]
            );

            // if user selects view all customers
            if (response == 0) {
                // populate arrays in case they aren't populated by calling method from the File Handler class
                boolean isPopulated = FileHandler.populate(FileHandler.countCustomers(), names, phoneNums, addresses, accountKeys, balances);

                viewAllCustomers(names, phoneNums, addresses, accountKeys, balances); // method will enable user to view all customers
            }
            // else if user wishes to modify a user's information
            else if (response == 1) {
                modifyCustomerInfo(names, phoneNums, addresses, accountKeys, balances, defaultFont); //  method will help admin modify user's information
            }
            // else if user wishes to log-out
            else if (response == 2) {
                toContinue = false; // user no longer wishes to continue
                return false;
            }
        }

        return true; // will return true is this statement is reached

    }


    // method to view all customers
    public static void viewAllCustomers(String names[], String phoneNums[], String addresses[], String accountKeys[], double balances[]) throws IOException {
        ImageIcon icon = new ImageIcon("./imgs/unlock.png"); // set up icon

        // change font and text size for JTextArea
        Font font = new Font("Monospaced", Font.BOLD, 13);

        NumberFormat money = NumberFormat.getCurrencyInstance(); // set up Number Format for formatting the balance

        int count = FileHandler.countCustomers(); // count # of customers by calling method from File Handler class

        String allCustomers = "FORMAT: Name, Phone Number, Address, Account Key, Balance\n"; // initialize string that will hold all the customers

        // call method from Sort And Search class to alphabetically sort the customers
        SortAndSearch.alphaSort(names, phoneNums, addresses, accountKeys, balances);

        // loop through each customer and add their data to the string
        for (int i = 0; i < count; i++) {
            allCustomers += names[i] + ", " + PermissionsGranted.formatPhone(phoneNums[i]) +
                ", " + addresses[i] + ", " + accountKeys[i] + ", " + money.format(balances[i]) +
                "\n";
        }

        // add string to JTextArea
        JTextArea textArea = new JTextArea(allCustomers);

        textArea.setForeground(Color.black); // make text black
        textArea.setFont(font); // set font to default font

        JScrollPane scrollPane = new JScrollPane(textArea); // set up scrollPane

        // set up formatting
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // message dialog that will display all the customers
        JOptionPane.showMessageDialog(
            null, scrollPane, "Banking On It: View All Customers", JOptionPane.INFORMATION_MESSAGE, icon
        );

    }


    // method that will orchestrate the process of modifying customer information
    public static void modifyCustomerInfo(String names[], String phoneNums[], String addresses[], String accountKeys[], double balances[], Font defaultFont) throws IOException {
        // populate arrays in case they aren't populated by calling the populate method from the File Handler class
        boolean isPopulated = FileHandler.populate(FileHandler.countCustomers(), names, phoneNums, addresses, accountKeys, balances);

        // initialize variables for inputted data
        String enteredName, enteredKey;

        // render images
        ImageIcon iconName = new ImageIcon("./imgs/name.png"); // image for name dialog
        ImageIcon iconKey = new ImageIcon("./imgs/key.png"); // image for key dialog
        ImageIcon iconError = new ImageIcon("./imgs/error.png"); // image for error dialog

        int index; // will store index of user

        // use jLabels to output text on dialog boxes:
        // label for name dialog
        JLabel labelName = new JLabel(
            "Please enter the customer's full name."
        );
        // label for key dialog
        JLabel labelKey = new JLabel(
            "Please enter the customer's alphanumeric account key."
        );
        // label for error dialog
        JLabel labelError = new JLabel(
            "Error! We received an empty input. Please try again."
        );

        // set color and font
        labelError.setForeground(Color.white); // make text white
        labelError.setFont(defaultFont); // set font to default font

        // call method from the New Customer class that will return inputted name by sending input dialog to user
        enteredName = NewCustomer.collectName(iconName, iconError, labelName, labelError, defaultFont);

        // if the name entered indicates that the user would like to go back
        if (enteredName.equals("Back123321")) {
            // since newCustomer is a procedure-type method, a standalone return statement will break out of method
            return;
        }

        // call method from the New Customer class that will return newKey by sending input dialog to user
        enteredKey = NewCustomer.collectKey(iconKey, iconError, labelKey, labelError, defaultFont);

        // if the key entered indicates that the user would like to go back
        if (enteredKey.equals("Back123321")) {
            // since newCustomer is a procedure-type method, a standalone return statement will break out of method
            return;
        }

        // call method from the Sort and Search class that will alphabetically sort all the customers
        SortAndSearch.alphaSort(names, phoneNums, addresses, accountKeys, balances);

        // call method from the Sort and Search class that will search for a specific customer using their name and key
        // method called returns -1 if the customer does not exist, else it returns index of the user
        index = SortAndSearch.searchCustomer(enteredName, names, enteredKey, accountKeys);

        // if the name and key entered aligns with a customer that exists
        if (index >= 0) {
            boolean toContinue = true; // assume user wishes to continue

            do {
                // method from Permissions Granted class will supply interface for admin controls
                // the method will return a boolean indicating whether the user wishes to re-access the controls
                toContinue = PermissionsGranted.accessGranted(names, phoneNums, addresses, accountKeys, balances, defaultFont, index);

            }
            while (toContinue); // orchestrate loop while the user wishes to continue
        }
        // else - user does not exist
        else {
            // label for dialog - use html to format
            JLabel labelUserInvalid = new JLabel(
                "<html> Sorry, the information entered does not match with our records. <br> You will now be redirected to the Admin Portal. </html>"
            );

            labelUserInvalid.setForeground(Color.white); // make text white
            labelUserInvalid.setFont(defaultFont); // set font to default font

            // dialog to inform the user that the customer searched does not exist
            JOptionPane.showMessageDialog(
                null, labelUserInvalid, "Banking On It: Customer (Invalid Log-In)", JOptionPane.ERROR_MESSAGE, iconName
            );
        }

    }
    
}