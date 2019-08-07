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

import java.io.Serializable;
import java.util.Date;

/**
 * This class keeps track of transaction history for accounts. Accounts hold an arrayList of AccountActivity objects.
 */
public class AccountActivity implements Serializable {

    /**
     * Date when this account was last accessed
     */
    private Date dateAccessed;

    /**
     * Amount of money being transferred
     */
    private double amountMoved;

    /**
     * Account number for transaction
     */
    private int transferredAccountNumber;

    /**
     * Type of account activity
     */
    private MoveType moveType;

    enum MoveType {

        // Account types
        WITHDRAW("Withdraw"),
        DEPOSIT("Deposit"),
        TRANSFER("Transfer");

        private String type;

        /**
         * Constructor
         *
         * @param type The way the money was moved (withdraw, deposit, or transfer)
         */
        MoveType(String type) {

            this.type = type;
        }

        /**
         * Displays the type of transaction
         *
         * @return Type of transaction
         */
        @Override
        public String toString() {

            return type;
        }
    }

    /**
     * Constructor
     *
     * @param amountMoved              How much money was transferred
     * @param transferredAccountNumber Account number for transfer
     * @param moveType                 Type of transaction
     */
    public AccountActivity(double amountMoved, int transferredAccountNumber, MoveType moveType) {

        dateAccessed = new Date();
        this.amountMoved = amountMoved;
        this.transferredAccountNumber = transferredAccountNumber;
        this.moveType = moveType;
    }

    /**
     * Displays activity transcript
     *
     * @return Activity transcript
     */
    @Override
    public String toString() {

        return "Date accessed: " + dateAccessed.toString() +
                "\nType of transaction: " + moveType.toString() +
                "\nAmount moved: " + String.format("%,.2f", amountMoved) +
                "\nAccount number: " + transferredAccountNumber + "\n";
    }
}
