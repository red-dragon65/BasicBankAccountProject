

BrainStorming UI Modifications
-------------------------------

Classes from old project:
- Bank

- Admin
- Customer

- Account
- AccountActivity






Customer methods input requirements:
-------------------------------------
- listMyAccounts:
    :Selection number

- deleteAccount
    :

- createAccount
    :Selection number
    :Enter a name for the new account
    :Enter a pin for the new account
    :Enter the account type

- deposit
    :Selection number
    :Enter the account number
    :Enter the amount

- withdraw
    :Selection number
    :Enter the account number
    :Enter the amount

- requestAccountDetails
    :

- transferMoney
    :
.



Admin methods input requirements:
----------------------------------













User flow diagram for new code:
--------------------------------
- User clicks selection button

- Call code to update ui (UI class)

- User clicks submit button

- Call code to retrieve ui data (Action class)






What clicking a button will do:
--------------------------------

- Update the UI (selection button)

- Retrieve the data (submit button)






Psuedo code
------------

//What the selection button does
SelectionButton.actionListener{

    //Hide all the fields
    textField1.setVisible(false)

    //Call code to setup UI
    adminUI.call(buttontype);

    //Refresh JFrame
    pane.revalidate()
    pane.repaint()

}


//What the submit button does
Submit.actionListener{

    //Call code to processes data
    adminAction.call(buttontype);

    //Reset fields to show selection field
    resetUI();

}



//The code called by the buttons
call(buttontype){

    //Go through selection type
    switch(selection){

        //Call the correct method based on the button pressed
        if(selectionButton)
            adminUI.blahblah(textfield1, textfield2, textfield3, textfield4);

        if(submitButton)
            adminAction.something();

    }

}

Passing data:
- AccountList
- JLabels
- JFields
- Form







TODO:
Split: Admin and Customer
* AdminLogic
* CustomerLogic
* AdminUI
* CustomerUI

Bank added JFrame code
* Input panel (buttons and labels)
* Output panel (form)

Setup the button listeners:
* Pass the J components to the correct methods





* Implement 'TransferWorker' class
* Implement 'Runnable' for 'TransferWorker'
* Allow 'TransferWorker' to transfer money from one account to another
* Calculate the total amount from all bank accounts once transfer is complete

* Implement 'TransferDriver' class
* Allow the class to create threads of 'TransferWorker' objects



* Test GUI code
* Test Multi-threading code



Documentation:
- Add comments to new code
- Document that covers what we did
- Screen shots showing how to use the program
- Jar file submission
- Submit project file



Mujtaba:
* Test code
- Add comments to code
- Create screen shot document

Brandon:
* Finish code split
- Add comments to code
- Create Design Document

- Generate javadoc
- Generate jar file
- Submit project

