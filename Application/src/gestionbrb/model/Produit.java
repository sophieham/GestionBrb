package gestionbrb.model;


import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class Produit {
	
	private final IntegerProperty idProduit;
	private final StringProperty nomProduit;
	private final IntegerProperty quantiteProduit;
	private final StringProperty descriptionProduit;
	private final FloatProperty prixProduit;
	private final StringProperty type ;

	public Produit(int idProduit, String nomProduit, int quantiteProduit, String descriptionProduit, float prixProduit, String type){
		this.idProduit = new SimpleIntegerProperty(idProduit);
		this.nomProduit = new SimpleStringProperty(nomProduit);
		this.quantiteProduit = new SimpleIntegerProperty(quantiteProduit);
		this.descriptionProduit = new SimpleStringProperty(descriptionProduit);
		this.prixProduit = new SimpleFloatProperty(prixProduit);
		this.type = new SimpleStringProperty(type);
	}
	
	public Produit(String nomProduit, float prixProduit, int quantiteProduit){
		this.idProduit = new SimpleIntegerProperty(0);
		this.nomProduit = new SimpleStringProperty(nomProduit);
		this.quantiteProduit = new SimpleIntegerProperty(quantiteProduit);
		this.descriptionProduit = new SimpleStringProperty(null);
		this.prixProduit = new SimpleFloatProperty(prixProduit);
		this.type = new SimpleStringProperty(null);
	}

	public Produit() {
		this(0 ,null , 0, null, 0, null);
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
	public final FloatProperty prixProduitProperty() {
		return this.prixProduit;
	}
	public final float getPrixProduit() {
		return this.prixProduitProperty().get();
	}
	public final void  setPrixProduit(final int prixProduit) {
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
	
}


