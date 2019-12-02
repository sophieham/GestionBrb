package gestionbrb.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
			fournisseurs.add(new Fournisseur(rs.getInt("idFournisseur"),
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
		ResultSet fournisseurDB = conn.createStatement().executeQuery("select idFournisseur, nom from fournisseur");

		while (fournisseurDB.next()) {
			choixFournisseur.add("ID: "+fournisseurDB.getInt("idFournisseur")+" -> "+fournisseurDB.getString("nom"));
		}
		return choixFournisseur;
	}

	@Override
	public void ajouter(Fournisseur f) throws SQLException {
		PreparedStatement fournisseur = conn.prepareStatement("INSERT INTO `fournisseur` (`idFournisseur`, `nom`, `numTel`, `adresseMail`, `adresseDepot`, `cpDepot`, `villeDepot`) "
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
		PreparedStatement suppression = conn.prepareStatement("DELETE FROM `fournisseur` WHERE idFournisseur=?");
		suppression.setInt(1, (f.getIdFournisseur()));
		suppression.execute();
		
	}

	@Override
	public void modifier(Fournisseur f) throws SQLException {
		PreparedStatement fournisseur = conn.prepareStatement("UPDATE `fournisseur` SET `nom` = ?, `numTel` = ?, `adresseMail` = ?, `adresseDepot` = ?, `cpDepot` = ?, `villeDepot` = ? WHERE `idFournisseur` = ?");
		fournisseur.setInt(7, f.getIdFournisseur());
		fournisseur.setString(6, f.getNomVille());
		fournisseur.setInt(5, f.getCodePostal());
		fournisseur.setString(4, f.getAdresse());
		fournisseur.setString(3, f.getMail());
		fournisseur.setInt(2, f.getNumTel());
		fournisseur.setString(1, f.getNom());
		fournisseur.execute();
		
	}

}
