package gestionbrb.controleur;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import gestionbrb.model.Commande;
import gestionbrb.model.Produit;
import gestionbrb.model.Reservations;
import gestionbrb.model.Table;
import gestionbrb.util.bddUtil;
import gestionbrb.vue.AdditionControleur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * @author Sophie
 */

public class CommandeControleur implements Initializable {
	@FXML
	private Label infoTable;
	@FXML
	private TableView<Produit> tableRecap;
	@FXML
	private TableColumn<Produit, String> colonneProduit;
	@FXML
	private TableColumn<Produit, Number> colonnePrix;
	@FXML
	private TableColumn<Produit, Number> colonneQte;
	@FXML
	private Label totalProduit;
	@FXML
	private Label totalPrix;
	@FXML
	private TabPane typeProduit;
	@FXML
	private static ObservableList<Produit> produitCommande = FXCollections.observableArrayList();
	private TextArea textArea = new TextArea();
	
	List<Button> listeProduits = new ArrayList<>();
	List<Tab> listeOnglets = new ArrayList<>();
	
	Map<String, Tab> mapTypeParOnglet= new HashMap<>();
	Map<String, Integer> mapNomParId = new HashMap<>();
	// clé : nom produit ; value = type produit
	Map<String, String> mapNomParType = new HashMap<>();
	ArrayList<String> nomProduits = new ArrayList<>();
	
	private Commande commande;
	
	DemarrerCommandeControleur parent;

    private Stage primaryStage;
    private Stage fenetreAddition;
	
	public CommandeControleur() {
	}

	public void setParent(DemarrerCommandeControleur parent) {
		this.parent = parent;		
	}

	
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	List<Float> listePrix = new ArrayList<>();
	
	/*
	 * Initialise la page avec les données.
	 * 
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		commande = DemarrerCommandeControleur.getCommande();
		infoTable.setText("Table "+commande.getNoTable()+" ("+commande.getNbCouverts()+" couvert(s))");
		colonneProduit.setCellValueFactory(cellData -> cellData.getValue().nomProduitProperty());
		colonnePrix.setCellValueFactory(cellData -> cellData.getValue().prixProduitProperty());
		colonneQte.setCellValueFactory(cellData -> cellData.getValue().quantiteProduitProperty());
		tableRecap.setItems(produitCommande);
		etablirCommande();
		
	}
	
	/**
	 * Fonction principale du controleur. <br>
	 * Il affiche des onglets en fonction du nombre et du nom des types, et affiche dans ces onglets les differents produits qui sont du même type. <br>
	 * Il permet également de sélectionner les produits que le client souhaite commander et l'affiche d'un tableau contenant le nom et le prix des produits commandés
	 * <br>
	 * <br>
	 * Affiche une boite de dialogue si la fonction génére une erreur
	 */
	public void etablirCommande() {
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
									public void handle(ActionEvent arg0) {
										try {
											String rgx = "\n";
											String[] tabResultat = produit.getKey().split(rgx); // tab[0] -> nom ;
																								// tab[1] -> prix
											String subPrix = tabResultat[1].substring(2); // coupe le signe €
											float prix = Float.parseFloat(subPrix);
											Produit prod = new Produit(tabResultat[0], prix, 1);
											prod.setIdProduit(mapNomParId.get(produit.getKey()));
											listePrix.add(prix);
											mapNomParId.get(produit.getKey());
											boolean doublon = false;
											for (Produit prdt : produitCommande) {
												if (prdt.getNomProduit().equals(prod.getNomProduit())) {
													prdt.setQuantiteProduit(prdt.getQuantiteProduit()+1);
													bddUtil.dbQueryExecute("UPDATE `contenirproduit` SET qte='"+prdt.getQuantiteProduit()+"' WHERE idProduit = "+mapNomParId.get(produit.getKey())+" AND idCommande = "+CommandeControleur.this.commande.getIdCommande());
													doublon = true;
												}
											}
											if(!doublon) {
												produitCommande.add(prod);
												bddUtil.dbQueryExecute("INSERT INTO `contenirproduit` (`idProduit`, `idCommande`, `qte`) VALUES ("
														+ mapNomParId.get(produit.getKey()) + ", "
														+ CommandeControleur.this.commande.getIdCommande() + ", "
														+ "'1') ");
											}
											ResultSet commandeDB = conn.createStatement().executeQuery("SELECT sum(contenirproduit.qte*produit.prix) as prixt FROM `contenirproduit` INNER JOIN produit on contenirproduit.idProduit = produit.idProduit WHERE idCommande ="+CommandeControleur.this.commande.getIdCommande());
											
											ResultSet qteTotal = conn.createStatement().executeQuery("SELECT sum(qte) FROM `contenirproduit` WHERE idCommande = '"+CommandeControleur.this.commande.getIdCommande()+"'");
											while (commandeDB.next()) {
												String tprix = commandeDB.getString("prixt");
												totalPrix.setText(tprix+" €");
									
											}
											while(qteTotal.next()) {
												int qte = qteTotal.getInt("sum(qte)");
												totalProduit.setText(""+qte);
											}
										} catch (Exception e) {
											FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Une erreur est survenue","Détails: "+e);
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
		}
	}
	
	@FXML
	public void afficherAddition() {
		if (totalProduit.getText().equals("0")) {
			FonctionsControleurs.alerteAttention("Attention!", "La commande est vide!",
					"Il ne peut donc pas y avoir d'addition. Veuillez imprimer un ticket si vous voulez terminer la commande.");
		} else {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/AdditionCommande.fxml"));
				Parent vueCommande = (Parent) loader.load();
				fenetreAddition = new Stage();
				fenetreAddition
						.setTitle("-- Addition de la table " + CommandeControleur.this.commande.getNoTable() + " --");
				fenetreAddition.setScene(new Scene(vueCommande));
				fenetreAddition.show();

				AdditionControleur controller = loader.getController();
				controller.setParent(this);

			} catch (Exception e) {
				FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Une erreur est survenue", "Détails: " + e);
				e.printStackTrace();
			}
		}
	}
	
