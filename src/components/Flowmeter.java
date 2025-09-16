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
    private Text text;
    private Gas gas;
    private double gasFlow;
    private Shit6 communicatorServer;
    private Thread flowThread;
    private Rotate rotate1;
    private Rotate rotate2;
    private ProgressBar progressBar;
    private double startR1 = 45;
    private double startR2 = 135;
    public Flowmeter(){
        this.communicatorServer = new Shit6(PortAddresses.FLOW_METER_PORT);
        this.text = new Text("00.00");
        this.rotate1 = new Rotate();
        this.rotate2 = new Rotate();
        this.progressBar = null;
        this.gas = null;
        this.gasFlow = 0;
        this.flowThread = new Thread(this.communicatorServer);
        flowThread.start();
    }

    public boolean connected(){
        return !communicatorServer.notConnected;
    }
    public void setRotate1(Rotate rotate1) {
        this.rotate1 = rotate1;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public void setRotate2(Rotate rotate2) {
        this.rotate2 = rotate2;
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
        this.text.setText(String.valueOf(String.format("%.2f", gasFlow)));
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
                updatedText();
                rotate1.setAngle(startR1 + 3);
                rotate2.setAngle(startR2 + 3);
                startR1 += 3;
                startR2 += 3;
                if(progressBar.getProgress()<= 1.0)
                progressBar.setProgress(progressBar.getProgress() + 0.1);
                if (startR1 > 360) {
                    startR1 = 0;
                }
                if (startR2 > 360) {
                    startR2 = 0;
                }
                //this.actuator.send("");
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }else if(progressBar.getProgress() > 0.1){
                progressBar.setProgress(progressBar.getProgress()-0.1);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
