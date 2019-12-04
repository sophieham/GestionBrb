package gestionbrb.controleur;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import gestionbrb.DAO.DAOCommande;
import gestionbrb.DAO.DAOFournisseur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

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
    
    CommandeIngredientsController parent;
    
    DAOFournisseur daoFournisseur = new DAOFournisseur();
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			String idFacture = daoFournisseur.afficherDerniereCommande().get(0);
			String nomIngredient = daoFournisseur.afficherDerniereCommande().get(1);
			String qte = daoFournisseur.afficherDerniereCommande().get(2);
			String date = daoFournisseur.afficherDerniereCommande().get(3).substring(0, 10);
			String heure = daoFournisseur.afficherDerniereCommande().get(3).substring(11);
			String prixTotal = daoFournisseur.afficherDerniereCommande().get(4);
			String fournisseur = daoFournisseur.afficherDerniereCommande().get(5);

			infoFacture.setText("Facture #"+idFacture);
			dateCommandeLbl.setText("Commande effectuée le "+date+" à "+heure);
			totalLbl.setText("Total: "+qte+" ingredient(s) pour "+prixTotal);
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
    void imprimer(ActionEvent event) {

    }

	public void setParent(CommandeIngredientsController parent) {
		this.parent = parent;
	}


}
