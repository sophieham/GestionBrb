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

/**
 * 
 * @author Sophie
 *
 */
public class ModifierTypesControleur {
	@FXML
	private TextField chNomType;
	
	private Stage dialogStage;
	IngredientsProduitsControleur mainApp;
	private Type type;
	private boolean okClicked = false;
	@FXML
	private ResourceBundle bundle;
	@FXML
	private Button valider;
	@FXML
	private Label nom;
	DAOUtilisateur daoUtilisateur = new DAOUtilisateur();
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
	private void loadLang(String lang, String LANG) {
		Locale locale = new Locale(lang, LANG);  
		
		ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_"+lang, locale);
		valider.setText(bundle.getString("Valider"));
		nom.setText(bundle.getString("Nom"));
	

		
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public void setMainApp(IngredientsProduitsControleur mainApp) {
		this.mainApp = mainApp;
	}
	
	public void setType(Type type) {
		this.type = type;
		chNomType.setText(type.getNomType());
	}
	
	public boolean isOkClicked() {
		return okClicked;
	}
	
	@FXML
	public void actionValider() {
		
		try {
			if (estValide()) {
				type.setNomType(chNomType.getText());
				okClicked = true;
				dialogStage.close();
			}
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur!", "Erreur d'éxecution", "D�tails: "+e);
		}
		
	}
	
	@FXML
	private void actionAnnuler() {
		dialogStage.close();
	}
	
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
