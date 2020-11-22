package gestionbrb.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import gestionbrb.controleur.ConnexionControleur;
import gestionbrb.model.Commande;
import gestionbrb.model.Produit;
import gestionbrb.util.bddUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// TODO: Auto-generated Javadoc
/**
 * The Class DAOCommande.
 */
/*
 * Gère les requetes sql sur les commandes clients
 */
public class DAOCommande extends DAO<Commande>{
	
	/** The stats. */
	private ArrayList<Integer> stats = new ArrayList<>();
	
	/** The liste commande. */
	private ObservableList<Commande> listeCommande = FXCollections.observableArrayList();
	
	/** The conn. */
	public static Connection conn = bddUtil.dbConnect();
	
	/**
	 * Crée des objets Commande à l'aide de la base de donnée puis les ajoute dans la base de donnée.
	 *
	 * @return the observable list
	 * @throws SQLException the SQL exception
	 */
	@Override
	public ObservableList<Commande> afficher() throws SQLException {
		ResultSet commandeDB = conn.createStatement().executeQuery("SELECT commande.`CommandeID`, `noTable`, `prixTotal`, `nbCouverts`, `date`, contenirproduit.serveurID FROM `commande` INNER JOIN contenirproduit ON commande.CommandeID = contenirproduit.CommandeID group by commande.CommandeID order by year(date) desc, month(date) desc, day(date) desc");
		while (commandeDB.next()) {
			listeCommande.add(new Commande(	commandeDB.getInt("CommandeID"), 
											commandeDB.getInt("noTable"), 
											commandeDB.getInt("nbCouverts"), 
											commandeDB.getDouble("prixTotal"), 
											commandeDB.getString("date"), 
											commandeDB.getString("serveurID")));
		}
		return listeCommande;
	}
	
	/**
	 * Affiche le prix total de la commande en cours.
	 *
	 * @param c la commande en cours
	 * @return le prix total
	 * @throws SQLException the SQL exception
	 */
	public double afficherPrixTotal(Commande c) throws SQLException {
		double prixTotal = 0;
		ResultSet commandeDB = conn.createStatement().executeQuery("SELECT sum(contenirproduit.qte*produit.prix) as prixt FROM `contenirproduit` INNER JOIN produit on contenirproduit.ProduitID = produit.ProduitID WHERE CommandeID = "+c.getIdCommande());
		while (commandeDB.next()) {
		prixTotal = Double.parseDouble(commandeDB.getString("prixt"));
		}
		return prixTotal;
	}
	
	/**
	 * Met à jour le prix total lorsqu'un produit est rajouté à la commande en cours.
	 *
	 * @param c la commande en cours
	 * @param prix le nouveau total
	 * @throws SQLException the SQL exception
	 */
	public void majPrix(Commande c, double prix) throws SQLException {
		PreparedStatement requete = conn.prepareStatement("UPDATE `commande` SET prixTotal= ? WHERE CommandeID = ?");
		requete.setDouble(1, prix);
		requete.setInt(2, c.getIdCommande());
		requete.execute();
	}
	
	/**
	 * Affiche le nombre total de produits commandés.
	 *
	 * @param c the c
	 * @return le nombre total de produits
	 * @throws SQLException the SQL exception
	 */
	public int afficherQteTotal(Commande c) throws SQLException {
		ResultSet qteTotal = conn.createStatement().executeQuery("SELECT sum(qte) FROM `contenirproduit` WHERE CommandeID = '"+c.getIdCommande()+"'");
		qteTotal.next();
		int qte = qteTotal.getInt("sum(qte)");
		return qte;
	}
	
	/**
	 * Maj devise.
	 *
	 * @param devise the devise
	 * @throws SQLException the SQL exception
	 */
	/*
	 * Met à jour la devise lorsqu'une nouvelle devise en entrée dans les paramètres
	 * @param devise la devise entrée
	 */
	public void majDevise(String devise) throws SQLException {
		PreparedStatement maj = conn.prepareStatement("UPDATE preferences SET devise = ? WHERE PreferenceID = 1");
		maj.setString(1, devise);
		maj.execute();
	}
	
