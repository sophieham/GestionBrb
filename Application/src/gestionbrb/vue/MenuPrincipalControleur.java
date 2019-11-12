package gestionbrb.vue;

import java.sql.SQLException;

import gestionbrb.DemarrerCommande;
import gestionbrb.MenuPrincipal;
import gestionbrb.controleur.FonctionsControleurs;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MenuPrincipalControleur extends FonctionsControleurs {
	private MenuPrincipal princip;
	

	@FXML
	public void fenetreNouvelleCommande() throws ClassNotFoundException, SQLException {
		
		try {
			princip.stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DemarrerCommande comm = new DemarrerCommande();
		//DemarrerCommande.main(princip.arg);
	}

	@FXML
	public void fenetreStock() throws ClassNotFoundException, SQLException {

	}

	@FXML
	public void fenetreCarte() throws ClassNotFoundException, SQLException {

	}

	@FXML
	public void fenetreAdministration() throws ClassNotFoundException, SQLException {

	}

	@FXML
	public void fenetreParamètres() throws ClassNotFoundException, SQLException {

	}

	public void setMainApp(MenuPrincipal menuPrincipal) {
		// TODO Auto-generated method stub
		princip = menuPrincipal;
	}

}
