package gestionbrb.vue;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import gestionbrb.Calendrier;
import gestionbrb.model.Reservations;
import gestionbrb.util.bddUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CalendrierControleur {
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

	// Reference to the main application.
	private Calendrier mainApp;

	/**
	 * The constructor. The constructor is called before the initialize() method.
	 */
	public CalendrierControleur() {
	}

	/**
	 * Initialise la classe controleur avec les données par défaut du tableau
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


		// Change les détails en fonction de ce qui à été selectionné
		reservationTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			try {
				showReservationDetails(newValue);
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
	 * Appellé par la classe principale (ici DemarrerCommande) pour lier cette classe à elle-meme
	 * 
	 * @param mainApp
	 */
	public void setMainApp(Calendrier mainApp) {
		this.mainApp = mainApp;

		// Add observable list data to the table
		reservationTable.setItems(mainApp.getReservationData());
	}

	/**
	 * Rempli tous les champs avec la reservation séléctionnée dans la partie
	 * détails. Si il n'y a pas de reservation séléctionnée, les champs sont vides.
	 * 
	 * @param reservation the reservation or null
	 * @throws SQLException
	 */

	private void showReservationDetails(Reservations reservation) throws SQLException, ClassNotFoundException {
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

	@FXML
	private void afficherTout() throws ClassNotFoundException, SQLException {
		reservationTable.getItems().clear();
		Connection conn = bddUtil.dbConnect();
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
		showReservationDetails(null);
	}

	/**
	 * Appellé quand l'utilisateur clique sur le bouton supprimer
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void handleDeleteReservation() throws ClassNotFoundException, SQLException {
		int selectedIndex = reservationTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			Connection conn = bddUtil.dbConnect();
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM `calendrier` WHERE idReservation=?");
			pstmt.setInt(1, (selectedIndex + 1));
			pstmt.execute();
			pstmt.close();
			reservationTable.getItems().remove(selectedIndex);

		} else {
			// Si rien n'est séléctionné
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Aucune sélection");
			alert.setHeaderText("Aucune réservation de sélectionnée!");
			alert.setContentText("Selectionnez une réservation pour pouvoir la modifier");
			alert.showAndWait();
		}
	}

	/**
	 * Appellé quand l'utilisateur clique sur le bouton modifier la réservation.
	 * Ouvre une nouvelle page pour effectuer la modification
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void handleEditReservation() throws ClassNotFoundException, SQLException {
		Reservations selectedReservation = reservationTable.getSelectionModel().getSelectedItem();
		if (selectedReservation != null) {
			boolean okClicked = mainApp.showReservationEditDialog(selectedReservation);
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
				showReservationDetails(null);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Modification effectuée");
				alert.setHeaderText(null);
				alert.setContentText("Les informations ont été modifiées avec succès!");
				alert.showAndWait();
			}

		} else {
			// Si rien n'est selectionné
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Aucune sélection");
			alert.setHeaderText("Aucune réservation de sélectionnée!");
			alert.setContentText("Selectionnez une réservation pour pouvoir la modifier");
			alert.showAndWait();
		}
	}

	@FXML
	private void recherche() throws ClassNotFoundException, SQLException {
		reservationTable.getItems().clear();
		String date = rechercheDate.getValue().toString();
		showReservationDetails(null);
		System.out.println(date);
		Connection conn = bddUtil.dbConnect();
		PreparedStatement stmt = conn.prepareStatement("select * from calendrier where dateReservation LIKE ?");
		stmt.setString(1, date);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			reservationTable.getItems()
					.add(new Reservations(rs.getInt("idReservation"), rs.getString("nom"), rs.getString("prenom"),
							rs.getString("numeroTel"), rs.getString("dateReservation"),
							rs.getString("heureReservation"), rs.getInt("nbCouverts"), rs.getString("demandeSpe")));
		}

		conn.close();
		rs.close();
	}
}