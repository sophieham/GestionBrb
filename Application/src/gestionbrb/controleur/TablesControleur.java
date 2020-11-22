package gestionbrb.controleur;

import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import gestionbrb.Connexion;
import gestionbrb.DAO.DAOTables;
import gestionbrb.DAO.DAOUtilisateur;
import gestionbrb.model.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

// TODO: Auto-generated Javadoc
/**
 * Affiche les tables avec le différentes propriétés.
 * @author Sophie
 *
 */
public class TablesControleur {
	
	/** The tables. */
	private static ObservableList<Table> tables = FXCollections.observableArrayList();
	
	/** The table table. */
	@FXML
	private TableView<Table> tableTable;
	
	/** The colonne no table. */
	@FXML
	private TableColumn<Table, Number> colonneNoTable;
	
	/** The colonne nb couverts min. */
	@FXML
	private TableColumn<Table, Number> colonneNbCouvertsMin;
	
	/** The colonne nb couverts max. */
	@FXML
	private TableColumn<Table, Number> colonneNbCouvertsMax;

	/** The champ no table. */
	@FXML
	private Label champNoTable;
	
	/** The champ nb couvert max. */
	@FXML
	private Label champNbCouvertMax;
	
	/** The champ nb couvert min. */
	@FXML
	private Label champNbCouvertMin;
	
	/** The parent. */
	@SuppressWarnings("unused")
	private AdministrationControleur parent;
	
	/** The ajouter. */
	@FXML
	private Button ajouter;
	
	/** The modifier. */
	@FXML
	private Button modifier;
	
	/** The supprimer. */
	@FXML
	private Button supprimer;
	
	/** The bundle. */
	@FXML
	private ResourceBundle bundle;
	
	/** The gestion table. */
	@FXML
	private Label gestionTable;
	
	/** The dao utilisateur. */
	DAOUtilisateur daoUtilisateur = new DAOUtilisateur();
	
	/** The dao tables. */
	DAOTables daoTables = new DAOTables();

	
	/**
	 * Instantiates a new tables controleur.
	 */
	public TablesControleur() {
		
	}

	/**
	 * Initialise la classe controleur avec des données récupérées dans la base de données.
	 */
	@FXML
	private void initialize() {
		try {
			String langue = daoUtilisateur.recupererLangue(ConnexionControleur.getUtilisateurConnecte().getIdUtilisateur());
			
			switch(langue) {
			case "fr":
				loadLang("fr", "FR");
				break;
			case "en":
				loadLang("en", "US");
				break;
			case "zh":
				loadLang("zh", "CN");
				break;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			colonneNoTable.setCellValueFactory(cellData -> cellData.getValue().NoTableProperty());
			colonneNbCouvertsMax.setCellValueFactory(cellData -> cellData.getValue().nbCouvertsMaxProperty());
			colonneNbCouvertsMin.setCellValueFactory(cellData -> cellData.getValue().nbCouvertsMinProperty());
			tableTable.setItems(daoTables.afficher());
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue","Dé– ails: "+e);
			e.printStackTrace();
		}
	}

	/**
	 * Load lang.
	 *
	 * @param lang the lang
	 * @param LANG the lang
	 */
	private void loadLang(String lang, String LANG) {
		Locale locale = new Locale(lang, LANG);  
		
		ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_"+lang, locale);
		ajouter.setText(bundle.getString("Ajouter"));
		modifier.setText(bundle.getString("Modifier"));
		supprimer.setText(bundle.getString("Supprimer"));
		colonneNoTable.setText(bundle.getString("Table"));
		colonneNbCouvertsMin.setText(bundle.getString("CouvMin"));
		colonneNbCouvertsMax.setText(bundle.getString("CouvMax"));
		gestionTable.setText(bundle.getString("gestionTable"));

		
	}
	
	/**
	 * Appelé quand l'utilisateur clique sur le bouton modifier la table. Ouvre une
	 * nouvelle page pour effectuer la modification
	 */
	@FXML
	private void ajoutTable() {
        Table tempTable = new Table();
        try {
        boolean okClicked = fenetreModification(tempTable);
        if (okClicked) {
        		daoTables.ajouter(tempTable);
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
	 */
	private void refresh() {
		tableTable.getItems().clear();
		tables.clear();
		try {
			tableTable.setItems(daoTables.afficher());
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur!", "Erreur d'éxecution", "Détails: "+e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Appellé quand l'utilisateur clique sur le bouton supprimer.
	 *
	 * @throws ClassNotFoundException the class not found exception
	 */
	@FXML
	private void supprimerTable() throws ClassNotFoundException {
		Table selectedTable = tableTable.getSelectionModel().getSelectedItem();
		int selectedIndex = tableTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			try {
			daoTables.supprimer(selectedTable);
			refresh();
			FonctionsControleurs.alerteInfo("Suppression réussie", null, "La table "+selectedTable.getNoTable()+" vient d'être supprimée!");
			}
			catch(SQLException e) {
				FonctionsControleurs.alerteErreur("Erreur!", "Erreur d'éxecution", "Détails: "+e);
				e.printStackTrace();
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
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
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
					FonctionsControleurs.alerteInfo("Modification éffectuée", null, "Les informations ont été modifiées avec succès!");
				} catch (Exception e) {
					FonctionsControleurs.alerteErreur("Erreur!", "Erreur d'éxecution", "Détails: "+e);
					e.printStackTrace();
				}
			}

		} else {
			// Si rien n'est selectionné
			FonctionsControleurs.alerteAttention("Aucune sélection", "Aucune table de sélectionnée!",
					"Selectionnez une table pour pouvoir la modifier");
		}
	}
	
	/**
	 * Fenetre modification.
	 *
	 * @param table the table
	 * @return true, if successful
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	public boolean fenetreModification(Table table) throws ClassNotFoundException, SQLException {
		try {
			Locale locale = new Locale("fr", "FR");

			ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_fr", locale);
			// Charge le fichier fxml et l'ouvre en pop-up
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/ModifierTables.fxml"), bundle);
			AnchorPane page = (AnchorPane) loader.load();

			// Crée une nouvelle page
			Stage dialogStage = new Stage();
			dialogStage.setResizable(false);
			dialogStage.setTitle("Gestion des tables");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			dialogStage.getIcons().add(new Image(
	          	      Connexion.class.getResourceAsStream( "ico.png" ))); 

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

	/**
	 * Sets the parent.
	 *
	 * @param administrationControleur the new parent
	 */
	public void setParent(AdministrationControleur administrationControleur) {
		// TODO Auto-generated method stub
		this.parent = administrationControleur;
	}

	/**
	 * Gets the table data.
	 *
	 * @return the table data
	 */
	public static ObservableList<Table> getTableData() {
		return tables;
	}

	/**
	 * Sets the table data.
	 *
	 * @param tables the new table data
	 */
	public static void setTableData(ObservableList<Table> tables) {
		TablesControleur.tables = tables;
	}

}