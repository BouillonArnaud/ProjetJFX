package utils;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Case;
import model.GameBoard;
import model.Pion;

class GameBoardTest {

    private GameBoard board1;
    private GameBoard board2;
    private GameBoard boardDifferentPath;
    private GameBoard boardDifferentPawn;
    private Pion pion1;
    private Pion pion2;

    @BeforeEach
    void setUp() {
        // Initialize boards and pawns for testing
        board1 = new GameBoard();
        board2 = new GameBoard();
        boardDifferentPath = new GameBoard();
        
        pion1 = new Pion(0); // Pawn at position 0
        pion2 = new Pion(1); // Pawn at position 1
        
        board1.ajouterPion(pion1);
        board2.ajouterPion(pion1); // Same pawn as board1
        boardDifferentPawn = new GameBoard();
        boardDifferentPawn.ajouterPion(pion2);
        
        // Artificially modify one path for testing
        if (boardDifferentPath.getChemin().size() > 0) {
            boardDifferentPath.getChemin().set(0, new Case(0, 999, 999));
        }
    }

    // equals() tests
    @Test
    void equals_ShouldReturnTrueForSameInstance() {
        assertTrue(board1.equals(board1));
    }

    @Test
    void equals_ShouldReturnTrueForEqualBoards() {
        assertTrue(board1.equals(board2));
    }

    @Test
    void equals_ShouldReturnFalseForDifferentPath() {
        assertFalse(board1.equals(boardDifferentPath));
    }

    @Test
    void equals_ShouldReturnFalseForDifferentPawn() {
        assertFalse(board1.equals(boardDifferentPawn));
    }

    @Test
    void equals_ShouldReturnFalseForNull() {
        assertFalse(board1.equals(null));
    }

    @Test
    void equals_ShouldReturnFalseForDifferentClass() {
        assertFalse(board1.equals("Not a GameBoard"));
    }

    // deplacerPion() tests
    @Test
    void deplacerPion_ShouldMoveForwardWhenValid() {
        // Arrange
        Pion pion = new Pion(0);
        GameBoard board = new GameBoard();
        board.ajouterPion(pion);
        
        // Act
        boolean result = board.deplacerPion(3);
        
        // Assert
        assertAll(
            () -> assertTrue(result),
            () -> assertEquals(3, pion.getIndex())
        );
    }

    @Test
    void deplacerPion_ShouldNotMoveWhenNegative() {
        // Arrange
        Pion pion = new Pion(5);
        GameBoard board = new GameBoard();
        board.ajouterPion(pion);
        
        // Act
        boolean result = board.deplacerPion(-6); // Try to move before position 0
        
        // Assert
        assertAll(
            () -> assertFalse(result),
            () -> assertEquals(5, pion.getIndex()) // Position unchanged
        );
    }

    @Test
    void deplacerPion_ShouldNotMoveBeyondLastCase() {
        // Arrange
        Pion pion = new Pion(board1.getChemin().size() - 2); // Second-to-last case
        GameBoard board = new GameBoard();
        board.ajouterPion(pion);
        
        // Act
        boolean result = board.deplacerPion(2); // Try to go beyond board
        
        // Assert
        assertAll(
            () -> assertFalse(result),
            () -> assertEquals(board.getChemin().size() - 2, pion.getIndex())
        );
    }

    @Test
    void deplacerPion_ShouldHandleZeroMovement() {
        // Arrange
        Pion pion = new Pion(3);
        GameBoard board = new GameBoard();
        board.ajouterPion(pion);
        
        // Act
        boolean result = board.deplacerPion(0);
        
        // Assert
        assertAll(
            () -> assertTrue(result), // 0 is a valid movement
            () -> assertEquals(3, pion.getIndex()) // Position unchanged
        );
    }
}