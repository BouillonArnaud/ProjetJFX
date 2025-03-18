package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GameBoard extends Pane {
	public GameBoard() {
		// Set the preferred size of the game board
		setPrefSize(1295, 727);

		// Add the background image
		ImageView background = new ImageView(
				new Image(getClass().getResource("/resources/background_cyberpunk.jpg").toExternalForm()));
		background.setFitWidth(1295);
		background.setFitHeight(727);
		background.setPreserveRatio(true);
		getChildren().add(background);

		// Add rectangles (cases) to the game board
		getCaseAtPosition();
	}

	private void getCaseAtPosition() {
		// Define an array of colors for cases
		Color[] colors = { Color.web("#4000ff66"), Color.web("#0088ff66"), Color.ORANGE,
				Color.web("#00ff0466") };
		Color[] reversecolors = { Color.web("#00ff0466"), Color.ORANGE, Color.web("#0088ff66"),
				Color.web("#4000ff66") };

		// Add cases in rows and columns
		double startX = 44;
		double startY = 24;
		double caseWidth = 146;
		double caseHeight = 90;

		// First-Top row
		for (int i = 0; i < 8; i++) {
			Case rectangle = new Case(startX + i * caseWidth, startY, colors[i % colors.length]);
			getChildren().add(rectangle);
		}

		// First-Right column
		for (int i = 0; i < 6; i++) {
			Case rectangle = new Case(startX + caseWidth * 7, startY + (i + 1) * caseHeight,
					colors[(i + 4) % colors.length]);
			getChildren().add(rectangle);
		}

		// First-Bottom row
		for (int i = 5; i >= 0; i--) {
			Case rectangle = new Case(startX + (i + 1) * caseWidth, startY + 6 * caseHeight,
					reversecolors[(i) % reversecolors.length]);
			getChildren().add(rectangle);
		}

		// Left column
		for (int i = 4; i > 0; i--) {
			Case rectangle = new Case(startX + caseWidth, startY + caseHeight + i * caseHeight,
					reversecolors[(i + 3) % reversecolors.length]);
			getChildren().add(rectangle);
		}

		// Second-Top row
		for (int i = 0; i < 4; i++) {
			Case rectangle = new Case(startX + (i + 2) * caseWidth, startY + 2 * caseHeight, colors[i % colors.length]);
			getChildren().add(rectangle);
		}

		// Second-Right column
		for (int i = 0; i < 2; i++) {
			Case rectangle = new Case(startX + caseWidth * 5, startY + caseHeight + (i + 2) * caseHeight,
					colors[(i + 4) % colors.length]);
			getChildren().add(rectangle);
		}

		// Second-Bottom row
		for (int i = 1; i >= 0; i--) {
			Case rectangle = new Case(startX + (i + 3) * caseWidth, startY + 4 * caseHeight,
					reversecolors[(i) % reversecolors.length]);
			getChildren().add(rectangle);
		}
	}
}
