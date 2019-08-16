package ezmoney.clap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The main frame that holds the input and output panels.
 * In addition, the frame sends data between the panel and bank logic.
 */
public class BankFrame extends JFrame {

    /**
     * The panels for input and output.
     */
    private InputPanel inputPanel;
    private OutputPanel outputPanel;

    /**
     * The 'Bank' class that runs all logic.
     */
    private Bank bank;

    /**
     * Checks to see if logic should run, or if the UI should update
     */
    private boolean selection = true;

    /**
     * Allows changes to be saved to the database upon exit.
     */
    private JButton exitButton;


    /**
     * The constructor that sets the output and input JPanel.
     * In addition, it sets the listener action for buttons.
     */
    public BankFrame() {

        //Set title
        super("Bank Account System");

        //Create the bank
        bank = new Bank();

        //Set panel layout
        setLayout(new BorderLayout());

        //Set frame values
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        //Create the panels
        inputPanel = new InputPanel();
        outputPanel = new OutputPanel();

        //Set the button listener
        inputPanel.setListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!bank.isLoggedIn()) {

                    //Login using field input data
                    bank.login(inputPanel.getFields(), outputPanel.getOutputArea());

                    //If the user logs in, update the UI for selections
                    if (bank.isLoggedIn()) {

                        inputPanel.selectionUI();
                        bank.showSelectionList(outputPanel.getOutputArea());
                    }

                } else {

                    //Pass the components to the correct method
                    if (selection) { //---- UI update code

                        //Get the selection
                        bank.setSelected(inputPanel.getFields()[0], outputPanel.getOutputArea());

                        //Update UI
                        inputPanel.clearUI();

                        //Refresh the UI to display the selection input if the bank logic does not require any further input
                        if (bank.calculateInput(selection, inputPanel.getFields(), inputPanel.getLabels(), outputPanel.getOutputArea())) {
                            inputPanel.selectionUI();
                            bank.showSelectionList(outputPanel.getOutputArea());
                        }

                        //Update the JPanel
                        inputPanel.refreshUI();

                        //Switch to logic mode
                        selection = false;

                    } else { //---- Logic update code

                        //Run the logic
                        bank.calculateInput(selection, inputPanel.getFields(), inputPanel.getLabels(), outputPanel.getOutputArea());

                        //Show the selection and update the JPanels
                        inputPanel.selectionUI();
                        bank.showSelectionList(outputPanel.getOutputArea());

                        //Switch to UI mode
                        selection = true;
                    }
                }


            }
        });


        //End the program and save data if the exit button is clicked
        exitButton = new JButton("Exit and Save Changes");

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bank.endProgram(outputPanel.getOutputArea());
            }
        });


        //Add the panels
        add(inputPanel, BorderLayout.CENTER);
        add(outputPanel, BorderLayout.EAST);
        add(exitButton, BorderLayout.SOUTH);


        //Set login for panel
        bank.setLoginUI(inputPanel.getFields(), inputPanel.getLabels());

        //Update the JPanel
        inputPanel.refreshUI();
    }


}