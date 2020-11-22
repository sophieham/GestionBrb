package gestionbrb.controleur;

import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gestionbrb.DAO.DAOUtilisateur;
import gestionbrb.model.Fournisseur;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

// TODO: Auto-generated Javadoc
/**
 * The Class ModifierFournisseurControleur.
 *
 * @author Roman
 */
public class ModifierFournisseurControleur {

	/** The champ nom. */
	@FXML 
	private TextField champNom;
	
	/** The champ num tel. */
	@FXML
	private TextField champNumTel;
	
	/** The champ mail. */
	@FXML
	private TextField champMail;
	
	/** The champ adresse. */
	@FXML
	private TextField champAdresse;
	
	/** The champ ville. */
	@FXML
	private TextField champVille;
	
	/** The champ code postal. */
	@FXML 
	private TextField champCodePostal;
	
	/** The nom. */
	@FXML
	private Label nom;
	
	/** The postal. */
	@FXML
	private Label postal;
	
	/** The adresse. */
	@FXML
	private Label adresse;
	
	/** The mail. */
	@FXML
	private Label mail;	
	
	/** The ville. */
	@FXML
	private Label ville;
	
	/** The num tel. */
	@FXML
	private Label numTel;
	
	/** The valider. */
	@FXML
	private Button valider;

	/** The dialog stage. */
	private Stage dialogStage;
	
	/** The fournisseur. */
	private Fournisseur fournisseur;
	
	/** The ok clicked. */
	private boolean okClicked = false;
	
	/** The bundle. */
	@FXML
	private ResourceBundle bundle;
	
	/** The dao utilisateur. */
	DAOUtilisateur daoUtilisateur = new DAOUtilisateur();

	/**
	 * Initialize.
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
	 * Load lang.
	 *
	 * @param lang the lang
	 * @param LANG the lang
	 */
	private void loadLang(String lang, String LANG) {
		Locale locale = new Locale(lang, LANG);  
		
		ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_"+lang, locale);
		numTel.setText(bundle.getString("telephone"));
		mail.setText(bundle.getString("Mail"));
		adresse.setText(bundle.getString("Adresse"));
		nom.setText(bundle.getString("Nom"));
		postal.setText(bundle.getString("postal"));
		ville.setText(bundle.getString("Ville"));
		valider.setText(bundle.getString("Valider"));
		
	}

	/**
	 * Sets the dialog stage.
	 *
	 * @param dialogStage the new dialog stage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Sets the fournisseur.
	 *
	 * @param fournisseur the new fournisseur
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public void setFournisseur(Fournisseur fournisseur) throws SQLException, ClassNotFoundException {
		this.fournisseur = fournisseur;
		champNom.setText(fournisseur.getNom());
		champAdresse.setText(fournisseur.getAdresse());
		champMail.setText(fournisseur.getMail());
		champNumTel.setText(fournisseur.getNumTel());
		champVille.setText(fournisseur.getNomVille());
		champCodePostal.setText(Integer.toString(fournisseur.getCodePostal()));
		

	}

	/**
	 * Checks if is ok clicked.
	 *
	 * @return true si le bouton a modifié a été appuyé, faux sinon
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Appellé quand l'utilisateur appuie sur Valider.
	 */
	@FXML
	public void actionValider() {
		if (estValide()) {
			fournisseur.setNom(champNom.getText());
			fournisseur.setAdresse(champAdresse.getText());
			fournisseur.setMail(champMail.getText());
			fournisseur.setNumTel(champNumTel.getText());
			fournisseur.setNomVille(champVille.getText());
			fournisseur.setCodePostal(Integer.parseInt(champCodePostal.getText()));
			okClicked = true;
			dialogStage.close();
		}	
	}

	/**
	 * Appellé quand le bouton annuler est appuyé. Ferme la page sans sauvegarder.
	 */
	@FXML
	private void actionAnnuler() {
		dialogStage.close();
	}

	/**
	 * Vérifie si la saisie est conforme aux données requises.
	 *
	 * @return true si la saisie est bien conforme
	 */
	public boolean estValide() {
		String erreurMsg = "";

		

		if (champAdresse.getText() == null || champAdresse.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le champ Adresse\n";

		}
		if (champCodePostal.getText() == null || champCodePostal.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le champ Code Postal\n";

		}
		if (champNom.getText() == null || champNom.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le champ nom\n";

		}
		if (champMail.getText() == null || champMail.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le champ mail\n";

		}
		if (champNumTel.getText() == null || champNumTel.getText().length() == 0) {
			erreurMsg += "Veuillez rentrer le numéro de téléphone\n";
		} else {
			Pattern p = Pattern.compile("(0|\\+)[0-9]{8,12}");
			Matcher m = p.matcher(champNumTel.getText());
			if (!(m.find() && m.group().equals(champNumTel.getText()))) {
				erreurMsg += "Erreur! Le champ no. téléphone n'accepte que les numéros commençant par + ou 0 et ayant une longueur entre 8 et 12 chiffres\n";
			}
		}
		if (champVille.getText() == null || champVille.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le champ ville\n";
		}


		if (erreurMsg.length() == 0) {
			return true;
		} else {
			// Affiche un message d'erreur
			FonctionsControleurs.alerteErreur("Entrée incorrecte", "Corrigez les erreurs suivantes pour pouvoir modifier la reservation",erreurMsg);

			return false;
		}
	}

}