package gestionbrb.model;


/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class Serveur extends Utilisateur
{
	private int idServeur;

	public Serveur(String identifiant, String mot2passe, String nom, String prenom, int idServeur){
		super(0, identifiant, mot2passe, nom, prenom, 2);
		this.idServeur = idServeur;
	}

	public void prendreCommande() {
		// TODO implement me	
	}
	
}

