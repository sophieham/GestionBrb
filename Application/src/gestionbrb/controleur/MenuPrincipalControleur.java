package gestionbrb.controleur;

import java.sql.SQLException;

import gestionbrb.MenuPrincipal;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MenuPrincipalControleur extends FonctionsControleurs {
	private MenuPrincipal princip;
	

	@FXML
	public void fenetreNouvelleCommande() throws ClassNotFoundException, SQLException {
		
	}

	@FXML
	public void fenetreStock() throws ClassNotFoundException, SQLException {
	}

	@FXML
	public void fenetreCarte() throws ClassNotFoundException, SQLException {
		
	}


	@FXML
	public void fenetreParam�tres() throws ClassNotFoundException, SQLException {

	}

	
	public void fenetreAdministration() {
		Stage primaryStage = princip.getPrimaryStage();
		try {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MenuPrincipal.class.getResource("vue/Administration.fxml"));
		AnchorPane tablesOverview = (AnchorPane) loader.load();
			Scene scene = new Scene(tablesOverview);
			primaryStage.setScene(scene);
			princip.setPrimarystage(primaryStage);
			
		} catch (Exception e) {
			alerteErreur("Erreur d'�xecution", null, "D�tails: "+e);
		}

		
	}

	public void setMainApp(MenuPrincipal menuPrincipal) {
		// TODO Auto-generated method stub
		princip = menuPrincipal;
	}

}
