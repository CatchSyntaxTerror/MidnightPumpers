package UI;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class CreditCardReaderUI extends Application {

    private final String WHITE_BACKGROUND =
            "-fx-background-color: #FFFFFF;";
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
        List<Image> images = new ArrayList<>();
        StackPane root = new StackPane();
        ArrayList<Node> sillyList=new ArrayList<>();
        images.add(new Image(getClass().getResource("/CC.png").toExternalForm()));
        System.out.println(images.get(0).getHeight());
        images.add(new Image(getClass().getResource("/CCReader.png").toExternalForm()));
        ImageView imageView = new ImageView(images.get(0));
        ImageView imageView2 = new ImageView(images.get(1));
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView2.setFitWidth(700);
        imageView2.setFitHeight(200);
        Rectangle rectButton = new Rectangle(100, 100,Color.TRANSPARENT);
        rectButton.setStroke(Color.BLACK);
        sillyList.add(imageView2);
        sillyList.add(imageView);
        sillyList.add(rectButton);
        draw(root,sillyList);
        root.setStyle(WHITE_BACKGROUND);





        rectButton.setOnMouseDragged(event -> {
            double offsetX = event.getX();
            double offsetY = event.getY();
            System.out.println(imageView.getX());
            imageView.setX(offsetX);
            System.out.println(imageView.getX());
            imageView.setLayoutY(offsetY);

            rectButton.setX(offsetX);
            rectButton.setY(offsetY);


            sillyList.clear();
            sillyList.add(imageView2);
            sillyList.add(imageView);
            sillyList.add(rectButton);
            draw(root, sillyList);
        });






        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Creddit Card Reader");
        stage.show();
    }

    private void draw(StackPane root, ArrayList<Node> elems){
        System.out.println("Redraw");

        root.getChildren().clear();
        root.getChildren().removeAll();
        root.getChildren().addAll(elems);
    }
}
