package oldShitGarbage;

import java.io.*;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The client side of the Communicator. For JavaFX
 * Author: Youssef Amin, Natalie Onion, Daniel Thompson
 */
public class Shit1 extends Shit5 implements Runnable {

    public Shit1(int port) {
        super(port);
        CLIENT_UUID = UUID.randomUUID();
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
    public Object get() {
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
    public Object read() {
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
        while (notConnected){
            try {
                System.out.println("Trying " + PORT);
                connect(0);
                LISTENER = new BufferedReader(new InputStreamReader(CLIENT_SOCKET.getInputStream()));
                WRITER = new PrintWriter(CLIENT_SOCKET.getOutputStream(), true);
                System.out.println("Server listening on port " + PORT);
                notConnected = false;
            } catch (Exception e) {
                System.out.println("Could not start server on port " + PORT);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        ON = true;
        while (ON) {
            receive();
        }
    }



}
