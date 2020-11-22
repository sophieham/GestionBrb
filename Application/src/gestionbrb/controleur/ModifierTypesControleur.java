package gestionbrb.controleur;

import java.util.Locale;
import java.util.ResourceBundle;

import gestionbrb.DAO.DAOUtilisateur;
import gestionbrb.model.Type;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

// TODO: Auto-generated Javadoc
/**
 * Fenetre de modification/ajout de type.
 * @author Leo
 *
 */
public class ModifierTypesControleur {
	
	/** The ch nom type. */
	@FXML
	private TextField chNomType;
	
	/** The dialog stage. */
	private Stage dialogStage;
	
	/** The main app. */
	IngredientsProduitsControleur mainApp;
	
	/** The type. */
	private Type type;
	
	/** The ok clicked. */
	private boolean okClicked = false;
	
	/** The bundle. */
	@FXML
	private ResourceBundle bundle;
	
	/** The valider. */
	@FXML
	private Button valider;
	
	/** The nom. */
	@FXML
	private Label nom;
	
	/** The dao utilisateur. */
	DAOUtilisateur daoUtilisateur = new DAOUtilisateur();
	
	/**
	 * Initialise la classe controleur avec les données par défaut du tableau <br>
	 * Charge les fichiers de langue nécessaires à  la traduction.
	 * <br>
	 * Affiche un message d'erreur si il y a un problème.
	 */
	
	@FXML
	private void initialize() {
		try {
			String langue = daoUtilisateur.recupererLangue(ConnexionControleur.getUtilisateurConnecte().getIdUtilisateur());
			switch(langue) {
			case "fr":
				loadLang("fr", "FR");
				break;
			case "en":
				loadLang("en", "US");
				break;
			case "zh":
				loadLang("zh", "CN");
				break;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * charger la langue.
	 *
	 * @param lang the lang
	 * @param LANG the lang
	 */
	
	private void loadLang(String lang, String LANG) {
		Locale locale = new Locale(lang, LANG);  
		
		ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_"+lang, locale);
		valider.setText(bundle.getString("Valider"));
		nom.setText(bundle.getString("Nom"));
	}

	/**
	 * definir fenetre en cours.
	 *
	 * @param dialogStage the new dialog stage
	 */
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	/**
	 * relie au controleur principal.
	 *
	 * @param mainApp the new main app
	 */
	
	public void setMainApp(IngredientsProduitsControleur mainApp) {
		this.mainApp = mainApp;
	}
	
	/**
	 * Remplit les champs avec les données déjà  existantes.
	 *
	 * @param type the new type
	 */
	
	public void setType(Type type) {
		this.type = type;
		chNomType.setText(type.getNomType());
	}
	
	/** 
     * @return true si le bouton a modifié a été appuyé, faux sinon
     */
	
	public boolean isOkClicked() {
		return okClicked;
	}
	
	 /**
 	 * Appellé quand l'utilisateur appuie sur "valider" <br>
 	 * Modifie si tout les champs sont valides.
 	 */
	
	@FXML
	public void actionValider() {
		
		try {
			if (estValide()) {
				type.setNomType(chNomType.getText());
				okClicked = true;
				dialogStage.close();
			}
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur!", "Erreur d'éxecution", "Dï¿½tails: "+e);
		}
		
	}
	
	 /**
     * Appellé quand le bouton annuler est appuyé.
     * Ferme la page sans sauvegarder.
     */
	
	@FXML
	private void actionAnnuler() {
		dialogStage.close();
	}
	
	/**
	 * Vérifie si la saisie est conforme aux données requises.
	 * Affiche un message d'erreur si il y a au moins une erreur.
	 * @return true si la saisie est bien conforme, false sinon
	 */
	
	public boolean estValide() {
		String erreurMsg = "";

		if (chNomType.getText() == null || chNomType.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le type\n";
		}
		if (erreurMsg.length() == 0) {
			return true;
		} else 
			return false;
	}


}
