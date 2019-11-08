package gestionbrb.model;

import java.util.ArrayList;

public class Administrateur extends Compte
{
	ArrayList<Utilisateurs> utilisateurs = new ArrayList<Utilisateurs>();

	public Administrateur(String identifiant, String mot2passe, String nom, String prenom ) {
		super(identifiant, mot2passe, nom, prenom, 0);
		// TODO Auto-generated constructor stub
	}

	public void modifierUtilisateurs(String identifiant) {
		
		
	}
	public void ajouterUtilisateur(Utilisateurs user) {
		utilisateurs.add(user);
	}

}

