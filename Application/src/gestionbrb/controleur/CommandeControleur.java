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

import gestionbrb.model.Commande;
import gestionbrb.model.Produit;
import gestionbrb.util.bddUtil;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class CommandeControleur extends FonctionsControleurs implements Initializable {
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
	private ObservableList<Produit> produitCommande = FXCollections.observableArrayList();
	
	
	List<Button> listeProduits = new ArrayList<>();
	List<Tab> listeOnglets = new ArrayList<>();
	
	Map<String, Tab> mapTypeParOnglet= new HashMap<>();
	
	// clé : nom produit ; value = type produit
	Map<String, String> mapNomParType = new HashMap<>();
	Map<String, Integer> mapNomParId = new HashMap<>();
	ArrayList<String> nomProduits = new ArrayList<>();
	
	private Commande commande;
	
	DemarrerCommandeControleur parent;
    private Stage primaryStage;
	
	public CommandeControleur() {
	}
	
	
	class HandlerBtn1 implements EventHandler<ActionEvent>{
		public void handle(ActionEvent e) {
			
		}
	}

	public void setParent(DemarrerCommandeControleur parent) {
		this.parent = parent;		
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}
	List<Float> listePrix = new ArrayList<>();
	public float addition(float prix) {
		float prixT = 0;
		return prixT + prix;
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		commande = DemarrerCommandeControleur.getCommande();
		infoTable.setText("Table "+commande.getNoTable()+" ("+commande.getNbCouverts()+" couvert(s))");
		colonneProduit.setCellValueFactory(cellData -> cellData.getValue().nomProduitProperty());
		colonnePrix.setCellValueFactory(cellData -> cellData.getValue().prixProduitProperty());
		colonneQte.setCellValueFactory(cellData -> cellData.getValue().quantiteProduitProperty());
		tableRecap.setItems(produitCommande);
		try {
		Connection conn = bddUtil.dbConnect();
			listeProduits.clear();
			listeOnglets.clear();
			listeProduits.clear();
			ResultSet rs = conn.createStatement().executeQuery("select idProduit, produit.nom, prix, type_produit.nom from produit inner join type_produit on produit.idType=type_produit.idType");
			ResultSet commande = conn.createStatement().executeQuery("select count(*) from contientproduit where idCommande="+CommandeControleur.this.commande.getIdCommande());
			while (rs.next()) {
				Tab tab = new Tab(rs.getString("type_produit.nom"));
				mapTypeParOnglet.put(rs.getString("type_produit.nom"), tab);
				String typeProduit = rs.getString("type_produit.nom");
				String nomProduit = rs.getString("produit.nom")+"\n €"+rs.getString("prix");
				int idProduit = rs.getInt("idProduit");
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
										//System.out.println(arg0); // affiche javafx.event.ActionEvent[source=Button@1b17cef6[styleClass=button]'Lieu jaune a l'estragon 0.00 €']
										try {
											String rgx = "\n";
											String[] tabResultat = produit.getKey().split(rgx); // tab[0] -> nom ;
																								// tab[1] -> prix
											String subPrix = tabResultat[1].substring(2); // coupe le signe €
											float prix = Float.parseFloat(subPrix);
											Produit prod = new Produit(tabResultat[0], prix, 1);
											produitCommande.add(prod);
											listePrix.add(prix);
											totalProduit.setText("" + produitCommande.size());
											mapNomParId.get(produit.getKey());
											bddUtil.dbQueryExecute("INSERT INTO `contientproduit` (`idProduit`, `idCommande`, `qte`) VALUES ("
															+ mapNomParId.get(produit.getKey()) + ", "
															+ CommandeControleur.this.commande.getIdCommande() + ", "
															+ "'1') ");
											ResultSet res = conn.createStatement().executeQuery("SELECT contientproduit.idProduit, idCommande, contientproduit.qte, sum(produit.prix) FROM `contientproduit` inner join produit on contientproduit.idProduit = produit.idProduit where idCommande = "+CommandeControleur.this.commande.getIdCommande());
											while (res.next()) {
												String tprix = res.getString("sum(produit.prix)");
												totalPrix.setText(tprix+" €");
											}
										} catch (Exception e) {
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
			alerteErreur("Erreur!", "Erreur d'éxecution", "Détails: "+e);
		}
		
	}

	
	

}
