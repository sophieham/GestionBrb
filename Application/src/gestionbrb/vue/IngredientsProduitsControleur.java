	package gestionbrb.vue;

	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import gestionbrb.IngredientsProduits;
	import gestionbrb.controleur.FonctionsControleurs;
	import gestionbrb.model.Ingredients;
<<<<<<< HEAD:Application/src/gestionbrb/vue/IngredientsControleur.java
	import gestionbrb.util.bddUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
=======
import gestionbrb.model.Produit;
import gestionbrb.util.bddUtil;
	import javafx.fxml.FXML;
	import javafx.scene.control.Label;
>>>>>>> 6ba442970c543bbaced91fda4674ecf5870a1eae:Application/src/gestionbrb/vue/IngredientsProduitsControleur.java
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
		}

		/**
		 * @param mainApp
		 */
		public void setMainApp(IngredientsProduits mainApp) {
			this.mainApp = mainApp;

			tableIngredients.setItems(IngredientsProduits.getIngredientsData());
			tableProduit.setItems(IngredientsProduits.getProduitData());
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
					pstmt.setString(4, tempIngredient.getFournisseur());
					pstmt.execute();
					refresh();
					alerteInfo("Ajout éffectué", null, "Les informations ont été ajoutées avec succès!");
				}
		}
		@FXML
		private void ajoutProduit() throws ClassNotFoundException, SQLException {
	        Produit tempProduit = new Produit();
	        boolean okClicked = mainApp.fenetreModificationn(tempProduit);
	        if (okClicked) {
					Connection conn = bddUtil.dbConnect();
					PreparedStatement pstmt = conn.prepareStatement
							("INSERT INTO `produit` (`idProduit`, `nom`, `qte`, `description`, `prix`, `idType`) VALUES (NULL, ?, ?, ?, ?, ?)");
					pstmt.setString(1, tempProduit.getNomProduit());
					pstmt.setInt(4, tempProduit.getPrixProduit());
					pstmt.setInt(2, tempProduit.getQuantiteProduit());	
					pstmt.setString(3, tempProduit.getDescriptionProduit());
					pstmt.setString(5, tempProduit.getType());
					pstmt.execute();
					refreshProduit();
					alerteInfo("Ajout éffectué", null, "Les informations ont été ajoutées avec succès!");
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
			ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM ingredients");
			while(rs.next()) {
				IngredientsProduits.getIngredientsData().add(new Ingredients (rs.getInt(1), rs.getString(2),rs.getInt(3),rs.getInt(4), rs.getString(5)));
				tableIngredients.setItems(IngredientsProduits.getIngredientsData());
			}
			
		}
		private void refreshProduit() throws ClassNotFoundException, SQLException {
			IngredientsProduits.getProduitData().clear();
			Connection conn = bddUtil.dbConnect();
			ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Produit");
			while(rs.next()) {
				IngredientsProduits.getProduitData().add(new Produit (rs.getInt(1), rs.getString(2),rs.getInt(3),rs.getString(4),rs.getInt(5),rs.getString(6)));
				tableProduit.setItems(IngredientsProduits.getProduitData());
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
				catch(SQLException e) {
					System.out.println("Erreur dans le code sql"+e);
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
				catch(SQLException e) {
					System.out.println("Erreur dans le code sql"+e);
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
<<<<<<< HEAD:Application/src/gestionbrb/vue/IngredientsControleur.java
					bddUtil.dbQueryExecute("UPDATE `ingredients` SET `nomIngredient` = '" + selectedIngredient.getNomIngredient() + "', "
																+ "`prixIngredient` = " +selectedIngredient.getPrixIngredient()+ ", "
																+ "`qteRestante` = " +selectedIngredient.getQuantiteIngredient()+", "
																+ "`idfournisseur` = "+selectedIngredient.getFournisseur()+";");
=======
					bddUtil.dbQueryExecute("UPDATE `ingredients` SET `nomIngredient` = '" + selectedIngredient.getNomIngredient() + "', `prixIngredient` = " +selectedIngredient.getPrixIngredient()+ ", `qteRestante` = " +selectedIngredient.getQuantiteIngredient()+ ", `idfournisseur` = "+selectedIngredient.getFournisseur()+";");
>>>>>>> 6ba442970c543bbaced91fda4674ecf5870a1eae:Application/src/gestionbrb/vue/IngredientsProduitsControleur.java

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
		private void modifierProduit() throws ClassNotFoundException, SQLException {
			Produit selectedProduit = tableProduit.getSelectionModel().getSelectedItem();
			if (selectedProduit != null) {
				boolean okClicked = mainApp.fenetreModificationn(selectedProduit);
				if (okClicked) {
					bddUtil.dbQueryExecute("UPDATE `Produit` SET `nom` = '" + selectedProduit.getNomProduit() + ", `qte` = " +selectedProduit.getQuantiteProduit()+  ", `description` = " +selectedProduit.getDescriptionProduit()+"', `prixIngredient` = " +selectedProduit.getPrixProduit()+"', `idType` = " +selectedProduit.getType()+";");

					refreshProduit();
					alerteInfo("Modification éffectuée", null, "Les informations ont été modifiées avec succès!");
				}

			} else {
				// Si rien n'est selectionné
				alerteAttention("Aucune sélection", "Aucune réservation de sélectionnée!",
						"Selectionnez une réservation pour pouvoir la modifier");
			}
	}
}
