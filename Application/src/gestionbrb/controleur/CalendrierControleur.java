package gestionbrb.controleur;

import java.sql.SQLException;

import gestionbrb.DAO.DAOCalendrier;
import gestionbrb.model.Reservations;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * 
 * @author Sophie
 *
 */

public class CalendrierControleur {
	
    private static ObservableList<Reservations> reservationData = FXCollections.observableArrayList();

	@FXML
	private TableView<Reservations> reservationTable;
	@FXML
	private TableColumn<Reservations, String> colonneNom;
	@FXML
	private TableColumn<Reservations, String> colonneDate;
	@FXML
	private TableColumn<Reservations, String> colonneHeure;
	@FXML
	private TableColumn<Reservations, Number> colonneNbCouverts;

	@FXML
	private Label champID;
	@FXML
	private Label champNom;
	@FXML
	private Label champPrenom;
	@FXML
	private Label champNumTel;
	@FXML
	private Label champDate;
	@FXML
	private Label champHeure;
	@FXML
	private Label champNbCouverts;
	@FXML
	private Label champDemandeSpe;
	@FXML
	private DatePicker rechercheDate;
	@FXML
	private Label nbTotalReservations;

	DemarrerCommandeControleur mainApp;
	
	DAOCalendrier daoCalendrier = new DAOCalendrier();

	public CalendrierControleur() {
	}

	/**
	 * Initialise la classe controleur avec les données par défaut du tableau <br>
	 * <br>
	 * Affiche un message d'erreur si il y a un problème.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */

	@FXML
	private void initialize() {

		colonneNom.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
		colonneDate.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
		colonneHeure.setCellValueFactory(cellData -> cellData.getValue().heureProperty());
		colonneNbCouverts.setCellValueFactory(cellData -> cellData.getValue().nbCouvertsProperty());
		try {
			colonneNom.setResizable(false);
			colonneDate.setResizable(false);
			colonneHeure.setResizable(false);
			colonneNbCouverts.setResizable(false);
			nbTotalReservations.setText(daoCalendrier.nombreTotalRsv()+" réservations au total");
			reservationTable.getSelectionModel().selectedItemProperty().addListener((observable, ancienneValeur, nouvelleValeur) -> {
					try {
						detailsReservation(nouvelleValeur);
					} catch (Exception e) {
						FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue","Détails: "+e);
						e.printStackTrace();
					}
			});
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue","Détails: "+e);
			e.printStackTrace();
		}

	}

	/**
	 * Appellé par la classe principale pour faire la liaison avec le controleur
	 * 
	 * @param mainApp
	 */
	public void setMainApp(DemarrerCommandeControleur mainApp) {
		this.mainApp = mainApp;
		reservationTable.setItems(reservationData);
	}

	/**
	 * Remplit tous les champs avec la reservation séléctionnée dans la partie
	 * détails. Si il n'y a pas de reservation séléctionnée, les champs sont vides.
	 * <br>
	 * <br>
	 * Si l'affiche génére une erreur, une boite d'erreur est affichée
	 * 
	 * @param reservation s'il en existe une, null sinon
	 * @throws SQLException
	 */

