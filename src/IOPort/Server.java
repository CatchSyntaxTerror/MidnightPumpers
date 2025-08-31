package IOPort;

import util.PortAddresses;

import java.util.*;

/**
 * This class is a wrapper for Threads. It starts the server threads
 * Allows other classes to call send(), get(), read() on server/client IOPorts
 * Author: Danny Phantom, Natalie Onion, Youssef Amin
 */
public class Server {
    private IOPortClient nozzle;
    private IOPortClient flowMeter;
    private IOPortServer pump;
    private Map<UUID, IOPortServer> serverMap;
    private Map<UUID, IOPortClient> clientMap;

    public Server(Map<UUID, IOPortServer> serverMap, Map<UUID, IOPortClient> clientMap) {
        this.serverMap = serverMap;
        this.clientMap = clientMap;
    }

    /**
     * Allows server to send messages through IOPort
     * @param message The message to be sent
     * @param ID UUID of Server that sends the message
     */
    public void send(String message, UUID ID) {
        IOPortServer server = serverMap.get(ID);
        if (server != null) {
            server.send(message);
        } else {
            clientMap.get(ID).send(message);
        }

    }

    /**
     * Obtains value from server/client BlockingQueue
     * Does remove from BlockingQueue
     * @param ID UUID of server/client
     * @return Message
     */
    public Object get(UUID ID) {
        IOPortServer server = serverMap.get(ID);
        if (server != null) {
            return server.get();
        } else {
            return clientMap.get(ID).get();
        }
    }

    /**
     * Allows for a peek at a servers BlockingQueue
     * Does not remove from IOPort BlockingQueue
     * @param ID UUID of a server/client
     * @return Message
     */
    public Object read(UUID ID) {
        IOPortServer server = serverMap.get(ID);
        if (server != null) {
            return server.read();
        } else {
            return clientMap.get(ID).read();
        }
    }

    /**
     * Starts each server/client thread
     */
    public void startUp() {
        for (IOPortServer server : serverMap.values()) {
            if (server instanceof Actuator actuator) {
                Thread thread = new Thread(actuator);
                thread.start();
            } else if (server instanceof CommunicatorServer comSer) {
                Thread thread = new Thread(comSer);
                thread.start();
            }
        }
        for (IOPortClient client : clientMap.values()) {
            if (client instanceof Status status) {
                Thread thread = new Thread(status);
                thread.start();
            } else if (client instanceof CommunicatorClient comCli) {
                Thread thread = new Thread(comCli);
                thread.start();
            }
        }
    }
}
