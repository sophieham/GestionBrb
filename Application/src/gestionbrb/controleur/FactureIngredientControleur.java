package gestionbrb.controleur;

import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import gestionbrb.DAO.DAOCommande;
import gestionbrb.DAO.DAOFournisseur;
import gestionbrb.DAO.DAOUtilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

// TODO: Auto-generated Javadoc
/**
 * The Class FactureIngredientControleur.
 */
/*
 * Affiche les factures lors d'achats d'ingrédients
 */
public class FactureIngredientControleur implements Initializable{
    
    /** The fenetre. */
    @FXML
    private AnchorPane fenetre;
    
    /** The info facture. */
    @FXML
    private Label infoFacture;
    
    /** The date commande lbl. */
    @FXML
    private Label dateCommandeLbl;
    
    /** The total lbl. */
    @FXML
    private Label totalLbl;
    
    /** The devise. */
    @FXML
    private Label devise;
    
    /** The imprimer addition. */
    @FXML
    private Button imprimerAddition;
    
    /** The ingredient lbl. */
    @FXML
    private Label ingredientLbl;
    
    /** The prix lbl. */
    @FXML
    private Label prixLbl;
    
    /** The qte lbl. */
    @FXML
    private Label qteLbl;
    
    /** The fournisseur lbl. */
    @FXML
    private Label fournisseurLbl;
    
    /** The bundle. */
    @FXML
	private ResourceBundle bundle;
    
    /** The label libelle. */
    @FXML
    private Label labelLibelle;
    
    /** The label prix. */
    @FXML
    private Label labelPrix;
    
    /** The qte. */
    @FXML
    private Label qte;
    
    /** The fournisseur. */
    @FXML
    private Label fournisseur;
 
    /** The parent. */
    CommandeIngredientsController parent;
	
	/** The dao utilisateur. */
	DAOUtilisateur daoUtilisateur = new DAOUtilisateur();

    /** The dao fournisseur. */
    DAOFournisseur daoFournisseur = new DAOFournisseur();
    
    /** The Facture. */
    private String Facture;
	
	/** The info. */
	private String info;
	
	/** The info 1. */
	private String info1;
	
	/** The info 2. */
	private String info2;
	
	/** The total. */
	private String total;
	
	/** The total 1. */
	private String total1;
    
	/**
	 * Initialize.
	 *
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			initialize();
			String idFacture = daoFournisseur.afficherDerniereCommande().get(0);
			String nomIngredient = daoFournisseur.afficherDerniereCommande().get(1);
			String qte = daoFournisseur.afficherDerniereCommande().get(2);
			String date = daoFournisseur.afficherDerniereCommande().get(3).substring(0, 10);
			String heure = daoFournisseur.afficherDerniereCommande().get(3).substring(11);
			String prixTotal = daoFournisseur.afficherDerniereCommande().get(4);
			String fournisseur = daoFournisseur.afficherDerniereCommande().get(5);

			//infoFacture.setText("Facture #"+idFacture);
			infoFacture.setText(Facture+idFacture);
			
			dateCommandeLbl.setText(info1+date+info2+heure);
			totalLbl.setText(total+qte+total1+prixTotal);
			devise.setText(DAOCommande.recupererDevise());
			ingredientLbl.setText(nomIngredient);
			prixLbl.setText(CommandeIngredientsController.getPrix()+devise.getText());
			qteLbl.setText(qte);
			fournisseurLbl.setText(fournisseur);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
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
		Facture = bundle.getString("Facture");
		setInfo(bundle.getString("info"));
		info1 = bundle.getString("info1");
		info2 = bundle.getString("info2");
		labelLibelle.setText(bundle.getString("Libelle"));
		labelPrix.setText(bundle.getString("Prix"));
		qte.setText(bundle.getString("qte"));
		fournisseur.setText(bundle.getString("Fournisseur"));
		ingredientLbl.setText(bundle.getString("ingredients"));
		fournisseurLbl.setText(bundle.getString("Fournisseur"));
		totalLbl.setText(bundle.getString("totalLbl"));
		total=bundle.getString("total");
		total1=bundle.getString("total1");
		imprimerAddition.setText(bundle.getString("Imprimer"));
	}

    /**
     * Imprimer.
     *
     * @param event the event
     */
    @FXML
    void imprimer(ActionEvent event) {

    }

	/**
	 * Sets the parent.
	 *
	 * @param parent the new parent
	 */
	public void setParent(CommandeIngredientsController parent) {
		this.parent = parent;
	}
	
	/**
	 * Gets the info.
	 *
	 * @return the info
	 */
	public String getInfo() {
		return info;
	}
	
	/**
	 * Sets the info.
	 *
	 * @param info the new info
	 */
	public void setInfo(String info) {
		this.info = info;
	}


}
