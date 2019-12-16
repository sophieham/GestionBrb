package gestionbrb.controleur;

import java.util.Locale;
import java.util.ResourceBundle;

import gestionbrb.Connexion;
import gestionbrb.DAO.DAOIngredients;
import gestionbrb.DAO.DAOProduit;
import gestionbrb.DAO.DAOType;
import gestionbrb.DAO.DAOUtilisateur;
import gestionbrb.model.Ingredients;
import gestionbrb.model.Produit;
import gestionbrb.model.Type;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * 
 * @author Leo
 *
 */

public class IngredientsProduitsControleur {
	private static ObservableList<Ingredients> listeingredients = FXCollections.observableArrayList();
	private static ObservableList<Produit> listeproduits = FXCollections.observableArrayList();
	private static ObservableList<Type> listeType = FXCollections.observableArrayList();
	@FXML
	private ResourceBundle bundle;
	@FXML
	private TableView<Ingredients> tableIngredients;
	@FXML
	private TableColumn<Ingredients, String> colonneNomIngredient;
	@FXML
	private TableColumn<Ingredients, Number> colonnePrixIngredient;
	@FXML
	private TableColumn<Ingredients, Number> colonneQuantiteIngredient;
	@FXML
	private TableColumn<Ingredients, String> colonneFournisseur;
	@FXML
	private TableColumn<Ingredients, Number> colonneIdIngredient;

	@FXML
	private TableView<Produit> tableProduit;
	@FXML
	private TableColumn<Produit, String> colonneNomProduit;
	@FXML
	private TableColumn<Produit, Number> colonnePrixProduit;
	@FXML
	private TableColumn<Produit, Number> colonneQuantiteProduit;
	@FXML
	private TableColumn<Produit, String> colonneType;
	@FXML
	private TableColumn<Produit, String> colonneDescriptionProduit;
	@FXML
	private TableColumn<Produit, Number> colonneIdProduit;

	@FXML
	private TableView<Type> tableType;
	@FXML
	private TableColumn<Type, Number> colonneIdType;
	@FXML
	private TableColumn<Type, String> colonneNomType;
	@FXML
	private Button ajouter;
	@FXML
	private Button modifier;
	@FXML
	private Button supprimer;
	@FXML
	private Button ajouter1;
	@FXML
	private Button modifier1;
	@FXML
	private Button supprimer1;
	@FXML
	private Button ajouter2;
	@FXML
	private Button modifier2;
	@FXML
	private Button supprimer2;
	@FXML
	private Button ingredients;
	@SuppressWarnings("unused")
	private AdministrationControleur parent;
	DAOUtilisateur daoUtilisateur = new DAOUtilisateur();
	DAOProduit daoProduit = new DAOProduit();
	DAOIngredients daoIngredients = new DAOIngredients();
	DAOType daoType = new DAOType();
	
	public IngredientsProduitsControleur() {
	}

	

@FXML
private void initialize(){
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
		colonneNomIngredient.setCellValueFactory(cellData -> cellData.getValue().nomIngredientProperty());
		colonnePrixIngredient.setCellValueFactory(cellData -> cellData.getValue().prixIngredientProperty());
		colonneQuantiteIngredient.setCellValueFactory(cellData -> cellData.getValue().quantiteIngredientProperty());
		colonneFournisseur.setCellValueFactory(cellData -> cellData.getValue().fournisseurProperty());
		colonneIdIngredient.setCellValueFactory(cellData -> cellData.getValue().idIngredientProperty());

		colonneNomProduit.setCellValueFactory(cellData -> cellData.getValue().nomProduitProperty());
		colonnePrixProduit.setCellValueFactory(cellData -> cellData.getValue().prixProduitProperty());
		colonneQuantiteProduit.setCellValueFactory(cellData -> cellData.getValue().quantiteProduitProperty());
		colonneType.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
		colonneIdProduit.setCellValueFactory(cellData -> cellData.getValue().idProduitProperty());
		colonneDescriptionProduit.setCellValueFactory(cellData -> cellData.getValue().descriptionProduitProperty());

		colonneIdType.setCellValueFactory(cellData -> cellData.getValue().idTypeProperty());
		colonneNomType.setCellValueFactory(cellData -> cellData.getValue().nomTypeProperty());
		
		tableProduit.setItems(daoProduit.afficher());

		tableIngredients.setItems(daoIngredients.afficher());

		tableType.setItems(daoType.afficher());
		
	} catch (Exception e) {
		FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "D閠ails: " + e);
		e.printStackTrace();
	}
}

