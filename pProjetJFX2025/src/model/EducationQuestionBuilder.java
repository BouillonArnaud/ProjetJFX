package model;

public class EducationQuestionBuilder extends QuestionBuilder {

	@Override
	public Question createQuestion(String theme,String subject ,int level, String questionContent, String answer) {
		return new QuestionEducation(theme,subject, level, questionContent, answer);
	}
	

}
