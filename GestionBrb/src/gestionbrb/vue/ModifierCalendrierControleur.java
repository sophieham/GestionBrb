package gestionbrb.vue;

import gestionbrb.model.Reservations;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Dialog to edit details of a reservation.
 * 
 * @author Marco Jakob
 */
public class ModifierCalendrierControleur {

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

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the reservation to be edited in the dialog.
     * 
     * @param reservation
     */
    public void setReservation(Reservations reservation) {
        this.reservation = reservation;

        champNom.setText(reservation.getNom());
        champPrenom.setText(reservation.getPrenom());
        champNumTel.setText(reservation.getNumTel());
        champDate.setPromptText(reservation.getDate());
        champHeure.setText(reservation.getHeure());
        champNbCouverts.setText(Integer.toString(reservation.getNbCouverts()));
        champDemandeSpe.setText(reservation.getDemandeSpe());
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     * 
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void actionModifier() {
        if (estValide()) {
            reservation.setNom(champNom.getText());
            reservation.setPrenom(champPrenom.getText());
            reservation.setNumTel(champNumTel.getText());
            reservation.setDate(champDate.getValue());
            reservation.setHeure(champHeure.getText());
            reservation.setNbCouverts(Integer.parseInt(champNbCouverts.getText()));
            reservation.setDemandeSpe(champDemandeSpe.getText());

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void actionAnnuler() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean estValide() {
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
            // try to parse the postal code into an int.
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
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(champNbCouverts.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Erreur! Le champ \"nombre de couverts\" n'accepte que les nombres\n"; 
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Entrée incorrecte");
            alert.setHeaderText("Corrigez les erreurs suivantes pour pouvoir modifier la réservation");
            alert.setContentText(errorMessage);
            
            alert.showAndWait();
            
            return false;
        }
    }
}