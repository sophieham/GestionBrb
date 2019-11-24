package gestionbrb.controleur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import gestionbrb.Tables;
import gestionbrb.model.Table;
import gestionbrb.util.bddUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * 
 * @author Sophie
 *
 */
public class TablesControleur {
	@FXML
	private TableView<Table> tableTable;
	@FXML
	private TableColumn<Table, Number> colonneNoTable;
	@FXML
	private TableColumn<Table, Number> colonneNbCouvertsMin;
	@FXML
	private TableColumn<Table, Number> colonneNbCouvertsMax;

	@FXML
	private Label champNoTable;
	@FXML
	private Label champNbCouvertMax;
	@FXML
	private Label champNbCouvertMin;
	// Reference to the main application.
	private Tables mainApp;
	private AdministrationControleur parent;

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

		colonneNoTable.setCellValueFactory(cellData -> cellData.getValue().NoTableProperty());
		colonneNbCouvertsMax.setCellValueFactory(cellData -> cellData.getValue().nbCouvertsMaxProperty());
		colonneNbCouvertsMin.setCellValueFactory(cellData -> cellData.getValue().nbCouvertsMinProperty());
	}

	/**
	 * @param mainApp
	 */
	public void setMainApp(Tables mainApp) {
		this.mainApp = mainApp;

		tableTable.setItems(Tables.getTableData());
	}
	
	/**
	 * Appelé quand l'utilisateur clique sur le bouton modifier la table. Ouvre une
	 * nouvelle page pour effectuer la modification
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void ajoutTable() throws ClassNotFoundException, SQLException {
        Table tempTable = new Table();
        boolean okClicked = mainApp.fenetreModification(tempTable);
        if (okClicked) {
				Connection conn = bddUtil.dbConnect();
				PreparedStatement calendrierDB = conn.prepareStatement
						("INSERT INTO `tables` (`idTable`, `NoTable`, `nbCouverts_min`, `nbCouverts_max`, `idReservation`) VALUES (NULL, ?, ?, ?, NULL)");
				calendrierDB.setInt(1, tempTable.getNoTable());
				calendrierDB.setInt(2, tempTable.getNbCouvertsMin());
				calendrierDB.setInt(3, tempTable.getNbCouvertsMax());
				calendrierDB.execute();
				refresh();
				FonctionsControleurs.alerteInfo("Ajout éffectué", null, "Les informations ont été ajoutées avec succès!");
			}
	}

	/** 
	 * Rafraichit les colonnes après un ajout, une modification ou une suppression d'éléments.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private void refresh() throws ClassNotFoundException, SQLException {
		Tables.getTableData().clear();
		Connection conn = bddUtil.dbConnect();
		ResultSet tableDB = conn.createStatement().executeQuery("SELECT * FROM tables");
		while(tableDB.next()) {
			Tables.getTableData().add(new Table (tableDB.getInt(1), tableDB.getInt(2),tableDB.getInt(3),tableDB.getInt(4),tableDB.getInt(5)));
			tableTable.setItems(Tables.getTableData());
		}
		
	}
	/**
	 * Appellé quand l'utilisateur clique sur le bouton supprimer
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void supprimerTable() throws ClassNotFoundException {
		Table selectedTable = tableTable.getSelectionModel().getSelectedItem();
		int selectedIndex = tableTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			try {
			Connection conn = bddUtil.dbConnect();
			PreparedStatement tables = conn.prepareStatement("DELETE FROM `tables` WHERE idTable=?");
			tables.setInt(1, (selectedTable.getIdTable()));
			tables.execute();
			refresh();
			FonctionsControleurs.alerteInfo("Suppression réussie", null, "La table "+selectedTable.getNoTable()+" vient d'être supprimée!");
			conn.close();
			tables.close();
			}
			catch(SQLException e) {
				System.out.println("Erreur dans le code sql"+e);
			}
			
		

		} else {
			// Si rien n'est séléctionné
			FonctionsControleurs.alerteAttention("Aucune sélection", "Aucune table de sélectionnée!",
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
				bddUtil.dbQueryExecute("UPDATE `tables` SET " 
						+ "`NoTable` = '"+selectedTable.getNoTable()+"', "
						+ "`nbCouverts_Min` = '" +selectedTable.getNbCouvertsMin()+ "', "
						+ "`nbCouverts_Max` = '" +selectedTable.getNbCouvertsMax()+ "', "
						+ "`idReservation` = NULL WHERE `idTable`="+selectedTable.getIdTable()+";");

				refresh();
				FonctionsControleurs.alerteInfo("Modification éffectuée", null, "Les informations ont été modifiées avec succès!");
			}

		} else {
			// Si rien n'est selectionné
			FonctionsControleurs.alerteAttention("Aucune sélection", "Aucune réservation de sélectionnée!",
					"Selectionnez une réservation pour pouvoir la modifier");
		}
	}

	public void setParent(AdministrationControleur administrationControleur) {
		// TODO Auto-generated method stub
		this.parent = administrationControleur;
		tableTable.setItems(Tables.getTableData());
	}

}
