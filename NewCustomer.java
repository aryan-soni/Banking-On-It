/*

    NOTE: The sole purpose of this class is to supply methods.
    This program SHOULD NOT be run on its own.

*/

import javax.swing.*;
import java.io.*;
import java.awt.*;

// The "NewCustomer" class.
public class NewCustomer {
    public static void main(String[] args) {
        // Leave empty - main method will not explicitly be called
    } // main method


    // method will start the process of registering a new customer
    public static void start(String names[], String phoneNums[], String addresses[], String accountKeys[], double balances[], Font defaultFont) throws IOException {
        // initialize variables for new user data
        String newName, newPhoneNum, newAddress, newAccountKey;
        double newBalance;

        // render images
        ImageIcon iconName = new ImageIcon("./imgs/name.png"); // image for name dialog
        ImageIcon iconPhone = new ImageIcon("./imgs/phone.png"); // image for phone dialog
        ImageIcon iconAddress = new ImageIcon("./imgs/address.png"); // image for address dialog
        ImageIcon iconKey = new ImageIcon("./imgs/key.png"); // image for key dialog
        ImageIcon iconBalance = new ImageIcon("./imgs/balance.png"); // image for balance dialog
        ImageIcon iconError = new ImageIcon("./imgs/error.png"); // image for error dialog

        // use jLabels to output text on dialog boxes:
        // label for name dialog
        JLabel labelName = new JLabel(
            "Please enter your full name."
        );
        // label for phone dialog
        JLabel labelPhone = new JLabel(
            "Please enter your 10-digit phone number in the format: ##########"
        );
        // label for address dialog
        JLabel labelAddress = new JLabel(
            "Please enter your address."
        );
        // label for key dialog
        JLabel labelKey = new JLabel(
            "Please create an alphanumeric account key that will be used to identify your account."
        );
        // label for balance dialog
        JLabel labelBalance = new JLabel(
            "Please enter the amount you would like to deposit. "
        );
        // label for error dialog
        JLabel labelError = new JLabel(
            "Error! We received an empty input. Please try again."
        );
        // label for invalid input (when customer deposits amount)
        JLabel labelErrorEmpty = new JLabel(
            "Error! Please enter an amount greater than or equal to 0."
        );
        // label for invalid input (when customer inputs phone number in incorrect format)
        JLabel labelErrorPhone = new JLabel(
            "Error! Your 10-digit phone number must be in the format: ##########."
        );

        labelError.setForeground(Color.white); // make text white
        labelError.setFont(defaultFont); // set font to default font

        // call method that will return the new customer's name by sending an input dialog to user
        newName = collectName(iconName, iconError, labelName, labelError, defaultFont);

        // if the user wishes to go back
        if (newName.equals("Back123321")) {
            // since newCustomer is a procedure-type method, a standalone return statement will break out of method
            return;
        }

        // call method that will return the new customer's phone number by sending an input dialog to user
        newPhoneNum = collectPhone(iconPhone, iconError, labelPhone, labelError, labelErrorPhone, defaultFont);

        // if the user wishes to go back
        if (newPhoneNum.equals("Back123321")) {
            // since newCustomer is a procedure-type method, a standalone return statement will break out of method
            return;
        }

        // call method that will return the new customer's address by sending input dialog to user
        newAddress = collectAddress(iconAddress, iconError, labelAddress, labelError, defaultFont);

        // if the user wishes to go back
        if (newAddress.equals("Back123321")) {
            // since newCustomer is a procedure-type method, a standalone return statement will break out of method
            return;
        }

        // call method that will return the new customer's account key by sending input dialog to user
        newAccountKey = collectKey(iconKey, iconError, labelKey, labelError, defaultFont);

        // if the user wishes to go back
        if (newAccountKey.equals("Back123321")) {
            // since newCustomer is a procedure-type method, a standalone return statement will break out of method
            return;
        }

        // call method that will return the new customer's balance by sending input dialog to user
        newBalance = collectBalance(iconBalance, iconError, labelBalance, labelError, labelErrorEmpty, defaultFont);

        // if the user wishes to go back
        if (newBalance == -1) {
            // since newCustomer is a procedure-type method, a standalone return statement will break out of method
            return;
        }

        // call method from Sort And Search class to alphabetically sort the customers
        SortAndSearch.alphaSort(names, phoneNums, addresses, accountKeys, balances);

        // call method from Sort And Search class to search for a customer
        // method called returns -1 if the customer does not exist, else it will return the index of the customer
        // if the name and key entered aligns with a customer that exists
        if (SortAndSearch.searchCustomer(newName, names, newAccountKey, accountKeys) >= 0) {
            // label for dialog - use html to format
            JLabel labelUserExists = new JLabel(
                "<html> Error! A user with this key already exists.<br>Please choose a different key or log-in. <br> You will now be redirected to the Customer Hub. </html>"
            );

            labelUserExists.setForeground(Color.white); // make text white
            labelUserExists.setFont(defaultFont); // set font to default font

            // dialog to inform user that a customer with this information already exists
            JOptionPane.showMessageDialog(
                null, labelUserExists, "Banking On It: Customer - ERROR!", JOptionPane.ERROR_MESSAGE, iconError
            );
        }
        // else if the new customer limit has been reached
        else if (!names[names.length - 1].equals(" ")) {
            // label for max session reached - use html to embed the text
            JLabel labelErrorSession = new JLabel(
                "<html> Error! You cannot add more than 20 customers in one use. <br> Please restart the program if you would like to add more customers. <br> You will now be redirected back to the Customer Hub. </html>"
            );

            labelErrorSession.setForeground(Color.white); // make text white
            labelErrorSession.setFont(defaultFont); // set font to default font

            // dialog
            JOptionPane.showMessageDialog(
                null, labelErrorSession, "Banking On It: Customer - ERROR!", JOptionPane.ERROR_MESSAGE, iconError
            );
        }
        // else - user is valid
        else {
            // label for dialog - use html to format
            JLabel labelValidUser = new JLabel(
                "<html> Thank you for signing up! You will now be redirected to the Customer Hub. <br> Once you are redirected, select 'Existing Customer' and log-in if you wish to make changes. </html>"
            );

            labelValidUser.setForeground(Color.white); // make text white
            labelValidUser.setFont(defaultFont); // set font to default font

            // call method from File Handler class that appends the customer data to the database (the text file)
            FileHandler.appendToFile(newName, newPhoneNum, newAddress, newAccountKey, newBalance);

            // call method from File Handler class that updates the arrays using the updated text file
            boolean isPopulated = FileHandler.populate(FileHandler.countCustomers(), names, phoneNums, addresses, accountKeys, balances);

            // dialog
            JOptionPane.showMessageDialog(
                null, labelValidUser, "Banking On It: Customer", JOptionPane.INFORMATION_MESSAGE, iconName
            );
        }
    }


