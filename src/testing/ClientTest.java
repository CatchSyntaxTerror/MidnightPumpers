package testing;

import IOPort.IOPort;

import java.io.IOException;

/**
 * This is used to test IOPort.
 * It will be used by main
 * Make sure to run server first
 * Author: Youssef Amin
 */
public class ClientTest {
    public static void main(String[] args) {
        IOPort client = new IOPort(42);

        try {
            System.out.println(client.get());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
