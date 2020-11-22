package gestionbrb.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import gestionbrb.model.Table;
import gestionbrb.util.bddUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// TODO: Auto-generated Javadoc
/**
 * Gère les requetes sql sur les tables.
 */
public class DAOTables extends DAO<Table> {
	
	/** The liste tables. */
	private ObservableList<Table> listeTables = FXCollections.observableArrayList();
	
	/** The liste tables libre. */
	private ObservableList<String> listeTablesLibre = FXCollections.observableArrayList();
	
	/** The liste no tables. */
	private ObservableList<String> listeNoTables = FXCollections.observableArrayList();
	
	/** The conn. */
	public static Connection conn = bddUtil.dbConnect();
	
	/**
	 * Afficher.
	 *
	 * @return the observable list
	 * @throws SQLException the SQL exception
	 */
	@Override
	public ObservableList<Table> afficher() throws SQLException {
		listeTables.clear();
		ResultSet TableDB = conn.createStatement().executeQuery("select * from tables");
		while (TableDB.next()) {
			listeTables.add(new Table(TableDB.getInt("TableID"), TableDB.getInt("noTable"), TableDB.getInt("nbCouverts_Min"), TableDB.getInt("nbCouverts_Max"), TableDB.getInt("occupation")));
		}
		return listeTables;
	}
	
	/**
	 * Afficher no tables.
	 *
	 * @return the observable list
	 * @throws SQLException the SQL exception
	 */
	public ObservableList<String> afficherNoTables() throws SQLException {
		listeNoTables.clear();
		ResultSet TableDB = conn.createStatement().executeQuery("select * from tables");
		while (TableDB.next()) {
			listeNoTables.add("Table n°" + TableDB.getInt(2) + " [" + TableDB.getInt(3) + " à " + TableDB.getInt(4) + " couverts]");
		}
		return listeNoTables;
	}
	
	/**
	 * Afficher tables libres.
	 *
	 * @return the observable list
	 * @throws SQLException the SQL exception
	 */
	public ObservableList<String> afficherTablesLibres() throws SQLException {
		listeTablesLibre.clear();
		ResultSet tableDB = conn.createStatement().executeQuery("select * from tables");
		while(tableDB.next()) {
			if (tableDB.getInt("occupation") == 0) {
				listeTablesLibre.add("Table n°" + tableDB.getInt(2) + " [" + tableDB.getInt(3) + " à " + tableDB.getInt(4) + " couverts]");

			}
		}
		return listeTablesLibre;
	}

	/**
	 * Ajouter.
	 *
	 * @param t the t
	 * @throws SQLException the SQL exception
	 */
	@Override
	public void ajouter(Table t) throws SQLException {
		PreparedStatement ajout = conn.prepareStatement
				("INSERT INTO `tables` (`TableID`, `NoTable`, `nbCouverts_min`, `nbCouverts_max`, `ReservationID`) VALUES (NULL, ?, ?, ?, NULL)");
		ajout.setInt(1, t.getNoTable());
		ajout.setInt(2, t.getNbCouvertsMin());
		ajout.setInt(3, t.getNbCouvertsMax());
		ajout.execute();
		
	}

	/**
	 * Supprimer.
	 *
	 * @param t the t
	 * @throws SQLException the SQL exception
	 */
	@Override
	public void supprimer(Table t) throws SQLException {
		PreparedStatement tables = conn.prepareStatement("DELETE FROM `tables` WHERE TableID=?");
		tables.setInt(1, (t.getIdTable()));
		tables.execute();
		
	}

	/**
	 * Modifier.
	 *
	 * @param t the t
	 * @throws SQLException the SQL exception
	 */
	@Override
	public void modifier(Table t) throws SQLException {
		PreparedStatement supprimer = conn.prepareStatement("UPDATE `tables` SET `NoTable` = ?, `nbCouverts_Min`= ?, `nbCouverts_Max`= ?, `ReservationID` = NULL WHERE `TableID`= ?");
		supprimer.setInt(1, t.getNoTable());
		supprimer.setInt(2, t.getNbCouvertsMin());
		supprimer.setInt(3, t.getNbCouvertsMax());
		supprimer.setInt(4, t.getIdTable());
		supprimer.execute();
	}
	
	/**
	 * Modifier occupation.
	 *
	 * @param numeroTable the numero table
	 * @param occupation the occupation
	 * @throws SQLException the SQL exception
	 */
	public void modifierOccupation(int numeroTable, int occupation) throws SQLException{
		PreparedStatement modifierOccupation = conn.prepareStatement("UPDATE `tables` SET occupation = ? WHERE noTable=?");
		modifierOccupation.setInt(1, occupation);
		modifierOccupation.setInt(2, numeroTable);
		modifierOccupation.execute();
	}
	

}
