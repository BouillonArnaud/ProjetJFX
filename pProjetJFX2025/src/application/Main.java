package application;

import java.io.File;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
			
			
	        // Chemin absolu du fichier audio
	        String musicFile = "C:/Users/Mamad085/Desktop/ProjetJFX/pProjetJFX2025/src/application/resources/pain.mp3";
	        File file = new File(musicFile);

	        if (!file.exists()) {
	            System.out.println("⚠️ Fichier introuvable !");
	            return;
	        }

	        Media media = new Media(file.toURI().toString());
	        MediaPlayer mediaPlayer = new MediaPlayer(media);
	        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
	        
	        // Bouton ON/OFF pour la musique
	        Button musicButton = new Button("🎵 ON/OFF");
	        musicButton.setLayoutX(1100);
	        musicButton.setLayoutY(20);
	        musicButton.setStyle("-fx-background-color: #ff0066; -fx-text-fill: white; -fx-font-size: 16px;");

	        // Gestion du bouton
	        musicButton.setOnAction(event -> {
	            if (mediaPlayer != null) {
	                if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
	                    mediaPlayer.pause();
	                } else {
	                    mediaPlayer.play();
	                }
	            }
	        });

	        root.getChildren().add(musicButton);
			

			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}