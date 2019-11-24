package gestionbrb;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import gestionbrb.controleur.FonctionsControleurs;
import gestionbrb.vue.DemarrerCommandeControleur;

public class DemarrerCommande extends Application {

    public static Stage primaryStage;

    public DemarrerCommande() {

    }
    
    @Override
    public void start(Stage primaryStage) {
        DemarrerCommande.primaryStage = primaryStage;
        DemarrerCommande.primaryStage.setTitle("Démarrer une nouvelle commande");
        afficherDemarrerCommande();
    }
    
    /**
     * Appellée pour afficher la page principale
     */
    public void afficherDemarrerCommande() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(DemarrerCommande.class.getResource("vue/DemarrerCommande.fxml"));
            AnchorPane reservationOverview = (AnchorPane) loader.load();
            Scene scene = new Scene(reservationOverview);
            primaryStage.setScene(scene);
            primaryStage.show();
            // Give the controller access to the main app.
            DemarrerCommandeControleur controller = loader.getController();
            controller.setParent(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /*
     * Appellé pour ouvrir le calendrier
     */
    
    public void Calendrier() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("CalendrierReservations.fxml"));
			Parent vueCalendrier = (Parent) loader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(vueCalendrier));
			stage.show();
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Impossible d'ouvrir cette fenetre", "Détails: "+e);

		}
    }

    /**
     * Retourne le stage parent
     * @return primaryStage
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}