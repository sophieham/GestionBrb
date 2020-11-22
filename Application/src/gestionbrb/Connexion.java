package gestionbrb;

import gestionbrb.controleur.ConnexionControleur;
import gestionbrb.controleur.FonctionsControleurs;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

// TODO: Auto-generated Javadoc
/**
 * Page de connexion.
 *
 * @author Léo
 */
public class Connexion extends Application {
    
    /** The primary stage. */
    private Stage primaryStage;
    
    /**
     * Constructeur de la connexion.
     */
    public Connexion() {

    }

    /**
     * Start.
     *
     * @param primaryStage the primary stage
     */
    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle(" * * * GestionBrb * * * ");
        afficherPageConnexion();
    }
    
	/**
	 * Charge la page connexion et l'affiche.
	 * 
	 */
    public void afficherPageConnexion() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Connexion.class.getResource("vue/Connexion.fxml"));
            AnchorPane ConnexionOverview = (AnchorPane) loader.load();
            Scene scene = new Scene(ConnexionOverview, 1500, 780);
            primaryStage.getIcons().add(new Image(
            	      Connexion.class.getResourceAsStream( "ico.png" ))); 
            primaryStage.setScene(scene);
            primaryStage.show();
            
            // initialise le controleur
            ConnexionControleur controller = loader.getController();
            controller.setParent(this);

        } catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Une erreur est survenue","Détails: "+e);

            e.printStackTrace();
        }
    }


	/**
	 * Retourne la fenetre principale.
	 *
	 * @return primaryStage
	 */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}