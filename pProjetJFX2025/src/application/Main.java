package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	

	public void start(Stage primaryStage) {
		try {
			Pane root = FXMLLoader.load(getClass().getResource("/application/Sample.fxml"));

			Scene scene = new Scene(root, 1295,727);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Tu te mets combien - Cyberpunk ");
			primaryStage.setScene(scene);
			String imageUrl = getClass().getResource("/application/resources/background_cyberpunk.jpg").toExternalForm();
			root.setStyle("-fx-background-image: url('" + imageUrl + "'); -fx-background-size: cover;");

			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}