private void loadLang(String lang, String LANG) {
	Locale locale = new Locale(lang, LANG);  
	
	ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_"+lang, locale);
	colonneNomIngredient.setText(bundle.getString("Nom"));
	colonnePrixIngredient.setText(bundle.getString("Prix"));
	colonneFournisseur.setText(bundle.getString("Fournisseur"));
	colonneQuantiteIngredient.setText(bundle.getString("qte"));
	ajouter.setText(bundle.getString("Ajouter"));
	modifier.setText(bundle.getString("Modifier"));
	supprimer.setText(bundle.getString("Supprimer"));
	colonneNomProduit.setText(bundle.getString("Nom"));
	colonnePrixProduit.setText(bundle.getString("Prix"));
	colonneType.setText(bundle.getString("Type"));
	colonneQuantiteProduit.setText(bundle.getString("qte"));
	colonneDescriptionProduit.setText(bundle.getString("Description"));
	ajouter1.setText(bundle.getString("Ajouter"));
	modifier1.setText(bundle.getString("Modifier"));
	supprimer1.setText(bundle.getString("Supprimer"));
	ingredients.setText(bundle.getString("ingredients"));
	colonneNomType.setText(bundle.getString("Nom"));
	ajouter2.setText(bundle.getString("Ajouter"));
	modifier2.setText(bundle.getString("Modifier"));
	supprimer2.setText(bundle.getString("Supprimer"));
}

/**
 * Appelé quand l'utilisateur clique sur le bouton ajouter. Ouvre une nouvelle
 * page pour effectuer la modification
 * 
 */
@FXML
private void ajoutIngredient(){
	try {
		Ingredients tempIngredient = new Ingredients();
		boolean okClicked = fenetreModification(tempIngredient);
		if (okClicked) {
			daoIngredients.ajouter(tempIngredient);
			refreshIngredient();
			FonctionsControleurs.alerteInfo("Ajout éffectué", null, "Les informations ont été ajoutées avec succès!");
		}
	} catch (Exception e) {
		FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "Détails: " + e);
		e.printStackTrace();
	}
}

/**
 * Affiche les ingrédients du produit séléctionné dans une boite de dialogue.
 * 
 * @see Produit
 * @see Ingredients
 */
@FXML
private void afficheIngredients() {
	Produit selectedProduit = tableProduit.getSelectionModel().getSelectedItem();
	if (selectedProduit != null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Ingredients");
			alert.setHeaderText("Ingrédients de "+selectedProduit.getNomProduit());
			alert.setContentText(selectedProduit.getIngredients());
			alert.showAndWait();
		
		refreshIngredient();
	}
		else {
			FonctionsControleurs.alerteAttention("Aucune sélection", "Aucun produit de sélectionné!",
					"Selectionnez un produit pour pouvoir la modifier");
		}
	}

/**
 * Crée un nouveau produit à la base de donnée. <br>
 * Appellé quand l'utilisateur clique sur le bouton ajouter, 
 * il ouvre une nouvelle page pour effectuer l'ajout. <br>
 * Affiche une boite de dialogue pour confirmer ou pas l'ajout du produit.
 * 
 * @see Produit
 */
@FXML
private void ajoutProduit() {
	Produit tempProduit = new Produit();
	try {
		boolean okClicked = fenetreModification(tempProduit);
		if (okClicked) {
			FonctionsControleurs.alerteInfo("Ajout éffectué", null, "Les informations ont été ajoutées avec succès!");
			daoProduit.ajouter(tempProduit);
			refreshProduit();
		}
	} catch (Exception e) {
		FonctionsControleurs.alerteErreur("Erreur!", "Erreur d'éxecution", "Détails: " + e);
	}
}

