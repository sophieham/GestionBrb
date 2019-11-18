package gestionbrb;

import java.sql.SQLException;

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

public class Calendrier extends FonctionsControleurs {

    private Stage primaryStage;
    private static ObservableList<Reservations> reservationData = FXCollections.observableArrayList();


    public Calendrier() {
    }
    
    
    /**
     * Retourne les donn�es pr�sentes dans la liste Reservations. 
     * @return reservationData
     */
    
    public static ObservableList<Reservations> getReservationData() {
        return reservationData;
    }
    
    
    /**
     * Ouvre une fen�tre pour modifier les reservations.
     * 
     * @param reservation
     * @return true si l'utilisateur appuie sur modifier, false sinon
     * @throws Exception affiche une fenetre d'erreur expliquant l'exception
     */
    
    public static boolean fenetreModification(Reservations reservation) throws ClassNotFoundException, SQLException {
        try {
            // Charge le fichier fxml et l'ouvre en pop-up
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Calendrier.class.getResource("vue/ModifierReservation.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Cr�e une nouvelle page
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Modifier une reservation");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // D�finition du controleur pour la fenetre
            ModifierCalendrierControleur controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setReservation(reservation);

            // Affiche la page et attend que l'utilisateur la ferme.
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (Exception e) {
            alerteErreur("Erreur d'�x�cution", null, "D�tails:"+e);
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