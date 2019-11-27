package gestionbrb.controleur;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import gestionbrb.model.Table;
import gestionbrb.util.bddUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * 
 * @author Sophie
 *
 */
public class TablesControleur {
	private static ObservableList<Table> tables = FXCollections.observableArrayList();
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
	private AdministrationControleur parent;

	
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
		Connection conn = bddUtil.dbConnect();
		ResultSet rs = conn.createStatement().executeQuery("select * from tables");
		while (rs.next()) {
			tables.add(new Table(rs.getInt("idTable"), rs.getInt("noTable"), rs.getInt("nbCouverts_Min"), rs.getInt("nbCouverts_Max"), rs.getInt("occupation")));
		}
		colonneNoTable.setCellValueFactory(cellData -> cellData.getValue().NoTableProperty());
		colonneNbCouvertsMax.setCellValueFactory(cellData -> cellData.getValue().nbCouvertsMaxProperty());
		colonneNbCouvertsMin.setCellValueFactory(cellData -> cellData.getValue().nbCouvertsMinProperty());
	}

	/**
	 * Appelé quand l'utilisateur clique sur le bouton modifier la table. Ouvre une
	 * nouvelle page pour effectuer la modification
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void ajoutTable() {
        Table tempTable = new Table();
        try {
        boolean okClicked = fenetreModification(tempTable);
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
        catch(Exception e) {
        	FonctionsControleurs.alerteErreur("Erreur!", "Erreur d'éxecution", "Détails: "+e);
			e.printStackTrace();
		}
	}

	/** 
	 * Rafraichit les colonnes après un ajout, une modification ou une suppression d'éléments.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private void refresh() throws ClassNotFoundException, SQLException {
		tables.clear();
		Connection conn = bddUtil.dbConnect();
		ResultSet tableDB = conn.createStatement().executeQuery("SELECT * FROM tables");
		while(tableDB.next()) {
			tables.add(new Table (tableDB.getInt(1), tableDB.getInt(2),tableDB.getInt(3),tableDB.getInt(4),tableDB.getInt(5)));
			tableTable.setItems(tables);
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
			boolean okClicked = fenetreModification(selectedTable);
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
	
	public boolean fenetreModification(Table table) throws ClassNotFoundException, SQLException {
		try {
			// Charge le fichier fxml et l'ouvre en pop-up
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(TablesControleur.class.getResource("../vue/ModifierTables.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Crée une nouvelle page
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Gestion des tables");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Définition du controleur pour la fenetre
			ModifierTablesControleur controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setTable(table);

			// Affiche la page et attend que l'utilisateur la ferme.
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur!", "Erreur d'éxecution", "Détails: "+e);
			e.printStackTrace();
			return false;
		}
	}

	public void setParent(AdministrationControleur administrationControleur) {
		// TODO Auto-generated method stub
		this.parent = administrationControleur;
		tableTable.setItems(tables);
	}

	public static ObservableList<Table> getTableData() {
		return tables;
	}

	public static void setTableData(ObservableList<Table> tables) {
		TablesControleur.tables = tables;
	}

}
