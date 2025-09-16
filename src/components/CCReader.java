package components;

import oldShitGarbage.Shit;
import util.PortAddresses;

/**
 * This class is a device that takes in a card and sends it to main to be
 * sent to card company to be processed
 * Author: Danny Thompson
 */
public class CCReader {
    private Shit actuator;
    public CCReader(){
        this.actuator = new Shit(PortAddresses.CARD_READER_PORT);
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