	/**
	 * Recupère la devise dans la base de donnée pour l'afficher a coté des prix.
	 *
	 * @return la devise
	 * @throws SQLException the SQL exception
	 */
	public static String recupererDevise() throws SQLException{
		String devise = null;
		ResultSet deviseDB = conn.createStatement().executeQuery("select devise from preferences");
		while (deviseDB.next()) {
			devise = deviseDB.getString("devise");
			}
		return devise;
	}
	
	/**
	 * Recupère l'ID de la prochaine commande.
	 *
	 * @return id
	 * @throws SQLException the SQL exception
	 */
	public int recupererID() throws SQLException {
		int id = 0;
		ResultSet commandeDB = conn.createStatement().executeQuery("select count(*) from commande");
		while (commandeDB.next()) {
			id=commandeDB.getInt(1);
			id++;
		}
		return id;
	}
	
	/**
	 * Ajoute une nouvelle commande.
	 *
	 * @param c the c
	 * @throws SQLException the SQL exception
	 */
	@Override
	public void ajouter(Commande c) throws SQLException {
		PreparedStatement ajout = conn.prepareStatement("INSERT INTO `commande` (`CommandeID`, `noTable`, `prixTotal`, `nbCouverts`, `date`) VALUES (?, ?, NULL, ?, current_timestamp())");
		ajout.setInt(1, c.getIdCommande());
		ajout.setInt(2, c.getNoTable());
		ajout.setInt(3, c.getNbCouverts());
		ajout.execute();
		
	}

	/**
	 * Supprimer.
	 *
	 * @param c the c
	 * @throws SQLException the SQL exception
	 */
	@Override
	public void supprimer(Commande c) throws SQLException {
	}
	
	/**
	 * Supprime un produit commandé dans la commande en cours.
	 *
	 * @param c la commande en cours
	 * @param p le produit à supprimer
	 * @throws SQLException the SQL exception
	 */
	public void supprimer(Commande c, Produit p) throws SQLException {
		PreparedStatement suppression = conn.prepareStatement("DELETE FROM `contenirproduit` WHERE `contenirproduit`.`ProduitID` = ? AND `contenirproduit`.`CommandeID` = "+c.getIdCommande());
		suppression.setInt(1, (p.getIdProduit()));
		suppression.execute();
		
	}

	/**
	 * Modifier.
	 *
	 * @param c the c
	 * @throws SQLException the SQL exception
	 */
	@Override
	public void modifier(Commande c) throws SQLException {
		
	}
	
	/**
	 * Affiche le total de la commande (prix total, quantité totale, nombre de produit total, le reste a payer).
	 *
	 * @param c la commande en cours
	 * @return une liste contenant 4 valeurs
	 * @throws SQLException the SQL exception
	 */
	public ArrayList<String> afficherAddition(Commande c) throws SQLException {
		ArrayList<String> addition = new ArrayList<>();
		ResultSet commandeDB = conn.createStatement().executeQuery("SELECT sum(contenirproduit.qte), count(contenirproduit.ProduitID), contenirproduit.ProduitID, commande.CommandeID, contenirproduit.qte, sum(produit.prix), commande.Reste_A_Payer FROM `contenirproduit` INNER JOIN produit on contenirproduit.ProduitID = produit.ProduitID inner join commande on contenirproduit.CommandeID = commande.CommandeID where commande.CommandeID = "+c.getIdCommande());
		while (commandeDB.next()) {
			int qte = commandeDB.getInt("contenirproduit.qte");
			double tprix = commandeDB.getDouble("sum(produit.prix)")*qte;
			int totalprdt = commandeDB.getInt("count(contenirproduit.ProduitID)")*qte;
 
			addition.add(Integer.toString(qte));
			addition.add(Double.toString(tprix));
			addition.add(Integer.toString(totalprdt));
			addition.add(commandeDB.getString("commande.Reste_A_Payer"));
		}
		return addition;
	}
	
