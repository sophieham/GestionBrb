package gestionbrb.vue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import gestionbrb.controleur.FonctionsControleurs;
import gestionbrb.model.Table;
import gestionbrb.util.bddUtil;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GestionTableControleur extends FonctionsControleurs {

	@FXML
	private TextField champNbCouvertMin;
	@FXML
	private TextField champNbCouvertMax;
	@FXML
	private TextField champNbCouverts;
	@FXML
	private TextField champEstOccupe;

	private Stage dialogStage;
	private Table table;
	private boolean okClicked = false;

	@FXML
	private void initialize() {
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * 
	 * @param reservation
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void setTable(Table table) throws SQLException, ClassNotFoundException {
		this.table = table;
		Connection conn = bddUtil.dbConnect();
		ResultSet rs = conn.createStatement().executeQuery("select * from tables");
		while (rs.next()) {
			champNbCouvertMin.setText(rs.getString("NbCouverts_min"));
			champNbCouvertMax.setText(rs.getString("NbCouverts_max"));

		}
	}

	/**
	 * @return true si le bouton a �t� appuy�, faux sinon
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Appel� quand l'utilisateur appuie sur "ajouter"
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws NumberFormatException
	 */
	@FXML
	private void actionAjouter() throws NumberFormatException, ClassNotFoundException, SQLException {
		if (estValide()) {
			bddUtil.dbQueryExecute(
					"INSERT INTO `table` (`idTable`, `nbCouvertMax`, `nbCouvertMin`, `nbCouverts`, `estOccupe`) "
							+ "VALUES (NULL, '" + champNbCouvertMax.getText() + "', '" + champNbCouvertMin.getText()
							+ "','" + champNbCouverts.getText() + "' , '" + champEstOccupe.getText() + "');");
			alerteInfo("Reservation enregistr�e!", "", "La reservation � bien �t� enregistr�e!");
			champNbCouvertMax.clear();
			champEstOccupe.clear();
			champNbCouvertMin.clear();
			okClicked = true;
			dialogStage.close();
		}
	}

	/**
	 * Appel� quand le bouton annuler est appuy�. Ferme la page sans sauvegarder.
	 */
	@FXML
	private void actionAnnuler() {
		dialogStage.close();
	}

	/**
	 * V�rifie si la saisie est conforme aux donn�es requises
	 * 
	 * @return true si la saisie est bien conforme
	 */
	public boolean estValide() {
		String erreurMsg = "";

		if (champNbCouvertMax.getText() == null || champNbCouvertMax.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le nombre de couvert maximum\n";
		}
		if (champNbCouvertMin.getText() == null || champNbCouvertMin.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le nombre de couvert minimum\n";

		}
		if (champNbCouverts.getText() == null || champNbCouverts.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le nombre de couverts \n";

		}
		if (champEstOccupe.getText() == null || champEstOccupe.getText().length() == 0) {
			erreurMsg += "Veuillez remplir si la table est occup� ou non\n";

		}

		if (erreurMsg.length() == 0) {
			return true;
		} else {
			// Affiche un message d'erreur
			alerteErreur("Entr�e incorrecte", "Corrigez les erreurs suivantes pour pouvoir ajouter la reservation",
					erreurMsg);

			return false;
		}
	}

}
