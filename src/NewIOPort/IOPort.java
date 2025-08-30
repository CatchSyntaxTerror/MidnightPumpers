package NewIOPort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.concurrent.BlockingQueue;

/**
 * This class will be extended by IOPortClient and IOPortServer
 * When we create an instance of this socket wrapper we will create it like
 * IOPort communicator = new IOPortClient("LocalHost",1234) or
 * IOPort communicator = new IOPortClient("LocalHost",1234)
 */
public abstract class IOPort {
    //Not private anymore(Thank you, Youssef(my bad king))
    protected String host;   //Should be local host
    protected final int address;//Some nonreserved port number
    protected BufferedReader listener;
    protected BufferedWriter writer;

    /**
     * Creates an IO port instance, should be called with the child class
     * @param host The server host
     * @param address the server address
     */
    public IOPort(String host,int address){
        this.host=host;
        this.address=address;
    }

    /**
     * Sends a message across the 'wire'
     *
     * @param message The message(a string) to be sent across the socket
     */
    public void send(Object message) {
        System.out.println("This hardware shouldn't be sending messages");
    }

    /**
     * Gets a message, but does not store it
     * @return The string message
     */
    public Object get(){
        System.out.println("This hardware shouldn't be getting messages");
        return null;
    }

    /**
     * Gets the nearest message and stores it. Think of this as switches/pins
     * on hardware
     * @return the data
     */
    public Object read(){
        System.out.println("This hardware shouldn't be reading messages");
        return null;
    }

    /**
     * This is just here for testing, in prod, we shouldn't have this
     */
    public abstract void disconnect();  //just for test



}
