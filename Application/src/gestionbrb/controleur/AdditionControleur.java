package gestionbrb.controleur;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import gestionbrb.Connexion;
import gestionbrb.DAO.DAOCommande;
import gestionbrb.DAO.DAOUtilisateur;
import gestionbrb.model.Commande;
import gestionbrb.model.Produit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Affichage du récapitulatif de la commande.
 *
 * @author Sophie
 */
public class AdditionControleur implements Initializable {
	
	/** The table recap. */
	@FXML
	private TableView<Produit> tableRecap;
	
	/** The colonne produit. */
	@FXML
	private TableColumn<Produit, String> colonneProduit;
	
	/** The colonne prix. */
	@FXML
	private TableColumn<Produit, Number> colonnePrix;
	
	/** The colonne qte. */
	@FXML
	private TableColumn<Produit, Number> colonneQte;
	
	/** The info table. */
	@FXML
	private Label infoTable;
	
	/** The total prix. */
	@FXML
	private Label totalPrix;
	
	/** The devise. */
	@FXML
	private Label devise;
	
	/** The devise 1. */
	@FXML
	private Label devise1;
	
	/** The total produits. */
	@FXML
	private Label totalProduits;
	
	/** The total qte. */
	@FXML
	private Label totalQte;
	
	/** The total A payer. */
	@FXML
	private Label totalAPayer;
	
	/** The reste. */
	@FXML
	private Label reste;
	
	/** The carte. */
	@FXML
	private Button carte;
	
	/** The espece. */
	@FXML
	private Button espece;
	
	/** The cheque. */
	@FXML
	private Button cheque;
	
	/** The ticket. */
	@FXML
	private Button ticket;
	
	/** The imprimer. */
	@FXML
	private Button imprimer;
	
	/** The annuler. */
	@FXML
	private Button annuler;
	
	/** The fenetre. */
	@FXML
	private AnchorPane fenetre;
	
	/** The imprimer addition. */
	private static Stage imprimerAddition;
	
	/** The paiement addition. */
	private static Stage paiementAddition;
	
	/** The bundle. */
	@FXML
	private ResourceBundle bundle;
	
	/** The dao utilisateur. */
	DAOUtilisateur daoUtilisateur = new DAOUtilisateur();
	
	
	/** The paiement addition controleur. */
	PaiementAdditionControleur paiementAdditionControleur;
	
	/** The parent. */
	CommandeControleur parent;
	
	/** The commande. */
	Commande commande;
	
	/** The dao commande. */
	DAOCommande daoCommande = new DAOCommande();

	/**
	 * Sets the parent.
	 *
	 * @param commandeControleur the new parent
	 */
	public void setParent(CommandeControleur commandeControleur) {
		this.parent = commandeControleur;
		
	}

	/**
	 * Affiche le tableau récapitulatif de la commande et affiche des infos sur la table <br>
	 * Charge aussi les fichiers de langue necessaires à  la traduction de l'application.
	 *
	 * @param location the location
	 * @param resources the resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		bundle = resources;
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
			devise.setText(DAOCommande.recupererDevise());
			devise1.setText(DAOCommande.recupererDevise());
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'éxecution", "Une erreur est survenue", "Détails: " + e);
			e.printStackTrace();
		}
		commande = DemarrerCommandeControleur.getCommande();
		colonneProduit.setCellValueFactory(cellData -> cellData.getValue().nomProduitProperty());
		colonnePrix.setCellValueFactory(cellData -> cellData.getValue().prixProduitProperty());
		colonneQte.setCellValueFactory(cellData -> cellData.getValue().quantiteProduitProperty());
		tableRecap.setItems(CommandeControleur.getCommandeData());
		infoTable.setText("Table " + commande.getNoTable() + " (" + commande.getNbCouverts() + " couvert(s))");
		calculTotal();

	}
	
	/**
	 * Affiche le nombre de produits commandés et calcule le montant total de la commande.
	 *
	 * @param lang the lang
	 * @param LANG the lang
	 * @throws NumberFormatException the number format exception
	 */
	private void loadLang(String lang, String LANG) {
		Locale locale = new Locale(lang, LANG);  
		
		ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_"+lang, locale);
		colonnePrix.setText(bundle.getString("Prix"));
		colonneQte.setText(bundle.getString("qte"));
		colonneProduit.setText(bundle.getString("Produit"));
		reste.setText(bundle.getString("reste"));
		carte.setText(bundle.getString("carte"));
		espece.setText(bundle.getString("espece"));
		cheque.setText(bundle.getString("cheque"));
		ticket.setText(bundle.getString("ticket"));
		imprimer.setText(bundle.getString("imprimer"));
		annuler.setText(bundle.getString("annuler"));
		
	}
	
