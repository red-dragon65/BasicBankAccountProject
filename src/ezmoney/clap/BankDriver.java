package ezmoney.clap;

import javax.swing.*;

/**
 * This class creates the GUI interface.
 */
public class BankDriver {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                new BankFrame();

            }
        });

    }
}
