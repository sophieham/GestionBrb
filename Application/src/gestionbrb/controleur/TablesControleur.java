package gestionbrb.controleur;

import java.sql.SQLException;

import gestionbrb.DAO.DAOTables;
import gestionbrb.model.Table;
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
	@SuppressWarnings("unused")
	private AdministrationControleur parent;
	
	DAOTables daoTables = new DAOTables();

	
	public TablesControleur() {
		
	}

	/**
	 * Initialise la classe controleur avec les donn�es par d�faut du tableau
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void initialize() {
		try {
			colonneNoTable.setCellValueFactory(cellData -> cellData.getValue().NoTableProperty());
			colonneNbCouvertsMax.setCellValueFactory(cellData -> cellData.getValue().nbCouvertsMaxProperty());
			colonneNbCouvertsMin.setCellValueFactory(cellData -> cellData.getValue().nbCouvertsMinProperty());
			tableTable.setItems(daoTables.afficher());
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue","D�tails: "+e);
			e.printStackTrace();
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
	private void ajoutTable() {
        Table tempTable = new Table();
        try {
        boolean okClicked = fenetreModification(tempTable);
        if (okClicked) {
        		daoTables.ajouter(tempTable);
				refresh();
				FonctionsControleurs.alerteInfo("Ajout �ffectu�", null, "Les informations ont �t� ajout�es avec succ�s!");
			}
        }
        catch(Exception e) {
        	FonctionsControleurs.alerteErreur("Erreur!", "Erreur d'�xecution", "D�tails: "+e);
			e.printStackTrace();
		}
	}

	/** 
	 * Rafraichit les colonnes apr�s un ajout, une modification ou une suppression d'�l�ments.
	 */
	private void refresh() {
		tableTable.getItems().clear();
		tables.clear();
		try {
			tableTable.setItems(daoTables.afficher());
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur!", "Erreur d'�xecution", "D�tails: "+e);
			e.printStackTrace();
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
			daoTables.supprimer(selectedTable);
			refresh();
			FonctionsControleurs.alerteInfo("Suppression r�ussie", null, "La table "+selectedTable.getNoTable()+" vient d'�tre supprim�e!");
			}
			catch(SQLException e) {
				FonctionsControleurs.alerteErreur("Erreur!", "Erreur d'�xecution", "D�tails: "+e);
				e.printStackTrace();
			}
			
		

		} else {
			// Si rien n'est s�l�ctionn�
			FonctionsControleurs.alerteAttention("Aucune s�lection", "Aucune table de s�lectionn�e!",
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
			boolean okClicked = fenetreModification(selectedTable);
			if (okClicked) {
				try {
					daoTables.modifier(selectedTable);
					refresh();
					FonctionsControleurs.alerteInfo("Modification �ffectu�e", null, "Les informations ont �t� modifi�es avec succ�s!");
				} catch (Exception e) {
					FonctionsControleurs.alerteErreur("Erreur!", "Erreur d'�xecution", "D�tails: "+e);
					e.printStackTrace();
				}
			}

		} else {
			// Si rien n'est selectionn�
			FonctionsControleurs.alerteAttention("Aucune s�lection", "Aucune table de s�lectionn�e!",
					"Selectionnez une table pour pouvoir la modifier");
		}
	}
	
	public boolean fenetreModification(Table table) throws ClassNotFoundException, SQLException {
		try {
			// Charge le fichier fxml et l'ouvre en pop-up
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(TablesControleur.class.getResource("../vue/ModifierTables.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Cr�e une nouvelle page
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Gestion des tables");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// D�finition du controleur pour la fenetre
			ModifierTablesControleur controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setTable(table);

			// Affiche la page et attend que l'utilisateur la ferme.
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur!", "Erreur d'�xecution", "D�tails: "+e);
			e.printStackTrace();
			return false;
		}
	}

	public void setParent(AdministrationControleur administrationControleur) {
		// TODO Auto-generated method stub
		this.parent = administrationControleur;
	}

	public static ObservableList<Table> getTableData() {
		return tables;
	}

	public static void setTableData(ObservableList<Table> tables) {
		TablesControleur.tables = tables;
	}

}
