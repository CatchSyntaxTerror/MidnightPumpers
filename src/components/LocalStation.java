package components;

import IOPort.CommunicatorClient;

import java.util.Random;

import static util.PortAddresses.*;

public class LocalStation implements Runnable{

    private double money;

    private double gasPriceT1;
    private double gasPriceT2;
    private double gasPriceT3;

    private double gasT1Gallons = 100;
    private double gasT2Gallons = 100;
    private double gasT3Gallons = 100;
    private double gasT4Gallons = 100;
    private double gasT5Gallons = 100;

    private int gasType;

    private CommunicatorClient communicatorClient;


    public LocalStation(){

        gasPriceT1 = 0;
        gasPriceT2 = 0;
        gasPriceT3 = 0;
        money = 0;
        gasType =1;

        communicatorClient = new CommunicatorClient(GAS_STATION_PORT);
        Thread thread = new Thread(this.communicatorClient);
        thread.start();
    }

    public void makePrice(){
        Random random = new Random();
        double min = 1.0;
        double max = 4.0;
        double newPrice = min + (random.nextDouble() * (max - min));
        if(gasT1Gallons < 0){
            gasPriceT1 = newPrice;
        }
        if(gasT2Gallons < 0){
            gasPriceT2 = newPrice + .3;
        }
        if(gasT3Gallons < 0){
            gasPriceT3 = newPrice +1.0;
        }
    }

    public void sendOutNewPrices(){
        communicatorClient.send("SOME THAT MEANS NEWPRICE FOR GASES");
    }
    public void sendOutNoMoreGas(int type){
        if(type == 1){
            communicatorClient.send("SOMETHING THAT MEANS OUT GAS 1");
            gasPriceT1 = 0.00;
        } else if (type == 2) {
            communicatorClient.send("SOMETHING THAT MEANS OUT GAS 2");
            gasPriceT2 = 0.00;
        } else if (type == 3){
            communicatorClient.send("SOMETHING THAT MEANS OUT GAS 3");
            gasPriceT3 = 0.00;
        }
    }
    public void updatedMoney(double money){
        this.money += money;
    }
    public void gasUsed(double gasVol){
        switch (gasType){
            case 1 -> gasT1Gallons -= gasVol;
            case 2 -> gasT2Gallons -= gasVol;
            case 3 -> gasT3Gallons -= gasVol;
        }
        if(gasT1Gallons < 0){
            sendOutNoMoreGas(1);
        }else if (gasT2Gallons < 0) {
            sendOutNoMoreGas(2);
        }else if (gasT3Gallons <0){
            sendOutNoMoreGas(3);
        }
    }

    //TODO : Handle messages properly
    @Override
    public void run() {
        makePrice();
        int timeToUpdatedPrice = 0;
        while (communicatorClient.ON){
            String message = this.communicatorClient.get().toString();
            if(message.equals("SOMETHING THAT MEANS GAS TYPE")){
                gasType = message.charAt(1); // this will need to be updated
            } else if (message.equals("SOMETHING THAT MEANS TRANSACTION COMPLETE")) {
                updatedMoney(message.charAt(1)); // this will need to be updated
                timeToUpdatedPrice++;
            } else if (message.equals("SOMETHING THAT MEANS GAS USED")) {
                gasUsed(5); // THIS WILL NEED TO BE UPDATED
            }
            if(timeToUpdatedPrice == 3){//we can change later for testing
                makePrice();
                sendOutNewPrices();
                timeToUpdatedPrice = 0;
            }
        }
    }
}
