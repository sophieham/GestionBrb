package gestionbrb.model;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Produits que les clients commandent
 */

public class Produit {
	
	private final IntegerProperty idProduit;
	private final StringProperty nomProduit;
	private final IntegerProperty quantiteProduit;
	private final StringProperty descriptionProduit;
	private final DoubleProperty prixProduit;
	private final StringProperty type;
	private final StringProperty ingredients;

	public Produit(int idProduit, String nomProduit, int quantiteProduit, String descriptionProduit, double prixProduit, String type, String ingredients){
		this.idProduit = new SimpleIntegerProperty(idProduit);
		this.nomProduit = new SimpleStringProperty(nomProduit);
		this.quantiteProduit = new SimpleIntegerProperty(quantiteProduit);
		this.descriptionProduit = new SimpleStringProperty(descriptionProduit);
		this.prixProduit = new SimpleDoubleProperty(prixProduit);
		this.type = new SimpleStringProperty(type);
		this.ingredients = new SimpleStringProperty(ingredients);
	}
	
	public Produit(String nomProduit, double prixProduit, int quantiteProduit){
		this.idProduit = new SimpleIntegerProperty(0);
		this.nomProduit = new SimpleStringProperty(nomProduit);
		this.quantiteProduit = new SimpleIntegerProperty(quantiteProduit);
		this.descriptionProduit = new SimpleStringProperty(null);
		this.prixProduit = new SimpleDoubleProperty(prixProduit);
		this.type = new SimpleStringProperty(null);
		this.ingredients = new SimpleStringProperty(null);
	}

	public Produit() {
		this(0 ,null , 0, null, 0, null, null);
	}
	public final IntegerProperty idProduitProperty() {
		return this.idProduit;
	}
	public final int getIdProduit() {
		return this.idProduitProperty().get();
	}
	public final void  setIdProduit(final int idProduit) {
		this.idProduitProperty().set(idProduit);
	}
	public final StringProperty nomProduitProperty() {
		return this.nomProduit;
	}
	public final String getNomProduit() {
		return this.nomProduitProperty().get();
	}
	public final void  setNomProduit(final String nomProduit) {
		this.nomProduitProperty().set(nomProduit);
	}
	public final DoubleProperty prixProduitProperty() {
		return this.prixProduit;
	}
	public final double getPrixProduit() {
		return this.prixProduitProperty().get();
	}
	public final void  setPrixProduit(final double prixProduit) {
		this.prixProduitProperty().set(prixProduit);
	}
	public final IntegerProperty quantiteProduitProperty() {
		return this.quantiteProduit;
	}
	public final int getQuantiteProduit() {
		return this.quantiteProduitProperty().get();
	}
	public final void  setQuantiteProduit(final int quantiteProduit) {
		this.quantiteProduitProperty().set(quantiteProduit);
	}
	public final StringProperty descriptionProduitProperty() {
		return this.descriptionProduit;
	}
	public final String getDescriptionProduit() {
		return this.descriptionProduitProperty().get();
	}
	public final void  setDescriptionProduit(final String descriptionProduit) {
		this.descriptionProduitProperty().set(descriptionProduit);
	}
	public final StringProperty typeProperty() {
		return this.type;
	}
	public final String getType() {
		return this.typeProperty().get();
	}
	public final void  setType(final String type) {
		this.typeProperty().set(type);
	}
	
	public final StringProperty ingredientsProperty() {
		return this.ingredients;
	}
	public final String getIngredients() {
		return this.ingredientsProperty().get();
	}
	public final void setIngredients(final String ingredients) {
		this.ingredientsProperty().set(ingredients);
	}
	
}


