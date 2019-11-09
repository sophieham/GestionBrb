package gestionbrb.model;

import java.util.ArrayList;

public class Administrateur extends Utilisateur
{
	ArrayList<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();

	public Administrateur(String identifiant, String mot2passe, String nom, String prenom ) {
		super(identifiant, mot2passe, nom, prenom, 0);
		// TODO Auto-generated constructor stub
	}

	public void modifierUtilisateurs(String identifiant) {
		
		
	}
	public void ajouterUtilisateur(Utilisateur user) {
		utilisateurs.add(user);
	}

}

