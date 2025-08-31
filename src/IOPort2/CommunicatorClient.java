package IOPort2;

import NewIOPort.IOPort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The client side of the Communicator. For JavaFX
 * Author: Youssef Amin, Natalie Onion
 */
public class CommunicatorClient extends IOPortClient2 implements Runnable {

    public CommunicatorClient(int port) {
        super(port);
        try {
            CLIENT_SOCKET = new Socket(HOST, port);
            LISTENER = new BufferedReader(new InputStreamReader(CLIENT_SOCKET.getInputStream()));
            WRITER = new PrintWriter(CLIENT_SOCKET.getOutputStream(), true);
            System.out.println("Server listening on port " + port);
        } catch (Exception e) {
            System.out.println("Could not start server on port " + port);
        }
        INBOX = new LinkedBlockingQueue<>();
    }

    /**
     * closes sockets gracefully
     */
    @Override
    public void close() {
        try {
            CLIENT_SOCKET.close();
            System.out.println("Server closed");
            ON = false;
        } catch (IOException e) {
            System.out.println("Could not close client socket");
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
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
