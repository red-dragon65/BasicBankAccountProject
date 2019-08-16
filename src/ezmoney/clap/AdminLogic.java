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

 */

package ezmoney.clap;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class modifies the database and has access to all accounts. It extends the CustomerLogic class for code reuse.
 */
public class AdminLogic extends CustomerLogic {

    protected Scanner consoleInput = new Scanner(System.in);

    /**
     * Displays all accounts in the database
     *
     * @param accountDatabase The list of all bank accounts
     */
    public void listAllAccounts(ArrayList<Account> accountDatabase, JTextArea outputArea) {

        outputArea.append("Complete list of accounts:\n");

        // Runs through every value in accountDatabase
        for (Account account : accountDatabase) {

            // Prints out account details
            outputArea.append(account.print() + "\n");
        }
    }

    /**
     * Displays all accounts belonging to a specific user id in the database
     *
     * @param accountDatabase The list of all bank accounts
     */
    public void listAccountsUserID(ArrayList<Account> accountDatabase, JTextField[] fields, JTextArea outputArea) {

        int userID = 0;

        try {

            // Grabs the specific user id to search the database for
            userID = Integer.parseInt(fields[0].getText());

        } catch (Exception ex) {

            outputArea.append("Incorrect input! Returning to main menu.\n");
        }

        for (Account account : accountDatabase) {

            // Checks database for an account that has the specified user id
            if (account.getUserID() == userID) {

                // Prints out account details
                outputArea.append(account.print() + "\n");
            }
        }
    }

    /**
     * Displays all accounts belonging to the same name in the database
     *
     * @param accountDatabase The list of all bank accounts
     */
    public void listAccountsUsername(ArrayList<Account> accountDatabase, JTextField[] fields, JTextArea outputArea) {

        String username = "";

        try {

            // Grabs specific user name to search the database for
            username = fields[0].getText();

        } catch (Exception ex) {

            outputArea.append("Incorrect input! Returning to main menu.\n");
        }

        for (Account account : accountDatabase) {

            // Checks database for an account with the specified user name
            if (account.getHoldersName().equalsIgnoreCase(username)) {

                // Prints out account details
                outputArea.append(account.toString());
            }
        }
    }

    /**
     * Deletes an account with help from the super class
     *
     * @param accountDatabase The list of all bank accounts
     */
    public void deleteAccount(ArrayList<Account> accountDatabase, JTextField[] fields, JTextArea outputArea) {

        //Call the super method
        super.deleteAccount(accountDatabase, getUserID(fields, outputArea), fields, outputArea);

    }

    /**
     * Deposits money into account specified
     *
     * @param accountDatabase The list of all bank accounts
     */
    public void deposit(ArrayList<Account> accountDatabase, JTextField[] fields, JTextArea outputArea) {

        // Calls the super method
        super.deposit(accountDatabase, getUserID(fields, outputArea), fields, outputArea);

    }

    /**
     * Withdraws money into account specified
     *
     * @param accountDatabase The list of all bank accounts
     */
    public void withdraw(ArrayList<Account> accountDatabase, JTextField[] fields, JTextArea outputArea) {

        // Calls the super method
        super.withdraw(accountDatabase, getUserID(fields, outputArea), fields, outputArea);
    }

    /**
     * Get specified accounts activity
     *
     * @param accountDatabase The list of all bank accounts
     */
    public void requestAccountSummary(ArrayList<Account> accountDatabase, JTextField[] fields, JTextArea outputArea) {

        // Calls the super method
        super.requestAccountDetails(accountDatabase, getUserID(fields, outputArea), "Account summary", fields, outputArea);
    }

    /**
     * Get specified accounts transaction history
     *
     * @param accountDatabase The list of all bank accounts
     */
    public void requestTransactionDetails(ArrayList<Account> accountDatabase, JTextField[] fields, JTextArea outputArea) {

        // Calls the super method
        super.requestAccountDetails(accountDatabase, getUserID(fields, outputArea), "Transaction details", fields, outputArea);
    }

    /**
     * Send money from the specified account to another specified account
     *
     * @param accountDatabase The list of all bank accounts
     */
    public void transferMoney(ArrayList<Account> accountDatabase, JTextField[] fields, JTextArea outputArea) {

        // Calls the super method
        super.transferMoney(accountDatabase, getUserID(fields, outputArea), fields, outputArea);
    }


    /**
     * Returns the userID by requesting for user input
     *
     * @return Returns a userID from the user
     */
    public int getUserID(JTextField[] fields, JTextArea outputArea) {

        try {
            //Get input from the user
            int userID = 0;
            userID = Integer.parseInt(fields[0].getText());

            return userID;

        } catch (Exception ex) {

            outputArea.append("Incorrect input!");
        }

        return 0;
    }



}
