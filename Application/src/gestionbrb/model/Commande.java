package gestionbrb.model;

import java.util.ArrayList;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 
 * @author Sophie
 *
 */
public class Commande 
{
	private IntegerProperty idCommande;
	private ArrayList<Produit> listeProduitCommande;
	private FloatProperty prixTotal;
	private IntegerProperty noTable;
	private IntegerProperty nbCouverts;
	private SimpleStringProperty date;
	private SimpleStringProperty serveur;

	public Commande(int id, int noTable, int nbCouverts){
		this.idCommande=new SimpleIntegerProperty(id);
		this.noTable = new SimpleIntegerProperty(noTable);
		this.nbCouverts = new SimpleIntegerProperty(nbCouverts);
	}
	
	public Commande(int id, int noTable, int nbCouverts, float prixTotal, String date, String serveur) {
		this.idCommande=new SimpleIntegerProperty(id);
		this.noTable = new SimpleIntegerProperty(noTable);
		this.prixTotal = new SimpleFloatProperty(prixTotal);
		this.nbCouverts = new SimpleIntegerProperty(nbCouverts);
		this.serveur = new SimpleStringProperty(serveur);
		this.date= new SimpleStringProperty(date);	
	}
	
    public IntegerProperty idCommandeProperty() {
        return idCommande;
    }

	public int getIdCommande() {
		return idCommande.get();
	}

	public void setIdCommande(int idCommande) {
		this.idCommande.set(idCommande);
	}

    public ArrayList<Produit> listeProduitCommandeProperty() {
        return listeProduitCommande;
    }


	public ArrayList<Produit> getListeProduitCommande() {
		return listeProduitCommande;
	}

	public void setListeProduitCommande(ArrayList<Produit> listeProduitCommande) {
		this.listeProduitCommande.addAll(listeProduitCommande);
	}

    public FloatProperty prixTotalProperty() {
        return prixTotal;
    }

	public float getPrixTotal() {
		return prixTotal.get();
	}

	public void setPrixTotal(float prixTotal) {
		this.prixTotal.set(prixTotal);
	}
	
    public IntegerProperty noTableProperty() {
        return noTable;
    }

	public int getNoTable() {
		return noTable.get();
	}
	

	public void setNoTable(int noTable) {
		this.noTable.set(noTable);
	}

    public IntegerProperty nbCouvertsProperty() {
        return nbCouverts;
    }
    
	public int getNbCouverts() {
		return nbCouverts.get();
	}

	public void setNbCouverts(int nbCouverts) {
		this.nbCouverts.set(nbCouverts);
	}
	

    public StringProperty dateProperty() {
        return date;
    }
    
	public String getDate() {
		return date.get();
	}
	
	public StringProperty serveurProperty() {
		return serveur;
	}
	
	public String getServeur() {
		return serveur.get();
	}
	
	public void setServeur(String serveur) {
		this.serveur.set(serveur);
	}

	
}

