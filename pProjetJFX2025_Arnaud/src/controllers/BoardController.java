package controllers;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.GameBoard;
import views.BoardView;

public class BoardController {
    private final GameBoard board;
    private final BoardView boardView;

    public BoardController(GameBoard board, BoardView boardView) {
        this.board = board;
        this.boardView = boardView;
    }

    public void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.RIGHT) {
            board.deplacerPion(1);
            boardView.updatePawnPosition();
        } else if (event.getCode() == KeyCode.LEFT) {
            board.deplacerPion(-1);
            boardView.updatePawnPosition();
        }
    }
}
    //read the Json file 
    /*public void loadConfig() {
        try {
            JsonObject config = JsonUtils.readJson("src/resources/game_config.json");
            
            // Exemple : Extraire une valeur
            String boardColor = config.get("boardColor").getAsString();
            System.out.println("Couleur du plateau : " + boardColor);

        } catch (Exception e) {
            System.err.println("Erreur de lecture : " + e.getMessage());
        }
    }*/