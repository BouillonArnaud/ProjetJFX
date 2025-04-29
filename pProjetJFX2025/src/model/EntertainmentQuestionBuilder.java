package model;

public class EntertainmentQuestionBuilder extends QuestionBuilder{

	@Override
	public Question createQuestion(String theme,String subject, int level, String questionContent, String answer) {
		return new QuestionEntertainment(theme,subject, level, questionContent, answer);
	}

}