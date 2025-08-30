package NewIOPort;

import java.io.*;
import java.net.Socket;

public class IOPortClient extends IOPort{
    protected Socket socket;
    protected Object lastResponse = null;

    /**
     * Creates an IO port instance, should be called with the child class
     *
     * @param host    The server host
     * @param address the server address
     */
    public IOPortClient(String host, int address) {
        super(host, address);
        //Try to connect to socket and set up input/output
        try {
            socket=new Socket(host, address);
            listener = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            System.out.println("connected to server/parent at " + host + ":" + address);


        }catch (Exception e){
            System.out.println("failed connection");

        }


    }

    /**
     * This is just here for testing, in prod, we shouldn't have this
     */
    @Override
    public void disconnect() {
        try {
            listener.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static class Actuator extends IOPortClient{

        /**
         * Creates an IO port instance, should be called with the child class
         *
         * @param host    The server host
         * @param address the server address
         */
        public Actuator(String host, int address) {
            super(host, address);
        }

        /**
         * Sends a message across the 'wire'
         *This is the only method an actuator should have
         * @param message The message(a string) to be sent across the socket
         */
        @Override
        public void send(Object message) {
            String sHeader=message.toString();
            try {
                writer.write(sHeader);
                writer.newLine();
                writer.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }
    public static class Status extends IOPortClient{
        /**
         * Creates an IO port instance, should be called with the child class
         *
         * @param host    The server host
         * @param address the server address
         */
        public Status(String host, int address) {
            super(host, address);
        }

        /**
         * Gets the nearest message and stores it. Think of this as switches/pins
         * on hardware
         * @return the data
         */
        @Override
        public Object read(){
            try {
                String response = listener.readLine();
                lastResponse = response; //Saves it in the 'wire'/'board'
                System.out.println(response);
                return response;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
    public static class Communicator extends IOPortClient{
        /**
         * Creates an IO port instance, should be called with the child class
         *
         * @param host    The server host
         * @param address the server address
         */
        public Communicator(String host, int address) {
            super(host, address);
        }

        /**
         * Sends a message across the 'wire'
         *
         * @param message The message(a string) to be sent across the socket
         */
        @Override
        public void send(Object message) {
            String sHeader=message.toString();
            try {
                writer.write(sHeader);
                writer.newLine();
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
        @Override
        public Object get(){
            try {
                String response= listener.readLine();
                //flush input stream??
                System.out.println(response);
                return response;
                //Handle response?????
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }


}
