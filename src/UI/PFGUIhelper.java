package UI;

import IOPort.CommunicatorServer;
import components.Flowmeter;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import sim.Gas;

public class PFGUIhelper implements Runnable{
    private Text text;
    private Gas gas;
    private Flowmeter flowmeter;
    private Thread pumpFlowThread;
    private Rotate rotate1;
    private Rotate rotate2;
    private ProgressBar progressBar;
    private double startR1 = 45;
    private double startR2 = 135;
    PFGUIhelper(){
        this.text = new Text("00.00");
        this.rotate1 = new Rotate();
        this.rotate2 = new Rotate();
        this.progressBar = null;
        this.gas = null;
    }

    public void setFlowmeter(Flowmeter flowmeter) {
        this.flowmeter = flowmeter;
    }

    public void setRotate1(Rotate rotate1) {
        this.rotate1 = rotate1;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }
    public void setRotate2(Rotate rotate2) {
        this.rotate2 = rotate2;
    }

    public void setText(Text text) {
        this.text = text;
    }
    private void updatedText(){
        this.text.setText(String.valueOf(String.format("%.2f", flowmeter.getGasFlow())));
    }
    public void setGas(Gas gas) {
        this.gas = gas;
    }
    @Override
    public void run() {
        while (flowmeter.connected()) {
            if (gas.isOnOff()) {
                updatedText();
                rotate1.setAngle(startR1 + 3);
                rotate2.setAngle(startR2 + 3);
                startR1 += 3;
                startR2 += 3;
                if (progressBar.getProgress() <= 1.0)
                    progressBar.setProgress(progressBar.getProgress() + 0.1);
                if (startR1 > 360) {
                    startR1 = 0;
                }
                if (startR2 > 360) {
                    startR2 = 0;
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else if (progressBar.getProgress() > 0.1) {
                progressBar.setProgress(progressBar.getProgress() - 0.1);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
