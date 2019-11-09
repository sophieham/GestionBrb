package gestionbrb.controleur;

import javafx.scene.control.Alert;
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
}
