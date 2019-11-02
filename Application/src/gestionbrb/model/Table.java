package gestionbrb.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Table {
	private final IntegerProperty idTable;
	private final IntegerProperty nbCouvertsMin;
	private IntegerProperty nbCouverts;
	private Commande commande;
	private final IntegerProperty nbCouvertsMax;
	private BooleanProperty estOccupe;

	public Table(int idTable, int nbCouvertsMax, boolean estOccupe) {
		this.idTable = new SimpleIntegerProperty(idTable);
		this.nbCouvertsMin = new SimpleIntegerProperty(1);
		this.nbCouvertsMax = new SimpleIntegerProperty(nbCouvertsMax);
		this.estOccupe = new SimpleBooleanProperty(estOccupe);
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

	public final IntegerProperty nbCouvertsProperty() {
		return this.nbCouverts;
	}

	public final int getNbCouverts() {
		return this.nbCouvertsProperty().get();
	}

	public final void setNbCouverts(final int nbCouverts) {
		this.nbCouvertsProperty().set(nbCouverts);
	}

	public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}

}
