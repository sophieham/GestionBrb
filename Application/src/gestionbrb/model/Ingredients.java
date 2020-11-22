package gestionbrb.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

// TODO: Auto-generated Javadoc
/**
 * Ingredients utilisés dans la confections des produits.
 */

public class Ingredients
{
	
	/** The id ingredient. */
	private final IntegerProperty idIngredient;
	
	/** The nom ingredient. */
	private final StringProperty nomIngredient;
	
	/** The quantite ingredient. */
	private final IntegerProperty quantiteIngredient;
	
	/** The prix ingredient. */
	private final DoubleProperty prixIngredient;
	
	/** The fournisseur. */
	private final StringProperty fournisseur ;

	/**
	 * instancie le constructeur.
	 *
	 * @param idIngredient the id ingredient
	 * @param nomIngredient the nom ingredient
	 * @param prixIngredient the prix ingredient
	 * @param quantiteIngredient the quantite ingredient
	 * @param fournisseur the fournisseur
	 */
	
	public Ingredients(int idIngredient, String nomIngredient, int prixIngredient, int quantiteIngredient, String fournisseur){
		this.idIngredient = new SimpleIntegerProperty(idIngredient);
		this.nomIngredient = new SimpleStringProperty(nomIngredient);
		this.quantiteIngredient = new SimpleIntegerProperty(quantiteIngredient);
		this.prixIngredient = new SimpleDoubleProperty(prixIngredient);
		this.fournisseur = new SimpleStringProperty(fournisseur);
	}

	/**
	 * Instantiates a new ingredients.
	 */
	public Ingredients() {
		this(0,null,0,0,null);
	}
	
	/**
	 * Id ingredient property.
	 *
	 * @return the integer property
	 */
	public final IntegerProperty idIngredientProperty() {
		return this.idIngredient;
	}
	
	/**
	 * Gets the id ingredient.
	 *
	 * @return the id ingredient
	 */
	public final int getIdIngredient() {
		return this.idIngredientProperty().get();
	}
	
	/**
	 * Sets the id ingredient.
	 *
	 * @param idIngredient the new id ingredient
	 */
	public final void  setIdIngredient(final int idIngredient) {
		this.idIngredientProperty().set(idIngredient);
	}
	
	/**
	 * Nom ingredient property.
	 *
	 * @return the string property
	 */
	public final StringProperty nomIngredientProperty() {
		return this.nomIngredient;
	}
	
	/**
	 * Gets the nom ingredient.
	 *
	 * @return the nom ingredient
	 */
	public final String getNomIngredient() {
		return this.nomIngredientProperty().get();
	}
	
	/**
	 * Sets the nom ingredient.
	 *
	 * @param nomIngredient the new nom ingredient
	 */
	public final void  setNomIngredient(final String nomIngredient) {
		this.nomIngredientProperty().set(nomIngredient);
	}
	
	/**
	 * Prix ingredient property.
	 *
	 * @return the double property
	 */
	public final DoubleProperty prixIngredientProperty() {
		return this.prixIngredient;
	}
	
	/**
	 * Gets the prix ingredient.
	 *
	 * @return the prix ingredient
	 */
	public final double getPrixIngredient() {
		return this.prixIngredientProperty().get();
	}
	
	/**
	 * Sets the prix ingredient.
	 *
	 * @param prixIngredient the new prix ingredient
	 */
	public final void  setPrixIngredient(final double prixIngredient) {
		this.prixIngredientProperty().set(prixIngredient);
	}
	
	/**
	 * Quantite ingredient property.
	 *
	 * @return the integer property
	 */
	public final IntegerProperty quantiteIngredientProperty() {
		return this.quantiteIngredient;
	}
	
	/**
	 * Gets the quantite ingredient.
	 *
	 * @return the quantite ingredient
	 */
	public final int getQuantiteIngredient() {
		return this.quantiteIngredientProperty().get();
	}
	
	/**
	 * Sets the quantite ingredient.
	 *
	 * @param quantiteIngredient the new quantite ingredient
	 */
	public final void  setQuantiteIngredient(final int quantiteIngredient) {
		this.quantiteIngredientProperty().set(quantiteIngredient);
	}
	
	/**
	 * Fournisseur property.
	 *
	 * @return the string property
	 */
	public final StringProperty fournisseurProperty() {
		return this.fournisseur;
	}
	
	/**
	 * Gets the fournisseur.
	 *
	 * @return the fournisseur
	 */
	public final String getFournisseur() {
		return this.fournisseurProperty().get();
	}
	
	/**
	 * Sets the fournisseur.
	 *
	 * @param fournisseur the new fournisseur
	 */
	public final void  setFournisseur(final String fournisseur) {
		this.fournisseurProperty().set(fournisseur);
	}
	
}