/**
 * Ajoute un nouveau type à la base de donnée. <br>
 * Appellé quand l'utilisateur clique sur le bouton ajouter, 
 * il ouvre une nouvelle page pour effectuer l'ajout. <br>
 * Affiche une boite de dialogue pour confirmer ou pas l'ajout du type.
 * 
 * @see Type
 */
@FXML
private void ajoutType() {
	Type tempType = new Type();
	try {
		boolean okClicked = fenetreModification(tempType);
		if (okClicked) {
			daoType.ajouter(tempType);
			refreshType();
			FonctionsControleurs.alerteInfo("Ajout éffectué", null,
					"Les informations ont été ajoutées avec succès!");
		}
	} catch (Exception e) {
		FonctionsControleurs.alerteErreur("Erreur!", "Erreur d'éxecution", "Détails: " + e);
	}
}

/**
 * Rafraichit les colonnes après un ajout, une modification ou une suppression
 * d'éléments à partir de la base de donnée.
 * 
 * @see Ingredient
 */
private void refreshIngredient() {
	try {
		getIngredientsData().clear();
		DAOIngredients DAOIngredients = new DAOIngredients();
		tableIngredients.setItems(DAOIngredients.afficher());
	} catch (Exception e) {
		FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "Détails: " + e);
		e.printStackTrace();
	}
}

/**
 * Rafraichit les colonnes après un ajout, une modification ou une suppression
 * d'éléments à partir de la base de donnée.
 * 
 * @see Produit
 */
private void refreshProduit() {
	try {
		getProduitData().clear();
		DAOProduit DAOProduit = new DAOProduit();
		tableProduit.setItems(DAOProduit.afficher());
	} catch (Exception e) {
		FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "Détails: " + e);
		e.printStackTrace();
	}
}

/**
 * Rafraichit les colonnes après un ajout, une modification ou une suppression
 * d'éléments à partir de la base de donnée.
 * 
 * @see Type
 */
private void refreshType() {
	try {
		getTypeData().clear();
		DAOType DAOType = new DAOType();
		tableType.setItems(DAOType.afficher());
	} catch (Exception e) {
		FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "Détails: " + e);
		e.printStackTrace();
	}
}

/**
 * Appellé quand l'utilisateur clique sur le bouton supprimer, <br>
 * il supprime l'ingrédient séléctionné si c'est possible et affiche une boite de dialogue 
 * confirmant ou non la suppression de l'élément.
 * 
 */
@FXML
private void supprimerIngredient() {
	Ingredients selectedIngredient = tableIngredients.getSelectionModel().getSelectedItem();
	int selectedIndex = tableIngredients.getSelectionModel().getSelectedIndex();
	if (selectedIndex >= 0) {
		try {
			daoIngredients.supprimer(selectedIngredient);
			refreshIngredient();
			FonctionsControleurs.alerteInfo("Suppression réussie", null,
					"L'ingrédient " + selectedIngredient.getNomIngredient() + " vient d'être supprimé!");

		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur!", "Erreur d'éxecution", "Détails: " + e
					+ "\nSi l'ingrédient est sélectionné dans un ou plusieurs produits, il sera alors impossible de le supprimer");
		}

	} else {
		// Si rien n'est séléctionné
		FonctionsControleurs.alerteAttention("Aucune sélection", "Aucun ingrédient de sélectionné!",
				"Selectionnez un ingrédient pour pouvoir le supprimer");
	}
}

/**
 * Appellé quand l'utilisateur clique sur le bouton supprimer, <br>
 * il supprime le produit séléctionné si c'est possible et affiche une boite de dialogue 
 * confirmant ou non la suppression de l'élément.
 * 
 * @see Produit
 */
@FXML
private void supprimerProduit() {
	Produit selectedProduit = tableProduit.getSelectionModel().getSelectedItem();
	int selectedIndex = tableProduit.getSelectionModel().getSelectedIndex();
	if (selectedIndex >= 0) {
		try {
			daoProduit.supprimer(selectedProduit);
			refreshProduit();
			FonctionsControleurs.alerteInfo("Suppression réussie", null,
					"Le produit " + selectedProduit.getNomProduit() + " vient d'être supprimé!");
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur!", "Erreur d'éxecution", "Détails: " + e);
		}

	} else {
		// Si rien n'est séléctionné
		FonctionsControleurs.alerteAttention("Aucune sélection", "Aucun produit de sélectionné!",
				"Selectionnez un produit pour pouvoir le supprimer");
	}
}

