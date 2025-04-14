package views;

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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Case;
import model.GameBoard;
import model.QuestionEducation;
import model.QuestionEntertainment;
import model.QuestionImprobable;
import model.QuestionInformatic;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.util.Collections;

public class BoardView extends Pane {
    private final GameBoard board;
    private ImageView pawnView;
    private final Color[] colors = {Color.PURPLE, Color.ORANGE, Color.BLUE, Color.GREEN,
                                   Color.ORANGE, Color.BLUE, Color.PURPLE, Color.GREEN};
    private static final int RECT_WIDTH = 146;
    private static final int RECT_HEIGHT = 90;
    private MediaPlayer mediaPlayer;
    private ImageView imgView;

    public BoardView(GameBoard board, Stage boardStage) {
        this.board = board;
        this.pawnView = new ImageView();
        initializeUI(boardStage);
    }

    private void initializeUI(Stage boardStage) {
        Image backgroundImage = new Image(getClass().getResource("/resources/background_cyberpunk.jpg").toExternalForm());
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.fitWidthProperty().bind(this.widthProperty());
        backgroundView.fitHeightProperty().bind(this.heightProperty());
        this.getChildren().add(backgroundView);

        // Dessiner le plateau
        int index = 0;
        for (Case c : board.getChemin()) {
            Rectangle casePlateau = new Rectangle(RECT_WIDTH, RECT_HEIGHT);
            casePlateau.setFill(colors[index % colors.length]);
            casePlateau.setStroke(Color.BLACK);
            casePlateau.setX(c.getX() * RECT_WIDTH / 40);
            casePlateau.setY(c.getY() * RECT_HEIGHT / 40);
            this.getChildren().add(casePlateau);
            index++;
        }

        // Initialiser le pion
        Image pawnImage = new Image(getClass().getResource("/resources/pawns1.png").toExternalForm());
        pawnView = new ImageView(pawnImage);
        pawnView.setFitWidth(RECT_HEIGHT / 2);
        pawnView.setFitHeight(RECT_HEIGHT / 2);
        updatePawnPosition();
        this.getChildren().add(pawnView);
        
        // Mélanger les questions au début du jeu
        shuffleQuestions();
        
        // Bouton menu
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
            
            // Arrêter la musique quand on retourne au menu
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
        });
        this.getChildren().add(btnMenu);
        
        // Bouton musique
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

    // Mélanger toutes les questions
    private void shuffleQuestions() {
        Collections.shuffle(board.getEducationQuestions());
        Collections.shuffle(board.getEntertainmentQuestions());
        Collections.shuffle(board.getImprobableQuestions());
        Collections.shuffle(board.getInformaticQuestions());
    }

    public void updatePawnPosition() {
        Case currentCase = board.getChemin().get(board.getPion().getIndex());
        pawnView.setX(currentCase.getX() * RECT_WIDTH / 40 + RECT_WIDTH / 8);
        pawnView.setY(currentCase.getY() * RECT_HEIGHT / 40 + RECT_HEIGHT / 2);
        
        int caseIndex = board.getPion().getIndex();
        showCasePopup(colors[caseIndex % colors.length], caseIndex);
    }
    
    private void showCasePopup(Color caseColor, int caseIndex) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        
        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20));
        content.setStyle("-fx-background-color: #" + caseColor.toString().substring(2, 8) + ";");
        
        String colorName = getColorName(caseColor);
        String questionContent = "Plus de questions disponibles dans ce thème!";
        String answer = "Veuillez recharger le jeu.";
        String theme = colorName;
        
        // Obtenir une question selon la couleur
        switch(colorName.toLowerCase()) {
        case "purple":
            if (!board.getImprobableQuestions().isEmpty()) {
                QuestionImprobable question = board.getImprobableQuestions().remove(0); // <-- retire la question
                questionContent = question.getQuestionContent();
                answer = question.getAnswer();
                theme = "Improbable";
            }
            break;
        case "orange":
            if (!board.getEntertainmentQuestions().isEmpty()) {
                QuestionEntertainment question = board.getEntertainmentQuestions().remove(0); // <-- retire la question
                questionContent = question.getQuestionContent();
                answer = question.getAnswer();
                theme = "Entertainment";
            }
            break;
        case "blue":
            if (!board.getInformaticQuestions().isEmpty()) {
                QuestionInformatic question = board.getInformaticQuestions().remove(0); // <-- retire la question
                questionContent = question.getQuestionContent();
                answer = question.getAnswer();
                theme = "Informatique";
            }
            break;
        case "green":
            if (!board.getEducationQuestions().isEmpty()) {
                QuestionEducation question = board.getEducationQuestions().remove(0); // <-- retire la question
                questionContent = question.getQuestionContent();
                answer = question.getAnswer();
                theme = "Education";
            }
            break;
    }

        Label title = new Label("Case #" + caseIndex + " - " + theme);
        title.setStyle("-fx-font-size: 20; -fx-text-fill: white; -fx-font-weight: bold;");
        
        Label questionLabel = new Label("Question:\n" + questionContent);
        questionLabel.setStyle("-fx-font-size: 16; -fx-text-fill: white;");
        questionLabel.setTextAlignment(TextAlignment.CENTER);
        questionLabel.setWrapText(true);
        
        Label answerLabel = new Label("Réponse: " + answer);
        answerLabel.setStyle("-fx-font-size: 14; -fx-text-fill: white; -fx-font-style: italic;");
        answerLabel.setTextAlignment(TextAlignment.CENTER);
        answerLabel.setWrapText(true);
        
        Button closeButton = new Button("Fermer");
        closeButton.setOnAction(e -> popupStage.close());
        
        content.getChildren().addAll(title, questionLabel, answerLabel, closeButton);
        
        Scene scene = new Scene(content, 400, 300);
        popupStage.setScene(scene);

        PauseTransition delay = new PauseTransition(Duration.seconds(0.3));
        delay.setOnFinished(event -> popupStage.show());
        delay.play();
    }

    private String getColorName(Color color) {
        if (color.equals(Color.PURPLE)) return "Purple";
        if (color.equals(Color.ORANGE)) return "Orange";
        if (color.equals(Color.BLUE)) return "Blue";
        if (color.equals(Color.GREEN)) return "Green";
        return "Unknown";
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
    
    private void toggleMusic() {
        if (mediaPlayer == null) return;
        
        MediaPlayer.Status status = mediaPlayer.getStatus();
        if (status == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
            imgView.setImage(new Image(getClass().getResource("/resources/button_son_off.png").toExternalForm()));
        } else {
            mediaPlayer.play();
            imgView.setImage(new Image(getClass().getResource("/resources/button_son_on.png").toExternalForm()));
        }
    }
}