    // method will use input dialogs to collect user name
    public static String collectName(ImageIcon iconName, ImageIcon iconError, JLabel labelName, JLabel labelError, Font defaultFont) {
        JLabel labelNonAlpha = new JLabel("ERROR! Please enter a name with ONLY letters."); // label

        labelNonAlpha.setForeground(Color.white); // make text white
        labelNonAlpha.setFont(defaultFont); // set font to default font
        labelName.setForeground(Color.white); // make text white
        labelName.setFont(defaultFont); // set font to default font

        // Note: Due to multiple parameters, InputDialog will return answer as object - need to convert to string to store it in variable
        // ask for name
        String newName = (String) JOptionPane.showInputDialog(
            null, labelName, "Banking On It: Name", JOptionPane.QUESTION_MESSAGE, iconName, null, ""
        );

        boolean validInput = false; // input is invalid by default

        do {
            // if user wishes to return to Home Page
            if (newName == null) {
                // return string that indicates that user wishes to go back to Home Page
                return "Back123321";
            }
            // else if there's empty input
            else if (newName.trim().length() == 0) {
                // message dialog informs user that input is invalid
                JOptionPane.showMessageDialog(
                    null, labelError, "Banking On It: Name - ERROR!", JOptionPane.ERROR_MESSAGE, iconError
                );

                // ask for input again
                newName = (String) JOptionPane.showInputDialog(
                    null, labelName, "Banking On It: Name", JOptionPane.QUESTION_MESSAGE, iconName, null, ""
                );
            }
            // else if the input does not have only letters - call isAlphaNum method
            // first parameter for isAlphaNum denotes that it only needs to check for non-letter characters
            else if (!isAlphaNum(true, newName)) {
                // message dialog informs user that input is invalid
                JOptionPane.showMessageDialog(
                    null, labelNonAlpha, "Banking On It: Name - ERROR!", JOptionPane.ERROR_MESSAGE, iconError
                );

                // ask for input again
                newName = (String) JOptionPane.showInputDialog(
                    null, labelName, "Banking On It: Name", JOptionPane.QUESTION_MESSAGE, iconName, null, ""
                );
            }
            // else
            else {
                validInput = true; // input is valid
            }

        }
        while (!validInput); // while input isn't valid

        return newName; // return name

    }


