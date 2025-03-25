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
            board.deplacerPion(1); // Avancer d'une case
        } else if (event.getCode() == KeyCode.LEFT) {
            board.deplacerPion(-1); // Reculer d'une case
        }
        boardView.updatePionPosition();
    }
}
