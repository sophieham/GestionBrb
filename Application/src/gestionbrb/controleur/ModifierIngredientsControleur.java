package gestionbrb.controleur;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import gestionbrb.model.Ingredients;
import gestionbrb.util.bddUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
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
	
	private Stage dialogStage;
	IngredientsProduitsControleur mainApp;
	private Ingredients ingredient;
	private boolean okClicked = false;
	
	@FXML
	private void initialize() {
		try {
			Connection conn = bddUtil.dbConnect();
			ResultSet fournisseurDB = conn.createStatement().executeQuery("select idFournisseur, nom from fournisseur");

			while (fournisseurDB.next()) {
				listeFournisseur.add("ID: "+fournisseurDB.getInt("idFournisseur")+" -> "+fournisseurDB.getString("nom"));
				System.out.println("ID: "+fournisseurDB.getInt("idFournisseur")+" -> "+fournisseurDB.getString("nom"));
			}
			chChoixFournisseur.setItems(listeFournisseur);
			
		} catch (Exception e) {
			e.printStackTrace();
			FonctionsControleurs.alerteErreur("Erreur!", "Erreur d'�xecution", "D�tails: "+e);
		}
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
		chPrixIngredient.setText(Float.toString(ingredient.getPrixIngredient()));
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
			FonctionsControleurs.alerteErreur("Erreur", "Erreur d'�xecution", "D�tails: "+e);
		}
	}
	@FXML
	private void actionAnnuler() {
		dialogStage.close();
	}
	
	public boolean estValide() {
		String erreurMsg = "";

		if (chQuantiteIngredient.getText() == null || chQuantiteIngredient.getText().length() == 0) {
			erreurMsg += "Veuillez remplir la quantit� de l'ingr�dient\n";
		} else {
			try {
				Integer.parseInt(chQuantiteIngredient.getText());
			} catch (NumberFormatException e) {
				erreurMsg += "Erreur! Le champ Quantit� n'accepte que les nombres\n";
			}
		}
		
		if (chPrixIngredient.getText() == null || chPrixIngredient.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le prix de l'ingredient\n";
		} else {
			try {
				Integer.parseInt(chPrixIngredient.getText());
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
			FonctionsControleurs.alerteErreur("Entr�e incorrecte", "Corrigez les erreurs suivantes pour pouvoir modifier les informations",
					erreurMsg);

			return false;
		}
	}
	


}
