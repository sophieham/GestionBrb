package gestionbrb;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import gestionbrb.controleur.FonctionsControleurs;
import gestionbrb.controleur.ModifierCalendrierControleur;
import gestionbrb.model.Reservations;

/**
 * 
 * @author Sophie
 *
 */
public class Calendrier {

    private Stage primaryStage;
    private static ObservableList<Reservations> reservationData = FXCollections.observableArrayList();


    public Calendrier() {
    }
    
    
    /**
     * Retourne les données présentes dans la liste Reservations. 
     * @return reservationData
     */
    
    public static ObservableList<Reservations> getReservationData() {
        return reservationData;
    }
    
    
    /**
     * Ouvre une fenêtre pour modifier les reservations.
     * 
     * @param reservation la reservation a modifier
     * @return true si l'utilisateur appuie sur modifier, false sinon
     */
    
    public static boolean fenetreModification(Reservations reservation) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Calendrier.class.getResource("vue/ModifierReservation.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Modifier une reservation");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            ModifierCalendrierControleur controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setReservation(reservation);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (Exception e) {
            FonctionsControleurs.alerteErreur("Erreur d'éxécution", null, "Détails:"+e);
            return false;
        }
    }

    /**
     * Retourne un lien vers la page principale
     * @return primaryStage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

}