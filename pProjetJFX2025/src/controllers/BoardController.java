package controllers;

import com.google.gson.JsonObject;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.GameBoard;
import model.JsonUtils;
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
            if (board.deplacerPion(1)) {
                showCasePopup("Pion déplacé à droite"); // APPEL ICI
            }
        } else if (event.getCode() == KeyCode.LEFT) {
            if (board.deplacerPion(-1)) {
                showCasePopup("Pion déplacé à gauche"); // ET ICI
            }
        }
        boardView.updatePionPosition();
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
    
    private void showCasePopup(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Position du pion");
        alert.setHeaderText("Nouvelle position : Case " + board.getPion().getIndex());
        alert.setContentText(message);
        alert.showAndWait();
    }
}
