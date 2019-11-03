package gestionbrb.vue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import gestionbrb.Calendrier;
import gestionbrb.controleur.FonctionsControleurs;
import gestionbrb.model.Reservations;
import gestionbrb.util.bddUtil;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CalendrierControleur extends FonctionsControleurs {
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

	// Reference to the main application.
	private Calendrier mainApp;

	/**
	 * The constructor. The constructor is called before the initialize() method.
	 */
	public CalendrierControleur() {
	}

	/**
	 * Initialise la classe controleur avec les donn�es par d�faut du tableau
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
		ResultSet nbTotalResv = conn.createStatement().executeQuery("select count(*) from calendrier");
		while(nbTotalResv.next()) {
		nbTotalReservations.setText(nbTotalResv.getString("count(*)")+" r�servations au total");}
		
		// Change les d�tails en fonction de ce qui � �t� selectionn�
		reservationTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			try {
				detailsReservation(newValue);
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	/**
	 * Appell� par la classe principale (ici DemarrerCommande) pour lier cette classe � elle-meme
	 * 
	 * @param mainApp
	 */
	public void setMainApp(Calendrier mainApp) {
		this.mainApp = mainApp;

		// Add observable list data to the table
		reservationTable.setItems(mainApp.getReservationData());
	}

	/**
	 * Rempli tous les champs avec la reservation s�l�ctionn�e dans la partie
	 * d�tails. Si il n'y a pas de reservation s�l�ctionn�e, les champs sont vides.
	 * 
	 * @param reservation the reservation or null
	 * @throws SQLException
	 */

	private void detailsReservation(Reservations reservation) throws SQLException, ClassNotFoundException {
		Connection conn = bddUtil.dbConnect();		
		ResultSet rs = conn.createStatement().executeQuery("select * from calendrier");
		try {

			if (reservation != null) {
				while (rs.next()) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			conn.close();
			rs.close();
		}
	}

	
	/**
	 * Appell� quand l'utilisateur clique sur le bouton supprimer
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void supprimerReservation() throws ClassNotFoundException, SQLException {
		Reservations selectedReservation = reservationTable.getSelectionModel().getSelectedItem();
		int selectedIndex = reservationTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			Connection conn = bddUtil.dbConnect();
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM `calendrier` WHERE idReservation=?");
			pstmt.setInt(1, (selectedReservation.getID()));
			pstmt.execute();
			pstmt.close();
			reservationTable.getItems().remove(selectedIndex);

		} else {
			// Si rien n'est s�l�ctionn�
			alerteAttention("Aucune s�lection", "Aucune r�servation de s�lectionn�e!", "Selectionnez une r�servation pour pouvoir la modifier");
		}
	}

	/**
	 * Appell� quand l'utilisateur clique sur le bouton modifier la r�servation.
	 * Ouvre une nouvelle page pour effectuer la modification
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void modifierReservation() throws ClassNotFoundException, SQLException {
		Reservations selectedReservation = reservationTable.getSelectionModel().getSelectedItem();
		if (selectedReservation != null) {
			boolean okClicked = mainApp.fenetreModification(selectedReservation);
			if (okClicked) {
				reservationTable.getItems().clear();
				Connection conn = bddUtil.dbConnect();
				ResultSet rs = conn.createStatement().executeQuery("select * from calendrier");
				while (rs.next()) {
					reservationTable.getItems().add(new Reservations(
							rs.getInt("idReservation"),
							rs.getString("nom"),
							rs.getString("prenom"), 
							rs.getString("numeroTel"), 
							rs.getString("dateReservation"),
							rs.getString("heureReservation"), 
							rs.getInt("nbCouverts"), 
							rs.getString("demandeSpe")));
				}
				detailsReservation(null);
				alerteInfo("Modification �ffectu�e", null, "Les informations ont �t� modifi�es avec succ�s!");
			}

		} else {
			// Si rien n'est selectionn�
			alerteAttention("Aucune s�lection", "Aucune r�servation de s�lectionn�e!", "Selectionnez une r�servation pour pouvoir la modifier");
		}
	}

	/**
	 * Appell� quand on s�lectionne une date.
	 * Affiche uniquement les r�servations � la date s�lectionn�e
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
		PreparedStatement stmt = conn.prepareStatement("select * from calendrier where dateReservation LIKE ?");
		stmt.setString(1, date);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			reservationTable.getItems().add(new Reservations(
							rs.getInt("idReservation"), 
							rs.getString("nom"), 
							rs.getString("prenom"),
							rs.getString("numeroTel"), 
							rs.getString("dateReservation"),
							rs.getString("heureReservation"), 
							rs.getInt("nbCouverts"), 
							rs.getString("demandeSpe")));
		}

		conn.close();
		rs.close();
	}
	
	
	/**
	 * Appell� quand l'utilisateur appuie sur le bouton Afficher tout.
	 * Affiche toutes les r�servations quelquesoit la date (annule la recherche)
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@FXML
	private void afficherTout() throws ClassNotFoundException, SQLException {
		reservationTable.getItems().clear();
		Connection conn = bddUtil.dbConnect();
		ResultSet nbTotalResv = conn.createStatement().executeQuery("select count(*) from calendrier");
		while(nbTotalResv.next()) {
		nbTotalReservations.setText(nbTotalResv.getString("count(*)")+" r�servations au total");}
		ResultSet rs = conn.createStatement().executeQuery("select * from calendrier");
		while (rs.next()) {
			reservationTable.getItems()
					.add(new Reservations(
							rs.getInt("idReservation"), 
							rs.getString("nom"), 
							rs.getString("prenom"),
							rs.getString("numeroTel"), 
							rs.getString("dateReservation"),
							rs.getString("heureReservation"), 
							rs.getInt("nbCouverts"), 
							rs.getString("demandeSpe")));
		}
		detailsReservation(null);
	}

}