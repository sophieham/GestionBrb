package gestionbrb.controleur;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gestionbrb.DAO.DAOIngredients;
import gestionbrb.DAO.DAOType;
import gestionbrb.model.Produit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * Fenetre de modification/ajout de produits
 * @author Leo
 */
public class ModifierProduitsControleur {
	@FXML
	private TextField chNomProduit;
	@FXML
	private TextField chPrixProduit;
	@FXML
	private TextField chQuantiteProduit;
	@FXML
	private TextArea chDescription;
	@FXML
	private ObservableList<String> listeType = FXCollections.observableArrayList();
	@FXML
	private ChoiceBox<String> chChoixType;
	@FXML
	private VBox vb;
	private ArrayList<RadioButton> listebouton = new ArrayList<>();
	private ArrayList<String> listeNomIngredient = new ArrayList<>();
	private Produit produit;
	private Stage dialogStage;
	private boolean okClicked = false;
	IngredientsProduitsControleur mainApp;
	
	DAOType daoType = new DAOType();
	DAOIngredients daoIngredients = new DAOIngredients();
	
	
	/**
	 * Initialise les valeurs du menu déroulant et des boutons à cocher avec des valeurs provenant de la base de données.
	 */
	@FXML
	private void initialize() {
		try {
		listeNomIngredient.addAll(daoIngredients.listeIngredients());
		chChoixType.setItems(daoType.choixType());
		for (int i = 0; i < listeNomIngredient.size(); i++) {
			RadioButton radiobtn = new RadioButton(listeNomIngredient.get(i));
			listebouton.add(radiobtn);
			}
			vb.getChildren().addAll(listebouton);
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur!", "Erreur d'éxecution", "Détails: " + e);
			e.printStackTrace();
		}
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public void setMainApp(IngredientsProduitsControleur mainApp) {
		this.mainApp = mainApp;
	}
	
	/**
	 * Affiche les détails du produits (notamment pour la modification de produits)
	 * 
	 * @param produit le produit qu'on affiche
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void setProduit(Produit produit) throws SQLException, ClassNotFoundException {
		this.produit = produit;
		chNomProduit.setText(produit.getNomProduit());
		chPrixProduit.setText(Double.toString(produit.getPrixProduit()));
		chQuantiteProduit.setText(Integer.toString(produit.getQuantiteProduit()));
		chDescription.setText(produit.getDescriptionProduit());
		chChoixType.setValue(produit.getType());
		
	}
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