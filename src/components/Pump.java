package components;

import javafx.scene.shape.Rectangle;
import sim.Gas;
import IOPort.*;
import util.*;

public class Pump {
    private Rectangle rectangle;
    private Gas gas;
    private Status status;
    public Pump(){
        this.status = new Status(PortAddresses.PUMP_PORT);
        this.gas = null;
        this.rectangle = null;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }
    public void setGas(Gas gas) {
        this.gas = gas;
    }
}
