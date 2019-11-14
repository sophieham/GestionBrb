package gestionbrb.vue;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import gestionbrb.controleur.FonctionsControleurs;
import gestionbrb.model.Produit;
import gestionbrb.util.bddUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class CommandeControleur extends FonctionsControleurs implements Initializable {
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
	ArrayList<Button> produitListe = new ArrayList<>();
	ArrayList<Tab> onglets = new ArrayList<>();
	
	DemarrerCommandeControleur parent;
    private Stage primaryStage;
	
	public CommandeControleur() {
	}
	
	
	class HandlerBtn1 implements EventHandler<ActionEvent>{
		public void handle(ActionEvent e) {
			System.out.println("ok");
		}
	}

	public void setParent(DemarrerCommandeControleur parent) {
		this.parent = parent;		
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colonneProduit.setCellValueFactory(cellData -> cellData.getValue().nomProduitProperty());
		colonnePrix.setCellValueFactory(cellData -> cellData.getValue().prixProduitProperty());
		colonneQte.setCellValueFactory(cellData -> cellData.getValue().quantiteProduitProperty());
		produitListe.clear();
		try {
			Connection conn = bddUtil.dbConnect();
			ResultSet produit = conn.createStatement().executeQuery("select * from produit");
			ResultSet rsType = conn.createStatement().executeQuery("select nom from type_produit");
			while (rsType.next()) {
				Tab typePlat = new Tab(rsType.getString("nom"));
				onglets.add(typePlat);
				FlowPane produitBox = new FlowPane();
				produitBox.setAlignment(Pos.BASELINE_CENTER);
				while (produit.next()) {
					Button btnPlat = new Button(produit.getString("nom") + "\n" + produit.getInt("prix") + "€");
					btnPlat.setPrefSize(200, 100);
					btnPlat.setTextAlignment(TextAlignment.CENTER);
					btnPlat.setAlignment(Pos.CENTER);
					btnPlat.setOnAction(new HandlerBtn1());
					produitListe.add(btnPlat);	
				}	
				produitBox.getChildren().addAll(produitListe);
				produitBox.getChildren().add(new Label("abc"));
				typePlat.setContent(produitBox);
			}
			
			
			typeProduit.getTabs().addAll(onglets);
			
		} catch (ClassNotFoundException | SQLException e) {
			alerteErreur("Erreur!", "Erreur d'éxecution", "Détails: "+e);
		}
		
	}

	
	

}
