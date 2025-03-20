package View;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class GameBoard extends Application {
    private MediaPlayer mediaPlayer;

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();

        // Background image
        ImageView background = new ImageView(new Image("/application/resources/background_cyberpunk.jpg"));
        background.setFitWidth(1295);
        background.setFitHeight(727);
        background.setPreserveRatio(true);
        root.getChildren().add(background);

        // Ajouter la musique de fond
        String musicFile = "C:/Users/Mamad085/Desktop/ProjetJFX/pProjetJFX2025/src/application/resources/pain.mp3";
        File file = new File(musicFile);

        if (file.exists()) {
            Media media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Boucle infinie
            mediaPlayer.play(); // Démarrer la musique
        } else {
            System.out.println("⚠️ Fichier audio introuvable !");
        }

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

        // Ajouter des rectangles
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
