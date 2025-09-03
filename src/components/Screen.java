package components;

import IOPort.CommunicatorClient;
import IOPort.CommunicatorServer;
import UI.ButtonType;
import UI.ScreenUI;
import util.MarkdownConstants;
import util.MarkdownLanguage;

import java.util.ArrayList;

import static util.PortAddresses.SCREEN_PORT;

/**
 * Joel Villarreal
 * The touch screen display object, for simulation purposes
 */
//TODO:
// - Connect to IO Port
public class Screen {
    // Constants
    private final String REGEX_0 = ":"; // split messages by the ':' character
    private final String REGEX_1 = "-"; // split messages by the '-' character
    private final int NULL_BTN    =  -1; // signifies no button selected
    private final int UNSET = -1; // signifies a text option is unset

    // Button Tracking
    private int selectedBtn = NULL_BTN; // the fuel grade selection

    // Screen has a Communicator type IO Port (client or server?)
    //TODO: IOPort
//    private final CommunicatorClient communicator = new CommunicatorClient(SCREEN_PORT);
//    private final CommunicatorServer communicator = new CommunicatorServer(SCREEN_PORT);


    // The gui
    private ScreenUI screenUI = null;

    /**
     * A screen UI setter
     * @param scrUI the screen UI
     */
    public void setScreenUI(ScreenUI scrUI) {
        this.screenUI = scrUI;
    }

    /**
     * Set the screen based on commands
     * @param cmds the commands
     */
    public void setScreen(MarkdownLanguage.Commands cmds) {
        if (screenUI == null) {
            System.out.println("ERROR: Screen UI not instantiated");
            errorOccurred();
            return;
        }
        MarkdownLanguage.ButtonCommands bCmds = cmds.getBCommands();
        MarkdownLanguage.TextFieldCommands tCmds = cmds.getTCommands();

        ArrayList<MarkdownLanguage.ButtonCommands.Button> btns = bCmds.getButtonCommands();
        ArrayList<MarkdownLanguage.TextFieldCommands.TextField> txts = tCmds.getTextFieldCommands();

        for (MarkdownLanguage.ButtonCommands.Button b: btns) {
            // Create buttons
            createBtn(b);
        }
        for (MarkdownLanguage.TextFieldCommands.TextField t: txts) {
            // Create text boxes
            createTxt(t);
        }
    }

    /**
     * Create a text box on the UI
     * @param t
     */
    private void createTxt(MarkdownLanguage.TextFieldCommands.TextField t) {
        if (screenUI == null) {
            System.out.println("Error: screen not initialized");
            errorOccurred();
        }
        int size, font, color;
        String text = t.getText();
        int[] fieldNums = {t.getField()}; //TODO need more than single field sometimes
        MarkdownConstants.Size sz = t.getSize();
        MarkdownConstants.Font fnt = t.getFont();
        MarkdownConstants.BGColor bgColor = t.getBGColor();
        switch (sz) {
            // set size
            case Small -> size = 0;
            case Large -> size = 2;
            default -> size = 1;
        }
        switch (fnt) {
            // set font type
            case Italic -> font = 0;
            case Bold -> font = 2;
            default -> font = 1;
        }
        switch (bgColor) {
            // set background color
            default -> color = 1;
        }
        screenUI.createLbl(fieldNums, size, font, color, text);
    }

    /**
     * Create a button on the UI
     * @param btn
     */
    private void createBtn(MarkdownLanguage.ButtonCommands.Button btn) {
        if (screenUI == null) {
            System.out.println("Error: screen not initialized");
            errorOccurred();
        }
        int btnNum = btn.getField();
        boolean isResponsive = btn.getResponsive();
        if (isResponsive) {
            screenUI.createBtn(btnNum, ButtonType.RESPONSIVE);
        } else {
            screenUI.createBtn(btnNum, ButtonType.MUTUALLY_EXCLUSIVE);
        }
    }

    /**
     * Set the screen display based on text
     * @param screenStr the mark-down language string
     */
    public void setScreen(String screenStr) {
        //"t3-s2-f1-c0-text:b4m:b5m:b10x"
        // split the strings by semicolons
        String[] instructions = screenStr.split(REGEX_0);

        if (screenUI == null) {
            // Error, cannot set display of a null screen
            System.out.println("ERROR: Cannot set display of null screen");
            errorOccurred();
        } else if (screenStr.length() == 0) {
            // Blank Screen
            screenUI.setBlank();
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
            createTxt(mlString.substring(1));
        } else if (fst == 'b') {
            // button instruction
            createBtn(mlString.substring(1));
        } else {
            // Only expecting text and button instructions
            System.out.println("ERROR: only expecting text and button " +
                    "instructions");
            errorOccurred();
        }

    }

