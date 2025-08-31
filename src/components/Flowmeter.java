package components;

import javafx.scene.text.Text;
import sim.Gas;

public class Flowmeter {
    private Text text;
    private Gas gas;
    private int gasFlow;
    public Flowmeter(){
        this.text = new Text("0000");
        this.gas = null;
        this.gasFlow = 0;
    }

    public void setText(Text text) {
        this.text = text;
    }
    public void checkingFlow(){
        this.gasFlow += 1;
        updatedText();
    }
    private void updatedText(){
        this.text.setText(String.valueOf(this.gasFlow));
    }
    public void setGas(Gas gas) {
        this.gas = gas;
    }
}
