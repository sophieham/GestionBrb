package gestionbrb;

import java.io.IOException;
import java.sql.SQLException;

import gestionbrb.controleur.ConnexionProfil;
import gestionbrb.controleur.MenuPrincipalControleur;
import gestionbrb.controleur.ModifierFournisseurControleur;
import gestionbrb.model.Fournisseur;
import gestionbrb.model.Utilisateur;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuPrincipal extends Application {
	public static String[] arg;
	private Stage primaryStage;
	private Utilisateur compteConnecte;

	public MenuPrincipal() {
	}

	@Override
	public void start(Stage primaryStage){
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Menu Principal");
		afficheTable();
		
	}

	public void afficheTable() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MenuPrincipal.class.getResource("vue/MenuPrincipal.fxml"));
			AnchorPane tablesOverview = (AnchorPane) loader.load();
			Scene scene = new Scene(tablesOverview);
			primaryStage.setScene(scene);
			primaryStage.show();

			
			
			 

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public boolean fenetreConnection(Utilisateur compte) throws ClassNotFoundException, SQLException {
		try {
			// Charge le fichier fxml et l'ouvre en pop-up
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MenuPrincipal.class.getResource("vue/Connection.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Crée une nouvelle page
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Gestion des fournisseurs");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Définition du controleur pour la fenetre
			ConnexionProfil controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setCompte(compte);

			// Affiche la page et attend que l'utilisateur la ferme.
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}


	public Stage getPrimaryStage() {
		return primaryStage;
	}
	public void setPrimarystage(Stage stage) {
		primaryStage = stage;
		primaryStage.show();
	}


	public static void main(String[] args) {
		arg = args;
		launch(args);
		
	}
}
