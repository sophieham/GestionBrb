package gestionbrb.controleur;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import gestionbrb.DAO.DAOCommande;
import gestionbrb.DAO.DAOUtilisateur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.event.ActionEvent;

/**
 * 
 * @author Linxin
 *
 */
public class ParametresControleur implements Initializable{
	@FXML
	private TextField chDevise;
	@FXML
	private  Label lblTextByController;
	@FXML
	private ChoiceBox<String> choicebox;
	@FXML
	private TitledPane choiceCouleur;
	@FXML
	private Button btnHello;
	@FXML
	private ResourceBundle bundle;
	@FXML
	private Label choiceBackground;
	@FXML
	private TitledPane title;
	@FXML
	private ColorPicker btnbackground;
	@FXML
	private AnchorPane AnchorPane;
	
	private MenuPrincipalControleur parent;
 DAOUtilisateur daoUtilisateur = new DAOUtilisateur();
 DAOCommande daoCommande = new DAOCommande();


	@Override
	   public void initialize(URL location, ResourceBundle resources) {
		
	    bundle = resources;
		

	
		
	}
	
		
	

	@FXML
	 public void Onbutton(ActionEvent event) {
		//btnHello.setText("change");
		
		try {
			try {
				Locale locale = new Locale("fr", "FR");

				ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_fr", locale);
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/MenuPrincipal.fxml"), bundle);
				Parent menuPrincipal = (Parent) loader.load();
				AnchorPane.getChildren().setAll(menuPrincipal); // remplace la fenetre de connexion par celle du menu principal
				
				MenuPrincipalControleur controller = loader.getController();
				controller.setParent(this);
			} catch (Exception e) {
				FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Une erreur est survenue","Détails: "+e);
				e.printStackTrace();
			}
	            
	          
	            
	        }
	        catch (Exception e1) {
	            e1.printStackTrace();
	        }
        }
	 
	

		public void OnBackground(ActionEvent event) {
		Color background = btnbackground.getValue();
		AnchorPane.setBackground(new Background(new BackgroundFill(background, null, null)));
		
	}
	
		@FXML
		public void actionValiderDevise() {
			chDevise.getText().toUpperCase();
			try {
				if (estValide()) {
				daoCommande.majDevise(chDevise.getText());
				FonctionsControleurs.alerteInfo("Modification effectuée", null, "La modification à bien été prise en compte!");
				}
			} catch (Exception e) {
				FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Une erreur est survenue","Détails: "+e);
				e.printStackTrace();
			}
		}
		
		public boolean estValide() {
			String erreurMsg = "";
		if (chDevise.getText() == null || chDevise.getText().length() == 0) {
			erreurMsg += "Veuillez remplir la devise\n";
			}
		if (chDevise.getText().length() >3) {
			erreurMsg += "Veuillez remplir une devise code ISO valide\n";
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
		
		public void choiceMade(ActionEvent event) throws IOException {
		try {
			String output = choicebox.getSelectionModel().getSelectedItem().toString();
			switch(output) {
			case "Chinese":
				loadLang("zh", "CN");
				break;
			case "English":
				loadLang("en", "US");
				break;
			case "Francais":
				loadLang("fr", "FR");
				break;
			}
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Changement de langue impossible", "Détails: " + e);
			e.printStackTrace();
		}
		
	}


		private void loadLang(String lang, String LANG) {
			Locale locale = new Locale(lang, LANG);  
			try {
				daoUtilisateur.modifierLangue(lang);
			} catch (Exception e) {
				FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Changement de langue impossible", "Détails: " + e);
				e.printStackTrace();
			}
			ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_"+lang,locale);
			lblTextByController.setText(bundle.getString("key1"));
			choiceCouleur.setText(bundle.getString("key2"));
			choiceBackground.setText(bundle.getString("key3"));
			title.setText(bundle.getString("key4"));
			btnHello.setText(bundle.getString("key5"));
			
		}

		public void setParent(MenuPrincipalControleur menuPrincipalControleur) {
			this.parent = menuPrincipalControleur;
			
		}




		}