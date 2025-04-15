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
		for (int i = 0; i < (nombreJoueurs - 1); i++) {
			Pion pion = new Pion(0); // Assign a different index for each pawn
			board.ajouterPion(pion);
		}

		boardView.setCurrentPlayerIndex(currentPlayerIndex);
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

	// Handle moving the pawn and checking if the last case is reached
	public void handleAnswerTest(int userlevel) {
		Pion currentPion = board.getPions().get(currentPlayerIndex);
		if (currentPion.getIndex() == board.getChemin().size() - 1) {
			// Trigger a Level 4 question automatically when the last case is reached
			showLevel4Question(currentPion);
		}
		board.deplacerPion(currentPion, userlevel);
		transitionToNextPlayer();
	}

	// Handle the level 4 question and end the game if correct
	public void showLevel4Question(Pion pion) {
		// Create a popup for the question
		boardView.showFinalQuestionPopup(pion);

	}

	public void transitionToNextPlayer() {
		currentPlayerIndex = (currentPlayerIndex + 1) % board.getPions().size();
		boardView.setCurrentPlayerIndex(currentPlayerIndex); // Notify BoardView of the change
		boardView.updatePawnPositions();
	}

}