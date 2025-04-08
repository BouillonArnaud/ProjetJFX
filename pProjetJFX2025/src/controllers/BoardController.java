package controllers;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.GameBoard;
import views.BoardView;
import model.Question;

public class BoardController {
    private final GameBoard board;
    private final BoardView boardView;
    private Question currentQuestion;

    public BoardController(GameBoard board, BoardView boardView) {
        this.board = board;
        this.boardView = boardView;
    }
    
    public void setCurrentQuestion(Question question) {
    	this.currentQuestion = question;
    }
    
//  Handle move of pawns thanks to question level
    public void handleAnswer(String userAnswer) {
    	if (currentQuestion != null && currentQuestion.checkAnswer(userAnswer)) {
    		int moveBy = currentQuestion.getLevel();
    		
    		board.deplacerPion(moveBy);
    		boardView.updatePawnPosition();
    	} else {
    		System.out.println("Wrong answer!");
    	}
    	currentQuestion = null;
    }

//  Handle move of pawns by pressing right or left arrows
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