import legacy.IOPort;
import legacy.IOPortServerLegacy;
import util.PortAddresses;


/**
 * Authors: Youssef, Valerie, Joel, Natalie, Danny
 * This is the main class
 */

public class Main {
    public static void main(String[] args) {
        IOPort statusIshtest = new IOPortServerLegacy("localhost", PortAddresses.PUMP_PORT);
        statusIshtest.send("PING");

    }
}

