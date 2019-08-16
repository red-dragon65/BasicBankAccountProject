package ezmoney.clap;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * This class sets up the threads to transfer money
 */
public class TransferDriver {

    private ExecutorService es = Executors.newFixedThreadPool(5);

    private Random rand = new Random();

    private Account withdrawAccount, depositAccount;

    private final double testAmount = 1_000;

    private final int numberOfTransactions = 100;

    private double totalBefore;
    private double totalAfter;


    public TransferDriver(ArrayList<Account> accountDatabase, JTextArea outputArea) {

        if (accountDatabase.size() == 0) {

            //Break code if no accounts exist
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


    public double getAccountTotal(ArrayList<Account> accountDatabase) {

        double totalMoneyInVault = 0;

        for (Account a : accountDatabase) {

            totalMoneyInVault += a.getBalance();
        }

        return totalMoneyInVault;
    }

}
