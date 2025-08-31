package IOPort;

import util.PortAddresses;

import java.util.*;

/**
 * Starts the servers
 */
public class Server {
    private IOPortClient nozzle;
    private IOPortClient flowMeter;
    private IOPortServer pump;
    private Map<UUID,IOPortServer> serverMap=new HashMap();
    private Map<UUID,IOPortClient> clientMap=new HashMap();
    public Server (Map<UUID,IOPortServer> serverMap,Map<UUID,IOPortClient> clientMap){
       this.serverMap=serverMap;
       this.clientMap=clientMap;
    }


    public void send(String message, UUID ID){
        IOPortServer server=serverMap.get(ID);
       if(server!=null){
           server.send(message);
       }else{
           clientMap.get(ID).send(message);
       }

    }

    public Object get(UUID ID){
        IOPortServer server=serverMap.get(ID);
        if(server!=null){
            return server.get();
        }else{
            return clientMap.get(ID).get();
        }
    }

    public Object read(UUID ID){
        IOPortServer server=serverMap.get(ID);
        if(server!=null){
            return server.read();
        }else{
            return clientMap.get(ID).read();
        }
    }


    public void startUp(){
        for(IOPortServer server:serverMap.values()){
            if(server instanceof Actuator actuator){
                Thread thread = new Thread(actuator);
                thread.start();
            } else if(server instanceof CommunicatorServer comSer) {
                Thread thread = new Thread(comSer);
                thread.start();
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (IOPortClient client:clientMap.values()){
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

    }
}
