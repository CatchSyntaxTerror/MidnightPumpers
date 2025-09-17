package UI;

import components.Flowmeter;
import components.Pump;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import sim.Gas;

import java.util.Random;

public class PumpAndFlowGUI extends Application {
    private static final AnchorPane anchorPane = new AnchorPane();
    private Flowmeter flowmeter;
    private PFGUIhelper guiHelper;
    private Pump pump;
    private Gas gas = new Gas();
    private FuelTank fuelTank;

    private Timeline timeline;
    //this is how long it takes to fill, the rate can be changed, this is just the
    // default
    final double durration =20;
    @Override
    public void start(Stage primaryStage) throws Exception {
        fuelTank = new FuelTank(150, 200, 100, 50,0);
        fuelTank.getTankPane().setBackground(Background.fill(Color.BLACK));
        //fuelTank.getTankPane().setLayoutX(1000);
        HBox fuelroot = new HBox(50, fuelTank.getTankPane());
        fuelroot.setPadding(new Insets(20));
        fuelroot.setBackground(Background.fill(Color.BLACK));

        timeline = new Timeline(
                new javafx.animation.KeyFrame(javafx.util.Duration.seconds(0),
                        new javafx.animation.KeyValue(fuelTank.fuelLevelProperty(), 0)),
                new javafx.animation.KeyFrame(javafx.util.Duration.seconds(durration),
                        new javafx.animation.KeyValue(fuelTank.fuelLevelProperty(), 100))
        );
        timeline.setAutoReverse(true);
        timeline.setCycleCount(1);





        flowmeter = new Flowmeter();
        pump = new Pump();
        guiHelper = new PFGUIhelper();
        flowmeter.setGas(gas);
        pump.setGas(gas);
        guiHelper.setGas(gas);
        guiHelper.setFlowmeter(flowmeter);
        Group root = new Group();
        anchorPane.setPrefHeight(750);
        anchorPane.setPrefWidth(1000);
        anchorPane.setBackground(Background.fill(Color.BLACK));
        fuelroot.setBackground(Background.fill(Color.BLACK));
        makeAnchor();

        root.getChildren().add(anchorPane);
        root.getChildren().add(fuelroot);
        //fuelroot.setAlignment(Pos.CENTER);
        fuelroot.setLayoutX(700);


        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("PumpFlow");
        primaryStage.show();
    }
    private void makeAnchor(){
        double xs = 50;
        double ys = 0;
        Rectangle leftFirstPipe = new Rectangle(100-xs,400,550,100);
        leftFirstPipe.setStroke(Color.BLUE);
        leftFirstPipe.setFill(Color.LIGHTBLUE);

        Rectangle leftScrewBlock = new Rectangle(350-xs,375,50,150);
        leftScrewBlock.setStroke(Color.BLUE);
        leftScrewBlock.setFill(Color.LIGHTBLUE);

        Rectangle rightFirstPipe = new Rectangle(625-xs,400,375,100);
        rightFirstPipe.setStroke(Color.BLUE);
        rightFirstPipe.setFill(Color.LIGHTBLUE);

        Rectangle rightScrewBlock = new Rectangle(700-xs,375,50,150);
        rightScrewBlock.setStroke(Color.BLUE);
        rightScrewBlock.setFill(Color.LIGHTBLUE);

        Circle outerCircle = new Circle(550-xs, 450, 100); // centerX, centerY,
        // radius
        outerCircle.setFill(Color.BLUE);

        // Inner circle (the "hole")
        Circle innerCircle = new Circle(550-xs, 450, 75); // centerX, centerY,
        // radius
        innerCircle.setFill(Color.WHITE); // Or any color to represent the hole

        // Subtract the inner circle from the outer circle to create the donut shape
        Shape donut = Shape.subtract(outerCircle, innerCircle);
        donut.setStroke(Color.RED);
        donut.setFill(Color.LIGHTPINK);

        Circle pivot = new Circle(550-xs,450,20);
        pivot.setFill(Color.LIGHTPINK);
        pivot.setStroke(Color.RED);

        Rectangle valvePipe1 = new Rectangle(475-xs,437.5,150,25);
        valvePipe1.setFill(Color.LIGHTPINK);
        valvePipe1.setStroke(Color.RED);

        Rectangle valvePipe2 = new Rectangle(475-xs,437.5,150,25);
        valvePipe2.setFill(Color.LIGHTPINK);
        valvePipe2.setStroke(Color.RED);

        Rotate rotate1 = new Rotate();
        rotate1.setAngle(45);
        rotate1.setPivotX(550-xs);
        rotate1.setPivotY(450);

        Rotate rotate2 = new Rotate();
        rotate2.setAngle(135);
        rotate2.setPivotX(550-xs);
        rotate2.setPivotY(450);

        ProgressBar progressBar = new ProgressBar(.1);
        progressBar.setStyle("-fx-accent: brown;");
        progressBar.setPrefHeight(60);
        progressBar.setPrefWidth(850);
        progressBar.setLayoutX(120-xs);
        progressBar.setLayoutY(420);

        Text text = new Text(375,300,"00.00");
        text.setFill(Color.WHITE);
        text.setFont(new Font(100));



        Circle tempButtOn = new Circle(60,60,30,Color.BLUE);
        tempButtOn.setOnMouseClicked(event -> turnON());

        Circle tempButtOff = new Circle(150,60,30,Color.RED);
        tempButtOff.setOnMouseClicked(event -> turnOF());

        Circle tempButtThread = new Circle(60,150,30,Color.GREEN);
        tempButtThread.setOnMouseClicked(event -> startThread(tempButtThread));

        Circle tempButtGasType = new Circle(150,150,30,Color.YELLOW);
        tempButtGasType.setOnMouseClicked(event -> setGasType());
        guiHelper.setRotate1(rotate1);
        guiHelper.setRotate2(rotate2);
        guiHelper.setProgressBar(progressBar);
        guiHelper.setText(text);

        pump.setProgressBar(progressBar);

        valvePipe1.getTransforms().add(rotate1);
        valvePipe2.getTransforms().add(rotate2);

        anchorPane.getChildren().add(tempButtOn);
        anchorPane.getChildren().add(tempButtOff);
        anchorPane.getChildren().add(tempButtThread);
        anchorPane.getChildren().add(tempButtGasType);
        anchorPane.getChildren().add(leftFirstPipe);
        anchorPane.getChildren().add(rightFirstPipe);
        anchorPane.getChildren().add(progressBar);
        anchorPane.getChildren().add(leftScrewBlock);
        anchorPane.getChildren().add(rightScrewBlock);
        anchorPane.getChildren().add(valvePipe1);
        anchorPane.getChildren().add(valvePipe2);
        anchorPane.getChildren().add(donut);
        anchorPane.getChildren().add(pivot);
        anchorPane.getChildren().add(text);

    }
    private void turnON(){
        pump.turnOn();
        System.out.println(gas.isOnOff());
        System.out.println("on");
        timeline.play();
    }
    private void turnOF(){
        pump.turnOf();
        System.out.println(gas.isOnOff());
        System.out.println("off");
        timeline.stop();
    }
    private void setGasType(){
        Random random = new Random();
        int gasType = random.nextInt(3) + 1;
        pump.setGasType(gasType);
    }
    private void startThread(Circle butt){
        if(flowmeter.connected()) {
            Thread thread = new Thread(flowmeter);
            thread.start();
            System.out.println("on");
            Thread thread1 = new Thread(pump);
            thread1.start();
            Thread thread2 = new Thread(guiHelper);
            thread2.start();
            butt.setOnMouseClicked(event -> nothing() );

        }else {
            System.out.println("wait");
        }
    }
    private void nothing(){

    }

