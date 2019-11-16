	package gestionbrb.vue;

	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import gestionbrb.IngredientsProduits;
	import gestionbrb.controleur.FonctionsControleurs;
	import gestionbrb.model.Ingredients;
import gestionbrb.model.Produit;
import gestionbrb.model.Type;
import gestionbrb.util.bddUtil;
	import javafx.fxml.FXML;
	import javafx.scene.control.Label;
	import javafx.scene.control.TableColumn;
	import javafx.scene.control.TableView;

	public class IngredientsProduitsControleur extends FonctionsControleurs{
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
		private Label chNomInredients;
		@FXML
		private Label chPrixIngredient;
		@FXML
		private Label chQuantiteIngredient;
		@FXML
		private Label chNomProduit;
		@FXML
		private Label chPrixProduit;
		@FXML
		private Label chQuantiteProduit;
		// Reference to the main application.
		private IngredientsProduits mainApp;

		/**
		 * The constructor. The constructor is called before the initialize() method.
		 */
		public IngredientsProduitsControleur() {
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
		 * Appelé quand l'utilisateur clique sur le bouton modifier la table. Ouvre une
		 * nouvelle page pour effectuer la modification
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
					PreparedStatement pstmt = conn.prepareStatement
							("INSERT INTO `ingredients` (`idIngredient`, `nomIngredient`, `prixIngredient`, `qteRestante`, `idFournisseur`) VALUES (NULL, ?, ?, ?, ?)");
					pstmt.setString(1, tempIngredient.getNomIngredient());
					pstmt.setInt(2, tempIngredient.getPrixIngredient());
					pstmt.setInt(3, tempIngredient.getQuantiteIngredient());
					pstmt.setInt(4, retrouveID(tempIngredient.getFournisseur()));
					pstmt.execute();
					refresh();
					alerteInfo("Ajout éffectué", null, "Les informations ont été ajoutées avec succès!");
				}
		}
		@FXML
		private void ajoutProduit()  {
	        Produit tempProduit = new Produit();
	        try {
				boolean okClicked = mainApp.fenetreModification(tempProduit);
				if (okClicked) {
						Connection conn = bddUtil.dbConnect();
						PreparedStatement pstmt = conn.prepareStatement
								("INSERT INTO `produit` (`idProduit`, `nom`, `qte`, `description`, `prix`, `idType`) VALUES (NULL, ?, ?, ?, ?, ?)");
						pstmt.setString(1, tempProduit.getNomProduit());
						pstmt.setInt(4, tempProduit.getPrixProduit());
						pstmt.setInt(2, tempProduit.getQuantiteProduit());	
						pstmt.setString(3, tempProduit.getDescriptionProduit());
						pstmt.setInt(5, retrouveID(tempProduit.getType()));
						pstmt.execute();
						refreshProduit();
						alerteInfo("Ajout éffectué", null, "Les informations ont été ajoutées avec succès!");
					}
			} catch (Exception e) {
				alerteErreur("Erreur!", "Erreur d'éxecution", "Détails: "+e);
			}
		}
		
		@FXML
		private void ajoutType() throws ClassNotFoundException, SQLException {
	        Type tempType = new Type();
	        try {
	        boolean okClicked = mainApp.fenetreModification(tempType);
	        if (okClicked) {
					Connection conn = bddUtil.dbConnect();
					PreparedStatement pstmt = conn.prepareStatement
							("INSERT INTO `type_produit` (`idType`, `nom`) VALUES (NULL, ?)");
					pstmt.setString(1, tempType.getNomType());
					pstmt.execute();
					refreshType();
					alerteInfo("Ajout éffectué", null, "Les informations ont été ajoutées avec succès!");
				}
	        }
	        catch(Exception e) {
	        	alerteErreur("Erreur!", "Erreur d'éxecution", "Détails: "+e);
	        }
		}
		/** 
		 * Rafraichit les colonnes après un ajout, une modification ou une suppression d'éléments.
		 * @throws ClassNotFoundException
		 * @throws SQLException
		 */
		private void refresh() throws ClassNotFoundException, SQLException {
			IngredientsProduits.getIngredientsData().clear();
			Connection conn = bddUtil.dbConnect();
			ResultSet rs = conn.createStatement().executeQuery("select idIngredient, nomIngredient, prixIngredient, qteRestante, ingredients.idfournisseur, fournisseur.nom from ingredients INNER JOIN fournisseur on ingredients.idfournisseur = fournisseur.idFournisseur ");
			while(rs.next()) {
				IngredientsProduits.getIngredientsData().add(new Ingredients(rs.getInt("idIngredient"), rs.getString("nomIngredient"), rs.getInt("prixIngredient"), rs.getInt("qteRestante"), rs.getString("nom")));
				tableIngredients.setItems(IngredientsProduits.getIngredientsData());
			}
			
		}
		private void refreshProduit() throws ClassNotFoundException, SQLException {
			IngredientsProduits.getProduitData().clear();
			Connection conn = bddUtil.dbConnect();
			ResultSet rs = conn.createStatement().executeQuery("SELECT `idProduit`, produit.`nom`, `qte`, `description`, `prix`, produit.idType, type_produit.nom FROM `produit` INNER JOIN type_produit on produit.idType = type_produit.idType ");
			while(rs.next()) {
				IngredientsProduits.getProduitData().add(new Produit(rs.getInt("idProduit"), rs.getString("nom"),rs.getInt("qte"),rs.getString("description"),rs.getInt("prix"), rs.getString("type_produit.nom")));
				tableProduit.setItems(IngredientsProduits.getProduitData());
			}
			
		}
		private void refreshType() throws ClassNotFoundException, SQLException {
			IngredientsProduits.getTypeData().clear();
			Connection conn = bddUtil.dbConnect();
			ResultSet rs = conn.createStatement().executeQuery("select idType, nom from type_produit ");
			while(rs.next()) {
				IngredientsProduits.getTypeData().add(new Type(rs.getInt("idType"), rs.getString("nom")));
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
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM `ingredients` WHERE idIngredient=?");
				pstmt.setInt(1, selectedIngredient.getIdIngredient());
				pstmt.execute();
				refresh();
				alerteInfo("Suppression réussie", null, "La table "+selectedIngredient.getNomIngredient()+" vient d'être supprimée!");
				conn.close();
				pstmt.close();
				}
				catch(Exception e) {
					alerteErreur("Erreur!", "Erreur d'éxecution", "Détails: "+e+"\nSi l'ingrédient est sélectionné dans un ou plusieurs produits, il sera alors impossible de le supprimer");
				}
				
			

			} else {
				// Si rien n'est séléctionné
				alerteAttention("Aucune sélection", "Aucune table de sélectionnée!",
						"Selectionnez une table pour pouvoir la supprimer");
			}
		}
		@FXML
		private void supprimerProduit() throws ClassNotFoundException {
			Produit selectedProduit = tableProduit.getSelectionModel().getSelectedItem();
			int selectedIndex = tableProduit.getSelectionModel().getSelectedIndex();
			if (selectedIndex >= 0) {
				try {
				Connection conn = bddUtil.dbConnect();
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM `Produit` WHERE idProduit=?");
				pstmt.setInt(1, (selectedProduit.getIdProduit()));
				pstmt.execute();
				refreshProduit();
				alerteInfo("Suppression réussie", null, "La table "+selectedProduit.getNomProduit()+" vient d'être supprimée!");
				conn.close();
				pstmt.close();
				}
				catch(Exception e) {
					alerteErreur("Erreur!", "Erreur d'éxecution", "Détails: "+e);
				}
				
			

			} else {
				// Si rien n'est séléctionné
				alerteAttention("Aucune sélection", "Aucune table de sélectionnée!",
						"Selectionnez une table pour pouvoir la supprimer");
			}
		}
		@FXML
		private void supprimerType() throws ClassNotFoundException {
			Type selectedType = tableType.getSelectionModel().getSelectedItem();
			int selectedIndex = tableType.getSelectionModel().getSelectedIndex();
			if (selectedIndex >= 0) {
				try {
				Connection conn = bddUtil.dbConnect();
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM `type_produit` WHERE idType=?");
				pstmt.setInt(1, selectedType.getIdType());
				pstmt.execute();
				refreshType();
				alerteInfo("Suppression réussie", null, "La table "+selectedType.getNomType()+" vient d'être supprimée!");
				conn.close();
				pstmt.close();
				}
				catch(Exception e) {
					alerteErreur("Erreur!", "Erreur d'éxecution", "Détails: "+e+"\nSi le type est sélectionné dans un ou plusieurs produits, il sera alors impossible de le supprimer");

				}
				
			

			} else {
				// Si rien n'est séléctionné
				alerteAttention("Aucune sélection", "Aucune table de sélectionnée!",
						"Selectionnez une table pour pouvoir la supprimer");
			}
		}
		/**
		 * Appelé quand l'utilisateur clique sur le bouton modifier la table. Ouvre une
		 * nouvelle page pour effectuer la modification
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
					bddUtil.dbQueryExecute("UPDATE `ingredients` SET `nomIngredient` = '" + selectedIngredient.getNomIngredient() + "', "
																+ "`prixIngredient` = " +selectedIngredient.getPrixIngredient()+ ", "
																+ "`qteRestante` = " +selectedIngredient.getQuantiteIngredient()+ ", "
																+ "`idfournisseur` = "+retrouveID(selectedIngredient.getFournisseur())
																+ " WHERE idIngredient= "+selectedIngredient.getIdIngredient()+";");
					refresh();
					alerteInfo("Modification éffectuée", null, "Les informations ont été modifiées avec succès!");
				}

			} else {
				// Si rien n'est selectionné
				alerteAttention("Aucune sélection", "Aucune réservation de sélectionnée!",
						"Selectionnez une réservation pour pouvoir la modifier");
			}
	}
		@FXML
		private void modifierProduit() {
			try {
				Produit selectedProduit = tableProduit.getSelectionModel().getSelectedItem();
				if (selectedProduit != null) {
					boolean okClicked = mainApp.fenetreModification(selectedProduit);
					if (okClicked) {
						bddUtil.dbQueryExecute("UPDATE `Produit` SET `nom` = '" + selectedProduit.getNomProduit() + "', "
																	+ "`qte` = " +selectedProduit.getQuantiteProduit()+  ", "
																	+ "`description` = '"+selectedProduit.getDescriptionProduit()+"', "
																	+ "`prix` = " +selectedProduit.getPrixProduit()+", "
																	+ "`idType` = " +retrouveID(selectedProduit.getType())
																	+" WHERE idProduit= "+selectedProduit.getIdProduit()+";");


						refreshProduit();
						alerteInfo("Modification éffectuée", null, "Les informations ont été modifiées avec succès!");
					}

				} else {
					// Si rien n'est selectionné
					alerteAttention("Aucune sélection", "Aucune réservation de sélectionnée!",
							"Selectionnez une réservation pour pouvoir la modifier");
				}
			} catch (Exception e) {
				alerteErreur("Erreur!", "Erreur d'éxecution", "Détails: "+e);
			}
	}
		@FXML
		private void modifierType() throws ClassNotFoundException, SQLException {
			Type selectedType = tableType.getSelectionModel().getSelectedItem();
			if (selectedType != null) {
				boolean okClicked = mainApp.fenetreModification(selectedType);
				if (okClicked) {
					bddUtil.dbQueryExecute("UPDATE `type_produit` SET `nom` = '" + selectedType.getNomType()+ "' WHERE idType= "+selectedType.getIdType()+";");
					refreshType();
					alerteInfo("Modification éffectuée", null, "Les informations ont été modifiées avec succès!");
				}

			} else {
				// Si rien n'est selectionné
				alerteAttention("Aucune sélection", "Aucune réservation de sélectionnée!",
						"Selectionnez une réservation pour pouvoir la modifier");
			}
	}
}
