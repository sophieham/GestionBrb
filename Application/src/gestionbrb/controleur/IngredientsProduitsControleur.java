package gestionbrb.controleur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import gestionbrb.model.Ingredients;
import gestionbrb.model.Produit;
import gestionbrb.model.Type;
import gestionbrb.util.bddUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
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
	
	@SuppressWarnings("unused")
	private AdministrationControleur parent;

	public IngredientsProduitsControleur() {
		try {
			Connection conn = bddUtil.dbConnect();
			ResultSet rs = conn.createStatement().executeQuery("SELECT idIngredient, nomIngredient, prixIngredient, qteRestante, ingredients.idfournisseur, fournisseur.nom from ingredients INNER JOIN fournisseur on ingredients.idfournisseur = fournisseur.idFournisseur ");
			ResultSet res = conn.createStatement().executeQuery("SELECT `idProduit`, produit.`nom`, `qte`, `description`, `prix`, produit.idType, type_produit.nom, ingredients FROM `produit` INNER JOIN type_produit on produit.idType = type_produit.idType ");
			ResultSet resu = conn.createStatement().executeQuery("SELECT `idType`, `nom` FROM `type_produit`");
			while (rs.next()) {
				listeingredients.add(new Ingredients(rs.getInt("idIngredient"), rs.getString("nomIngredient"), rs.getInt("prixIngredient"), rs.getInt("qteRestante"), rs.getString("nom")));
			}
			rs.close();
			while (res.next()) {
				listeproduits.add(new Produit(res.getInt("idProduit"), res.getString("nom"),res.getInt("qte"),res.getString("description"),res.getInt("prix"), res.getString("type_produit.nom"), res.getString("ingredients")));
			}
			res.close();
			while (resu.next()) {
				listeType.add(new Type(resu.getInt("idType"), resu.getString("nom")));
			}
			resu.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	/**
 * Initialise la classe controleur avec les données par défaut du tableau
 * 
 * @throws SQLException
 * @throws ClassNotFoundException
 */

@FXML
private void initialize() throws ClassNotFoundException, SQLException {

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
	

	tableIngredients.setItems(getIngredientsData());
	tableProduit.setItems(getProduitData());
	tableType.setItems(getTypeData());
}



/**
	 * Appelé quand l'utilisateur clique sur le bouton ajouter. Ouvre une nouvelle
	 * page pour effectuer la modification
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void ajoutIngredient() throws ClassNotFoundException, SQLException {
		Ingredients tempIngredient = new Ingredients();
		boolean okClicked = fenetreModification(tempIngredient);
		if (okClicked) {
			Connection conn = bddUtil.dbConnect();
			PreparedStatement ajoutDB = conn.prepareStatement(
					"INSERT INTO `ingredients` (`idIngredient`, `nomIngredient`, `prixIngredient`, `qteRestante`, `idFournisseur`) VALUES (NULL, ?, ?, ?, ?)");
			ajoutDB.setString(1, tempIngredient.getNomIngredient());
			ajoutDB.setFloat(2, tempIngredient.getPrixIngredient());
			ajoutDB.setInt(3, tempIngredient.getQuantiteIngredient());
			ajoutDB.setInt(4, FonctionsControleurs.retrouveID(tempIngredient.getFournisseur()));
			ajoutDB.execute();
			refresh();
			FonctionsControleurs.alerteInfo("Ajout éffectué", null, "Les informations ont été ajoutées avec succès!");
		}
	}
	
	@FXML
	private void afficheIngredients() throws ClassNotFoundException, SQLException {
		Produit selectedProduit = tableProduit.getSelectionModel().getSelectedItem();
		if (selectedProduit != null) {
			Connection conn = bddUtil.dbConnect();
			ResultSet afficheDB = conn.createStatement().executeQuery(
					"SELECT nom, ingredients FROM `produit` WHERE idProduit = "+ selectedProduit.getIdProduit());
			while (afficheDB.next()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Ingredients");
				alert.setHeaderText("Ingrédients de "+afficheDB.getString("nom"));
				alert.setContentText(afficheDB.getString("ingredients"));
				alert.showAndWait();
			}
			
			refresh();
		}
			else {
				FonctionsControleurs.alerteAttention("Aucune sélection", "Aucun produit de sélectionné!",
						"Selectionnez un produit pour pouvoir la modifier");
			}
		}

	/**
	 * Appelé quand l'utilisateur clique sur le bouton ajouter. Ouvre une nouvelle
	 * page pour effectuer la modification
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void ajoutProduit() {
		Produit tempProduit = new Produit();
		try {
			boolean okClicked = fenetreModification(tempProduit);
			if (okClicked) {
				Connection conn = bddUtil.dbConnect();
				PreparedStatement ajoutDB = conn.prepareStatement(
						"INSERT INTO `produit` (`idProduit`, `nom`, `qte`, `description`, `prix`, `idType`, `ingredients`) VALUES (NULL, ?, ?, ?, ?, ?, ?)");
				ajoutDB.setString(1, tempProduit.getNomProduit());
				ajoutDB.setFloat(4, tempProduit.getPrixProduit());
				ajoutDB.setInt(2, tempProduit.getQuantiteProduit());
				ajoutDB.setString(3, tempProduit.getDescriptionProduit());
				ajoutDB.setInt(5, FonctionsControleurs.retrouveID(tempProduit.getType()));
				ajoutDB.setString(6, tempProduit.getIngredients());
				ajoutDB.execute();
				refreshProduit();
				FonctionsControleurs.alerteInfo("Ajout éffectué", null,
						"Les informations ont été ajoutées avec succès!");
			}
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur!", "Erreur d'éxecution", "Détails: " + e);
		}
	}

	/**
	 * Appelé quand l'utilisateur clique sur le bouton ajouter. Ouvre une nouvelle
	 * page pour effectuer la modification
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void ajoutType() throws ClassNotFoundException, SQLException {
		Type tempType = new Type();
		try {
			boolean okClicked = fenetreModification(tempType);
			if (okClicked) {
				Connection conn = bddUtil.dbConnect();
				PreparedStatement ajoutDB = conn
						.prepareStatement("INSERT INTO `type_produit` (`idType`, `nom`) VALUES (NULL, ?)");
				ajoutDB.setString(1, tempType.getNomType());
				ajoutDB.execute();
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
	 * d'éléments.
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private void refresh() throws ClassNotFoundException, SQLException {
		getIngredientsData().clear();
		Connection conn = bddUtil.dbConnect();
		ResultSet rs = conn.createStatement().executeQuery(
				"SELECT idIngredient, nomIngredient, prixIngredient, qteRestante, ingredients.idfournisseur, fournisseur.nom from ingredients INNER JOIN fournisseur on ingredients.idfournisseur = fournisseur.idFournisseur ");
		while (rs.next()) {
			getIngredientsData()
					.add(new Ingredients(rs.getInt("idIngredient"), rs.getString("nomIngredient"),
							rs.getInt("prixIngredient"), rs.getInt("qteRestante"), rs.getString("nom")));

			tableIngredients.setItems(getIngredientsData());
		}
	}
	
private void refreshProduit() throws ClassNotFoundException, SQLException {
	getProduitData().clear();
	Connection conn = bddUtil.dbConnect();
	ResultSet requete = conn.createStatement().executeQuery("SELECT `idProduit`, produit.`nom`, `qte`, `description`, `prix`, ingredients, produit.idType, type_produit.nom FROM `produit` INNER JOIN type_produit on produit.idType = type_produit.idType ");
	while(requete.next()) { // j'ai modifié Produit.java, tu modifies ça en conséquence
		getProduitData().add(new Produit(requete.getInt("idProduit"), 
															 requete.getString("nom"),
															 requete.getInt("qte"),
															 requete.getString("description"),
															 requete.getInt("prix"), 
															 requete.getString("type_produit.nom"),
															 requete.getString("ingredients")));
		tableProduit.setItems(getProduitData());

		}
	}
	/**
	 * Rafraichit les colonnes après un ajout, une modification ou une suppression
	 * d'éléments.
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	private void refreshType() throws ClassNotFoundException, SQLException {
		getTypeData().clear();
		Connection conn = bddUtil.dbConnect();
		ResultSet requete = conn.createStatement().executeQuery("select idType, nom from type_produit ");
		while (requete.next()) {
			getTypeData().add(new Type(requete.getInt("idType"), requete.getString("nom")));
			tableType.setItems(getTypeData());
		}
	}

	/**
	 * Appellé quand l'utilisateur clique sur le bouton supprimer
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void supprimerIngredient() throws ClassNotFoundException {
		Ingredients selectedIngredient = tableIngredients.getSelectionModel().getSelectedItem();
		int selectedIndex = tableIngredients.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			try {
				Connection conn = bddUtil.dbConnect();
				PreparedStatement requete = conn.prepareStatement("DELETE FROM `ingredients` WHERE idIngredient=?");
				requete.setInt(1, selectedIngredient.getIdIngredient());
				requete.execute();
				refresh();
				FonctionsControleurs.alerteInfo("Suppression réussie", null,
						"L'ingrédient " + selectedIngredient.getNomIngredient() + " vient d'être supprimé!");
				conn.close();
				requete.close();
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
	 * Appellé quand l'utilisateur clique sur le bouton supprimer
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void supprimerProduit() throws ClassNotFoundException {
		Produit selectedProduit = tableProduit.getSelectionModel().getSelectedItem();
		int selectedIndex = tableProduit.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			try {
				Connection conn = bddUtil.dbConnect();
				PreparedStatement requete = conn.prepareStatement("DELETE FROM `Produit` WHERE idProduit=?");
				requete.setInt(1, (selectedProduit.getIdProduit()));
				requete.execute();
				refreshProduit();
				FonctionsControleurs.alerteInfo("Suppression réussie", null,
						"Le produit " + selectedProduit.getNomProduit() + " vient d'être supprimé!");
				conn.close();
				requete.close();
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
	 * Appellé quand l'utilisateur clique sur le bouton supprimer
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void supprimerType() throws ClassNotFoundException {
		Type selectedType = tableType.getSelectionModel().getSelectedItem();
		int selectedIndex = tableType.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			try {
				Connection conn = bddUtil.dbConnect();
				PreparedStatement requete = conn.prepareStatement("DELETE FROM `type_produit` WHERE idType=?");
				requete.setInt(1, selectedType.getIdType());
				requete.execute();
				refreshType();
				FonctionsControleurs.alerteInfo("Suppression réussie", null,
						"Le type " + selectedType.getNomType() + " vient d'être supprimée!");
				conn.close();
				requete.close();
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
	 * une nouvelle page pour effectuer la modification
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void modifierIngredient() throws ClassNotFoundException, SQLException {
		Ingredients selectedIngredient = tableIngredients.getSelectionModel().getSelectedItem();
		if (selectedIngredient != null) {
			boolean okClicked = fenetreModification(selectedIngredient);
			if (okClicked) {
				bddUtil.dbQueryExecute("UPDATE `ingredients` SET `nomIngredient` = '"
						+ selectedIngredient.getNomIngredient() + "', " + "`prixIngredient` = "
						+ selectedIngredient.getPrixIngredient() + ", " + "`qteRestante` = "
						+ selectedIngredient.getQuantiteIngredient() + ", " + "`idfournisseur` = "
						+ FonctionsControleurs.retrouveID(selectedIngredient.getFournisseur()) + " WHERE idIngredient= "
						+ selectedIngredient.getIdIngredient() + ";");
				refresh();
				FonctionsControleurs.alerteInfo("Modification éffectuée", null,
						"Les informations ont été modifiées avec succès!");
			}

		} else {
			// Si rien n'est selectionné
			FonctionsControleurs.alerteAttention("Aucune sélection", "Aucun ingrédient de sélectionné!",
					"Selectionnez un ingrédient pour pouvoir la modifier");
		}
	}

	/**
	 * Appelé quand l'utilisateur clique sur le bouton modifier le produit. Ouvre
	 * une nouvelle page pour effectuer la modification
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void modifierProduit() {
		try {
			Produit selectedProduit = tableProduit.getSelectionModel().getSelectedItem();
			if (selectedProduit != null) {
				boolean okClicked = fenetreModification(selectedProduit);
				if (okClicked) {
					bddUtil.dbQueryExecute("UPDATE `Produit` SET `nom` = '" + selectedProduit.getNomProduit() + "', "
							+ "`qte` = " + selectedProduit.getQuantiteProduit() + ", " + "`description` = '"
							+ selectedProduit.getDescriptionProduit() + "', " + "`prix` = "
							+ selectedProduit.getPrixProduit() + ", " + "`idType` = "
							+ FonctionsControleurs.retrouveID(selectedProduit.getType()) + " WHERE idProduit= "
							+ selectedProduit.getIdProduit() + ";");

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
			FonctionsControleurs.alerteErreur("Erreur!", "Erreur d'éxecution", "Détails: " + e);
		}
	}

	/**
	 * Appelé quand l'utilisateur clique sur le bouton modifier le type. Ouvre une
	 * nouvelle page pour effectuer la modification
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void modifierType() throws ClassNotFoundException, SQLException {
		Type selectedType = tableType.getSelectionModel().getSelectedItem();
		if (selectedType != null) {
			boolean okClicked = fenetreModification(selectedType);
			if (okClicked) {
				bddUtil.dbQueryExecute("UPDATE `type_produit` SET `nom` = '" + selectedType.getNomType()
						+ "' WHERE idType= " + selectedType.getIdType() + ";");
				refreshType();
				FonctionsControleurs.alerteInfo("Modification éffectuée", null,
						"Les informations ont été modifiées avec succès!");
			}

		} else {
			// Si rien n'est selectionné
			FonctionsControleurs.alerteAttention("Aucune sélection", "Aucun type de sélectionné!",
					"Selectionnez un type pour pouvoir la modifier");
		}
	}

	public void setParent(AdministrationControleur administrationControleur) {
		this.parent = administrationControleur;
	}
	
	public boolean fenetreModification(Ingredients ingredient) throws ClassNotFoundException, SQLException {
		try {
			// Charge le fichier fxml et l'ouvre en pop-up
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(IngredientsProduitsControleur.class.getResource("../vue/ModifierIngredients.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Crée une nouvelle page
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Gestion liste ingredients");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Définition du controleur pour la fenetre
			ModifierIngredientsControleur controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setIngredients(ingredient);

			// Affiche la page et attend que l'utilisateur la ferme.
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Erreur d'éxecution", "Détails: "+e);
			return false;
		}
} 
public boolean fenetreModification(Produit produit) throws ClassNotFoundException, SQLException {
	try {
		// Charge le fichier fxml et l'ouvre en pop-up
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(IngredientsProduitsControleur.class.getResource("../vue/ModifierProduits.fxml"));
		AnchorPane page = (AnchorPane) loader.load();

		// Crée une nouvelle page
		Stage dialogStage = new Stage();
		dialogStage.setTitle("Gestion liste ingredients");
		dialogStage.initModality(Modality.WINDOW_MODAL);
		Scene scene = new Scene(page);
		dialogStage.setScene(scene);

		// Définition du controleur pour la fenetre
		ModifierProduitsControleur controller = loader.getController();
		controller.setDialogStage(dialogStage);
		controller.setProduit(produit);

		// Affiche la page et attend que l'utilisateur la ferme.
		dialogStage.showAndWait();

		return controller.isOkClicked();
	} catch (Exception e) {
		FonctionsControleurs.alerteErreur("Erreur", "Erreur d'éxecution", "Détails: "+e);
		return false;
	}
} 
public boolean fenetreModification(Type type) throws ClassNotFoundException, SQLException {
	try {
		// Charge le fichier fxml et l'ouvre en pop-up
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(IngredientsProduitsControleur.class.getResource("../vue/ModifierType.fxml"));
		AnchorPane page = (AnchorPane) loader.load();

		// Crée une nouvelle page
		Stage dialogStage = new Stage();
		dialogStage.setTitle("Gestion liste type");
		dialogStage.initModality(Modality.WINDOW_MODAL);
		Scene scene = new Scene(page);
		dialogStage.setScene(scene);

		// Définition du controleur pour la fenetre
		ModifierTypesControleur controller = loader.getController();
		controller.setDialogStage(dialogStage);
		controller.setType(type);

		// Affiche la page et attend que l'utilisateur la ferme.
		dialogStage.showAndWait();

		return controller.isOkClicked();
	} catch (Exception e) {
		e.printStackTrace();
		FonctionsControleurs.alerteErreur("Erreur", "Erreur d'éxecution", "Détails: "+e);
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
