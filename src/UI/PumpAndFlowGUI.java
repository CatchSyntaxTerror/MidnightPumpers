package UI;

import components.Flowmeter;
import components.Pump;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import sim.Gas;

public class PumpAndFlowGUI extends Application {
    private static final AnchorPane anchorPane = new AnchorPane();
    private Flowmeter flowmeter;
    private Pump pump;
    private Gas gas = new Gas();
    @Override
    public void start(Stage primaryStage) throws Exception {
        flowmeter = new Flowmeter();
        pump = new Pump();
        flowmeter.setGas(gas);
        pump.setGas(gas);
        Group root = new Group();
        anchorPane.setPrefHeight(750);
        anchorPane.setPrefWidth(1000);
        anchorPane.setBackground(Background.fill(Color.BLACK));
        makeAnchor();
        root.getChildren().add(anchorPane);
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

        flowmeter.setRotate1(rotate1);
        flowmeter.setRotate2(rotate2);
        flowmeter.setProgressBar(progressBar);
        flowmeter.setText(text);


        valvePipe1.getTransforms().add(rotate1);
        valvePipe2.getTransforms().add(rotate2);

        anchorPane.getChildren().add(tempButtOn);
        anchorPane.getChildren().add(tempButtOff);
        anchorPane.getChildren().add(tempButtThread);
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
    }
    private void turnOF(){
        pump.turnOf();
        System.out.println(gas.isOnOff());
        System.out.println("off");
    }
    private void startThread(Circle butt){
        if(flowmeter.connected()) {
            Thread thread = new Thread(flowmeter);
            thread.start();
            System.out.println("on");
            butt.setOnMouseClicked(event -> nothing() );
        }else {
            System.out.println("wait");
        }
    }
    private void nothing(){

    }
}
