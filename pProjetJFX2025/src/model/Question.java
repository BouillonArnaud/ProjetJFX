package model;

import java.util.Objects;

public class Question {
	
	private int level;
	private String questionContent;
	private String answer;
	
	public Question(int level, String questionContent, String answer) {
		super();
		this.level = level;
		this.questionContent = questionContent;
		this.answer = answer;
	}
	
//	Compare user input with the answer
	public boolean checkAnswer(String userAnswer) {
	    String processedReal = this.answer.toUpperCase();
	    String processedUser = userAnswer.toUpperCase();
	    return processedReal.equals(processedUser);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Question) {
			Question q = (Question)o;
			return this.level == q.level && this.questionContent.equalsIgnoreCase(q.questionContent) && 
					this.answer.equalsIgnoreCase(q.answer);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(level, questionContent, answer);
	}
	

}
