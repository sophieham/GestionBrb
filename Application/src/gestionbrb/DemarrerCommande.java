package gestionbrb;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import gestionbrb.controleur.FonctionsControleurs;
import gestionbrb.vue.DemarrerCommandeControleur;

public class DemarrerCommande extends Application {

    private static Stage primaryStage;
    /**
     * Constructeur
     */
    public DemarrerCommande() {

    }
    
    

    /**
     * Retourne les données présentes dans la liste Reservations. 
     * @return
     */
    

    @Override
    public void start(Stage primaryStage) {
        DemarrerCommande.primaryStage = primaryStage;
        DemarrerCommande.primaryStage.setTitle("Démarrer une nouvelle commande");
        afficherReservation();
    }
    
    /**
     * Shows the command overview inside the root layout.
     */
    public void afficherReservation() {
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