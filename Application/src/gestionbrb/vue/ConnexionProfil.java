package gestionbrb.vue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import gestionbrb.Connexion;
import gestionbrb.util.bddUtil;
import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
public class ConnexionProfil {	
	
	@FXML private TextField txtF;
	@FXML private PasswordField passF;
	@FXML private Label lbletat;
	Connexion mainApp;
	
    public void setMainApp(Connexion mainApp) {
        this.mainApp = mainApp;
    }
	
	@FXML 
	public void Connexion() throws SQLException, ClassNotFoundException {
		Connection con = bddUtil.dbConnect();
		PreparedStatement stat = null;
		ResultSet rs = null; 
		String sql = "SELECT * FROM utilisateurs WHERE identifiant = ? AND mot2passe = ?";
		try {
			stat = con.prepareStatement(sql);
			stat.setString(1, txtF.getText().toString());
			stat.setString(2, passF.getText().toString());
			rs = stat.executeQuery();
			if(rs.next()) {
				lbletat.setText("Connecté"); // a enlever quand la redirection sera 100% fonctionnelle
				// en attente du menu principal pour faire la redirection
			}else {
	            Alert alert = new Alert(AlertType.ERROR);
	            alert.setTitle("Erreur");
	            alert.setHeaderText("Combinaison identifiant/mot de passe incorrect");
	            alert.setContentText("Veuillez réessayer.");
	            
	            alert.showAndWait();
			}
		} catch (Exception e) {
			
		}

	}
	
	



}
