import IOPort.Actuator;
import IOPort.IOPortClient;
import IOPort.IOPortServer;
import IOPort.*;
import sim.Gas;
import util.PortAddresses;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Authors: Youssef, Valerie, Joel, Natalie, Danny
 * This is the main class It manages communication for all components of the Gas Pump
 */

public class Main {
    private static IOPortServer PUMP;
    private static IOPortServer SCREEN;
    private static IOPortServer CARD_COMPANY;
    private static IOPortServer GAS_STATION;
    private static IOPortClient CC_READER;
    private static IOPortClient NOZZLE;
    private static IOPortClient FLOWMETER;
    private static Map<UUID, IOPortServer> ioPortServers;
    private static Map<UUID, IOPortClient> ioPortClients;
    private static Server SERVER;

    /**
     * Instantiates server Runnables for Server class
     * @return HashMap of runnables to be passed to Server
     */
    private static Map<UUID, IOPortServer> initializeServers(){
        PUMP = new Actuator(PortAddresses.PUMP_PORT);
        SCREEN = new CommunicatorServer(PortAddresses.SCREEN_PORT);
        CARD_COMPANY = new CommunicatorServer(PortAddresses.CARD_COMPANY_PORT);
        GAS_STATION = new CommunicatorServer(PortAddresses.GAS_STATION_PORT);
        Map<UUID,IOPortServer> ioPortServers = new HashMap<>();
        ioPortServers.put(PUMP.SERVER_UUID, PUMP);
        ioPortServers.put(SCREEN.SERVER_UUID, SCREEN);
        ioPortServers.put(CARD_COMPANY.SERVER_UUID, CARD_COMPANY);
        ioPortServers.put(GAS_STATION.SERVER_UUID, GAS_STATION);
        return ioPortServers;
    }

    /**
     * Instatiates client Runnables for Server class
     * @return HashMaps of Runnables to be passed to Server
     */
    private static Map<UUID,IOPortClient> initializeClients(){
        CC_READER = new Status(PortAddresses.CARD_READER_PORT);
        NOZZLE = new Status(PortAddresses.HOSE_PORT);
        FLOWMETER = new Status(PortAddresses.FLOW_METER_PORT);
        Map<UUID,IOPortClient> ioPortClients = new HashMap<>();
        ioPortClients.put(CC_READER.CLIENT_UUID, CC_READER);
        ioPortClients.put(NOZZLE.CLIENT_UUID, NOZZLE);
        ioPortClients.put(FLOWMETER.CLIENT_UUID, FLOWMETER);
        return ioPortClients;
    }

    /**
     * This is where the magic happens
     * @param args N/A
     */
    public static void main(String[] args) {
        ioPortServers = initializeServers();
        ioPortClients = initializeClients();
        SERVER = new Server(ioPortServers, ioPortClients);
    }
}