package gestionbrb.controleur;

import java.io.IOException;
import java.net.URL;
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
import javafx.fxml.Initializable;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/*
 * Controleur pour dé–™arrer une commande
 * @author Sophie
 */

public class DemarrerCommandeControleur{
	// Variables pour la partie ré–Ÿervation
	@FXML
	private AnchorPane mainWindow;
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
	
	// Variables pour la partie dé–™arrer une nouvelle commande
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
	@FXML
	private ResourceBundle bundle;
	
	@FXML
	private Button btnValider;
	@FXML
	private TitledPane titlepaneDemarrer;
	@FXML
	private Label labelcouvert;
	@FXML
	private Label labelTable1;
	@FXML
	private Label labelTable;
	@FXML
	private Button buttonCalenderier;
	@FXML
	private Button btnRetour;
	@FXML
	private Label nom;
	@FXML
	private Label prenom;
	@FXML
	private Label heure;
	@FXML
	private Label date;
	@FXML
	private Label nombre;
	@FXML
	private Label telephone;
	@FXML
	private Label demande;
	@FXML
	private Button btnReserve;
	@FXML
	private Label labelcentre;
	DAOCalendrier daoCalendrier = new DAOCalendrier();
	DAOTables daoTables = new DAOTables();
	DAOCommande daoCommande = new DAOCommande();
	DAOUtilisateur daoUtilisateur = new DAOUtilisateur();
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
	 * <i> Initialise le controleur. </i> <br> Remplit la liste des tables avec les donné–‘s
	 * provenant de la base de donné–‘. <br>
	 * Met ï¿½ jour la liste des tables libres. <br>
	 * <br>
	 * Affiche un fené˜¾re d'erreur si il y a des exceptions
	 */
	@FXML
	private void initialize() {
		//bundle = resources;
		try {
			String langue = daoUtilisateur.recupererLangue(ConnexionControleur.getUtilisateurConnecte().getIdUtilisateur());
			System.out.println(langue);
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
			lblOccupation.setText((daoTables.afficherNoTables().size()-daoTables.afficherTablesLibres().size())+" tables occupé–‘(s), "+daoTables.afficherTablesLibres().size()+" libres");
			champNoTable.setItems(daoTables.afficherNoTables());
			champChoixTable.setItems(daoTables.afficherTablesLibres());
			
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'é–¤é–�ution", "Une erreur est survenue", "Dé– ails: "+e);
			e.printStackTrace();
		}
		

	}
	private void loadLang(String lang, String LANG) {
		Locale locale = new Locale(lang, LANG);  
		System.out.println(bundle);
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
		telephone.setText(bundle.getString("tÃ©lÃ©phone"));
		date.setText(bundle.getString("Date"));
		demande.setText(bundle.getString("Demande"));
		btnReserve.setText(bundle.getString("RÃ©server"));
		colonneNoTable.setText(bundle.getString("Table"));
		colonneNbCouvertsMax.setText(bundle.getString("Max"));
		colonneStatut.setText(bundle.getString("Statut"));
		lblOccupation.setText(bundle.getString("x"));
		labelcentre.setText(bundle.getString("Occupation"));
		labelTable.setText(bundle.getString("Table"));
	}
	/**
	 * Appellï¿½ lors de l'appui sur le bouton. <br>
	 * Ouvre le registre des ré–Ÿervations.
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
			FonctionsControleurs.alerteErreur("Erreur", "Impossible d'ouvrir cette fenetre", "Dé– ails: "+e);
			e.printStackTrace();
		}
	}

	/**
	 * Appellï¿½ lors de l'appui sur le bouton Retour au menu principal <br>
	 * Il ferme la page actuelle et revient au menu principal.
	 * 
	 * @throws IOException
	 */
	public void afficherMenuPrincipal() {
		MenuPrincipalControleur.getDemarrerCommande().close();
	}

