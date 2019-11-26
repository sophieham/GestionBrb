package gestionbrb.controleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import gestionbrb.model.Ingredients;
import gestionbrb.util.bddUtil;
import gestionbrb.vue.MenuControleur;
import gestionbrb.GestionStockAdmin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

/**
 * 
 * @author Linxin
 *
 */
public class GestionStockController implements Initializable{
	@FXML
	private AnchorPane GestionStockController;
	@FXML
	private TableColumn<Ingredients,Integer> colID;
	@FXML
	private TableColumn<Ingredients,String> colNom;
	@FXML
	private TableColumn<Ingredients,Integer> colPrix;
	@FXML
	private TableColumn <Ingredients,Integer>colQte;
	@FXML
	private TableColumn <Ingredients,String>colFournisseur;
	@FXML
	private TableView<Ingredients> tview;
	@FXML
	private ObservableList<Ingredients> data;
	@FXML
	private Button btnCommander;
	private Ingredients mainApp;
	private Connection conn;
	private GestionStockController gestionStockControllerp;
	private MenuPrincipalControleur menuPrincipalControleur;
	public static String Nom;
	public static String qteRest;
	public static String idFournisseur;
	public static String idIngredient;
	public GestionStockController() {
		
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(ConnexionControleur.getUtilisateurConnecte().getPrivileges()==0) {
			btnCommander.setVisible(false);
		}
	 	try {
			 conn = bddUtil.dbConnect();
			 data = FXCollections.observableArrayList();
			setCellTable();
			loadDataFrombase();
			refresh();
		} catch (ClassNotFoundException | SQLException e) {
			FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Une erreur est survenue", "Détails: " + e);
			e.printStackTrace();
		}
	
	 	
	}
	private void setCellTable() {
		colID.setCellValueFactory(new PropertyValueFactory<Ingredients,Integer>("idIngredient"));
		   colNom.setCellValueFactory(new PropertyValueFactory<Ingredients,String>("nomIngredient"));
		   colPrix.setCellValueFactory(new PropertyValueFactory<Ingredients,Integer>("prixIngredient"));
		   colQte.setCellValueFactory(new PropertyValueFactory<Ingredients,Integer>("quantiteIngredient"));
		   colFournisseur.setCellValueFactory(new PropertyValueFactory<Ingredients,String>("fournisseur"));
	
			}
	private void loadDataFrombase() {
		try {
			ResultSet c = conn.createStatement().executeQuery("select * from ingredients");
			while(c.next()) {
				data.add(new Ingredients(c.getInt("idIngredient"),c.getString("nomIngredient"),c.getInt("prixIngredient"),c.getInt("qteRestante"), null));
			}
			tview.setRowFactory( tv -> {
			    TableRow<Ingredients> row = new TableRow<>();
			    row.setOnMouseClicked(event -> {
			        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
			        	Ingredients rowData = row.getItem();
			            System.out.println(rowData);
			            Nom=tview.getColumns().get(1).getCellObservableValue(rowData).getValue().toString(); 
			            System.out.println(Nom);
			            qteRest = tview.getColumns().get(3).getCellObservableValue(rowData).getValue().toString(); 
			            idFournisseur = tview.getColumns().get(4).getCellObservableValue(rowData).getValue().toString(); 
			            idIngredient = tview.getColumns().get(0).getCellObservableValue(rowData).getValue().toString(); 

			        }
			    });
			    return row; });
		}
		 catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Une erreur est survenue", "Détails: " + e);
		} 
		
		tview.setItems(data);
	}
	

	public void setMainApp(Ingredients mainApp) {
		this.mainApp = mainApp;
	

	}
	private void refresh() throws ClassNotFoundException, SQLException {
		GestionStockAdmin.getTableData().clear();
		Connection conn = bddUtil.dbConnect();
		ResultSet c = conn.createStatement().executeQuery("select * from ingredients");
		while(c.next()) {
			GestionStockAdmin.getTableData().add(new Ingredients(c.getInt("idIngredient"),c.getString("nomIngredient"),c.getInt("prixIngredient"),c.getInt("qteRestante"),  c.getString("idfournisseur")));
		}
		tview.setItems(GestionStockAdmin.getTableData());
		
		
	}
	 @FXML
	    public void RetourMenuPrincipal(ActionEvent event) {
		 try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/GestionStockController.fxml"));
				Parent gestionStock = (Parent) loader.load();
				GestionStockController.getChildren().setAll(gestionStock);

				GestionStockController controller = loader.getController();
				controller.setParent(this);
			} catch (Exception e) {
				FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Une erreur est survenue", "Détails: " + e);
				e.printStackTrace();
			}
	    }
	@FXML
	 	public void CommanderIngredients(ActionEvent event) {
		 Parent root;
		 try {
			 root = FXMLLoader.load(GestionStockAdmin.class.getResource("vue/CommanderIngredients.fxml"));
	            Stage stage = new Stage();
	            stage.setTitle("Commande_Ingredients");
	            stage.setScene(new Scene(root));
	            stage.show();
	           
		 }
		 catch(IOException e){
			 e.printStackTrace();
			FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Une erreur est survenue", "Détails: " + e);

		 }
	 }
	 
	public static String getNom() {
		 return Nom;
	 }

	public static String getQteRest() {
		return qteRest;
	}
	public static String getIdfournisseur() {
		return idFournisseur;
	}
	public void setParent(MenuPrincipalControleur parent) {
		// TODO Auto-generated method stub
		this.menuPrincipalControleur = parent;
	}
	 private void setParent(GestionStockController parent) {
		// TODO Auto-generated method stub
		this.gestionStockControllerp = parent;
	}
	

}
