package ezmoney.clap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputPanel extends JPanel {

    //1 button
    private JButton submitButton;

    //4 input fields
    private JLabel labels[];
    private JTextField fields[];


    public InputPanel(){

        //Get the size
        Dimension dim = getPreferredSize();
        dim.width = 250;

        //Set the size
        setPreferredSize(dim);


        //Set the components
        submitButton = new JButton("Submit");

        labels = new JLabel[4];
        fields = new JTextField[4];


        //Set the action listener
        submitButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e){

                //TODO: Do something



            }
        });



        //Set panel layout
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();


        /***** first grid row (2 cells) *****/
        gc.weightx = 1; //Controls how much space it takes up relative to other cells
        gc.weighty = 0.1;

        //Top left cell
        gc.gridx = 0; //Column
        gc.gridy = 0; //Row
        gc.fill = GridBagConstraints.NONE; //Tell the component to take up all space or no space in the cell
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0,0,0,5); // The indent. Leaves 5 pixels on the right.

        //Add the component to the cell
        add(labels[0], gc);


        gc.gridx = 1; //Column
        gc.gridy = 0; //Row
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0,0,0,0);

        //Add the component to the cell
        add(fields[0], gc);




        /***** second grid row *****/

        //Top left cell
        gc.gridx = 0; //Column
        gc.gridy = 1; //Row
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0,0,0,5); // The indent. Leaves 5 pixels on the right.

        //Add the component to the cell
        add(labels[1], gc);


        gc.gridx = 1; //Column
        gc.gridy = 1; //Row
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0,0,0,0);

        //Add the component to the cell
        add(fields[1], gc);




        /***** third grid row *****/

        //Top left cell
        gc.gridx = 0; //Column
        gc.gridy = 2; //Row
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0,0,0,5); // The indent. Leaves 5 pixels on the right.

        //Add the component to the cell
        add(labels[2], gc);


        gc.gridx = 1; //Column
        gc.gridy = 2; //Row
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0,0,0,0);

        //Add the component to the cell
        add(fields[2], gc);


        /***** fourth grid row *****/

        //Top left cell
        gc.gridx = 0; //Column
        gc.gridy = 3; //Row
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0,0,0,5); // The indent. Leaves 5 pixels on the right.

        //Add the component to the cell
        add(labels[3], gc);


        gc.gridx = 1; //Column
        gc.gridy = 3; //Row
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0,0,0,0);

        //Add the component to the cell
        add(fields[3], gc);






        /***** third grid row *****/
        gc.weightx = 1; //Controls how much space it takes up relative to other cells
        gc.weighty = 2;

        //Top left cell
        gc.gridx = 1; //Column
        gc.gridy = 2; //Row
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.insets = new Insets(0,0,0,0); // The indent. Leaves 5 pixels on the right.

        //Add the component to the cell
        add(submitButton, gc);

    }
}
