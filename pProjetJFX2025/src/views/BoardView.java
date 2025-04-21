package views;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
import model.Question;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class BoardView extends Pane {
    private final GameBoard board;
    private final ImageView pawnView;
    private final Color[] colors = {Color.PURPLE, Color.ORANGE, Color.BLUE, Color.GREEN,
                                   Color.ORANGE, Color.ORANGE, Color.PURPLE, Color.GREEN};
    private static final int RECT_WIDTH = 146; // Rectangle width
    private static final int RECT_HEIGHT = 90; // Rectangle height
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    private ImageView imgView;

    public BoardView(GameBoard board, Stage boardStage) {
        this.board = board;
        
        // Load background image
        Image backgroundImage = new Image(getClass().getResource("/resources/background_cyberpunk.jpg").toExternalForm());
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.fitWidthProperty().bind(this.widthProperty());
        backgroundView.fitHeightProperty().bind(this.heightProperty());
        this.getChildren().add(backgroundView);

        // Draw game board path with alternating colors
        int index = 0;
        for (Case c : board.getChemin()) {
            Rectangle casePlateau = new Rectangle(RECT_WIDTH, RECT_HEIGHT);
            casePlateau.setFill(colors[index % colors.length]);
            casePlateau.setStroke(Color.BLACK);
            casePlateau.setX(c.getX() * RECT_WIDTH / 40); // Adjust proportions
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
        
        // Create menu return button
        StackPane btnMenu = createButton("Return to menu");
        
        btnMenu.setOnMouseClicked(event -> {
            Stage mainMenuStage = new Stage();
            MainMenuView mainMenuView = new MainMenuView(mainMenuStage);
            Scene mainMenuScene = new Scene(mainMenuView, 1920, 1080);
            mainMenuStage.setScene(mainMenuScene);
            mainMenuStage.setTitle("Main Menu");
            mainMenuStage.setMaximized(true);
            mainMenuStage.show();
            boardStage.close();
        });
        this.getChildren().add(btnMenu);
        
        // Create music toggle button
        imgView = new ImageView(new Image(getClass().getResource("/resources/button_son_off.png").toExternalForm()));
        imgView.setFitWidth(50);
        imgView.setFitHeight(50);
        
        StackPane btnMusic = new StackPane(imgView);
        btnMusic.setLayoutX(120);
        this.getChildren().add(btnMusic);

        btnMusic.setOnMouseClicked(event -> toggleMusic());

        // Initialize music player
        String musicFile = getClass().getResource("/resources/pain.mp3").toExternalForm();
        Media sound = new Media(musicFile);
        mediaPlayer = new MediaPlayer(sound);
    }

    /**
     * Updates the pawn's position on the board
     */
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
        
        // Add level selection
        Label levelLabel = new Label("What level are you choosing?:");
        levelLabel.setTextAlignment(TextAlignment.CENTER);
        levelLabel.setStyle("-fx-font-size: 16; -fx-text-fill: white;");
        
        VBox levelBox = new VBox(10);
        levelBox.setAlignment(Pos.CENTER);
        
        // Create level selection buttons
        for (int i = 1; i <= 4; i++) {
            Button levelButton = new Button("Level " + i);
            final int level = i;
            levelButton.setOnAction(e -> {
                showQuestionPopup(caseColor, level);
                popupStage.close();
            });
            levelBox.getChildren().add(levelButton);
        }
        
        content.getChildren().addAll(levelLabel, levelBox);
        
        Scene scene = new Scene(content, 400, 300);
        popupStage.setScene(scene);
        
        // Add slight delay before showing
        PauseTransition delay = new PauseTransition(Duration.seconds(0.3));
        delay.setOnFinished(event -> popupStage.show());
        delay.play();
    }
    
    /**
     * Displays the question popup for the selected level with user input and answer verification
     * @param caseColor The case color
     * @param userLevel The selected difficulty level
     */
    private void showQuestionPopup(Color caseColor, int userLevel) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        
        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20));
        content.setStyle("-fx-background-color: #" + caseColor.toString().substring(2, 8) + ";");
        
        // 1. Filter questions by selected level
        List<? extends Question> filteredQuestions = filterQuestionsByLevel(
            selectQuestionList(caseColor), 
            userLevel
        );
        
        // 2. Randomly select a question
        final Question randomQuestion = filteredQuestions != null && !filteredQuestions.isEmpty()
                ? filteredQuestions.get(new Random().nextInt(filteredQuestions.size()))
                : null;
        
        // 3. Display question
        Label title = new Label(randomQuestion != null ? randomQuestion.getQuestionContent() : "No questions available for this level");
        title.setStyle("-fx-font-size: 20; -fx-text-fill: white; -fx-font-weight: bold;");
        title.setWrapText(true);
        
        // Display case information
        int caseIndex = board.getPion().getIndex();
        Label info = new Label(String.format("Case #%d\nTheme: %s\nLevel: %s", 
            caseIndex, 
            randomQuestion != null ? randomQuestion.getTheme() : "N/A", 
            randomQuestion != null ? randomQuestion.getLevel() : "N/A"));
        info.setStyle("-fx-font-size: 16; -fx-text-fill: white;");
        info.setTextAlignment(TextAlignment.CENTER);
        
        // Add input field for user answer
        TextField answerField = new TextField();
        answerField.setPromptText("Entrez votre réponse...");
        answerField.setMaxWidth(300);
        
        // Add submit button for answer verification
        Button submitButton = new Button("Submit");
        Label feedbackLabel = new Label();
        feedbackLabel.setStyle("-fx-text-fill: white;");
        
        submitButton.setOnAction(e -> {
            if (randomQuestion == null) {
                feedbackLabel.setText("Erreur : Aucune question disponible.");
                return;
            }
            
            String userAnswer = answerField.getText();
            String correctAnswer = randomQuestion.getAnswer();
            
            // Verify answer (case-insensitive comparison)
            if (userAnswer.trim().equalsIgnoreCase(correctAnswer.trim())) {
                feedbackLabel.setText("Good answer !");
                // Move the pawn forward
                board.deplacerPion(userLevel);
            } else {
                feedbackLabel.setText("Wrong answer the right answer was : " + correctAnswer);
                // Move the pawn backward
                
            }
            
            submitButton.setDisable(true);
            answerField.setDisable(true);
            
            // Close the current popup before opening the next one
            PauseTransition delay = new PauseTransition(Duration.seconds(1)); // Small delay to show feedback
            delay.setOnFinished(event -> {
                popupStage.close(); // Close the current popup
                updatePawnPosition(); // Open the next popup
            });
            delay.play();
        });
        
        // Keep the "Show answer" functionality
        Button answerButton = new Button("Show answer");
        Label answerLabel = new Label();
        answerLabel.setStyle("-fx-text-fill: white;");
        
        answerButton.setOnAction(e -> {
            if (randomQuestion != null) {
                answerLabel.setText("Answer: " + randomQuestion.getAnswer());
                answerButton.setDisable(true);
            }
        });
        
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> popupStage.close());
        
        content.getChildren().addAll(title, info, answerField, submitButton, feedbackLabel, answerButton, answerLabel, closeButton);
        
        Scene scene = new Scene(content, 800, 600);
        popupStage.setScene(scene);
        
        // Add slight delay before showing
        PauseTransition delay = new PauseTransition(Duration.seconds(0.3));
        delay.setOnFinished(event -> popupStage.show());
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
    
    /**
     * Toggles music playback on/off
     */
    private void toggleMusic() {
        if (mediaPlayer == null) return; // Safety check
        
        MediaPlayer.Status status = mediaPlayer.getStatus();
        if (status == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
            // Set image to OFF state
            imgView.setImage(new Image(getClass().getResource("/resources/button_son_off.png").toExternalForm()));
        } else {
            mediaPlayer.play();
            // Set image to ON state
            imgView.setImage(new Image(getClass().getResource("/resources/button_son_on.png").toExternalForm()));
        }
    }
    
    /**
     * Selects the appropriate question list based on case color
     * @param color The case color
     * @return The corresponding question list
     */
    private List<? extends Question> selectQuestionList(Color color) {
        if (color.equals(Color.ORANGE)) return board.getEntertainmentQuestions();
        if (color.equals(Color.GREEN)) return board.getEducationQuestions();
        if (color.equals(Color.BLUE)) return board.getInformaticQuestions();
        if (color.equals(Color.PURPLE)) return board.getImprobableQuestions();
        return null;
    }
    
    /**
     * Filters questions by difficulty level
     * @param questions The list of questions to filter
     * @param level The difficulty level to filter by
     * @return Filtered list of questions
     */
    private List<? extends Question> filterQuestionsByLevel(List<? extends Question> questions, int level) {
        if (questions == null) return null;
        
        return questions.stream()
                       .filter(q -> q.getLevel() == level)
                       .collect(Collectors.toList());
    }
}