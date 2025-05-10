package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import views.MainMenuView;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        MainMenuView mainMenu = new MainMenuView(primaryStage);
        Scene scene = new Scene(mainMenu, 1920, 1080);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Main Menu");
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
