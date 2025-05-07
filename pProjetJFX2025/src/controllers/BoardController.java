package controllers;

import model.GameBoard;
import model.Pawn;
import model.Question;
import views.BoardView;

import java.util.List;

public class BoardController {
    private final GameBoard board;
    private final BoardView boardView;
    private int currentPlayerIndex;
    private Question currentQuestion;

    public BoardController(GameBoard board, BoardView boardView, List<String> playerNames) {
        this.board = board;
        this.boardView = boardView;
        this.currentPlayerIndex = 0;

        // Add Pawns with player names
        for (String name : playerNames) {
            Pawn Pawn = new Pawn(0);
            Pawn.setName(name);
            board.addPawn(Pawn);
        }

        boardView.setCurrentPlayerIndex(currentPlayerIndex);
    }

    public void setCurrentQuestion(Question question) {
        this.currentQuestion = question;
    }

    public void handleAnswer(String userAnswer) {
        Pawn currentPawn = board.getPawns().get(currentPlayerIndex);
        if (currentQuestion != null) {
            int moveBy = currentQuestion.getLevel();
            if (currentQuestion.checkAnswer(userAnswer)) {
                System.out.println("Correct! The answer is: " + currentQuestion.getAnswer());
                currentPawn.addScore(moveBy * 10); // 10 points per level

                if (currentPawn.getIndex() == board.getPath().size() - 1) {
                    showLevel4Question(currentPawn);
                }

                board.movePawn(currentPawn, moveBy);
            }
            transitionToNextPlayer();
        }
    }

    public void handleAnswerTest(int userlevel) {
        Pawn currentPawn = board.getPawns().get(currentPlayerIndex);
        if (currentPawn.getIndex() == board.getPath().size() - 1) {
            showLevel4Question(currentPawn);
        }
        board.movePawn(currentPawn, userlevel);
        transitionToNextPlayer();
    }

    public void showLevel4Question(Pawn Pawn) {
        boardView.showFinalQuestionPopup(Pawn);
    }

    public void transitionToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % board.getPawns().size();
        boardView.setCurrentPlayerIndex(currentPlayerIndex);
        boardView.updatePawnPositions();
    }
}