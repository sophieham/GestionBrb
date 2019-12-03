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
			reservationData.add(new Reservations(calendrierDB.getInt("ReservationID"), 
															 calendrierDB.getString("Nom"), 
															 calendrierDB.getString("Prenom"),
															 calendrierDB.getString("Numero_Tel"), 
															 calendrierDB.getString("Date_Reservation"),
															 calendrierDB.getString("Heure_Reservation"), 
															 calendrierDB.getInt("Nombre_Couverts"), 
															 calendrierDB.getString("Demande_Speciale")));
		}
		return reservationData;
		
	}

	public Reservations afficherReservation() throws SQLException {
		ResultSet calendrierDB = conn.createStatement().executeQuery("select * from calendrier");
		Reservations resv = new Reservations();
		while (calendrierDB.next()) {
			resv.setID(calendrierDB.getInt("ReservationID"));
			resv.setNom(calendrierDB.getString("Nom"));
			resv.setPrenom(calendrierDB.getString("Prenom"));
			resv.setNumTel(calendrierDB.getString("Numero_Tel"));
			resv.setDate(LocalDate.parse(calendrierDB.getString("Date_Reservation")));
			resv.setHeure(calendrierDB.getString("Heure_Reservation"));
			resv.setNbCouverts(calendrierDB.getInt("Nombre_Couverts"));
			resv.setDemandeSpe(calendrierDB.getString("Demande_Speciale"));
		}
		return resv;
		
	}

	
	public void ajouter(Reservations r, int noTable) throws SQLException {
		PreparedStatement ajout = 
				conn.prepareStatement("INSERT INTO `calendrier` (`ReservationID`, `Nom`, `Prenom`, `Numero_Tel`, `Date_Reservation`, `Heure_Reservation`, `Nombre_Couverts`, `Demande_Speciale`, `noTable`) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?)");
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
		PreparedStatement suppression = conn.prepareStatement("DELETE FROM `calendrier` WHERE ReservationID=?");
		suppression.setInt(1, (r.getID()));
		suppression.execute();
		
	}

	@Override
	public void modifier(Reservations r) {
		
		try {
			PreparedStatement ajout = conn.prepareStatement("UPDATE `calendrier` SET Nom= ?, Prenom = ?, Numero_Tel= ?, Date_Reservation= ?, Heure_Reservation= ?, Nombre_Couverts= ?, Demande_Speciale= ? WHERE ReservationID = ?");
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
		ResultSet requete = conn.createStatement().executeQuery("select count(*) from calendrier where Date_Reservation LIKE '" + date + "'");
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
		PreparedStatement recherche = conn.prepareStatement("select * from calendrier where Date_Reservation LIKE ?");
		recherche.setString(1, date);
		ResultSet calendrier = recherche.executeQuery();
		while (calendrier.next()) {
			reservationData.add(new Reservations(
							calendrier.getInt("ReservationID"), 
							calendrier.getString("Nom"), 
							calendrier.getString("Prenom"),
							calendrier.getString("Numero_Tel"), 
							calendrier.getString("Date_Reservation"),
							calendrier.getString("Heure_Reservation"), 
							calendrier.getInt("Nombre_Couverts"), 
							calendrier.getString("Demande_Speciale")));
		}
		
	}


	@Override
	public void ajouter(Reservations objet) throws SQLException {
	}
}
