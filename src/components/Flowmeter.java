package components;

import javafx.scene.text.Text;
import sim.Gas;
import IOPort.*;
import util.*;

/**
 * This class is a device that tracks how much gas has been filled and
 * sends messages to main to update the flow value
 * Author: Danny Thompson
 */
public class Flowmeter implements Runnable {
    private Text text;
    private Gas gas;
    private int gasFlow;
    private Actuator actuator;
    private Thread flowThread;
    public Flowmeter(){
        this.actuator = new Actuator(PortAddresses.FLOW_METER_PORT);
        this.text = new Text("0000");
        this.gas = null;
        this.gasFlow = 0;
        this.flowThread = new Thread(this.actuator);
        flowThread.start();
    }

    public void setText(Text text) {
        this.text = text;
    }
    public void checkingFlow(){
        if(this.gas.isOnOff()){
            updatedText();
        }
    }
    private void updatedText(){
        this.text.setText(String.valueOf(this.gasFlow));
    }
    public void setGas(Gas gas) {
        this.gas = gas;
    }

    /*
    //TODO : send proper message for actuator
    This function checks to see if the gas is flowing and updates the flowmeter
     */
    @Override
    public void run() {
        while (this.gas.isOnOff()){
            gasFlow += 1;
            updatedText();
            //this.actuator.send("");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
