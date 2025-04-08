package views;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Case;
import model.GameBoard;
import model.Pion;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class BoardView extends Pane {
	private final GameBoard board;
	private final List<ImageView> pawnViews = new ArrayList<>(); // For visual pawns
	private final Color[] colors = { Color.PURPLE, Color.ORANGE, Color.BLUE, Color.GREEN, Color.ORANGE, Color.ORANGE,
			Color.PURPLE, Color.GREEN };
	private static final int RECT_WIDTH = 146; // Largeur des rectangles
	private static final int RECT_HEIGHT = 90; // Hauteur des rectangles
	private MediaPlayer mediaPlayer;
	private boolean isPlaying = false;
	private ImageView imgView;
	private Stage currentPopup = null; // Store the current popup stage
	private int currentPlayerIndex;
	private boolean isInitialized = false;


	public BoardView(GameBoard board, Stage boardStage) {
		this.board = board;
		this.currentPlayerIndex = 0;

		Image backgroundImage = new Image("/resources/background_cyberpunk.jpg"); // Remplace par le chemin réel de ton
																					// image
		ImageView backgroundView = new ImageView(backgroundImage);
		backgroundView.fitWidthProperty().bind(this.widthProperty());
		backgroundView.fitHeightProperty().bind(this.heightProperty());
		this.getChildren().add(backgroundView);

		// Dessiner le plateau selon le chemin avec alternance de couleurs
		int index = 0;
		for (Case c : board.getChemin()) {
			Rectangle casePlateau = new Rectangle(RECT_WIDTH, RECT_HEIGHT);
			casePlateau.setFill(colors[index % colors.length]);
			casePlateau.setStroke(Color.BLACK);
			casePlateau.setX(c.getX() * RECT_WIDTH / 40); // Ajustement des proportions
			casePlateau.setY(c.getY() * RECT_HEIGHT / 40);
			this.getChildren().add(casePlateau);
			index++;
		}

		updatePawnPositions();

		StackPane btnMenu = createButton("Return to menu");

		btnMenu.setOnMouseClicked(event -> {
			Stage mainMenuStage = new Stage();
			MainMenuView mainMenuView = new MainMenuView(mainMenuStage);
			Scene mainMenuScene = new Scene(mainMenuView, 1920, 1080);
			mainMenuStage.setScene(mainMenuScene);
			mainMenuStage.setTitle("Menu Principal");
			mainMenuStage.setMaximized(true);
			mainMenuStage.show();
			boardStage.close();
		});
		this.getChildren().add(btnMenu);

		imgView = new ImageView(new Image("/resources/button_son_off.png"));
		imgView.setFitWidth(50);
		imgView.setFitHeight(50);

		StackPane btnMusic = new StackPane(imgView);
		btnMusic.setLayoutX(120);
		this.getChildren().add(btnMusic);

		btnMusic.setOnMouseClicked(event -> toggleMusic());

		String musicFile = getClass().getResource("/resources/pain.mp3").toExternalForm();
		Media sound = new Media(musicFile);
		mediaPlayer = new MediaPlayer(sound);
	}

	public void setCurrentPlayerIndex(int currentPlayerIndex) {
		this.currentPlayerIndex = currentPlayerIndex;
	}

	public void updatePawnPositions() {
	    List<Pion> pions = board.getPions(); // Get the list of pawns

	    // Add new pawns if necessary
	    if (pawnViews.size() < pions.size()) {
	        for (int i = pawnViews.size(); i < pions.size(); i++) {
	            // Create new ImageView for the new pawn
	            String imagePath = "/resources/pawns" + (i + 1) + ".png";
	            Image pawnImage = new Image(imagePath);
	            ImageView pawnView = new ImageView(pawnImage);
	            pawnView.setFitWidth(RECT_HEIGHT / 2);
	            pawnView.setFitHeight(RECT_HEIGHT / 2);
	            pawnViews.add(pawnView);
	            this.getChildren().add(pawnView); // Add the pawn view to the pane
	        }
	    }

	    // Update the positions of all the pawns
	    for (int i = 0; i < pions.size(); i++) {
	        Pion pion = pions.get(i);

	        // Get the current case for the pawn
	        Case currentCase = board.getChemin().get(pion.getIndex()); // Ensure the index is valid
	        ImageView pawnView = pawnViews.get(i);

	        System.out.println("Pawn " + i + " at Case " + pion.getIndex() + " -> X: " + currentCase.getX() + ", Y: "
	                + currentCase.getY());

	        // Position the pawns in different corners (top-left, top-right, bottom-left, bottom-right)
	        int cornerOffsetX = 0;
	        int cornerOffsetY = 0;

	        switch (i) {
	            case 0:
	                // Top-left corner
	                cornerOffsetX = 0;
	                cornerOffsetY = 0;
	                break;
	            case 1:
	                // Top-right corner
	                cornerOffsetX = RECT_WIDTH / 2;
	                cornerOffsetY = 0;
	                break;
	            case 2:
	                // Bottom-left corner
	                cornerOffsetX = 0;
	                cornerOffsetY = RECT_HEIGHT / 2;
	                break;
	            case 3:
	                // Bottom-right corner
	                cornerOffsetX = RECT_WIDTH / 2;
	                cornerOffsetY = RECT_HEIGHT / 2;
	                break;
	            default:
	                break;
	        }

	        // Calculate the exact position based on currentCase and corner offset
	        double xPosition = currentCase.getX() * RECT_WIDTH / 40 + cornerOffsetX;
	        double yPosition = currentCase.getY() * RECT_HEIGHT / 40 + cornerOffsetY;

	        // Update the position of the pawn in the selected corner
	        pawnView.setX(xPosition);
	        pawnView.setY(yPosition);

	        // Only show the popup for the current player's pawn if not initialized yet
	        if (i == currentPlayerIndex && isInitialized) {
	            showCasePopup(pion, colors[pion.getIndex() % colors.length]);
	        }
	    }

	    // Set initialized to true once the initial setup is done
	    if (!isInitialized) {
	        isInitialized = true;
	    }
	}

	/**
	 * Displays a colored popup window with case information
	 * 
	 * @param caseColor The background color for the popup
	 */
	private void showCasePopup(Pion pion, Color caseColor) {
	    // Close the previous popup if there is one
	    if (currentPopup != null) {
	        currentPopup.close();
	    }

	    // Create a new popup window
	    Stage popupStage = new Stage();
	    popupStage.initModality(Modality.APPLICATION_MODAL);

	    VBox content = new VBox(20);
	    content.setAlignment(Pos.CENTER);
	    content.setPadding(new Insets(20));
	    content.setStyle("-fx-background-color: #" + caseColor.toString().substring(2, 8) + ";");

	    // Add title
	    Label title = new Label("Pawn Details");
	    title.setStyle("-fx-font-size: 20; -fx-text-fill: white; -fx-font-weight: bold;");

	    // Display the current position of the pawn
	    int caseIndex = pion.getIndex();
	    Label info = new Label("Pawn #" + pion.hashCode() + "\n" + "Position: Case #" + caseIndex + "\n" + "Color: "
	            + getColorName(caseColor));
	    info.setStyle("-fx-font-size: 16; -fx-text-fill: white;");
	    info.setTextAlignment(TextAlignment.CENTER);

	    // Add close button
	    Button closeButton = new Button("Close");
	    closeButton.setOnAction(e -> popupStage.close());

	    content.getChildren().addAll(title, info, closeButton);

	    // Set up and show the scene
	    Scene scene = new Scene(content, 350, 250);
	    popupStage.setScene(scene);

	    // Set the current popup reference
	    currentPopup = popupStage;

	    // Display the popup with a slight delay
	    PauseTransition delay = new PauseTransition(Duration.seconds(0.3));
	    delay.setOnFinished(event -> popupStage.show()); // Show after delay to avoid blocking
	    delay.play();
	}

	/**
	 * Converts JavaFX Color to color name
	 * 
	 * @param color The color to convert
	 * @return The color name in English
	 */
	private String getColorName(Color color) {
		if (color.equals(Color.PURPLE))
			return "Purple";
		if (color.equals(Color.ORANGE))
			return "Orange";
		if (color.equals(Color.BLUE))
			return "Blue";
		if (color.equals(Color.GREEN))
			return "Green";
		return "Unknown";
	}

	/**
	 * Creates a styled button
	 * 
	 * @param textContent Button text
	 * @return Configured StackPane button
	 */

	public StackPane createButton(String textContent) {
		Rectangle rectangle = new Rectangle(100, 50);
		rectangle.setFill(Color.YELLOW);
		rectangle.setStroke(Color.BLACK);

		Text text = new Text(textContent);
		text.setFill(Color.BLACK);

		StackPane stack = new StackPane();
		stack.getChildren().addAll(rectangle, text);

		return stack;
	}

	private void toggleMusic() {
		if (mediaPlayer == null)
			return; // Sécurité : Vérifier que mediaPlayer existe

		MediaPlayer.Status status = mediaPlayer.getStatus();
		if (status == MediaPlayer.Status.PLAYING) {
			mediaPlayer.pause();
			// Mettre l'image en OFF
			imgView.setImage(new Image(getClass().getResource("/resources/button_son_off.png").toExternalForm()));
		} else {
			mediaPlayer.play();
			// Mettre l'image en ON
			imgView.setImage(new Image(getClass().getResource("/resources/button_son_on.png").toExternalForm()));
		}
	}

}
