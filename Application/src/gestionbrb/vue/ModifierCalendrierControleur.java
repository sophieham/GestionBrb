package gestionbrb.vue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gestionbrb.controleur.FonctionsControleurs;
import gestionbrb.model.Reservations;
import gestionbrb.util.bddUtil;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifierCalendrierControleur extends FonctionsControleurs{

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


    private Stage dialogStage;
    private Reservations reservation;
    private boolean okClicked = false;


    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Rempli les champs avec les donn�es d�j� existantes sur la r�servation.
     * 
     * @param reservation
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    public void setReservation(Reservations reservation) throws SQLException, ClassNotFoundException {
        this.reservation = reservation;
		Connection conn = bddUtil.dbConnect();
		ResultSet rs = conn.createStatement().executeQuery("select * from calendrier where idReservation = "+reservation.getID());
		while (rs.next()) {
			champNom.setText(rs.getString("nom"));
			champPrenom.setText(rs.getString("prenom"));
			champNumTel.setText(rs.getString("numeroTel"));
			champDate.setValue(LocalDate.parse(rs.getString("dateReservation")));
			champHeure.setText(rs.getString("heureReservation"));
			champNbCouvertsReservation.setText(Integer.toString(rs.getInt("nbCouverts")));
			champDemandeSpe.setText(rs.getString("demandeSpe"));
		}
    }

    /** 
     * @return true si le bouton a modifi� a �t� appuy�, faux sinon
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Appell� quand l'utilisateur appuie sur "modifier"
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * @throws NumberFormatException 
     */
    @FXML
    private void actionModifier() throws NumberFormatException, ClassNotFoundException, SQLException {
        if (estValide()) {
        	bddUtil.dbQueryExecute("UPDATE `calendrier` SET "
        			+ "`nom` = '"+champNom.getText()+"', "
        			+ "`prenom` = '"+champPrenom.getText()+"', "
        			+ "`numeroTel` = '"+champNumTel.getText()+"', "
        			+ "`dateReservation` = '"+champDate.getValue()+"', "
        			+ "`heureReservation` = '"+champHeure.getText()+"', "
        			+ "`nbCouverts` = '"+Integer.parseInt(champNbCouvertsReservation.getText())+"', "
        			+ "`demandeSpe` = '"+champDemandeSpe.getText()+"' "
        			+ "WHERE `calendrier`.`idReservation` = '"+reservation.getID()+"'");
            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Appell� quand le bouton annuler est appuy�.
     * Ferme la page sans sauvegarder.
     */
    @FXML
    private void actionAnnuler() {
        dialogStage.close();
    }
    
    /**
	 * V�rifie si la saisie est conforme aux donn�es requises
	 * 
	 * @return true si la saisie est bien conforme, false sinon
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
			Pattern p = Pattern.compile("(0|\\+)[0-9]{8,12}"); // regex d'un num�ro de t�l�phone, fran�ais ou �tranger
			Matcher m = p.matcher(champNumTel.getText());
			if (!(m.find() && m.group().equals(champNumTel.getText()))) {
				errorMessage += "Erreur! Le champ no. t�l�phone n'accepte que les num�ros commen�ant par + ou 0 et ayant une longueur entre 8 et 12 chiffres\n";
			}
		}
		if (champDate.getValue() == null) {
			errorMessage += "Veuillez selectionner la date\n";
		}

		if (champHeure.getText() == null || champHeure.getText().length() == 0) {
			errorMessage += "Veuillez rentrer l'heure\n";
		} else {
			Pattern heurep = Pattern.compile("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$"); // regex pour afficher une heure
																						// valide sous forme hh:mm
			Matcher heurem = heurep.matcher(champHeure.getText());
			if (!(heurem.find() && heurem.group().equals(champHeure.getText()))) {
				errorMessage += "Format de l'heure incorrect, veuillez r�essayer avec le format hh:mm appropri�\n";
			}
		}

		if (champNbCouvertsReservation.getText() == null || champNbCouvertsReservation.getText().length() == 0) {
			errorMessage += "Veuillez rentrer le nombre de couverts!\n";
		} else {
			try {
				Integer.parseInt(champNbCouvertsReservation.getText()); // transformation en int pour voir si la saisie est un
																// chiffre
			} catch (NumberFormatException e) {
				errorMessage += "Erreur! Le champ \"nombre de couverts\" n'accepte que les nombres\n";
			}
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			alerteErreur("Entr�e incorrecte", "Corrigez les erreurs suivantes pour pouvoir modifier la reservation",errorMessage);
			return false;
		}
	}

}