package components;

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
public class Screen implements Runnable {
    private final int NULL_BTN    =  -1; // signifies no button selected
    private int selectedBtn = NULL_BTN; // the fuel grade selection


    /**
     * Runs this operation.
     */
    //TODO: make this screen runnable
    @Override
    public void run() {

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
            notifyCommunicator(btnNumber);
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
        System.out.println(getScreenState(pressedBtn));
        //TODO: implement Communicator IO Port
    }
    /**
     * for communicating with the Main System
     * @param pressedBtn the responsive button responsible for the notify call
     * @return the screens current state
     */
    private String getScreenState(int pressedBtn) {
        //TODO: make more modular? State object rather than String?
        //TODO: make sure everyone is on the same page about button messaging
        // info
        if (selectedBtn == NULL_BTN) {
            // No button selected, only notify of the responsive button press
            return pressedBtn + ";";
        } else {
            /* Return fuel grade selection and responsive button, and reset 
            button selection */
            int val = selectedBtn;
            selectedBtn = NULL_BTN;
            return pressedBtn + ":" + val + ";";
        }
    }
}
