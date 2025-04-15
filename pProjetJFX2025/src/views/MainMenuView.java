package views;

import controllers.BoardController;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.GameBoard;
import model.Pion;

public class MainMenuView extends BorderPane {

    public MainMenuView(Stage primaryStage) {
        this.setStyle("-fx-background-color:yellow");

     // Arrière-plan
        ImageView background = new ImageView(new Image("/resources/Acceuil_Cyberpunk_2077.jpg"));
        background.fitWidthProperty().bind(this.widthProperty());
        background.fitHeightProperty().bind(this.heightProperty());
        

        // Images des boutons
        ImageView icon1 = createImageView("/resources/acceil.png", 63, 254, 120, 96);
        ImageView icon2 = createImageView("/resources/acceil.png", 207, 254, 120, 96);
        ImageView icon3 = createImageView("/resources/acceil.png", 348, 254, 120, 96);
        ImageView icon4 = createImageView("/resources/acceil2.png", 73, 261, 101, 66);
        ImageView icon5 = createImageView("/resources/acceil2.png", 216, 261, 101, 66);
        ImageView icon6 = createImageView("/resources/acceil2.png", 358, 260, 101, 66);
        ImageView title = createImageView("/resources/acceil3.png", 113, 82, 370, 180);
        ImageView image5 = createImageView("/resources/Image5.png", 48, 350, 702, 318);

        // Textes
        Text settingsText = createText("SETTINGS", 98, 298);
        Text starText = createText("START", 253, 298);
        Text adminText = createText("ADMIN SPACES", 367, 297);

        // Gestion du clic sur le bouton
        starText.setOnMouseClicked(event -> {
            // Initialize the Game Board
            GameBoard board = new GameBoard();
            Pion pion = new Pion(0); // Create the first pawn
            board.ajouterPion(pion); // Add the pawn to the board
            
            // Stage for the game board
            Stage boardStage = new Stage();
            BoardView boardView = new BoardView(board, boardStage);
            
            int nombreJoueurs = 1; // Assuming 2 players, adjust if needed
            BoardController controller = new BoardController(board, boardView, nombreJoueurs);
            boardView.setController(controller); // Set the controller to the board view
            
            // Create scene and show game stage
            Scene boardScene = new Scene(boardView, 1920, 1080);
            boardStage.setScene(boardScene);
            boardStage.setTitle("Game Board");
            boardStage.setMaximized(true); 
            boardStage.show();

            // Optionally, close the main menu after starting the game
            primaryStage.close();
        });

        
        this.getChildren().addAll(background, icon1, icon2, icon3, icon4, icon5, icon6, title, image5, settingsText, starText, adminText);
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

