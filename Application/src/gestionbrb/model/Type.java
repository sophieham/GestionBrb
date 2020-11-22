package gestionbrb.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

// TODO: Auto-generated Javadoc
/**
 * Catégorie de plats (Entrée, dessert...)
 */
public class Type
{
	
	/** The id type. */
	private final IntegerProperty idType;
	
	/** The nom type. */
	private final StringProperty nomType;
	
	/**
	 * Instantiates a new type.
	 *
	 * @param idType the id type
	 * @param nomType the nom type
	 */
	public Type(int idType, String nomType){
		this.idType = new SimpleIntegerProperty(idType);
		this.nomType = new SimpleStringProperty(nomType);
	}
	
	/**
	 * Instantiates a new type.
	 */
	public Type() {
		this(0 ,null);
	}
	
	/**
	 * Id type property.
	 *
	 * @return the integer property
	 */
	public final IntegerProperty idTypeProperty() {
		return this.idType;
	}
	
	/**
	 * Gets the id type.
	 *
	 * @return the id type
	 */
	public final int getIdType() {
		return this.idTypeProperty().get();
	}
	
	/**
	 * Sets the id type.
	 *
	 * @param idType the new id type
	 */
	public final void  setIdType(final int idType) {
		this.idTypeProperty().set(idType);
	}
	
	/**
	 * Nom type property.
	 *
	 * @return the string property
	 */
	public final StringProperty nomTypeProperty() {
		return this.nomType;
	}
	
	/**
	 * Gets the nom type.
	 *
	 * @return the nom type
	 */
	public final String getNomType() {
		return this.nomTypeProperty().get();
	}
	
	/**
	 * Sets the nom type.
	 *
	 * @param nomType the new nom type
	 */
	public final void  setNomType(final String nomType) {
		this.nomTypeProperty().set(nomType);
	}
}
