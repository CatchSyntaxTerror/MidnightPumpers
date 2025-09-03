package components;

import IOPort.CommunicatorClient;
import util.PortAddresses;

public class CardCompany implements Runnable {
    private CommunicatorClient communicatorClient;
    private boolean accept;
    public CardCompany(){
        this.accept = true;
        this.communicatorClient =
                new CommunicatorClient(PortAddresses.CARD_COMPANY_PORT);
        Thread thread = new Thread(communicatorClient);
        thread.start();
    }

    /*
    Will go between accepting and rejecting cards.
    /*
    //TODO : send proper message for communicator
     */
    public void sendAccRej(){
        if(accept){
            this.communicatorClient.send("SOMETHING THAT MEANS ACCEPTED");
        }else {
            this.communicatorClient.send("SOMETHING THAT MEANS REJECTED");
        }
        this.accept = !accept;
    }

    /*
    //TODO : send proper message for communicator
    Gets the message from its communicator and checks to see if the card is
    valid or not.

    Might need to use the read function instead but at the moment IDK
     */
    @Override
    public void run() {
        while (this.communicatorClient.ON){
            String message = this.communicatorClient.get().toString();
            if(message.equals("SOMETHING THAT MEANS HERES A CARD")){
                sendAccRej();
            }
        }
    }
}
