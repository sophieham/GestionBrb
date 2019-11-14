package application;
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
import java.util.List;
import java.util.ResourceBundle;



import application.Ingredients;
import application.bddUtil;
import application.Fournisseur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
public class GestionStockServController implements Initializable {
	@FXML
	private AnchorPane GestionStockServController;
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
	private Button btn;
	@FXML
	private ObservableList<Ingredients> data;
	private Ingredients mainApp;
	private Connection conn;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		try {
			 conn = bddUtil.dbConnect();
			 data = FXCollections.observableArrayList();
			setCellTable();
			loadDataFrombase();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
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
				/**tview.setRowFactory( tv -> {
				    TableRow<Ingredients> row = new TableRow<>();
				    row.setOnMouseClicked(event -> {
				        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
				        	Ingredients rowData = row.getItem();
				            System.out.println(rowData);
				        }
				    });
				    return row; });**/
			}
			 catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			tview.setItems(data);
		}

		
		    @FXML
		    public void RetourMenuPrincipal(ActionEvent event) {
		        Parent root;
		        try {
		            root = FXMLLoader.load(getClass().getResource("MenuPrincipal.fxml"));//s'il y a des erreurs sue"Location is required",changer le lien ici
		            Stage stage = new Stage();
		            stage.setTitle("My New Stage Title");
		            stage.setScene(new Scene(root));
		            stage.show();
		            // Hide this current window (if this is what you want)
		          
		            
		        }
		        catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		    private void refresh() throws ClassNotFoundException, SQLException {
				Main.getTableData().clear();
				Connection conn = bddUtil.dbConnect();
				ResultSet c = conn.createStatement().executeQuery("select * from ingredients");
				while(c.next()) {
					data.add(new Ingredients(c.getInt("idIngredient"),c.getString("nomIngredient"),c.getInt("prixIngredient"),c.getInt("qteRestante"), null));
				}
				
			}

	





}
		

		


