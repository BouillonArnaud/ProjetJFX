package application;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

public class SampleController {

    @FXML
    private ImageView pion;

    private double offsetX, offsetY;

    @FXML
    public void initialize() {
        // Appliquer un effet néon au pion
        DropShadow glow = new DropShadow();
        glow.setColor(Color.CYAN);
        glow.setRadius(20);
        pion.setEffect(glow);
    }

    @FXML
    private void onDragStart(MouseEvent event) {
        offsetX = event.getSceneX() - pion.getLayoutX();
        offsetY = event.getSceneY() - pion.getLayoutY();
    }

    @FXML
    private void onDrag(MouseEvent event) {
        pion.setLayoutX(event.getSceneX() - offsetX);
        pion.setLayoutY(event.getSceneY() - offsetY);
    }
}
