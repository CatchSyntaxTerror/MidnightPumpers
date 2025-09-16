package UI;

import IOPort.Actuator;
import components.Nozzle2;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import util.PortAddresses;

import java.awt.event.MouseEvent;
import java.beans.EventHandler;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the GUI for the hose
 */
//TODO: ask danny about his version, ie why is credit card getting started there
public class HoseConGUI extends Application {
    private Nozzle2 nozzle;  //How it sends the messages
    private int currentIndex;
    private boolean flip=false;
    private boolean connected=false;
    private int getTo=4;
    public HoseConGUI(){
        nozzle=new Nozzle2();
    }

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param stage the primary stage for this application, onto which
     *                     the application scene can be set.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages.
     * @throws Exception if something goes wrong
     */
    @Override
    public void start(Stage stage) throws Exception {
        Rectangle hose = new Rectangle(550,500,50,50);
        hose.setFill(Color.PURPLE);
        hose.setOnMouseClicked(event -> flipConnection());
        StackPane stackPane=new StackPane(hose);


        List<Image> images = new ArrayList<>();
        images.add(new Image(getClass().getResource("/hoseCon1.png").toExternalForm()));
        System.out.println(images.get(0).getHeight());
        images.add(new Image(getClass().getResource("/hoseCon2.png").toExternalForm()));
        images.add(new Image(getClass().getResource("/hoseCon3.png").toExternalForm()));
        images.add(new Image(getClass().getResource("/hoseCon4.png").toExternalForm()));
        images.add(new Image(getClass().getResource("/hoseCon5.png").toExternalForm()));



        ImageView imageView = new ImageView(images.get(currentIndex));
        imageView.setFitWidth(700);
        imageView.setFitHeight(200);
        //imageView.setPreserveRatio(true);

        AnimationTimer animationTimer =new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(flip){
                    if(now%1000==0){
                        if(currentIndex==getTo){
                            flip=false;
                        }else{
                            currentIndex = (currentIndex + 1) % images.size();
                            imageView.setImage(images.get(currentIndex));
                        }


                    }


                }

            }
        };
        animationTimer.start();

        // Rectangle button
        Rectangle rectButton = new Rectangle(700, 1000, Color.TRANSPARENT);
        rectButton.setStroke(Color.TRANSPARENT);

        // When rectangle it cycles through, so we need a whole circle
        rectButton.setOnMouseClicked(event -> {
            flip=true;
            //flipConnection(); For this to be called, the nozzle has to be turned on
            if(currentIndex==0){
                getTo=images.size()-1;

            }else{
                getTo=0;
            }
        });

        StackPane root = new StackPane();
        root.getChildren().addAll(imageView, rectButton);
        root.setStyle("-fx-background-color: #000000;");
        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Image Switcher Example");
        stage.show();

    }




    private void flipConnection(){
        nozzle.sendConnectionInfo(!connected);
        connected=!connected;
    }


}
