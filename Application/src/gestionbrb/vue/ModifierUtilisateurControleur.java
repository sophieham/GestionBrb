package gestionbrb.vue;

import java.sql.SQLException;

import gestionbrb.Utilisateurs;
import gestionbrb.controleur.FonctionsControleurs;
import gestionbrb.model.Table;
import gestionbrb.model.Utilisateur;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifierUtilisateurControleur extends FonctionsControleurs {

	@FXML
	private TextField champNom;
	@FXML
	private TextField champPrenom;
	@FXML
	private TextField champIdentifiant;
	@FXML
	private TextField champMot2Passe;
	@FXML
	private TextField champRoles;

	private Stage dialogStage;
	private Utilisateur compte;
	private boolean okClicked = false;
	private Utilisateurs mainApp;

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
		champMot2Passe.setText(compte.getMot2passe());

	}

	/**
	 * @return true si le bouton a modifi� a �t� appuy�, faux sinon
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Appell� quand l'utilisateur appuie sur Valider
	 * 
	 */
	@FXML
	public void actionValider() {
		if (estValide()) {
			compte.setNom(champNom.getText());
			compte.setPrenom(champPrenom.getText());
			compte.setIdentifiant(champIdentifiant.getText());
			compte.setMot2passe(champMot2Passe.getText());
			compte.setPrivileges(Integer.parseInt(champRoles.getText()));

			okClicked = true;
			dialogStage.close();
		}	
	}

	/**
	 * Appell� quand le bouton annuler est appuy�. Ferme la page sans sauvegarder.
	 */
	@FXML
	private void actionAnnuler() {
		dialogStage.close();
	}

	/**
	 * V�rifie si la saisie est conforme aux donn�es requises
	 * 
	 * @return true si la saisie est bien conforme
	 */
	public boolean estValide() {
		String erreurMsg = "";

		if (champRoles.getText() == null || champRoles.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le champ privil�ges\n";
		} else {
			try {
				Integer.parseInt(champNom.getText());
			} catch (NumberFormatException e) {
				erreurMsg += "Erreur! Le champ privil�ges n'accepte que les nombres\n";
			}
			if(Integer.parseInt(champRoles.getText()) < 0 || Integer.parseInt(champRoles.getText())>2)
					erreurMsg+="saisie des privil�ges invalide";
					// ajout d'une fonction de verification de privil�ges
		}

		if (champPrenom.getText() == null | champPrenom.getText().length() == 0) {
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
			alerteErreur("Entr�e incorrecte", "Corrigez les erreurs suivantes pour pouvoir modifier la reservation",
					erreurMsg);

			return false;
		}
	}

}
