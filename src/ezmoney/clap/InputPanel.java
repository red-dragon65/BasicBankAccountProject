package ezmoney.clap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is a custom JPanel takes handles the input panel in the GUI.
 */
public class InputPanel extends JPanel {

    /**
     * Sends the input to the bank class.
     */
    private JButton submitButton;

    /**
     * The fields used for user input.
     * These are mainly modified by the 'AdminUI' and 'CustomerUI' classes.
     */
    private JLabel[] labels;
    private JTextField[] fields;

    /**
     * The action listener set in the 'BankFrame' class.
     * It defines where the buttons send the input data.
     */
    private ActionListener listener;


    /**
     * The default constructor.
     * It initializes the components for this JPanel.
     * In addition, it uses a grid layout to position the components.
     */
    public InputPanel() {

        //Get the size
        Dimension dim = getPreferredSize();
        dim.width = 250;

        //Set the size
        setPreferredSize(dim);


        //Initialize the components
        submitButton = new JButton("Submit");

        labels = new JLabel[4];
        fields = new JTextField[4];

        for (int i = 0; i < 4; i++) {

            labels[i] = new JLabel();
            fields[i] = new JTextField(10);
        }


        //Set the action listener
        submitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                listener.actionPerformed(e);
            }
        });


        //Set panel layout
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();



        /*---- first grid row ----*/

        gc.weightx = 1; //Controls how much space it takes up relative to other cells
        gc.weighty = 0.1;

        //Top left cell
        gc.gridx = 0; //Column
        gc.gridy = 0; //Row
        gc.fill = GridBagConstraints.NONE; //Tell the component to take up all space or no space in the cell
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0, 0, 0, 5); // The indent. Leaves 5 pixels on the right.

        //Add the component to the cell
        add(labels[0], gc);


        gc.gridx = 1; //Column
        gc.gridy = 0; //Row
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0, 0, 0, 0);

        //Add the component to the cell
        add(fields[0], gc);



        /*---- second grid row ----*/

        gc.gridx = 0; //Column
        gc.gridy = 1; //Row
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0, 0, 0, 5); // The indent. Leaves 5 pixels on the right.

        //Add the component to the cell
        add(labels[1], gc);


        gc.gridx = 1; //Column
        gc.gridy = 1; //Row
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0, 0, 0, 0);

        //Add the component to the cell
        add(fields[1], gc);



        /*---- third grid row ----*/

        gc.gridx = 0; //Column
        gc.gridy = 2; //Row
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0, 0, 0, 5); // The indent. Leaves 5 pixels on the right.

        //Add the component to the cell
        add(labels[2], gc);


        gc.gridx = 1; //Column
        gc.gridy = 2; //Row
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0, 0, 0, 0);

        //Add the component to the cell
        add(fields[2], gc);



        /*---- fourth grid row ----*/

        //Top left cell
        gc.gridx = 0; //Column
        gc.gridy = 3; //Row
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0, 0, 0, 5); // The indent. Leaves 5 pixels on the right.

        //Add the component to the cell
        add(labels[3], gc);


        gc.gridx = 1; //Column
        gc.gridy = 3; //Row
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0, 0, 0, 0);

        //Add the component to the cell
        add(fields[3], gc);



        /*---- fifth grid row ----*/

        gc.weightx = 1; //Controls how much space it takes up relative to other cells
        gc.weighty = 2;

        //Top left cell
        gc.gridx = 1; //Column
        gc.gridy = 4; //Row
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.insets = new Insets(0, 0, 0, 0); // The indent. Leaves 5 pixels on the right.

        //Add the component to the cell
        add(submitButton, gc);


        clearUI();

    }


    /**
     * Sets the listener for the button.
     *
     * @param listener The action performed by the button.
     */
    public void setListener(ActionListener listener) {

        this.listener = listener;
    }

    /**
     * Used to allow data retrieval from the form.
     * In addition, the fields are modified in other classes.
     *
     * @return Returns the text field for data retrieval
     */
    public JTextField[] getFields() {

        return fields;
    }

    /**
     * Used to set the text for the form by other classes.
     *
     * @return Returns the labels for modification.
     */
    public JLabel[] getLabels() {

        return labels;
    }


    /**
     * Sets the fields to display input for menu selection.
     */
    public void selectionUI() {

        clearUI();

        //Hide all fields
        for (JTextField field : fields) {
            field.setVisible(false);
        }

        //Show one field
        fields[0].setVisible(true);

        //Hide all labels
        for (JLabel label : labels) {
            label.setText("");
        }

        //Show one label
        labels[0].setText("Enter selection: ");

        refreshUI();

    }


    /**
     * Resets all fields to by empty.
     * This allows other classes to set them as desired.
     */
    public void clearUI() {

        //Clear out old component data

        for (JTextField field : fields) {
            field.setVisible(false);
            field.setText("");
        }

        for (JLabel label : labels) {
            label.setText("");
        }
    }


    /**
     * Notifies the panel to refresh the component contents.
     * Used after the components have been modified.
     */
    public void refreshUI() {

        //Refresh the JPanel
        this.revalidate();
        this.repaint();
    }
}
