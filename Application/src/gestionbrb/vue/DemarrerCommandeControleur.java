package gestionbrb.vue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gestionbrb.DemarrerCommande;
import gestionbrb.Tables;
import gestionbrb.controleur.FonctionsControleurs;
import gestionbrb.model.Table;
import gestionbrb.util.bddUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class DemarrerCommandeControleur extends FonctionsControleurs {

	// observable liste pour l'afficher dans le champ no table
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
	@FXML
	private ChoiceBox<String> champNoTable;
	@FXML
	private ObservableList<String> noTables = FXCollections.observableArrayList();
	@FXML
	private TableView<Table> tableTable;
	@FXML
	private TableColumn<Table, Number> colonneNoTable;
	@FXML
	private TableColumn<Table, Number> colonneNbCouvertsMax;
	@FXML
	private TableColumn<Table, Boolean> colonneStatut;

	private Table table;
	private DemarrerCommande mainApp;
	// Reference to the main application.

	private boolean okClicked = false;

	public DemarrerCommandeControleur() {
	}

	@FXML
	private void initialize() {
		colonneNoTable.setCellValueFactory(cellData -> cellData.getValue().NoTableProperty());
		colonneNbCouvertsMax.setCellValueFactory(cellData -> cellData.getValue().nbCouvertsMaxProperty());
		colonneStatut.setCellValueFactory(cellData -> cellData.getValue().estOccupeProperty());
		try {
			Connection conn = bddUtil.dbConnect();
			ResultSet rs = conn.createStatement().executeQuery("select * from tables");
			while (rs.next()) {
				System.out.println(rs.getInt(2));
				noTables.add("Table n°"+rs.getInt(2)+" ["+rs.getInt(3)+" à "+rs.getInt(4)+" couverts]");
				Tables.getTableData().add(new Table(rs.getInt("idTable"), rs.getInt("noTable"),
						rs.getInt("nbCouverts_Min"), rs.getInt("nbCouverts_Max"), false));
			}
			champNoTable.setItems(noTables);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("erreur");
			e.printStackTrace();
		}

	}

	public void setMainApp(DemarrerCommande mainApp) {
		this.mainApp = mainApp;

		tableTable.setItems(Tables.getTableData());

	}

	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Appellé quand l'utilisateur clique sur réserver
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void actionAjouter() throws ClassNotFoundException, SQLException {
		System.out.println(table.getIdTable());
		if (estValide()) {
			bddUtil.dbQueryExecute(
					"INSERT INTO `calendrier` (`idReservation`, `nom`, `prenom`, `numeroTel`, `dateReservation`, `HeureReservation`, `nbCouverts`, `demandeSpe`, `idTable`) "
							+ "VALUES (NULL, '" + champNom.getText() + "', '" + champPrenom.getText() + "','"
							+ champNumTel.getText() + "' , '" + champDate.getValue() + "', '" + champHeure.getText()
							+ "', '" + champNbCouverts.getText() + "', '" + champDemandeSpe.getText() + "', null );");
			alerteInfo("Reservation enregistrée!", "", "La reservation à bien été enregistrée!");
			champNom.clear();
			champPrenom.clear();
			champNumTel.clear();
			champDate.setValue(null);
			champHeure.clear();
			champNbCouverts.clear();
			champDemandeSpe.clear();

			okClicked = true;
		}
	}

	/**
	 * Vérifie si la saisie est conforme aux données requises
	 * 
	 * @return true si la saisie est bien conforme
	 */
	public boolean estValide() {
		String stringNoTable = champNoTable.getValue();
		String numeroTable;
		switch (stringNoTable.length()) {
		case 26:
			numeroTable = stringNoTable.substring(8, 9);
			System.out.println(numeroTable);
			break;
		case 27:
			numeroTable = stringNoTable.substring(8, 10);
			System.out.println(numeroTable);
			break;
		case 28:
			numeroTable = stringNoTable.substring(8, 11);
			System.out.println(numeroTable);
			break;
		default: 
			break;
		}
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
			Pattern p = Pattern.compile("(0|\\+)[0-9]{8,12}");
			Matcher m = p.matcher(champNumTel.getText());
			if (!(m.find() && m.group().equals(champNumTel.getText()))) {
				errorMessage += "Erreur! Le champ no. téléphone n'accepte que les numéros commençant par + ou 0 et ayant une longueur entre 8 et 12 chiffres\n";
			}
		}
		if (champDate.getValue() == null) {
			System.out.println(champDate.getValue());
			errorMessage += "Veuillez selectionner la date\n";
		}

		if (champHeure.getText() == null || champHeure.getText().length() == 0) {
			errorMessage += "Veuillez rentrer l'heure\n";
		} else {
			Pattern heurep = Pattern.compile("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$");
			Matcher heurem = heurep.matcher(champHeure.getText());
			if (!(heurem.find() && heurem.group().equals(champHeure.getText()))) {
				errorMessage += "Format de l'heure incorrect, veuillez réessayer avec le format hh:mm approprié\n";
			}
		}

		if (champNbCouverts.getText() == null || champNbCouverts.getText().length() == 0) {
			errorMessage += "Veuillez rentrer le nombre de couverts!\n";
		} else {
			// essaye de transformer la saisie en un nombre de type int
			try {
				Integer.parseInt(champNbCouverts.getText());
			} catch (NumberFormatException e) {
				errorMessage += "Erreur! Le champ \"nombre de couverts\" n'accepte que les nombres\n";
			}
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			alerteErreur("Entrée incorrecte", "Corrigez les erreurs suivantes pour pouvoir modifier la reservation",
					errorMessage);
			return false;
		}
	}
}