package gestionbrb.controleur;

import java.io.IOException;
//import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.event.ActionEvent;

/**
 * 
 * @author Linxin
 *
 */
public class ParametresControleur implements Initializable{
	@FXML
	private  Label lblTextByController;
	@FXML
	private ChoiceBox<String> choicebox;
	@FXML
	private TitledPane choiceCouleur;
	@FXML
	private Button btnHello;
	@FXML
	private ResourceBundle bundle;
	@FXML
	private Label choiceBackground;
	@FXML
	private TitledPane title;
	@FXML
	private ColorPicker btnbackground;
	@FXML
	private AnchorPane AnchorPane;
	
	private MenuPrincipalControleur parent;



	@Override
	   public void initialize(URL location, ResourceBundle resources) {
		
	    bundle = resources;
		

	
		
	}
	
		
	

	@FXML
	 public void Onbutton(ActionEvent event) {
		//btnHello.setText("change");
		
		try {
	            Parent root = FXMLLoader.load(CommandeIngredientsController.class.getResource("../vue/MenuPrincipal.fxml"));
	            Stage stage = new Stage();
	            stage.setTitle("GererIngredientsProduits");
	            stage.setScene(new Scene(root));
	            stage.show();
	            
	          
	            
	        }
	        catch (IOException e1) {
	            e1.printStackTrace();
	        }
        }
	 
	

		public void OnBackground(ActionEvent event) {
		Color background = btnbackground.getValue();
		AnchorPane.setBackground(new Background(new BackgroundFill(background, null, null)));
		
	}
	

		public void choiceMade(ActionEvent event) throws IOException {
		String output = choicebox.getSelectionModel().getSelectedItem().toString();
		System.out.println(output);
		switch(output) {
		case "Chinese":
			System.out.println("chinese");
			
			Locale locale = new Locale("zh","CN");  
			
			//FXMLLoader fxmlLoader = new FXMLLoader(); 
			//fxmlLoader.setResources(ResourceBundle.getBundle("MyBundle_zh")); 
			//Pane pane = (BorderPane) fxmlLoader.load(this.getClass().getResource("Application.fxml").openStream());
			ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_zh");

			//Parent root = FXMLLoader.load(getClass()
		             //.getResource("/application/Application.fxml"));
			String a =  bundle.getString("key1");
			lblTextByController=getblTextByController();
			lblTextByController.setText(a);
			String b = bundle.getString("key2");
			choiceCouleur=getchoiceCouleur();
			choiceCouleur.setText(b);
			String c = bundle.getString("key3");
			choiceBackground=getchoiceBackground();
			choiceBackground.setText(c);
			String d = bundle.getString("key4");
			title=gettitle();
			title.setText(d);
			String e = bundle.getString("key5");
			btnHello=getbtnHello();
			btnHello.setText(e);
			
			
			
			break;
		case "English":
			Locale locale1 = new Locale("en","US");  
			ResourceBundle bundle1 = ResourceBundle.getBundle("gestionbrb/language/Language_en",locale1);
			//bundle = ResourceBundle.getBundle("Language/MyBundle");		
			 String A =  bundle1.getString("key1");
				lblTextByController=getblTextByController();
				lblTextByController.setText(A);
				String B = bundle1.getString("key2");
				choiceCouleur=getchoiceCouleur();
				choiceCouleur.setText(B);
				String C = bundle1.getString("key3");
				choiceBackground=getchoiceBackground();
				choiceBackground.setText(C);
				String D = bundle1.getString("key4");
				title=gettitle();
				title.setText(D);
				
				String E = bundle1.getString("key5");
				btnHello=getbtnHello();
				btnHello.setText(E);
				break;
			
		case "Francais":
			Locale locale2 = new Locale("en","US");  
			ResourceBundle bundle2 = ResourceBundle.getBundle("gestionbrb/language/Language_fr",locale2);
			//bundle = ResourceBundle.getBundle("Language/MyBundle");		
			 String Aa =  bundle2.getString("key1");
				lblTextByController=getblTextByController();
				lblTextByController.setText(Aa);
				String Bb = bundle2.getString("key2");
				choiceCouleur=getchoiceCouleur();
				choiceCouleur.setText(Bb);
				String Cc = bundle2.getString("key3");
				choiceBackground=getchoiceBackground();
				choiceBackground.setText(Cc);
				String Dd = bundle2.getString("key4");
				title=gettitle();
				title.setText(Dd);
				
				String Ee = bundle2.getString("key5");
				btnHello=getbtnHello();
				btnHello.setText(Ee);
				break;
			
		}
		
	}




		private Button getbtnHello() {
			// TODO Auto-generated method stub
			return btnHello;
		}




		private TitledPane gettitle() {
			// TODO Auto-generated method stub
			return title;
		}




		private Label getchoiceBackground() {
			// TODO Auto-generated method stub
			return choiceBackground;
		}




		private TitledPane getchoiceCouleur() {
			// TODO Auto-generated method stub
			return choiceCouleur;
		}




		private Label getblTextByController() {
			// TODO Auto-generated method stub
			return lblTextByController;
		}




		public void setParent(MenuPrincipalControleur menuPrincipalControleur) {
			this.parent = menuPrincipalControleur;
			
		}




		}