package views;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import controllers.BoardController;
import controllers.HelpPageController;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Case;
import model.GameBoard;
import model.Pawn;
import model.Question;

public class BoardView extends Pane {
    private final GameBoard board;
    private final List<ImageView> pawnViews = new ArrayList<>();
    private final Color[] colors = {
        Color.web("#C3B1E1"), // Pastel Lavender (Improbable)
        Color.web("#FFD1A3"), // Pastel Peach (Entertainment)
        Color.web("#A3BFFA"), // Pastel Blue (Informatics)
        Color.web("#A7EBC6"), // Pastel Mint (Education)
    };
    private static final int RECT_WIDTH = 160;
    private static final int RECT_HEIGHT = 100;
    private MediaPlayer mediaPlayer;
    private ImageView imgView;
    private Stage currentPopup = null;
    private int currentPlayerIndex;
    private BoardController controller;
    private final Stage boardStage;
    private HBox navBar;
    private Label currentPlayerLabel;
    private ImageView currentPlayerPawn;
    private VBox scoresBox;
    private Rectangle lastCaseRectangle; // Store the Rectangle for the last case

    public BoardView(GameBoard board, Stage boardStage) {
        this.board = board;
        this.currentPlayerIndex = 0;
        this.boardStage = boardStage;

        // Background with animated overlay
        Image backgroundImage = new Image("/resources/background_cyberpunk.jpg");
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.fitWidthProperty().bind(this.widthProperty());
        backgroundView.fitHeightProperty().bind(this.heightProperty());
        backgroundView.setOpacity(0.8); // Adjusted opacity for better visibility
        this.getChildren().add(backgroundView);

        // Animated overlay for dynamic effect
        Rectangle overlay = new Rectangle();
        overlay.widthProperty().bind(this.widthProperty());
        overlay.heightProperty().bind(this.heightProperty());
        overlay.setFill(new LinearGradient(0, 0, 1, 1, true, null,
                new Stop(0, Color.rgb(0, 0, 0, 0.2)),
                new Stop(1, Color.rgb(0, 0, 0, 0.4))));
        FadeTransition fade = new FadeTransition(Duration.seconds(5), overlay);
        fade.setFromValue(0.3);
        fade.setToValue(0.6);
        fade.setAutoReverse(true);
        fade.setCycleCount(Timeline.INDEFINITE);
        fade.play();
        this.getChildren().add(overlay);

        // Draw the board with glowing cases
        int index = 0;
        for (Case c : board.getPath()) {
            Rectangle boardCase = new Rectangle(RECT_WIDTH, RECT_HEIGHT);
            boardCase.setFill(colors[index % colors.length]);
            boardCase.setStroke(Color.BLACK);
            boardCase.setArcWidth(20);
            boardCase.setArcHeight(20);
            boardCase.setX(c.getX() * RECT_WIDTH / 40);
            boardCase.setY(c.getY() * RECT_HEIGHT / 40);
            boardCase.setEffect(new DropShadow(10, Color.BLACK));
            this.getChildren().add(boardCase);
            // Store the Rectangle for the last case (index 32)
            if (index == board.getPath().size() - 1) {
                lastCaseRectangle = boardCase;
            }
            index++;
        }

        // Navigation bar
        navBar = new HBox(30);
        navBar.setAlignment(Pos.CENTER);
        navBar.setPadding(new Insets(10, 20, 10, 20));
        navBar.setStyle("-fx-background-color: linear-gradient(to right, rgba(0, 0, 0, 0.85), rgba(30, 30, 30, 0.85)); -fx-background-radius: 15; -fx-border-color: #10B981; -fx-border-width: 2; -fx-border-radius: 15;");
        navBar.setPrefHeight(100);
        navBar.setMaxWidth(1400);
        navBar.setEffect(new DropShadow(15, Color.BLACK));

        // Current player section
        HBox currentPlayerBox = new HBox(10);
        currentPlayerBox.setAlignment(Pos.CENTER);
        currentPlayerLabel = new Label("Current Player: Waiting...");
        currentPlayerLabel.setStyle("-fx-font-size: 20; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-font-family: 'Arial';");
        currentPlayerPawn = new ImageView();
        currentPlayerPawn.setFitWidth(50);
        currentPlayerPawn.setFitHeight(50);
        currentPlayerPawn.setEffect(new Glow(0.4));
        currentPlayerBox.getChildren().addAll(currentPlayerPawn, currentPlayerLabel);

        // Scores section
        scoresBox = new VBox(5);
        scoresBox.setAlignment(Pos.CENTER);
        updateScoresDisplay();

        navBar.getChildren().addAll(currentPlayerBox, scoresBox);
        navBar.setLayoutX(0);
        navBar.setLayoutY(0);
        navBar.layoutXProperty().bind(this.widthProperty().subtract(navBar.widthProperty()).divide(2)); // Center horizontally
        this.getChildren().add(navBar);

        // Color legend
        HBox legendHBox = new HBox(20);
        legendHBox.setAlignment(Pos.CENTER);
        legendHBox.setPadding(new Insets(10, 20, 10, 20));
        legendHBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.85); -fx-background-radius: 10; -fx-border-color: #10B981; -fx-border-width: 2; -fx-border-radius: 10;");
        legendHBox.setEffect(new DropShadow(10, Color.BLACK));

