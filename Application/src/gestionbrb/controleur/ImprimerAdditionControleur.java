package gestionbrb.controleur;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import gestionbrb.DAO.DAOCommande;
import gestionbrb.DAO.DAOTables;
import gestionbrb.DAO.DAOUtilisateur;
import gestionbrb.model.Commande;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Window;

// TODO: Auto-generated Javadoc
/**
 * Affichage de l'addition et impression du ticket.
 *
 * @author Sophie
 */
public class ImprimerAdditionControleur implements Initializable {

	/** The info table lbl. */
	@FXML
	private Label infoTableLbl;
	
	/** The date commande lbl. */
	@FXML
	private Label dateCommandeLbl;
	
	/** The vbox produits. */
	@FXML
	private VBox vboxProduits;
	
	/** The total lbl. */
	@FXML
	private Label totalLbl;
	
	/** The serveur lbl. */
	@FXML
	private Label serveurLbl;
	
	/** The devise. */
	@FXML
	private Label devise;
	
	/** The libelle. */
	@FXML
	private Label libelle;
	
	/** The prix. */
	@FXML
	private Label prix;
	
	/** The qte. */
	@FXML
	private Label qte;
	
	/** The merci. */
	@FXML
	private Label merci;
	
	/** The imprimer addition. */
	@FXML
	private Button imprimerAddition;
	
	/** The fenetre. */
	@FXML
	private AnchorPane fenetre;
	
	/** The bundle. */
	@FXML
	private ResourceBundle bundle;
	
	/** The dao utilisateur. */
	DAOUtilisateur daoUtilisateur = new DAOUtilisateur();
	
	/** The addition controleur. */
	private AdditionControleur additionControleur;
	
	/** The commande controleur. */
	private CommandeControleur commandeControleur;
	
	/** The commande. */
	private Commande commande;
	
	/** The window. */
	private Window window;

	/** The dao commande. */
	DAOCommande daoCommande = new DAOCommande();
	
	/** The dao table. */
	DAOTables daoTable = new DAOTables();
	
	/** The info 1. */
	private String info1;
	
	/** The total. */
	private String total;
	
	/** The servir. */
	private String servir;
	
	/** The Addition. */
	private String Addition;
	
	/** The info 2. */
	private String info2;
	
	/** The couvert. */
	private String couvert;
	
	/** The pour. */
	private String pour;
	
	
	/**
	 * Initialise le ticket avec les informations sur la table et les produits commandés.
	 *
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
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
			e.printStackTrace();
		}
		
		try {
			
			commande = DemarrerCommandeControleur.getCommande();

			for(int i=0; i<daoCommande.afficherProduitCommande(commande).get(0).size(); i++) {
				HBox produitBox = new HBox();
				
				Label nom = new Label(daoCommande.afficherProduitCommande(commande).get(0).get(i));
				nom.setPrefSize(293,30);
				nom.setFont((new Font("System", 20)));
				Label prix = new Label(daoCommande.afficherProduitCommande(commande).get(1).get(i));
				prix.setPrefSize(96,30);
				prix.setAlignment(Pos.CENTER);
				prix.setFont((new Font("System", 20)));
				Label qte = new Label(daoCommande.afficherProduitCommande(commande).get(2).get(i));
				qte.setPrefSize(106,30);
				qte.setAlignment(Pos.CENTER);
				qte.setFont((new Font("System", 20)));
				produitBox.getChildren().addAll(nom, prix, qte);
				HBox.setMargin(nom, new Insets(0,0,0,5));
				vboxProduits.getChildren().add(produitBox);
			}
			//////////////////////// !!!!!!!!!!!!!!!!!!!!!!!!!!!! \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
				infoTableLbl.setText(Addition+daoCommande.afficherTicket(commande).get(0)+" ("+daoCommande.afficherTicket(commande).get(1)+couvert);
				dateCommandeLbl.setText(info1+daoCommande.afficherTicket(commande).get(2)+info2+daoCommande.afficherTicket(commande).get(3));
				totalLbl.setText(total+daoCommande.afficherTicket(commande).get(4)+pour+daoCommande.afficherTicket(commande).get(5));
				serveurLbl.setText(servir+daoCommande.afficherTicket(commande).get(6));
				devise.setText(DAOCommande.recupererDevise());
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Une erreur est survenue","Détails: "+e);
			e.printStackTrace();
		}

	}
	
	/**
	 * Traduction des menus & boutons dans la langue de l'utilisateur.
	 *
	 * @param lang the lang
	 * @param LANG the lang
	 */
	private void loadLang(String lang, String LANG) {
		Locale locale = new Locale(lang, LANG);  
		
		ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_"+lang, locale);
		dateCommandeLbl.setText(bundle.getString("info"));
		infoTableLbl.setText(bundle.getString("addition"));
		libelle.setText(bundle.getString("Libelle"));
		prix.setText(bundle.getString("Prix"));
		qte.setText(bundle.getString("qte"));
		imprimerAddition.setText(bundle.getString("imprimer"));
		serveurLbl.setText(bundle.getString("Servir"));
		merci.setText(bundle.getString("merci"));
		info1=bundle.getString("info1");
		total=bundle.getString("total");
		servir=bundle.getString("Servir");
		Addition=bundle.getString("Addition");
		info2=bundle.getString("info2");
		couvert=bundle.getString("couvert");
		pour=bundle.getString("pour");
	}

	/**
	 * Affiche la boite de dialogue pour lancer l'impression. <br>
	 * Une fois l'impression lancée ou annulée, la table est libérée
	 *
	 * @param event the event
	 */
	@FXML
	public void imprimer(ActionEvent event) {
		Button button = (Button) event.getSource();
		try {
			daoTable.modifierOccupation(commande.getNoTable(), 0);
			PrinterJob job = PrinterJob.createPrinterJob();
			 if(job != null){
			   job.showPrintDialog(window);
			   job.printPage(fenetre);
			   job.endJob();
			   if(button.getText().equals("Imprimer ticket")) {
				 CommandeControleur.getImprimerAddition().close();  
			   }
			   else {
			   AdditionControleur.getImprimerAddition().close();
			   }
			   DemarrerCommandeControleur.getFenetreCommande().close();
			   FonctionsControleurs.alerteInfo("Table libérée!", null, "L'addition à  bien été envoyée à  l'imprimante");
			 }
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Une erreur est survenue","Détails: "+e);
			e.printStackTrace();
		}
	}

	/**
	 * Gets the parent A.
	 *
	 * @return the parent A
	 */
	public AdditionControleur getParentA() {
		return this.additionControleur;
	}
	
	/**
	 * Gets the parent C.
	 *
	 * @return the parent C
	 */
	public CommandeControleur getParentC() {
		return this.commandeControleur;
	}
	
	/**
	 * Sets the parent.
	 *
	 * @param parent the new parent
	 */
	public void setParent(AdditionControleur parent) {
		this.additionControleur = parent;
	}

	/**
	 * Sets the parent.
	 *
	 * @param parent the new parent
	 */
	public void setParent(CommandeControleur parent) {
		this.commandeControleur = parent;
		
	}

}
