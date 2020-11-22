package gestionbrb.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

// TODO: Auto-generated Javadoc
/**
 * Personne utilisant le programme (peut etre un administrateur, un serveur ou un client).
 */
public class Utilisateur {
	
	/** The id utilisateur. */
	private final IntegerProperty idUtilisateur;
	
	/** The identifiant. */
	private final StringProperty identifiant;
	
	/** The motdepasse. */
	private final StringProperty motdepasse;
	
	/** The nom. */
	private final StringProperty nom;
	
	/** The prenom. */
	private final StringProperty prenom;
	
	/** The privileges. */
	private final IntegerProperty privileges;
	
	/** The date. */
	private final StringProperty date;

	/**
	 * Constructeur pour de utilisateur.
	 *
	 * @param id the id
	 * @param identifiant the identifiant
	 * @param motdepasse the motdepasse
	 * @param nom the nom
	 * @param prenom the prenom
	 * @param privileges  (1 pour admin, 0 pour serveur)
	 */
	public Utilisateur(int id, String identifiant, String motdepasse, String nom, String prenom, int privileges) {
		this.idUtilisateur = new SimpleIntegerProperty(id);
		this.privileges = new SimpleIntegerProperty(privileges);
		this.identifiant = new SimpleStringProperty(identifiant);
		this.motdepasse = new SimpleStringProperty(motdepasse);
		this.nom = new SimpleStringProperty(nom);
		this.prenom = new SimpleStringProperty(prenom);
		this.date = new SimpleStringProperty(null);

	}

	/**
	 * Instantiates a new utilisateur.
	 */
	public Utilisateur() {
		this(0, "", "", "", "", 0);
	}
	
	
	
	/**
	 * Instantiates a new utilisateur.
	 *
	 * @param id the id
	 * @param identifiant the identifiant
	 * @param date the date
	 */
	public Utilisateur(int id, String identifiant, String date) {
		this.idUtilisateur = new SimpleIntegerProperty(id);
		this.privileges = new SimpleIntegerProperty(-1);
		this.identifiant = new SimpleStringProperty(identifiant);
		this.motdepasse = new SimpleStringProperty(null);
		this.nom = new SimpleStringProperty(null);
		this.prenom = new SimpleStringProperty(null);
		this.date = new SimpleStringProperty(date);
	}

	/**
	 * Instantiates a new utilisateur.
	 *
	 * @param id the id
	 * @param identifiant the identifiant
	 * @param nom the nom
	 * @param prenom the prenom
	 * @param privileges the privileges
	 */
	public Utilisateur(int id, String identifiant, String nom, String prenom, int privileges) {
		this.idUtilisateur = new SimpleIntegerProperty(id);
		this.privileges = new SimpleIntegerProperty(privileges);
		this.identifiant = new SimpleStringProperty(identifiant);
		this.motdepasse = new SimpleStringProperty("");
		this.nom = new SimpleStringProperty(nom);
		this.prenom = new SimpleStringProperty(prenom);	
		this.date = new SimpleStringProperty(null);
		}

	/**
	 * Identifiant property.
	 *
	 * @return the string property
	 */
	public final StringProperty identifiantProperty() {
		return this.identifiant;
	}

	/**
	 * Gets the identifiant.
	 *
	 * @return the identifiant
	 */
	public final String getIdentifiant() {
		return this.identifiantProperty().get();
	}

	/**
	 * Sets the identifiant.
	 *
	 * @param identifiant the new identifiant
	 */
	public final void setIdentifiant(final String identifiant) {
		this.identifiantProperty().set(identifiant);
	}

	/**
	 * Motdepasse property.
	 *
	 * @return the string property
	 */
	public final StringProperty motdepasseProperty() {
		return this.motdepasse;
	}

	/**
	 * Gets the motdepasse.
	 *
	 * @return the motdepasse
	 */
	public final String getMotdepasse() {
		return this.motdepasseProperty().get();
	}

	/**
	 * Sets the mot 2 passe.
	 *
	 * @param mot2passe the new mot 2 passe
	 */
	public final void setMot2passe(final String mot2passe) {
		this.motdepasseProperty().set(mot2passe);
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
	 * Prenom property.
	 *
	 * @return the string property
	 */
	public final StringProperty prenomProperty() {
		return this.prenom;
	}

	/**
	 * Gets the prenom.
	 *
	 * @return the prenom
	 */
	public final String getPrenom() {
		return this.prenomProperty().get();
	}

	/**
	 * Sets the prenom.
	 *
	 * @param prenom the new prenom
	 */
	public final void setPrenom(final String prenom) {
		this.prenomProperty().set(prenom);
	}

	/**
	 * Privileges property.
	 *
	 * @return the integer property
	 */
	public final IntegerProperty privilegesProperty() {
		return this.privileges;
	}

	/**
	 * Gets the privileges.
	 *
	 * @return the privileges
	 */
	public final int getPrivileges() {
		return this.privilegesProperty().get();
	}

	/**
	 * Sets the privileges.
	 *
	 * @param privileges the new privileges
	 */
	public final void setPrivileges(final int privileges) {
		this.privilegesProperty().set(privileges);
	}

	/**
	 * Id utilisateur property.
	 *
	 * @return the integer property
	 */
	public final IntegerProperty idUtilisateurProperty() {
		return this.idUtilisateur;
	}

	/**
	 * Sets the id utilisateur.
	 *
	 * @param idUtilisateur the new id utilisateur
	 */
	public final void setIdUtilisateur(final int idUtilisateur) {
		this.idUtilisateurProperty().set(idUtilisateur);
	}

	/**
	 * Gets the id utilisateur.
	 *
	 * @return the id utilisateur
	 */
	public int getIdUtilisateur() {
		return this.idUtilisateurProperty().get();
	}
	
	/**
	 * Date property.
	 *
	 * @return the string property
	 */
	public final StringProperty dateProperty() {
		return this.date;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public final String getDate() {
		return this.dateProperty().get();
	}

	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public final void setDate(final String date) {
		this.dateProperty().set(date);
	}

}