        for (Color color : colors) {
            Rectangle colorRect = new Rectangle(20, 20);
            colorRect.setFill(color);
            colorRect.setStroke(Color.BLACK);
            colorRect.setArcWidth(5);
            colorRect.setArcHeight(5);

            Label colorLabel = new Label(getThemeName(color));
            colorLabel.setStyle("-fx-font-size: 16; -fx-text-fill: white; -fx-font-family: 'Arial';");

            HBox colorBox = new HBox(5);
            colorBox.setAlignment(Pos.CENTER);
            colorBox.getChildren().addAll(colorRect, colorLabel);
            legendHBox.getChildren().add(colorBox);
        }

        legendHBox.layoutXProperty().bind(this.widthProperty().subtract(legendHBox.widthProperty()).divide(2));
        legendHBox.setLayoutY(850); // Position at bottom
        this.getChildren().add(legendHBox);

        updatePawnPositions();

        // Menu button
        StackPane btnMenu = createButton("Back to Menu", Color.web("#EF4444"));
        btnMenu.setLayoutX(20);
        btnMenu.setLayoutY(100);
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

        // Music toggle button
        imgView = new ImageView(new Image("/resources/button_son_off.png"));
        imgView.setFitWidth(60);
        imgView.setFitHeight(60);
        StackPane btnMusic = new StackPane(imgView);
        btnMusic.setLayoutX(20);
        btnMusic.setLayoutY(20);
        btnMusic.setOnMouseClicked(event -> toggleMusic());
        addHoverEffect(btnMusic);

        String musicFile = getClass().getResource("/resources/pain.mp3").toExternalForm();
        Media sound = new Media(musicFile);
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        // Question popup button
        StackPane showPopupQuestion = createButton("Launch Question", Color.web("#10B981"));
        showPopupQuestion.setLayoutX(20);
        showPopupQuestion.setLayoutY(180);
        showPopupQuestion.setOnMouseClicked(event -> {
            List<Pawn> Pawns = board.getPawns();
            Pawn Pawn = Pawns.get(currentPlayerIndex);
            showCasePopup(Pawn, colors[Pawn.getIndex() % colors.length]);
        });

        // Help button
        ImageView helpIcon = new ImageView(new Image("/resources/help_icon.png"));
        helpIcon.setFitWidth(60);
        helpIcon.setFitHeight(60);
        StackPane btnHelp = new StackPane(helpIcon);
        btnHelp.setLayoutX(1450); // Position in top-right corner
        btnHelp.setLayoutY(20);
        btnHelp.setOnMouseClicked(event -> {
            Stage helpStage = new Stage();
            new HelpPageController(helpStage);
        });
        Tooltip helpTooltip = new Tooltip("View Game Rules");
        helpTooltip.setStyle("-fx-font-size: 14; -fx-background-color: rgba(0, 0, 0, 0.85); -fx-text-fill: white;");
        Tooltip.install(btnHelp, helpTooltip);
        addHoverEffect(btnHelp);

