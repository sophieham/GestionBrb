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
	
	public static String getString(ChoiceBox<String> cible) { // pour ingredient : faire de la fin du -> à la fin du string
		String stringToSub = cible.getValue();
		String sFinal = null;
		try {
			switch (stringToSub.length()) {
			case 26:
				sFinal = stringToSub.substring(8, 9);
			case 27:
				sFinal = stringToSub.substring(8, 10);
			case 28:
				sFinal = stringToSub.substring(8, 11);
			default:
				System.out.println("erreur");
				break;
			}

			// affiche une exception alors que le String à bien été transformé en nombre
		} catch (NumberFormatException e) {
		}
		return sFinal;
	}
}
