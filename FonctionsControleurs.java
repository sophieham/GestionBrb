package gestionbrb.controleur;

import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert.AlertType;

public abstract class FonctionsControleurs {

	public static void alerteErreur(String titre, String entete, String texte) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(titre);
		alert.setHeaderText(entete);
		alert.setContentText(texte);
		alert.showAndWait();
	}
	public static void alerteAttention(String titre, String entete, String texte) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(titre);
		alert.setHeaderText(entete);
		alert.setContentText(texte);
		alert.showAndWait();
	}
	public static void alerteInfo(String titre, String entete, String texte) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(titre);
		alert.setHeaderText(entete);
		alert.setContentText(texte);
		alert.showAndWait();
	}
	
	/**
	 * Extrait une chaine de caractère d'une séléction de ChoiceBox
	 * 
	 * @return numero de la table
	 */
	
	public static String getString(String string) { 
		
		String stringToSub = string;
		String resultat = null;
		switch (stringToSub.length()) {
		case 13:
			resultat = stringToSub.substring(9);
		case 14:
			resultat = stringToSub.substring(10);
		case 15:
			resultat = stringToSub.substring(11);
		default:
			System.out.println("erreur");
			break;
		}
		return resultat;
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

	/**
	 * Extrait un numéro d'une séléction de ChoiceBox
	 * 
	 * @return numero de la table
	 */
	public static int getNumero(String cible) {
		String stringNoTable = cible;
		String subStr = null;
		int resultat = 0;
		try {
			switch (stringNoTable.length()) {
			case 26:
				subStr = stringNoTable.substring(8, 9);
				resultat = Integer.parseInt(subStr);
			case 27:
				subStr = stringNoTable.substring(8, 10);
				resultat = Integer.parseInt(subStr);
			case 28:
				subStr = stringNoTable.substring(8, 11);
				resultat = Integer.parseInt(subStr);
			default:
				System.out.println("erreur");
				break;
			}

			// affiche une exception alors que le String à bien été transformé en nombre
		} catch (NumberFormatException e) {
		}
		return resultat;
	}
}
