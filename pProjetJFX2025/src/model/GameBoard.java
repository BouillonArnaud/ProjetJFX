package model;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private final List<Case> chemin; // Liste des cases en suivant le parcours du jeu de l'oie
    private Pion pion;

    public GameBoard() {
        this.chemin = genererChemin(); // Génère les cases selon un chemin spécifique
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
}
