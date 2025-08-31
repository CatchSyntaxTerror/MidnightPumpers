package testing;

import IOPort2.CommunicatorClient;
import util.PortAddresses;

/**
 * Tests if communicator client can send and receive messages
 */
public class CommunicatorTestClient {
    public static void main(String[] args) throws InterruptedException {
        CommunicatorClient pumpClient = new CommunicatorClient(PortAddresses.TEST_COMMUNICATOR_PORT);
        Thread pumpThread = new Thread(pumpClient);
        pumpThread.start();
        String message = pumpClient.get();
        if (message.equals("I like pizza")){
            Thread.sleep(3000);
            pumpClient.send("No way me too!");
            System.out.println("No way me too!");
        }
    }
}
