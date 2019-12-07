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
/**
 * 
 * @author Leo
 *
 */
public class ConnexionControleur extends Connexion {	
	
	@FXML private TextField identifiant;
	@FXML private PasswordField pass;
	Connexion parent;
	private static Utilisateur utilisateurConnecte;
	@SuppressWarnings("unused")
	private MenuPrincipalControleur mainApp;
	
	@FXML
	private AnchorPane fenetre;
	
	DAOUtilisateur daoUtilisateur = new DAOUtilisateur();

	/**
	 * Lance le processus de connexion. <br>
	 * Hash le mot de passe entr�, puis v�rifie si il y a une combinaison identique a celle entr�e dans la base de donn�e. <br>
	 * Si il y en a une on cr�e un objet utilisateur avec les informations de l'utilisateur et on le redirige vers le menu principal <br>
	 * Si les deux champs sont vides, cela ouvre une session client tr�s restreinte.
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
				FonctionsControleurs.alerteErreur("Erreur de connexion", "Combinaison identifiant/mot de passe incorrecte", "Veuillez r�essayer.");
			}
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Un erreur est survenue!", "D�tails: "+e);
		}

	}
	/**
	 * Permet � l'utilisateur d'appuyer sur entr�e lors de la saisie pour lancer le processus de connexion
	 * @param key
	 */
	@FXML
	public void keyPressed(KeyEvent key) {
		KeyCode kc = key.getCode();
		if (kc == KeyCode.ENTER) {
			connexion();
		}
	}
	
	/**
	 * Affiche le menu principal
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
			FonctionsControleurs.alerteErreur("Erreur d'�x�cution", "Une erreur est survenue","D�tails: "+e);
			e.printStackTrace();
		}
	}


	/**
	 * D�fini Connexion.java comme parent
	 * @param connexion
	 */
    public void setParent(Connexion connexion) {
        this.parent = connexion;
    }
	
	/**
	 * D�fini MenuPrincipalControleur comme parent lorsque on tente d'acc�der � la page connexion (se deconnecter) � partir du menu principal
	 * @param connexion
	 */
	public void setMainApp(MenuPrincipalControleur menuPrincipalControleurTest) {
		this.mainApp = menuPrincipalControleurTest;
		
	}

	public static Utilisateur getUtilisateurConnecte() {
		return utilisateurConnecte;
	}

	public static void setUtilisateurConnecte(Utilisateur utilisateurConnecte) {
		ConnexionControleur.utilisateurConnecte = utilisateurConnecte;
	}

}
