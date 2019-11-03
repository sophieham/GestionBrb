package gestionbrb.vue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import gestionbrb.controleur.FonctionsControleurs;
import gestionbrb.model.Table;
import gestionbrb.util.bddUtil;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifierTablesControleur extends FonctionsControleurs {

	@FXML
	private TextField champNbCouvertsMin;
	@FXML
	private TextField champNbCouvertsMax;
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
			champNbCouvertsMin.setText(rs.getString("NbCouverts_Min"));
			champNbCouvertsMax.setText(rs.getString("NbCouverts_Max"));

		}
	}

	/**
	 * @return true si le bouton a modifié a été appuyé, faux sinon
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Appellé quand l'utilisateur appuie sur "modifier"
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws NumberFormatException
	 */
	@FXML
	private void actionAjouter() throws NumberFormatException, ClassNotFoundException, SQLException {
		if (estValide()) {
			Connection conn = bddUtil.dbConnect();
			PreparedStatement pstmt = conn.prepareStatement
					("INSERT INTO `tables` (`idTable`, `nbCouverts_min`, `nbCouverts_max`, `idReservation`) VALUES (NULL, ?, ?, NULL)");
			pstmt.setInt(1, Integer.parseInt(champNbCouvertsMin.getText()));
			pstmt.setInt(2,Integer.parseInt(champNbCouvertsMax.getText()));
			pstmt.execute();
			okClicked = true;
			dialogStage.close();
			pstmt.close();
		}
	}
	
	@FXML
	private void actionModifier() throws NumberFormatException, ClassNotFoundException, SQLException {
		if (estValide()) {
			bddUtil.dbQueryExecute("UPDATE `table` SET " + "`nbCouvertMin` = '" + champNbCouvertsMin.getText() + "', "
					+ "`nbCouvertMax` = '" + champNbCouvertsMax.getText() + "', " + "`estOccupe` = '" + champEstOccupe.getText());
			okClicked = true;
			dialogStage.close();
		}
	}

	/**
	 * Appellé quand le bouton annuler est appuyé. Ferme la page sans sauvegarder.
	 */
	@FXML
	private void actionAnnuler() {
		dialogStage.close();
	}

	/**
	 * Vérifie si la saisie est conforme aux données requises
	 * 
	 * @return true si la saisie est bien conforme
	 */
	public boolean estValide() {
		String erreurMsg = "";

		if (champNbCouvertsMax.getText() == null || champNbCouvertsMax.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le nombre de couvert maximum\n";
		}
		if (champNbCouvertsMin.getText() == null || champNbCouvertsMin.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le nombre de couvert minimum\n";

		}

		if (erreurMsg.length() == 0) {
			return true;
		} else {
			// Affiche un message d'erreur
			alerteErreur("Entrée incorrecte", "Corrigez les erreurs suivantes pour pouvoir modifier la reservation",
					erreurMsg);

			return false;
		}
	}

}
