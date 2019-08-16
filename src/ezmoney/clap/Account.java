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
import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class modifies the database and has access to only the customers accounts.
 */
public class Account implements Serializable {

    /**
     * Unique account number.
     * Old id's are never recycled.
     */
    private static int accountNumberCounter = 9000;

    /**
     * Unique userID number.
     * Old id's are never recycled.
     */
    private static int userIDCounter = 100;


    /**
     * Amount of money in this account
     */
    private double money;

    /**
     * Name of account owner
     */
    private String name;

    /**
     * Pin to access account
     */
    private int pin;

    /**
     * User ID to access account
     */
    private int userID;

    /**
     * Unique account id
     */
    private int accountNumber;

    /**
     * Activity log for this account
     */
    private ArrayList<AccountActivity> activitylist = new ArrayList<>();

    /**
     * Object to access AccountType
     */
    private AccountType accountType;

    /**
     * Enum to keep track of what type of account this is
     */
    enum AccountType {

        // Types of accounts
        PERSONAL("Personal"),
        BUSINESS("Business"),
        CHECKING("Checking"),
        SAVING("Saving");

        // Holds the type of account
        private String type;

        // Constructor
        AccountType(String type) {

            this.type = type;
        }

        /**
         * Displays type of account
         *
         * @return Type of account
         */
        @Override
        public String toString() {

            return type;
        }

    }

    /**
     * Constructor
     *
     * @param name The customer name on the account
     * @param pin The customer pin to access their account
     * @param accountType This account's type
     * @param userID The unique userID tied to the customers account(s)
     */
    public Account(String name, int pin, AccountType accountType, int userID) {

        this.name = name;
        this.pin = pin;
        this.accountType = accountType;
        this.userID = userID;

        // Sets accountNumber for this account
        accountNumber = ++accountNumberCounter;
    }

    /**
     * Constructor
     *
     * @param name The customer name on the account
     * @param pin The customer pin to access their account
     * @param accountType This account's type
     */
    public Account(String name, int pin, AccountType accountType) {

        this.name = name;
        this.pin = pin;
        this.accountType = accountType;

        // Sets accountNumber for this account
        accountNumber = ++accountNumberCounter;

        // Sets userID for this account
        userID = ++userIDCounter;
    }

    /**
     * Get the account number value.
     * Used for saving to a file.
     *
     * @return Returns the account number counter value
     */
    public static int getAccountNumberCounter() {
        return accountNumberCounter;
    }

    /**
     * Gets the UserID counter value.
     * Used for saving to a file.
     *
     * @return Returns the UserID counter value
     */
    public static int getUserIDCounter() {
        return userIDCounter;
    }

    /**
     * Set the accountNumberCounter value.
     *
     * @param counter The saved counter value to be set to the static account counter member.
     */
    public static void setAccountNumberCounter(int counter) {
        accountNumberCounter = counter;
    }

    /**
     * Set the userIDCounter value.
     *
     * @param counter The saved counter value to be set to the static userID counter member.
     */
    public static void setUserIDCounter(int counter) {
        userIDCounter = counter;
    }

    /**
     * Returns the current balance of this account
     *
     * @return Balance of account
     */
    public double getBalance() {
        return money;
    }

    /**
     * Deposits money into account
     *
     * @param moneyAmount Amount of money to be deposited
     * @param transfer Notifies if the account activity is a transfer type
     */
    public synchronized void deposit(double moneyAmount, boolean transfer) {

        // Add specified amount of money to account
        money += moneyAmount;

        // Log account account activity
        if (transfer) {

            AccountActivity activity = new AccountActivity(moneyAmount, accountNumber, AccountActivity.MoveType.TRANSFER);
            activitylist.add(activity);

        } else {

            AccountActivity activity = new AccountActivity(moneyAmount, accountNumber, AccountActivity.MoveType.DEPOSIT);
            activitylist.add(activity);
        }

    }

    /**
     * Withdraws money from account
     *
     * @param moneyAmount Amount of money to be withdrawn
     * @param transfer Notifies if the account activity is a transfer type
     */
    public synchronized boolean withdraw(double moneyAmount, boolean transfer, JTextArea outputArea) {

        //Attempt to withdraw money
        if (money - moneyAmount < 0) {

            //Notify user
            outputArea.append("Cannot withdraw: $" + String.format("%,.2f", moneyAmount) + "!");
            outputArea.append("The balance is too low!: $" + String.format("%,.2f", money));

            return false;

        } else {

            // Removes specified amount of money from account
            money -= moneyAmount;

            // Log account account activity
            if (transfer) {

                AccountActivity activity = new AccountActivity(moneyAmount, accountNumber, AccountActivity.MoveType.TRANSFER);
                activitylist.add(activity);

            } else {

                AccountActivity activity = new AccountActivity(moneyAmount, accountNumber, AccountActivity.MoveType.WITHDRAW);
                activitylist.add(activity);
            }

            //Notify user
            outputArea.append("The money was successfully withdrawn!");
            outputArea.append("Your new balance is: $" + String.format("%,.2f", money));
        }

        return true;
    }

    /**
     * Returns the type of this account
     *
     * @return This account's type
     */
    public AccountType getType() {

        return accountType;
    }

    /**
     * Returns the user's id
     *
     * @return User id
     */
    public int getUserID() {

        return userID;
    }

    /**
     * Returns the user's pin
     *
     * @return User's pin
     */
    public int getPin() {

        return pin;
    }

    /**
     * Returns the account holder's name
     *
     * @return Account holder's name
     */
    public String getHoldersName() {

        return name;
    }

    /**
     * Returns the account number
     *
     * @return Account number
     */
    public int getAccountNumber() {

        return accountNumber;
    }

    /**
     * Displays the activity log for this account
     */
    public void showActivity(JTextArea outputArea) {

        for (AccountActivity activity : activitylist) {

            outputArea.append(activity.toString());
        }
    }

    /**
     * Converts an input string from the console into the correct enum type
     *
     * @param rawType The string that will be converted to an enum
     * @return Returns an enum by using a string value
     */
    public static AccountType convertString(String rawType) {

        if (Account.AccountType.PERSONAL.toString().equalsIgnoreCase(rawType)) {
            return Account.AccountType.PERSONAL;
        }

        if (Account.AccountType.BUSINESS.toString().equalsIgnoreCase(rawType)) {
            return Account.AccountType.BUSINESS;
        }

        if (Account.AccountType.CHECKING.toString().equalsIgnoreCase(rawType)) {
            return Account.AccountType.CHECKING;
        }

        if (Account.AccountType.SAVING.toString().equalsIgnoreCase(rawType)) {
            return Account.AccountType.SAVING;
        }

        return null;
    }

    /**
     * Returns a summary of this account
     *
     * @return Summary of this account
     */
    public String print() {

        String formatMoney = String.format("%,.2f", money);

        return "Name: " + name +
                "\nUser ID: " + userID +
                "\nAccount number: " + accountNumber +
                "\nAccount type: " + accountType +
                "\nMoney: $" + formatMoney +
                "\nPin: " + pin + "\n";
    }


    /**
     * Returns a summary of this account
     *
     * @return Summary of this account
     */
    @Override
    public String toString() {

        return "Name: " + name +
                "\nUser ID: " + userID +
                "\nAccount number: " + accountNumber +
                "\nAccount type: " + accountType +
                "\nMoney: $" + money +
                "\nPin: " + pin +
                "\nAccountCounter: " + accountNumberCounter +
                "\nUIDCounter " + userIDCounter +
                "\nActivity" + activitylist + "\n";
    }

}
