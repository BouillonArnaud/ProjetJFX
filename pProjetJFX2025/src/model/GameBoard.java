package model;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private final List<Case> chemin;
    private final List<Pawn> Pawns;
    private List<QuestionEducation> educationQuestions;
    private List<QuestionEntertainment> entertainmentQuestions;
    private List<QuestionImprobable> improbableQuestions;
    private List<QuestionInformatic> informaticQuestions;

    public GameBoard() {
        this.chemin = genererChemin();
        this.Pawns = new ArrayList<>();
        this.educationQuestions = new ArrayList<QuestionEducation>();
        this.entertainmentQuestions = new ArrayList<QuestionEntertainment>();
        this.improbableQuestions = new ArrayList<QuestionImprobable>();
        this.informaticQuestions = new ArrayList<QuestionInformatic>();
        initializeQuestionLists();
    }

    public void ajouterPawn(Pawn Pawn) {
        this.Pawns.add(Pawn);
    }

    public List<Pawn> getPawns() {
        return Pawns;
    }

    public List<Case> getChemin() {
        return chemin;
    }

    public List<Case> genererChemin() {
        List<Case> chemin = new ArrayList<>();

        int[][] cheminPositions = { { 50, 50 }, { 90, 50 }, { 130, 50 }, { 170, 50 }, { 210, 50 }, { 250, 50 },
                { 290, 50 }, { 330, 50 }, // Ligne 1
                { 330, 90 }, { 330, 130 }, { 330, 170 }, { 330, 210 }, { 330, 250 }, { 330, 290 }, // Descente
                { 290, 290 }, { 250, 290 }, { 210, 290 }, { 170, 290 }, { 130, 290 }, { 90, 290 }, // Retour ligne 2
                { 90, 250 }, { 90, 210 }, { 90, 170 }, { 90, 130 }, // Nouvelle descente et ligne 3
                { 130, 130 }, { 170, 130 }, { 210, 130 }, { 250, 130 }, // Ligne
                { 250, 170 }, { 250, 210 }, // Colone
                { 210, 210 }, { 170, 210 } // Ligne
        };

        for (int i = 0; i < cheminPositions.length; i++) {
            chemin.add(new Case(i, cheminPositions[i][0], cheminPositions[i][1]));
        }
        return chemin;
    }

    public boolean deplacerPawn(Pawn Pawn, int deplacement) {
        int newIndex = Pawn.getIndex() + deplacement;

        if (newIndex >= 0 && newIndex < chemin.size()) {
            Pawn.setIndex(newIndex);
            return true;
        } else if (newIndex >= chemin.size()) {
            Pawn.setIndex((chemin.size() - 1));
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
                            q.getQuestionContent(), q.getAnswer());
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

        for (QuestionEducation q : educationQuestions) {
            System.out.println("Question : " + q.getQuestionContent() + " Answer : " + q.getAnswer());
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
            GameBoard gb = (GameBoard) o;
            return this.chemin.equals(gb.chemin) && this.Pawns.equals(gb.Pawns);
        }
        return false;
    }
}