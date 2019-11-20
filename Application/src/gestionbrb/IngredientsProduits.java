package gestionbrb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import gestionbrb.controleur.FonctionsControleurs;
import gestionbrb.controleur.IngredientsProduitsControleur;
import gestionbrb.controleur.ModifierIngredientsControleur;
import gestionbrb.controleur.ModifierProduitsControleur;
import gestionbrb.controleur.ModifierTypesControleur;
import gestionbrb.model.Ingredients;
import gestionbrb.model.Produit;
import gestionbrb.model.Type;
import gestionbrb.util.bddUtil;
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
	private static ObservableList<Type> listeType = FXCollections.observableArrayList();
	
	public IngredientsProduits() {
		try {
			Connection conn = bddUtil.dbConnect();
			ResultSet rs = conn.createStatement().executeQuery("SELECT idIngredient, nomIngredient, prixIngredient, qteRestante, ingredients.idfournisseur, fournisseur.nom from ingredients INNER JOIN fournisseur on ingredients.idfournisseur = fournisseur.idFournisseur ");
			ResultSet res = conn.createStatement().executeQuery("SELECT `idProduit`, produit.`nom`, `qte`, `description`, `prix`, produit.idType, type_produit.nom FROM `produit` INNER JOIN type_produit on produit.idType = type_produit.idType ");
			ResultSet resu = conn.createStatement().executeQuery("SELECT `idType`, `nom` FROM `type_produit`");
			while (rs.next()) {
				listeingredients.add(new Ingredients(rs.getInt("idIngredient"), rs.getString("nomIngredient"), rs.getInt("prixIngredient"), rs.getInt("qteRestante"), rs.getString("nom")));
			}
			rs.close();
			while (res.next()) {
				listeproduits.add(new Produit(res.getInt("idProduit"), res.getString("nom"),res.getInt("qte"),res.getString("description"),res.getInt("prix"), res.getString("type_produit.nom")));
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
	public static ObservableList<Ingredients> getIngredientsData() {
		return listeingredients;
	}
	
	public static ObservableList<Produit> getProduitData() {
		return listeproduits;
	}
	public static ObservableList<Type> getTypeData() {
		return listeType;
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

		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Erreur d'éxecution", "Détails: "+e);
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
			} catch (Exception e) {
				FonctionsControleurs.alerteErreur("Erreur", "Erreur d'éxecution", "Détails: "+e);
				return false;
			}
	} 
	public boolean fenetreModification(Produit produit) throws ClassNotFoundException, SQLException {
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
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Erreur d'éxecution", "Détails: "+e);
			return false;
		}
} 
	public boolean fenetreModification(Type type) throws ClassNotFoundException, SQLException {
		try {
			// Charge le fichier fxml et l'ouvre en pop-up
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(IngredientsProduits.class.getResource("vue/ModifierType.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Crée une nouvelle page
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Gestion liste type");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
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
	public Stage getPrimaryStage() {
        return primaryStage;
	}

    public static void main(String[] args) {
        launch(args);
    }
}
