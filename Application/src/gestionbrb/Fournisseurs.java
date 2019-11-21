package gestionbrb;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import gestionbrb.controleur.FournisseursControleur;
import gestionbrb.controleur.ModifierFournisseurControleur;
import gestionbrb.model.Fournisseur;
import gestionbrb.util.bddUtil;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * 
 * @author Roman
 *
 */
public class Fournisseurs extends Application {
	private Stage primaryStage;
	private static ObservableList<Fournisseur> fournisseurs = FXCollections.observableArrayList();

	
	public Fournisseurs() {
		try {
			Connection conn = bddUtil.dbConnect();
			ResultSet rs = conn.createStatement().executeQuery("select * from fournisseur");
			while (rs.next()) {
				fournisseurs.add(new Fournisseur(rs.getInt("idFournisseur"),  rs.getString("nom"), rs.getInt("numTel"), rs.getString("adresseMail"), rs.getString("adresseDepot"), rs.getInt("cpDepot"),  rs.getString("villeDepot")));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public static ObservableList<Fournisseur> getTableData() {
		return fournisseurs;
	}
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Administration -- Fournisseurs");
		afficheFournisseur();
	}

	/**
	 * Appeler pour afficher la liste des fournisseurs
	 **/
	public void afficheFournisseur() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Fournisseurs.class.getResource("vue/GestionFournisseurs.fxml"));
			AnchorPane tablesOverview = (AnchorPane) loader.load();

			Scene scene = new Scene(tablesOverview);
			primaryStage.setScene(scene);
			primaryStage.show();

			FournisseursControleur controller = loader.getController();
			controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * appelé pour afficher la fenetre de modification/ajout d'un fournisseur
	 * 
	 * @param fournisseur Fournisseur à ajouter/modifier
	 * @see ajoutFournisseur
	 * @see modifierFournisseur
	 * 
	 **/
	public boolean fenetreModification(Fournisseur fournisseur) throws ClassNotFoundException, SQLException {
		try {
			// Charge le fichier fxml et l'ouvre en pop-up
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Fournisseurs.class.getResource("vue/ModifierFournisseur.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Crée une nouvelle page
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Gestion des fournisseurs");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Définition du controleur pour la fenetre
			ModifierFournisseurControleur controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setFournisseur(fournisseur);

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

	public static void main(String[] args) {
		launch(args);
	}
}
