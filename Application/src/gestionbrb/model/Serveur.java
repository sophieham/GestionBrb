package gestionbrb.model;


/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class Serveur extends Compte
{
	private int idServeur;

	public Serveur(String identifiant, String mot2passe, String nom, String prenom){
		super(identifiant, mot2passe, nom, prenom, 2);
	}

	public void prendreCommande() {
		// TODO implement me	
	}
	
}

