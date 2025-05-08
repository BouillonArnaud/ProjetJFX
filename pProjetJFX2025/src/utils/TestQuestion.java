package utils;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Question;

class TestQuestion {

    private Question question;

    @BeforeEach
    void setUp() {
        question = new Question("Informatic", "OOP", 2, 
                              "What is a class in Java?", 
                              "A blueprint for creating objects");
    }

    // Tests for checkAnswer()
    @Test
    void checkAnswer_CorrectAnswer_ReturnsTrue() {
        assertTrue(question.checkAnswer("A blueprint for creating objects"));
    }

    @Test
    void checkAnswer_CorrectAnswerWithWhitespace_ReturnsTrue() {
        assertTrue(question.checkAnswer("  A blueprint for creating objects  "));
    }

    @Test
    void checkAnswer_CorrectAnswerDifferentCase_ReturnsTrue() {
        assertTrue(question.checkAnswer("A BLUEPRINT FOR CREATING OBJECTS"));
    }

    @Test
    void checkAnswer_IncorrectAnswer_ReturnsFalse() {
        assertFalse(question.checkAnswer("A function"));
    }

    @Test
    void checkAnswer_NullAnswer_ReturnsFalse() {
        assertFalse(question.checkAnswer(null));
    }

    @Test
    void checkAnswer_EmptyAnswer_ReturnsFalse() {
        assertFalse(question.checkAnswer(""));
    }

    // Tests for getters
    @Test
    void getTheme_ReturnsCorrectTheme() {
        assertEquals("Informatic", question.getTheme());
    }

    @Test
    void getSubject_ReturnsCorrectSubject() {
        assertEquals("OOP", question.getSubject());
    }

    @Test
    void getLevel_ReturnsCorrectLevel() {
        assertEquals(2, question.getLevel());
    }

    @Test
    void getQuestionContent_ReturnsCorrectContent() {
        assertEquals("What is a class in Java?", question.getQuestionContent());
    }

    @Test
    void getAnswer_ReturnsCorrectAnswer() {
        assertEquals("A blueprint for creating objects", question.getAnswer());
    }

    // Tests for setters
    @Test
    void setTheme_UpdatesThemeCorrectly() {
        question.setTheme("Education");
        assertEquals("Education", question.getTheme());
    }

    @Test
    void setSubject_UpdatesSubjectCorrectly() {
        question.setSubject("Design Patterns");
        assertEquals("Design Patterns", question.getSubject());
    }

    @Test
    void setLevel_UpdatesLevelCorrectly() {
        question.setLevel(3);
        assertEquals(3, question.getLevel());
    }

    @Test
    void setQuestionContent_UpdatesContentCorrectly() {
        question.setQuestionContent("What is an interface?");
        assertEquals("What is an interface?", question.getQuestionContent());
    }

    @Test
    void setAnswer_UpdatesAnswerCorrectly() {
        question.setAnswer("A contract that classes must implement");
        assertEquals("A contract that classes must implement", question.getAnswer());
    }

    // Tests for equals()
    @Test
    void equals_SameObject_ReturnsTrue() {
        assertTrue(question.equals(question));
    }

    @Test
    void equals_NullObject_ReturnsFalse() {
        assertFalse(question.equals(null));
    }

    @Test
    void equals_DifferentClass_ReturnsFalse() {
        assertFalse(question.equals("Not a Question object"));
    }

    @Test
    void equals_EqualQuestions_ReturnsTrue() {
        Question sameQuestion = new Question("Informatic", "OOP", 2, 
                                          "What is a class in Java?", 
                                          "A blueprint for creating objects");
        assertTrue(question.equals(sameQuestion));
    }

    @Test
    void equals_DifferentLevel_ReturnsFalse() {
        Question differentLevel = new Question("Informatic", "OOP", 3, 
                                             "What is a class in Java?", 
                                             "A blueprint for creating objects");
        assertFalse(question.equals(differentLevel));
    }

    @Test
    void equals_DifferentContent_ReturnsFalse() {
        Question differentContent = new Question("Informatic", "OOP", 2, 
                                               "What is an interface?", 
                                               "A blueprint for creating objects");
        assertFalse(question.equals(differentContent));
    }

    @Test
    void equals_DifferentAnswer_ReturnsFalse() {
        Question differentAnswer = new Question("Informatic", "OOP", 2, 
                                              "What is a class in Java?", 
                                              "A collection of methods");
        assertFalse(question.equals(differentAnswer));
    }

    // Tests for hashCode()
    @Test
    void hashCode_EqualObjects_SameHashCode() {
        Question sameQuestion = new Question("Informatic", "OOP", 2, 
                                           "What is a class in Java?", 
                                           "A blueprint for creating objects");
        assertEquals(question.hashCode(), sameQuestion.hashCode());
    }

    @Test
    void hashCode_DifferentObjects_DifferentHashCode() {
        Question differentQuestion = new Question("Informatic", "OOP", 3, 
                                                "What is a class in Java?", 
                                                "A blueprint for creating objects");
        assertNotEquals(question.hashCode(), differentQuestion.hashCode());
    }
}