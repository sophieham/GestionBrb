package gestionbrb.vue;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import gestionbrb.controleur.CommandeControleur;
import gestionbrb.controleur.DemarrerCommandeControleur;
import gestionbrb.controleur.FonctionsControleurs;
import gestionbrb.model.Commande;
import gestionbrb.util.bddUtil;
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
	private Button imprimerAddition;
	@FXML
	private AnchorPane fenetre;
	
	private AdditionControleur additionControleur;
	private CommandeControleur commandeControleur;
	
	private Commande commande;
	
	private Window window;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			commande = DemarrerCommandeControleur.getCommande();
			Connection conn = bddUtil.dbConnect();
			ResultSet produitCommandeDB = conn.createStatement().executeQuery(
					"SELECT produit.nom, produit.prix, contenirproduit.qte from contenirproduit "
					+ "INNER JOIN produit on contenirproduit.idProduit = produit.idProduit "
					+ "WHERE contenirproduit.idCommande ="+commande.getIdCommande());
			ResultSet commandeDB = conn.createStatement().executeQuery(""
					+ "SELECT sum(contenirproduit.qte) AS 'qte', prixTotal, substring(date,1,10) as 'date', substring(date,12,15) as 'heure', noTable, nbCouverts "
					+ "FROM commande INNER JOIN contenirproduit on commande.idCommande = contenirproduit.idCommande where commande.idcommande = "+commande.getIdCommande());
			while(produitCommandeDB.next()) {
				HBox produitBox = new HBox();
				
				Label nom = new Label(produitCommandeDB.getString("produit.nom"));
				nom.setPrefSize(293,30);
				nom.setFont((new Font("System", 20)));
				Label prix = new Label(produitCommandeDB.getString("produit.prix"));
				prix.setPrefSize(96,30);
				prix.setAlignment(Pos.CENTER);
				prix.setFont((new Font("System", 20)));
				Label qte = new Label(produitCommandeDB.getString("contenirproduit.qte"));
				qte.setPrefSize(106,30);
				qte.setAlignment(Pos.CENTER);
				qte.setFont((new Font("System", 20)));
				produitBox.getChildren().addAll(nom, prix, qte);
				HBox.setMargin(nom, new Insets(0,0,0,5));
				vboxProduits.getChildren().add(produitBox);

			}
			
			while(commandeDB.next()) {
				infoTableLbl.setText("Addition de la table n°"+commandeDB.getInt("noTable")+" ("+commandeDB.getInt("nbCouverts")+" couverts)");
				dateCommandeLbl.setText("Commande effectuée le "+commandeDB.getDate("date")+" à "+commandeDB.getString("heure"));
				totalLbl.setText("Total: "+commandeDB.getInt("qte")+" produits pour "+commandeDB.getFloat("prixTotal")+" €");
			}
			
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Une erreur est survenue","Détails: "+e);
			e.printStackTrace();
		}

	}

	public void imprimer() {
		try {
			bddUtil.dbQueryExecute("UPDATE `tables` SET occupation = 0 WHERE noTable="+commande.getNoTable());
			PrinterJob job = PrinterJob.createPrinterJob();
			 if(job != null){
			   job.showPrintDialog(window); // Window must be your main Stage
			   job.printPage(fenetre);
			   job.endJob();
			   AdditionControleur.getImprimerAddition().close();
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
