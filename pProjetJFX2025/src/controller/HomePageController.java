
package controller;

import java.io.File;

import View.GameBoardView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.HomePageModel;

public class HomePageController {
    private HomePageModel model;
    private Pane view;
    private Stage stage; // Pour la navigation entre les vues

    public HomePageController(HomePageModel model, Stage stage) {
        this.model = model;
        this.stage = stage;
        this.view = new Pane();
        initializeView();
    }

    private void initializeView() {
        // Arrière-plan
       ImageView background = new ImageView(new Image("/application/resources/Acceuil_Cyberpunk_2077.jpg"));
        background.setFitWidth(1275);
       background.setFitHeight(727);
    	
        //String videoPath = "/application/resources/acceuil.mp4"; // Mets le bon chemin
       // File file = new File(videoPath);
        //Media media = new Media(file.toURI().toString());
        //MediaPlayer mediaPlayer = new MediaPlayer(media);
        //mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Répétition infinie
       // mediaPlayer.setAutoPlay(true); // Démarrer automatiquement
        
       // MediaView mediaView = new MediaView(mediaPlayer);
       // mediaView.setFitWidth(1275);
       // mediaView.setFitHeight(727);

        // Images des boutons
        ImageView icon1 = createImageView("/application/resources/acceil.png", 63, 254, 120, 96);
        ImageView icon2 = createImageView("/application/resources/acceil.png", 207, 254, 120, 96);
        ImageView icon3 = createImageView("/application/resources/acceil.png", 348, 254, 120, 96);
        ImageView icon4 = createImageView("/application/resources/acceil2.png", 73, 261, 101, 66);
        ImageView icon5 = createImageView("/application/resources/acceil2.png", 216, 261, 101, 66);
        ImageView icon6 = createImageView("/application/resources/acceil2.png", 358, 260, 101, 66);
        ImageView title = createImageView("/application/resources/acceil3.png", 113, 82, 370, 180);
        ImageView image5 = createImageView("/application/resources/Image5.png", 48, 350, 702, 318);

        // Textes
        Text settingsText = createText("SETTINGS", 98, 298);
        Text starText = createText("START", 253, 298);
        Text adminText = createText("ADMIN SPACES", 367, 297);

        // Gestion du clic sur "STAR"
       starText.setOnMouseClicked(this::handleStarClick);

        // Ajout des éléments à la vue
        view.getChildren().addAll(background, icon1, icon2, icon3, icon4, icon5, icon6, title, image5, settingsText, starText, adminText);
    }

    private void handleStarClick(MouseEvent event) {
        GameBoardView gameBoardView = new GameBoardView();
        gameBoardView.start(stage);
    }

    private ImageView createImageView(String path, double x, double y, double width, double height) {
        ImageView imageView = new ImageView(new Image(path));
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        return imageView;
    }

    private Text createText(String content, double x, double y) {
        Text text = new Text(content);
        text.setFill(javafx.scene.paint.Color.WHITE);
        text.setLayoutX(x);
        text.setLayoutY(y);
        return text;
    }

    public Pane getView() {
        return view;
    }
}
