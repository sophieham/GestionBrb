package gestionbrb.controleur;

import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import gestionbrb.Connexion;
import gestionbrb.DAO.DAOCalendrier;
import gestionbrb.DAO.DAOUtilisateur;
import gestionbrb.model.Reservations;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Recense toutes les résrvations et leurs détails
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
	@FXML
	private ResourceBundle bundle;
	@FXML
	private Label Labelcalendrier;
	@FXML
	private Label Nom;
	@FXML
	private Label prenom;
	@FXML
	private Label heure;
	@FXML
	private Label nb;
	@FXML
	private Label telephone;
	@FXML
	private Label demande;
	@FXML
	private Label date;
	@FXML
	private Label Id;
	@FXML
	private Button btn1;
	@FXML 
	private Button btn2;
	@FXML 
	private Button btn3;

	DemarrerCommandeControleur mainApp;
	DAOUtilisateur daoUtilisateur = new DAOUtilisateur();

	
	DAOCalendrier daoCalendrier = new DAOCalendrier();

	public CalendrierControleur() {
	}

	/**
	 * Initialise la classe controleur avec les données par défaut du tableau <br>
	 * Charge les fichiers de langue nécessaires à la traduction.
	 * <br>
	 * Affiche un message d'erreur si il y a un problème.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */

	@FXML
	private void initialize() {

		try {
			String langue = daoUtilisateur.recupererLangue(ConnexionControleur.getUtilisateurConnecte().getIdUtilisateur());
			
			switch(langue) {
			case "fr":
				loadLang("fr", "FR");
				break;
			case "en":
				loadLang("en", "US");
				break;
			case "zh":
				loadLang("zh", "CN");
				break;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	
	
	
	private void loadLang(String lang, String LANG) {
		Locale locale = new Locale(lang, LANG);  
		
		bundle = ResourceBundle.getBundle("gestionbrb/language/Language_"+lang, locale);
		nbTotalReservations.setText(bundle.getString("0reservation"));
		Labelcalendrier.setText(bundle.getString("Calendrier"));
		colonneNom.setText(bundle.getString("Nom"));
		colonneDate.setText(bundle.getString("Date"));
		colonneHeure.setText(bundle.getString("Heure"));
		colonneNbCouverts.setText(bundle.getString("Couverts"));
		Nom.setText(bundle.getString("Nom"));
		prenom.setText(bundle.getString("Prenom"));
		heure.setText(bundle.getString("Heure"));
		nb.setText(bundle.getString("key15"));
		date.setText(bundle.getString("Date"));
		demande.setText(bundle.getString("Demande"));
		telephone.setText(bundle.getString("telephone"));
		Id.setText(bundle.getString("Identifiant"));
		btn1.setText(bundle.getString("AfficherReservation"));
		btn2.setText(bundle.getString("ModifierReservation"));
		btn3.setText(bundle.getString("SupprimerReservation"));
	}
	/**
	 * Appellé la classe principale pour faire la liaison avec le controleur
	 * 
	 * @param mainApp
	 */
	public void setMainApp(DemarrerCommandeControleur mainApp) {
		this.mainApp = mainApp;
		reservationTable.setItems(reservationData);
	}

	/**
	 * Remplit tous les champs avec la reservation séléctionnée dans la partie
	 * détails. Si il n'y a pas de reservation séléctionnée les champs sont vides.
	 * <br>
	 * <br>
	 * Si l'afficheage génère une erreur, une boite d'erreur est affichée.
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
	 * Appell� quand l'utilisateur clique sur le bouton supprimer <br>
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
			FonctionsControleurs.alerteAttention("Aucune sélection", "Aucune réservation de sélectionné!", "Sélectionnez une réservation pour pouvoir la modifier");
		}
	}

	/**
	 * Appell� quand l'utilisateur clique sur le bouton modifier la réservation. <br>
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
					FonctionsControleurs.alerteInfo("Modification effectué", null, "Les informations ont déjà modifiés avec succès!");
				}
			} catch (Exception e) {
				FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue","Détails: "+e);
				e.printStackTrace();
			}

		} else {
			// Si rien n'est selectionn�
			FonctionsControleurs.alerteAttention("Aucune sélection", "Aucune réservation de sélectionné!", "Selectionnez une réservation pour pouvoir la modifier");
		}
	}

	/**
	 * Appellée quand on sélectionne une date. <br>
	 * Outil de recherche qui affiche uniquement les réservations à la date séectionnée.
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
	 * Appellée quand l'utilisateur appuie sur le bouton Afficher tout.
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
        	Locale locale = new Locale("fr", "FR");

			ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_fr", locale);
            FXMLLoader loader = new FXMLLoader(CalendrierControleur.class.getResource("../vue/ModifierReservation.fxml"),bundle);
            //loader.setLocation(CalendrierControleur.class.getResource("../vue/ModifierReservation.fxml"),bundle);
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setResizable(false);
            dialogStage.setTitle("Modifier une reservation");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.getIcons().add(new Image(
            	      Connexion.class.getResourceAsStream( "ico.png" ))); 

            ModifierCalendrierControleur controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setReservation(reservation);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (Exception e) {
        	FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue","Détails: "+e);
            return false;
        }
    }

    public static ObservableList<Reservations> getReservationData() {
        return reservationData;
    }

}