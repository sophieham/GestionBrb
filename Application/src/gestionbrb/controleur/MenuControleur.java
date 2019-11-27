package gestionbrb.controleur;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import gestionbrb.model.Produit;
import gestionbrb.util.bddUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.TextAlignment;

public class MenuControleur implements Initializable {
	private static ObservableList<Produit> produitCommande = FXCollections.observableArrayList();
	private TextArea textArea = new TextArea();
	
	List<Button> listeProduits = new ArrayList<>();
	List<Tab> listeOnglets = new ArrayList<>();
	
	Map<String, Tab> mapTypeParOnglet= new HashMap<>();
	
	// clé : nom ; value : id
	Map<String, Integer> mapNomParId = new HashMap<>();
	
	// clé : nom produit ; value = type produit
	Map<String, String> mapNomParType = new HashMap<>();
	ArrayList<String> nomProduits = new ArrayList<>();

    @FXML
    private AnchorPane fenetre;

    @FXML
    private TabPane typeProduit;
    
    private MenuPrincipalControleur parent;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			Connection conn = bddUtil.dbConnect();
				listeProduits.clear();
				listeOnglets.clear();
				listeProduits.clear();
				ResultSet produitDB = conn.createStatement().executeQuery("select idProduit, produit.nom, prix, type_produit.nom from produit inner join type_produit on produit.idType=type_produit.idType");
				while (produitDB.next()) {
					Tab tab = new Tab(produitDB.getString("type_produit.nom"));
					mapTypeParOnglet.put(produitDB.getString("type_produit.nom"), tab);
					String typeProduit = produitDB.getString("type_produit.nom");
					String nomProduit = produitDB.getString("produit.nom")+"\n €"+produitDB.getString("prix");
					int idProduit = produitDB.getInt("idProduit");
					mapNomParId.put(nomProduit, idProduit);
					mapNomParType.put(nomProduit, typeProduit);
					nomProduits.add(nomProduit);
				}
				for (Entry<String, Tab> tab : mapTypeParOnglet.entrySet()) {
					FlowPane fp = new FlowPane();
					fp.setPadding(new Insets(5,5,5,5));
					fp.setHgap(5);
					fp.setVgap(5);
					tab.getValue().setOnSelectionChanged(new EventHandler<Event>() {
						public void handle(Event event) {
							fp.getChildren().clear();
							fp.setAlignment(Pos.BASELINE_CENTER);
							
							for (Map.Entry<String, String> produit : mapNomParType.entrySet()) {
								if (tab.getKey().equals(produit.getValue())) {
									Button btnPlat = new Button(produit.getKey());
									btnPlat.setPrefSize(200, 100);
									btnPlat.setTextAlignment(TextAlignment.CENTER);
									btnPlat.setAlignment(Pos.CENTER);
									btnPlat.setOnAction(new EventHandler<ActionEvent>() {
										@Override
										public void handle(ActionEvent event) {
											try {
												Connection conn = bddUtil.dbConnect();
												Alert alert = new Alert(AlertType.INFORMATION);
												int idProd = mapNomParId.get(btnPlat.getText());
												ResultSet produitsDB = conn.createStatement().executeQuery("SELECT idProduit,`nom`,`description`,`ingredients` FROM `produit` WHERE idProduit = "+idProd);
												while(produitsDB.next()) {
												alert.setTitle("Détails du produit n°"+idProd);
												alert.setHeaderText(produitsDB.getString("nom"));
												VBox conteneur = new VBox();
												conteneur.setPrefWidth(500);
												conteneur.setPrefHeight(100);
												conteneur.setAlignment(Pos.CENTER);
												Label description = new Label(produitsDB.getString("description"));
												description.setFont(Font.font("Arial", FontPosture.ITALIC, 20));
												description.setWrapText(true);
												Label ingredients = new Label(produitsDB.getString("ingredients"));
												ingredients.setWrapText(true);
												conteneur.getChildren().addAll(description, ingredients);
												alert.getDialogPane().setContent(conteneur);
												}
									 
												// Set content for Dialog Pane
												//alert.getDialogPane().setContent(conteneur);

												alert.showAndWait();
											} catch (Exception e) {
												FonctionsControleurs.alerteErreur("Erreur!", "Erreur d'éxecution", "Détails: "+e);
												e.printStackTrace();
											}
										}
											
									});
		
									fp.getChildren().add(btnPlat);
								}
							}
							tab.getValue().setContent(fp);

						}
					});
				}
				for (Produit prdt : produitCommande) {
					System.out.println(prdt.getNomProduit());
					System.out.println("après nomproduit:");
				}
				typeProduit.getTabs().addAll(mapTypeParOnglet.values());
			} catch (Exception e) {
				FonctionsControleurs.alerteErreur("Erreur!", "Erreur d'éxecution", "Détails: "+e);
				e.printStackTrace();
			}
		}
		

    @FXML
    void retourMenuPrincipal(MouseEvent event) {
    	MenuPrincipalControleur.getCarte().close();
    	}


	public void setParent(MenuPrincipalControleur menuPrincipalControleur) {
		this.parent = menuPrincipalControleur;
		
	}

}
