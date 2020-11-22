package gestionbrb.controleur;

import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gestionbrb.DAO.DAOCalendrier;
import gestionbrb.DAO.DAOCommande;
import gestionbrb.DAO.DAOTables;
import gestionbrb.DAO.DAOUtilisateur;
import gestionbrb.model.Commande;
import gestionbrb.model.Reservations;
import gestionbrb.model.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;

// TODO: Auto-generated Javadoc
/**
 * The Class DemarrerCommandeControleur.
 */
/*
 * Controleur pour démarrer une commande
 * @author Sophie
 */
public class DemarrerCommandeControleur{
	
	/** The champ nom. */
	// Variables pour la partie réservation
	@FXML
	private TextField champNom;
	
	/** The champ prenom. */
	@FXML
	private TextField champPrenom;
	
	/** The champ num tel. */
	@FXML
	private TextField champNumTel;
	
	/** The champ date. */
	@FXML
	private DatePicker champDate;
	
	/** The champ heure. */
	@FXML
	private TextField champHeure;
	
	/** The champ nb couverts reservation. */
	@FXML
	private TextField champNbCouvertsReservation;
	
	/** The champ demande spe. */
	@FXML
	private TextField champDemandeSpe;
	
	/** The champ no table. */
	@FXML
	private ChoiceBox<String> champNoTable;
	
	/** The no tables. */
	@FXML
	private ObservableList<String> noTables = FXCollections.observableArrayList();
	
	/** The champ choix table. */
	// Variables pour la partie démarrer une nouvelle commande
	@FXML
	private ChoiceBox<String> champChoixTable;
	
	/** The tables libre. */
	@FXML
	private ObservableList<String> tablesLibre = FXCollections.observableArrayList();
	
	/** The champ nb couverts. */
	@FXML
	private TextField champNbCouverts;
	
	/** The table table. */
	// Variables pour la partie occupation tables
	@FXML
	private TableView<Table> tableTable;
	
	/** The colonne no table. */
	@FXML
	private TableColumn<Table, Number> colonneNoTable;
	
	/** The colonne nb couverts max. */
	@FXML
	private TableColumn<Table, Number> colonneNbCouvertsMax;
	
	/** The colonne statut. */
	@FXML
	private TableColumn<Table, String> colonneStatut;
	
	/** The lbl occupation. */
	@FXML
	private Label lblOccupation;
	
	/** The bundle. */
	@FXML
	private ResourceBundle bundle;
	
	/** The btn valider. */
	@FXML
	private Button btnValider;
	
	/** The titlepane demarrer. */
	@FXML
	private TitledPane titlepaneDemarrer;
	
	/** The labelcouvert. */
	@FXML
	private Label labelcouvert;
	
	/** The label table 1. */
	@FXML
	private Label labelTable1;
	
	/** The label table. */
	@FXML
	private Label labelTable;
	
	/** The button calenderier. */
	@FXML
	private Button buttonCalenderier;
	
	/** The btn retour. */
	@FXML
	private Button btnRetour;
	
	/** The nom. */
	@FXML
	private Label nom;
	
	/** The prenom. */
	@FXML
	private Label prenom;
	
	/** The heure. */
	@FXML
	private Label heure;
	
	/** The date. */
	@FXML
	private Label date;
	
	/** The nombre. */
	@FXML
	private Label nombre;
	
	/** The telephone. */
	@FXML
	private Label telephone;
	
	/** The demande. */
	@FXML
	private Label demande;
	
	/** The btn reserve. */
	@FXML
	private Button btnReserve;
	
	/** The labelcentre. */
	@FXML
	private Label labelcentre;
	
	/** The lable reserver table. */
	@FXML
	private Label lableReserverTable;
	
	/** The dao calendrier. */
	DAOCalendrier daoCalendrier = new DAOCalendrier();
	
	/** The dao tables. */
	DAOTables daoTables = new DAOTables();
	
	/** The dao commande. */
	DAOCommande daoCommande = new DAOCommande();
	
	/** The dao utilisateur. */
	DAOUtilisateur daoUtilisateur = new DAOUtilisateur();
	
	/** The table. */
	@SuppressWarnings("unused")
	private Table table;
	
	/** The parent. */
	@SuppressWarnings("unused")
	private MenuPrincipalControleur parent;
	
	/** The ok clicked. */
	private boolean okClicked = false;
	
	/** The commande. */
	private static Commande commande;

	/** The fenetre commande. */
	private static Stage fenetreCommande;
	
	/**
	 * Gets the fenetre commande.
	 *
	 * @return the fenetre commande
	 */
	public static Stage getFenetreCommande() {
		return fenetreCommande;
	}

