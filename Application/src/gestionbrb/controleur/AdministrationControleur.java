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

public class AdministrationControleur extends FonctionsControleurs implements Initializable{

	private static Stage historiqueCommande;
	private static Stage fournisseurs;
	private static Stage tables;
	private static Stage utilisateur;
	private static Stage platsIngredients;

	MenuPrincipalControleur parent;

	@FXML
	private AnchorPane fenetre;
	@FXML
	private ResourceBundle bundle;
	DAOUtilisateur daoUtilisateur = new DAOUtilisateur();
	@FXML
	private Button historique;
	@FXML
	private Button gererFournisseur;
	@FXML
	private Button gererTable;
	@FXML
	private Button gererUtilisateur;
	@FXML
	private Button retour;
	@FXML
	private Button gererplats;
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
		
	}

	private void loadLang(String lang, String LANG) {
		Locale locale = new Locale(lang, LANG);  
		
		ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_"+lang, locale);
		historique.setText(bundle.getString("historique"));
		gererFournisseur.setText(bundle.getString("gererFournisseur"));
		gererTable.setText(bundle.getString("gererTable"));
		gererUtilisateur.setText(bundle.getString("gererUtilisateur"));
		retour.setText(bundle.getString("key5"));
		gererplats.setText(bundle.getString("gererplats"));

		
	}
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
	// associés
	public static Stage getHistoriqueCommande() {
		return historiqueCommande;
	}

	public static void setHistoriqueCommande(Stage historiqueCommande) {
		AdministrationControleur.historiqueCommande = historiqueCommande;
	}

	public static Stage getFournisseurs() {
		return fournisseurs;
	}

	public static void setFournisseurs(Stage fournisseurs) {
		AdministrationControleur.fournisseurs = fournisseurs;
	}

	public static Stage getTables() {
		return tables;
	}

	public static void setTables(Stage tables) {
		AdministrationControleur.tables = tables;
	}

	public static Stage getUtilisateur() {
		return utilisateur;
	}

	public static void setUtilisateur(Stage utilisateur) {
		AdministrationControleur.utilisateur = utilisateur;
	}

	public static Stage getPlatsIngredients() {
		return platsIngredients;
	}

	public static void setPlatsIngredients(Stage platsIngredients) {
		AdministrationControleur.platsIngredients = platsIngredients;
	}

	/**
	 * Défini connexionProfil comment parent quand on accède au menu principal
	 * depuis la page de connexion
	 * 
	 * @param menuPrincipalControleur
	 */
	public void setParent(MenuPrincipalControleur menuPrincipalControleur) {
		this.parent = menuPrincipalControleur;
	}


	
	
}
