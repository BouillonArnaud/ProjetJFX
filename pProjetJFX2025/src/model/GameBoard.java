package model;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private final List<Case> chemin; // Liste des cases en suivant le parcours du jeu de l'oie
    private Pion pion;

    public GameBoard(int taille) {
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
                {50, 50}, {100, 50}, {150, 50}, {200, 50}, {250, 50}, // Ligne 1
                {250, 100}, {250, 150}, {250, 200}, // Descente
                {200, 200}, {150, 200}, {100, 200}, {50, 200}, // Retour ligne 2
                {50, 250}, {50, 300}, {100, 300}, {150, 300}, // Nouvelle descente et ligne 3
                {200, 300}, {250, 300}, {250, 350}, {250, 400},
                {250,450}// Dernières cases
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
