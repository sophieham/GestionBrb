package gestionbrb.controleur;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import gestionbrb.DAO.DAOCommande;
import gestionbrb.DAO.DAOUtilisateur;
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
 * Choix d'un mode de paiement et saisie du montant payé
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
	private Button revenir;
	@FXML
	private Label devise;
	@FXML
	private AnchorPane fenetre;
	@FXML
	private ResourceBundle bundle;
	DAOUtilisateur daoUtilisateur = new DAOUtilisateur();
	AdditionControleur parent;
	DemarrerCommandeControleur parentC;
	Commande commande;
	private Stage dialogStage;

	DAOCommande daoCommande = new DAOCommande();

	@FXML
	private Button btnCalculMonnaie;
	@FXML
	private Button btnValider;
	private String entrer;
	private String toutpayer;

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
	 * paramètres en fonctions du type de paiement
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
		devise.setText(DAOCommande.recupererDevise());
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'éxcution", "Une erreur est survenue", "Détails: " + e);
			e.printStackTrace();
		}
		btnCalculMonnaie.setVisible(false);
		commande = DemarrerCommandeControleur.getCommande();
		String moyenDePaiement = getMoyenPaiement();
		moyenDePaiementLbl.setText(entrer + moyenDePaiement);
		montantRestantLbl.setText(aRendre());
		btnToutPayer.setText(toutpayer + moyenDePaiement);
		switch (moyenDePaiement) {
		case "Espèces":
			detailsLbl.setText("A rendre: ");
			btnCalculMonnaie.setVisible(true);
			btnValider.setVisible(false);
			break;
		case "Carte Bancaire":
			break;
		case "Chèque":
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
		private void loadLang(String lang, String LANG) {
			Locale locale = new Locale(lang, LANG);  
			
			ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_"+lang, locale);
			moyenDePaiementLbl.setText(bundle.getString("entrer"));
			btnCalculMonnaie.setText(bundle.getString("rendu"));
			btnToutPayer.setText(bundle.getString("toutpayer"));
			revenir.setText(bundle.getString("revenir"));
			entrer=bundle.getString("entrer");
			toutpayer=bundle.getString("toutpayer");
			
		}
		
	

		/**
		 * Affiche et met à jour le montant restant à payer
		 * 
		 * @return rendu le montant restant à payer
		 */
		public String aRendre() {
			String rendu = null;
			try {
				rendu = daoCommande.afficherRendu(commande) + "";
			} catch (Exception e) {
				FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Une erreur est survenue", "Détails: " + e);
				e.printStackTrace();
			}
			return rendu;
		}

		/**
		 * Paie l'intégralité (ou ce qu'il reste) de la commande en une seule fois
		 */
		@FXML
		void ToutPayer() {
			try {
				daoCommande.majPaiement(commande, 0);
				FonctionsControleurs.alerteInfo("Paiement accepté", null, "L'addition à été entièrement payée!");
				actionAnnuler();
			} catch (Exception e) {
				FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Une erreur est survenue", "Détails: " + e);
				e.printStackTrace();
			}
		}

		/*
		 * Appellé lors de l'appui sur le bouton Valider. <br> Il calcule le total
		 * restant à payer après la saisie du montant payé
		 */
		@FXML
		void actionValider() {
			try {

				try {
					Label tprix = new Label();
					tprix.setText(daoCommande.afficherRendu(commande) + "");
					double montant = Double.parseDouble(champMontant.getText());
					double totalPrix = Double.parseDouble(tprix.getText());

					if (montant > totalPrix) {
						FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Vous avez entré une valeur supérieur à celle du prix total", "Veuillez réessayer. S'il ne s'agit pas d'une erreur, retentez avec un montant plus petit. ");
					} else {
						double restantAPayer = Math.abs(totalPrix - montant);
						double nouveauTotal = Math.abs(restantAPayer - totalPrix);
						daoCommande.majPaiement(commande, restantAPayer);
						FonctionsControleurs.alerteInfo("Paiement accepté", null, "L'addition à été payé de " + nouveauTotal + ", il reste " + restantAPayer + " à payer");
						actionAnnuler();
					}
				} catch (NumberFormatException e) {
					FonctionsControleurs.alerteErreur("Erreur d'éxécution",
							"Vous avez fait une erreur dans la saisie du montant!!", "Veuillez réessayer.\nDétails: " + e);
					e.printStackTrace();
				}
			} catch (Exception e) {
				FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Une erreur est survenue", "Détails: " + e);
				e.printStackTrace();
			}
		}

		/**
		 * Revient à la page précédente
		 */
		@FXML
		public void actionAnnuler() {
			try {
				Locale locale = new Locale("fr", "FR");

				ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_fr", locale);
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/AdditionCommande.fxml"), bundle);
				Parent vueAddition = (Parent) loader.load();
				fenetre.getChildren().setAll(vueAddition);

				AdditionControleur controller = loader.getController();
				controller.setParent(this);

			} catch (Exception e) {
				FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Une erreur est survenue", "Détails: " + e);
				e.printStackTrace();
			}
		}

		/**
		 * Calcule le rendu si le paiement est par espèces
		 * 
		 * @return rendu le montant à rendre
		 */
		@FXML
		public double calculRenduEspece() {
			double rendu = 0;
			try {
				double totalAPayer = daoCommande.afficherRendu(commande);
				double montant = Double.parseDouble(champMontant.getText());
				if (montant < totalAPayer) {
					FonctionsControleurs.alerteAttention("Attention!", null,
							"Si le client ne souhaite pas payer la totalité de l'addition par espèce, "
									+ "il vaut mieux procéder à un autre moyen de paiement avant de revenir aux espèces");
				} else {
					rendu = montant - totalAPayer;
					detailsLbl.setText("A rendre: " + rendu + "€");
				}
			} catch (Exception e) {
				FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Une erreur est survenue", "Détails: " + e);
				e.printStackTrace();
			}

			return rendu;

		}
	}