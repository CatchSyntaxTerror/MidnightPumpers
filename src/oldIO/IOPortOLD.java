package oldIO;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class IOPortOLD {
    private final String host;
    private final int address;
    private Socket socket;
    private BufferedReader listener;
    private BufferedWriter writer;
    private ServerSocket serverSocket;

    //THIS IS NOT permanent, just here for testing
    private Object lastResponce= null;

    /**
     * Creates a new server wrapper instance
     * @param host the host - likly localhost
     * @param address the address, found in the config file
     */

    public IOPortOLD(String host, int address, boolean server){
        this.host=host;
        this.address=address;

        if(server){start(address);

        }else connect();
    }

    /**
     * Sends data out on the input stream to the server.
     * @param header the data to be sent to the server
     */

    public void send(Object header){
        //mabye just make it a string idk how the xml stuff he wants goes
        String sHeader=header.toString();
        try {
            writer.write(sHeader);
            writer.newLine();
            //New line? Idk
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Grabs the nearest responce, very simplistic, just returns the responce and
     * doesn't do anything with it, in future we probably want to flush the input stream
     * @return The response from its server
     */
    public Object get(){
        try {
            String responce= listener.readLine();
            //flush input stream??
            System.out.println(responce);
            return responce;
            //Handle response?????
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //TODO: Ask team about read, in my mind,
    // it shouldn't be something the api saves, but maybe it is
    /**
     * Reads the responce, saves it??? and returns it,
     * I really don't know if we want to save it in the API, it feels really wrong
     * but we'll have it in here for testing rn
     * @return The response from its server
     */
    public Object read(){
        try {
            String responce= listener.readLine();
            lastResponce=responce;
            //flush input stream??
            System.out.println(responce);
            return responce;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return true or false depending on if the connection was achieved
     * This sets up the connection with the listner and writer as well
     */

    private boolean connect() {
        try {
            socket=new Socket(host, address);
            listener = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            System.out.println("connected to server/parent at " + host + ":" + address);
            return true;

        }catch (Exception e){
            System.out.println("failed connection");
            return false;
        }

    }

    /**
     * This disconnects from the server. This shouldn't be a thing we let our
     * devices do, this is just here for testing
     * @return true if disconnected, false if an error is thrown
     */

    private boolean disconnect() {
        try {
            listener.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            return false;
            //this is just for testing, in a real env we wouldn't want to just
            //swallow this exception
            //throw new RuntimeException(e);

        }
        return true;
    }

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("prot: " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("address " + clientSocket.getInetAddress());

                handleClient(clientSocket);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    private void handleClient(Socket socket) {
        //TODO: Spawn a new thread per client
        try (
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {
            String line;
            while ((line = input.readLine()) != null) {
                System.out.println("input:" + line);
                //handle
                String response = handle(line);
                output.write(response);
                output.newLine();
                output.flush();
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

    /**
     * This is just left in for testing of responses, this will be taken out eventually
     * @param request a basic request of the server
     * @return  a basic response
     */
    private String handle(String request) {
        switch (request.trim().toUpperCase()) {
            case "STATUS?":
                return "STATUS: OK";
            case "PING":
                return "PONG"; // I'm not certain put im pretty sure we PING and the server PONGS
            default:
                return "ERROR: UNKNOWN COMMAND";
        }
    }
}
