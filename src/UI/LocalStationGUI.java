package UI;

import components.LocalStation;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LocalStationGUI extends Application {
    private static final AnchorPane anchorPane = new AnchorPane();
    private Image image =  new Image("/cartoon-gas-station" + "-background.jpg");
    private ImageView stationPic = new ImageView(image);

    private LocalStation localStation;

    @Override
    public void start(Stage primaryStage) throws Exception {
        localStation = new LocalStation();
        Group root = new Group();
        makeAnchor();

        root.getChildren().add(anchorPane);

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Station");
        primaryStage.show();
    }
    private void makeAnchor(){
        anchorPane.getChildren().add(stationPic);

    }
}
