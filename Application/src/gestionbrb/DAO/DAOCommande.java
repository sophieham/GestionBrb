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

public class DAOCommande extends DAO<Commande>{
	private ArrayList<Integer> stats = new ArrayList<>();
	private ObservableList<Commande> listeCommande = FXCollections.observableArrayList();
	public static Connection conn = bddUtil.dbConnect();
	
	@Override
	public ObservableList<Commande> afficher() throws SQLException {
		ResultSet commandeDB = conn.createStatement().executeQuery("SELECT commande.`CommandeID`, `noTable`, `prixTotal`, `nbCouverts`, `date`, contenirproduit.serveurID FROM `commande` INNER JOIN contenirproduit ON commande.CommandeID = contenirproduit.CommandeID group by commande.CommandeID order by year(date) desc, month(date) desc, day(date) desc");
		while (commandeDB.next()) {
			listeCommande.add(new Commande(	commandeDB.getInt("CommandeID"), 
											commandeDB.getInt("noTable"), 
											commandeDB.getInt("nbCouverts"), 
											commandeDB.getFloat("prixTotal"), 
											commandeDB.getString("date"), 
											commandeDB.getString("serveurID")));
		}
		return listeCommande;
	}
	
	
	public float afficherPrixTotal(Commande c) throws SQLException {
		float prixTotal = 0;
		ResultSet commandeDB = conn.createStatement().executeQuery("SELECT sum(contenirproduit.qte*produit.prix) as prixt FROM `contenirproduit` INNER JOIN produit on contenirproduit.ProduitID = produit.ProduitID WHERE CommandeID = "+c.getIdCommande());
		while (commandeDB.next()) {
		prixTotal = Float.parseFloat(commandeDB.getString("prixt"));
		}
		return prixTotal;
	}
	
	public void majPrix(Commande c, float prix) throws SQLException {
		PreparedStatement requete = conn.prepareStatement("UPDATE `commande` SET prixTotal= ? WHERE CommandeID = ?");
		requete.setFloat(1, prix);
		requete.setInt(2, c.getIdCommande());
		requete.execute();
	}
	
	public int afficherQteTotal(Commande c) throws SQLException {
		ResultSet qteTotal = conn.createStatement().executeQuery("SELECT sum(qte) FROM `contenirproduit` WHERE CommandeID = '"+c.getIdCommande()+"'");
		qteTotal.next();
		int qte = qteTotal.getInt("sum(qte)");
		return qte;
	}
	
	public String recupererDevise() throws SQLException{
		String devise = null;
		ResultSet deviseDB = conn.createStatement().executeQuery("select devise from preferences");
		while (deviseDB.next()) {
			devise = deviseDB.getString("devise");
			}
		return devise;
	}
	
	public int recupererID() throws SQLException {
		int id = 0;
		ResultSet commandeDB = conn.createStatement().executeQuery("select count(*) from commande");
		while (commandeDB.next()) {
			id=commandeDB.getInt(1);
			id++;
		}
		return id;
	}
	

	@Override
	public void ajouter(Commande c) throws SQLException {
		PreparedStatement ajout = conn.prepareStatement("INSERT INTO `commande` (`CommandeID`, `noTable`, `prixTotal`, `nbCouverts`, `date`) VALUES (?, ?, NULL, ?, current_timestamp())");
		ajout.setInt(1, c.getIdCommande());
		ajout.setInt(2, c.getNoTable());
		ajout.setInt(3, c.getNbCouverts());
		ajout.execute();
		
	}

	@Override
	public void supprimer(Commande c) throws SQLException {
		
		
	}
	
	public void supprimer(Commande c, Produit p) throws SQLException {
		PreparedStatement suppression = conn.prepareStatement("DELETE FROM `contenirproduit` WHERE `contenirproduit`.`ProduitID` = ? AND `contenirproduit`.`CommandeID` = "+c.getIdCommande());
		suppression.setInt(1, (p.getIdProduit()));
		suppression.execute();
		
	}

	@Override
	public void modifier(Commande c) throws SQLException {
		
	}
	
