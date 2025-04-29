package views;

import controllers.BoardController;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.GameBoard;
import model.Pion;

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
            // Create a popup for entering number of players and their names
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initStyle(StageStyle.TRANSPARENT);

            VBox content = new VBox(20);
            content.setAlignment(Pos.CENTER);
            content.setPadding(new Insets(30));
            content.setStyle("-fx-background-color: rgba(0, 0, 0, 0.9); -fx-background-radius: 20; -fx-border-color: #10B981; -fx-border-width: 2; -fx-border-radius: 20;");
            content.setEffect(new DropShadow(20, Color.BLACK));

            Label titleLabel = new Label("Player Configuration");
            titleLabel.setStyle("-fx-font-size: 24; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-family: 'Arial';");

            Label promptLabel = new Label("Enter the number of players (2-4):"); 
            promptLabel.setStyle("-fx-font-size: 18; -fx-text-fill: white; -fx-font-family: 'Arial';");

            TextField numberField = new TextField();
            numberField.setPromptText("2-4");
            numberField.setMaxWidth(100);
            numberField.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-padding: 8; -fx-font-size: 16;");

            VBox nameFields = new VBox(10);
            List<TextField> nameInputs = new ArrayList<>();

            HBox buttonBox = new HBox(20);
            buttonBox.setAlignment(Pos.CENTER);

            Button submitButton = new Button("Start Game"); // 
            submitButton.setStyle("-fx-background-color: #10B981; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 10; -fx-padding: 10 20;");
            submitButton.setDisable(true);
            addHoverEffect(submitButton);

            Button cancelButton = new Button("Cancel"); // tr
            cancelButton.setStyle("-fx-background-color: #EF4444; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 10; -fx-padding: 10 20;");
            cancelButton.setOnAction(e -> popupStage.close());
            addHoverEffect(cancelButton);

            numberField.textProperty().addListener((obs, oldValue, newValue) -> {
                nameFields.getChildren().clear();
                nameInputs.clear();
                try {
                    int nombreJoueurs = Integer.parseInt(newValue);
                    if (nombreJoueurs >= 2 && nombreJoueurs <= 4) {
                        for (int i = 0; i < nombreJoueurs; i++) {
                            TextField nameField = new TextField();
                            nameField.setPromptText("Playear name " + (i + 1)); 
                            nameField.setMaxWidth(200);
                            nameField.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-padding: 8; -fx-font-size: 16;");
                            nameInputs.add(nameField);
                            nameFields.getChildren().add(nameField);
                        }
                        submitButton.setDisable(false); 
                        promptLabel.setText("Enter the number of players (2-4):");
                        promptLabel.setStyle("-fx-font-size: 18; -fx-text-fill: white; -fx-font-family: 'Arial';");
                    } else {
                        submitButton.setDisable(true);
                        promptLabel.setText("Enter the number of players (2-4):");
                        promptLabel.setStyle("-fx-font-size: 18; -fx-text-fill: red; -fx-font-family: 'Arial';");
                    }
                } catch (NumberFormatException ex) {
                    submitButton.setDisable(true);
                    promptLabel.setText("Please enter a valid number."); 
                    promptLabel.setStyle("-fx-font-size: 18; -fx-text-fill: red; -fx-font-family: 'Arial';");
                }
            });

            submitButton.setOnAction(e -> {
                try {
                    int nombreJoueurs = Integer.parseInt(numberField.getText());
                    if (nombreJoueurs >= 2 && nombreJoueurs <= 4) {
                        List<String> playerNames = new ArrayList<>();
                        for (TextField nameField : nameInputs) {
                            String name = nameField.getText().trim();
                            playerNames.add(name.isEmpty() ? "Player " + (playerNames.size() + 1) : name);  
                        }

                        // Initialize the game board
                        GameBoard board = new GameBoard();

                        // Stage for the game board
                        Stage boardStage = new Stage();
                        BoardView boardView = new BoardView(board, boardStage);
                        BoardController controller = new BoardController(board, boardView, playerNames);
                        boardView.setController(controller);

                        // Show popup for the first player
                        boardView.updatePawnPositions();
                        Pion firstPion = board.getPions().get(0);
                        boardView.showCasePopup(firstPion, boardView.getColors()[firstPion.getIndex() % boardView.getColors().length]);

                        // Create the scene and show the board
                        Scene boardScene = new Scene(boardView, 1920, 1080);
                        boardStage.setScene(boardScene);
                        boardStage.setTitle("Plateau de jeu");
                        boardStage.setMaximized(true);
                        boardStage.show();

                        // Close the popup and main menu
                        popupStage.close();
                        primaryStage.close();
                    } else {
                        promptLabel.setText("Enter the number of players (2-4):"); //
                        promptLabel.setStyle("-fx-font-size: 18; -fx-text-fill: red; -fx-font-family: 'Arial';");
                    }
                } catch (NumberFormatException ex) {
                    promptLabel.setText("Please enter a valid number.");
                    promptLabel.setStyle("-fx-font-size: 18; -fx-text-fill: red; -fx-font-family: 'Arial';");
                }
            });

            content.getChildren().addAll(titleLabel, promptLabel, numberField, nameFields, buttonBox);
            buttonBox.getChildren().addAll(submitButton, cancelButton);

            Scene popupScene = new Scene(content, 500, 500);
            popupScene.setFill(Color.TRANSPARENT);
            popupStage.setScene(popupScene);

            // Fade-in animation
            content.setOpacity(0);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), content);
            fadeIn.setToValue(1);
            fadeIn.play();

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
        text.setFill(Color.WHITE);
        text.setLayoutX(x);
        text.setLayoutY(y);
        return text;
    }

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
}