	/** 
	 * Actualise les donné–‘s de la page lorsqu'une nouvelle commande est lancé–‘.
	 */
	public void refreshMain() {
		tableTable.getItems().clear();
		champNoTable.setValue(null);
		champChoixTable.setValue(null);
		try {
			daoTables.afficherNoTables().clear();
			daoTables.afficherTablesLibres().clear();
		} catch (SQLException e) {
			FonctionsControleurs.alerteErreur("Erreur d'é–¤é–�ution", "Une erreur est survenue", "Dé– ails: "+e);
			e.printStackTrace();
		}
		initialize();
	}
	
	public boolean isOkClicked() {
		return okClicked;
	}
	
	/**
	 * Dé–™arre une nouvelle commande en prenant en compte le nombre de couverts et le numé–žo de table dé–’ini en modifiant son occupation. <br>
	 * Affiche une nouvelle page Commande.fxml <br>
	 * <br> 
	 * Affiche un message d'erreur si la commande ne peut pas é˜¾re lancé–‘.
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
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/Commande.fxml"));
			Parent vueCommande = (Parent) loader.load();
			setFenetreCommande(new Stage());
			getFenetreCommande().setTitle("-- Commande de la table "+numTable+" --");
			getFenetreCommande().setScene(new Scene(vueCommande));
			getFenetreCommande().show();
			MenuPrincipalControleur.getDemarrerCommande().close();
			
			CommandeControleur controller = loader.getController();
	        controller.setParent(this);
			
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Impossible d'ouvrir cette fenetre", "Dé– ails: "+e);
			e.printStackTrace();
		}
	}

	/**
	 * Extrait le numé–žo de table provenant de la table choisie lors de la ré–Ÿervation <br>
	 * Recupé‘¢e la valeur de la cible puis en fonction de la taille va ré–�uperer la valeur du nombre de la table puis transforme la valeur en entier
	 * <br>
	 * <br>
	 * <i> exemple: Table nï¿½2 [4 ï¿½ 5 couverts] > va ré–�upé–žer la valeur 2 </i>
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
	 * Appellï¿½ quand l'utilisateur clique sur ré–Ÿerver <br>
	 * Ajoute les donné–‘s saisies ï¿½ la base de donné–‘ si elles sont valides 
	 * et affiche un message si cela s'est bien passï¿½.<br>
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
				FonctionsControleurs.alerteInfo("Reservation enregistré–‘!", "", "La reservation ï¿½ bien é– ï¿½ enregistré–‘!");
			} catch (Exception e) {
				FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "Dé– ails: " + e);
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
	 * Vé–žifie si la saisie est conforme aux donné–‘s requises <br>
	 * Cette mé– hode vé–žifie chacune des donné–‘s saisies et dé– ermine 
	 * si elle ré–œond aux crité‘¢es de la case.
	 * 
	 * @return true si la saisie est bien conforme, false sinon
	 */
	public boolean estValide() {
		String msgErreur = "";

		if (champNom.getText() == null || champNom.getText().length() == 0) {
			msgErreur += "Veuillez remplir le nom\n";
		}
		if (champPrenom.getText() == null || champPrenom.getText().length() == 0) {
			msgErreur += "Veuillez remplir le pré–šom\n";
		}
		if (champNumTel.getText() == null || champNumTel.getText().length() == 0) {
			msgErreur += "Veuillez rentrer le numé–žo de té–˜é–œhone\n";
		} else {
			Pattern p = Pattern.compile("(0|\\+)[0-9]{8,12}"); // regex d'un numé–žo de té–˜é–œhone, frané�°is ou é– ranger
			Matcher m = p.matcher(champNumTel.getText());
			if (!(m.find() && m.group().equals(champNumTel.getText()))) {
				msgErreur += "Erreur! Le champ no. té–˜é–œhone n'accepte que les numé–žos commené�°nt par + ou 0 et ayant une longueur entre 8 et 12 chiffres\n";
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
				msgErreur += "Format de l'heure incorrect, veuillez ré–‘ssayer avec le format hh:mm approprié–ˆn";
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
			FonctionsControleurs.alerteErreur("Entré–‘ incorrecte", "Corrigez les erreurs suivantes pour pouvoir enregistrer la reservation",msgErreur);
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