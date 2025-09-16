package testing;

import oldShitGarbage.Shit1;
import util.PortAddresses;

/**
 * Tests if communicator client can send and receive messages
 */
public class CommunicatorTestClient {
    public static void main(String[] args) throws InterruptedException {
        Shit1 pumpClient = new Shit1(PortAddresses.PUMP_PORT);
        Thread pumpThread = new Thread(pumpClient);
        pumpThread.start();
        String message = pumpClient.get().toString();
        if (message.equals("I like pizza")){
            Thread.sleep(2000);
            pumpClient.send("No way me too!");
            System.out.println("No way me too!");
        }
    }
}
