package View;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class RectangleView extends Rectangle {
    public RectangleView(double x, double y, String color) {
        super(146, 90, Color.web(color));
        setArcHeight(5.0);
        setArcWidth(5.0);
        setStroke(Color.web("#0000001a"));
        setLayoutX(x);
        setLayoutY(y);
    }
}
