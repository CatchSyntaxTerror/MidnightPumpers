package components;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import IOPort.*;
import util.*;

public class Nuzzle {
    private Line line;
    private Circle cirHead;
    private Circle cirEnd;
    private boolean conCar;
    private boolean conPump;
    private Actuator actuator;

    public Nuzzle() {
        this.actuator = new Actuator(PortAddresses.HOSE_PORT);
        this.line = null;
        this.cirHead = null;
        this.cirEnd = null;
        this.conPump = true;
        this.conCar = false;
        Thread pumpThread = new Thread(actuator);
        pumpThread.start();
    }

    public void setNotCon(){
        this.conPump = false;
        this.conCar = false;
        this.line.setStroke(Color.RED);
    }
    public void setConCar() {
        this.conCar = true;
        this.conPump = false;
        this.line.setStroke(Color.GREEN);
    }

    public void setConPump() {
        this.conPump = true;
        this.conCar = false;
        this.line.setStroke(Color.GREENYELLOW);
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
