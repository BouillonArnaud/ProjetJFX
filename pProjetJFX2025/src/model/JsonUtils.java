package model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonUtils {

    /**
     * Lit un fichier JSON et retourne son contenu sous forme d'objet JSON.
     * @param filePath Chemin relatif/absolu du fichier (ex: "src/resources/config.json")
     * @return JsonObject (structure JSON exploitable)
     * @throws Exception Si le fichier est introuvable ou mal formaté
     */
    public static JsonObject readJson(String filePath) throws Exception {
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        return new Gson().fromJson(content, JsonObject.class);
    }
}