package hello;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    WebcamImage webImage = new WebcamImage();

    public static void main(String[] args) {


        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        VBox root = new VBox();

        HBox bottomRow = new HBox();
        ImageView mainImage = new ImageView(webImage.image);
        ImageView[] individualImages = new ImageView[webImage.faceMat.length];
        for (int i = 0; i < individualImages.length; i++) {
            individualImages[i] = new ImageView(webImage.faceMat[i]);

            individualImages[i].setFitWidth(100);
            individualImages[i].setFitHeight(100);
            bottomRow.getChildren().add(individualImages[i]);
        }

        root.getChildren().addAll(mainImage, bottomRow);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
