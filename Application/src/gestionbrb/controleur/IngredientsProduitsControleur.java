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

// TODO: Auto-generated Javadoc
/**
 * The Class IngredientsProduitsControleur.
 *
 * @author Leo
 */

public class IngredientsProduitsControleur {
	
	/** The listeingredients. */
	private static ObservableList<Ingredients> listeingredients = FXCollections.observableArrayList();
	
	/** The listeproduits. */
	private static ObservableList<Produit> listeproduits = FXCollections.observableArrayList();
	
	/** The liste type. */
	private static ObservableList<Type> listeType = FXCollections.observableArrayList();
	
	/** The bundle. */
	@FXML
	private ResourceBundle bundle;
	
	/** The table ingredients. */
	@FXML
	private TableView<Ingredients> tableIngredients;
	
	/** The colonne nom ingredient. */
	@FXML
	private TableColumn<Ingredients, String> colonneNomIngredient;
	
	/** The colonne prix ingredient. */
	@FXML
	private TableColumn<Ingredients, Number> colonnePrixIngredient;
	
	/** The colonne quantite ingredient. */
	@FXML
	private TableColumn<Ingredients, Number> colonneQuantiteIngredient;
	
	/** The colonne fournisseur. */
	@FXML
	private TableColumn<Ingredients, String> colonneFournisseur;
	
	/** The colonne id ingredient. */
	@FXML
	private TableColumn<Ingredients, Number> colonneIdIngredient;

	/** The table produit. */
	@FXML
	private TableView<Produit> tableProduit;
	
	/** The colonne nom produit. */
	@FXML
	private TableColumn<Produit, String> colonneNomProduit;
	
	/** The colonne prix produit. */
	@FXML
	private TableColumn<Produit, Number> colonnePrixProduit;
	
	/** The colonne quantite produit. */
	@FXML
	private TableColumn<Produit, Number> colonneQuantiteProduit;
	
	/** The colonne type. */
	@FXML
	private TableColumn<Produit, String> colonneType;
	
	/** The colonne description produit. */
	@FXML
	private TableColumn<Produit, String> colonneDescriptionProduit;
	
	/** The colonne id produit. */
	@FXML
	private TableColumn<Produit, Number> colonneIdProduit;

	/** The table type. */
	@FXML
	private TableView<Type> tableType;
	
	/** The colonne id type. */
	@FXML
	private TableColumn<Type, Number> colonneIdType;
	
	/** The colonne nom type. */
	@FXML
	private TableColumn<Type, String> colonneNomType;
	
	/** The ajouter. */
	@FXML
	private Button ajouter;
	
	/** The modifier. */
	@FXML
	private Button modifier;
	
	/** The supprimer. */
	@FXML
	private Button supprimer;
	
	/** The ajouter 1. */
	@FXML
	private Button ajouter1;
	
	/** The modifier 1. */
	@FXML
	private Button modifier1;
	
	/** The supprimer 1. */
	@FXML
	private Button supprimer1;
	
	/** The ajouter 2. */
	@FXML
	private Button ajouter2;
	
	/** The modifier 2. */
	@FXML
	private Button modifier2;
	
	/** The supprimer 2. */
	@FXML
	private Button supprimer2;
	
	/** The ingredients. */
	@FXML
	private Button ingredients;
	
	/** The parent. */
	@SuppressWarnings("unused")
	private AdministrationControleur parent;
	
	/** The dao utilisateur. */
	DAOUtilisateur daoUtilisateur = new DAOUtilisateur();
	
	/** The dao produit. */
	DAOProduit daoProduit = new DAOProduit();
	
	/** The dao ingredients. */
	DAOIngredients daoIngredients = new DAOIngredients();
	
	/** The dao type. */
	DAOType daoType = new DAOType();
	
	/**
	 * Instantiates a new ingredients produits controleur.
	 */
	public IngredientsProduitsControleur() {
	}

	/**
	 * Initialise la classe controleur avec les donn�es par d�faut du tableau <br>
	 * Charge les fichiers de langue n�cessaires � la traduction.
	 * <br>
	 * Affiche un message d'erreur si il y a un probl�me.
	 */

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
		FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "D�tails: " + e);
		e.printStackTrace();
	}
}

/**
 * charger la langue.
 *
 * @param lang the lang
 * @param LANG the lang
 */

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
 * Appel� quand l'utilisateur clique sur le bouton ajouter. Ouvre une nouvelle
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
			FonctionsControleurs.alerteInfo("Ajout �ffectu�", null, "Les informations ont �t� ajout�es avec succ�s!");
		}
	} catch (Exception e) {
		FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "D�tails: " + e);
		e.printStackTrace();
	}
}

