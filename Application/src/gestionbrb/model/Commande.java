package gestionbrb.model;

import java.util.ArrayList;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

// TODO: Auto-generated Javadoc
/**
 * Ensemble de produits commandés par un client a une date et une table donnée.
 *
 * @author Sophie
 */
public class Commande 
{
	
	/** The id commande. */
	private IntegerProperty idCommande;
	
	/** The liste produit commande. */
	private ArrayList<Produit> listeProduitCommande;
	
	/** The prix total. */
	private DoubleProperty prixTotal;
	
	/** The no table. */
	private IntegerProperty noTable;
	
	/** The nb couverts. */
	private IntegerProperty nbCouverts;
	
	/** The date. */
	private SimpleStringProperty date;
	
	/** The serveur. */
	private SimpleStringProperty serveur;

	/**
	 * Instantiates a new commande.
	 *
	 * @param id the id
	 * @param noTable the no table
	 * @param nbCouverts the nb couverts
	 */
	public Commande(int id, int noTable, int nbCouverts){
		this.idCommande=new SimpleIntegerProperty(id);
		this.noTable = new SimpleIntegerProperty(noTable);
		this.nbCouverts = new SimpleIntegerProperty(nbCouverts);
	}
	
	/**
	 * Instantiates a new commande.
	 *
	 * @param id the id
	 * @param noTable the no table
	 * @param nbCouverts the nb couverts
	 * @param prixTotal the prix total
	 * @param date the date
	 * @param serveur the serveur
	 */
	public Commande(int id, int noTable, int nbCouverts, double prixTotal, String date, String serveur) {
		this.idCommande=new SimpleIntegerProperty(id);
		this.noTable = new SimpleIntegerProperty(noTable);
		this.prixTotal = new SimpleDoubleProperty(prixTotal);
		this.nbCouverts = new SimpleIntegerProperty(nbCouverts);
		this.serveur = new SimpleStringProperty(serveur);
		this.date= new SimpleStringProperty(date);	
	}
	
    /**
     * Id commande property.
     *
     * @return the integer property
     */
    public IntegerProperty idCommandeProperty() {
        return idCommande;
    }

	/**
	 * Gets the id commande.
	 *
	 * @return the id commande
	 */
	public int getIdCommande() {
		return idCommande.get();
	}

	/**
	 * Sets the id commande.
	 *
	 * @param idCommande the new id commande
	 */
	public void setIdCommande(int idCommande) {
		this.idCommande.set(idCommande);
	}

    /**
     * Liste produit commande property.
     *
     * @return the array list
     */
    public ArrayList<Produit> listeProduitCommandeProperty() {
        return listeProduitCommande;
    }


	/**
	 * Gets the liste produit commande.
	 *
	 * @return the liste produit commande
	 */
	public ArrayList<Produit> getListeProduitCommande() {
		return listeProduitCommande;
	}

	/**
	 * Sets the liste produit commande.
	 *
	 * @param listeProduitCommande the new liste produit commande
	 */
	public void setListeProduitCommande(ArrayList<Produit> listeProduitCommande) {
		this.listeProduitCommande.addAll(listeProduitCommande);
	}

    /**
     * Prix total property.
     *
     * @return the double property
     */
    public DoubleProperty prixTotalProperty() {
        return prixTotal;
    }

	/**
	 * Gets the prix total.
	 *
	 * @return the prix total
	 */
	public double getPrixTotal() {
		return prixTotal.get();
	}

	/**
	 * Sets the prix total.
	 *
	 * @param prixTotal the new prix total
	 */
	public void setPrixTotal(double prixTotal) {
		this.prixTotal.set(prixTotal);
	}
	
    /**
     * No table property.
     *
     * @return the integer property
     */
    public IntegerProperty noTableProperty() {
        return noTable;
    }

	/**
	 * Gets the no table.
	 *
	 * @return the no table
	 */
	public int getNoTable() {
		return noTable.get();
	}
	

	/**
	 * Sets the no table.
	 *
	 * @param noTable the new no table
	 */
	public void setNoTable(int noTable) {
		this.noTable.set(noTable);
	}

    /**
     * Nb couverts property.
     *
     * @return the integer property
     */
    public IntegerProperty nbCouvertsProperty() {
        return nbCouverts;
    }
    
	/**
	 * Gets the nb couverts.
	 *
	 * @return the nb couverts
	 */
	public int getNbCouverts() {
		return nbCouverts.get();
	}

	/**
	 * Sets the nb couverts.
	 *
	 * @param nbCouverts the new nb couverts
	 */
	public void setNbCouverts(int nbCouverts) {
		this.nbCouverts.set(nbCouverts);
	}
	

    /**
     * Date property.
     *
     * @return the string property
     */
    public StringProperty dateProperty() {
        return date;
    }
    
	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public String getDate() {
		return date.get();
	}
	
	/**
	 * Serveur property.
	 *
	 * @return the string property
	 */
	public StringProperty serveurProperty() {
		return serveur;
	}
	
	/**
	 * Gets the serveur.
	 *
	 * @return the serveur
	 */
	public String getServeur() {
		return serveur.get();
	}
	
	/**
	 * Sets the serveur.
	 *
	 * @param serveur the new serveur
	 */
	public void setServeur(String serveur) {
		this.serveur.set(serveur);
	}

	
}

