package gestionbrb.controleur;

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

	@FXML 
	public void connexion(){
		try {
			if(daoUtilisateur.combinaisonEstValide(identifiant.getText(), pass.getText())) {
				setUtilisateurConnecte(daoUtilisateur.connexion(identifiant.getText(), pass.getText()));
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

	@FXML
	public void keyPressed(KeyEvent key) {
		KeyCode kc = key.getCode();
		if (kc == KeyCode.ENTER) {
			connexion();
		}
	}
	@FXML
	public void afficherMenuPrincipal() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/MenuPrincipal.fxml"));
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
	 * @param connexion
	 */
    public void setParent(Connexion connexion) {
        this.parent = connexion;
    }
	
	/**
	 * Défini MenuPrincipalControleur comme parent lorsque on tente d'accéder à la page connexion (se deconnecter) à partir du menu principal
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
