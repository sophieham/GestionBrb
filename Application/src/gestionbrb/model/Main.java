package application;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	/**private Locale locale = new Locale("en");

	public Locale getLocale(){
	    return locale;
	}

	public void setLocale(Locale locale){
	    this.locale = locale;
	}
**/
	
	@Override
	public void start(Stage primaryStage) throws Exception{
 try
		 {
			 //AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Application.fxml"));
	 Locale locale = new Locale("fr", "FR");  

	ResourceBundle bundle = ResourceBundle.getBundle("language/language_fr",locale);
	 
	 
	 Parent root = FXMLLoader.load(getClass()
             .getResource("/application/Parametres.fxml"),bundle);
	
	
	 
	 // primaryStage.setTitle("My Application");
			 //ApplicationController controller = loader.getController();
 
	 primaryStage.setScene(new Scene(root));



     
         primaryStage.show();

     } catch(Exception e) {
         e.printStackTrace();
     }
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}