package gestionbrb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GestionStockServ extends Application{

	@Override
	public void start(Stage stage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(GestionStockAdmin.class.getResource("GestionStockServ.fxml"));
			AnchorPane tablesOverview = (AnchorPane) loader.load();

			Scene scene = new Scene(tablesOverview);
			stage.setTitle("GestionStock_Serveur");
			stage.setScene(scene);
			stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch(args);
	}

}
