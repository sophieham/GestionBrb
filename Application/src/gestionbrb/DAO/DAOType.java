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

public class DAOType extends DAO<Type>{
	private ArrayList<String> nomType = new ArrayList<>();
	private ObservableList<Type> listeType = FXCollections.observableArrayList();
	public static Connection conn = bddUtil.dbConnect();

	public ObservableList<Type> afficher() throws SQLException {
			ResultSet typeDB = conn.createStatement().executeQuery("SELECT `idType`, `nom` FROM `type_produit`");

			while (typeDB.next()) {
				listeType.add(new Type(typeDB.getInt("idType"), typeDB.getString("nom")));
			}
			typeDB.close();
		return listeType;
	}
	
	public ObservableList<String> choixType() throws SQLException{
		ObservableList<String> choixType =  FXCollections.observableArrayList();
		ResultSet typeDB = conn.createStatement().executeQuery("select idType, nom from type_produit");
		while (typeDB.next()) {
			choixType.add("ID: "+typeDB.getInt("idType")+" -> "+typeDB.getString("nom"));
		}
		return choixType;
	}

	public ArrayList<String> recupererType() throws SQLException{
		
		ResultSet typeDB = conn.createStatement().executeQuery("SELECT `nom` FROM `type_produit`");

		while (typeDB.next()) {
			nomType.add(typeDB.getString("nom"));
		}
		typeDB.close();
		return nomType;
	}
	
@Override
public void ajouter(Type t)  throws SQLException{
	PreparedStatement ajoutDB = conn.prepareStatement("INSERT INTO `type_produit` (`idType`, `nom`) VALUES (NULL, ?)");
	ajoutDB.setString(1, t.getNomType());
	ajoutDB.execute();
	
}

@Override
public void supprimer(Type t) throws SQLException {
	PreparedStatement suppression = conn.prepareStatement("DELETE FROM `type_produit` WHERE idType=?");
	suppression.setInt(1, t.getIdType());
	suppression.execute();
	
}

@Override
public void modifier(Type t) throws SQLException{
	PreparedStatement requete = conn.prepareStatement("UPDATE `Produit` SET `nom` = ? WHERE idType = ?");
	requete.setString(1, (t.getNomType()));
	requete.setInt(2, (t.getIdType()));
	requete.execute();
}


}
