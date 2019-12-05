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
import gestionbrb.model.Commande;
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
import javafx.scene.layout.AnchorPane;
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
	private Label devise;
	@FXML
	private Label totalQte;
	@FXML
	private TabPane typeProduit;
	@FXML
	private static ObservableList<Produit> produitCommande = FXCollections.observableArrayList();
	private TextArea textArea = new TextArea();
	
	List<Button> listeProduits = new ArrayList<>();
	List<Tab> listeOnglets = new ArrayList<>();
	
	Map<String, Tab> mapTypeParOnglet= new HashMap<>();
	//Map<String, Integer> mapNomParId = new HashMap<>();
	// clé : nom produit ; value = type produit
	//Map<String, String> mapNomParType = new HashMap<>();
	ArrayList<String> nomProduits = new ArrayList<>();
	
	private Commande commande;
	
	DemarrerCommandeControleur parent;

    private static Stage primaryStage;
    private static Stage fenetreAddition;
	private static Stage imprimerAddition;
    
    @FXML
    private AnchorPane fenetre;
    
    DAOCommande daoCommande = new DAOCommande();
    DAOType daoType = new DAOType();
    DAOProduit daoProduit = new DAOProduit();
	
	public CommandeControleur() {
	}

	public void setParent(DemarrerCommandeControleur parent) {
		this.parent = parent;		
	}

	
	
	public static Stage getPrimaryStage() {
		return primaryStage;
	}
	
	List<Float> listePrix = new ArrayList<>();
	
	/*
	 * Initialise la page avec les données.
	 * 
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		produitCommande.clear();
		tableRecap.getItems().clear();

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
	 * Il affiche des onglets en fonction du nombre et du nom des types, et affiche
	 * dans ces onglets les differents produits qui sont du même type. <br>
	 * Il permet également de sélectionner les produits que le client souhaite
	 * commander et l'affiche d'un tableau contenant le nom et le prix des produits
	 * commandés <br>
	 * <br>
	 * Affiche une boite de dialogue si la fonction génére une erreur
	 */
	public void etablirCommande() {
		try {
			devise.setText(DAOCommande.recupererDevise());
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
											String subPrix = tabResultat[1].substring(4); // coupe la devise

											float prix = Float.parseFloat(subPrix);
											Produit prod = new Produit(tabResultat[0], prix, 1);
											prod.setIdProduit(mapNomParId.get(tabResultat[0]));
											listePrix.add(prix);
											mapNomParId.get(tabResultat[0]);
											boolean doublon = false;
											for (Produit prdt : produitCommande) {
												if (prdt.getNomProduit().equals(prod.getNomProduit())) {
													prdt.setQuantiteProduit(prdt.getQuantiteProduit() + 1);
													daoCommande.modifier(commande.getIdCommande(),
															mapNomParId.get(tabResultat[0]), prdt);
													doublon = true;
												}
											}
											if (!doublon) {
												produitCommande.add(prod);
												daoCommande.ajouterProduitCommande(commande.getIdCommande(),
														mapNomParId.get(tabResultat[0]), prod);
											}

											totalPrix.setText(daoCommande.afficherPrixTotal(commande) + "");
											daoCommande.majPrix(commande, prix);
											int qte = daoCommande.afficherQteTotal(commande);

											totalProduit.setText("" + qte);
											totalQte.setText(qte + "");
										} catch (Exception e) {
											FonctionsControleurs.alerteErreur("Erreur d'éxécution",
													"Une erreur est survenue", "Détails: " + e);
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
	public void afficherAddition() {
		if (totalProduit.getText().equals("0")) {
			FonctionsControleurs.alerteAttention("Attention!", "La commande est vide!",
					"Il ne peut donc pas y avoir d'addition. Veuillez imprimer un ticket si vous voulez terminer la commande.");
		} else {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/AdditionCommande.fxml"));
				Parent vueAdditionCommande = (Parent) loader.load();
				setFenetreAddition(new Stage());
				getFenetreAddition().setTitle("-- Addition de la table " + CommandeControleur.this.commande.getNoTable() + " --");
				getFenetreAddition().setScene(new Scene(vueAdditionCommande));
				getFenetreAddition().show();

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
			try {
			daoCommande.supprimer(commande, selectionProduit);
			produitCommande.remove(indexSelection);
			FonctionsControleurs.alerteInfo("Suppression réussie", null, "Le produit "+selectionProduit.getIdProduit()+" vient d'être supprimée!");
			
			totalPrix.setText(daoCommande.afficherPrixTotal(commande)+""+daoCommande.recupererDevise());
			totalProduit.setText(daoCommande.afficherQteTotal(commande)+"");
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
	public void imprimerAddition() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/ImpressionAddition.fxml"));
			Parent addition = (Parent) loader.load();
			setImprimerAddition(new Stage());
			getImprimerAddition().setResizable(false);
			getImprimerAddition().setTitle("-- Addition de la table " + commande.getNoTable() + " --");
			getImprimerAddition().setScene(new Scene(addition));
			getImprimerAddition().show();
			ImprimerAdditionControleur controller = loader.getController();
			controller.setParent(this);
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur d'éxécution", "Une erreur est survenue", "Détails: " + e);
			e.printStackTrace();
		}
	}
    /**
     * Retourne la liste des produits commandés. 
     * @return reservationData
     */
    
    public static ObservableList<Produit> getCommandeData() {
        return produitCommande;
    }

	public static Stage getFenetreAddition() {
		return fenetreAddition;
	}

	public static void setFenetreAddition(Stage fenetreAddition) {
		CommandeControleur.fenetreAddition = fenetreAddition;
	}

	public static Stage getImprimerAddition() {
		return imprimerAddition;
	}

	public static void setImprimerAddition(Stage imprimerAddition) {
		CommandeControleur.imprimerAddition = imprimerAddition;
	}




}
