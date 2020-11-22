package gestionbrb.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import gestionbrb.controleur.FonctionsControleurs;
import gestionbrb.controleur.GestionStockController;
import gestionbrb.model.Ingredients;
import gestionbrb.util.bddUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// TODO: Auto-generated Javadoc
/**
 * The Class DAOIngredients.
 */
/*
 * Gère les requetes sql sur les ingrédients
 */
public class DAOIngredients extends DAO<Ingredients>{
	
	/** The liste ingredients. */
	private ObservableList<Ingredients> listeIngredients = FXCollections.observableArrayList();
	
	/** The conn. */
	public static Connection conn = bddUtil.dbConnect();

	/**
	 * Afficher.
	 *
	 * @return the observable list
	 * @throws SQLException the SQL exception
	 */
	public ObservableList<Ingredients> afficher() throws SQLException {
			ResultSet rs = conn.createStatement().executeQuery("SELECT IngredientID, nomIngredient, prixIngredient, qteRestante, ingredients.FournisseurID, fournisseur.nom from ingredients INNER JOIN fournisseur on ingredients.FournisseurID = fournisseur.FournisseurID ");
			while (rs.next()) {
				listeIngredients.add(new Ingredients(rs.getInt("IngredientID"), rs.getString("nomIngredient"), rs.getInt("prixIngredient"), rs.getInt("qteRestante"), rs.getString("nom")));
			}
			rs.close();
			return listeIngredients;
	}
	
	/**
	 * Liste ingredients.
	 *
	 * @return the array list
	 * @throws SQLException the SQL exception
	 */
	public ArrayList<String> listeIngredients() throws SQLException {
		ArrayList<String> listeNomIngredients = new ArrayList<>();
		ResultSet ingredientDB = conn.createStatement().executeQuery("select nomIngredient from ingredients");
		while(ingredientDB.next()) {
			String nomIngredient = ingredientDB.getString("nomIngredient");
			listeNomIngredients.add(nomIngredient);
		}
		return listeNomIngredients;
	}

	/**
	 * Ajouter.
	 *
	 * @param i the i
	 * @throws SQLException the SQL exception
	 */
	@Override
	public void ajouter(Ingredients i) throws SQLException {
		PreparedStatement ajoutDB = conn.prepareStatement(
				"INSERT INTO `ingredients` (`IngredientID`, `nomIngredient`, `prixIngredient`, `qteRestante`, `FournisseurID`) VALUES (NULL, ?, ?, ?, ?)");
		ajoutDB.setString(1, i.getNomIngredient());
		ajoutDB.setDouble(2, i.getPrixIngredient());
		ajoutDB.setInt(3, i.getQuantiteIngredient());
		ajoutDB.setInt(4, FonctionsControleurs.retrouveID(i.getFournisseur()));
		ajoutDB.execute();
		ajoutDB.close();
	}

	/**
	 * Supprimer.
	 *
	 * @param i the i
	 * @throws SQLException the SQL exception
	 */
	@Override
	public void supprimer(Ingredients i) throws SQLException {
		PreparedStatement requete = conn.prepareStatement("DELETE FROM `ingredients` WHERE IngredientID=?");
		requete.setInt(1, i.getIdIngredient());
		requete.execute();
		requete.close();

	}

	/**
	 * Modifier.
	 *
	 * @param i the i
	 * @throws SQLException the SQL exception
	 */
	@Override
	public void modifier(Ingredients i) throws SQLException {
		PreparedStatement requete = conn.prepareStatement("UPDATE `ingredients` SET `nomIngredient` = ?, prixIngredient = ?, qteRestante = ?, FournisseurID = ? WHERE IngredientID = ?");
		requete.setString(1, (i.getNomIngredient()));
		requete.setDouble(2, (i.getPrixIngredient()));
		requete.setInt(3, (i.getQuantiteIngredient()));
		requete.setInt(4, (FonctionsControleurs.retrouveID(i.getFournisseur())));
		requete.setInt(5, (i.getIdIngredient()));
		requete.execute();
		requete.close();
	}
	
	/**
	 * Afficher prix ingredient.
	 *
	 * @param nomIngredient the nom ingredient
	 * @param nomFournisseur the nom fournisseur
	 * @return the array list
	 * @throws SQLException the SQL exception
	 */
	public ArrayList<Double> afficherPrixIngredient(String nomIngredient, String nomFournisseur) throws SQLException{
		ArrayList<Double> prixIngredient = new ArrayList<>();
		ResultSet rs = conn.createStatement().executeQuery("SELECT ingredients.prixIngredient FROM ingredients INNER JOIN fournisseur ON fournisseur.FournisseurID = ingredients.FournisseurID WHERE ingredients.nomIngredient = '"+nomIngredient+"' AND fournisseur.nom =  '"+nomFournisseur+"'");
		while (rs.next()) {
			prixIngredient.add(rs.getDouble("ingredients.prixIngredient"));
		}
		return prixIngredient;
	}
	
	/**
	 * Afficher qte restante.
	 *
	 * @param nomIngredient the nom ingredient
	 * @param nomFournisseur the nom fournisseur
	 * @return the array list
	 * @throws SQLException the SQL exception
	 */
	public ArrayList<String> afficherQteRestante(String nomIngredient, String nomFournisseur) throws SQLException{
		ArrayList<String> qteR = new ArrayList<>();
		ResultSet rs = conn.createStatement().executeQuery("SELECT ingredients.qteRestante FROM ingredients INNER JOIN fournisseur ON fournisseur.FournisseurID = ingredients.FournisseurID WHERE ingredients.nomIngredient = '"+nomIngredient+"' AND fournisseur.nom =  '"+nomFournisseur+"'");
		while (rs.next()) {
			qteR.add(rs.getString("ingredients.qteRestante"));
		}
		return qteR;
	}
	
	/**
	 * Commander ingredients.
	 *
	 * @param n the n
	 * @param nomIngredient the nom ingredient
	 * @param output the output
	 * @throws SQLException the SQL exception
	 */
	public void commanderIngredients(String n, String nomIngredient, String output) throws SQLException{
		PreparedStatement requete = conn.prepareStatement("UPDATE ingredients INNER JOIN fournisseur ON ingredients.FournisseurID = fournisseur.FournisseurID SET ingredients.qteRestante = ? WHERE ingredients.nomIngredient = ? AND fournisseur.nom = ?");
		requete.setString(1, n);
		requete.setString(2, GestionStockController.Nom);
		requete.setString(3, output);
		requete.execute();
	}
	

}