/**
 * Affiche les ingr�dients du produit s�l�ctionn� dans une boite de dialogue.
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
			alert.setHeaderText("Ingr�dients de "+selectedProduit.getNomProduit());
			alert.setContentText(selectedProduit.getIngredients());
			alert.showAndWait();
		
		refreshIngredient();
	}
		else {
			FonctionsControleurs.alerteAttention("Aucune s�lection", "Aucun produit de s�lectionn�!",
					"Selectionnez un produit pour pouvoir la modifier");
		}
	}

/**
 * Cr�e un nouveau produit � la base de donn�e. <br>
 * Appell� quand l'utilisateur clique sur le bouton ajouter, 
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
			FonctionsControleurs.alerteInfo("Ajout �ffectu�", null, "Les informations ont �t� ajout�es avec succ�s!");
			daoProduit.ajouter(tempProduit);
			refreshProduit();
		}
	} catch (Exception e) {
		FonctionsControleurs.alerteErreur("Erreur!", "Erreur d'�xecution", "D�tails: " + e);
		e.printStackTrace();
	}
}

/**
 * Ajoute un nouveau type � la base de donn�e. <br>
 * Appell� quand l'utilisateur clique sur le bouton ajouter, 
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
			FonctionsControleurs.alerteInfo("Ajout �ffectu�", null,
					"Les informations ont �t� ajout�es avec succ�s!");
		}
	} catch (Exception e) {
		FonctionsControleurs.alerteErreur("Erreur!", "Erreur d'�xecution", "D�tails: " + e);
		e.printStackTrace();
	}
}

/**
 * Rafraichit les colonnes apr�s un ajout, une modification ou une suppression
 * d'�l�ments � partir de la base de donn�e.
 * 
 * @see Ingredient
 */
private void refreshIngredient() {
	try {
		getIngredientsData().clear();
		DAOIngredients DAOIngredients = new DAOIngredients();
		tableIngredients.setItems(DAOIngredients.afficher());
	} catch (Exception e) {
		FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "D�tails: " + e);
		e.printStackTrace();
	}
}

/**
 * Rafraichit les colonnes apr�s un ajout, une modification ou une suppression
 * d'�l�ments � partir de la base de donn�e.
 * 
 * @see Produit
 */
private void refreshProduit() {
	try {
		getProduitData().clear();
		DAOProduit DAOProduit = new DAOProduit();
		tableProduit.setItems(DAOProduit.afficher());
	} catch (Exception e) {
		FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "D�tails: " + e);
		e.printStackTrace();
	}
}

/**
 * Rafraichit les colonnes apr�s un ajout, une modification ou une suppression
 * d'�l�ments � partir de la base de donn�e.
 * 
 * @see Type
 */
private void refreshType() {
	try {
		getTypeData().clear();
		DAOType DAOType = new DAOType();
		tableType.setItems(DAOType.afficher());
	} catch (Exception e) {
		FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "D�tails: " + e);
		e.printStackTrace();
	}
}

