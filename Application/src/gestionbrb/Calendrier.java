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
    
    /**
     * ObservableList de Reservations
     */
    private ObservableList<Reservations> reservationData = FXCollections.observableArrayList();

    /**
     * Constructeur
     */
    public Calendrier() {
    	try {
    		Connection conn= bddUtil.dbConnect();
    		ResultSet rs = conn.createStatement().executeQuery("select * from calendrier");
    		while (rs.next()) {
        reservationData.add(new Reservations(rs.getInt("idReservation"), rs.getString("nom"), rs.getString("prenom"), rs.getString("numeroTel"), rs.getString("dateReservation"),rs.getString("heureReservation"),rs.getInt("nbCouverts"), rs.getString("demandeSpe")));
    		}
    	} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * Retourne les données présentes dans la liste Reservations. 
     * @return reservationData
     */
    public ObservableList<Reservations> getReservationData() {
        return reservationData;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Calendrier");
        showReservationOverview();
    }
    
    /**
     * Shows the person overview inside the root layout.
     */
    public void showReservationOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Calendrier.class.getResource("vue/CalendrierReservations.fxml"));
            AnchorPane reservationOverview = (AnchorPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(reservationOverview);
            primaryStage.setScene(scene);
            primaryStage.show();
            // Give the controller access to the main app.
            CalendrierControleur controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Opens a dialog to edit details for the specified person. If the user
     * clicks OK, the changes are saved into the provided person object and true
     * is returned.
     * 
     * @param person the person object to be edited
     * @return true if the user clicked OK, false otherwise.
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    public boolean showReservationEditDialog(Reservations reservation) throws ClassNotFoundException, SQLException {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Calendrier.class.getResource("vue/ModifierReservation.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Modifier une reservation");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            ModifierCalendrierControleur controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setReservation(reservation);

            // Show the dialog and wait until the user closes it
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