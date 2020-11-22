package gestionbrb.DAO;

import java.sql.SQLException;

import javafx.collections.ObservableList;

// TODO: Auto-generated Javadoc
/**
 * The Class DAO.
 *
 * @param <T> the generic type
 */
/*
 * G�re les op�ration CRUD de mani�re g�n�rique
 */
public abstract class DAO<T> {
	
	/**
	 * Afficher.
	 *
	 * @return the observable list
	 * @throws SQLException the SQL exception
	 */
	public abstract ObservableList<T> afficher() throws SQLException;
	
	/**
	 * Ajouter.
	 *
	 * @param objet the objet
	 * @throws SQLException the SQL exception
	 */
	public abstract void ajouter(T objet) throws SQLException;
	
	/**
	 * Supprimer.
	 *
	 * @param objet the objet
	 * @throws SQLException the SQL exception
	 */
	public abstract void supprimer(T objet) throws SQLException;
	
	/**
	 * Modifier.
	 *
	 * @param objet the objet
	 * @throws SQLException the SQL exception
	 */
	public abstract void modifier(T objet) throws SQLException;
}
