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



    public TransferDriver(ArrayList<Account> accountDatabase, JTextArea outputArea){

        //Output total money of all accounts
        outputArea.append("" + getAccountTotal(accountDatabase));


        //Transfer 1000 times
        for (int i = 0; i < numberOfTransactions; i++) {


            //Get two different accounts
            withdrawAccount = accountDatabase.get(rand.nextInt(accountDatabase.size()));

            do{
                depositAccount = accountDatabase.get(rand.nextInt(accountDatabase.size()));

            }while(withdrawAccount == depositAccount);


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
        outputArea.append("" + getAccountTotal(accountDatabase));
    }



    public double getAccountTotal(ArrayList<Account> accountDatabase){

        double totalMoneyInVault = 0;

        for(Account a : accountDatabase){

            totalMoneyInVault += a.getBalance();
        }

        return totalMoneyInVault;
    }

}