    // method will use input dialogs to collect user phone num
    public static String collectPhone(ImageIcon iconPhone, ImageIcon iconError, JLabel labelPhone, JLabel labelError, JLabel labelErrorPhone, Font defaultFont) {
        labelPhone.setForeground(Color.white); // make text white
        labelPhone.setFont(defaultFont); // set font to default font
        labelErrorPhone.setForeground(Color.white); // make text white
        labelErrorPhone.setFont(defaultFont); // set font to default font

        // Note: Due to multiple parameters, InputDialog will return answer as object - need to convert to string to store it in variable
        // ask for phone num
        String newPhoneNum = (String) JOptionPane.showInputDialog(
            null, labelPhone, "Banking On It: Phone Number", JOptionPane.QUESTION_MESSAGE, iconPhone, null, ""
        );

        boolean validInput = false; // input is invalid by default

        do {
            // if user wishes to return to Home Page
            if (newPhoneNum == null) {
                // return string that indicates that user wishes to go back to Home Page
                return "Back123321";
            }
            // else if there's empty input
            else if (newPhoneNum.trim().length() == 0) {
                // message dialog informs user that input is invalid
                JOptionPane.showMessageDialog(
                    null, labelError, "Banking On It: Phone Number - ERROR!", JOptionPane.ERROR_MESSAGE, iconError
                );

                // ask for input again
                newPhoneNum = (String) JOptionPane.showInputDialog(
                    null, labelPhone, "Banking On It: Phone Number", JOptionPane.QUESTION_MESSAGE, iconPhone, null, ""
                );
            }
            // else if method that determines whether phone num is in proper format returns false
            else if (!validPhoneNum(newPhoneNum)) {
                // message dialog informs user that input is invalid
                JOptionPane.showMessageDialog(
                    null, labelErrorPhone, "Banking On It: Phone Number - ERROR!", JOptionPane.ERROR_MESSAGE, iconError
                );

                // ask for input again
                newPhoneNum = (String) JOptionPane.showInputDialog(
                    null, labelPhone, "Banking On It: Phone Number", JOptionPane.QUESTION_MESSAGE, iconPhone, null, ""
                );
            }
            // else
            else {
                validInput = true; // input is valid
            }

        }
        while (!validInput); // while input isn't valid

        return newPhoneNum; // return phone number

    }


    // method will determine whether a phone number is valid
    public static boolean validPhoneNum(String newPhoneNum) {
        // try and catch statement to ensure that a valid number was entered
        try {
            long test = Long.parseLong(newPhoneNum); // the phone number can successfully be parsed
        } catch (Exception e) {
            return false; // there are non-number chars in the string - return false
        }

        // if phone num is over or under 10 digits
        if (newPhoneNum.length() > 10 || newPhoneNum.length() < 10) {
            return false; // format which number was inputted in was incorrect
        }

        return true; // if this statement is reached, assume phone number is correctly formatted
    }


