package gestionbrb.controleur;

import java.util.Locale;
import java.util.ResourceBundle;

import gestionbrb.Connexion;
import gestionbrb.DAO.DAOUtilisateur;
import gestionbrb.model.Utilisateur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
// TODO: Auto-generated Javadoc

/**
 * The Class ConnexionControleur.
 *
 * @author Leo
 */
public class ConnexionControleur extends Connexion {	
	
	/** The identifiant. */
	@FXML private TextField identifiant;
	
	/** The pass. */
	@FXML private PasswordField pass;
	
	/** The parent. */
	Connexion parent;
	
	/** The utilisateur connecte. */
	private static Utilisateur utilisateurConnecte;
	
	/** The main app. */
	@SuppressWarnings("unused")
	private MenuPrincipalControleur mainApp;
	
	/** The fenetre. */
	@FXML
	private AnchorPane fenetre;
	
	/** The dao utilisateur. */
	DAOUtilisateur daoUtilisateur = new DAOUtilisateur();

	/**
	 * Lance le processus de connexion. <br>
	 * Hash le mot de passe entré, puis vérifie si il y a une combinaison identique a celle entrée dans la base de donnée. <br>
	 * Si il y en a une on crée un objet utilisateur avec les informations de l'utilisateur et on le redirige vers le menu principal <br>
	 * Si les deux champs sont vides, cela ouvre une session client très restreinte.
	 * 
	 * Affiche un message d'erreur si la combinaison est inconnue.
	 */
	@FXML 
	public void connexion(){
		try {
			String passHash = FonctionsControleurs.toHexString(FonctionsControleurs.getSHA(pass.getText()));
			if(daoUtilisateur.combinaisonEstValide(identifiant.getText(), passHash)) {
				setUtilisateurConnecte(daoUtilisateur.connexion(identifiant.getText(), passHash));
				daoUtilisateur.log(getUtilisateurConnecte());
				afficherMenuPrincipal();
			} else if(identifiant.getText().isEmpty() && pass.getText().isEmpty()) {
				setUtilisateurConnecte(new Utilisateur(0, "client", null));
				afficherMenuPrincipal();
			}else{
				FonctionsControleurs.alerteErreur("Erreur de connexion", "Combinaison identifiant/mot de passe incorrecte", "Veuillez réessayer.");
			}
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Un erreur est survenue!", "Détails: "+e);
		}

	}
	
	/**
	 * Permet à l'utilisateur d'appuyer sur entrée lors de la saisie pour lancer le processus de connexion.
	 *
	 * @param key the key
	 */
	@FXML
	public void keyPressed(KeyEvent key) {
		KeyCode kc = key.getCode();
		if (kc == KeyCode.ENTER) {
			connexion();
		}
	}
	
	/**
	 * Affiche le menu principal.
	 */
	@FXML
	public void afficherMenuPrincipal() {
		try {
			Locale locale = new Locale("fr", "FR");

			ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_fr", locale);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/MenuPrincipal.fxml"), bundle);
			Parent menuPrincipal = (Parent) loader.load();
			fenetre.getChildren().setAll(menuPrincipal); // remplace la fenetre de connexion par celle du menu principal
			
			MenuPrincipalControleur controller = loader.getController();
			controller.setParent(this);
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Une erreur est survenue","Détails: "+e);
			e.printStackTrace();
		}
	}


	/**
	 * Défini Connexion.java comme parent
	 *
	 * @param connexion the new parent
	 */
    public void setParent(Connexion connexion) {
        this.parent = connexion;
    }
	
	/**
	 * Défini MenuPrincipalControleur comme parent lorsque on tente d'accéder à la page connexion (se deconnecter) à partir du menu principal.
	 *
	 * @param menuPrincipalControleurTest the new main app
	 */
	public void setMainApp(MenuPrincipalControleur menuPrincipalControleurTest) {
		this.mainApp = menuPrincipalControleurTest;
		
	}

	/**
	 * Gets the utilisateur connecte.
	 *
	 * @return the utilisateur connecte
	 */
	public static Utilisateur getUtilisateurConnecte() {
		return utilisateurConnecte;
	}

	/**
	 * Sets the utilisateur connecte.
	 *
	 * @param utilisateurConnecte the new utilisateur connecte
	 */
	public static void setUtilisateurConnecte(Utilisateur utilisateurConnecte) {
		ConnexionControleur.utilisateurConnecte = utilisateurConnecte;
	}

}
