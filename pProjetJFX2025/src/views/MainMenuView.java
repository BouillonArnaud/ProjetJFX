package views;

import controllers.BoardController;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.GameBoard;
import model.Pawn;

import java.util.ArrayList;
import java.util.List;

public class MainMenuView extends BorderPane {

    public MainMenuView(Stage primaryStage) {
        this.setStyle("-fx-background-color:yellow");

        // Background
        ImageView background = new ImageView(new Image("/resources/Acceuil_Cyberpunk_2077.jpg"));
        background.fitWidthProperty().bind(this.widthProperty());
        background.fitHeightProperty().bind(this.heightProperty());

        // Button images
        ImageView icon1 = createImageView("/resources/acceil.png", 63, 254, 120, 96);
        ImageView icon2 = createImageView("/resources/acceil.png", 207, 254, 120, 96);
        ImageView icon3 = createImageView("/resources/acceil.png", 348, 254, 120, 96);
        ImageView icon4 = createImageView("/resources/acceil2.png", 73, 261, 101, 66);
        ImageView icon5 = createImageView("/resources/acceil2.png", 216, 261, 101, 66);
        ImageView icon6 = createImageView("/resources/acceil2.png", 358, 260, 101, 66);
        ImageView title = createImageView("/resources/acceil3.png", 113, 82, 370, 180);
        ImageView image5 = createImageView("/resources/Image5.png", 48, 350, 702, 318);

        // Texts
        Text settingsText = createText("SETTINGS", 98, 298);
        Text startText = createText("START", 253, 298);
        Text adminText = createText("ADMIN SPACES", 367, 297);

        // Handle click on Start button
        startText.setOnMouseClicked(event -> {
            // Créer une popup pour demander le nombre de joueurs et leurs noms
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initStyle(StageStyle.DECORATED);
            popupStage.setTitle("Players configuration");

            VBox content = new VBox(20);
            content.setAlignment(Pos.CENTER);
            content.setPadding(new Insets(20));
            content.setStyle("-fx-background-color: #333333;");

            Label promptLabel = new Label("Enter the number of players (2-4) :");
          promptLabel.setTextFill(Color.WHITE);

            TextField numberField = new TextField();
            numberField.setPromptText("2-4");
            numberField.setMaxWidth(100);

            VBox nameFields = new VBox(10);
            List<TextField> nameInputs = new ArrayList<>();

            Button submitButton = new Button("Start the game");
            submitButton.setDisable(true); // Désactivé jusqu'à ce que le nombre de joueurs soit valide

            numberField.textProperty().addListener((obs, oldValue, newValue) -> {
                nameFields.getChildren().clear();
                nameInputs.clear();
                try {
                    int nombreJoueurs = Integer.parseInt(newValue);
                    if (nombreJoueurs >= 2 && nombreJoueurs <= 4) {
                        for (int i = 0; i < nombreJoueurs; i++) {
                            TextField nameField = new TextField();
                            nameField.setPromptText("Nom du joueur " + (i + 1));
                            nameField.setMaxWidth(200);
                            nameInputs.add(nameField);
                            nameFields.getChildren().add(nameField);
                        }
                        submitButton.setDisable(false);
                    } else {
                        submitButton.setDisable(true);
                    }
                } catch (NumberFormatException ex) {
                    submitButton.setDisable(true);
                }
            });

            submitButton.setOnAction(e -> {
                try {
                    int nombreJoueurs = Integer.parseInt(numberField.getText());
                    if (nombreJoueurs >= 2 && nombreJoueurs <= 4) {
                        List<String> playerNames = new ArrayList<>();
                        for (TextField nameField : nameInputs) {
                            String name = nameField.getText().trim();
                            playerNames.add(name.isEmpty() ? "Joueur " + (playerNames.size() + 1) : name);
                        }

                        // Initialiser le plateau de jeu
                        GameBoard board = new GameBoard();

                        // Stage pour le plateau de jeu
                        Stage boardStage = new Stage();
                        BoardView boardView = new BoardView(board, boardStage);
                        BoardController controller = new BoardController(board, boardView, playerNames);
                        boardView.setController(controller);

                        // Afficher le popup pour le premier joueur
                        boardView.updatePawnPositions(); // Met à jour les positions des Pawns
                        Pawn firstPawn = board.getPawns().get(0); // Premier joueur
                        boardView.showCasePopup(firstPawn, boardView.getColors()[firstPawn.getIndex() % boardView.getColors().length]);

                        // Créer la scène et afficher le plateau
                        Scene boardScene = new Scene(boardView, 1920, 1080);
                        boardStage.setScene(boardScene);
                        boardStage.setTitle("Plateau de jeu");
                        boardStage.setMaximized(true);
                        boardStage.show();

                        // Fermer la popup et le menu principal
                        popupStage.close();
                        primaryStage.close();
                    } else {
                        promptLabel.setText("Veuillez entrer un nombre entre 2 et 4.");
                        promptLabel.setStyle("-fx-font-size: 16; -fx-text-fill: red;");
                    }
                } catch (NumberFormatException ex) {
                    promptLabel.setText("Veuillez entrer un nombre valide.");
                    promptLabel.setStyle("-fx-font-size: 16; -fx-text-fill: red;");
                }
            });

            content.getChildren().addAll(promptLabel, numberField, nameFields, submitButton);
            Scene popupScene = new Scene(content, 400, 400);
            popupStage.setScene(popupScene);
            popupStage.show();
        });

        this.getChildren().addAll(background, icon1, icon2, icon3, icon4, icon5, icon6, title, image5, settingsText,
                startText, adminText);
    }

    public StackPane createButton(String textContent) {
        Rectangle rectangle = new Rectangle(100, 50);
        rectangle.setFill(Color.BLACK);
        rectangle.setStroke(Color.BLACK);

        Text text = new Text(textContent);
        text.setFill(Color.WHITE);

        StackPane stack = new StackPane();
        stack.getChildren().addAll(rectangle, text);

        return stack;
    }

    private ImageView createImageView(String path, double x, double y, double width, double height) {
        ImageView imageView = new ImageView(new Image(path));
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        return imageView;
    }

    private Text createText(String content, double x, double y) {
        Text text = new Text(content);
        text.setFill(javafx.scene.paint.Color.WHITE);
        text.setLayoutX(x);
        text.setLayoutY(y);
        return text;
    }
}