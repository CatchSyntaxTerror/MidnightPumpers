package oldShitGarbage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Actuator only sends messages cant read or peek
 * Author: Youssef Amin, Natalie Onion, Daniel Thompson
 */
public class Shit extends Shit3 implements Runnable {
    public Shit(int port) {
        super(port);
        SERVER_UUID = UUID.randomUUID();
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
     * doesnt matter, dont use it
     */
    @Override
    public Object get() {
        System.out.println("You Shouldn't be using this");
        ON = false;
        throw new UnsupportedOperationException();
    }

    /**
     *  doesnt matter, dont use it
     */
    @Override
    public Object read() {
        System.out.println("You Shouldn't be using this");
        ON = false;
        throw new UnsupportedOperationException();
    }

    /**
     * put messages in blocking queue
     */
    @Override
    public void receive() {
        try {
            INBOX.add(LISTENER.readLine());
        } catch (IOException e) {
            System.out.println("closing server");
            close();
        }
    }

    /**
     * constantly tries to add messages to inbox
     */
    @Override
    public void run() {
        while(notConnected){
            try {
                System.out.println("Trying " + PORT);
                SERVER_SOCKET = new ServerSocket(PORT);
                CLIENT_SOCKET = SERVER_SOCKET.accept();
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
