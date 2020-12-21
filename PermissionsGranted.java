/*

    NOTE: The sole purpose of this class is to supply methods.
    This program SHOULD NOT be run on its own.

*/

import java.text.*;
import javax.swing.*;
import java.io.*;
import java.awt.*;

// The "PermissionsGranted" class.
public class PermissionsGranted {
    public static void main(String[] args) {
        // Leave empty - main method will not explicitly be called
    } // main method


    // this method will allow user to modify a customer's information once access to a customer profile has been granted
    // method will return whether user wishes to continue
    public static boolean accessGranted(String names[], String phoneNums[], String addresses[], String accountKeys[], double balances[], Font defaultFont, int index) throws IOException {
        // label for dialog - use html to format
        JLabel label = new JLabel(
            "<html> Welcome to the Action Portal! <br> Please select an action.</html>"
        );

        label.setForeground(Color.white); // make text white
        label.setFont(defaultFont); // set font to default font

        String options[] = {
            "Deposit Money",
            "Withdraw Money",
            "View Account Details",
            "Change Phone Number",
            "Change Address",
            "Change Key",
            "Log-Out"
        }; // options
        ImageIcon icon = new ImageIcon("./imgs/unlock.png"); // image for dialog

        // prompt user to choose option - store response into var
        int response = JOptionPane.showOptionDialog(
            null, label,
            "Banking On It - Action Portal", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, icon, options, options[0]
        );

        // if user wishes to deposit money
        if (response == 0) {
            // call method that will edit balance - first parameter indicates that user wishes to deposit instead of withdraw
            editBalance(true, index, names, phoneNums, addresses, accountKeys, balances, defaultFont);
        }
        // else if user wishes to withdraw money
        else if (response == 1) {
            // call method that will edit balance - first parameter indicates that user wishes to withdraw instead of deposit
            editBalance(false, index, names, phoneNums, addresses, accountKeys, balances, defaultFont);
        }
        // else if user wants to view account details
        else if (response == 2) {
            // call method that outputs account details
            outputDetails(index, defaultFont, names, phoneNums, addresses, accountKeys, balances);
        }
        // else if user wants to change their phone number
        else if (response == 3) {
            // call method that will prompt the user to change their information
            // pass "phone" to second-last parameter to indicate that phone number needs to be modified
            changeInfo(index, names, phoneNums, addresses, accountKeys, balances, "phone", defaultFont);
        }
        // else if user wants to change their address
        else if (response == 4) {
            // call method that will prompt the user to change their information
            // pass "address" to second-last parameter to indicate that address needs to be modified
            changeInfo(index, names, phoneNums, addresses, accountKeys, balances, "address", defaultFont);
        }
        // else if user wants to change their key
        else if (response == 5) {
            // call method that will prompt the user to change their information
            // pass "key" to second-last parameter to indicate that account key needs to be modified
            changeInfo(index, names, phoneNums, addresses, accountKeys, balances, "key", defaultFont);
        }
        // else - user wanted to exit or "X" button was clicked
        else {
            return false; // user wishes to return to customer portal
        }

        return true; // if this statement is reached, user wishes to continue

    }


