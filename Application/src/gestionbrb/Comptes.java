package gestionbrb;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import gestionbrb.model.Compte;
import gestionbrb.util.bddUtil;
import gestionbrb.vue.ComptesControleur;
import gestionbrb.vue.ModifierCompteControleur;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Comptes extends Application {
	private Stage primaryStage;
	private static ObservableList<Compte> comptes = FXCollections.observableArrayList();

	public Comptes() {
		try {
			Connection conn = bddUtil.dbConnect();
			ResultSet rs = conn.createStatement().executeQuery("select * from comptes");
			while (rs.next()) {
				comptes.add(new Compte(rs.getString("idCompte"), rs.getString("pass"), rs.getString("nom"), rs.getString("prenom"), rs.getInt("typeCompte")) {
				});
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public static ObservableList<Compte> getTableData() {
		return comptes;
	}

	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Administration -- Comptes");
		afficheCompte();
	}

	public void afficheCompte() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Comptes.class.getResource("vue/GestionComptes.fxml"));
			AnchorPane tablesOverview = (AnchorPane) loader.load();

			Scene scene = new Scene(tablesOverview);
			primaryStage.setScene(scene);
			primaryStage.show();

			ComptesControleur controller = loader.getController();
			controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean fenetreModification(Compte compte) throws ClassNotFoundException, SQLException {
		try {
			// Charge le fichier fxml et l'ouvre en pop-up
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Comptes.class.getResource("vue/ModifierComptes.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Crée une nouvelle page
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Gestion des comptes");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Définition du controleur pour la fenetre
			ModifierCompteControleur controller = loader.getController();
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

	public static void main(String[] args) {
		launch(args);
	}
}
