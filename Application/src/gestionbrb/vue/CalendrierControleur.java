package gestionbrb.vue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */

	// Initialize the reservation table with the two columns.
	@FXML
	private void initialize() throws ClassNotFoundException, SQLException {

		colonneNom.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
		colonneDate.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
		colonneHeure.setCellValueFactory(cellData -> cellData.getValue().heureProperty());
		colonneNbCouverts.setCellValueFactory(cellData -> cellData.getValue().nbCouvertsProperty());

		// Clear reservation details.
		showReservationDetails(null);

		// Listen for selection changes and show the reservation details when changed.
		reservationTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
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
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(Calendrier mainApp) {
		this.mainApp = mainApp;

		// Add observable list data to the table
		reservationTable.setItems(mainApp.getReservationData());
	}

	/**
	 * Fills all text fields to show details about the reservation. If the specified
	 * reservation is null, all text fields are cleared.
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
					champNom.setText(reservation.getNom());
					champPrenom.setText(rs.getString("prenom"));
					champNumTel.setText(rs.getString("numeroTel"));
					champDate.setText(reservation.getDate());
					champHeure.setText(reservation.getHeure());
					champNbCouverts.setText(Integer.toString(reservation.getNbCouverts()));
					champDemandeSpe.setText(rs.getString("demandeSpe"));
				}
			}
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	}
}

	/**
	 * Called when the user clicks on the delete button.
	 */
	@FXML
	private void handleDeleteReservation() {
		int selectedIndex = reservationTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			reservationTable.getItems().remove(selectedIndex);
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No reservation Selected");
			alert.setContentText("Please select a reservation in the table.");

			alert.showAndWait();
		}
	}

	/**
	 * Called when the user clicks the new button. Opens a dialog to edit details
	 * for a new reservation.
	 */
	@FXML
	private void handleNewReservation() {
		Reservations tempReservation = new Reservations();
		boolean okClicked = mainApp.showReservationEditDialog(tempReservation);
		if (okClicked) {
			mainApp.getReservationData().add(tempReservation);
		}
	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit details
	 * for the selected reservation.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@FXML
	private void handleEditReservation() throws ClassNotFoundException, SQLException {
		Reservations selectedReservation = reservationTable.getSelectionModel().getSelectedItem();
		if (selectedReservation != null) {
			boolean okClicked = mainApp.showReservationEditDialog(selectedReservation);
			if (okClicked) {
				showReservationDetails(selectedReservation);
			}

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Aucune sélection");
			alert.setHeaderText("Aucune réservation de sélectionnée!");
			alert.setContentText("Selectionnez une réservation pour pouvoir la modifier");

			alert.showAndWait();
		}
	}

	@FXML
	private void recherche() {
		Reservations reservation = new Reservations();
		try {
			String date = rechercheDate.getPromptText();
			Connection conn = bddUtil.dbConnect();
			PreparedStatement stmt=conn.prepareStatement("select * from calendrier where dateReservation LIKE ?");  
			stmt.setString(1, date);
			ResultSet rs=stmt.executeQuery();   
				if (reservation != null) {
					while (rs.next()) {
						champNom.setText(reservation.getNom());
						champPrenom.setText(rs.getString("prenom"));
						champNumTel.setText(rs.getString("numeroTel"));
						champDate.setText(reservation.getDate());
						champHeure.setText(reservation.getHeure());
						champNbCouverts.setText(Integer.toString(reservation.getNbCouverts()));
						champDemandeSpe.setText(rs.getString("demandeSpe"));
					}
				}
			showReservationDetails(null);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}