	package gestionbrb.vue;

	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import gestionbrb.IngredientsProduits;
	import gestionbrb.controleur.FonctionsControleurs;
	import gestionbrb.model.Ingredients;
	import gestionbrb.util.bddUtil;
	import javafx.fxml.FXML;
	import javafx.scene.control.Label;
	import javafx.scene.control.TableColumn;
	import javafx.scene.control.TableView;

	public class IngredientsControleur extends FonctionsControleurs{
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
		private Label chNomInredients;
		@FXML
		private Label chPrixIngredient;
		@FXML
		private Label chQuantiteIngredient;
		// Reference to the main application.
		private IngredientsProduits mainApp;

		/**
		 * The constructor. The constructor is called before the initialize() method.
		 */
		public IngredientsControleur() {
		}

		/**
		 * Initialise la classe controleur avec les donn�es par d�faut du tableau
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
		}

		/**
		 * @param mainApp
		 */
		public void setMainApp(IngredientsProduits mainApp) {
			this.mainApp = mainApp;

			tableIngredients.setItems(IngredientsProduits.getIngredientsData());
		}
		
		/**
		 * Appel� quand l'utilisateur clique sur le bouton modifier la table. Ouvre une
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
							("INSERT INTO `ingredients` (`idIngredient`, `nomIngredient`, `prixIngredient`, `qteRestante`, `idFournisseur`) VALUES (NULL, ?, ?, ?, NULL)");
					pstmt.setString(1, tempIngredient.getNomIngredient());
					pstmt.setInt(2, tempIngredient.getPrixIngredient());
					pstmt.setInt(3, tempIngredient.getQuantiteIngredient());	
					pstmt.execute();
					refresh();
					alerteInfo("Ajout �ffectu�", null, "Les informations ont �t� ajout�es avec succ�s!");
				}
		}

		/** 
		 * Rafraichit les colonnes apr�s un ajout, une modification ou une suppression d'�l�ments.
		 * @throws ClassNotFoundException
		 * @throws SQLException
		 */
		private void refresh() throws ClassNotFoundException, SQLException {
			IngredientsProduits.getIngredientsData().clear();
			Connection conn = bddUtil.dbConnect();
			ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM ingredients");
			while(rs.next()) {
				IngredientsProduits.getIngredientsData().add(new Ingredients (rs.getInt(1), rs.getString(2),rs.getInt(3),rs.getInt(4), null));
				tableIngredients.setItems(IngredientsProduits.getIngredientsData());
			}
			
		}
		/**
		 * Appell� quand l'utilisateur clique sur le bouton supprimer
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
				pstmt.setInt(1, (selectedIngredient.getIdIngredient()));
				pstmt.execute();
				refresh();
				alerteInfo("Suppression r�ussie", null, "La table "+selectedIngredient.getNomIngredient()+" vient d'�tre supprim�e!");
				conn.close();
				pstmt.close();
				}
				catch(SQLException e) {
					System.out.println("Erreur dans le code sql"+e);
				}
				
			

			} else {
				// Si rien n'est s�l�ctionn�
				alerteAttention("Aucune s�lection", "Aucune table de s�lectionn�e!",
						"Selectionnez une table pour pouvoir la supprimer");
			}
		}

		/**
		 * Appel� quand l'utilisateur clique sur le bouton modifier la table. Ouvre une
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
					bddUtil.dbQueryExecute("UPDATE `ingredients` SET `nomIngredient` = '" + selectedIngredient.getNomIngredient() + "', `prixIngredient` = " +selectedIngredient.getPrixIngredient()+ ", `qteRestante` = " +selectedIngredient.getQuantiteIngredient()+";");

					refresh();
					alerteInfo("Modification �ffectu�e", null, "Les informations ont �t� modifi�es avec succ�s!");
				}

			} else {
				// Si rien n'est selectionn�
				alerteAttention("Aucune s�lection", "Aucune r�servation de s�lectionn�e!",
						"Selectionnez une r�servation pour pouvoir la modifier");
			}
	}

}
