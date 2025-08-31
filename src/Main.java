import legacy.IOPort;
import legacy.IOPortServer;
import util.PortAddresses;


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

