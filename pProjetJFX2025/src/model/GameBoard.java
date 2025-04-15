package model;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private final List<Case> chemin; // Liste des cases en suivant le parcours du jeu de l'oie
    private Pion pion;
    private List<QuestionEducation> educationQuestions;
    private List<QuestionEntertainment> entertainmentQuestions;
    private List<QuestionImprobable> improbableQuestions;
    private List<QuestionInformatic> informaticQuestions;

    public GameBoard() {
        this.chemin = genererChemin(); // Génère les cases selon un chemin spécifique
        this.educationQuestions = new ArrayList<QuestionEducation>();
        this.entertainmentQuestions = new ArrayList<QuestionEntertainment>();
        this.improbableQuestions = new ArrayList<QuestionImprobable>();
        this.informaticQuestions = new ArrayList<QuestionInformatic>();
        initializeQuestionLists();
    }

    public void ajouterPion(Pion pion) {
        this.pion = pion;
    }

    public Pion getPion() {
        return pion;
    }

    public List<Case> getChemin() {
        return chemin;
    }

    private List<Case> genererChemin() {
        List<Case> chemin = new ArrayList<>();

        // Ex : Création d'un chemin de 20 cases (à adapter pour un vrai plateau)
        int[][] cheminPositions = {
                {50, 50}, {90, 50}, {130, 50}, {170, 50}, {210, 50},{250, 50},{290, 50},{330, 50}, // Ligne 1
                {330, 90},{330, 130},{330, 170},{330, 210},{330, 250},{330, 290},  // Descente
                {290, 290},{250, 290},{210, 290},{170, 290},{130, 290},{90, 290},  // Retour ligne 2
                {90, 250},{90, 210},{90, 170},{90, 130},  // Nouvelle descente et ligne 3
                {130, 130},{170, 130},{210, 130},{250, 130}, //Ligne
                {250, 170},{250, 210}, //Colone
                {210, 210},{170, 210} //Ligne
            };

        for (int i = 0; i < cheminPositions.length; i++) {
            chemin.add(new Case(i, cheminPositions[i][0], cheminPositions[i][1]));
        }
        return chemin;
    }

    public boolean deplacerPion(int deplacement) {
        int newIndex = pion.getIndex() + deplacement;

        // Vérifier que le pion ne dépasse pas les limites du plateau
        if (newIndex >= 0 && newIndex < chemin.size()) {
            pion.setIndex(newIndex);
            return true;
        }
        return false;
    }
    
    public void initializeQuestionLists() {
    	List<Question> jsonContent = JsonUtils.getJsonContent();
    	
    	if (jsonContent == null) {
    	    System.err.println("Aucune question chargée depuis le JSON");
    	    return;
    	}
    	
    	EntertainmentQuestionBuilder enB = new EntertainmentQuestionBuilder();
    	EducationQuestionBuilder eduB = new EducationQuestionBuilder();
    	ImprobableQuestionBuilder impB = new ImprobableQuestionBuilder();
    	InformaticQuestionBuilder infB = new InformaticQuestionBuilder();
    	
    	for (Question q : jsonContent) {
    		switch (q.getTheme()) {
			case "Entertainment": {
				Question questionEn = enB.createQuestion(q.getTheme(), q.getSubject(), q.getLevel(), 
						q.getQuestionContent(),q.getAnswer());
				entertainmentQuestions.add((QuestionEntertainment) questionEn);
				break;
			}
			case "Informatic": {
				Question questionInf = infB.createQuestion(q.getTheme(), q.getSubject(), q.getLevel(), 
						q.getQuestionContent(), q.getAnswer());
				informaticQuestions.add((QuestionInformatic) questionInf);
				break;
			}
			case "Education": {
				Question questionEdu = eduB.createQuestion(q.getTheme(), q.getSubject(), q.getLevel(), 
						q.getQuestionContent(), q.getAnswer());
				educationQuestions.add((QuestionEducation) questionEdu);
				break;
			}
			case "Improbable": {
				Question questionImp = impB.createQuestion(q.getTheme(), q.getSubject(), q.getLevel(), 
						q.getQuestionContent(), q.getAnswer());
				improbableQuestions.add((QuestionImprobable) questionImp);
				break;
			}
			default:
				System.out.println("Unknown theme: " + q.getTheme());
			}
    	}
    	
//    	To debug lists content
    	for (QuestionEducation q : educationQuestions) {
    		System.out.println("Question : " + q.getQuestionContent() + " Answer : " +  q.getAnswer());
    	}
    }
    
    public List<QuestionEducation> getEducationQuestions() {
		return educationQuestions;
	}

	public List<QuestionEntertainment> getEntertainmentQuestions() {
		return entertainmentQuestions;
	}

	public List<QuestionImprobable> getImprobableQuestions() {
		return improbableQuestions;
	}

	public List<QuestionInformatic> getInformaticQuestions() {
		return informaticQuestions;
	}

	@Override
    public boolean equals(Object o) {
    	if (o instanceof GameBoard) {
    		GameBoard gb = (GameBoard)o;
    		return this.chemin.equals(gb.chemin) && this.pion.equals(gb.pion);
    	}
    	return false;
    }
}
