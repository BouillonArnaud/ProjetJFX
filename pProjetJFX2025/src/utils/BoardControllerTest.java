package utils;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import controllers.BoardController;
import model.Case;
import model.GameBoard;
import model.Pawn;
import model.Question;
import views.BoardView;

class BoardControllerTest {

    private BoardController boardController;
    private GameBoard mockBoard;
    private BoardView mockBoardView;
    private Question mockQuestion;
    private List<String> playerNames;
    private List<Case> mockPath;
    
    @BeforeEach
    void setUp() {
        // Initialize mock objects
        mockBoard = mock(GameBoard.class);
        mockBoardView = mock(BoardView.class);
        mockQuestion = mock(Question.class);
        
        // Sample player names for testing
        playerNames = Arrays.asList("Player1", "Player2");
        
        // Create Case objects with correct constructor (index, x, y)
        Case startCase = new Case(0, 100, 100);    // First case at position (100,100)
        Case middleCase = new Case(1, 200, 200);   // Second case at position (200,200)
        Case endCase = new Case(2, 300, 300);      // Final case at position (300,300)
        mockPath = Arrays.asList(startCase, middleCase, endCase);
        
        // Create controller with mocked dependencies
        boardController = new BoardController(mockBoard, mockBoardView, playerNames);
        
        // Set up mock behavior
        when(mockBoard.getPawns()).thenReturn(Arrays.asList(new Pawn(0), new Pawn(0)));
        when(mockBoard.getPath()).thenReturn(mockPath);
    }

    @Test
    void handleAnswer_whenAtLastPosition_shouldShowFinalQuestion() {
        // Set up
        boardController.setCurrentQuestion(mockQuestion);
        when(mockQuestion.checkAnswer("correct")).thenReturn(true);
        when(mockQuestion.getLevel()).thenReturn(1);
        
        // Set current pawn to last position (index = path size - 1)
        Pawn testPawn = mockBoard.getPawns().get(0);
        testPawn.setIndex(mockPath.size() - 1);  // index 2 in our 3-case path
        
        // Act
        boardController.handleAnswer("correct");
        
        // Assert
        // Verify final question popup was shown
        verify(mockBoardView).showFinalQuestionPopup(testPawn);
        
        // Verify the pawn was at the correct last position
        assertEquals(2, testPawn.getIndex());
        assertEquals(mockPath.get(2), mockBoard.getPath().get(testPawn.getIndex()));
    }

    @Test
    void handleAnswer_movingBeyondPath_shouldHandleGracefully() {
        // Arrange
        boardController.setCurrentQuestion(mockQuestion);
        when(mockQuestion.checkAnswer("correct")).thenReturn(true);
        when(mockQuestion.getLevel()).thenReturn(5);  // Large move that would go beyond path
        
        // Act
        boardController.handleAnswer("correct");
        
        // Assert
        // Verify the board handles the movement
        verify(mockBoard).movePawn(any(Pawn.class), eq(5));
    }

    // Helper method to access pawns for verification
    private List<Pawn> getPawns() {
        return mockBoard.getPawns();
    }
}