/**
 * Appell� quand l'utilisateur clique sur le bouton supprimer, <br>
 * il supprime l'ingr�dient s�l�ctionn� si c'est possible et affiche une boite de dialogue 
 * confirmant ou non la suppression de l'�l�ment.
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
			FonctionsControleurs.alerteInfo("Suppression r�ussie", null,
					"L'ingr�dient " + selectedIngredient.getNomIngredient() + " vient d'�tre supprim�!");

		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur!", "Erreur d'�xecution", "D�tails: " + e
					+ "\nSi l'ingr�dient est s�lectionn� dans un ou plusieurs produits, il sera alors impossible de le supprimer");
		}

	} else {
		// Si rien n'est s�l�ctionn�
		FonctionsControleurs.alerteAttention("Aucune s�lection", "Aucun ingr�dient de s�lectionn�!",
				"Selectionnez un ingr�dient pour pouvoir le supprimer");
	}
}

/**
 * Appell� quand l'utilisateur clique sur le bouton supprimer, <br>
 * il supprime le produit s�l�ctionn� si c'est possible et affiche une boite de dialogue 
 * confirmant ou non la suppression de l'�l�ment.
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
			FonctionsControleurs.alerteInfo("Suppression r�ussie", null,
					"Le produit " + selectedProduit.getNomProduit() + " vient d'�tre supprim�!");
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur!", "Erreur d'�xecution", "D�tails: " + e);
		}

	} else {
		// Si rien n'est s�l�ctionn�
		FonctionsControleurs.alerteAttention("Aucune s�lection", "Aucun produit de s�lectionn�!",
				"Selectionnez un produit pour pouvoir le supprimer");
	}
}

/**
 * Appell� quand l'utilisateur clique sur le bouton supprimer, <br>
 * il supprime le type s�l�ctionn� si c'est possible et affiche une boite de dialogue 
 * confirmant ou non la suppression de l'�l�ment.
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
			FonctionsControleurs.alerteInfo("Suppression r�ussie", null,
					"Le type " + selectedType.getNomType() + " vient d'�tre supprim�e!");
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur!", "Erreur d'�xecution", "D�tails: " + e
					+ "\nSi le type est s�lectionn� dans un ou plusieurs produits, il sera alors impossible de le supprimer");
		}
	} else {
		// Si rien n'est s�l�ctionn�
		FonctionsControleurs.alerteAttention("Aucune s�lection", "Aucun type de s�lectionn�!",
				"Selectionnez un type pour pouvoir le supprimer");
	}
}

/**
 * Appel� quand l'utilisateur clique sur le bouton modifier l'ingr�dient. Ouvre
 * une nouvelle page pour effectuer la modification. <br>
 * Affiche une boite de dialogue confirmant ou non la modification de l'�l�ment
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
				FonctionsControleurs.alerteInfo("Modification �ffectu�e", null,
						"Les informations ont �t� modifi�es avec succ�s!");
			}
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "D�tails: " + e);
			e.printStackTrace();
		}

	} else {
		// Si rien n'est selectionn�
		FonctionsControleurs.alerteAttention("Aucune s�lection", "Aucun ingr�dient de s�lectionn�!",
				"Selectionnez un ingr�dient pour pouvoir la modifier");
	}
}

/**
 * Appel� quand l'utilisateur clique sur le bouton modifier le produit. Ouvre
 * une nouvelle page pour effectuer la modification <br>
 * Affiche une boite de dialogue confirmant ou non la modification de l'�l�ment
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
				FonctionsControleurs.alerteInfo("Modification �ffectu�e", null,
						"Les informations ont �t� modifi�es avec succ�s!");
			}

		} else {
			// Si rien n'est selectionn�
			FonctionsControleurs.alerteAttention("Aucune s�lection", "Aucun produit de s�l�ctionn�!",
					"Selectionnez un produit pour pouvoir la modifier");
		}
	} catch (Exception e) {
		FonctionsControleurs.alerteErreur("Erreur!", "Une erreur est survenue", "D�tails: " + e);
		e.printStackTrace();
	}
}

/**
 * Appel� quand l'utilisateur clique sur le bouton modifier le type. Ouvre une
 * nouvelle page pour effectuer la modification <br>
 * Affiche une boite de dialogue confirmant ou non la modification de l'�l�ment
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
				FonctionsControleurs.alerteInfo("Modification �ffectu�e", null,
						"Les informations ont �t� modifi�es avec succ�s!");
			}

		} else {
			// Si rien n'est selectionn�
			FonctionsControleurs.alerteAttention("Aucune s�lection", "Aucun type de s�lectionn�!",
					"Selectionnez un type pour pouvoir la modifier");
		}
	} catch (Exception e) {
		FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue", "D�tails: "+e);
		e.printStackTrace();
	}
}

/**
 * Sets the parent.
 *
 * @param administrationControleur the new parent
 */
public void setParent(AdministrationControleur administrationControleur) {
	this.parent = administrationControleur;
}

/**
 * Ouvre la fen�tre utilis�e dans toutes les op�rations d'ajout ou de modifications d'ingr�dients.
 *
 * @param ingredient the ingredient
 * @return true, if successful
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
		FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue", "D�tails: "+e);
		e.printStackTrace();
		return false;
	}
} 

/**
 * Ouvre la fen�tre utilis�e dans toutes les op�rations d'ajout ou de modifications de produits.
 *
 * @param produit the produit
 * @return true, if successful
 * @see Produit
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
	FonctionsControleurs.alerteErreur("Erreur", "Erreur d'�xecution", "D�tails: "+e);
	e.printStackTrace();
	return false;
}
} 

/**
 * Ouvre la fen�tre utilis�e dans toutes les op�rations d'ajout ou de
 * modifications de types.
 *
 * @param type the type
 * @return true, if successful
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
		FonctionsControleurs.alerteErreur("Erreur", "Erreur d'�xecution", "D�tails: " + e);
		e.printStackTrace();
		return false;
	}
}

/**
 * Gets the ingredients data.
 *
 * @return the ingredients data
 */
public static ObservableList<Ingredients> getIngredientsData() {
	return listeingredients;
}

/**
 * Gets the produit data.
 *
 * @return the produit data
 */
public static ObservableList<Produit> getProduitData() {
	return listeproduits;
}

/**
 * Gets the type data.
 *
 * @return the type data
 */
public static ObservableList<Type> getTypeData() {
	return listeType;
}

}