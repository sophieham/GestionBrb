package gestionbrb.vue;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import gestionbrb.controleur.FonctionsControleurs;
import gestionbrb.model.Ingredients;
import gestionbrb.util.bddUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;


public class GestionStockServController extends FonctionsControleurs implements Initializable {
	@FXML
	private AnchorPane GestionStockServController;
	@FXML
	private TableColumn<Ingredients, Number> colID;
	@FXML
	private TableColumn<Ingredients, String> colNom;
	@FXML
	private TableColumn<Ingredients, Number> colPrix;
	@FXML
	private TableColumn <Ingredients, Number>colQte;
	@FXML
	private TableColumn <Ingredients, String>colFournisseur;
	@FXML
	private TableView<Ingredients> tview;
	@FXML
	private Button btn;
	@FXML
	private ObservableList<Ingredients> data;
	Ingredients mainApp;
	private Connection conn;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		try {
			 conn = bddUtil.dbConnect();
			 data = FXCollections.observableArrayList();
			setCellTable();
			loadDataFrombase();
		} catch (Exception e) {
			alerteErreur("Erreur d'éxecution", null, "Détails: "+e);
		}
	}
	
		
	
		private void setCellTable() {
			colID.setCellValueFactory(cellData -> cellData.getValue().idIngredientProperty());
			   colNom.setCellValueFactory(cellData -> cellData.getValue().nomIngredientProperty());
			   colPrix.setCellValueFactory(cellData -> cellData.getValue().prixIngredientProperty());
			   colQte.setCellValueFactory(cellData -> cellData.getValue().quantiteIngredientProperty());
			   colFournisseur.setCellValueFactory(cellData -> cellData.getValue().fournisseurProperty());
		
				}
		private void loadDataFrombase() {
			try {
				ResultSet c = conn.createStatement().executeQuery("select `idIngredient`,`nomIngredient`,`prixIngredient`,`qteRestante`, fournisseur.nom from ingredients INNER JOIN fournisseur on ingredients.idfournisseur = fournisseur.idFournisseur ");
				while(c.next()) {
					data.add(new Ingredients(c.getInt("idIngredient"),
											c.getString("nomIngredient"),
											c.getInt("prixIngredient"),
											c.getInt("qteRestante"), 
											c.getString("nom")));
				}
				/**tview.setRowFactory( tv -> {
				    TableRow<Ingredients> row = new TableRow<>();
				    row.setOnMouseClicked(event -> {
				        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
				        	Ingredients rowData = row.getItem();
				            System.out.println(rowData);
				        }
				    });
				    return row; });**/
			} catch (Exception e) {
				alerteErreur("Erreur d'éxecution", null, "Détails: "+e);
			}
			tview.setItems(data);
		}

		
		    @FXML
		    public void RetourMenuPrincipal(ActionEvent event) {
		        Parent root;
		        try {
		            root = FXMLLoader.load(getClass().getResource("MenuPrincipal.fxml"));//attention ¨¤ ce lien
		            Stage stage = new Stage();
		            stage.setTitle("My New Stage Title");
		            stage.setScene(new Scene(root));
		            stage.show();
		            // Hide this current window (if this is what you want)
		          
		            
				} catch (Exception e) {
					alerteErreur("Erreur d'éxecution", null, "Détails: "+e);
				}
		    }
}
		

		


