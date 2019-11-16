package gestionbrb.vue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import gestionbrb.IngredientsProduits;
import gestionbrb.controleur.FonctionsControleurs;
import gestionbrb.model.Produit;
import gestionbrb.util.bddUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifierProduitsControleur extends FonctionsControleurs {
	@FXML
	private TextField chNomProduit;
	@FXML
	private TextField chPrixProduit;
	@FXML
	private TextField chQuantiteProduit;
	@FXML
	private TextArea chDescription;
	@FXML
	private ObservableList<String> listeType = FXCollections.observableArrayList();
	@FXML
	private ChoiceBox<String> chChoixType;
	private Produit produit;
	private Stage dialogStage;
	private boolean okClicked = false;
	IngredientsProduits mainApp;
	
	@FXML
	private void initialize() {
		try {
		Connection conn = bddUtil.dbConnect();
		ResultSet rs = conn.createStatement().executeQuery("select idType, nom from type_produit");

		while (rs.next()) {
			listeType.add("ID: "+rs.getInt("idType")+" -> "+rs.getString("nom"));
		}
		chChoixType.setItems(listeType);
	} catch (Exception e) {
		alerteErreur("Erreur!", "Erreur d'éxecution", "Détails: "+e);
	}
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public void setMainApp(IngredientsProduits mainApp) {
		this.mainApp = mainApp;
	}
	
	public void setProduit(Produit produit) throws SQLException, ClassNotFoundException {
		this.produit = produit;
		chNomProduit.setText(produit.getNomProduit());
		chPrixProduit.setText(Integer.toString(produit.getPrixProduit()));
		chQuantiteProduit.setText(Integer.toString(produit.getQuantiteProduit()));
		chDescription.setText(produit.getDescriptionProduit());
		chChoixType.setValue(produit.getType());
	}
	public boolean isOkClicked() {
		return okClicked;
	}
	@FXML
	public void actionValider() {
		
		try {
			if (estValide()) {
				produit.setNomProduit(chNomProduit.getText());
				produit.setPrixProduit(Integer.parseInt(chPrixProduit.getText()));
				produit.setQuantiteProduit(Integer.parseInt(chQuantiteProduit.getText()));
				produit.setDescriptionProduit(chDescription.getText());
				produit.setType(chChoixType.getValue());
				okClicked = true;
				dialogStage.close();
			}
		} catch (Exception e) {
			alerteErreur("Erreur!", "Erreur d'éxecution", "Détails: "+e);
		}
	}
	@FXML
	private void actionAnnuler() {
		dialogStage.close();
	}
	
	public boolean estValide() {
		String erreurMsg = "";

		if (chQuantiteProduit.getText() == null || chQuantiteProduit.getText().length() == 0) {
			erreurMsg += "Veuillez remplir la quantité du produit\n";
		} else {
			try {
				Integer.parseInt(chQuantiteProduit.getText());
			} catch (NumberFormatException e) {
				erreurMsg += "Erreur! Le champ Quantité n'accepte que les nombres\n";
			}
		}
		
		if (chPrixProduit.getText() == null || chPrixProduit.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le prix du produit\n";
		} else {
			try {
				Integer.parseInt(chPrixProduit.getText());
			} catch (NumberFormatException e) {
				erreurMsg += "Erreur! Le champ prix n'accepte que les nombres\n";
			}
		}
		if (chNomProduit.getText() == null || chNomProduit.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le nom du produit\n";
			}
		
		if (chChoixType.getValue() == null) {
			erreurMsg += "Veuillez saisir le type du produit\n";
			}
		
		if (chDescription.getText() == null || chDescription.getText().length() == 0) {
			erreurMsg += "Veuillez décrire le produit\n";
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
