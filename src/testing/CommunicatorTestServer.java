package testing;

import IOPort.CommunicatorServer;
import util.PortAddresses;

/**
 * Tests if communicator server can send and receive message
 */
public class CommunicatorTestServer {
    public static void main(String[] args) throws InterruptedException {
        CommunicatorServer pump = new CommunicatorServer(PortAddresses.PUMP_PORT);
        Thread pumpThread = new Thread(pump);
        pumpThread.start();
        pump.send("I like pizza");
        System.out.println("I like pizza");
        String message = pump.get();
        if (message.equals("No way me too!")){
            Thread.sleep(2000);
            System.out.println("Sweet");
        } else {
            System.out.println("You suck!");
        }
    }
}
