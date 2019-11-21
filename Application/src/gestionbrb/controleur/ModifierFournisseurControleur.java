package gestionbrb.controleur;

import java.sql.SQLException;

import gestionbrb.Fournisseurs;
import gestionbrb.model.Fournisseur;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * 
 * @author Roman
 *
 */
public class ModifierFournisseurControleur {

	@FXML 
	private TextField champNom;
	@FXML
	private TextField champNumTel;
	@FXML
	private TextField champMail;
	@FXML
	private TextField champAdresse;
	@FXML
	private TextField champVille;
	@FXML 
	private TextField champCodePostal;
	
	private Stage dialogStage;
	private Fournisseur fournisseur;
	private boolean okClicked = false;
	Fournisseurs mainApp;
	
	@FXML
	private void initialize() {
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setMainApp(Fournisseurs mainApp) {
		this.mainApp = mainApp;
	}

	/**
	 * 
	 * @param reservation
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void setFournisseur(Fournisseur fournisseur) throws SQLException, ClassNotFoundException {
		this.fournisseur = fournisseur;
		champNom.setText(fournisseur.getNom());
		champAdresse.setText(fournisseur.getAdresse());
		champMail.setText(fournisseur.getMail());
		champNumTel.setText(Integer.toString(fournisseur.getNumTel()));
		champVille.setText(fournisseur.getNomVille());
		champCodePostal.setText(Integer.toString(fournisseur.getCodePostal()));
		

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
			fournisseur.setNom(champNom.getText());
			fournisseur.setAdresse(champAdresse.getText());
			fournisseur.setMail(champMail.getText());
			fournisseur.setNumTel(Integer.parseInt(champNumTel.getText()));
			fournisseur.setNomVille(champVille.getText());
			fournisseur.setCodePostal(Integer.parseInt(champCodePostal.getText()));
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

		

		if (champAdresse.getText() == null || champAdresse.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le champ Adresse\n";

		}
		if (champCodePostal.getText() == null || champCodePostal.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le champ Code Postal\n";

		}
		if (champNom.getText() == null || champNom.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le champ nom\n";

		}
		if (champMail.getText() == null || champMail.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le champ mail\n";

		}
		if (champNumTel.getText() == null || champNumTel.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le champ numéro de telephone\n";
		}
		if (champVille.getText() == null || champVille.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le champ ville\n";
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
