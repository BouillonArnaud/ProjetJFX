package views;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import controllers.BoardController;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Case;
import model.GameBoard;
import model.Pion;
import model.Question;

public class BoardView extends Pane {
    private final GameBoard board;
    private final List<ImageView> pawnViews = new ArrayList<>(); // For visual pawns
    private final Color[] colors = { 
        Color.web("#D7BDE2"), // Pastel Purple
        Color.web("#FAD7A0"), // Pastel Orange
        Color.web("#AED6F1"), // Pastel Blue
        Color.web("#A9DFBF"), // Pastel Green
    };
    private static final int RECT_WIDTH = 146; // Largeur des rectangles
    private static final int RECT_HEIGHT = 90; // Hauteur des rectangles
    private MediaPlayer mediaPlayer;
    private ImageView imgView;
    private Stage currentPopup = null; // Store the current popup stage
    private int currentPlayerIndex;
    private BoardController controller;
    private final Stage boardStage;
    private HBox navBar; // Barre de navigation
    private Label currentPlayerLabel; // Label pour le joueur actuel
    private ImageView currentPlayerPawn; // Pion du joueur actuel
    private VBox scoresBox; // Conteneur pour les scores

    public BoardView(GameBoard board, Stage boardStage) {
        this.board = board;
        this.currentPlayerIndex = 0;
        this.boardStage = boardStage;

        Image backgroundImage = new Image("/resources/background_cyberpunk.jpg");
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.fitWidthProperty().bind(this.widthProperty());
        backgroundView.fitHeightProperty().bind(this.heightProperty());
        this.getChildren().add(backgroundView);

        // Draw the board with alternating colors
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

        // Créer la barre de navigation
        navBar = new HBox(20);
        navBar.setAlignment(Pos.CENTER);
        navBar.setPadding(new Insets(10));
        navBar.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");
        navBar.setPrefHeight(60);
        navBar.setMinWidth(1920); // Largeur pleine

        // Section joueur actuel
        HBox currentPlayerBox = new HBox(10);
        currentPlayerBox.setAlignment(Pos.CENTER);
        currentPlayerLabel = new Label("Joueur actuel : En attente...");
        currentPlayerLabel.setStyle("-fx-font-size: 18; -fx-text-fill: white; -fx-font-weight: bold;");
        currentPlayerPawn = new ImageView();
        currentPlayerPawn.setFitWidth(30);
        currentPlayerPawn.setFitHeight(30);
        currentPlayerBox.getChildren().addAll(currentPlayerPawn, currentPlayerLabel);

        // Section scores
        scoresBox = new VBox(5);
        scoresBox.setAlignment(Pos.CENTER);
        updateScoresDisplay(); // Initialiser les scores

        navBar.getChildren().addAll(currentPlayerBox, scoresBox);
        navBar.setLayoutX(0);
        navBar.setLayoutY(0);
        this.getChildren().add(navBar);

        updatePawnPositions();

        StackPane btnMenu = createButton("Return to menu");
        btnMenu.setLayoutX(10);
        btnMenu.setLayoutY(70); // Décalé pour éviter la barre
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

        imgView = new ImageView(new Image("/resources/button_son_off.png"));
        imgView.setFitWidth(50);
        imgView.setFitHeight(50);

        StackPane btnMusic = new StackPane(imgView);
        btnMusic.setLayoutX(120);
        btnMusic.setLayoutY(70); // Décalé
        btnMusic.setOnMouseClicked(event -> toggleMusic());

        String musicFile = getClass().getResource("/resources/pain.mp3").toExternalForm();
        Media sound = new Media(musicFile);
        mediaPlayer = new MediaPlayer(sound);

        StackPane showPopupQuestion = createButton("LevelPopup");
        showPopupQuestion.setLayoutX(10);
        showPopupQuestion.setLayoutY(130); // Décalé
        showPopupQuestion.setOnMouseClicked(event -> {
            List<Pion> pions = board.getPions();
            Pion pion = pions.get(currentPlayerIndex);
            showCasePopup(pion, colors[pion.getIndex() % colors.length]);
        });

        this.getChildren().addAll(btnMenu, btnMusic, showPopupQuestion);
    }

