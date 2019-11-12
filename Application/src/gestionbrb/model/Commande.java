package gestionbrb.model;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class Commande 
{
	private IntegerProperty idCommande;
	private ArrayList<Produit> listeProduitCommande;
	private IntegerProperty prixTotal;
	private IntegerProperty noTable;
	private IntegerProperty nbCouverts;
	private IntegerProperty qteTotal;
	private LocalDate date;

	public Commande(int id, int noTable, int nbCouverts){
		this.idCommande=new SimpleIntegerProperty(id);
		this.noTable = new SimpleIntegerProperty(noTable);
		this.nbCouverts = new SimpleIntegerProperty(nbCouverts);
		this.date=LocalDate.now();		
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

    public IntegerProperty prixTotalProperty() {
        return prixTotal;
    }

	public int getPrixTotal() {
		return prixTotal.get();
	}

	public void setPrixTotal(int prixTotal) {
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
	
    public IntegerProperty qteTotalProperty() {
        return qteTotal;
    }

	public int getQte() {
		return qteTotal.get();
	}

	public void setQte(int qte) {
		this.qteTotal.set(qte);
	}

    public LocalDate dateProperty() {
        return date;
    }
    
	public LocalDate getDate() {
		return date;
	}

	public void modifierCommande() {
		// TODO implement me	
	}

	public void voirCommande() {
		// TODO implement me	
	}
	
}

