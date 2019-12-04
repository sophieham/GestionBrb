package gestionbrb.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import gestionbrb.model.Fournisseur;
import gestionbrb.util.bddUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAOFournisseur extends DAO<Fournisseur> {
	private ObservableList<Fournisseur> fournisseurs = FXCollections.observableArrayList();
	public static Connection conn = bddUtil.dbConnect();
	
	@Override
	public ObservableList<Fournisseur> afficher() throws SQLException {
		ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM fournisseur");
		while (rs.next()) {
			fournisseurs.add(new Fournisseur(rs.getInt("FournisseurID"),
										 					rs.getString("nom"),
										 					rs.getInt("numTel"), 
										 					rs.getString("adresseMail"), 
										 					rs.getString("adresseDepot"),
										 					rs.getInt("cpDepot"), 
										 					rs.getString("villeDepot")));
		}
		return fournisseurs;
	}
	
	public ObservableList<String> choixFournisseur() throws SQLException{
		ObservableList<String> choixFournisseur =  FXCollections.observableArrayList();
		ResultSet fournisseurDB = conn.createStatement().executeQuery("select FournisseurID, nom from fournisseur");

		while (fournisseurDB.next()) {
			choixFournisseur.add("ID: "+fournisseurDB.getInt("FournisseurID")+" -> "+fournisseurDB.getString("nom"));
		}
		return choixFournisseur;
	}

	@Override
	public void ajouter(Fournisseur f) throws SQLException {
		PreparedStatement fournisseur = conn.prepareStatement("INSERT INTO `fournisseur` (`FournisseurID`, `nom`, `numTel`, `adresseMail`, `adresseDepot`, `cpDepot`, `villeDepot`) "
						+ "VALUES (NULL, ?, ?, ?, ?, ?, ?)");
		fournisseur.setString(6, f.getNomVille());
		fournisseur.setInt(5, f.getCodePostal());
		fournisseur.setString(4, f.getAdresse());
		fournisseur.setString(3, f.getMail());
		fournisseur.setInt(2, f.getNumTel());
		fournisseur.setString(1, f.getNom());
		fournisseur.execute();
		
	}

	@Override
	public void supprimer(Fournisseur f) throws SQLException {
		PreparedStatement suppression = conn.prepareStatement("DELETE FROM `fournisseur` WHERE FournisseurID=?");
		suppression.setInt(1, (f.getIdFournisseur()));
		suppression.execute();
		
	}

	@Override
	public void modifier(Fournisseur f) throws SQLException {
		PreparedStatement fournisseur = conn.prepareStatement("UPDATE `fournisseur` SET `nom` = ?, `numTel` = ?, `adresseMail` = ?, `adresseDepot` = ?, `cpDepot` = ?, `villeDepot` = ? WHERE `FournisseurID` = ?");
		fournisseur.setInt(7, f.getIdFournisseur());
		fournisseur.setString(6, f.getNomVille());
		fournisseur.setInt(5, f.getCodePostal());
		fournisseur.setString(4, f.getAdresse());
		fournisseur.setString(3, f.getMail());
		fournisseur.setInt(2, f.getNumTel());
		fournisseur.setString(1, f.getNom());
		fournisseur.execute();
		
	}

	public ArrayList<String> afficherNomFournisseur(String nomIngredient) throws SQLException {
		ArrayList<String> nomFournisseur = new ArrayList<>();
		ResultSet rs = conn.createStatement().executeQuery("SELECT fournisseur.nom FROM fournisseur INNER JOIN ingredients ON fournisseur.FournisseurID = ingredients.FournisseurID WHERE ingredients.nomIngredient = '"+nomIngredient+"'");
		while (rs.next()) {
			nomFournisseur.add(rs.getString("nom"));
		}
		return nomFournisseur;
	}
	
	public int afficherIDFournisseur(String nomIngredient) throws SQLException {
		int idF = 0;
		ResultSet rs = conn.createStatement().executeQuery("SELECT fournisseur.FournisseurID FROM fournisseur INNER JOIN ingredients ON fournisseur.FournisseurID = ingredients.FournisseurID WHERE ingredients.nomIngredient = '"+nomIngredient+"'");
		while (rs.next()) {
			idF = rs.getInt("fournisseur.FournisseurID");
		}
		return idF;
	}
	
	public void fournirIngredient(int idIngredient, int idFournisseur, int value, String prix) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("INSERT INTO `fourniringredients`(`FournirID`,`IngredientID`, `FournisseurID`, `qte`, `date`, `Prix_Total`) "
				+ "VALUES (null,?,?,?,now(), ?)");
		pstmt.setInt(1, idIngredient);
		pstmt.setInt(2, idFournisseur);
		pstmt.setInt(3, value);
		pstmt.setString(4, prix);
		pstmt.execute();
	}
	
	public ArrayList<String> afficherDerniereCommande() throws SQLException{
		ArrayList<String> commandeIngredient = new ArrayList<>();
		ResultSet requete = conn.createStatement().executeQuery("SELECT `FournirID`, ingredients.nomIngredient, `qte`, `date`, `Prix_Total`, fournisseur.nom FROM `fourniringredients` INNER JOIN ingredients ON ingredients.IngredientID = fourniringredients.IngredientID INNER JOIN fournisseur ON fournisseur.FournisseurID = fourniringredients.FournisseurID ORDER BY FournirID");
		requete.last();
		commandeIngredient.add(requete.getString("FournirID"));
		commandeIngredient.add(requete.getString("ingredients.nomIngredient"));
		commandeIngredient.add(requete.getString("qte"));
		commandeIngredient.add(requete.getString("date"));
		commandeIngredient.add(requete.getString("Prix_Total"));
		commandeIngredient.add(requete.getString("fournisseur.nom"));
		return commandeIngredient;
	}
}
