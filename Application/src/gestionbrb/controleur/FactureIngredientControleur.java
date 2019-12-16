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

public class FactureIngredientControleur implements Initializable{
    @FXML
    private AnchorPane fenetre;
    @FXML
    private Label infoFacture;
    @FXML
    private Label dateCommandeLbl;
    @FXML
    private Label totalLbl;
    @FXML
    private Label devise;
    @FXML
    private Button imprimerAddition;
    @FXML
    private Label ingredientLbl;
    @FXML
    private Label prixLbl;
    @FXML
    private Label qteLbl;
    @FXML
    private Label fournisseurLbl;
    @FXML
	private ResourceBundle bundle;
    @FXML
    private Label labelLibelle;
    @FXML
    private Label labelPrix;
    @FXML
    private Label qte;
    @FXML
    private Label fournisseur;
 
    CommandeIngredientsController parent;
	DAOUtilisateur daoUtilisateur = new DAOUtilisateur();

    DAOFournisseur daoFournisseur = new DAOFournisseur();
    
    private String Facture;
	private String info;
	private String info1;
	private String info2;
	private String total;
	private String total1;
    
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
	private void loadLang(String lang, String LANG) {
		Locale locale = new Locale(lang, LANG);  
		
		bundle = ResourceBundle.getBundle("gestionbrb/language/Language_"+lang, locale);
		Facture = bundle.getString("Facture");
		info = bundle.getString("info");
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

    @FXML
    void imprimer(ActionEvent event) {

    }

	public void setParent(CommandeIngredientsController parent) {
		this.parent = parent;
	}


}
