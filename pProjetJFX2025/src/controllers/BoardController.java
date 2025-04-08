package controllers;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.GameBoard;
import model.Pion;
import model.Question;
import views.BoardView;

public class BoardController {
    private final GameBoard board;
    private final BoardView boardView;
    private int currentPlayerIndex;
    private Question currentQuestion;
    
    public BoardController(GameBoard board, BoardView boardView, int nombreJoueurs) {
        this.board = board;
        this.boardView = boardView;
        this.currentPlayerIndex = 0; // Start with the first player

        // Add pawns for the number of players
        for (int i = 0; i < nombreJoueurs; i++) {
            Pion pion = new Pion(0);  // Assign a different index for each pawn
            board.ajouterPion(pion);
        }
    }

    public void setCurrentQuestion(Question question) {
    	this.currentQuestion = question;
    }
    
//  Handle move of pawns thanks to question level
    public void handleAnswer(String userAnswer) {
    	if (currentQuestion != null && currentQuestion.checkAnswer(userAnswer)) {
    		int moveBy = currentQuestion.getLevel();
    		
    		board.deplacerPion(board.getPions().get(currentPlayerIndex), moveBy);
    		boardView.updatePawnPositions();
    	} else {
    		System.out.println("Wrong answer!");
    	}
    	currentQuestion = null;
    }
    
    
    public void handleKeyPress(KeyEvent event) {
        Pion currentPion = board.getPions().get(currentPlayerIndex);

        if (event.getCode() == KeyCode.RIGHT) {
            board.deplacerPion(currentPion, 1);
        } else if (event.getCode() == KeyCode.LEFT) {
            board.deplacerPion(currentPion, -1);
        }else if (event.getCode() == KeyCode.UP) {
            board.deplacerPion(currentPion, 2);
        }
        
        // Update positions and switch turn
        boardView.updatePawnPositions();
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