package components;

import IOPort.Actuator;
import util.PortAddresses;

public class CCReader {
    private Actuator actuator;
    public CCReader(){
        this.actuator = new Actuator(PortAddresses.CARD_READER_PORT);
        Thread thread = new Thread(this.actuator);
        thread.start();
    }
    /*
    //TODO : send proper message for actuator
    car info
     */
    public void sendCard(){
        this.actuator.send("");
    }
}
