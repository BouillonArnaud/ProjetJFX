package model;

import java.util.Objects;

public class Question {

	private String theme;
	private String subject;
	private int level;
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
		if (userAnswer == null)
			return false;
		String processedReal = this.answer.trim().toUpperCase();
		String processedUser = userAnswer.trim().toUpperCase();

		return processedReal.equals(processedUser);
	}

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
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Question question = (Question) o;
		return level == question.level && Objects.equals(questionContent, question.questionContent)
				&& Objects.equals(answer, question.answer);
	}

	@Override
	public int hashCode() {
		return Objects.hash(level, questionContent, answer);
	}

}