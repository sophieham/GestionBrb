package gestionbrb.controleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AdministrationControleur extends FonctionsControleurs {
	private static Stage historiqueCommande;
	private static Stage fournisseurs;
	private static Stage tables;
	private static Stage utilisateur;
	private static Stage platsIngredients;

	MenuPrincipalControleur parent;

	@FXML
	private AnchorPane fenetre;

	@FXML
	public void fenetreHistoriqueCommandes() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/CalendrierReservations.fxml"));
			Parent vueDCommande = (Parent) loader.load();
			setHistoriqueCommande(new Stage());
			getHistoriqueCommande().setScene(new Scene(vueDCommande));
			getHistoriqueCommande().show();
			getHistoriqueCommande().setTitle("Historique des commandes");

			CalendrierControleur controller = loader.getController();
			controller.setParent(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void fenetreFournisseur() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/GestionFournisseurs.fxml"));
			Parent vueGestionStockAdmin = (Parent) loader.load();
			setFournisseurs(new Stage());
			getFournisseurs().setScene(new Scene(vueGestionStockAdmin));
			getFournisseurs().show();
			getFournisseurs().setTitle("Gestion du stock");

			FournisseursControleur controller = loader.getController();
			controller.setParent(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void fenetreTables() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/GererTables.fxml"));
			Parent vueParametre = (Parent) loader.load();
			setTables(new Stage());
			getTables().setScene(new Scene(vueParametre));
			getTables().show();
			getTables().setTitle("Tables");

			TablesControleur controller = loader.getController();
			controller.setParent(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void fenetreUtilisateur() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/GestionComptes.fxml"));
			Parent vueParametre = (Parent) loader.load();
			setUtilisateur(new Stage());
			getUtilisateur().setScene(new Scene(vueParametre));
			getUtilisateur().show();
			getUtilisateur().setTitle("Liste des utilisateurs");

			UtilisateursControleur controller = loader.getController();
			controller.setParent(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void fenetrePlatsIngerdients() {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/GererIngredientsProduits.fxml"));
			Parent vueAdministration= (Parent) loader.load();
			setPlatsIngredients(new Stage());
			getPlatsIngredients().setScene(new Scene(vueAdministration));
			getPlatsIngredients().show();
			getPlatsIngredients().setTitle("Plats Ingredients");

			IngredientsProduitsControleur controller = loader.getController();
			controller.setParent(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	

	}
	

	

	// getters & setters utiles pour gérer la fenetre depuis les controleurs
	// associés
	public static Stage getHistoriqueCommande() {
		return historiqueCommande;
	}

	public static void setHistoriqueCommande(Stage historiqueCommande) {
		AdministrationControleur.historiqueCommande = historiqueCommande;
	}

	public static Stage getFournisseurs() {
		return fournisseurs;
	}

	public static void setFournisseurs(Stage fournisseurs) {
		AdministrationControleur.fournisseurs = fournisseurs;
	}

	public static Stage getTables() {
		return tables;
	}

	public static void setTables(Stage tables) {
		AdministrationControleur.tables = tables;
	}

	public static Stage getUtilisateur() {
		return utilisateur;
	}

	public static void setUtilisateur(Stage utilisateur) {
		AdministrationControleur.utilisateur = utilisateur;
	}

	public static Stage getPlatsIngredients() {
		return platsIngredients;
	}

	public static void setPlatsIngredients(Stage platsIngredients) {
		AdministrationControleur.platsIngredients = platsIngredients;
	}

	/**
	 * Défini connexionProfil comment parent quand on accède au menu principal
	 * depuis la page de connexion
	 * 
	 * @param menuPrincipalControleur
	 */
	public void setParent(MenuPrincipalControleur menuPrincipalControleur) {
		this.parent = menuPrincipalControleur;
	}

	
	
}