	private void detailsReservation(Reservations reservation) throws SQLException, ClassNotFoundException {
		try {
			if (reservation != null) {
				champID.setText(Integer.toString(reservation.getID()));
				champNom.setText(reservation.getNom());
				champPrenom.setText(reservation.getPrenom());
				champNumTel.setText(reservation.getNumTel());
				champDate.setText(reservation.getDate());
				champHeure.setText(reservation.getHeure());
				champNbCouverts.setText(Integer.toString(reservation.getNbCouverts()));
				champDemandeSpe.setText(reservation.getDemandeSpe());
			} else {
				champID.setText("");
				champNom.setText("");
				champPrenom.setText("");
				champNumTel.setText("");
				champDate.setText("");
				champHeure.setText("");
				champNbCouverts.setText("");
				champDemandeSpe.setText("");
			}
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue","Détails: "+e);
		}
	}
	

	
	/**
	 * Appellé quand l'utilisateur clique sur le bouton supprimer <br>
	 * Supprime la réservation séléctionnée.
	 * <br>
	 * <br>
	 * Si la supression génère une erreur, une fenêtre d'erreur s'affiche.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void supprimerReservation() throws ClassNotFoundException, SQLException {
		Reservations reservationSelectionnee = reservationTable.getSelectionModel().getSelectedItem();
		int indexSelection = reservationTable.getSelectionModel().getSelectedIndex();
		if (indexSelection >= 0) {
			try {
				daoCalendrier.supprimer(reservationSelectionnee);
				reservationTable.getItems().remove(indexSelection);
			} catch (Exception e) {
				FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue","Détails: "+e);
				e.printStackTrace();
			}
		} else {
			FonctionsControleurs.alerteAttention("Aucune sélection", "Aucune réservation de sélectionnée!", "Selectionnez une réservation pour pouvoir la modifier");
		}
	}

	/**
	 * Appellé quand l'utilisateur clique sur le bouton modifier la réservation. <br>
	 * Ouvre une nouvelle page pour effectuer la modification et remplit le formulaire avec les informations déjà existantes.
	 * <br>
	 * <br>
	 * Une fenetre d'information est affichée si tout se passe bien, sinon c'est une boite d'erreur qui est affichée.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void modifierReservation() throws ClassNotFoundException, SQLException {
		Reservations selectedReservation = reservationTable.getSelectionModel().getSelectedItem();
		if (selectedReservation != null) {
			try {
				boolean okClicked = fenetreModification(selectedReservation);
				if (okClicked) {
					daoCalendrier.modifier(selectedReservation);
					initialize();
					FonctionsControleurs.alerteInfo("Modification éffectuée", null, "Les informations ont été modifiées avec succès!");
				}
			} catch (Exception e) {
				FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue","Détails: "+e);
				e.printStackTrace();
			}

		} else {
			// Si rien n'est selectionné
			FonctionsControleurs.alerteAttention("Aucune sélection", "Aucune réservation de sélectionnée!", "Selectionnez une réservation pour pouvoir la modifier");
		}
	}

	/**
	 * Appellé quand on sélectionne une date. <br>
	 * Outil de recherche qui affiche uniquement les réservations à la date sélectionnée.
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@FXML
	private void rechercherReservation() throws ClassNotFoundException, SQLException {
		reservationTable.getItems().clear();
		String date = rechercheDate.getValue().toString();
		detailsReservation(null);
		try {
			nbTotalReservations.setText(daoCalendrier.nombreTotalRsv(date)+" réservations le "+date);
			daoCalendrier.recherche(date);
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue","Détails: "+e);
			e.printStackTrace();
		}

	}
	
	
	/**
	 * Appellé quand l'utilisateur appuie sur le bouton Afficher tout.
	 * Affiche toutes les réservations quelquesoit la date (annule la recherche)
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@FXML
	public void afficherTout() throws ClassNotFoundException, SQLException {
		reservationTable.getItems().clear();
		try {
			nbTotalReservations.setText(daoCalendrier.nombreTotalRsv()+" réservations au total");
			reservationTable.setItems(daoCalendrier.afficher());
			detailsReservation(null);
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue","Détails: "+e);
			e.printStackTrace();
		}
	}

	
	 /**
     * Ouvre une fenêtre pour modifier les reservations.
     * 
     * @param reservation la reservation a modifier
     * @return true si l'utilisateur appuie sur modifier, false sinon
     */
    
    public static boolean fenetreModification(Reservations reservation) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CalendrierControleur.class.getResource("../vue/ModifierReservation.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Modifier une reservation");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            ModifierCalendrierControleur controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setReservation(reservation);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (Exception e) {
            FonctionsControleurs.alerteErreur("Erreur d'éxécution", null, "Détails:"+e);
            return false;
        }
    }

    public static ObservableList<Reservations> getReservationData() {
        return reservationData;
    }

}