	/**
	 * Met a jour le total a payer dans la commande en cours.
	 *
	 * @param c la commande en cours
	 * @param prix the prix
	 * @throws SQLException the SQL exception
	 */
	public void majTotalAPayer(Commande c, double prix) throws SQLException {
		PreparedStatement requete = conn.prepareStatement("UPDATE `commande` SET `prixTotal` = ?, `Reste_A_Payer` = ?  WHERE `commande`.`CommandeID` = ?");
		requete.setDouble(1, prix);
		requete.setDouble(2, prix);
		requete.setInt(3, c.getIdCommande());
		requete.execute();

	}
	
	/**
	 * Met à jour le prix lorsqu'un client paye qu'une partie de l'addition.
	 *
	 * @param c the c
	 * @param reste the reste
	 * @throws SQLException the SQL exception
	 */
	public void majPaiement(Commande c, double reste) throws SQLException {
		PreparedStatement requete = conn.prepareStatement("UPDATE `commande` SET `Reste_A_Payer` = ? WHERE `commande`.`CommandeID`= ?");
		requete.setDouble(1, reste);
		requete.setInt(2, c.getIdCommande());
		requete.execute();
	}
	
	/**
	 * Affiche le reste à payer dans la commande en cours.
	 *
	 * @param c la commande en cours
	 * @return le reste a payer
	 * @throws SQLException the SQL exception
	 */
	public double afficherRendu(Commande c) throws SQLException {
		double rendu = 0.00;
		ResultSet commandeDB = conn.createStatement().executeQuery("SELECT Reste_A_Payer from commande where CommandeID = "+c.getIdCommande());
		while (commandeDB.next()) {
			rendu = commandeDB.getDouble("Reste_A_Payer");
		}
		return rendu;
	}
	
	/**
	 * Affiche l'ensemble des produits commandés, leurs prix et leurs quantité dans un tableau a deux dimensions.
	 *
	 * @param c la commande en cours
	 * @return un tableau a deux dimensions
	 * @throws SQLException the SQL exception
	 */
	public ArrayList<ArrayList<String>> afficherProduitCommande(Commande c) throws SQLException {
		ArrayList<String> listeProduit = new ArrayList<>();
		ArrayList<String> listePrix = new ArrayList<>();
		ArrayList<String> listeQte = new ArrayList<>();
		ArrayList<ArrayList<String> > liste = new ArrayList<ArrayList<String>>();
		ResultSet produitCommandeDB = conn.createStatement().executeQuery(
				"SELECT produit.nom, produit.prix, contenirproduit.qte from contenirproduit "
				+ "INNER JOIN produit on contenirproduit.ProduitID = produit.ProduitID "
				+ "WHERE contenirproduit.CommandeID ="+c.getIdCommande());
		while(produitCommandeDB.next()) {
			listeProduit.add(produitCommandeDB.getString("produit.nom"));
			listePrix.add(produitCommandeDB.getString("produit.prix"));
			listeQte.add(produitCommandeDB.getString("contenirproduit.qte"));
		}
		liste.add(listeProduit);
		liste.add(listePrix);
		liste.add(listeQte);
		return liste;
	}
	
	/**
	 * Affiche un ticket avec toutes les informations sur la commande: <br>
	 * Numéro de table, nombre de couverts, date et heure, quantité, prix total, nom du serveur.
	 *
	 * @param c the c
	 * @return une liste d'informations
	 * @throws SQLException the SQL exception
	 */
	public ArrayList<String> afficherTicket(Commande c) throws SQLException{
		ArrayList<String> listeAddition = new ArrayList<>();
		ResultSet commandeDB = conn.createStatement().executeQuery(""
				+ "SELECT count(DISTINCT contenirproduit.ProduitID) as 'qte', sum(DISTINCT prixTotal) as 'prixTotal', substring(date,1,10) as 'date', substring(date,12,15) as 'heure', noTable, nbCouverts, serveurID FROM commande INNER JOIN contenirproduit on commande.CommandeID = contenirproduit.CommandeID where commande.CommandeID = "+c.getIdCommande());
		while (commandeDB.next()) {
			listeAddition.add(commandeDB.getString("noTable"));
			listeAddition.add(commandeDB.getString("nbCouverts"));
			listeAddition.add(commandeDB.getString("date"));
			listeAddition.add(commandeDB.getString("heure"));
			listeAddition.add(commandeDB.getString("qte"));
			listeAddition.add(commandeDB.getString("prixTotal"));
			listeAddition.add(commandeDB.getString("serveurID"));
		}
		return listeAddition;
	}
	
