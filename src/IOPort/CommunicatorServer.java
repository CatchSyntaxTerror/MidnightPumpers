package IOPort;

import java.io.*;
import java.net.ServerSocket;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Communicator class is an instance of both
 * Author: Youssef Amin, Natalie Onion
 */
public class CommunicatorServer extends IOPortServer implements Runnable {

    public CommunicatorServer(int port) {
        super(port);
        try {
            SERVER_SOCKET = new ServerSocket(port);
            CLIENT_SOCKET = SERVER_SOCKET.accept();
            LISTENER = new BufferedReader(new InputStreamReader(CLIENT_SOCKET.getInputStream()));
            WRITER = new PrintWriter(CLIENT_SOCKET.getOutputStream(), true);
            System.out.println("Server listening on port " + port);
        } catch (Exception e) {
            System.out.println("Could not start server on port " + port);
        }
        INBOX = new LinkedBlockingQueue<>();
    }

    /**
     * closes client socket and then server socket
     */
    @Override
    public void close() {
        try {
            CLIENT_SOCKET.close();
            SERVER_SOCKET.close();
            System.out.println("Server closed");
            ON = false;
        } catch (IOException e) {
            System.out.println("Could not close client/server socket");
        }
    }

    /**
     * Send messages to client
     *
     * @param message String for client
     */
    @Override
    public void send(String message) {
        WRITER.println(message);
    }

    /**
     * get messages and place in blocking queue
     */
    @Override
    public String get() {
        try {
            return INBOX.take();
        } catch (InterruptedException e) {
            System.out.println("Could not take -Communicator Server");
            return null;
        }
    }

    /**
     * peak at blocking queue
     */
    @Override
    public String read() {
        return INBOX.peek();
    }

    /**
     * put messages in blocking queue
     */
    @Override
    public void receive() {
        try {
            INBOX.add(LISTENER.readLine());
        } catch (IOException e) {
            System.out.println("Closing server");
            close();
        }
    }

    /**
     * constantly tries to add messages to inbox
     */
    @Override
    public void run() {
        ON = true;
        while (ON) {
            receive();
        }
    }
}
