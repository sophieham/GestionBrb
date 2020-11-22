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

// TODO: Auto-generated Javadoc
/**
 * Gestion des commandes pour chacune des tables. Permet d'ajouter ou de supprimer les produits commandés par les clients.
 * @author Sophie
 */

public class CommandeControleur implements Initializable {
	
	/** The info table. */
	@FXML
	private Label infoTable;
	
	/** The table recap. */
	@FXML
	private TableView<Produit> tableRecap;
	
	/** The colonne produit. */
	@FXML
	private TableColumn<Produit, String> colonneProduit;
	
	/** The colonne prix. */
	@FXML
	private TableColumn<Produit, Number> colonnePrix;
	
	/** The colonne qte. */
	@FXML
	private TableColumn<Produit, Number> colonneQte;
	
	/** The total produit. */
	@FXML
	private Label totalProduit;
	
	/** The total prix. */
	@FXML
	private Label totalPrix;
	
	/** The devise. */
	@FXML
	private Label devise;
	
	/** The total qte. */
	@FXML
	private Label totalQte;
	
	/** The total. */
	@FXML
	private Label total;
	
	/** The supprimer produit. */
	@FXML
	private Button supprimerProduit;
	
	/** The encaisser. */
	@FXML
	private Button encaisser;
	
	/** The demande. */
	@FXML
	private Button demande;
	
	/** The imprimer. */
	@FXML
	private Button imprimer;
	
	/** The retour. */
	@FXML
	private Button retour;
	
	/** The panneau. */
	@FXML
	private Label panneau;
	
	/** The type produit. */
	@FXML
	private TabPane typeProduit;
	
	/** The bundle. */
	@FXML
	private ResourceBundle bundle;
	
	/** The dao utilisateur. */
	DAOUtilisateur daoUtilisateur = new DAOUtilisateur();
	
	/** The produit commande. */
	@FXML
	private static ObservableList<Produit> produitCommande = FXCollections.observableArrayList();
	
	/** The text area. */
	private TextArea textArea = new TextArea();
	
	/** The liste produits. */
	List<Button> listeProduits = new ArrayList<>();
	
	/** The liste onglets. */
	List<Tab> listeOnglets = new ArrayList<>();
	
	/** The map type par onglet. */
	Map<String, Tab> mapTypeParOnglet= new HashMap<>();
	
	/** The nom produits. */
	ArrayList<String> nomProduits = new ArrayList<>();
	
	/** The commande. */
	private Commande commande;
	
	/** The parent. */
	DemarrerCommandeControleur parent;

    /** The primary stage. */
    private static Stage primaryStage;
    
    /** The fenetre addition. */
    private static Stage fenetreAddition;
	
	/** The imprimer addition. */
	private static Stage imprimerAddition;
    
    /** The fenetre. */
    @FXML
    private AnchorPane fenetre;
    
    /** The dao commande. */
    DAOCommande daoCommande = new DAOCommande();
    
    /** The dao type. */
    DAOType daoType = new DAOType();
    
    /** The dao produit. */
    DAOProduit daoProduit = new DAOProduit();
	
	/** The saisir. */
	private String saisir;
	
	/**
	 * Instantiates a new commande controleur.
	 */
	public CommandeControleur() {
	
	}

	/**
	 * Initialize.
	 */
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
	
	/**
	 * Load lang.
	 *
	 * @param lang the lang
	 * @param LANG the lang
	 */
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
	
	/**
	 * Sets the parent.
	 *
	 * @param parent the new parent
	 */
	public void setParent(DemarrerCommandeControleur parent) {
		this.parent = parent;		
	}

	
	
	/**
	 * Gets the primary stage.
	 *
	 * @return the primary stage
	 */
	public static Stage getPrimaryStage() {
		return primaryStage;
	}
	
	/** The liste prix. */
	List<Double> listePrix = new ArrayList<>();
	
	/**
	 * Initialize.
	 *
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 */
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
			FonctionsControleurs.alerteInfo("Suppression réussie", null, "Le produit "+selectionProduit.getIdProduit()+" vient d'être supprimé!");
			
			totalPrix.setText(daoCommande.afficherPrixTotal(commande)+""+DAOCommande.recupererDevise());
			totalProduit.setText(daoCommande.afficherQteTotal(commande)+"");
			}
			catch(Exception e) {
				FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue","Détails: "+e);
				e.printStackTrace();
			}

		} else {
			FonctionsControleurs.alerteAttention("Aucune sélection", "Aucune table de sélectionnée!",
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
	 * Affiche le ticket lié à  l'addition en vue de l'imprimer.
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
	
	/**
	 * Retour menu principal.
	 */
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

	/**
	 * Gets the fenetre addition.
	 *
	 * @return the fenetre addition
	 */
	public static Stage getFenetreAddition() {
		return fenetreAddition;
	}

	/**
	 * Sets the fenetre addition.
	 *
	 * @param fenetreAddition the new fenetre addition
	 */
	public static void setFenetreAddition(Stage fenetreAddition) {
		CommandeControleur.fenetreAddition = fenetreAddition;
	}

	/**
	 * Gets the imprimer addition.
	 *
	 * @return the imprimer addition
	 */
	public static Stage getImprimerAddition() {
		return imprimerAddition;
	}

	/**
	 * Sets the imprimer addition.
	 *
	 * @param imprimerAddition the new imprimer addition
	 */
	public static void setImprimerAddition(Stage imprimerAddition) {
		CommandeControleur.imprimerAddition = imprimerAddition;
	}




}
