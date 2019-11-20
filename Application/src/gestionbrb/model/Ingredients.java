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

public class Ingredients
{
	private final IntegerProperty idIngredient;
	private final StringProperty nomIngredient;
	private final IntegerProperty quantiteIngredient;
	private final FloatProperty prixIngredient;
	private final StringProperty fournisseur ;

	public Ingredients(int idIngredient, String nomIngredient, int prixIngredient, int quantiteIngredient, String fournisseur){
		this.idIngredient = new SimpleIntegerProperty(idIngredient);
		this.nomIngredient = new SimpleStringProperty(nomIngredient);
		this.quantiteIngredient = new SimpleIntegerProperty(quantiteIngredient);
		this.prixIngredient = new SimpleFloatProperty(prixIngredient);
		this.fournisseur = new SimpleStringProperty(fournisseur);
	}

	public Ingredients() {
		this(0,null,0,0,null);
	}
	public final IntegerProperty idIngredientProperty() {
		return this.idIngredient;
	}
	public final int getIdIngredient() {
		return this.idIngredientProperty().get();
	}
	public final void  setIdIngredient(final int idIngredient) {
		this.idIngredientProperty().set(idIngredient);
	}
	public final StringProperty nomIngredientProperty() {
		return this.nomIngredient;
	}
	public final String getNomIngredient() {
		return this.nomIngredientProperty().get();
	}
	public final void  setNomIngredient(final String nomIngredient) {
		this.nomIngredientProperty().set(nomIngredient);
	}
	public final FloatProperty prixIngredientProperty() {
		return this.prixIngredient;
	}
	public final float getPrixIngredient() {
		return this.prixIngredientProperty().get();
	}
	public final void  setPrixIngredient(final float prixIngredient) {
		this.prixIngredientProperty().set(prixIngredient);
	}
	public final IntegerProperty quantiteIngredientProperty() {
		return this.quantiteIngredient;
	}
	public final int getQuantiteIngredient() {
		return this.quantiteIngredientProperty().get();
	}
	public final void  setQuantiteIngredient(final int quantiteIngredient) {
		this.quantiteIngredientProperty().set(quantiteIngredient);
	}
	public final StringProperty fournisseurProperty() {
		return this.fournisseur;
	}
	public final String getFournisseur() {
		return this.fournisseurProperty().get();
	}
	public final void  setFournisseur(final String fournisseur) {
		this.fournisseurProperty().set(fournisseur);
	}
	
}