	public void supprimerProduit() {
		Produit selectionProduit = tableRecap.getSelectionModel().getSelectedItem();
		int indexSelection = tableRecap.getSelectionModel().getSelectedIndex();
		if (indexSelection >= 0) {
			System.out.println(selectionProduit.getIdProduit());
			try {
				Connection conn = bddUtil.dbConnect();
				//System.out.println(selectionProduit.);
				
			PreparedStatement suppression = conn.prepareStatement("DELETE FROM `contenirproduit` WHERE `contenirproduit`.`idProduit` = ? AND `contenirproduit`.`idCommande` = "+CommandeControleur.this.commande.getIdCommande());
			System.out.println(suppression);
			suppression.setInt(1, (selectionProduit.getIdProduit()));
			suppression.execute();
			FonctionsControleurs.alerteInfo("Suppression réussie", null, "Le produit "+selectionProduit.getIdProduit()+" vient d'être supprimée!");
			produitCommande.remove(indexSelection);
			ResultSet commandeDB = conn.createStatement().executeQuery("SELECT sum(contenirproduit.qte*produit.prix) as prixt FROM `contenirproduit` INNER JOIN produit on contenirproduit.idProduit = produit.idProduit WHERE idCommande ="+CommandeControleur.this.commande.getIdCommande());
			
			ResultSet qteTotal = conn.createStatement().executeQuery("SELECT sum(qte) FROM `contenirproduit` WHERE idCommande = '"+CommandeControleur.this.commande.getIdCommande()+"'");
			while (commandeDB.next()) {
				String tprix = commandeDB.getString("prixt");
				totalPrix.setText(tprix+" €");
	
			}
			while(qteTotal.next()) {
				int qte = qteTotal.getInt("sum(qte)");
				totalProduit.setText(""+qte);
			}
			
			conn.close();
			suppression.close();
			
			}
			catch(Exception e) {
				FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Une erreur est survenue","Détails: "+e);
				e.printStackTrace();
			}
			
		

		} else {
			// Si rien n'est séléctionné
			FonctionsControleurs.alerteAttention("Aucune sélection", "Aucune table de sélectionnée!",
					"Selectionnez une table pour pouvoir la supprimer");
		}
	}
	
	@FXML
	public void demandeSpeciale() {
		Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Demande spéciale");
        alert.setHeaderText("Saisir une demande spéciale d'un client");
 
        VBox conteneur = new VBox();
        conteneur.setPrefWidth(300);
        conteneur.setPrefHeight(150);
        
        textArea.setPromptText("Veuillez saisir la demande puis valider sur Ok");
        textArea.setText(textArea.getText());
        textArea.setWrapText(true);
        conteneur.getChildren().add(textArea);
       
 
        // Set content for Dialog Pane
        alert.getDialogPane().setContent(conteneur);

        alert.showAndWait();
	}
	
	@FXML
	public void afficherTicket() {
		
	}
    /**
     * Retourne la liste des produits commandés. 
     * @return reservationData
     */
    
    public static ObservableList<Produit> getCommandeData() {
        return produitCommande;
    }

	public Stage getFenetreAddition() {
		return fenetreAddition;
	}

	public void setFenetreAddition(Stage fenetreAddition) {
		this.fenetreAddition = fenetreAddition;
	}

}
