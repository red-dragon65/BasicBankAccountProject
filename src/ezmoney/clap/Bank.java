package ezmoney.clap;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.exit;

public class Bank {

    //Hold all the accounts in the bank
    private ArrayList<Account> accountDatabase = new ArrayList<Account>();

    //Hold input file
    private InputStream inputFile;


    //Hold user data
    private boolean login = false;
    private String userId = "";
    private int userIdParsed = 0;
    private int pinParsed = 0;
    private String selection = "";
    private int selectionParsed = 0;

    private String userType = "";
    private int defaultAdminAccount = 1000;
    private int defaultAdminPassword = 1234;

    //Get input
    private Scanner consoleInput = new Scanner(System.in);

    //Hold the database manipulators
    private CustomerLogic customerLogic = new CustomerLogic();
    private AdminLogic adminLogic = new AdminLogic();
    private CustomerUI customerUI = new CustomerUI();
    private AdminUI adminUI = new AdminUI();


    public Bank() {

        //Deserialize file (load accountDatabase)
        try {

            //Get the account counter data
            File file = new File("./data/AccountCounter.txt");
            Scanner in = new Scanner(file);

            String tempAccountCounter = in.nextLine();
            String tempUserCounter = in.nextLine();

            Account.setAccountNumberCounter(Integer.parseInt(tempAccountCounter));
            Account.setUserIDCounter(Integer.parseInt(tempUserCounter));
            in.close();


            //Get the account database data
            FileInputStream fis = new FileInputStream("./data/AccountData");
            ObjectInputStream ois = new ObjectInputStream(fis);

            accountDatabase = (ArrayList<Account>) ois.readObject();

            ois.close();
            fis.close();

        } catch (IOException ioe) {

            //ioe.printStackTrace();
            System.out.println("Error: The account database file could not be found! It will be created on exit!\n");

        } catch (ClassNotFoundException c) {

            System.out.println("Class not found");
            c.printStackTrace();

        }


        //TODO: Use this test code to view all account data from the file.
        //Verify list data
        for (Account a : accountDatabase) {
            System.out.println(a);
        }

    }

    public void login(JTextField[] fields, JTextArea outputArea) {


        if (fields[0].getText().equalsIgnoreCase("admin")) {

            //Attempt to get input
            try {

                //Get account number
                userIdParsed = Integer.parseInt(fields[1].getText());

                //Get pin
                pinParsed = Integer.parseInt(fields[2].getText());

            } catch (Exception e) {

                //Get input again if input could not be validated
                outputArea.append("\nIncorrect input for account or pin!");
            }


            //The password matches
            if (pinParsed == defaultAdminPassword && userIdParsed == defaultAdminAccount) {

                //Break this loop
                outputArea.append("\nLogging in... Welcome!\n");
                login = true;
                userType = "admin";

            } else {

                outputArea.append("\nInvalid credentials!");
            }


        } else if (fields[0].getText().equalsIgnoreCase("customer")) {


            //Attempt to get input
            try {

                //Parse input
                userIdParsed = Integer.parseInt(fields[1].getText());

                //Parse input
                pinParsed = Integer.parseInt(fields[2].getText());


            } catch (Exception e) {

                //Get input again if input could not be validated
                outputArea.append("\nIncorrect input for account or pin!");
            }


            //See if account exists
            //Loop through all accounts
            for (Account a : accountDatabase) {

                //See if name matches
                if (a.getUserID() == userIdParsed) {

                    //Found an account with the same name!

                    //See if pin matches
                    if (a.getPin() == pinParsed) {

                        //Notify account has been found
                        login = true;
                        outputArea.append("\nLogging in... Welcome!\n");
                        userType = "customer";
                        break;
                    }

                }
            }


            //Break loop if the account was found
            if (!login) {

                //Tell user about login attempt
                outputArea.append("\nThe account was not found!");
            }


        } else {

            outputArea.append("\nThe user type was incorrect!");
        }
    }


    public boolean isLoggedIn() {
        return login;
    }


