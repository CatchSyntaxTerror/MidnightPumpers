package testing;

import NewIOPort.IOPort;
import NewIOPort.IOPortClient;
import NewIOPort.PortAddresses;

public class PumpTest {
    //This is just a test of the current socket wrapper,
    // feel free to delete if you don't like
    //The pump is a status, which is connected to an actuator at main
    //the pump can only read, the actuator can only send
    IOPort statusTest;
    public PumpTest(String host,int address){
        statusTest=new IOPortClient.Status(host,address);
    }

    public static void main(String [] args){
        PumpTest pumpTest=new PumpTest("localhost", PortAddresses.PUMP_PORT);
        System.out.println(pumpTest.statusTest.read().toString());
    }
}
