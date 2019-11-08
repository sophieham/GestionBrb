package gestionbrb.vue;

import java.sql.SQLException;

import gestionbrb.Comptes;
import gestionbrb.controleur.FonctionsControleurs;
import gestionbrb.model.Compte;
import gestionbrb.model.Table;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifierCompteControleur extends FonctionsControleurs {

	@FXML
	private TextField champNom;
	@FXML
	private TextField champPrenom;
	@FXML
	private TextField champIdentifiant;
	@FXML
	private TextField champMot2Passe;
	@FXML
	private TextField champPrivileges;

	private Stage dialogStage;
	private Compte compte;
	private boolean okClicked = false;
	private Comptes mainApp;

	@FXML
	private void initialize() {
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setMainApp(Comptes mainApp) {
		this.mainApp = mainApp;
	}

	/**
	 * 
	 * @param reservation
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void setCompte(Compte compte) throws SQLException, ClassNotFoundException {
		this.compte = compte;
		champNom.setText("");
		champPrenom.setText("");
		champIdentifiant.setText("");
		champMot2Passe.setText("");
		champIdentifiant.setText("");

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
			compte.setNom(champNom.getText());
			compte.setPrenom(champPrenom.getText());
			compte.setIdentifiant(champIdentifiant.getText());
			compte.setMot2passe(champIdentifiant.getText());
			compte.setPrivileges(Integer.parseInt(champPrivileges.getText()));

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

		if (champPrivileges.getText() == null || champPrivileges.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le champ privilèges\n";
		} else {
			try {
				Integer.parseInt(champNom.getText());
			} catch (NumberFormatException e) {
				erreurMsg += "Erreur! Le champ privilèges n'accepte que les nombres\n";
			}
			if(Integer.parseInt(champPrivileges.getText()) < 0 || Integer.parseInt(champPrivileges.getText())>2)
					erreurMsg+="saisie des privilèges invalide";
					// ajout d'une fonction de verification de privilèges
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
			alerteErreur("Entrée incorrecte", "Corrigez les erreurs suivantes pour pouvoir modifier la reservation",
					erreurMsg);

			return false;
		}
	}

}