    // method will use input dialogs to collect user address
    public static String collectAddress(ImageIcon iconAddress, ImageIcon iconError, JLabel labelAddress, JLabel labelError, Font defaultFont) {
        JLabel labelNonAlphaNum = new JLabel("ERROR! Please enter an address with ONLY letters and numbers."); // label

        labelNonAlphaNum.setForeground(Color.white); // make text white
        labelNonAlphaNum.setFont(defaultFont); // set font to default font
        labelAddress.setForeground(Color.white); // make text white
        labelAddress.setFont(defaultFont); // set font to default font

        // Note: Due to multiple parameters, InputDialog will return answer as object - need to convert to string to store it in variable
        // ask for address
        String newAddress = (String) JOptionPane.showInputDialog(
            null, labelAddress, "Banking On It: Address", JOptionPane.QUESTION_MESSAGE, iconAddress, null, ""
        );

        boolean validInput = false; // input is invalid by default

        do {
            // if user wishes to return to Home Page
            if (newAddress == null) {
                // return string that indicates that user wishes to go back to Home Page
                return "Back123321";
            }
            // else if there's empty input
            else if (newAddress.trim().length() == 0) {
                // message dialog informs user that input is invalid
                JOptionPane.showMessageDialog(
                    null, labelError, "Banking On It: Address - ERROR!", JOptionPane.ERROR_MESSAGE, iconError
                );

                // ask for input again
                newAddress = (String) JOptionPane.showInputDialog(
                    null, labelAddress, "Banking On It: Address", JOptionPane.QUESTION_MESSAGE, iconAddress, null, ""
                );
            }
            // else if the input does not have only letters - call isAlphaNum method
            // first parameter for isAlphaNum denotes that it only needs to check for non-letter and non-number characters
            else if (!isAlphaNum(false, newAddress)) {
                // message dialog informs user that input is invalid
                JOptionPane.showMessageDialog(
                    null, labelNonAlphaNum, "Banking On It: Address - ERROR!", JOptionPane.ERROR_MESSAGE, iconError
                );

                // ask for input again
                newAddress = (String) JOptionPane.showInputDialog(
                    null, labelAddress, "Banking On It: Address", JOptionPane.QUESTION_MESSAGE, iconAddress, null, ""
                );
            }
            // else
            else {
                validInput = true; // input is valid
            }

        }
        while (!validInput); // while input isn't valid

        return newAddress; // return address

    }


    // method will use input dialogs to collect user account key
    public static String collectKey(ImageIcon iconKey, ImageIcon iconError, JLabel labelKey, JLabel labelError, Font defaultFont) {
        JLabel labelNonAlphaNum = new JLabel("ERROR! Please enter an Account Key with ONLY letters and numbers."); // label

        labelNonAlphaNum.setForeground(Color.white); // make text white
        labelNonAlphaNum.setFont(defaultFont); // set font to default font
        labelKey.setForeground(Color.white); // make text white
        labelKey.setFont(defaultFont); // set font to default font

        // Note: Due to multiple parameters, InputDialog will return answer as object - need to convert to string to store it in variable
        // ask for key
        String newAccountKey = (String) JOptionPane.showInputDialog(
            null, labelKey, "Banking On It: Account Key", JOptionPane.QUESTION_MESSAGE, iconKey, null, ""
        );

        boolean validInput = false; // input is invalid by default

        do {
            // if user wishes to return to Home Page
            if (newAccountKey == null) {
                // return string that indicates that user wishes to go back to Home Page
                return "Back123321";
            }
            // else if there's empty input
            else if (newAccountKey.trim().length() == 0) {
                // message dialog informs user that input is invalid
                JOptionPane.showMessageDialog(
                    null, labelError, "Banking On It: Account Key - ERROR!", JOptionPane.ERROR_MESSAGE, iconError
                );

                // ask for input again
                newAccountKey = (String) JOptionPane.showInputDialog(
                    null, labelKey, "Banking On It: Account Key", JOptionPane.QUESTION_MESSAGE, iconKey, null, ""
                );
            }
            // else if the input does not have only letters - call isAlphaNum method
            // first parameter for isAlphaNum denotes that it only needs to check for non-letter and non-number characters
            else if (!isAlphaNum(false, newAccountKey)) {
                // message dialog informs user that input is invalid
                JOptionPane.showMessageDialog(
                    null, labelNonAlphaNum, "Banking On It: Account Key - ERROR!", JOptionPane.ERROR_MESSAGE, iconError
                );

                // ask for input again
                newAccountKey = (String) JOptionPane.showInputDialog(
                    null, labelKey, "Banking On It: Account Key", JOptionPane.QUESTION_MESSAGE, iconKey, null, ""
                );
            }
            // else
            else {
                validInput = true; // input is valid
            }

        }
        while (!validInput); // while input isn't valid

        return newAccountKey; // return account key

    }


