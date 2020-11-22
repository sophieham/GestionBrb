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

// TODO: Auto-generated Javadoc
/**
 * Choix d'un mode de paiement et saisie du montant pay�.
 *
 * @author Sophie
 */
public class PaiementAdditionControleur extends AdditionControleur implements Initializable {
	
	/** The montant restant lbl. */
	@FXML
	private Label montantRestantLbl;
	
	/** The moyen de paiement lbl. */
	@FXML
	private Label moyenDePaiementLbl;
	
	/** The champ montant. */
	@FXML
	private TextField champMontant;
	
	/** The details lbl. */
	@FXML
	private Label detailsLbl;
	
	/** The btn tout payer. */
	@FXML
	private Button btnToutPayer;
	
	/** The revenir. */
	@FXML
	private Button revenir;
	
	/** The devise. */
	@FXML
	private Label devise;
	
	/** The fenetre. */
	@FXML
	private AnchorPane fenetre;
	
	/** The bundle. */
	@FXML
	private ResourceBundle bundle;
	
	/** The dao utilisateur. */
	DAOUtilisateur daoUtilisateur = new DAOUtilisateur();
	
	/** The parent. */
	AdditionControleur parent;
	
	/** The parent C. */
	DemarrerCommandeControleur parentC;
	
	/** The commande. */
	Commande commande;
	
	/** The dialog stage. */
	private Stage dialogStage;

	/** The dao commande. */
	DAOCommande daoCommande = new DAOCommande();

	/** The btn calcul monnaie. */
	@FXML
	private Button btnCalculMonnaie;
	
	/** The btn valider. */
	@FXML
	private Button btnValider;
	
	/** The entrer. */
	private String entrer;
	
	/** The toutpayer. */
	private String toutpayer;

	/**
	 * Sets the parent.
	 *
	 * @param additionControleur the new parent
	 */
	public void setParent(AdditionControleur additionControleur) {
		this.parent = additionControleur;

	}

	/**
	 * Gets the dialog stage.
	 *
	 * @return the dialog stage
	 */
	public Stage getDialogStage() {
		return dialogStage;
	}

	/**
	 * Sets the dialog stage.
	 *
	 * @param dialogStage the new dialog stage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Affiche des informations sur le paiement de la commande ainsi que des
	 * param�tres en fonctions du type de paiement.
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
		devise.setText(DAOCommande.recupererDevise());
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'�xcution", "Une erreur est survenue", "D�tails: " + e);
			e.printStackTrace();
		}
		btnCalculMonnaie.setVisible(false);
		commande = DemarrerCommandeControleur.getCommande();
		String moyenDePaiement = getMoyenPaiement();
		moyenDePaiementLbl.setText(entrer + moyenDePaiement);
		montantRestantLbl.setText(aRendre());
		btnToutPayer.setText(toutpayer + moyenDePaiement);
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
		 * Load lang.
		 *
		 * @param lang the lang
		 * @param LANG the lang
		 */
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
		 * Affiche et met � jour le montant restant � payer.
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
		 * Paie l'int�gralit� (ou ce qu'il reste) de la commande en une seule fois.
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

		/**
		 * Action valider.
		 */
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
					double montant = Double.parseDouble(champMontant.getText());
					double totalPrix = Double.parseDouble(tprix.getText());

					if (montant > totalPrix) {
						FonctionsControleurs.alerteErreur("Erreur d'�x�cution", "Vous avez entr� une valeur sup�rieur � celle du prix total", "Veuillez r�essayer. S'il ne s'agit pas d'une erreur, retentez avec un montant plus petit. ");
					} else {
						double restantAPayer = Math.abs(totalPrix - montant);
						double nouveauTotal = Math.abs(restantAPayer - totalPrix);
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
		 * Revient � la page pr�c�dente.
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
				FonctionsControleurs.alerteErreur("Erreur d'�x�cution", "Une erreur est survenue", "D�tails: " + e);
				e.printStackTrace();
			}
		}

		/**
		 * Calcule le rendu si le paiement est par esp�ces.
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
					detailsLbl.setText("A rendre: " + rendu + "€");
				}
			} catch (Exception e) {
				FonctionsControleurs.alerteErreur("Erreur d'�x�cution", "Une erreur est survenue", "D�tails: " + e);
				e.printStackTrace();
			}

			return rendu;

		}
	}