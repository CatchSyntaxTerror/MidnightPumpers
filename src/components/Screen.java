package components;

import UI.ScreenUI;

/**
 * Joel Villarreal
 * The touch screen display object, for simulation purposes
 */
//TODO:
// - Connect to IO Port
//  - Received message(s): text/button layouts
//  - Sent message(s): button presses
// - Connect to the GUI
//  - Received message(s): button presses
//  -Sent message(s): text/button layouts
// - Store button presses
public class Screen {
    // Constants
    private final String REGEX = "[:]"; // split messages by the ':' character
    // Button Tracking
    private final int NULL_BTN    =  -1; // signifies no button selected
    private int selectedBtn = NULL_BTN; // the fuel grade selection

    private ScreenUI screenUI = null;

    /**
     * A screen UI setter
     * @param scrUI
     */
    public void setScreenUI(ScreenUI scrUI) {
        this.screenUI = scrUI;
    }

    /**
     * Set the screen display based on text
     * @param screenStr the mark-down language string
     */
    public void setScreen(String screenStr) {
        //"t3-s2-f1-c0-text:b4m:b5m:b10x"
        // split the strings by semicolons
        String[] instructions = screenStr.split(REGEX);

        if (screenUI == null) {
            // Error, cannot set display of a null screen
            errorOccurred();
        } else if (screenStr.length() == 0) {
            // Blank Screen
            screenUI.setBlank();
            System.out.println("Set the screen to be blank");
        } else {
            for (String inst : instructions) {
                updateScreenUI(inst);
            }
        }

    }

    /**
     * Update the screen with the following instruction
     * @param mlString the instruction to follow
     */
    private void updateScreenUI(String mlString) {
        char fst = mlString.charAt(0);
        if (fst == 't') {
            // text box instruction
        } else if (fst == 'b') {
            // button instruction
            int lastIndex = mlString.length() - 1;
            char buttonType = mlString.charAt(lastIndex); // last char
            String numS = mlString.substring(1, lastIndex); // the number String

            // TODO GUI button creation
            try {
                int fieldNum = Integer.parseInt(numS);
                System.out.println("Converted integer: " + fieldNum);
            } catch (NumberFormatException e) {
                errorOccurred();
                e.printStackTrace();
            }


        } else {
            // Only expecting text and button instructions
            errorOccurred();
        }

    }


    /**
     * Notify communicator of an error
     */
    private void errorOccurred() {
        notifyCommunicator(NULL_BTN);
    }

    /**
     * Tell this screen a button was pressed
     * @param btnNumber the button that was pressed
     * @param notifyMain notify main?
     */
    public void notify(int btnNumber, boolean notifyMain) {
//        System.out.println(btnNumber + ", " + notifyMain);
        if (btnNumber == NULL_BTN) {
            // TODO notify IO-Port there is an issue
            errorOccurred();
        } else {
            if (notifyMain) {
                //TODO notify IO-Port of this state
                notifyCommunicator(btnNumber);
            } else {
                // store the button press
                selectedBtn = btnNumber;
            }
        }
    }
    /**
     * Notify the Main System of the screen button state, via the Communicator
     * IO Port
     */
    private void notifyCommunicator(int pressedBtn) {
        if (pressedBtn == NULL_BTN) {
            // TODO send error message
            System.out.println("ERROR");
        }
        //TODO: implement Communicator IO Port
        System.out.println(btnString(pressedBtn));
    }
    /**
     * for communicating with the Main System
     * @param pressedBtn the responsive button responsible for the notify call
     * @return the screens current state
     */
    private String btnString(int pressedBtn) {
        //TODO: make more modular? State object rather than String?
        //TODO: make sure everyone is on the same page about button messaging
        // info
        if (pressedBtn == NULL_BTN) {
            // Should never be called
            errorOccurred();
            return "";
        } else if (selectedBtn == NULL_BTN) {
            // No button selected, only notify of the responsive button press
            return pressedBtn + "";
        } else {
            /* Return fuel grade selection and responsive button, and reset 
            button selection */
            int val = selectedBtn;
            selectedBtn = NULL_BTN;
            return pressedBtn + ":" + val;
        }
    }
}
