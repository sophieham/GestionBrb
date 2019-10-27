package gestionbrb.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Table
{
	private int idTable;
	private int nbCouvertsMin;
	private String  demandeSpe;
	private Commande commande;
	private int nbCouvertsMax;
	private boolean estOccupe;
	
	public Table(int idTable, int nbCouvertsMin, int nbCouvertsMax, String demandeSpe, boolean estOccupe){
		this.idTable =idTable;
		this.nbCouvertsMin = (nbCouvertsMin);
		this.nbCouvertsMax = (nbCouvertsMax);
		this.demandeSpe= (demandeSpe);
		this.estOccupe= (estOccupe);
	}

	public int getIdTable() {
		return idTable;
	}
	
	public void setIdTable(int idTable) {
		this.idTable = idTable;
	}
	
	public int getNbCouvertsMin() {
		return nbCouvertsMin;
	}
	
	public void setNbCouvertsMin(int nbCouvertsMin) {
		this.nbCouvertsMin=nbCouvertsMin;
	}


	public int getNbCouvertsMax() {
		return nbCouvertsMax;
	}

	public void setNbCouvertsMax(int nbCouvertsMax) {
		this.nbCouvertsMax=nbCouvertsMax;
	}
	
	public String getDemandeSpe() {
		return demandeSpe;
	}

	public void setDemandeSpe(String demandeSpe) {
		this.demandeSpe = demandeSpe;
	}
	
	public boolean getEstOccupe() {
		return estOccupe;
	}

	public void setEstOccupe(boolean estOccupe) {
		this.estOccupe = estOccupe;
	}
	
	
}

