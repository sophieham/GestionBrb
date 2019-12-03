package gestionbrb.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gestionbrb.controleur.FonctionsControleurs;
import gestionbrb.model.Commande;
import gestionbrb.model.Produit;
import gestionbrb.util.bddUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAOProduit extends DAO<Produit>{
	DAOCommande commandeDAO = new DAOCommande();
	Map<String, Integer> mapNomParId = new HashMap<>();
	Map<String, String> mapNomParType = new HashMap<>();
	private ObservableList<Produit> listeproduits = FXCollections.observableArrayList();
	public static Connection conn = bddUtil.dbConnect();

	public ObservableList<Produit> afficher() throws SQLException {
				ResultSet res = conn.createStatement().executeQuery("SELECT `idProduit`, produit.`nom`, `qte`, `description`, `prix`, produit.idType, type_produit.nom, ingredients FROM `produit` INNER JOIN type_produit on produit.idType = type_produit.idType ");
				while (res.next()) {
					listeproduits.add(new Produit(res.getInt("idProduit"), res.getString("nom"),res.getInt("qte"),res.getString("description"),res.getInt("prix"), res.getString("type_produit.nom"), res.getString("ingredients")));
				}
			return listeproduits;
	}


	public Map<String, Integer> recupererIDProduit() throws SQLException {
		ResultSet requete = conn.createStatement().executeQuery("SELECT idProduit, nom from produit");
		while(requete.next()) {
			mapNomParId.put(requete.getString("nom"), requete.getInt("idProduit"));
		}
		return mapNomParId;	
	}
	
	@SuppressWarnings("unused")
	public Map<String, String> recupererTypeProduit() throws SQLException {
		ResultSet requete = conn.createStatement().executeQuery("SELECT idProduit, produit.nom, prix, type_produit.nom from produit inner join type_produit on produit.idType=type_produit.idType");
		int i = 0;
		while(requete.next()) {
			mapNomParType.put(requete.getString("produit.nom")+"\n "+commandeDAO.recupererDevise()+""+requete.getString("prix"), requete.getString("type_produit.nom"));
			i++;
		}
		return mapNomParType;	
	}
	
	public ArrayList<String> recupererNomProduit() throws SQLException{
		ArrayList<String> listeNomProduits = new ArrayList<>();
		ResultSet requete = conn.createStatement().executeQuery("SELECT nom, prix FROM produit");
		while(requete.next()) {
			
			listeNomProduits.add(requete.getString("nom")+"\n "+commandeDAO.recupererDevise()+""+requete.getString("prix"));
		}
		return listeNomProduits;	
	}
	
	public ArrayList<String> afficherDetailsProduit(int idProduit) throws SQLException {
		ArrayList<String> listeProduit = new ArrayList<>();
		ResultSet produitDB = conn.createStatement().executeQuery("SELECT idProduit,`nom`,`description`,`ingredients` FROM `produit` WHERE idProduit ="+idProduit);
		while(produitDB.next()) {
			listeProduit.add(produitDB.getString("nom"));
			listeProduit.add(produitDB.getString("description"));
			listeProduit.add(produitDB.getString("ingredients"));
		}
		return listeProduit;
	}
	

	
@Override
public void ajouter(Produit p) throws SQLException{
	PreparedStatement ajoutDB = conn.prepareStatement(
			"INSERT INTO `produit` (`idProduit`, `nom`, `qte`, `description`, `prix`, `idType`, `ingredients`) VALUES (NULL, ?, ?, ?, ?, ?, ?)");
	ajoutDB.setString(1, p.getNomProduit());
	ajoutDB.setInt(2, p.getQuantiteProduit());
	ajoutDB.setString(3, p.getDescriptionProduit());
	ajoutDB.setFloat(4, p.getPrixProduit());
	ajoutDB.setInt(5, FonctionsControleurs.retrouveID(p.getType()));
	ajoutDB.setString(6, p.getIngredients());
	ajoutDB.execute();
	
}

@Override
public void supprimer(Produit p) throws SQLException{
	try {
		PreparedStatement requete = conn.prepareStatement("DELETE FROM `Produit` WHERE idProduit=?");
		requete.setInt(1, (p.getIdProduit()));
		requete.execute();
	} catch (Exception e) {
		e.printStackTrace();
	}
	
}

@Override
public void modifier(Produit p) throws SQLException {
	try {
		PreparedStatement requete = conn.prepareStatement("UPDATE `Produit` SET `nom` = ?, qte = ?, description = ?, prix = ?, idType = ? WHERE idProduit = ?");
		requete.setString(1, (p.getNomProduit()));
		requete.setInt(2, (p.getQuantiteProduit()));
		requete.setString(3, (p.getDescriptionProduit()));
		requete.setFloat(4, (p.getPrixProduit()));
		requete.setInt(5, (FonctionsControleurs.retrouveID(p.getType())));
		requete.setInt(6, (p.getIdProduit()));
		requete.execute();
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	
}


}
