package gestionbrb.controleur;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import gestionbrb.Connexion;
import gestionbrb.DAO.DAOUtilisateur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

// TODO: Auto-generated Javadoc
/**
 * The Class AdministrationControleur.
 */
/*
 * Panel d'administration permettant des opérations CRUD sur la plupart des objets
 */
public class AdministrationControleur extends FonctionsControleurs implements Initializable{

	/** The historique commande. */
	private static Stage historiqueCommande;
	
	/** The fournisseurs. */
	private static Stage fournisseurs;
	
	/** The tables. */
	private static Stage tables;
	
	/** The utilisateur. */
	private static Stage utilisateur;
	
	/** The plats ingredients. */
	private static Stage platsIngredients;

	/** The parent. */
	MenuPrincipalControleur parent;

	/** The fenetre. */
	@FXML
	private AnchorPane fenetre;
	
	/** The bundle. */
	@FXML
	private ResourceBundle bundle;
	
	/** The dao utilisateur. */
	DAOUtilisateur daoUtilisateur = new DAOUtilisateur();
	
	/** The historique. */
	@FXML
	private Button historique;
	
	/** The gerer fournisseur. */
	@FXML
	private Button gererFournisseur;
	
	/** The gerer table. */
	@FXML
	private Button gererTable;
	
	/** The gerer utilisateur. */
	@FXML
	private Button gererUtilisateur;
	
	/** The retour. */
	@FXML
	private Button retour;
	
	/** The gererplats. */
	@FXML
	private Button gererplats;
	
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
		historique.setText(bundle.getString("historique"));
		gererFournisseur.setText(bundle.getString("gererFournisseur"));
		gererTable.setText(bundle.getString("gererTable"));
		gererUtilisateur.setText(bundle.getString("gererUtilisateur"));
		retour.setText(bundle.getString("key5"));
		gererplats.setText(bundle.getString("gererplats"));

