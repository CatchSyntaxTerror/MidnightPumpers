package components;

import javafx.scene.shape.Rectangle;

public class Pump {
    private Rectangle rectangle;
    public Pump(){
        this.rectangle = null;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }
}