    // method will prompt the user to edit their balance
    public static void editBalance(boolean toDeposit, int index, String names[], String phoneNums[], String addresses[], String accountKeys[], double balances[], Font defaultFont) throws IOException {
        ImageIcon iconBalance = new ImageIcon("./imgs/balance.png"); // image for depositing/withdrawing
        ImageIcon iconError = new ImageIcon("./imgs/error.png"); // image for error dialog
        ImageIcon iconSuccess = new ImageIcon("./imgs/success.png"); // image for success dialog

        // label for deposit/withdrawal dialog - set as placeholder
        JLabel label = new JLabel(
            " "
        );

        // label for error dialog
        JLabel labelError = new JLabel(
            "Error! We received an empty input. Please try again."
        );

        // label for invalid input (when customer deposits amount less than 0)
        JLabel labelErrorEmpty = new JLabel(
            "Error! Please enter an amount greater than or equal to 0."
        );

        // label for success dialog
        JLabel labelSuccess = new JLabel(
            "The account balance was successfully updated!"
        );

        labelError.setForeground(Color.white); // make text white
        labelError.setFont(defaultFont); // set font to default font
        labelErrorEmpty.setForeground(Color.white); // make text white
        labelErrorEmpty.setFont(defaultFont); // set font to default font
        labelSuccess.setForeground(Color.white); // make text white
        labelSuccess.setFont(defaultFont); // set font to default font

        // if user wishes to deposit
        if (toDeposit) {
            // label for deposit dialog
            label.setText(
                "Please enter the amount to deposit. "
            );

        }
        // else - user wishes to withdraw
        else {
            // label for withdrawal dialog
            label.setText(
                "Please enter the amount to withdraw. "
            );
        }

        label.setForeground(Color.white); // make text white
        label.setFont(defaultFont); // set font to default font

        boolean validInput = false; // input is invalid by default

        // Note: Due to multiple parameters, InputDialog will return answer as object - need to convert to string to store it in variable
        // ask for balance
        String tempAmount = (String) JOptionPane.showInputDialog(
            null, label, "Banking On It: Account Balance", JOptionPane.QUESTION_MESSAGE, iconBalance, null, ""
        );

        do {
            // if user wishes to return to portal
            if (tempAmount == null) {
                // break out of method
                return;
            }

            // else if there's empty input
            else if (tempAmount.trim().length() == 0) {
                // message dialog informs user that input is invalid
                JOptionPane.showMessageDialog(
                    null, labelError, "Banking On It: Account Balance - ERROR!", JOptionPane.ERROR_MESSAGE, iconError
                );

                // ask for input again
                tempAmount = (String) JOptionPane.showInputDialog(
                    null, label, "Banking On It: Account Balance", JOptionPane.QUESTION_MESSAGE, iconBalance, null, ""
                );

                continue; // continue (passes this iteration - will test again)
            }

            // try and catch to ensure that a valid number was entered
            try {
                double test = Double.parseDouble(tempAmount); // parsing the amount as a double will test if a number was entered
            } catch (Exception e) {
                // message dialog informs user that input is invalid
                JOptionPane.showMessageDialog(
                    null, labelErrorEmpty, "Banking On It: Account Balance - ERROR!", JOptionPane.ERROR_MESSAGE, iconError
                );

                // ask for input again
                tempAmount = (String) JOptionPane.showInputDialog(
                    null, label, "Banking On It: Account Balance", JOptionPane.QUESTION_MESSAGE, iconBalance, null, ""
                );

                continue; // continue (passes this iteration - will test again)
            }

            // if user deposits a negative amount
            if (Double.parseDouble(tempAmount) < 0) {
                // message dialog informs user that input is invalid
                JOptionPane.showMessageDialog(
                    null, labelErrorEmpty, "Banking On It: Account Balance - ERROR!", JOptionPane.ERROR_MESSAGE, iconError
                );

                // ask for input again
                tempAmount = (String) JOptionPane.showInputDialog(
                    null, label, "Banking On It: Account Balance", JOptionPane.QUESTION_MESSAGE, iconBalance, null, ""
                );
            }
            // else
            else {
                validInput = true; // input is valid
            }

        }
        while (!validInput); // while input isn't valid

        double amount = Double.parseDouble(tempAmount); // convert amount to double


        // if user wished to deposit
        if (toDeposit) {
            balances[index] += amount; // update balance

            // call method from File Handler class to update the database (text file)
            FileHandler.updateFile(names, phoneNums, addresses, accountKeys, balances);

            // message dialog informs user that balance was successfully updated
            JOptionPane.showMessageDialog(
                null, labelSuccess, "Banking On It: Account Balance - SUCCESS!", JOptionPane.INFORMATION_MESSAGE, iconSuccess
            );

        }
        // else - user wishes to withdraw
        else {
            // if the withdrawal is too large
            if ((balances[index] - amount) < 0) {
                // label for balance error dialog - use html to format
                JLabel labelBalanceError = new JLabel(
                    "<html> We could not complete the withdrawal. <br> There are insufficient funds in the account. <br> You will now be redirected back to the Action Portal. </html>"
                );

                labelBalanceError.setForeground(Color.white); // make text white
                labelBalanceError.setFont(defaultFont); // set font to default font

                // message dialog informs user that the balance could not be updated
                JOptionPane.showMessageDialog(
                    null, labelBalanceError, "Banking On It: Account Balance - ERROR!", JOptionPane.ERROR_MESSAGE, iconError
                );
            }
            // else if the withdrawal was valid
            else if ((balances[index] - amount) >= 0) {
                balances[index] -= amount; // update balance

                // call method from File Handler class to update the database (text file)
                FileHandler.updateFile(names, phoneNums, addresses, accountKeys, balances);

                // message dialog informs user that balance was successfully updated
                JOptionPane.showMessageDialog(
                    null, labelSuccess, "Banking On It: Account Balance - SUCCESS!", JOptionPane.INFORMATION_MESSAGE, iconSuccess
                );
            }
        }

    }