        this.getChildren().addAll(btnMenu, btnMusic, showPopupQuestion, btnHelp);
    }

    public Color[] getColors() {
        return colors;
    }

//  use to set pawn + player information
    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
        List<Pawn> Pawns = board.getPawns();
        if (!Pawns.isEmpty()) {
            Pawn currentPawn = Pawns.get(currentPlayerIndex);
            currentPlayerLabel.setText("Current Player: " + currentPawn.getName());
            String pawnImagePath = "/resources/pawns" + (currentPlayerIndex + 1) + ".png";
            currentPlayerPawn.setImage(new Image(pawnImagePath));
        } else {
            currentPlayerLabel.setText("Current Player: No Player");
            currentPlayerPawn.setImage(null);
        }
        updateScoresDisplay();
    }

//  update the position of the pawn
    public void updatePawnPositions() {
        List<Pawn> Pawns = board.getPawns();

        if (pawnViews.size() < Pawns.size()) {
            for (int i = pawnViews.size(); i < Pawns.size(); i++) {
                String imagePath = "/resources/pawns" + (i + 1) + ".png";
                Image pawnImage = new Image(imagePath);
                ImageView pawnView = new ImageView(pawnImage);
                pawnView.setFitWidth(RECT_HEIGHT / 2);
                pawnView.setFitHeight(RECT_HEIGHT / 2);
                pawnView.setEffect(new Glow(0.3));
                pawnViews.add(pawnView);
                this.getChildren().add(pawnView);
            }
        }

        for (int i = 0; i < Pawns.size(); i++) {
            Pawn Pawn = Pawns.get(i);
            Case currentCase = board.getPath().get(Pawn.getIndex());
            ImageView pawnView = pawnViews.get(i);

            int cornerOffsetX = 0;
            int cornerOffsetY = 0;
            switch (i) {
                case 0: cornerOffsetX = 0; cornerOffsetY = 0; break;
                case 1: cornerOffsetX = RECT_WIDTH / 2; cornerOffsetY = 0; break;
                case 2: cornerOffsetX = 0; cornerOffsetY = RECT_HEIGHT / 2; break;
                case 3: cornerOffsetX = RECT_WIDTH / 2; cornerOffsetY = RECT_HEIGHT / 2; break;
            }

            double xPosition = currentCase.getX() * RECT_WIDTH / 40 + cornerOffsetX;
            double yPosition = currentCase.getY() * RECT_HEIGHT / 40 + cornerOffsetY;

            // Animate pawn movement
            TranslateTransition transition = new TranslateTransition(Duration.millis(500), pawnView);
            transition.setToX(xPosition);
            transition.setToY(yPosition);
            transition.play();

            if (i == currentPlayerIndex) {
                if (Pawn.getIndex() == board.getPath().size() - 1) {
                    controller.showLevel4Question(Pawn);
                } else {
                    showCasePopup(Pawn, colors[Pawn.getIndex() % colors.length]);
                }
            }
        }
    }

