package ezmoney.clap;

import javax.swing.*;

/**
 * This class transfer money between two accounts
 */
public class TransferWorker implements Runnable {

    private Account withdrawAccount;
    private Account depositAccount;
    private double amount;
    private JTextArea outputArea;


    public TransferWorker(Account withdrawAccount, Account depositAccount, double amount, JTextArea outputArea){

        this.withdrawAccount = withdrawAccount;
        this.depositAccount = depositAccount;
        this.amount = amount;
        this.outputArea = outputArea;
    }


    @Override
    public void run(){

        //If the withdrawal is possible, transfer the money
        if(withdrawAccount.withdraw(amount, true, outputArea)){

            depositAccount.deposit(amount, true);
        }

    }
}
