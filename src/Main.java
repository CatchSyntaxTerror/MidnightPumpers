import NewIOPort.IOPort;
import NewIOPort.IOPortClient;
import NewIOPort.IOPortServer;


/**
 * Authors: Youssef, Valerie, Joel, Natalie, Danny
 * This is the main class
 */

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World");
        IOPort statusIshtest = new IOPortServer("127.0.0.1", 12345);
        statusIshtest.send("PING");

    }
}

