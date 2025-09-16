package UI;

import oldShitGarbage.Shit1;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import util.PortAddresses;

public class GasStationGUI extends Application {

    private Shit1 communicatorClient;
    public GasStationGUI(){
        this.communicatorClient = new Shit1(PortAddresses.GAS_STATION_PORT);
        Thread thread = new Thread(this.communicatorClient);
        thread.start();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("GasStation");
        stage.setResizable(false);

        Image bgImage = new Image("/cartoon-gas-station-background.jpg");
        BackgroundImage backgroundImage =
                new BackgroundImage(
                        bgImage,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false)
                );
        StackPane root = new StackPane();
        root.setBackground(new Background(backgroundImage));

//        Label label = new Label("Gas Prices");
//        label.setStyle("-fx-background-color: black; -fx-text-fill: white;");
//        label.setAlignment(Pos.CENTER);
//        root.getChildren().add(label);
        // TODO: add labels for gas prices
        Scene scene = new Scene(root, 800, 380);
        stage.setTitle("Background Image Example");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * TODO: implement with componentMessage object
     * @param message
     */

    public void sendMessage(String message){
        communicatorClient.send(message);
    }
    public Object get(){
        return communicatorClient.get();
    }





}
