package gestionbrb.controleur;

import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import gestionbrb.Connexion;
import gestionbrb.DAO.DAOUtilisateur;
import gestionbrb.model.Utilisateur;
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
 * G�re les utilisateurs et leurs fonctions.
 * @author Roman
 *
 */
public class UtilisateursControleur {
	
	/** The comptes. */
	private static ObservableList<Utilisateur> comptes = FXCollections.observableArrayList();
	
	/** The utilisateurs table. */
	@FXML
	private TableView<Utilisateur> utilisateursTable;
	
	/** The colonne identifiant. */
	@FXML
	private TableColumn<Utilisateur, String> colonneIdentifiant;
	
	/** The colonne nom. */
	@FXML
	private TableColumn<Utilisateur, String> colonneNom;
	
	/** The colonne prenom. */
	@FXML
	private TableColumn<Utilisateur, String> colonnePrenom;
	
	/** The colonne roles. */
	@FXML
	private TableColumn<Utilisateur, Number> colonneRoles;

	/** The champ nom. */
	@FXML
	private Label champNom;
	
	/** The champ prenom. */
	@FXML
	private Label champPrenom;
	
	/** The champ roles. */
	@FXML
	private Label champRoles;
	
	/** The champ identifiant. */
	@FXML
	private Label champIdentifiant;
	
	/** The label centre. */
	@FXML
	private Label labelCentre;
	
	/** The bundle. */
	@FXML
	private ResourceBundle bundle;
	
	/** The ajouter. */
	@FXML
	private Button ajouter;
	
	/** The modifier. */
	@FXML
	private Button modifier;
	
	/** The supprimer. */
	@FXML
	private Button supprimer;
	
	/** The parent. */
	@SuppressWarnings("unused")
	private AdministrationControleur parent;
	
	/** The dao utilisateur. */
	DAOUtilisateur daoUtilisateur = new DAOUtilisateur();

	/**
	 * Instantiates a new utilisateurs controleur.
	 */
	public UtilisateursControleur() {
	}

	/**
	 * Initialise la classe controleur avec les donn�es par d�faut du tableau.
	 *
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */

	@FXML
	private void initialize() throws ClassNotFoundException, SQLException {
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
		
		
		colonneIdentifiant.setCellValueFactory(cellData -> cellData.getValue().identifiantProperty());
		colonneNom.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
		colonnePrenom.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
		colonneRoles.setCellValueFactory(cellData -> cellData.getValue().privilegesProperty());
		try {
			utilisateursTable.setItems(daoUtilisateur.afficher());
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "D�tails: "+e);
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
		colonneIdentifiant.setText(bundle.getString("Identifiant"));
		colonneNom.setText(bundle.getString("Nom"));
		colonnePrenom.setText(bundle.getString("Prenom"));
		colonneRoles.setText(bundle.getString("Role"));
		ajouter.setText(bundle.getString("Ajouter"));
		modifier.setText(bundle.getString("Modifier"));
		supprimer.setText(bundle.getString("Supprimer"));
		labelCentre.setText(bundle.getString("gestionCompte"));
	}


