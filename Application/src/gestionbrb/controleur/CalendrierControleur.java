package gestionbrb.controleur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import gestionbrb.model.Reservations;
import gestionbrb.util.bddUtil;
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
	private AdministrationControleur parent;

	public CalendrierControleur() {
	}

	/**
	 * Initialise la classe controleur avec les donn�es par d�faut du tableau <br>
	 * <br>
	 * Affiche un message d'erreur si il y a un probl�me.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */

	@FXML
	private void initialize() throws ClassNotFoundException, SQLException {

		colonneNom.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
		colonneDate.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
		colonneHeure.setCellValueFactory(cellData -> cellData.getValue().heureProperty());
		colonneNbCouverts.setCellValueFactory(cellData -> cellData.getValue().nbCouvertsProperty());

		Connection conn = bddUtil.dbConnect();
		ResultSet nbTotalReserv = conn.createStatement().executeQuery("select count(*) from calendrier");
		while(nbTotalReserv.next()) {
		nbTotalReservations.setText(nbTotalReserv.getString("count(*)")+" r�servations au total");}
		
		reservationTable.getSelectionModel().selectedItemProperty().addListener((observable, ancienneValeur, nouvelleValeur) -> {
			try {
				detailsReservation(nouvelleValeur);
			} catch (ClassNotFoundException e) {
				FonctionsControleurs.alerteErreur("Erreur", "Erreur du programme", ""+e);
			} catch (SQLException e) {
				FonctionsControleurs.alerteErreur("Erreur", "Erreur SQL", ""+e);
			}
		});
	}

	/**
	 * Appell� par la classe principale pour faire la liaison avec le controleur
	 * 
	 * @param mainApp
	 */
	public void setMainApp(DemarrerCommandeControleur mainApp) {
		this.mainApp = mainApp;

		reservationTable.setItems(getReservationData());
	}

	/**
	 * Remplit tous les champs avec la reservation s�l�ctionn�e dans la partie
	 * d�tails. Si il n'y a pas de reservation s�l�ctionn�e, les champs sont vides.
	 * <br>
	 * <br>
	 * Si l'affiche g�n�re une erreur, une boite d'erreur est affich�e
	 * 
	 * @param reservation s'il en existe une, null sinon
	 * @throws SQLException
	 */

	private void detailsReservation(Reservations reservation) throws SQLException, ClassNotFoundException {
		Connection conn = bddUtil.dbConnect();		
		ResultSet entreeCalendrier = conn.createStatement().executeQuery("select * from calendrier");
		try {
			if (reservation != null) {
				while (entreeCalendrier.next()) {
					champID.setText(Integer.toString(reservation.getID()));
					champNom.setText(reservation.getNom());
					champPrenom.setText(reservation.getPrenom());
					champNumTel.setText(reservation.getNumTel());
					champDate.setText(reservation.getDate());
					champHeure.setText(reservation.getHeure());
					champNbCouverts.setText(Integer.toString(reservation.getNbCouverts()));
					champDemandeSpe.setText(reservation.getDemandeSpe());
				}
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
		} catch (SQLException e) {
			FonctionsControleurs.alerteErreur("Erreur", "Erreur dans le code SQL",""+e);
		} finally {
			conn.close();
			entreeCalendrier.close();
		}
	}
	

	
	/**
	 * Appell� quand l'utilisateur clique sur le bouton supprimer <br>
	 * Supprime la r�servation s�l�ctionn�e.
	 * <br>
	 * <br>
	 * Si la supression g�n�re une erreur, une fen�tre d'erreur s'affiche.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void supprimerReservation() throws ClassNotFoundException, SQLException {
		Reservations reservationSelectionnee = reservationTable.getSelectionModel().getSelectedItem();
		int indexSelection = reservationTable.getSelectionModel().getSelectedIndex();
		if (indexSelection >= 0) {
			Connection conn = bddUtil.dbConnect();
			PreparedStatement suppression = conn.prepareStatement("DELETE FROM `calendrier` WHERE idReservation=?");
			suppression.setInt(1, (reservationSelectionnee.getID()));
			suppression.execute();
			reservationTable.getItems().remove(indexSelection);
			suppression.close();
			conn.close();
			
		} else {
			FonctionsControleurs.alerteAttention("Aucune s�lection", "Aucune r�servation de s�lectionn�e!", "Selectionnez une r�servation pour pouvoir la modifier");
		}
	}

	/**
	 * Appell� quand l'utilisateur clique sur le bouton modifier la r�servation. <br>
	 * Ouvre une nouvelle page pour effectuer la modification et remplit le formulaire avec les informations d�j� existantes.
	 * <br>
	 * <br>
	 * Une fenetre d'information est affich�e si tout se passe bien, sinon c'est une boite d'erreur qui est affich�e.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void modifierReservation() throws ClassNotFoundException, SQLException {
		Reservations selectedReservation = reservationTable.getSelectionModel().getSelectedItem();
		if (selectedReservation != null) {
			boolean okClicked = fenetreModification(selectedReservation);
			if (okClicked) {
				reservationTable.getItems().clear();
				Connection conn = bddUtil.dbConnect();
				ResultSet calendrier = conn.createStatement().executeQuery("select * from calendrier");
				while (calendrier.next()) {
					reservationTable.getItems().add(new Reservations(
							calendrier.getInt("idReservation"),
							calendrier.getString("nom"),
							calendrier.getString("prenom"), 
							calendrier.getString("numeroTel"), 
							calendrier.getString("dateReservation"),
							calendrier.getString("heureReservation"), 
							calendrier.getInt("nbCouverts"), 
							calendrier.getString("demandeSpe")));
				}
				detailsReservation(null);
				FonctionsControleurs.alerteInfo("Modification �ffectu�e", null, "Les informations ont �t� modifi�es avec succ�s!");
			}

		} else {
			// Si rien n'est selectionn�
			FonctionsControleurs.alerteAttention("Aucune s�lection", "Aucune r�servation de s�lectionn�e!", "Selectionnez une r�servation pour pouvoir la modifier");
		}
	}

	/**
	 * Appell� quand on s�lectionne une date. <br>
	 * Outil de recherche qui affiche uniquement les r�servations � la date s�lectionn�e.
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@FXML
	private void rechercherReservation() throws ClassNotFoundException, SQLException {
		reservationTable.getItems().clear();
		String date = rechercheDate.getValue().toString();
		detailsReservation(null);
		Connection conn = bddUtil.dbConnect();
		ResultSet nbTotalResv = conn.createStatement().executeQuery("select count(*) from calendrier where dateReservation LIKE '"+date+"'");
		while(nbTotalResv.next()) {
		nbTotalReservations.setText(nbTotalResv.getString("count(*)")+" r�servations le "+date);}
		PreparedStatement recherche = conn.prepareStatement("select * from calendrier where dateReservation LIKE ?");
		recherche.setString(1, date);
		ResultSet calendrier = recherche.executeQuery();
		while (calendrier.next()) {
			reservationTable.getItems().add(new Reservations(
							calendrier.getInt("idReservation"), 
							calendrier.getString("nom"), 
							calendrier.getString("prenom"),
							calendrier.getString("numeroTel"), 
							calendrier.getString("dateReservation"),
							calendrier.getString("heureReservation"), 
							calendrier.getInt("nbCouverts"), 
							calendrier.getString("demandeSpe")));
		}

		conn.close();
		calendrier.close();
	}
	
	
	/**
	 * Appell� quand l'utilisateur appuie sur le bouton Afficher tout.
	 * Affiche toutes les r�servations quelquesoit la date (annule la recherche)
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@FXML
	public void afficherTout() throws ClassNotFoundException, SQLException {
		reservationTable.getItems().clear();
		Connection conn = bddUtil.dbConnect();
		ResultSet nbTotalResv = conn.createStatement().executeQuery("select count(*) from calendrier");
		while(nbTotalResv.next()) {
		nbTotalReservations.setText(nbTotalResv.getString("count(*)")+" r�servations au total");}
		ResultSet calendrier = conn.createStatement().executeQuery("select * from calendrier");
		while (calendrier.next()) {
			reservationTable.getItems().add(new Reservations(calendrier.getInt("idReservation"), 
															 calendrier.getString("nom"), 
															 calendrier.getString("prenom"),
															 calendrier.getString("numeroTel"), 
															 calendrier.getString("dateReservation"),
															 calendrier.getString("heureReservation"), 
															 calendrier.getInt("nbCouverts"), 
															 calendrier.getString("demandeSpe")));
		}
		detailsReservation(null);
	}

	public void setParent(AdministrationControleur administrationControleur) {
		// TODO Auto-generated method stub
		this.parent = administrationControleur;
		reservationTable.setItems(getReservationData());
		
	}
	
	 /**
     * Ouvre une fen�tre pour modifier les reservations.
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
            FonctionsControleurs.alerteErreur("Erreur d'�x�cution", null, "D�tails:"+e);
            return false;
        }
    }

    public static ObservableList<Reservations> getReservationData() {
        return reservationData;
    }

}