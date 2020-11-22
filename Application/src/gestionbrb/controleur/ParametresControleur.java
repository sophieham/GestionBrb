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

// TODO: Auto-generated Javadoc
/**
 * The Class ParametresControleur.
 *
 * @author Linxin
 * @author Leo pour la devise
 */
public class ParametresControleur implements Initializable{
	
	/** The ch devise. */
	@FXML
	private TextField chDevise;
	
	/** The lbl text by controller. */
	@FXML
	private  Label lblTextByController;
	
	/** The choicebox. */
	@FXML
	private ChoiceBox<String> choicebox;
	
	/** The choice couleur. */
	@FXML
	private TitledPane choiceCouleur;
	
	/** The choice devise. */
	@FXML
	private TitledPane choiceDevise;
	
	/** The btn hello. */
	@FXML
	private Button btnHello;
	
	/** The bundle. */
	@FXML
	private ResourceBundle bundle;
	
	/** The choice background. */
	@FXML
	private Label choiceBackground;
	
	/** The title. */
	@FXML
	private TitledPane title;
	
	/** The btnbackground. */
	@FXML
	private ColorPicker btnbackground;
	
	/** The Anchor pane. */
	@FXML
	private AnchorPane AnchorPane;
	
	/** The btn valider. */
	@FXML
	private Button btnValider;
	
	/** The devise. */
	@FXML
	private Label devise;
	
	/** The parent. */
	@SuppressWarnings("unused")
	private MenuPrincipalControleur parent;
 
 /** The dao utilisateur. */
 DAOUtilisateur daoUtilisateur = new DAOUtilisateur();
 
 /** The dao commande. */
 DAOCommande daoCommande = new DAOCommande();


	/**
	 * Initialize.
	 *
	 * @param location the location
	 * @param resources the resources
	 */
	@Override
	   public void initialize(URL location, ResourceBundle resources) {	
	    bundle = resources;
	    initialize();
	}
	
	/**
	 * Onbutton.
	 *
	 * @param event the event
	 */
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
	 
	/**
	 * changer la couleur du fond de l'application.
	 *
	 * @param event the event
	 */

		public void OnBackground(ActionEvent event) {
		Color background = btnbackground.getValue();
		AnchorPane.setBackground(new Background(new BackgroundFill(background, null, null)));
		
	}
	
		/**
		 * modifier le devise.
		 */
		@FXML
		public void actionValiderDevise() {
			chDevise.getText().toUpperCase();
			try {
				if (estValide()) {
				daoCommande.majDevise(chDevise.getText());
				FonctionsControleurs.alerteInfo("Modification effectuée", null, "La modification à  bien été prise en compte!");
				}
			} catch (Exception e) {
				FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Une erreur est survenue","Détails: "+e);
				e.printStackTrace();
			}
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
		
		/**
		 * Les utilisateur peuvent changer la langue et conserver sur le base de données.
		 *
		 * @param event the event
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		
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

		/**
		 * Initialize.
		 */
		@FXML
		public void initialize() {
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
			try {
				daoUtilisateur.modifierLangue(lang);
			} catch (Exception e) {
				FonctionsControleurs.alerteErreur("Erreur d'éxecution", "Changement de langue impossible", "Détails: " + e);
				e.printStackTrace();
			}
			ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_"+lang,locale);
			lblTextByController.setText(bundle.getString("key1"));
			choiceCouleur.setText(bundle.getString("key2"));
			choiceBackground.setText(bundle.getString("key3"));
			title.setText(bundle.getString("key4"));
			btnHello.setText(bundle.getString("key5"));
			btnValider.setText(bundle.getString("key13"));
			choiceDevise.setText(bundle.getString("Devise"));
			devise.setText(bundle.getString("devise"));
		}

		/**
		 * Sets the parent.
		 *
		 * @param menuPrincipalControleur the new parent
		 */
		public void setParent(MenuPrincipalControleur menuPrincipalControleur) {
			this.parent = menuPrincipalControleur;
			
		}




		}