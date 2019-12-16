package gestionbrb.controleur;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import gestionbrb.DAO.DAOCommande;
import gestionbrb.DAO.DAOUtilisateur;
import gestionbrb.model.Commande;
import gestionbrb.model.Utilisateur;
import gestionbrb.util.bddUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class HistoriqueCommandeControleur {
    @FXML
    private TableView<Commande> commandeTable;
    @FXML
    private TableColumn<Commande, Number> colonneIDCommande;
    @FXML
    private TableColumn<Commande, Number> colonneTable;
    @FXML
    private TableColumn<Commande, String> colonneDate;
    @FXML
    private TableColumn<Commande, Number> colonneMontant;
    @FXML
    private Label totalCommandeLbl;
    @FXML
    private Label totalMontantLbl;
    @FXML
    private Label totalServeurLbl;
    @FXML
    private Label affichage;
    @FXML
    private Label devise;
   
    
    @FXML
    private TableView<Utilisateur> connexionTable;
    @FXML
    private TableColumn<Utilisateur, Number> colonneID;
    @FXML
    private TableColumn<Utilisateur, String> colonneIdentifiant;
    @FXML
    private TableColumn<Utilisateur, String> colonneDateConnexion;
    @FXML
    private Label totalConnexionsLbl;
    @FXML
    private Label affichages;
	@FXML
	private ResourceBundle bundle;
    @FXML
    private Button detail;
    @FXML
    private Button fermer;
    @FXML
    private Button fermer1;
    @FXML
    private MenuItem jour;
    @FXML
    private MenuItem week;   
    @FXML
    private MenuItem mois;
    @FXML
    private MenuItem annee;
    @FXML
    private MenuItem jour1;
    @FXML
    private MenuItem week1;   
    @FXML
    private MenuItem mois1;
    @FXML
    private MenuItem annee1;
    @FXML
    private MenuItem tout;
    @FXML
    private MenuItem tout1;
    @FXML
    private Label labelAffichage;
    @FXML
    private Label Affichage1;
    AdministrationControleur parent;
    
    DAOCommande daoCommande = new DAOCommande();
    DAOUtilisateur daoUtilisateur = new DAOUtilisateur();
    
    Connection conn = bddUtil.dbConnect();
	private String label1;
	private String label2;
	private String label3;
	private String label4;
	private String label5;
	private String label6;
	private String label7;
	private String label8;
	private String label9;
	private String label10;
	private String label11;
	private String label12;
	private String label13;
	private String detailCommande;

    @FXML
    void fermer(ActionEvent event) {
    	AdministrationControleur.getHistoriqueCommande().close();
    }
    
	/**
	 * Initialise la classe controleur avec les donn閑s par d閒aut du tableau
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			devise.setText(DAOCommande.recupererDevise());
			colonneIDCommande.setCellValueFactory(cellData -> cellData.getValue().idCommandeProperty());
			colonneTable.setCellValueFactory(cellData -> cellData.getValue().noTableProperty());
			colonneDate.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
			colonneMontant.setCellValueFactory(cellData -> cellData.getValue().prixTotalProperty());
	
			commandeTable.setItems(daoCommande.afficher());
			totalCommandeLbl.setText(label1+daoCommande.compterTout().get(0)+label7);
			totalMontantLbl.setText(daoCommande.compterTout().get(1).toString());
			totalServeurLbl.setText(label8+daoCommande.compterTout().get(2));
			
			
			
			colonneID.setCellValueFactory(cellData -> cellData.getValue().idUtilisateurProperty());
			colonneIdentifiant.setCellValueFactory(cellData -> cellData.getValue().identifiantProperty());
			colonneDateConnexion.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
			
			connexionTable.setItems(daoUtilisateur.afficherTout());
			totalConnexionsLbl.setText(label1+daoUtilisateur.compterTout()+label2);
			
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue","D閠ails: "+e);
			e.printStackTrace();
		}
	}
	
	
	private void loadLang(String lang, String LANG) {
		Locale locale = new Locale(lang, LANG);  
		
		ResourceBundle bundle = ResourceBundle.getBundle("gestionbrb/language/Language_"+lang, locale);
		
		detail.setText(bundle.getString("detail"));
		fermer.setText(bundle.getString("fermer"));
		fermer1.setText(bundle.getString("fermer"));
		colonneDate.setText(bundle.getString("Date"));
		colonneTable.setText(bundle.getString("Table"));
		colonneMontant.setText(bundle.getString("Montant"));
		jour.setText(bundle.getString("jour"));
		jour1.setText(bundle.getString("jour"));
		mois.setText(bundle.getString("mois"));
		mois1.setText(bundle.getString("mois"));
		week.setText(bundle.getString("week"));
		week1.setText(bundle.getString("week"));
		annee.setText(bundle.getString("annee"));
		annee1.setText(bundle.getString("annee"));
		tout.setText(bundle.getString("Tout"));
		tout1.setText(bundle.getString("Tout"));
		affichages.setText(bundle.getString("Affichage"));
		labelAffichage.setText(bundle.getString("affichage"));
		totalConnexionsLbl.setText(bundle.getString("totalConnexionsLbl"));
		Affichage1.setText(bundle.getString("affichage"));
		colonneDateConnexion.setText(bundle.getString("DateHeure"));
		colonneIdentifiant.setText(bundle.getString("Identifiant"));
		affichage.setText(bundle.getString("Affichage"));
		
		
		
		label1=bundle.getString("key18");
		label2=bundle.getString("key19");
		label3=bundle.getString("mois");
		label4=bundle.getString("annee");
		label5=bundle.getString("week");
		label6=bundle.getString("aujourdui");
		label7=bundle.getString("commande");
		label8=bundle.getString("totalServeur");
		label9=bundle.getString("Nom");
		label10=bundle.getString("Prix");
		label11=bundle.getString("Serveur");
		label12=bundle.getString("qte");
		label13=bundle.getString("Tablen");
		detailCommande=bundle.getString("detailCommande");
		
	}
	/**
	 * Affiche toutes les commandes effectuées aujourd'hui.
	 * @author Sophie
	 */
	@FXML
	public void afficherAujourdhui() {
		commandeTable.getItems().clear();
		affichage.setText("Aujourd'hui seulement");
		try {
			daoCommande.trierParJour();
			totalCommandeLbl.setText("Total: "+daoCommande.compterParJour().get(0)+" commande(s)");
			totalMontantLbl.setText(daoCommande.compterParJour().get(1)+"");
			totalServeurLbl.setText("Total serveurs: "+daoCommande.compterParJour().get(2));
			
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue","Détails: "+e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Affiche toutes les commandes effectuées cette semaine.
	 * @author Sophie
	 */
	@FXML
	public void afficherSemaine() {
		commandeTable.getItems().clear();
		affichage.setText("Cette semaine");
		try {
			daoCommande.trierParSemaine();
			totalCommandeLbl.setText("Total: "+daoCommande.compterParSemaine().get(0)+" commande(s)");
			totalMontantLbl.setText(daoCommande.compterParSemaine().get(1)+"");
			totalServeurLbl.setText("Total serveurs: "+daoCommande.compterParSemaine().get(2));
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue", "Détails: " + e);
			e.printStackTrace();
		}
	}

	/**
	 * Affiche toutes les commandes effectuées ce mois-ci.
	 * @author Sophie
	 */
	@FXML
	public void afficherMois() {
		commandeTable.getItems().clear();
		affichage.setText("Ce mois-ci");
		try {
			daoCommande.trierParMois();
			totalCommandeLbl.setText("Total: "+daoCommande.compterParMois().get(0)+" commande(s)");
			totalMontantLbl.setText(daoCommande.compterParMois().get(1)+"");
			totalServeurLbl.setText("Total serveurs: "+daoCommande.compterParMois().get(2));
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue", "Détails: " + e);
			e.printStackTrace();
		}
	}

	/**
	 * Affiche toutes les commandes effectuées cette année.
	 * @author Sophie
	 */
	@FXML
	public void afficherAnnee() {
		commandeTable.getItems().clear();
		affichage.setText("Cette année");
		try {
			daoCommande.trierParAnnee();
			totalCommandeLbl.setText("Total: "+daoCommande.compterParAnnee().get(0)+" commande(s)");
			totalMontantLbl.setText(daoCommande.compterParAnnee().get(1)+"");
			totalServeurLbl.setText("Total serveurs: "+daoCommande.compterParAnnee().get(2));
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue", "Détails: " + e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Affiche toutes les commandes effectuées.
	 * @author Sophie
	 */
	@FXML
	public void afficherTout() {
		commandeTable.getItems().clear();
		affichage.setText("Affichage par défaut");
		initialize();
	}
	
	/**
	 * Affiche les produits consommés dans la commande selectionnée.
	 * @author Sophie
	 * @param event
	 */
    @FXML
    void voirDetails(ActionEvent event) {
    	Commande selectedCommande = commandeTable.getSelectionModel().getSelectedItem();
		if (selectedCommande != null) {
			try {
				int i = 1;
				ScrollPane sp = new ScrollPane();
				GridPane conteneur = new GridPane();
				conteneur.setPrefWidth(500);
				conteneur.setPrefHeight(200);
				conteneur.setHgap(25);
				conteneur.setVgap(10);
				Label nomP = new Label("Nom du Produit");
				conteneur.add(nomP, 0, 0);
				Label prixP = new Label("Prix");
				conteneur.add(prixP, 1, 0);
				Label qteP = new Label("Quantité");
				conteneur.add(qteP, 2, 0);
				Label servP = new Label("Serveur");
				conteneur.add(servP, 3, 0);
				Alert boite = new Alert(AlertType.INFORMATION);
				boite.setHeaderText("Table n°"+selectedCommande.getNoTable()+", "+selectedCommande.getNbCouverts()+" couvert(s)");
				ResultSet detailsCommande = conn.createStatement().executeQuery("SELECT produit.nom, produit.prix, contenirproduit.qte, contenirproduit.serveurID FROM `contenirproduit` INNER JOIN produit on contenirproduit.ProduitID = produit.ProduitID WHERE CommandeID = "+selectedCommande.getIdCommande());
				while(detailsCommande.next()) {
					boite.setTitle("Détails de la commande n°"+selectedCommande.getIdCommande());
				Label nom = new Label(detailsCommande.getString("produit.nom"));
				conteneur.add(nom, 0, i);
				Label prix = new Label(detailsCommande.getString("produit.prix"));
				conteneur.add(prix, 1, i);
				Label qte = new Label(detailsCommande.getString("contenirproduit.qte"));
				conteneur.add(qte, 2, i);
				Label serv = new Label(detailsCommande.getString("contenirproduit.serveurID"));
				conteneur.add(serv, 3, i);
				i++;
				}
				sp.setContent(conteneur);
				boite.getDialogPane().setContent(sp);
				boite.showAndWait();
			} catch (Exception e) {
				FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue", "Détails: " + e);
				e.printStackTrace();
			}

		} else {
			// Si rien n'est selectionné
			FonctionsControleurs.alerteAttention("Aucune sélection", "Aucune commande de sélectionnée!",
					"Selectionnez une commande pour pouvoir voir son détail");
		}
    }
    
    /**
	 * Affiche toutes les instances de connexions du jour.
	 * @author Sophie
	 */
    @FXML
	public void afficherConnexionsAujourdhui() {
		connexionTable.getItems().clear();
		affichages.setText("Aujourd'hui seulement");
		try {
			daoUtilisateur.trierParJour();
			totalConnexionsLbl.setText("Total: "+daoUtilisateur.compterParJour()+" connexion(s)");
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue","Détails: "+e);
			e.printStackTrace();
		}
	}
	
    /**
	 * Affiche toutes les instances connexions de la semaine.
	 * @author Sophie
	 */
	@FXML
	public void afficherConnexionsSemaine() {
		connexionTable.getItems().clear();
		affichages.setText("Cette semaine");
		try {
			daoUtilisateur.trierParSemaine();
			totalConnexionsLbl.setText("Total: "+daoUtilisateur.compterParSemaine()+" connexion(s)");
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue", "Détails: " + e);
			e.printStackTrace();
		}
	}

	/**
	 * Affiche toutes les instances connexions du mois.
	 * @author Sophie
	 */
	@FXML
	public void afficherConnexionsMois() {
		connexionTable.getItems().clear();
		affichages.setText("Ce mois-ci");
		try {
			daoUtilisateur.trierParMois();
			totalConnexionsLbl.setText("Total: "+daoUtilisateur.compterParMois()+" connexion(s)");
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue", "Détails: " + e);
			e.printStackTrace();
		}
	}

	/**
	 * Affiche toutes les instances connexions de l'année.
	 * @author Sophie
	 */
	@FXML
	public void afficherConnexionsAnnee() {
		connexionTable.getItems().clear();
		affichages.setText("Cette année");
		try {
			daoUtilisateur.trierParAnnee();
			totalConnexionsLbl.setText("Total: "+daoUtilisateur.compterParAnnee()+" connexion(s)");
		} catch (Exception e) {
			FonctionsControleurs.alerteErreur("Erreur", "Une erreur est survenue", "Détails: " + e);
			e.printStackTrace();
		}
	}
	
	public void setParent(AdministrationControleur administrationControleur) {
		this.parent = administrationControleur;
		
	}
}