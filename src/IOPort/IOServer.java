package IOPort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class is used to communicate Main -> JavaFX
 * This class instantiates a client socket
 */
public class IOServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private int portNumber;
    public String message;

    public IOServer(int portNumber) {
        this.portNumber = portNumber;
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            clientSocket = serverSocket.accept();
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Trouble Connecting to " + portNumber);
            throw new RuntimeException(e);
        }
    }

    /**
     * Allows for sending
     *
     * @param message String message sent to
     */
    public void send(String message) {
        out.println(message);
    }

    /**
     * returns message. throws message away after.
     *
     * @return String containing the message.
     */
    public String get() throws IOException {
        String buffer = in.readLine();
        String temp = buffer;
        buffer = null;
        return temp;
    }

    /**
     * @return String message
     * @throws IOException
     */
    public String read() throws IOException {
        String buffer = in.readLine();
        message = buffer;
        return message;
    }

    /**
     * closes sockets gracefully
     */
    public void close() {
        try {
            clientSocket.close();
            System.out.println("Server closed");
        } catch (IOException e) {
            System.out.println("Could not close client socket");
            throw new RuntimeException(e);
        }
    }
}