package gestionbrb.controleur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import gestionbrb.Connexion;
import gestionbrb.model.Utilisateur;
import gestionbrb.util.bddUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
	private Utilisateur compte;
	private MenuPrincipalControleur mainApp;
	
	@FXML
	private AnchorPane fenetre;

	@FXML 
	public void connexion() throws SQLException, ClassNotFoundException {
		Connection con = bddUtil.dbConnect();
		PreparedStatement requete = null;
		ResultSet connex = null; 
		String sql = "SELECT * FROM utilisateurs WHERE identifiant = ? AND pass = ?";
		try {
			requete = con.prepareStatement(sql);
			requete.setString(1, identifiant.getText().toString());
			requete.setString(2, pass.getText().toString());
			connex = requete.executeQuery();
			if(connex.next()) {
				afficherMenuPrincipal();
			}else {
				FonctionsControleurs.alerteErreur("Erreur de connexion", "Combinaison identifiant/mot de passe incorrecte", "Veuillez réessayer.");
			}
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Un erreur est survenue!", "Détails: "+e);
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

}
