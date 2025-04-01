package views;

import java.io.File;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Case;
import model.GameBoard;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class BoardView extends Pane {
    private final GameBoard board;
    private final ImageView pawnView;
    private final Color[] colors = {Color.PURPLE, Color.ORANGE, Color.BLUE, Color.GREEN,
                                      Color.ORANGE, Color.ORANGE, Color.PURPLE, Color.GREEN};
    private static final int RECT_WIDTH = 146; // Largeur des rectangles
    private static final int RECT_HEIGHT = 90; // Hauteur des rectangles
<<<<<<< HEAD
=======
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    private ImageView imgView;
>>>>>>> 076e8a814926535e96616a33c2b1e27ec7befcf8

    public BoardView(GameBoard board,Stage boardStage) {
        this.board = board;
        
        Image backgroundImage = new Image(getClass().getResource("/resources/background_cyberpunk.jpg").toExternalForm()); // Remplace par le chemin réel de ton image
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.fitWidthProperty().bind(this.widthProperty());
        backgroundView.fitHeightProperty().bind(this.heightProperty());
<<<<<<< HEAD
       
=======
>>>>>>> 076e8a814926535e96616a33c2b1e27ec7befcf8
        this.getChildren().add(backgroundView);

        // Dessiner le plateau selon le chemin avec alternance de couleurs
        int index = 0;
        for (Case c : board.getChemin()) {
            Rectangle casePlateau = new Rectangle(RECT_WIDTH, RECT_HEIGHT);
            casePlateau.setFill(colors[index % colors.length]);
            casePlateau.setStroke(Color.BLACK);
            casePlateau.setX(c.getX() * RECT_WIDTH / 40); // Ajustement des proportions
            casePlateau.setY(c.getY() * RECT_HEIGHT / 40);
            this.getChildren().add(casePlateau);
            index++;
        }

     // Draw pawn
        Image pawnImage = new Image(getClass().getResource("/resources/pawns1.png").toExternalForm());
        pawnView = new ImageView(pawnImage);
        pawnView.setFitWidth(RECT_HEIGHT / 2);
        pawnView.setFitHeight(RECT_HEIGHT / 2);
        updatePawnPosition();
        this.getChildren().add(pawnView);
        
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
        
        imgView = new ImageView(new Image(getClass().getResource("/resources/button_son_off.png").toExternalForm()));
        imgView.setFitWidth(50);
        imgView.setFitHeight(50);
        
        StackPane btnMusic = new StackPane(imgView);
        btnMusic.setLayoutX(120);
        this.getChildren().add(btnMusic);

        btnMusic.setOnMouseClicked(event -> toggleMusic());

        String musicFile = getClass().getResource("/resources/pain.mp3").toExternalForm();
        Media sound = new Media(musicFile);
        mediaPlayer = new MediaPlayer(sound);
    }

    public void updatePawnPosition() {
        Case currentCase = board.getChemin().get(board.getPion().getIndex());
        
        // Update pawn position
        pawnView.setX(currentCase.getX() * RECT_WIDTH / 40 + RECT_WIDTH / 8);
        pawnView.setY(currentCase.getY() * RECT_HEIGHT / 40 + RECT_HEIGHT / 2);
        
        // Show popup with case color
        int caseIndex = board.getPion().getIndex();
        showCasePopup(colors[caseIndex % colors.length]);
    }
    
    /**
     * Displays a colored popup window with case information
     * @param caseColor The background color for the popup
     */
    private void showCasePopup(Color caseColor) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        
        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20));
        content.setStyle("-fx-background-color: #" + caseColor.toString().substring(2, 8) + ";");
        
        Label title = new Label("Case Details");
        title.setStyle("-fx-font-size: 20; -fx-text-fill: white; -fx-font-weight: bold;");
        
        int caseIndex = board.getPion().getIndex();
        Label info = new Label("Case #" + caseIndex + "\nColor: " + getColorName(caseColor));
        info.setStyle("-fx-font-size: 16; -fx-text-fill: white;");
        info.setTextAlignment(TextAlignment.CENTER);
        
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> popupStage.close());
        
        content.getChildren().addAll(title, info, closeButton);
        
        Scene scene = new Scene(content, 350, 250);
        popupStage.setScene(scene);

        // Correction : Use show() instead of showAndWait()
        PauseTransition delay = new PauseTransition(Duration.seconds(0.3));
        delay.setOnFinished(event -> {
            popupStage.show(); // Display without blocking thread
        });
        delay.play();
    }

    /**
     * Converts JavaFX Color to color name
     * @param color The color to convert
     * @return The color name in English
     */
    private String getColorName(Color color) {
        if (color.equals(Color.PURPLE)) return "Purple";
        if (color.equals(Color.ORANGE)) return "Orange";
        if (color.equals(Color.BLUE)) return "Blue";
        if (color.equals(Color.GREEN)) return "Green";
        return "Unknown";
    }

    /**
     * Creates a styled button
     * @param textContent Button text
     * @return Configured StackPane button
     */

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
    
    private void toggleMusic() {
        if (mediaPlayer == null) return; // Sécurité : Vérifier que mediaPlayer existe
        
        MediaPlayer.Status status = mediaPlayer.getStatus();
        if (status == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
            // Mettre l'image en OFF
            imgView.setImage(new Image(getClass().getResource("/resources/button_son_off.png").toExternalForm()));
        } else {
            mediaPlayer.play();
            // Mettre l'image en ON
            imgView.setImage(new Image(getClass().getResource("/resources/button_son_on.png").toExternalForm()));
        }
    }


}
