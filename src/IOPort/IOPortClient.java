package IOPort;

import java.io.*;
import java.net.Socket;
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
    BufferedReader LISTENER;
    PrintWriter WRITER;
    boolean ON;

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

    protected void connect(int n) {
        try {
            CLIENT_SOCKET=new Socket(HOST, PORT);


        }catch (Exception e){
            try {
                Thread.sleep(1000);
                if(n<15){
                    connect(n+1);
                }

            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }

        }

    }
}


