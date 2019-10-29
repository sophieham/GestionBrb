package gestionbrb.model;


/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class Produit
{
	private int idProduit;
	private String nom;
	private String description;
	private double prix;
	private Type type;
	private boolean IngredientsEnStock;
	private boolean estServi;

	public Produit(){
		super();
	}

	public void modifierProduit() {
		// TODO implement me	
	}

	public boolean estFaisable() {
		// TODO implement me
		return false;	
	}
	
}

