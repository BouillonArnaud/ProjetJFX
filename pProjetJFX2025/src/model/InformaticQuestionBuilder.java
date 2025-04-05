package model;

public class InformaticQuestionBuilder extends QuestionBuilder {

	@Override
	public Question createQuestion(String theme,String subject, int level, String questionContent, String answer) {
		return new QuestionInformatic(theme,subject, level, questionContent, answer);
		
	}

}
