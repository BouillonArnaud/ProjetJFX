package model;

public class HelpPageModel {
    private final String rulesText;
    private final String backgroundImagePath;
    private final String logoImagePath;
    private final String decorImagePath;
    private final String sidebarImagePath;

    public HelpPageModel() {
        this.rulesText = """
             Game Objective

Be the first player to reach the final square on the board and correctly answer a final question.

Gameplay

The game features 4 themes, each associated with a color:

Purple: Improbable, covering unique topics (yawning, Breton pancakes, etc.).
Orange: Entertainment, focused on leisure-related topics (movies, music, etc.).
Blue: IT, related to object-oriented programming and analysis.
Green: Education.
Themes are determined by your pawn’s position on the board.

At the start, all players begin with the "Improbable" theme. The opposing team selects a card matching the theme of your pawn’s position. You assess your knowledge of the given theme, on a difficulty scale from 1 (easy question) to 4 (expert question).

You generally have 30 seconds to answer.

If you answer correctly, you advance the number of squares equal to your score. If not, you stay in place. Roles rotate clockwise.

Once you reach the final square, the opposing player selects a “Don’t Hesitate to Win” card. If you answer correctly, you win the game; otherwise, you must try again on the next turn with a new question.""";
        this.backgroundImagePath = "/resources/Page2.jpg";
        this.logoImagePath = "/resources/Role_Game_Title.png";
        this.decorImagePath = "/resources/acceuil6.png";
        this.sidebarImagePath = "/resources/Image7.png";
    }

    public String getRulesText() {
        return rulesText;
    }

    public String getBackgroundImagePath() {
        return backgroundImagePath;
    }

    public String getLogoImagePath() {
        return logoImagePath;
    }

    public String getDecorImagePath() {
        return decorImagePath;
    }

    public String getSidebarImagePath() {
        return sidebarImagePath;
    }
}