    /**
     * This changes the rate of how the tank is filled
     * @param value the new pace the fuel is se at, default is .5
     */
    private void changeRate(double value){
        timeline.setRate(value);
    }

    /**
     * So this class creates the pane and controls the look of the animation
     */
    class FuelTank {
        private final Pane tankPane;
        private final Rectangle tankBody;
        private final Rectangle fuel;
        private final DoubleProperty fuelLevel;
        // 0-100 so we can change this if the thing tells us the percent

        public FuelTank(double width, double height, double arc, double yOffset,int initialValue) {
            fuelLevel = new SimpleDoubleProperty(0);
            tankPane = new Pane();
            tankPane.setBackground(Background.fill(Color.BLACK));

            tankBody = new Rectangle(width, height);
            tankBody.setArcWidth(arc);
            tankBody.setArcHeight(arc);
            tankBody.setFill(Color.RED);
            tankBody.setStroke(Color.MAROON);
            tankBody.setStrokeWidth(4);
            tankBody.setStrokeType(StrokeType.INSIDE);
            tankBody.setY(yOffset);
            fuel = new Rectangle(width - 8, height - 8);
            fuel.setX(4);
            fuel.setY(yOffset + height - 4 - (height - 8));
            fuel.setArcWidth(arc);
            fuel.setArcHeight(arc);
            fuel.setFill(new LinearGradient(
                    0, 0, 0, 1, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
                    new Stop(0, new Color(.81, .42, .04, 1)), new Stop(1, new Color(.158, .79, .08, 1)))
            );
            Rectangle emptyPart = new Rectangle(width - 8, height - 8);
            emptyPart.setX(4);
            emptyPart.setY(yOffset + 4);
            emptyPart.setArcWidth(arc);
            emptyPart.setArcHeight(arc);
            fuel.setClip(emptyPart);

            fuel.heightProperty().bind(fuelLevel.divide(100).multiply(height - 8));
            fuel.yProperty().bind(tankBody.yProperty()
                    .add(tankBody.heightProperty())
                    .subtract(fuel.heightProperty())
                    .subtract(4));


            Circle cap = new Circle(width / 2, yOffset + 4, 15);
            cap.setFill(Color.MAROON);
            cap.setStroke(Color.BLACK);


            QuadCurve hose = new QuadCurve();
            hose.setStartX(width / 2);
            hose.setStartY(yOffset + 4);
            hose.setControlX(width / 2 - 60);
            hose.setControlY(yOffset - 80);
            hose.setEndX(width / 2 - 30);
            hose.setEndY(yOffset - 30);
            hose.setStroke(Color.valueOf("CDCB1AFF"));
            hose.setStrokeWidth(10);
            hose.setFill(null);

            Circle nozzle = new Circle(hose.getEndX(), hose.getEndY(), 7);
            nozzle.setFill(Color.valueOf("CDCB1AFF"));
            nozzle.setStroke(Color.valueOf("CDCB1AFF"));
            tankPane.getChildren().addAll(hose, tankBody, fuel, cap, nozzle);
        }

        public DoubleProperty fuelLevelProperty() {
            return fuelLevel;
        }

        public Pane getTankPane() {
            return tankPane;
        }
    }
}
