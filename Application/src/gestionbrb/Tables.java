package gestionbrb;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import gestionbrb.controleur.ModifierTablesControleur;
import gestionbrb.controleur.TablesControleur;
import gestionbrb.model.Table;
import gestionbrb.util.bddUtil;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * 
 * @author Sophie
 *
 */
public class Tables extends Application {
	private Stage primaryStage;
	private static ObservableList<Table> tables = FXCollections.observableArrayList(); // Ca tu le déplace dans ton controleur

	public Tables() { // Si y'a quelquechose ici tu le mets dans le controleur aussi
		try {
			Connection conn = bddUtil.dbConnect();
			ResultSet rs = conn.createStatement().executeQuery("select * from tables");
			while (rs.next()) {
				tables.add(new Table(rs.getInt("idTable"), rs.getInt("noTable"), rs.getInt("nbCouverts_Min"), rs.getInt("nbCouverts_Max"), rs.getInt("occupation")));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public static ObservableList<Table> getTableData() {
		return tables;
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Administration -- Tables");
		afficheTable();
	}
	/**
	 * affiche la fenetre de gestion des tables 
	 * liste les tables
	 */

	public void afficheTable() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Tables.class.getResource("vue/GererTables.fxml"));
			AnchorPane tablesOverview = (AnchorPane) loader.load();

			Scene scene = new Scene(tablesOverview);
			primaryStage.setScene(scene);
			primaryStage.show();

			TablesControleur controller = loader.getController();
			controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
