package gestionbrb.controleur;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import gestionbrb.Connexion;
import gestionbrb.DAO.DAOCommande;
import gestionbrb.DAO.DAOProduit;
import gestionbrb.DAO.DAOType;
import gestionbrb.DAO.DAOUtilisateur;
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
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * Gestion des commandes pour chacune des tables. Permet d'ajouter ou de supprimer les produits commandés par les clients.
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
	private Label total;
	@FXML
	private Button supprimerProduit;
	@FXML
	private Button encaisser;
	@FXML
	private Button demande;
	@FXML
	private Button imprimer;
	@FXML
	private Button retour;
	@FXML
	private Label panneau;
	@FXML
	private TabPane typeProduit;
	@FXML
	private ResourceBundle bundle;
	DAOUtilisateur daoUtilisateur = new DAOUtilisateur();
	@FXML
	private static ObservableList<Produit> produitCommande = FXCollections.observableArrayList();
	private TextArea textArea = new TextArea();
	
	List<Button> listeProduits = new ArrayList<>();
	List<Tab> listeOnglets = new ArrayList<>();
	
	Map<String, Tab> mapTypeParOnglet= new HashMap<>();
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
	private String saisir;
	
	public CommandeControleur() {
	
	}

	@FXML
	private void initialize() {
		
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
			FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue","Détails: "+e);
			e.printStackTrace();
		}
	}
	private void loadLang(String lang, String LANG) {
		Locale locale = new Locale(lang, LANG);  
		
		ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_"+lang, locale);
		colonneProduit.setText(bundle.getString("Produit"));
		colonnePrix.setText(bundle.getString("Prix"));
		colonneQte.setText(bundle.getString("qte"));
		total.setText(bundle.getString("total"));
		retour.setText(bundle.getString("key5"));
		encaisser.setText(bundle.getString("encaisser"));
		demande.setText(bundle.getString("demande"));
		supprimerProduit.setText(bundle.getString("supprimerProduit"));
		imprimer.setText(bundle.getString("imprimer"));
		panneau.setText(bundle.getString("panneau"));
		saisir=bundle.getString("saisir");
		
	}
	public void setParent(DemarrerCommandeControleur parent) {
		this.parent = parent;		
	}

	
	
	public static Stage getPrimaryStage() {
		return primaryStage;
	}
	
	List<Double> listePrix = new ArrayList<>();
	
	/*
	 * Initialise la page avec les données issues de la base de données.
	 * 
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initialize();
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
	 * Affiche une boite de dialogue si la fonction génère une erreur
	 * @author Sophie
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
								btnPlat.setWrapText(true);
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

											double prix = Double.parseDouble(subPrix);
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
											FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue","Détails: "+e);
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
			FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue","Détails: "+e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Affiche l'addition si la liste des produits commandés n'est pas vide, sinon affiche un message.
	 */
	@FXML
	public void afficherAddition() {
		if (totalProduit.getText().equals("0")) {
			FonctionsControleurs.alerteAttention("Attention!", "La commande est vide!",
					"Il ne peut donc pas y avoir d'addition. Veuillez imprimer un ticket si vous voulez terminer la commande.");
		} else {
			try {
				Locale locale = new Locale("fr", "FR");

				ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_fr", locale);
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/AdditionCommande.fxml"),bundle);
				Parent vueAdditionCommande = (Parent) loader.load();
				setFenetreAddition(new Stage());
				getFenetreAddition().setTitle("-- Addition de la table " + CommandeControleur.this.commande.getNoTable() + " --");
				getFenetreAddition().setScene(new Scene(vueAdditionCommande));
				getFenetreAddition().show();
				getFenetreAddition().getIcons().add(new Image(
		          	      Connexion.class.getResourceAsStream( "ico.png" ))); 

				AdditionControleur controller = loader.getController();
				controller.setParent(this);

			} catch (Exception e) {
				FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue","Détails: "+e);
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Supprime le produit selectionné s'il y en a un, sinon affiche un message d'erreur.
	 */
	public void supprimerProduit() {
		Produit selectionProduit = tableRecap.getSelectionModel().getSelectedItem();
		int indexSelection = tableRecap.getSelectionModel().getSelectedIndex();
		if (indexSelection >= 0) {
			try {
			daoCommande.supprimer(commande, selectionProduit);
			produitCommande.remove(indexSelection);
			FonctionsControleurs.alerteInfo("Suppression r閡ssie", null, "Le produit "+selectionProduit.getIdProduit()+" vient d'阾re supprim閑!");
			
			totalPrix.setText(daoCommande.afficherPrixTotal(commande)+""+DAOCommande.recupererDevise());
			totalProduit.setText(daoCommande.afficherQteTotal(commande)+"");
			}
			catch(Exception e) {
				FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue","Détails: "+e);
				e.printStackTrace();
			}

		} else {
			FonctionsControleurs.alerteAttention("Aucune sélection", "Aucune table de séléctionnée!",
					"Selectionnez une table pour pouvoir la supprimer");
		}
	}

	/**
	 * Ouvre une boite de dialogue permettant de saisir une quelqueconque demande du client.
	 */
	@FXML
	public void demandeSpeciale() {
		Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Demande spéciale");
        alert.setHeaderText(saisir);
 
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
	
	/** 
	 * Affiche le ticket lié à l'addition en vue de l'imprimer.
	 */
	@FXML
	public void imprimerTicket() {
		try {
			Locale locale = new Locale("fr", "FR");

			ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_fr", locale);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../vue/ImpressionAddition.fxml"),bundle);
			Parent addition = (Parent) loader.load();
			setImprimerAddition(new Stage());
			getImprimerAddition().setResizable(false);
			getImprimerAddition().setTitle("-- Addition de la table " + commande.getNoTable() + " --");
			getImprimerAddition().setScene(new Scene(addition));
			getImprimerAddition().show();
			getImprimerAddition().getIcons().add(new Image(
	          	      Connexion.class.getResourceAsStream( "ico.png" ))); 
			ImprimerAdditionControleur controller = loader.getController();
			controller.setParent(this);
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue","Détails: "+e);
			e.printStackTrace();
		}
	}
	
	public void retourMenuPrincipal() {
		DemarrerCommandeControleur.getFenetreCommande().close();
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
