package gestionbrb.DAO;

import java.sql.SQLException;

import javafx.collections.ObservableList;

public abstract class DAO<T> {
	public abstract ObservableList<T> afficher() throws SQLException;
	public abstract void ajouter(T objet) throws SQLException;
	public abstract void supprimer(T objet) throws SQLException;
	public abstract void modifier(T objet) throws SQLException;
}
