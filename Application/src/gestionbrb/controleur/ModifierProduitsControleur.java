package gestionbrb.controleur;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import gestionbrb.DAO.DAOIngredients;
import gestionbrb.DAO.DAOType;
import gestionbrb.DAO.DAOUtilisateur;
import gestionbrb.model.Produit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


// TODO: Auto-generated Javadoc
/**
 * Fenetre de modification/ajout de produits.
 *
 * @author Leo
 */
public class ModifierProduitsControleur {
	
	/** The ch nom produit. */
	@FXML
	private TextField chNomProduit;
	
	/** The ch prix produit. */
	@FXML
	private TextField chPrixProduit;
	
	/** The ch quantite produit. */
	@FXML
	private TextField chQuantiteProduit;
	
	/** The ch description. */
	@FXML
	private TextArea chDescription;
	
	/** The liste type. */
	@FXML
	private ObservableList<String> listeType = FXCollections.observableArrayList();
	
	/** The ch choix type. */
	@FXML
	private ChoiceBox<String> chChoixType;
	
	/** The vb. */
	@FXML
	private VBox vb;
	
	/** The type. */
	@FXML
	private Label type;
	
	/** The nom. */
	@FXML
	private Label nom;
	
	/** The description. */
	@FXML
	private Label description;
	
	/** The qte. */
	@FXML
	private Label qte;
	
	/** The selection. */
	@FXML
	private Label selection;
	
	/** The valider. */
	@FXML
	private Button valider;
	
	/** The prix. */
	@FXML
	private Label prix;
	
	/** The container. */
	@FXML
	private ScrollPane container;
	
	/** The listebouton. */
	private ArrayList<RadioButton> listebouton = new ArrayList<>();
	
	/** The liste nom ingredient. */
	private ArrayList<String> listeNomIngredient = new ArrayList<>();
	
	/** The produit. */
	private Produit produit;
	
	/** The dialog stage. */
	private Stage dialogStage;
	
	/** The ok clicked. */
	private boolean okClicked = false;
	
	/** The main app. */
	IngredientsProduitsControleur mainApp;
	
	/** The bundle. */
	@FXML
	private ResourceBundle bundle;
	
	/** The dao utilisateur. */
	DAOUtilisateur daoUtilisateur = new DAOUtilisateur();
	
	/** The dao type. */
	DAOType daoType = new DAOType();
	
	/** The dao ingredients. */
	DAOIngredients daoIngredients = new DAOIngredients();
	
	
	/**
	 * Initialise les valeurs du menu déroulant et des boutons à  cocher avec des valeurs provenant de la base de données.
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
		listeNomIngredient.addAll(daoIngredients.listeIngredients());
		chChoixType.setItems(daoType.choixType());
		for (int i = 0; i < listeNomIngredient.size(); i++) {
			RadioButton radiobtn = new RadioButton(listeNomIngredient.get(i));
			listebouton.add(radiobtn);
			}
			vb.getChildren().addAll(listebouton);
			container = new ScrollPane(vb);
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur!", "Erreur d'éxecution", "Détails: " + e);
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
		type.setText(bundle.getString("Type"));
		prix.setText(bundle.getString("Prix"));
		nom.setText(bundle.getString("Nom"));
		nom.setText(bundle.getString("Nom"));
		description.setText(bundle.getString("Description"));
		qte.setText(bundle.getString("qte"));
		valider.setText(bundle.getString("Valider"));
		selection.setText(bundle.getString("selection"));
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
	 * Affiche les détails du produits (notamment pour la modification de produits).
	 *
	 * @param produit le produit qu'on affiche
	 * @throws SQLException the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public void setProduit(Produit produit) throws SQLException, ClassNotFoundException {
		this.produit = produit;
		chNomProduit.setText(produit.getNomProduit());
		chPrixProduit.setText(Double.toString(produit.getPrixProduit()));
		chQuantiteProduit.setText(Integer.toString(produit.getQuantiteProduit()));
		chDescription.setText(produit.getDescriptionProduit());
		chChoixType.setValue(produit.getType());
		
	}
	
	/** 
     * @return true si le bouton a modifié a été appuyé, faux sinon
     */
	
	public boolean isOkClicked() {
		return okClicked;
	}
	
	/**
	 * Appellé lorsqu'on valide l'ajout/la modification. <br>
	 * Vérifie les valeurs rentrées et concatène le nom des ingredients cochés pour les enrengistrer dans une liste.
	 */
	
	@FXML
	public void actionValider() {
	List<String> liste = new ArrayList<>();
	String ingredients = "";
		try {
			for (int j = 0; j < listebouton.size(); j++) {
				if (listebouton.get(j).isSelected()) {
					String listeSelection = listebouton.get(j).toString();
					String ingSplit[] = listeSelection.split("'"); 
					liste.add(ingSplit[1]);
					ingredients = String.join(", ", liste);
				}
			}
			if (estValide()) {
				produit.setNomProduit(chNomProduit.getText());
				produit.setPrixProduit(Double.parseDouble(chPrixProduit.getText()));
				produit.setQuantiteProduit(Integer.parseInt(chQuantiteProduit.getText()));
				produit.setDescriptionProduit(chDescription.getText());
				produit.setType(chChoixType.getValue());
				produit.setIngredients(ingredients);
				okClicked = true;
				dialogStage.close();
			}
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur!", "Erreur d'éxecution", "Détails: "+e);
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

		if (chQuantiteProduit.getText() == null || chQuantiteProduit.getText().length() == 0) {
			erreurMsg += "Veuillez remplir la quantité du produit\n";
		} else {
			try {
				Integer.parseInt(chQuantiteProduit.getText());
			} catch (NumberFormatException e) {
				erreurMsg += "Erreur! Le champ Quantité n'accepte que les nombres\n";
			}
		}
		
		if (chPrixProduit.getText() == null || chPrixProduit.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le prix du produit\n";
		} else {
			try {
				Double.parseDouble(chPrixProduit.getText());
			} catch (NumberFormatException e) {
				erreurMsg += "Erreur! Le champ prix n'accepte que les nombres\n";
			}
		}
		if (chNomProduit.getText() == null || chNomProduit.getText().length() == 0) {
			erreurMsg += "Veuillez remplir le nom du produit\n";
			}
		
		if (chChoixType.getValue() == null) {
			erreurMsg += "Veuillez saisir le type du produit\n";
			}
		
		if (chDescription.getText() == null || chDescription.getText().length() == 0) {
			erreurMsg += "Veuillez décrire le produit\n";
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