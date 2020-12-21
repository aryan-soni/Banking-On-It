/*

    NOTE: The sole purpose of this class is to supply methods.
    This program SHOULD NOT be run on its own.

*/

import java.io.*;

// The "FileHandler" class.
public class FileHandler {
    public static void main(String[] args) {
        // leave main method empty as it won't be explicitly called
    } // main method


    // method will return the number of customers
    public static int countCustomers() throws IOException {
        int count = 0; // value that will be returned - will store number of customers

        // will be used to count # of accounts - useful for defining length of arrays
        BufferedReader counter = new BufferedReader(new FileReader("CustomerData.txt"));

        // while there is data to parse in the text file
        while (counter.readLine() != null) {
            count++; // add 1 to count
        }

        // since there are 5 parameters for customer data, # of accounts = # of lines / 5
        count = count / 5;

        counter.close(); // close counter reader

        return count; // return count
    }


    // method will populate arrays, taking in # of customers and all parallel arrays as input - call decode methods when storing data to arrays as the data is encrypted
    // will return false if the arrays can no longer be populated
    public static boolean populate(int count, String names[], String phoneNums[], String addresses[], String accountKeys[], double balances[]) throws IOException {
        // if the last index has been populated
        if (!names[names.length - 1].equals(" ")) {
            return false; // return false if limit has been exceeded for # of new customers that can be stored in the database
        }

        // set up main file reader
        BufferedReader fr = new BufferedReader(new FileReader("CustomerData.txt"));

        // populate parallel arrays - call method from Cryptographer class to decode each line of data
        for (int i = 0; i < count; i++) {
            names[i] = Cryptographer.decode(fr.readLine());
            phoneNums[i] = Cryptographer.decode(fr.readLine());
            addresses[i] = Cryptographer.decode(fr.readLine());
            accountKeys[i] = Cryptographer.decode(fr.readLine());
            balances[i] = Double.parseDouble(Cryptographer.decode(fr.readLine()));

        }

        fr.close(); // close file reader

        return true; // if the method has reached this statement, the array has been successfully populated

    }


    // method will append new data to the text file - call method from Cryptographer class to encode data when sending it to the text file
    public static void appendToFile(String newName, String newPhoneNum, String newAddress, String newAccountKey, double newBalance) throws IOException {
        // config FileWriter to append to existing file
        FileWriter outFile = new FileWriter("CustomerData.txt", true);
        PrintWriter output = new PrintWriter(outFile);

        // add info to file
        output.println(Cryptographer.encode(newName));
        output.println(Cryptographer.encode(newPhoneNum));
        output.println(Cryptographer.encode(newAddress));
        output.println(Cryptographer.encode(newAccountKey));
        output.println(Cryptographer.encode(Double.toString(newBalance)));

        outFile.close(); // close outFile

    }


    // method will update the textFile
    public static void updateFile(String names[], String phoneNums[], String addresses[], String accountKeys[], double balances[]) throws IOException {
        int count = countCustomers(); // store # of customers by calling countCustomers method

        // config FileWriter to rewrite existing file
        FileWriter outFile = new FileWriter("CustomerData.txt");
        PrintWriter output = new PrintWriter(outFile);

        // populate text file - call method from Cryptographer class to encode data when sending it to the text file
        for (int i = 0; i < count; i++) {
            // add info to file
            output.println(Cryptographer.encode(names[i]));
            output.println(Cryptographer.encode(phoneNums[i]));
            output.println(Cryptographer.encode(addresses[i]));
            output.println(Cryptographer.encode(accountKeys[i]));
            output.println(Cryptographer.encode(Double.toString(balances[i])));
        }

        outFile.close(); // close outFile

    }
} // FileHandler class