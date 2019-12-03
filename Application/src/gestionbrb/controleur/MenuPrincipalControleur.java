package gestionbrb.controleur;

import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import gestionbrb.DAO.DAOUtilisateur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * 
 * @author Roman
 *
 */
public class MenuPrincipalControleur implements Initializable {
	@FXML
	private Label infoCompteLbl;
	@FXML
	private Button btnAdministration;
	@FXML
	private Button btnNvelleCommande;
	@FXML
	private Button btnStock;
	@FXML
	private ResourceBundle bundle;
	
	
	private static Stage demarrerCommande;
	private static Stage stockAdmin;
	private static Stage stockServeur;
	private static Stage carte;
	private static Stage parametres;
	private static Stage administration;

	ConnexionControleur parent;

	@FXML
	private AnchorPane fenetre;

	DAOUtilisateur daoUtilisateur = new DAOUtilisateur();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		bundle = resources;
		try {
			String langue = daoUtilisateur.recupererLangue(ConnexionControleur.getUtilisateurConnecte().getIdUtilisateur());
			System.out.println(langue);
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
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		infoCompteLbl.setText(ConnexionControleur.getUtilisateurConnecte().getIdentifiant());
		if(ConnexionControleur.getUtilisateurConnecte().getIdentifiant().equals("client")) {
			btnAdministration.setVisible(false);
			btnNvelleCommande.setVisible(false);
			btnStock.setVisible(false);
		}
		else if(ConnexionControleur.getUtilisateurConnecte().getPrivileges()==0) {
			btnAdministration.setVisible(false);
		}
	}

	
	private void loadLang(String lang, String LANG) {
		Locale locale = new Locale(lang, LANG);  
		System.out.println(bundle);
		ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_"+lang, locale);
		System.out.println(bundle.getString("key6"));
		System.out.println(bundle.getString("%key6"));
		btnNvelleCommande.setText(bundle.getString("%key6"));

		
	}

	@FXML
	public void fenetreNouvelleCommande() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/DemarrerCommande.fxml"));
			Parent vueDCommande = (Parent) loader.load();
			setDemarrerCommande(new Stage());
			getDemarrerCommande().setScene(new Scene(vueDCommande));
			getDemarrerCommande().show();
			getDemarrerCommande().setTitle("Démarrer une nouvelle commande");

			DemarrerCommandeControleur controller = loader.getController();
			controller.setParent(this);
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "Détails: "+e);
			e.printStackTrace();
		}
	}

	@FXML
	public void fenetreStock() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/GestionStock.fxml"));
			Parent vueGestionStock = (Parent) loader.load();
			setStockAdmin(new Stage());
			getStockAdmin().setScene(new Scene(vueGestionStock));
			getStockAdmin().show();
			getStockAdmin().setTitle("Gestion du stock");

			GestionStockAdminController controller = loader.getController();
			controller.setParent(this);
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "Détails: "+e);
			e.printStackTrace();
		}
	}

	@FXML
	public void fenetreCarte() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/Menu.fxml"));
			Parent vueMenu = (Parent) loader.load();
			setCarte(new Stage());
			getCarte().setScene(new Scene(vueMenu));
			getCarte().show();
			getCarte().setTitle("Menu");

			MenuControleur controller = loader.getController();
			controller.setParent(this);
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "Détails: "+e);
			e.printStackTrace();
		}
	}

	@FXML
	public void fenetreParametres() {
		try {
			 Locale locale = new Locale("fr", "FR");  

				ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_fr",locale);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/Parametres.fxml"),bundle);
		Parent vueParametre = (Parent) loader.load();
		setParametres(new Stage());
		getParametres().setScene(new Scene(vueParametre));
		getParametres().show();
		getParametres().setTitle("Paramètres");

		ParametresControleur controller = loader.getController();
		controller.setParent(this);
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "Détails: "+e);
			e.printStackTrace();
		}
	}

	@FXML
	public void fenetreAdministration() {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/Administration.fxml"));
			Parent vueAdministration= (Parent) loader.load();
			setAdministration(new Stage());
			getAdministration().setScene(new Scene(vueAdministration));
			getAdministration().show();
			getAdministration().setTitle("Administration");

			AdministrationControleur controller = loader.getController();
			controller.setParent(this);
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "Détails: "+e);
			e.printStackTrace();
		}
	

	}

	@FXML
	public void deconnexion() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/Connexion.fxml"));
			Parent menuConnexion = (Parent) loader.load();
			fenetre.getChildren().setAll(menuConnexion);

			ConnexionControleur controller = loader.getController();
			controller.setMainApp(this);
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Une erreur est survenue", "Détails: " + e);
			e.printStackTrace();
		}
	}

	// getters & setters utiles pour gérer la fenetre depuis les controleurs
	// associés
	public static Stage getDemarrerCommande() {
		return demarrerCommande;
	}

	public static void setDemarrerCommande(Stage demarrerCommande) {
		MenuPrincipalControleur.demarrerCommande = demarrerCommande;
	}

	public static Stage getStockAdmin() {
		return stockAdmin;
	}

	public static void setStockAdmin(Stage stockAdmin) {
		MenuPrincipalControleur.stockAdmin = stockAdmin;
	}

	public static Stage getStockServeur() {
		return stockServeur;
	}

	public static void setStockServeur(Stage stockServeur) {
		MenuPrincipalControleur.stockServeur = stockServeur;
	}

	public static Stage getCarte() {
		return carte;
	}

	public static void setCarte(Stage carte) {
		MenuPrincipalControleur.carte = carte;
	}

	public static Stage getParametres() {
		return parametres;
	}

	public static void setParametres(Stage parametres) {
		MenuPrincipalControleur.parametres = parametres;
	}

	public static Stage getAdministration() {
		return administration;
	}

	public static void setAdministration(Stage administration) {
		MenuPrincipalControleur.administration = administration;
	}

	/**
	 * Défini connexionProfil comment parent quand on accède au menu principal
	 * depuis la page de connexion
	 * 
	 * @param connexionProfil
	 */
	public void setParent(ConnexionControleur connexionProfil) {
		this.parent = connexionProfil;
	}


}