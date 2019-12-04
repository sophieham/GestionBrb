package gestionbrb.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import gestionbrb.controleur.ConnexionControleur;
import gestionbrb.model.Utilisateur;
import gestionbrb.util.bddUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAOUtilisateur extends DAO<Utilisateur> {
	private ObservableList<Utilisateur> listeComptes = FXCollections.observableArrayList();
	private ObservableList<Utilisateur> connexions = FXCollections.observableArrayList();
	public static Connection conn = bddUtil.dbConnect();
	@Override
	public ObservableList<Utilisateur> afficher() throws SQLException {
		ResultSet rs = conn.createStatement().executeQuery("select * from utilisateurs");
		while (rs.next()) {
			listeComptes.add(new Utilisateur(rs.getInt("CompteID"),  
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
		PreparedStatement utilisateursDB = conn.prepareStatement("INSERT INTO `utilisateurs` (`CompteID`, `identifiant`, `pass`, `nom`, `prenom`, `typeCompte`) VALUES (NULL, ?, ?, ?, ?, ?)");
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
		PreparedStatement utilisateursDB = conn.prepareStatement("UPDATE `utilisateurs` SET `CompteID` = ?, `identifiant` = ?, `pass` = ?, `nom` = ?, `prenom` = ?, `typeCompte` = ? WHERE `utilisateurs`.`CompteID` = ? ");
		utilisateursDB.setInt(7,  u.getIdUtilisateur());
		utilisateursDB.setInt(6, u.getPrivileges());
		utilisateursDB.setString(5, u.getPrenom());
		utilisateursDB.setString(4, u.getNom());
		utilisateursDB.setString(3, u.getMotdepasse());
		utilisateursDB.setString(2, u.getIdentifiant());
		utilisateursDB.setInt(1, u.getIdUtilisateur());
		utilisateursDB.execute();
	}
	
	
	public boolean combinaisonEstValide(String user, String pass) throws SQLException {
		PreparedStatement requete = conn.prepareStatement("SELECT * FROM utilisateurs WHERE identifiant = ? AND pass = ?");
		requete.setString(1, user);
		requete.setString(2, pass);
		ResultSet combinaison = requete.executeQuery();
		if (combinaison.next()) {
			return true;
		} else
			return false;
	}

	public Utilisateur connexion(String user, String pass) throws SQLException {
		Utilisateur utilisateur = new Utilisateur();
		ResultSet combinaison = conn.createStatement().executeQuery(
				"SELECT * FROM utilisateurs WHERE identifiant = '" + user + "' AND pass = '" + pass + "'");

		while (combinaison.next()) {
			utilisateur = new Utilisateur(combinaison.getInt("CompteID"), 
					combinaison.getString("identifiant"),
					combinaison.getString("pass"), 
					combinaison.getString("nom"), 
					combinaison.getString("prenom"),
					combinaison.getInt("typeCompte"));
			return utilisateur;
		}
		return utilisateur;

	}
	
	public void log(Utilisateur u) throws SQLException{
		PreparedStatement utilisateursDB = conn.prepareStatement("INSERT INTO `logs` (`ID`, `identifiant`, `timestamp`) VALUES (NULL, ?, current_timestamp())");
		utilisateursDB.setString(1, u.getIdentifiant());
		utilisateursDB.execute();
	}
	
	public void modifierLangue(String langue) throws SQLException {
		PreparedStatement modifierLangue = conn.prepareStatement("UPDATE utilisateurs SET langue = ? WHERE identifiant = ? ");
		modifierLangue.setString(1, langue);
		modifierLangue.setString(2, ConnexionControleur.getUtilisateurConnecte().getIdentifiant());
		modifierLangue.execute();
		System.out.println(modifierLangue);
	}
	
	public String recupererLangue(int id) throws SQLException{
		String langue = "fr";
		ResultSet requete = conn.createStatement().executeQuery("select langue from utilisateurs WHERE identifiant = "+id);
		System.out.println(id);
		while(requete.next()) {
			langue = requete.getString("langue");
		}
		
		return langue;
	}
	
	/**************************************************************/

	public ObservableList<Utilisateur> afficherTout() throws SQLException{
		ResultSet connexionsDB = conn.createStatement().executeQuery("SELECT * from logs ORDER BY day(timestamp) desc");
		while (connexionsDB.next()) {
			connexions.add(new Utilisateur(connexionsDB.getInt("ID"),
											connexionsDB.getString("identifiant"),
											connexionsDB.getString("timestamp")));
		}
		return connexions;
	}
	
	public String compterTout() throws SQLException{
		ResultSet count = conn.createStatement().executeQuery("SELECT count(ID) from logs ORDER BY ID desc");
		String stat = 0+"";
		count.last();
		stat = count.getString("count(ID)");
		return stat;
	}
	
	public void trierParJour() throws SQLException{
		ResultSet connexionsDB = conn.createStatement().executeQuery("SELECT * from logs WHERE CURRENT_DATE = substring(timestamp, 1, 10) ORDER BY hour(timestamp) desc, minute(timestamp) desc");
		while (connexionsDB.next()) {
			connexions.add(new Utilisateur(connexionsDB.getInt("ID"),
											connexionsDB.getString("identifiant"),
											connexionsDB.getString("timestamp")));
		}
	}
	
	public String compterParJour() throws SQLException{
		ResultSet count = conn.createStatement().executeQuery("SELECT count(ID) from logs WHERE CURRENT_DATE = substring(timestamp, 1, 10) ORDER BY ID desc ");
		String stat = 0+"";
		count.last();
		stat = count.getString("count(ID)");
		return stat;
	}
	
	public void trierParSemaine() throws SQLException{
		ResultSet connexionsDB = conn.createStatement().executeQuery("SELECT * FROM logs WHERE week(timestamp) = week(CURRENT_DATE) order by hour(timestamp) desc");
		while (connexionsDB.next()) {
			connexions.add(new Utilisateur(connexionsDB.getInt("ID"),
											connexionsDB.getString("identifiant"),
											connexionsDB.getString("timestamp")));
		}
	}
	
	public String compterParSemaine() throws SQLException{
		ResultSet count = conn.createStatement().executeQuery("SELECT count(ID) from logs WHERE week(timestamp) = week(CURRENT_DATE) order by ID desc");
		String stat = 0+"";
		count.last();
		stat = count.getString("count(ID)");
		return stat;
	}
	
	public void trierParMois() throws SQLException{
		ResultSet connexionsDB = conn.createStatement().executeQuery("SELECT * FROM `logs` WHERE substring(CURRENT_DATE, 1, 7) = substring(timestamp, 1, 7) ORDER BY DAY(timestamp) desc"); 
		while (connexionsDB.next()) {
			connexions.add(new Utilisateur(connexionsDB.getInt("ID"),
											connexionsDB.getString("identifiant"),
											connexionsDB.getString("timestamp")));
		}
	}
	
	public String compterParMois() throws SQLException{
		ResultSet count = conn.createStatement().executeQuery("SELECT count(ID) from logs WHERE substring(CURRENT_DATE, 1, 7) = substring(timestamp, 1, 7) ORDER BY ID desc"); 
		String stat = 0+"";
		count.last();
		stat = count.getString("count(ID)");
		return stat;
	}
	
	public void trierParAnnee() throws SQLException{
		ResultSet connexionsDB = conn.createStatement().executeQuery("SELECT * FROM `logs` WHERE substring(CURRENT_DATE, 1, 4) = substring(timestamp, 1, 4) ORDER BY MONTH(timestamp) desc, DAY(timestamp) DESC"); 
		while (connexionsDB.next()) {
			connexions.add(new Utilisateur(connexionsDB.getInt("ID"),
											connexionsDB.getString("identifiant"),
											connexionsDB.getString("timestamp")));
		}
	}
	
	public String compterParAnnee() throws SQLException{
		ResultSet count = conn.createStatement().executeQuery("SELECT count(ID) from logs WHERE substring(CURRENT_DATE, 1, 4) = substring(timestamp, 1, 4) ORDER BY ID DESC"); 
		String stat = 0+"";
		count.last();
		stat = count.getString("count(ID)");
		return stat;
	}
	

}
