package gestionbrb;

import java.io.IOException;

import gestionbrb.vue.AjoutTableControleur;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AjoutTable extends Application {

    private Stage primaryStage;

    /**
     * Constructeur
     */
    public AjoutTable() {

    }

    /**
     * Retourne les données présentes dans la liste Table. 
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
            loader.setLocation(AjoutTable.class.getResource("vue/GererTables.fxml"));
            AnchorPane reservationOverview = (AnchorPane) loader.load();
            Scene scene = new Scene(reservationOverview);
            primaryStage.setScene(scene);
            primaryStage.show();
            // Give the controller access to the main app.
            AjoutTableControleur controller = loader.getController();
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