	/**
	 * Calcule et affiche le total de la commande.
	 *
	 * @throws NumberFormatException the number format exception
	 */
	public void calculTotal() throws NumberFormatException {
		try {
			Label valeurReste = new Label();
			totalPrix.setText(daoCommande.afficherPrixTotal(commande)+"");
			
			totalProduits.setText("Total: "+daoCommande.afficherQteTotal(commande)+" produit(s)");
			totalQte.setText(daoCommande.afficherQteTotal(commande)+"");
			totalAPayer.setText(daoCommande.afficherPrixTotal(commande)+"");
			
			valeurReste.setText(daoCommande.afficherAddition(commande).get(3));
				if (valeurReste.getText()==null) {
				totalAPayer.setText(totalPrix.getText());
				daoCommande.majTotalAPayer(commande, Float.parseFloat(totalPrix.getText()));
				}
				else totalAPayer.setText(valeurReste.getText());
			} catch (Exception e) {
				FonctionsControleurs.alerteErreur("Erreur d'éxecution", "Une erreur est survenue", "Détails: " + e);
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Ferme la page.
	 */
	@FXML
	public void boutonAnnuler() {
		try {
			CommandeControleur.getFenetreAddition().close();
		}
		catch(Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'éxecution", "Une erreur est survenue", "Détails: " + e);
			e.printStackTrace();
		}
	}
	

	/** The moyen paiement. */
	private static String moyenPaiement;
	
	/**
	 * Remplace cette fenêtre par une autre.
	 * Elle va afficher la page où le serveur saisie le paiement qu'il reçoit
	 *
	 * @param clicSouris the clic souris
	 */
	@FXML
	public void payerAddition(ActionEvent clicSouris) {
	    Button button = (Button) clicSouris.getSource();
	    moyenPaiement = button.getText();
		try {
				Locale locale = new Locale("fr", "FR");
				ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_fr", locale);
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/PaiementAddition.fxml"),bundle);
				Parent additionPaiement = (Parent) loader.load();
				fenetre.getChildren().setAll(additionPaiement);
				
				PaiementAdditionControleur controller = loader.getController();
				controller.setParent(this);
		
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'éxecution", "Une erreur est survenue", "Détails: " + e);
			e.printStackTrace();
		}
	}

	/**
	 * Affiche le ticket de l'addition en vue de l'imprimer.
	 */
	@FXML
	public void imprimerAddition() {
		try {
			Locale locale = new Locale("fr", "FR");
			ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_fr", locale);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/ImpressionAddition.fxml"),bundle);
			Parent addition = (Parent) loader.load();
			setImprimerAddition(new Stage());
			getImprimerAddition().setResizable(false);
			getImprimerAddition().setTitle("-- Addition de la table " + commande.getNoTable() + " --");
			getImprimerAddition().setScene(new Scene(addition));
			getImprimerAddition().show();
			getImprimerAddition().getIcons().add(new Image(
          	      Connexion.class.getResourceAsStream( "ico.png" ))); 
			ImprimerAdditionControleur controller = loader.getController();
			controller.setParent(this);
			CommandeControleur.getFenetreAddition().close();
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'éxecution", "Une erreur est survenue", "Détails: " + e);
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Retourne le moyen de paiement utilisé(carte bancaire, espèces, chèque ou ticket-resto).
	 *
	 * @return moyenPaiement
	 */
	public String getMoyenPaiement() {
		return moyenPaiement;
	}

	
	/**
	 * Retourne la page d'impression de l'addition.
	 *
	 * @return imprimerAddition
	 * @see ImprimerAdditionControleur
	 */
	public static Stage getImprimerAddition() {
		return imprimerAddition;
	}
	
	/**
	 * Setter.
	 *
	 * @param imprimerAddition the new imprimer addition
	 */
	public static void setImprimerAddition(Stage imprimerAddition) {
		AdditionControleur.imprimerAddition = imprimerAddition;
	}
	
	/**
	 * Retourne la page de paiement de l'addition.
	 *
	 * @return paiementAddition
	 * @see PaiementAdditionControleur
	 */
	public static Stage getFenetrePaiement() {
		return paiementAddition;
	}
	
	/**
	 * Setter.
	 *
	 * @param paiementAddition the new fenetre paiement
	 */
	public static void setFenetrePaiement(Stage paiementAddition) {
		AdditionControleur.paiementAddition = paiementAddition;
	}


	/**
	 * Setter.
	 *
	 * @param parent the new parent
	 */
	public void setParent(PaiementAdditionControleur parent) {
		this.paiementAdditionControleur = parent;
		
	}

	

	

}
