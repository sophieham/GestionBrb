package gestionbrb.vue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import gestionbrb.Tables;
import gestionbrb.controleur.FonctionsControleurs;
import gestionbrb.model.Table;
import gestionbrb.util.bddUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TablesControleur extends FonctionsControleurs {
	@FXML
	private TableView<Table> tableTable;
	@FXML
	private TableColumn<Table, Number> colonneIdTable;
	@FXML
	private TableColumn<Table, Number> colonneNbCouvertsMin;
	@FXML
	private TableColumn<Table, Number> colonneNbCouvertsMax;

	@FXML
	private Label champID;
	@FXML
	private Label champNbCouvertMax;
	@FXML
	private Label champNbCouvertMin;
	// Reference to the main application.
	private Tables mainApp;

	/**
	 * The constructor. The constructor is called before the initialize() method.
	 */
	public TablesControleur() {
	}

	/**
	 * Initialise la classe controleur avec les donn�es par d�faut du tableau
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */

	@FXML
	private void initialize() throws ClassNotFoundException, SQLException {

		colonneIdTable.setCellValueFactory(cellData -> cellData.getValue().idTableProperty());
		colonneNbCouvertsMax.setCellValueFactory(cellData -> cellData.getValue().nbCouvertsMaxProperty());
		colonneNbCouvertsMin.setCellValueFactory(cellData -> cellData.getValue().nbCouvertsMinProperty());
	}

	/**
	 * @param mainApp
	 */
	public void setMainApp(Tables mainApp) {
		this.mainApp = mainApp;

		tableTable.setItems(mainApp.getTableData());
	}
	
	/**
	 * Appel� quand l'utilisateur clique sur le bouton modifier la table. Ouvre une
	 * nouvelle page pour effectuer la modification
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void ajoutTable() throws ClassNotFoundException, SQLException {
		boolean okClicked = mainApp.fenetreModification(null);
			if (okClicked) {
				refresh();
				alerteInfo("Ajout �ffectu�", null, "Les informations ont �t� ajout�es avec succ�s!");
			}
	}

	private void refresh() throws ClassNotFoundException, SQLException {
		mainApp.getTableData().clear();
		Connection conn = bddUtil.dbConnect();
		ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM tables");
		while(rs.next()) {
			mainApp.getTableData().add(new Table (rs.getInt("idTable"),rs.getInt("nbCouverts_Min"),rs.getInt("nbCouverts_Max"),false));
			tableTable.setItems(mainApp.getTableData());
			colonneIdTable.setText(rs.getString("idTable"));
			colonneNbCouvertsMin.setText(rs.getString("nbCouverts_Min"));
			colonneNbCouvertsMin.setText(rs.getString("nbCouverts_Max"));
		}
		
	}
	/**
	 * Appell� quand l'utilisateur clique sur le bouton supprimer
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void supprimerTable() throws ClassNotFoundException, SQLException {
		Table selectedTable = tableTable.getSelectionModel().getSelectedItem();
		int selectedIndex = tableTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			Connection conn = bddUtil.dbConnect();
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM `table` WHERE idTable=?");
			pstmt.setInt(1, (selectedTable.getIdTable()));
			pstmt.execute();
			pstmt.close();
			tableTable.getItems().remove(selectedIndex);

		} else {
			// Si rien n'est s�l�ctionn�
			alerteAttention("Aucune s�lection", "Aucune table de s�lectionn�e!",
					"Selectionnez une table pour pouvoir la supprimer");
		}
	}

	/**
	 * Appel� quand l'utilisateur clique sur le bouton modifier la table. Ouvre une
	 * nouvelle page pour effectuer la modification
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void modifierTable() throws ClassNotFoundException, SQLException {
		Table selectedTable = tableTable.getSelectionModel().getSelectedItem();
		if (selectedTable != null) {
			boolean okClicked = mainApp.fenetreModification(selectedTable);
			if (okClicked) {
				refresh();
				alerteInfo("Modification �ffectu�e", null, "Les informations ont �t� modifi�es avec succ�s!");
			}

		} else {
			// Si rien n'est selectionn�
			alerteAttention("Aucune s�lection", "Aucune r�servation de s�lectionn�e!",
					"Selectionnez une r�servation pour pouvoir la modifier");
		}
	}

}
