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

/**
 * 
 * @author Linxin
 *
 */
public class GestionStockController implements Initializable {
	@FXML
	private static AnchorPane fenetre;
	@FXML
	private TableColumn<Ingredients, Integer> colID;
	@FXML
	private TableColumn<Ingredients, String> colNom;
	@FXML
	private TableColumn<Ingredients, Integer> colPrix;
	@FXML
	private TableColumn<Ingredients, Integer> colQte;
	@FXML
	private TableColumn<Ingredients, String> colFournisseur;
	@FXML
	private TableView<Ingredients> tview;
	@FXML
	private static ObservableList<Ingredients> data= FXCollections.observableArrayList();
    @FXML
	private ResourceBundle bundle;
    @FXML
    private Button btn1;
    @FXML
    private Button btnCommander;
    
	private Ingredients mainApp;
	private Connection conn;
	private MenuPrincipalControleur parent;
	boolean isSelected = false;
	public static String Nom;
	public static String qteRest;
	public static String idFournisseur;
	public static String idIngredient;
	
	private static Stage stage;
	DAOUtilisateur daoUtilisateur = new DAOUtilisateur();
	DAOIngredients daoIngredient = new DAOIngredients();
	public GestionStockController() {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		try {
			conn = bddUtil.dbConnect();
			data = FXCollections.observableArrayList();
			setCellTable();
			loadDataFrombase();
			initialize();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
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
	     * Remplir la table de la gestion de stock
	     */
	private void setCellTable() {
		colID.setCellValueFactory(new PropertyValueFactory<Ingredients, Integer>("idIngredient"));
		colNom.setCellValueFactory(new PropertyValueFactory<Ingredients, String>("nomIngredient"));
		colPrix.setCellValueFactory(new PropertyValueFactory<Ingredients, Integer>("prixIngredient"));
		colQte.setCellValueFactory(new PropertyValueFactory<Ingredients, Integer>("quantiteIngredient"));
		colFournisseur.setCellValueFactory(new PropertyValueFactory<Ingredients, String>("fournisseur"));

	}

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

	public void setMainApp(Ingredients mainApp) {
		this.mainApp = mainApp;

	}


	@FXML
	public void RetourMenuPrincipal(ActionEvent event) {
		try {
			MenuPrincipalControleur.getStock().close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
/**
 * Un button pour changer la page pour commander les ingrédients
 * @param event
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
				FonctionsControleurs.alerteAttention("Attention!", null, "Veuillez s��lectionner un ingredient pour pouvoir commander");
			}
			

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public static String getNom() {
		return Nom;
	}

	public static String getQteRest() {
		return qteRest;
	}

	public static String getIdfournisseur() {
		return idFournisseur;
	}
	
	public static Stage getStage() {
		return stage;
	}

	public static void setStage(Stage stage) {
		GestionStockController.stage = stage;
	}

	public void setParent(MenuPrincipalControleur menuPrincipalControleur) {
		// TODO Auto-generated method stub
		this.parent = menuPrincipalControleur;
		tview.setItems(getTableData());

	}
	public static ObservableList<Ingredients> getTableData() {
		return data;
	}


}