package ezmoney.clap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BankFrame extends JFrame {

    //Panels
    private InputPanel inputPanel;
    private OutputPanel outputPanel;
    private Bank bank;
    private boolean selection = true;
    private JButton exitButton;

    public BankFrame() {

        //Set title
        super("Bank Account System");

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
                    if (selection) {

                        //Get the selection
                        bank.setSelected(inputPanel.getFields()[0], outputPanel.getOutputArea());

                        //Update UI
                        inputPanel.clearUI();

                        if (bank.calculateInput(selection, inputPanel.getFields(), inputPanel.getLabels(), outputPanel.getOutputArea())) {
                            inputPanel.selectionUI();
                            bank.showSelectionList(outputPanel.getOutputArea());
                        }

                        inputPanel.refreshUI();
                        outputPanel.refreshUI();

                        selection = false;

                    } else {

                        //Run the logic
                        bank.calculateInput(selection, inputPanel.getFields(), inputPanel.getLabels(), outputPanel.getOutputArea());

                        //Show the selection
                        inputPanel.selectionUI();
                        bank.showSelectionList(outputPanel.getOutputArea());
                        outputPanel.refreshUI();

                        selection = true;
                    }
                }


            }
        });

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

        inputPanel.refreshUI();
    }


}