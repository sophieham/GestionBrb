package gestionbrb.controleur;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import java.util.ResourceBundle;

import gestionbrb.Connexion;
import gestionbrb.DAO.DAOCommande;
import gestionbrb.DAO.DAOFournisseur;
import gestionbrb.DAO.DAOIngredients;
import gestionbrb.controleur.GestionStockController;
/**
 * 
 * @author Linxin
 *
 */
public class CommandeIngredientsController implements Initializable{
	@FXML
	private VBox vbox;
	@FXML
	private Label ingredients;
	@FXML
	private Label fournisseur;
	@FXML
	private Label prixUni;
	@FXML
	private Label qte;
	@FXML
	private Label totalprix;
	@FXML
	private Label NomIngredients;
	@FXML
	private Label prixunite;
	@FXML
	private ChoiceBox<String> choiceFournisseur;
	@FXML
	private Label prixtotal;
	@FXML
	private Spinner<Integer> spinner;
	@FXML
	private MenuButton menubutton;
	@FXML
	private ObservableList<String> data;
	private static String output;
	private static int value;
	private static int prix;
	private static int qteRest;
	
	private static Stage factureIngredient; 
	
	DAOFournisseur daoFournisseur = new DAOFournisseur();
	DAOIngredients daoIngredient = new DAOIngredients();
	
public CommandeIngredientsController() {
	

}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	 	try {
	 		
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "DÃ©tails: "+e);
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
	@FXML
	public void choice() throws SQLException {
		try {
			ObservableList<String> data = FXCollections.observableArrayList();
			
			data.addAll(daoFournisseur.afficherNomFournisseur(GestionStockController.Nom));
			System.out.println(GestionStockController.Nom);
			choiceFournisseur.setItems(data);
			System.out.println("idx of: "+ GestionStockController.Nom+ " " +daoFournisseur.choixFournisseur().indexOf(GestionStockController.Nom));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	
	@FXML
	public void prixunite() throws SQLException {
		try {
			for (int i = 0; i <daoIngredient.afficherPrixIngredient(GestionStockController.Nom, output).size(); i++) {
				prixunite.setText(daoIngredient.afficherPrixIngredient(GestionStockController.Nom, output).get(i));
				prix = Integer.parseInt(daoIngredient.afficherPrixIngredient(GestionStockController.Nom, output).get(i));
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@FXML
		private void initSpinner() throws SQLException {
		spinner.setEditable(true);   
		spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000));
	
		
		//System.out.println(spinner.getValue().toString());
		
		//
		spinner.valueProperty().addListener((obs, oldValue, newValue) -> {
		value = newValue;
			System.out.println("New value: "+value);
		try {
			prixtotal();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		});
		;
		
		/**spinner.getEditor().setOnAction(e -> {
		   
		    String n = spinner.getEditor().getText(); 
		    System.out.println(n);
		}); commitEditorText(spinner);**/
	}
	
		//@SuppressWarnings("hiding")
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
		            System.out.println(spinner.getValue().toString());


		        }
		    }
		   
		}

	
@FXML
	public void prixtotal() throws SQLException {
	try {
		System.out.println(prix);

		System.out.println(value);
		//System.out.println(prix*value);
		prixtotal.setText(InttoString(prix*value)+DAOCommande.recupererDevise());
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	
public static String InttoString(int prixtotal2) {
    
        return String.valueOf(prixtotal2);
   
}
	
public static int StringtoInt(String n) {
	return Integer.valueOf(n).intValue();
}

	public void choice(ActionEvent event) throws IOException, SQLException {
		try {
			output = choiceFournisseur.getSelectionModel().getSelectedItem().toString();
			System.out.println(output);
			
			
			prixunite();
			initSpinner();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
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
	@FXML
    public void Commande (ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
		try {
			qteRest();
			int n = qteRest+value;
			daoIngredient.commanderIngredients(InttoString(n), GestionStockController.Nom, output);
			System.out.println("nouveauquantite" +n);
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
    
	public void afficherFacture() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/FactureIngredient.fxml"));
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
	
	private void refresh() throws ClassNotFoundException, SQLException {
		GestionStockController.getTableData().clear();
		GestionStockController.getTableData().addAll(daoIngredient.afficher());
		
	}
	
	
	public static double getPrix() {
		return prix;
	}
	public VBox getVbox() {
		return vbox;
	}
	public Label getIngredients() {
		return ingredients;
	}
	public Label getFournisseur() {
		return fournisseur;
	}
	public Label getPrixUni() {
		return prixUni;
	}
	public Label getQte() {
		return qte;
	}
	public Label getTotalprix() {
		return totalprix;
	}
	public Label getNomIngredients() {
		return NomIngredients;
	}

	public static Stage getFactureIngredient() {
		return factureIngredient;
	}

	public static void setFactureIngredient(Stage factureIngredient) {
		CommandeIngredientsController.factureIngredient = factureIngredient;
	}
	
}