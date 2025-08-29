package UI;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.stage.Stage;


/**
 * Joel Villarreal
 * The Screen GUI
 */
public class ScreenUI extends Application {
    private GridPane                  gp; // the meat of the display
    private int selectedBtn       =   -1; // the fuel grade selection
    private final double HORZ     =    1; // the horizontal gap of the grid
    private final double VERT     =    1; // the vertical gap of the grid
    private final double DISP_W   =  300; // the GUI's width
    private final double DISP_H   =  250; // the GUI's height
    private final int  NUM_COLS   =    4; // number of columns on the grid
    private final int  NUM_ROWS   =    5; // number of columns on the grid
    private final int  BTN_FACTOR =    8; /* buttons take up an eighth of the
                                             horizontal space */

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
        gp = new GridPane(HORZ, VERT);
        setGridConstraints();
        //TODO: delete these methods, they are for testing purposes
        createButtons();
        createTextBoxes();
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
            gp.getColumnConstraints().add(col);
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
            gp.getRowConstraints().add(row);
            row.setPercentHeight(txtPercent);
        }

    }

    /**
     * Put text boxes in the correct positions
     * This method is for testing purposes
     */
    private void createTextBoxes() {
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
     * @param row
     * @param col
     * @return
     */
    private Label createLbl(int row, int col) {
        Label lbl = new Label("lorem ipsum");
        HBox hBox = new HBox(0, lbl);

        //Some stuff for prettiness
        lbl.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        HBox.setHgrow(lbl, Priority.ALWAYS);
        hBox.setAlignment(Pos.CENTER);

        gp.add(hBox, col, row);
        return lbl;
    }

    //    private void createText()
    private void createText(int rowI, int i) {
    }

    /**
     * Put Buttons in the correct positions
     * This method is for testing purposes
     */
    private void createButtons() {
        for (int i = 0; i < 10; i++) {
            createBtn(i, ButtonType.RESPONSIVE);
        }
    }
    /* TODO: redo button creation to make mutually exclusive buttons display
       properly */
    /**
     Create a button at this row and this column in the display
     * @param row, the row to create a button at
     * @param col, the column to create a button at
     * @return that button
     */
    private Button createBtn(int row, int col) {
        Button btn = new Button();
        HBox hBox = new HBox(0, btn);

        //Some stuff for prettiness
        btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        HBox.setHgrow(btn, Priority.ALWAYS);
        hBox.setAlignment(Pos.CENTER);

        gp.add(hBox, col, row);
        return btn;
    }

    /**
     * Create a button at this location in display
     * @param btnNum the number that signifies this button
     * @return the created button
     */
    private Button createBtn(int btnNum) {
        Button btn;
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
        Button btn = createBtn(btnNumber);
        if (btnType == ButtonType.RESPONSIVE) {
            /* Responsive buttons need to notify all listeners of the screen
            state */
            btn.setOnMouseClicked(event -> {
                notifyListener(btnNumber);
            });
        } else {
            btn.setOnMouseClicked(event -> {
                btn.setStyle("-fx-background-color: red;");
                selectedBtn = btnNumber;
            });
        }
    }

    /**
     * Notify the Main System of the screen button state, via the Communicator
     * IO Port
     */
    private void notifyListener(int pressedBtn) {
        System.out.println(getScreenState(pressedBtn));
        //TODO: implement Communicator
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
        if (selectedBtn == -1) {
            // No button selected, only notify of the responsive button press
            return pressedBtn + ";";
        } else {
            /* Return fuel grade selection and responsive button, and reset 
            button selection */
            int val = selectedBtn;
            selectedBtn = -1;
            return pressedBtn + ":" + val + ";";
        }
    }

    /**
     * Get the scene of this ScreenUI object
     * @return the grid pane (for display purposes)
     */
    private Parent getScene() {
        return gp;
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

