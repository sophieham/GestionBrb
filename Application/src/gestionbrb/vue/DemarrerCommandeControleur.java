package gestionbrb.vue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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

	@SuppressWarnings("unused")
	private Table table;
	@SuppressWarnings("unused")
	private DemarrerCommande parent;

	private boolean okClicked = false;

	public DemarrerCommandeControleur() {
	}
	/**
	 * Initialise le controleur.
	 * Remplit la liste des tables avec les données provenant de la base de donnée.
	 */
	
	@FXML
	private void initialize() {
		colonneNoTable.setCellValueFactory(cellData -> cellData.getValue().NoTableProperty());
		colonneNbCouvertsMax.setCellValueFactory(cellData -> cellData.getValue().nbCouvertsMaxProperty());
		colonneStatut.setCellValueFactory(cellData -> cellData.getValue().estOccupeProperty());
		
		try {
			
			Connection conn = bddUtil.dbConnect();
			ResultSet rs = conn.createStatement().executeQuery("select * from tables");
			
			while (rs.next()) {
				
				noTables.add("Table n°" + rs.getInt(2) + " [" + rs.getInt(3) + " à " + rs.getInt(4) + " couverts]");
				Tables.getTableData().add(new Table(rs.getInt("idTable"), 
													rs.getInt("noTable"),
													rs.getInt("nbCouverts_Min"), 
													rs.getInt("nbCouverts_Max"), 
													false));
				
			}
			
			champNoTable.setItems(noTables);
			
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("erreur sql");
			e.printStackTrace();
		}

	}
	
	/**
	 * Fait la liaison avec la page principale
	 * 
	 * @param mainApp
	 */

	public void setParent(DemarrerCommande parent) {
		this.parent=parent;
		tableTable.setItems(Tables.getTableData()); // Recupère la liste des tables

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
		if (estValide()) {
			bddUtil.dbQueryExecute(
					"INSERT INTO `calendrier` (`idReservation`, `nom`, `prenom`, `numeroTel`, `dateReservation`, `heureReservation`, `nbCouverts`, `demandeSpe`, `noTable`) "
							+ "VALUES (NULL, '" + champNom.getText() + "', '" + champPrenom.getText() + "','"
							+ champNumTel.getText() + "' , '" + champDate.getValue() + "', '" + champHeure.getText()
							+ "', '" + champNbCouverts.getText() + "', '" + champDemandeSpe.getText() + "', '"
							+ getNumeroTable() + "');");
			
			alerteInfo("Reservation enregistrée!", "", "La reservation à bien été enregistrée!");
			
			champNom.clear();
			champPrenom.clear();
			champNumTel.clear();
			champDate.setValue(null);
			champHeure.clear();
			champNbCouverts.clear();
			champDemandeSpe.clear();
			champNoTable.setValue(null);

			okClicked = true;
		}
	}
	
	/**
	 * Extrait le numéro de table provenant de la table choisie lors de la réservation
	 * @return numero de la table
	 */
	public int getNumeroTable() {
		String stringNoTable = champNoTable.getValue();
		String sTable = null;
		int numeroTable = 0;
		try {
			switch (stringNoTable.length()) {
			case 26:
				sTable = stringNoTable.substring(8, 9);
				numeroTable = Integer.parseInt(sTable);
			case 27:
				sTable = stringNoTable.substring(8, 10);
				numeroTable = Integer.parseInt(sTable);
			case 28:
				sTable = stringNoTable.substring(8, 11);
				numeroTable = Integer.parseInt(sTable);
			default:
				break;
			}
			
		// affiche une exception alors que le String à bien été transformé en nombre
		} catch (NumberFormatException e) {} 
		return numeroTable;
	}

	/**
	 * Vérifie si la saisie est conforme aux données requises
	 * 
	 * @return true si la saisie est bien conforme, false sinon
	 */
	public boolean estValide() {
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
			Pattern p = Pattern.compile("(0|\\+)[0-9]{8,12}"); // regex d'un numéro de téléphone, français ou étranger
			Matcher m = p.matcher(champNumTel.getText());
			if (!(m.find() && m.group().equals(champNumTel.getText()))) {
				errorMessage += "Erreur! Le champ no. téléphone n'accepte que les numéros commençant par + ou 0 et ayant une longueur entre 8 et 12 chiffres\n";
			}
		}
		if (champDate.getValue() == null) {
			errorMessage += "Veuillez selectionner la date\n";
		}

		if (champHeure.getText() == null || champHeure.getText().length() == 0) {
			errorMessage += "Veuillez rentrer l'heure\n";
		} else {
			Pattern heurep = Pattern.compile("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$"); // regex pour afficher une heure valide sous forme hh:mm
			Matcher heurem = heurep.matcher(champHeure.getText());
			if (!(heurem.find() && heurem.group().equals(champHeure.getText()))) {
				errorMessage += "Format de l'heure incorrect, veuillez réessayer avec le format hh:mm approprié\n";
			}
		}

		if (champNbCouverts.getText() == null || champNbCouverts.getText().length() == 0) {
			errorMessage += "Veuillez rentrer le nombre de couverts!\n";
		} else {
			try {
				Integer.parseInt(champNbCouverts.getText()); // transformation en int pour voir si la saisie est un chiffre
			} catch (NumberFormatException e) {
				errorMessage += "Erreur! Le champ \"nombre de couverts\" n'accepte que les nombres\n";
			}
		}
		
		if (champNoTable.getValue() == null) {
			errorMessage += "Veuillez selectionner une table\n";
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