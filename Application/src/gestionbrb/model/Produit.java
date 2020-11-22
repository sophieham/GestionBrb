package gestionbrb.model;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

// TODO: Auto-generated Javadoc
/**
 * Produits que les clients commandent.
 */

public class Produit {
	
	/** The id produit. */
	private final IntegerProperty idProduit;
	
	/** The nom produit. */
	private final StringProperty nomProduit;
	
	/** The quantite produit. */
	private final IntegerProperty quantiteProduit;
	
	/** The description produit. */
	private final StringProperty descriptionProduit;
	
	/** The prix produit. */
	private final DoubleProperty prixProduit;
	
	/** The type. */
	private final StringProperty type;
	
	/** The ingredients. */
	private final StringProperty ingredients;
	
	/**
	 * instancie le constructeur.
	 *
	 * @param idProduit the id produit
	 * @param nomProduit the nom produit
	 * @param quantiteProduit the quantite produit
	 * @param descriptionProduit the description produit
	 * @param prixProduit the prix produit
	 * @param type the type
	 * @param ingredients the ingredients
	 */

	public Produit(int idProduit, String nomProduit, int quantiteProduit, String descriptionProduit, double prixProduit, String type, String ingredients){
		this.idProduit = new SimpleIntegerProperty(idProduit);
		this.nomProduit = new SimpleStringProperty(nomProduit);
		this.quantiteProduit = new SimpleIntegerProperty(quantiteProduit);
		this.descriptionProduit = new SimpleStringProperty(descriptionProduit);
		this.prixProduit = new SimpleDoubleProperty(prixProduit);
		this.type = new SimpleStringProperty(type);
		this.ingredients = new SimpleStringProperty(ingredients);
	}
	
	/**
	 * instancie le constructeur.
	 *
	 * @param nomProduit the nom produit
	 * @param prixProduit the prix produit
	 * @param quantiteProduit the quantite produit
	 */
	
	public Produit(String nomProduit, double prixProduit, int quantiteProduit){
		this.idProduit = new SimpleIntegerProperty(0);
		this.nomProduit = new SimpleStringProperty(nomProduit);
		this.quantiteProduit = new SimpleIntegerProperty(quantiteProduit);
		this.descriptionProduit = new SimpleStringProperty(null);
		this.prixProduit = new SimpleDoubleProperty(prixProduit);
		this.type = new SimpleStringProperty(null);
		this.ingredients = new SimpleStringProperty(null);
	}
	
	/**
	 * Instantiates a new produit.
	 */
	public Produit() {
		this(0 ,null , 0, null, 0, null, null);
	}
	
	/**
	 * Id produit property.
	 *
	 * @return the integer property
	 */
	public final IntegerProperty idProduitProperty() {
		return this.idProduit;
	}
	
	/**
	 * Gets the id produit.
	 *
	 * @return the id produit
	 */
	public final int getIdProduit() {
		return this.idProduitProperty().get();
	}
	
	/**
	 * Sets the id produit.
	 *
	 * @param idProduit the new id produit
	 */
	public final void  setIdProduit(final int idProduit) {
		this.idProduitProperty().set(idProduit);
	}
	
	/**
	 * Nom produit property.
	 *
	 * @return the string property
	 */
	public final StringProperty nomProduitProperty() {
		return this.nomProduit;
	}
	
	/**
	 * Gets the nom produit.
	 *
	 * @return the nom produit
	 */
	public final String getNomProduit() {
		return this.nomProduitProperty().get();
	}
	
	/**
	 * Sets the nom produit.
	 *
	 * @param nomProduit the new nom produit
	 */
	public final void  setNomProduit(final String nomProduit) {
		this.nomProduitProperty().set(nomProduit);
	}
	
	/**
	 * Prix produit property.
	 *
	 * @return the double property
	 */
	public final DoubleProperty prixProduitProperty() {
		return this.prixProduit;
	}
	
	/**
	 * Gets the prix produit.
	 *
	 * @return the prix produit
	 */
	public final double getPrixProduit() {
		return this.prixProduitProperty().get();
	}
	
	/**
	 * Sets the prix produit.
	 *
	 * @param prixProduit the new prix produit
	 */
	public final void  setPrixProduit(final double prixProduit) {
		this.prixProduitProperty().set(prixProduit);
	}
	
	/**
	 * Quantite produit property.
	 *
	 * @return the integer property
	 */
	public final IntegerProperty quantiteProduitProperty() {
		return this.quantiteProduit;
	}
	
	/**
	 * Gets the quantite produit.
	 *
	 * @return the quantite produit
	 */
	public final int getQuantiteProduit() {
		return this.quantiteProduitProperty().get();
	}
	
	/**
	 * Sets the quantite produit.
	 *
	 * @param quantiteProduit the new quantite produit
	 */
	public final void  setQuantiteProduit(final int quantiteProduit) {
		this.quantiteProduitProperty().set(quantiteProduit);
	}
	
	/**
	 * Description produit property.
	 *
	 * @return the string property
	 */
	public final StringProperty descriptionProduitProperty() {
		return this.descriptionProduit;
	}
	
	/**
	 * Gets the description produit.
	 *
	 * @return the description produit
	 */
	public final String getDescriptionProduit() {
		return this.descriptionProduitProperty().get();
	}
	
	/**
	 * Sets the description produit.
	 *
	 * @param descriptionProduit the new description produit
	 */
	public final void  setDescriptionProduit(final String descriptionProduit) {
		this.descriptionProduitProperty().set(descriptionProduit);
	}
	
	/**
	 * Type property.
	 *
	 * @return the string property
	 */
	public final StringProperty typeProperty() {
		return this.type;
	}
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public final String getType() {
		return this.typeProperty().get();
	}
	
	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public final void  setType(final String type) {
		this.typeProperty().set(type);
	}
	
	/**
	 * Ingredients property.
	 *
	 * @return the string property
	 */
	public final StringProperty ingredientsProperty() {
		return this.ingredients;
	}
	
	/**
	 * Gets the ingredients.
	 *
	 * @return the ingredients
	 */
	public final String getIngredients() {
		return this.ingredientsProperty().get();
	}
	
	/**
	 * Sets the ingredients.
	 *
	 * @param ingredients the new ingredients
	 */
	public final void setIngredients(final String ingredients) {
		this.ingredientsProperty().set(ingredients);
	}
	
}


