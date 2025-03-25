package controller;

import javafx.scene.layout.Pane;
import model.GameBoardModel;
import View.RectangleView;
import javafx.scene.paint.Color;

public class GameBoardController {
    private GameBoardModel model;
    private Pane view;

    public GameBoardController(GameBoardModel model) {
        this.model = model;
        this.view = new Pane();
        initializeBoard();
    }

    private void initializeBoard() {
        Color[] colors = { Color.web("#4000ff66"), Color.web("#0088ff66"), Color.ORANGE, Color.web("#00ff0466") };
        Color[] reverseColors = { Color.web("#00ff0466"), Color.ORANGE, Color.web("#0088ff66"), Color.web("#4000ff66") };

        double startX = 44;
        double startY = 24;
        double caseWidth = 146;
        double caseHeight = 90;

        // First-Top row
        for (int i = 0; i < 8; i++) {
            RectangleView rectangle = new RectangleView(startX + i * caseWidth, startY, colors[i % colors.length]);
            view.getChildren().add(rectangle);
        }

        // First-Right column
        for (int i = 0; i < 6; i++) {
            RectangleView rectangle = new RectangleView(startX + caseWidth * 7, startY + (i + 1) * caseHeight,
                    colors[(i + 4) % colors.length]);
            view.getChildren().add(rectangle);
        }

        // First-Bottom row
        for (int i = 5; i >= 0; i--) {
            RectangleView rectangle = new RectangleView(startX + (i + 1) * caseWidth, startY + 6 * caseHeight,
                    reverseColors[i % reverseColors.length]);
            view.getChildren().add(rectangle);
        }

        // Left column
        for (int i = 4; i > 0; i--) {
            RectangleView rectangle = new RectangleView(startX + caseWidth, startY + caseHeight + i * caseHeight,
                    reverseColors[(i + 3) % reverseColors.length]);
            view.getChildren().add(rectangle);
        }

        // Second-Top row
        for (int i = 0; i < 4; i++) {
            RectangleView rectangle = new RectangleView(startX + (i + 2) * caseWidth, startY + 2 * caseHeight, colors[i % colors.length]);
            view.getChildren().add(rectangle);
        }

        // Second-Right column
        for (int i = 0; i < 2; i++) {
            RectangleView rectangle = new RectangleView(startX + caseWidth * 5, startY + caseHeight + (i + 2) * caseHeight,
                    colors[(i + 4) % colors.length]);
            view.getChildren().add(rectangle);
        }

        // Second-Bottom row
        for (int i = 1; i >= 0; i--) {
            RectangleView rectangle = new RectangleView(startX + (i + 3) * caseWidth, startY + 4 * caseHeight,
                    reverseColors[i % reverseColors.length]);
            view.getChildren().add(rectangle);
        }
    }

    public Pane getView() {
        return view;
    }
}