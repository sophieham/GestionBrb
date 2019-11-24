package gestionbrb.controleur;

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
	private static Stage param�tres;
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
			getDemarrerCommande().setTitle("D�marrer une nouvelle commande");

			DemarrerCommandeControleur controller = loader.getController();
			controller.setParent(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void fenetreStock() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/GestionStockAdmin.fxml"));
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

	}

	@FXML
	public void fenetreParametres() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/Parametres.fxml"));
			Parent vueParametre = (Parent) loader.load();
			setParam�tres(new Stage());
			getParam�tres().setScene(new Scene(vueParametre));
			getParam�tres().show();
			getParam�tres().setTitle("Param�tres");

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
			FonctionsControleurs.alerteErreur("Erreur d'�x�cution", "Une erreur est survenue", "D�tails: " + e);
			e.printStackTrace();
		}
	}

	// getters & setters utiles pour g�rer la fenetre depuis les controleurs
	// associ�s
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

	public static Stage getParam�tres() {
		return param�tres;
	}

	public static void setParam�tres(Stage param�tres) {
		MenuPrincipalControleur.param�tres = param�tres;
	}

	public static Stage getAdministration() {
		return administration;
	}

	public static void setAdministration(Stage administration) {
		MenuPrincipalControleur.administration = administration;
	}

	/**
	 * D�fini connexionProfil comment parent quand on acc�de au menu principal
	 * depuis la page de connexion
	 * 
	 * @param connexionProfil
	 */
	public void setParent(ConnexionControleur connexionProfil) {
		this.parent = connexionProfil;
	}
}
