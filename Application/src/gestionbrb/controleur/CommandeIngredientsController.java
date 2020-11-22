package gestionbrb.controleur;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import gestionbrb.Connexion;
import gestionbrb.DAO.DAOCommande;
import gestionbrb.DAO.DAOFournisseur;
import gestionbrb.DAO.DAOIngredients;
import gestionbrb.DAO.DAOUtilisateur;

// TODO: Auto-generated Javadoc
/**
 * The Class CommandeIngredientsController.
 *
 * @author Linxin
 */
public class CommandeIngredientsController implements Initializable{
	
	/** The vbox. */
	@FXML
	private VBox vbox;
	
	/** The ingredients. */
	@FXML
	private Label ingredients;
	
	/** The fournisseur. */
	@FXML
	private Label fournisseur;
	
	/** The prix uni. */
	@FXML
	private Label prixUni;
	
	/** The qte. */
	@FXML
	private Label qte;
	
	/** The totalprix. */
	@FXML
	private Label totalprix;
	
	/** The Nom ingredients. */
	@FXML
	private Label NomIngredients;
	
	/** The prixunite. */
	@FXML
	private Label prixunite;
	
	/** The choice fournisseur. */
	@FXML
	private ChoiceBox<String> choiceFournisseur;
	
	/** The prixtotal. */
	@FXML
	private Label prixtotal;
	
	/** The spinner. */
	@FXML
	private Spinner<Integer> spinner;
	
	/** The menubutton. */
	@FXML
	private MenuButton menubutton;
	
	/** The data. */
	@FXML
	private ObservableList<String> data;
	
	/** The bundle. */
	@FXML
	private ResourceBundle bundle;
	
	/** The label. */
	@FXML
	private Label label;
	
	/** The btn. */
	@FXML
	private Button btn;
	
	/** The output. */
	private static String output;
	
	/** The value. */
	private static int value;
	
	/** The prix. */
	private static double prix;
	
	/** The qte rest. */
	private static int qteRest;
	
	/** The facture ingredient. */
	private static Stage factureIngredient; 
	
	/** The dao utilisateur. */
	DAOUtilisateur daoUtilisateur = new DAOUtilisateur();

	/** The dao fournisseur. */
	DAOFournisseur daoFournisseur = new DAOFournisseur();
	
