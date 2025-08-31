package IOPort;

import util.PortAddresses;

import java.util.ArrayList;

/**
 * Starts the servers
 */
public class Server {
    private IOPortClient nozzle;
    private IOPortClient flowMeter;
    private IOPortServer pump;
    private ArrayList<IOPortClient> ioPortClients;
    private ArrayList<IOPortServer> ioPortServers;
    public Server (){
        this.ioPortServers = new ArrayList<>();
        this.ioPortClients = new ArrayList<>();
    }

    public void addServer(IOPortServer ioPortServer){
        this.ioPortServers.add(ioPortServer);
    }

    public void addClient(IOPortClient ioPortClient){
        this.ioPortClients.add(ioPortClient);
    }

    public void startUp(){
        for(IOPortServer server : this.ioPortServers){
            if(server instanceof Actuator actuator){
                Thread thread = new Thread(actuator);
                thread.start();
            } else if(server instanceof CommunicatorServer comSer) {
                Thread thread = new Thread(comSer);
                thread.start();
            }
        }
        for (IOPortClient client : this.ioPortClients){
            if(client instanceof Status status){
                Thread thread = new Thread(status);
                thread.start();
            } else if (client instanceof CommunicatorClient comCli) {
                Thread thread = new Thread(comCli);
                thread.start();
            }
        }
    }
    public void connect(){
        for (IOPortServer server : this.ioPortServers){

        }
    }
}
