package ezmoney.clap;

import javax.swing.*;

public class CustomerUI {


    /**
     * Sets up UI so user can delete account
     * @param fields
     * @param labels
     */
    public void deleteAccount(JTextField[] fields, JLabel[] labels) {

        fields[0].setVisible(true);

        labels[0].setText("Enter the accounts number that you wist to delete:");

    }

    /**
     * Sets up UI so user can create account
     * @param fields
     * @param labels
     */
    public void createAccount(JTextField[] fields, JLabel[] labels) {

        fields[0].setVisible(true);
        fields[1].setVisible(true);
        fields[2].setVisible(true);

        labels[0].setText("Enter a name for the new account:");
        labels[1].setText("Enter a new pin for this account:");
        labels[2].setText("Enter the account type (Personal, Business, Checking, Saving):");
    }

    /**
     * Sets up UI so user can deposit money
     * @param fields
     * @param labels
     */
    public void deposit(JTextField[] fields, JLabel[] labels) {

        fields[0].setVisible(true);
        fields[1].setVisible(true);

        labels[0].setText("Enter your account number you wish to deposit to:");
        labels[1].setText("Enter the amount you wish to deposit:");
    }

    /**
     * Sets up UI so user can withdraw money
     * @param fields
     * @param labels
     */
    public void withdraw(JTextField[] fields, JLabel[] labels) {

        fields[0].setVisible(true);
        fields[1].setVisible(true);

        labels[0].setText("Enter your account number you wish to withdraw from:");
        labels[1].setText("Enter the amount you wish to withdraw:");
    }

    /**
     * Sets up UI so user can request account details
     * @param fields
     * @param labels
     */
    public void requestAccountDetails(JTextField[] fields, JLabel[] labels) {

        fields[0].setVisible(true);

        labels[0].setText("Enter the accounts number that you want a summary for:");
    }

    /**
     * Sets up UI so user can transfer money to another account
     * @param fields
     * @param labels
     */
    public void transferMoney(JTextField[] fields, JLabel[] labels) {

        fields[0].setVisible(true);
        fields[1].setVisible(true);
        fields[2].setVisible(true);

        labels[0].setText("Enter the account you wish to transfer from:");
        labels[1].setText("Enter the account you wish to transfer to:");
        labels[2].setText("Enter the amount that you would like to transfer:");
    }
}
