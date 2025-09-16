package components;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import oldShitGarbage.*;
import util.*;

/**
 * This class is a device that checks to see if the hose is connected to the
 * pump, car, or not connected and sends this information to main
 * Author: Danny Thompson
 */
public class Nuzzle {
    private Line line;
    private Circle cirHead;
    private Circle cirEnd;
    private boolean conCar;
    private boolean conPump;
    private Shit actuator;

    public Nuzzle() {
        this.actuator = new Shit(PortAddresses.HOSE_PORT);
        this.line = null;
        this.cirHead = null;
        this.cirEnd = null;
        this.conPump = true;
        this.conCar = false;
        Thread pumpThread = new Thread(actuator);
        pumpThread.start();
    }

    /*
    //TODO : send proper message for actuator
    The hose is not connected
     */
    public void setNotCon(){
        this.conPump = false;
        this.conCar = false;
        this.line.setStroke(Color.RED);
        //this.actuator.send("");
    }

    /*
    //TODO : send proper message for actuator
    The hose is connected to car
     */
    public void setConCar() {
        this.conCar = true;
        this.conPump = false;
        this.line.setStroke(Color.GREEN);
        //this.actuator.send("");
    }

    /*
    //TODO : send proper message for actuator
    The hose is connected to pump
     */
    public void setConPump() {
        this.conPump = true;
        this.conCar = false;
        this.line.setStroke(Color.GREENYELLOW);
        //this.actuator.send("");
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public void setCirEnd(Circle cirEnd) {
        this.cirEnd = cirEnd;
    }
    public void changHeadPos(double x, double y){
        this.cirHead.setCenterX(x);
        this.cirHead.setCenterY(y);
    }
    public void changLinePos(double x, double y){
        this.line.setEndX(x);
        this.line.setEndY(y);
    }

    public void setCirHead(Circle cirHead) {
        this.cirHead = cirHead;
    }
}
