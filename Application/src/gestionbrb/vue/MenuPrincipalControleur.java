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
	@FXML
	private Button btnmenu;
	@FXML
	private Button btnparametre;
	@FXML
	private Button btnDeconnexiton;
	@FXML 
	private Label connecte;
	
	private static Stage demarrerCommande;
	private static Stage stock;
	private static Stage carte;
	private static Stage parametres;
	private static Stage administration;

	ConnexionControleur connexionControleur;
	AdministrationControleur administrationControleur;
	ParametresControleur parametresControleur;
	MenuControleur menuControleur;
	@FXML
	private AnchorPane fenetre;
	
	final URL cssURL = getClass().getResource("../stylesheet.css"); 

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

	private void loadLang(String lang, String LANG) {
		Locale locale = new Locale(lang, LANG);  
		System.out.println(bundle);
		ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_"+lang, locale);
		System.out.println(bundle.getString("key6"));
		btnNvelleCommande.setText(bundle.getString("key6"));
		btnAdministration.setText(bundle.getString("key7"));
		btnStock.setText(bundle.getString("key8"));
		btnmenu.setText(bundle.getString("key9"));
		btnparametre.setText(bundle.getString("key10"));
		btnDeconnexiton.setText(bundle.getString("key11"));
		connecte.setText(bundle.getString("key12"));
		
	}

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

	public static Stage getStock() {
		return stock;
	}

	public static void setStock(Stage stock) {
		MenuPrincipalControleur.stock = stock;
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
	public void setParent(ConnexionControleur parent) {
		this.connexionControleur = parent;
	}


	public void setParent(AdministrationControleur parent) {
		this.administrationControleur = parent;
		
	}


	public void setParent(ParametresControleur parent) {
		this.parametresControleur = parent;
		
	}


	public void setParent(MenuControleur parent) {
		this.menuControleur = parent;	
	}


}