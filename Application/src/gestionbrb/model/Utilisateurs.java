package gestionbrb.model;

/**
 * <!-- begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public abstract class Utilisateurs {
	private String identifiant = "";
	private String mot2passe = "";
	private String nom = "";
	private String prenom = "";
	private int privileges = 1;

	public Utilisateurs(String identifiant, String mot2passe, String nom, String prenom, int privilèges) {
		this.identifiant = identifiant;
		this.mot2passe = mot2passe;
		this.nom = nom;
		this.prenom = prenom;
		this.privileges = privilèges;
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public String getMot2passe() {
		return mot2passe;
	}

	public void setMot2passe(String mot2passe) {
		this.mot2passe = mot2passe;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public int getPrivileges() {
		return privileges;
	}

	public void setPrivileges(int privileges) {
		this.privileges = privileges;
	}

}