//  show the popup where you can choose the level
    public void showCasePopup(Pawn Pawn, Color caseColor) {
        if (currentPopup != null) {
            currentPopup.close();
        }

        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initStyle(StageStyle.TRANSPARENT);

        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(30));
        content.setStyle("-fx-background-color: rgba(0, 0, 0, 0.9); -fx-background-radius: 20; -fx-border-color: " + toHex(caseColor) + "; -fx-border-width: 2; -fx-border-radius: 20;");
        content.setEffect(new DropShadow(20, Color.BLACK));

        Label title = new Label("Choose Your Challenge");
        title.setStyle("-fx-font-size: 24; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-family: 'Arial';");

        int caseIndex = Pawn.getIndex();
        Label info = new Label("Case #" + caseIndex + "\nTheme: " + getColorDisplayName(caseColor));
        info.setStyle("-fx-font-size: 18; -fx-text-fill: white; -fx-font-family: 'Arial';");
        info.setTextAlignment(TextAlignment.CENTER);

        Label levelLabel = new Label("Select a Level:");
        levelLabel.setStyle("-fx-font-size: 18; -fx-text-fill: white; -fx-font-family: 'Arial';");

        HBox levelBox = new HBox(10);
        levelBox.setAlignment(Pos.CENTER);
        for (int i = 1; i <= 4; i++) {
            Button levelButton = new Button("Level " + i);
            levelButton.setStyle("-fx-background-color: " + toHex(caseColor) + "; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 10; -fx-padding: 10 20;");
            final int level = i;
            levelButton.setOnAction(e -> {
                showQuestionPopup(Pawn, caseColor, level);
                popupStage.close();
            });
            addHoverEffect(levelButton);
            levelBox.getChildren().add(levelButton);
        }

        // Add Close button
        Button closeButton = new Button("Close");
        closeButton.setStyle("-fx-background-color: #6B7280; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 10; -fx-padding: 10 20;");
        closeButton.setOnAction(e -> popupStage.close());
        addHoverEffect(closeButton);

        content.getChildren().addAll(title, info, levelLabel, levelBox, closeButton);

        Scene scene = new Scene(content, 500, 400);
        scene.setFill(Color.TRANSPARENT);
        popupStage.setScene(scene);

        // Fade-in animation
        content.setOpacity(0);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), content);
        fadeIn.setToValue(1);
        fadeIn.play();

        PauseTransition delay = new PauseTransition(Duration.seconds(0.3));
        delay.setOnFinished(event -> popupStage.show());
        delay.play();
        currentPopup = popupStage;
    }

//  return the name of the color as a string
    private String getColorDisplayName(Color color) {
        if (color.equals(Color.web("#C3B1E1"))) return "Pastel Lavender";
        if (color.equals(Color.web("#FFD1A3"))) return "Pastel Peach";
        if (color.equals(Color.web("#A3BFFA"))) return "Pastel Blue";
        if (color.equals(Color.web("#A7EBC6"))) return "Pastel Mint";
        return "Unknown";
    }

