package model;

public class Pion {
    private int index;
    private String name;
    private int score;

    public Pion(int index) {
        this.index = index;
        this.name = "Player";
        this.score = 0;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        this.score += points;
    }
}