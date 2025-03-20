package View;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import java.io.File;

public class TestAudio extends Application {
    @Override
    public void start(Stage primaryStage) {
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
        mediaPlayer.play();

        System.out.println("🎵 Lecture de la musique en cours...");
    }

    public static void main(String[] args) {
        launch(args); // Lance l'application JavaFX
    }
}
