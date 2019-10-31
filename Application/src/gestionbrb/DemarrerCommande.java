package gestionbrb;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import gestionbrb.vue.DemarrerCommandeControleur;

public class DemarrerCommande extends Application {

    private Stage primaryStage;

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
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Calendrier");
        afficherReservation();
    }
    
    /**
     * Shows the person overview inside the root layout.
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
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
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