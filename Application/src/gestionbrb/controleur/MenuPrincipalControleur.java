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
	}

	@FXML
	public void fenetreCarte() {
		
	}


	@FXML
	public void fenetreParametres() {

	}

	@FXML
	public void fenetreAdministration() {
		
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
			FonctionsControleurs.alerteErreur("Erreur d'�x�cution", "Une erreur est survenue","D�tails: "+e);
			e.printStackTrace();
		}
	}

	// getters & setters utiles pour g�rer la fenetre depuis les controleurs associ�s 
	public static Stage getDemarrerCommande() {
		return demarrerCommande;
	}

	public static void setDemarrerCommande(Stage demarrerCommande) {
		MenuPrincipalControleur.demarrerCommande = demarrerCommande;
	}

	
	/**
	 * D�fini connexionProfil comment parent quand on acc�de au menu principal depuis la page de connexion
	 * @param connexionProfil
	 */
	public void setParent(ConnexionControleur connexionProfil) {
		this.parent = connexionProfil;
	}
}
