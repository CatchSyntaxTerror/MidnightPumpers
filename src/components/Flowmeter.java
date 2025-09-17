package components;

import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import sim.Gas;
import oldShitGarbage.*;
import util.*;

/**
 * This class is a device that tracks how much gas has been filled and
 * sends messages to main to update the flow value
 * Author: Danny Thompson
 */
public class Flowmeter implements Runnable {
    private Gas gas;
    private double gasFlow;
    private Shit6 communicatorServer;
    private Thread flowThread;

    public Flowmeter(){
        this.communicatorServer = new Shit6(PortAddresses.FLOW_METER_PORT);
        this.gas = null;
        this.gasFlow = 0;
        this.flowThread = new Thread(this.communicatorServer);
        flowThread.start();
    }

    public boolean connected(){
        return !communicatorServer.notConnected;
    }

    public double getGasFlow() {
        return gasFlow;
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
        int i = 0;
        while(this.communicatorServer.ON) {

            if(communicatorServer.get() != null) {
                gasFlow = 0;
            }

            if (gas.isOnOff()) {
                gasFlow += 0.02;
                //this.actuator.send("");
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
