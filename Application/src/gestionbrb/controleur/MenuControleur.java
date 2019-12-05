package gestionbrb.controleur;

import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import gestionbrb.DAO.DAOCommande;
import gestionbrb.DAO.DAOProduit;
import gestionbrb.DAO.DAOType;
import gestionbrb.model.Produit;
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

	List<Button> listeProduits = new ArrayList<>();
	List<Tab> listeOnglets = new ArrayList<>();
	
	Map<String, Tab> mapTypeParOnglet= new HashMap<>();
	
	// clé : nom ; value : id
	Map<String, Integer> mapNomParId = new HashMap<>();
	
	// clé : nom produit ; value = type produit
	Map<String, String> mapNomParType = new HashMap<>();
	ArrayList<String> nomProduits = new ArrayList<>();

	DAOCommande daoCommande = new DAOCommande();
	DAOType daoType = new DAOType();
	DAOProduit daoProduit = new DAOProduit();
	
    @FXML
    private AnchorPane fenetre;

    @FXML
    private TabPane typeProduit;
    
    @SuppressWarnings("unused")
	private MenuPrincipalControleur parent;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			listeProduits.clear();
			listeOnglets.clear();
			listeProduits.clear();

			for (String nom : daoType.recupererType()) {
				Tab tab = new Tab(nom);
				tab.setStyle("-fx-font-size: 20px;");
				mapTypeParOnglet.put(nom, tab);
			}
			Map<String, Integer> mapNomParId = daoProduit.recupererIDProduit();

			Map<String, String> mapNomParType = daoProduit.recupererTypeProduit();
			nomProduits.addAll(daoProduit.recupererNomProduit());

			for (Entry<String, Tab> tab : mapTypeParOnglet.entrySet()) {
				FlowPane fp = new FlowPane();
				tab.getValue().setOnSelectionChanged(new EventHandler<Event>() {
					public void handle(Event event) {
						fp.getChildren().clear();
						fp.setAlignment(Pos.BASELINE_CENTER);
						fp.setPadding(new Insets(5, 5, 5, 5));
						fp.setHgap(5);
						fp.setVgap(5);
						for (Map.Entry<String, String> produit : mapNomParType.entrySet()) {
							if (tab.getKey().equals(produit.getValue())) {
								Button btnPlat = new Button(produit.getKey());
								btnPlat.setPrefSize(300, 100);
								btnPlat.setTextAlignment(TextAlignment.CENTER);
								btnPlat.setAlignment(Pos.CENTER);
								btnPlat.setOnAction(new EventHandler<ActionEvent>() {
									@Override
									public void handle(ActionEvent arg0) {
										try {
											String rgx = "\n";
											String[] tabResultat = produit.getKey().split(rgx); // tab[0] -> nom ;
											Alert alert = new Alert(AlertType.INFORMATION);
											int idProd = mapNomParId.get(tabResultat[0]);
											alert.setTitle("Détails du produit n°"+idProd);
											alert.setHeaderText(daoProduit.afficherDetailsProduit(idProd).get(0));
											VBox conteneur = new VBox();
											conteneur.setPrefWidth(500);
											conteneur.setPrefHeight(100);
											conteneur.setAlignment(Pos.CENTER);
											Label description = new Label(daoProduit.afficherDetailsProduit(idProd).get(1));
											description.setFont(Font.font("Arial", FontPosture.ITALIC, 20));
											description.setWrapText(true);
											Label ingredients = new Label(daoProduit.afficherDetailsProduit(idProd).get(2));
											ingredients.setWrapText(true);
											conteneur.getChildren().addAll(description, ingredients);
											alert.getDialogPane().setContent(conteneur);
											

											alert.showAndWait(); 
										} catch (Exception e) {
											FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Une erreur est survenue", "Détails: " + e);
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
			typeProduit.getTabs().addAll(mapTypeParOnglet.values());
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur!", "Erreur d'éxecution", "Détails: " + e);
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


	public static ObservableList<Produit> getProduitCommande() {
		return produitCommande;
	}


	public static void setProduitCommande(ObservableList<Produit> produitCommande) {
		MenuControleur.produitCommande = produitCommande;
	}

}