	/**
	 * Modifie la quantité de produit commandé lorsqu'un produit à été commandé plus d'une fois.
	 *
	 * @param idCommande la commande en cours
	 * @param idProduit l'identifiant du produit
	 * @param p l'objet produit en question
	 * @throws SQLException the SQL exception
	 */
	public void modifier(int idCommande, int idProduit, Produit p) throws SQLException {
		PreparedStatement requete = conn.prepareStatement("UPDATE `contenirproduit` SET qte= ? WHERE ProduitID = ? AND CommandeID = ?");
		
		requete.setInt(1, p.getQuantiteProduit());
		requete.setInt(2, idProduit);
		requete.setInt(3, idCommande);
		requete.execute();
	}
	
	/**
	 * Ajoute le produit à la commande lorsqu'un produit n'a jamais été commandé encore.
	 *
	 * @param idCommande la commande en cours
	 * @param idProduit l'identifiant du produit
	 * @param p l'objet produit en question
	 * @throws SQLException the SQL exception
	 */
	public void ajouterProduitCommande(int idCommande, int idProduit, Produit p) throws SQLException {
		PreparedStatement requete = conn.prepareStatement("INSERT INTO `contenirproduit` (`ProduitID`, `CommandeID`, `qte`, serveurID) VALUES (?, ?, 1, ?)");
		requete.setInt(1, idProduit);
		requete.setInt(2, idCommande);
		requete.setString(3, ConnexionControleur.getUtilisateurConnecte().getIdentifiant());
		requete.execute();
	}
	
	
	/*----------Historique des commandes--------------*/
	
	/**
	 * Affiche le nombre total de commandes, le prix total de l'ensemble des commandes et le nombre total de serveurs.
	 *
	 * @return une liste contenant ces 3 valeurs
	 * @throws SQLException the SQL exception
	 */
	public ArrayList<Integer> compterTout() throws SQLException{
		stats.clear();
		ResultSet count = conn.createStatement().executeQuery("SELECT count(DISTINCT commande.CommandeID), count(DISTINCT contenirproduit.serveurID), sum(DISTINCT prixTotal) FROM `commande` INNER JOIN contenirproduit ON commande.CommandeID = contenirproduit.CommandeID");
		if(!count.next()) {
			for(int i =0; i<3; i++) {
				stats.add(0);
			}
		}	
		else {
		count.last();
			stats.add(count.getInt("count(DISTINCT commande.CommandeID)"));
			stats.add(count.getInt("sum(DISTINCT prixTotal)"));
			stats.add(count.getInt("count(DISTINCT contenirproduit.serveurID)"));
		}
		return stats;
	}
	
	/**
	 * Affiche uniquement les commandes faites aujourd'hui.
	 *
	 * @throws SQLException the SQL exception
	 */
	public void trierParJour() throws SQLException{
		ResultSet commandeDB = conn.createStatement().executeQuery("SELECT commande.`CommandeID`, `noTable`, `prixTotal`, `nbCouverts`, `date`, contenirproduit.serveurID FROM `commande` INNER JOIN contenirproduit ON commande.CommandeID = contenirproduit.CommandeID WHERE CURRENT_DATE = substring(date, 1, 10) group by commande.CommandeID ORDER BY hour(date) desc, minute(date) desc ");
		while (commandeDB.next()) {
			listeCommande.add(new Commande(	commandeDB.getInt("commande.CommandeID"), 
											commandeDB.getInt("noTable"), 
											commandeDB.getInt("nbCouverts"), 
											commandeDB.getDouble("prixTotal"), 
											commandeDB.getString("date"), 
											commandeDB.getString("contenirproduit.serveurID")));
		}
	}

