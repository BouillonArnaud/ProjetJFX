package model;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private final List<Case> chemin; // The path on the board
    private final List<Pion> pions; // List of pawns for multiple players
    public GameBoard() {
        this.chemin = genererChemin();
        this.pions = new ArrayList<>();
        
        
    }

    public void ajouterPion(Pion pion) {
        this.pions.add(pion);
    }

    public List<Pion> getPions() {
        return pions;
    }

    public List<Case> getChemin() {
        return chemin;
    }

    private List<Case> genererChemin() {
        List<Case> chemin = new ArrayList<>();
        int[][] cheminPositions = {
                {50, 50}, {90, 50}, {130, 50}, {170, 50}, {210, 50}, {250, 50}, {290, 50}, {330, 50},
                {330, 90}, {330, 130}, {330, 170}, {330, 210}, {330, 250}, {330, 290},
                {290, 290}, {250, 290}, {210, 290}, {170, 290}, {130, 290}, {90, 290},
                {90, 250}, {90, 210}, {90, 170}, {90, 130},
                {130, 130}, {170, 130}, {210, 130}, {250, 130},
                {250, 170}, {250, 210},
                {210, 210}, {170, 210}
        };

        for (int i = 0; i < cheminPositions.length; i++) {
            chemin.add(new Case(i, cheminPositions[i][0], cheminPositions[i][1]));
        }
        return chemin;
    }

    public boolean deplacerPion(Pion pion, int steps) {
        int newIndex = pion.getIndex() + steps;
        if (newIndex >= 0 && newIndex < chemin.size()) {
            pion.setIndex(newIndex);
            return true;
        }
        return false;
    }
}
