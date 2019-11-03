package gestionbrb.vue;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gestionbrb.DemarrerCommande;
import gestionbrb.controleur.FonctionsControleurs;
import gestionbrb.util.bddUtil;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class DemarrerCommandeControleur extends FonctionsControleurs{
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
	private DemarrerCommande mainApp;
	// Reference to the main application.

	private boolean okClicked = false;

	public DemarrerCommandeControleur() {
	}

	@FXML
	private void initDemCommande() {
	}

	public void setMainApp(DemarrerCommande mainApp) {
		this.mainApp = mainApp;
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Appell� quand l'utilisateur clique sur r�server
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void actionAjouter() throws ClassNotFoundException, SQLException {
		if (estValide()) {
			// manque le numero de la table qui recevera la reservation
			bddUtil.dbQueryExecute("INSERT INTO `calendrier` (`idReservation`, `nom`, `prenom`, `numeroTel`, `dateReservation`, `HeureReservation`, `nbCouverts`, `demandeSpe`, `idTable`) "
							+ "VALUES (NULL, '" + champNom.getText() + "', '" + champPrenom.getText() + "','"+champNumTel.getText()+"' , '"+ champDate.getValue() + "', '" + champHeure.getText() + "', '" + champNbCouverts.getText()+ "', '"+champDemandeSpe.getText()+"', NULL);");
			alerteInfo("Reservation enregistr�e!", "", "La reservation � bien �t� enregistr�e!");
			champNom.clear();
			champPrenom.clear();
			champNumTel.clear();
			champDate.setValue(null);
			champHeure.clear();
			champNbCouverts.clear();
			champDemandeSpe.clear();

			okClicked = true;
		}
	}
	
	 /**
     * V�rifie si la saisie est conforme aux donn�es requises
     * 
     * @return true si la saisie est bien conforme
     */
	public boolean estValide() {
		String errorMessage = "";

		if (champNom.getText() == null || champNom.getText().length() == 0) {
			errorMessage += "Veuillez remplir le nom\n";
		}
		if (champPrenom.getText() == null || champPrenom.getText().length() == 0) {
			errorMessage += "Veuillez remplir le pr�nom\n";
		}
		if (champNumTel.getText() == null || champNumTel.getText().length() == 0) {
			errorMessage += "Veuillez rentrer le num�ro de t�l�phone\n";
		} else {
				Pattern p = Pattern.compile("(0|\\+)[0-9]{8,12}");
				Matcher m = p.matcher(champNumTel.getText());
				if (!(m.find() && m.group().equals(champNumTel.getText()))){
					errorMessage += "Erreur! Le champ no. t�l�phone n'accepte que les num�ros commen�ant par + ou 0 et ayant une longueur entre 8 et 12 chiffres\n";
				}
		}
		if (champDate.getValue() == null) {
			System.out.println(champDate.getValue());
			errorMessage += "Veuillez selectionner la date\n";
		}

		if (champHeure.getText() == null || champHeure.getText().length() == 0) {
			errorMessage += "Veuillez rentrer l'heure\n";
		} else {
			Pattern heurep = Pattern.compile("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$");
			Matcher heurem = heurep.matcher(champHeure.getText());
			if (!(heurem.find()&& heurem.group().equals(champHeure.getText()))) {
				errorMessage += "Format de l'heure incorrect, veuillez r�essayer avec le format hh:mm appropri�\n";
			}
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
			alerteErreur("Entr�e incorrecte", "Corrigez les erreurs suivantes pour pouvoir modifier la reservation", errorMessage);
			return false;
		}
	}
}