package utils;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import model.Question;

class TestQuestion {
	
//	private final Question question1 = new Question(1, "Qu'est-ce que Java?", "Langage");
//    private final Question question1Copy = new Question(1, "Qu'est-ce que Java?", "Langage");
//    private final Question question2 = new Question(2, "Qu'est-ce que JUnit?", "Framework");
//    private final Question questionDiffLevel = new Question(2, "Qu'est-ce que Java?", "Langage");
//    private final Question questionDiffContent = new Question(1, "Différent", "Langage");
//    private final Question questionDiffAnswer = new Question(1, "Qu'est-ce que Java?", "Différent");
//
//    @Test
//    void checkAnswer_ShouldReturnTrueForCorrectAnswer() {
//        // Arrange
//        Question question = new Question(1, "What is this?", "Thisistherealanswer");
//        
//        // Act
//        boolean result = question.checkAnswer("Thisistherealanswer");
//        
//        // Assert
//        assertTrue(result, "Devrait retourner true pour la bonne réponse");
//        System.out.println(result);
//    }
//
//    @Test
//    void checkAnswer_ShouldBeCaseInsensitive() {
//        // Arrange
//        Question question = new Question(1, "Question", "Thisistherealanswer");
//        
//        // Act
//        boolean resultUpper = question.checkAnswer("THISISTHEREALANSWER");
//        boolean resultLower = question.checkAnswer("thisistherealanswer");
//        
//        // Assert
//        assertAll(
//            () -> assertTrue(resultUpper, "Devrait ignorer la casse (majuscules)"),
//            () -> assertTrue(resultLower, "Devrait ignorer la casse (minuscules)")
//        );
//        System.out.println(resultUpper);
//        System.out.println(resultLower);
//    }
//
//    @Test
//    void checkAnswer_ShouldReturnFalseForWrongAnswer() {
//        // Arrange
//        Question question = new Question(1, "Question", "Thisistherealanswer");
//        
//        // Act
//        boolean result = question.checkAnswer("Mauvaise réponse");
//        
//        // Assert
//        assertFalse(result);
//        System.out.println(result);
//    }
//    
//    @Test
//    void equals_ShouldBeReflexive() {
//        assertEquals(question1, question1);
//    }
//
//    @Test
//    void equals_ShouldBeSymmetric() {
//        assertEquals(question1.equals(question1Copy), question1Copy.equals(question1));
//    }
//
//    @Test
//    void equals_ShouldBeTransitive() {
//        Question question3 = new Question(1, "Qu'est-ce que Java?", "Langage");
//        assertAll(
//            () -> assertEquals(question1, question1Copy),
//            () -> assertEquals(question1Copy, question3),
//            () -> assertEquals(question1, question3)
//        );
//    }
//    
//    @Test
//    void equals_ShouldReturnTrueForSameValues() {
//        assertTrue(question1.equals(question1Copy));
//    }
//
//    @Test
//    void equals_ShouldReturnFalseForDifferentLevel() {
//        assertFalse(question1.equals(questionDiffLevel));
//    }
//
//    @Test
//    void equals_ShouldReturnFalseForDifferentContent() {
//        assertFalse(question1.equals(questionDiffContent));
//    }
//
//    @Test
//    void equals_ShouldReturnFalseForDifferentAnswer() {
//        assertFalse(question1.equals(questionDiffAnswer));
//    }
//    
//    @Test
//    void equals_ShouldReturnFalseForNull() {
//        assertFalse(question1.equals(null));
//    }
//
//    @Test
//    void equals_ShouldReturnFalseForDifferentClass() {
//        assertFalse(question1.equals("Ceci est une String"));
//    }
//
//    @Test
//    void hashCode_ShouldBeConsistent() {
//        int initialHashCode = question1.hashCode();
//        assertEquals(initialHashCode, question1.hashCode());
//    }
//
//    @Test
//    void hashCode_ShouldBeEqualForSameValues() {
//        assertEquals(question1.hashCode(), question1Copy.hashCode());
//    }
}