package gestionbrb.controleur;

import java.net.URL;
import java.util.ResourceBundle;

import gestionbrb.DAO.DAOCommande;
import gestionbrb.model.Commande;
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
 * 
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
	private Stage dialogStage;

	DAOCommande daoCommande = new DAOCommande();

	@FXML
	private Button btnCalculMonnaie;
	@FXML
	private Button btnValider;

	public void setParent(AdditionControleur additionControleur) {
		this.parent = additionControleur;

	}

	public Stage getDialogStage() {
		return dialogStage;
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Affiche des informations sur le paiement de la commande ainsi que des
	 * param�tres en fonctions du type de paiement
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
	 * 
	 * @return rendu le montant restant � payer
	 */
	public String aRendre() {
		String rendu = null;
		try {
			rendu = daoCommande.afficherRendu(commande) + "";
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'�x�cution", "Une erreur est survenue", "D�tails: " + e);
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
			daoCommande.majPaiement(commande, 0);
			FonctionsControleurs.alerteInfo("Paiement accept�", null, "L'addition � �t� enti�rement pay�e!");
			actionAnnuler();
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'�x�cution", "Une erreur est survenue", "D�tails: " + e);
			e.printStackTrace();
		}
	}

	/*
	 * Appell� lors de l'appui sur le bouton Valider. <br> Il calcule le total
	 * restant � payer apr�s la saisie du montant pay�
	 */
	@FXML
	void actionValider() {
		try {

			try {
				Label tprix = new Label();
				tprix.setText(daoCommande.afficherRendu(commande) + "");
				float montant = Float.parseFloat(champMontant.getText());
				float totalPrix = Float.parseFloat(tprix.getText());

				if (montant > totalPrix) {
					new NumberFormatException();
				} else {
					float restantAPayer = Math.abs(totalPrix - montant);
					float nouveauTotal = Math.abs(restantAPayer - totalPrix);
					daoCommande.majPaiement(commande, restantAPayer);
					FonctionsControleurs.alerteInfo("Paiement accept�", null, "L'addition � �t� pay� de " + nouveauTotal + ", il reste " + restantAPayer + " � payer");
					actionAnnuler();
				}
			} catch (NumberFormatException e) {
				FonctionsControleurs.alerteErreur("Erreur d'�x�cution",
						"Vous avez fait une erreur dans la saisie du montant!!", "Veuillez r�essayer.\nD�tails: " + e);
				e.printStackTrace();
			}
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'�x�cution", "Une erreur est survenue", "D�tails: " + e);
			e.printStackTrace();
		}
	}

	/**
	 * Revient � la page pr�c�dente
	 */
	@FXML
	public void actionAnnuler() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/AdditionCommande.fxml"));
			Parent vueAddition = (Parent) loader.load();
			fenetre.getChildren().setAll(vueAddition);

			AdditionControleur controller = loader.getController();
			controller.setParent(this);

		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'�x�cution", "Une erreur est survenue", "D�tails: " + e);
			e.printStackTrace();
		}
	}

	/**
	 * Calcule le rendu si le paiement est par esp�ces
	 * 
	 * @return rendu le montant � rendre
	 */
	@FXML
	public double calculRenduEspece() {
		double rendu = 0;
		try {
			double totalAPayer = daoCommande.afficherRendu(commande);
			double montant = Double.parseDouble(champMontant.getText());
			if (montant < totalAPayer) {
				FonctionsControleurs.alerteAttention("Attention!", null,
						"Si le client ne souhaite pas payer la totalit� de l'addition par esp�ce, "
								+ "il vaut mieux proc�der � un autre moyen de paiement avant de revenir aux esp�ces");
			} else {
				rendu = montant - totalAPayer;
				detailsLbl.setText("A rendre: " + rendu + "�");
			}
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'�x�cution", "Une erreur est survenue", "D�tails: " + e);
			e.printStackTrace();
		}

		return rendu;

	}
}
