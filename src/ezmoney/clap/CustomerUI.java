package ezmoney.clap;

import javax.swing.*;

public class CustomerUI {



    //TODO: Put comments
    public void deleteAccount(JTextField[] fields, JLabel[] labels) {

        fields[0].setVisible(true);

        labels[0].setText("Enter the accounts number that you wist to delete:");

    }

    //TODO: Put comments
    public void createAccount(JTextField[] fields, JLabel[] labels) {

        fields[0].setVisible(true);
        fields[1].setVisible(true);
        fields[2].setVisible(true);

        labels[0].setText("Enter a name for the new account:");
        labels[1].setText("Enter a new pin for this account:");
        labels[2].setText("Enter the account type (Personal, Business, Checking, Saving):");
    }

    //TODO: Put comments
    public void deposit(JTextField[] fields, JLabel[] labels) {

        fields[0].setVisible(true);
        fields[1].setVisible(true);

        labels[0].setText("Enter your account number you wish to deposit to:");
        labels[1].setText("Enter the amount you wish to deposit:");
    }

    //TODO: Put comments
    public void withdraw(JTextField[] fields, Jlabel[] labels) {

        fields[0].setVisible(true);
        fields[1].setVisible(true);

        labels.setText("Enter your account number you wish to withdraw from:");
        labels.setText("Enter the amount you wish to withdraw:");
    }

    //TODO: Put comments
    public void requestAccountDetails(JTextField[] fields, Jlabel[] labels) {

        fields[0].setVisible(true);

        labels[0].setText("Enter the accounts number that you want a summary for:");
    }

    //TODO: Put comments
    public void transferMoney(JTextField[] fields, Jlabel[] labels) {

        fields[0].setVisible(true);
        fields[1].setVisible(true);
        fields[2].setVisible(true);

        labels[0].setText("Enter the account you wish to transfer from:");
        labels[1].setText("Enter the account you wish to transfer to:");
        labels[2].setText("Enter the amount that you would like to transfer:");
    }
}
