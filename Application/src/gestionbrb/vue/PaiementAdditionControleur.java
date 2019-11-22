package gestionbrb.vue;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import gestionbrb.controleur.DemarrerCommandeControleur;
import gestionbrb.controleur.FonctionsControleurs;
import gestionbrb.model.Commande;
import gestionbrb.util.bddUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 * Choix d'un mode de paiement et saisie du montant pay�
 * @author Sophie
 *
 */
public class PaiementAdditionControleur extends AdditionControleur implements Initializable {
	@FXML
	private Label montantRestantLbl;
	@FXML
	private Label moyenDePaiementLbl;
	@FXML
	private TextField champMontant;
	@FXML
	private Label detailsLbl;
	@FXML
	private Button btnToutPayer;
	@FXML
	private AnchorPane fenetre;
	AdditionControleur parent;
	DemarrerCommandeControleur parentC;
	Commande commande;
	private Stage dialogStageee;

	@FXML
	private Button btnCalculMonnaie;
	@FXML
	private Button btnValider;
	public void setParent(AdditionControleur additionControleur) {
		this.parent = additionControleur;
		
	}

	public void setDialogStage(Stage dialogStageee) {
		this.dialogStageee = dialogStageee;
	}
	

	/**
	 * Affiche des informations sur le paiement de la commande ainsi que des param�tres en fonctions du type de paiement
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnCalculMonnaie.setVisible(false);
			commande = DemarrerCommandeControleur.getCommande();
			String moyenDePaiement = getMoyenPaiement();
			moyenDePaiementLbl.setText("Entrez le montant � payer par " + moyenDePaiement);
			montantRestantLbl.setText(aRendre());
			btnToutPayer.setText("Tout payer par " + moyenDePaiement);
			switch (moyenDePaiement) {
			case "Esp�ces":
				detailsLbl.setText("A rendre: ");
				btnCalculMonnaie.setVisible(true);
				btnValider.setVisible(false);
				break;
			case "Carte Bancaire":
				break;
			case "Ch�que":
				detailsLbl.setText("N'oubliez pas de noter le numero de table et l'heure au dos");
				break;
			case "Ticket-Resto":
				detailsLbl.setText("On ne peut pas rendre la monnaie d'un ticket-resto!");
				break;
			default:
				detailsLbl.setText("");
				break;
			}

	}

	/**
	 * Affiche et met � jour le montant restant � payer
	 * @return rendu le montant restant � payer
	 */
	public String aRendre() {
		String rendu = null;
		try {
			Connection conn = bddUtil.dbConnect();
			ResultSet commandeDB = conn.createStatement().executeQuery("SELECT Reste_A_Payer from commande where idCommande = "+commande.getIdCommande());
			while (commandeDB.next()) {
				rendu = commandeDB.getString("Reste_A_Payer");
			}
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'�x�cution", "Une erreur est survenue","D�tails: "+e);
			e.printStackTrace();
		}
		return rendu;
	}
	
	/**
	 * Paie l'int�gralit� (ou ce qu'il reste) de la commande en une seule fois
	 */
    @FXML
	void ToutPayer() {
		try {
			bddUtil.dbQueryExecute("UPDATE `commande` SET `Reste_A_Payer` = '0' WHERE `commande`.`idCommande`= "+ commande.getIdCommande());
			FonctionsControleurs.alerteInfo("Paiement accept�", null, "L'addition � �t� enti�rement pay�e!");
			actionAnnuler();
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'�x�cution", "Une erreur est survenue", "D�tails: " + e);
			e.printStackTrace();
		}
	}

    /*
     * Appell� lors de l'appui sur le bouton Valider. <br>
     * Il calcule le total restant � payer apr�s la saisie du montant pay�
     */
	@FXML
	void actionValider() {
		try {
			Label tprix = new Label();
			Connection conn = bddUtil.dbConnect();
			ResultSet commandeDB = conn.createStatement().executeQuery("SELECT Reste_A_Payer from commande where idCommande = "+commande.getIdCommande());
			while (commandeDB.next()) {
				String totalPrix = commandeDB.getString("Reste_A_Payer");
				tprix.setText(totalPrix);
			}
			float restantAPayer = Math.abs(Float.parseFloat((tprix.getText())) - Float.parseFloat(champMontant.getText()));
			float nouveauTotal = Math.abs(restantAPayer - Float.parseFloat((tprix.getText())));
			bddUtil.dbQueryExecute("UPDATE `commande` SET `Reste_A_Payer` = '"+restantAPayer+"' WHERE `commande`.`idCommande`= "+ commande.getIdCommande());
			FonctionsControleurs.alerteInfo("Paiement accept�", null, "L'addition � �t� pay� de "+nouveauTotal+", il reste "+restantAPayer+" � payer");
			actionAnnuler();
		} catch (SQLException | ClassNotFoundException e) {
			FonctionsControleurs.alerteErreur("Erreur d'�x�cution", "Une erreur est survenue", "D�tails: " + e);
			e.printStackTrace();
		}
		catch(NumberFormatException e) {
			FonctionsControleurs.alerteErreur("Erreur d'�x�cution", "Vous avez fait une erreur dans la saisie du montant!!", "Veuillez r�essayer.\nD�tails: " + e);
			e.printStackTrace();
		}
    }
	
	/**
	 * Revient � la page pr�c�dente
	 */
	@FXML
	public void actionAnnuler() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("AdditionCommande.fxml"));
			Parent additionPaiement = (Parent) loader.load();
			fenetre.getChildren().setAll(additionPaiement);
			
			AdditionControleur controller = loader.getController();
	
	} catch (Exception e) {
		FonctionsControleurs.alerteErreur("Erreur d'�x�cution", "Une erreur est survenue","D�tails: "+e);
		e.printStackTrace();
	}
		
	}
	
	/**
	 * Calcule le rendu si le paiement est par esp�ces
	 * @return rendu le montant � rendre
	 */
	@FXML
	public float calculRenduEspece() {
		float rendu = 0;
		float montant = Float.parseFloat(champMontant.getText());
		float totalAPayer = Float.parseFloat(aRendre());
		if(montant<totalAPayer) {
			FonctionsControleurs.alerteAttention("Attention!", null,
					"Si le client ne souhaite pas payer la totalit� de l'addition par esp�ce, "
					+ "il vaut mieux proc�der � un autre moyen de paiement avant de revenir aux esp�ces");
		}
		else {
			rendu = montant - totalAPayer;
			detailsLbl.setText("A rendre: " + rendu + "�");	
		}
		return rendu;
	}
}
