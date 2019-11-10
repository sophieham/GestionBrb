package gestionbrb.vue;

import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DemarrerCommandeControleur extends FonctionsControleurs {
	// Variables pour la partie r�servation
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
	private TextField champNbCouvertsReservation;
	@FXML
	private TextField champDemandeSpe;
	@FXML
	private ChoiceBox<String> champNoTable;
	@FXML
	private ObservableList<String> noTables = FXCollections.observableArrayList();
	
	// Variables pour la partie d�marrer une nouvelle commande
	@FXML
	private ChoiceBox<String> champChoixTable;
	@FXML
	private ObservableList<String> tablesLibre = FXCollections.observableArrayList();
	@FXML
	private TextField champNbCouverts;
	
	// Variables pour la partie occupation tables
	@FXML
	private TableView<Table> tableTable;
	@FXML
	private TableColumn<Table, Number> colonneNoTable;
	@FXML
	private TableColumn<Table, Number> colonneNbCouvertsMax;
	@FXML
	private TableColumn<Table, String> colonneStatut;
	@FXML
	private Label lblOccupation;

	@SuppressWarnings("unused")
	private Table table;
	@SuppressWarnings("unused")
	private DemarrerCommande parent;
	private boolean okClicked = false;

	public DemarrerCommandeControleur() {
	}

	/**
	 * Initialise le controleur. Remplit la liste des tables avec les donn�es
	 * provenant de la base de donn�e.
	 */

	@FXML
	private void initialize() {
		colonneNoTable.setCellValueFactory(cellData -> cellData.getValue().NoTableProperty());
		colonneNbCouvertsMax.setCellValueFactory(cellData -> cellData.getValue().nbCouvertsMaxProperty());
		colonneStatut.setCellValueFactory(cellData -> cellData.getValue().occupationStrProperty());

		try {

			Connection conn = bddUtil.dbConnect();
			ResultSet rs = conn.createStatement().executeQuery("select * from tables");

			while (rs.next()) {
				Tables.getTableData().add(new Table(rs.getInt("idTable"), 
													rs.getInt("noTable"),
													rs.getInt("nbCouverts_Min"), 
													rs.getInt("nbCouverts_Max"), 
													rs.getInt("occupation")));
				
				// ajout de tous les num�ros de table dans une liste pour s�l�ctionner la table � r�server
				noTables.add("Table n�" + rs.getInt(2) + " [" + rs.getInt(3) + " � " + rs.getInt(4) + " couverts]");
				
				// ajout de tous les num�ros de tables qui sont actuellement libres pour accueillir une commande
				if(rs.getInt("occupation")==0) {
					tablesLibre.add("Table n�" + rs.getInt(2) + " [" + rs.getInt(3) + " � " + rs.getInt(4) + " couverts]");
				}

			}
			lblOccupation.setText((noTables.size()-tablesLibre.size())+" tables occup�e(s), "+tablesLibre.size()+" libres");
			champNoTable.setItems(noTables);
			champChoixTable.setItems(tablesLibre);

		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("erreur sql");
			e.printStackTrace();
		}

	}

	/**
	 * Fait la liaison avec la page principale
	 * 
	 * @param parent
	 */

	public void setParent(DemarrerCommande parent) {
		this.parent = parent;
		tableTable.setItems(Tables.getTableData()); // Recup�re la liste des tables

	}

	/**
	 * Appell� lors de l'appui sur le bouton Voir le calendrier Ouvre le registre
	 * des r�servations.
	 * 
	 * @throws IOException
	 */

	public void afficherCalendrier() throws IOException { // il faut r�tablir le controleur
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("CalendrierReservations.fxml"));
			Parent vueCalendrier = (Parent) loader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(vueCalendrier));
			stage.show();
			
			CalendrierControleur controller = loader.getController();
            controller.setMainApp(this);
            controller.afficherTout();
		} catch (Exception e) {
			alerteErreur("Erreur", "Impossible d'ouvrir cette fenetre", "D�tails: "+e);

		}
	}

	/**
	 * Appell� lors de l'appui sur le bouton Retour au menu principal Il ferme la
	 * page actuelle et revient au menu principal.
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void afficherMenuPrincipal() {
		DemarrerCommande.getPrimaryStage().close();
	}

	/** 
	 * Actualise les donn�e de la page
	 * Appell� quand une nouvelle commande est lanc�e
	 */
	public void refreshMain() {
		Tables.getTableData().clear();
		tablesLibre.clear();
		noTables.clear();
		champNoTable.setValue(null);
		champChoixTable.setValue(null);
		initialize();
	}
	
	public boolean isOkClicked() {
		return okClicked;
	}
	
	/**
	 * D�marre une nouvelle commande.
	 * Affiche une nouvelle page Commande.fxml
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void lancerCommande() throws ClassNotFoundException, SQLException{ // a continuer
		try {
			//int nombreCouverts = Integer.parseInt(champNbCouverts.getText());
			int numTable = getNumeroTable(champChoixTable);
			bddUtil.dbQueryExecute("UPDATE `tables` SET occupation = 1 WHERE noTable="+numTable);
			refreshMain();
			//Commande commande= new Commande();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Commande.fxml"));
			Parent vueCommande = (Parent) loader.load();
			Stage stage = new Stage();
			stage.setTitle("-- Commande de la table "+numTable+" --");
			stage.setScene(new Scene(vueCommande));
			stage.show();
			
		} catch (IOException e) {
			alerteErreur("Erreur", "Impossible d'ouvrir cette fenetre", "D�tails: "+e);
		}
		catch(NumberFormatException e) {
			alerteErreur("Erreur", "Veuillez saisir uniquement des chiffres", "D�tails: "+e);
		}
	}

	/**
	 * Extrait le num�ro de table provenant de la table choisie lors de la
	 * r�servation
	 * 
	 * @return numero de la table
	 */
	public int getNumeroTable(ChoiceBox<String> cible) {
		String stringNoTable = cible.getValue();
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
				System.out.println("erreur");
				break;
			}

			// affiche une exception alors que le String � bien �t� transform� en nombre
		} catch (NumberFormatException e) {
		}
		return numeroTable;
	}
	
	/**
	 * Appell� quand l'utilisateur clique sur r�server
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
							+ "', '" + champNbCouvertsReservation.getText() + "', '" + champDemandeSpe.getText() + "', '"
							+ getNumeroTable(champNoTable) + "');");

			alerteInfo("Reservation enregistr�e!", "", "La reservation � bien �t� enregistr�e!");

			champNom.clear();
			champPrenom.clear();
			champNumTel.clear();
			champDate.setValue(null);
			champHeure.clear();
			champNbCouvertsReservation.clear();
			champDemandeSpe.clear();
			champNoTable.setValue(null);

			okClicked = true;
		}
	}

	/**
	 * V�rifie si la saisie est conforme aux donn�es requises
	 * 
	 * @return true si la saisie est bien conforme, false sinon
	 */
	public boolean estValide() {
		String errorMessage = "";

		if (champNom.getText() == null || champNom.getText().length() == 0) {
			errorMessage += "Veuillez remplir le nom\n";
		}
		if (champPrenom.getText() == null || champPrenom.getText().length() == 0) {
			errorMessage += "Veuillez remplir le pr�nom\n";
		}
		if (champNumTel.getText() == null || champNumTel.getText().length() == 0) {
			errorMessage += "Veuillez rentrer le num�ro de t�l�phone\n";
		} else {
			Pattern p = Pattern.compile("(0|\\+)[0-9]{8,12}"); // regex d'un num�ro de t�l�phone, fran�ais ou �tranger
			Matcher m = p.matcher(champNumTel.getText());
			if (!(m.find() && m.group().equals(champNumTel.getText()))) {
				errorMessage += "Erreur! Le champ no. t�l�phone n'accepte que les num�ros commen�ant par + ou 0 et ayant une longueur entre 8 et 12 chiffres\n";
			}
		}
		if (champDate.getValue() == null) {
			errorMessage += "Veuillez selectionner la date\n";
		}

		if (champHeure.getText() == null || champHeure.getText().length() == 0) {
			errorMessage += "Veuillez rentrer l'heure\n";
		} else {
			Pattern heurep = Pattern.compile("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$"); // regex pour afficher une heure
																						// valide sous forme hh:mm
			Matcher heurem = heurep.matcher(champHeure.getText());
			if (!(heurem.find() && heurem.group().equals(champHeure.getText()))) {
				errorMessage += "Format de l'heure incorrect, veuillez r�essayer avec le format hh:mm appropri�\n";
			}
		}

		if (champNbCouvertsReservation.getText() == null || champNbCouvertsReservation.getText().length() == 0) {
			errorMessage += "Veuillez rentrer le nombre de couverts!\n";
		} else {
			try {
				Integer.parseInt(champNbCouvertsReservation.getText()); // transformation en int pour voir si la saisie est un
																// chiffre
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
			alerteErreur("Entr�e incorrecte", "Corrigez les erreurs suivantes pour pouvoir enregistrer la reservation",errorMessage);
			return false;
		}
	}

}