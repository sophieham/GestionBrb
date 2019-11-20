package gestionbrb.model;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class FactureIngredients
{
	private IntegerProperty idCommande;
	private StringProperty date;
	private FloatProperty prixTotal;

	public FactureIngredients(int id, String date, float prixTotal){
		this.idCommande = new SimpleIntegerProperty(id);
		this.date = new SimpleStringProperty(date);
		this.prixTotal = new SimpleFloatProperty(prixTotal);
	}

}

