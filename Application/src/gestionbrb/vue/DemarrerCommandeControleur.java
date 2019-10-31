package gestionbrb.vue;

import java.sql.SQLException;

import gestionbrb.ControleurFonctions;
import gestionbrb.DemarrerCommande;
import gestionbrb.util.bddUtil;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class DemarrerCommandeControleur extends ControleurFonctions {
	@FXML
	private TextField champNom;
	@FXML
	private TextField champPrenom;
	@FXML
	private TextField champNumTel;
	@FXML
	private DatePicker champDate;
	@FXML
	private TextField champHeure;
	@FXML
	private TextField champNbCouverts;
	@FXML
	private TextField champDemandeSpe;

	// Reference to the main application.
	private DemarrerCommande mainApp;
	private boolean okClicked = false;

	public DemarrerCommandeControleur() {
	}

	@FXML
	private void initialize() {
	}

	public void setMainApp(DemarrerCommande mainApp) {
		this.mainApp = mainApp;
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Appellé quand l'utilisateur clique sur réserver
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void actionModifier() throws ClassNotFoundException, SQLException {
		if (estValide()) {
			// manque le pattern du numero de tel & celui de l'heure + le numero de la table
			// qui recevera la reservation
			bddUtil.dbQueryExecute("INSERT INTO `calendrier` (`idReservation`, `nom`, `prenom`, `numeroTel`, `dateReservation`, `HeureReservation`, `nbCouverts`, `demandeSpe`, `idTable`) "
							+ "VALUES (NULL, '" + champNom.getText() + "', '" + champPrenom.getText() + "', NULL, '"+ champDate.getValue() + "', '" + champHeure.getText() + "', '" + champNbCouverts.getText()+ "', NULL, NULL);");
			champNom.clear();
			champPrenom.clear();
			champNumTel.clear();
			champDate.setValue(null);
			champHeure.clear();
			champNbCouverts.clear();
			champDemandeSpe.clear();

			okClicked = true;
		}
	}
}