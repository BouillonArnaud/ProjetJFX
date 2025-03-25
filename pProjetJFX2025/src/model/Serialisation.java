package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Serialisation {
    
    static List<Questions> questionsList;
    
    public Serialisation() {
        this.questionsList = new ArrayList<>();
    }

    // ÉCRIRE DES QUESTIONS DANS UN FICHIER JSON
    public static void ecrireFichier(List<Questions> questionsToAdd, String fileName) {
        try (PrintWriter pw = new PrintWriter(fileName)) { // Création d'un objet PrintWriter pour écrire dans le fichier
            pw.println("[\n"); // Ouverture du tableau JSON

            for (Questions q : questionsToAdd) { // Pour chaque question dans la liste
                pw.write("\t" + q.toJson() + ",\n"); // Écriture de la question en JSON
            }

            pw.write("]"); // Fermeture du tableau JSON

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // LIRE DES QUESTIONS DANS UN FICHIER JSON
    @SuppressWarnings("finally")
    public static String lireFichier(String fileName) {
        String questionsJson = null;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) { // Création d'un BufferedReader pour lire le fichier
            questionsJson = ""; // Initialisation d'une chaîne de caractères vide
            String line = br.readLine(); // Lecture de la première ligne

            while (line != null) { // Tant qu'il y a des lignes à lire
                questionsJson += line + "\n"; // Ajout de la ligne à la chaîne de caractères
                line = br.readLine(); // Lecture de la ligne suivante
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichier non trouvé");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return questionsJson; // Retour de la chaîne de caractères
        }
    }
}
