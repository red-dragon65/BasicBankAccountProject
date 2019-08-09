package ezmoney.clap;

import javax.swing.*;

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
