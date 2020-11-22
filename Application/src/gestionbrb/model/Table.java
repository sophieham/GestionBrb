package gestionbrb.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

// TODO: Auto-generated Javadoc
/**
 * Tables composant le restaurant.
 *
 * @author Sophie
 */
public class Table {
	
	/** The id table. */
	private final IntegerProperty idTable;
	
	/** The nb couverts min. */
	private final IntegerProperty nbCouvertsMin;
	
	/** The commande. */
	private Commande commande;
	
	/** The nb couverts max. */
	private final IntegerProperty nbCouvertsMax;
	
	/** The no table. */
	private final IntegerProperty noTable;
	
	/** The occupation. */
	private IntegerProperty occupation; // Définit l'occupation comme un nombre binaire (0= libre, 1= occupé)

	/**
	 * Instantiates a new table.
	 *
	 * @param idTable the id table
	 * @param noTable the no table
	 * @param nbCouvertsMin the nb couverts min
	 * @param nbCouvertsMax the nb couverts max
	 * @param occupation the occupation
	 */
	public Table(int idTable, int noTable, int nbCouvertsMin, int nbCouvertsMax, int occupation) {
		this.idTable = new SimpleIntegerProperty(idTable);
		this.nbCouvertsMin = new SimpleIntegerProperty(nbCouvertsMin);
		this.nbCouvertsMax = new SimpleIntegerProperty(nbCouvertsMax);
		this.noTable = new SimpleIntegerProperty(noTable);
		this.occupation = new SimpleIntegerProperty(occupation); 
	}

	/**
	 * Instantiates a new table.
	 */
	public Table() {
		this(0, 0, 0, 0, 0);
	}

	/**
	 * Id table property.
	 *
	 * @return the integer property
	 */
	public final IntegerProperty idTableProperty() {
		return this.idTable;
	}

	/**
	 * Gets the id table.
	 *
	 * @return the id table
	 */
	public final int getIdTable() {
		return this.idTableProperty().get();
	}

	/**
	 * Sets the id table.
	 *
	 * @param idTable the new id table
	 */
	public final void setIdTable(final int idTable) {
		this.idTableProperty().set(idTable);
	}

	/**
	 * Nb couverts min property.
	 *
	 * @return the integer property
	 */
	public final IntegerProperty nbCouvertsMinProperty() {
		return this.nbCouvertsMin;
	}

	/**
	 * Gets the nb couverts min.
	 *
	 * @return the nb couverts min
	 */
	public final int getNbCouvertsMin() {
		return this.nbCouvertsMinProperty().get();
	}

	/**
	 * Sets the nb couverts min.
	 *
	 * @param nbCouvertsMin the new nb couverts min
	 */
	public final void setNbCouvertsMin(final int nbCouvertsMin) {
		this.nbCouvertsMinProperty().set(nbCouvertsMin);
	}

	/**
	 * Nb couverts max property.
	 *
	 * @return the integer property
	 */
	public final IntegerProperty nbCouvertsMaxProperty() {
		return this.nbCouvertsMax;
	}

	/**
	 * Gets the nb couverts max.
	 *
	 * @return the nb couverts max
	 */
	public final int getNbCouvertsMax() {
		return this.nbCouvertsMaxProperty().get();
	}

	/**
	 * Sets the nb couverts max.
	 *
	 * @param nbCouvertsMax the new nb couverts max
	 */
	public final void setNbCouvertsMax(final int nbCouvertsMax) {
		this.nbCouvertsMaxProperty().set(nbCouvertsMax);
	}

	/**
	 * Occupation property.
	 *
	 * @return the integer property
	 */
	public final IntegerProperty occupationProperty() {
		return this.occupation;
	}

	/**
	 * Gets the occupation.
	 *
	 * @return the occupation
	 */
	public final int getOccupation() {
		return this.occupationProperty().get();
	}

	/**
	 * Sets the occupation.
	 *
	 * @param occupation the new occupation
	 */
	public final void setOccupation(final int occupation) {
		this.occupationProperty().set(occupation);
	}
	
	/**
	 * Occupation str property.
	 *
	 * @return the string property
	 */
	public final StringProperty occupationStrProperty() {
		StringProperty occupe = new SimpleStringProperty("Occupé");
		StringProperty libre = new SimpleStringProperty("Libre");
		if (getOccupation()==1)
			return occupe;
		else return libre;
	}

	/**
	 * Gets the occupation str.
	 *
	 * @return the occupation str
	 */
	public final int getOccupationStr() {
		return this.occupationProperty().get();
	}

	/**
	 * Gets the commande.
	 *
	 * @return the commande
	 */
	public Commande getCommande() {
		return commande;
	}

	/**
	 * Sets the commande.
	 *
	 * @param commande the new commande
	 */
	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	/**
	 * No table property.
	 *
	 * @return the integer property
	 */
	public IntegerProperty NoTableProperty() {
		return noTable;
	}
	
	/**
	 * Gets the no table.
	 *
	 * @return the no table
	 */
	public final int getNoTable() {
		return this.NoTableProperty().get();
	}

	/**
	 * Sets the no table.
	 *
	 * @param noTable the new no table
	 */
	public final void setNoTable(int noTable) {
		this.NoTableProperty().set(noTable);
	}
	
	

}
