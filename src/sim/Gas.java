package sim;

public class Gas {
    private boolean onOff;

    public Gas(){
        this.onOff = false;
    }

    public void turnOnGas(){
        this.onOff = true;
    }

    public void turnOffGas() {
        this.onOff = false;
    }
}
