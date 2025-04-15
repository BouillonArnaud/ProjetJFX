package utils;

import model.GameBoard;
import model.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import controllers.BoardController;
import views.BoardView;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class BoardControllerTest {
	
	 @Mock
	    private GameBoard mockGameBoard;
	    
	    @Mock
	    private BoardView mockBoardView;
	    
	    private BoardController boardController;
	    
	    @BeforeEach
	    void setUp() {
	        boardController = new BoardController(mockGameBoard, mockBoardView);
	    }

	    @Test
	    void handleAnswer_ShouldMovePawn_WhenAnswerIsCorrect() {
	        // Arrange
	        Question question = new Question("Math", "Addition", 3, "1+1?", "2");
	        boardController.setCurrentQuestion(question);
	        
	        // Act
	        boardController.handleAnswer("2"); // Réponse correcte
	        
	        // Assert
	        verify(mockGameBoard).deplacerPion(3); // Doit se déplacer de 3 (niveau de la question)
	        verify(mockBoardView).updatePawnPosition();
	    }

	    @Test
	    void handleAnswer_ShouldNotMovePawn_WhenAnswerIsIncorrect() {
	        // Arrange
	        Question question = new Question("Math", "Addition", 2, "1+1?", "2");
	        boardController.setCurrentQuestion(question);
	        
	        // Act
	        boardController.handleAnswer("3"); // Réponse incorrecte
	        
	        // Assert
	        verify(mockGameBoard, never()).deplacerPion(anyInt());
	        verify(mockBoardView, never()).updatePawnPosition();
	    }

	    @Test
	    void handleAnswer_ShouldNotMovePawn_WhenNoCurrentQuestion() {
	        // Act (pas de question définie)
	        boardController.handleAnswer("2");
	        
	        // Assert
	        verify(mockGameBoard, never()).deplacerPion(anyInt());
	        verify(mockBoardView, never()).updatePawnPosition();
	    }

	    @Test
	    void handleAnswer_ShouldHandleCaseInsensitiveAnswers() {
	        // Arrange
	        Question question = new Question("Géographie", "Capitales", 1, "France?", "Paris");
	        boardController.setCurrentQuestion(question);
	        
	        // Act
	        boardController.handleAnswer("pArIs"); // Réponse correcte mais casse différente
	        
	        // Assert
	        verify(mockGameBoard).deplacerPion(1);
	        verify(mockBoardView).updatePawnPosition();
	    }

}