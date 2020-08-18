package src.BankingOnIt;/*

    NOTE: The sole purpose of this class is to supply methods.
    This program SHOULD NOT be run on its own.

*/

import javax.swing.*;
import java.io.*;
import java.awt.*;

// The "src.BankingOnIt.ExistingCustomer" class.
public class ExistingCustomer
{
    public static void main (String[] args)
    {
	// Leave empty - main method will not explicitly be called
    } // main method


    // method will start the process of logging in an existing customer
    public static void start (String names[], String phoneNums[], String addresses[], String accountKeys[], double balances[], Font defaultFont) throws IOException
    {
	// populate arrays in case they aren't populated using method from the File Handler class
	boolean isPopulated = FileHandler.populate (FileHandler.countCustomers (), names, phoneNums, addresses, accountKeys, balances);

	// initialize variables for inputted data
	String enteredName, enteredKey;

	// render images
	ImageIcon iconName = new ImageIcon ("src/imgs/name.png"); // image for name dialog
	ImageIcon iconKey = new ImageIcon ("src/imgs/key.png"); // image for key dialog
	ImageIcon iconError = new ImageIcon ("src/imgs/error.png"); // image for error dialog

	int index; // will store index of user

	// use jLabels to output text on dialog boxes:
	// label for name dialog
	JLabel labelName = new JLabel (
		"Please enter your full name."
		);
	// label for key dialog
	JLabel labelKey = new JLabel (
		"Please enter your alphanumeric account key."
		);
	// label for error dialog
	JLabel labelError = new JLabel (
		"Error! We received an empty input. Please try again."
		);

	// set color and font
	labelError.setForeground (Color.white); // make text white
	labelError.setFont (defaultFont); // set font to default font

	// call method that will return inputted name by sending input dialog to user - inherited from New Customer class
	enteredName = NewCustomer.collectName (iconName, iconError, labelName, labelError, defaultFont);

	// if the user wishes to go back
	if (enteredName.equals ("Back123321"))
	{
	    // since newCustomer is a procedure-type method, a standalone return statement will break out of method
	    return;
	}

	// call method that will return newKey by sending input dialog to user - inherited from New Customer class
	enteredKey = NewCustomer.collectKey (iconKey, iconError, labelKey, labelError, defaultFont);

	// if the user wishes to go back
	if (enteredKey.equals ("Back123321"))
	{
	    // since newCustomer is a procedure-type method, a standalone return statement will break out of method
	    return;
	}

	// call method from the Sort and Search class that will alphabetically sort the customers
	SortAndSearch.alphaSort (names, phoneNums, addresses, accountKeys, balances);

	// call method from the Sort and Search class that will search for a customers
	// method called returns -1 if the customer does not exist, else it returns index of the user
	index = SortAndSearch.searchCustomer (enteredName, names, enteredKey, accountKeys);

	// if the name and key entered aligns with a customer that exists
	if (index >= 0)
	{
	    boolean toContinue = true; // assume user wishes to continue

	    do
	    {
		// method called from Permissions Granted class will supply user with interface for modifying customer information
		// the method will return a boolean that indicates whether the user wishes to continue
		toContinue = PermissionsGranted.accessGranted (names, phoneNums, addresses, accountKeys, balances, defaultFont, index);

	    }
	    while (toContinue); // orchestrate loop while the user wishes to continue
	}
	// else - customer does not exist
	else
	{
	    // label for dialog - use html to format
	    JLabel labelUserInvalid = new JLabel (
		    "<html> Sorry, the information entered does not match with our records. <br> You will now be redirected to the Customer Hub. </html>"
		    );

	    labelUserInvalid.setForeground (Color.white); // make text white
	    labelUserInvalid.setFont (defaultFont); // set font to default font

	    // dialog that informs user that the customer does not exist
	    JOptionPane.showMessageDialog (
		    null, labelUserInvalid, "Banking On It: Customer (Invalid Log-In)", JOptionPane.ERROR_MESSAGE, iconName
		    );
	}
    }
} // src.BankingOnIt.ExistingCustomer class
