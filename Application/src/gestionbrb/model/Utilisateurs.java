package gestionbrb.model;

/**
 * <!-- begin-user-doc --> <!-- end-user-doc -->
 * 
 * 
 * @generated
 */
public class Utilisateurs extends Compte {
	private int idServeur;

	public Utilisateurs(String identifiant, String mot2passe, String nom, String prenom){
		super(identifiant, mot2passe, nom, prenom, 1);
	}

	public void prendreCommande() {
		// TODO implement me	
	}
	
}
