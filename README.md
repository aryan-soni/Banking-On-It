# Banking On It

Banking On It is a full-stack banking system that provides a comprehensive and secure interface for both customers and administrators. The project utilizes Object-Oriented Programming extensively, and also makes use of the Quicksort Sorting Algorithm and the Binary Search Algorithm. Furthermore, the application creates a custom database which encrypts customer information with a Substitution Cipher. Altogether, the program follows a modified version of the Model-View-Controller software design pattern.

## Demo

![](demo.gif)

## Application Features

**New Customers**: New Customers can create an account by inputting their name, phone number, address, account key, and account balance. There is constant input validation to ensure the data is formatted consistently across all customers. A New Customer can only be created if an Existing Customer does not already possess the same name and account key. Once a New Customer is created, their encrypted information is stored in the application custom database (a text file); this information can be accessed in future user sessions.

**Existing Customers**: Existing Customers can deposit/withdraw money, modify their account key, phone number, and address, and view their information. 

**Administrator**: Administrators can view the information of all customers, and can also modify the information of any Customer.

## Running the Program

To run this program, [clone this repository](https://docs.github.com/en/github/creating-cloning-and-archiving-repositories/cloning-a-repository) and run/compile *BankingOnIt.java* in the *src* directory on an IDE that supports Java. This program is optimized to run on MacOS.