	/**
	 * Affiche le nombre total de commandes, le prix total de l'ensemble des commandes et le nombre total de serveurs pour aujourd'hui.
	 *
	 * @return une liste contenant ces 3 valeurs
	 * @throws SQLException the SQL exception
	 */
	public ArrayList<Integer> compterParJour() throws SQLException{
		stats.clear();
		ResultSet count = conn.createStatement().executeQuery("SELECT count(DISTINCT commande.CommandeID), count(DISTINCT contenirproduit.serveurID), sum(DISTINCT prixTotal) FROM `commande` INNER JOIN contenirproduit ON commande.CommandeID = contenirproduit.CommandeID WHERE CURRENT_DATE = substring(date, 1, 10) ORDER BY hour(date) desc, minute(date) desc ");
		if(!count.next()) {
			for(int i =0; i<3; i++) {
				stats.add(0);
			}
		}	
		else {
		count.last();
		stats.add(count.getInt("count(DISTINCT commande.CommandeID)"));
		stats.add(count.getInt("sum(DISTINCT prixTotal)"));
		stats.add(count.getInt("count(DISTINCT contenirproduit.serveurID)"));
		}
		return stats;
	}
	
	/**
	 * Affiche uniquement les commandes faites cette semaine.
	 *
	 * @throws SQLException the SQL exception
	 */
	public void trierParSemaine() throws SQLException{
		ResultSet commandeDB = conn.createStatement().executeQuery("SELECT commande.`CommandeID`, `noTable`, `prixTotal`, `nbCouverts`, `date`, contenirproduit.serveurID FROM `commande` INNER JOIN contenirproduit ON commande.CommandeID = contenirproduit.CommandeID WHERE week(date) = week(CURRENT_DATE) group by commande.CommandeID  order by hour(date) desc");
		while (commandeDB.next()) {
			listeCommande.add(new Commande(	commandeDB.getInt("commande.CommandeID"), 
											commandeDB.getInt("noTable"), 
											commandeDB.getInt("nbCouverts"), 
											commandeDB.getDouble("prixTotal"), 
											commandeDB.getString("date"), 
											commandeDB.getString("contenirproduit.serveurID")));
		}
	}
	
	/**
	 * Affiche le nombre total de commandes, le prix total de l'ensemble des commandes et le nombre total de serveurs pour cette semaine.
	 *
	 * @return une liste contenant ces 3 valeurs
	 * @throws SQLException the SQL exception
	 */
	public ArrayList<Integer> compterParSemaine() throws SQLException{
		stats.clear();
		ResultSet count = conn.createStatement().executeQuery("SELECT count(DISTINCT commande.CommandeID), count(DISTINCT contenirproduit.serveurID), sum(DISTINCT prixTotal) FROM `commande` INNER JOIN contenirproduit ON commande.CommandeID = contenirproduit.CommandeID WHERE week(date) = week(CURRENT_DATE) order by hour(date) desc");
		if(!count.next()) {
			for(int i =0; i<3; i++) {
				stats.add(0);
			}
		}	
		else {
		count.last();
		stats.add(count.getInt("count(DISTINCT commande.CommandeID)"));
		stats.add(count.getInt("sum(DISTINCT prixTotal)"));
		stats.add(count.getInt("count(DISTINCT contenirproduit.serveurID)"));
		}
		return stats;
	}
	
