package gestionbrb.controleur;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import gestionbrb.DAO.DAOUtilisateur;
import gestionbrb.model.Utilisateur;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

// TODO: Auto-generated Javadoc
/**
 * Gérer les utilisateurs (Modifier/Ajouter des serveurs ou administrateurs).
 *
 * @author Roman
 */
public class ModifierUtilisateurControleur {

	/** The champ nom. */
	@FXML 
	private TextField champNom;
	
	/** The champ prenom. */
	@FXML
	private TextField champPrenom;
	
	/** The champ identifiant. */
	@FXML
	private TextField champIdentifiant;
	
	/** The champ mot 2 passe. */
	@FXML
	private TextField champMot2Passe;
	
	/** The bouton admin. */
	@FXML
	private RadioButton boutonAdmin;
	
	/** The bouton serveur. */
	@FXML
	private RadioButton boutonServeur;
	
	/** The labelprenom. */
	@FXML
	private Label labelprenom;
	
	/** The labelnom. */
	@FXML
	private Label labelnom;
	
	/** The labelindentifiant. */
	@FXML
	private Label labelindentifiant;
	
	/** The labelmot. */
	@FXML
	private Label labelmot;
	
	/** The labelrole. */
	@FXML
	private Label labelrole;
	
	/** The valider. */
	@FXML
	private Button valider;
	
	/** The annuler. */
	@FXML
	private Button annuler;
	
	/** The bundle. */
	@FXML
	private ResourceBundle bundle;
	
	/** The dao utilisateur. */
	DAOUtilisateur daoUtilisateur = new DAOUtilisateur();
	
	/** The dialog stage. */
	private Stage dialogStage;
	
	/** The compte. */
	private Utilisateur compte;
	
	/** The ok clicked. */
	private boolean okClicked = false;
	
	/** The role. */
	private int role;
	
	/**
	 * Sets the role admin.
	 */
	@FXML
	private void setRoleAdmin() {
		role = 1;
		boutonServeur.setSelected(false);

	}
	
	/**
	 * Sets the role serveur.
	 */
	@FXML
	private void setRoleServeur() {
		role = 1;
		boutonAdmin.setSelected(false);

	}
	
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
		valider.setText(bundle.getString("Valider"));
		annuler.setText(bundle.getString("Annuler"));
		labelrole.setText(bundle.getString("Role"));
		labelmot.setText(bundle.getString("MotdePass"));
		labelindentifiant.setText(bundle.getString("Identifiant"));
		labelnom.setText(bundle.getString("Nom"));
		labelprenom.setText(bundle.getString("Prenom"));
		boutonAdmin.setText(bundle.getString("Admin"));
		boutonServeur.setText(bundle.getString("Serveur"));
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
	 * Sets the utilisateur.
	 *
	 * @param compte the new utilisateur
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public void setUtilisateur(Utilisateur compte) throws SQLException, ClassNotFoundException {
		this.compte = compte;
		champNom.setText(compte.getNom());
		champPrenom.setText(compte.getPrenom());
		champIdentifiant.setText(compte.getIdentifiant());
		champMot2Passe.setText("");
		if(compte.getPrivileges()==1)
			boutonAdmin.setSelected(true);
		if(compte.getPrivileges() == 0)
			boutonServeur.setSelected(true);

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
	 *
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 */
	@FXML
	public void actionValider() throws NoSuchAlgorithmException {
		if (estValide()) {
			if(boutonAdmin.isSelected()) {
				role = 1;
			}
			if (boutonServeur.isSelected())
				role = 0;
			compte.setNom(champNom.getText());
			compte.setPrenom(champPrenom.getText());
			compte.setIdentifiant(champIdentifiant.getText());
			compte.setMot2passe(FonctionsControleurs.toHexString(FonctionsControleurs.getSHA(champMot2Passe.getText())));
			compte.setPrivileges(role);

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

		if (!boutonAdmin.isSelected() && !boutonServeur.isSelected()) {
			erreurMsg += "Veuillez cocher le role\n";
		} 

		if (champPrenom.getText() == null || champPrenom.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le champ prenom\n";

		}
		if (champNom.getText() == null || champNom.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le champ nom\n";

		}
		if (champMot2Passe.getText() == null || champMot2Passe.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le champ mot de passe\n";

		}
		if (champIdentifiant.getText() == null || champIdentifiant.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le champ Identifiant\n";
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