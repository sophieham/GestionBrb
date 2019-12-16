package gestionbrb.controleur;

import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import gestionbrb.DAO.DAOFournisseur;
import gestionbrb.DAO.DAOUtilisateur;
import gestionbrb.model.Ingredients;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * 
 * @author Leo
 *
 */

public class ModifierIngredientsControleur {
	
	@FXML
	private TextField chNomIngredient;
	@FXML
	private TextField chPrixIngredient;
	@FXML
	private TextField chQuantiteIngredient;
	@FXML
	private ObservableList<String> listeFournisseur = FXCollections.observableArrayList();
	@FXML
	private ChoiceBox<String> chChoixFournisseur;
	@FXML
	private Label prix;
	@FXML
	private Label qte;
	@FXML
	private Label nom;
	@FXML
	private Label fournisseur;
	@FXML
	private Button valider;
	@FXML
	private ResourceBundle bundle;
	DAOUtilisateur daoUtilisateur = new DAOUtilisateur();
	private Stage dialogStage;
	IngredientsProduitsControleur mainApp;
	private Ingredients ingredient;
	private boolean okClicked = false;
	
	DAOFournisseur daoFournisseur = new DAOFournisseur();
	
	@FXML
	private void initialize() {
		
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
		try {
			
			chChoixFournisseur.setItems(daoFournisseur.choixFournisseur());
			
		} catch (Exception e) {
			e.printStackTrace();
			FonctionsControleurs.alerteErreur("Erreur!", "Erreur d'éxecution", "Détails: "+e);
		}
	}

	
	private void loadLang(String lang, String LANG) {
		Locale locale = new Locale(lang, LANG);  
		
		ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_"+lang, locale);
		prix.setText(bundle.getString("Prix"));
		qte.setText(bundle.getString("qte"));
		nom.setText(bundle.getString("Nom"));
		fournisseur.setText(bundle.getString("Fournisseur"));
		valider.setText(bundle.getString("Valider"));
		

		
	}
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public void setMainApp(IngredientsProduitsControleur mainApp) {
		this.mainApp = mainApp;
	}
	public void setIngredients(Ingredients ingredient) throws SQLException, ClassNotFoundException {
		this.ingredient = ingredient;
		chNomIngredient.setText(ingredient.getNomIngredient());
		chPrixIngredient.setText(Double.toString(ingredient.getPrixIngredient()));
		chQuantiteIngredient.setText(Integer.toString(ingredient.getQuantiteIngredient()));
		chChoixFournisseur.setValue(ingredient.getFournisseur());
	}
	
	
	public boolean isOkClicked() {
		return okClicked;
	}
	
	@FXML
	public void actionValiderIngredient() {
		
		try {
			if (estValide()) {
				ingredient.setNomIngredient(chNomIngredient.getText());
				ingredient.setPrixIngredient(Integer.parseInt(chPrixIngredient.getText()));
				ingredient.setQuantiteIngredient(Integer.parseInt(chQuantiteIngredient.getText()));
				ingredient.setFournisseur(chChoixFournisseur.getValue());

				okClicked = true;
				dialogStage.close();
			}
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Erreur d'éxecution", "Détails: "+e);
		}
	}
	@FXML
	private void actionAnnuler() {
		dialogStage.close();
	}
	
	public boolean estValide() {
		String erreurMsg = "";

		if (chQuantiteIngredient.getText() == null || chQuantiteIngredient.getText().length() == 0) {
			erreurMsg += "Veuillez remplir la quantité de l'ingrédient\n";
		} else {
			try {
				Integer.parseInt(chQuantiteIngredient.getText());
			} catch (NumberFormatException e) {
				erreurMsg += "Erreur! Le champ Quantité n'accepte que les nombres\n";
			}
		}
		
		if (chPrixIngredient.getText() == null || chPrixIngredient.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le prix de l'ingredient\n";
		} else {
			try {
				Double.parseDouble(chPrixIngredient.getText());
			} catch (NumberFormatException e) {
				erreurMsg += "Erreur! Le champ prix n'accepte que les nombres\n";
			}
		}
		if (chNomIngredient.getText() == null || chNomIngredient.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le nom de l'ingredient\n";
			}
		if (erreurMsg.length() == 0) {
			return true;
		} else {
			// Affiche un message d'erreur
			FonctionsControleurs.alerteErreur("Entrée incorrecte", "Corrigez les erreurs suivantes pour pouvoir modifier les informations",
					erreurMsg);

			return false;
		}
	}
	


}