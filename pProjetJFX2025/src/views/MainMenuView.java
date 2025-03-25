package views;

import controllers.BoardController;
import javafx.scene.Scene;
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

        StackPane btnStart = createButton("Start");
//        this.getChildren().add(btnStart);
        this.setCenter(btnStart);

        // Gestion du clic sur le bouton
        btnStart.setOnMouseClicked(event -> {
            GameBoard board = new GameBoard();
            Pion pion = new Pion(0);
            board.ajouterPion(pion);// Instancier le modèle du plateau (à adapter)
            Stage boardStage = new Stage();
            BoardView boardView = new BoardView(board,boardStage);
            BoardController controller = new BoardController(board, boardView);
            
            Scene boardScene = new Scene(boardView, 1920, 1080);
            boardScene.setOnKeyPressed(controller :: handleKeyPress);
            boardStage.setScene(boardScene);
            boardStage.setTitle("Board View");
            boardStage.setMaximized(true);
            boardStage.show();
            
            // Optionnel : Fermer le menu principal
            primaryStage.close();
        });
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
}

