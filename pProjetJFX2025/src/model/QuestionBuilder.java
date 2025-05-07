package model;

public abstract class QuestionBuilder {
//	Abstract method use to create question
	public abstract Question createQuestion(String theme,String subject,int level,String questionContent,String answer);

}
