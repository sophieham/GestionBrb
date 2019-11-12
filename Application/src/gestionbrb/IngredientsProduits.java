package gestionbrb;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import gestionbrb.model.Ingredients;
import gestionbrb.model.Produit;
import gestionbrb.util.bddUtil;
import gestionbrb.vue.IngredientsProduitsControleur;
import gestionbrb.vue.ModifierIngredientsControleur;
import gestionbrb.vue.ModifierProduitsControleur;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class IngredientsProduits extends Application {
	
	private Stage primaryStage;
	private static ObservableList<Ingredients> listeingredients = FXCollections.observableArrayList();
	private static ObservableList<Produit> listeproduits = FXCollections.observableArrayList();
	
	public IngredientsProduits() {
		try {
			Connection conn = bddUtil.dbConnect();
			ResultSet rs = conn.createStatement().executeQuery("select * from ingredients");
			ResultSet res = conn.createStatement().executeQuery("select * from produit");
			while (rs.next()) {
				listeingredients.add(new Ingredients(rs.getInt("idIngredient"), rs.getString("nomIngredient"), rs.getInt("prixIngredient"), rs.getInt("qteRestante"), null));
			}
			rs.close();
			while (res.next()) {
				listeproduits.add(new Produit(res.getInt("idProduit"), res.getString("nom"),res.getInt("qte"),res.getString("description"),res.getInt("prix"), null));
			}
			res.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	public static ObservableList<Ingredients> getIngredientsData() {
		return listeingredients;
	}
	
	public static ObservableList<Produit> getProduitData() {
		return listeproduits;
	}
	
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Administration -- Listes");
		afficherListe();
	}
	public void afficherListe() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(IngredientsProduits.class.getResource("vue/GererIngredientsProduits.fxml"));
			AnchorPane listesOverview = (AnchorPane) loader.load();

			Scene scene = new Scene(listesOverview);
			primaryStage.setScene(scene);
			primaryStage.show();

			IngredientsProduitsControleur controller = loader.getController();
			controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public boolean fenetreModification(Ingredients ingredient) throws ClassNotFoundException, SQLException {
			try {
				// Charge le fichier fxml et l'ouvre en pop-up
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(IngredientsProduits.class.getResource("vue/ModifierIngredients.fxml"));
				AnchorPane page = (AnchorPane) loader.load();

				// Crée une nouvelle page
				Stage dialogStage = new Stage();
				dialogStage.setTitle("Gestion liste ingredients");
				dialogStage.initModality(Modality.WINDOW_MODAL);
				dialogStage.initOwner(primaryStage);
				Scene scene = new Scene(page);
				dialogStage.setScene(scene);

				// Définition du controleur pour la fenetre
				ModifierIngredientsControleur controller = loader.getController();
				controller.setDialogStage(dialogStage);
				controller.setIngredients(ingredient);

				// Affiche la page et attend que l'utilisateur la ferme.
				dialogStage.showAndWait();

				return controller.isOkClicked();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
	} 
	public boolean fenetreModificationn(Produit produit) throws ClassNotFoundException, SQLException {
		try {
			// Charge le fichier fxml et l'ouvre en pop-up
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(IngredientsProduits.class.getResource("vue/ModifierProduits.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Crée une nouvelle page
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Gestion liste ingredients");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Définition du controleur pour la fenetre
			ModifierProduitsControleur controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setProduit(produit);

			// Affiche la page et attend que l'utilisateur la ferme.
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
} 
	public Stage getPrimaryStage() {
        return primaryStage;
	}

    public static void main(String[] args) {
        launch(args);
    }
}
