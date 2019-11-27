package gestionbrb.controleur;

import gestionbrb.vue.MenuControleur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * 
 * @author Roman
 *
 */
public class MenuPrincipalControleur {
	private static Stage demarrerCommande;
	private static Stage stockAdmin;
	private static Stage stockServeur;
	private static Stage carte;
	private static Stage parametres;
	private static Stage administration;

	ConnexionControleur parent;

	@FXML
	private AnchorPane fenetre;

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
			e.printStackTrace();
		}
	}

	@FXML
	public void fenetreStock() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/GererStockAdmin.fxml"));
			Parent vueGestionStockAdmin = (Parent) loader.load();
			setStockAdmin(new Stage());
			getStockAdmin().setScene(new Scene(vueGestionStockAdmin));
			getStockAdmin().show();
			getStockAdmin().setTitle("Gestion du stock");

			GestionStockAdminController controller = loader.getController();
			controller.setParent(this);
		} catch (Exception e) {
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
			e.printStackTrace();
		}
	}

	@FXML
	public void fenetreParametres() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/Menu.fxml"));
		Parent vueParametre = (Parent) loader.load();
		setParametres(new Stage());
		getParametres().setScene(new Scene(vueParametre));
		getParametres().show();
		getParametres().setTitle("Paramètres");

		ParametresControleur controller = loader.getController();
		controller.setParent(this);
		} catch (Exception e) {
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