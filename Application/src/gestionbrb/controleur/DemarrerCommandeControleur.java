package gestionbrb.controleur;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gestionbrb.DemarrerCommande;
import gestionbrb.Tables;
import gestionbrb.model.Commande;
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

/*
 * Controleur pour d�marrer une commande
 * @author Sophie
 */

public class DemarrerCommandeControleur {
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
	@SuppressWarnings("unused")
	private DemarrerCommandeControleur parentC;
	private boolean okClicked = false;
	private static Commande commande;

	public DemarrerCommandeControleur() {
	}

	/**
	 * <i> Initialise le controleur. </i> <br> Remplit la liste des tables avec les donn�es
	 * provenant de la base de donn�e. <br>
	 * Met � jour la liste des tables libres. <br>
	 * <br>
	 * Affiche un fen�tre d'erreur si il y a des exceptions
	 */

	@FXML
	private void initialize() {
		colonneNoTable.setCellValueFactory(cellData -> cellData.getValue().NoTableProperty());
		colonneNbCouvertsMax.setCellValueFactory(cellData -> cellData.getValue().nbCouvertsMaxProperty());
		colonneStatut.setCellValueFactory(cellData -> cellData.getValue().occupationStrProperty());

		try {

			Connection conn = bddUtil.dbConnect();
			ResultSet TableDB = conn.createStatement().executeQuery("select * from tables");

			while (TableDB.next()) {
				Tables.getTableData().add(new Table(TableDB.getInt("idTable"), 
													TableDB.getInt("noTable"),
													TableDB.getInt("nbCouverts_Min"), 
													TableDB.getInt("nbCouverts_Max"), 
													TableDB.getInt("occupation")));
				
				noTables.add("Table n�" + TableDB.getInt(2) + " [" + TableDB.getInt(3) + " � " + TableDB.getInt(4) + " couverts]");
				
				if(TableDB.getInt("occupation")==0) {
					tablesLibre.add("Table n�" + TableDB.getInt(2) + " [" + TableDB.getInt(3) + " � " + TableDB.getInt(4) + " couverts]");
				}

			}
			lblOccupation.setText((noTables.size()-tablesLibre.size())+" tables occup�e(s), "+tablesLibre.size()+" libres");
			champNoTable.setItems(noTables);
			champChoixTable.setItems(tablesLibre);

		} catch (ClassNotFoundException | SQLException e) {
			FonctionsControleurs.alerteErreur("Erreur d'�x�cution", null, "D�tails: "+e);
			e.printStackTrace();
		}

	}

	/**
	 * Fait la liaison avec la page principale
	 * 
	 * @param demarrerCommande
	 */
	public void setParent(DemarrerCommande demarrerCommande) {
		this.parent = demarrerCommande;
		tableTable.setItems(Tables.getTableData()); 

	}
	
	public void setParent(DemarrerCommandeControleur demarrerCommandeControleur) {
		this.parentC = demarrerCommandeControleur;
		tableTable.setItems(Tables.getTableData());

	}

	/**
	 * Appell� lors de l'appui sur le bouton. <br>
	 * Ouvre le registre des r�servations.
	 * 
	 * Affiche un message d'erreur si il y a une exception
	 * @throws IOException
	 */

