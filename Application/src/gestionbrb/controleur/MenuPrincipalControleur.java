package gestionbrb.controleur;

import java.net.URL;
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

// TODO: Auto-generated Javadoc
/**
 * The Class MenuPrincipalControleur.
 *
 * @author Roman
 */
public class MenuPrincipalControleur implements Initializable {
	
	/** The info compte lbl. */
	@FXML
	private Label infoCompteLbl;
	
	/** The btn administration. */
	@FXML
	private Button btnAdministration;
	
	/** The btn nvelle commande. */
	@FXML
	private Button btnNvelleCommande;
	
	/** The btn stock. */
	@FXML
	private Button btnStock;
	
	/** The bundle. */
	@FXML
	private ResourceBundle bundle;
	
	/** The btnmenu. */
	@FXML
	private Button btnmenu;
	
	/** The btnparametre. */
	@FXML
	private Button btnparametre;
	
	/** The btn deconnexiton. */
	@FXML
	private Button btnDeconnexiton;
	
	/** The connecte. */
	@FXML 
	private Label connecte;
	
	/** The demarrer commande. */
	private static Stage demarrerCommande;
	
	/** The stock. */
	private static Stage stock;
	
	/** The carte. */
	private static Stage carte;
	
	/** The parametres. */
	private static Stage parametres;
	
	/** The administration. */
	private static Stage administration;

	/** The connexion controleur. */
	ConnexionControleur connexionControleur;
	
	/** The administration controleur. */
	AdministrationControleur administrationControleur;
	
	/** The parametres controleur. */
	ParametresControleur parametresControleur;
	
	/** The menu controleur. */
	MenuControleur menuControleur;
	
	/** The fenetre. */
	@FXML
	private AnchorPane fenetre;
	
	/** The css URL. */
	final URL cssURL = getClass().getResource("../stylesheet.css"); 

	/** The dao utilisateur. */
	DAOUtilisateur daoUtilisateur = new DAOUtilisateur();
	
	/**
	 * Initialize.
	 *
	 * @param location the location
	 * @param resources the resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		bundle = resources;
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

	/**
	 * Load lang.
	 *
	 * @param lang the lang
	 * @param LANG the lang
	 */
	private void loadLang(String lang, String LANG) {
		Locale locale = new Locale(lang, LANG);  
		
		ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_"+lang, locale);
		btnNvelleCommande.setText(bundle.getString("key6"));
		btnAdministration.setText(bundle.getString("key7"));
		btnStock.setText(bundle.getString("key8"));
		btnmenu.setText(bundle.getString("key9"));
		btnparametre.setText(bundle.getString("key10"));
		btnDeconnexiton.setText(bundle.getString("key11"));
		connecte.setText(bundle.getString("key12"));
		
	}


	/**
	 * Fenetre nouvelle commande.
	 */
	@FXML
	public void fenetreNouvelleCommande() {
		try {
			Locale locale = new Locale("fr", "FR");

			ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_fr", locale);

			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/DemarrerCommande.fxml"), bundle);
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

	/**
	 * Fenetre stock.
	 */
	@FXML
	public void fenetreStock() {
		try {
			Locale locale = new Locale("fr", "FR");

			ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_fr", locale);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/GestionStock.fxml"),bundle);
			Parent vueGestionStock = (Parent) loader.load();
			setStock(new Stage());
			getStock().setScene(new Scene(vueGestionStock));
			getStock().show();
			getStock().setTitle("Gestion du stock");

			GestionStockController controller = loader.getController();
			controller.setParent(this);
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "Détails: "+e);
			e.printStackTrace();
		}
	}

	/**
	 * Fenetre carte.
	 */
	@FXML
	public void fenetreCarte() {
		try {
			Locale locale = new Locale("fr", "FR");

			ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_fr", locale);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/Menu.fxml"),bundle);
			Parent menuCarte = (Parent) loader.load();
			fenetre.getChildren().setAll(menuCarte);
			MenuControleur controller = loader.getController();
			controller.setParent(this);
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "Détails: "+e);
			e.printStackTrace();
		}
	}

	/**
	 * Fenetre parametres.
	 */
	@FXML
	public void fenetreParametres() {
		try {
			Locale locale = new Locale("fr", "FR");

			ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_fr", locale);

			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/Parametres.fxml"), bundle);
			Parent menuAdministration = (Parent) loader.load();
			fenetre.getChildren().setAll(menuAdministration);

			ParametresControleur controller = loader.getController();
			controller.setParent(this);
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "Détails: "+e);
			e.printStackTrace();
		}
	}

	/**
	 * Fenetre administration.
	 */
	@FXML
	public void fenetreAdministration() {

		try {
			Locale locale = new Locale("fr", "FR");

			ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_fr", locale);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/Administration.fxml"),bundle);
			Parent menuAdministration = (Parent) loader.load();
			fenetre.getChildren().setAll(menuAdministration);

			AdministrationControleur controller = loader.getController();
			controller.setParent(this);
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "Détails: "+e);
			e.printStackTrace();
		}
	

	}

	/**
	 * Deconnexion.
	 */
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
	/**
	 * Gets the demarrer commande.
	 *
	 * @return the demarrer commande
	 */
	// associés
	public static Stage getDemarrerCommande() {
		return demarrerCommande;
	}

	/**
	 * Sets the demarrer commande.
	 *
	 * @param demarrerCommande the new demarrer commande
	 */
	public static void setDemarrerCommande(Stage demarrerCommande) {
		MenuPrincipalControleur.demarrerCommande = demarrerCommande;
	}

	/**
	 * Gets the stock.
	 *
	 * @return the stock
	 */
	public static Stage getStock() {
		return stock;
	}

	/**
	 * Sets the stock.
	 *
	 * @param stock the new stock
	 */
	public static void setStock(Stage stock) {
		MenuPrincipalControleur.stock = stock;
	}

	/**
	 * Gets the carte.
	 *
	 * @return the carte
	 */
	public static Stage getCarte() {
		return carte;
	}

	/**
	 * Sets the carte.
	 *
	 * @param carte the new carte
	 */
	public static void setCarte(Stage carte) {
		MenuPrincipalControleur.carte = carte;
	}

	/**
	 * Gets the parametres.
	 *
	 * @return the parametres
	 */
	public static Stage getParametres() {
		return parametres;
	}

	/**
	 * Sets the parametres.
	 *
	 * @param parametres the new parametres
	 */
	public static void setParametres(Stage parametres) {
		MenuPrincipalControleur.parametres = parametres;
	}

	/**
	 * Gets the administration.
	 *
	 * @return the administration
	 */
	public static Stage getAdministration() {
		return administration;
	}

	/**
	 * Sets the administration.
	 *
	 * @param administration the new administration
	 */
	public static void setAdministration(Stage administration) {
		MenuPrincipalControleur.administration = administration;
	}

	/**
	 * Défini connexionProfil comment parent quand on accède au menu principal
	 * depuis la page de connexion.
	 *
	 * @param parent the new parent
	 */
	public void setParent(ConnexionControleur parent) {
		this.connexionControleur = parent;
	}


	/**
	 * Sets the parent.
	 *
	 * @param parent the new parent
	 */
	public void setParent(AdministrationControleur parent) {
		this.administrationControleur = parent;
		
	}


	/**
	 * Sets the parent.
	 *
	 * @param parent the new parent
	 */
	public void setParent(ParametresControleur parent) {
		this.parametresControleur = parent;
		
	}


	/**
	 * Sets the parent.
	 *
	 * @param parent the new parent
	 */
	public void setParent(MenuControleur parent) {
		this.menuControleur = parent;	
	}


}