	/**
	 * Sets the fenetre commande.
	 *
	 * @param fenetreCommande the new fenetre commande
	 */
	public static void setFenetreCommande(Stage fenetreCommande) {
		DemarrerCommandeControleur.fenetreCommande = fenetreCommande;
	}

	/**
	 * Instantiates a new demarrer commande controleur.
	 */
	public DemarrerCommandeControleur() {
	}

	/**
	 * <i> Initialise le controleur. </i> <br> Remplit la liste des tables avec les données
	 * provenant de la base de donnée. <br>
	 * Met à  jour la liste des tables libres. <br>
	 * <br>
	 * Affiche un fenêtre d'erreur si il y a des exceptions
	 */
	@FXML
	private void initialize() {
		//bundle = resources;
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

		tableTable.getItems().clear();	
		colonneNoTable.setCellValueFactory(cellData -> cellData.getValue().NoTableProperty());
		colonneNbCouvertsMax.setCellValueFactory(cellData -> cellData.getValue().nbCouvertsMaxProperty());
		colonneStatut.setCellValueFactory(cellData -> cellData.getValue().occupationStrProperty());
		try {
			daoTables.afficher().clear();
			tableTable.setItems(daoTables.afficher());
			lblOccupation.setText((daoTables.afficherNoTables().size()-daoTables.afficherTablesLibres().size())+" table(s) occupée(s), "+daoTables.afficherTablesLibres().size()+" libres");
			champNoTable.setItems(daoTables.afficherNoTables());
			champChoixTable.setItems(daoTables.afficherTablesLibres());
			
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'exécution", "Une erreur est survenue", "Détails: "+e);
			e.printStackTrace();
		}
		

	}
	
	/**
	 * Load lang.
	 *
	 * @param lang the lang
	 * @param LANG the lang
	 */
	private void loadLang(String lang, String LANG) {
		Locale locale = new Locale(lang, LANG);  
		
		bundle = ResourceBundle.getBundle("gestionbrb/language/Language_"+lang, locale);
		btnValider.setText(bundle.getString("key13"));
		titlepaneDemarrer.setText(bundle.getString("key14"));
		labelcouvert.setText(bundle.getString("key15"));
		labelTable1.setText(bundle.getString("key16"));
		buttonCalenderier.setText(bundle.getString("key17"));
		btnRetour.setText(bundle.getString("key5"));
		nom.setText(bundle.getString("Nom"));
		prenom.setText(bundle.getString("Prenom"));
		heure.setText(bundle.getString("Heure"));
		nombre.setText(bundle.getString("key15"));
		telephone.setText(bundle.getString("telephone"));
		date.setText(bundle.getString("Date"));
		demande.setText(bundle.getString("Demande"));
		btnReserve.setText(bundle.getString("Reserver"));
		colonneNoTable.setText(bundle.getString("Table"));
		colonneNbCouvertsMax.setText(bundle.getString("Max"));
		colonneStatut.setText(bundle.getString("Statut"));
		lblOccupation.setText(bundle.getString("x"));
		labelcentre.setText(bundle.getString("Occupation"));
		labelTable.setText(bundle.getString("Table"));
		//champNbCouverts.setText(bundle.getString("rentrer"));
		lableReserverTable.setText(bundle.getString("table"));
	}

