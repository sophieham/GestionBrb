package gestionbrb.controleur;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gestionbrb.DAO.DAOCalendrier;
import gestionbrb.model.Reservations;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * 
 * @author Sophie
 *
 */
public class ModifierCalendrierControleur implements Initializable{

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
    private TextField champNbCouvertsReservation;
    @FXML
    private TextField champDemandeSpe;

    DAOCalendrier daoCalendrier = new DAOCalendrier();
    
    private Stage dialogStage;
    @SuppressWarnings("unused")
	private Reservations reservation;
    private boolean okClicked = false;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Remplit les champs avec les données déjà existantes sur la réservation.
     * 
     * @param reservation
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    public void setReservation(Reservations r) {
    	try {
			this.reservation = r;
				champNom.setText(r.getNom());
				champPrenom.setText(r.getPrenom());
				champNumTel.setText(r.getNumTel());
				champDate.setValue(LocalDate.parse(r.getDate()));
				champHeure.setText(r.getHeure());
				champNbCouvertsReservation.setText(Integer.toString(r.getNbCouverts()));
				champDemandeSpe.setText(r.getDemandeSpe());
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue","Détails: "+e);
			e.printStackTrace();
		}
    }

    /** 
     * @return true si le bouton a modifié a été appuyé, faux sinon
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
	 * Appellé quand l'utilisateur appuie sur "modifier" <br>
	 * Modifie la réservation si tout les champs sont valides
	 */
	@FXML
	private void actionModifier() {
	    if (estValide()) {
	    	try {
				reservation.setNom(champNom.getText());
				reservation.setPrenom(champNom.getText());
				reservation.setNumTel(champNumTel.getText());
				reservation.setDate(champDate.getValue());
				reservation.setHeure(champHeure.getText());
				reservation.setNbCouverts(Integer.parseInt(champNbCouvertsReservation.getText()));
				reservation.setDemandeSpe(champDemandeSpe.getText());
			} catch (Exception e) {
				FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue","Détails: "+e);
				e.printStackTrace();
			}
	        okClicked = true;
	        dialogStage.close();
	    }
	}

    /**
     * Appellé quand le bouton annuler est appuyé.
     * Ferme la page sans sauvegarder.
     */
    @FXML
    private void actionAnnuler() {
        dialogStage.close();
    }
    
    /**
	 * Vérifie si la saisie est conforme aux données requises parfois à l'aide de regex.
	 * Affiche un message d'erreur si il y a au moins une erreur.
	 * 
	 * @return true si la saisie est bien conforme, false sinon
	 */
	public boolean estValide() {
		String msgErreur = "";

		if (champNom.getText() == null || champNom.getText().length() == 0) {
			msgErreur += "Veuillez remplir le nom\n";
		}
		if (champPrenom.getText() == null || champPrenom.getText().length() == 0) {
			msgErreur += "Veuillez remplir le prénom\n";
		}
		if (champNumTel.getText() == null || champNumTel.getText().length() == 0) {
			msgErreur += "Veuillez rentrer le numéro de téléphone\n";
		} else {
			Pattern p = Pattern.compile("(0|\\+)[0-9]{8,12}");
			Matcher m = p.matcher(champNumTel.getText());
			if (!(m.find() && m.group().equals(champNumTel.getText()))) {
				msgErreur += "Erreur! Le champ no. téléphone n'accepte que les numéros commençant par + ou 0 et ayant une longueur entre 8 et 12 chiffres\n";
			}
		}
		if (champDate.getValue() == null) {
			msgErreur += "Veuillez selectionner la date\n";
		}

		if (champHeure.getText() == null || champHeure.getText().length() == 0) {
			msgErreur += "Veuillez rentrer l'heure\n";
		} else {
			Pattern heurep = Pattern.compile("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$");
			Matcher heurem = heurep.matcher(champHeure.getText());
			if (!(heurem.find() && heurem.group().equals(champHeure.getText()))) {
				msgErreur += "Format de l'heure incorrect, veuillez réessayer avec le format hh:mm approprié\n";
			}
		}

		if (champNbCouvertsReservation.getText() == null || champNbCouvertsReservation.getText().length() == 0) {
			msgErreur += "Veuillez rentrer le nombre de couverts!\n";
		} else {
			try {
				Integer.parseInt(champNbCouvertsReservation.getText());
						
			} catch (NumberFormatException e) {
				msgErreur += "Erreur! Le champ \"nombre de couverts\" n'accepte que les nombres\n";
			}
		}
		if (msgErreur.length() == 0) {
			return true;
		} else {
			FonctionsControleurs.alerteErreur("Entrée incorrecte", "Corrigez les erreurs suivantes pour pouvoir modifier la reservation",msgErreur);
			return false;
		}
	}

}