	public void afficherCalendrier() {
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
			FonctionsControleurs.alerteErreur("Erreur", "Impossible d'ouvrir cette fenetre", "D�tails: "+e);

		}
	}

	/**
	 * Appell� lors de l'appui sur le bouton Retour au menu principal <br>
	 * Il ferme la page actuelle et revient au menu principal.
	 * 
	 * @throws IOException
	 */
	public void afficherMenuPrincipal() {
		DemarrerCommande.getPrimaryStage().close();
	}

	/** 
	 * Actualise les donn�es de la page lorsqu'une nouvelle commande est lanc�e.
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
	 * D�marre une nouvelle commande en prenant en compte le nombre de couverts et le num�ro de table d�fini en modifiant son occupation. <br>
	 * Affiche une nouvelle page Commande.fxml <br>
	 * <br> 
	 * Affiche un message d'erreur si la commande ne peut pas �tre lanc�e.
	 * 
	 */
	public void lancerCommande() {
		try {
			int nombreCouverts = Integer.parseInt(champNbCouverts.getText());
			int numTable = getNumero(champChoixTable);
			
			//!!! a remplacer occupation =0 par 1 une fois commande param�tr�
			bddUtil.dbQueryExecute("UPDATE `tables` SET occupation = 0 WHERE noTable="+numTable);
			refreshMain();
			Connection conn = bddUtil.dbConnect();
			ResultSet commandeDB = conn.createStatement().executeQuery("select count(*) from commande");
			while (commandeDB.next()) {
				int id=commandeDB.getInt(1);
				id++;
				commande= new Commande(id, numTable, nombreCouverts);
			}
			bddUtil.dbQueryExecute("INSERT INTO `commande` (`idCommande`, `noTable`, `prixTotal`, `nbCouverts`, `qteTotal`, `date`) VALUES ("+commande.getIdCommande()+", '"+numTable+"', NULL, '"+nombreCouverts+"', NULL, current_timestamp())");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Commande.fxml"));
			Parent vueCommande = (Parent) loader.load();
			Stage stage = new Stage();
			stage.setTitle("-- Commande de la table "+numTable+" --");
			stage.setScene(new Scene(vueCommande));
			stage.show();
			
			CommandeControleur controller = loader.getController();
	        controller.setParent(this);
			
		} catch (IOException | SQLException | ClassNotFoundException e) {
			FonctionsControleurs.alerteErreur("Erreur", "Impossible d'ouvrir cette fenetre", "D�tails: "+e);
		}
		catch(NumberFormatException e) {
			FonctionsControleurs.alerteErreur("Erreur", "Veuillez saisir uniquement des chiffres", "D�tails: "+e);
		}
	}

	/**
	 * Extrait le num�ro de table provenant de la table choisie lors de la r�servation <br>
	 * Recup�re la valeur de la cible puis en fonction de la taille va r�cuperer la valeur du nombre de la table puis transforme la valeur en entier
	 * <br>
	 * <br>
	 * <i> exemple: Table n�2 [4 � 5 couverts] > va r�cup�rer la valeur 2 </i>
	 * 
	 * @param cible contenu d'un champ choicebox
	 * @return numeroTable le numero de la table
	 */
	public static int getNumero(ChoiceBox<String> cible) {
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


		} catch (NumberFormatException e) {
		}
		return numeroTable;
	}
	
	/**
	 * Appell� quand l'utilisateur clique sur r�server <br>
	 * Ajoute les donn�es saisies � la base de donn�e si elles sont valides 
	 * et affiche un message si cela s'est bien pass�.<br>
	 * Efface ensuite le contenu des champs.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void actionAjouter() throws ClassNotFoundException, SQLException {
		if (estValide()) {
			bddUtil.dbQueryExecute("INSERT INTO `calendrier` (`idReservation`, `nom`, `prenom`, `numeroTel`, `dateReservation`, `heureReservation`, `nbCouverts`, `demandeSpe`, `noTable`) "
									+ "VALUES (NULL, '" + champNom.getText() + "', '" + champPrenom.getText() + "','"
									+ champNumTel.getText() + "' , '" + champDate.getValue() + "', '" + champHeure.getText()
									+ "', '" + champNbCouvertsReservation.getText() + "', '" + champDemandeSpe.getText() + "', '"
									+ getNumero(champNoTable) + "');");

			FonctionsControleurs.alerteInfo("Reservation enregistr�e!", "", "La reservation � bien �t� enregistr�e!");

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
	 * V�rifie si la saisie est conforme aux donn�es requises <br>
	 * Cette m�thode v�rifie chacune des donn�es saisies et d�termine 
	 * si elle r�pond aux crit�res de la case.
	 * 
	 * @return true si la saisie est bien conforme, false sinon
	 */
	public boolean estValide() {
		String msgErreur = "";

		if (champNom.getText() == null || champNom.getText().length() == 0) {
			msgErreur += "Veuillez remplir le nom\n";
		}
		if (champPrenom.getText() == null || champPrenom.getText().length() == 0) {
			msgErreur += "Veuillez remplir le pr�nom\n";
		}
		if (champNumTel.getText() == null || champNumTel.getText().length() == 0) {
			msgErreur += "Veuillez rentrer le num�ro de t�l�phone\n";
		} else {
			Pattern p = Pattern.compile("(0|\\+)[0-9]{8,12}"); // regex d'un num�ro de t�l�phone, fran�ais ou �tranger
			Matcher m = p.matcher(champNumTel.getText());
			if (!(m.find() && m.group().equals(champNumTel.getText()))) {
				msgErreur += "Erreur! Le champ no. t�l�phone n'accepte que les num�ros commen�ant par + ou 0 et ayant une longueur entre 8 et 12 chiffres\n";
			}
		}
		if (champDate.getValue() == null) {
			msgErreur += "Veuillez selectionner la date\n";
		}

		if (champHeure.getText() == null || champHeure.getText().length() == 0) {
			msgErreur += "Veuillez rentrer l'heure\n";
		} else {
			Pattern heurep = Pattern.compile("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$"); // regex pour afficher une heure
																						// valide sous forme hh:mm
			Matcher heurem = heurep.matcher(champHeure.getText());
			if (!(heurem.find() && heurem.group().equals(champHeure.getText()))) {
				msgErreur += "Format de l'heure incorrect, veuillez r�essayer avec le format hh:mm appropri�\n";
			}
		}

		if (champNbCouvertsReservation.getText() == null || champNbCouvertsReservation.getText().length() == 0) {
			msgErreur += "Veuillez rentrer le nombre de couverts!\n";
		} else {
			try {
				Integer.parseInt(champNbCouvertsReservation.getText()); // transformation en int pour voir si la saisie est un
																// chiffre
			} catch (NumberFormatException e) {
				msgErreur += "Erreur! Le champ \"nombre de couverts\" n'accepte que les nombres\n";
			}
		}

		if (champNoTable.getValue() == null) {
			msgErreur += "Veuillez selectionner une table\n";
		}

		if (msgErreur.length() == 0) {
			return true;
		} else {
			FonctionsControleurs.alerteErreur("Entr�e incorrecte", "Corrigez les erreurs suivantes pour pouvoir enregistrer la reservation",msgErreur);
			return false;
		}
	}

	public static Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		DemarrerCommandeControleur.commande=commande;
	}

}