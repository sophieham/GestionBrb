package gestionbrb.vue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import gestionbrb.ControleurFonctions;
import gestionbrb.model.Reservations;
import gestionbrb.util.bddUtil;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifierCalendrierControleur extends ControleurFonctions  {

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
     * Rempli les champs avec les données déjà existantes sur la réservation.
     * 
     * @param reservation
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    public void setReservation(Reservations reservation) throws SQLException, ClassNotFoundException {
        this.reservation = reservation;
		Connection conn = bddUtil.dbConnect();
		ResultSet rs = conn.createStatement().executeQuery("select * from calendrier");
		while (rs.next()) {
			champNom.setText(rs.getString("nom"));
			champPrenom.setText(rs.getString("prenom"));
			champNumTel.setText(rs.getString("numeroTel"));
			champDate.setPromptText(rs.getString("dateReservation"));
			champHeure.setText(rs.getString("heureReservation"));
			champNbCouverts.setText(Integer.toString(rs.getInt("nbCouverts")));
			champDemandeSpe.setText(rs.getString("demandeSpe"));
		}
    }

    /** 
     * @return true si le bouton a modifié a été appuyé, faux sinon
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Appellé quand l'utilisateur appuie sur "modifier"
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
        			+ "`nbCouverts` = '"+Integer.parseInt(champNbCouverts.getText())+"', "
        			+ "`demandeSpe` = '"+champDemandeSpe.getText()+"' "
        			+ "WHERE `calendrier`.`idReservation` = '"+reservation.getID()+"'");

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Appellé quand le bouton annuler est appuyé
     */
    @FXML
    private void actionAnnuler() {
        dialogStage.close();
    }
    
}