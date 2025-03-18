package View;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameBoard extends Application {
    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();

        // Background image
        ImageView background = new ImageView(new Image("/application/resources/background_cyberpunk.jpg"));
        background.setFitWidth(1295);
        background.setFitHeight(727);
        background.setPreserveRatio(true);
        root.getChildren().add(background);

        // Adding rectangles
        addRectangle(root, 44, 24, "#4000ff66");
        addRectangle(root, 190, 24, "#0088ff66");
        addRectangle(root, 336, 24, "#ffdd0066");
        addRectangle(root, 482, 24, "#00ff0466");
        addRectangle(root, 628, 24, "#4000ff66");

        Scene scene = new Scene(root, 1295, 727);
        primaryStage.setTitle("Cyberpunk Game Board");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addRectangle(Pane pane, double x, double y, String color) {
        RectangleView rect = new RectangleView(x, y, color);
        pane.getChildren().add(rect);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
