package controllers;

import model.GameBoard;
import model.Pion;
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

        // Ajouter les pions avec les noms des joueurs
        for (String name : playerNames) {
            Pion pion = new Pion(0);
            pion.setName(name);
            board.ajouterPion(pion);
        }

        boardView.setCurrentPlayerIndex(currentPlayerIndex);
    }

    public void setCurrentQuestion(Question question) {
        this.currentQuestion = question;
    }

    public void handleAnswer(String userAnswer) {
        Pion currentPion = board.getPions().get(currentPlayerIndex);
        if (currentQuestion != null) {
            int moveBy = currentQuestion.getLevel();
            if (currentQuestion.checkAnswer(userAnswer)) {
                System.out.println("Correct! The answer is: " + currentQuestion.getAnswer());
                currentPion.addScore(moveBy * 10); // 10 points par niveau

                if (currentPion.getIndex() == board.getChemin().size() - 1) {
                    showLevel4Question(currentPion);
                }

                board.deplacerPion(currentPion, moveBy);
            }
            transitionToNextPlayer();
        }
    }

    public void handleAnswerTest(int userlevel) {
        Pion currentPion = board.getPions().get(currentPlayerIndex);
        if (currentPion.getIndex() == board.getChemin().size() - 1) {
            showLevel4Question(currentPion);
        }
        board.deplacerPion(currentPion, userlevel);
        transitionToNextPlayer();
    }

    public void showLevel4Question(Pion pion) {
        boardView.showFinalQuestionPopup(pion);
    }

    public void transitionToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % board.getPions().size();
        boardView.setCurrentPlayerIndex(currentPlayerIndex);
        boardView.updatePawnPositions();
    }
}