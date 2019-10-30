package gestionbrb.vue;

import java.sql.SQLException;

import gestionbrb.DemarrerCommande;
import gestionbrb.model.Reservations;
import gestionbrb.util.bddUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;


public class DemarrerCommandeControleur {
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

    // Reference to the main application.
    private DemarrerCommande mainApp;
    private boolean okClicked = false;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public DemarrerCommandeControleur() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    
    // Initialize the reservation table with the two columns.
    @FXML
    private void initialize() {
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(DemarrerCommande mainApp) {
        this.mainApp = mainApp;
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
        	// manque le pattern du numero de tel & celui de l'heure + le numero de la table qui recevera la reservation
        	try {
				bddUtil.dbQueryExecute("INSERT INTO `calendrier` (`idReservation`, `nom`, `prenom`, `numeroTel`, `dateReservation`, `HeureReservation`, `nbCouverts`, `demandeSpe`, `idTable`) "
																	+ "VALUES (NULL, '"+champNom.getText()+"', '"+champPrenom.getText()+"', NULL, '"+champDate.getValue()+"', '"+champHeure.getText()+"', '"+champNbCouverts.getText()+"', NULL, NULL);");
			} catch (ClassNotFoundException e) {
				System.out.println("Classe non trouvée");
				e.printStackTrace();
			} catch (SQLException e) {
				System.out.println("Erreur dans le code SQL");
				e.printStackTrace();
			}
            okClicked = true;
        }
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
        if (champDate.getValue() == null) {
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
            //alert.initOwner(primaryStage);
            alert.setTitle("Entrée incorrecte");
            alert.setHeaderText("Corrigez les erreurs suivantes pour pouvoir modifier la réservation");
            alert.setContentText(errorMessage);
            
            alert.showAndWait();
            
            return false;
        }
    }



        
    
}