    public void showSelectionList(JTextArea outputArea) {


        if (userType.equalsIgnoreCase("admin")) {


            //Show selection
            outputArea.append("\nEnter a selection:");
            outputArea.append("\n1. List all accounts");
            outputArea.append("\n2. List Accounts using user id");
            outputArea.append("\n3. List Accounts using username");
            outputArea.append("\n4. Delete Account");
            outputArea.append("\n5. Create Account");
            outputArea.append("\n6. Deposit Money into an account");
            outputArea.append("\n7. Withdraw money from an account");
            outputArea.append("\n8. Request an account summary");
            outputArea.append("\n9. Request account transaction details");
            outputArea.append("\n10. Transfer money between accounts");
            outputArea.append("\n11. Run multi-thread transfer test");



        } else if (userType.equalsIgnoreCase("customer")) {

            //Show selection
            outputArea.append("\nEnter a selection:");
            outputArea.append("\n1. List my Accounts");
            outputArea.append("\n2. Delete my Account");
            outputArea.append("\n3. Create a new Account");
            outputArea.append("\n4. Deposit Money into my account");
            outputArea.append("\n5. Withdraw money from my account");
            outputArea.append("\n6. Request an account summary");
            outputArea.append("\n7. Request my account transaction details");
            outputArea.append("\n8. Transfer money to another account");


        }
    }


    public void calculateInput(boolean selection, JTextField[] fields, JLabel[] labels, JTextArea outputArea) {

        if (userType.equalsIgnoreCase("admin")) {

            runAdmin(selection, fields, labels, outputArea);

        } else if (userType.equalsIgnoreCase("customer")) {

            runCustomer(selection, fields, labels, outputArea);
        }

    }


    public void setSelected(JTextField field, JTextArea outputArea) {

        try {

            selectionParsed = Integer.parseInt(field.getText());

        } catch (Exception e) {

            outputArea.append("\nInvalid input detected!");
        }
    }


    public void setLoginUI(JTextField[] fields, JLabel[] labels) {

        labels[0].setText("Enter user type: ");
        fields[0].setVisible(true);

        labels[1].setText("Enter user ID: ");
        fields[1].setVisible(true);

        labels[2].setText("Enter pin: ");
        fields[2].setVisible(true);


    }


    public void runAdmin(boolean selection, JTextField[] fields, JLabel[] labels, JTextArea outputArea) {

        //Run AdminLogic code
        switch (selectionParsed) {

            case 1:

                //List all accounts in the bank
                if (!selection)
                    adminLogic.listAllAccounts(accountDatabase, fields, outputArea);
                else
                    adminUI.listAllAccounts(fields, labels, outputArea);

                break;
            case 2:

                //This lists certain accounts based on user id
                if (!selection)
                    adminLogic.listAccountsUserID(accountDatabase, fields, outputArea);
                else
                    adminUI.listAccountsUserID(fields, labels, outputArea);

                break;
            case 3:

                //This lists certain accounts based on user name
                if (!selection)
                    adminLogic.listAccountsUsername(accountDatabase, fields, outputArea);
                else
                    adminUI.listAccountsUsername(fields, labels, outputArea);

                break;
            case 4:

                //Delete the specified account
                if (!selection)
                    adminLogic.deleteAccount(accountDatabase, fields, outputArea);
                else
                    adminUI.deleteAccount(fields, labels, outputArea);

                break;
            case 5:

                //Create a new account
                if (!selection)
                    adminLogic.createAccount(accountDatabase, "", fields, outputArea);
                else
                    adminUI.createAccount(fields, labels, outputArea);

                break;
            case 6:

                //Deposit money into the specified account
                if (!selection)
                    adminLogic.deposit(accountDatabase, fields, outputArea);
                else
                    adminUI.deposit(fields, labels, outputArea);

                break;
            case 7:

                //Withdraw money from the specified account
                if (!selection)
                    adminLogic.withdraw(accountDatabase, fields, outputArea);
                else
                    adminUI.withdraw(fields, labels, outputArea);

                break;
            case 8:

                //Get info from specified account
                if (!selection)
                    adminLogic.requestAccountSummary(accountDatabase, fields, outputArea);
                else
                    adminUI.requestAccountSummary(fields, labels, outputArea);

                break;
            case 9:

                //Get transaction history from specified account
                if (!selection)
                    adminLogic.requestTransactionDetails(accountDatabase, fields, outputArea);
                else
                    adminUI.requestTransactionDetails(fields, labels, outputArea);

                break;
            case 10:

                //Send money from a specified account to another specified account
                if (!selection)
                    adminLogic.transferMoney(accountDatabase, fields, outputArea);
                else
                    adminUI.transferMoney(fields, labels, outputArea);

                break;

            case 11:

                //Run the multi-thread money transfer test
                TransferDriver multithreadDriver = new TransferDriver(accountDatabase, outputArea);


                break;

            default:
                outputArea.append("The entered selection was not in the list!");
        }


    }

