import javax.swing.*;
import java.io.*;
import java.awt.*;

// The "Main" class.
public class Main
{
    public static void main (String[] args) throws IOException
    {
	boolean exit = false; // will store whether user wishes to exit

	// method will return the # of customers by reading through the text file (which is the data base)
	int count = FileHandler.countCustomers ();

	// create parallel arrays that will store customer data
	String names[], phoneNums[], addresses[], accountKeys[];
	double balances[];

	// set lengths for parallel arrays using established count
	// add 20, as in one session, a user will be able to add a max of 20 new customers
	names = new String [count + 20];
	phoneNums = new String [count + 20];
	addresses = new String [count + 20];
	accountKeys = new String [count + 20];
	balances = new double [count + 20];

	// temporarily populate arrays
	for (int i = 0 ; i < count + 20 ; i++)
	{
	    names [i] = " ";
	    phoneNums [i] = " ";
	    addresses [i] = " ";
	    accountKeys [i] = " ";
	    balances [i] = -1;
	}

	// method will populate arrays with customer data, taking in the # of customers as input and each parallel array
	// method returns false if the array was not able to be populated - cannot return false here as at this point, no new customers have been added
	boolean populateArr = FileHandler.populate (count, names, phoneNums, addresses, accountKeys, balances);

	String options[] = {"Customer", "Admin", "Help", "Exit"}; // options for option dialog
	ImageIcon icon = new ImageIcon ("bank.png"); // image for first dialog

	// use UIManager to change graphical settings for dialog boxes
	UIManager UI = new UIManager ();
	// black background
	UI.put ("OptionPane.background", Color.black);
	UI.put ("Panel.background", Color.black);
	// set minimum size
	UIManager.put ("OptionPane.minimumSize", new Dimension (600, 175));
	// use UIManager to modify text for input dialog buttons
	UI.put ("OptionPane.cancelButtonText", "Return");

	// use jLabel to output text on dialog box - format using html
	JLabel label = new JLabel (
		"<html>Welcome to Banking On It - the Ultimate Virtual ATM!<br><br>Please select whether you are a customer or administrator.<br><br>Click 'Help' for help, and 'Exit' to exit.</html>"
		);
	label.setForeground (Color.white); // make text white

	// change font and text size of label
	Font defaultFont = new Font ("Monospaced", Font.BOLD, 16);
	label.setFont (defaultFont);

	do
	{

	    // prompt user to choose option - store response into var
	    int response = JOptionPane.showOptionDialog (
		    null, label,
		    "Banking On It - Home Page", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, icon, options, options [0]
		    );

	    // if the user select customer
	    if (response == 0)
	    {
		customer (names, phoneNums, addresses, accountKeys, balances, defaultFont); // call method that will execute customer controls - pass parallel arrays and default font
	    }
	    // else if the user selects admin
	    else if (response == 1)
	    {
		admin (names, phoneNums, addresses, accountKeys, balances, defaultFont); // call method that will execute admin controls - pass parallel arrays and default font
	    }
	    // else if user selects help
	    else if (response == 2)
	    {
		help (defaultFont); // call method that will send dialog to user informing them how to receive help
	    }
	    // else - user selects exit or x button is clicked
	    else
	    {
		exit (defaultFont); // call method that sends exit message dialog
		exit = true; // set exit to true - will exit out of loop
	    }
	}
	while (!exit);  // keep looping while user does not wish to exit

    } // main method


    // method will run when customer is using program
    public static void customer (String names[], String phoneNums[], String addresses[], String accountKeys[], double balances[], Font defaultFont) throws IOException
    {
	// outline options for option dialog
	String[] options = {"New Customer", "Existing Customer", "Back"};

	ImageIcon icon = new ImageIcon ("customer.png"); // image for initial customer dialog

	// use jLabel to output text on dialog box
	JLabel label = new JLabel ("Welcome to the Customer Hub! Please select whether you are a new or existing customer."
		);
	label.setForeground (Color.white); // make text white
	label.setFont (defaultFont); // set font to default font

	boolean toContinue = true; // store whether user wishes to continue

	// while user wishes to continue
	while (toContinue)
	{
	    // prompt user to choose option - store response into var
	    int response = JOptionPane.showOptionDialog (
		    null, label,
		    "Banking On It - Customer Hub", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, icon, options, options [0]
		    );

	    // if user selects new customer
	    if (response == 0)
	    {
		// call the "start" method from New Customer class - will orchestrate the neccessary functionality for new customers
		NewCustomer.start (names, phoneNums, addresses, accountKeys, balances, defaultFont);
	    }
	    // else if user selects existing customer
	    else if (response == 1)
	    {
		// call the "start" method from Existing Customer class - will orchestrate the neccessary functionality for existing customers
		ExistingCustomer.start (names, phoneNums, addresses, accountKeys, balances, defaultFont);
	    }
	    // else - user clicked exit or "X" button was clicked
	    else
	    {
		toContinue = false; // customer no longer wishes to continue
	    }

	}
    }


    // method will run when admin is using program
    public static void admin (String names[], String phoneNums[], String addresses[], String accountKeys[], double balances[], Font defaultFont) throws IOException
    {
	String password = Admin.collectPass (defaultFont); // call method from Admin class that collects the admin password

	// if the user wishes to exit
	if (password.equals ("Back123321"))
	{
	    return; // break out of method
	}
	// else - the password is correct
	else
	{
	    // call method from Admin class that orchestrates the admin controls
	    Admin.adminControls (names, phoneNums, addresses, accountKeys, balances, defaultFont);
	}
    }


    // method will inform user how they can seek help
    public static void help (Font defaultFont)
    {
	// set up image
	ImageIcon icon = new ImageIcon ("help.png");

	// set up label - use html to format
	JLabel label = new JLabel (
		"<html>If you would like assistance, please refer to our documentation.<br>Our documentation is titled 'Aryan Soni Banking On It - READ ME'</html>"
		);

	label.setForeground (Color.white); // make text white
	label.setFont (defaultFont); // set font to default font

	// message dialog
	JOptionPane.showMessageDialog (
		null, label, "Banking On It: Help", JOptionPane.INFORMATION_MESSAGE, icon
		);
    }


    // methods sends exit message to users
    public static void exit (Font defaultFont)
    {
	// set up image
	ImageIcon icon = new ImageIcon ("thanks.png");

	// set up label - use html to format
	JLabel label = new JLabel (
		"<html>Thank you for using Banking On It! <br> We hope to see you again! </html>"
		);

	label.setForeground (Color.white); // make text white
	label.setFont (defaultFont); // set font to default font

	// message dialog
	JOptionPane.showMessageDialog (
		null, label, "Banking On It: Exit Message", JOptionPane.INFORMATION_MESSAGE, icon
		);

    }
} // Main class
