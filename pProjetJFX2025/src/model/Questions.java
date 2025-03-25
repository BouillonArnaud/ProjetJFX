package model;

import java.io.Serializable;
import java.util.Objects;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Questions implements Serializable{
    private String themes;
    private String card;
    private String author;
    private String subject;
    private String question;
    private String answer;

    public Questions(String themes, String card, String author, String subject, String question, String answer) {
        this.themes = themes;
        this.card = card;
        this.author = author;
        this.subject = subject;
        this.question = question;
        this.answer = answer;
    }

    public String getThemes() {
        return themes;
    }

    public void setThemes(String themes) {
        this.themes = themes;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Questions [themes=" + themes + ", card=" + card + ", author=" + author + ", subject=" + subject
                + ", question=" + question + ", answer=" + answer + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(answer, author, card, question, subject, themes);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Questions other = (Questions) obj;
        return Objects.equals(answer, other.answer) && Objects.equals(author, other.author)
                && Objects.equals(card, other.card) && Objects.equals(question, other.question)
                && Objects.equals(subject, other.subject) && Objects.equals(themes, other.themes);
    }

    @Override
    public Questions clone() {
        return new Questions(themes, card, author, subject, question, answer);
    }

    public String toJson() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(this);
    }

    public static Questions fromJson(String str) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        JsonElement parser = JsonParser.parseString(str);
        return gson.fromJson(parser, Questions.class);
    }
}
