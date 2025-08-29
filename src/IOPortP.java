public abstract class IOPortP {
    private String host;
    private final int address;

    public IOPortP(String host,int address){
        this.host=host;
        this.address=address;

    }
    public abstract void send(Object message);
    public abstract Object responce();
    public abstract void disconnect();  //just for test

}
