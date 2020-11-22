package gestionbrb.controleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import gestionbrb.Connexion;
import gestionbrb.DAO.DAOIngredients;
import gestionbrb.DAO.DAOUtilisateur;
import gestionbrb.model.Ingredients;
import gestionbrb.util.bddUtil;
//import gestionbrb.GestionStockAdmin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class GestionStockController.
 *
 * @author Linxin
 */
public class GestionStockController implements Initializable {
	
	/** The fenetre. */
	@FXML
	private static AnchorPane fenetre;
	
	/** The col ID. */
	@FXML
	private TableColumn<Ingredients, Integer> colID;
	
	/** The col nom. */
	@FXML
	private TableColumn<Ingredients, String> colNom;
	
	/** The col prix. */
	@FXML
	private TableColumn<Ingredients, Integer> colPrix;
	
	/** The col qte. */
	@FXML
	private TableColumn<Ingredients, Integer> colQte;
	
	/** The col fournisseur. */
	@FXML
	private TableColumn<Ingredients, String> colFournisseur;
	
	/** The tview. */
	@FXML
	private TableView<Ingredients> tview;
	
	/** The data. */
	@FXML
	private static ObservableList<Ingredients> data= FXCollections.observableArrayList();
    
    /** The bundle. */
    @FXML
	private ResourceBundle bundle;
    
    /** The btn 1. */
    @FXML
    private Button btn1;
    
    /** The btn commander. */
    @FXML
    private Button btnCommander;
    
	/** The conn. */
	private Connection conn;
	
	/** The is selected. */
	boolean isSelected = false;
	
	/** The Nom. */
	public static String Nom;
	
	/** The qte rest. */
	public static String qteRest;
	
	/** The id fournisseur. */
	public static String idFournisseur;
	
	/** The id ingredient. */
	public static String idIngredient;
	
	/** The stage. */
	private static Stage stage;
	
	/** The dao utilisateur. */
	DAOUtilisateur daoUtilisateur = new DAOUtilisateur();
	
	/** The dao ingredient. */
	DAOIngredients daoIngredient = new DAOIngredients();
	
	/**
	 * Instantiates a new gestion stock controller.
	 */
	public GestionStockController() {

	}

	/**
	 * Initialize.
	 *
	 * @param location the location
	 * @param resources the resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		try {
			setConn(bddUtil.dbConnect());
			data = FXCollections.observableArrayList();
			setCellTable();
			loadDataFrombase();
			initialize();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	 
 	/**
 	 * Initialize.
 	 */
 	@FXML
		public void initialize() {
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
			colID.setText(bundle.getString("Identifiant"));
			colNom.setText(bundle.getString("Nom"));
			colPrix.setText(bundle.getString("Prix"));
			colQte.setText(bundle.getString("Qte"));
			colFournisseur.setText(bundle.getString("Fournisseur"));
			btn1.setText(bundle.getString("key5"));
			btnCommander.setText(bundle.getString("Commander"));

	    }

	    /**
    	 * Remplir la table de la gestion de stock.
    	 */
	private void setCellTable() {
		colID.setCellValueFactory(new PropertyValueFactory<Ingredients, Integer>("idIngredient"));
		colNom.setCellValueFactory(new PropertyValueFactory<Ingredients, String>("nomIngredient"));
		colPrix.setCellValueFactory(new PropertyValueFactory<Ingredients, Integer>("prixIngredient"));
		colQte.setCellValueFactory(new PropertyValueFactory<Ingredients, Integer>("quantiteIngredient"));
		colFournisseur.setCellValueFactory(new PropertyValueFactory<Ingredients, String>("fournisseur"));

	}

	/**
	 * Load data frombase.
	 */
	private void loadDataFrombase() {
		try {
			getTableData().addAll(daoIngredient.afficher());
			tview.setRowFactory(tv -> {
				TableRow<Ingredients> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					Ingredients rowData = row.getItem();
					Nom = tview.getColumns().get(1).getCellObservableValue(rowData).getValue().toString();
					qteRest = tview.getColumns().get(3).getCellObservableValue(rowData).getValue().toString();
					idFournisseur = tview.getColumns().get(4).getCellObservableValue(rowData).getValue().toString();
					idIngredient = tview.getColumns().get(0).getCellObservableValue(rowData).getValue().toString();
					isSelected=true;
				});
				return row;
			});
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		tview.setItems(data);
	}

	/**
	 * Sets the main app.
	 *
	 * @param mainApp the new main app
	 */
	public void setMainApp(Ingredients mainApp) {

	}


	/**
	 * Retour menu principal.
	 *
	 * @param event the event
	 */
	@FXML
	public void RetourMenuPrincipal(ActionEvent event) {
		try {
			MenuPrincipalControleur.getStock().close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

/**
 * Un button pour changer la page pour commander les ingrédients.
 *
 * @param event the event
 */
	@FXML
	public void CommanderIngredients(ActionEvent event) {
		Parent root;
		try {
			if(isSelected) {
			Locale locale = new Locale("fr", "FR");
			ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_fr", locale);
			root = FXMLLoader.load(GestionStockController.class.getResource("../vue/CommanderIngredients.fxml"),bundle);
			setStage(new Stage());
			getStage().setTitle("Commande Ingredients");
			getStage().setScene(new Scene(root));
			getStage().show();
			getStage().getIcons().add(new Image(
	          	      Connexion.class.getResourceAsStream( "ico.png" ))); 
			}
			else {
				FonctionsControleurs.alerteAttention("Attention!", null, "Veuillez sélectionner un ingredient pour pouvoir commander");
			}
			

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	/**
	 * Gets the nom.
	 *
	 * @return the nom
	 */
	public static String getNom() {
		return Nom;
	}

	/**
	 * Gets the qte rest.
	 *
	 * @return the qte rest
	 */
	public static String getQteRest() {
		return qteRest;
	}

	/**
	 * Gets the idfournisseur.
	 *
	 * @return the idfournisseur
	 */
	public static String getIdfournisseur() {
		return idFournisseur;
	}
	
	/**
	 * Gets the stage.
	 *
	 * @return the stage
	 */
	public static Stage getStage() {
		return stage;
	}

	/**
	 * Sets the stage.
	 *
	 * @param stage the new stage
	 */
	public static void setStage(Stage stage) {
		GestionStockController.stage = stage;
	}

	/**
	 * Sets the parent.
	 *
	 * @param menuPrincipalControleur the new parent
	 */
	public void setParent(MenuPrincipalControleur menuPrincipalControleur) {
		tview.setItems(getTableData());

	}
	
	/**
	 * Gets the table data.
	 *
	 * @return the table data
	 */
	public static ObservableList<Ingredients> getTableData() {
		return data;
	}

	/**
	 * Gets the conn.
	 *
	 * @return the conn
	 */
	public Connection getConn() {
		return conn;
	}

	/**
	 * Sets the conn.
	 *
	 * @param conn the new conn
	 */
	public void setConn(Connection conn) {
		this.conn = conn;
	}


}