package gestionbrb.controleur;

import java.sql.SQLException;

import gestionbrb.DAO.DAOUtilisateur;
import gestionbrb.model.Utilisateur;
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
 * Gère les utilisateurs et leurs fonctions.
 * @author Roman
 *
 */
public class UtilisateursControleur {
	
	private static ObservableList<Utilisateur> comptes = FXCollections.observableArrayList();
	
	@FXML
	private TableView<Utilisateur> utilisateursTable;
	@FXML
	private TableColumn<Utilisateur, String> colonneIdentifiant;
	@FXML
	private TableColumn<Utilisateur, String> colonneNom;
	@FXML
	private TableColumn<Utilisateur, String> colonnePrenom;
	@FXML
	private TableColumn<Utilisateur, Number> colonneRoles;

	@FXML
	private Label champNom;
	@FXML
	private Label champPrenom;
	@FXML
	private Label champRoles;
	@FXML
	private Label champIdentifiant;

	@SuppressWarnings("unused")
	private AdministrationControleur parent;
	
	DAOUtilisateur daoUtilisateur = new DAOUtilisateur();

	public UtilisateursControleur() {
	}

	/**
	 * Initialise la classe controleur avec les données par défaut du tableau
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */

	@FXML
	private void initialize() throws ClassNotFoundException, SQLException {
		colonneIdentifiant.setCellValueFactory(cellData -> cellData.getValue().identifiantProperty());
		colonneNom.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
		colonnePrenom.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
		colonneRoles.setCellValueFactory(cellData -> cellData.getValue().privilegesProperty());
		try {
			utilisateursTable.setItems(daoUtilisateur.afficher());
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "Détails: "+e);
			e.printStackTrace();
		}
	}



	/**
	 * Appelé quand l'utilisateur clique sur le bouton ajouter un utilisateur. Ouvre
	 * une nouvelle page pour effectuer la modification
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void ajoutUtilisateur() throws ClassNotFoundException, SQLException {
		Utilisateur tempUtilisateur = new Utilisateur();
		boolean okClicked = fenetreModification(tempUtilisateur);
		if (okClicked) {
			try {
				daoUtilisateur.ajouter(tempUtilisateur);
				refresh();
				FonctionsControleurs.alerteInfo("Ajout éffectué", null, "Les informations ont été ajoutées avec succès!");
			} catch (Exception e) {
				FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "Détails: "+e);
				e.printStackTrace();
			}

		}
	}

	/**
	 * Rafraichit les colonnes après un ajout, une modification ou une suppression
	 * d'éléments.
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private void refresh() throws ClassNotFoundException, SQLException {
		utilisateursTable.getItems().clear();
		comptes.clear();
		try {
			utilisateursTable.setItems(daoUtilisateur.afficher());
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "Détails: "+e);
			e.printStackTrace();
		}

	}

	/**
	 * Appellé quand l'utilisateur clique sur le bouton supprimer
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void supprimerUtilisateur() throws ClassNotFoundException {
		Utilisateur selectedUtilisateur = utilisateursTable.getSelectionModel().getSelectedItem();
		int selectedIndex = utilisateursTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			try {
				daoUtilisateur.supprimer(selectedUtilisateur);
				refresh();
				FonctionsControleurs.alerteInfo("Suppression réussie", null, "L'utilisateur " + selectedUtilisateur.getIdentifiant() + " vient d'être supprimée!");
			} catch (Exception e) {
				FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "Détails: "+e);
				e.printStackTrace();
			}
		} else {
			// Si rien n'est séléctionné
			FonctionsControleurs.alerteAttention("Aucune sélection", "Aucun compte de sélectionné!", "Selectionnez un compte pour pouvoir le supprimer");
		}
	}

	/**
	 * Appelé quand l'utilisateur clique sur le bouton modifier le compte. Ouvre une
	 * nouvelle page pour effectuer la modification
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
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
					FonctionsControleurs.alerteInfo("Modification éffectuée", null, "Les informations ont été modifiées avec succès!");
				} catch (Exception e) {
					FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "Détails: "+e);
					e.printStackTrace();
				}
			}

		} else {
			// Si rien n'est selectionné
			FonctionsControleurs.alerteAttention("Aucune sélection", "Aucun compte de sélectionné!", "Selectionnez un compte pour pouvoir le modifier");
		}
	}

	public void setParent(AdministrationControleur administrationControleur) {
		// TODO Auto-generated method stub
		this.parent = administrationControleur;
	}
	
	/**
	 * affiche la fenetre de modification/ajout d'un utilisateur
	 * 
	 * @param compte
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @see ajoutUtilisateur
	 * @see modifierUtilisateur
	 */
		public boolean fenetreModification(Utilisateur compte) throws ClassNotFoundException, SQLException {
			try {
				// Charge le fichier fxml et l'ouvre en pop-up
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(UtilisateursControleur.class.getResource("../vue/ModifierComptes.fxml"));
				AnchorPane page = (AnchorPane) loader.load();

				// Crée une nouvelle page
				Stage dialogStage = new Stage();
				dialogStage.setResizable(false);
				dialogStage.setTitle("Gestion des comptes");
				dialogStage.initModality(Modality.WINDOW_MODAL);
				Scene scene = new Scene(page);
				dialogStage.setScene(scene);

				// Définition du controleur pour la fenetre
				ModifierUtilisateurControleur controller = loader.getController();
				controller.setDialogStage(dialogStage);
				controller.setUtilisateur(compte);

				// Affiche la page et attend que l'utilisateur la ferme.
				dialogStage.showAndWait();

				return controller.isOkClicked();
			} catch (Exception e) {
				FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "Détails: "+e);
				e.printStackTrace();
				return false;
			}
		}
	
	public static ObservableList<Utilisateur> getTableData() {
		return comptes;
	}


}