	/**
	 * Appellé lors de l'appui sur le bouton. <br>
	 * Ouvre le registre des réservations.
	 * 
	 * Affiche un message d'erreur si il y a une exception
	 */

	
	public void afficherCalendrier() {
		try {
			Locale locale = new Locale("fr", "FR");
	
			ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_fr", locale);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/CalendrierReservations.fxml"),bundle);
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
			FonctionsControleurs.alerteErreur("Erreur", "Impossible d'ouvrir cette fenetre", "Détails: "+e);
			e.printStackTrace();
		}
	}

	/**
	 * Appellé lors de l'appui sur le bouton Retour au menu principal <br>
	 * Il ferme la page actuelle et revient au menu principal.
	 */
	public void afficherMenuPrincipal() {
		MenuPrincipalControleur.getDemarrerCommande().close();
	}

	/** 
	 * Actualise les données de la page lorsqu'une nouvelle commande est lancée.
	 */
	public void refreshMain() {
		tableTable.getItems().clear();
		champNoTable.setValue(null);
		champChoixTable.setValue(null);
		try {
			daoTables.afficherNoTables().clear();
			daoTables.afficherTablesLibres().clear();
		} catch (SQLException e) {
			FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Une erreur est survenue", "Détails: "+e);
			e.printStackTrace();
		}
		initialize();
	}
	
	/**
	 * Checks if is ok clicked.
	 *
	 * @return true, if is ok clicked
	 */
	public boolean isOkClicked() {
		return okClicked;
	}
	
	/**
	 * Démarre une nouvelle commande en prenant en compte le nombre de couverts et le numéro de table défini en modifiant son occupation. <br>
	 * Affiche une nouvelle page Commande.fxml <br>
	 * <br> 
	 * Affiche un message d'erreur si la commande ne peut pas être lancée.
	 * 
	 */
	public void lancerCommande() {
		try {
			int nombreCouverts = Integer.parseInt(champNbCouverts.getText());
			int numTable = getNumero(champChoixTable);
			
			daoTables.modifierOccupation(numTable, 1);
			refreshMain();
			commande= new Commande(daoCommande.recupererID(), numTable, nombreCouverts);
			daoCommande.ajouter(commande);
			Locale locale = new Locale("fr", "FR");

			ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_fr", locale);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/Commande.fxml"), bundle);
			Parent vueCommande = (Parent) loader.load();
			setFenetreCommande(new Stage());
			getFenetreCommande().setTitle("-- Commande de la table "+numTable+" --");
			getFenetreCommande().setScene(new Scene(vueCommande));
			getFenetreCommande().show();
			MenuPrincipalControleur.getDemarrerCommande().close();
			
			CommandeControleur controller = loader.getController();
	        controller.setParent(this);
			
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Impossible d'ouvrir cette fenetre", "Détails: "+e);
			e.printStackTrace();
		}
	}

	/**
	 * Extrait le numéro de table provenant de la table choisie lors de la réservation <br>
	 * Recupère la valeur de la cible puis en fonction de la taille va récuperer la valeur du nombre de la table puis transforme la valeur en entier
	 * <br>
	 * <br>
	 * <i> exemple: Table n°2 [4 à  5 couverts] > va récupérer la valeur 2 </i>.
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
				break;
			}


		} catch (NumberFormatException e) {
		}
		return numeroTable;
	}
	
	/**
	 * Appellé quand l'utilisateur clique sur réserver <br>
	 * Ajoute les données saisies à  la base de donnée si elles sont valides 
	 * et affiche un message si cela s'est bien passé.<br>
	 * Efface ensuite le contenu des champs.
	 *
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
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
				FonctionsControleurs.alerteInfo("Reservation enregistrée!", "", "La reservation à  bien été enregistrée!");
			} catch (Exception e) {
				FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "Détails: " + e);
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
	 * Vérifie si la saisie est conforme aux données requises <br>
	 * Cette méthode vérifie chacune des données saisies et détermine 
	 * si elle répond aux critères de la case.
	 * 
	 * @return true si la saisie est bien conforme, false sinon
	 */
	public boolean estValide() {
		String msgErreur = "";

		if (champNom.getText() == null || champNom.getText().length() == 0) {
			msgErreur += "Veuillez remplir le nom\n";
		}
		if (champPrenom.getText() == null || champPrenom.getText().length() == 0) {
			msgErreur += "Veuillez remplir le prénom\n";
		}
		if (champNumTel.getText() == null || champNumTel.getText().length() == 0) {
			msgErreur += "Veuillez rentrer le numéro de téléphone\n";
		} else {
			Pattern p = Pattern.compile("(0|\\+)[0-9]{8,12}"); // regex d'un numéro de téléphone, français ou étranger
			Matcher m = p.matcher(champNumTel.getText());
			if (!(m.find() && m.group().equals(champNumTel.getText()))) {
				msgErreur += "Erreur! Le champ no. téléphone n'accepte que les numéros commençant par + ou 0 et ayant une longueur entre 8 et 12 chiffres\n";
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
				msgErreur += "Format de l'heure incorrect, veuillez réessayer avec le format hh:mm approprié\n";
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
			FonctionsControleurs.alerteErreur("Entrée incorrecte", "Corrigez les erreurs suivantes pour pouvoir enregistrer la reservation",msgErreur);
			return false;
		}
	}

	/**
	 * Gets the commande.
	 *
	 * @return the commande
	 */
	public static Commande getCommande() {
		return commande;
	}

	/**
	 * Sets the commande.
	 *
	 * @param commande the new commande
	 */
	public void setCommande(Commande commande) {
		DemarrerCommandeControleur.commande=commande;
	}
	
	/**
	 * Fait la liaison avec la page principale.
	 *
	 * @param menuPrincipalControleurTest the new parent
	 */
	public void setParent(MenuPrincipalControleur menuPrincipalControleurTest) {
		this.parent = menuPrincipalControleurTest;
	}



}