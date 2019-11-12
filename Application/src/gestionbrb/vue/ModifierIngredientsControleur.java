package gestionbrb.vue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import gestionbrb.IngredientsProduits;
import gestionbrb.controleur.FonctionsControleurs;
import gestionbrb.model.Ingredients;
import gestionbrb.util.bddUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifierIngredientsControleur extends FonctionsControleurs {
	
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
	IngredientsProduits mainApp;
	private Ingredients ingredient;
	private boolean okClicked = false;
	
	@FXML
	private void initialize() {
		try {
			Connection conn = bddUtil.dbConnect();
			ResultSet rs = conn.createStatement().executeQuery("select idFournisseur, nom from fournisseur");

			while (rs.next()) {
				listeFournisseur.add("ID: "+rs.getInt("idFournisseur")+" -> "+rs.getString("nom"));
			}
			chChoixFournisseur.setItems(listeFournisseur);
		} catch (Exception e) {
			e.printStackTrace();
			alerteErreur("Erreur!", "Erreur d'�xecution", "D�tails: "+e);
		}
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public void setMainApp(IngredientsProduits mainApp) {
		this.mainApp = mainApp;
	}
	
	public void setIngredients(Ingredients ingredient) throws SQLException, ClassNotFoundException {
		this.ingredient = ingredient;
		chNomIngredient.setText(ingredient.getNomIngredient());
		chPrixIngredient.setText(Integer.toString(ingredient.getPrixIngredient()));
		chQuantiteIngredient.setText(Integer.toString(ingredient.getQuantiteIngredient()));
		chChoixFournisseur.setValue(ingredient.getFournisseur());
	}
	
	
	public boolean isOkClicked() {
		return okClicked;
	}
	
	@FXML
	public void actionValiderIngredient() {
		if (estValide()) {
			ingredient.setNomIngredient(chNomIngredient.getText());
			ingredient.setPrixIngredient(Integer.parseInt(chPrixIngredient.getText()));
			ingredient.setQuantiteIngredient(Integer.parseInt(chQuantiteIngredient.getText()));
			ingredient.setFournisseur(chChoixFournisseur.getValue());

			okClicked = true;
			dialogStage.close();
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
			alerteErreur("Entr�e incorrecte", "Corrigez les erreurs suivantes pour pouvoir modifier les informations",
					erreurMsg);

			return false;
		}
	}
	


}
