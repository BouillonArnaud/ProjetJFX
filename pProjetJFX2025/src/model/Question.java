package model;

import java.util.Objects;

public class Question {
    private String theme;
    private String subject;
    private int level; // Niveau de difficulté (1-4)
    private String questionContent;
    private String answer;
    
    public Question(String theme, String subject, int level, String questionContent, String answer) {
        this.theme = theme;
        this.subject = subject;
        this.level = level;
        this.questionContent = questionContent;
        this.answer = answer;
    }
    
    public boolean checkAnswer(String userAnswer) {
        String processedReal = this.answer.toUpperCase();
        String processedUser = userAnswer.toUpperCase();
        return processedReal.equals(processedUser);
    }
    
    // Getters et Setters
    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
    
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof Question) {
            Question q = (Question)o;
            return this.level == q.level && 
                   this.questionContent.equalsIgnoreCase(q.questionContent) && 
                   this.answer.equalsIgnoreCase(q.answer);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(level, questionContent, answer);
    }
}