    public Color[] getColors() {
        return colors;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
        List<Pion> pions = board.getPions();
        if (!pions.isEmpty()) {
            Pion currentPion = pions.get(currentPlayerIndex);
            currentPlayerLabel.setText("Joueur actuel : " + currentPion.getName());
            String pawnImagePath = "/resources/pawns" + (currentPlayerIndex + 1) + ".png";
            currentPlayerPawn.setImage(new Image(pawnImagePath));
        } else {
            currentPlayerLabel.setText("Joueur actuel : Aucun joueur");
            currentPlayerPawn.setImage(null);
        }
        updateScoresDisplay();
    }

    public void updatePawnPositions() {
        List<Pion> pions = board.getPions();

        // Add new pawns if necessary
        if (pawnViews.size() < pions.size()) {
            for (int i = pawnViews.size(); i < pions.size(); i++) {
                String imagePath = "/resources/pawns" + (i + 1) + ".png";
                Image pawnImage = new Image(imagePath);
                ImageView pawnView = new ImageView(pawnImage);
                pawnView.setFitWidth(RECT_HEIGHT / 2);
                pawnView.setFitHeight(RECT_HEIGHT / 2);
                pawnViews.add(pawnView);
                this.getChildren().add(pawnView);
            }
        }

        // Update pawn positions
        for (int i = 0; i < pions.size(); i++) {
            Pion pion = pions.get(i);
            Case currentCase = board.getChemin().get(pion.getIndex());
            ImageView pawnView = pawnViews.get(i);

            System.out.println("Pawn " + i + " -> Case " + pion.getIndex() + " -> X: " + currentCase.getX() + ", Y: " + currentCase.getY());

            int cornerOffsetX = 0;
            int cornerOffsetY = 0;

            switch (i) {
                case 0:
                    cornerOffsetX = 0;
                    cornerOffsetY = 0;
                    break;
                case 1:
                    cornerOffsetX = RECT_WIDTH / 2;
                    cornerOffsetY = 0;
                    break;
                case 2:
                    cornerOffsetX = 0;
                    cornerOffsetY = RECT_HEIGHT / 2;
                    break;
                case 3:
                    cornerOffsetX = RECT_WIDTH / 2;
                    cornerOffsetY = RECT_HEIGHT / 2;
                    break;
                default:
                    break;
            }

            double xPosition = currentCase.getX() * RECT_WIDTH / 40 + cornerOffsetX;
            double yPosition = currentCase.getY() * RECT_HEIGHT / 40 + cornerOffsetY;

            pawnView.setX(xPosition);
            pawnView.setY(yPosition);
            if (i == currentPlayerIndex) {
                if (!(pion.getIndex() == board.getChemin().size() - 1)) {
                    showCasePopup(pion, colors[pion.getIndex() % colors.length]);
                } else {
                    controller.showLevel4Question(pion);
                }
            }
        }
    }

