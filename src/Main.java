import NewIOPort.IOPort;
import NewIOPort.IOPortClient;
import NewIOPort.IOPortServer;
import NewIOPort.PortAddresses;


/**
 * Authors: Youssef, Valerie, Joel, Natalie, Danny
 * This is the main class
 */

public class Main {
    public static void main(String[] args) {
        IOPort statusIshtest = new IOPortServer("localhost", PortAddresses.PUMP_PORT);
        statusIshtest.send("PING");

    }
}

