package gestionbrb.controleur;

import java.net.URL;
import java.util.ResourceBundle;

import gestionbrb.DAO.DAOCommande;
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
	private AnchorPane fenetre;
	private static Stage imprimerAddition;
	private static Stage paiementAddition;
	
	
	PaiementAdditionControleur paiementAdditionControleur;
	CommandeControleur parent;
	Commande commande;
	
	DAOCommande daoCommande = new DAOCommande();

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
			// 0: qte, 1; prix, 2: totalproduit, 3: resteapayer
			Label valeurReste = new Label();
			totalPrix.setText(daoCommande.afficherAddition(commande).get(1));
			
			totalProduits.setText("Total: "+daoCommande.afficherAddition(commande).get(2)+" produit(s)");
			totalQte.setText(daoCommande.afficherAddition(commande).get(0));
			totalAPayer.setText(daoCommande.afficherAddition(commande).get(1));
			
			valeurReste.setText(daoCommande.afficherAddition(commande).get(3));
				if (valeurReste.getText()==null) {
				totalAPayer.setText(totalPrix.getText());
				daoCommande.majTotalAPayer(commande, Float.parseFloat(totalPrix.getText()));
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