    /**
     * Create a text box on the screen based on field number, font size, font
     * type, background color, and text string
     * @param mlString
     */
    private void createTxt(String mlString) {
        int size = -1;
        int font = -1;
        int color = -1;
        String text;
        int[] tempFields = {-1, -1};
        int[] fieldNums;

        //"3-s2-f1-c0-text"
        String[] txtStrings = mlString.split(REGEX_1);

        // The text
        text = txtStrings[txtStrings.length - 1];

        // Set the field number(s)
        char numChar = txtStrings[0].charAt(0);
        boolean error = setFieldNum(tempFields, 0, numChar);
        if (error) {
            return;
        }
        if (txtStrings[0].length() > 1) {
            // Two text fields
            numChar = txtStrings[0].charAt(1);
            error = setFieldNum(tempFields, 1, numChar);
            if (error) {
                return;
            }
        }
        if (tempFields[1] == NULL_BTN) {
            // singular field number
            fieldNums = new int[]{tempFields[0]};
        } else {
            // two field numbers
            fieldNums = tempFields;
        }

        // Set text arguments
        for (int i = 1; i < txtStrings.length - 1; i++) {
            char indicator = txtStrings[i].charAt(0);
            numChar = txtStrings[i].charAt(1);
            switch (indicator) {
                case 's' :
                    // Size
                    size = Character.getNumericValue(numChar);
                    break;
                case 'f':
                    font = Character.getNumericValue(numChar);
                    // Font
                    break;
                case 'c':
                    // Background Color
                    color = Character.getNumericValue(numChar);
                    break;
                default:
                    // Error, unexpected input type
                    System.out.println("Error: unexpected text format");
                    errorOccurred();
            }
        }

        screenUI.createLbl(fieldNums, size, font, color, text);
    }

    /**
     * Set the field number at the given index
     * @param fieldNum list of field numbers
     * @param index index to set in fieldNum
     * @param numChar the character to put in fieldNum
     * @return did an error occur?
     */
    private boolean setFieldNum(int[] fieldNum, int index, char numChar) {
        fieldNum[index] = Character.getNumericValue(numChar);
        if (fieldNum[index] < 0 || fieldNum[index] > 9) {
            System.out.println("Field number must be numeric value");
            errorOccurred();
            return true;
        }
        return false;
    }

    /**
     * Create a button based on the number and type
     * @param mlString "xy" where x is the digit that represents screen location
     *                 and y is the character that represents button type
     */
    private void createBtn(String mlString) {
        ButtonType buttonType;
        int fieldNum;

        int lastIndex = mlString.length() - 1;

        char buttonTypeChar = mlString.charAt(lastIndex); // last char
        // Verify button type
        switch (buttonTypeChar) {
            case 'm' ->
                // a toggle group button
                    buttonType = ButtonType.MUTUALLY_EXCLUSIVE;
            case 'x' ->
                // A responsive button
                    buttonType = ButtonType.RESPONSIVE;
            default -> {
                System.out.println("ERROR: button type must be 'x' or 'm'");
                errorOccurred();
                return;
            }
        }
        String numS = mlString.substring(0, lastIndex); // the number String
        try {
            // Attempt to create a button
            fieldNum = Integer.parseInt(numS);

            if (fieldNum > 9 || fieldNum < 0) {
                // field numbers are 0 to 9
                System.out.println("ERROR: field numbers are 0 to 9");
                errorOccurred();
                return;
            }
            screenUI.createBtn(fieldNum, buttonType);

        } catch (NumberFormatException e) {
            // Input was not a number
            System.out.println("ERROR: expecting digit input");
            errorOccurred();
            e.printStackTrace();
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
        if (btnNumber == NULL_BTN) {
            System.out.println("ERROR: gui related");
            errorOccurred();
        } else {
            if (notifyMain) {
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
//            communicator.send("ERROR");
            return;
        }
        //TODO: implement Communicator IO Port
        System.out.println(btnString(pressedBtn));
//        communicator.send(btnString(pressedBtn));
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
            System.out.println("ERROR: calling btnString() w/ pressedBtn == NULL_BTN");
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
