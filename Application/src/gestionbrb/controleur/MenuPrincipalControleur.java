package gestionbrb.controleur;

import java.util.Locale;
import java.util.ResourceBundle;

import gestionbrb.vue.MenuControleur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
public class MenuPrincipalControleur {
	private static Stage demarrerCommande;
    private ConnexionControleur parent;
    private ParametresControleur parentParametres;
    
    @FXML
    private Label infoCompteLbl;
	@FXML
	private Button btnAdministration;
    @FXML
    private AnchorPane fenetre;

	@FXML
	public void initialize() {
		infoCompteLbl.setText(ConnexionControleur.getUtilisateurConnecte().getIdentifiant());
		if(ConnexionControleur.getUtilisateurConnecte().getPrivileges()==0) {
			btnAdministration.setVisible(false);
		}
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
			e.printStackTrace();
		}
	}

	@FXML
	public void fenetreStock() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/GestionStock.fxml"));
			Parent gestionStock = (Parent) loader.load();
			fenetre.getChildren().setAll(gestionStock);
			

			GestionStockAdminController controller = loader.getController();
			controller.setParent(this);
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Une erreur est survenue", "Détails: " + e);
			e.printStackTrace();
		}
	}

	@FXML
	public void fenetreCarte() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/Menu.fxml"));
			Parent menuClient = (Parent) loader.load();
			fenetre.getChildren().setAll(menuClient);

			MenuControleur controller = loader.getController();
			controller.setParent(this);
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Une erreur est survenue", "Détails: " + e);
			e.printStackTrace();
		}
	}


	@FXML
	public void fenetreParametres() {
		try {
			Locale locale = new Locale("fr", "FR");
			ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_fr",locale);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/Parametres.fxml"), bundle);
			Parent menuParametres = (Parent) loader.load();
			fenetre.getChildren().setAll(menuParametres);  
				
				 
			ParametresControleur controller = loader.getController();
			controller.setParent(this);
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Une erreur est survenue","Détails: "+e);
			e.printStackTrace();
			
		}
	}

	@FXML
	public void fenetreAdministration() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/Administration.fxml"));
			Parent menuClient = (Parent) loader.load();
			fenetre.getChildren().setAll(menuClient);

			AdministrationControleur controller = loader.getController();
			controller.setParent(this);
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Une erreur est survenue", "Détails: " + e);
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
			FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Une erreur est survenue","Détails: "+e);
			e.printStackTrace();
		}
	}

	// getters & setters utiles pour gérer la fenetre depuis les controleurs associés 
	public static Stage getDemarrerCommande() {
		return demarrerCommande;
	}

	public static void setDemarrerCommande(Stage demarrerCommande) {
		MenuPrincipalControleur.demarrerCommande = demarrerCommande;
	}

	
	/**
	 * Défini connexionProfil comment parent quand on accède au menu principal depuis la page de connexion
	 * @param connexionProfil
	 */
	public void setParent(ConnexionControleur connexionProfil) {
		this.parent = connexionProfil;
	}
}
