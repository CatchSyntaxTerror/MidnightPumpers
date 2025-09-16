package testing;

import IOPort.IOServer;

import java.util.Scanner;

/**
 * This class is meant to test the Server side Socket
 * It is meant to be instantiated by JavaFX controllers
 *
 * run this application first then run the clientTest
 * type in a string and client socket should print it out
 *
 * Author: Youssef Amin
 */
public class ServerTest {
    public static void main(String[] args) {
        IOServer server = new IOServer(42);
        Scanner sc = new Scanner(System.in);
        String test =  sc.nextLine();
        server.send(test);
    }
}
