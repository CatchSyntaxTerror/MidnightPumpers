package components;

import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
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
    private CommunicatorServer communicatorServer;
    private Thread flowThread;
    private Rotate rotate1;
    private Rotate rotate2;
    private double startR1 = 45;
    private double startR2 = 135;
    public Flowmeter(){
        this.communicatorServer = new CommunicatorServer(PortAddresses.FLOW_METER_PORT);
        this.text = new Text("0000");
        this.rotate1 = new Rotate();
        this.rotate2 = new Rotate();
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
        int i = 0;
        while(this.communicatorServer.ON) {
//            String message = this.communicatorServer.get().toString();
//            if(message.equals("SOMETHING THAT MEANS RESET")){
//                gasFlow = 0;
//            }
            if (gas.isOnOff()) {
                gasFlow += 1;
                updatedText();
                rotate1.setAngle(startR1 + 1);
                rotate2.setAngle(startR2 + 1);
                startR1 += 1;
                startR2 += 1;
                if (startR1 > 360) {
                    startR1 = 0;
                }
                if (startR2 > 360) {
                    startR2 = 0;
                }
                //this.actuator.send("");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
