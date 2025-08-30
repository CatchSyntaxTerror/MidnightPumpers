package UI;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.stage.Stage;


/**
 * Joel Villarreal
 * The Screen GUI
 */
public class ScreenUI extends Application {
    // Constants:
    private final int NULL_BTN    =  -1; // signifies no button selected
    private final double HORZ     =   1; // the horizontal gap of the grid
    private final double VERT     =   1; // the vertical gap of the grid
    private final double DISP_W   = 300; // the GUI's initial width
    private final double DISP_H   = 250; // the GUI's initial height
    private final int  NUM_COLS   =   4; // number of columns on the grid
    private final int  NUM_ROWS   =   5; // number of columns on the grid
    private final int  BTN_FACTOR =   8; /* buttons take up an eighth of the
                                             horizontal space */
    // CSS Style Strings
    private final String SELECTED_CLR   = "-fx-background-color: lightblue;";
    private final String UNSELECTED_CLR = "-fx-background-color: lightgray;";

    // Non-Constant Global Variables:
    private GridPane          gridPane; // the meat of the display
    private ToggleGroup    toggleGroup; // For mutually exclusive buttons
    private int selectedBtn = NULL_BTN; // the fuel grade selection

    /**
     * launches JavaFX application
     * @param args No expected arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The screen constructor
     */
    public ScreenUI() {
        resetGrid();
    }
    /**
     * Set up the grid
     */
    private void resetGrid() {
        //TODO: redraw display after a call to resetGrid()
        gridPane = new GridPane(HORZ, VERT);
        selectedBtn = NULL_BTN;
        setGridConstraints();
        setUpToggleGroup();

        //TODO: delete these methods, they are for testing purposes
        createButtons();
        createTextBoxes();
    }

    /**
     * Establishes multi-select behavior
     */
    private void setUpToggleGroup() {
        toggleGroup = new ToggleGroup();
        toggleGroup.selectedToggleProperty().addListener(
                (observable, oldToggle, newToggle) -> {
            if (newToggle != null) {
                // Set selected button color
                ((ToggleButton) newToggle).setStyle(SELECTED_CLR);
            }
            if (oldToggle != null) {
                // Set unselected button color
                ((ToggleButton) oldToggle).setStyle(UNSELECTED_CLR);
            }
        });
    }

    /**
     * Make Display more Readable, by setting column and row constraints to
     * adjust with the screen size
     */
    private void setGridConstraints() {
        /* the constraints */
        ColumnConstraints col;
        RowConstraints    row;

        /* the percent of the screen a button, text box, and row take up */
        double btnPercent, txtPercent, rowPercent;

        rowPercent = 100.0 / NUM_ROWS;
        btnPercent = 100.0 / BTN_FACTOR;
        txtPercent = (100.0 - btnPercent) / (NUM_COLS - 2);

        for (int i = 0; i < NUM_COLS; i++) {
            /* For each column, set the column constraints to a percent of the
               screen width */
            col = new ColumnConstraints();
            gridPane.getColumnConstraints().add(col);
            if(i != 0 && i != (NUM_COLS - 1)) {
                // Text columns have less width
                col.setPercentWidth(txtPercent);
            } else {
                // Button columns have more width
                col.setPercentWidth(btnPercent);
            }
        }

        for (int i = 0; i < NUM_ROWS; i++) {
            /* For each row, set the row constraints to a percent of the screen
               height*/
            row = new RowConstraints();
            gridPane.getRowConstraints().add(row);
            row.setPercentHeight(rowPercent);
        }

    }

    /**
     * Put text boxes in the correct positions
     * This method is for testing purposes
     */
    private void createTextBoxes() {
        //TODO: text box creation
        for (int i = 0; i < 10; i++) {
            Label lbl;
            int rowI = i / 2;

            if(i % 2 == 0) {
                // Even numbers are on the right of the display
                lbl = createLbl(rowI, 1);

            } else {
                // Odd numbers on the left of the display
                lbl = createLbl(rowI, 2);
            }
        }
    }

    /**
     * Add this label to the grid
     * @param row the row of the grid to add the label
     * @param col the column of the grid to add the label
     * @return the created label
     */
    private Label createLbl(int row, int col) {
        Label lbl = new Label("lorem ipsum");
        HBox hBox = new HBox(0, lbl);

        //Some stuff for prettiness
        lbl.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        HBox.setHgrow(lbl, Priority.ALWAYS);
        hBox.setAlignment(Pos.CENTER);

        gridPane.add(hBox, col, row);
        return lbl;
    }

    /**
     * Put Buttons in the correct positions
     * This method is for testing purposes
     */
    private void createButtons() {
        for (int i = 0; i < 9; i++) {
            createBtn(i, ButtonType.MUTUALLY_EXCLUSIVE);
        }
        createBtn(9, ButtonType.RESPONSIVE);
    }

    /**
     Create a button at this row and this column in the display
     * @param row, the row to create a button at
     * @param col, the column to create a button at
     * @return that button
     */
    private ToggleButton createBtn(int row, int col) {
        ToggleButton btn = new ToggleButton();
        HBox hBox = new HBox(0, btn);

        //Resizability
        btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        HBox.setHgrow(btn, Priority.ALWAYS);
        hBox.setAlignment(Pos.CENTER);

        //Coloring
        btn.setStyle(UNSELECTED_CLR);

        gridPane.add(hBox, col, row);
        return btn;
    }

    /**
     * Create a button at this location in display
     * @param btnNum the number that signifies this button
     * @return the created button
     */
    private ToggleButton createBtn(int btnNum) {
        ToggleButton btn;
        int rowI = btnNum / 2;

        if(btnNum % 2 == 0) {
            // Even numbers are on the right of the display
            btn = createBtn(rowI, 0);

        } else {
            // Odd numbers on the left of the display
            btn = createBtn(rowI, 3);
        }
        return btn;
    }
    /**
     * Create a button at this number, of this type
     * @param btnNumber the button location id
     * @param btnType the type of this button
     */
    private void createBtn(int btnNumber, ButtonType btnType) {
        ToggleButton btn = createBtn(btnNumber);
        if (btnType == ButtonType.RESPONSIVE) {
            // Responsive Button
            btn.setOnMouseClicked(event -> {
                // Notify the Communicator and reset the grid
                notifyListener(btnNumber);
                resetGrid();
            });
        } else {
            // Multi-Select Button, add it to the toggle group
            btn.setToggleGroup(toggleGroup);

            btn.setOnMouseClicked(event -> {
                // Track the number associated with this button
                selectedBtn = btnNumber;
            });
        }
    }


    /**
     * Notify the testing.Main System of the screen button state, via the Communicator
     * IO Port
     */
    private void notifyListener(int pressedBtn) {
        System.out.println(getScreenState(pressedBtn));
        //TODO: implement Communicator
    }

    /**
     * for communicating with the testing.Main System
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

    /**
     * Get the scene of this ScreenUI object
     * @return the grid pane (for display purposes)
     */
    private Parent getScene() {
        return gridPane;
    }
    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     *                     Applications may create other stages, if needed, but
     *                     they will not be primary stages.
     * @throws Exception if something goes wrong
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Touch Screen Display");
        ScreenUI scr = new ScreenUI();
        primaryStage.setScene(new Scene(scr.getScene(), DISP_W, DISP_H));
        primaryStage.show();
    }
}

