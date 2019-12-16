package gestionbrb.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Tables composant le restaurant
 * @author Sophie
 *
 */
public class Table {
	private final IntegerProperty idTable;
	private final IntegerProperty nbCouvertsMin;
	private Commande commande;
	private final IntegerProperty nbCouvertsMax;
	private final IntegerProperty noTable;
	private IntegerProperty occupation; // D�finit l'occupation comme un nombre binaire (0= libre, 1= occup�)

	public Table(int idTable, int noTable, int nbCouvertsMin, int nbCouvertsMax, int occupation) {
		this.idTable = new SimpleIntegerProperty(idTable);
		this.nbCouvertsMin = new SimpleIntegerProperty(nbCouvertsMin);
		this.nbCouvertsMax = new SimpleIntegerProperty(nbCouvertsMax);
		this.noTable = new SimpleIntegerProperty(noTable);
		this.occupation = new SimpleIntegerProperty(occupation); 
	}

	public Table() {
		this(0, 0, 0, 0, 0);
	}

	public final IntegerProperty idTableProperty() {
		return this.idTable;
	}

	public final int getIdTable() {
		return this.idTableProperty().get();
	}

	public final void setIdTable(final int idTable) {
		this.idTableProperty().set(idTable);
	}

	public final IntegerProperty nbCouvertsMinProperty() {
		return this.nbCouvertsMin;
	}

	public final int getNbCouvertsMin() {
		return this.nbCouvertsMinProperty().get();
	}

	public final void setNbCouvertsMin(final int nbCouvertsMin) {
		this.nbCouvertsMinProperty().set(nbCouvertsMin);
	}

	public final IntegerProperty nbCouvertsMaxProperty() {
		return this.nbCouvertsMax;
	}

	public final int getNbCouvertsMax() {
		return this.nbCouvertsMaxProperty().get();
	}

	public final void setNbCouvertsMax(final int nbCouvertsMax) {
		this.nbCouvertsMaxProperty().set(nbCouvertsMax);
	}

	public final IntegerProperty occupationProperty() {
		return this.occupation;
	}

	public final int getOccupation() {
		return this.occupationProperty().get();
	}

	public final void setOccupation(final int occupation) {
		this.occupationProperty().set(occupation);
	}
	
	public final StringProperty occupationStrProperty() {
		StringProperty occupe = new SimpleStringProperty("Occupé");
		StringProperty libre = new SimpleStringProperty("Libre");
		if (getOccupation()==1)
			return occupe;
		else return libre;
	}

	public final int getOccupationStr() {
		return this.occupationProperty().get();
	}

	public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	public IntegerProperty NoTableProperty() {
		return noTable;
	}
	
	public final int getNoTable() {
		return this.NoTableProperty().get();
	}

	public final void setNoTable(int noTable) {
		this.NoTableProperty().set(noTable);
	}
	
	

}
