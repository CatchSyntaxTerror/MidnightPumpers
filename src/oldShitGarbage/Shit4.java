package oldShitGarbage;

import java.util.*;

/**
 * This class is a wrapper for Threads. It starts the server threads
 * Allows other classes to call send(), get(), read() on server/client IOPorts
 * Author: Danny Phantom, Natalie Onion, Youssef Amin
 */
public class Shit4 {
    private Shit5 nozzle;
    private Shit5 flowMeter;
    private Shit3 pump;
    private Map<UUID, Shit3> serverMap;
    private Map<UUID, Shit5> clientMap;

    public Shit4(Map<UUID, Shit3> serverMap, Map<UUID, Shit5> clientMap) {
        this.serverMap = serverMap;
        this.clientMap = clientMap;
    }

    /**
     * Allows server to send messages through IOPort
     * @param message The message to be sent
     * @param ID UUID of Server that sends the message
     */
    public void send(String message, UUID ID) {
        Shit3 server = serverMap.get(ID);
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
        Shit3 server = serverMap.get(ID);
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
        Shit3 server = serverMap.get(ID);
        if (server != null) {
            return server.read();
        } else {
            return clientMap.get(ID).read();
        }
    }

    public boolean doneConnecting(){
        boolean clientDone = true;
        boolean serverDone = true;

        for (Shit5 client : clientMap.values()){
            clientDone = clientDone && !client.notConnected;
        }
        for (Shit3 server : serverMap.values()){
            serverDone = serverDone && !server.notConnected;
        }
        return clientDone && serverDone;
    }

    /**
     * Starts each server/client thread
     */
    public void startUp() {
        for (Shit3 server : serverMap.values()) {
            if (server instanceof Shit actuator) {
                Thread thread = new Thread(actuator);
                thread.start();
            } else if (server instanceof Shit6 comSer) {
                Thread thread = new Thread(comSer);
                thread.start();
            }
        }
        for (Shit5 client : clientMap.values()) {
            if (client instanceof Shit2 status) {
                Thread thread = new Thread(status);
                thread.start();
            } else if (client instanceof Shit1 comCli) {
                Thread thread = new Thread(comCli);
                thread.start();
            }
        }
    }
}