/**
 * Appellé quand l'utilisateur clique sur le bouton supprimer, <br>
 * il supprime le type séléctionné si c'est possible et affiche une boite de dialogue 
 * confirmant ou non la suppression de l'élément.
 * 
 * @see Type
 */
@FXML
private void supprimerType() {
	Type selectedType = tableType.getSelectionModel().getSelectedItem();
	int selectedIndex = tableType.getSelectionModel().getSelectedIndex();
	if (selectedIndex >= 0) {
		try {
			daoType.supprimer(selectedType);
			refreshType();
			FonctionsControleurs.alerteInfo("Suppression réussie", null,
					"Le type " + selectedType.getNomType() + " vient d'être supprimée!");
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur!", "Erreur d'éxecution", "Détails: " + e
					+ "\nSi le type est sélectionné dans un ou plusieurs produits, il sera alors impossible de le supprimer");
		}
	} else {
		// Si rien n'est séléctionné
		FonctionsControleurs.alerteAttention("Aucune sélection", "Aucun type de sélectionné!",
				"Selectionnez un type pour pouvoir le supprimer");
	}
}

/**
 * Appelé quand l'utilisateur clique sur le bouton modifier l'ingrédient. Ouvre
 * une nouvelle page pour effectuer la modification. <br>
 * Affiche une boite de dialogue confirmant ou non la modification de l'élément
 * 
 * @see Ingredient
 */
@FXML
private void modifierIngredient() {
	Ingredients selectedIngredient = tableIngredients.getSelectionModel().getSelectedItem();
	if (selectedIngredient != null) {
		try {
			boolean okClicked = fenetreModification(selectedIngredient);
			if (okClicked) {
				daoIngredients.modifier(selectedIngredient);
				refreshIngredient();
				FonctionsControleurs.alerteInfo("Modification éffectuée", null,
						"Les informations ont été modifiées avec succès!");
			}
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "Détails: " + e);
			e.printStackTrace();
		}

	} else {
		// Si rien n'est selectionné
		FonctionsControleurs.alerteAttention("Aucune sélection", "Aucun ingrédient de sélectionné!",
				"Selectionnez un ingrédient pour pouvoir la modifier");
	}
}

/**
 * Appelé quand l'utilisateur clique sur le bouton modifier le produit. Ouvre
 * une nouvelle page pour effectuer la modification <br>
 * Affiche une boite de dialogue confirmant ou non la modification de l'élément
 * 
 * @see Produit
 */
@FXML
private void modifierProduit() {
	try {
		Produit selectedProduit = tableProduit.getSelectionModel().getSelectedItem();
		if (selectedProduit != null) {
			boolean okClicked = fenetreModification(selectedProduit);
			if (okClicked) {
				daoProduit.modifier(selectedProduit);
				refreshProduit();
				FonctionsControleurs.alerteInfo("Modification éffectuée", null,
						"Les informations ont été modifiées avec succès!");
			}

		} else {
			// Si rien n'est selectionné
			FonctionsControleurs.alerteAttention("Aucune sélection", "Aucun produit de séléctionné!",
					"Selectionnez un produit pour pouvoir la modifier");
		}
	} catch (Exception e) {
		FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "Détails: " + e);
	}
}

/**
 * Appelé quand l'utilisateur clique sur le bouton modifier le type. Ouvre une
 * nouvelle page pour effectuer la modification <br>
 * Affiche une boite de dialogue confirmant ou non la modification de l'élément
 * 
 * @see Type
 */
@FXML
private void modifierType() {
	try {
		Type selectedType = tableType.getSelectionModel().getSelectedItem();
		if (selectedType != null) {
			boolean okClicked = fenetreModification(selectedType);
			if (okClicked) {
				daoType.modifier(selectedType);
				refreshType();
				FonctionsControleurs.alerteInfo("Modification éffectuée", null,
						"Les informations ont été modifiées avec succès!");
			}

		} else {
			// Si rien n'est selectionné
			FonctionsControleurs.alerteAttention("Aucune sélection", "Aucun type de sélectionné!",
					"Selectionnez un type pour pouvoir la modifier");
		}
	} catch (Exception e) {
		FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue", "Détails: "+e);
		e.printStackTrace();
	}
}

