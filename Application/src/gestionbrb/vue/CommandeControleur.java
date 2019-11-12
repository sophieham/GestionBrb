package gestionbrb.vue;

import gestionbrb.controleur.FonctionsControleurs;
import gestionbrb.model.Produit;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CommandeControleur extends FonctionsControleurs {
	@FXML
	private Label infoTable;
	@FXML
	private TableView<Produit> tableRecap;
	@FXML
	private TableColumn<Produit, String> colonneProduit;
	@FXML
	private TableColumn<Produit, Number> colonnePrix;
	@FXML
	private TableColumn<Produit, Number> colonneQte;
	@FXML
	private Label totalProduit;
	@FXML
	private Label totalPrix;
	@FXML
	private Label totalQte;
	@FXML
	private TabPane typeProduit;
	
	DemarrerCommandeControleur parent;
	
	public CommandeControleur() {
	}
	
	private void initialize() {
		/*colonneProduit.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
		colonnePrix.setCellValueFactory(cellData -> cellData.getValue().prixProperty());
		colonneQte.setCellValueFactory(cellData -> cellData.getValue().qteProperty());*/
	}

	public void setParent(DemarrerCommandeControleur parent) {
		this.parent = parent;		
	}
	
	

}
