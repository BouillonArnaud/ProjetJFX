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

public class GameBoard extends Application {
    private MediaPlayer mediaPlayer;

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();

        // Background image
        ImageView background = new ImageView(new Image("/application/resources/background_cyberpunk.jpg"));
        background.setFitWidth(1275);
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
        Button musicButton = new Button("\uD83C\uDFB5");
        musicButton.setLayoutX(1220);
        musicButton.setLayoutY(10);
        musicButton.setPrefSize(50, 30);
        musicButton.setStyle("-fx-background-color: #ff0066; -fx-text-fill: white; -fx-font-size: 12px;");

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

        // Ajouter les cases du plateau de jeu
        getCaseAtPosition(root);

        Scene scene = new Scene(root, 1295, 727);
        primaryStage.setTitle("Cyberpunk Game Board");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void getCaseAtPosition(Pane pane) {
        Color[] colors = { Color.web("#4000ff66"), Color.web("#0088ff66"), Color.ORANGE, Color.web("#00ff0466") };
        Color[] reverseColors = { Color.web("#00ff0466"), Color.ORANGE, Color.web("#0088ff66"), Color.web("#4000ff66") };

        double startX = 44;
        double startY = 24;
        double caseWidth = 146;
        double caseHeight = 90;

        // First-Top row
        for (int i = 0; i < 8; i++) {
            RectangleView rectangle = new RectangleView(startX + i * caseWidth, startY, colors[i % colors.length]);
            pane.getChildren().add(rectangle);
        }

        // First-Right column
        for (int i = 0; i < 6; i++) {
            RectangleView rectangle = new RectangleView(startX + caseWidth * 7, startY + (i + 1) * caseHeight,
                    colors[(i + 4) % colors.length]);
            pane.getChildren().add(rectangle);
        }

        // First-Bottom row
        for (int i = 5; i >= 0; i--) {
            RectangleView rectangle = new RectangleView(startX + (i + 1) * caseWidth, startY + 6 * caseHeight,
                    reverseColors[i % reverseColors.length]);
            pane.getChildren().add(rectangle);
        }

        // Left column
        for (int i = 4; i > 0; i--) {
            RectangleView rectangle = new RectangleView(startX + caseWidth, startY + caseHeight + i * caseHeight,
                    reverseColors[(i + 3) % reverseColors.length]);
            pane.getChildren().add(rectangle);
        }

        // Second-Top row
        for (int i = 0; i < 4; i++) {
            RectangleView rectangle = new RectangleView(startX + (i + 2) * caseWidth, startY + 2 * caseHeight, colors[i % colors.length]);
            pane.getChildren().add(rectangle);
        }

        // Second-Right column
        for (int i = 0; i < 2; i++) {
            RectangleView rectangle = new RectangleView(startX + caseWidth * 5, startY + caseHeight + (i + 2) * caseHeight,
                    colors[(i + 4) % colors.length]);
            pane.getChildren().add(rectangle);
        }

        // Second-Bottom row
        for (int i = 1; i >= 0; i--) {
            RectangleView rectangle = new RectangleView(startX + (i + 3) * caseWidth, startY + 4 * caseHeight,
                    reverseColors[i % reverseColors.length]);
            pane.getChildren().add(rectangle);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}