    // method will use input dialogs to collect user balance
    public static double collectBalance(ImageIcon iconBalance, ImageIcon iconError, JLabel labelBalance, JLabel labelError, JLabel labelErrorEmpty, Font defaultFont) {
        labelBalance.setForeground(Color.white); // make text white
        labelBalance.setFont(defaultFont); // set font to default font
        labelErrorEmpty.setForeground(Color.white); // make text white
        labelErrorEmpty.setFont(defaultFont); // set font to default font

        // Note: Due to multiple parameters, InputDialog will return answer as object - need to convert to string to store it in variable
        // ask for balance
        String tempBalance = (String) JOptionPane.showInputDialog(
            null, labelBalance, "Banking On It: Account Balance", JOptionPane.QUESTION_MESSAGE, iconBalance, null, ""
        );

        boolean validInput = false; // input is invalid by default

        do {
            // if user wishes to return to Home Page
            if (tempBalance == null) {
                // return number that indicates that user wishes to back to the Home Page (-1)
                return -1;
            }
            // else if there's empty input
            else if (tempBalance.trim().length() == 0) {
                // message dialog informs user that input is invalid
                JOptionPane.showMessageDialog(
                    null, labelError, "Banking On It: Account Balance - ERROR!", JOptionPane.ERROR_MESSAGE, iconError
                );

                // ask for input again
                tempBalance = (String) JOptionPane.showInputDialog(
                    null, labelBalance, "Banking On It: Account Balance", JOptionPane.QUESTION_MESSAGE, iconBalance, null, ""
                );

                continue; // continue (passes this iteration - will test again)
            }

            // try and catch to ensure that a valid number was entered
            try {
                double test = Double.parseDouble(tempBalance); // check to see if the balance can be successfully parsed
            } catch (Exception e) {
                // message dialog informs user that input is invalid
                JOptionPane.showMessageDialog(
                    null, labelErrorEmpty, "Banking On It: Account Balance - ERROR!", JOptionPane.ERROR_MESSAGE, iconError
                );

                // ask for input again
                tempBalance = (String) JOptionPane.showInputDialog(
                    null, labelBalance, "Banking On It: Account Balance", JOptionPane.QUESTION_MESSAGE, iconBalance, null, ""
                );

                continue; // continue (passes this iteration - will test again)
            }

            // if user deposits a negative amount
            if (Double.parseDouble(tempBalance) < 0) {
                // message dialog informs user that input is invalid
                JOptionPane.showMessageDialog(
                    null, labelErrorEmpty, "Banking On It: Account Balance - ERROR!", JOptionPane.ERROR_MESSAGE, iconError
                );

                // ask for input again
                tempBalance = (String) JOptionPane.showInputDialog(
                    null, labelBalance, "Banking On It: Account Balance", JOptionPane.QUESTION_MESSAGE, iconBalance, null, ""
                );
            }
            // else
            else {
                validInput = true; // input is valid
            }

        }
        while (!validInput); // while input isn't valid

        double newBalance = Double.parseDouble(tempBalance); // convert balance to double

        return newBalance; // return balance
    }


    // method will determine whether any string has characters that are not in the alphabet or are not alphabetic
    public static boolean isAlphaNum(boolean isAlphaOnly, String text) {
        String tempText = text.toLowerCase(); // convert all letters to lower case

        // use regex to remove spaces
        tempText = tempText.replaceAll("\\s", "");

        // if user only wants to check if a string is in the alphabet
        if (isAlphaOnly) {
            // loop through string
            for (int i = 0; i < tempText.length(); i++) {
                // if any char is not a letter
                if (!((int) tempText.charAt(i) >= 97 && (int) tempText.charAt(i) <= 122)) {
                    return false;
                }
            }
        }
        // else - check if a string is alphanumeric
        else {
            // loop through string
            for (int i = 0; i < tempText.length(); i++) {
                // if any char is not a letter and number
                if (!((int) tempText.charAt(i) >= 97 && (int) tempText.charAt(i) <= 122) && !((int) tempText.charAt(i) >= 48 && (int) tempText.charAt(i) <= 57)) {
                    return false;
                }
            }

        }

        return true; // return true if this statement is reached

    }
} // NewCustomer class