package utils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import model.GameBoard;
import model.Pawn;
import model.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import controllers.BoardController;
import views.BoardView;

class BoardControllerTest {

    @Mock
    private GameBoard mockBoard;
    
    @Mock
    private BoardView mockBoardView;
    
    @Mock
    private Question mockQuestion;
    
    private BoardController boardController;
    private Pawn testPawn;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testPawn = new Pawn(0);
        testPawn.setName("Test Player");
        
        when(mockBoard.getPawns()).thenReturn(List.of(testPawn));
        when(mockBoard.getChemin()).thenReturn(List.of(new model.Case(0, 0, 0), new model.Case(1, 1, 1)));
        
        boardController = new BoardController(mockBoard, mockBoardView, List.of("Test Player"));
        boardController.setCurrentQuestion(mockQuestion);
    }

    @Test
    void handleAnswer_CorrectAnswer_ShouldMovePawnAndAddScore() {
        // Arrange
        when(mockQuestion.checkAnswer("correct answer")).thenReturn(true);
        when(mockQuestion.getLevel()).thenReturn(2);
        when(mockQuestion.getAnswer()).thenReturn("correct answer");

        // Act
        boardController.handleAnswer("correct answer");

        // Assert
        assertEquals(20, testPawn.getScore()); // 2 * 10 points
        verify(mockBoard).deplacerPawn(testPawn, 2);
        verify(mockBoardView).setCurrentPlayerIndex(0);
        verify(mockBoardView).updatePawnPositions();
    }

    @Test
    void handleAnswer_IncorrectAnswer_ShouldNotMovePawn() {
        // Arrange
        when(mockQuestion.checkAnswer("wrong answer")).thenReturn(false);
        when(mockQuestion.getLevel()).thenReturn(1);

        // Act
        boardController.handleAnswer("wrong answer");

        // Assert
        assertEquals(0, testPawn.getScore());
        verify(mockBoard, never()).deplacerPawn(any(), anyInt());
        verify(mockBoardView).setCurrentPlayerIndex(0);
        verify(mockBoardView).updatePawnPositions();
    }

    @Test
    void handleAnswer_WhenOnLastCase_ShouldTriggerFinalQuestion() {
        // Arrange
        testPawn.setIndex(1); // Last case index
        when(mockQuestion.checkAnswer("correct answer")).thenReturn(true);
        when(mockQuestion.getLevel()).thenReturn(3);
        when(mockQuestion.getAnswer()).thenReturn("correct answer");

        // Act
        boardController.handleAnswer("correct answer");

        // Assert
        verify(mockBoardView).showFinalQuestionPopup(testPawn);
    }

    @Test
    void handleAnswer_WithNullQuestion_ShouldNotThrowException() {
        // Arrange
        boardController.setCurrentQuestion(null);

        // Act & Assert
        assertDoesNotThrow(() -> boardController.handleAnswer("any answer"));
    }

    @Test
    void handleAnswer_ShouldTransitionToNextPlayer() {
        // Arrange - Add second player
        Pawn secondPawn = new Pawn(0);
        secondPawn.setName("Player 2");
        when(mockBoard.getPawns()).thenReturn(List.of(testPawn, secondPawn));
        
        when(mockQuestion.checkAnswer("correct answer")).thenReturn(true);
        when(mockQuestion.getLevel()).thenReturn(1);

        // Act
        boardController.handleAnswer("correct answer");

        // Assert
        verify(mockBoardView).setCurrentPlayerIndex(1); // Should move to next player
    }
}