package gestionbrb.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import gestionbrb.model.Type;
import gestionbrb.util.bddUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// TODO: Auto-generated Javadoc
/**
 * The Class DAOType.
 */
/*
 * Gère les requetes sql sur les types de produits
 */
public class DAOType extends DAO<Type>{
	
	/** The nom type. */
	private ArrayList<String> nomType = new ArrayList<>();
	
	/** The liste type. */
	private ObservableList<Type> listeType = FXCollections.observableArrayList();
	
	/** The conn. */
	public static Connection conn = bddUtil.dbConnect();

	/**
	 * Afficher.
	 *
	 * @return the observable list
	 * @throws SQLException the SQL exception
	 */
	public ObservableList<Type> afficher() throws SQLException {
			ResultSet typeDB = conn.createStatement().executeQuery("SELECT `TypeID`, `nom` FROM `type_produit`");

			while (typeDB.next()) {
				listeType.add(new Type(typeDB.getInt("TypeID"), typeDB.getString("nom")));
			}
			typeDB.close();
		return listeType;
	}
	
	/**
	 * Choix type.
	 *
	 * @return the observable list
	 * @throws SQLException the SQL exception
	 */
	public ObservableList<String> choixType() throws SQLException{
		ObservableList<String> choixType =  FXCollections.observableArrayList();
		ResultSet typeDB = conn.createStatement().executeQuery("select TypeID, nom from type_produit");
		while (typeDB.next()) {
			choixType.add("ID: "+typeDB.getInt("TypeID")+" -> "+typeDB.getString("nom"));
		}
		return choixType;
	}

	/**
	 * Recuperer type.
	 *
	 * @return the array list
	 * @throws SQLException the SQL exception
	 */
	public ArrayList<String> recupererType() throws SQLException{
		
		ResultSet typeDB = conn.createStatement().executeQuery("SELECT `nom` FROM `type_produit`");

		while (typeDB.next()) {
			nomType.add(typeDB.getString("nom"));
		}
		typeDB.close();
		return nomType;
	}
	
/**
 * Ajouter.
 *
 * @param t the t
 * @throws SQLException the SQL exception
 */
@Override
public void ajouter(Type t)  throws SQLException{
	PreparedStatement ajoutDB = conn.prepareStatement("INSERT INTO `type_produit` (`TypeID`, `nom`) VALUES (NULL, ?)");
	ajoutDB.setString(1, t.getNomType());
	ajoutDB.execute();
	
}

/**
 * Supprimer.
 *
 * @param t the t
 * @throws SQLException the SQL exception
 */
@Override
public void supprimer(Type t) throws SQLException {
	PreparedStatement suppression = conn.prepareStatement("DELETE FROM `type_produit` WHERE TypeID=?");
	suppression.setInt(1, t.getIdType());
	suppression.execute();
	
}

/**
 * Modifier.
 *
 * @param t the t
 * @throws SQLException the SQL exception
 */
@Override
public void modifier(Type t) throws SQLException{
	PreparedStatement requete = conn.prepareStatement("UPDATE `Produit` SET `nom` = ? WHERE TypeID = ?");
	requete.setString(1, (t.getNomType()));
	requete.setInt(2, (t.getIdType()));
	requete.execute();
}


}
