package gestionbrb.controleur;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gestionbrb.DAO.DAOCalendrier;
import gestionbrb.DAO.DAOCommande;
import gestionbrb.DAO.DAOTables;
import gestionbrb.model.Commande;
import gestionbrb.model.Reservations;
import gestionbrb.model.Table;
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

	DAOCalendrier daoCalendrier = new DAOCalendrier();
	DAOTables daoTables = new DAOTables();
	DAOCommande daoCommande = new DAOCommande();
	
	@SuppressWarnings("unused")
	private Table table;
	@SuppressWarnings("unused")
	private MenuPrincipalControleur parent;
	private boolean okClicked = false;
	private static Commande commande;

	private static Stage fenetreCommande;
	
	public static Stage getFenetreCommande() {
		return fenetreCommande;
	}

	public static void setFenetreCommande(Stage fenetreCommande) {
		DemarrerCommandeControleur.fenetreCommande = fenetreCommande;
	}

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
		tableTable.getItems().clear();
		
		colonneNoTable.setCellValueFactory(cellData -> cellData.getValue().NoTableProperty());
		colonneNbCouvertsMax.setCellValueFactory(cellData -> cellData.getValue().nbCouvertsMaxProperty());
		colonneStatut.setCellValueFactory(cellData -> cellData.getValue().occupationStrProperty());
		try {
			daoTables.afficher().clear();
			tableTable.setItems(daoTables.afficher());
			lblOccupation.setText((daoTables.afficherNoTables().size()-daoTables.afficherTablesLibres().size())+" tables occup�e(s), "+daoTables.afficherTablesLibres().size()+" libres");
			champNoTable.setItems(daoTables.afficherNoTables());
			champChoixTable.setItems(daoTables.afficherTablesLibres());
			
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'�x�cution", "Une erreur est survenue", "D�tails: "+e);
			e.printStackTrace();
		}

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
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/CalendrierReservations.fxml"));
			Parent vueCalendrier = (Parent) loader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(vueCalendrier));
			stage.setTitle("Calendrier");
			stage.setResizable(false);
			stage.show();
			
			CalendrierControleur controller = loader.getController();
            controller.setMainApp(this);
            controller.afficherTout();
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Impossible d'ouvrir cette fenetre", "D�tails: "+e);
			e.printStackTrace();
		}
	}

	/**
	 * Appell� lors de l'appui sur le bouton Retour au menu principal <br>
	 * Il ferme la page actuelle et revient au menu principal.
	 * 
	 * @throws IOException
	 */
	public void afficherMenuPrincipal() {
		MenuPrincipalControleur.getDemarrerCommande().close();
	}

	/** 
	 * Actualise les donn�es de la page lorsqu'une nouvelle commande est lanc�e.
	 */
	public void refreshMain() {
		tableTable.getItems().clear();
		champNoTable.setValue(null);
		champChoixTable.setValue(null);
		try {
			daoTables.afficherNoTables().clear();
			daoTables.afficherTablesLibres().clear();
		} catch (SQLException e) {
			FonctionsControleurs.alerteErreur("Erreur d'�x�cution", "Une erreur est survenue", "D�tails: "+e);
			e.printStackTrace();
		}
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
			final URL cssURL = getClass().getResource("../stylesheet.css"); 
			int nombreCouverts = Integer.parseInt(champNbCouverts.getText());
			int numTable = getNumero(champChoixTable);
			
			daoTables.modifierOccupation(numTable, 1);
			refreshMain();
			commande= new Commande(daoCommande.recupererID(), numTable, nombreCouverts);
			daoCommande.ajouter(commande);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/Commande.fxml"));
			Parent vueCommande = (Parent) loader.load();
			setFenetreCommande(new Stage());
			getFenetreCommande().setTitle("-- Commande de la table "+numTable+" --");
			getFenetreCommande().setScene(new Scene(vueCommande));
			getFenetreCommande().getScene().getStylesheets().add(cssURL.toExternalForm());
			getFenetreCommande().show();
			MenuPrincipalControleur.getDemarrerCommande().close();
			
			CommandeControleur controller = loader.getController();
	        controller.setParent(this);
			
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Impossible d'ouvrir cette fenetre", "D�tails: "+e);
			e.printStackTrace();
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
			
			try {
				Reservations tempReservation = new Reservations(0, 
																champNom.getText(), 
																champPrenom.getText(), 
																champNumTel.getText(), 
																champDate.getValue().toString(), 
																champHeure.getText(), 
																Integer.parseInt(champNbCouvertsReservation.getText()), 
																champDemandeSpe.getText());
				int noTable = getNumero(champNoTable);
				daoCalendrier.ajouter(tempReservation, noTable);
				FonctionsControleurs.alerteInfo("Reservation enregistr�e!", "", "La reservation � bien �t� enregistr�e!");
			} catch (Exception e) {
				FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "D�tails: " + e);
				e.printStackTrace();
			}

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
	
	/**
	 * Fait la liaison avec la page principale
	 * 
	 * @param MenuPrincipal
	 */
	public void setParent(MenuPrincipalControleur menuPrincipalControleurTest) {
		this.parent = menuPrincipalControleurTest;
	}



}