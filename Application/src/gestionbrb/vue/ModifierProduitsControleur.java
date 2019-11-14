package gestionbrb.vue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
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
	@FXML
	private ScrollPane boxIngredient; 
	private List<RadioButton> ingredientListe = new ArrayList<>();
	@FXML
	private VBox vb; 
	private Produit produit;
	private Stage dialogStage;
	private boolean okClicked = false;
	IngredientsProduits mainApp;

	@FXML
	private void initialize() {
		try {
		Connection conn = bddUtil.dbConnect();
		ResultSet rs = conn.createStatement().executeQuery("select idType, nom from type_produit");
		ResultSet ig = conn.createStatement().executeQuery("select nomIngredient from ingredients");
		while (ig.next()) {
			RadioButton ingr = new RadioButton(ig.getString("nomIngredient"));
			ingredientListe.add(ingr);
		}
		while (rs.next()) {
			listeType.add("ID: "+rs.getInt("idType")+" -> "+rs.getString("nom"));
		}
		chChoixType.setItems(listeType);
		vb.getChildren().addAll(ingredientListe);
		boxIngredient.setContent(vb);
	} catch (Exception e) {
		alerteErreur("Erreur!", "Erreur d'�xecution", "D�tails: "+e);
	}
}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public void setMainApp(IngredientsProduits mainApp) {
		this.mainApp = mainApp;
	}
	
	public void ajoutType() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("AjoutType.fxml"));
			Parent vueCalendrier = (Parent) loader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(vueCalendrier));
			stage.show();
			
			CalendrierControleur controller = loader.getController();
            /*controller.setMainApp(this);
            controller.afficherTout();*/
		} catch (Exception e) {
			alerteErreur("Erreur", "Impossible d'ouvrir cette fenetre", "D�tails: "+e);

		}
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
			alerteErreur("Erreur!", "Erreur d'�xecution", "D�tails: "+e);
		}
	}
	@FXML
	private void actionAnnuler() {
		dialogStage.close();
	}
	
	public boolean estValide() {
		String erreurMsg = "";

		if (chQuantiteProduit.getText() == null || chQuantiteProduit.getText().length() == 0) {
			erreurMsg += "Veuillez remplir la quantit� du produit\n";
		} else {
			try {
				Integer.parseInt(chQuantiteProduit.getText());
			} catch (NumberFormatException e) {
				erreurMsg += "Erreur! Le champ Quantit� n'accepte que les nombres\n";
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
			erreurMsg += "Veuillez d�crire le produit\n";
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
