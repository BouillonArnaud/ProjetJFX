package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.stage.*;
import model.Case;
import model.GameBoard;

public class BoardView extends Pane {
    private final GameBoard board;
    private final ImageView pawnView;
    private final Color[] colors = {Color.PURPLE, Color.ORANGE, Color.BLUE, Color.GREEN,
                                  Color.ORANGE, Color.ORANGE, Color.PURPLE, Color.GREEN};
    private static final int RECT_WIDTH = 146; // Rectangle width
    private static final int RECT_HEIGHT = 90; // Rectangle height

    public BoardView(GameBoard board, Stage boardStage) {
        this.board = board;
        
        // Set background image
        Image backgroundImage = new Image(getClass().getResource("/resources/background_cyberpunk.jpg").toExternalForm());
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.fitWidthProperty().bind(this.widthProperty());
        backgroundView.fitHeightProperty().bind(this.heightProperty());
        this.getChildren().add(backgroundView);

        // Draw game board with alternating colors
        int index = 0;
        for (Case c : board.getChemin()) {
            Rectangle cell = new Rectangle(RECT_WIDTH, RECT_HEIGHT);
            cell.setFill(colors[index % colors.length]);
            cell.setStroke(Color.BLACK);
            cell.setX(c.getX() * RECT_WIDTH / 40); // Proportional adjustment
            cell.setY(c.getY() * RECT_HEIGHT / 40);
            this.getChildren().add(cell);
            index++;
        }

        // Draw pawn
        Image pawnImage = new Image(getClass().getResource("/resources/pawns1.png").toExternalForm());
        pawnView = new ImageView(pawnImage);
        pawnView.setFitWidth(RECT_HEIGHT / 2);
        pawnView.setFitHeight(RECT_HEIGHT / 2);
        updatePawnPosition();
        this.getChildren().add(pawnView);
        
        // Menu button
        StackPane menuButton = createButton("Return to menu");
        menuButton.setOnMouseClicked(event -> {
            Stage mainMenuStage = new Stage();
            MainMenuView mainMenuView = new MainMenuView(mainMenuStage);
            Scene mainMenuScene = new Scene(mainMenuView, 1920, 1080);
            mainMenuStage.setScene(mainMenuScene);
            mainMenuStage.setTitle("Main Menu");
            mainMenuStage.setMaximized(true);
            mainMenuStage.show();
            boardStage.close();
        });
        this.getChildren().add(menuButton);
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
        
        // Title
        Label title = new Label("Case Details");
        title.setStyle("-fx-font-size: 20; -fx-text-fill: white; -fx-font-weight: bold;");
        
        // Information
        int caseIndex = board.getPion().getIndex();
        Label info = new Label("Case #" + caseIndex + "\nColor: " + getColorName(caseColor));
        info.setStyle("-fx-font-size: 16; -fx-text-fill: white;");
        info.setTextAlignment(TextAlignment.CENTER);
        
        // Close button
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> popupStage.close());
        
        content.getChildren().addAll(title, info, closeButton);
        
        Scene scene = new Scene(content, 350, 250);
        popupStage.setScene(scene);
        popupStage.showAndWait();
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
}