// Package View
package View;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.HomePageModel;
import controller.HomePageController;

public class HomePageView extends Application {
    @Override
    public void start(Stage primaryStage) {
        HomePageModel model = new HomePageModel();
        HomePageController controller = new HomePageController(model, primaryStage);
        
        Pane root = controller.getView();

        Scene scene = new Scene(root, 1275, 727);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Home Page");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
// background.setFitWidth(1275);
//background.setFitHeight(727);