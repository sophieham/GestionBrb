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
 * Affichage du récapitulatif de la commande
 * @author Sophie
 *
 */
public class AdditionControleur implements Initializable {
	@FXML
	private TableView<Produit> tableRecap;
	@FXML
	private TableColumn<Produit, String> colonneProduit;
	@FXML
	private TableColumn<Produit, Number> colonnePrix;
	@FXML
	private TableColumn<Produit, Number> colonneQte;
	@FXML
	private Label infoTable;
	@FXML
	private Label totalPrix;
	@FXML
	private Label devise;
	@FXML
	private Label devise1;
	@FXML
	private Label totalProduits;
	@FXML
	private Label totalQte;
	@FXML
	private Label totalAPayer;
	@FXML
	private Label reste;
	@FXML
	private Button carte;
	@FXML
	private Button espece;
	@FXML
	private Button cheque;
	@FXML
	private Button ticket;
	@FXML
	private Button imprimer;
	@FXML
	private Button annuler;
	@FXML
	private AnchorPane fenetre;
	private static Stage imprimerAddition;
	private static Stage paiementAddition;
	@FXML
	private ResourceBundle bundle;
	DAOUtilisateur daoUtilisateur = new DAOUtilisateur();
	
	
	PaiementAdditionControleur paiementAdditionControleur;
	CommandeControleur parent;
	Commande commande;
	
	DAOCommande daoCommande = new DAOCommande();

	public void setParent(CommandeControleur commandeControleur) {
		this.parent = commandeControleur;
		
	}

	/**
	 * Affiche le tableau récapitulatif de la commande et affiche des infos sur la table <br>
	 * Charge aussi les fichiers de langue necessaires à la traduction de l'application
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
	 * @throws NumberFormatException
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
	 * Ferme la page
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
	
	/**
	 * Remplace cette fenêtre par une autre.
	 * Elle va afficher la page o� le serveur saisie le paiement qu'il reçoit
	 */
	private static String moyenPaiement;
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
	 * Retourne le moyen de paiement utilisé(carte bancaire, espèces, chèque ou ticket-resto)
	 * @return moyenPaiement 
	 */
	public String getMoyenPaiement() {
		return moyenPaiement;
	}

	
	/**
	 * Retourne la page d'impression de l'addition
	 * @see ImprimerAdditionControleur
	 * @return imprimerAddition
	 */
	public static Stage getImprimerAddition() {
		return imprimerAddition;
	}
	
	public static void setImprimerAddition(Stage imprimerAddition) {
		AdditionControleur.imprimerAddition = imprimerAddition;
	}
	
	/**
	 * Retourne la page de paiement de l'addition
	 * @see PaiementAdditionControleur
	 * @return paiementAddition
	 */
	public static Stage getFenetrePaiement() {
		return paiementAddition;
	}
	
	public static void setFenetrePaiement(Stage paiementAddition) {
		AdditionControleur.paiementAddition = paiementAddition;
	}


	public void setParent(PaiementAdditionControleur parent) {
		this.paiementAdditionControleur = parent;
		
	}

	

	

}
