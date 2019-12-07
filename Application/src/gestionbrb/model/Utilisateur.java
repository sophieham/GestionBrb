package gestionbrb.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Personne utilisant le programme (peut etre un administrateur, un serveur ou un client)
 */
public class Utilisateur {
	private final IntegerProperty idUtilisateur;
	private final StringProperty identifiant;
	private final StringProperty motdepasse;
	private final StringProperty nom;
	private final StringProperty prenom;
	private final IntegerProperty privileges;
	private final StringProperty date;

	/**
	 * Constructeur pour de utilisateur
	 * 
	 * @param identifiant
	 * @param mot2passe
	 * @param nom
	 * @param prenom
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

	public Utilisateur() {
		this(0, "", "", "", "", 0);
	}
	
	
	
	public Utilisateur(int id, String identifiant, String date) {
		this.idUtilisateur = new SimpleIntegerProperty(id);
		this.privileges = new SimpleIntegerProperty(-1);
		this.identifiant = new SimpleStringProperty(identifiant);
		this.motdepasse = new SimpleStringProperty(null);
		this.nom = new SimpleStringProperty(null);
		this.prenom = new SimpleStringProperty(null);
		this.date = new SimpleStringProperty(date);
	}

	public Utilisateur(int id, String identifiant, String nom, String prenom, int privileges) {
		this.idUtilisateur = new SimpleIntegerProperty(id);
		this.privileges = new SimpleIntegerProperty(privileges);
		this.identifiant = new SimpleStringProperty(identifiant);
		this.motdepasse = new SimpleStringProperty("");
		this.nom = new SimpleStringProperty(nom);
		this.prenom = new SimpleStringProperty(prenom);	
		this.date = new SimpleStringProperty(null);
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

	public final StringProperty motdepasseProperty() {
		return this.motdepasse;
	}

	public final String getMotdepasse() {
		return this.motdepasseProperty().get();
	}

	public final void setMot2passe(final String mot2passe) {
		this.motdepasseProperty().set(mot2passe);
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

	public final IntegerProperty idUtilisateurProperty() {
		return this.idUtilisateur;
	}

	public final void setIdUtilisateur(final int idUtilisateur) {
		this.idUtilisateurProperty().set(idUtilisateur);
	}

	public int getIdUtilisateur() {
		return this.idUtilisateurProperty().get();
	}
	
	public final StringProperty dateProperty() {
		return this.date;
	}

	public final String getDate() {
		return this.dateProperty().get();
	}

	public final void setDate(final String date) {
		this.dateProperty().set(date);
	}

}
