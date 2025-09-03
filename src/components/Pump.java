package components;

import javafx.scene.shape.Rectangle;
import sim.Gas;
import IOPort.*;
import util.*;

/**
 * This class is a device that turns on and off the gas flow based of the
 * messages it recieves from main
 * Author: Danny Thompson
 */
public class Pump implements Runnable {
    private Rectangle rectangle;
    private Gas gas;
    private Status status;
    public Pump(){
        this.status = new Status(PortAddresses.PUMP_PORT);
        this.gas = null;
        this.rectangle = null;
        Thread pumpT = new Thread(this.status);
        pumpT.start();
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }
    public void setGas(Gas gas) {
        this.gas = gas;
    }
    public void turnOn(){
        this.gas.turnOnGas();
    }
    public void turnOf(){
        this.gas.turnOffGas();
    }

    /*
    //TODO needs to read it's status so that it can turn on the pump needs a
       proper message to be able to read
     */
    @Override
    public void run() {
        while (status.ON) {
            String message = this.status.read().toString();
            if(message.equals("SOMETHING THAT MEANS ON")){
                turnOn();
            } else if (message.equals("SOMETHING THAT MEANS OFF")) {
                turnOf();
            }
        }
    }
}
