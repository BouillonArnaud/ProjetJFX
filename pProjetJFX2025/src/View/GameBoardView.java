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
import javafx.scene.paint.Color;
import java.io.File;
import model.GameBoardModel;
import controller.GameBoardController;

public class GameBoardView extends Application {
    private MediaPlayer mediaPlayer;

    
    @Override
    public void start(Stage primaryStage) {
        GameBoardModel model = new GameBoardModel();
        GameBoardController controller = new GameBoardController(model);
        Pane root = new Pane();

        // Background image (doit être ajouté en premier)
        ImageView background = new ImageView(new Image("/application/resources/background_cyberpunk.jpg"));
        background.setFitWidth(1275);
        background.setFitHeight(727);
        background.setPreserveRatio(true);
        root.getChildren().add(background); // Ajout en premier

        // Ajouter ensuite le plateau de jeu
        root.getChildren().add(controller.getView());

        // Ajouter la musique de fond
        String musicFile = "C:/Users/Mamad085/Desktop/ProjetJFX/pProjetJFX2025/src/application/resources/pain.mp3";
        File file = new File(musicFile);

        if (file.exists()) {
            Media media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        } else {
            System.out.println("⚠️ Fichier audio introuvable !");
        }

        // Bouton ON/OFF pour la musique
        Button musicButton = new Button("\uD83C\uDFB5");
        musicButton.setLayoutX(1220);
        musicButton.setLayoutY(10);
        musicButton.setPrefSize(50, 30);
        musicButton.setStyle("-fx-background-color: #ff0066; -fx-text-fill: white; -fx-font-size: 12px;");

        // Gestion du bouton musique
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

        Scene scene = new Scene(root, 1295, 727);
        primaryStage.setTitle("Cyberpunk Game Board");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}