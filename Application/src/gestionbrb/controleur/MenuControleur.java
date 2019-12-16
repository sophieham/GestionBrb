package gestionbrb.controleur;

import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import gestionbrb.DAO.DAOCommande;
import gestionbrb.DAO.DAOProduit;
import gestionbrb.DAO.DAOType;
import gestionbrb.DAO.DAOUtilisateur;
import gestionbrb.model.Produit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
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
	
	// cl� : nom ; value : id
	Map<String, Integer> mapNomParId = new HashMap<>();
	
	// cl� : nom produit ; value = type produit
	Map<String, String> mapNomParType = new HashMap<>();
	ArrayList<String> nomProduits = new ArrayList<>();

	DAOCommande daoCommande = new DAOCommande();
	DAOType daoType = new DAOType();
	DAOProduit daoProduit = new DAOProduit();
	DAOUtilisateur daoUtilisateur = new DAOUtilisateur();
    @FXML
	private ResourceBundle bundle;
    @FXML
    private AnchorPane fenetre;
    @FXML
    private Label label;
    @FXML
    private TabPane typeProduit;
    
    @SuppressWarnings("unused")
	private MenuPrincipalControleur parent;

    /**
	 * Fonction principale du controleur. <br>
	 * Il affiche des onglets en fonction du nombre et du nom des types, et affiche
	 * dans ces onglets les differents produits qui sont du même type. <br>
	 * Affiche aussi une description et les ingredients du produit lorsqu'on clique dessus.
	 * <br>
	 * Affiche une boite de dialogue si la fonction génére une erreur
	 * @author Sophie
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			initialize();
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
			FonctionsControleurs.alerteErreur("Erreur!", "Erreur d'閤ecution", "D閠ails: " + e);
			e.printStackTrace();
		}
	}
		

	@FXML
	public void initialize() {
	try {
		String langue = daoUtilisateur.recupererLangue(ConnexionControleur.getUtilisateurConnecte().getIdUtilisateur());
		
		switch(langue) {
		case "fr":
			loadLang("fr", "FR");
			break;
		case "en":
			loadLang("en", "US");
			break;
		case "zh":
			loadLang("zh", "CN");
			break;
		}
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
    
    private void loadLang(String lang, String LANG) {
		Locale locale = new Locale(lang, LANG);  
		bundle = ResourceBundle.getBundle("gestionbrb/language/Language_"+lang, locale);
		label.setText(bundle.getString("cliquer"));
		

    }
    
    /**
	 * Retourne au menu principal
	 * @param event
	 */
    @FXML
    void retourMenuPrincipal(MouseEvent event) {
    	try {
			Locale locale = new Locale("fr", "FR");

			ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_fr", locale);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/MenuPrincipal.fxml"), bundle);
			Parent menuPrincipal = (Parent) loader.load();
			fenetre.getChildren().setAll(menuPrincipal); // remplace la fenetre de connexion par celle du menu principal
			
			MenuPrincipalControleur controller = loader.getController();
			controller.setParent(this);
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Une erreur est survenue", "Détails: " + e);
			e.printStackTrace();
		}
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