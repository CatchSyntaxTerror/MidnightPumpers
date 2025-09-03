package UI;

import components.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sim.Gas;

public class GasStation extends Application {
    private static final AnchorPane anchorPane = new AnchorPane();

    //Calles all the constructors for the devices
    private Nuzzle nuzzle = new Nuzzle();
    private Flowmeter flowmeter = new Flowmeter();
    private Pump pump = new Pump();
    private CCReader ccReader = new CCReader();
    private CardCompany cardCompany = new CardCompany();

    //Threads for the devices that need them
    private Thread pumpThread = new Thread(pump);
    private Thread flowMeterThread = new Thread(flowmeter);
    private Thread cardCompanyThread = new Thread(cardCompany);

    //Sim gas
    private Gas gas = new Gas();

    /*
    Creates all devices and starts the threads for the devices that have them
    Makes a gui to simulate the station
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.pump.setGas(gas);
        this.flowmeter.setGas(gas);
        this.pumpThread.start();
        this.flowMeterThread.start();
        this.cardCompanyThread.start();
        Group root = new Group();
        anchorPane.setPrefHeight(750);
        anchorPane.setPrefWidth(750);
        anchorPane.setBackground(Background.fill(Color.BLACK));
        makeAnchor();
        root.getChildren().add(anchorPane);
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Station");
        primaryStage.show();
    }

    //Creates the Anchor pain
    private void makeAnchor() {
        double headX = 500;
        double heady = 360;
        double endX = 500;
        double endY = 625;
        double carSlotX = 280;
        double carSlotY = 550;
        double r = 10;

        //Button that cause the hose to not be connected
        AnchorPane buttPlane = new AnchorPane();
        buttPlane.setPrefHeight(750);
        buttPlane.setPrefWidth(750);
        buttPlane.setOnMouseClicked(event -> hoseNotConnected(this.nuzzle));

        Rectangle screen = new Rectangle(515,400,100, 50);
        screen.setFill(Color.WHITE);
        //screen.setOnMouseClicked(event -> textFlow(this.flowmeter));
        Text flowNum = new Text(525,430,"0000");
        flowNum.setFont(Font.font(35));

        Rectangle frontWindow = new Rectangle(165,400,100,50);
        frontWindow.setFill(Color.BLUE);
        frontWindow.setStroke(Color.BLACK);

        Rectangle backWindow = new Rectangle(165,550,100,50);
        backWindow.setFill(Color.BLUE);
        backWindow.setStroke(Color.BLACK);

        Rectangle wheelTL = new Rectangle(125,360,50,50);
        wheelTL.setFill(Color.DARKGRAY);

        Rectangle wheelTR = new Rectangle(255,360,50,50);
        wheelTR.setFill(Color.DARKGRAY);

        Rectangle wheelBL = new Rectangle(125,550,50,50);
        wheelBL.setFill(Color.DARKGRAY);

        Rectangle wheelBR = new Rectangle(255,550,50,50);
        wheelBR.setFill(Color.DARKGRAY);

        //Button to connect hose to pump by clicking on the pump rect
        Rectangle pump = new Rectangle(500,340,130,300);
        pump.setFill(Color.GREY);
        pump.setOnMouseClicked(event -> hosetoPump(this.nuzzle,endX,endY));

        //Button to give card input
        Rectangle cardReader = new Rectangle(550,500,50,50);
        cardReader.setFill(Color.PURPLE);
        cardReader.setOnMouseClicked(event -> sendCard());

        //Button to connect hose to car
        Rectangle car = new Rectangle(150,340,130,300);
        car.setFill(Color.RED);
        car.setOnMouseClicked(event -> hosetoCar(this.nuzzle,carSlotX,carSlotY));

        Circle cirEnd = new Circle(endX,endY,r,Color.GREEN);
        Circle cirHead = new Circle(headX,heady,r,Color.YELLOW);
        Circle pumpSlot = new Circle(endX,endY,r,Color.LIGHTYELLOW);
        Circle carSlot = new Circle(carSlotX,carSlotY,r,Color.GREY);

        Line hose = new Line(headX,heady,endX,endY);
        hose.setStroke(Color.GREENYELLOW);
        hose.setStrokeWidth(5);

        this.flowmeter.setText(flowNum);
        this.pump.setRectangle(pump);
        this.nuzzle.setCirEnd(cirEnd);
        this.nuzzle.setCirHead(cirEnd);
        this.nuzzle.setLine(hose);

        anchorPane.getChildren().add(buttPlane);
        anchorPane.getChildren().add(wheelTL);
        anchorPane.getChildren().add(wheelTR);
        anchorPane.getChildren().add(wheelBL);
        anchorPane.getChildren().add(wheelBR);
        anchorPane.getChildren().add(car);
        anchorPane.getChildren().add(frontWindow);
        anchorPane.getChildren().add(backWindow);
        anchorPane.getChildren().add(carSlot);
        anchorPane.getChildren().add(pump);
        anchorPane.getChildren().add(cardReader);
        anchorPane.getChildren().add(screen);
        anchorPane.getChildren().add(flowNum);
        anchorPane.getChildren().add(hose);
        anchorPane.getChildren().add(pumpSlot);
        anchorPane.getChildren().add(cirEnd);
        anchorPane.getChildren().add(cirHead);
    }

    //TODO might need more functionality to prevent card reader from reading
    // when a card is not asked for
    private void sendCard() {
        this.ccReader.sendCard();
    }

    //Is a throw away function to test if I could change the text for pump
    private void textFlow(Flowmeter flowmeter) {
        for(int i = 0; i< 20;i++){
            flowmeter.checkingFlow();
        }
    }

    private void hosetoPump(Nuzzle nuzzle,double x, double y){
        System.out.println("hose is on pump");
        nuzzle.setConPump();
        nuzzle.changHeadPos(x,y);
        nuzzle.changLinePos(x,y);
    }
    private void hosetoCar(Nuzzle nuzzle,double x, double y){
        System.out.println("hose is on car");
        nuzzle.setConCar();
        nuzzle.changHeadPos(x,y);
        nuzzle.changLinePos(x,y);
    }
    private void hoseNotConnected(Nuzzle nuzzle){
        System.out.println("hose is not connected");
        nuzzle.setNotCon();
        nuzzle.changHeadPos(400,600);
        nuzzle.changLinePos(400,600);
    }
}
