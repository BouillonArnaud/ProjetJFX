package utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Case;
import model.GameBoard;
import model.Pawn;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class GameBoardTest {
    private GameBoard gameBoard;
    private Pawn testPawn;

    @BeforeEach
    void setUp() {
        gameBoard = new GameBoard();
        testPawn = new Pawn(0);
        testPawn.setName("Test Player");
    }

    @Test
    void testGenererChemin_ShouldGenerateCorrectPath() {
        // Test that the path is generated with correct coordinates
        List<Case> chemin = gameBoard.genererChemin();
        
        assertNotNull(chemin);
        assertFalse(chemin.isEmpty());
        
        // Check first position
        assertEquals(50, chemin.get(0).getX());
        assertEquals(50, chemin.get(0).getY());
        
        // Check last position
        int lastIndex = chemin.size() - 1;
        assertEquals(170, chemin.get(lastIndex).getX());
        assertEquals(210, chemin.get(lastIndex).getY());
    }

    @Test
    void testAjouterPawn_ShouldAddPawnToList() {
        // Test adding a pawn to the game board
        assertEquals(0, gameBoard.getPawns().size());
        
        gameBoard.ajouterPawn(testPawn);
        
        assertEquals(1, gameBoard.getPawns().size());
        assertEquals(testPawn, gameBoard.getPawns().get(0));
    }

    @Test
    void testDeplacerPawn_ValidMovement_ShouldUpdatePosition() {
        // Test valid pawn movement within bounds
        gameBoard.ajouterPawn(testPawn);
        int initialPosition = testPawn.getIndex();
        
        boolean result = gameBoard.deplacerPawn(testPawn, 3);
        
        assertTrue(result);
        assertEquals(initialPosition + 3, testPawn.getIndex());
    }

    @Test
    void testDeplacerPawn_MovementBeyondBoard_ShouldSetToLastPosition() {
        // Test movement that would go beyond the board limits
        gameBoard.ajouterPawn(testPawn);
        int lastPosition = gameBoard.getChemin().size() - 1;
        
        boolean result = gameBoard.deplacerPawn(testPawn, 100);
        
        assertTrue(result);
        assertEquals(lastPosition, testPawn.getIndex());
    }

    @Test
    void testDeplacerPawn_NegativeMovement_ShouldReturnFalse() {
        // Test invalid negative movement
        gameBoard.ajouterPawn(testPawn);
        
        boolean result = gameBoard.deplacerPawn(testPawn, -1);
        
        assertFalse(result);
        assertEquals(0, testPawn.getIndex()); // Position shouldn't change
    }

    @Test
    void testInitializeQuestionLists_ShouldPopulateQuestionCategories() {
        // Test that question lists are initialized
        // This is a basic test - in a real scenario you'd mock the JsonUtils
        
        assertNotNull(gameBoard.getEducationQuestions());
        assertNotNull(gameBoard.getEntertainmentQuestions());
        assertNotNull(gameBoard.getImprobableQuestions());
        assertNotNull(gameBoard.getInformaticQuestions());
    }

    @Test
    void testEquals_DifferentGameBoard_ShouldReturnFalse() {
        // Test inequality of different game boards
        GameBoard otherBoard = new GameBoard();
        otherBoard.ajouterPawn(new Pawn(0));
        
        assertFalse(gameBoard.equals(otherBoard));
    }

    @Test
    void testEquals_NonGameBoardObject_ShouldReturnFalse() {
        // Test comparison with non-GameBoard object
        String notAGameBoard = "Not a game board";
        
        assertFalse(gameBoard.equals(notAGameBoard));
    }
}