	/**
	 * Affiche uniquement les commandes faites ce mois-ci.
	 *
	 * @throws SQLException the SQL exception
	 */
	public void trierParMois() throws SQLException{
		ResultSet commandeDB = conn.createStatement().executeQuery("SELECT commande.`CommandeID`, `noTable`, `prixTotal`, `nbCouverts`, `date`, contenirproduit.serveurID FROM `commande` INNER JOIN contenirproduit ON commande.CommandeID = contenirproduit.CommandeID WHERE substring(CURRENT_DATE, 1, 7) = substring(date, 1, 7) group by commande.CommandeID ORDER BY DAY(date) desc"); 
		while (commandeDB.next()) {
			listeCommande.add(new Commande(	commandeDB.getInt("commande.CommandeID"), 
											commandeDB.getInt("noTable"), 
											commandeDB.getInt("nbCouverts"), 
											commandeDB.getDouble("prixTotal"), 
											commandeDB.getString("date"), 
											commandeDB.getString("contenirproduit.serveurID")));
		}
	}
	
	/**
	 * Affiche le nombre total de commandes, le prix total de l'ensemble des commandes et le nombre total de serveurs pour ce mois-ci.
	 *
	 * @return une liste contenant ces 3 valeurs
	 * @throws SQLException the SQL exception
	 */
	public ArrayList<Integer> compterParMois() throws SQLException{
		stats.clear();
		ResultSet count = conn.createStatement().executeQuery("SELECT count(DISTINCT commande.CommandeID), count(DISTINCT contenirproduit.serveurID), sum(DISTINCT prixTotal) FROM `commande` INNER JOIN contenirproduit ON commande.CommandeID = contenirproduit.CommandeID WHERE substring(CURRENT_DATE, 1, 7) = substring(date, 1, 7) ORDER BY DAY(date) desc"); 
		if(!count.next()) {
			for(int i =0; i<3; i++) {
				stats.add(0);
			}
		}	
		else {
		count.last();
		stats.add(count.getInt("count(DISTINCT commande.CommandeID)"));
		stats.add(count.getInt("sum(DISTINCT prixTotal)"));
		stats.add(count.getInt("count(DISTINCT contenirproduit.serveurID)"));
		}
		return stats;
	}
	
	/**
	 * Affiche uniquement les commandes faites cette année.
	 *
	 * @throws SQLException the SQL exception
	 */
	public void trierParAnnee() throws SQLException{
		ResultSet commandeDB = conn.createStatement().executeQuery("SELECT commande.`CommandeID`, `noTable`, `prixTotal`, `nbCouverts`, `date`, contenirproduit.serveurID FROM `commande` INNER JOIN contenirproduit ON commande.CommandeID = contenirproduit.CommandeID WHERE substring(CURRENT_DATE, 1, 4) = substring(date, 1, 4) group by commande.CommandeID  ORDER BY MONTH(date) desc, DAY(date) DESC"); 
		while (commandeDB.next()) {
			listeCommande.add(new Commande( commandeDB.getInt("commande.CommandeID"), 
											commandeDB.getInt("noTable"),
											commandeDB.getInt("nbCouverts"), 
											commandeDB.getDouble("prixTotal"), 
											commandeDB.getString("date"),
											commandeDB.getString("contenirproduit.serveurID")));
		}
	}
	
	/**
	 * Affiche le nombre total de commandes, le prix total de l'ensemble des commandes et le nombre total de serveurs pour l'année en cours.
	 *
	 * @return une liste contenant ces 3 valeurs
	 * @throws SQLException the SQL exception
	 */
	public ArrayList<Integer> compterParAnnee() throws SQLException{
		stats.clear();
		ResultSet count = conn.createStatement().executeQuery("SELECT count(DISTINCT commande.CommandeID), count(DISTINCT contenirproduit.serveurID), sum(DISTINCT prixTotal) FROM `commande` INNER JOIN contenirproduit ON commande.CommandeID = contenirproduit.CommandeID WHERE substring(CURRENT_DATE, 1, 4) = substring(date, 1, 4) ORDER BY MONTH(date) desc, DAY(date) DESC"); 
		if(!count.next()) {
			for(int i =0; i<3; i++) {
				stats.add(0);
			}
		}	
		else {
		count.last();
		stats.add(count.getInt("count(DISTINCT commande.CommandeID)"));
		stats.add(count.getInt("sum(DISTINCT prixTotal)"));
		stats.add(count.getInt("count(DISTINCT contenirproduit.serveurID)"));
		}
		return stats;
	}


}
