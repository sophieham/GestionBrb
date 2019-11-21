package gestionbrb;
	
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import gestionbrb.model.Ingredients;
import gestionbrb.util.bddUtil;
import gestionbrb.controleur.GestionStockServController;
import gestionbrb.model.Fournisseur;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * 
 * @author Linxin
 *
 */
public class GestionStockAdmin extends Application {
	private Connection conn;
	@FXML
	private static ObservableList<Ingredients> data= FXCollections.observableArrayList();;
	@FXML
	private AnchorPane GestionStockAdminController;
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
	private static TableView<Ingredients> tview;	
	private Ingredients mainApp;

	
	public static ObservableList<Ingredients> getTableData() {
		return data;
	}

	@Override
	public void start(Stage stage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(GestionStockAdmin.class.getResource("GestionStockAdmin.fxml"));
			AnchorPane tablesOverview = (AnchorPane) loader.load();

			Scene scene = new Scene(tablesOverview);
			stage.setScene(scene);
			stage.show();
			
	//GestionStockAdminController controller=loader.getController();

			
			
			//BorderPane root = new BorderPane();
			//Scene scene = new Scene(root,400,400);
			//scene.getStylesheets().add(getClass().getResource("GestionStockAdmin.fxml").toExternalForm());
		/**Parent root = FXMLLoader.load(getClass()
		             .getResource("/application/GestionStockAdmin.fxml"));
			Scene scene = new Scene(root);
			//primaryStage.setScene(root);
			//root.setController(new GestionStockAdminController());
			stage.setScene(scene);
			stage.show();**/
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	public static void main(String[] args) {
		launch(args);
	}
}
