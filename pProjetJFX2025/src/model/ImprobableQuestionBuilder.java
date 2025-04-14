package model;

public class ImprobableQuestionBuilder extends QuestionBuilder {

	@Override
	public Question createQuestion(String theme,String subject, int level, String questionContent, String answer) {
		return new QuestionImprobable(theme,subject,level,questionContent,answer);
	}

}
