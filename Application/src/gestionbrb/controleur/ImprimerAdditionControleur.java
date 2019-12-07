package gestionbrb.controleur;

import java.net.URL;
import java.util.ResourceBundle;

import gestionbrb.DAO.DAOCommande;
import gestionbrb.DAO.DAOTables;
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

/**
 * Affichage de l'addition et impression du ticket
 * @author Sophie
 *
 */
public class ImprimerAdditionControleur implements Initializable {

	@FXML
	private Label infoTableLbl;
	@FXML
	private Label dateCommandeLbl;
	@FXML
	private VBox vboxProduits;
	@FXML
	private Label totalLbl;
	@FXML
	private VBox vboxPaiement;
	@FXML
	private Label serveurLbl;
	@FXML
	private Label devise;
	@FXML
	private Button imprimerAddition;
	@FXML
	private AnchorPane fenetre;
	
	private AdditionControleur additionControleur;
	private CommandeControleur commandeControleur;
	
	private Commande commande;
	
	private Window window;

	DAOCommande daoCommande = new DAOCommande();
	DAOTables daoTable = new DAOTables();
	
	
	/**
	 * Initialise le ticket avec les informations sur la table et les produits commandés.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
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
				infoTableLbl.setText("Addition de la table n°"+daoCommande.afficherTicket(commande).get(0)+" ("+daoCommande.afficherTicket(commande).get(1)+" couverts)");
				dateCommandeLbl.setText("Commande effectuée le "+daoCommande.afficherTicket(commande).get(2)+" à "+daoCommande.afficherTicket(commande).get(3));
				totalLbl.setText("Total: "+daoCommande.afficherTicket(commande).get(4)+" produits pour "+daoCommande.afficherTicket(commande).get(5));
				serveurLbl.setText("Vous avez été servi par "+daoCommande.afficherTicket(commande).get(6));
				devise.setText(DAOCommande.recupererDevise());
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Une erreur est survenue","Détails: "+e);
			e.printStackTrace();
		}

	}

	/**
	 * Affiche la boite de dialogue pour lancer l'impression. <br>
	 * Une fois l'impression lancée ou annulée, la table est libérée.
	 * @param event
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
			   System.out.println(button.getText());
			   if(button.getText().equals("Imprimer ticket")) {
				 CommandeControleur.getImprimerAddition().close();  
			   }
			   else {
			   AdditionControleur.getImprimerAddition().close();
			   }
			   DemarrerCommandeControleur.getFenetreCommande().close();
			   FonctionsControleurs.alerteInfo("Table libérée", null, "L'addition à bien été envoyée à l'imprimante et la table à été libérée!");
			 }
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Une erreur est survenue","Détails: "+e);
			e.printStackTrace();
		}
	}

	public AdditionControleur getParentA() {
		return this.additionControleur;
	}
	
	public CommandeControleur getParentC() {
		return this.commandeControleur;
	}
	
	public void setParent(AdditionControleur parent) {
		this.additionControleur = parent;
	}

	public void setParent(CommandeControleur parent) {
		this.commandeControleur = parent;
		
	}

}
