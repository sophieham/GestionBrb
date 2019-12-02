package gestionbrb.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import gestionbrb.model.Utilisateur;
import gestionbrb.util.bddUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAOUtilisateur extends DAO<Utilisateur> {
	private ObservableList<Utilisateur> listeComptes = FXCollections.observableArrayList();
	public static Connection conn = bddUtil.dbConnect();
	@Override
	public ObservableList<Utilisateur> afficher() throws SQLException {
		ResultSet rs = conn.createStatement().executeQuery("select * from utilisateurs");
		while (rs.next()) {
			listeComptes.add(new Utilisateur(rs.getInt("idCompte"),  
										rs.getString("identifiant"), 
										rs.getString("pass"), 
										rs.getString("nom"), 
										rs.getString("prenom"), 
										rs.getInt("typeCompte")));
		}
		return listeComptes;
	}

	@Override
	public void ajouter(Utilisateur u) throws SQLException {
		PreparedStatement utilisateursDB = conn.prepareStatement("INSERT INTO `utilisateurs` (`idCompte`, `identifiant`, `pass`, `nom`, `prenom`, `typeCompte`) VALUES (NULL, ?, ?, ?, ?, ?)");
		utilisateursDB.setInt(5, u.getPrivileges());
		utilisateursDB.setString(4, u.getPrenom());
		utilisateursDB.setString(3, u.getNom());
		utilisateursDB.setString(2, u.getMotdepasse());
		utilisateursDB.setString(1, u.getIdentifiant());
		utilisateursDB.execute();
	}

	@Override
	public void supprimer(Utilisateur u) throws SQLException {
		PreparedStatement utilisateursDB = conn.prepareStatement("DELETE FROM `utilisateurs` WHERE identifiant=?");
		utilisateursDB.setString(1, (u.getIdentifiant()));
		utilisateursDB.execute();
		
	}

	@Override
	public void modifier(Utilisateur u) throws SQLException {
		PreparedStatement utilisateursDB = conn.prepareStatement("UPDATE `utilisateurs` SET `idCompte` = ?, `identifiant` = ?, `pass` = ?, `nom` = ?, `prenom` = ?, `typeCompte` = ? WHERE `utilisateurs`.`idCompte` = ? ");
		utilisateursDB.setInt(7,  u.getIdUtilisateur());
		utilisateursDB.setInt(6, u.getPrivileges());
		utilisateursDB.setString(5, u.getPrenom());
		utilisateursDB.setString(4, u.getNom());
		utilisateursDB.setString(3, u.getMotdepasse());
		utilisateursDB.setString(2, u.getIdentifiant());
		utilisateursDB.setInt(1, u.getIdUtilisateur());
		utilisateursDB.execute();
	}

}
