package gestionbrb.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import gestionbrb.model.Table;
import gestionbrb.util.bddUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAOTables extends DAO<Table> {
	private ObservableList<Table> listeTables = FXCollections.observableArrayList();
	private ObservableList<String> listeTablesLibre = FXCollections.observableArrayList();
	private ObservableList<String> listeNoTables = FXCollections.observableArrayList();
	public static Connection conn = bddUtil.dbConnect();
	
	@Override
	public ObservableList<Table> afficher() throws SQLException {
		listeTables.clear();
		ResultSet TableDB = conn.createStatement().executeQuery("select * from tables");
		while (TableDB.next()) {
			listeTables.add(new Table(TableDB.getInt("idTable"), TableDB.getInt("noTable"), TableDB.getInt("nbCouverts_Min"), TableDB.getInt("nbCouverts_Max"), TableDB.getInt("occupation")));
		}
		return listeTables;
	}
	
	public ObservableList<String> afficherNoTables() throws SQLException {
		listeNoTables.clear();
		ResultSet TableDB = conn.createStatement().executeQuery("select * from tables");
		while (TableDB.next()) {
			listeNoTables.add("Table n°" + TableDB.getInt(2) + " [" + TableDB.getInt(3) + " à " + TableDB.getInt(4) + " couverts]");
		}
		return listeNoTables;
	}
	
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

	@Override
	public void ajouter(Table t) throws SQLException {
		PreparedStatement ajout = conn.prepareStatement
				("INSERT INTO `tables` (`idTable`, `NoTable`, `nbCouverts_min`, `nbCouverts_max`, `idReservation`) VALUES (NULL, ?, ?, ?, NULL)");
		ajout.setInt(1, t.getNoTable());
		ajout.setInt(2, t.getNbCouvertsMin());
		ajout.setInt(3, t.getNbCouvertsMax());
		ajout.execute();
		
	}

	@Override
	public void supprimer(Table t) throws SQLException {
		PreparedStatement tables = conn.prepareStatement("DELETE FROM `tables` WHERE idTable=?");
		tables.setInt(1, (t.getIdTable()));
		tables.execute();
		
	}

	@Override
	public void modifier(Table t) throws SQLException {
		PreparedStatement supprimer = conn.prepareStatement("UPDATE `tables` SET `NoTable` = ?, `nbCouverts_Min`= ?, `nbCouverts_Max`= ?, `idReservation` = NULL WHERE `idTable`= ?");
		supprimer.setInt(1, t.getNoTable());
		supprimer.setInt(2, t.getNbCouvertsMin());
		supprimer.setInt(3, t.getNbCouvertsMax());
		supprimer.setInt(4, t.getIdTable());
		supprimer.execute();
	}
	
	public void modifierOccupation(int numeroTable) throws SQLException{
		PreparedStatement occupation = conn.prepareStatement("UPDATE `tables` SET occupation = ? WHERE noTable=?");
		occupation.setInt(1, 1);
		occupation.setInt(2, numeroTable);
		occupation.execute();
	}
	

}
