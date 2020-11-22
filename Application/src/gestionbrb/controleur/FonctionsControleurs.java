package gestionbrb.controleur;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

// TODO: Auto-generated Javadoc
/**
 * The Class FonctionsControleurs.
 */
/*
 * Fonctions utiles qui évitent la répétition dans chaque fonction
 */
public abstract class FonctionsControleurs {

	/**
	 * Affiche une popup avec un message d'erreur.
	 *
	 * @param titre the titre
	 * @param entete the entete
	 * @param texte the texte
	 */
	public static void alerteErreur(String titre, String entete, String texte) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(titre);
		alert.setHeaderText(entete);
		alert.setContentText(texte);
		alert.showAndWait();
	}
	
	/**
	 * Affiche un popup avec un message d'avertissement.
	 *
	 * @param titre the titre
	 * @param entete the entete
	 * @param texte the texte
	 */
	public static void alerteAttention(String titre, String entete, String texte) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(titre);
		alert.setHeaderText(entete);
		alert.setContentText(texte);
		alert.showAndWait();
	}
	
	/**
	 * Affiche une popup avec un message d'information.
	 *
	 * @param titre the titre
	 * @param entete the entete
	 * @param texte the texte
	 */
	public static void alerteInfo(String titre, String entete, String texte) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(titre);
		alert.setHeaderText(entete);
		alert.setContentText(texte);
		alert.showAndWait();
	}
	
	/**
	 * Extrait une chaine de caractère d'une séléction de ChoiceBox.
	 *
	 * @param string the string
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
	
	/**
	 * Retrouve un identifiant dans une chaine de caractères.
	 *
	 * @param cible la chaine de caractères à exploiter
	 * @return the int
	 */
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
	 * Extrait un numéro d'une séléction de ChoiceBox.
	 *
	 * @param cible la chaine de caractères à exploiter
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

		} catch (NumberFormatException e) {
		}
		return resultat;
	}
	
	/**
	 * Fonction pour sécuriser les mots de passes entrés.
	 *
	 * @param input la chaine à sécuriser
	 * @return the sha
	 * @throws NoSuchAlgorithmException when the algorithm requested is not available
	 */
	public static byte[] getSHA(String input) throws NoSuchAlgorithmException 
    {  
        MessageDigest md = MessageDigest.getInstance("SHA-256");  
        return md.digest(input.getBytes(StandardCharsets.UTF_8));  
    } 
    
	/**
	 * Convertit une chaine héxadécimale en chaine de caractères.
	 *
	 * @param hash la chaine héxadécimale
	 * @return the string
	 */
    public static String toHexString(byte[] hash) 
    {  
        BigInteger number = new BigInteger(1, hash);  
        StringBuilder hexString = new StringBuilder(number.toString(16));  
        while (hexString.length() < 32)  
        {  
            hexString.insert(0, '0');  
        }  
  
        return hexString.toString();  
    } 
}
