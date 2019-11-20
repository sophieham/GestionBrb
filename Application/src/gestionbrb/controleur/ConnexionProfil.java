package gestionbrb.controleur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import gestionbrb.Connexion;
import gestionbrb.model.Fournisseur;
import gestionbrb.model.Utilisateur;
import gestionbrb.util.bddUtil;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConnexionProfil{	
	
	@FXML private TextField identifiant;
	@FXML private PasswordField pass;
	@FXML private Label etat; // a enlever lorsque la page sera 100% reliée
	Connexion mainApp;
	private Stage dialogStage;
	private Utilisateur compte;
	private boolean okClicked = false;
	
	public void setCompte(Utilisateur compte) throws SQLException, ClassNotFoundException {
		this.compte = compte;
		identifiant.setText(compte.getIdentifiant());
		pass.setText(compte.getMotdepasse());
	}
		
	public boolean isOkClicked() {
		return okClicked;
	}
    public void setMainApp(Connexion mainApp) {
        this.mainApp = mainApp;
    }
    public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	@FXML 
	public void Connexion() throws SQLException, ClassNotFoundException {
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
				etat.setText("Connecté"); // a enlever quand la redirection sera 100% fonctionnelle
				// en attente du menu principal pour faire la redirection
			}else {
				FonctionsControleurs.alerteErreur("Erreur de connexion", "Combinaison identifiant/mot de passe incorrecte", "Veuillez réessayer.");
			}
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Un erreur est survenue!", "Détails: "+e);
		}

	}


	
	
	



}
