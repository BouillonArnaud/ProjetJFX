package views;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Case;
import model.GameBoard;

public class BoardView extends Pane {
    private final GameBoard board;
    private final Circle pionGraphique;
    private final Color[] couleurs = {Color.PURPLE, Color.ORANGE, Color.BLUE, Color.GREEN,
                                      Color.ORANGE, Color.ORANGE, Color.PURPLE, Color.GREEN};
    private static final int RECT_WIDTH = 100; // Largeur des rectangles
    private static final int RECT_HEIGHT = 60; // Hauteur des rectangles

    public BoardView(GameBoard board,Stage boardStage) {
        this.board = board;
        
        Image backgroundImage = new Image(getClass().getResource("/ressources/images.jpg").toExternalForm()); // Remplace par le chemin réel de ton image
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(1000); // Ajuste selon la taille souhaitée
        backgroundView.setFitHeight(800);
        this.getChildren().add(backgroundView);

        // Dessiner le plateau selon le chemin avec alternance de couleurs
        int index = 0;
        for (Case c : board.getChemin()) {
            Rectangle casePlateau = new Rectangle(RECT_WIDTH, RECT_HEIGHT);
            casePlateau.setFill(couleurs[index % couleurs.length]);
            casePlateau.setStroke(Color.BLACK);
            casePlateau.setX(c.getX() * RECT_WIDTH / 40); // Ajustement des proportions
            casePlateau.setY(c.getY() * RECT_HEIGHT / 40);
            this.getChildren().add(casePlateau);
            index++;
        }

        // Dessiner le pion
        pionGraphique = new Circle(RECT_HEIGHT / 2, Color.RED);
        updatePionPosition();
        this.getChildren().add(pionGraphique);
        
       StackPane btnMenu = createButton("Return to menu");
       
        
        btnMenu.setOnMouseClicked(event -> {
        	 Stage mainMenuStage = new Stage();
             MainMenuView mainMenuView = new MainMenuView(mainMenuStage);
             Scene mainMenuScene = new Scene(mainMenuView, 1000, 800);
             mainMenuStage.setScene(mainMenuScene);
             mainMenuStage.setTitle("Menu Principal");
             mainMenuStage.show();
             boardStage.close();
        });
        this.getChildren().add(btnMenu);
    }

    public void updatePionPosition() {
        Case currentCase = board.getChemin().get(board.getPion().getIndex());
        
        pionGraphique.setCenterX(currentCase.getX() * RECT_WIDTH / 40 + RECT_WIDTH / 2);
        pionGraphique.setCenterY(currentCase.getY() * RECT_HEIGHT / 40 + RECT_HEIGHT / 2);
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

}
