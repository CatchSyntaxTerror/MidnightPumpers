package NewIOPort;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class IOPortServer extends IOPort {
    private ServerSocket serverSocket;

    public IOPortServer(String host, int address) {
        super(host, address);
        try {
            serverSocket = new ServerSocket(address);
            System.out.println("prot: " + address);

                Socket clientSocket = serverSocket.accept();
                System.out.println("address " + clientSocket.getInetAddress());

                handleClient(clientSocket);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    private void handleClient(Socket socket) {
        try{
            listener = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String line;
            while ((line = listener.readLine()) != null) {
                System.out.println("input:" + line);
//                String response = handle(line);
//                output.write(response);
//                output.newLine();
//                output.flush();
            }
        } catch (IOException e) {
            System.err.println( e.getMessage());
        } finally {
            try {
                socket.close();
                System.out.println("Client disconnected.");
            } catch (IOException e) {
                System.err.println("Error closing socket: " + e.getMessage());
            }
        }
    }

    @Override
    public void send(Object message) {
        System.out.println("sending " + message);
        String sMessage =message.toString();
        try {
            writer.write(sMessage);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Gets a message, but does not store it
     *
     * @return The string message
     */
    @Override
    public Object get() {
        return null;
    }

    /**
     * This is just for testing, in a prod env, hardware should not ever
     * have a way to disconnect itself, I can't imagine we would even want this
     * as a last resort 'self distruck' feature
     */
    @Override
    public void disconnect() {
        try {
            listener.close();
            writer.close();
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
