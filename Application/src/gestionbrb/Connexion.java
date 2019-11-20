package gestionbrb;

import java.io.IOException;

import gestionbrb.controleur.ConnexionProfil;
import gestionbrb.controleur.FonctionsControleurs;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/*
 * Page de connexion
 * @author Léo
 */

public class Connexion extends Application {

    private Stage primaryStage;
    public Connexion() {

    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Connexion");
        afficherPageConnexion();
    }
    
	/**
	 * Charge la page connexion et l'affiche.
	 * 
	 */
    public void afficherPageConnexion() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Connexion.class.getResource("vue/Connexion.fxml"));
            AnchorPane ConnexionOverview = (AnchorPane) loader.load();
            Scene scene = new Scene(ConnexionOverview);
            primaryStage.setScene(scene);
            primaryStage.show();
            
            // initialise le controleur
            ConnexionProfil controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
        	FonctionsControleurs.alerteErreur("Erreur d'éxécution", null, "Détails: "+e);
            e.printStackTrace();
        }
    }

    /*
     * Retourne la fenetre principale
     * @return primaryStage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}