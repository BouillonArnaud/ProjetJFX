package views;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.HelpPageModel;

public class HelpPageView extends Pane {
    private final HelpPageModel model;
    private final Stage stage;

    public HelpPageView(HelpPageModel model, Stage stage) {
        this.model = model;
        this.stage = stage;
        this.setPrefSize(1920, 1080);
        initializeUI();
    }

    private void initializeUI() {
        // Background Image
        ImageView background;
        try {
            background = new ImageView(new Image(getClass().getResourceAsStream(model.getBackgroundImagePath())));
        } catch (Exception e) {
            background = new ImageView();
            background.setStyle("-fx-background-color: #1a1a1a;"); // Fallback dark background
        }
        background.setFitWidth(1920);
        background.setFitHeight(1080);
        background.setPreserveRatio(true);

        // Gradient Overlay
        Rectangle overlay = new Rectangle(1920, 1080);
        overlay.setFill(new LinearGradient(0, 0, 1, 1, true, null,
                new Stop(0, Color.rgb(0, 0, 0, 0.3)),
                new Stop(1, Color.rgb(0, 0, 0, 0.5))));
        FadeTransition fade = new FadeTransition(Duration.seconds(5), overlay);
        fade.setFromValue(0.4);
        fade.setToValue(0.7);
        fade.setAutoReverse(true);
        fade.setCycleCount(FadeTransition.INDEFINITE);
        fade.play();

        // Content Container
        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(30));
        content.setStyle("-fx-background-color: rgba(0, 0, 0, 0.85); -fx-background-radius: 20; -fx-border-color: #10B981; -fx-border-width: 2; -fx-border-radius: 20;");
        content.setEffect(new DropShadow(20, Color.BLACK));
        content.setMaxWidth(1000);
        content.setMaxHeight(800);
        content.setLayoutX(460);
        content.setLayoutY(140);

        // Title
        Label title = new Label("Game Rules");
        title.setStyle("-fx-font-size: 36; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-family: 'Arial';");
        title.setEffect(new DropShadow(10, Color.BLACK));

        // TextArea for Game Rules
        TextArea textArea = new TextArea(model.getRulesText());
        textArea.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        textArea.setStyle("-fx-control-inner-background: #1a1a1a; -fx-text-fill: white; -fx-highlight-fill: #10B981; -fx-highlight-text-fill: white;");
        textArea.setPrefWidth(900);
        textArea.setPrefHeight(600);
        textArea.setWrapText(true);
        textArea.setEditable(false);

        // Back Button
        Button backButton = new Button("Back to Game");
        backButton.setStyle("-fx-background-color: #EF4444; -fx-text-fill: white; -fx-font-size: 16; -fx-background-radius: 10; -fx-padding: 10 20;");
        backButton.setEffect(new DropShadow(10, Color.BLACK));
        backButton.setOnAction(e -> stage.close());
        addHoverEffect(backButton);

        // Add components to content
        content.getChildren().addAll(title, textArea, backButton);

        // Add all components to the root pane
        this.getChildren().addAll(background, overlay, content);

        // Set up the Scene and Stage
        Scene scene = new Scene(this);
        stage.setTitle("Rules");
        stage.setScene(scene);
        stage.show();
    }

    private void addHoverEffect(javafx.scene.Node node) {
        ScaleTransition scale = new ScaleTransition(Duration.millis(200), node);
        scale.setToX(1.1);
        scale.setToY(1.1);
        node.setOnMouseEntered(e -> scale.playFromStart());
        node.setOnMouseExited(e -> {
            ScaleTransition reverse = new ScaleTransition(Duration.millis(200), node);
            reverse.setToX(1);
            reverse.setToY(1);
            reverse.play();
        });
    }
}