//  return theme name based on color
    private String getThemeName(Color color) {
        if (color.equals(Color.web("#C3B1E1"))) return "Improbable";
        if (color.equals(Color.web("#FFD1A3"))) return "Entertainment";
        if (color.equals(Color.web("#A3BFFA"))) return "Informatics";
        if (color.equals(Color.web("#A7EBC6"))) return "Education";
        return "Unknown";
    }

    private String toHex(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

//  use to create a button
    private StackPane createButton(String textContent, Color bgColor) {
        Rectangle rectangle = new Rectangle(140, 60);
        rectangle.setFill(bgColor);
        rectangle.setStroke(Color.BLACK);
        rectangle.setArcWidth(15);
        rectangle.setArcHeight(15);
        rectangle.setEffect(new DropShadow(10, Color.BLACK));

        Text text = new Text(textContent);
        text.setFill(Color.WHITE);
        text.setFont(Font.font("Arial", 16));

        StackPane stack = new StackPane();
        stack.getChildren().addAll(rectangle, text);
        addHoverEffect(stack);

        return stack;
    }

//  add an hover effect to a javafx element
    private void addHoverEffect(javafx.scene.Node node) {
        ScaleTransition scale = new ScaleTransition(Duration.millis(200), node);
        scale.setToX(1.1);
        scale.setToY(1.1);
        node.setOnMouseEntered(e -> scale.playFromStart());
        node.setOnMouseExited(e -> {
            ScaleTransition reverse = new ScaleTransition(Duration.millis(200), node);
            reverse.setToX(1);
            reverse.setToY(1);
            reverse.play();
        });
    }

//  show the popup where the question is displayed
    private void showQuestionPopup(Pawn Pawn, Color caseColor, int userLevel) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initStyle(StageStyle.TRANSPARENT);

        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(30));
        content.setStyle("-fx-background-color: rgba(0, 0, 0, 0.9); -fx-background-radius: 20; -fx-border-color: " + toHex(caseColor) + "; -fx-border-width: 2; -fx-border-radius: 20;");
        content.setEffect(new DropShadow(20, Color.BLACK));

        List<? extends Question> filteredQuestions = filterQuestionsByLevel(selectQuestionList(caseColor), userLevel);
        final Question randomQuestion = filteredQuestions != null && !filteredQuestions.isEmpty()
                ? filteredQuestions.get(new Random().nextInt(filteredQuestions.size()))
                : null;

        Label title = new Label(randomQuestion != null ? randomQuestion.getQuestionContent() : "No Question Available");
        title.setStyle("-fx-font-size: 22; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-family: 'Arial';");
        title.setWrapText(true);
        title.setMaxWidth(600);

        int caseIndex = Pawn.getIndex();
        Label info = new Label(String.format("Case #%d\nTheme: %s\nLevel: %s", caseIndex,
                randomQuestion != null ? randomQuestion.getTheme() : "N/A",
                randomQuestion != null ? randomQuestion.getLevel() : "N/A"));
        info.setStyle("-fx-font-size: 18; -fx-text-fill: white; -fx-font-family: 'Arial';");
        info.setTextAlignment(TextAlignment.CENTER);

        TextField answerField = new TextField();
        answerField.setPromptText("Enter your answer...");
        answerField.setMaxWidth(400);
        answerField.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-padding: 10;");

        Label timerLabel = new Label("Time Remaining: 30");
        timerLabel.setStyle("-fx-font-size: 18; -fx-text-fill: white; -fx-font-family: 'Arial';");

        Button submitButton = new Button("Submit");
        submitButton.setStyle("-fx-background-color: " + toHex(caseColor) + "; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 10; -fx-padding: 10 20;");
        addHoverEffect(submitButton);

        Label feedbackLabel = new Label();
        feedbackLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16;");

        final int[] timeLeft = {30};
        Timeline timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeLeft[0]--;
            timerLabel.setText("Time Remaining: " + timeLeft[0]);
            if (timeLeft[0] <= 0) {
                controller.transitionToNextPlayer();
                popupStage.close();
            }
        }));
        timer.setCycleCount(30);
        timer.play();

        submitButton.setOnAction(e -> {
            timer.stop();
            String userAnswer = answerField.getText();
            controller.setCurrentQuestion(randomQuestion);
            controller.handleAnswer(userAnswer);
            popupStage.close();
        });

        if (randomQuestion != null) {
            Button answerButton = new Button("Show Answer");
            answerButton.setStyle("-fx-background-color: #6B7280; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 10; -fx-padding: 10 20;");
            addHoverEffect(answerButton);
            Label answerLabel = new Label();
            answerLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16;");

            answerButton.setOnAction(e -> {
                answerLabel.setText("Answer: " + randomQuestion.getAnswer());
                answerButton.setDisable(true);
            });

            content.getChildren().addAll(title, info, timerLabel, answerField, submitButton, feedbackLabel, answerButton, answerLabel);
        } else {
            content.getChildren().addAll(title, info, timerLabel, answerField, submitButton, feedbackLabel);
        }

        Scene scene = new Scene(content, 800, 600);
        scene.setFill(Color.TRANSPARENT);
        popupStage.setScene(scene);

        content.setOpacity(0);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), content);
        fadeIn.setToValue(1);
        fadeIn.play();

        PauseTransition delay = new PauseTransition(Duration.seconds(0.3));
        delay.setOnFinished(event -> popupStage.show());
        delay.play();

        popupStage.setOnCloseRequest(e -> timer.stop());
        currentPopup = popupStage;
    }

//  use to turn on/off the music
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

//  select the right question list based on color
    private List<? extends Question> selectQuestionList(Color color) {
        if (color.equals(Color.web("#FFD1A3"))) return board.getEntertainmentQuestions();
        if (color.equals(Color.web("#A7EBC6"))) return board.getEducationQuestions();
        if (color.equals(Color.web("#A3BFFA"))) return board.getInformaticQuestions();
        if (color.equals(Color.web("#C3B1E1"))) return board.getImprobableQuestions();
        return null;
    }

