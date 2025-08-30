package components;

import javafx.scene.text.Text;

public class Flowmeter {
    private Text text;
    public Flowmeter(){
        this.text = new Text("0000");
    }

    public void setText(Text text) {
        this.text = text;
    }
}
