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
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class modifies the arraylist
 */
public class CustomerLogic {


    private Scanner consoleInput = new Scanner(System.in);


    /**
     * Show all accounts tied to userId
     *
     * @param accountDatabase The list of all bank accounts
     * @param userID          The user id that may be tied to multiple bank accounts
     */
    public void listMyAccounts(ArrayList<Account> accountDatabase, int userID, JTextArea outputArea) {

        //TODO: Update to output to form instead of console
//        System.out.println("Your Account(s):\n");
        outputArea.append("Your Account(s):\n");


        //Display the users accounts
        for (Account a : accountDatabase) {

            if (a.getUserID() == userID) {

                //System.out.println(a.print() + "\n");
                outputArea.append(a.print() + "\n");
            }
        }
    }


    /**
     * Delete the account specified using the userID and an account number
     *
     * @param accountDatabase The list of all bank accounts
     * @param userID          The user id that may be tied to multiple bank accounts
     */
    public void deleteAccount(ArrayList<Account> accountDatabase, int userID, JTextField[] fields, JTextArea outputArea) {

        //TODO: this moves to the UI class
        //Get the account number
        //System.out.println("Enter the accounts number that you wish to delete: ");

        int accountNumber = 0;

        try {

            //TODO: this gets data from the JTextFields
            //accountNumber = Integer.parseInt(consoleInput.nextLine());
            accountNumber = Integer.parseInt(fields[0].getText());


        } catch (Exception ex) {

            //TODO: this outputs to the form
            //System.out.println();
            outputArea.append("Incorrect input! Returning to main menu.");

        }


        //Flag if the account is found
        boolean found = false;


        //Delete the account if it is found
        for (Account a : accountDatabase) {

            if (a.getUserID() == userID && a.getAccountNumber() == accountNumber) {

                found = true;

                //Attempt to safely delete account
                if (a.getBalance() == 0.0) {

                    accountDatabase.remove(a);

                    //TODO: this outputs to the form
                    //System.out.println("The account was successfully deleted!");
                    outputArea.append("The account was successfully deleted!");

                    break;

                } else {

                    //TODO: this outputs to the form
                    //System.out.println("The account could not be deleted!");
                    //System.out.println("It has an outstanding balance of: " + String.format("%,.2f", a.getBalance()));
                    outputArea.append("The account could not be deleted!");
                    outputArea.append("It has an outstanding balance of: " + String.format("%,.2f", a.getBalance()));

                    break;
                }
            }

        }


        //Tell user the account was not found
        if (!found) {

            System.out.println("The specified account could not be found!");
        }

    }


    /**
     * Create a new account
     *
     * @param accountDatabase The list of all bank accounts
     * @param userId          The user id that may be tied to multiple bank accounts
     */
    public void createAccount(ArrayList<Account> accountDatabase, String userId, JTextField[] fields, JTextArea outputArea) {

        String name = "";

        int pin = 0;

        String type = "";

        int userIDinput = 0;

        try {

            //Get the data for this account
            name = fields[0].getText();
            pin = Integer.parseInt(fields[1].getText());
            type = fields[2].getText();

            //TODO: Figure out how to handle admin user creation
            //Get user id if necessary (AdminLogic)
            if (userId.equalsIgnoreCase("")) {

                System.out.println("Enter an existing userID for this account (or 0 to generate a new id): ");
                userIDinput = Integer.parseInt(consoleInput.nextLine());

            } else {

                //Set the userID if it is passed in
                userIDinput = Integer.parseInt(userId);
            }

        } catch (Exception ex) {

            outputArea.append("Incorrect input! Returning to main menu.\n");
        }


        //Get the correct enum value
        Account.AccountType enumType = Account.convertString(type);


        //Attempt to create the account
        if (enumType == null) {

            outputArea.append("The correct account type was not found.\n");
            outputArea.append("The account could not be created!\n");

        } else {

            if (userIDinput == 0) {

                //Generate the userID

                //Create the new account
                Account account = new Account(name, pin, enumType);
                accountDatabase.add(account);

                //Notify the user
                outputArea.append("The account has been created!\n");
                outputArea.append("Account:\n");
                outputArea.append(account.print());

            } else {

                //Do not generate the userID

                //Create the new account
                Account account = new Account(name, pin, enumType, userIDinput);
                accountDatabase.add(account);

                //Notify the user
                outputArea.append("The account has been created!\n");
                outputArea.append("Account info:\n");
                outputArea.append(account.print());
            }
        }

    }


