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


    /**
     * Show all accounts tied to userId
     *
     * @param accountDatabase The list of all bank accounts
     * @param userID          The user id that may be tied to multiple bank accounts
     */
    public void listMyAccounts(ArrayList<Account> accountDatabase, int userID, JTextArea outputArea) {

        outputArea.append("\nYour Account(s):\n");


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


        int accountNumber = 0;

        try {

            accountNumber = Integer.parseInt(fields[0].getText());


        } catch (Exception ex) {

            outputArea.append("\nIncorrect input! Returning to main menu.\n");

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

                    outputArea.append("\nThe account was successfully deleted!\n");

                    break;

                } else {

                    outputArea.append("\nThe account could not be deleted!");
                    outputArea.append("\nIt has an outstanding balance of: " + String.format("%,.2f", a.getBalance()));

                    break;
                }
            }

        }


        //Tell user the account was not found
        if (!found) {

            System.out.println("\nThe specified account could not be found!\n");
        }

    }


    /**
     * Create a new account
     *
     * @param accountDatabase The list of all bank accounts
     * @param userId          The user id that may be tied to multiple bank accounts
     */
    public void createAccount(ArrayList<Account> accountDatabase, int userId, JTextField[] fields, JTextArea outputArea) {

        String name = "";

        int pin = 0;

        String type = "";

        try {

            //Get the data for this account
            name = fields[0].getText();
            pin = Integer.parseInt(fields[1].getText());
            type = fields[2].getText();


        } catch (Exception ex) {

            outputArea.append("\nIncorrect input! Returning to main menu.\n");
        }


        //Get the correct enum value
        Account.AccountType enumType = Account.convertString(type);


        //Attempt to create the account
        if (enumType == null) {

            outputArea.append("\nThe correct account type was not found.\n");
            outputArea.append("\nThe account could not be created!\n");

        } else {

            if (userId == 0) {

                //Generate the userID

                //Create the new account
                Account account = new Account(name, pin, enumType);
                accountDatabase.add(account);

                //Notify the user
                outputArea.append("\nThe account has been created!\n");
                outputArea.append("\nAccount:\n");
                outputArea.append(account.print());

            } else {

                //Do not generate the userID

                //Create the new account
                Account account = new Account(name, pin, enumType, userId);
                accountDatabase.add(account);

                //Notify the user
                outputArea.append("\nThe account has been created!\n");
                outputArea.append("\nAccount info:\n");
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

            outputArea.append("\nIncorrect input! Returning to main menu.\n");
        }

        //Flag if the account is found
        boolean found = false;


        //Find the correct account
        for (Account a : accountDatabase) {

            if (a.getUserID() == userID && a.getAccountNumber() == accountNumber) {

                found = true;

                a.deposit(amount, false);

                outputArea.append("\nThe money was successfully deposited!\n");
                outputArea.append("\nYour new balance is: " + String.format("%,.2f", a.getBalance()) + "\n");
                break;
            }

        }


        //Tell user the account was not found
        if (!found) {

            outputArea.append("\nThe specified account could not be found!\n");
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

            outputArea.append("\nIncorrect input! Returning to main menu.\n");
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

            outputArea.append("\nThe specified account could not be found!\n");
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

            outputArea.append("\nIncorrect input! Returning to main menu.\n");
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

            outputArea.append("\nThe specified account could not be found!\n");
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

            outputArea.append("\nIncorrect input! Returning to main menu.\n");
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

            outputArea.append("\nCould not find the source account!\n");
            outputArea.append("\nThe transfer was canceled!\n");

            return;
        }

        //See if the dstAccount was found
        if (dstAccount == null) {

            outputArea.append("\nCould not find the destination account!\n");
            outputArea.append("\nThe transfer was canceled!\n");

            return;
        }


        //See if enough money is in the src account
        if (srcAccount.getBalance() - transAmount < 0) {

            outputArea.append("\nThere is not enough money in the src account to transfer!\n");
            outputArea.append("\nYou requested: " + transAmount + "\n");
            outputArea.append("\nYou only have a balance of: " + String.format("%,.2f", srcAccount.getBalance()) + "\n");

            return;
        }


        //Transfer the money
        srcAccount.withdraw(transAmount, true, outputArea);
        dstAccount.deposit(transAmount, true);

        outputArea.append("\nThe money was successfully transferred!\n");


    }


}
