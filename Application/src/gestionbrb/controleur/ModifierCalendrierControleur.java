package gestionbrb.controleur;

import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gestionbrb.DAO.DAOCalendrier;
import gestionbrb.DAO.DAOUtilisateur;
import gestionbrb.model.Reservations;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

// TODO: Auto-generated Javadoc
/**
 * The Class ModifierCalendrierControleur.
 *
 * @author Sophie
 */
public class ModifierCalendrierControleur{

    /** The champ nom. */
    @FXML
    private TextField champNom;
    
    /** The champ prenom. */
    @FXML
    private TextField champPrenom;
    
    /** The champ num tel. */
    @FXML
    private TextField champNumTel;
    
    /** The champ date. */
    @FXML
    private DatePicker champDate;
    
    /** The champ heure. */
    @FXML
    private TextField champHeure;
    
    /** The champ nb couverts reservation. */
    @FXML
    private TextField champNbCouvertsReservation;
    
    /** The champ demande spe. */
    @FXML
    private TextField champDemandeSpe;
    
    /** The bundle. */
    @FXML
	private ResourceBundle bundle;
    
    /** The labeltele. */
    @FXML
    private Label labeltele;
    
    /** The labeldate. */
    @FXML
    private Label labeldate;
    
    /** The nb. */
    @FXML
    private Label nb;
    
    /** The demande. */
    @FXML
    private Label demande;
    
    /** The heure. */
    @FXML
    private Label heure;
    
    /** The nom. */
    @FXML
    private Label nom;
    
    /** The prenom. */
    @FXML
    private Label prenom;
    
    /** The modifier. */
    @FXML
    private Button modifier;
    
    /** The annuler. */
    @FXML
    private Button annuler;
    
    
    /** The dao calendrier. */
    DAOCalendrier daoCalendrier = new DAOCalendrier();
	
	/** The dao utilisateur. */
	DAOUtilisateur daoUtilisateur = new DAOUtilisateur();
	
    /** The dialog stage. */
    private Stage dialogStage;
    
    /** The reservation. */
    @SuppressWarnings("unused")
	private Reservations reservation;
    
    /** The ok clicked. */
    private boolean okClicked = false;
    
    /**
     * Initialize.
     */
    @FXML
	public void initialize() {
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
	}
    
    /**
     * Load lang.
     *
     * @param lang the lang
     * @param LANG the lang
     */
    private void loadLang(String lang, String LANG) {
		Locale locale = new Locale(lang, LANG);  
		
		bundle = ResourceBundle.getBundle("gestionbrb/language/Language_"+lang, locale);
		labeltele.setText(bundle.getString("telephone"));
		labeldate.setText(bundle.getString("Date"));
		nb.setText(bundle.getString("key15"));
		demande.setText(bundle.getString("Demande"));
		heure.setText(bundle.getString("Heure"));
		nom.setText(bundle.getString("Nom"));
		prenom.setText(bundle.getString("Prenom"));
		modifier.setText(bundle.getString("Modifier"));
		annuler.setText(bundle.getString("Annuler"));
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
     * Remplit les champs avec les données déjà  existantes sur la réservation.
     *
     * @param r the new reservation
     */
    public void setReservation(Reservations r) {
    	try {
			this.reservation = r;
				champNom.setText(r.getNom());
				champPrenom.setText(r.getPrenom());
				champNumTel.setText(r.getNumTel());
				champDate.setValue(LocalDate.parse(r.getDate()));
				champHeure.setText(r.getHeure());
				champNbCouvertsReservation.setText(Integer.toString(r.getNbCouverts()));
				champDemandeSpe.setText(r.getDemandeSpe());
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue","Détails: "+e);
			e.printStackTrace();
		}
    }

    /** 
     * @return true si le bouton a modifié a été appuyé, faux sinon
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Appellé quand l'utilisateur appuie sur "modifier" <br>
     * Modifie la réservation si tout les champs sont valides.
     */
	@FXML
	private void actionModifier() {
	    if (estValide()) {
	    	try {
				reservation.setNom(champNom.getText());
				reservation.setPrenom(champNom.getText());
				reservation.setNumTel(champNumTel.getText());
				reservation.setDate(champDate.getValue());
				reservation.setHeure(champHeure.getText());
				reservation.setNbCouverts(Integer.parseInt(champNbCouvertsReservation.getText()));
				reservation.setDemandeSpe(champDemandeSpe.getText());
			} catch (Exception e) {
				FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue","Détails: "+e);
				e.printStackTrace();
			}
	        okClicked = true;
	        dialogStage.close();
	    }
	}

    /**
     * Appellé quand le bouton annuler est appuyé.
     * Ferme la page sans sauvegarder.
     */
    @FXML
    private void actionAnnuler() {
        dialogStage.close();
    }
    
    /**
	 * Vérifie si la saisie est conforme aux données requises parfois à  l'aide de regex.
	 * Affiche un message d'erreur si il y a au moins une erreur.
	 * 
	 * @return true si la saisie est bien conforme, false sinon
	 */
	public boolean estValide() {
		String msgErreur = "";

		if (champNom.getText() == null || champNom.getText().length() == 0) {
			msgErreur += "Veuillez remplir le nom\n";
		}
		if (champPrenom.getText() == null || champPrenom.getText().length() == 0) {
			msgErreur += "Veuillez remplir le prénom\n";
		}
		if (champNumTel.getText() == null || champNumTel.getText().length() == 0) {
			msgErreur += "Veuillez rentrer le numéro de téléphone\n";
		} else {
			Pattern p = Pattern.compile("(0|\\+)[0-9]{8,12}");
			Matcher m = p.matcher(champNumTel.getText());
			if (!(m.find() && m.group().equals(champNumTel.getText()))) {
				msgErreur += "Erreur! Le champ no. téléphone n'accepte que les numéros commençant par + ou 0 et ayant une longueur entre 8 et 12 chiffres\n";
			}
		}
		if (champDate.getValue() == null) {
			msgErreur += "Veuillez selectionner la date\n";
		}

		if (champHeure.getText() == null || champHeure.getText().length() == 0) {
			msgErreur += "Veuillez rentrer l'heure\n";
		} else {
			Pattern heurep = Pattern.compile("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$");
			Matcher heurem = heurep.matcher(champHeure.getText());
			if (!(heurem.find() && heurem.group().equals(champHeure.getText()))) {
				msgErreur += "Format de l'heure incorrect, veuillez réessayer avec le format hh:mm approprié\n";
			}
		}

		if (champNbCouvertsReservation.getText() == null || champNbCouvertsReservation.getText().length() == 0) {
			msgErreur += "Veuillez rentrer le nombre de couverts!\n";
		} else {
			try {
				Integer.parseInt(champNbCouvertsReservation.getText());
						
			} catch (NumberFormatException e) {
				msgErreur += "Erreur! Le champ \"nombre de couverts\" n'accepte que les nombres\n";
			}
		}
		if (msgErreur.length() == 0) {
			return true;
		} else {
			FonctionsControleurs.alerteErreur("Entrée incorrecte", "Corrigez les erreurs suivantes pour pouvoir modifier la reservation",msgErreur);
			return false;
		}
	}

}