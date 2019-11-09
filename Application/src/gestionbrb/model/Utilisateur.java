package gestionbrb.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * <!-- begin-user-doc --> <!-- end-user-doc -->
 * 
 * 
 * @generated
 */
public class Utilisateur {
	private final StringProperty identifiant;
	private final StringProperty mot2passe;
	private final StringProperty nom;
	private final StringProperty prenom;
	private final IntegerProperty privileges;

	/**
	 * 
	 * @param identifiant
	 * @param mot2passe
	 * @param nom
	 * @param prenom
	 * @param privileges  (0 pour admin, 1 pour utilisateur, 2 pour serveur)
	 */
	public Utilisateur(String identifiant, String mot2passe, String nom, String prenom, int privileges) {
		this.privileges = new SimpleIntegerProperty(privileges);
		this.identifiant = new SimpleStringProperty(identifiant);
		this.mot2passe = new SimpleStringProperty(mot2passe);
		this.nom = new SimpleStringProperty(nom);
		this.prenom = new SimpleStringProperty(prenom);

	}

	public Utilisateur() {
		this("", "", "", "", 1);
	}

	public final StringProperty identifiantProperty() {
		return this.identifiant;
	}

	public final String getIdentifiant() {
		return this.identifiantProperty().get();
	}

	public final void setIdentifiant(final String identifiant) {
		this.identifiantProperty().set(identifiant);
	}

	public final StringProperty mot2passeProperty() {
		return this.mot2passe;
	}

	public final String getMot2passe() {
		return this.mot2passeProperty().get();
	}

	public final void setMot2passe(final String mot2passe) {
		this.mot2passeProperty().set(mot2passe);
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

	public final StringProperty prenomProperty() {
		return this.prenom;
	}

	public final String getPrenom() {
		return this.prenomProperty().get();
	}

	public final void setPrenom(final String prenom) {
		this.prenomProperty().set(prenom);
	}

	public final IntegerProperty privilegesProperty() {
		return this.privileges;
	}

	public final int getPrivileges() {
		return this.privilegesProperty().get();
	}

	public final void setPrivileges(final int privileges) {
		this.privilegesProperty().set(privileges);
	}

}
