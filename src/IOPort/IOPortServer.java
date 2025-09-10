package IOPort;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class is meant to be instantiated by main
 * Author: Youssef Amin, Natalie Onion
 */
public abstract class IOPortServer {
    protected ServerSocket SERVER_SOCKET;
    public Socket CLIENT_SOCKET;
    protected int PORT;
    protected String HOST;
    protected BlockingQueue<String> INBOX;
    public BufferedReader LISTENER;
    public PrintWriter WRITER;
    public volatile boolean ON;
    public UUID SERVER_UUID;
    public boolean notConnected = true;

    public IOPortServer(int port) {
        this.PORT = port;
        this.HOST = "localhost";
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
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
