package IOPort;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;

/**
 * This class is meant to be instantiated by JavaFX applications
 * Author: Youssef Amin, Natalie Onion
 */
public abstract class IOPortClient {
    public Socket CLIENT_SOCKET;
    protected int PORT;
    protected String HOST;
    protected BlockingQueue<String> INBOX;
    public BufferedReader LISTENER;
    public PrintWriter WRITER;
    boolean ON;
    public UUID CLIENT_UUID;

    public IOPortClient(int port) {
        this.PORT = port;
        this.HOST = "localhost";
    }

    /**
     * just to make sure all sockets are closed appropriately
     */
    public abstract void close();

    /**
     * these methods allow users to send receive and peak at messages
     */
    public abstract void send(String message);

    public abstract Object get();

    public abstract Object read();

    public abstract void receive();
}


