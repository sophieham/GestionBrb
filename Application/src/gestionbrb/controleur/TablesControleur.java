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

public class TablesControleur extends FonctionsControleurs {
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
	 * Appel� quand l'utilisateur clique sur le bouton modifier la table. Ouvre une
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
				PreparedStatement pstmt = conn.prepareStatement
						("INSERT INTO `tables` (`idTable`, `NoTable`, `nbCouverts_min`, `nbCouverts_max`, `idReservation`) VALUES (NULL, ?, ?, ?, NULL)");
				pstmt.setInt(1, tempTable.getNoTable());
				pstmt.setInt(2, tempTable.getNbCouvertsMin());
				pstmt.setInt(3, tempTable.getNbCouvertsMax());
				pstmt.execute();
				refresh();
				alerteInfo("Ajout �ffectu�", null, "Les informations ont �t� ajout�es avec succ�s!");
			}
	}

	/** 
	 * Rafraichit les colonnes apr�s un ajout, une modification ou une suppression d'�l�ments.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private void refresh() throws ClassNotFoundException, SQLException {
		Tables.getTableData().clear();
		Connection conn = bddUtil.dbConnect();
		ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM tables");
		while(rs.next()) {
			Tables.getTableData().add(new Table (rs.getInt(1), rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getInt(5)));
			tableTable.setItems(Tables.getTableData());
		}
		
	}
	/**
	 * Appell� quand l'utilisateur clique sur le bouton supprimer
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
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM `tables` WHERE idTable=?");
			pstmt.setInt(1, (selectedTable.getIdTable()));
			pstmt.execute();
			refresh();
			alerteInfo("Suppression r�ussie", null, "La table "+selectedTable.getNoTable()+" vient d'�tre supprim�e!");
			conn.close();
			pstmt.close();
			}
			catch(SQLException e) {
				System.out.println("Erreur dans le code sql"+e);
			}
			
		

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
				bddUtil.dbQueryExecute("UPDATE `tables` SET " 
						+ "`NoTable` = '"+selectedTable.getNoTable()+"', "
						+ "`nbCouverts_Min` = '" +selectedTable.getNbCouvertsMin()+ "', "
						+ "`nbCouverts_Max` = '" +selectedTable.getNbCouvertsMax()+ "', "
						+ "`idReservation` = NULL WHERE `idTable`="+selectedTable.getIdTable()+";");

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