	public ArrayList<String> afficherAddition(Commande c) throws SQLException {
		ArrayList<String> addition = new ArrayList<>();
		ResultSet commandeDB = conn.createStatement().executeQuery("SELECT sum(contenirproduit.qte), count(contenirproduit.ProduitID), contenirproduit.ProduitID, commande.CommandeID, contenirproduit.qte, sum(produit.prix), commande.Reste_A_Payer FROM `contenirproduit` INNER JOIN produit on contenirproduit.ProduitID = produit.ProduitID inner join commande on contenirproduit.CommandeID = commande.CommandeID where commande.CommandeID = "+c.getIdCommande());
		while (commandeDB.next()) {
			int qte = commandeDB.getInt("contenirproduit.qte");
			float tprix = commandeDB.getFloat("sum(produit.prix)")*qte;
			int totalprdt = commandeDB.getInt("count(contenirproduit.ProduitID)")*qte;
 
			addition.add(Integer.toString(qte));
			addition.add(Float.toString(tprix));
			addition.add(Integer.toString(totalprdt));
			addition.add(commandeDB.getString("commande.Reste_A_Payer"));
		}
		return addition;
	}
	
	public void majTotalAPayer(Commande c, float prix) throws SQLException {
		PreparedStatement requete = conn.prepareStatement("UPDATE `commande` SET `prixTotal` = ?, `Reste_A_Payer` = ?  WHERE `commande`.`idCommande` = ?");
		System.out.println(requete);
		requete.setFloat(1, prix);
		requete.setFloat(2, prix);
		requete.setInt(3, c.getIdCommande());
		requete.execute();

	}
	
	public void majPaiement(Commande c, float reste) throws SQLException {
		PreparedStatement requete = conn.prepareStatement("UPDATE `commande` SET `Reste_A_Payer` = ? WHERE `commande`.`idCommande`= ?");
		requete.setFloat(1, reste);
		requete.setInt(2, c.getIdCommande());
		requete.execute();
	}
	
	public double afficherRendu(Commande c) throws SQLException {
		double rendu = 0.00;
		ResultSet commandeDB = conn.createStatement().executeQuery("SELECT Reste_A_Payer from commande where idCommande = "+c.getIdCommande());
		while (commandeDB.next()) {
			rendu = commandeDB.getDouble("Reste_A_Payer");
		}
		return rendu;
	}
	