    public void runCustomer(boolean selection, JTextField[] fields, JLabel[] labels, JTextArea outputArea) {

        //Run customerLogic code
        switch (selectionParsed) {


            case 1:

                //Show accounts tied to userId
                if (!selection)
                    customerLogic.listMyAccounts(accountDatabase, userIdParsed, fields, outputArea);
                else
                    customerUI.listMyAccounts(fields, labels, outputArea);

                break;
            case 2:

                //Delete account specified
                if (!selection)
                    customerLogic.deleteAccount(accountDatabase, userIdParsed, fields, outputArea);
                else
                    customerUI.deleteAccount(fields, labels);

                break;
            case 3:

                //Create a new account
                if (!selection)
                    customerLogic.createAccount(accountDatabase, userId, fields, outputArea);
                else
                    customerUI.createAccount(fields, labels, outputArea);

                break;
            case 4:

                //Deposit money to the account specified
                if (!selection)
                    customerLogic.deposit(accountDatabase, userIdParsed, fields, outputArea);
                else
                    customerUI.deposit(fields, labels, outputArea);

                break;
            case 5:

                //Withdraw money from the account specified
                if (!selection)
                    customerLogic.withdraw(accountDatabase, userIdParsed, fields, outputArea);
                else
                    customerUI.withdraw(fields, labels, outputArea);

                break;
            case 6:

                //Get specified accounts activity
                if (!selection)
                    customerLogic.requestAccountDetails(accountDatabase, userIdParsed, "Account summary", fields, outputArea);
                else
                    customerUI.requestAccountDetails(fields, labels, outputArea);

                break;
            case 7:

                //Get specified accounts transaction history
                if (!selection)
                    customerLogic.requestAccountDetails(accountDatabase, userIdParsed, "Transaction details", fields, outputArea);
                else
                    customerUI.requestAccountDetails(fields, labels, outputArea, fields, outputArea);

                break;
            case 8:

                //Send money from the specified account to another specified account
                if (!selection)
                    customerLogic.transferMoney(accountDatabase, userIdParsed, fields, outputArea);
                else
                    customerUI.transferMoney(fields, labels, outputArea);

                break;

            default:
                outputArea.append("The entered selection was not in the list!");

        }


    }


    //TODO: Make this code run when the user clicks the exit button

    /**
     * Ends the program and saves the database values.
     *
     * @param value           Checks to see if input is 'exit'
     * @param accountDatabase The ArrayList holding all customer accounts
     */
    private static void endProgram(String value, ArrayList<Account> accountDatabase) {

        if (value.equalsIgnoreCase("exit")) {


            //Serialize to file (save accountDatabase)
            try {

                //Write the account counter data
                Writer wr = new FileWriter("./data/AccountCounter.txt");

                int tempAccountNumber = Account.getAccountNumberCounter();
                int tempUserCounter = Account.getUserIDCounter();

                wr.write(tempAccountNumber + "\n");
                wr.write(tempUserCounter + "");

                wr.close();


                //Write the account database values
                FileOutputStream fos = new FileOutputStream("./data/AccountData");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(accountDatabase);
                oos.close();
                fos.close();

            } catch (IOException ioe) {

                ioe.printStackTrace();
                //TODO: show as dialogue box
                System.out.println("Something went wrong while saving the data!!!");

            }


            //TODO: show as dialogue box?
            System.out.println("\nThank you for your business!");
            System.out.println("Please come again!");
            exit(0);
        }

    }


}
