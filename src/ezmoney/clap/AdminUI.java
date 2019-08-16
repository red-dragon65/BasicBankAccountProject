package ezmoney.clap;

import javax.swing.*;

public class AdminUI extends CustomerUI{

    /**
     * Sets the UI up for listing all accounts by user id
     * @param fields
     * @param labels
     */
    public void listAccountsUserID(JTextField[] fields, JLabel[] labels) {

        fields[0].setVisible(true);

        labels[0].setText("Enter user id to view all accounts associated with it:");
    }

    /**
     * Sets the UI up for listing all accounts specified by username
     * @param fields
     * @param labels
     */
    public void listAccountsUsername(JTextField[] fields, JLabel[] labels) {

        fields[0].setVisible(true);

        labels[0].setText("Enter user name to view all accounts associated with it:");
    }

    /**
     * Gets the user id
     * @param fields
     * @param labels
     */
    public void getUserID(JTextField[] fields, JLabel[] labels) {

        fields[3].setVisible(true);

        labels[3].setText("Enter the userID for the account you want:");
    }
}