    public void showCasePopup(Pion pion, Color caseColor) {
        if (currentPopup != null) {
            currentPopup.close();
        }

        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);

        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20));
        content.setStyle("-fx-background-color: #" + caseColor.toString().substring(2, 8) + ";");

        Label title = new Label("Détails de la case");
        title.setStyle("-fx-font-size: 20; -fx-text-fill: white; -fx-font-weight: bold;");

        int caseIndex = pion.getIndex();
        Label info = new Label("Position : Case #" + caseIndex + "\nCouleur : " + getColorName(caseColor));
        info.setStyle("-fx-font-size: 16; -fx-text-fill: white;");
        info.setTextAlignment(TextAlignment.CENTER);

        Label levelLabel = new Label("Quel niveau choisissez-vous ? :");
        levelLabel.setTextAlignment(TextAlignment.CENTER);
        levelLabel.setStyle("-fx-font-size: 16; -fx-text-fill: white;");

        VBox levelBox = new VBox(10);
        levelBox.setAlignment(Pos.CENTER);

        for (int i = 1; i <= 4; i++) {
            Button levelButton = new Button("Niveau " + i);
            final int level = i;
            levelButton.setOnAction(e -> {
                showQuestionPopup(pion, caseColor, level);
                popupStage.close();
            });
            levelBox.getChildren().add(levelButton);
        }

        content.getChildren().addAll(title, info, levelLabel, levelBox);

        Scene scene = new Scene(content, 400, 300);
        popupStage.setScene(scene);

        PauseTransition delay = new PauseTransition(Duration.seconds(0.3));
        delay.setOnFinished(event -> popupStage.show());
        delay.play();
    }

    private String getColorName(Color color) {
        if (color.equals(Color.web("#D7BDE2")))
            return "Violet Pastel";
        if (color.equals(Color.web("#FAD7A0")))
            return "Orange Pastel";
        if (color.equals(Color.web("#AED6F1")))
            return "Bleu Pastel";
        if (color.equals(Color.web("#A9DFBF")))
            return "Vert Pastel";
        return "Inconnu";
    }

    public StackPane createButton(String textContent) {
        Rectangle rectangle = new Rectangle(100, 50);
        rectangle.setFill(Color.web("#F5B7B1")); // Pastel Pink for buttons
        rectangle.setStroke(Color.BLACK);

        Text text = new Text(textContent);
        text.setFill(Color.BLACK);

        StackPane stack = new StackPane();
        stack.getChildren().addAll(rectangle, text);

        return stack;
    }

    private void showQuestionPopup(Pion pion, Color caseColor, int userLevel) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initStyle(StageStyle.UNDECORATED);

        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20));
        content.setStyle("-fx-background-color: #" + caseColor.toString().substring(2, 8) + ";");

        // Filtrer les questions par niveau
        List<? extends Question> filteredQuestions = filterQuestionsByLevel(selectQuestionList(caseColor), userLevel);

        // Sélectionner une question aléatoire
        final Question randomQuestion = filteredQuestions != null && !filteredQuestions.isEmpty()
                ? filteredQuestions.get(new Random().nextInt(filteredQuestions.size()))
                : null;

        // Afficher la question
        Label title = new Label(
                randomQuestion != null ? randomQuestion.getQuestionContent() : "Aucune question disponible pour ce niveau");
        title.setStyle("-fx-font-size: 20; -fx-text-fill: white; -fx-font-weight: bold;");
        title.setWrapText(true);

        // Afficher les informations de la case
        int caseIndex = pion.getIndex();
        Label info = new Label(String.format("Case #%d\nThème : %s\nNiveau : %s", caseIndex,
                randomQuestion != null ? randomQuestion.getTheme() : "N/A",
                randomQuestion != null ? randomQuestion.getLevel() : "N/A"));
        info.setStyle("-fx-font-size: 16; -fx-text-fill: white;");
        info.setTextAlignment(TextAlignment.CENTER);

        // Champ de saisie pour la réponse
        TextField answerField = new TextField();
        answerField.setPromptText("Entrez votre réponse...");
        answerField.setMaxWidth(300);

        // Étiquette du minuteur
        Label timerLabel = new Label("Temps restant : 30");
        timerLabel.setStyle("-fx-font-size: 16; -fx-text-fill: white;");

        // Bouton de soumission
        Button submitButton = new Button("Soumettre");
        Label feedbackLabel = new Label();
        feedbackLabel.setStyle("-fx-text-fill: white;");

        // Configuration du minuteur
        final int[] timeLeft = {30};
        Timeline timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeLeft[0]--;
            timerLabel.setText("Temps restant : " + timeLeft[0]);
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

        // Fonctionnalité pour révéler la réponse
        if (randomQuestion != null) {
            Button answerButton = new Button("Montrer la réponse");
            Label answerLabel = new Label();
            answerLabel.setStyle("-fx-text-fill: white;");

            answerButton.setOnAction(e -> {
                answerLabel.setText("Réponse : " + randomQuestion.getAnswer());
                answerButton.setDisable(true);
            });

            content.getChildren().addAll(title, info, timerLabel, answerField, submitButton, feedbackLabel, answerButton,
                    answerLabel);
        }

        Scene scene = new Scene(content, 800, 600);
        popupStage.setScene(scene);

        PauseTransition delay = new PauseTransition(Duration.seconds(0.3));
        delay.setOnFinished(event -> popupStage.show());
        delay.play();

        // Arrêter le minuteur si la popup est fermée manuellement
        popupStage.setOnCloseRequest(e -> timer.stop());
    }

    private void toggleMusic() {
        if (mediaPlayer == null)
            return;

        MediaPlayer.Status status = mediaPlayer.getStatus();
        if (status == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
            imgView.setImage(new Image(getClass().getResource("/resources/button_son_off.png").toExternalForm()));
        } else {
            mediaPlayer.play();
            imgView.setImage(new Image(getClass().getResource("/resources/button_son_on.png").toExternalForm()));
        }
    }

    private List<? extends Question> selectQuestionList(Color color) {
        if (color.equals(Color.web("#FAD7A0")))
            return board.getEntertainmentQuestions();
        if (color.equals(Color.web("#A9DFBF")))
            return board.getEducationQuestions();
        if (color.equals(Color.web("#AED6F1")))
            return board.getInformaticQuestions();
        if (color.equals(Color.web("#D7BDE2")))
            return board.getImprobableQuestions();
        return null;
    }

    private List<? extends Question> filterQuestionsByLevel(List<? extends Question> questions, int level) {
        if (questions == null)
            return null;

        return questions.stream().filter(q -> q.getLevel() == level).collect(Collectors.toList());
    }

    public void setController(BoardController controller) {
        this.controller = controller;
        System.out.println("Controller set successfully.");
    }

    public void showFinalQuestionPopup(Pion pion) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initStyle(StageStyle.UNDECORATED);

        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20));
        content.setStyle("-fx-background-color: #A9DFBF;"); // Pastel Green for final question

        List<? extends Question> level4Questions = filterQuestionsByLevel(
                selectQuestionList(colors[pion.getIndex() % colors.length]), 4);

        Question randomQuestion = level4Questions != null && !level4Questions.isEmpty()
                ? level4Questions.get(new Random().nextInt(level4Questions.size()))
                : null;

        Label title = new Label("Question finale");
        title.setStyle("-fx-font-size: 20; -fx-text-fill: white; -fx-font-weight: bold;");

        Label questionLabel = new Label(randomQuestion.getQuestionContent());
        questionLabel.setStyle("-fx-font-size: 16; -fx-text-fill: white;");
        questionLabel.setWrapText(true);

        TextField answerField = new TextField();
        answerField.setPromptText("Entrez votre réponse...");

        Button submitButton = new Button("Soumettre la réponse");
        submitButton.setOnAction(e -> {
            if (answerField.getText().equals("A")) {
                showEndGamePopup();
                popupStage.close();
            } else {
                System.out.println("Mauvaise réponse !");
                popupStage.close();
                controller.transitionToNextPlayer();
            }
        });

        content.getChildren().addAll(title, questionLabel, answerField, submitButton);

        Scene scene = new Scene(content, 400, 300);
        popupStage.setScene(scene);
        popupStage.show();
    }

    private void showEndGamePopup() {
        Stage endGameStage = new Stage();
        endGameStage.initModality(Modality.APPLICATION_MODAL);

        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20));
        content.setStyle("-fx-background-color: #F5B7B1;"); // Pastel Pink for end game

        Label title = new Label("Félicitations !");
        title.setStyle("-fx-font-size: 20; -fx-text-fill: black; -fx-font-weight: bold;");

        Label message = new Label("Vous avez gagné le jeu !");
        message.setStyle("-fx-font-size: 16; -fx-text-fill: black;");

        Button closeButton = new Button("Retour au menu principal");
        closeButton.setOnAction(e -> {
            endGameStage.close();
            boardStage.close();

            Stage mainMenuStage = new Stage();
            MainMenuView mainMenuView = new MainMenuView(mainMenuStage);
            Scene mainMenuScene = new Scene(mainMenuView, 1920, 1080);
            mainMenuStage.setScene(mainMenuScene);
            mainMenuStage.setTitle("Menu principal");
            mainMenuStage.setMaximized(true);
            mainMenuStage.show();
        });

        content.getChildren().addAll(title, message, closeButton);

        Scene scene = new Scene(content, 400, 300);
        endGameStage.setScene(scene);
        endGameStage.show();
    }

    private void updateScoresDisplay() {
        scoresBox.getChildren().clear();
        for (Pion pion : board.getPions()) {
            Label scoreLabel = new Label(pion.getName() + " : " + pion.getScore() + " points");
            scoreLabel.setStyle("-fx-font-size: 16; -fx-text-fill: white;");
            scoresBox.getChildren().add(scoreLabel);
        }
    }
}