	/** The dao ingredient. */
	DAOIngredients daoIngredient = new DAOIngredients();
	
/**
 * Instantiates a new commande ingredients controller.
 */
public CommandeIngredientsController() {
	

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
	fournisseur.setText(bundle.getString("Fournisseur"));
	ingredients.setText(bundle.getString("ingredients"));
	prixUni.setText(bundle.getString("prixUni"));
	qte.setText(bundle.getString("qte"));
	totalprix.setText(bundle.getString("totalprix"));
	label.setText(bundle.getString("key6"));
	btn.setText(bundle.getString("Commander"));

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
	 		 initialize();
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "Détails: "+e);
			e.printStackTrace();
		}
	 	String Nom = GestionStockController.Nom;
	 	NomIngredients.setText(Nom);
	 
			//initSpinner();
			try {
				choice();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		
	}
	
	/**
	 * Choisir le fournisseur de l'ingrédients.
	 *
	 * @throws SQLException the SQL exception
	 */
	@FXML
	public void choice() throws SQLException {
		try {
			ObservableList<String> data = FXCollections.observableArrayList();
			
			data.addAll(daoFournisseur.afficherNomFournisseur(GestionStockController.Nom));
			choiceFournisseur.setItems(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	
	/**
	 * Prixunite.
	 *
	 * @throws SQLException the SQL exception
	 */
	@FXML
	public void prixunite() throws SQLException {
		try {
			for (int i = 0; i <daoIngredient.afficherPrixIngredient(GestionStockController.Nom, output).size(); i++) {
				prixunite.setText(daoIngredient.afficherPrixIngredient(GestionStockController.Nom, output).get(i).toString());
				prix= daoIngredient.afficherPrixIngredient(GestionStockController.Nom, output).get(i);
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Inits the spinner.
	 *
	 * @throws SQLException the SQL exception
	 */
	@FXML
		private void initSpinner() throws SQLException {
		spinner.setEditable(true);   
		spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000));
	
		
	
		//
		spinner.valueProperty().addListener((obs, oldValue, newValue) -> {
		value = newValue;
		try {
			prixtotal();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		});
		;
	}
	
		/**
		 * Commit editor text.
		 *
		 * @param <Integer> the generic type
		 * @param spinner the spinner
		 */
		//@SuppressWarnings("hiding")
		@SuppressWarnings({ "hiding", "unused" })
		private <Integer> void commitEditorText(Spinner<Integer> spinner) {
		    if (!spinner.isEditable())
		        return;
		    String text = spinner.getEditor().getText();
		    SpinnerValueFactory<Integer> valueFactory = spinner.getValueFactory();
		    if (valueFactory != null) {
		        StringConverter<Integer> converter = valueFactory.getConverter();
		        if (converter != null) {
		        	Integer value = converter.fromString(text);
		            valueFactory.setValue(value); 


		        }
		    }
		   
		}

	
/**
 * Prixtotal.
 *
 * @throws SQLException the SQL exception
 */
@FXML
	public void prixtotal() throws SQLException {
	try {
		prixtotal.setText(InttoString(prix*value)+DAOCommande.recupererDevise());
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	
/**
 * Intto string.
 *
 * @param prixtotal2 the prixtotal 2
 * @return the string
 */
public static String InttoString(double prixtotal2) {
    
        return String.valueOf(prixtotal2);
   
}
	
/**
 * Stringto int.
 *
 * @param n the n
 * @return the int
 */
public static int StringtoInt(String n) {
	return Integer.valueOf(n).intValue();
}

	/**
	 * Choice.
	 *
	 * @param event the event
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SQLException the SQL exception
	 */
	public void choice(ActionEvent event) throws IOException, SQLException {
		try {
			output = choiceFournisseur.getSelectionModel().getSelectedItem().toString();
			
			prixunite();
			initSpinner();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * Qte rest.
	 *
	 * @throws SQLException the SQL exception
	 */
	public void qteRest() throws SQLException {
		try {
			for (int i = 0; i <daoIngredient.afficherQteRestante(GestionStockController.Nom, output).size(); i++) {
				qteRest = Integer.parseInt(daoIngredient.afficherQteRestante(GestionStockController.Nom, output).get(i));
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Commande.
	 *
	 * @param event the event
	 * @throws SQLException the SQL exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException the class not found exception
	 */
	@FXML
    public void Commande (ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
		try {
			qteRest();
			int n = qteRest+value;
			daoIngredient.commanderIngredients(InttoString(n), GestionStockController.Nom, output);
			refresh();
						daoFournisseur.fournirIngredient(StringtoInt(GestionStockController.idIngredient), daoFournisseur.afficherIDFournisseur(GestionStockController.Nom), value, InttoString(value*prix));
				//refresh();
						GestionStockController.getStage().close();
				FonctionsControleurs.alerteInfo("Ajout effectué", null, "La commande a été éffectuée avec succès!");
				afficherFacture();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}	
    
	/**
	 * Afficher facture.
	 */
	public void afficherFacture() {
		try {
			Locale locale = new Locale("fr", "FR");

			ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_fr", locale);

			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/FactureIngredient.fxml"), bundle);
			Parent vueFacture= (Parent) loader.load();
			setFactureIngredient((new Stage()));
			getFactureIngredient().setScene(new Scene(vueFacture));
			getFactureIngredient().show();
			getFactureIngredient().setTitle("Facture");
			getFactureIngredient().getIcons().add(new Image(
	          	      Connexion.class.getResourceAsStream( "ico.png" ))); 

			FactureIngredientControleur controller = loader.getController();
			controller.setParent(this);
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "Détails: "+e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Refresh.
	 *
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the SQL exception
	 */
	private void refresh() throws ClassNotFoundException, SQLException {
		GestionStockController.getTableData().clear();
		GestionStockController.getTableData().addAll(daoIngredient.afficher());
		
	}
	
	
	/**
	 * Gets the prix.
	 *
	 * @return the prix
	 */
	public static double getPrix() {
		return prix;
	}
	
	/**
	 * Gets the vbox.
	 *
	 * @return the vbox
	 */
	public VBox getVbox() {
		return vbox;
	}
	
	/**
	 * Gets the ingredients.
	 *
	 * @return the ingredients
	 */
	public Label getIngredients() {
		return ingredients;
	}
	
	/**
	 * Gets the fournisseur.
	 *
	 * @return the fournisseur
	 */
	public Label getFournisseur() {
		return fournisseur;
	}
	
	/**
	 * Gets the prix uni.
	 *
	 * @return the prix uni
	 */
	public Label getPrixUni() {
		return prixUni;
	}
	
	/**
	 * Gets the qte.
	 *
	 * @return the qte
	 */
	public Label getQte() {
		return qte;
	}
	
	/**
	 * Gets the totalprix.
	 *
	 * @return the totalprix
	 */
	public Label getTotalprix() {
		return totalprix;
	}
	
	/**
	 * Gets the nom ingredients.
	 *
	 * @return the nom ingredients
	 */
	public Label getNomIngredients() {
		return NomIngredients;
	}

	/**
	 * Gets the facture ingredient.
	 *
	 * @return the facture ingredient
	 */
	public static Stage getFactureIngredient() {
		return factureIngredient;
	}

	/**
	 * Sets the facture ingredient.
	 *
	 * @param factureIngredient the new facture ingredient
	 */
	public static void setFactureIngredient(Stage factureIngredient) {
		CommandeIngredientsController.factureIngredient = factureIngredient;
	}
	
}