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
    public void listMyAccounts(ArrayList<Account> accountDatabase, int userID) {

        System.out.println("Your Account(s):\n");

        //Display the users accounts
        for (Account a : accountDatabase) {

            if (a.getUserID() == userID) {

                System.out.println(a.print() + "\n");

            }
        }
    }


    /**
     * Delete the account specified using the userID and an account number
     *
     * @param accountDatabase The list of all bank accounts
     * @param userID          The user id that may be tied to multiple bank accounts
     */
    public void deleteAccount(ArrayList<Account> accountDatabase, int userID) {

        //Get the account number
        System.out.println("Enter the accounts number that you wish to delete: ");

        int accountNumber = 0;

        try {

            accountNumber = Integer.parseInt(consoleInput.nextLine());
        } catch (Exception ex) {

            System.out.println("Incorrect input! Returning to main menu.");
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

                    System.out.println("The account was successfully deleted!");

                    break;

                } else {

                    System.out.println("The account could not be deleted!");
                    System.out.println("It has an outstanding balance of: " + String.format("%,.2f", a.getBalance()));

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
    public void createAccount(ArrayList<Account> accountDatabase, String userId) {

        String name = "";

        int pin = 0;

        String type = "";

        int userIDinput = 0;

        try {

            //Get the data for this account
            System.out.println("Enter a name for the new account: ");
            name = consoleInput.nextLine();

            System.out.println("Enter a new pin for this account: ");
            String pinString = consoleInput.nextLine();
            pin = Integer.parseInt(pinString);

            System.out.println("Enter the account type (Personal, Business, Checking, Saving): ");
            type = consoleInput.nextLine();


            //Get user id if necessary (AdminLogic)
            if (userId.equalsIgnoreCase("")) {

                System.out.println("Enter an existing userID for this account (or 0 to generate a new id): ");
                userIDinput = Integer.parseInt(consoleInput.nextLine());

            } else {

                //Set the userID if it is passed in
                userIDinput = Integer.parseInt(userId);
            }

        } catch (Exception ex) {

            System.out.println("Incorrect input! Returning to main menu.");
        }


        //Get the correct enum value
        Account.AccountType enumType = Account.convertString(type);


        //Attempt to create the account
        if (enumType == null) {

            System.out.println("The correct account type was not found.");
            System.out.println("The account could not be created!");

        } else {

            if (userIDinput == 0) {

                //Generate the userID

                //Create the new account
                Account account = new Account(name, pin, enumType);
                accountDatabase.add(account);

                //Notify the user
                System.out.println("The account has been created!");
                System.out.println("Account info:");
                System.out.println(account.print());

            } else {

                //Do not generate the userID

                //Create the new account
                Account account = new Account(name, pin, enumType, userIDinput);
                accountDatabase.add(account);

                //Notify the user
                System.out.println("The account has been created!");
                System.out.println("Account info:");
                System.out.println(account.print());
            }
        }

    }


    /**
     * Deposit money to the account specified using the userID and an account number
     *
     * @param accountDatabase The list of all bank accounts
     * @param userID          The user id that may be tied to multiple bank accounts
     */
    public void deposit(ArrayList<Account> accountDatabase, int userID) {

        int accountNumber = 0;

        double amount = 0;

        try {

            //Get the users input
            System.out.println("Enter your account number you wish to deposit to: ");
            accountNumber = Integer.parseInt(consoleInput.nextLine());

            System.out.println("Enter the amount you wish to deposit: ");
            amount = Double.parseDouble(consoleInput.nextLine());

        } catch (Exception ex) {

            System.out.println("Incorrect input! Returning to main menu.");
        }

        //Flag if the account is found
        boolean found = false;


        //Find the correct account
        for (Account a : accountDatabase) {

            if (a.getUserID() == userID && a.getAccountNumber() == accountNumber) {

                found = true;

                a.deposit(amount, false);

                System.out.println("The money was successfully deposited!");
                System.out.println("Your new balance is: " + String.format("%,.2f", a.getBalance()));
                break;
            }

        }


        //Tell user the account was not found
        if (!found) {

            System.out.println("The specified account could not be found!");
        }


    }


    /**
     * Withdraw money from the account specified using the userID and an account number
     *
     * @param accountDatabase The list of all bank accounts
     * @param userID          The user id that may be tied to multiple bank accounts
     */
    public void withdraw(ArrayList<Account> accountDatabase, int userID) {

        int accountNumber = 0;

        double amount = 0;

        try {

            //Get the users input
            System.out.println("Enter your account number you wish to withdraw from: ");
            accountNumber = Integer.parseInt(consoleInput.nextLine());

            System.out.println("Enter the amount you wish to withdraw: ");
            amount = Double.parseDouble(consoleInput.nextLine());

        } catch (Exception ex) {

            System.out.println("Incorrect input! Returning to main menu.");
        }


        //Flag if the account is found
        boolean found = false;

        //Find the correct account
        for (Account a : accountDatabase) {

            if (a.getUserID() == userID && a.getAccountNumber() == accountNumber) {

                found = true;

                a.withdraw(amount, false);

                break;

            }

        }


        //Tell user the account was not found
        if (!found) {

            System.out.println("The specified account could not be found!");
        }


    }


    /**
     * Get specified accounts activity using the userID and an account number
     *
     * @param accountDatabase The list of all bank accounts
     * @param userID          The user id that may be tied to multiple bank accounts
     * @param type            The type sets whether transaction details will be shown
     */
    public void requestAccountDetails(ArrayList<Account> accountDatabase, int userID, String type) {

        int accountNumber = 0;

        try {

            //Get input
            System.out.println("Enter the accounts number that you want a summary for: ");
            accountNumber = Integer.parseInt(consoleInput.nextLine());

        } catch (Exception ex) {

            System.out.println("Incorrect input! Returning to main menu.");
        }


        //Flag if the account is found
        boolean found = false;

        //Find the account
        for (Account a : accountDatabase) {

            if (a.getUserID() == userID && a.getAccountNumber() == accountNumber) {

                //Display the account info
                found = true;
                System.out.println(a.print());

                //Show account activity if necessary
                if (type.equalsIgnoreCase("Transaction details")) {

                    a.showActivity();
                }

                break;
            }
        }


        //Tell user the account was not found
        if (!found) {

            System.out.println("The specified account could not be found!");
        }


    }


    /**
     * Send money from the specified account to another specified account
     *
     * @param accountDatabase The list of all bank accounts
     * @param userID          The user id that may be tied to multiple bank accounts
     */
    public void transferMoney(ArrayList<Account> accountDatabase, int userID) {

        int srcNum = 0;

        int dstNum = 0;

        double transAmount = 0;

        try {

            //Get input
            System.out.println("Enter the account you wish to transfer from: ");
            srcNum = Integer.parseInt(consoleInput.nextLine());

            System.out.println("Enter the account you wish to transfer to: ");
            dstNum = Integer.parseInt(consoleInput.nextLine());

            System.out.println("Enter the amount that you would like to transfer: ");
            transAmount = Double.parseDouble(consoleInput.nextLine());

        } catch (Exception ex) {

            System.out.println("Incorrect input! Returning to main menu.");
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

            System.out.println("Could not find the source account!");
            System.out.println("The transfer was canceled!");

            return;
        }

        //See if the dstAccount was found
        if (dstAccount == null) {

            System.out.println("Could not find the destination account!");
            System.out.println("The transfer was canceled!");

            return;
        }


        //See if enough money is in the src account
        if (srcAccount.getBalance() - transAmount < 0) {

            System.out.println("There is not enough money in the src account to transfer!");
            System.out.println("You requested: " + transAmount);
            System.out.println("You only have a balance of: " + String.format("%,.2f", srcAccount.getBalance()));

            return;
        }


        //Transfer the money
        srcAccount.withdraw(transAmount, true);
        dstAccount.deposit(transAmount, true);

        System.out.println("The money was successfully transferred!");


    }


}
