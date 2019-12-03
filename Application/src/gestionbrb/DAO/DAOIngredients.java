package gestionbrb.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import gestionbrb.controleur.FonctionsControleurs;
import gestionbrb.model.Ingredients;
import gestionbrb.util.bddUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAOIngredients extends DAO<Ingredients>{
	private ObservableList<Ingredients> listeIngredients = FXCollections.observableArrayList();
	public static Connection conn = bddUtil.dbConnect();

	public ObservableList<Ingredients> afficher() throws SQLException {
			ResultSet rs = conn.createStatement().executeQuery("SELECT IngredientID, nomIngredient, prixIngredient, qteRestante, ingredients.idfournisseur, fournisseur.nom from ingredients INNER JOIN fournisseur on ingredients.idfournisseur = fournisseur.idFournisseur ");
			while (rs.next()) {
				listeIngredients.add(new Ingredients(rs.getInt("IngredientID"), rs.getString("nomIngredient"), rs.getInt("prixIngredient"), rs.getInt("qteRestante"), rs.getString("nom")));
			}
			rs.close();
			return listeIngredients;
	}
	
	public ArrayList<String> listeIngredients() throws SQLException {
		ArrayList<String> listeNomIngredients = new ArrayList<>();
		ResultSet ingredientDB = conn.createStatement().executeQuery("select nomIngredient from ingredients");
		while(ingredientDB.next()) {
			String nomIngredient = ingredientDB.getString("nomIngredient");
			listeNomIngredients.add(nomIngredient);
		}
		return listeNomIngredients;
	}

	@Override
	public void ajouter(Ingredients i) throws SQLException {
		PreparedStatement ajoutDB = conn.prepareStatement(
				"INSERT INTO `ingredients` (`IngredientID`, `nomIngredient`, `prixIngredient`, `qteRestante`, `idFournisseur`) VALUES (NULL, ?, ?, ?, ?)");
		ajoutDB.setString(1, i.getNomIngredient());
		ajoutDB.setFloat(2, i.getPrixIngredient());
		ajoutDB.setInt(3, i.getQuantiteIngredient());
		ajoutDB.setInt(4, FonctionsControleurs.retrouveID(i.getFournisseur()));
		ajoutDB.execute();
		ajoutDB.close();
	}

	@Override
	public void supprimer(Ingredients i) throws SQLException {
		PreparedStatement requete = conn.prepareStatement("DELETE FROM `ingredients` WHERE IngredientID=?");
		requete.setInt(1, i.getIdIngredient());
		requete.execute();
		requete.close();

	}

	@Override
	public void modifier(Ingredients i) throws SQLException {
		PreparedStatement requete = conn.prepareStatement("UPDATE `ingredients` SET `nomIngredient` = ?, prixIngredient = ?, qteRestante = ?, idfournisseur = ? WHERE IngredientID = ?");
		requete.setString(1, (i.getNomIngredient()));
		requete.setFloat(2, (i.getPrixIngredient()));
		requete.setInt(3, (i.getQuantiteIngredient()));
		requete.setInt(4, (FonctionsControleurs.retrouveID(i.getFournisseur())));
		requete.setInt(5, (i.getIdIngredient()));
		requete.execute();
		requete.close();
	}

}