	public ArrayList<ArrayList<String>> afficherProduitCommande(Commande c) throws SQLException {
		ArrayList<String> listeProduit = new ArrayList<>();
		ArrayList<String> listePrix = new ArrayList<>();
		ArrayList<String> listeQte = new ArrayList<>();
		ArrayList<ArrayList<String> > liste = new ArrayList<ArrayList<String>>();
		ResultSet produitCommandeDB = conn.createStatement().executeQuery(
				"SELECT produit.nom, produit.prix, contenirproduit.qte from contenirproduit "
				+ "INNER JOIN produit on contenirproduit.ProduitID = produit.ProduitID "
				+ "WHERE contenirproduit.idCommande ="+c.getIdCommande());
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
	
	public ArrayList<String> afficherTicket(Commande c) throws SQLException{
		ArrayList<String> listeAddition = new ArrayList<>();
		ResultSet commandeDB = conn.createStatement().executeQuery(""
				+ "SELECT count(DISTINCT contenirproduit.ProduitID) as 'qte', sum(DISTINCT prixTotal) as 'prixTotal', substring(date,1,10) as 'date', substring(date,12,15) as 'heure', noTable, nbCouverts, serveurID FROM commande INNER JOIN contenirproduit on commande.idCommande = contenirproduit.idCommande where commande.idcommande = "+c.getIdCommande());
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
	public void modifier(int idCommande, int idProduit, Produit p) throws SQLException {
		PreparedStatement requete = conn.prepareStatement("UPDATE `contenirproduit` SET qte= ? WHERE ProduitID = ? AND idCommande = ?");
		
		requete.setInt(1, p.getQuantiteProduit());
		requete.setInt(2, idProduit);
		requete.setInt(3, idCommande);
		requete.execute();
	}
	
	public void ajouterProduitCommande(int idCommande, int idProduit, Produit p) throws SQLException {
		PreparedStatement requete = conn.prepareStatement("INSERT INTO `contenirproduit` (`ProduitID`, `CommandeID`, `qte`, serveurID) VALUES (?, ?, 1, ?)");
		requete.setInt(1, idProduit);
		requete.setInt(2, idCommande);
		requete.setString(3, ConnexionControleur.getUtilisateurConnecte().getIdentifiant());
		requete.execute();
	}
	
	
	/*----------------------------------*/
	
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
	
	public void trierParJour() throws SQLException{
		ResultSet commandeDB = conn.createStatement().executeQuery("SELECT commande.`CommandeID`, `noTable`, `prixTotal`, `nbCouverts`, `date`, contenirproduit.serveurID FROM `commande` INNER JOIN contenirproduit ON commande.CommandeID = contenirproduit.CommandeID WHERE CURRENT_DATE = substring(date, 1, 10) group by commande.CommandeID ORDER BY hour(date) desc, minute(date) desc ");
		while (commandeDB.next()) {
			listeCommande.add(new Commande(	commandeDB.getInt("commande.CommandeID"), 
											commandeDB.getInt("noTable"), 
											commandeDB.getInt("nbCouverts"), 
											commandeDB.getFloat("prixTotal"), 
											commandeDB.getString("date"), 
											commandeDB.getString("contenirproduit.serveurID")));
		}
	}
	
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
	
	public void trierParSemaine() throws SQLException{
		ResultSet commandeDB = conn.createStatement().executeQuery("SELECT commande.`CommandeID`, `noTable`, `prixTotal`, `nbCouverts`, `date`, contenirproduit.serveurID FROM `commande` INNER JOIN contenirproduit ON commande.CommandeID = contenirproduit.CommandeID WHERE week(date) = week(CURRENT_DATE) group by commande.CommandeID  order by hour(date) desc");
		while (commandeDB.next()) {
			listeCommande.add(new Commande(	commandeDB.getInt("commande.CommandeID"), 
											commandeDB.getInt("noTable"), 
											commandeDB.getInt("nbCouverts"), 
											commandeDB.getFloat("prixTotal"), 
											commandeDB.getString("date"), 
											commandeDB.getString("contenirproduit.serveurID")));
		}
	}
	
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
	
	public void trierParMois() throws SQLException{
		ResultSet commandeDB = conn.createStatement().executeQuery("SELECT commande.`CommandeID`, `noTable`, `prixTotal`, `nbCouverts`, `date`, contenirproduit.serveurID FROM `commande` INNER JOIN contenirproduit ON commande.CommandeID = contenirproduit.CommandeID WHERE substring(CURRENT_DATE, 1, 7) = substring(date, 1, 7) group by commande.CommandeID ORDER BY DAY(date) desc"); 
		while (commandeDB.next()) {
			listeCommande.add(new Commande(	commandeDB.getInt("commande.CommandeID"), 
											commandeDB.getInt("noTable"), 
											commandeDB.getInt("nbCouverts"), 
											commandeDB.getFloat("prixTotal"), 
											commandeDB.getString("date"), 
											commandeDB.getString("contenirproduit.serveurID")));
		}
	}
	
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
	
	public void trierParAnnee() throws SQLException{
		ResultSet commandeDB = conn.createStatement().executeQuery("SELECT commande.`CommandeID`, `noTable`, `prixTotal`, `nbCouverts`, `date`, contenirproduit.serveurID FROM `commande` INNER JOIN contenirproduit ON commande.CommandeID = contenirproduit.CommandeID WHERE substring(CURRENT_DATE, 1, 4) = substring(date, 1, 4) group by commande.CommandeID  ORDER BY MONTH(date) desc, DAY(date) DESC"); 
		while (commandeDB.next()) {
			listeCommande.add(new Commande( commandeDB.getInt("commande.CommandeID"), 
											commandeDB.getInt("noTable"),
											commandeDB.getInt("prixTotal"), 
											commandeDB.getFloat("nbCouverts"), 
											commandeDB.getString("date"),
											commandeDB.getString("contenirproduit.serveurID")));
		}
	}
	
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
