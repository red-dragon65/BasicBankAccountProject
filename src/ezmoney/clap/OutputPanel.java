package ezmoney.clap;

import javax.swing.*;
import java.awt.*;

/**
 * The class used to display output.
 */
public class OutputPanel extends JPanel {

    /**
     * The text area output screen.
     */
    private JTextArea outputArea;


    /**
     * The constructor.
     * Initializes the text area component.
     */
    public OutputPanel() {

        //Initialize text area
        outputArea = new JTextArea();
        outputArea.setColumns(30);
        setLayout(new BorderLayout());

        //Add text area
        //Allow scrolling
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

    }

    /**
     * Used to allow classes to output to the screen.
     *
     * @return Returns the JTextArea to allow other classes to output to the screen.
     */
    public JTextArea getOutputArea() {
        return outputArea;
    }
}
