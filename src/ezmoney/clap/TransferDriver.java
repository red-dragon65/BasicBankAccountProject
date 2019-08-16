package ezmoney.clap;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * This class sets up the threads to transfer money.
 */
public class TransferDriver {

    /**
     * The max number of threads to create.
     */
    private ExecutorService es = Executors.newFixedThreadPool(5);

    /**
     * Used to find accounts to transfer between.
     */
    private Random rand = new Random();

    /**
     * The accounts that will transfer money between them.
     */
    private Account withdrawAccount, depositAccount;

    /**
     * The number of money to move between accounts.
     */
    private final double testAmount = 1_000;

    /**
     * The number of times money should be transferred.
     */
    private final int numberOfTransactions = 100;

    /**
     * The amount of money in all accounts before and after the test.
     */
    private double totalBefore;
    private double totalAfter;


    /**
     * The default constructor that begins the money transfer test.
     *
     * @param accountDatabase The list of all user accounts.
     * @param outputArea      The output JPanel field for displaying results.
     */
    public TransferDriver(ArrayList<Account> accountDatabase, JTextArea outputArea) {

        //Break code if no accounts exist
        if (accountDatabase.size() == 0) {

            outputArea.append("\nWarning: No accounts exist for this calculation!\n");
            return;
        }

        //Output total money of all accounts
        totalBefore = getAccountTotal(accountDatabase);
        outputArea.append("\nTotal money before test: " + totalBefore);


        //Transfer 1000 times
        for (int i = 0; i < numberOfTransactions; i++) {

            int index = rand.nextInt(accountDatabase.size());

            //Get two different accounts
            withdrawAccount = accountDatabase.get(index);

            do {
                index = rand.nextInt(accountDatabase.size());
                depositAccount = accountDatabase.get(index);

            } while (withdrawAccount == depositAccount);


            //Send the accounts for transaction
            TransferWorker worker = new TransferWorker(withdrawAccount, depositAccount, testAmount, outputArea);
            es.submit(worker);
        }


        try {
            //Attempt to shutdown
            es.shutdown();

            //Timeout length
            es.awaitTermination(60, TimeUnit.SECONDS);

        } catch (InterruptedException e) {

            e.printStackTrace();
        }


        //Output total money of all accounts
        totalAfter = getAccountTotal(accountDatabase);
        outputArea.append("\n Total money after test: " + totalAfter);


        //Do total comparison
        if (totalBefore == totalAfter) {
            outputArea.append("\n\nThe total money before: " + totalBefore);
            outputArea.append("\nThe total money after: " + totalAfter);
            outputArea.append("\nThe test was successful!");
        } else {

            outputArea.append("\n\nThe total money before: " + totalBefore);
            outputArea.append("\nThe total money after: " + totalAfter);
            outputArea.append("\nThe test FAILED!");
        }
    }


    /**
     * Sums the total amount of money in all accounts.
     *
     * @param accountDatabase The list of all user accounts.
     * @return Returns the total amount of money in all accounts.
     */
    private double getAccountTotal(ArrayList<Account> accountDatabase) {

        //Hold total amount of money
        double totalMoneyInVault = 0;

        //Add up money
        for (Account a : accountDatabase) {

            totalMoneyInVault += a.getBalance();
        }

        //Return money total
        return totalMoneyInVault;
    }

}
