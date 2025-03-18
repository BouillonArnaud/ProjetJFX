package View;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

class PawnsView extends ImageView {
    public PawnsView(String imagePath, double x, double y) {
        super(new Image(imagePath));
        setFitWidth(50);
        setFitHeight(50);
        setLayoutX(x);
        setLayoutY(y);
    }
}
