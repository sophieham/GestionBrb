package gestionbrb.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import gestionbrb.model.Reservations;
import gestionbrb.util.bddUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAOCalendrier extends DAO<Reservations> {
	private ObservableList<Reservations> reservationData = FXCollections.observableArrayList();
	public static Connection conn = bddUtil.dbConnect();

	@Override
	public ObservableList<Reservations> afficher() throws SQLException {
		ResultSet calendrierDB = conn.createStatement().executeQuery("select * from calendrier");
		while (calendrierDB.next()) {
			reservationData.add(new Reservations(calendrierDB.getInt("idReservation"), 
															 calendrierDB.getString("nom"), 
															 calendrierDB.getString("prenom"),
															 calendrierDB.getString("numeroTel"), 
															 calendrierDB.getString("dateReservation"),
															 calendrierDB.getString("heureReservation"), 
															 calendrierDB.getInt("nbCouverts"), 
															 calendrierDB.getString("demandeSpe")));
		}
		return reservationData;
		
	}

	public Reservations afficherReservation() throws SQLException {
		ResultSet calendrierDB = conn.createStatement().executeQuery("select * from calendrier");
		Reservations resv = new Reservations();
		while (calendrierDB.next()) {
			resv.setID(calendrierDB.getInt("idReservation"));
			resv.setNom(calendrierDB.getString("nom"));
			resv.setPrenom(calendrierDB.getString("prenom"));
			resv.setNumTel(calendrierDB.getString("numeroTel"));
			resv.setDate(LocalDate.parse(calendrierDB.getString("dateReservation")));
			resv.setHeure(calendrierDB.getString("heureReservation"));
			resv.setNbCouverts(calendrierDB.getInt("nbCouverts"));
			resv.setDemandeSpe(calendrierDB.getString("demandeSpe"));
		}
		return resv;
		
	}

	
	public void ajouter(Reservations r, int noTable) throws SQLException {
		PreparedStatement ajout = 
				conn.prepareStatement("INSERT INTO `calendrier` (`idReservation`, `nom`, `prenom`, `numeroTel`, `dateReservation`, `heureReservation`, `nbCouverts`, `demandeSpe`, `noTable`) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?)");
		ajout.setString(1, r.getNom());
		ajout.setString(2, r.getPrenom());
		ajout.setString(3, r.getNumTel());
		ajout.setString(4, r.getDate());
		ajout.setString(5, r.getHeure());
		ajout.setInt(6, r.getNbCouverts());
		ajout.setString(7, r.getDemandeSpe());
		ajout.setInt(8, noTable);
		System.out.println(ajout);
		ajout.execute();
		
	}

	@Override
	public void supprimer(Reservations r) throws SQLException {
		PreparedStatement suppression = conn.prepareStatement("DELETE FROM `calendrier` WHERE idReservation=?");
		suppression.setInt(1, (r.getID()));
		suppression.execute();
		
	}

	@Override
	public void modifier(Reservations r) {
		
		try {
			PreparedStatement ajout = conn.prepareStatement("UPDATE `calendrier` SET nom= ?, prenom = ?, numeroTel= ?, dateReservation= ?, heureReservation= ?, nbCouverts= ?, demandeSpe= ? WHERE idReservation = ?");
			ajout.setString(1, r.getNom());
			ajout.setString(2, r.getPrenom());
			ajout.setString(3, r.getNumTel());
			ajout.setString(4, r.getDate());
			ajout.setString(5, r.getHeure());
			ajout.setInt(6, r.getNbCouverts());
			ajout.setString(7, r.getDemandeSpe());
			ajout.setInt(8, r.getID());
			ajout.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String nombreTotalRsv(String date) throws SQLException {
		String nbTotalDate = null;
		ResultSet requete = conn.createStatement().executeQuery("select count(*) from calendrier where dateReservation LIKE '" + date + "'");
		while (requete.next()) {
			nbTotalDate = requete.getString("count(*)");
		}
		
		return nbTotalDate;
	}

	public String nombreTotalRsv() throws SQLException {
		String nbTotal = null;
		ResultSet requete = conn.createStatement().executeQuery("select count(*) from calendrier");
		while (requete.next()) {
			nbTotal = requete.getString("count(*)");
		}
		
		return nbTotal;
	}

	public void recherche(String date) throws SQLException {
		PreparedStatement recherche = conn.prepareStatement("select * from calendrier where dateReservation LIKE ?");
		recherche.setString(1, date);
		ResultSet calendrier = recherche.executeQuery();
		while (calendrier.next()) {
			reservationData.add(new Reservations(
							calendrier.getInt("idReservation"), 
							calendrier.getString("nom"), 
							calendrier.getString("prenom"),
							calendrier.getString("numeroTel"), 
							calendrier.getString("dateReservation"),
							calendrier.getString("heureReservation"), 
							calendrier.getInt("nbCouverts"), 
							calendrier.getString("demandeSpe")));
		}
		
	}


	@Override
	public void ajouter(Reservations objet) throws SQLException {
	}
}
