package gestionbrb.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Table
{
	private final IntegerProperty idTable;
	private final IntegerProperty nbCouvertsMin;
	private final StringProperty  demandeSpe;
	private Commande commande;
	private final IntegerProperty nbCouvertsMax;
	private BooleanProperty estOccupe;
	
	public Table(int idTable, int nbCouvertsMin, int nbCouvertsMax, String demandeSpe, boolean estOccupe){
		this.idTable = new SimpleIntegerProperty(idTable);
		this.nbCouvertsMin = new SimpleIntegerProperty(nbCouvertsMin);
		this.nbCouvertsMax = new SimpleIntegerProperty(nbCouvertsMax);
		this.demandeSpe= new SimpleStringProperty(demandeSpe);
		this.estOccupe= new SimpleBooleanProperty(estOccupe);
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
	

	public final StringProperty demandeSpeProperty() {
		return this.demandeSpe;
	}
	

	public final String getDemandeSpe() {
		return this.demandeSpeProperty().get();
	}
	

	public final void setDemandeSpe(final String demandeSpe) {
		this.demandeSpeProperty().set(demandeSpe);
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
	

	public final void setEstOccupe(final boolean estOccupe) {
		this.estOccupeProperty().set(estOccupe);
	}
	

	
	
	
}