    // method will output the customer's account details
    public static void outputDetails(int index, Font defaultFont, String names[], String phoneNums[], String addresses[], String accountKeys[], double balances[]) throws IOException {
        NumberFormat money = NumberFormat.getCurrencyInstance(); // will be used to format balance

        ImageIcon icon = new ImageIcon("./imgs/info.png"); // image for dialog

        // label - use html to format
        JLabel label = new JLabel(
            "<html> Name: " + names[index] + "<br>Phone: " + formatPhone(phoneNums[index]) +
            "<br> Address: " + addresses[index] + "<br> Balance: " + money.format(balances[index])
        );

        label.setForeground(Color.white); // make text white
        label.setFont(defaultFont); // set font to default font

        // message dialog informs user that balance was successfully updated
        JOptionPane.showMessageDialog(
            null, label, "Banking On It: Account Details", JOptionPane.INFORMATION_MESSAGE, icon
        );
    }


    // method will be used to format the phone number
    public static String formatPhone(String phoneNum) {
        String formatted; // var for formatted phone num

        // utilize substring built-in method to format phone num
        formatted = "(" + phoneNum.substring(0, 3) + ") " + phoneNum.substring(3, 6) + "-" + phoneNum.substring(6);

        return formatted; // return formatted string
    }


    // method will be used to modify the account information
    public static void changeInfo(int index, String names[], String phoneNums[], String addresses[], String accountKeys[], double balances[], String info, Font defaultFont) throws IOException {
        ImageIcon iconSuccess = new ImageIcon("./imgs/success.png"); // image for success dialog

        // label for success dialog
        JLabel labelSuccess = new JLabel(
            "The account's information was successfully updated!"
        );

        ImageIcon iconError = new ImageIcon("./imgs/error.png"); // image for error dialog

        // label for error dialog
        JLabel labelError = new JLabel(
            "Error! We received an empty input. Please try again."
        );

        labelSuccess.setForeground(Color.white); // make text white
        labelSuccess.setFont(defaultFont); // set font to default font
        labelError.setForeground(Color.white); // make text white
        labelError.setFont(defaultFont); // set font to default font

        // if the phone number is to be modified
        if (info.equals("phone")) {
            ImageIcon iconPhone = new ImageIcon("./imgs/phone.png"); // image for phone

            // label for phone dialog
            JLabel labelPhone = new JLabel(
                "Please enter the new 10-digit phone number in the format: ##########"
            );

            // label for invalid input (when customer inputs phone number in incorrect format)
            JLabel labelErrorPhone = new JLabel(
                "Error! Your phone number must be in the format: ##########."
            );

            // call method from New Customer class that collects new phone number
            String newPhone = NewCustomer.collectPhone(iconPhone, iconError, labelPhone, labelError, labelErrorPhone, defaultFont);

            // if the user wishes to go back
            if (newPhone.equals("Back123321")) {
                return; // break out of the function
            }
            // else - the input is valid
            else {
                phoneNums[index] = newPhone; // change phone number
                // call method from the File Handler class to update the database (the text file)
                FileHandler.updateFile(names, phoneNums, addresses, accountKeys, balances);

                // message dialog informs user that information was successfully updated
                JOptionPane.showMessageDialog(
                    null, labelSuccess, "Banking On It: Phone Number - SUCCESS!", JOptionPane.INFORMATION_MESSAGE, iconSuccess
                );
            }

        }
        // else if the user wishes to modify the address
        else if (info.equals("address")) {
            ImageIcon iconAddress = new ImageIcon("./imgs/address.png"); // image for address

            // label for address dialog
            JLabel labelAddress = new JLabel(
                "Please enter the new address."
            );

            // call method from New Customer class that collects new address
            String newAddress = NewCustomer.collectAddress(iconAddress, iconError, labelAddress, labelError, defaultFont);

            // if the user wishes to return to home
            if (newAddress.equals("Back123321")) {
                return; // break out of the function
            }
            // else - the input is valid
            else {
                addresses[index] = newAddress; // change address
                // call method from the File Handler class to update the database (the text file)
                FileHandler.updateFile(names, phoneNums, addresses, accountKeys, balances);

                // message dialog informs user that information was successfully updated
                JOptionPane.showMessageDialog(
                    null, labelSuccess, "Banking On It: Address - SUCCESS!", JOptionPane.INFORMATION_MESSAGE, iconSuccess
                );
            }

        }
        // else if the user wishes to modify the account key
        else if (info.equals("key")) {
            ImageIcon iconKey = new ImageIcon("./imgs/key.png"); // image for key

            // label for key dialog
            JLabel labelKey = new JLabel(
                "Please enter the new alphanumeric account key."
            );

            // call method from New Customer class that collects new key
            String newKey = NewCustomer.collectKey(iconKey, iconError, labelKey, labelError, defaultFont);

            // if the user wishes to return to home
            if (newKey.equals("Back123321")) {
                return; // break out of the function
            }
            // else - the input is valid
            else {
                // call method from Sort And Search class to alphabetically sort the customers
                SortAndSearch.alphaSort(names, phoneNums, addresses, accountKeys, balances);

                // call method from Sort And Search class to search for a customer
                // method called returns -1 if the customer does not exist, else it returns the customer's index
                // if the name and key entered aligns with a customer that exists
                if (SortAndSearch.searchCustomer(names[index], names, newKey, accountKeys) >= 0) {
                    // label for key error dialog
                    JLabel labelKeyError = new JLabel(
                        "A user with the same name and key already exists. We could not update your key."
                    );

                    labelKeyError.setForeground(Color.white); // make text white
                    labelKeyError.setFont(defaultFont); // set font to default font

                    // message dialog informs user that information was successfully updated
                    JOptionPane.showMessageDialog(
                        null, labelKeyError, "Banking On It: Account Key - ERROR!", JOptionPane.INFORMATION_MESSAGE, iconError
                    );
                }
                // else - the key is valid
                else {
                    accountKeys[index] = newKey; // change key
                    // call method from the File Handler class to update the database (the text file)
                    FileHandler.updateFile(names, phoneNums, addresses, accountKeys, balances);

                    // message dialog informs user that information was successfully updated
                    JOptionPane.showMessageDialog(
                        null, labelSuccess, "Banking On It: Account Key - SUCCESS!", JOptionPane.INFORMATION_MESSAGE, iconSuccess
                    );
                }

            }

        }

    }
} // PermissionsGranted class