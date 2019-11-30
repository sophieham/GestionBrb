package gestionbrb.controleur;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import gestionbrb.model.Commande;
import gestionbrb.model.Produit;
import gestionbrb.model.Table;
import gestionbrb.util.bddUtil;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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
	private AnchorPane fenetre;
	private static Stage imprimerAddition;
	
	
	CommandeControleur parent;
	Commande commande;

	public void setParent(CommandeControleur commandeControleur) {
		this.parent = commandeControleur;
		
	}

	/**
	 * Affiche le tableau recapitulatif de la commande et affiche des infos sur la table
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
		Connection conn = bddUtil.dbConnect();
		ResultSet deviseDB = conn.createStatement().executeQuery("select devise from preference");
		while (deviseDB.next()) {
			devise.setText(deviseDB.getString("devise"));
			devise1.setText(deviseDB.getString("devise"));
		}
		}
		catch(Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Une erreur est survenue","Détails: "+e);
			e.printStackTrace();		
		}
		commande = DemarrerCommandeControleur.getCommande();
		colonneProduit.setCellValueFactory(cellData -> cellData.getValue().nomProduitProperty());
		colonnePrix.setCellValueFactory(cellData -> cellData.getValue().prixProduitProperty());
		colonneQte.setCellValueFactory(cellData -> cellData.getValue().quantiteProduitProperty());
		tableRecap.setItems(CommandeControleur.getCommandeData());
		infoTable.setText("Table "+commande.getNoTable()+" ("+commande.getNbCouverts()+" couvert(s))");
		calculTotal();
		
	}
	/**
	 * Affiche le nombre de produits commandés et calcule le montant total de la commande.
	 * 
	 * @throws NumberFormatException
	 */
	public void calculTotal() throws NumberFormatException {
		try {
			Label valeurReste = new Label();
			Connection conn = bddUtil.dbConnect();
			ResultSet commandeDB = conn.createStatement().executeQuery("SELECT sum(contenirproduit.qte), count(contenirproduit.idProduit), contenirproduit.idProduit, commande.idCommande, contenirproduit.qte, sum(produit.prix), commande.Reste_A_Payer FROM `contenirproduit` inner join produit on contenirproduit.idProduit = produit.idProduit inner join commande on contenirproduit.idCommande = commande.idCommande where commande.idCommande = "+commande.getIdCommande());
			while (commandeDB.next()) {
				int qte = commandeDB.getInt("contenirproduit.qte");
				float tprix = commandeDB.getFloat("sum(produit.prix)")*qte;
				totalPrix.setText(tprix+"");
				int totalprdt = commandeDB.getInt("count(contenirproduit.idProduit)")*qte;
				totalProduits.setText("Total: "+totalprdt+" produit(s)");
				totalQte.setText(commandeDB.getString("sum(contenirproduit.qte)"));
				totalAPayer.setText(tprix+"");
				valeurReste.setText(commandeDB.getString("commande.Reste_A_Payer"));
			}
				if (valeurReste.getText()==null) {
				totalAPayer.setText(totalPrix.getText());
				bddUtil.dbQueryExecute("UPDATE `commande` SET `prixTotal` = '"+totalPrix.getText()+"', `Reste_A_Payer` = '"+totalPrix.getText()+"'  WHERE `commande`.`idCommande` = "+commande.getIdCommande()+"; ");
				}
				else totalAPayer.setText(valeurReste.getText());
			} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Une erreur est survenue","Détails: "+e);
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
			FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Une erreur est survenue","Détails: "+e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Remplace cette fenêtre par une autre.
	 * Elle va afficher la page où le serveur saisie le paiement qu'il reçoit
	 */
	private static String moyenPaiement;
	@FXML
	public void payerAddition(ActionEvent clicSouris) {
	    Button button = (Button) clicSouris.getSource();
	    moyenPaiement = button.getText();
		try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/PaiementAddition.fxml"));
				Parent additionPaiement = (Parent) loader.load();
				fenetre.getChildren().setAll(additionPaiement);
				
				PaiementAdditionControleur controller = loader.getController();
				controller.setParent(this);
		
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Une erreur est survenue","Détails: "+e);
			e.printStackTrace();
		}
	}

	public void imprimerAddition() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/ImpressionAddition.fxml"));
			Parent addition = (Parent) loader.load();		
			setImprimerAddition(new Stage());
			getImprimerAddition().setTitle("-- Addition de la table " + commande.getNoTable() + " --");
			getImprimerAddition().setScene(new Scene(addition));
			getImprimerAddition().show();
			ImprimerAdditionControleur controller = loader.getController();
			controller.setParent(this);
			CommandeControleur.getFenetreAddition().close();
	} catch (Exception e) {
		FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Une erreur est survenue","Détails: "+e);
		e.printStackTrace();
	}
	}
	
	
	
	/**
	 * Retourne le moyen de paiement utilisé (carte bancaire, espèces, chèque ou ticket-resto)
	 * @return moyenPaiement 
	 */
	public String getMoyenPaiement() {
		return moyenPaiement;
	}


	public static Stage getImprimerAddition() {
		return imprimerAddition;
	}


	public static void setImprimerAddition(Stage imprimerAddition) {
		AdditionControleur.imprimerAddition = imprimerAddition;
	}

	

}
