package model;

import com.google.gson.Gson;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.control.Alert;

public class JsonUtils {

    private static final String JSON_FILENAME = "questions (1).json";

    /**
     * Load questions form Json files 
     */
    public static List<Question> getJsonContent() {
        try {
            // Solution 1: Load from ressources
            InputStream is = JsonUtils.class.getResourceAsStream("/" + JSON_FILENAME);
            if (is != null) {
                return loadFromStream(is);
            }

            // Solution 2: Load from execution directory
            Path filePath = Paths.get(JSON_FILENAME).toAbsolutePath();
            if (Files.exists(filePath)) {
                return loadFromFile(filePath);
            }

            // Solution 3: Load from "ressources" directory
            Path resourcesPath = Paths.get("src/main/resources/" + JSON_FILENAME);
            if (Files.exists(resourcesPath)) {
                return loadFromFile(resourcesPath);
            }

            showErrorAlert("Fichier '" + JSON_FILENAME + "' introuvable.\n"
                    + "Emplacements vérifiés:\n"
                    + "1. Ressources du JAR\n"
                    + "2. " + filePath + "\n"
                    + "3. " + resourcesPath);

        } catch (Exception e) {
            showErrorAlert("Erreur de lecture: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private static List<Question> loadFromStream(InputStream is) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8))) {
            
            QuestionWrapper wrapper = new Gson().fromJson(reader, QuestionWrapper.class);
            logSuccess(wrapper.getQuestions(), "ressources incorporées");
            return wrapper.getQuestions();
        }
    }

    private static List<Question> loadFromFile(Path path) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            QuestionWrapper wrapper = new Gson().fromJson(reader, QuestionWrapper.class);
            logSuccess(wrapper.getQuestions(), path.toString());
            return wrapper.getQuestions();
        }
    }

    private static void logSuccess(List<Question> questions, String source) {
        System.out.printf("%d questions chargées depuis %s%n", questions.size(), source);
        if (!questions.isEmpty()) {
            Question first = questions.get(0);
            System.out.printf("Exemple: %s → %s%n", first.getQuestionContent(), first.getAnswer());
        }
    }

    private static void showErrorAlert(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de chargement");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}