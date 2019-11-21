package gestionbrb;

import java.io.IOException;
import java.sql.SQLException;

import gestionbrb.controleur.AdministrationControleur;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * 
 * @author Roman
 *
 */
public class Administration extends Application {
	private Stage primaryStage;

	public Administration() {
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Menu Principal");
		afficheTable();
	}

	public void afficheTable() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Fournisseurs.class.getResource("vue/Administration.fxml"));
			AnchorPane tablesOverview = (AnchorPane) loader.load();

			Scene scene = new Scene(tablesOverview);
			primaryStage.setScene(scene);
			primaryStage.show();

			//AdministrationControleur controller = loader.getController();
			//controller.setMainApp(this);
			 

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@FXML
	public void fenetreHistoriqueCommandes() throws ClassNotFoundException, SQLException {
		
	}
	@FXML
	public void fenetreGererFournisseurs() throws ClassNotFoundException, SQLException {
		
	}
	@FXML
	public void fenetreGererTables() throws ClassNotFoundException, SQLException {

	}
	@FXML
	public void fenetreGererUtilisateurs() throws ClassNotFoundException, SQLException {

	}
	@FXML
	public void fenetrePlatsIngredients() throws ClassNotFoundException, SQLException {

	}
	@FXML
	public void fenetreRetour() throws ClassNotFoundException, SQLException {

	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
