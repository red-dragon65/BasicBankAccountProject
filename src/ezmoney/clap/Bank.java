/*

Author: Brandyn Call
Author: Mujtaba Ashfaq

Created: 7/9/19
Last Edited: 7/17/19

About:
CS 3230 Midterm project. It is an application that mimics the way banking software would function.
The user can access the system using an admin account or a customer account. Customers can manipulate their
accounts and admins can manipulate all accounts.

Execution:
Run the driver class 'Bank'. One first execution, the database will not be initialized. In order to populate the
database, login as the admin using the pin 1234. The database will not be saved unless the user exits the program
using the 'exit' term in a main menu. Once the database has been initialized, the customers can login to access the
accounts that were created by the admin.

Note:
Use this test code to make sure all accounts are being populated.
Check the todo below.

//Verify list data
for (Account a : accountDatabase) {
    System.out.println(a);
}

 */

package ezmoney.clap;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.exit;

/**
 * The driver class that gets input from the console and passes it to the correct class.
 */
public class Bank {

    public static void main(String[] args) {

        //Hold all the accounts in the bank
        ArrayList<Account> accountDatabase = new ArrayList<Account>();

        //Hold input file
        InputStream inputFile;


        //Deserialize file (load accountDatabase)
        try {

            //Get the account counter data
            File file = new File("./data/AccountCounter.txt");
            Scanner in = new Scanner(file);

            String tempAccountCounter = in.nextLine();
            String tempUserCounter = in.nextLine();

            Account.setAccountNumberCounter(Integer.parseInt(tempAccountCounter));
            Account.setUserIDCounter(Integer.parseInt(tempUserCounter));
            in.close();


            //Get the account database data
            FileInputStream fis = new FileInputStream("./data/AccountData");
            ObjectInputStream ois = new ObjectInputStream(fis);

            accountDatabase = (ArrayList<Account>) ois.readObject();

            ois.close();
            fis.close();

        } catch (IOException ioe) {

            //ioe.printStackTrace();
            System.out.println("Error: The account database file could not be found! It will be created on exit!\n");

        } catch (ClassNotFoundException c) {

            System.out.println("Class not found");
            c.printStackTrace();

        }

        //TODO: Use this test code to view all account data from the file.
        /*
        //Verify list data
        for (Account a : accountDatabase) {
            System.out.println(a);
        }*/


        //Hold user data
        boolean login = false;
        String userId = "";
        int userIdParsed = 0;
        String pin = "";
        int pinParsed = 0;
        String selection = "";
        int selectionParsed = 0;

        String userType = "";
        int defaultAdminPassword = 1234;

        //Get input
        Scanner consoleInput = new Scanner(System.in);

        //Hold the database manipulators
        Customer customer = new Customer();
        Admin admin = new Admin();



        /*
        Get the account type
         */
        while (true) {

            //Attempt to get input
            try {

                //Get account type
                System.out.println("Enter user type (Admin or Customer): ");
                System.out.println("(Enter 'exit' to close the program)");
                userType = consoleInput.nextLine();

                //End the program if necessary
                endProgram(userType, accountDatabase);


            } catch (Exception e) {

                //Get input again if input could not be validated
                System.out.println("Incorrect input!");
                continue;
            }


            //Check type
            if (!userType.equalsIgnoreCase("admin") && !userType.equalsIgnoreCase("customer")) {

                //Neither account type selected
                System.out.println("Incorrect value entered!");

            } else {

                //Account selected
                //Move onto next code
                break;
            }

        }



        /*
        Run code for bank teller
        */
        if (userType.equalsIgnoreCase("admin")) {

            //Login admin
            while (!login) {

                //Attempt to get input
                try {

                    //Get the pin
                    System.out.println("\nEnter the Admin pin: ");
                    System.out.println("Enter 'exit' to close the program.");

                    pin = consoleInput.nextLine();

                    //Exit the program if necessary
                    endProgram(pin, accountDatabase);

                    //Parse input
                    pinParsed = Integer.parseInt(pin);

                } catch (Exception e) {

                    //Get input again if input could not be validated
                    System.out.println("Incorrect input!");
                    continue;
                }


                //The password matches
                if (pinParsed == defaultAdminPassword) {

                    //Break this loop
                    System.out.println("\nLogging in... Welcome!\n");
                    login = true;
                    break;

                } else {

                    System.out.println("Invalid credentials!");
                }
            }


            //Show admin selection
            while (login) {

                //Show selection
                System.out.println("\nEnter a selection:");
                System.out.println("1. List all accounts");
                System.out.println("2. List Accounts using user id");
                System.out.println("3. List Accounts using username");
                System.out.println("4. Delete Account");
                System.out.println("5. Create Account");
                System.out.println("6. Deposit Money into an account");
                System.out.println("7. Withdraw money from an account");
                System.out.println("8. Request an account summary");
                System.out.println("9. Request account transaction details");
                System.out.println("10. Transfer money between accounts");

                System.out.println("Selection: ");


                //Attempt to get input
                try {

                    //Get input
                    selection = consoleInput.nextLine();

                    //Exit the program if necessary
                    endProgram(selection, accountDatabase);

                    //Parse input
                    selectionParsed = Integer.parseInt(selection);

                } catch (Exception e) {

                    //Get input again if input could not be validated
                    System.out.println("Incorrect input!");
                    continue;
                }


                //Run Admin code
                switch (selectionParsed) {

                    case 1:

                        //List all accounts in the bank
                        admin.listAllAccounts(accountDatabase);

                        break;
                    case 2:

                        //This lists certain accounts based on user id
                        admin.listAccountsUserID(accountDatabase);

                        break;
                    case 3:

                        //This lists certain accounts based on user name
                        admin.listAccountsUsername(accountDatabase);

                        break;
                    case 4:

                        //Delete the specified account
                        admin.deleteAccount(accountDatabase);

                        break;
                    case 5:

                        //Create a new account
                        admin.createAccount(accountDatabase, "");

                        break;
                    case 6:

                        //Deposit money into the specified account
                        admin.deposit(accountDatabase);

                        break;
                    case 7:

                        //Withdraw money from the specified account
                        admin.withdraw(accountDatabase);

                        break;
                    case 8:

                        //Get info from specified account
                        admin.requestAccountSummary(accountDatabase);

                        break;
                    case 9:

                        //Get transaction history from specified account
                        admin.requestTransactionDetails(accountDatabase);

                        break;
                    case 10:

                        //Send money from a specified account to another specified account
                        admin.transferMoney(accountDatabase);

                        break;

                    default:
                        System.out.println("The entered selection was not in the list!");
                }


            }


        }



        /*
        Loop through customer login attempts
         */
        if (userType.equalsIgnoreCase("customer")) {

            //Loop through customer login attempts
            while (!login) {

                //Attempt to get input
                try {

                    //Get credentials
                    System.out.println("\n-----LOGIN-----");

                    System.out.println("Enter UserID:");

                    //Hold input
                    userId = consoleInput.nextLine();

                    //End program if necessary
                    endProgram(userId, accountDatabase);

                    //Parse input
                    userIdParsed = Integer.parseInt(userId);


                    System.out.println("Enter Pin:");

                    //Hold input
                    pin = consoleInput.nextLine();

                    //End program if necessary
                    endProgram(pin, accountDatabase);

                    //Parse input
                    pinParsed = Integer.parseInt(pin);

                } catch (Exception e) {

                    //Get input again if input could not be validated
                    System.out.println("Incorrect input!");
                    continue;
                }


                //See if account exists
                //Loop through all accounts
                for (Account a : accountDatabase) {

                    //See if name matches
                    if (a.getUserID() == userIdParsed) {

                        //Found an account with the same name!

                        //See if pin matches
                        if (a.getPin() == pinParsed) {

                            //Notify account has been found
                            login = true;
                            System.out.println("\nLogging in... Welcome!\n");
                            break;
                        }

                    }
                }


                //Break loop if the account was found
                if (login) {

                    //Tell user about login attempt
                    System.out.println("The account was found!");
                    break;

                } else {

                    //Tell user about login attempt
                    System.out.println("The account was not found!");
                }


            }


            //Loop through selection options
            while (login) {

                //Show selection
                System.out.println("\nEnter a selection:");
                System.out.println("1. List my Accounts");
                System.out.println("2. Delete my Account");
                System.out.println("3. Create a new Account");
                System.out.println("4. Deposit Money into my account");
                System.out.println("5. Withdraw money from my account");
                System.out.println("6. Request an account summary");
                System.out.println("7. Request my account transaction details");
                System.out.println("8. Transfer money to another account");

                System.out.println("Selection: ");


                //Attempt to get input
                try {

                    //Get input
                    selection = consoleInput.nextLine();

                    //Exit the program if necessary
                    endProgram(selection, accountDatabase);

                    //Parse input
                    selectionParsed = Integer.parseInt(selection);


                } catch (Exception e) {

                    //Get input again if input could not be validated
                    System.out.println("Incorrect input!");
                    continue;
                }


                //Run customer code
                switch (selectionParsed) {


                    case 1:

                        //Show accounts tied to userId
                        customer.listMyAccounts(accountDatabase, userIdParsed);

                        break;
                    case 2:

                        //Delete account specified
                        customer.deleteAccount(accountDatabase, userIdParsed);

                        break;
                    case 3:

                        //Create a new account
                        customer.createAccount(accountDatabase, userId);

                        break;
                    case 4:

                        //Deposit money to the account specified
                        customer.deposit(accountDatabase, userIdParsed);

                        break;
                    case 5:

                        //Withdraw money from the account specified
                        customer.withdraw(accountDatabase, userIdParsed);

                        break;
                    case 6:

                        //Get specified accounts activity
                        customer.requestAccountDetails(accountDatabase, userIdParsed, "Account summary");

                        break;
                    case 7:

                        //Get specified accounts transaction history
                        customer.requestAccountDetails(accountDatabase, userIdParsed, "Transaction details");

                        break;
                    case 8:

                        //Send money from the specified account to another specified account
                        customer.transferMoney(accountDatabase, userIdParsed);

                        break;

                    default:
                        System.out.println("The entered selection was not in the list!");

                }
            }


        }


    }


    //Save account database and end the program

    /**
     * Ends the program and saves the database values.
     * @param value Checks to see if input is 'exit'
     * @param accountDatabase The ArrayList holding all customer accounts
     */
    private static void endProgram(String value, ArrayList<Account> accountDatabase) {

        if (value.equalsIgnoreCase("exit")) {


            //Serialize to file (save accountDatabase)
            try {

                //Write the account counter data
                Writer wr = new FileWriter("./data/AccountCounter.txt");

                int tempAccountNumber = Account.getAccountNumberCounter();
                int tempUserCounter = Account.getUserIDCounter();

                wr.write(tempAccountNumber + "\n");
                wr.write(tempUserCounter + "");

                wr.close();


                //Write the account database values
                FileOutputStream fos = new FileOutputStream("./data/AccountData");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(accountDatabase);
                oos.close();
                fos.close();

            } catch (IOException ioe) {

                ioe.printStackTrace();
                System.out.println("Something went wrong while saving the data!!!");

            }


            System.out.println("\nThank you for your business!");
            System.out.println("Please come again!");
            exit(0);
        }

    }

}
