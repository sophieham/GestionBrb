package gestionbrb.controleur;

import gestionbrb.IngredientsProduits;
import gestionbrb.model.Type;
import javafx.fxml.FXML;
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
	IngredientsProduits mainApp;
	private Type type;
	private boolean okClicked = false;
	
	@FXML
	private void initialize() {
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public void setMainApp(IngredientsProduits mainApp) {
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
			FonctionsControleurs.alerteErreur("Erreur!", "Erreur d'éxecution", "Détails: "+e);
		}
	}@FXML
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