    /**
     * Deposit money to the account specified using the userID and an account number
     *
     * @param accountDatabase The list of all bank accounts
     * @param userID          The user id that may be tied to multiple bank accounts
     */
    public void deposit(ArrayList<Account> accountDatabase, int userID, JTextField[] fields, JTextArea outputArea) {

        int accountNumber = 0;

        double amount = 0;

        try {

            accountNumber = Integer.parseInt(fields[0].getText());
            amount = Double.parseDouble(fields[1].getText());

        } catch (Exception ex) {

            outputArea.append("Incorrect input! Returning to main menu.\n");
        }

        //Flag if the account is found
        boolean found = false;


        //Find the correct account
        for (Account a : accountDatabase) {

            if (a.getUserID() == userID && a.getAccountNumber() == accountNumber) {

                found = true;

                a.deposit(amount, false);

                outputArea.append("The money was successfully deposited!\n");
                outputArea.append("Your new balance is: " + String.format("%,.2f", a.getBalance()) + "\n");
                break;
            }

        }


        //Tell user the account was not found
        if (!found) {

            outputArea.append("The specified account could not be found!");
        }
    }


    /**
     * Withdraw money from the account specified using the userID and an account number
     *
     * @param accountDatabase The list of all bank accounts
     * @param userID          The user id that may be tied to multiple bank accounts
     */
    public void withdraw(ArrayList<Account> accountDatabase, int userID, JTextField[] fields, JTextArea outputArea) {

        int accountNumber = 0;

        double amount = 0;

        try {

            //Get the users input
            accountNumber = Integer.parseInt(fields[0].getText());
            amount = Double.parseDouble(fields[1].getText());

        } catch (Exception ex) {

            outputArea.append("Incorrect input! Returning to main menu.\n");
        }


        //Flag if the account is found
        boolean found = false;

        //Find the correct account
        for (Account a : accountDatabase) {

            if (a.getUserID() == userID && a.getAccountNumber() == accountNumber) {

                found = true;

                a.withdraw(amount, false, outputArea);

                break;

            }

        }


        //Tell user the account was not found
        if (!found) {

            outputArea.append("The specified account could not be found!\n");
        }


    }


    /**
     * Get specified accounts activity using the userID and an account number
     *
     * @param accountDatabase The list of all bank accounts
     * @param userID          The user id that may be tied to multiple bank accounts
     * @param type            The type sets whether transaction details will be shown
     */
    public void requestAccountDetails(ArrayList<Account> accountDatabase, int userID, String type, JTextField[] fields, JTextArea outputArea) {

        int accountNumber = 0;

        try {

            //Get input
            accountNumber = Integer.parseInt(fields[0].getText());

        } catch (Exception ex) {

            outputArea.append("Incorrect input! Returning to main menu.\n");
        }


        //Flag if the account is found
        boolean found = false;

        //Find the account
        for (Account a : accountDatabase) {

            if (a.getUserID() == userID && a.getAccountNumber() == accountNumber) {

                //Display the account info
                found = true;
                outputArea.append(a.print() + "\n");

                //Show account activity if necessary
                if (type.equalsIgnoreCase("Transaction details")) {

                    a.showActivity(outputArea);
                }

                break;
            }
        }


        //Tell user the account was not found
        if (!found) {

            outputArea.append("The specified account could not be found!");
        }


    }


    /**
     * Send money from the specified account to another specified account
     *
     * @param accountDatabase The list of all bank accounts
     * @param userID          The user id that may be tied to multiple bank accounts
     */
    public void transferMoney(ArrayList<Account> accountDatabase, int userID, JTextField[] fields, JTextArea outputArea) {

        int srcNum = 0;

        int dstNum = 0;

        double transAmount = 0;

        try {

            //Get input
            srcNum = Integer.parseInt(fields[0].getText());
            dstNum = Integer.parseInt(fields[1].getText());
            transAmount = Double.parseDouble(fields[2].getText());

        } catch (Exception ex) {

            outputArea.append("Incorrect input! Returning to main menu.\n");
        }


        //Hold the transfer accounts
        Account srcAccount = null;
        Account dstAccount = null;

        //Attempt to get the two accounts
        for (Account a : accountDatabase) {

            //Get source account
            if (a.getUserID() == userID && a.getAccountNumber() == srcNum) {

                srcAccount = a;
            }

            //Get destination account
            if (a.getAccountNumber() == dstNum) {

                dstAccount = a;
            }

        }


        //See if the srcAccount was found
        if (srcAccount == null) {

            outputArea.append("Could not find the source account!\n");
            outputArea.append("The transfer was canceled!\n");

            return;
        }

        //See if the dstAccount was found
        if (dstAccount == null) {

            outputArea.append("Could not find the destination account!\n");
            outputArea.append("The transfer was canceled!\n");

            return;
        }


        //See if enough money is in the src account
        if (srcAccount.getBalance() - transAmount < 0) {

            outputArea.append("There is not enough money in the src account to transfer!\n");
            outputArea.append("You requested: " + transAmount + "\n");
            outputArea.append("You only have a balance of: " + String.format("%,.2f", srcAccount.getBalance()) + "\n");

            return;
        }


        //Transfer the money
        srcAccount.withdraw(transAmount, true, outputArea);
        dstAccount.deposit(transAmount, true);

        outputArea.append("The money was successfully transferred!");


    }


}
