package model;

public abstract class QuestionBuilder {
	
	public abstract Question createQuestion(String theme,String subject,int level,String questionContent,String answer);

}
