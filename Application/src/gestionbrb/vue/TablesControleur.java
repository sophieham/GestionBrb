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
	private TableColumn<Table, Number> colonneNbCouvertMin;
	@FXML
	private TableColumn<Table, Number> colonneNbCouvertMax;
	@FXML
	private TableColumn<Table, Number> colonneNbCouverts;

	@FXML
	private Label champID;
	@FXML
	private Label champNbCouvertMax;
	@FXML
	private Label champNbCouvertMin;
	@FXML
	private Label champNbCouverts;

	// Reference to the main application.
	private Tables mainApp;

	/**
	 * The constructor. The constructor is called before the initialize() method.
	 */
	public TablesControleur() {
	}

	/**
	 * Initialise la classe controleur avec les données par défaut du tableau
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */

	@FXML
	private void initialize() throws ClassNotFoundException, SQLException {

		colonneIdTable.setCellValueFactory(cellData -> cellData.getValue().idTableProperty());
		colonneNbCouvertMax.setCellValueFactory(cellData -> cellData.getValue().nbCouvertsMaxProperty());
		colonneNbCouvertMin.setCellValueFactory(cellData -> cellData.getValue().nbCouvertsMinProperty());
		colonneNbCouverts.setCellValueFactory(cellData -> cellData.getValue().nbCouvertsProperty());

	}

	/**
	 * @param mainApp
	 */
	public void setMainApp(Tables mainApp) {
		this.mainApp = mainApp;

	}

	/**
	 * @param reservation the reservation or null
	 * @throws SQLException
	 */

	private void detailsTable(Table table) throws SQLException, ClassNotFoundException {
		Connection conn = bddUtil.dbConnect();
		ResultSet rs = conn.createStatement().executeQuery("select * from table");
		try {

			if (table != null) {
				while (rs.next()) {
					champID.setText(Integer.toString(table.getIdTable()));
					champNbCouvertMax.setText(Integer.toString(table.getNbCouvertsMax()));
					champNbCouvertMin.setText(Integer.toString(table.getNbCouvertsMin()));
					champNbCouverts.setText(Integer.toString(table.getNbCouverts()));
				}
			} else {
				champID.setText("");
				champNbCouvertMax.setText("");
				champNbCouvertMin.setText("");
				champNbCouverts.setText("");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			conn.close();
			rs.close();
		}
	}
	

	/**
	 * Appellé quand l'utilisateur clique sur le bouton supprimer
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
			// Si rien n'est séléctionné
			alerteAttention("Aucune sélection", "Aucune table de sélectionnée!",
					"Selectionnez une table pour pouvoir la supprimer");
		}
	}

	/**
	 * Appelé quand l'utilisateur clique sur le bouton modifier la table. Ouvre une
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
				tableTable.getItems().clear();
				Connection conn = bddUtil.dbConnect();
				ResultSet rs = conn.createStatement().executeQuery("select * from table");
				while (rs.next()) {
					tableTable.getItems().add(
							new Table(rs.getInt("idTable"), rs.getInt("nbCouvertMax"), rs.getBoolean("estOccupe")));
				}
				detailsTable(null);
				alerteInfo("Modification éffectuée", null, "Les informations ont été modifiées avec succès!");
			}

		} else {
			// Si rien n'est selectionné
			alerteAttention("Aucune sélection", "Aucune réservation de sélectionnée!",
					"Selectionnez une réservation pour pouvoir la modifier");
		}
	}
	

	/**
	 * Affiche uniquement les tables libres
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@FXML
	private void rechercherTable() throws ClassNotFoundException, SQLException {
		tableTable.getItems().clear();
		//String couverts = champNbCouvertMax.getText();
		detailsTable(null);
		Connection conn = bddUtil.dbConnect();
		PreparedStatement stmt = conn.prepareStatement("select * from calendrier where estOccupe =  ?");
		stmt.setString(1, "false");
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			tableTable.getItems()
					.add(new Table(rs.getInt("idTable"), rs.getInt("nbCouvertMax"),
							rs.getBoolean("estOccupe")));
		}

		conn.close();
		rs.close();
	}

	/**
	 * Appellé quand l'utilisateur appuie sur le bouton Afficher tout. Affiche
	 * toutes les tables quelque soit leur occupation ou le nombre max de couverts (annule la recherche)
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@FXML
	private void afficherTout() throws ClassNotFoundException, SQLException {
		tableTable.getItems().clear();
		Connection conn = bddUtil.dbConnect();
		ResultSet rs = conn.createStatement().executeQuery("select * from calendrier");
		while (rs.next()) {
			tableTable.getItems()
					.add(new Table(rs.getInt("idTable"), rs.getInt("nbCouvertMax"),
							rs.getBoolean("estOccupe")));
		}
		detailsTable(null);
	}

}
