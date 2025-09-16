package components;

import IOPort.Actuator;
import util.PortAddresses;

public class Nozzle2 {
    private Actuator actuator;

    /**
     * Just creates an actuator thread
     */
    public Nozzle2() {
        actuator = new Actuator(PortAddresses.HOSE_PORT);
        Thread pumpThread = new Thread(actuator);
        pumpThread.start();
    }

    public void sendConnectionInfo(boolean connected){
        if(connected){
            actuator.send("Connected");
        }else{
            actuator.send("Not Connected");
        }
    }

}
