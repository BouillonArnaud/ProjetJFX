package application;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class HelpPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Root Pane
        Pane root = new Pane();
        root.setPrefSize(1920, 1080);

        // Background Image
        ImageView background = new ImageView(new Image(getClass().getResourceAsStream("/resources/Page2.jpg")));
        background.setFitWidth(1920);
        background.setFitHeight(1080);
        background.setPreserveRatio(true);
        background.setPickOnBounds(true);

        // Logo Image
        ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("/resources/Role_Game_Title.png")));
        logo.setFitWidth(853);
        logo.setFitHeight(216);
        logo.setLayoutX(790);
        logo.setLayoutY(21);
        logo.setPreserveRatio(true);
        logo.setPickOnBounds(true);

        // Additional Decorative Image
        ImageView decorImage = new ImageView(new Image(getClass().getResourceAsStream("/resources/acceuil6.png")));
        decorImage.setFitWidth(540);
        decorImage.setFitHeight(539);
        decorImage.setLayoutY(540);
        decorImage.setPreserveRatio(true);
        decorImage.setPickOnBounds(true);

        // Sidebar Image
        ImageView sidebar = new ImageView(new Image(getClass().getResourceAsStream("/resources/Image7.png")));
        sidebar.setFitWidth(526);
        sidebar.setFitHeight(216);
        sidebar.setLayoutX(1394);
        sidebar.setPreserveRatio(true);
        sidebar.setPickOnBounds(true);

        // TextArea for Game Rules
        TextArea textArea = new TextArea(
                "Objective of the Game\n" +
                        "Be the first team to reach the last square on the board and successfully answer a final question.\n\n" +
                        "Gameplay\n" +
                        "The game contains 4 themes, each associated with a color:\n\n" +
                        "Mauve: Improbable, which covers quirky topics (yawning, Breton pancakes…).\n" +
                        "Orange: Entertainment (divertissement), which focuses on leisure-related topics (movies, music, etc.).\n" +
                        "Blue: Informatics (informatique), in the field of OOP and analysis.\n" +
                        "Green: Education.\n" +
                        "There are also special types of cards:\n\n" +
                        "Challenge, with random subjects.\n" +
                        "Not at all, usually cards designed to slow you down.\n" +
                        "Superb, cards that can help you move forward faster if you answer correctly.\n" +
                        "These themes are determined by the location of your pawn.\n\n" +
                        "At the beginning of the game, all teams will start with the \"Improbable\" theme. The opposing team draws a card from the theme associated with the location of the pawn.\n\n" +
                        "They ask you the famous question:\n\n" +
                        "\"How confident are you in…?\"\n\n" +
                        "Your team evaluates its knowledge on the given theme, on a difficulty scale from 1 (easy question) to 10 (expert question).\n\n" +
                        "You generally have 30 seconds to answer, but you can adjust the time as you see fit.\n\n" +
                        "If you answer correctly, you move forward the number of squares corresponding to your score. If not, you stay on your square. Roles are exchanged clockwise.\n\n" +
                        "Once you reach the last square, the opposing team draws a \"Don't hesitate to win\" card. If you answer correctly, you win the game; otherwise, you will have to try again in the next round with a new question."
        );
        textArea.setFont(new Font(22));
        textArea.setLayoutX(549);
        textArea.setLayoutY(213);
        textArea.setPrefWidth(1228);
        textArea.setPrefHeight(756);
        textArea.setWrapText(true);
        textArea.setDisable(true);

        // Add all components to the root pane
        root.getChildren().addAll(background, logo, decorImage, sidebar, textArea);

        // Set up the Scene and Stage
        Scene scene = new Scene(root);
        primaryStage.setTitle("Rules");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

