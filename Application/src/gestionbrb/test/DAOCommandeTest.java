package gestionbrb.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import gestionbrb.DAO.DAOCommande;
import gestionbrb.model.Commande;
import gestionbrb.model.Produit;
import gestionbrb.util.bddUtil;

class DAOCommandeTest {
	public static Connection conn = bddUtil.dbConnect();
	DAOCommande dao = new DAOCommande();

	@Test
	void testAfficher() throws SQLException {
		DAOCommande dao = new DAOCommande();
		PreparedStatement ajout = conn.prepareStatement(
				"INSERT INTO `commande` (`CommandeID`, `noTable`, `prixTotal`, `nbCouverts`, `date`) VALUES (59, 2, NULL, 3, current_timestamp())");
		ajout.execute();
		assertTrue(dao.afficher().contains(new Commande(59, 2, 3)));
	}

	@Test
	void testAfficherPrixTotal() throws SQLException {
		assertEquals(23.96, dao.afficherPrixTotal(new Commande(6, 0, 0)));
	}

	@Test
	void testMajPrix() throws SQLException {
		dao.majPrix(new Commande(6, 0, 0), 20);
		fail("Not yet implemented");
		ResultSet resultSet = conn.createStatement()
				.executeQuery("SELECT prixTotal FROM commande WHERE CommandeID = 6");
		double prixTotal = 0;
		while (resultSet.next()) {
			prixTotal = resultSet.getDouble(1);
		}
		assertEquals(20, prixTotal);
	}

	@Test
	void testAfficherQteTotal() throws SQLException {
		assertEquals(4, dao.afficherQteTotal(new Commande(36, 0, 0)));
	}

	@Test
	void testMajDevise() {
		fail("Not yet implemented");
	}

	@Test
	void testRecupererDevise() {
		fail("Not yet implemented");
	}

	@Test
	void testRecupererID() throws SQLException {
		assertEquals(60, dao.recupererID());
	}

	@Test
	void testAjouterCommande() throws SQLException {
		dao.ajouter(new Commande(60, 2, 2));
		assertTrue(dao.afficher().contains(new Commande(60, 2, 2)));
	}

	@Test
	void testSupprimerCommande() throws SQLException {
		dao.supprimer(new Commande(60, 0, 0));
		assertFalse(dao.afficher().contains(new Commande(60, 2, 2)));
	}

	@Test
	void testSupprimerCommandeProduit() throws SQLException {
		dao.supprimer(new Commande(36, 0, 0), new Produit(15, null, 0, null, 0, null, null));
		ResultSet resultSet = conn.createStatement().executeQuery("SELECT * FROM contenirproduit WHERE ProduitID = 15 AND CommandeID = 36");
		assertFalse(resultSet.next());
	}

	@Test
	void testModifierCommande() {
		fail("Not yet implemented");
	}

	@Test
	void testAfficherAddition() {
		fail("Not yet implemented");
	}

	@Test
	void testMajTotalAPayer() {
		fail("Not yet implemented");
	}

	@Test
	void testMajPaiement() {
		fail("Not yet implemented");
	}

	@Test
	void testAfficherRendu() {
		fail("Not yet implemented");
	}

	@Test
	void testAfficherProduitCommande() {
		fail("Not yet implemented");
	}

	@Test
	void testAfficherTicket() {
		fail("Not yet implemented");
	}

	@Test
	void testModifierIntIntProduit() {
		fail("Not yet implemented");
	}

	@Test
	void testAjouterProduitCommande() {
		fail("Not yet implemented");
	}

	@Test
	void testCompterTout() {
		fail("Not yet implemented");
	}

	@Test
	void testTrierParJour() {
		fail("Not yet implemented");
	}

	@Test
	void testCompterParJour() {
		fail("Not yet implemented");
	}

	@Test
	void testTrierParSemaine() {
		fail("Not yet implemented");
	}

	@Test
	void testCompterParSemaine() {
		fail("Not yet implemented");
	}

	@Test
	void testTrierParMois() {
		fail("Not yet implemented");
	}

	@Test
	void testCompterParMois() {
		fail("Not yet implemented");
	}

	@Test
	void testTrierParAnnee() {
		fail("Not yet implemented");
	}

	@Test
	void testCompterParAnnee() {
		fail("Not yet implemented");
	}

}