//  use to filter a question list by the level
    private List<? extends Question> filterQuestionsByLevel(List<? extends Question> questions, int level) {
        if (questions == null) return null;
        return questions.stream().filter(q -> q.getLevel() == level).collect(Collectors.toList());
    }

    public void setController(BoardController controller) {
        this.controller = controller;
        System.out.println("Controller set successfully.");
    }

//  show the popup with the final question with random theme + level 4 question
    public void showFinalQuestionPopup(Pawn Pawn) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initStyle(StageStyle.TRANSPARENT);

        // Select a random theme (color) for the last case
        Random random = new Random();
        Color randomColor = colors[random.nextInt(colors.length)];
        // Update the last case's color to match the random theme
        if (lastCaseRectangle != null) {
            lastCaseRectangle.setFill(randomColor);
        }

        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(30));
        content.setStyle("-fx-background-color: rgba(0, 0, 0, 0.9); -fx-background-radius: 20; -fx-border-color: " + toHex(randomColor) + "; -fx-border-width: 2; -fx-border-radius: 20;");
        content.setEffect(new DropShadow(20, Color.BLACK));

        List<? extends Question> level4Questions = filterQuestionsByLevel(selectQuestionList(randomColor), 4);
        Question randomQuestion = level4Questions != null && !level4Questions.isEmpty()
                ? level4Questions.get(random.nextInt(level4Questions.size()))
                : null;

        Label title = new Label("Final Question");
        title.setStyle("-fx-font-size: 24; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-family: 'Arial';");

        Label info = new Label(String.format("Case #%d\nTheme: %s\nLevel: 4", board.getPath().size() - 1,
                randomQuestion != null ? getThemeName(randomColor) : "N/A"));
        info.setStyle("-fx-font-size: 18; -fx-text-fill: white; -fx-font-family: 'Arial';");
        info.setTextAlignment(TextAlignment.CENTER);

        Label questionLabel = new Label(randomQuestion != null ? randomQuestion.getQuestionContent() : "Question Unavailable");
        questionLabel.setStyle("-fx-font-size: 18; -fx-text-fill: white; -fx-font-family: 'Arial';");
        questionLabel.setWrapText(true);
        questionLabel.setMaxWidth(600);

        Label timerLabel = new Label("Time Remaining: 30");
        timerLabel.setStyle("-fx-font-size: 18; -fx-text-fill: white; -fx-font-family: 'Arial';");

        TextField answerField = new TextField();
        answerField.setPromptText("Enter your answer...");
        answerField.setMaxWidth(400);
        answerField.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-padding: 10;");

        Button submitButton = new Button("Submit Answer");
        submitButton.setStyle("-fx-background-color: " + toHex(randomColor) + "; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 10; -fx-padding: 10 20;");
        addHoverEffect(submitButton);

        final int[] timeLeft = {30}; // Time left for the question
        Timeline timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeLeft[0]--; // Decrease time left
            timerLabel.setText("Time Remaining: " + timeLeft[0]); // Update timer label
            if (timeLeft[0] <= 0) { // If time is up
                controller.transitionToNextPlayer(); // Transition to the next player
                popupStage.close();
            }
        }));
        timer.setCycleCount(30);
        timer.play();

        submitButton.setOnAction(e -> { // Submit answer button action
            timer.stop(); // Stop the timer when the button is clicked
            if (randomQuestion != null && answerField.getText().equalsIgnoreCase(randomQuestion.getAnswer())) { // Check if the answer is correct
                showEndGamePopup(Pawn); // Pass the winning Pawn	
                popupStage.close(); // Close the popup
            } else {
                System.out.println("Incorrect answer!");
                popupStage.close();
                controller.transitionToNextPlayer();
            }
        });
        
        if (randomQuestion != null) {
            Button answerButton = new Button("Show Answer");
            answerButton.setStyle("-fx-background-color: #6B7280; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 10; -fx-padding: 10 20;");
            addHoverEffect(answerButton);
            Label answerLabel = new Label();
            answerLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16;");

            answerButton.setOnAction(e -> {
                answerLabel.setText("Answer: " + randomQuestion.getAnswer());
                answerButton.setDisable(true);
            });
            
            content.getChildren().addAll(title, info, questionLabel, timerLabel, answerField, submitButton,answerButton,answerLabel);
        } else {
        	content.getChildren().addAll(title, info, questionLabel, timerLabel, answerField, submitButton);
        }

        Scene scene = new Scene(content, 1000, 800);
        scene.setFill(Color.TRANSPARENT);
        popupStage.setScene(scene);

        content.setOpacity(0);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), content);
        fadeIn.setToValue(1);
        fadeIn.play();

        popupStage.setOnCloseRequest(e -> timer.stop());
        popupStage.show();
        currentPopup = popupStage;
    }

