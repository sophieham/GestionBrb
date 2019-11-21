package gestionbrb.controleur;

import java.sql.SQLException;

import gestionbrb.Utilisateurs;
import gestionbrb.model.Utilisateur;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
/**
 * 
 * @author Roman
 *
 */
public class ModifierUtilisateurControleur {

	@FXML 
	private TextField champNom;
	@FXML
	private TextField champPrenom;
	@FXML
	private TextField champIdentifiant;
	@FXML
	private TextField champMot2Passe;
	@FXML
	private RadioButton boutonAdmin;
	@FXML
	private RadioButton boutonServeur;
	private Stage dialogStage;
	private Utilisateur compte;
	private boolean okClicked = false;
	private int role;
	@SuppressWarnings("unused")
	private Utilisateurs mainApp;
	
	@FXML
	private void setRoleAdmin() {
		role = 1;
		boutonServeur.setSelected(false);

	}
	
	@FXML
	private void setRoleServeur() {
		role = 1;
		boutonAdmin.setSelected(false);

	}
	
	@FXML
	private void initialize() {
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setMainApp(Utilisateurs mainApp) {
		this.mainApp = mainApp;
	}

	/**
	 * 
	 * @param reservation
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void setUtilisateur(Utilisateur compte) throws SQLException, ClassNotFoundException {
		this.compte = compte;
		champNom.setText(compte.getNom());
		champPrenom.setText(compte.getPrenom());
		champIdentifiant.setText(compte.getIdentifiant());
		champMot2Passe.setText(compte.getMotdepasse());
		if(compte.getPrivileges()==1)
			boutonAdmin.setSelected(true);
		if(compte.getPrivileges() == 0)
			boutonServeur.setSelected(true);

	}

	/**
	 * @return true si le bouton a modifié a été appuyé, faux sinon
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Appellé quand l'utilisateur appuie sur Valider
	 * 
	 */
	@FXML
	public void actionValider() {
		if (estValide()) {
			if(boutonAdmin.isSelected()) {
				role = 1;
			}
			if (boutonServeur.isSelected())
				role = 0;
			compte.setNom(champNom.getText());
			compte.setPrenom(champPrenom.getText());
			compte.setIdentifiant(champIdentifiant.getText());
			compte.setMot2passe(champMot2Passe.getText());
			compte.setPrivileges(role);

			okClicked = true;
			dialogStage.close();
		}	
	}

	/**
	 * Appellé quand le bouton annuler est appuyé. Ferme la page sans sauvegarder.
	 */
	@FXML
	private void actionAnnuler() {
		dialogStage.close();
	}

	/**
	 * Vérifie si la saisie est conforme aux données requises
	 * 
	 * @return true si la saisie est bien conforme
	 */
	public boolean estValide() {
		String erreurMsg = "";

		if (!boutonAdmin.isSelected() && !boutonServeur.isSelected()) {
			erreurMsg += "Veuillez cocher le role\n";
		} 

		if (champPrenom.getText() == null || champPrenom.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le champ prenom\n";

		}
		if (champNom.getText() == null || champNom.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le champ nom\n";

		}
		if (champMot2Passe.getText() == null || champMot2Passe.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le champ mot de passe\n";

		}
		if (champIdentifiant.getText() == null || champIdentifiant.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le champ Identifiant\n";
		}

		if (erreurMsg.length() == 0) {
			return true;
		} else {
			// Affiche un message d'erreur
			FonctionsControleurs.alerteErreur("Entrée incorrecte", "Corrigez les erreurs suivantes pour pouvoir modifier la reservation",erreurMsg);

			return false;
		}
	}

}
