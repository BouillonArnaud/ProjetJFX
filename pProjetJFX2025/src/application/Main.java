package application;

import javafx.application.Application;
import javafx.stage.Stage;
import model.GameBoard;
import javafx.scene.Scene;

public class Main extends Application {
	

	public void start(Stage primaryStage) {
        GameBoard gameBoard = new GameBoard();
        Scene scene = new Scene(gameBoard);

        primaryStage.setTitle("Game Board");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

