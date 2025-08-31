package components;

import javafx.scene.shape.Rectangle;
import sim.Gas;

public class Pump {
    private Rectangle rectangle;
    private Gas gas;
    public Pump(){
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
