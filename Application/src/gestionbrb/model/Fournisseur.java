package gestionbrb.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

// TODO: Auto-generated Javadoc
/**
 * Fournisseur des ingredients.
 */

public class Fournisseur {
	
	/** The id fournisseur. */
	private IntegerProperty idFournisseur;
	
	/** The nom. */
	private StringProperty nom;
	
	/** The num tel. */
	private StringProperty numTel;
	
	/** The mail. */
	private StringProperty mail;
	
	/** The adresse. */
	private StringProperty adresse;
	
	/** The code postal. */
	private IntegerProperty codePostal;
	
	/** The nom ville. */
	private StringProperty nomVille;

	/**
	 * Instantiates a new fournisseur.
	 *
	 * @param id the id
	 * @param nom the nom
	 * @param numTel the num tel
	 * @param mail the mail
	 * @param adresse the adresse
	 * @param codePostal the code postal
	 * @param ville the ville
	 */
	public Fournisseur(int id, String nom, String numTel, String mail, String adresse, int codePostal, String ville) {
		this.idFournisseur = new SimpleIntegerProperty(id);
		this.nom = new SimpleStringProperty(nom);
		this.numTel = new SimpleStringProperty(numTel);
		this.mail = new SimpleStringProperty(mail);
		this.adresse = new SimpleStringProperty(adresse);
		this.codePostal = new SimpleIntegerProperty(codePostal);
		this.nomVille = new SimpleStringProperty(ville);
	}
	
	/**
	 * Instantiates a new fournisseur.
	 */
	public Fournisseur() {
		this(0,"", "", "", "", 0, "");
	}

	/**
	 * Id fournisseur property.
	 *
	 * @return the integer property
	 */
	public final IntegerProperty idFournisseurProperty() {
		return this.idFournisseur;
	}
	

	/**
	 * Gets the id fournisseur.
	 *
	 * @return the id fournisseur
	 */
	public final int getIdFournisseur() {
		return this.idFournisseurProperty().get();
	}
	

	/**
	 * Sets the id fournisseur.
	 *
	 * @param idFournisseur the new id fournisseur
	 */
	public final void setIdFournisseur(final int idFournisseur) {
		this.idFournisseurProperty().set(idFournisseur);
	}
	

	/**
	 * Nom property.
	 *
	 * @return the string property
	 */
	public final StringProperty nomProperty() {
		return this.nom;
	}
	

	/**
	 * Gets the nom.
	 *
	 * @return the nom
	 */
	public final String getNom() {
		return this.nomProperty().get();
	}
	

	/**
	 * Sets the nom.
	 *
	 * @param nom the new nom
	 */
	public final void setNom(final String nom) {
		this.nomProperty().set(nom);
	}
	

	/**
	 * Num tel property.
	 *
	 * @return the string property
	 */
	public final StringProperty numTelProperty() {
		return this.numTel;
	}
	

	/**
	 * Gets the num tel.
	 *
	 * @return the num tel
	 */
	public final String getNumTel() {
		return this.numTelProperty().get();
	}
	

	/**
	 * Sets the num tel.
	 *
	 * @param numTel the new num tel
	 */
	public final void setNumTel(final String numTel) {
		this.numTelProperty().set(numTel);
	}
	

	/**
	 * Mail property.
	 *
	 * @return the string property
	 */
	public final StringProperty mailProperty() {
		return this.mail;
	}
	

	/**
	 * Gets the mail.
	 *
	 * @return the mail
	 */
	public final String getMail() {
		return this.mailProperty().get();
	}
	

	/**
	 * Sets the mail.
	 *
	 * @param mail the new mail
	 */
	public final void setMail(final String mail) {
		this.mailProperty().set(mail);
	}
	

	/**
	 * Adresse property.
	 *
	 * @return the string property
	 */
	public final StringProperty adresseProperty() {
		return this.adresse;
	}
	

	/**
	 * Gets the adresse.
	 *
	 * @return the adresse
	 */
	public final String getAdresse() {
		return this.adresseProperty().get();
	}
	

	/**
	 * Sets the adresse.
	 *
	 * @param adresse the new adresse
	 */
	public final void setAdresse(final String adresse) {
		this.adresseProperty().set(adresse);
	}
	

	/**
	 * Code postal property.
	 *
	 * @return the integer property
	 */
	public final IntegerProperty codePostalProperty() {
		return this.codePostal;
	}
	

	/**
	 * Gets the code postal.
	 *
	 * @return the code postal
	 */
	public final int getCodePostal() {
		return this.codePostalProperty().get();
	}
	

	/**
	 * Sets the code postal.
	 *
	 * @param codePostal the new code postal
	 */
	public final void setCodePostal(final int codePostal) {
		this.codePostalProperty().set(codePostal);
	}
	

	/**
	 * Nom ville property.
	 *
	 * @return the string property
	 */
	public final StringProperty nomVilleProperty() {
		return this.nomVille;
	}
	

	/**
	 * Gets the nom ville.
	 *
	 * @return the nom ville
	 */
	public final String getNomVille() {
		return this.nomVilleProperty().get();
	}
	

	/**
	 * Sets the nom ville.
	 *
	 * @param nomVille the new nom ville
	 */
	public final void setNomVille(final String nomVille) {
		this.nomVilleProperty().set(nomVille);
	}
	
	
}
