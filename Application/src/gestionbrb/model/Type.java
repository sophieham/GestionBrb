package gestionbrb.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Type d'un produit
 */
public class Type
{
	private final IntegerProperty idType;
	private final StringProperty nomType;
	
	public Type(int idType, String nomType){
		this.idType = new SimpleIntegerProperty(idType);
		this.nomType = new SimpleStringProperty(nomType);
	}
	public Type() {
		this(0 ,null);
	}
	public final IntegerProperty idTypeProperty() {
		return this.idType;
	}
	public final int getIdType() {
		return this.idTypeProperty().get();
	}
	public final void  setIdType(final int idType) {
		this.idTypeProperty().set(idType);
	}
	public final StringProperty nomTypeProperty() {
		return this.nomType;
	}
	public final String getNomType() {
		return this.nomTypeProperty().get();
	}
	public final void  setNomType(final String nomType) {
		this.nomTypeProperty().set(nomType);
	}
}
