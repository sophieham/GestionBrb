package gestionbrb.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Fournisseur des ingredients
 * @generated
 */

public class Fournisseur {
	private IntegerProperty idFournisseur;
	private StringProperty nom;
	private StringProperty numTel;
	private StringProperty mail;
	private StringProperty adresse;
	private IntegerProperty codePostal;
	private StringProperty nomVille;

	public Fournisseur(int id, String nom, String numTel, String mail, String adresse, int codePostal, String ville) {
		this.idFournisseur = new SimpleIntegerProperty(id);
		this.nom = new SimpleStringProperty(nom);
		this.numTel = new SimpleStringProperty(numTel);
		this.mail = new SimpleStringProperty(mail);
		this.adresse = new SimpleStringProperty(adresse);
		this.codePostal = new SimpleIntegerProperty(codePostal);
		this.nomVille = new SimpleStringProperty(ville);
	}
	public Fournisseur() {
		this(0,"", "", "", "", 0, "");
	}

	public final IntegerProperty idFournisseurProperty() {
		return this.idFournisseur;
	}
	

	public final int getIdFournisseur() {
		return this.idFournisseurProperty().get();
	}
	

	public final void setIdFournisseur(final int idFournisseur) {
		this.idFournisseurProperty().set(idFournisseur);
	}
	

	public final StringProperty nomProperty() {
		return this.nom;
	}
	

	public final String getNom() {
		return this.nomProperty().get();
	}
	

	public final void setNom(final String nom) {
		this.nomProperty().set(nom);
	}
	

	public final StringProperty numTelProperty() {
		return this.numTel;
	}
	

	public final String getNumTel() {
		return this.numTelProperty().get();
	}
	

	public final void setNumTel(final String numTel) {
		this.numTelProperty().set(numTel);
	}
	

	public final StringProperty mailProperty() {
		return this.mail;
	}
	

	public final String getMail() {
		return this.mailProperty().get();
	}
	

	public final void setMail(final String mail) {
		this.mailProperty().set(mail);
	}
	

	public final StringProperty adresseProperty() {
		return this.adresse;
	}
	

	public final String getAdresse() {
		return this.adresseProperty().get();
	}
	

	public final void setAdresse(final String adresse) {
		this.adresseProperty().set(adresse);
	}
	

	public final IntegerProperty codePostalProperty() {
		return this.codePostal;
	}
	

	public final int getCodePostal() {
		return this.codePostalProperty().get();
	}
	

	public final void setCodePostal(final int codePostal) {
		this.codePostalProperty().set(codePostal);
	}
	

	public final StringProperty nomVilleProperty() {
		return this.nomVille;
	}
	

	public final String getNomVille() {
		return this.nomVilleProperty().get();
	}
	

	public final void setNomVille(final String nomVille) {
		this.nomVilleProperty().set(nomVille);
	}
	
	
}
