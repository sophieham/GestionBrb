package gestionbrb.controleur;

import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import gestionbrb.DAO.DAOUtilisateur;
import gestionbrb.model.Table;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Fenetre de modification ou d'ajout de tables
 * @author Sophie
 *
 */
public class ModifierTablesControleur {

	@FXML
	private TextField champNbCouvertsMin;
	@FXML
	private TextField champNbCouvertsMax;
	@FXML
	private TextField champNoTable;

	private Stage dialogStage;
	private Table table;
	private boolean okClicked = false;
	private TablesControleur parent;
	@FXML
	private ResourceBundle bundle;
	@FXML
	private Label labelnumeroTablle;
	@FXML
	private Label labelCouvMin;
	@FXML
	private Label labelCouvMax;
	@FXML
	private Button valider;
	@FXML
	private Button annuler;
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
			labelnumeroTablle.setText(bundle.getString("Table"));
			labelCouvMax.setText(bundle.getString("Max"));
			labelCouvMin.setText(bundle.getString("Min"));
			annuler.setText(bundle.getString("Annuler"));
			valider.setText(bundle.getString("Valider"));
			

			
		}
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public void setMainApp(TablesControleur parent) {
		this.setParent(parent);
	}

	/**
	 * Rempli les champs de saisie avec les informations sur la table (si il y en a)
	 * @param reservation
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void setTable(Table table) throws SQLException, ClassNotFoundException {
		this.table = table;
		champNbCouvertsMin.setText(Integer.toString(table.getNbCouvertsMin()));
		champNbCouvertsMax.setText(Integer.toString(table.getNbCouvertsMax()));
		champNoTable.setText(Integer.toString(table.getNoTable()));

	}

	/**
	 * @return true si le bouton a modifié a été appuyé, faux sinon
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Appellé quand l'utilisateur appuie sur Valider
	 * 
	 */
	@FXML
	public void actionValider() {
		if (estValide()) {
			table.setNbCouvertsMin(Integer.parseInt(champNbCouvertsMin.getText()));
			table.setNbCouvertsMax(Integer.parseInt(champNbCouvertsMax.getText()));
			table.setNoTable(Integer.parseInt(champNoTable.getText()));

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
	 * Vérifie si la saisie est conforme aux données requises
	 * 
	 * @return true si la saisie est bien conforme
	 */
	public boolean estValide() {
		String erreurMsg = "";

		if (champNoTable.getText() == null || champNoTable.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le nombre de couvert maximum\n";
		} else {
			try {
				int parse = Integer.parseInt(champNoTable.getText());
				if(parse>999) {
					erreurMsg += "Erreur! Le champ No. Table est trop grand\n";
				}
			} catch (NumberFormatException e) {
				erreurMsg += "Erreur! Le champ No. Table n'accepte que les nombres\n";
			}
		}
		
		if (champNbCouvertsMax.getText() == null || champNbCouvertsMax.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le nombre de couvert maximum\n";
		} else {
			try {
				Integer.parseInt(champNbCouvertsMax.getText());
			} catch (NumberFormatException e) {
				erreurMsg += "Erreur! Le champ couverts max. n'accepte que les nombres\n";
			}
		}
		if (champNbCouvertsMin.getText() == null || champNbCouvertsMin.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le nombre de couvert minimum\n";

		}else {
			try {
				Integer.parseInt(champNbCouvertsMin.getText());
			} catch (NumberFormatException e) {
				erreurMsg += "Erreur! Le champ couverts min. n'accepte que les nombres\n";
			}
		}

		if (erreurMsg.length() == 0) {
			return true;
		} else {
			// Affiche un message d'erreur
			FonctionsControleurs.alerteErreur("Entrée incorrecte", "Corrigez les erreurs suivantes pour pouvoir modifier la reservation",
					erreurMsg);

			return false;
		}
	}

	public TablesControleur getParent() {
		return parent;
	}

	public void setParent(TablesControleur parent) {
		this.parent = parent;
	}

}