	/**
	 * Appel� quand l'utilisateur clique sur le bouton ajouter un utilisateur. Ouvre
	 * une nouvelle page pour effectuer la modification
	 *
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	@FXML
	private void ajoutUtilisateur() throws ClassNotFoundException, SQLException {
		Utilisateur tempUtilisateur = new Utilisateur();
		boolean okClicked = fenetreModification(tempUtilisateur);
		if (okClicked) {
			try {
				daoUtilisateur.ajouter(tempUtilisateur);
				refresh();
				FonctionsControleurs.alerteInfo("Ajout �ffectu�", null, "Les informations ont �t� ajout�es avec succ�s!");
			} catch (Exception e) {
				FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "D�tails: "+e);
				e.printStackTrace();
			}

		}
	}

	/**
	 * Rafraichit les colonnes apr�s un ajout, une modification ou une suppression
	 * d'�l�ments.
	 *
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	private void refresh() throws ClassNotFoundException, SQLException {
		utilisateursTable.getItems().clear();
		comptes.clear();
		try {
			utilisateursTable.setItems(daoUtilisateur.afficher());
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "D�tails: "+e);
			e.printStackTrace();
		}

	}

	/**
	 * Appell� quand l'utilisateur clique sur le bouton supprimer.
	 *
	 * @throws ClassNotFoundException the class not found exception
	 */
	@FXML
	private void supprimerUtilisateur() throws ClassNotFoundException {
		Utilisateur selectedUtilisateur = utilisateursTable.getSelectionModel().getSelectedItem();
		int selectedIndex = utilisateursTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			try {
				daoUtilisateur.supprimer(selectedUtilisateur);
				refresh();
				FonctionsControleurs.alerteInfo("Suppression r�ussie", null, "L'utilisateur " + selectedUtilisateur.getIdentifiant() + " vient d'�tre supprim�e!");
			} catch (Exception e) {
				FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "D�tails: "+e);
				e.printStackTrace();
			}
		} else {
			// Si rien n'est s�l�ctionn�
			FonctionsControleurs.alerteAttention("Aucune s�lection", "Aucun compte de s�lectionn�!", "Selectionnez un compte pour pouvoir le supprimer");
		}
	}

	/**
	 * Appel� quand l'utilisateur clique sur le bouton modifier le compte. Ouvre une
	 * nouvelle page pour effectuer la modification
	 *
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	@FXML
	private void modifierUtilisateur() throws ClassNotFoundException, SQLException {
		Utilisateur selectedUtilisateur = utilisateursTable.getSelectionModel().getSelectedItem();
		if (selectedUtilisateur != null) {
			boolean okClicked = fenetreModification(selectedUtilisateur);
			if (okClicked) {
				try {
					daoUtilisateur.modifier(selectedUtilisateur);
					refresh();
					FonctionsControleurs.alerteInfo("Modification �ffectu�e", null, "Les informations ont �t� modifi�es avec succ�s!");
				} catch (Exception e) {
					FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "D�tails: "+e);
					e.printStackTrace();
				}
			}

		} else {
			// Si rien n'est selectionn�
			FonctionsControleurs.alerteAttention("Aucune s�lection", "Aucun compte de s�lectionn�!", "Selectionnez un compte pour pouvoir le modifier");
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
	 * affiche la fenetre de modification/ajout d'un utilisateur.
	 *
	 * @param compte the compte
	 * @return true, if successful
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 * @see ajoutUtilisateur
	 * @see modifierUtilisateur
	 */
		public boolean fenetreModification(Utilisateur compte) throws ClassNotFoundException, SQLException {
			try {
				Locale locale = new Locale("fr", "FR");

				ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_fr", locale);

				// Charge le fichier fxml et l'ouvre en pop-up
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/ModifierComptes.fxml"), bundle);
				AnchorPane page = (AnchorPane) loader.load();

				// Cr�e une nouvelle page
				Stage dialogStage = new Stage();
				dialogStage.setResizable(false);
				dialogStage.setTitle("Gestion des comptes");
				dialogStage.initModality(Modality.WINDOW_MODAL);
				Scene scene = new Scene(page);
				dialogStage.setScene(scene);
				dialogStage.getIcons().add(new Image(
		          	      Connexion.class.getResourceAsStream( "ico.png" ))); 

				// D�finition du controleur pour la fenetre
				ModifierUtilisateurControleur controller = loader.getController();
				controller.setDialogStage(dialogStage);
				controller.setUtilisateur(compte);

				// Affiche la page et attend que l'utilisateur la ferme.
				dialogStage.showAndWait();

				return controller.isOkClicked();
			} catch (Exception e) {
				FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "D�tails: "+e);
				e.printStackTrace();
				return false;
			}
		}
	
	/**
	 * Gets the table data.
	 *
	 * @return the table data
	 */
	public static ObservableList<Utilisateur> getTableData() {
		return comptes;
	}


}