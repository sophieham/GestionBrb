package gestionbrb.controleur;

public class dsgdsgd {

	public static void main(String[] args) {
		retrouveID("ID: 4578155 -> Entrée");
	}
	
	public static int retrouveID(String cible) {
		int resultat = 0;
		try {
			String rgx = " ->";
			String[] tabResultat = cible.split(rgx);
			String resultatStr = tabResultat[0].substring(4);
			resultat = Integer.parseInt(resultatStr);
		} 
		catch (NumberFormatException e) {
		}
		return resultat;
	}

}
