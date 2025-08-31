package IOPort2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Actuator only sends messages cant read or peek
 */
public class Actuator extends IOPortServer2 implements Runnable {
    public Actuator(int port) {
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
    public String get() {
        System.out.println("You Shouldn't be using this");
        ON = false;
        throw new UnsupportedOperationException();
    }

    /**
     *  doesnt matter, dont use it
     */
    @Override
    public String read() {
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
            System.out.println("Could not receive message.");
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
