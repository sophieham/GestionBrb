package gestionbrb.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gestionbrb.controleur.FonctionsControleurs;
import gestionbrb.model.Produit;
import gestionbrb.util.bddUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// TODO: Auto-generated Javadoc
/**
 * The Class DAOProduit.
 */
/*
 * G�re les requetes sql sur les produits
 */
public class DAOProduit extends DAO<Produit>{
	
	/** The commande DAO. */
	DAOCommande commandeDAO = new DAOCommande();
	
	/** The map nom par id. */
	Map<String, Integer> mapNomParId = new HashMap<>();
	
	/** The map nom par type. */
	Map<String, String> mapNomParType = new HashMap<>();
	
	/** The listeproduits. */
	private ObservableList<Produit> listeproduits = FXCollections.observableArrayList();
	
	/** The conn. */
	public static Connection conn = bddUtil.dbConnect();

	/**
	 * Afficher.
	 *
	 * @return the observable list
	 * @throws SQLException the SQL exception
	 */
	public ObservableList<Produit> afficher() throws SQLException {
				ResultSet res = conn.createStatement().executeQuery("SELECT `ProduitID`, produit.`nom`, `qte`, `description`, `prix`, produit.TypeID, type_produit.nom, ingredients FROM `produit` INNER JOIN type_produit on produit.TypeID = type_produit.TypeID ");
				while (res.next()) {
					listeproduits.add(new Produit(res.getInt("ProduitID"), res.getString("nom"),res.getInt("qte"),res.getString("description"),res.getInt("prix"), res.getString("type_produit.nom"), res.getString("ingredients")));
				}
			return listeproduits;
	}


	/**
	 * Recuperer ID produit.
	 *
	 * @return the map
	 * @throws SQLException the SQL exception
	 */
	public Map<String, Integer> recupererIDProduit() throws SQLException {
		ResultSet requete = conn.createStatement().executeQuery("SELECT ProduitID, nom from produit");
		while(requete.next()) {
			mapNomParId.put(requete.getString("nom"), requete.getInt("ProduitID"));
		}
		return mapNomParId;	
	}
	
	/**
	 * Recuperer type produit.
	 *
	 * @return the map
	 * @throws SQLException the SQL exception
	 */
	@SuppressWarnings("unused")
	public Map<String, String> recupererTypeProduit() throws SQLException {
		ResultSet requete = conn.createStatement().executeQuery("SELECT ProduitID, produit.nom, prix, type_produit.nom from produit inner join type_produit on produit.TypeID=type_produit.TypeID");
		int i = 0;
		while(requete.next()) {
			mapNomParType.put(requete.getString("produit.nom")+"\n "+DAOCommande.recupererDevise()+""+requete.getString("prix"), requete.getString("type_produit.nom"));
			i++;
		}
		return mapNomParType;	
	}
	
	/**
	 * Recuperer nom produit.
	 *
	 * @return the array list
	 * @throws SQLException the SQL exception
	 */
	public ArrayList<String> recupererNomProduit() throws SQLException{
		ArrayList<String> listeNomProduits = new ArrayList<>();
		ResultSet requete = conn.createStatement().executeQuery("SELECT nom, prix FROM produit");
		while(requete.next()) {
			
			listeNomProduits.add(requete.getString("nom")+"\n "+DAOCommande.recupererDevise()+""+requete.getString("prix"));
		}
		return listeNomProduits;	
	}
	
	/**
	 * Afficher details produit.
	 *
	 * @param idProduit the id produit
	 * @return the array list
	 * @throws SQLException the SQL exception
	 */
	public ArrayList<String> afficherDetailsProduit(int idProduit) throws SQLException {
		ArrayList<String> listeProduit = new ArrayList<>();
		ResultSet produitDB = conn.createStatement().executeQuery("SELECT ProduitID,`nom`,`description`,`ingredients` FROM `produit` WHERE ProduitID ="+idProduit);
		while(produitDB.next()) {
			listeProduit.add(produitDB.getString("nom"));
			listeProduit.add(produitDB.getString("description"));
			listeProduit.add(produitDB.getString("ingredients"));
		}
		return listeProduit;
	}
	

	
/**
 * Ajouter.
 *
 * @param p the p
 * @throws SQLException the SQL exception
 */
@Override
public void ajouter(Produit p) throws SQLException{
	PreparedStatement ajoutDB = conn.prepareStatement(
			"INSERT INTO `produit` (`ProduitID`, `nom`, `qte`, `description`, `prix`, `TypeID`, `ingredients`) VALUES (NULL, ?, ?, ?, ?, ?, ?)");
	ajoutDB.setString(1, p.getNomProduit());
	ajoutDB.setInt(2, p.getQuantiteProduit());
	ajoutDB.setString(3, p.getDescriptionProduit());
	ajoutDB.setDouble(4, p.getPrixProduit());
	ajoutDB.setInt(5, FonctionsControleurs.retrouveID(p.getType()));
	ajoutDB.setString(6, p.getIngredients());
	ajoutDB.execute();
	
}

/**
 * Supprimer.
 *
 * @param p the p
 * @throws SQLException the SQL exception
 */
@Override
public void supprimer(Produit p) throws SQLException{
	try {
		PreparedStatement requete = conn.prepareStatement("DELETE FROM `Produit` WHERE ProduitID=?");
		requete.setInt(1, (p.getIdProduit()));
		requete.execute();
	} catch (Exception e) {
		e.printStackTrace();
	}
	
}

/**
 * Modifier.
 *
 * @param p the p
 * @throws SQLException the SQL exception
 */
@Override
public void modifier(Produit p) throws SQLException {
	try {
		PreparedStatement requete = conn.prepareStatement("UPDATE `Produit` SET `nom` = ?, qte = ?, description = ?, prix = ?, TypeID = ? WHERE ProduitID = ?");
		requete.setString(1, (p.getNomProduit()));
		requete.setInt(2, (p.getQuantiteProduit()));
		requete.setString(3, (p.getDescriptionProduit()));
		requete.setDouble(4, (p.getPrixProduit()));
		requete.setInt(5, (FonctionsControleurs.retrouveID(p.getType())));
		requete.setInt(6, (p.getIdProduit()));
		requete.execute();
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	
}


}