//  show the winner popup with winner player information
    private void showEndGamePopup(Pawn winner) {
        Stage endGameStage = new Stage();
        endGameStage.initModality(Modality.APPLICATION_MODAL);
        endGameStage.initStyle(StageStyle.TRANSPARENT);

        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(30));
        content.setStyle("-fx-background-color: rgba(0, 0, 0, 0.9); -fx-background-radius: 20; -fx-border-color: #EF4444; -fx-border-width: 2; -fx-border-radius: 20;");
        content.setEffect(new DropShadow(20, Color.BLACK));

        Label title = new Label("Congratulations!");
        title.setStyle("-fx-font-size: 24; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-family: 'Arial';");

        // Display winner's name
        Label winnerLabel = new Label("Winner: " + winner.getName()); // Display winner's name 
        winnerLabel.setStyle("-fx-font-size: 20; -fx-text-fill: white; -fx-font-family: 'Arial';");

        // Display winner's pawn
        int winnerIndex = board.getPawns().indexOf(winner);// Get the index of the winner and use it to load the image
        ImageView winnerPawn = new ImageView(new Image("/resources/pawns" + (winnerIndex + 1) + ".png"));
        winnerPawn.setFitWidth(50);
        winnerPawn.setFitHeight(50);
        winnerPawn.setEffect(new Glow(0.4));

        Label message = new Label("You Have Won!");
        message.setStyle("-fx-font-size: 18; -fx-text-fill: white; -fx-font-family: 'Arial';");

        Button closeButton = new Button("Back to Main Menu");
        closeButton.setStyle("-fx-background-color: #EF4444; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 10; -fx-padding: 10 20;");
        addHoverEffect(closeButton);
        closeButton.setOnAction(e -> {
            endGameStage.close();
            boardStage.close();
            Stage mainMenuStage = new Stage();
            MainMenuView mainMenuView = new MainMenuView(mainMenuStage);
            Scene mainMenuScene = new Scene(mainMenuView, 1920, 1080);
            mainMenuStage.setScene(mainMenuScene);
            mainMenuStage.setTitle("Main Menu");
            mainMenuStage.setMaximized(true);
            mainMenuStage.show();
        });

        content.getChildren().addAll(title, winnerPawn, winnerLabel, message, closeButton);

        Scene scene = new Scene(content, 500, 400);
        scene.setFill(Color.TRANSPARENT);
        endGameStage.setScene(scene);

        content.setOpacity(0);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), content);
        fadeIn.setToValue(1);
        fadeIn.play();

        endGameStage.show();
    }

//  update score display based on the score of the model
    private void updateScoresDisplay() {
        scoresBox.getChildren().clear();
        for (Pawn Pawn : board.getPawns()) {
            Label scoreLabel = new Label(Pawn.getName() + ": " + Pawn.getScore() + " points");
            scoreLabel.setStyle("-fx-font-size: 18; -fx-text-fill: white; -fx-font-family: 'Arial';");
            scoresBox.getChildren().add(scoreLabel);
        }
    }
}