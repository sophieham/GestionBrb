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

// TODO: Auto-generated Javadoc
/**
 * The Class DAOUtilisateur.
 */
/*
 * Gère les requetes sql sur les utilisateurs
 */
public class DAOUtilisateur extends DAO<Utilisateur> {
	
	/** The liste comptes. */
	private ObservableList<Utilisateur> listeComptes = FXCollections.observableArrayList();
	
	/** The connexions. */
	private ObservableList<Utilisateur> connexions = FXCollections.observableArrayList();
	
	/** The conn. */
	public static Connection conn = bddUtil.dbConnect();
	
	/**
	 * Afficher.
	 *
	 * @return the observable list
	 * @throws SQLException the SQL exception
	 */
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

	/**
	 * Ajouter.
	 *
	 * @param u the u
	 * @throws SQLException the SQL exception
	 */
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

	/**
	 * Supprimer.
	 *
	 * @param u the u
	 * @throws SQLException the SQL exception
	 */
	@Override
	public void supprimer(Utilisateur u) throws SQLException {
		PreparedStatement utilisateursDB = conn.prepareStatement("DELETE FROM `utilisateurs` WHERE identifiant=?");
		utilisateursDB.setString(1, (u.getIdentifiant()));
		utilisateursDB.execute();
		
	}

	/**
	 * Modifier.
	 *
	 * @param u the u
	 * @throws SQLException the SQL exception
	 */
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
	
	
	/**
	 * Combinaison est valide.
	 *
	 * @param user the user
	 * @param pass the pass
	 * @return true, if successful
	 * @throws SQLException the SQL exception
	 */
	public boolean combinaisonEstValide(String user, String pass) throws SQLException {
		PreparedStatement requete = conn.prepareStatement("SELECT * FROM utilisateurs WHERE identifiant = ? AND pass = ?");
		requete.setString(1, user);
		requete.setString(2, pass);
		ResultSet combinaison = requete.executeQuery();
		
			return combinaison.next();

	}

	/**
	 * Connexion.
	 *
	 * @param user the user
	 * @param pass the pass
	 * @return the utilisateur
	 * @throws SQLException the SQL exception
	 */
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
	
	/**
	 * Log.
	 *
	 * @param u the u
	 * @throws SQLException the SQL exception
	 */
	public void log(Utilisateur u) throws SQLException{
		PreparedStatement utilisateursDB = conn.prepareStatement("INSERT INTO `logs` (`ID`, `identifiant`, `timestamp`) VALUES (NULL, ?, current_timestamp())");
		utilisateursDB.setString(1, u.getIdentifiant());
		utilisateursDB.execute();
	}
	
	/**
	 * Modifier langue.
	 *
	 * @param langue the langue
	 * @throws SQLException the SQL exception
	 */
	public void modifierLangue(String langue) throws SQLException {
		PreparedStatement modifierLangue = conn.prepareStatement("UPDATE utilisateurs SET langue = ? WHERE identifiant = ? ");
		modifierLangue.setString(1, langue);
		modifierLangue.setString(2, ConnexionControleur.getUtilisateurConnecte().getIdentifiant());
		modifierLangue.execute();
	}
	
	/**
	 * Recuperer langue.
	 *
	 * @param id the id
	 * @return the string
	 * @throws SQLException the SQL exception
	 */
	public String recupererLangue(int id) throws SQLException{
		ResultSet requete = conn.createStatement().executeQuery("select langue from utilisateurs WHERE CompteID = " + id);
		requete.next();
		String langue = requete.getString("langue");

		return langue;
	}
	
	/**
	 * *********************Logs de connexions********************************.
	 *
	 * @return the observable list
	 * @throws SQLException the SQL exception
	 */

	public ObservableList<Utilisateur> afficherTout() throws SQLException{
		ResultSet connexionsDB = conn.createStatement().executeQuery("SELECT * from logs ORDER BY day(timestamp) desc");
		while (connexionsDB.next()) {
			connexions.add(new Utilisateur(connexionsDB.getInt("ID"),
											connexionsDB.getString("identifiant"),
											connexionsDB.getString("timestamp")));
		}
		return connexions;
	}
	
	/**
	 * Compter tout.
	 *
	 * @return the string
	 * @throws SQLException the SQL exception
	 */
	public String compterTout() throws SQLException{
		ResultSet count = conn.createStatement().executeQuery("SELECT count(ID) from logs ORDER BY ID desc");
		String stat = 0+"";
		count.last();
		stat = count.getString("count(ID)");
		return stat;
	}
	
