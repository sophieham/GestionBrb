package gestionbrb;

import java.io.IOException;

import gestionbrb.vue.ConnexionProfil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Connexion extends Application {

    private Stage primaryStage;

    /**
     * Constructeur
     */
    public Connexion() {

    }

    /**
     * Retourne les données présentes dans la liste Connexions. 
     * @return
     */
    

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Connexion");
        showConnexionOverview();
    }
    
    /**
     * Shows the person overview inside the root layout.
     */
    public void showConnexionOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Connexion.class.getResource("vue/Connexion.fxml"));
            AnchorPane ConnexionOverview = (AnchorPane) loader.load();
            Scene scene = new Scene(ConnexionOverview);
            primaryStage.setScene(scene);
            primaryStage.show();
            ConnexionProfil controller = loader.getController();
            controller.setMainApp(this);

            // Give the controller access to the main app.

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