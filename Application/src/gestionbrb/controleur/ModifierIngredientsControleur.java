package gestionbrb.controleur;

import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import gestionbrb.DAO.DAOFournisseur;
import gestionbrb.DAO.DAOUtilisateur;
import gestionbrb.model.Ingredients;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

// TODO: Auto-generated Javadoc
/**
 * Fenetre de modification/ajout des ingredients.
 *
 * @author Leo
 */

public class ModifierIngredientsControleur {
	
	/** The ch nom ingredient. */
	@FXML
	private TextField chNomIngredient;
	
	/** The ch prix ingredient. */
	@FXML
	private TextField chPrixIngredient;
	
	/** The ch quantite ingredient. */
	@FXML
	private TextField chQuantiteIngredient;
	
	/** The liste fournisseur. */
	@FXML
	private ObservableList<String> listeFournisseur = FXCollections.observableArrayList();
	
	/** The ch choix fournisseur. */
	@FXML
	private ChoiceBox<String> chChoixFournisseur;
	
	/** The prix. */
	@FXML
	private Label prix;
	
	/** The qte. */
	@FXML
	private Label qte;
	
	/** The nom. */
	@FXML
	private Label nom;
	
	/** The fournisseur. */
	@FXML
	private Label fournisseur;
	
	/** The valider. */
	@FXML
	private Button valider;
	
	/** The bundle. */
	@FXML
	private ResourceBundle bundle;
	
	/** The dao utilisateur. */
	DAOUtilisateur daoUtilisateur = new DAOUtilisateur();
	
	/** The dialog stage. */
	private Stage dialogStage;
	
	/** The main app. */
	IngredientsProduitsControleur mainApp;
	
	/** The ingredient. */
	private Ingredients ingredient;
	
	/** The ok clicked. */
	private boolean okClicked = false;
	
	/** The dao fournisseur. */
	DAOFournisseur daoFournisseur = new DAOFournisseur();
	
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
		try {
			
			chChoixFournisseur.setItems(daoFournisseur.choixFournisseur());
			
		} catch (Exception e) {
			e.printStackTrace();
			FonctionsControleurs.alerteErreur("Erreur!", "Erreur d'éxecution", "Détails: "+e);
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
		prix.setText(bundle.getString("Prix"));
		qte.setText(bundle.getString("qte"));
		nom.setText(bundle.getString("Nom"));
		fournisseur.setText(bundle.getString("Fournisseur"));
		valider.setText(bundle.getString("Valider"));	
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
	 * Affiche les détails des ingredients (notamment pour la modification des ingredients).
	 *
	 * @param ingredient l'ingredient qu'on affiche
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	
	public void setIngredients(Ingredients ingredient) throws SQLException, ClassNotFoundException {
		this.ingredient = ingredient;
		chNomIngredient.setText(ingredient.getNomIngredient());
		chPrixIngredient.setText(Double.toString(ingredient.getPrixIngredient()));
		chQuantiteIngredient.setText(Integer.toString(ingredient.getQuantiteIngredient()));
		chChoixFournisseur.setValue(ingredient.getFournisseur());
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
	public void actionValiderIngredient() {
		
		try {
			if (estValide()) {
				ingredient.setNomIngredient(chNomIngredient.getText());
				ingredient.setPrixIngredient(Double.parseDouble(chPrixIngredient.getText()));
				ingredient.setQuantiteIngredient(Integer.parseInt(chQuantiteIngredient.getText()));
				ingredient.setFournisseur(chChoixFournisseur.getValue());

				okClicked = true;
				dialogStage.close();
			}
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Erreur d'éxecution", "Détails: "+e);
			e.printStackTrace();
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
	 * Vérifie si les entrées sont correctes. <br>
	 * A chaque fois qu'une entrée n'est pas valide, il incrémente le compteur d'erreurs 
	 * et affiche ensuite les erreurs dans une boite de dialogue.
	 * 
	 * @return true si il n'y a pas d'erreur, false sinon
	 */
	
	public boolean estValide() {
		String erreurMsg = "";

		if (chQuantiteIngredient.getText() == null || chQuantiteIngredient.getText().length() == 0) {
			erreurMsg += "Veuillez remplir la quantité de l'ingrédient\n";
		} else {
			try {
				Integer.parseInt(chQuantiteIngredient.getText());
			} catch (NumberFormatException e) {
				erreurMsg += "Erreur! Le champ Quantité n'accepte que les nombres\n";
			}
		}
		
		if (chPrixIngredient.getText() == null || chPrixIngredient.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le prix de l'ingredient\n";
		} else {
			try {
				Double.parseDouble(chPrixIngredient.getText());
			} catch (NumberFormatException e) {
				erreurMsg += "Erreur! Le champ prix n'accepte que les nombres\n";
			}
		}
		if (chNomIngredient.getText() == null || chNomIngredient.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le nom de l'ingredient\n";
			}
		if (erreurMsg.length() == 0) {
			return true;
		} else {
			// Affiche un message d'erreur
			FonctionsControleurs.alerteErreur("Entrée incorrecte", "Corrigez les erreurs suivantes pour pouvoir modifier les informations",
					erreurMsg);

			return false;
		}
	}
	


}