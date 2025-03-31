package views;

import java.io.File;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Case;
import model.GameBoard;

public class BoardView extends Pane {
    private final GameBoard board;
    private final ImageView pionGraphique;
    private final Color[] couleurs = {Color.PURPLE, Color.ORANGE, Color.BLUE, Color.GREEN,
                                      Color.ORANGE, Color.ORANGE, Color.PURPLE, Color.GREEN};
    private static final int RECT_WIDTH = 146; // Largeur des rectangles
    private static final int RECT_HEIGHT = 90; // Hauteur des rectangles
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    private ImageView imgView; // ImageView pour le bouton son

    public BoardView(GameBoard board, Stage boardStage) {
        this.board = board;
        
        Image backgroundImage = new Image(getClass().getResource("/resources/background_cyberpunk.jpg").toExternalForm());
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.fitWidthProperty().bind(this.widthProperty());
        backgroundView.fitHeightProperty().bind(this.heightProperty());
        this.getChildren().add(backgroundView);

        // Dessiner le plateau selon le chemin avec alternance de couleurs
        int index = 0;
        for (Case c : board.getChemin()) {
            Rectangle casePlateau = new Rectangle(RECT_WIDTH, RECT_HEIGHT);
            casePlateau.setFill(couleurs[index % couleurs.length]);
            casePlateau.setStroke(Color.BLACK);
            casePlateau.setX(c.getX() * RECT_WIDTH / 40); // Ajustement des proportions
            casePlateau.setY(c.getY() * RECT_HEIGHT / 40);
            this.getChildren().add(casePlateau);
            index++;
        }

        // Dessiner le pion
        Image pionImage = new Image(getClass().getResource("/resources/pawns1.png").toExternalForm());
        pionGraphique = new ImageView(pionImage);
        pionGraphique.setFitWidth(RECT_HEIGHT / 2);  // Ajuste la taille du pion
        pionGraphique.setFitHeight(RECT_HEIGHT / 2);
        updatePionPosition();
        this.getChildren().add(pionGraphique);
        
        // Créer le bouton de retour au menu
        StackPane btnMenu = createButton("Return to menu");
        btnMenu.setOnMouseClicked(event -> {
            Stage mainMenuStage = new Stage();
            MainMenuView mainMenuView = new MainMenuView(mainMenuStage);
            Scene mainMenuScene = new Scene(mainMenuView, 1920, 1080);
            mainMenuStage.setScene(mainMenuScene);
            mainMenuStage.setTitle("Menu Principal");
            mainMenuStage.setMaximized(true);
            mainMenuStage.show();
            boardStage.close();
        });
        this.getChildren().add(btnMenu);

        // Initialiser le bouton de musique
        imgView = new ImageView(new Image(getClass().getResource("/resources/button_son_on.png").toExternalForm()));
        imgView.setFitWidth(50); // Ajuste la taille selon tes besoins
        imgView.setFitHeight(50);

        StackPane btnMusic = new StackPane(imgView);
        btnMusic.setLayoutX(120);
        this.getChildren().add(btnMusic);

        // Ajouter l'événement de clic pour alterner l'état de la musique
        btnMusic.setOnMouseClicked(event -> toggleMusic());

        // Charger le fichier de musique
        String musicFile = getClass().getResource("/resources/pain.mp3").toExternalForm();
        Media sound = new Media(musicFile);
        mediaPlayer = new MediaPlayer(sound);
    }
    
    public void updatePionPosition() {
        Case currentCase = board.getChemin().get(board.getPion().getIndex());
        pionGraphique.setX(currentCase.getX() * RECT_WIDTH / 40 + RECT_WIDTH / 8);
        pionGraphique.setY(currentCase.getY() * RECT_HEIGHT / 40 + RECT_HEIGHT / 2);
    }

    private VBox createCyberpunkButtonGroup() {
        // TODO Auto-generated method stub
        return null;
    }

    public StackPane createButton(String textContent) {
        Rectangle rectangle = new Rectangle(100, 50);
        rectangle.setFill(Color.YELLOW);
        rectangle.setStroke(Color.BLACK);

        Text text = new Text(textContent);
        text.setFill(Color.BLACK);

        StackPane stack = new StackPane();
        stack.getChildren().addAll(rectangle, text);

        return stack;
    }
    
    // Méthode pour alterner entre musique ON et OFF
    private void toggleMusic() {
        if (mediaPlayer == null) return; // Sécurité : Vérifier que mediaPlayer existe
        
        MediaPlayer.Status status = mediaPlayer.getStatus();
        if (status == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
            // Mettre l'image en ON
            imgView.setImage(new Image(getClass().getResource("/resources/button_son_on.png").toExternalForm()));
        } else {
            mediaPlayer.play();
            // Mettre l'image en OFF
            imgView.setImage(new Image(getClass().getResource("/resources/button_son_off.png").toExternalForm()));
        }
    }
}
