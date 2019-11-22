package gestionbrb.controleur;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


import gestionbrb.model.Ingredients;
import gestionbrb.util.bddUtil;
import gestionbrb.CommandeIngredients;
import gestionbrb.GestionStockAdmin;
import gestionbrb.controleur.GestionStockAdminController;
/**
 * 
 * @author Linxin
 *
 */
public class CommandeIngredientsController implements Initializable{
	@FXML
	private VBox vbox;
	@FXML
	private Label ingredients;
	@FXML
	private Label fournisseur;
	@FXML
	private Label prixUni;
	@FXML
	private Label qte;
	@FXML
	private Label totalprix;
	@FXML
	private Label NomIngredients;
	@FXML
	private Label prixunite;
	@FXML
	private ChoiceBox<String> choiceFournisseur;
	@FXML
	private Label prixtotal;
	@FXML
	private Spinner<Integer> spinner;
	@FXML
	private MenuButton menubutton;
	@FXML
	private MenuItem a;
	@FXML
	private MenuItem b;
	private ObservableList<String> data;
	private Connection conn;
	private String Nom;
	private String idFournisseur;
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	private ResultSet rqte = null;
	private static String output;
	private static int value;
	private static int prix;
	private static int qteRest;
	
public CommandeIngredientsController() {
	

}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	 	try {
			 conn = bddUtil.dbConnect();
			 
				
		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 	String Nom = GestionStockAdminController.Nom;
	 	NomIngredients.setText(Nom);
	 
			//initSpinner();
			try {
				choice();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			menubutton();
			
			
		
	}
	@FXML
	public void choice() throws SQLException {
		/**ResultSet c = conn.createStatement().executeQuery("SELECT * FROM fournisseur INNER JOIN ingredients ON fournisseur.idfournisseur = ingredients.idfournisseur ");
		while(c.next()) {
			data.add(new CommandIngredients(c.getString("nom")));
			choiceFournisseur.addAll(c.getString("fournisseur.nom"));
		}
		
		System.out.println(data);**/
		String query = "SELECT fournisseur.nom FROM fournisseur INNER JOIN ingredients ON fournisseur.idfournisseur = ingredients.idfournisseur WHERE ingredients.nomIngredient = ?";
		pst = conn.prepareStatement(query);
		pst.setString(1, GestionStockAdminController.Nom);
		rs = pst.executeQuery();
		data = FXCollections.observableArrayList();
		while(rs.next()) {
			
			data.add(rs.getString("nom"));
			choiceFournisseur.setItems(data);
		}
		
		
		}
	
	@FXML
	public void prixunite() throws SQLException {
	
		String query = "SELECT ingredients.prixIngredient FROM ingredients INNER JOIN fournisseur ON fournisseur.idfournisseur = ingredients.idfournisseur WHERE ingredients.nomIngredient = ? AND fournisseur.nom = ? ";
		
		try {
		pst = conn.prepareStatement(query);
		pst.setString(1, GestionStockAdminController.Nom);	
		pst.setString(2, output);
		rs = pst.executeQuery();
		while(rs.next()) {
		prixunite.setText(rs.getString("prixIngredient") + "¢ã");
		prix = rs.getInt("prixIngredient");
		}
		
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@FXML
		private void initSpinner() throws SQLException {
		spinner.setEditable(true);   
		spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000));
	
		
		//System.out.println(spinner.getValue().toString());
		
		//
		spinner.valueProperty().addListener((obs, oldValue, newValue) -> {
		value = newValue;
			System.out.println("New value: "+value);
		try {
			prixtotal();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		});
		;
		
		/**spinner.getEditor().setOnAction(e -> {
		   
		    String n = spinner.getEditor().getText(); 
		    System.out.println(n);
		}); commitEditorText(spinner);**/
	}
	
		//@SuppressWarnings("hiding")
		private <Integer> void commitEditorText(Spinner<Integer> spinner) {
		    if (!spinner.isEditable())
		        return;
		    String text = spinner.getEditor().getText();
		    SpinnerValueFactory<Integer> valueFactory = spinner.getValueFactory();
		    if (valueFactory != null) {
		        StringConverter<Integer> converter = valueFactory.getConverter();
		        if (converter != null) {
		        	Integer value = converter.fromString(text);
		            valueFactory.setValue(value); 
		            System.out.println(spinner.getValue().toString());


		        }
		    }
		   
		}

	
@FXML
	public void prixtotal() throws SQLException {
	System.out.println(prix);

	System.out.println(value);
	//System.out.println(prix*value);
	prixtotal.setText(InttoString(prix*value)+"¢ã");
	}
	
	
public static String InttoString(int prixtotal2) {
    
        return String.valueOf(prixtotal2);
   
}
	
public static int StringtoInt(String n) {
	return Integer.valueOf(n).intValue();
}

	public void choice(ActionEvent event) throws IOException, SQLException {
		output = choiceFournisseur.getSelectionModel().getSelectedItem().toString();
		System.out.println(output);
		
		
		prixunite();
		initSpinner();
		
	}
	
	public void menubutton()  {
		EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
               System.out.println(((MenuItem)e.getSource()).getText());
               Parent root;
   	        try {
   	            root = FXMLLoader.load(CommandeIngredients.class.getResource("vue/GererIngredientsProduits.fxml"));
   	            Stage stage = new Stage();
   	            stage.setTitle("My New Stage Title");
   	            stage.setScene(new Scene(root));
   	            stage.show();
   	            // Hide this current window (if this is what you want)
   	          
   	            
   	        }
   	        catch (IOException e1) {
   	            e1.printStackTrace();
   	        }
               
            } 
        }; 
        
        EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
               System.out.println(((MenuItem)e.getSource()).getText());
               Parent root;
   	        try {
   	            root = FXMLLoader.load(CommandeIngredients.class.getResource("vue/GestionFournisseurs.fxml"));
   	            Stage stage = new Stage();
   	            stage.setTitle("My New Stage Title");
   	            stage.setScene(new Scene(root));
   	            stage.show();
   	            // Hide this current window (if this is what you want)
   	          
   	            
   	        }
   	        catch (IOException e1) {
   	            e1.printStackTrace();
   	        }
               
            } 
        }; 
        a.setOnAction(event1);
        b.setOnAction(event2);
	}
	
	public void qteRest() throws SQLException {
		
		String query = "SELECT ingredients.qteRestante FROM ingredients INNER JOIN fournisseur ON fournisseur.idfournisseur = ingredients.idfournisseur WHERE ingredients.nomIngredient = ? AND fournisseur.nom = ? ";
		
		try {
		pst = conn.prepareStatement(query);
		pst.setString(1, GestionStockAdminController.Nom);	
		pst.setString(2, output);
		rqte = pst.executeQuery();
		while(rqte.next()) {
			qteRest=rqte.getInt("qteRestante");
		}
		
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@FXML
    public void Commande(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
		qteRest();
		int n = qteRest+value;
        System.out.println("nouveauquantite" +n);
        String query= "UPDATE ingredients INNER JOIN fournisseur ON ingredients.idfournisseur = fournisseur.idfournisseur SET ingredients.qteRestante = ? WHERE ingredients.nomIngredient = ? AND fournisseur.nom = ? ";
        pst = conn.prepareStatement(query);
		pst.setString(1, InttoString(n));
		pst.setString(2, GestionStockAdminController.Nom);
		pst.setString(3, output);
		int r = pst.executeUpdate();
		refresh();
		//fournisseurIngredients();

		
			Connection conn = bddUtil.dbConnect();
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO `fourniringredients`(`idOperation`,`idIngredient`, `idFournisseur`, `qteCommandee`, `dateCommande`, `prixTotal`) "
															+ "VALUES (null,?,?,?,now(), ?)");
			
			pstmt.setInt(1, StringtoInt(GestionStockAdminController.idIngredient));
			pstmt.setInt(2,StringtoInt(GestionStockAdminController.idFournisseur));
			pstmt.setInt(3, value);
			//pstmt.setString(4,date(now()));
			pstmt.setString(4, InttoString(value*prix));
			pstmt.execute();
			//refresh();
			//alerteInfo("Ajout ¨¦ffectu¨¦", null, "Les informations ont ¨¦t¨¦ ajout¨¦es avec succ¨¨s!");
			System.out.println(StringtoInt(GestionStockAdminController.idFournisseur));
		
	
	}	
    
	
	private void refresh() throws ClassNotFoundException, SQLException {
		GestionStockAdmin.getTableData().clear();
		Connection conn = bddUtil.dbConnect();
		ResultSet c = conn.createStatement().executeQuery("select * from ingredients");
		while(c.next()) {
			GestionStockAdmin.getTableData().add(new Ingredients(c.getInt("idIngredient"),c.getString("nomIngredient"),c.getInt("prixIngredient"),c.getInt("qteRestante"), c.getString("idfournisseur")));
		}
		//GestionStockAdmin.getTview().setItems(GestionStockAdmin.getTableData());
		
	}
	
	
	public VBox getVbox() {
		return vbox;
	}
	public Label getIngredients() {
		return ingredients;
	}
	public Label getFournisseur() {
		return fournisseur;
	}
	public Label getPrixUni() {
		return prixUni;
	}
	public Label getQte() {
		return qte;
	}
	public Label getTotalprix() {
		return totalprix;
	}
	public Label getNomIngredients() {
		return NomIngredients;
	}
	
	

	
}
