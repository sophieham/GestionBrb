package gestionbrb.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Table
{
	private IntegerProperty idTable;
	private IntegerProperty nbCouvertsMin;
	private StringProperty  demandeSpe;
	private Commande commande;
	private IntegerProperty nbCouvertsMax;
	private SimpleBooleanProperty estOccupe;
	
	public Table(int idTable, int nbCouvertsMin, int nbCouvertsMax, String demandeSpe, boolean estOccupe){
		this.idTable = new SimpleIntegerProperty(idTable);
		this.nbCouvertsMin = new SimpleIntegerProperty(nbCouvertsMin);
		this.nbCouvertsMax = new SimpleIntegerProperty(nbCouvertsMax);
		this.demandeSpe= new SimpleStringProperty(demandeSpe);
		this.estOccupe= new SimpleBooleanProperty(estOccupe);
	}

	public int getIdTable() {
		return idTable.get();
	}
	
	public void setIdTable(int idTable) {
		this.idTable.set(idTable);
	}
	
	public int getNbCouvertsMin() {
		return nbCouvertsMin.get();
	}
	
	public void setNbCouvertsMin(int nbCouvertsMin) {
		this.nbCouvertsMin.set(nbCouvertsMin);
	}


	public int getNbCouvertsMax() {
		return nbCouvertsMax.get();
	}

	public void setNbCouvertsMax(int nbCouvertsMax) {
		this.nbCouvertsMax.set(nbCouvertsMax);
	}
	
	public String getDemandeSpe() {
		return demandeSpe.get();
	}

	public void setDemandeSpe(String demandeSpe) {
		this.demandeSpe.set(demandeSpe);
	}
	
	public boolean getEstOccupe() {
		return estOccupe.get();
	}

	public void setEstOccupe(boolean estOccupe) {
		this.estOccupe.set(estOccupe);
	}
	
	
}

