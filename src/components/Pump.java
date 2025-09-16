package components;

import javafx.scene.control.ProgressBar;
import javafx.scene.shape.Rectangle;
import sim.Gas;
import oldShitGarbage.*;
import util.*;

/**
 * This class is a device that turns on and off the gas flow based of the
 * messages it recieves from main
 * Author: Danny Thompson
 */
public class Pump implements Runnable {
    private Rectangle rectangle;
    private Gas gas;
    private Shit2 status;
    private int gasType = 1;
    private ProgressBar progressBar;
    public Pump(){
        this.status = new Shit2(PortAddresses.PUMP_PORT);
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
    public void setGasType(int gasType){
        this.gasType = gasType;
        setProgressBarColor();
    }
    public void setProgressBarColor(){
        if(gasType == 1) {
            progressBar.setStyle("-fx-accent: brown;");
        }else if (gasType == 2) {
            progressBar.setStyle("-fx-accent: yellow;");
        }else{
            progressBar.setStyle("-fx-accent: green;");
        }
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    /*
        //TODO needs to read it's status so that it can turn on the pump needs a
           proper message to be able to read
         */
    @Override
    public void run() {
        while (status.ON) {
            Object inbox = this.status.read();
            if(inbox != null) {
                String message = this.status.read().toString();
                if (message.equals("SOMETHING THAT MEANS ON")) {
                    turnOn();
                } else if (message.equals("SOMETHING THAT MEANS OFF")) {
                    turnOf();
                } else if (message.equals("SOMETHING THAT MEANS GASTYPE")) {
                    setGasType(message.charAt(1)); // will need update
                }
            }
        }
    }
}