public void setParent(AdministrationControleur administrationControleur) {
	this.parent = administrationControleur;
}

/**
 * Ouvre la fenêtre utilisée dans toutes les opérations d'ajout ou de modifications d'ingrédients.
 * 
 * @param ingredient
 */
public boolean fenetreModification(Ingredients ingredient) {
	try {
		Locale locale = new Locale("fr", "FR");

		ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_fr", locale);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/ModifierIngredients.fxml"), bundle);
		AnchorPane page = (AnchorPane) loader.load();

		Stage dialogStage = new Stage();
		dialogStage.setResizable(false);
		dialogStage.setTitle("Gestion liste ingredients");
		dialogStage.initModality(Modality.WINDOW_MODAL);
		Scene scene = new Scene(page);
		dialogStage.setScene(scene);
		dialogStage.getIcons().add(new Image(
          	      Connexion.class.getResourceAsStream( "ico.png" ))); 

		ModifierIngredientsControleur controller = loader.getController();
		controller.setDialogStage(dialogStage);
		controller.setIngredients(ingredient);

		dialogStage.showAndWait();

		return controller.isOkClicked();
	} catch (Exception e) {
		FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue", "Détails: "+e);
		return false;
	}
} 

/**
 * Ouvre la fenêtre utilisée dans toutes les opérations d'ajout ou de modifications de produits.
 * 
 * @see Produit
 * @param produit
 * @return
 */
public boolean fenetreModification(Produit produit) {
try {
	Locale locale = new Locale("fr", "FR");

	ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_fr", locale);
	FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/ModifierProduits.fxml"), bundle);
	AnchorPane page = (AnchorPane) loader.load();

	Stage dialogStage = new Stage();
	dialogStage.setResizable(false);
	dialogStage.setTitle("Gestion liste ingredients");
	dialogStage.initModality(Modality.WINDOW_MODAL);
	Scene scene = new Scene(page);
	dialogStage.setScene(scene);
	dialogStage.getIcons().add(new Image(
    	      Connexion.class.getResourceAsStream( "ico.png" ))); 

	ModifierProduitsControleur controller = loader.getController();
	controller.setDialogStage(dialogStage);
	controller.setProduit(produit);

	dialogStage.showAndWait();

	return controller.isOkClicked();
} catch (Exception e) {
	FonctionsControleurs.alerteErreur("Erreur", "Erreur d'éxecution", "Détails: "+e);
	return false;
}
} 

/**
 * Ouvre la fenêtre utilisée dans toutes les opérations d'ajout ou de
 * modifications de types.
 * 
 * @param type
 * @return
 */
public boolean fenetreModification(Type type) {
	try {
		Locale locale = new Locale("fr", "FR");

		ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_fr", locale);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/ModifierType.fxml"), bundle);
		AnchorPane page = (AnchorPane) loader.load();

		Stage dialogStage = new Stage();
		dialogStage.setResizable(false);
		dialogStage.setTitle("Gestion liste type");
		dialogStage.initModality(Modality.WINDOW_MODAL);
		Scene scene = new Scene(page);
		dialogStage.setScene(scene);
		dialogStage.getIcons().add(new Image(
          	      Connexion.class.getResourceAsStream( "ico.png" ))); 

		ModifierTypesControleur controller = loader.getController();
		controller.setDialogStage(dialogStage);
		controller.setType(type);

		dialogStage.showAndWait();

		return controller.isOkClicked();
	} catch (Exception e) {
		e.printStackTrace();
		FonctionsControleurs.alerteErreur("Erreur", "Erreur d'éxecution", "Détails: " + e);
		return false;
	}
}

public static ObservableList<Ingredients> getIngredientsData() {
	return listeingredients;
}

public static ObservableList<Produit> getProduitData() {
	return listeproduits;
}
public static ObservableList<Type> getTypeData() {
	return listeType;
}

}