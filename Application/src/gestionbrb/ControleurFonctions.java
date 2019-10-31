package gestionbrb;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public abstract class ControleurFonctions {
	@FXML
	private TextField champNom;
	@FXML
	private TextField champPrenom;
	@FXML
	private TextField champNumTel;
	@FXML
	private DatePicker champDate;
	@FXML
	private TextField champHeure;
	@FXML
	private TextField champNbCouverts;
	@FXML
	private TextField champDemandeSpe;

    /**
     * Vérifie si la saisie est conforme aux données requises
     * 
     * @return true si la saisie est bien conforme
     */
	public boolean estValide() { // Manque la vérification du numéro de téléphone (format 0678458459), de l'heure
									// (format 12:30)
		String errorMessage = "";

		if (champNom.getText() == null || champNom.getText().length() == 0) {
			errorMessage += "Veuillez remplir le nom\n";
		}
		if (champPrenom.getText() == null || champPrenom.getText().length() == 0) {
			errorMessage += "Veuillez remplir le prénom\n";
		}
		if (champNumTel.getText() == null || champNumTel.getText().length() == 0) {
			errorMessage += "Veuillez rentrer le numéro de téléphone\n";
		} else {
			try {
				Integer.parseInt(champNumTel.getText());
			} catch (NumberFormatException e) {
				errorMessage += "Erreur! Le champ \"N° de téléphone\" n'accepte que les nombres\n";
			}
		}
		if (champDate.getValue() == null || champDate.getPromptText().length() == 0) {
			errorMessage += "Veuillez selectionner la date\n";
		}

		if (champHeure.getText() == null || champHeure.getText().length() == 0) {
			errorMessage += "Veuillez rentrer l'heure\n";
		}

		if (champNbCouverts.getText() == null || champNbCouverts.getText().length() == 0) {
			errorMessage += "Veuillez rentrer le nombre de couverts!\n";
		} else {
			// essaye de transformer la saisie en un nombre de type int
			try {
				Integer.parseInt(champNbCouverts.getText());
			} catch (NumberFormatException e) {
				errorMessage += "Erreur! Le champ \"nombre de couverts\" n'accepte que les nombres\n";
			}
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Affiche un message d'erreur
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Entrée incorrecte");
			alert.setHeaderText("Corrigez les erreurs suivantes pour pouvoir modifier la réservation");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}
	}

}
