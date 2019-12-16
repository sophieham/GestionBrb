package gestionbrb.controleur;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import gestionbrb.Connexion;
import gestionbrb.DAO.DAOFournisseur;
import gestionbrb.DAO.DAOUtilisateur;
import gestionbrb.model.Fournisseur;
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

/**
 * 
 * @author Roman
 *
 */
public class FournisseursControleur extends FonctionsControleurs {
	
	private static ObservableList<Fournisseur> fournisseurs = FXCollections.observableArrayList();
	
	@FXML
	private TableView<Fournisseur> fournisseursTable;
	@FXML
	private TableColumn<Fournisseur, Number> colonneId;
	@FXML
	private TableColumn<Fournisseur, String> colonneNom;
	@FXML
	private TableColumn<Fournisseur, String> colonneTel;
	@FXML
	private TableColumn<Fournisseur, String> colonneMail;
	@FXML
	private TableColumn<Fournisseur, String> colonneAdresse;
	@FXML
	private TableColumn<Fournisseur, String> colonneVille;
	@FXML
	private Button Ajouter;
	@FXML
	private Button Modifier;
	@FXML
	private Button Supprimer;
	@FXML
	private Label champNom;
	@FXML
	private Label champVille;
	@FXML
	private Label champAdresse;
	@FXML
	private Label champId;
	@FXML
	private Label champTel;
	@FXML
	private Label champMail;
	@FXML
	private Label labelCentre;

	@SuppressWarnings("unused")
	private AdministrationControleur parent;
	@FXML
	private ResourceBundle bundle;
	DAOUtilisateur daoUtilisateur = new DAOUtilisateur();
	DAOFournisseur daoFournisseur = new DAOFournisseur();

	public FournisseursControleur() throws ClassNotFoundException, SQLException {
	}
	
	public void setParent(AdministrationControleur administrationControleur) throws SQLException {
		// TODO Auto-generated method stub
		this.parent = administrationControleur;
	}
	

	/**
	 * Initialise la classe controleur avec les données par défaut du tableau
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
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
		colonneId.setCellValueFactory(cellData -> cellData.getValue().idFournisseurProperty());
		colonneNom.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
		colonneMail.setCellValueFactory(cellData -> cellData.getValue().mailProperty());
		colonneTel.setCellValueFactory(cellData -> cellData.getValue().numTelProperty());
		colonneAdresse.setCellValueFactory(cellData -> cellData.getValue().adresseProperty());
		colonneVille.setCellValueFactory(cellData -> cellData.getValue().nomVilleProperty());
		fournisseursTable.setItems(daoFournisseur.afficher());

	}

	private void loadLang(String lang, String LANG) {
		Locale locale = new Locale(lang, LANG);  
		
		ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_"+lang, locale);
		colonneNom.setText(bundle.getString("Nom"));
		colonneTel.setText(bundle.getString("telephone"));
		colonneMail.setText(bundle.getString("Mail"));
		colonneAdresse.setText(bundle.getString("Adresse"));
		colonneVille.setText(bundle.getString("Ville"));
		Modifier.setText(bundle.getString("Modifier"));
		Supprimer.setText(bundle.getString("Supprimer"));
		Ajouter.setText(bundle.getString("Ajouter"));
		labelCentre.setText(bundle.getString("labelCentre"));
	}
	/**
	 * Appelé quand l'utilisateur clique sur le bouton ajouter un utilisateur. Ouvre
	 * une nouvelle page pour effectuer la modification
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void ajoutFournisseur() throws ClassNotFoundException, SQLException {
		Fournisseur tempFournisseur = new Fournisseur();
		boolean okClicked = fenetreModification(tempFournisseur);
		if (okClicked) {
			try {
				
				daoFournisseur.ajouter(tempFournisseur);
				
				refresh();
				alerteInfo("Ajout éffectué", null, "Les informations ont été ajoutées avec succès!");
			} catch (Exception e) {
				FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue","Détails: "+e);
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
	private void refresh() {
		fournisseursTable.getItems().clear();
		fournisseurs.clear();
		try {
			fournisseursTable.setItems(daoFournisseur.afficher());
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue","Détails: "+e);
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
	private void supprimerFournisseur() throws ClassNotFoundException {
		Fournisseur selectedTable = fournisseursTable.getSelectionModel().getSelectedItem();
		int selectedIndex = fournisseursTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			try {
				daoFournisseur.supprimer(selectedTable);
				refresh();
				alerteInfo("Suppression réussie", null, "Le fournisseur " + selectedTable.getIdFournisseur() + " vient d'être supprimée!");
			} catch (Exception e) {
				FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue","Détails: "+e);
				e.printStackTrace();
			}

		} else {
			// Si rien n'est séléctionné
			FonctionsControleurs.alerteAttention("Aucune sélection", "Aucun fournisseur de sélectionnée!","Selectionnez un fournisseur pour pouvoir la supprimer");
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
	private void modifierFournisseur() throws ClassNotFoundException, SQLException {
		Fournisseur selectedFournisseur = fournisseursTable.getSelectionModel().getSelectedItem();
		if (selectedFournisseur != null) {
			boolean okClicked = fenetreModification(selectedFournisseur);
			if (okClicked) {
				try {
					daoFournisseur.modifier(selectedFournisseur);
					//refresh();
					FonctionsControleurs.alerteInfo("Modification éffectuée", null, "Les informations ont été modifiées avec succès!");
				} catch (Exception e) {
					FonctionsControleurs.alerteAttention("Aucune sélection", "Aucun fournisseur de sélectionnée!","Selectionnez un fournisseur pour pouvoir la supprimer");
					e.printStackTrace();
				}
			}

		} else {
			// Si rien n'est selectionné
			FonctionsControleurs.alerteAttention("Aucune sélection", "Aucun founisseur de sélectionnée!", "Selectionnez un fournisseur pour pouvoir le modifier");
		}
	}

	
	/**
	 * Appellé pour afficher la fenetre de modification/ajout d'un fournisseur
	 * 
	 * @param fournisseur Fournisseur à ajouter/modifier
	 * @see ajoutFournisseur
	 * @see modifierFournisseur
	 * 
	 **/
	public boolean fenetreModification(Fournisseur fournisseur) throws ClassNotFoundException, SQLException {
		try {
			Locale locale = new Locale("fr", "FR");

			ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_fr", locale);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/ModifierFournisseur.fxml"), bundle);
			AnchorPane page = (AnchorPane) loader.load();

			// Crée une nouvelle page
			Stage dialogStage = new Stage();
			dialogStage.setResizable(false);
			dialogStage.setTitle("Gestion des fournisseurs");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			dialogStage.getIcons().add(new Image(
	          	      Connexion.class.getResourceAsStream( "ico.png" ))); 

			// Définition du controleur pour la fenetre
			ModifierFournisseurControleur controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setFournisseur(fournisseur);

			// Affiche la page et attend que l'utilisateur la ferme.
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static ObservableList<Fournisseur> getTableData() {
		return fournisseurs;
	}

}