	/**
	 * Trier par jour.
	 *
	 * @throws SQLException the SQL exception
	 */
	public void trierParJour() throws SQLException{
		ResultSet connexionsDB = conn.createStatement().executeQuery("SELECT * from logs WHERE CURRENT_DATE = substring(timestamp, 1, 10) ORDER BY hour(timestamp) desc, minute(timestamp) desc");
		while (connexionsDB.next()) {
			connexions.add(new Utilisateur(connexionsDB.getInt("ID"),
											connexionsDB.getString("identifiant"),
											connexionsDB.getString("timestamp")));
		}
	}
	
	/**
	 * Compter par jour.
	 *
	 * @return the string
	 * @throws SQLException the SQL exception
	 */
	public String compterParJour() throws SQLException{
		ResultSet count = conn.createStatement().executeQuery("SELECT count(ID) from logs WHERE CURRENT_DATE = substring(timestamp, 1, 10) ORDER BY ID desc ");
		String stat = 0+"";
		count.last();
		stat = count.getString("count(ID)");
		return stat;
	}
	
	/**
	 * Trier par semaine.
	 *
	 * @throws SQLException the SQL exception
	 */
	public void trierParSemaine() throws SQLException{
		ResultSet connexionsDB = conn.createStatement().executeQuery("SELECT * FROM logs WHERE week(timestamp) = week(CURRENT_DATE) order by hour(timestamp) desc");
		while (connexionsDB.next()) {
			connexions.add(new Utilisateur(connexionsDB.getInt("ID"),
											connexionsDB.getString("identifiant"),
											connexionsDB.getString("timestamp")));
		}
	}
	
	/**
	 * Compter par semaine.
	 *
	 * @return the string
	 * @throws SQLException the SQL exception
	 */
	public String compterParSemaine() throws SQLException{
		ResultSet count = conn.createStatement().executeQuery("SELECT count(ID) from logs WHERE week(timestamp) = week(CURRENT_DATE) order by ID desc");
		String stat = 0+"";
		count.last();
		stat = count.getString("count(ID)");
		return stat;
	}
	
	/**
	 * Trier par mois.
	 *
	 * @throws SQLException the SQL exception
	 */
	public void trierParMois() throws SQLException{
		ResultSet connexionsDB = conn.createStatement().executeQuery("SELECT * FROM `logs` WHERE substring(CURRENT_DATE, 1, 7) = substring(timestamp, 1, 7) ORDER BY DAY(timestamp) desc"); 
		while (connexionsDB.next()) {
			connexions.add(new Utilisateur(connexionsDB.getInt("ID"),
											connexionsDB.getString("identifiant"),
											connexionsDB.getString("timestamp")));
		}
	}
	
	/**
	 * Compter par mois.
	 *
	 * @return the string
	 * @throws SQLException the SQL exception
	 */
	public String compterParMois() throws SQLException{
		ResultSet count = conn.createStatement().executeQuery("SELECT count(ID) from logs WHERE substring(CURRENT_DATE, 1, 7) = substring(timestamp, 1, 7) ORDER BY ID desc"); 
		String stat = 0+"";
		count.last();
		stat = count.getString("count(ID)");
		return stat;
	}
	
	/**
	 * Trier par annee.
	 *
	 * @throws SQLException the SQL exception
	 */
	public void trierParAnnee() throws SQLException{
		ResultSet connexionsDB = conn.createStatement().executeQuery("SELECT * FROM `logs` WHERE substring(CURRENT_DATE, 1, 4) = substring(timestamp, 1, 4) ORDER BY MONTH(timestamp) desc, DAY(timestamp) DESC"); 
		while (connexionsDB.next()) {
			connexions.add(new Utilisateur(connexionsDB.getInt("ID"),
											connexionsDB.getString("identifiant"),
											connexionsDB.getString("timestamp")));
		}
	}
	
	/**
	 * Compter par annee.
	 *
	 * @return the string
	 * @throws SQLException the SQL exception
	 */
	public String compterParAnnee() throws SQLException{
		ResultSet count = conn.createStatement().executeQuery("SELECT count(ID) from logs WHERE substring(CURRENT_DATE, 1, 4) = substring(timestamp, 1, 4) ORDER BY ID DESC"); 
		String stat = 0+"";
		count.last();
		stat = count.getString("count(ID)");
		return stat;
	}
	

}
