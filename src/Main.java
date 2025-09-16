import oldShitGarbage.Shit;
import oldShitGarbage.Shit5;
import oldShitGarbage.Shit3;
import oldShitGarbage.*;
import util.PortAddresses;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Authors: Youssef, Valerie, Joel, Natalie, Danny
 * This is the main class It manages communication for all components of the Gas Pump
 */

public class Main {
    private static Shit3 PUMP;
    private static Shit3 SCREEN;
    private static Shit3 CARD_COMPANY;
    private static Shit3 GAS_STATION;
    private static Shit5 CC_READER;
    private static Shit5 NOZZLE;
    private static Shit5 FLOWMETER;
    private static Map<UUID, Shit3> ioPortServers;
    private static Map<UUID, Shit5> ioPortClients;
    private static Shit4 SERVER;

    /**
     * Instantiates server Runnables for Server class
     * @return HashMap of runnables to be passed to Server
     */
    private static Map<UUID, Shit3> initializeServers(){
        PUMP = new Shit(PortAddresses.PUMP_PORT);
        SCREEN = new Shit6(PortAddresses.SCREEN_PORT);
        CARD_COMPANY =new Shit6(PortAddresses.CARD_COMPANY_PORT);
        //GAS_STATION = new CommunicatorServer(PortAddresses.GAS_STATION_PORT);
        Map<UUID, Shit3> ioPortServers = new HashMap<>();
        ioPortServers.put(PUMP.SERVER_UUID, PUMP);
        ioPortServers.put(SCREEN.SERVER_UUID, SCREEN);
        ioPortServers.put(CARD_COMPANY.SERVER_UUID, CARD_COMPANY);
        //ioPortServers.put(GAS_STATION.SERVER_UUID, GAS_STATION);
        return ioPortServers;
    }

    /**
     * Instatiates client Runnables for Server class
     * @return HashMaps of Runnables to be passed to Server
     */
    private static Map<UUID, Shit5> initializeClients(){
        CC_READER = new Shit2(PortAddresses.CARD_READER_PORT);
        NOZZLE = new Shit2(PortAddresses.HOSE_PORT);
        FLOWMETER = new Shit2(PortAddresses.FLOW_METER_PORT);
        Map<UUID, Shit5> ioPortClients = new HashMap<>();
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
        ioPortClients = initializeClients();
        ioPortServers = initializeServers();
        SERVER = new Shit4(ioPortServers, ioPortClients);
        SERVER.startUp();

        boolean connected = false;
        while (!connected){
            connected = SERVER.doneConnecting();
        }

        System.out.println("Done connecting");

    }
}