package src.BankingOnIt;/*

    NOTE: The sole purpose of this class is to supply methods.
    This program SHOULD NOT be run on its own.

*/

import src.BankingOnIt.FileHandler;

import java.io.*;

// The "src.BankingOnIt.SortAndSearch" class.
public class SortAndSearch
{
    public static void main (String[] args)
    {
	// Leave empty - main method
    } // main method


    // method will use bubble sort to alphabetically sort the customer names (by first name)
    public static void alphaSort (String names[], String phoneNums[], String addresses[], String accountKeys[], double balances[]) throws IOException
    {
	int count = FileHandler.countCustomers (); // utilize countCustomers method from the File Handler class to find the # of customers

	// loop through the # of accounts
	for (int i = 0 ; i < count - 1 ; i++)
	{
	    /*
	    loop through the # of accounts - 1 - i times
	    subtract 1 as the logic will be comparing names at two indices, meaning the leftIndex + 1 must be within the array's bounds
	    subtract i as each iteration, the last letter will always be sorted
	    */

	    for (int j = 0 ; j < count - 1 - i ; j++)
	    {
		// store names into vars - remove spaces and make lower case
		String name1 = names [j].replaceAll ("\\s", "").toLowerCase ();
		String name2 = names [j + 1].replaceAll ("\\s", "").toLowerCase ();

		// length of the first name being compared
		int length1 = name1.length ();
		// length of the second name being compared
		int length2 = name2.length ();

		int min = length1; // default min length to length1

		// if length2 is less than length1
		if (length2 < length1)
		{
		    min = length2;
		}

		// loop through the min length - to compare the two names
		for (int k = 0 ; k < min ; k++)
		{
		    // convert selected letter of the first name being compared to ascii
		    int letter1 = (int) name1.charAt (k);
		    // convert select letter of the second name being compared to ascii
		    int letter2 = (int) name2.charAt (k);

		    // if the letter of the first name selected is higher in the alphabet than the letter of the second name selected
		    if (letter1 > letter2)
		    {
			// temporarily store selected items of parallel arrays
			String tempName = names [j];
			String tempPhone = phoneNums [j];
			String tempAddress = addresses [j];
			String tempKey = accountKeys [j];
			double tempBalance = balances [j];

			// sort:

			names [j] = names [j + 1];
			phoneNums [j] = phoneNums [j + 1];
			addresses [j] = addresses [j + 1];
			accountKeys [j] = accountKeys [j + 1];
			balances [j] = balances [j + 1];

			names [j + 1] = tempName;
			phoneNums [j + 1] = tempPhone;
			addresses [j + 1] = tempAddress;
			accountKeys [j + 1] = tempKey;
			balances [j + 1] = tempBalance;

			break;

		    }
		    // else if the two selected letters are equal
		    else if (letter1 == letter2)
		    {
			continue; // jump to next iteration
		    }
		    // else - if the two conditions above aren't met - the names are already sorted
		    else
		    {
			break; // break out of the loop
		    }
		}
		// if names [j+1] is shorter than names[j] and names[j+1] is a part of names[j]
		if (min == length2 && name2.equalsIgnoreCase (name1.substring (0, min)))
		{
		    // temporarily store selected items of parallel arrays
		    String tempName = names [j];
		    String tempPhone = phoneNums [j];
		    String tempAddress = addresses [j];
		    String tempKey = accountKeys [j];
		    double tempBalance = balances [j];

		    // sort:

		    names [j] = names [j + 1];
		    phoneNums [j] = phoneNums [j + 1];
		    addresses [j] = addresses [j + 1];
		    accountKeys [j] = accountKeys [j + 1];
		    balances [j] = balances [j + 1];

		    names [j + 1] = tempName;
		    phoneNums [j + 1] = tempPhone;
		    addresses [j + 1] = tempAddress;
		    accountKeys [j + 1] = tempKey;
		    balances [j + 1] = tempBalance;

		}

	    }
	}


    }


    // method will use binary search to search for a customer - return index of name
    // will use both the name and the key to find the customer
    public static int searchCustomer (String name, String names[], String key, String accountKeys[]) throws IOException
    {
	int low, high, middle; // high, low, and middle values when finding index

	int count = FileHandler.countCustomers (); // utilize countCustomers method from the File Handler class to find the # of customers

	// initialize high and low values
	low = 0;
	high = count - 1;

	// while low is less than or equal to high
	while (low <= high)
	{
	    // set middle value
	    middle = (high + low) / 2;

	    // store names into vars - remove spaces and make lower case
	    String name1 = name.replaceAll ("\\s", "").toLowerCase ();
	    String name2 = names [middle].replaceAll ("\\s", "").toLowerCase ();

	    // length of the name that was searched
	    int length1 = name1.length ();
	    // length of the name at the middle index
	    int length2 = name2.length ();

	    int min = length1; // default min length to length1

	    // if length2 is less than length1
	    if (length2 < length1)
	    {
		min = length2;
	    }

	    boolean isChanged = false; // will store whether low/high index was changed


	    // loop through the min length - to compare the two names
	    for (int i = 0 ; i < min ; i++)
	    {
		// if the name that was searched equals to the name at the middle index, and the keys of both of the names are the same
		if (name.equalsIgnoreCase (names [middle]) && key.equals (accountKeys [middle]))
		{
		    return middle; // return the index
		}
		// else if the letter selected of the name is searched is higher in the alphabet than the name at the middle index
		else if (name1.charAt (i) > name2.toLowerCase ().charAt (i))
		{
		    low = middle + 1; // change the lowest index
		    isChanged = true; // index was changed
		    break; // break out of the loop, as it's now confirmed that the name at the middle index is not the name that was searched
		}
		// else if the letter selected of the name is searched is lower in the alphabet than the name at the middle index
		else if (name1.charAt (i) < name2.toLowerCase ().charAt (i))
		{
		    high = middle - 1; // move the highest index down by making it middle - one
		    isChanged = true; // index was changed
		    break; // break out of the loop, as it's now confirmed that the name at the middle index is not the name that was searched

		}
		// else if the names are the same but the keys are different
		else if (name.equalsIgnoreCase (names [middle]) && !key.equals (accountKeys [middle]))
		{
		    // method called will return the index of the customer if they exist
		    // need to use linear search as this is an edge case
		    return linearSearch (name, names, key, accountKeys, count);

		}
	    }

	    // if the index was changed
	    if (isChanged)
	    {
		continue; // continue to the next iteration
	    }

	    // if names [middle] is shorter than the searched name and names[middle] is a part of the searched name
	    if (min == length2 && name2.equalsIgnoreCase (name1.substring (0, min)))
	    {
		low = middle + 1; // change the lowest index

	    }
	    // else if names [middle] is longer than the searched name and the searched name is a part of names[middle]
	    else if (min == length1 && name1.equalsIgnoreCase (name2.substring (0, min)))
	    {
		high = middle - 1; // change the highest index
	    }

	}

	return -1; // if index hasn't been returned by now, assume the name doesn't exist
    }


    // linear search algorithm - will you use in the edge case where multiple customers with the same name and with different keys exist
    // will return the index of the user
    public static int linearSearch (String name, String names[], String key, String accountKeys[], int count) throws IOException
    {
	// loop through all the customers
	for (int i = 0 ; i < count ; i++)
	{
	    // if a name and key matches
	    if (name.equalsIgnoreCase (names [i]) && key.equals (accountKeys [i]))
	    {
		return i; // return the index
	    }

	}

	return -1; // if this statement is reached, return -1

    }
} // src.BankingOnIt.SortAndSearch class


