package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class Case extends Rectangle {
	public Case(double x, double y, Color fillColor) {
		super(x, y, 146, 90);
		setFill(fillColor);
		setArcHeight(5.0);
		setArcWidth(5.0);
		setStroke(Color.web("#0000001a"));
		setStrokeType(StrokeType.INSIDE);
	}
}