package gestionbrb.controleur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import gestionbrb.IngredientsProduits;
import gestionbrb.model.Ingredients;
import gestionbrb.model.Produit;
import gestionbrb.model.Type;
import gestionbrb.util.bddUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * 
 * @author Leo
 *
 */

public class IngredientsProduitsControleur {
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

	private IngredientsProduits mainApp;
	private AdministrationControleur parent;

	public IngredientsProduitsControleur() {
	}

	/**
 * Initialise la classe controleur avec les données par défaut du tableau
 * 
 * @throws SQLException
 * @throws ClassNotFoundException
 */

	Maj du Scenebuiler (rajouter un bouton dans un tableview tu regardes sur internet)
	Tu rajoutes la colonne que t'as crée dans le initialize aussi
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
}


	/**
	 * @param mainApp
	 */
	public void setMainApp(IngredientsProduits mainApp) {
		this.mainApp = mainApp;

		tableIngredients.setItems(IngredientsProduits.getIngredientsData());
		tableProduit.setItems(IngredientsProduits.getProduitData());
		tableType.setItems(IngredientsProduits.getTypeData());
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
		boolean okClicked = mainApp.fenetreModification(tempIngredient);
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

	tu ajoute ingredients dans la base de donnée
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
			boolean okClicked = mainApp.fenetreModification(tempProduit);
			if (okClicked) {
				Connection conn = bddUtil.dbConnect();
				PreparedStatement ajoutDB = conn.prepareStatement(
						"INSERT INTO `produit` (`idProduit`, `nom`, `qte`, `description`, `prix`, `idType`) VALUES (NULL, ?, ?, ?, ?, ?)");
				ajoutDB.setString(1, tempProduit.getNomProduit());
				ajoutDB.setFloat(4, tempProduit.getPrixProduit());
				ajoutDB.setInt(2, tempProduit.getQuantiteProduit());
				ajoutDB.setString(3, tempProduit.getDescriptionProduit());
				ajoutDB.setInt(5, FonctionsControleurs.retrouveID(tempProduit.getType()));
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
			boolean okClicked = mainApp.fenetreModification(tempType);
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
		IngredientsProduits.getIngredientsData().clear();
		Connection conn = bddUtil.dbConnect();
		ResultSet rs = conn.createStatement().executeQuery(
				"SELECT idIngredient, nomIngredient, prixIngredient, qteRestante, ingredients.idfournisseur, fournisseur.nom from ingredients INNER JOIN fournisseur on ingredients.idfournisseur = fournisseur.idFournisseur ");
		while (rs.next()) {
			IngredientsProduits.getIngredientsData()
					.add(new Ingredients(rs.getInt("idIngredient"), rs.getString("nomIngredient"),
							rs.getInt("prixIngredient"), rs.getInt("qteRestante"), rs.getString("nom")));

			tableIngredients.setItems(IngredientsProduits.getIngredientsData());
		}
	}
	
private void refreshProduit() throws ClassNotFoundException, SQLException {
	IngredientsProduits.getProduitData().clear();
	Connection conn = bddUtil.dbConnect();
	ResultSet requete = conn.createStatement().executeQuery("SELECT `idProduit`, produit.`nom`, `qte`, `description`, `prix`, produit.idType, type_produit.nom FROM `produit` INNER JOIN type_produit on produit.idType = type_produit.idType ");
	while(requete.next()) { // j'ai modifié Produit.java, tu modifies ça en conséquence
		IngredientsProduits.getProduitData().add(new Produit(requete.getInt("idProduit"), 
															 requete.getString("nom"),
															 requete.getInt("qte"),
															 requete.getString("description"),
															 requete.getInt("prix"), 
															 requete.getString("type_produit.nom")));
		tableProduit.setItems(IngredientsProduits.getProduitData());

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
		IngredientsProduits.getTypeData().clear();
		Connection conn = bddUtil.dbConnect();
		ResultSet requete = conn.createStatement().executeQuery("select idType, nom from type_produit ");
		while (requete.next()) {
			IngredientsProduits.getTypeData().add(new Type(requete.getInt("idType"), requete.getString("nom")));
			tableType.setItems(IngredientsProduits.getTypeData());
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
						"La table " + selectedIngredient.getNomIngredient() + " vient d'être supprimée!");
				conn.close();
				requete.close();
			} catch (Exception e) {
				FonctionsControleurs.alerteErreur("Erreur!", "Erreur d'éxecution", "Détails: " + e
						+ "\nSi l'ingrédient est sélectionné dans un ou plusieurs produits, il sera alors impossible de le supprimer");
			}

		} else {
			// Si rien n'est séléctionné
			FonctionsControleurs.alerteAttention("Aucune sélection", "Aucune table de sélectionnée!",
					"Selectionnez une table pour pouvoir la supprimer");
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
						"La table " + selectedProduit.getNomProduit() + " vient d'être supprimée!");
				conn.close();
				requete.close();
			} catch (Exception e) {
				FonctionsControleurs.alerteErreur("Erreur!", "Erreur d'éxecution", "Détails: " + e);
			}

		} else {
			// Si rien n'est séléctionné
			FonctionsControleurs.alerteAttention("Aucune sélection", "Aucune table de sélectionnée!",
					"Selectionnez une table pour pouvoir la supprimer");
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
						"La table " + selectedType.getNomType() + " vient d'être supprimée!");
				conn.close();
				requete.close();
			} catch (Exception e) {
				FonctionsControleurs.alerteErreur("Erreur!", "Erreur d'éxecution", "Détails: " + e
						+ "\nSi le type est sélectionné dans un ou plusieurs produits, il sera alors impossible de le supprimer");
			}
		} else {
			// Si rien n'est séléctionné
			FonctionsControleurs.alerteAttention("Aucune sélection", "Aucune table de sélectionnée!",
					"Selectionnez une table pour pouvoir la supprimer");
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
			boolean okClicked = mainApp.fenetreModification(selectedIngredient);
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
			FonctionsControleurs.alerteAttention("Aucune sélection", "Aucune réservation de sélectionnée!",
					"Selectionnez une réservation pour pouvoir la modifier");
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
				boolean okClicked = mainApp.fenetreModification(selectedProduit);
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
				FonctionsControleurs.alerteAttention("Aucune sélection", "Aucune réservation de sélectionnée!",
						"Selectionnez une réservation pour pouvoir la modifier");
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
			boolean okClicked = mainApp.fenetreModification(selectedType);
			if (okClicked) {
				bddUtil.dbQueryExecute("UPDATE `type_produit` SET `nom` = '" + selectedType.getNomType()
						+ "' WHERE idType= " + selectedType.getIdType() + ";");
				refreshType();
				FonctionsControleurs.alerteInfo("Modification éffectuée", null,
						"Les informations ont été modifiées avec succès!");
			}

		} else {
			// Si rien n'est selectionné
			FonctionsControleurs.alerteAttention("Aucune sélection", "Aucune réservation de sélectionnée!",
					"Selectionnez une réservation pour pouvoir la modifier");
		}
	}

	public void setParent(AdministrationControleur administrationControleur) {
		// TODO Auto-generated method stub
		this.parent = administrationControleur;
		tableIngredients.setItems(IngredientsProduits.getIngredientsData());
		tableProduit.setItems(IngredientsProduits.getProduitData());
	}
}
