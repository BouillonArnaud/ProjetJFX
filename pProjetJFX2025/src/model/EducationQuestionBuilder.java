package model;

public class EducationQuestionBuilder extends QuestionBuilder {

	@Override
//	Define the method createQuestion to create an object of the class QuestionEducation by his constructor
	public Question createQuestion(String theme,String subject ,int level, String questionContent, String answer) {
		return new QuestionEducation(theme,subject, level, questionContent, answer);
	}
	

}
