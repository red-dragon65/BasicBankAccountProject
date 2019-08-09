package ezmoney.clap;

import javax.swing.*;
import java.awt.*;

public class BankFrame extends JFrame {

    //Panels
    private InputPanel inputPanel;
    private OutputPanel outputPanel;



    public BankFrame(){

        //Set title
        super("Bank Account System");

        //Set frame values
        setLayout(new BorderLayout());
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        //Create the panels
        inputPanel = new InputPanel();
        outputPanel = new OutputPanel();

        //Add the panels
        add(inputPanel, BorderLayout.WEST);
        add(outputPanel, BorderLayout.EAST);
    }






}


/*

1 Button

4 TextFields
4 Labels

1 Form




todo
- create panel for login
- create panel for form
- create panel for buttons


 */