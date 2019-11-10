package gestionbrb;

import java.io.IOException;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import gestionbrb.model.Reservations;
import gestionbrb.vue.ModifierCalendrierControleur;

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
     * @param reservation
     * @return true si l'utilisateur appuie sur modifier, false sinon
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    public static boolean fenetreModification(Reservations reservation) throws ClassNotFoundException, SQLException {
        try {
            // Charge le fichier fxml et l'ouvre en pop-up
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Calendrier.class.getResource("vue/ModifierReservation.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Crée une nouvelle page
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Modifier une reservation");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Définition du controleur pour la fenetre
            ModifierCalendrierControleur controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setReservation(reservation);

            // Affiche la page et attend que l'utilisateur la ferme.
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

}