		/**
		 * 
		 */
	}
	
	/**
	 * Fenetre historique commandes.
	 */
	@FXML
	public void fenetreHistoriqueCommandes() {
		try {
			Locale locale = new Locale("fr", "FR");

			ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_fr", locale);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/HistoriqueCommande.fxml"),bundle);
			Parent vueHistoriqueCommande = (Parent) loader.load();
			setHistoriqueCommande(new Stage());
			getHistoriqueCommande().setScene(new Scene(vueHistoriqueCommande));
			getHistoriqueCommande().show();
			getHistoriqueCommande().setTitle("Historique des commandes");
			getHistoriqueCommande().setResizable(false);
			getHistoriqueCommande().getIcons().add(new Image(
          	      Connexion.class.getResourceAsStream( "ico.png" ))); 
			HistoriqueCommandeControleur controller = loader.getController();
			controller.setParent(this);
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'éxecution", "Une erreur est survenue", "Détails: " + e);
			e.printStackTrace();
		}
	}

	/**
	 * Fenetre fournisseur.
	 */
	@FXML
	public void fenetreFournisseur() {
		try {
			Locale locale = new Locale("fr", "FR");

			ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_fr", locale);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/GestionFournisseurs.fxml"),bundle);
			Parent vueGestionStockAdmin = (Parent) loader.load();
			setFournisseurs(new Stage());
			getFournisseurs().setScene(new Scene(vueGestionStockAdmin));
			getFournisseurs().show();
			getFournisseurs().setTitle("Gestion des fournisseurs");
			getFournisseurs().setResizable(false);
			getFournisseurs().getIcons().add(new Image(
	          	      Connexion.class.getResourceAsStream( "ico.png" ))); 
			FournisseursControleur controller = loader.getController();
			controller.setParent(this);
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'éxecution", "Une erreur est survenue", "Détails: " + e);
			e.printStackTrace();
		}
	}

	/**
	 * Fenetre tables.
	 */
	@FXML
	public void fenetreTables() {
		try {
			Locale locale = new Locale("fr", "FR");

			ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_fr", locale);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/GererTables.fxml"),bundle);
			Parent vueParametre = (Parent) loader.load();
			setTables(new Stage());
			getTables().setScene(new Scene(vueParametre));
			getTables().show();
			getTables().setTitle("Gestion des tables");
			getTables().setResizable(false);
			getTables().getIcons().add(new Image(
	          	      Connexion.class.getResourceAsStream( "ico.png" ))); 
			TablesControleur controller = loader.getController();
			controller.setParent(this);
			
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'éxecution", "Une erreur est survenue", "Détails: " + e);
			e.printStackTrace();
		}
	}

	/**
	 * Fenetre utilisateur.
	 */
	@FXML
	public void fenetreUtilisateur() {
		try {
			Locale locale = new Locale("fr", "FR");

			ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_fr", locale);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/GestionComptes.fxml"),bundle);
			Parent vueParametre = (Parent) loader.load();
			setUtilisateur(new Stage());
			getUtilisateur().setScene(new Scene(vueParametre));
			getUtilisateur().show();
			getUtilisateur().setTitle("Liste du personnel");
			getUtilisateur().setResizable(false);
			getUtilisateur().getIcons().add(new Image(
	          	      Connexion.class.getResourceAsStream( "ico.png" ))); 
			UtilisateursControleur controller = loader.getController();
			controller.setParent(this);
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'éxecution", "Une erreur est survenue", "Détails: " + e);
			e.printStackTrace();
		}
	}

	/**
	 * Fenetre plats ingerdients.
	 */
	@FXML
	public void fenetrePlatsIngerdients() {

		try {
			Locale locale = new Locale("fr", "FR");
			ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_fr", locale);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/GererIngredientsProduits.fxml"),bundle);
			Parent vueAdministration= (Parent) loader.load();
			setPlatsIngredients(new Stage());
			getPlatsIngredients().setScene(new Scene(vueAdministration));
			getPlatsIngredients().show();
			getPlatsIngredients().setTitle("Plats & Ingredients");
			getPlatsIngredients().setResizable(false);
			getPlatsIngredients().getIcons().add(new Image(
	          	      Connexion.class.getResourceAsStream( "ico.png" ))); 
			IngredientsProduitsControleur controller = loader.getController();
			controller.setParent(this);
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'éxecution", "Une erreur est survenue", "Détails: " + e);
			e.printStackTrace();
		}
	

	}
	
	/**
	 * Fermer fenetre.
	 */
	@FXML
	public void fermerFenetre() {
		try {
			Locale locale = new Locale("fr", "FR");

			ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_fr", locale);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/MenuPrincipal.fxml"), bundle);
			Parent menuPrincipal = (Parent) loader.load();
			fenetre.getChildren().setAll(menuPrincipal); // remplace la fenetre de connexion par celle du menu principal
			
			MenuPrincipalControleur controller = loader.getController();
			controller.setParent(this);
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'éxecution", "Une erreur est survenue", "Détails: " + e);
			e.printStackTrace();
		}
	}
	

	

	// getters & setters utiles pour gérer la fenetre depuis les controleurs
	/**
	 * Gets the historique commande.
	 *
	 * @return the historique commande
	 */
	// associés
	public static Stage getHistoriqueCommande() {
		return historiqueCommande;
	}

	/**
	 * Sets the historique commande.
	 *
	 * @param historiqueCommande the new historique commande
	 */
	public static void setHistoriqueCommande(Stage historiqueCommande) {
		AdministrationControleur.historiqueCommande = historiqueCommande;
	}

	/**
	 * Gets the fournisseurs.
	 *
	 * @return the fournisseurs
	 */
	public static Stage getFournisseurs() {
		return fournisseurs;
	}

	/**
	 * Sets the fournisseurs.
	 *
	 * @param fournisseurs the new fournisseurs
	 */
	public static void setFournisseurs(Stage fournisseurs) {
		AdministrationControleur.fournisseurs = fournisseurs;
	}

	/**
	 * Gets the tables.
	 *
	 * @return the tables
	 */
	public static Stage getTables() {
		return tables;
	}

	/**
	 * Sets the tables.
	 *
	 * @param tables the new tables
	 */
	public static void setTables(Stage tables) {
		AdministrationControleur.tables = tables;
	}

	/**
	 * Gets the utilisateur.
	 *
	 * @return the utilisateur
	 */
	public static Stage getUtilisateur() {
		return utilisateur;
	}

	/**
	 * Sets the utilisateur.
	 *
	 * @param utilisateur the new utilisateur
	 */
	public static void setUtilisateur(Stage utilisateur) {
		AdministrationControleur.utilisateur = utilisateur;
	}

	/**
	 * Gets the plats ingredients.
	 *
	 * @return the plats ingredients
	 */
	public static Stage getPlatsIngredients() {
		return platsIngredients;
	}

	/**
	 * Sets the plats ingredients.
	 *
	 * @param platsIngredients the new plats ingredients
	 */
	public static void setPlatsIngredients(Stage platsIngredients) {
		AdministrationControleur.platsIngredients = platsIngredients;
	}

	/**
	 * Défini connexionProfil comment parent quand on accède au menu principal
	 * depuis la page de connexion.
	 *
	 * @param menuPrincipalControleur the new parent
	 */
	public void setParent(MenuPrincipalControleur menuPrincipalControleur) {
		this.parent = menuPrincipalControleur;
	}


	
	
}
