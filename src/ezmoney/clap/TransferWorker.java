package ezmoney.clap;

import javax.swing.*;

/**
 * This class transfer money between two accounts.
 */
public class TransferWorker implements Runnable {

    /**
     * The two accounts that transfer between each other.
     */
    private Account withdrawAccount;
    private Account depositAccount;

    /**
     * The amount of money to move between accounts.
     */
    private double amount;

    /**
     * The output JPanel field for displaying results.
     */
    private JTextArea outputArea;


    /**
     * The default constructor that initializes variables used in the run method.
     *
     * @param withdrawAccount The source account for transfer.
     * @param depositAccount  The destination account for transfer.
     * @param amount          The amount of money to transfer.
     * @param outputArea      The output JPanel field for displaying results.
     */
    public TransferWorker(Account withdrawAccount, Account depositAccount, double amount, JTextArea outputArea) {

        //Set the variables
        this.withdrawAccount = withdrawAccount;
        this.depositAccount = depositAccount;
        this.amount = amount;
        this.outputArea = outputArea;
    }


    /**
     * Attempts to move money between two accounts.
     * Uses thread safe code found in the 'Account' class.
     */
    @Override
    public void run() {

        //If the withdrawal is possible, transfer the money
        if (withdrawAccount.withdraw(amount, true, outputArea)) {

            depositAccount.deposit(amount, true);
        }

    }
}
