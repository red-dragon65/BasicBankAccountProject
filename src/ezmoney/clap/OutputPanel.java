package ezmoney.clap;

import javax.swing.*;
import java.awt.*;

public class OutputPanel extends JPanel {

    private JTextArea outputArea;


    public OutputPanel(){

        //Initialize text area
        outputArea = new JTextArea();
        setLayout(new BorderLayout());

        //Add text area
        //Allow scrolling
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

    }

    public JTextArea getOutputArea(){
        return outputArea;
    }

    public void refreshUI(){

    }
}
