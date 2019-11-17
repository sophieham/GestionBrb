package gestionbrb.vue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import gestionbrb.Connexion;
import gestionbrb.controleur.FonctionsControleurs;
import gestionbrb.model.Fournisseur;
import gestionbrb.model.Utilisateur;
import gestionbrb.util.bddUtil;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConnexionProfil extends FonctionsControleurs{	
	
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
		PreparedStatement stat = null;
		ResultSet rs = null; 
		String sql = "SELECT * FROM utilisateurs WHERE identifiant = ? AND pass = ?";
		try {
			stat = con.prepareStatement(sql);
			stat.setString(1, identifiant.getText().toString());
			stat.setString(2, pass.getText().toString());
			rs = stat.executeQuery();
			if(rs.next()) {
				etat.setText("Connecté"); // a enlever quand la redirection sera 100% fonctionnelle
				// en attente du menu principal pour faire la redirection
			}else {
				alerteErreur("Erreur de connexion", "Combinaison identifiant/mot de passe incorrecte", "Veuillez réessayer.");
			}
		} catch (Exception e) {
			alerteErreur("Erreur", "Un erreur est survenue!", "Détails: "+e);
		}

	}


	
	
	



}
