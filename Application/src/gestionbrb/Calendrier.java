package gestionbrb;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import gestionbrb.model.Reservations;
import gestionbrb.util.bddUtil;
import gestionbrb.vue.CalendrierControleur;
import gestionbrb.vue.ModifierCalendrierControleur;

public class Calendrier extends Application {

    private Stage primaryStage;
    private ObservableList<Reservations> reservationData = FXCollections.observableArrayList();


    public Calendrier() {
    	try {
    		Connection conn= bddUtil.dbConnect();
    		ResultSet rs = conn.createStatement().executeQuery("select * from calendrier");
    		while (rs.next()) {
        reservationData.add(new Reservations(rs.getInt("idReservation"), rs.getString("nom"), rs.getString("prenom"), rs.getString("numeroTel"), rs.getString("dateReservation"),rs.getString("heureReservation"),rs.getInt("nbCouverts"), rs.getString("demandeSpe")));
    		}
    	} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
    }
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Calendrier");
        afficheCalendrier();
    }

    /**
     * Retourne les données présentes dans la liste Reservations. 
     * @return reservationData
     */
    public ObservableList<Reservations> getReservationData() {
        return reservationData;
    }
    
    /**
     * Ouvre une fenetre contenant le calendrier
     */
    public void afficheCalendrier() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Calendrier.class.getResource("vue/CalendrierReservations.fxml"));
            AnchorPane reservationOverview = (AnchorPane) loader.load();

            Scene scene = new Scene(reservationOverview);
            primaryStage.setScene(scene);
            primaryStage.show();

            
            CalendrierControleur controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Ouvre une fenêtre pour modifier les reservations.
     * 
     * @param reservation
     * @return true si l'utilisateur appuie sur modifier, false sinon
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    public boolean fenetreModification(Reservations reservation) throws ClassNotFoundException, SQLException {
        try {
            // Charge le fichier fxml et l'ouvre en pop-up
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Calendrier.class.getResource("vue/ModifierReservation.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Crée une nouvelle page
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Modifier une reservation");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
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

    public static void main(String[] args) {
        launch(args);
    }
}