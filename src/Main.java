import IOPort.Actuator;
import IOPort.IOPortClient;
import IOPort.IOPortServer;
import IOPort.Status;
import sim.Gas;
import util.PortAddresses;

/**
 * Authors: Youssef, Valerie, Joel, Natalie, Danny
 * This is the main class
 */

public class Main {
    public static void main(String[] args) {
        IOPortClient nozzle = new Status(PortAddresses.HOSE_PORT);
        IOPortClient flowMeter = new Status(PortAddresses.FLOW_METER_PORT);
        IOPortServer pump = new Actuator(PortAddresses.PUMP_PORT);

        Server server = new Server(nozzle, flowMeter, pump);
        server.startUp();
        server.connect();
    }
}