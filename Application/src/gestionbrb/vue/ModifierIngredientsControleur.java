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
<<<<<<< HEAD
<<<<<<< HEAD

=======
>>>>>>> 6ba442970c543bbaced91fda4674ecf5870a1eae
=======
>>>>>>> 6ba442970c543bbaced91fda4674ecf5870a1eae
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
<<<<<<< HEAD
<<<<<<< HEAD
			ResultSet rs = conn.createStatement().executeQuery("select idfournisseur, nom from fournisseur");

			while (rs.next()) {
				listeFournisseur.add("ID: "+rs.getInt("idfournisseur")+" -> "+rs.getString("nom"));
			}
			chChoixFournisseur.setItems(listeFournisseur);
		} catch (Exception e) {
=======
=======
>>>>>>> 6ba442970c543bbaced91fda4674ecf5870a1eae
			ResultSet rs = conn.createStatement().executeQuery("select idFournisseur, nom from fournisseur");

			while (rs.next()) {
				listeFournisseur.add("ID: "+rs.getInt("idFournisseur")+" -> "+rs.getString("nom"));
			}
			chChoixFournisseur.setItems(listeFournisseur);
		} catch (Exception e) {
			e.printStackTrace();
<<<<<<< HEAD
>>>>>>> 6ba442970c543bbaced91fda4674ecf5870a1eae
=======
>>>>>>> 6ba442970c543bbaced91fda4674ecf5870a1eae
			alerteErreur("Erreur!", "Erreur d'éxecution", "Détails: "+e);
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
	
<<<<<<< HEAD
<<<<<<< HEAD
	
=======
>>>>>>> 6ba442970c543bbaced91fda4674ecf5870a1eae
=======
>>>>>>> 6ba442970c543bbaced91fda4674ecf5870a1eae
	@FXML
	public void actionValiderIngredient() {
		if (estValide()) {
			String nomFournisseur = chChoixFournisseur.getValue().substring()
			ingredient.setNomIngredient(chNomIngredient.getText());
			ingredient.setPrixIngredient(Integer.parseInt(chPrixIngredient.getText()));
			ingredient.setQuantiteIngredient(Integer.parseInt(chQuantiteIngredient.getText()));
<<<<<<< HEAD
<<<<<<< HEAD
			ingredient.setFournisseur((chChoixFournisseur));
=======
			ingredient.setFournisseur(chChoixFournisseur.getValue());
>>>>>>> 6ba442970c543bbaced91fda4674ecf5870a1eae
=======
			ingredient.setFournisseur(chChoixFournisseur.getValue());
>>>>>>> 6ba442970c543bbaced91fda4674ecf5870a1eae

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
				Integer.parseInt(chPrixIngredient.getText());
			} catch (NumberFormatException e) {
				erreurMsg += "Erreur! Le champ prix n'accepte que les nombres\n";
			}
		}
		if (chNomIngredient.getText() == null || chNomIngredient.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le nom de l'ingredient\n";
<<<<<<< HEAD
<<<<<<< HEAD
			}
		
		if (chChoixFournisseur.getValue() == null) {
			erreurMsg += "Veuillez sélectionner le fournisseur\n";
=======
>>>>>>> 6ba442970c543bbaced91fda4674ecf5870a1eae
=======
>>>>>>> 6ba442970c543bbaced91fda4674ecf5870a1eae
			}
		if (erreurMsg.length() == 0) {
			return true;
		} else {
			// Affiche un message d'erreur
			alerteErreur("Entrée incorrecte", "Corrigez les erreurs suivantes pour pouvoir modifier les informations",
					erreurMsg);

			return false;
		}
	}
	


}
