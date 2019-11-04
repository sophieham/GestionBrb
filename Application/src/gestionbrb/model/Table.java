package gestionbrb.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Table {
	private final IntegerProperty idTable;
	private final IntegerProperty nbCouvertsMin;
	private Commande commande;
	private final IntegerProperty nbCouvertsMax;
	private final IntegerProperty noTable;
	private BooleanProperty estOccupe;

	public Table(int idTable, int noTable, int nbCouvertsMin, int nbCouvertsMax, boolean estOccupe) {
		this.idTable = new SimpleIntegerProperty(idTable);
		this.nbCouvertsMin = new SimpleIntegerProperty(nbCouvertsMin);
		this.nbCouvertsMax = new SimpleIntegerProperty(nbCouvertsMax);
		this.noTable = new SimpleIntegerProperty(noTable);
		this.estOccupe = new SimpleBooleanProperty(estOccupe);
	}

	public Table() {
		this(0, 0, 0, 0, false);
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

	public final BooleanProperty estOccupeProperty() {
		return this.estOccupe;
	}

	public final boolean isEstOccupe() {
		return this.estOccupeProperty().get();
	}

	public final void setEstOccupe() {
		this.estOccupeProperty().set(!this.estOccupeProperty().get());
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
